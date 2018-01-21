package travelcompare.restapi.logic;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.NonNull;
import travelcompare.restapi.api.model.request.RouteType;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.provider.model.*;
import travelcompare.restapi.provider.perimeter.AirportPerimeterSearchProvider;
import travelcompare.restapi.provider.perimeter.TrainPerimeterSearchProvider;
import travelcompare.restapi.provider.route.AirportRoutesProvider;
import travelcompare.restapi.provider.route.CarRoutesProvider;
import travelcompare.restapi.provider.route.TrainRoutesProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@SuppressWarnings("Duplicates")
public class RouteProvider {

    private AirportPerimeterSearchProvider airportPerimeterSearchProvider = new AirportPerimeterSearchProvider();
    private TrainPerimeterSearchProvider trainPerimeterSearchProvider = new TrainPerimeterSearchProvider();
    private AirportRoutesProvider airportRoutesProvider = new AirportRoutesProvider();
    private CarRoutesProvider carRoutesProvider = new CarRoutesProvider();
    private TrainRoutesProvider trainRoutesProvider = new TrainRoutesProvider();

    public Optional<Route> find(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FuelType fuelType,
            RouteType routeType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        Optional<Route> airportRoute = findAirportRoute(start, destination, radius, date, fuelType, routeType);
        Optional<Route> carRoute = findCarRoute(start, destination, date, fuelType, routeType);
        Optional<Route> trainRoute = findTrainRoute(start, destination, radius, date, fuelType, routeType);

        Route route = null;

        if(airportRoute.isPresent()) {
            route = airportRoute.get();
        }

        if (routeType.equals(RouteType.CHEAPEST)) {
            if(carRoute.isPresent()) {
                if(route == null || route.getPrice() > carRoute.get().getPrice()) {
                    route = carRoute.get();
                }
            }

            if(trainRoute.isPresent()) {
                if(route == null || route.getPrice() > trainRoute.get().getPrice()) {
                    route = trainRoute.get();
                }
            }
        } else {
            if(carRoute.isPresent()) {
                if(route == null || route.getDuration() > carRoute.get().getDuration()) {
                    route = carRoute.get();
                }
            }

            if(trainRoute.isPresent()) {
                if(route == null || route.getDuration() > trainRoute.get().getDuration()) {
                    route = trainRoute.get();
                }
            }
        }

        if(route != null) {
            return Optional.of(route);
        }

        return Optional.empty();
    }

    public Optional<Route> findAirportRoute(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FuelType fuelType,
            RouteType routeType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        List<Airport> startAirports = airportPerimeterSearchProvider.findByLh(start);

        List<Airport> destinationAirports = airportPerimeterSearchProvider.findByLh(destination);

        if (startAirports.get(0).equals(destinationAirports.get(0))) {
            return Optional.empty();
        }

        Route route = null;

        for(Airport startAirport : startAirports) {
            for(Airport destinationAirport : destinationAirports) {
                List<Route> routesFromStartAirportToDestinationAirport = airportRoutesProvider.find(startAirport, destinationAirport, date);

                if (routeType.equals(RouteType.CHEAPEST)) {
                    for (Route routeFromStartAirportToDestinationAirport : routesFromStartAirportToDestinationAirport) {
                        Optional<Route> carRouteToStartAirport = findCarRoute(start, startAirport, date, fuelType, RouteType.CHEAPEST);
                        Optional<Route> carRouteToDestination = findCarRoute(destinationAirport, destination, date, fuelType, RouteType.CHEAPEST);

                        if(carRouteToStartAirport.isPresent() && carRouteToDestination.isPresent()) {
                            Route combinedRoute = carRouteToStartAirport.get().combineWith(routeFromStartAirportToDestinationAirport).combineWith(carRouteToDestination.get());

                            if (route == null || combinedRoute.getPrice() < route.getPrice()) {
                                route = combinedRoute;
                            }
                        }
                    }
                } else {
                    for (Route routeFromStartAirportToDestinationAirport : routesFromStartAirportToDestinationAirport) {
                        Optional<Route> carRouteToStartAirport = findCarRoute(start, startAirport, date, fuelType, RouteType.FASTEST);
                        Optional<Route> carRouteToDestination = findCarRoute(destinationAirport, destination, date, fuelType, RouteType.FASTEST);

                        if(carRouteToStartAirport.isPresent() && carRouteToDestination.isPresent()) {
                            Route fastestRoute = carRouteToStartAirport.get().combineWith(routeFromStartAirportToDestinationAirport).combineWith(carRouteToDestination.get());

                            if ((route == null || fastestRoute.getDuration() < route.getDuration()) || (fastestRoute.getDuration() == route.getDuration() && fastestRoute.getPrice() < route.getPrice())) {
                                route = fastestRoute;
                            }
                        }
                    }
                }
            }
        }

        if(route != null) {
            route.setTransport(Transport.AIRCRAFT);
            return Optional.of(route);
        }

        return Optional.empty();
    }

    public Optional<Route> findTrainRoute(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FuelType fuelType,
            RouteType routeType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        List<TrainStation> startTrainStations = trainPerimeterSearchProvider.findNearest(start, false);
        List<TrainStation> destinationTrainStations = trainPerimeterSearchProvider.findNearest(destination, true);

        Route route = null;

        for(TrainStation startTrainStation : startTrainStations) {
            for(TrainStation destinationTrainStation : destinationTrainStations) {
                List<Route> routesFromStartTrainStationToDestinationTrainStation = trainRoutesProvider.find(startTrainStation, destinationTrainStation, date);

                if (routeType.equals(RouteType.CHEAPEST)) {
                    for (Route routeFromStartTrainStationToDestinationTrainStation : routesFromStartTrainStationToDestinationTrainStation) {
                        Optional<Route> carRouteToStartTrainStation = findCarRoute(start, startTrainStation, date, fuelType, RouteType.CHEAPEST);
                        Optional<Route> carRouteToDestinationTrainStation = findCarRoute(destinationTrainStation, destination, date, fuelType, RouteType.CHEAPEST);

                        if(carRouteToStartTrainStation.isPresent() && carRouteToDestinationTrainStation.isPresent()) {
                            Route combinedRoute = carRouteToStartTrainStation.get().combineWith(routeFromStartTrainStationToDestinationTrainStation).combineWith(carRouteToDestinationTrainStation.get());

                            if (route == null || combinedRoute.getPrice() < route.getPrice()) {
                                route = combinedRoute;
                            }
                        }
                    }
                } else {
                    for (Route routeFromStartTrainStationToDestinationTrainStation : routesFromStartTrainStationToDestinationTrainStation) {
                        Optional<Route> carRouteToStartTrainStation = findCarRoute(start, startTrainStation, date, fuelType, RouteType.FASTEST);
                        Optional<Route> carRouteToDestinationTrainStation = findCarRoute(destinationTrainStation, destination, date, fuelType, RouteType.FASTEST);

                        if(carRouteToStartTrainStation.isPresent() && carRouteToDestinationTrainStation.isPresent()) {
                            Route fastestRoute = carRouteToStartTrainStation.get().combineWith(routeFromStartTrainStationToDestinationTrainStation).combineWith(carRouteToDestinationTrainStation.get());
                            if (route == null || fastestRoute.getDuration() < route.getDuration()) {
                                route = fastestRoute;
                            }
                        }
                    }
                }
            }
        }

        if(route != null) {
            route.setTransport(Transport.TRAIN);
            return Optional.of(route);
        }

        return Optional.empty();
    }

    public Optional<Route> findCarRoute(
            @NonNull Geo start,
            @NonNull Geo destination,
            @NonNull Date date,
            FuelType fuelType,
            RouteType routeType
    ) {
        List<Route> carRoutes = Lists.newArrayList();

        if(fuelType != null) {
            carRoutes = carRoutesProvider.findWithFuelType(start, destination, date, fuelType);
        } else {
            carRoutes = carRoutesProvider.find(start, destination, date);
        }

        Route route = null;

        if (routeType.equals(RouteType.CHEAPEST)) {
            for(Route carRoute: carRoutes) {
                if(route == null || carRoute.getPrice() < route.getPrice()) {
                    route = carRoute;
                }
            }
        } else {
            for(Route carRoute: carRoutes) {
                if(route == null || carRoute.getDuration() < route.getDuration()) {
                    route = carRoute;
                }
            }
        }


        if(route != null) {
            return Optional.of(route);
        }

        return Optional.empty();
    }

}

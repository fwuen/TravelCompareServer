package travelcompare.restapi.logic;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.NonNull;
import travelcompare.restapi.api.model.request.WayType;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.provider.model.*;
import travelcompare.restapi.provider.perimeter.AirportPerimeterSearchProvider;
import travelcompare.restapi.provider.perimeter.TrainPerimeterSearchProvider;
import travelcompare.restapi.provider.way.AirportWaysProvider;
import travelcompare.restapi.provider.way.CarWaysProvider;
import travelcompare.restapi.provider.way.TrainWaysProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("Duplicates")
public class WayProvider {

    private AirportPerimeterSearchProvider airportPerimeterSearchProvider = new AirportPerimeterSearchProvider();
    private TrainPerimeterSearchProvider trainPerimeterSearchProvider = new TrainPerimeterSearchProvider();
    private AirportWaysProvider airportWaysProvider = new AirportWaysProvider();
    private CarWaysProvider carRoutesProvider = new CarWaysProvider();
    private TrainWaysProvider trainWaysProvider = new TrainWaysProvider();

    public Optional<Route> find(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FuelType fuelType,
            WayType wayType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        Optional<Route> airportWay = findAirportWay(start, destination, radius, date, fuelType, wayType);
        Optional<Route> carWay = findCarWay(start, destination, date, fuelType, wayType);
        Optional<Route> trainWay = findTrainWay(start, destination, radius, date, fuelType, wayType);

        Route route = null;

        if(airportWay.isPresent()) {
            route = airportWay.get();
        }

        if (wayType.equals(WayType.CHEAPEST)) {
            if(carWay.isPresent()) {
                if(route == null || route.getPrice() > carWay.get().getPrice()) {
                    route = carWay.get();
                }
            }

            if(trainWay.isPresent()) {
                if(route == null || route.getPrice() > trainWay.get().getPrice()) {
                    route = trainWay.get();
                }
            }
        } else {
            if(carWay.isPresent()) {
                if(route == null || route.getDuration() > carWay.get().getDuration()) {
                    route = carWay.get();
                }
            }

            if(trainWay.isPresent()) {
                if(route == null || route.getDuration() > trainWay.get().getDuration()) {
                    route = trainWay.get();
                }
            }
        }

        if(route != null) {
            return Optional.of(route);
        }

        return Optional.empty();
    }

    public Optional<Route> findAirportWay(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FuelType fuelType,
            WayType wayType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        List<Airport> startAirports = airportPerimeterSearchProvider.findByLh(start, false);

        List<Airport> destinationAirports = airportPerimeterSearchProvider.findByLh(destination, true);

        Route route = null;

        for(Airport startAirport : startAirports) {
            for(Airport destinationAirport : destinationAirports) {
                List<Route> routesFromStartAirportToDestinationAirport = airportWaysProvider.find(startAirport, destinationAirport, date);

                if (wayType.equals(WayType.CHEAPEST)) {
                    for (Route routeFromStartAirportToDestinationAirport : routesFromStartAirportToDestinationAirport) {
                        Optional<Route> carWayToStartAirport = findCarWay(start, startAirport, date, fuelType, WayType.CHEAPEST);
                        Optional<Route> carWayToDestination = findCarWay(destinationAirport, destination, date, fuelType, WayType.CHEAPEST);

                        if(carWayToStartAirport.isPresent() && carWayToDestination.isPresent()) {
                            Route combinedRoute = carWayToStartAirport.get().combineWith(routeFromStartAirportToDestinationAirport).combineWith(carWayToDestination.get());

                            if (route == null || combinedRoute.getPrice() < route.getPrice()) {
                                route = combinedRoute;
                            }
                        }
                    }
                } else {
                    for (Route routeFromStartAirportToDestinationAirport : routesFromStartAirportToDestinationAirport) {
                        Optional<Route> carWayToStartAirport = findCarWay(start, startAirport, date, fuelType, WayType.FASTEST);
                        Optional<Route> carWayToDestination = findCarWay(destinationAirport, destination, date, fuelType, WayType.FASTEST);

                        if(carWayToStartAirport.isPresent() && carWayToDestination.isPresent()) {
                            Route fastestRoute = carWayToStartAirport.get().combineWith(routeFromStartAirportToDestinationAirport).combineWith(carWayToDestination.get());

                            if ((route == null || fastestRoute.getDuration() < route.getDuration()) || (fastestRoute.getDuration() == route.getDuration() && fastestRoute.getPrice() < route.getPrice())) {
                                route = fastestRoute;
                            }
                        }
                    }
                }
            }
        }

        if(route != null) {
            return Optional.of(route);
        }

        return Optional.empty();
    }

    public Optional<Route> findTrainWay(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FuelType fuelType,
            WayType wayType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        List<TrainStation> startTrainStations = trainPerimeterSearchProvider.findNearest(start, false);
        List<TrainStation> destinationTrainStations = trainPerimeterSearchProvider.findNearest(destination, true);

        Route route = null;

        for(TrainStation startTrainStation : startTrainStations) {
            for(TrainStation destinationTrainStation : destinationTrainStations) {
                List<Route> waysFromStartTrainStationToDestinationTrainStation = trainWaysProvider.find(startTrainStation, destinationTrainStation, date);

                if (wayType.equals(WayType.CHEAPEST)) {
                    for (Route routeFromStartTrainStationToDestinationTrainStation : waysFromStartTrainStationToDestinationTrainStation) {
                        Optional<Route> carRouteToStartTrainStation = findCarWay(start, startTrainStation, date, fuelType, WayType.CHEAPEST);
                        Optional<Route> carRouteToDestinationTrainStation = findCarWay(destinationTrainStation, destination, date, fuelType, WayType.CHEAPEST);

                        if(carRouteToStartTrainStation.isPresent() && carRouteToDestinationTrainStation.isPresent()) {
                            Route combinedRoute = carRouteToStartTrainStation.get().combineWith(routeFromStartTrainStationToDestinationTrainStation).combineWith(carRouteToDestinationTrainStation.get());

                            if (route == null || combinedRoute.getPrice() < route.getPrice()) {
                                route = combinedRoute;
                            }
                        }
                    }
                } else {
                    for (Route routeFromStartTrainStationToDestinationTrainStation : waysFromStartTrainStationToDestinationTrainStation) {
                        Optional<Route> carWayToStartTrainStation = findCarWay(start, startTrainStation, date, fuelType, WayType.FASTEST);
                        Optional<Route> carWayToDestinationTrainStation = findCarWay(destinationTrainStation, destination, date, fuelType, WayType.FASTEST);

                        if(carWayToStartTrainStation.isPresent() && carWayToDestinationTrainStation.isPresent()) {
                            Route fastestRoute = carWayToStartTrainStation.get().combineWith(routeFromStartTrainStationToDestinationTrainStation).combineWith(carWayToDestinationTrainStation.get());

                            if (route == null || fastestRoute.getDuration() < route.getDuration()) {
                                route = fastestRoute;
                            }
                        }
                    }
                }
            }
        }

        if(route != null) {
            return Optional.of(route);
        }

        return Optional.empty();
    }

    public Optional<Route> findCarWay(
            @NonNull Geo start,
            @NonNull Geo destination,
            @NonNull Date date,
            FuelType fuelType,
            WayType wayType
    ) {
        List<Route> carRoutes = Lists.newArrayList();

        if(fuelType != null) {
            carRoutes = carRoutesProvider.findWithFuelType(start, destination, date, fuelType);
        } else {
            carRoutes = carRoutesProvider.find(start, destination, date);
        }

        Route route = null;

        if (wayType.equals(WayType.CHEAPEST)) {
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

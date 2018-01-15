package travelcompare.restapi.logic;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.NonNull;
import travelcompare.restapi.external.tankerkoenig.response.FUEL_TYPE;
import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.TrainStation;
import travelcompare.restapi.provider.perimeter.AirportPerimeterSearchProvider;
import travelcompare.restapi.provider.perimeter.TrainPerimeterSearchProvider;
import travelcompare.restapi.provider.way.AirportWaysProvider;
import travelcompare.restapi.provider.way.CarWaysProvider;
import travelcompare.restapi.provider.way.TrainWaysProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CheapestWayProvider {

    private AirportPerimeterSearchProvider airportPerimeterSearchProvider = new AirportPerimeterSearchProvider();
    private TrainPerimeterSearchProvider trainPerimeterSearchProvider = new TrainPerimeterSearchProvider();
    private AirportWaysProvider airportWaysProvider = new AirportWaysProvider();
    private CarWaysProvider carWaysProvider = new CarWaysProvider();
    private TrainWaysProvider trainWaysProvider = new TrainWaysProvider();

    public Optional<Route> find(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FUEL_TYPE fuelType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        Optional<Route> cheapestAirportWay = findCheapestAirportWay(start, destination, radius, date, fuelType);
        Optional<Route> cheapestCarWay = findCheapestCarWay(start, destination, date, fuelType);
        Optional<Route> cheapestTrainWay = findCheapestTrainWay(start, destination, radius, date, fuelType);

        Route cheapestRoute = null;

        if(cheapestAirportWay.isPresent()) {
            cheapestRoute = cheapestAirportWay.get();
        }

        if(cheapestCarWay.isPresent()) {
            if(cheapestRoute == null || cheapestRoute.getPrice() > cheapestCarWay.get().getPrice()) {
                cheapestRoute = cheapestCarWay.get();
            }
        }

        if(cheapestTrainWay.isPresent()) {
            if(cheapestRoute == null || cheapestRoute.getPrice() > cheapestTrainWay.get().getPrice()) {
                cheapestRoute = cheapestTrainWay.get();
            }
        }

        if(cheapestRoute != null) {
            return Optional.of(cheapestRoute);
        }

        return Optional.empty();
    }

    public Optional<Route> findCheapestAirportWay(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FUEL_TYPE fuelType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        List<Airport> startAirports = airportPerimeterSearchProvider.findNearest(start, false);

        List<Airport> destinationAirports = airportPerimeterSearchProvider.findNearest(destination, true);

        Route cheapestRoute = null;

        for(Airport startAirport : startAirports) {
            for(Airport destinationAirport : destinationAirports) {
                List<Route> waysFromStartAirportToDestinationAirport = airportWaysProvider.find(startAirport, destinationAirport, date);

                for(Route routeFromStartAirportToDestinationAirport : waysFromStartAirportToDestinationAirport) {
                    Optional<Route> carWayToStartAirport = findCheapestCarWay(start, startAirport, date, fuelType);
                    Optional<Route> carWayToDestination = findCheapestCarWay(destinationAirport, destination, date, fuelType);

                    if(carWayToStartAirport.isPresent() && carWayToDestination.isPresent()) {
                        Route combinedRoute = carWayToStartAirport.get().combineWith(routeFromStartAirportToDestinationAirport).combineWith(carWayToDestination.get());

                        if(cheapestRoute == null || combinedRoute.getPrice() < cheapestRoute.getPrice()) {
                            cheapestRoute = combinedRoute;
                        }
                    }
                }
            }
        }

        if(cheapestRoute != null) {
            return Optional.of(cheapestRoute);
        }

        return Optional.empty();
    }

    public Optional<Route> findCheapestTrainWay(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FUEL_TYPE fuelType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        List<TrainStation> startTrainStations = trainPerimeterSearchProvider.findNearest(start, false);
        List<TrainStation> destinationTrainStations = trainPerimeterSearchProvider.findNearest(destination, true);

        Route cheapestRoute = null;

        for(TrainStation startTrainStation : startTrainStations) {
            for(TrainStation destinationTrainStation : destinationTrainStations) {
                List<Route> waysFromStartTrainStationToDestinationTrainStation = trainWaysProvider.find(startTrainStation, destinationTrainStation, date);

                for(Route routeFromStartTrainStationToDestinationTrainStation : waysFromStartTrainStationToDestinationTrainStation) {
                    Optional<Route> carWayToStartTrainStation = findCheapestCarWay(start, startTrainStation, date, fuelType);
                    Optional<Route> carWayToDestination = findCheapestCarWay(destinationTrainStation, destination, date, fuelType);

                    if(carWayToStartTrainStation.isPresent() && carWayToDestination.isPresent()) {
                        Route combinedRoute = carWayToStartTrainStation.get().combineWith(routeFromStartTrainStationToDestinationTrainStation).combineWith(carWayToDestination.get());

                        if(cheapestRoute == null || combinedRoute.getPrice() < cheapestRoute.getPrice()) {
                            cheapestRoute = combinedRoute;
                        }
                    }
                }
            }
        }

        if(cheapestRoute != null) {
            return Optional.of(cheapestRoute);
        }

        return Optional.empty();
    }

    public Optional<Route> findCheapestCarWay(
            @NonNull Geo start,
            @NonNull Geo destination,
            @NonNull Date date,
            FUEL_TYPE fuelType
    ) {
        List<Route> carRoutes = Lists.newArrayList();

        if(fuelType != null) {
            carRoutes = carWaysProvider.findWithFuelType(start, destination, date, fuelType);
        } else {
            carRoutes = carWaysProvider.find(start, destination, date);
        }

        Route cheapestRoute = null;

        for(Route carRoute : carRoutes) {
            if(cheapestRoute == null || carRoute.getPrice() < cheapestRoute.getPrice()) {
                cheapestRoute = carRoute;
            }
        }

        if(cheapestRoute != null) {
            return Optional.of(cheapestRoute);
        }

        return Optional.empty();
    }

}

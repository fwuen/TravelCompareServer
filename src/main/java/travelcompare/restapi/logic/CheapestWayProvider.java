package travelcompare.restapi.logic;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.NonNull;
import travelcompare.restapi.external.tankerkoenig.response.FUEL_TYPE;
import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.TrainStation;
import travelcompare.restapi.provider.model.Way;
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

    public Optional<Way> find(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FUEL_TYPE fuelType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        Optional<Way> cheapestAirportWay = findCheapestAirportWay(start, destination, radius, date, fuelType);
        Optional<Way> cheapestCarWay = findCheapestCarWay(start, destination, date, fuelType);
        Optional<Way> cheapestTrainWay = findCheapestTrainWay(start, destination, radius, date, fuelType);

        Way cheapestWay = null;

        if(cheapestAirportWay.isPresent()) {
            cheapestWay = cheapestAirportWay.get();
        }

        if(cheapestCarWay.isPresent()) {
            if(cheapestWay == null || cheapestWay.getPrice() > cheapestCarWay.get().getPrice()) {
                cheapestWay = cheapestCarWay.get();
            }
        }

        if(cheapestTrainWay.isPresent()) {
            if(cheapestWay == null || cheapestWay.getPrice() > cheapestTrainWay.get().getPrice()) {
                cheapestWay = cheapestTrainWay.get();
            }
        }

        if(cheapestWay != null) {
            return Optional.of(cheapestWay);
        }

        return Optional.empty();
    }

    public Optional<Way> findCheapestAirportWay(
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

        Way cheapestWay = null;

        for(Airport startAirport : startAirports) {
            for(Airport destinationAirport : destinationAirports) {
                List<Way> waysFromStartAirportToDestinationAirport = airportWaysProvider.find(startAirport, destinationAirport, date);

                for(Way wayFromStartAirportToDestinationAirport: waysFromStartAirportToDestinationAirport) {
                    Optional<Way> carWayToStartAirport = findCheapestCarWay(start, startAirport, date, fuelType);
                    Optional<Way> carWayToDestination = findCheapestCarWay(destinationAirport, destination, date, fuelType);

                    if(carWayToStartAirport.isPresent() && carWayToDestination.isPresent()) {
                        Way combinedWay = carWayToStartAirport.get().combineWith(wayFromStartAirportToDestinationAirport).combineWith(carWayToDestination.get());

                        if(cheapestWay == null || combinedWay.getPrice() < cheapestWay.getPrice()) {
                            cheapestWay = combinedWay;
                        }
                    }
                }
            }
        }

        if(cheapestWay != null) {
            return Optional.of(cheapestWay);
        }

        return Optional.empty();
    }

    public Optional<Way> findCheapestTrainWay(
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

        Way cheapestWay = null;

        for(TrainStation startTrainStation : startTrainStations) {
            for(TrainStation destinationTrainStation : destinationTrainStations) {
                List<Way> waysFromStartTrainStationToDestinationTrainStation = trainWaysProvider.find(startTrainStation, destinationTrainStation, date);

                for(Way wayFromStartTrainStationToDestinationTrainStation: waysFromStartTrainStationToDestinationTrainStation) {
                    Optional<Way> carWayToStartTrainStation = findCheapestCarWay(start, startTrainStation, date, fuelType);
                    Optional<Way> carWayToDestination = findCheapestCarWay(destinationTrainStation, destination, date, fuelType);

                    if(carWayToStartTrainStation.isPresent() && carWayToDestination.isPresent()) {
                        Way combinedWay = carWayToStartTrainStation.get().combineWith(wayFromStartTrainStationToDestinationTrainStation).combineWith(carWayToDestination.get());

                        if(cheapestWay == null || combinedWay.getPrice() < cheapestWay.getPrice()) {
                            cheapestWay = combinedWay;
                        }
                    }
                }
            }
        }

        if(cheapestWay != null) {
            return Optional.of(cheapestWay);
        }

        return Optional.empty();
    }

    public Optional<Way> findCheapestCarWay(
            @NonNull Geo start,
            @NonNull Geo destination,
            @NonNull Date date,
            FUEL_TYPE fuelType
    ) {
        List<Way> carWays = Lists.newArrayList();

        if(fuelType != null) {
            carWays = carWaysProvider.findWithFuelType(start, destination, date, fuelType);
        } else {
            carWays = carWaysProvider.find(start, destination, date);
        }

        Way cheapestWay = null;

        for(Way carWay: carWays) {
            if(cheapestWay == null || carWay.getPrice() < cheapestWay.getPrice()) {
                cheapestWay = carWay;
            }
        }

        if(cheapestWay != null) {
            return Optional.of(cheapestWay);
        }

        return Optional.empty();
    }

}

package travelcompare.restapi.logic;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.NonNull;
import travelcompare.restapi.api.model.request.WayType;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
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

@SuppressWarnings("Duplicates")
public class WayProvider {

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
            FuelType fuelType,
            WayType wayType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        Optional<Way> airportWay = findAirportWay(start, destination, radius, date, fuelType, wayType);
        Optional<Way> carWay = findCarWay(start, destination, date, fuelType, wayType);
        Optional<Way> trainWay = findTrainWay(start, destination, radius, date, fuelType, wayType);

        Way way = null;

        if(airportWay.isPresent()) {
            way = airportWay.get();
        }

        if (wayType.equals(WayType.CHEAPEST)) {
            if(carWay.isPresent()) {
                if(way == null || way.getPrice() > carWay.get().getPrice()) {
                    way = carWay.get();
                }
            }

            if(trainWay.isPresent()) {
                if(way == null || way.getPrice() > trainWay.get().getPrice()) {
                    way = trainWay.get();
                }
            }
        } else {
            if(carWay.isPresent()) {
                if(way == null || way.getDuration() > carWay.get().getDuration()) {
                    way = carWay.get();
                }
            }

            if(trainWay.isPresent()) {
                if(way == null || way.getDuration() > trainWay.get().getDuration()) {
                    way = trainWay.get();
                }
            }
        }

        if(way != null) {
            return Optional.of(way);
        }

        return Optional.empty();
    }

    public Optional<Way> findAirportWay(
            @NonNull Geo start,
            @NonNull Geo destination,
            int radius,
            @NonNull Date date,
            FuelType fuelType,
            WayType wayType
    ) throws Exception {
        Preconditions.checkArgument(radius > 0, "Der Radius muss positiv sein.");
        Preconditions.checkArgument(radius <= 50000, "Der Radius darf maximal 50000m betragen.");

        List<Airport> startAirports = airportPerimeterSearchProvider.findNearest(start, false);

        List<Airport> destinationAirports = airportPerimeterSearchProvider.findNearest(destination, true);

        Way cheapestWay = null;

        for(Airport startAirport : startAirports) {
            for(Airport destinationAirport : destinationAirports) {
                List<Way> waysFromStartAirportToDestinationAirport = airportWaysProvider.find(startAirport, destinationAirport, date);

                if (wayType.equals(WayType.CHEAPEST)) {
                    for (Way wayFromStartAirportToDestinationAirport : waysFromStartAirportToDestinationAirport) {
                        Optional<Way> carWayToStartAirport = findCarWay(start, startAirport, date, fuelType, WayType.CHEAPEST);
                        Optional<Way> carWayToDestination = findCarWay(destinationAirport, destination, date, fuelType, WayType.CHEAPEST);

                        if(carWayToStartAirport.isPresent() && carWayToDestination.isPresent()) {
                            Way combinedWay = carWayToStartAirport.get().combineWith(wayFromStartAirportToDestinationAirport).combineWith(carWayToDestination.get());

                            if (cheapestWay == null || combinedWay.getPrice() < cheapestWay.getPrice()) {
                                cheapestWay = combinedWay;
                            }
                        }
                    }
                } else {
                    for (Way wayFromStartAirportToDestinationAirport : waysFromStartAirportToDestinationAirport) {
                        Optional<Way> carWayToStartAirport = findCarWay(start, startAirport, date, fuelType, WayType.FASTEST);
                        Optional<Way> carWayToDestination = findCarWay(destinationAirport, destination, date, fuelType, WayType.FASTEST);

                        if(carWayToStartAirport.isPresent() && carWayToDestination.isPresent()) {
                            Way fastestWay = carWayToStartAirport.get().combineWith(wayFromStartAirportToDestinationAirport).combineWith(carWayToDestination.get());

                            if (cheapestWay == null || fastestWay.getDuration() < cheapestWay.getDuration()) {
                                cheapestWay = fastestWay;
                            }
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

    public Optional<Way> findTrainWay(
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

        Way cheapestWay = null;

        for(TrainStation startTrainStation : startTrainStations) {
            for(TrainStation destinationTrainStation : destinationTrainStations) {
                List<Way> waysFromStartTrainStationToDestinationTrainStation = trainWaysProvider.find(startTrainStation, destinationTrainStation, date);

                if (wayType.equals(WayType.CHEAPEST)) {
                    for (Way wayFromStartTrainStationToDestinationTrainStation : waysFromStartTrainStationToDestinationTrainStation) {
                        Optional<Way> carWayToStartTrainStation = findCarWay(start, startTrainStation, date, fuelType, WayType.CHEAPEST);
                        Optional<Way> carWayToDestinationTrainStation = findCarWay(destinationTrainStation, destination, date, fuelType, WayType.CHEAPEST);

                        if(carWayToStartTrainStation.isPresent() && carWayToDestinationTrainStation.isPresent()) {
                            Way combinedWay = carWayToStartTrainStation.get().combineWith(wayFromStartTrainStationToDestinationTrainStation).combineWith(carWayToDestinationTrainStation.get());

                            if (cheapestWay == null || combinedWay.getPrice() < cheapestWay.getPrice()) {
                                cheapestWay = combinedWay;
                            }
                        }
                    }
                } else {
                    for (Way wayFromStartTrainStationToDestinationTrainStation : waysFromStartTrainStationToDestinationTrainStation) {
                        Optional<Way> carWayToStartTrainStation = findCarWay(start, startTrainStation, date, fuelType, WayType.FASTEST);
                        Optional<Way> carWayToDestinationTrainStation = findCarWay(destinationTrainStation, destination, date, fuelType, WayType.FASTEST);

                        if(carWayToStartTrainStation.isPresent() && carWayToDestinationTrainStation.isPresent()) {
                            Way fastestWay = carWayToStartTrainStation.get().combineWith(wayFromStartTrainStationToDestinationTrainStation).combineWith(carWayToDestinationTrainStation.get());

                            if (cheapestWay == null || fastestWay.getDuration() < cheapestWay.getDuration()) {
                                cheapestWay = fastestWay;
                            }
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

    public Optional<Way> findCarWay(
            @NonNull Geo start,
            @NonNull Geo destination,
            @NonNull Date date,
            FuelType fuelType,
            WayType wayType
    ) {
        List<Way> carWays = Lists.newArrayList();

        if(fuelType != null) {
            carWays = carWaysProvider.findWithFuelType(start, destination, date, fuelType);
        } else {
            carWays = carWaysProvider.find(start, destination, date);
        }

        Way way = null;

        if (wayType.equals(WayType.CHEAPEST)) {
            for(Way carWay: carWays) {
                if(way == null || carWay.getPrice() < way.getPrice()) {
                    way = carWay;
                }
            }
        } else {
            for(Way carWay: carWays) {
                if(way == null || carWay.getDuration() < way.getDuration()) {
                    way = carWay;
                }
            }
        }


        if(way != null) {
            return Optional.of(way);
        }

        return Optional.empty();
    }

}

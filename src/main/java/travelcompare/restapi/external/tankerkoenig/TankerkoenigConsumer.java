package travelcompare.restapi.external.tankerkoenig;

import com.google.common.base.Preconditions;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.external.tankerkoenig.response.RangeSearchResponse;

public class TankerkoenigConsumer extends Consumer {

    private static final String API_KEY = "61635f2c-08ac-2bf5-83c6-99108d45c152";
    private static final int MAX_RADIUS_IN_KILOMETERS = 25;



    private double latitude = 50.212932; /* Hof */
    private double longitude = 11.943976; /* Hof */
    private int radiusInKilometers = MAX_RADIUS_IN_KILOMETERS;
    private FuelType fuelType = FuelType.ALL;
    private SORT sort = SORT.dist;



    public TankerkoenigConsumer withLatitude(double latitude) {
        /* TODO: Ist das korrekt? */
        Preconditions.checkArgument(latitude >= -180 && latitude <= 180, "Das Latitude ist nicht gültig. Wähle einen Wert zwischen -180 und 180.");

        this.latitude = latitude;

        return this;
    }

    public TankerkoenigConsumer withLongitude(double longitude) {
        /* TODO: Ist das korrekt? */
        Preconditions.checkArgument(longitude >= -180 && longitude <= 180, "Das Longitude ist nicht gültig. Wähle einen WErt zwischen -180 und 180.");

        this.longitude = longitude;

        return this;
    }

    public TankerkoenigConsumer withRadiusInKilometers(int radiusInKilometers) {
        Preconditions.checkArgument(radiusInKilometers > 0, "Der Radius kann nicht 0 sein.");
        Preconditions.checkArgument(radiusInKilometers <= MAX_RADIUS_IN_KILOMETERS, "Der Radius kann nicht größer als " + String.valueOf(MAX_RADIUS_IN_KILOMETERS) + " sein.");

        this.radiusInKilometers = radiusInKilometers;

        return this;
    }

    public TankerkoenigConsumer withFuelType(FuelType fuelType) {
        Preconditions.checkNotNull(fuelType);
        Preconditions.checkState(!(
                fuelType == FuelType.ALL &&
                        this.sort == SORT.price
        ), "Du kannst nicht nach dem Preis sortieren wenn du alle Treibstoffarten gesetzt hast.");

        this.fuelType = fuelType;

        return this;
    }

    public TankerkoenigConsumer withSort(SORT sort) {
        Preconditions.checkNotNull(sort);
        Preconditions.checkState(!(
                this.fuelType == FuelType.ALL &&
                        sort == SORT.price
        ), "Du kannst nicht nach dem Preis sortieren wenn du alle Treibstoffarten gesetzt hast.");

        this.sort = sort;

        return this;
    }



    public RangeSearchResponse consume() throws UnirestException {
        return Unirest.get(getBaseURL() + "list.php").
                queryString("apikey", API_KEY).
                queryString("lat", String.valueOf(this.latitude)).
                queryString("lng", String.valueOf(this.longitude)).
                queryString("rad", String.valueOf(this.radiusInKilometers)).
                queryString("type", this.fuelType.toString()).
                queryString("sort", this.sort.toString()).
                asObject(RangeSearchResponse.class).getBody();
    }



    @Override
    protected String getBaseURL() {
        return "https://creativecommons.tankerkoenig.de/json/";
    }



    public enum SORT {
        price, dist
    }

}

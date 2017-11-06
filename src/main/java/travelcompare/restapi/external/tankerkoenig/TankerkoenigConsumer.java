package travelcompare.restapi.external.tankerkoenig;

import com.google.common.base.Preconditions;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.tankerkoenig.response.RangeSearchResponse;

public class TankerkoenigConsumer extends Consumer {

    private static final String API_KEY = "57bdc10a-38e9-21e8-f0c9-e7fd65234e42";
    private static final int MAX_RADIUS_IN_KILOMETERS = 25;



    private double latitude = 50.212932; /* Hof */
    private double longitude = 11.943976; /* Hof */
    private int radiusInKilometers = MAX_RADIUS_IN_KILOMETERS;
    private FUEL_TYPE fuelType = FUEL_TYPE.all;
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

    public TankerkoenigConsumer withFuelType(FUEL_TYPE fuelType) {
        Preconditions.checkNotNull(fuelType);
        Preconditions.checkState(!(
            fuelType == FUEL_TYPE.all &&
            this.sort == SORT.price
        ), "Du kannst nicht nach dem Preis sortieren wenn du alle Treibstoffarten gesetzt hast.");

        this.fuelType = fuelType;

        return this;
    }

    public TankerkoenigConsumer withSort(SORT sort) {
        Preconditions.checkNotNull(sort);
        Preconditions.checkState(!(
                this.fuelType == FUEL_TYPE.all &&
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

    public enum FUEL_TYPE {
        e5, e10, diesel, all
    }

    public enum SORT {
        price, dist
    }

}

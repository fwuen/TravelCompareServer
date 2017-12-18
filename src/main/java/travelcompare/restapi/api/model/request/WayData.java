package travelcompare.restapi.api.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class WayData implements Validateable {

    private double lat_start;
    private double lon_start;
    private double lat_end;
    private double lon_end;
    private double distance;
    private long creatorId;
    private double price;

    @Override
    public Validation valid() {
        Validation validation = new Validation(true, "");

        if(lat_start < -85 || lat_start > 85) {
            validation.setError("Der Breitengrad der Startkoordinaten ist falsch!");
            return validation;
        }

        if(lat_end < -85 || lat_end > 85) {
            validation.setError("Der Breitengrad der Endkoordinaten ist falsch !");
            return validation;
        }

        if(lon_start < -180 || lon_start > 180) {
            validation.setError("Der Längengrad der Startkoordinaten ist falsch!");
            return validation;
        }

        if(lon_end < -180 || lon_end > 180) {
            validation.setError("Der Längengrad der Endkoordinaten ist falsch!");
            return validation;
        }

        if(distance < 0) {
            validation.setError("Die Distanz darf nicht kleiner als 0 sein!");
            return validation;
        }

        if(price < 0) {
            validation.setError("Der Preis darf nicht kleiner als 0 sein!");
            return validation;
        }

        return validation;
    }
}

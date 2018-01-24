package travelcompare.restapi.data.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import travelcompare.restapi.api.model.request.Validateable;
import travelcompare.restapi.api.model.request.Validation;

import javax.persistence.*;

@Entity
@ToString
@EqualsAndHashCode
public class RouteInfo implements Validateable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private long id;

    @Column
    @Getter
    @Setter
    private double lat_start;

    @Column
    @Getter
    @Setter
    private double lon_start;

    @Column
    @Getter
    @Setter
    private double lat_end;

    @Column
    @Getter
    @Setter
    private double lon_end;

    @Column
    @Getter
    @Setter
    private double distance;

    @Column
    @Getter
    @Setter
    private long creatorId;

    @Column
    @Getter
    @Setter
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

package travelcompare.restapi.provider.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Airport extends Geo {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String identifier;

    public Airport(double lat, double lon) {
        super(lat, lon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Airport airport = (Airport) o;
        return Objects.equals(name, airport.name) &&
                Objects.equals(identifier, airport.identifier);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, identifier);
    }
}

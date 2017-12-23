package travelcompare.restapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import travelcompare.restapi.provider.model.Geo;

import javax.persistence.*;

@Entity
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private long id;

    @Column
    @Getter
    @Setter
    private String googleId;

    @Column(nullable = false)
    @Getter
    @Setter
    @NonNull
    private String firstName;

    @Column(nullable = false)
    @Getter
    @Setter
    @NonNull
    private String lastName;

    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    @NonNull
    private String email;

    @Column(nullable = false)
    @Getter
    @Setter
    @NonNull
    @JsonIgnore
    private String password;

    @Column
    private double lat;

    @Column
    private double lon;

    public void setGeo(Geo geo) {
        if(geo != null) {
            this.lat = geo.getLat();
            this.lon = geo.getLon();
        }
    }

    public Geo getGeo() {
        return new Geo(this.lat, this.lon);
    }

}

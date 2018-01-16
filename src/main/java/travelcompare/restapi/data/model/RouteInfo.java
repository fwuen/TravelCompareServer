package travelcompare.restapi.data.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@EqualsAndHashCode
public class RouteInfo {

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

}

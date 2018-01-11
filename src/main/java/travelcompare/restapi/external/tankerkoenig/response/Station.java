package travelcompare.restapi.external.tankerkoenig.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Station {

    @JsonProperty(required = true)
    private String id;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty
    private String brand;

    @JsonProperty
    private String street;

    @JsonProperty(value = "place")
    private String city;

    @JsonProperty
    private String houseNumber;

    @JsonProperty(value = "postCode")
    private int zip;

    @JsonProperty(value = "lat", required = true)
    private float latitude;

    @JsonProperty(value = "lng", required = true)
    private float longitude;

    @JsonProperty(value = "dist", required = true)
    private float distance;

    @JsonProperty(value = "diesel")
    private float diesel;

    @JsonProperty(value = "e5")
    private float priceOfPatrol;

    @JsonProperty(value = "e10")
    private float priceOfE10Patrol;

    @JsonProperty
    private boolean isOpen = false;

    @JsonProperty
    private double price;

}

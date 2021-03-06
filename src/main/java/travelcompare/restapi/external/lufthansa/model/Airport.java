package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Airport {
    @JsonProperty(value = "AirportCode")
    private String airportCode;

    @JsonProperty(value = "Position")
    private Position position;

    @JsonProperty(value = "CityCode")
    private String cityCode;

    @JsonProperty(value = "CountryCode")
    private String countryCode;

    @JsonProperty(value = "LocationType")
    private String locationType;

    @JsonProperty(value = "Names")
    private Names names;

    @JsonProperty(value = "Distance")
    private Distance distance;

    @JsonProperty(value = "UtcOffset")
    @JsonIgnoreProperties
    private int utcOffset;

    @JsonProperty(value = "TimeZoneId")
    @JsonIgnoreProperties
    private String timeZoneId;
}

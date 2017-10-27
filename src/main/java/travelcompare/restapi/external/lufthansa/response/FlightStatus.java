package travelcompare.restapi.external.lufthansa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import travelcompare.restapi.external.lufthansa.model.Flight;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
public class FlightStatus {
    private Flight[] flights;
}

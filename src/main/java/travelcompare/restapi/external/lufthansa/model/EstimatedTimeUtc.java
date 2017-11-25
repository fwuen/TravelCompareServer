package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
@Getter
@EqualsAndHashCode
@ToString
public class EstimatedTimeUtc {
    @JsonProperty(value = "DateTime")
    private Date dateTime;
}

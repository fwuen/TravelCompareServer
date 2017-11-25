package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Details {
    @JsonProperty(value = "Stops")
    private Stops stops;

    @JsonProperty(value = "DaysOfOperation")
    private long daysOfOperation;

    @JsonProperty(value = "DatePeriod")
    private DatePeriod datePeriod;
}

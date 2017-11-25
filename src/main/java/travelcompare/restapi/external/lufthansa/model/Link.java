package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Link {
    @JsonProperty(value = "@Href")
    private String href;

    @JsonProperty(value = "@Rel")
    private String rel;
}

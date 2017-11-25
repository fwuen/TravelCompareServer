package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Name {
    @JsonProperty(value = "@LanguageCode")
    private String languageCode;

    @JsonProperty(value = "$")
    private String name;
}

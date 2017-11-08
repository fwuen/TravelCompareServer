package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Name {
    @JsonProperty(value = "@LanguageCode")
    private String languageCode;

    @JsonProperty(value = "$")
    private String name;
}

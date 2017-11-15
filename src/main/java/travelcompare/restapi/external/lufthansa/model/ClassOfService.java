package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassOfService {
    @JsonProperty(value = "Code")
    private Code code;
    
    @JsonProperty(value = "MarketingName")
    private String marketingName;
}

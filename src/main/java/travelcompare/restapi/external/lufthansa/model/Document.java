package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Document {
    @JsonProperty(value = "MessageVersion")
    private String messageVersion;
    
    @JsonProperty(value = "CreateTime")
    private String createTime;
}

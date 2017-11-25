package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Document {
    @JsonProperty(value = "MessageVersion")
    private String messageVersion;
    
    @JsonProperty(value = "CreateTime")
    private String createTime;
}

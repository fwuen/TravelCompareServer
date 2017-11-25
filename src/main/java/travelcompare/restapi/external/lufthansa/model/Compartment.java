package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Compartment {
    @JsonProperty(value = "ClassCode")
    private String classCode;

    @JsonProperty(value ="ClassDesc")
    private String classDesc;

    @JsonProperty(value = "FlyNet")
    private boolean flyNet;

    @JsonProperty(value = "SeatPower")
    private boolean seatPower;

    @JsonProperty(value = "Usb")
    private boolean usb;

    @JsonProperty(value = "LiveTv")
    private boolean liveTv;
}

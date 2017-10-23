package travelcompare.restapi.external.tankerkoenig.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public abstract class Response {

    private boolean ok;

    private String license;

    private String data;

    private Optional<String> message;

}

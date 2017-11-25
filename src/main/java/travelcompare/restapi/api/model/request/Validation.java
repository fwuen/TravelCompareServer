package travelcompare.restapi.api.model.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Validation {

    public Validation(
            boolean valid,
            @NonNull String message
    ) {
        this.valid = valid;
        this.message = message;
    }

    private boolean valid = true;

    @NonNull
    private String message = "";

    public void setError(@NonNull String message) {
        this.valid = false;
        this.message = message;
    }

    public void setSuccess(@NonNull String message) {
        this.valid = true;
        this.message = message;
    }

}

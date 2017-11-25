package travelcompare.restapi.api.model.response;

import lombok.*;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class MessageResponse {

    @Getter
    @NonNull
    private String message;

}

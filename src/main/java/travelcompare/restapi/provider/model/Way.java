package travelcompare.restapi.provider.model;

import com.google.common.collect.Lists;
import lombok.*;
import travelcompare.restapi.provider.model.Route;

import java.util.List;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Way {

    @Getter
    @NonNull
    private List<Route> routes = Lists.newArrayList();

    @Getter
    @Setter
    private double price;
}

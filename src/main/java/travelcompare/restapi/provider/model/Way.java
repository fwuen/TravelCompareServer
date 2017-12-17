package travelcompare.restapi.provider.model;

import com.google.common.collect.Lists;
import lombok.*;

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

    public Way combineWith(@NonNull Way way) {
        this.routes.addAll(way.routes);
        this.price = this.getPrice() + way.getPrice();

        return this;
    }

}

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

    @Getter
    @Setter
    private long duration;

    public Way combineWith(@NonNull Way way) {
        this.routes.addAll(way.routes);
        this.price = this.getPrice() + way.getPrice();
        this.duration = this.getDuration() + way.getDuration();

        return this;
    }

}

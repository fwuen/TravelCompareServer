package travelcompare.restapi.provider.model;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Route {

    @Getter
    @NonNull
    private List<Step> steps = Lists.newArrayList();

    @Getter
    @Setter
    private double price;

    public Route combineWith(@NonNull Route route) {
        this.steps.addAll(route.steps);
        this.price = this.getPrice() + route.getPrice();
        this.duration += route.getDuration();
        this.distance += route.getDistance();

        return this;
    }

    public void addStep(Step step) {
        steps.add(step);
        distance += step.getDistance();
        duration += step.getDuration();
    }

    @Getter
    @Setter
    private double distance;

    @Getter
    @Setter
    @NonNull
    private Transport transport;

    @Getter
    @Setter
    private double duration;

}

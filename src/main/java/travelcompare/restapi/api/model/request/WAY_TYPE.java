package travelcompare.restapi.api.model.request;

public enum WAY_TYPE {

    FASTEST("fastest"),
    CHEAPEST("cheapest");

    private String wayType;

    private WAY_TYPE(String type) {
        wayType = type;
    }
}

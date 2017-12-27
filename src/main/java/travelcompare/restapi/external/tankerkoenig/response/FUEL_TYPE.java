package travelcompare.restapi.external.tankerkoenig.response;

public enum FUEL_TYPE {
    e5("e5"), e10("e10"), diesel("diesel"), all("all");

    private String type;

    FUEL_TYPE(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
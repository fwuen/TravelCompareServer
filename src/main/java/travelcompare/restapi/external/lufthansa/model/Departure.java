package travelcompare.restapi.external.lufthansa.model;

import java.util.Date;

public class Departure {
    private String airportCode;
    private Date scheduledTimeLocal;
    private Date scheduledTimeUTC;
    private TimeStatus timeStatus;
    private Terminal terminal;
}

package travelcompare.restapi.external.viamichelin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.json.XML;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.viamichelin.model.RouteResponse;
import travelcompare.restapi.external.viamichelin.util.ViaMichelinHelper;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ViaMichelinConsumer extends Consumer {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getBaseURL() {
        return ViaMichelinConstants.BASE_URL;
    }

    /**
     * /route Request der Michelin Api zur Berechnung der Step zwischen zwei Zielen
     * Liefert neben den codierten Strecke auch Daten zur Zeit und den Kosten zurück
     *
     * @param longitudeStart double
     * @param latitudeStart  double
     * @param longitudeDest  double
     * @param latitudeDest   double
     * @param itit           double
     * @return RouteResponse
     * @throws UnirestException Exception
     */
    public RouteResponse getRoute(double longitudeStart, double latitudeStart, double longitudeDest, double latitudeDest, int itit, double fuelCosts, Date date) throws UnirestException {
        String url = ViaMichelinConstants.BASE_URL + ViaMichelinConstants.ROUTE_URL;

        ViaMichelinHelper helper = new ViaMichelinHelper();
        if (helper.checkCoordinates(longitudeStart, latitudeStart) == null || helper.checkCoordinates(latitudeDest, longitudeDest) == null) {
            return null;
        }
        String startCoordinates = helper.checkCoordinates(longitudeStart, latitudeStart);
        String endCoordinates = helper.checkCoordinates(longitudeDest, latitudeDest);

        // Zur Not den Default Wert verwenden
        String fuelCost = "&fuelCost=" + fuelCosts;
        if (fuelCosts == 0.0) {
            fuelCost = "";
        }
        String ititString = "&itit=" + itit;
        if (itit < 1 || itit > 4) {
            ititString = "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = "&date=" + format.format(date);
        if (date == null) {
            dateString = "";
        }
        HttpResponse httpResponse = Unirest.get(url +
                ".xml/deu?steps=" + startCoordinates + ";" + endCoordinates
                + ititString + fuelCost +
                "&authkey=" + ViaMichelinConstants.API_KEY).asString();

        JSONObject response = XML.toJSONObject(httpResponse.getBody().toString());
        return new ViaMichelinHelper().createResponseModel(response.getJSONObject("response"));
    }
}

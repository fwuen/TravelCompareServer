package travelcompare.restapi.external.viamichelin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.json.XML;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.viamichelin.model.RouteResponse;
import travelcompare.restapi.external.viamichelin.util.ViaMichelinHelper;


public class ViaMichelinConsumer extends Consumer {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getBaseURL() {
        return ViaMichelinConstants.BASE_URL;
    }

    /**
     * @param country String
     * @param zip     String
     * @param city    String
     * @param address String
     * @throws UnirestException exception
     */
    public void getGeoCoding(String country, String zip, String city, String address) throws UnirestException {
        String destination;
        if (address != null) {
            destination = "&address=" + address;
        } else if (city != null) {
            destination = "&city=" + city;
        } else if (zip != null) {
            destination = "&zip=" + zip;
        } else {
            return;
        }
        HttpResponse httpResponse = Unirest.get(getBaseURL() + ViaMichelinConstants.GEOCODING_URL + "?country=" + country + destination + "&authkey=" + ViaMichelinConstants.API_KEY).asString();
    }

    /**
     * /route Request der Michelin Api zur Berechnung der Route zwischen zwei Zielen
     * Liefert neben den codierten Strecke auch Daten zur Zeit und den Kosten zurück
     *
     * @param longitudeStart double
     * @param latitudeStart  double
     * @param longitudeDest  double
     * @param latitudeDest   double
     * @return RouteResponse
     * @throws UnirestException Exception
     */


    RouteResponse getRoute(double longitudeStart, double latitudeStart, double longitudeDest, double latitudeDest) throws UnirestException {
        HttpResponse httpResponse = Unirest.get("http://apir.viamichelin.com/apir/1/" + ViaMichelinConstants.ROUTE_URL + ".xml/deu?steps=1:e:" + longitudeStart + ":" + latitudeStart + ";1:e:" + longitudeDest + ":" + latitudeDest + "&data=roadsheet&itit=3&authkey=" + ViaMichelinConstants.API_KEY).asString();
        JSONObject response = XML.toJSONObject(httpResponse.getBody().toString());
        return new ViaMichelinHelper().createResponseModel(response.getJSONObject("response"));
    }
}

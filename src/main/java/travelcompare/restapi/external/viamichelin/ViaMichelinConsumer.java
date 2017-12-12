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
     public RouteResponse getRoute(double longitudeStart, double latitudeStart, double longitudeDest, double latitudeDest, double fuelCosts) throws UnirestException {
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

        HttpResponse httpResponse = Unirest.get(url +
                ".xml/deu?steps=" + startCoordinates + ";" + endCoordinates
                + fuelCost +
                "&authkey=" + ViaMichelinConstants.API_KEY).asString();

        JSONObject response = XML.toJSONObject(httpResponse.getBody().toString());
        return new ViaMichelinHelper().createResponseModel(response.getJSONObject("response"));
    }
}

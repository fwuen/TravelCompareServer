package travelcompare.restapi.external.viamichelin.util;

import org.json.JSONArray;
import org.json.JSONObject;
import travelcompare.restapi.external.viamichelin.model.*;

import java.util.ArrayList;

public class ViaMichelinHelper {

    /**
     * Helper Methode, die aus der JSON Antwort auf den /route Request, ein RouteResponseModel erzeugt
     *
     * @param jsonObject JSONObject
     * @return RouteResponse
     */
    public RouteResponse createResponseModel(JSONObject jsonObject) {
        RouteResponse response = new RouteResponse();

        RouteIti routeIti = new RouteIti();
        if (!jsonObject.has("iti")) {
            return null;
        }
        JSONObject itiJSON = jsonObject.getJSONObject("iti");

        if (!itiJSON.has("roadSheet") || itiJSON.getJSONObject("roadSheet") == null || this.parseRoadSheet(itiJSON.getJSONObject("roadSheet")) == null) {
            return null;
        }
        routeIti.setRoadSheet(this.parseRoadSheet(itiJSON.getJSONObject("roadSheet")));


        if (!itiJSON.has("header") || itiJSON.getJSONObject("header") == null || this.parseRouteHeader(itiJSON.getJSONObject("header")) == null) {
            return null;
        }
        routeIti.setHeader(parseRouteHeader(itiJSON.getJSONObject("header")));


        response.setIti(routeIti);
        return response;
    }

    /**
     * Hilfsmethode zu Parsen des RoadSheet-Objekts
     *
     * @param jsonObject JSONObject
     * @return RoadSheet
     */
    private RoadSheet parseRoadSheet(JSONObject jsonObject) {
        RoadSheet roadSheet = new RoadSheet();

        if (!jsonObject.has("roadSheetStepList") || jsonObject.getJSONObject("roadSheetStepList") == null) {
            return null;
        }
        JSONObject roadSheetStepListJSON = jsonObject.getJSONObject("roadSheetStepList");

        if (!roadSheetStepListJSON.has("roadSheetStep") || roadSheetStepListJSON.optJSONArray("roadSheetStep") == null) {
            return null;
        }

        JSONArray roadSheetStepJSON = roadSheetStepListJSON.optJSONArray("roadSheetStep");
        ArrayList<RoadSheetStep> roadSheetStep = new ArrayList<>();
        for (int i = 0; i < roadSheetStepJSON.length(); i++) {
            RoadSheetStep step = new RoadSheetStep();
            if (roadSheetStepJSON.isNull(i)) {
                return null;
            }
            JSONObject item = roadSheetStepJSON.getJSONObject(i);

            if (!item.has("duration")) {
                return null;
            }
            step.setDuration(item.getDouble("duration") / 60);

            if (!item.has("instructions") || item.getString("instructions") == null) {
                return null;
            }
            step.setInstructions(item.getString("instructions"));

            if (item.has("distance")) {
                step.setDistance(item.getLong("distance"));
            }

            if (!item.has("coords") || item.getJSONObject("coords") == null) {
                return null;
            }
            JSONObject routeCoordsJSON = item.getJSONObject("coords");
            RouteCoords routeCoords = new RouteCoords();

            if (!routeCoordsJSON.has("lat")) {
                return null;
            }
            routeCoords.setLat_coordinate(routeCoordsJSON.getDouble("lat"));

            if (!routeCoordsJSON.has("lon")) {
                return null;
            }
            routeCoords.setLong_coordinate(routeCoordsJSON.getDouble("lon"));
            step.setCoords(routeCoords);

            roadSheetStep.add(step);
        }
        roadSheet.setRoadSheetStep(roadSheetStep);

        return roadSheet;
    }

    /**
     * @param jsonObject JSONObject
     * @return RouteHeader
     */
    private RouteHeader parseRouteHeader(JSONObject jsonObject) {
        RouteHeader header = new RouteHeader();

        if (!jsonObject.has("summaries") || jsonObject.getJSONObject("summaries") == null) {
            return null;
        }
        JSONObject summariesJSON = jsonObject.getJSONObject("summaries");

        if (!summariesJSON.has("summary") || summariesJSON.getJSONObject("summary") == null) {
            return null;
        }
        JSONObject summaryJSON = summariesJSON.getJSONObject("summary");
        Summary summary = new Summary();

        if (summaryJSON.has("totalTime")) {
            summary.setTotalTime(summaryJSON.getDouble("totalTime"));
        }

        // TODO wichtig
        if (!summaryJSON.has("consumption")) {
            return null;
        }
        summary.setConsumption(summaryJSON.getDouble("consumption"));

        // TODO wichtig
        if (!summaryJSON.has("totalDist")) {
            return null;
        }
        summary.setTotalDist(summaryJSON.getDouble("totalDist"));

        header.setSummaries(summary);

        return header;
    }

    /**
     * Überprüfen der Koordinaten
     *
     * @param longitude double
     * @param latitude  double
     * @return String
     */
    public String checkCoordinates(double longitude, double latitude) {
        if (longitude > 180.0 || longitude < -180.0 | latitude > 90.0 | latitude < -90.0) {
            return null;
        }
        return "1:e:" + longitude +
                ":" + latitude;
    }
}

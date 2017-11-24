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
        JSONObject itiJSON = jsonObject.getJSONObject("iti");

        routeIti.setItitraceLevels(itiJSON.getString("ititraceLevels"));
        routeIti.setItineraryTrace(itiJSON.getString("itineraryTrace"));
        routeIti.setRoadSheet(this.parseRoadSheet(itiJSON.getJSONObject("roadSheet")));
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

        JSONObject roadSheetStepListJSON = jsonObject.getJSONObject("roadSheetStepList");

        JSONArray roadSheetStepJSON = roadSheetStepListJSON.getJSONArray("roadSheetStep");
        ArrayList<RoadSheetStep> roadSheetStep = new ArrayList<>();

        // TODO Überprüfungen hinzufügen
        for (int i = 0; i < roadSheetStepJSON.length(); i++) {
            RoadSheetStep step = new RoadSheetStep();
            JSONObject item = roadSheetStepJSON.getJSONObject(i);

            step.setDuration(item.getLong("duration"));
            step.setInstructions(item.getString("instructions"));

            if (item.has("mapDef")) {
                MapDef mapDef = this.parseMapDef(item.getJSONObject("mapDef"));
                step.setMapDef(mapDef);
            }
            step.setDistance(item.getDouble("distance"));
            step.setLevel(item.getLong("level"));
            step.setPartialDuration(item.getLong("partialDuration"));
            step.setGathering(item.getLong("gathering"));
            step.setPartialDistance(item.getDouble("partialDistance"));

            JSONObject routeCoordsJSON = item.getJSONObject("coords");
            RouteCoords routeCoords = new RouteCoords();
            routeCoords.setLat_coordinate(routeCoordsJSON.getDouble("lat"));
            routeCoords.setLong_coordinate(routeCoordsJSON.getDouble("lon"));
            step.setCoords(routeCoords);

            step.setPicto(item.getString("picto"));
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

        MapDef startMapDef = this.parseMapDef(jsonObject.getJSONObject("startMapDef"));
        header.setStartMapDef(startMapDef);

        MapDef destMapDef = this.parseMapDef(jsonObject.getJSONObject("destMapDef"));
        header.setDestMapDef(destMapDef);

        header.setItiType(jsonObject.getLong("itiType"));
        header.setItidate(jsonObject.getString("itidate"));
        header.setIdx(jsonObject.getLong("idx"));
        header.setVehicle(jsonObject.getLong("vehicle"));

        JSONObject summariesJSON = jsonObject.getJSONObject("summaries");
        JSONObject summaryJSON = summariesJSON.getJSONObject("summary");
        Summary summary = new Summary();
        summary.setEcoTaxDist(summaryJSON.getDouble("ecoTaxDist"));
        summary.setFullMapDef(this.parseMapDef(summaryJSON.getJSONObject("fullMapDef")));
        summary.setMotorwayTime(summaryJSON.getDouble("motorwayTime"));
        summary.setPleasentDist(summaryJSON.getDouble("pleasantDist"));
        summary.setTotalTime(summaryJSON.getDouble("totalTime"));
        summary.setPleasentTime(summaryJSON.getDouble("pleasantTime"));
        summary.setIndex(summaryJSON.getLong("index"));
        summary.setDrivingDist(summaryJSON.getDouble("drivingTime"));
        summary.setConsumption(summaryJSON.getDouble("consumption"));
        summary.setDistanceByCountry(summaryJSON.getString("distanceByCountry"));
        summary.setCCZCost(this.parseCost(summaryJSON.getJSONObject("CCZCost")));
        summary.setTollCost(this.parseCost(summaryJSON.getJSONObject("tollCost")));
        summary.setEcoTaxRepercussionRate(summaryJSON.getDouble("ecoTaxRepercussionRate"));
        summary.setAvoidClosedRoadUsed(summaryJSON.getBoolean("avoidClosedRoadUsed"));

        JSONObject namesJSON = summaryJSON.getJSONObject("names");
        JSONArray nameJSON = namesJSON.getJSONArray("name");
        ArrayList<String> name = new ArrayList<>();
        for (int i = 0; i < nameJSON.length(); i++) {
            name.add(nameJSON.getString(i));
        }
        summary.setNames(name);
        summary.setEventTrafficDatabaseAvailable(summaryJSON.getBoolean("eventTrafficDatabaseAvailable"));
        summary.setDrivingDist(summaryJSON.getDouble("drivingDist"));
        summary.setTotalDist(summaryJSON.getDouble("totalDist"));
        summary.setEcoTax(summaryJSON.getDouble("ecoTax"));
        summary.setMotorwayDist(summaryJSON.getDouble("motorwayDist"));

        header.setSummaries(summary);

        return header;
    }

    /**
     * Hilfsmethode zu Parsen des MapDef-Objekts
     *
     * @param jsonObject JSONObject
     * @return MapDef
     */
    private MapDef parseMapDef(JSONObject jsonObject) {
        JSONObject size = jsonObject.getJSONObject("size");
        MapDef mapDef = new MapDef();
        mapDef.setId(jsonObject.getString("id"));

        MapSize mapSize = new MapSize();
        mapSize.setW(size.getLong("w"));
        mapSize.setH(size.getLong("h"));
        mapDef.setSize(mapSize);

        return mapDef;
    }

    /**
     * Hilfsmethode zu Parsen des Cost-Objekts
     *
     * @param jsonObject JSONObject
     * @return Cost
     */
    private Cost parseCost(JSONObject jsonObject) {
        Cost cost = new Cost();
        cost.setCar(jsonObject.getDouble("car"));
        cost.setCaravan(jsonObject.getDouble("caravan"));
        cost.setPl2(jsonObject.getDouble("pl2"));
        cost.setPl3(jsonObject.getDouble("pl3"));
        cost.setPl4(jsonObject.getDouble("pl4"));
        cost.setPl5(jsonObject.getDouble("pl5"));
        cost.setMoto(jsonObject.getDouble("moto"));

        return cost;
    }


}

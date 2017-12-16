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

        if (!itiJSON.has("ititraceLevels") || itiJSON.getString("ititraceLevels") == null) {
            return null;
        }
        routeIti.setItitraceLevels(itiJSON.getString("ititraceLevels"));


        if (!itiJSON.has("itineraryTrace") || itiJSON.getString("itineraryTrace") == null) {
            return null;
        }
        routeIti.setItineraryTrace(itiJSON.getString("itineraryTrace"));


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
            step.setDuration(item.getLong("duration"));

            if (!item.has("instructions") || item.getString("instructions") == null) {
                return null;
            }
            step.setInstructions(item.getString("instructions"));

            if (item.has("mapDef")) {
                if (item.has("mapDef") && item.getJSONObject("mapDef") != null) {
                    MapDef mapDef = this.parseMapDef(item.getJSONObject("mapDef"));
                    step.setMapDef(mapDef);
                }

            }
            if (item.has("distance")) {
                step.setDistance(item.getDouble("distance"));
            }

            if (item.has("level")) {
                step.setLevel(item.getLong("level"));
            }

            if (item.has("partialDuration")) {
                step.setPartialDuration(item.getLong("partialDuration"));
            }

            if (item.has("gathering")) {
                step.setGathering(item.getLong("gathering"));
            }

            if (item.has("partialDistance")) {
                step.setPartialDistance(item.getDouble("partialDistance"));
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

            if (item.has("picto") && item.getString("picto") != null) {
                step.setPicto(item.getString("picto"));
            }
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

        if (jsonObject.has("startMapDef") && this.parseMapDef(jsonObject.getJSONObject("startMapDef")) != null) {
            MapDef startMapDef = this.parseMapDef(jsonObject.getJSONObject("startMapDef"));
            header.setStartMapDef(startMapDef);
        }


        if (jsonObject.has("startMapDef") && this.parseMapDef(jsonObject.getJSONObject("startMapDef")) != null) {
            MapDef destMapDef = this.parseMapDef(jsonObject.getJSONObject("destMapDef"));
            header.setDestMapDef(destMapDef);
        }


        if (jsonObject.has("itiType")) {
            header.setItiType(jsonObject.getLong("itiType"));

        }

        if (jsonObject.has("itidate") && jsonObject.getString("itidate") != null) {
            header.setItidate(jsonObject.getString("itidate"));

        }

        if (jsonObject.has("idx")) {
            header.setIdx(jsonObject.getLong("idx"));

        }

        if (jsonObject.has("vehicle")) {
            header.setVehicle(jsonObject.getLong("vehicle"));
        }

        if (!jsonObject.has("summaries") || jsonObject.getJSONObject("summaries") == null) {
            return null;
        }
        JSONObject summariesJSON = jsonObject.getJSONObject("summaries");

        if (!summariesJSON.has("summary") || summariesJSON.getJSONObject("summary") == null) {
            return null;
        }
        JSONObject summaryJSON = summariesJSON.getJSONObject("summary");
        Summary summary = new Summary();

        if (summaryJSON.has("ecoTaxDist")) {
            summary.setEcoTaxDist(summaryJSON.getDouble("ecoTaxDist"));

        }

        if (summaryJSON.has("fullMapDef") && summaryJSON.getJSONObject("fullMapDef") != null) {
            summary.setFullMapDef(this.parseMapDef(summaryJSON.getJSONObject("fullMapDef")));
        }

        if (summaryJSON.has("motorwayTime")) {
            summary.setMotorwayTime(summaryJSON.getDouble("motorwayTime"));
        }

        if (summaryJSON.has("pleasantDist")) {
            summary.setPleasentDist(summaryJSON.getDouble("pleasantDist"));
        }

        if (summaryJSON.has("totalTime")) {
            summary.setTotalTime(summaryJSON.getDouble("totalTime"));
        }

        if (summaryJSON.has("pleasantTime")) {
            summary.setPleasentTime(summaryJSON.getDouble("pleasantTime"));
        }

        if (summaryJSON.has("index")) {
            summary.setIndex(summaryJSON.getLong("index"));

        }

        if (summaryJSON.has("drivingTime")) {
            summary.setDrivingDist(summaryJSON.getDouble("drivingTime"));
        }

        // TODO wichtig
        if (!summaryJSON.has("consumption")) {
            return null;
        }
        summary.setConsumption(summaryJSON.getDouble("consumption"));

        if (summaryJSON.has("distanceByCountry")) {
            summary.setDistanceByCountry(summaryJSON.getString("distanceByCountry"));
        }

        if (summaryJSON.has("CCZCost") && summaryJSON.getJSONObject("CCZCost") != null && this.parseCost(summaryJSON.getJSONObject("CCZCost")) != null) {
            summary.setCCZCost(this.parseCost(summaryJSON.getJSONObject("CCZCost")));
        }

        // TODO wichtig
        if (summaryJSON.has("tollCost") && summaryJSON.getJSONObject("tollCost") != null && this.parseCost(summaryJSON.getJSONObject("tollCost")) != null) {
            summary.setTollCost(this.parseCost(summaryJSON.getJSONObject("tollCost")));
        }

        if (summaryJSON.has("ecoTaxRepercussionRate")) {
            summary.setEcoTaxRepercussionRate(summaryJSON.getDouble("ecoTaxRepercussionRate"));
        }

        if (summaryJSON.has("avoidClosedRoadUsed")) {
            summary.setAvoidClosedRoadUsed(summaryJSON.getBoolean("avoidClosedRoadUsed"));
        }

        if (summaryJSON.has("names") && summaryJSON.getJSONObject("names") != null) {
            JSONObject namesJSON = summaryJSON.getJSONObject("names");

            if (namesJSON.has("name") && namesJSON.optJSONArray("name") != null) {
                JSONArray nameJSON = namesJSON.optJSONArray("name");
                ArrayList<String> name = new ArrayList<>();
                for (int i = 0; i < nameJSON.length(); i++) {
                    if (!nameJSON.isNull(i)) {
                        name.add(nameJSON.getString(i));
                    }
                }
                summary.setNames(name);
            }
        }
        if (summaryJSON.has("eventTrafficDatabaseAvailable")) {
            summary.setEventTrafficDatabaseAvailable(summaryJSON.getBoolean("eventTrafficDatabaseAvailable"));
        }

        // TODO wichtig
        if (!summaryJSON.has("drivingDist")) {
            return null;
        }
        summary.setDrivingDist(summaryJSON.getDouble("drivingDist"));

        // TODO wichtig
        if (!summaryJSON.has("totalDist")) {
            return null;
        }
        summary.setTotalDist(summaryJSON.getDouble("totalDist"));

        if (summaryJSON.has("ecoTax")) {
            summary.setEcoTax(summaryJSON.getDouble("ecoTax"));
        }

        if (summaryJSON.has("motorwayDist")) {
            summary.setMotorwayDist(summaryJSON.getDouble("motorwayDist"));
        }

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
        if (!jsonObject.has("size")) {
            return null;
        }
        JSONObject size = jsonObject.getJSONObject("size");
        MapDef mapDef = new MapDef();

        if (!jsonObject.has("id") && jsonObject.getString("id") == null) {
            return null;
        }
        mapDef.setId(jsonObject.getString("id"));

        MapSize mapSize = new MapSize();
        if (!size.has("w")) {
            return null;
        }
        mapSize.setW(size.getLong("w"));

        if (!size.has("h")) {
            return null;
        }
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
        if (!jsonObject.has("car")) {
            return null;
        }
        cost.setCar(jsonObject.getDouble("car"));

        if (!jsonObject.has("caravan")) {
            return null;
        }
        cost.setCaravan(jsonObject.getDouble("caravan"));

        if (!jsonObject.has("pl2")) {
            return null;
        }
        cost.setPl2(jsonObject.getDouble("pl2"));

        if (!jsonObject.has("pl3")) {
            return null;
        }
        cost.setPl3(jsonObject.getDouble("pl3"));

        if (!jsonObject.has("pl4")) {
            return null;
        }
        cost.setPl4(jsonObject.getDouble("pl4"));

        if (!jsonObject.has("pl5")) {
            return null;
        }
        cost.setPl5(jsonObject.getDouble("pl5"));

        if (!jsonObject.has("moto")) {
            return null;
        }
        cost.setMoto(jsonObject.getDouble("moto"));

        return cost;
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

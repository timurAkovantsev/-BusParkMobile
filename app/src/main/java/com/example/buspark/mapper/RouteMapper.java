package com.example.buspark.mapper;

import com.example.buspark.model.Route;

import org.json.JSONException;
import org.json.JSONObject;

public class RouteMapper {

    public Route routeFromBusJsonArray(JSONObject jsonObject) {

        Route route = null;
        try {
            route = new Route(
                    jsonObject.getJSONObject("routeDto").getLong("id"),
                    jsonObject.getJSONObject("routeDto").getString("name")
            );
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return route;
    }

    public Route routeFromJsonArray(JSONObject jsonObject) {

        Route route = null;
        try {

            route = new Route(
                    jsonObject.getLong("id"),
                    jsonObject.getString("name")
            );
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return route;
    }
}
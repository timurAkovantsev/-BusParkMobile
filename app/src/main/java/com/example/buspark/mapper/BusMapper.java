package com.example.buspark.mapper;

import com.example.buspark.model.Bus;
import com.example.buspark.model.Driver;
import com.example.buspark.model.Route;

import org.json.JSONException;
import org.json.JSONObject;

public class BusMapper {

    public Bus busFromJsonArray(JSONObject jsonObject, Route route, Driver driver) {

        Bus bus = null;

        try {
            bus = new Bus(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    route,
                    driver
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bus;
    }

}
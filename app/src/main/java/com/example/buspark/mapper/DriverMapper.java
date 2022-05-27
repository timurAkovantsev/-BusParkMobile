package com.example.buspark.mapper;

import com.example.buspark.model.Driver;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverMapper {

    public Driver driverFromBusJsonArray(JSONObject jsonObject) {

        Driver driver = null;
        try {

            driver = new Driver(
                    jsonObject.getJSONObject("driverDto").getLong("id"),
                    jsonObject.getJSONObject("driverDto").getString("name")
            );
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return driver;
    }

    public Driver driverFromJsonArray(JSONObject jsonObject) {

        Driver driver = null;
        try {

            driver = new Driver(
                    jsonObject.getLong("id"),
                    jsonObject.getString("name")
            );
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return driver;
    }

}
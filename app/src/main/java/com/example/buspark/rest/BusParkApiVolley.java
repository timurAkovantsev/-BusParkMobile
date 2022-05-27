package com.example.buspark.rest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buspark.MainActivity;
import com.example.buspark.mapper.BusMapper;
import com.example.buspark.mapper.DriverMapper;
import com.example.buspark.mapper.RouteMapper;
import com.example.buspark.model.Bus;
import com.example.buspark.model.Driver;
import com.example.buspark.model.Route;
import com.example.buspark.nodb.BusParkDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BusParkApiVolley implements BusParkApi{

    private final Context context;
    public static final String BASE_URL = "http://192.168.0.194:8080";

    private Response.ErrorListener errorListener;

    public BusParkApiVolley(Context context) {
        this.context = context;
    }


    @Override
    public void fillBus() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/buses";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            BusParkDB.BUS_LIST.clear();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                Driver driver = new DriverMapper().driverFromBusJsonArray(jsonObject);

                                Route route = new RouteMapper().routeFromBusJsonArray(jsonObject);

                                Bus bus = new BusMapper().busFromJsonArray(jsonObject, route, driver);
                                BusParkDB.BUS_LIST.add(bus);
                            }
                            Log.d("BUS_LIST", BusParkDB.BUS_LIST.toString());
                            ((MainActivity) context).update();
                        } catch (JSONException e) {

                            Log.d("BUS_LIST", e.getMessage());
                        }

                    }
                },
                errorListener
        );

        queue.add(jsonArrayRequest);
    }

    @Override
    public void fillDriver() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/drivers";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                Driver driver = new DriverMapper().driverFromJsonArray(jsonObject);

                                BusParkDB.DRIVER_LIST.add(driver);
                            }
                            Log.d("DRIVER_LIST", BusParkDB.DRIVER_LIST.toString());
                        } catch (JSONException e) {

                            Log.d("DRIVER_LIST", e.getMessage());
                        }

                    }
                },

                errorListener
        );

        queue.add(jsonArrayRequest);
    }

    @Override
    public void fillRoute() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/routes";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                Route route = new RouteMapper().routeFromJsonArray(jsonObject);

                                BusParkDB.ROUTE_LIST.add(route);
                            }
                            Log.d("ROUTES_LIST", BusParkDB.ROUTE_LIST.toString());
                        } catch (JSONException e) {

                            Log.d("ROUTES_LIST", e.getMessage());
                        }

                    }
                },

                errorListener
        );

        queue.add(jsonArrayRequest);
    }

    @Override
    public void addBus(Bus bus) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/buses";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        fillBus();
                    }
                },

                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("busName", bus.getName());
                params.put("routeName", bus.getRoute().getName());
                params.put("driverName", bus.getDriver().getName());

                return params;
            }
        };

        queue.add(postRequest);
    }

    @Override
    public void updateBus(int id, String newBusName, String newRouteName, String newDriverName) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/buses/" + id + "/";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        fillBus();
                    }
                },
                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("newBusName", newBusName);
                params.put("newRouteName", newRouteName);
                params.put("newDriverName", newDriverName);
                params.put("id", String.valueOf(id));

                return params;
            }
        };

        queue.add(postRequest);
    }

    @Override
    public void deleteBus(int id) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/buses/" + id;

        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        fillBus();
                    }
                },

                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };

        queue.add(dr);
    }

    private class ErrorListenerImpl implements Response.ErrorListener {


        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
    }
}
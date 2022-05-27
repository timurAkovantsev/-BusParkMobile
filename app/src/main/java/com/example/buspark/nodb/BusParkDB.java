package com.example.buspark.nodb;

import com.example.buspark.model.Bus;
import com.example.buspark.model.Driver;
import com.example.buspark.model.Route;

import java.util.ArrayList;
import java.util.List;

public class BusParkDB {

        private BusParkDB(){}

        public static final List<Bus> BUS_LIST = new ArrayList<>();

        public static final List<Route> ROUTE_LIST = new ArrayList<>();

        public static final List<Driver> DRIVER_LIST = new ArrayList<>();
}

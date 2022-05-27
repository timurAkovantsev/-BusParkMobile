package com.example.buspark.model;

import java.io.Serializable;

public class Bus implements Serializable {

    private int id;

    private String name;

    private Route route;

    private Driver driver;

    public Bus(int id, String name, Route route, Driver driver) {
        this.id = id;
        this.name = name;
        this.route = route;
        this.driver = driver;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Route getRoute() {
        return route;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", route=" + route +
                ", driver=" + driver +
                '}';
    }
}

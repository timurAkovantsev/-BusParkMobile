package com.example.buspark.rest;

import com.example.buspark.model.Bus;

public interface BusParkApi {

    void fillBus();

    void fillRoute();

    void fillDriver();

    void addBus(Bus bus);

    void updateBus(
            int id,
            String newBusName,
            String newRouteName,
            String newDriverName
    );

    void deleteBus(int id);
}

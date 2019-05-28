package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehiclePartData {

    @JsonProperty("vehiclePartName")
    String vehiclePartName;

    public String getVehiclePartName() {
        return vehiclePartName;
    }
}

package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum VehicleDataField {
    VIN("Vehicle.VIN"),
    MAKE("Vehicle.Make"),
    MODEL("Vehicle.Model"),
    YEAR("Vehicle.Year"),
    MILAGE("Vehicle.Milage"),
    STOCK_NO("Estimations.StockNo"),
    RO_NO("Estimations.RON"),
    PO_NO("Estimations.PONo"),
    LIC_PLATE("Vehicle.PlateNo"),
    COLOR("Vehicle.Color"),
    VEHICLE_TECH("Orders.EmployeeId");

    @Getter
    private String value;

    VehicleDataField(String value) {
        this.value = value;
    }
}

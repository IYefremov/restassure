package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MatrixServiceData {

    @JsonProperty("matrixServiceName")
    String matrixServiceName;

    @JsonProperty("hailMatrixName")
    String hailMatrixName;

    @JsonProperty("vehiclePartsData")
    List<VehiclePartData> vehiclePartsData;

    @JsonProperty("vehiclePartData")
    VehiclePartData vehiclePartData;


    public String getMatrixServiceName() {
        return matrixServiceName;
    }

    public String getHailMatrixName() {
        return hailMatrixName;
    }

    public List<VehiclePartData> getVehiclePartsData() {
        return vehiclePartsData;
    }

    public VehiclePartData getVehiclePartData() {
        return vehiclePartData;
    }

}

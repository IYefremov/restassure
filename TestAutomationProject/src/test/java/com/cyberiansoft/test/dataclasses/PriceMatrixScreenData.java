package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class PriceMatrixScreenData {

    @JsonProperty("screenName")
    String screenName;

    @Getter
    @JsonProperty("matrixService")
    MatrixServiceData matrixService;

    @JsonProperty("vehiclePartData")
    VehiclePartData vehiclePartData;

    @JsonProperty("vehiclePartsData")
    List<VehiclePartData> vehiclePartsData;

    @JsonProperty("screenPrice")
    String screenPrice;

    @JsonProperty("screenTotalPrice")
    String screenTotalPrice;

    public String getMatrixScreenName() {
        return screenName;
    }

    public VehiclePartData getVehiclePartData() {
        return vehiclePartData;
    }

    public List<VehiclePartData> getVehiclePartsData() {
        return vehiclePartsData;
    }

    public String getMatrixScreenPrice() {
        return screenPrice;
    }

    public String getMatrixScreenTotalPrice() {
        return screenTotalPrice;
    }
}

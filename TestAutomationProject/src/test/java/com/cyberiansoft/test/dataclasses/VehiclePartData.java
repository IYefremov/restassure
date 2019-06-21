package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class VehiclePartData {

    @JsonProperty("vehiclePartName")
    String vehiclePartName;

    @JsonProperty("vehiclePartSize")
    String vehiclePartSize;

    @JsonProperty("vehiclePartSeverity")
    String vehiclePartSeverity;

    @JsonProperty("laborService")
    LaborServiceData laborService;

    @JsonProperty("vehiclePartAdditionalService")
    ServiceData vehiclePartAdditionalService;

    @JsonProperty("vehiclePartAdditionalServices")
    List<ServiceData> vehiclePartAdditionalServices;

    @JsonProperty("vehiclePartPrice")
    String vehiclePartPrice;

    @JsonProperty("vehiclePartTime")
    String vehiclePartTime;

    @JsonProperty("vehiclePartTotalPrice")
    String vehiclePartTotalPrice;

    @JsonProperty("vehiclePartSubTotalPrice")
    String vehiclePartSubTotalPrice;

    @JsonProperty("vehiclePartOption")
    String vehiclePartOption;

    @Getter
    @JsonProperty("serviceDefaultTechnician")
    ServiceTechnician serviceDefaultTechnician;

    @Getter
    @JsonProperty("serviceNewTechnician")
    ServiceTechnician serviceNewTechnician;

    public String getVehiclePartName() {
        return vehiclePartName;
    }

    public String getVehiclePartSize() {
        return vehiclePartSize;
    }

    public String getVehiclePartSeverity() {
        return vehiclePartSeverity;
    }

    public String getVehiclePartPrice() {
        return vehiclePartPrice;
    }

    public String getVehiclePartTotalPrice() {
        return vehiclePartTotalPrice;
    }

    public String getVehiclePartSubTotalPrice() {
        return vehiclePartSubTotalPrice;
    }

    public String getVehiclePartTime() {
        return vehiclePartTime;
    }

    public List<ServiceData> getVehiclePartAdditionalServices() {
        return vehiclePartAdditionalServices;
    }

    public ServiceData getVehiclePartAdditionalService() {
        return vehiclePartAdditionalService;
    }

    public LaborServiceData getMatrixAdditionalLaborService() {
        return laborService;
    }

    public String getVehiclePartOption() {
        return vehiclePartOption;
    }

}

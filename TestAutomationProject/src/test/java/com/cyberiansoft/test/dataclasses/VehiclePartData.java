package com.cyberiansoft.test.dataclasses;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

    @JsonProperty("partServicesList")
    List<PartServiceData> partServicesList;

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

    @Getter
    @JsonProperty("serviceDefaultTechnicians")
    List<ServiceTechnician> serviceDefaultTechnicians;

    @Getter
    @JsonProperty("serviceNewTechnicians")
    List<ServiceTechnician> serviceNewTechnicians;

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

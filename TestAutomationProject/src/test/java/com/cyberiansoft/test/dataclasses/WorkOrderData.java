package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WorkOrderData {

    @JsonProperty("workOrderType")
    String workOrderType;

    @JsonProperty("vihicleInfo")
    VehicleInfoData vihicleInfo;

    @JsonProperty("service")
    ServiceData service;

    @JsonProperty("services")
    List<ServiceData> services;

    @JsonProperty("workOrderPrice")
    String workOrderPrice;

    public String getWorkOrderType() {
        return workOrderType;
    }

    public VehicleInfoData getVehicleInfoData() {
        return vihicleInfo;
    }

    public String getVinNumber() {
        return vihicleInfo.getVINNumber();
    }

    public String getServiceName() {
        return service.getServiceName();
    }

    public List<ServiceData> getServicesList() {
        return services;
    }

    public String getWorkOrderPrice() {
        return workOrderPrice;
    }

}

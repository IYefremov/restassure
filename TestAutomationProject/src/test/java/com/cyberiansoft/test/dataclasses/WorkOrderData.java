package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkOrderData {

    @JsonProperty("workOrderType")
    String workOrderType;

    @JsonProperty("vihicleInfo")
    VehicleInfoData vihicleInfo;

    @JsonProperty("service")
    ServiceData service;

    public String getWorkOrderType() {
        return workOrderType;
    }

    public String getVinNumber() {
        return vihicleInfo.getVINNumber();
    }

    public String getServiceName() {
        return service.getServiceName();
    }

}

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

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    @JsonProperty("matrixService")
    MatrixServiceData matrixService;

    @JsonProperty("laborService")
    LaborServiceData laborService;

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

    public String getServicePrice() {
        return service.getServicePrice();
    }

    public List<ServiceData> getServicesList() {
        return services;
    }

    public String getWorkOrderPrice() {
        return workOrderPrice;
    }

    public String getMoneyServiceName() {
        return moneyService.getServiceName();
    }

    public String getMoneyServicePrice() {
        return moneyService.getServicePrice();
    }

    public String getMoneyServiceQuantity() { return moneyService.getServiceQuantity(); }

    public String getPercentageServiceName() {
        return percentageService.getServiceName();
    }

    public String getPercentageServicePrice() {
        return percentageService.getServicePrice();
    }

    public MatrixServiceData getMatrixServiceData() { return matrixService; }

    public LaborServiceData getLaborServiceData() { return laborService; }

}

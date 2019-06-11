package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

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

    @Getter
    @JsonProperty("selectedServices")
    List<ServiceData> selectedServices;

    @JsonProperty("workOrderPrice")
    String workOrderPrice;

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    @Getter
    @JsonProperty("percentageServices")
    List<ServiceData> percentageServices;

    @JsonProperty("matrixService")
    MatrixServiceData matrixService;

    @JsonProperty("taxService")
    TaxServiceData taxService;

    @JsonProperty("taxServices")
    List<TaxServiceData> taxServices;

    @JsonProperty("laborService")
    LaborServiceData laborService;

    @JsonProperty("insuranceCompany")
    InsuranceCompanyData insuranceCompany;

    @JsonProperty("retailCustomer")
    RetailCustomer retailCustomer;

    @JsonProperty("damageData")
    DamageData damageData;

    @JsonProperty("damagesData")
    List<DamageData> damagesData;

    @JsonProperty("inspectionData")
    InspectionData inspectionData;

    @Getter
    @JsonProperty("monitoring")
    Monitoring monitoring;

    @Getter
    @JsonProperty("bundleService")
    BundleServiceData bundleService;

    @Getter
    @JsonProperty("workOrderTotalSale")
    String workOrderTotalSale;

    @Getter
    @JsonProperty("questionScreenData")
    QuestionScreenData questionScreenData;

    @Getter
    @JsonProperty("wholesailCustomer")
    WholesailCustomer wholesailCustomer;

    @Getter
    @JsonProperty("workOrderJob")
    String workOrderJob;

    public String getWorkOrderType() {
        return workOrderType;
    }

    public VehicleInfoData getVehicleInfoData() {
        return vihicleInfo;
    }

    public String getVinNumber() {
        return vihicleInfo.getVINNumber();
    }

    public ServiceData getServiceData() {
        return service;
    }

    public List<ServiceData> getServicesList() {
        return services;
    }

    public String getWorkOrderPrice() {
        return workOrderPrice;
    }

    public ServiceData getMoneyServiceData() { return moneyService; }

    public ServiceData getPercentageServiceData() { return percentageService; }

    public MatrixServiceData getMatrixServiceData() { return matrixService; }

    public TaxServiceData getTaxServiceData() { return taxService; }

    public List<TaxServiceData> getTaxServicesData() { return taxServices; }

    public LaborServiceData getLaborServiceData() { return laborService; }

    public InsuranceCompanyData getInsuranceCompanyData() {
        return insuranceCompany;
    }

    public RetailCustomer getWorlOrderRetailCustomer() {
        return retailCustomer;
    }

    public DamageData getDamageData() {
        return damageData;
    }

    public List<DamageData> getDamagesData() {
        return damagesData;
    }

    public InspectionData getInspectionData() {
        return inspectionData;
    }
}

package com.cyberiansoft.test.dataclasses;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class WorkOrderData {

    @Getter
    @Setter
    @JsonProperty("workOrderID")
    String workOrderID;

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

    @Getter
    @JsonProperty("moneyServices")
    List<ServiceData> moneyServices;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    @Getter
    @JsonProperty("percentageServices")
    List<ServiceData> percentageServices;

    @JsonProperty("matrixService")
    com.cyberiansoft.test.dataclasses.MatrixServiceData matrixService;

    @Getter
    @JsonProperty("matrixServiceDataList")
    List<com.cyberiansoft.test.dataclasses.MatrixServiceData> matrixServiceDataList;

    @JsonProperty("taxService")
    TaxServiceData taxService;

    @JsonProperty("taxServices")
    List<TaxServiceData> taxServices;

    @JsonProperty("laborService")
    LaborServiceData laborService;

    @Getter
    @JsonProperty("laborServicesList")
    List<LaborServiceData> laborServicesList;

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
    @JsonProperty("bundleServices")
    List<BundleServiceData> bundleServices;

    @Getter
    @JsonProperty("workOrderTotalSale")
    String workOrderTotalSale;

    @Getter
    @JsonProperty("questionScreenData")
    QuestionScreenData questionScreenData;

    @Getter
    @JsonProperty("questionScreensData")
    List<QuestionScreenData> questionScreensData;

    @Getter
    @JsonProperty("wholesailCustomer")
    WholesailCustomer wholesailCustomer;

    @Getter
    @JsonProperty("workOrderJob")
    String workOrderJob;

    @Getter
    @JsonProperty("priceMatrixScreenData")
    PriceMatrixScreenData priceMatrixScreenData;

    @Getter
    @JsonProperty("priceMatrixScreensData")
    List<PriceMatrixScreenData> priceMatrixScreensData;

    @Getter
    @JsonProperty("orderMonitorData")
    OrderMonitorData orderMonitorData;

    @Getter
    @JsonProperty("invoiceData")
    InvoiceData invoiceData;

    @Getter
    @JsonProperty("technicianList")
    List<Employee> technicianList;

    @Getter
    @JsonProperty("technicianSplitData")
    Map<String, String> technicianSplitData;

    @Getter
    @JsonProperty("servicesScreen")
    ServicesScreenData servicesScreen;

    @Getter
    @JsonProperty("partServiceDataList")
    private List<PartServiceData> partServiceDataList;

    public String getWorkOrderType() {
        return workOrderType;
    }

    public VehicleInfoData getVehicleInfoData() {
        return vihicleInfo;
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

    public ServiceData getMoneyServiceData() {
        return moneyService;
    }

    public ServiceData getPercentageServiceData() {
        return percentageService;
    }

    public com.cyberiansoft.test.dataclasses.MatrixServiceData getMatrixServiceData() {
        return matrixService;
    }

    public TaxServiceData getTaxServiceData() {
        return taxService;
    }

    public List<TaxServiceData> getTaxServicesData() {
        return taxServices;
    }

    public LaborServiceData getLaborServiceData() {
        return laborService;
    }

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

package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;


public class InspectionData {

    @JsonProperty("inspType")
    String inspType;

    @JsonProperty("vihicleInfo")
    VehicleInfoData vihicleInfo;

    @JsonProperty("newVinNumber")
    String newVinNumber;

    @JsonProperty("service")
    ServiceData service;

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    @JsonProperty("services")
    List<ServiceData> services;

    @JsonProperty("moneyServices")
    List<ServiceData> moneyServices;

    @JsonProperty("percentageServices")
    List<ServiceData> percentageServices;

    @JsonProperty("servicesToApprove")
    List<ServiceData> servicesToApprove;

    @JsonProperty("matrixService")
    MatrixServiceData matrixService;

    @JsonProperty("vehiclePartData")
    VehiclePartData vehiclePartData;

    @JsonProperty("vehiclePartsData")
    List<VehiclePartData> vehiclePartsData;

    @JsonProperty("laborService")
    LaborServiceData laborService;

    @JsonProperty("insuranceCompany")
    InsuranceCompanyData insuranceCompany;

    @JsonProperty("inspPrice")
    String inspPrice;

    @JsonProperty("inspectionTotalPrice")
    String inspectionTotalPrice;

    @JsonProperty("inspApprovedPrice")
    String inspApprovedPrice;

    @JsonProperty("declineReason")
    String declineReason;

    @JsonProperty("inspectionNumber")
    String inspectionNumber;

    @JsonProperty("inspectionStatus")
    String inspectionStatus;

    @JsonProperty("retailCustomer")
    RetailCustomer retailCustomer;

    @JsonProperty("damageData")
    DamageData damageData;

    @JsonProperty("damagesData")
    List<DamageData> damagesData;

    @JsonProperty("priceMatrixScreenData")
    PriceMatrixScreenData priceMatrixScreenData;

    @JsonProperty("priceMatrixScreensData")
    List<PriceMatrixScreenData> priceMatrixScreensData;

    @Getter
    @JsonProperty("bundleService")
    BundleServiceData bundleService;

    @Getter
    @JsonProperty("questionScreenData")
    QuestionScreenData questionScreenData;

    @Getter
    @JsonProperty("questionScreensData")
    List<QuestionScreenData> questionScreensData;

    @Getter
    @JsonProperty("visualScreenData")
    VisualScreenData visualScreenData;

    @Getter
    @JsonProperty("visualScreensData")
    List<VisualScreenData> visualScreensData;

    @Getter
    @JsonProperty("archiveReason")
    String archiveReason;

    @Getter
    @JsonProperty("selectedServices")
    List<ServiceData> selectedServices;

    public String getInspectionType() {
        return inspType;
    }

    public String getInspectionPrice() {
        return inspPrice;
    }

    public String getInspectionTotalPrice() {
        return inspectionTotalPrice;
    }

    public String getInspectionApprovedPrice() {
        return inspApprovedPrice;
    }

    public String getDeclineReason() {
        return declineReason;
    }

    public VehicleInfoData getVehicleInfo() {
        return vihicleInfo;
    }

    public String getVinNumber() {
        return vihicleInfo.getVINNumber();
    }

    public String getNewVinNumber() {
        return newVinNumber;
    }

    public ServiceData getServiceData() {
        return service;
    }

    public List<ServiceData> getServicesList() {
        return services;
    }

    public ServiceData getMoneyServiceData() {
        return moneyService;
    }

    public List<ServiceData> getMoneyServicesList() {
        return moneyServices;
    }

    public List<ServiceData> getPercentageServicesList() {
        return percentageServices;
    }

    public List<ServiceData> getServicesToApprovesList() {
        return servicesToApprove;
    }

    public ServiceData getPercentageServiceData() {
        return percentageService;
    }

    public MatrixServiceData getMatrixServiceData() {
        return matrixService;
    }

    public VehiclePartData getVehiclePartData() {
        return vehiclePartData;
    }

    public List<VehiclePartData> getVehiclePartsData() {
        return vehiclePartsData;
    }

    public LaborServiceData getLaborServiceData() {
        return laborService;
    }

    public String getServiceNameByIndex(int serviceIndex) {
        return services.get(serviceIndex).getServiceName();
    }

    public String getServiceQuantityByIndex(int serviceIndex) {
        return services.get(serviceIndex).getServiceQuantity();
    }

    public String getServicePriceByIndex(int serviceIndex) {
        return services.get(serviceIndex).getServicePrice();
    }

    public InsuranceCompanyData getInsuranceCompanyData() {
        return insuranceCompany;
    }

    public String getInspectionNumber() {
        return inspectionNumber;
    }

    public void setInspectionNumber(String inspNumber) {

        this.inspectionNumber = inspNumber;
    }

    public InspectionStatus getInspectionStatus() {
        return InspectionStatus.getStatus(inspectionStatus);
    }

    public RetailCustomer getInspectionRetailCustomer() {
        return retailCustomer;
    }

    public DamageData getDamageData() {
        return damageData;
    }

    public List<DamageData> getDamagesData() {
        return damagesData;
    }

    public PriceMatrixScreenData getPriceMatrixScreenData() {
        return priceMatrixScreenData;
    }

    public List<PriceMatrixScreenData> getPriceMatrixScreensData() {
        return priceMatrixScreensData;
    }
}

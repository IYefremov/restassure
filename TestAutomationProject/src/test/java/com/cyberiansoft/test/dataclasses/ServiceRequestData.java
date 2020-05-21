package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServiceRequestData {

    @JsonProperty("serviceRequestType")
    String serviceRequestType;

    @JsonProperty("serviceRequestNumber")
    String serviceRequestNumber;

    @JsonProperty("wholesailCustomer")
    WholesailCustomer wholesailCustomer;

    @JsonProperty("vihicleInfo")
    VehicleInfoData vihicleInfo;

    @JsonProperty("inspectionData")
    InspectionData inspectionData;

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("moneyServices")
    List<ServiceData> moneyServices;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    @JsonProperty("percentageServices")
    List<ServiceData> percentageServices;

    @JsonProperty("questionScreenData")
    QuestionScreenData questionScreenData;

    @JsonProperty("serviceRequestPrice")
    String serviceRequestPrice;

    @JsonProperty("insuranceCompany")
    InsuranceCompanyData insuranceCompany;
}

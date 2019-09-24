package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BOOperationsData {

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tech")
    private String tech;

    @JsonProperty("page1")
    private String page1;

    @JsonProperty("page2")
    private String page2;

    @JsonProperty("page20")
    private String page20;

    @JsonProperty("pageInt50")
    private int pageInt50;

    @JsonProperty("page999")
    private String page999;

    @JsonProperty("pageInt1")
    private int pageInt1;

    @JsonProperty("tableRowCount40")
    private int tableRowCount40;

    @JsonProperty("tableRowCount189")
    private int tableRowCount189;

    @JsonProperty("tableRowCount1")
    private int tableRowCount1;

    @JsonProperty("WOnum")
    private String WOnum;

    @JsonProperty("searchPackage")
    private String searchPackage;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("inspNum")
    private String inspNum;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("technician")
    private String technician;

    @JsonProperty("technicianFull")
    private String technicianFull;

    @JsonProperty("SRtype")
    private String SRtype;

    @JsonProperty("teamName")
    private String teamName;

    @JsonProperty("textSearchParameter")
    private String textSearchParameter;

    @JsonProperty("newServiceRequest")
    private String newServiceRequest;

    @JsonProperty("assignedTo")
    private String assignedTo;

    @JsonProperty("POnum")
    private String POnum;

    @JsonProperty("ROnum")
    private String ROnum;

    @JsonProperty("VIN")
    private String VIN;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("year")
    private String year;

    @JsonProperty("insurance")
    private String insurance;

    @JsonProperty("label")
    private String label;

    @JsonProperty("inspectionNum1")
    private String inspectionNum1;

    @JsonProperty("inspectionNum2")
    private String inspectionNum2;

    @JsonProperty("fromTime")
    private String fromTime;

    @JsonProperty("toTime")
    private String toTime;

    @JsonProperty("serviceType")
    private String serviceType;

    @JsonProperty("serviceRequestGeneralInfo")
    private String serviceRequestGeneralInfo;

    @JsonProperty("serviceTypeVit")
    private String serviceTypeVit;

    @JsonProperty("searchData")
    private BOSearchData searchData;
}

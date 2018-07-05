package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOMonitorData {

    @JsonProperty("woNumber")
    private String woNumber;

    @JsonProperty("searchLocation")
    private String searchLocation;

    @JsonProperty("searchTimeFrame")
    private String searchTimeFrame;

    @JsonProperty("company")
    private String company;

    @JsonProperty("VIN")
    private String VIN;

    @JsonProperty("page1")
    private String page1;

    @JsonProperty("page2")
    private String page2;

    @JsonProperty("page999")
    private String page999;

    @JsonProperty("vendor")
    private String vendor;

    @JsonProperty("serviceStatus")
    private String serviceStatus;

    @JsonProperty("searchWoType")
    private String searchWoType;

    @JsonProperty("fromDate")
    private String fromDate;

    @JsonProperty("toDate")
    private String toDate;

    @JsonProperty("fromMonth")
    private String fromMonth;

    @JsonProperty("toMonth")
    private String toMonth;

    @JsonProperty("fromYear")
    private String fromYear;

    @JsonProperty("toYear")
    private String toYear;

    @JsonProperty("orderStatus")
    private String orderStatus;

    @JsonProperty("orderStatusReason")
    private String orderStatusReason;

    @JsonProperty("repairOrderNumber")
    private String repairOrderNumber;

    @JsonProperty("orderStatusEdited")
    private String orderStatusEdited;

    @JsonProperty("orderStatusReasonEdited")
    private String orderStatusReasonEdited;

    @JsonProperty("phase1")
    private String phase1;

    @JsonProperty("status1")
    private String status1;

    @JsonProperty("phase2")
    private String phase2;

    @JsonProperty("statuses2")
    private String[] statuses2;

    public String getWoNumber() {
        return woNumber;
    }

    public String getSearchLocation() {
        return searchLocation;
    }

    public String getSearchTimeFrame() {
        return searchTimeFrame;
    }

    public String getCompany() {
        return company;
    }

    public String getVIN() {
        return VIN;
    }

    public String getPage1() {
        return page1;
    }

    public String getPage2() {
        return page2;
    }

    public String getPage999() {
        return page999;
    }

    public String getVendor() {
        return vendor;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public String getSearchWoType() {
        return searchWoType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getFromMonth() {
        return fromMonth;
    }

    public String getToMonth() {
        return toMonth;
    }

    public String getFromYear() {
        return fromYear;
    }

    public String getToYear() {
        return toYear;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderStatusReason() {
        return orderStatusReason;
    }

    public String getRepairOrderNumber() {
        return repairOrderNumber;
    }

    public String getOrderStatusEdited() {
        return orderStatusEdited;
    }

    public String getOrderStatusReasonEdited() {
        return orderStatusReasonEdited;
    }

    public String getPhase1() {
        return phase1;
    }

    public String getStatus1() {
        return status1;
    }

    public String getPhase2() {
        return phase2;
    }

    public String[] getStatuses2() {
        return statuses2;
    }
}
package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOOperationsInspectionsData {

    @JsonProperty("page1")
    private String page1;

    @JsonProperty("page2")
    private String page2;

    @JsonProperty("pageInt50")
    private int pageInt50;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("inspNum")
    private String inspNum;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("technician")
    private String technician;

    @JsonProperty("status")
    private String status;

    @JsonProperty("technicianFull")
    private String technicianFull;

    @JsonProperty("tableRowCount1")
    private int tableRowCount1;

    @JsonProperty("inspectionNum1")
    private String inspectionNum1;

    @JsonProperty("inspectionNum2")
    private String inspectionNum2;

    @JsonProperty("fromTime")
    private String fromTime;

    @JsonProperty("toTime")
    private String toTime;

    @JsonProperty("ROnum")
    private String ROnum;

    @JsonProperty("VIN")
    private String VIN;

    @JsonProperty("day")
    private int day;

    public String getPage1() {
        return page1;
    }

    public String getPage2() {
        return page2;
    }

    public int getPageInt50() {
        return pageInt50;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public String getInspNum() {
        return inspNum;
    }

    public String getCustomer() {
        return customer;
    }

    public String getTechnician() {
        return technician;
    }

    public String getStatus() {
        return status;
    }

    public String getTechnicianFull() {
        return technicianFull;
    }

    public int getTableRowCount1() {
        return tableRowCount1;
    }

    public String getInspectionNum1() {
        return inspectionNum1;
    }

    public String getInspectionNum2() {
        return inspectionNum2;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public String getROnum() {
        return ROnum;
    }

    public String getVIN() {
        return VIN;
    }

    public int getDay() {
        return day;
    }
}
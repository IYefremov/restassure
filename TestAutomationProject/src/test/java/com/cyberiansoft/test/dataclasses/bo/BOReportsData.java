package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOReportsData {

    @JsonProperty("defaultValue")
    private String defaultValue;

    @JsonProperty("area")
    private String area;

    @JsonProperty("team")
    private String team;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("employee")
    private String employee;

    @JsonProperty("areaParameter")
    private String areaParameter;

    @JsonProperty("teamParameter")
    private String teamParameter;

    @JsonProperty("customerParameter")
    private String customerParameter;

    @JsonProperty("employeeParameter")
    private String employeeParameter;

    @JsonProperty("invoice")
    private String invoice;

    @JsonProperty("year")
    private String year;

    @JsonProperty("stock")
    private String stock;

    @JsonProperty("VIN")
    private String VIN;

    @JsonProperty("RO")
    private String RO;

    @JsonProperty("service")
    private String service;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("billed")
    private String billed;

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getArea() {
        return area;
    }

    public String getTeam() {
        return team;
    }

    public String getCustomer() {
        return customer;
    }

    public String getEmployee() {
        return employee;
    }

    public String getAreaParameter() {
        return areaParameter;
    }

    public String getTeamParameter() {
        return teamParameter;
    }

    public String getCustomerParameter() {
        return customerParameter;
    }

    public String getEmployeeParameter() {
        return employeeParameter;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getYear() {
        return year;
    }

    public String getStock() {
        return stock;
    }

    public String getVIN() {
        return VIN;
    }

    public String getRO() {
        return RO;
    }

    public String getService() {
        return service;
    }

    public String getAmount() {
        return amount;
    }

    public String getBilled() {
        return billed;
    }
}
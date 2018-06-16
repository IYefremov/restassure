package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOOperationsServiceRequests {

    @JsonProperty("teamName")
    private String teamName;

    @JsonProperty("addServiceRequestValue")
    private String addServiceRequestValue;

    @JsonProperty("assignedTo")
    private String assignedTo;

    @JsonProperty("insuranceCompanyName")
    private String insuranceCompanyName;

    @JsonProperty("poNum")
    private String poNum;

    @JsonProperty("roNum")
    private String roNum;

    @JsonProperty("newServiceRequest")
    private String newServiceRequest;

    @JsonProperty("VIN")
    private String VIN;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("insurance")
    private String insurance;

    @JsonProperty("label")
    private String label;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("technicianFieldValue")
    private String technicianFieldValue;

    @JsonProperty("status")
    private String status;

    @JsonProperty("locationType")
    private String locationType;

    @JsonProperty("clientAddress")
    private String clientAddress;

    @JsonProperty("clientCity")
    private String clientCity;

    @JsonProperty("clientZip")
    private String clientZip;

    @JsonProperty("clientZip2")
    private String clientZip2;

    @JsonProperty("location")
    private String location;

    @JsonProperty("anotherLogin")
    private String anotherLogin;

    @JsonProperty("anotherPassword")
    private String anotherPassword;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("checkInButton")
    private String checkInButton;

    @JsonProperty("undoCheckInButton")
    private String undoCheckInButton;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("tags")
    private String[] tags;

    @JsonProperty("descriptions")
    private String[] descriptions;

    public String getTeamName() {
        return teamName;
    }

    public String getAddServiceRequestValue() {
        return addServiceRequestValue;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    public String getPoNum() {
        return poNum;
    }

    public String getRoNum() {
        return roNum;
    }

    public String getNewServiceRequest() {
        return newServiceRequest;
    }

    public String getVIN() {
        return VIN;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getLabel() {
        return label;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getClientName() {
        return clientName;
    }

    public String getTechnicianFieldValue() {
        return technicianFieldValue;
    }

    public String getStatus() {
        return status;
    }

    public String getLocationType() {
        return locationType;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getClientCity() {
        return clientCity;
    }

    public String getClientZip() {
        return clientZip;
    }

    public String getClientZip2() {
        return clientZip2;
    }

    public String getLocation() {
        return location;
    }

    public String getAnotherLogin() {
        return anotherLogin;
    }

    public String getAnotherPassword() {
        return anotherPassword;
    }

    public String getCustomer() {
        return customer;
    }

    public String getCheckInButton() {
        return checkInButton;
    }

    public String getUndoCheckInButton() {
        return undoCheckInButton;
    }

    public String getSymbol() {
        return symbol;
    }

    public String[] getTags() {
        return tags;
    }

    public String[] getDescriptions() {
        return descriptions;
    }
}
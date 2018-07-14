package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BOCompanyData {

    @JsonProperty("employeeName")
    private String employeeName;

    @JsonProperty("employeeName2")
    private String employeeName2;

    @JsonProperty("searchEmployee")
    private String searchEmployee;

    @JsonProperty("employeeFullName")
    private String employeeFullName;

    @JsonProperty("teamName")
    private String teamName;

    @JsonProperty("teamName2")
    private String teamName2;

    @JsonProperty("topBubbleInfo")
    private String topBubbleInfo;

    @JsonProperty("topBubbleInfoWithReassign")
    private String topBubbleInfoWithReassign;

    @JsonProperty("userFirstName")
    private String userFirstName;

    @JsonProperty("userLastName")
    private String userLastName;

    @JsonProperty("page1")
    private String page1;

    @JsonProperty("page2")
    private String page2;

    @JsonProperty("page999")
    private String page999;

    @JsonProperty("emptyString")
    private String emptyString;

    @JsonProperty("priceType")
    private String priceType;

    @JsonProperty("serviceType")
    private String serviceType;

    @JsonProperty("serviceName")
    private String serviceName;

    @JsonProperty("teamLocation")
    private String teamLocation;

    @JsonProperty("type")
    private String type;

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("job")
    private String job;

    @JsonProperty("client")
    private String client;

    @JsonProperty("questionSectionName")
    private String questionSectionName;

    @JsonProperty("questionName")
    private String questionName;

    @JsonProperty("questionFormName")
    private String questionFormName;

    @JsonProperty("supplyName")
    private String supplyName;

    @JsonProperty("supplyNameEdited")
    private String supplyNameEdited;

    @JsonProperty("expenseType")
    private String expenseType;

    @JsonProperty("newExpenseTypeName")
    private String newExpenseTypeName;

    @JsonProperty("vehiclePart")
    private String vehiclePart;

    @JsonProperty("newVehiclePartName")
    private String newVehiclePartName;

    @JsonProperty("employeeFirstName")
    private String employeeFirstName;

    @JsonProperty("employeeLastName")
    private String employeeLastName;

    @JsonProperty("priceMatrix")
    private String priceMatrix;

    @JsonProperty("priceMatrixService")
    private String priceMatrixService;

    @JsonProperty("priceMatrixVehiclePart")
    private String priceMatrixVehiclePart;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("serviceNames")
    private List<String> serviceNames;

    @JsonProperty("damageSeverities")
    private List<String> damageSeverities;

    @JsonProperty("damageSizes")
    private List<String> damageSizes;

    public String getCustomer() {
        return customer;
    }

    public String getPriceType() {
        return priceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getTeamLocation() {
        return teamLocation;
    }

    public String getType() {
        return type;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getJob() {
        return job;
    }

    public String getClient() {
        return client;
    }

    public String getQuestionSectionName() {
        return questionSectionName;
    }

    public String getQuestionName() {
        return questionName;
    }

    public String getQuestionFormName() {
        return questionFormName;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public String getSupplyNameEdited() {
        return supplyNameEdited;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public String getNewExpenseTypeName() {
        return newExpenseTypeName;
    }

    public String getVehiclePart() {
        return vehiclePart;
    }

    public String getNewVehiclePartName() {
        return newVehiclePartName;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public String getPriceMatrix() {
        return priceMatrix;
    }

    public String getPriceMatrixService() {
        return priceMatrixService;
    }

    public String getPriceMatrixVehiclePart() {
        return priceMatrixVehiclePart;
    }

    public List<String> getDamageSeverities() {
        return damageSeverities;
    }

    public List<String> getDamageSizes() {
        return damageSizes;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
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

    public String getEmptyString() {
        return emptyString;
    }

    public List<String> getServiceNames() {
        return serviceNames;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeName2() {
        return employeeName2;
    }

    public String getSearchEmployee() {
        return searchEmployee;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamName2() {
        return teamName2;
    }

    public String getTopBubbleInfo() {
        return topBubbleInfo;
    }

    public String getTopBubbleInfoWithReassign() {
        return topBubbleInfoWithReassign;
    }
}

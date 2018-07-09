package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

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
package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOSuperUserData {

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("appName")
    private String appName;

    @JsonProperty("employee")
    private String employee;

    @JsonProperty("status")
    private String status;

    public String getUserName() {
        return userName;
    }

    public String getAppName() {
        return appName;
    }

    public String getEmployee() {
        return employee;
    }

    public String getStatus() {
        return status;
    }
}
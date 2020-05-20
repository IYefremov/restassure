package com.cyberiansoft.test.dataclasses.vNextBO.servicerequests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOSRSearchData {

    @JsonProperty("hasThisText")
    private String hasThisText;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("advisor")
    private String advisor;

    @JsonProperty("employee")
    private String employee;

    @JsonProperty("team")
    private String team;

    @JsonProperty("srType")
    private String srType;

    @JsonProperty("srNum")
    private String srNum;

    @JsonProperty("roNum")
    private String roNum;

    @JsonProperty("stockNum")
    private String stockNum;

    @JsonProperty("vinNum")
    private String vinNum;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("fromDate")
    private String fromDate;

    @JsonProperty("toDate")
    private String toDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("phase")
    private String phase;

    @JsonProperty("repairLocation")
    private String repairLocation;

    @JsonProperty("tag")
    private String tag;
}

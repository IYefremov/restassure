package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOROAdvancedSearchValues {

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("employee")
    private String employee;

    @JsonProperty("phase")
    private String phase;

    @JsonProperty("phaseStatus")
    private String phaseStatus;

    @JsonProperty("department")
    private String department;

    @JsonProperty("woType")
    private String woType;

    @JsonProperty("woNum")
    private String woNum;

    @JsonProperty("roNum")
    private String roNum;

    @JsonProperty("stockNum")
    private String stockNum;

    @JsonProperty("vinNum")
    private String vinNum;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("repairStatus")
    private String repairStatus;

    @JsonProperty("daysInProcess")
    private String daysInProcess;

    @JsonProperty("daysInPhase")
    private String daysInPhase;

    @JsonProperty("flag")
    private String flag;

    @JsonProperty("sortBy")
    private String sortBy;

    @JsonProperty("searchName")
    private String searchName;

    @JsonProperty("searchName")
    private String[] searchNames;
}

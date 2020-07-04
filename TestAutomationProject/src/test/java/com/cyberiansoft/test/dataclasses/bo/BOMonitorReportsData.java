package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BOMonitorReportsData {

    @JsonProperty("repairLocationName")
    private String repairLocationName;

    @JsonProperty("approxRepairTime")
    private String approxRepairTime;

    @JsonProperty("workingDay")
    private String workingDay;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("finishTime")
    private String finishTime;

    @JsonProperty("phaseNameStart")
    private String phaseNameStart;

    @JsonProperty("phaseNameOnHold")
    private String phaseNameOnHold;

    @JsonProperty("phaseType")
    private String phaseType;

    @JsonProperty("phaseTypeOnHold")
    private String phaseTypeOnHold;

    @JsonProperty("transitionTime")
    private String transitionTime;

    @JsonProperty("repairTime")
    private String repairTime;

    @JsonProperty("serviceNameVacuumCleaner")
    private String serviceNameVacuumCleaner;

    @JsonProperty("serviceNameWashing")
    private String serviceNameWashing;

    @JsonProperty("vendorTeamName")
    private String vendorTeamName;

    @JsonProperty("vendorTimeZone")
    private String vendorTimeZone;

    @JsonProperty("vendorDescription")
    private String vendorDescription;

    @JsonProperty("vendorTimeSheetType")
    private String vendorTimeSheetType;

    @JsonProperty("searchLocation")
    private String searchLocation;

    @JsonProperty("addressValue")
    private String addressValue;

    @JsonProperty("VIN")
    private String VIN;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("newServiceRequest")
    private String newServiceRequest;

    @JsonProperty("year")
    private String year;

    @JsonProperty("woType")
    private String woType;

    @JsonProperty("woNumber")
    private String woNumber;

    @JsonProperty("searchTimeFrame")
    private String searchTimeFrame;

    @JsonProperty("servicesStatus")
    private String servicesStatus;

    @JsonProperty("workStatusTracking")
    private String workStatusTracking;
}

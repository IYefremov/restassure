package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
public class BOMonitorRepairLocationsData {

    @JsonProperty("locationName")
    private String locationName;

    @JsonProperty("locationStatus")
    private String locationStatus;

    @JsonProperty("repairLocationName")
    private String repairLocationName;

    @JsonProperty("repairLocationStatus")
    private String repairLocationStatus;

    @JsonProperty("repairLocationApproxRepairTime")
    private String repairLocationApproxRepairTime;

    @JsonProperty("repairLocationWorkDay1")
    private String repairLocationWorkDay1;

    @JsonProperty("repairLocationStartTime1")
    private String repairLocationStartTime1;

    @JsonProperty("repairLocationEndTime1")
    private String repairLocationEndTime1;

    @JsonProperty("repairLocationWorkDay2")
    private String repairLocationWorkDay2;

    @JsonProperty("repairLocationStartTime2")
    private String repairLocationStartTime2;

    @JsonProperty("repairLocationEndTime2")
    private String repairLocationEndTime2;

    @JsonProperty("repairLocationTimeZone")
    private String repairLocationTimeZone;

    @JsonProperty("vendorTeam")
    private String vendorTeam;

    @JsonProperty("repairLocationDepartmentDefault")
    private String repairLocationDepartmentDefault;

    @JsonProperty("repairLocationDepartment")
    private String repairLocationDepartment;

    @JsonProperty("repairLocationDepartmentDescription")
    private String repairLocationDepartmentDescription;

    @JsonProperty("repairLocationDepartmentNew")
    private String repairLocationDepartmentNew;

    @JsonProperty("repairLocationDepartmentDescriptionNew")
    private String repairLocationDepartmentDescriptionNew;

    @JsonProperty("repairLocationPhase")
    private String repairLocationPhase;

    @JsonProperty("repairLocationPhaseDescription")
    private String repairLocationPhaseDescription;

    @JsonProperty("repairLocationPhaseType")
    private String repairLocationPhaseType;

    @JsonProperty("repairLocationPhaseCheckoutType")
    private String repairLocationPhaseCheckoutType;

    @JsonProperty("workStatusTracking")
    private String workStatusTracking;

    @JsonProperty("approxTransTime")
    private String approxTransTime;

    @JsonProperty("approxRepairTime")
    private String approxRepairTime;

    @JsonProperty("repairLocationPhaseNew")
    private String repairLocationPhaseNew;

    @JsonProperty("repairLocationPhaseDescriptionNew")
    private String repairLocationPhaseDescriptionNew;

    @JsonProperty("repairLocationPhaseTypeNew")
    private String repairLocationPhaseTypeNew;

    @JsonProperty("workStatusTrackingNew")
    private String workStatusTrackingNew;

    @JsonProperty("repairLocationPhaseCheckoutTypeNew")
    private String repairLocationPhaseCheckoutTypeNew;

    @JsonProperty("approxTransTimeNew")
    private String approxTransTimeNew;

    @JsonProperty("approxRepairTimeNew")
    private String approxRepairTimeNew;

    @JsonProperty("repairLocationClient")
    private String repairLocationClient;

    @JsonProperty("woType")
    private String woType;

    @JsonProperty("phaseClosed")
    private String phaseClosed;

    @JsonProperty("phaseClosedType")
    private String phaseClosedType;

    @JsonProperty("phaseStarted")
    private String phaseStarted;

    @JsonProperty("phaseStartedType")
    private String phaseStartedType;

    @JsonProperty("team")
    private String team;

    @JsonProperty("manager")
    private String manager;

    @JsonProperty("checkboxesAmount")
    private int checkboxesAmount;

    @JsonProperty("teamName")
    private String teamName;

    @JsonProperty("teamDefaultLocation")
    private String teamDefaultLocation;

    @JsonProperty("repairLocationStatusNotActive")
    private String repairLocationStatusNotActive;

    @JsonProperty("phase")
    private String phase;

    public String getRandomLocationName() {
        return "test-" + RandomStringUtils.randomAlphanumeric(7);
    }
}

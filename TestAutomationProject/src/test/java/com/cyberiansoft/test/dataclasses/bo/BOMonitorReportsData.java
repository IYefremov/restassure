package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getRepairLocationName() {
        return repairLocationName;
    }

    public String getApproxRepairTime() {
        return approxRepairTime;
    }

    public String getWorkingDay() {
        return workingDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public String getPhaseNameStart() {
        return phaseNameStart;
    }

    public String getPhaseNameOnHold() {
        return phaseNameOnHold;
    }

    public String getPhaseType() {
        return phaseType;
    }

    public String getPhaseTypeOnHold() {
        return phaseTypeOnHold;
    }

    public String getTransitionTime() {
        return transitionTime;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public String getServiceNameVacuumCleaner() {
        return serviceNameVacuumCleaner;
    }

    public String getServiceNameWashing() {
        return serviceNameWashing;
    }

    public String getVendorTeamName() {
        return vendorTeamName;
    }

    public String getVendorTimeZone() {
        return vendorTimeZone;
    }

    public String getVendorDescription() {
        return vendorDescription;
    }

    public String getVendorTimeSheetType() {
        return vendorTimeSheetType;
    }

    public String getSearchLocation() {
        return searchLocation;
    }

    public String getAddressValue() {
        return addressValue;
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

    public String getNewServiceRequest() {
        return newServiceRequest;
    }

    public String getYear() {
        return year;
    }

    public String getWoType() {
        return woType;
    }

    public String getWoNumber() {
        return woNumber;
    }

    public String getSearchTimeFrame() {
        return searchTimeFrame;
    }

    public String getServicesStatus() {
        return servicesStatus;
    }
}

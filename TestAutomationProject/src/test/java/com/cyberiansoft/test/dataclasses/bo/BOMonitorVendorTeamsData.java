package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOMonitorVendorTeamsData {

    @JsonProperty("vendorTeam")
    private String vendorTeam;

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("defaultLocation")
    private String defaultLocation;

    @JsonProperty("vendorType")
    private String vendorType;

    @JsonProperty("vendorDescription")
    private String vendorDescription;

    @JsonProperty("vendorAccountingId")
    private String vendorAccountingId;

    @JsonProperty("vendorArea")
    private String vendorArea;

    @JsonProperty("vendorTimeSheetType")
    private String vendorTimeSheetType;

    @JsonProperty("additionalLocation")
    private String additionalLocation;

    @JsonProperty("vendorTeamEdited")
    private String vendorTeamEdited;

    @JsonProperty("timeZoneEdited")
    private String timeZoneEdited;

    @JsonProperty("vendorTimeSheetTypeEdited")
    private String vendorTimeSheetTypeEdited;

    @JsonProperty("vendorTypeEdited")
    private String vendorTypeEdited;

    @JsonProperty("vendorCompany")
    private String vendorCompany;

    @JsonProperty("vendorTeamEmployee")
    private String vendorTeamEmployee;

    public String getVendorTeam() {
        return vendorTeam;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public String getVendorType() {
        return vendorType;
    }

    public String getVendorDescription() {
        return vendorDescription;
    }

    public String getVendorAccountingId() {
        return vendorAccountingId;
    }

    public String getVendorArea() {
        return vendorArea;
    }

    public String getVendorTimeSheetType() {
        return vendorTimeSheetType;
    }

    public String getAdditionalLocation() {
        return additionalLocation;
    }

    public String getVendorTeamEdited() {
        return vendorTeamEdited;
    }

    public String getTimeZoneEdited() {
        return timeZoneEdited;
    }

    public String getVendorTimeSheetTypeEdited() {
        return vendorTimeSheetTypeEdited;
    }

    public String getVendorTypeEdited() {
        return vendorTypeEdited;
    }

    public String getVendorCompany() {
        return vendorCompany;
    }

    public String getAuditLogsMessageRestored() {
        return "Team " + vendorTeam + " is restored";
    }

    public String getAuditLogsMessageDeleted() {
        return "Team " + vendorTeam + " is deleted";
    }

    public String getVendorTeamEmployee() {
        return vendorTeamEmployee;
    }
}
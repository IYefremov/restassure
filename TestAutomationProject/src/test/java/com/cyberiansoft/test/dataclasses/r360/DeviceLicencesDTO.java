package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "LicenceId",
        "ApplicationId",
        "LicenceNo",
        "Description",
        "IsDeleted",
        "DeviceID",
        "ClientId",
        "ClientType",
        "TimeZoneId",
        "TimeZoneOffset",
        "IncludeAllClients",
        "EmployeeId"
})

public class DeviceLicencesDTO {

    @JsonProperty("LicenceId")
    private String LicenceId;
    @JsonProperty("ApplicationId")
    private String ApplicationId;
    @JsonProperty("LicenceNo")
    private Integer LicenceNo;
    @JsonProperty("Description")
    private Object Description;
    @JsonProperty("IsDeleted")
    private Boolean IsDeleted;
    @JsonProperty("DeviceID")
    private String DeviceID;
    @JsonProperty("ClientId")
    private Object ClientId;
    @JsonProperty("ClientType")
    private Integer ClientType;
    @JsonProperty("TimeZoneId")
    private String TimeZoneId;
    @JsonProperty("TimeZoneOffset")
    private Integer TimeZoneOffset;
    @JsonProperty("IncludeAllClients")
    private Boolean IncludeAllClients;
    @JsonProperty("EmployeeId")
    private Object EmployeeId;

    @JsonProperty("LicenceId")
    public String getLicenceId() {
        return LicenceId;
    }

    @JsonProperty("LicenceId")
    public void setLicenceId(String licenceId) {
        this.LicenceId = licenceId;
    }

    @JsonProperty("ApplicationId")
    public String getApplicationId() {
        return ApplicationId;
    }

    @JsonProperty("ApplicationId")
    public void setApplicationId(String applicationId) {
        this.ApplicationId = applicationId;
    }

    @JsonProperty("LicenceNo")
    public Integer getLicenceNo() {
        return LicenceNo;
    }

    @JsonProperty("LicenceNo")
    public void setLicenceNo(Integer licenceNo) {
        this.LicenceNo = licenceNo;
    }

    @JsonProperty("Description")
    public Object getDescription() {
        return Description;
    }

    @JsonProperty("Description")
    public void setDescription(Object description) {
        this.Description = description;
    }

    @JsonProperty("IsDeleted")
    public Boolean getIsDeleted() {
        return IsDeleted;
    }

    @JsonProperty("IsDeleted")
    public void setIsDeleted(Boolean isDeleted) {
        this.IsDeleted = isDeleted;
    }

    @JsonProperty("DeviceID")
    public String getDeviceID() {
        return DeviceID;
    }

    @JsonProperty("DeviceID")
    public void setDeviceID(String deviceID) {
        this.DeviceID = deviceID;
    }

    @JsonProperty("ClientId")
    public Object getClientId() {
        return ClientId;
    }

    @JsonProperty("ClientId")
    public void setClientId(Object clientId) {
        this.ClientId = clientId;
    }

    @JsonProperty("ClientType")
    public Integer getClientType() {
        return ClientType;
    }

    @JsonProperty("ClientType")
    public void setClientType(Integer clientType) {
        this.ClientType = clientType;
    }

    @JsonProperty("TimeZoneId")
    public String getTimeZoneId() {
        return TimeZoneId;
    }

    @JsonProperty("TimeZoneId")
    public void setTimeZoneId(String timeZoneId) {
        this.TimeZoneId = timeZoneId;
    }

    @JsonProperty("TimeZoneOffset")
    public Integer getTimeZoneOffset() {
        return TimeZoneOffset;
    }

    @JsonProperty("TimeZoneOffset")
    public void setTimeZoneOffset(Integer timeZoneOffset) {
        this.TimeZoneOffset = timeZoneOffset;
    }

    @JsonProperty("IncludeAllClients")
    public Boolean getIncludeAllClients() {
        return IncludeAllClients;
    }

    @JsonProperty("IncludeAllClients")
    public void setIncludeAllClients(Boolean includeAllClients) {
        this.IncludeAllClients = includeAllClients;
    }

    @JsonProperty("EmployeeId")
    public Object getEmployeeId() {
        return EmployeeId;
    }

    @JsonProperty("EmployeeId")
    public void setEmployeeId(Object employeeId) {
        this.EmployeeId = employeeId;
    }

}

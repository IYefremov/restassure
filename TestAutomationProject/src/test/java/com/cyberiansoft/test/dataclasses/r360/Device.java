package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "DeviceId",
        "LicenceId",
        "OSTypeId",
        "UTCTime",
        "TimeZoneOffset",
        "EmployeeId"
})
public class Device {

    @JsonProperty("DeviceId")
    private String DeviceId;
    @JsonProperty("LicenceId")
    private String LicenceId;
    @JsonProperty("OSTypeId")
    private Integer OSTypeId;
    @JsonProperty("UTCTime")
    private String UTCTime;
    @JsonProperty("TimeZoneOffset")
    private Integer TimeZoneOffset;
    @JsonProperty("EmployeeId")
    private String EmployeeId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("DeviceId")
    public String getDeviceId() {
        return DeviceId;
    }

    @JsonProperty("DeviceId")
    public void setDeviceId(String deviceId) {
        this.DeviceId = deviceId;
    }

    @JsonProperty("LicenceId")
    public String getLicenceId() {
        return LicenceId;
    }

    @JsonProperty("LicenceId")
    public void setLicenceId(String licenceId) {
        this.LicenceId = licenceId;
    }

    @JsonProperty("OSTypeId")
    public Integer getOSTypeId() {
        return OSTypeId;
    }

    @JsonProperty("OSTypeId")
    public void setOSTypeId(Integer oSTypeId) {
        this.OSTypeId = oSTypeId;
    }

    @JsonProperty("UTCTime")
    public String getUTCTime() {
        return UTCTime;
    }

    @JsonProperty("UTCTime")
    public void setUTCTime(String uTCTime) {
        this.UTCTime = uTCTime;
    }

    @JsonProperty("TimeZoneOffset")
    public Integer getTimeZoneOffset() {
        return TimeZoneOffset;
    }

    @JsonProperty("TimeZoneOffset")
    public void setTimeZoneOffset(Integer timeZoneOffset) {
        this.TimeZoneOffset = timeZoneOffset;
    }

    @JsonProperty("EmployeeId")
    public String getEmployeeId() {
        return EmployeeId;
    }

    @JsonProperty("EmployeeId")
    public void setEmployeeId(String employeeId) {
        this.EmployeeId = employeeId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

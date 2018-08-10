package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ClientId",
        "EstimationTypeId",
        "EstimationId",
        "EmployeeId",
        "VehicleID",
        "LicenceID",
        "UTCTime",
        "EstimationDate",
        "TeamId",
        "TimeZoneOffset",
        "LocalNo",
        "Amount",
        "EStatusID",
        "StockNo",
        "RONo",
        "OtherInsuranceName",
        "Vehicle",
        "EstimationServices",
        "InspectionDetails",
        "EstimationAnswers",
        "Device",
        "ApprovedAmount"
})
public class InspectionDTO {

    @JsonProperty("ClientId")
    private String ClientId;
    @JsonProperty("EstimationTypeId")
    private String EstimationTypeId;
    @JsonProperty("EstimationId")
    private String EstimationId;
    @JsonProperty("EmployeeId")
    private String EmployeeId;
    @JsonProperty("VehicleID")
    private String VehicleID;
    @JsonProperty("LicenceID")
    private String LicenceID;
    @JsonProperty("UTCTime")
    private String UTCTime;
    @JsonProperty("EstimationDate")
    private String EstimationDate;
    @JsonProperty("TeamId")
    private String TeamId;
    @JsonProperty("TimeZoneOffset")
    private Integer TimeZoneOffset;
    @JsonProperty("LocalNo")
    private Integer LocalNo;
    @JsonProperty("Amount")
    private Double Amount;
    @JsonProperty("ApprovedAmount")
    private Double ApprovedAmount;
    @JsonProperty("EStatusID")
    private Integer EStatusID;
    @JsonProperty("StockNo")
    private String StockNo;
    @JsonProperty("RONo")
    private String RONo;
    @JsonProperty("OtherInsuranceName")
    private Object OtherInsuranceName;
    @JsonProperty("Vehicle")
    private VehicleDTO Vehicle;
    @JsonProperty("EstimationServices")
    private List<Object> EstimationServices = null;
    @JsonProperty("InspectionDetails")
    private List<Object> InspectionDetails = null;
    @JsonProperty("EstimationAnswers")
    private List<Object> EstimationAnswers = null;
    @JsonProperty("Device")
    private DeviceDTO Device;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ClientId")
    public String getClientId() {
        return ClientId;
    }

    @JsonProperty("ClientId")
    public void setClientId(String clientId) {
        this.ClientId = clientId;
    }

    @JsonProperty("EstimationTypeId")
    public String getEstimationTypeId() {
        return EstimationTypeId;
    }

    @JsonProperty("EstimationTypeId")
    public void setEstimationTypeId(String estimationTypeId) {
        this.EstimationTypeId = estimationTypeId;
    }

    @JsonProperty("EstimationId")
    public String getEstimationId() {
        return EstimationId;
    }

    @JsonProperty("EstimationId")
    public void setEstimationId(String estimationId) {
        this.EstimationId = estimationId;
    }

    @JsonProperty("EmployeeId")
    public String getEmployeeId() {
        return EmployeeId;
    }

    @JsonProperty("EmployeeId")
    public void setEmployeeId(String employeeId) {
        this.EmployeeId = employeeId;
    }

    @JsonProperty("VehicleID")
    public String getVehicleID() {
        return VehicleID;
    }

    @JsonProperty("VehicleID")
    public void setVehicleID(String vehicleID) {
        this.VehicleID = vehicleID;
    }

    @JsonProperty("LicenceID")
    public String getLicenceID() {
        return LicenceID;
    }

    @JsonProperty("LicenceID")
    public void setLicenceID(String licenceID) {
        this.LicenceID = licenceID;
    }

    @JsonProperty("UTCTime")
    public String getUTCTime() {
        return UTCTime;
    }

    @JsonProperty("UTCTime")
    public void setUTCTime(String uTCTime) {
        this.UTCTime = uTCTime;
    }

    @JsonProperty("EstimationDate")
    public String getEstimationDate() {
        return EstimationDate;
    }

    @JsonProperty("EstimationDate")
    public void setEstimationDate(String estimationDate) {
        this.EstimationDate = estimationDate;
    }

    @JsonProperty("TeamId")
    public String getTeamId() {
        return TeamId;
    }

    @JsonProperty("TeamId")
    public void setTeamId(String teamId) {
        this.TeamId = teamId;
    }

    @JsonProperty("TimeZoneOffset")
    public Integer getTimeZoneOffset() {
        return TimeZoneOffset;
    }

    @JsonProperty("TimeZoneOffset")
    public void setTimeZoneOffset(Integer timeZoneOffset) {
        this.TimeZoneOffset = timeZoneOffset;
    }

    @JsonProperty("LocalNo")
    public Integer getLocalNo() {
        return LocalNo;
    }

    @JsonProperty("LocalNo")
    public void setLocalNo(Integer localNo) {
        this.LocalNo = localNo;
    }

    @JsonProperty("Amount")
    public Double getAmount() {
        return Amount;
    }

    @JsonProperty("Amount")
    public void setAmount(Double amount) {
        this.Amount = amount;
    }


    @JsonProperty("ApprovedAmount")
    public Double getApprovedAmount() {
        return ApprovedAmount;
    }

    @JsonProperty("ApprovedAmount")
    public void setApprovedAmount(Double approvedAmount) {
        this.ApprovedAmount = approvedAmount;
    }

    @JsonProperty("EStatusID")
    public Integer getEStatusID() {
        return EStatusID;
    }

    @JsonProperty("EStatusID")
    public void setEStatusID(Integer eStatusID) {
        this.EStatusID = eStatusID;
    }

    @JsonProperty("StockNo")
    public String getStockNo() {
        return StockNo;
    }

    @JsonProperty("StockNo")
    public void setStockNo(String stockNo) {
        this.StockNo = stockNo;
    }

    @JsonProperty("RONo")
    public String getRONo() {
        return RONo;
    }

    @JsonProperty("RONo")
    public void setRONo(String rONo) {
        this.RONo = rONo;
    }

    @JsonProperty("OtherInsuranceName")
    public Object getOtherInsuranceName() {
        return OtherInsuranceName;
    }

    @JsonProperty("OtherInsuranceName")
    public void setOtherInsuranceName(Object otherInsuranceName) {
        this.OtherInsuranceName = otherInsuranceName;
    }

    @JsonProperty("Vehicle")
    public VehicleDTO getVehicle() {
        return Vehicle;
    }

    @JsonProperty("Vehicle")
    public void setVehicle(VehicleDTO vehicle) {
        this.Vehicle = vehicle;
    }

    @JsonProperty("EstimationServices")
    public List<Object> getEstimationServices() {
        return EstimationServices;
    }

    @JsonProperty("EstimationServices")
    public void setEstimationServices(List<Object> estimationServices) {
        this.EstimationServices = estimationServices;
    }

    @JsonProperty("InspectionDetails")
    public List<Object> getInspectionDetails() {
        return InspectionDetails;
    }

    @JsonProperty("InspectionDetails")
    public void setInspectionDetails(List<Object> inspectionDetails) {
        this.InspectionDetails = inspectionDetails;
    }

    @JsonProperty("EstimationAnswers")
    public List<Object> getEstimationAnswers() {
        return EstimationAnswers;
    }

    @JsonProperty("EstimationAnswers")
    public void setEstimationAnswers(List<Object> estimationAnswers) {
        this.EstimationAnswers = estimationAnswers;
    }

    @JsonProperty("Device")
    public DeviceDTO getDevice() {
        return Device;
    }

    @JsonProperty("Device")
    public void setDevice(DeviceDTO device) {
        this.Device = device;
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

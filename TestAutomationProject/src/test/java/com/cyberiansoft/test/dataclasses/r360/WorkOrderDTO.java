package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ClientId",
        "OrderTypeId",
        "OrderId",
        "OrderStatusID",
        "Approved",
        "EmployeeId",
        "VehicleID",
        "LicenceId",
        "UTCTime",
        "OrderDate",
        "TeamId",
        "TimeZoneOffset",
        "LocalNo",
        "Amount",
        "ServiceGroupId",
        "StockNo",
        "RONo",
        "OrderEmployees",
        "OrderServices",
        "Vehicle",
        "Device"
})
public class WorkOrderDTO {

    @JsonProperty("ClientId")
    private String ClientId;
    @JsonProperty("OrderTypeId")
    private String OrderTypeId;
    @JsonProperty("OrderId")
    private String OrderId;
    @JsonProperty("OrderStatusID")
    private Integer OrderStatusID;
    @JsonProperty("Approved")
    private Boolean Approved;
    @JsonProperty("EmployeeId")
    private String EmployeeId;
    @JsonProperty("VehicleID")
    private String VehicleID;
    @JsonProperty("LicenceId")
    private String LicenceId;
    @JsonProperty("UTCTime")
    private String UTCTime;
    @JsonProperty("OrderDate")
    private String OrderDate;
    @JsonProperty("TeamId")
    private String TeamId;
    @JsonProperty("TimeZoneOffset")
    private Integer TimeZoneOffset;
    @JsonProperty("LocalNo")
    private Integer LocalNo;
    @JsonProperty("Amount")
    private Double Amount;
    @JsonProperty("ServiceGroupId")
    private String ServiceGroupId;
    @JsonProperty("StockNo")
    private String StockNo;
    @JsonProperty("RONo")
    private String RONo;
    @JsonProperty("OrderEmployees")
    private List<OrderEmployeeDTO> OrderEmployees = null;
    @JsonProperty("OrderServices")
    private List<Object> OrderServices = null;
    @JsonProperty("Vehicle")
    private VehicleDTO Vehicle;
    @JsonProperty("Device")
    private DeviceDTO Device;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("ClientId")
    public String getClientId() {
        return ClientId;
    }

    @JsonProperty("ClientId")
    public void setClientId(String clientId) {
        this.ClientId = clientId;
    }

    @JsonProperty("OrderTypeId")
    public String getOrderTypeId() {
        return OrderTypeId;
    }

    @JsonProperty("OrderTypeId")
    public void setOrderTypeId(String orderTypeId) {
        this.OrderTypeId = orderTypeId;
    }

    @JsonProperty("OrderId")
    public String getOrderId() {
        return OrderId;
    }

    @JsonProperty("OrderId")
    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    @JsonProperty("OrderStatusID")
    public Integer getOrderStatusID() {
        return OrderStatusID;
    }

    @JsonProperty("OrderStatusID")
    public void setOrderStatusID(Integer orderStatusID) {
        this.OrderStatusID = orderStatusID;
    }

    @JsonProperty("Approved")
    public Boolean getApproved() {
        return Approved;
    }

    @JsonProperty("Approved")
    public void setApproved(Boolean approved) {
        this.Approved = approved;
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

    @JsonProperty("LicenceId")
    public String getLicenceId() {
        return LicenceId;
    }

    @JsonProperty("LicenceId")
    public void setLicenceId(String licenceId) {
        this.LicenceId = licenceId;
    }

    @JsonProperty("UTCTime")
    public String getUTCTime() {
        return UTCTime;
    }

    @JsonProperty("UTCTime")
    public void setUTCTime(String uTCTime) {
        this.UTCTime = uTCTime;
    }

    @JsonProperty("OrderDate")
    public String getOrderDate() {
        return OrderDate;
    }

    @JsonProperty("OrderDate")
    public void setOrderDate(String orderDate) {
        this.OrderDate = orderDate;
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

    @JsonProperty("ServiceGroupId")
    public String getServiceGroupId() {
        return ServiceGroupId;
    }

    @JsonProperty("ServiceGroupId")
    public void setServiceGroupId(String serviceGroupId) {
        this.ServiceGroupId = serviceGroupId;
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

    @JsonProperty("OrderEmployees")
    public List<OrderEmployeeDTO> getOrderEmployees() {
        return OrderEmployees;
    }

    @JsonProperty("OrderEmployees")
    public void setOrderEmployees(List<OrderEmployeeDTO> orderEmployees) {
        this.OrderEmployees = orderEmployees;
    }

    @JsonProperty("OrderServices")
    public List<Object> getOrderServices() {
        return OrderServices;
    }

    @JsonProperty("OrderServices")
    public void setOrderServices(List<Object> orderServices) {
        this.OrderServices = orderServices;
    }

    @JsonProperty("Vehicle")
    public VehicleDTO getVehicle() {
        return Vehicle;
    }

    @JsonProperty("Vehicle")
    public void setVehicle(VehicleDTO vehicle) {
        this.Vehicle = vehicle;
    }

    @JsonProperty("Device")
    public DeviceDTO getDevice() {
        return Device;
    }

    @JsonProperty("Device")
    public void setDevice(DeviceDTO device) {
        this.Device = device;
    }

}

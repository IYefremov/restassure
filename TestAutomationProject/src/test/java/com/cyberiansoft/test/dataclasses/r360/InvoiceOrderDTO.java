package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "OrderId",
        "OrderTypeId",
        "Amount",
        "OrderDate",
        "EmployeeId",
        "PONo",
        "OrderNo",
        "RONo",
        "StockNo",
        "InvoiceId",
        "InsuranceId",
        "OrderStatusID",
        "Make",
        "Model",
        "Year",
        "Color",
        "VIN",
        "PlateNo",
        "IsWholeSaler",
        "ClientId",
        "FirstName",
        "LastName",
        "CompanyName",
        "StatusIcons",
        "ClientName",
        "VehicleInfo1",
        "Status",
        "IsSelected",
        "toggleIconVisiblity",
        "InvoiceOrderId",
        "VehicleInfo2",
        "isEditMode",
        "vehicleDetails"
})

public class InvoiceOrderDTO {


    @JsonProperty("OrderId")
    private String OrderId;
    @JsonProperty("OrderTypeId")
    private String OrderTypeId;
    @JsonProperty("Amount")
    private Integer Amount;
    @JsonProperty("OrderDate")
    private String OrderDate;
    @JsonProperty("EmployeeId")
    private String EmployeeId;
    @JsonProperty("PONo")
    private Object PONo;
    @JsonProperty("OrderNo")
    private String OrderNo;
    @JsonProperty("RONo")
    private Object RONo;
    @JsonProperty("StockNo")
    private Object StockNo;
    @JsonProperty("InvoiceId")
    private String InvoiceId;
    @JsonProperty("InsuranceId")
    private Object InsuranceId;
    @JsonProperty("OrderStatusID")
    private Integer OrderStatusID;
    @JsonProperty("Make")
    private String Make;
    @JsonProperty("Model")
    private String Model;
    @JsonProperty("Year")
    private Integer Year;
    @JsonProperty("Color")
    private String Color;
    @JsonProperty("VIN")
    private String VIN;
    @JsonProperty("PlateNo")
    private String PlateNo;
    @JsonProperty("IsWholeSaler")
    private Integer IsWholeSaler;
    @JsonProperty("ClientId")
    private String ClientId;
    @JsonProperty("FirstName")
    private String FirstName;
    @JsonProperty("LastName")
    private String LastName;
    @JsonProperty("CompanyName")
    private String CompanyName;
    @JsonProperty("StatusIcons")
    private List<Object> StatusIcons = null;
    @JsonProperty("ClientName")
    private String ClientName;
    @JsonProperty("VehicleInfo1")
    private String VehicleInfo1;
    @JsonProperty("Status")
    private String Status;
    @JsonProperty("IsSelected")
    private Boolean IsSelected;
    @JsonProperty("toggleIconVisiblity")
    private Integer toggleIconVisiblity;
    @JsonProperty("InvoiceOrderId")
    private String InvoiceOrderId;
    @JsonProperty("VehicleInfo2")
    private String VehicleInfo2;
    @JsonProperty("isEditMode")
    private Boolean isEditMode;
    @JsonProperty("vehicleDetails")
    private String vehicleDetails;

    @JsonProperty("OrderId")
    public String getOrderId() {
        return OrderId;
    }

    @JsonProperty("OrderId")
    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    @JsonProperty("OrderTypeId")
    public String getOrderTypeId() {
        return OrderTypeId;
    }

    @JsonProperty("OrderTypeId")
    public void setOrderTypeId(String orderTypeId) {
        this.OrderTypeId = orderTypeId;
    }

    @JsonProperty("Amount")
    public Integer getAmount() {
        return Amount;
    }

    @JsonProperty("Amount")
    public void setAmount(Integer amount) {
        this.Amount = amount;
    }

    @JsonProperty("OrderDate")
    public String getOrderDate() {
        return OrderDate;
    }

    @JsonProperty("OrderDate")
    public void setOrderDate(String orderDate) {
        this.OrderDate = orderDate;
    }

    @JsonProperty("EmployeeId")
    public String getEmployeeId() {
        return EmployeeId;
    }

    @JsonProperty("EmployeeId")
    public void setEmployeeId(String employeeId) {
        this.EmployeeId = employeeId;
    }

    @JsonProperty("PONo")
    public Object getPONo() {
        return PONo;
    }

    @JsonProperty("PONo")
    public void setPONo(Object pONo) {
        this.PONo = pONo;
    }

    @JsonProperty("OrderNo")
    public String getOrderNo() {
        return OrderNo;
    }

    @JsonProperty("OrderNo")
    public void setOrderNo(String orderNo) {
        this.OrderNo = orderNo;
    }

    @JsonProperty("RONo")
    public Object getRONo() {
        return RONo;
    }

    @JsonProperty("RONo")
    public void setRONo(Object rONo) {
        this.RONo = rONo;
    }

    @JsonProperty("StockNo")
    public Object getStockNo() {
        return StockNo;
    }

    @JsonProperty("StockNo")
    public void setStockNo(Object stockNo) {
        this.StockNo = stockNo;
    }

    @JsonProperty("InvoiceId")
    public String getInvoiceId() {
        return InvoiceId;
    }

    @JsonProperty("InvoiceId")
    public void setInvoiceId(String invoiceId) {
        this.InvoiceId = invoiceId;
    }

    @JsonProperty("InsuranceId")
    public Object getInsuranceId() {
        return InsuranceId;
    }

    @JsonProperty("InsuranceId")
    public void setInsuranceId(Object insuranceId) {
        this.InsuranceId = insuranceId;
    }

    @JsonProperty("OrderStatusID")
    public Integer getOrderStatusID() {
        return OrderStatusID;
    }

    @JsonProperty("OrderStatusID")
    public void setOrderStatusID(Integer orderStatusID) {
        this.OrderStatusID = orderStatusID;
    }

    @JsonProperty("Make")
    public String getMake() {
        return Make;
    }

    @JsonProperty("Make")
    public void setMake(String make) {
        this.Make = make;
    }

    @JsonProperty("Model")
    public String getModel() {
        return Model;
    }

    @JsonProperty("Model")
    public void setModel(String model) {
        this.Model = model;
    }

    @JsonProperty("Year")
    public Integer getYear() {
        return Year;
    }

    @JsonProperty("Year")
    public void setYear(Integer year) {
        this.Year = year;
    }

    @JsonProperty("Color")
    public String getColor() {
        return Color;
    }

    @JsonProperty("Color")
    public void setColor(String color) {
        this.Color = color;
    }

    @JsonProperty("VIN")
    public String getVIN() {
        return VIN;
    }

    @JsonProperty("VIN")
    public void setVIN(String vIN) {
        this.VIN = vIN;
    }

    @JsonProperty("PlateNo")
    public String getPlateNo() {
        return PlateNo;
    }

    @JsonProperty("PlateNo")
    public void setPlateNo(String plateNo) {
        this.PlateNo = plateNo;
    }

    @JsonProperty("IsWholeSaler")
    public Integer getIsWholeSaler() {
        return IsWholeSaler;
    }

    @JsonProperty("IsWholeSaler")
    public void setIsWholeSaler(Integer isWholeSaler) {
        this.IsWholeSaler = isWholeSaler;
    }

    @JsonProperty("ClientId")
    public String getClientId() {
        return ClientId;
    }

    @JsonProperty("ClientId")
    public void setClientId(String clientId) {
        this.ClientId = clientId;
    }

    @JsonProperty("FirstName")
    public String getFirstName() {
        return FirstName;
    }

    @JsonProperty("FirstName")
    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    @JsonProperty("LastName")
    public String getLastName() {
        return LastName;
    }

    @JsonProperty("LastName")
    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    @JsonProperty("CompanyName")
    public String getCompanyName() {
        return CompanyName;
    }

    @JsonProperty("CompanyName")
    public void setCompanyName(String companyName) {
        this.CompanyName = companyName;
    }

    @JsonProperty("StatusIcons")
    public List<Object> getStatusIcons() {
        return StatusIcons;
    }

    @JsonProperty("StatusIcons")
    public void setStatusIcons(List<Object> statusIcons) {
        this.StatusIcons = statusIcons;
    }

    @JsonProperty("ClientName")
    public String getClientName() {
        return ClientName;
    }

    @JsonProperty("ClientName")
    public void setClientName(String clientName) {
        this.ClientName = clientName;
    }

    @JsonProperty("VehicleInfo1")
    public String getVehicleInfo1() {
        return VehicleInfo1;
    }

    @JsonProperty("VehicleInfo1")
    public void setVehicleInfo1(String vehicleInfo1) {
        this.VehicleInfo1 = vehicleInfo1;
    }

    @JsonProperty("Status")
    public String getStatus() {
        return Status;
    }

    @JsonProperty("Status")
    public void setStatus(String status) {
        this.Status = status;
    }

    @JsonProperty("IsSelected")
    public Boolean getIsSelected() {
        return IsSelected;
    }

    @JsonProperty("IsSelected")
    public void setIsSelected(Boolean isSelected) {
        this.IsSelected = isSelected;
    }

    @JsonProperty("toggleIconVisiblity")
    public Integer getToggleIconVisiblity() {
        return toggleIconVisiblity;
    }

    @JsonProperty("toggleIconVisiblity")
    public void setToggleIconVisiblity(Integer toggleIconVisiblity) {
        this.toggleIconVisiblity = toggleIconVisiblity;
    }

    @JsonProperty("InvoiceOrderId")
    public String getInvoiceOrderId() {
        return InvoiceOrderId;
    }

    @JsonProperty("InvoiceOrderId")
    public void setInvoiceOrderId(String invoiceOrderId) {
        this.InvoiceOrderId = invoiceOrderId;
    }

    @JsonProperty("VehicleInfo2")
    public String getVehicleInfo2() {
        return VehicleInfo2;
    }

    @JsonProperty("VehicleInfo2")
    public void setVehicleInfo2(String vehicleInfo2) {
        this.VehicleInfo2 = vehicleInfo2;
    }

    @JsonProperty("isEditMode")
    public Boolean getIsEditMode() {
        return isEditMode;
    }

    @JsonProperty("isEditMode")
    public void setIsEditMode(Boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    @JsonProperty("vehicleDetails")
    public String getVehicleDetails() {
        return vehicleDetails;
    }

    @JsonProperty("vehicleDetails")
    public void setVehicleDetails(String vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

}

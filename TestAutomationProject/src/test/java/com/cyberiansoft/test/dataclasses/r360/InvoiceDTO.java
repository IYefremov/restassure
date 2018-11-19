package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "InvoiceId",
        "ClientId",
        "LicenceID",
        "LocalNo",
        "InvoiceDate",
        "Status",
        "PONo",
        "UTCTime",
        "TimeZoneOffset",
        "Amount",
        "InvoiceOrders",
        "InvoiceTypeId",
        "Orders",
        "Device"
})
public class InvoiceDTO {

    @JsonProperty("InvoiceId")
    private String InvoiceId;
    @JsonProperty("ClientId")
    private String ClientId;
    @JsonProperty("LicenceID")
    private String LicenceID;
    @JsonProperty("LocalNo")
    private Integer LocalNo;
    @JsonProperty("InvoiceDate")
    private String InvoiceDate;
    @JsonProperty("Status")
    private Integer Status;
    @JsonProperty("PONo")
    private String PONo;
    @JsonProperty("UTCTime")
    private String UTCTime;
    @JsonProperty("TimeZoneOffset")
    private Integer TimeZoneOffset;
    @JsonProperty("Amount")
    private Integer Amount;
    @JsonProperty("InvoiceOrders")
    private List<InvoiceOrderDTO> InvoiceOrders = null;
    @JsonProperty("InvoiceTypeId")
    private String InvoiceTypeId;
    @JsonProperty("Orders")
    private List<InvoiceOrderDTO> Orders = null;
    @JsonProperty("Device")
    private DeviceDTO Device;

    @JsonProperty("InvoiceId")
    public String getInvoiceId() {
        return InvoiceId;
    }

    @JsonProperty("InvoiceId")
    public void setInvoiceId(String invoiceId) {
        this.InvoiceId = invoiceId;
    }

    @JsonProperty("ClientId")
    public String getClientId() {
        return ClientId;
    }

    @JsonProperty("ClientId")
    public void setClientId(String clientId) {
        this.ClientId = clientId;
    }

    @JsonProperty("LicenceID")
    public String getLicenceID() {
        return LicenceID;
    }

    @JsonProperty("LicenceID")
    public void setLicenceID(String licenceID) {
        this.LicenceID = licenceID;
    }

    @JsonProperty("LocalNo")
    public Integer getLocalNo() {
        return LocalNo;
    }

    @JsonProperty("LocalNo")
    public void setLocalNo(Integer localNo) {
        this.LocalNo = localNo;
    }

    @JsonProperty("InvoiceDate")
    public String getInvoiceDate() {
        return InvoiceDate;
    }

    @JsonProperty("InvoiceDate")
    public void setInvoiceDate(String invoiceDate) {
        this.InvoiceDate = invoiceDate;
    }

    @JsonProperty("Status")
    public Integer getStatus() {
        return Status;
    }

    @JsonProperty("Status")
    public void setStatus(Integer status) {
        this.Status = status;
    }

    @JsonProperty("PONo")
    public String getPONo() {
        return PONo;
    }

    @JsonProperty("PONo")
    public void setPONo(String pONo) {
        this.PONo = pONo;
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

    @JsonProperty("Amount")
    public Integer getAmount() {
        return Amount;
    }

    @JsonProperty("Amount")
    public void setAmount(Integer amount) {
        this.Amount = amount;
    }

    @JsonProperty("InvoiceOrders")
    public List<InvoiceOrderDTO> getInvoiceOrders() {
        return InvoiceOrders;
    }

    @JsonProperty("InvoiceOrders")
    public void setInvoiceOrders(List<InvoiceOrderDTO> invoiceOrders) {
        this.InvoiceOrders = invoiceOrders;
    }

    @JsonProperty("InvoiceTypeId")
    public String getInvoiceTypeId() {
        return InvoiceTypeId;
    }

    @JsonProperty("InvoiceTypeId")
    public void setInvoiceTypeId(String invoiceTypeId) {
        this.InvoiceTypeId = invoiceTypeId;
    }

    @JsonProperty("Orders")
    public List<InvoiceOrderDTO> getOrders() {
        return Orders;
    }

    @JsonProperty("Orders")
    public void setOrders(List<InvoiceOrderDTO> orders) {
        this.Orders = orders;
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
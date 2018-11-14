package com.cyberiansoft.test.vnext.restclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "InvoiceId",
        "LicenceId",
        "ClientId",
        "LocalNo",
        "InvoiceDate",
        "Status",
        "PORequired",
        "PONo",
        "Discount",
        "Gratuity",
        "Amount",
        "PaidAmount",
        "PaymentNotes",
        "InvoiceDescription",
        "CustomerSignature",
        "InvoiceTypeId",
        "EmailSent",
        "PartitionID",
        "UTCTime",
        "TimeZoneOffset",
        "IsPrinted",
        "CustomerApproved",
        "InvoiceNo",
        "ClientName",
        "LicenceNo",
        "StatusIcons",
        "AllowClientChanging",
        "Invoice_ID",
        "InvoiceDetails"
})

public class InvoiceResultDTO {

    @JsonProperty("InvoiceId")
    private String InvoiceId;
    @JsonProperty("LicenceId")
    private String LicenceId;
    @JsonProperty("ClientId")
    private String ClientId;
    @JsonProperty("LocalNo")
    private Integer LocalNo;
    @JsonProperty("InvoiceDate")
    private String InvoiceDate;
    @JsonProperty("Status")
    private Integer Status;
    @JsonProperty("PORequired")
    private Boolean PORequired;
    @JsonProperty("PONo")
    private String PONo;
    @JsonProperty("Discount")
    private Integer Discount;
    @JsonProperty("Gratuity")
    private Integer Gratuity;
    @JsonProperty("Amount")
    private Double Amount;
    @JsonProperty("PaidAmount")
    private Integer PaidAmount;
    @JsonProperty("PaymentNotes")
    private Object PaymentNotes;
    @JsonProperty("InvoiceDescription")
    private Object InvoiceDescription;
    @JsonProperty("CustomerSignature")
    private Object CustomerSignature;
    @JsonProperty("InvoiceTypeId")
    private String InvoiceTypeId;
    @JsonProperty("EmailSent")
    private Boolean EmailSent;
    @JsonProperty("PartitionID")
    private Integer PartitionID;
    @JsonProperty("UTCTime")
    private String UTCTime;
    @JsonProperty("TimeZoneOffset")
    private Integer TimeZoneOffset;
    @JsonProperty("IsPrinted")
    private Boolean IsPrinted;
    @JsonProperty("CustomerApproved")
    private Boolean CustomerApproved;
    @JsonProperty("InvoiceNo")
    private String InvoiceNo;
    @JsonProperty("ClientName")
    private String ClientName;
    @JsonProperty("LicenceNo")
    private Integer LicenceNo;
    @JsonProperty("StatusIcons")
    private String StatusIcons;
    @JsonProperty("AllowClientChanging")
    private Boolean AllowClientChanging;
    @JsonProperty("Invoice_ID")
    private Integer Invoice_ID;
    @JsonProperty("InvoiceDetails")
    private String InvoiceDetails;

    @JsonProperty("InvoiceId")
    public String getInvoiceId() {
        return InvoiceId;
    }

    @JsonProperty("InvoiceId")
    public void setInvoiceId(String invoiceId) {
        this.InvoiceId = invoiceId;
    }

    @JsonProperty("LicenceId")
    public String getLicenceId() {
        return LicenceId;
    }

    @JsonProperty("LicenceId")
    public void setLicenceId(String licenceId) {
        this.LicenceId = licenceId;
    }

    @JsonProperty("ClientId")
    public String getClientId() {
        return ClientId;
    }

    @JsonProperty("ClientId")
    public void setClientId(String clientId) {
        this.ClientId = clientId;
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

    @JsonProperty("PORequired")
    public Boolean getPORequired() {
        return PORequired;
    }

    @JsonProperty("PORequired")
    public void setPORequired(Boolean pORequired) {
        this.PORequired = pORequired;
    }

    @JsonProperty("PONo")
    public String getPONo() {
        return PONo;
    }

    @JsonProperty("PONo")
    public void setPONo(String pONo) {
        this.PONo = pONo;
    }

    @JsonProperty("Discount")
    public Integer getDiscount() {
        return Discount;
    }

    @JsonProperty("Discount")
    public void setDiscount(Integer discount) {
        this.Discount = discount;
    }

    @JsonProperty("Gratuity")
    public Integer getGratuity() {
        return Gratuity;
    }

    @JsonProperty("Gratuity")
    public void setGratuity(Integer gratuity) {
        this.Gratuity = gratuity;
    }

    @JsonProperty("Amount")
    public Double getAmount() {
        return Amount;
    }

    @JsonProperty("Amount")
    public void setAmount(Double amount) {
        this.Amount = amount;
    }

    @JsonProperty("PaidAmount")
    public Integer getPaidAmount() {
        return PaidAmount;
    }

    @JsonProperty("PaidAmount")
    public void setPaidAmount(Integer paidAmount) {
        this.PaidAmount = paidAmount;
    }

    @JsonProperty("PaymentNotes")
    public Object getPaymentNotes() {
        return PaymentNotes;
    }

    @JsonProperty("PaymentNotes")
    public void setPaymentNotes(Object paymentNotes) {
        this.PaymentNotes = paymentNotes;
    }

    @JsonProperty("InvoiceDescription")
    public Object getInvoiceDescription() {
        return InvoiceDescription;
    }

    @JsonProperty("InvoiceDescription")
    public void setInvoiceDescription(Object invoiceDescription) {
        this.InvoiceDescription = invoiceDescription;
    }

    @JsonProperty("CustomerSignature")
    public Object getCustomerSignature() {
        return CustomerSignature;
    }

    @JsonProperty("CustomerSignature")
    public void setCustomerSignature(Object customerSignature) {
        this.CustomerSignature = customerSignature;
    }

    @JsonProperty("InvoiceTypeId")
    public String getInvoiceTypeId() {
        return InvoiceTypeId;
    }

    @JsonProperty("InvoiceTypeId")
    public void setInvoiceTypeId(String invoiceTypeId) {
        this.InvoiceTypeId = invoiceTypeId;
    }

    @JsonProperty("EmailSent")
    public Boolean getEmailSent() {
        return EmailSent;
    }

    @JsonProperty("EmailSent")
    public void setEmailSent(Boolean emailSent) {
        this.EmailSent = emailSent;
    }

    @JsonProperty("PartitionID")
    public Integer getPartitionID() {
        return PartitionID;
    }

    @JsonProperty("PartitionID")
    public void setPartitionID(Integer partitionID) {
        this.PartitionID = partitionID;
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

    @JsonProperty("IsPrinted")
    public Boolean getIsPrinted() {
        return IsPrinted;
    }

    @JsonProperty("IsPrinted")
    public void setIsPrinted(Boolean isPrinted) {
        this.IsPrinted = isPrinted;
    }

    @JsonProperty("CustomerApproved")
    public Boolean getCustomerApproved() {
        return CustomerApproved;
    }

    @JsonProperty("CustomerApproved")
    public void setCustomerApproved(Boolean customerApproved) {
        this.CustomerApproved = customerApproved;
    }

    @JsonProperty("InvoiceNo")
    public String getInvoiceNo() {
        return InvoiceNo;
    }

    @JsonProperty("InvoiceNo")
    public void setInvoiceNo(String invoiceNo) {
        this.InvoiceNo = invoiceNo;
    }

    @JsonProperty("ClientName")
    public String getClientName() {
        return ClientName;
    }

    @JsonProperty("ClientName")
    public void setClientName(String clientName) {
        this.ClientName = clientName;
    }

    @JsonProperty("LicenceNo")
    public Integer getLicenceNo() {
        return LicenceNo;
    }

    @JsonProperty("LicenceNo")
    public void setLicenceNo(Integer licenceNo) {
        this.LicenceNo = licenceNo;
    }

    @JsonProperty("StatusIcons")
    public String getStatusIcons() {
        return StatusIcons;
    }

    @JsonProperty("StatusIcons")
    public void setStatusIcons(String statusIcons) {
        this.StatusIcons = statusIcons;
    }

    @JsonProperty("AllowClientChanging")
    public Boolean getAllowClientChanging() {
        return AllowClientChanging;
    }

    @JsonProperty("AllowClientChanging")
    public void setAllowClientChanging(Boolean allowClientChanging) {
        this.AllowClientChanging = allowClientChanging;
    }

    @JsonProperty("Invoice_ID")
    public Integer getInvoiceID() {
        return Invoice_ID;
    }

    @JsonProperty("Invoice_ID")
    public void setInvoiceID(Integer invoiceID) {
        this.Invoice_ID = invoiceID;
    }

    @JsonProperty("InvoiceDetails")
    public String getInvoiceDetails() {
        return InvoiceDetails;
    }

    @JsonProperty("InvoiceDetails")
    public void setInvoiceDetails(String invoiceDetails) {
        this.InvoiceDetails = invoiceDetails;
    }

}

package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "OrderId",
        "TeamId",
        "VehicleID",
        "ClientId",
        "EstimationId",
        "EstimationNo",
        "EmployeeId",
        "SalesPersonId",
        "ServiceGroupId",
        "LocalNo",
        "OrderDate",
        "RONo",
        "StockNo",
        "Amount",
        "TotalSale",
        "CreateTime",
        "LicenceId",
        "Approved",
        "DateApproved",
        "OrderNo",
        "OrderDescription",
        "OrderStatusID",
        "OrderStatusReasonId",
        "InsuranceId",
        "InvoiceId",
        "PolicyNumber",
        "ClaimNumber",
        "Deductible",
        "AccidentDate",
        "Revision",
        "OrderTypeId",
        "OtherInsuranceName",
        "Vehicle",
        "OrderServices",
        "OrderAnswers",
        "OrderEmployees",
        "DiscountAmount",
        "TaxAmount",
        "FeesAmount",
        "PONo",
        "PartitionID",
        "UTCTime",
        "TimeZoneOffset",
        "Client",
        "Device",
        "DeviceLicences"
})

public class WorkOrderForInvoiceDTO {
    @JsonProperty("OrderId")
    private String OrderId;
    @JsonProperty("TeamId")
    private String TeamId;
    @JsonProperty("VehicleID")
    private String VehicleID;
    @JsonProperty("ClientId")
    private String ClientId;
    @JsonProperty("EstimationId")
    private Object EstimationId;
    @JsonProperty("EstimationNo")
    private Object EstimationNo;
    @JsonProperty("EmployeeId")
    private String EmployeeId;
    @JsonProperty("SalesPersonId")
    private Object SalesPersonId;
    @JsonProperty("ServiceGroupId")
    private String ServiceGroupId;
    @JsonProperty("LocalNo")
    private Integer LocalNo;
    @JsonProperty("OrderDate")
    private String OrderDate;
    @JsonProperty("RONo")
    private Object RONo;
    @JsonProperty("StockNo")
    private Object StockNo;
    @JsonProperty("Amount")
    private Integer Amount;
    @JsonProperty("TotalSale")
    private Integer TotalSale;
    @JsonProperty("CreateTime")
    private String CreateTime;
    @JsonProperty("LicenceId")
    private String LicenceId;
    @JsonProperty("Approved")
    private Boolean Approved;
    @JsonProperty("DateApproved")
    private Object DateApproved;
    @JsonProperty("OrderNo")
    private String OrderNo;
    @JsonProperty("OrderDescription")
    private Object OrderDescription;
    @JsonProperty("OrderStatusID")
    private Integer OrderStatusID;
    @JsonProperty("OrderStatusReasonId")
    private Object OrderStatusReasonId;
    @JsonProperty("InsuranceId")
    private Object InsuranceId;
    @JsonProperty("InvoiceId")
    private String InvoiceId;
    @JsonProperty("PolicyNumber")
    private Object PolicyNumber;
    @JsonProperty("ClaimNumber")
    private Object ClaimNumber;
    @JsonProperty("Deductible")
    private Object Deductible;
    @JsonProperty("AccidentDate")
    private Object AccidentDate;
    @JsonProperty("Revision")
    private Integer Revision;
    @JsonProperty("OrderTypeId")
    private String OrderTypeId;
    @JsonProperty("OtherInsuranceName")
    private Object OtherInsuranceName;
    @JsonProperty("Vehicle")
    private VehicleDTO Vehicle;
    @JsonProperty("OrderServices")
    private List<Object> OrderServices = null;
    @JsonProperty("OrderAnswers")
    private List<Object> OrderAnswers = null;
    @JsonProperty("OrderEmployees")
    private List<OrderEmployeeDTO> OrderEmployees = null;
    @JsonProperty("DiscountAmount")
    private Integer DiscountAmount;
    @JsonProperty("TaxAmount")
    private Integer TaxAmount;
    @JsonProperty("FeesAmount")
    private Integer FeesAmount;
    @JsonProperty("PONo")
    private String PONo;
    @JsonProperty("PartitionID")
    private Integer PartitionID;
    @JsonProperty("UTCTime")
    private String UTCTime;
    @JsonProperty("TimeZoneOffset")
    private Integer TimeZoneOffset;
    @JsonProperty("Client")
    private ClientDTO Client;
    @JsonProperty("Device")
    private Object Device;
    @JsonProperty("DeviceLicences")
    private DeviceLicencesDTO DeviceLicences;

    @JsonProperty("OrderId")
    public String getOrderId() {
        return OrderId;
    }

    @JsonProperty("OrderId")
    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    @JsonProperty("TeamId")
    public String getTeamId() {
        return TeamId;
    }

    @JsonProperty("TeamId")
    public void setTeamId(String teamId) {
        this.TeamId = teamId;
    }

    @JsonProperty("VehicleID")
    public String getVehicleID() {
        return VehicleID;
    }

    @JsonProperty("VehicleID")
    public void setVehicleID(String vehicleID) {
        this.VehicleID = vehicleID;
    }

    @JsonProperty("ClientId")
    public String getClientId() {
        return ClientId;
    }

    @JsonProperty("ClientId")
    public void setClientId(String clientId) {
        this.ClientId = clientId;
    }

    @JsonProperty("EstimationId")
    public Object getEstimationId() {
        return EstimationId;
    }

    @JsonProperty("EstimationId")
    public void setEstimationId(Object estimationId) {
        this.EstimationId = estimationId;
    }

    @JsonProperty("EstimationNo")
    public Object getEstimationNo() {
        return EstimationNo;
    }

    @JsonProperty("EstimationNo")
    public void setEstimationNo(Object estimationNo) {
        this.EstimationNo = estimationNo;
    }

    @JsonProperty("EmployeeId")
    public String getEmployeeId() {
        return EmployeeId;
    }

    @JsonProperty("EmployeeId")
    public void setEmployeeId(String employeeId) {
        this.EmployeeId = employeeId;
    }

    @JsonProperty("SalesPersonId")
    public Object getSalesPersonId() {
        return SalesPersonId;
    }

    @JsonProperty("SalesPersonId")
    public void setSalesPersonId(Object salesPersonId) {
        this.SalesPersonId = salesPersonId;
    }

    @JsonProperty("ServiceGroupId")
    public String getServiceGroupId() {
        return ServiceGroupId;
    }

    @JsonProperty("ServiceGroupId")
    public void setServiceGroupId(String serviceGroupId) {
        this.ServiceGroupId = serviceGroupId;
    }

    @JsonProperty("LocalNo")
    public Integer getLocalNo() {
        return LocalNo;
    }

    @JsonProperty("LocalNo")
    public void setLocalNo(Integer localNo) {
        this.LocalNo = localNo;
    }

    @JsonProperty("OrderDate")
    public String getOrderDate() {
        return OrderDate;
    }

    @JsonProperty("OrderDate")
    public void setOrderDate(String orderDate) {
        this.OrderDate = orderDate;
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

    @JsonProperty("Amount")
    public Integer getAmount() {
        return Amount;
    }

    @JsonProperty("Amount")
    public void setAmount(Integer amount) {
        this.Amount = amount;
    }

    @JsonProperty("TotalSale")
    public Integer getTotalSale() {
        return TotalSale;
    }

    @JsonProperty("TotalSale")
    public void setTotalSale(Integer totalSale) {
        this.TotalSale = totalSale;
    }

    @JsonProperty("CreateTime")
    public String getCreateTime() {
        return CreateTime;
    }

    @JsonProperty("CreateTime")
    public void setCreateTime(String createTime) {
        this.CreateTime = createTime;
    }

    @JsonProperty("LicenceId")
    public String getLicenceId() {
        return LicenceId;
    }

    @JsonProperty("LicenceId")
    public void setLicenceId(String licenceId) {
        this.LicenceId = licenceId;
    }

    @JsonProperty("Approved")
    public Boolean getApproved() {
        return Approved;
    }

    @JsonProperty("Approved")
    public void setApproved(Boolean approved) {
        this.Approved = approved;
    }

    @JsonProperty("DateApproved")
    public Object getDateApproved() {
        return DateApproved;
    }

    @JsonProperty("DateApproved")
    public void setDateApproved(Object dateApproved) {
        this.DateApproved = dateApproved;
    }

    @JsonProperty("OrderNo")
    public String getOrderNo() {
        return OrderNo;
    }

    @JsonProperty("OrderNo")
    public void setOrderNo(String orderNo) {
        this.OrderNo = orderNo;
    }

    @JsonProperty("OrderDescription")
    public Object getOrderDescription() {
        return OrderDescription;
    }

    @JsonProperty("OrderDescription")
    public void setOrderDescription(Object orderDescription) {
        this.OrderDescription = orderDescription;
    }

    @JsonProperty("OrderStatusID")
    public Integer getOrderStatusID() {
        return OrderStatusID;
    }

    @JsonProperty("OrderStatusID")
    public void setOrderStatusID(Integer orderStatusID) {
        this.OrderStatusID = orderStatusID;
    }

    @JsonProperty("OrderStatusReasonId")
    public Object getOrderStatusReasonId() {
        return OrderStatusReasonId;
    }

    @JsonProperty("OrderStatusReasonId")
    public void setOrderStatusReasonId(Object orderStatusReasonId) {
        this.OrderStatusReasonId = orderStatusReasonId;
    }

    @JsonProperty("InsuranceId")
    public Object getInsuranceId() {
        return InsuranceId;
    }

    @JsonProperty("InsuranceId")
    public void setInsuranceId(Object insuranceId) {
        this.InsuranceId = insuranceId;
    }

    @JsonProperty("InvoiceId")
    public String getInvoiceId() {
        return InvoiceId;
    }

    @JsonProperty("InvoiceId")
    public void setInvoiceId(String invoiceId) {
        this.InvoiceId = invoiceId;
    }

    @JsonProperty("PolicyNumber")
    public Object getPolicyNumber() {
        return PolicyNumber;
    }

    @JsonProperty("PolicyNumber")
    public void setPolicyNumber(Object policyNumber) {
        this.PolicyNumber = policyNumber;
    }

    @JsonProperty("ClaimNumber")
    public Object getClaimNumber() {
        return ClaimNumber;
    }

    @JsonProperty("ClaimNumber")
    public void setClaimNumber(Object claimNumber) {
        this.ClaimNumber = claimNumber;
    }

    @JsonProperty("Deductible")
    public Object getDeductible() {
        return Deductible;
    }

    @JsonProperty("Deductible")
    public void setDeductible(Object deductible) {
        this.Deductible = deductible;
    }

    @JsonProperty("AccidentDate")
    public Object getAccidentDate() {
        return AccidentDate;
    }

    @JsonProperty("AccidentDate")
    public void setAccidentDate(Object accidentDate) {
        this.AccidentDate = accidentDate;
    }

    @JsonProperty("Revision")
    public Integer getRevision() {
        return Revision;
    }

    @JsonProperty("Revision")
    public void setRevision(Integer revision) {
        this.Revision = revision;
    }

    @JsonProperty("OrderTypeId")
    public String getOrderTypeId() {
        return OrderTypeId;
    }

    @JsonProperty("OrderTypeId")
    public void setOrderTypeId(String orderTypeId) {
        this.OrderTypeId = orderTypeId;
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

    @JsonProperty("OrderServices")
    public List<Object> getOrderServices() {
        return OrderServices;
    }

    @JsonProperty("OrderServices")
    public void setOrderServices(List<Object> orderServices) {
        this.OrderServices = orderServices;
    }

    @JsonProperty("OrderAnswers")
    public List<Object> getOrderAnswers() {
        return OrderAnswers;
    }

    @JsonProperty("OrderAnswers")
    public void setOrderAnswers(List<Object> orderAnswers) {
        this.OrderAnswers = orderAnswers;
    }

    @JsonProperty("OrderEmployees")
    public List<OrderEmployeeDTO> getOrderEmployees() {
        return OrderEmployees;
    }

    @JsonProperty("OrderEmployees")
    public void setOrderEmployees(List<OrderEmployeeDTO> orderEmployees) {
        this.OrderEmployees = orderEmployees;
    }

    @JsonProperty("DiscountAmount")
    public Integer getDiscountAmount() {
        return DiscountAmount;
    }

    @JsonProperty("DiscountAmount")
    public void setDiscountAmount(Integer discountAmount) {
        this.DiscountAmount = discountAmount;
    }

    @JsonProperty("TaxAmount")
    public Integer getTaxAmount() {
        return TaxAmount;
    }

    @JsonProperty("TaxAmount")
    public void setTaxAmount(Integer taxAmount) {
        this.TaxAmount = taxAmount;
    }

    @JsonProperty("FeesAmount")
    public Integer getFeesAmount() {
        return FeesAmount;
    }

    @JsonProperty("FeesAmount")
    public void setFeesAmount(Integer feesAmount) {
        this.FeesAmount = feesAmount;
    }

    @JsonProperty("PONo")
    public String getPONo() {
        return PONo;
    }

    @JsonProperty("PONo")
    public void setPONo(String pONo) {
        this.PONo = pONo;
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

    @JsonProperty("Client")
    public ClientDTO getClient() {
        return Client;
    }

    @JsonProperty("Client")
    public void setClient(ClientDTO client) {
        this.Client = client;
    }

    @JsonProperty("Device")
    public Object getDevice() {
        return Device;
    }

    @JsonProperty("Device")
    public void setDevice(Object device) {
        this.Device = device;
    }

    @JsonProperty("DeviceLicences")
    public DeviceLicencesDTO getDeviceLicences() {
        return DeviceLicences;
    }

    @JsonProperty("DeviceLicences")
    public void setDeviceLicences(DeviceLicencesDTO deviceLicences) {
        this.DeviceLicences = deviceLicences;
    }

}

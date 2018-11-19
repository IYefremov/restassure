package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ClientId",
        "ApplicationId",
        "CompanyName",
        "FirstName",
        "LastName",
        "Address",
        "Address2",
        "City",
        "State",
        "Country",
        "Zip",
        "BillingAddress",
        "BillingAddress2",
        "BillingCity",
        "BillingState",
        "BillingCountry",
        "BillingZip",
        "Phone",
        "Email",
        "IsWholeSaler",
        "PORequired",
        "SalesCommission",
        "IsDeleted",
        "Device"
})

public class ClientDTO {

    @JsonProperty("ClientId")
    private String ClientId;
    @JsonProperty("ApplicationId")
    private String ApplicationId;
    @JsonProperty("CompanyName")
    private String CompanyName;
    @JsonProperty("FirstName")
    private String FirstName;
    @JsonProperty("LastName")
    private String LastName;
    @JsonProperty("Address")
    private Object Address;
    @JsonProperty("Address2")
    private Object Address2;
    @JsonProperty("City")
    private Object City;
    @JsonProperty("State")
    private String State;
    @JsonProperty("Country")
    private String Country;
    @JsonProperty("Zip")
    private Object Zip;
    @JsonProperty("BillingAddress")
    private Object BillingAddress;
    @JsonProperty("BillingAddress2")
    private Object BillingAddress2;
    @JsonProperty("BillingCity")
    private Object BillingCity;
    @JsonProperty("BillingState")
    private Object BillingState;
    @JsonProperty("BillingCountry")
    private Object BillingCountry;
    @JsonProperty("BillingZip")
    private Object BillingZip;
    @JsonProperty("Phone")
    private String Phone;
    @JsonProperty("Email")
    private String Email;
    @JsonProperty("IsWholeSaler")
    private Boolean IsWholeSaler;
    @JsonProperty("PORequired")
    private Boolean PORequired;
    @JsonProperty("SalesCommission")
    private Integer SalesCommission;
    @JsonProperty("IsDeleted")
    private Boolean IsDeleted;
    @JsonProperty("Device")
    private Object Device;

    @JsonProperty("ClientId")
    public String getClientId() {
        return ClientId;
    }

    @JsonProperty("ClientId")
    public void setClientId(String clientId) {
        this.ClientId = clientId;
    }

    @JsonProperty("ApplicationId")
    public String getApplicationId() {
        return ApplicationId;
    }

    @JsonProperty("ApplicationId")
    public void setApplicationId(String applicationId) {
        this.ApplicationId = applicationId;
    }

    @JsonProperty("CompanyName")
    public String getCompanyName() {
        return CompanyName;
    }

    @JsonProperty("CompanyName")
    public void setCompanyName(String companyName) {
        this.CompanyName = companyName;
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

    @JsonProperty("Address")
    public Object getAddress() {
        return Address;
    }

    @JsonProperty("Address")
    public void setAddress(Object address) {
        this.Address = address;
    }

    @JsonProperty("Address2")
    public Object getAddress2() {
        return Address2;
    }

    @JsonProperty("Address2")
    public void setAddress2(Object address2) {
        this.Address2 = address2;
    }

    @JsonProperty("City")
    public Object getCity() {
        return City;
    }

    @JsonProperty("City")
    public void setCity(Object city) {
        this.City = city;
    }

    @JsonProperty("State")
    public String getState() {
        return State;
    }

    @JsonProperty("State")
    public void setState(String state) {
        this.State = state;
    }

    @JsonProperty("Country")
    public String getCountry() {
        return Country;
    }

    @JsonProperty("Country")
    public void setCountry(String country) {
        this.Country = country;
    }

    @JsonProperty("Zip")
    public Object getZip() {
        return Zip;
    }

    @JsonProperty("Zip")
    public void setZip(Object zip) {
        this.Zip = zip;
    }

    @JsonProperty("BillingAddress")
    public Object getBillingAddress() {
        return BillingAddress;
    }

    @JsonProperty("BillingAddress")
    public void setBillingAddress(Object billingAddress) {
        this.BillingAddress = billingAddress;
    }

    @JsonProperty("BillingAddress2")
    public Object getBillingAddress2() {
        return BillingAddress2;
    }

    @JsonProperty("BillingAddress2")
    public void setBillingAddress2(Object billingAddress2) {
        this.BillingAddress2 = billingAddress2;
    }

    @JsonProperty("BillingCity")
    public Object getBillingCity() {
        return BillingCity;
    }

    @JsonProperty("BillingCity")
    public void setBillingCity(Object billingCity) {
        this.BillingCity = billingCity;
    }

    @JsonProperty("BillingState")
    public Object getBillingState() {
        return BillingState;
    }

    @JsonProperty("BillingState")
    public void setBillingState(Object billingState) {
        this.BillingState = billingState;
    }

    @JsonProperty("BillingCountry")
    public Object getBillingCountry() {
        return BillingCountry;
    }

    @JsonProperty("BillingCountry")
    public void setBillingCountry(Object billingCountry) {
        this.BillingCountry = billingCountry;
    }

    @JsonProperty("BillingZip")
    public Object getBillingZip() {
        return BillingZip;
    }

    @JsonProperty("BillingZip")
    public void setBillingZip(Object billingZip) {
        this.BillingZip = billingZip;
    }

    @JsonProperty("Phone")
    public String getPhone() {
        return Phone;
    }

    @JsonProperty("Phone")
    public void setPhone(String phone) {
        this.Phone = phone;
    }

    @JsonProperty("Email")
    public String getEmail() {
        return Email;
    }

    @JsonProperty("Email")
    public void setEmail(String email) {
        this.Email = email;
    }

    @JsonProperty("IsWholeSaler")
    public Boolean getIsWholeSaler() {
        return IsWholeSaler;
    }

    @JsonProperty("IsWholeSaler")
    public void setIsWholeSaler(Boolean isWholeSaler) {
        this.IsWholeSaler = isWholeSaler;
    }

    @JsonProperty("PORequired")
    public Boolean getPORequired() {
        return PORequired;
    }

    @JsonProperty("PORequired")
    public void setPORequired(Boolean pORequired) {
        this.PORequired = pORequired;
    }

    @JsonProperty("SalesCommission")
    public Integer getSalesCommission() {
        return SalesCommission;
    }

    @JsonProperty("SalesCommission")
    public void setSalesCommission(Integer salesCommission) {
        this.SalesCommission = salesCommission;
    }

    @JsonProperty("IsDeleted")
    public Boolean getIsDeleted() {
        return IsDeleted;
    }

    @JsonProperty("IsDeleted")
    public void setIsDeleted(Boolean isDeleted) {
        this.IsDeleted = isDeleted;
    }

    @JsonProperty("Device")
    public Object getDevice() {
        return Device;
    }

    @JsonProperty("Device")
    public void setDevice(Object device) {
        this.Device = device;
    }

}

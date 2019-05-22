package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RetailCustomer implements AppCustomer {
	
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("clientId")
	private String clientId;

	@JsonProperty("companyName")
	private String companyName;

	@JsonProperty("mailAddress")
	private String mailAddress;

	@JsonProperty("customerPhone")
	private String customerPhone;

	@JsonProperty("customerAddress1")
	private String customerAddress1;

	@JsonProperty("customerAddress2")
	private String customerAddress2;

	@JsonProperty("customerCity")
	private String customerCity;

	@JsonProperty("customerCountry")
	private String customerCountry;

	@JsonProperty("customerState")
	private String customerState;

	@JsonProperty("customerZipCode")
	private String customerZipCode;
	
	public RetailCustomer() {
		this.firstName = "";
		this.lastName = "";
	}
	
	public RetailCustomer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;	
	}

	@Override
	public String getMailAddress() {
		return mailAddress;
	}
	
	@Override
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	
	@Override
	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	public boolean isWholesale() {
		return false;
	}

	@Override
	public String getCompany() {
		return companyName;
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName = companyName;		
	}

	public void setClientId(String clientId) { this.clientId = clientId; }

	public String getClientId() { return clientId; }

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;		
	}
	
	public String getCustomerAddress1() {
		return customerAddress1;
	}

	public void setCustomerAddress1(String customerAddress1) {
		this.customerAddress1 = customerAddress1;		
	}
	
	public String getCustomerAddress2() {
		return customerAddress2;
	}

	public void setCustomerAddress2(String customerAddress2) {
		this.customerAddress2 = customerAddress2;		
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;		
	}
	
	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;		
	}
	
	public String getCustomerState() {
		return customerState;
	}

	public void setCustomerState(String customerState) {
		this.customerState = customerState;		
	}
	
	public String getCustomerZip() {
		return customerZipCode;
	}

	public void setCustomerZip(String customerZip) {
		this.customerZipCode = customerZip;		
	}
}

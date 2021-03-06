package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WholesailCustomer implements AppCustomer {
	
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("lastName")
	private String lastName;
	
	@JsonProperty("companyName")
	private String companyName;
	
	@JsonProperty("mailAddress")
	private String mailAddress;

	public WholesailCustomer() {

	}

	public WholesailCustomer(String firstName, String lastName, String companyName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyName = companyName;
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
		return companyName;
	}

	@Override
	public boolean isWholesale() {
		return true;
	}

	@Override
	public String getCompany() {
		return companyName;
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
		
	}


}

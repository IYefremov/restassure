package com.cyberiansoft.test.dataclasses;

public class WholesailCustomer implements AppCustomer {
	
	private String firstName;
	private String lastName;
	private String companyName;
	private String mailAddress;
	
	public WholesailCustomer(String companyName) {
		this.setCompanyName(companyName);	
	}
	
	public WholesailCustomer(String companyName, String mailAddress) {
		this.setCompanyName(companyName);	
		this.setMailAddress(mailAddress);
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
	public String getCompany() {
		return companyName;
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
		
	}


}

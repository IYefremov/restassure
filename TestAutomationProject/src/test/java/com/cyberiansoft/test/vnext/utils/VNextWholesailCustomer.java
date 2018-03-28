package com.cyberiansoft.test.vnext.utils;

public class VNextWholesailCustomer implements VNextCustomer {
	
	private String firstname;
	private String lastname;
	private String companyName;
	private String mailAddress;
	
	public VNextWholesailCustomer(String companyName) {
		this.setCompanyName(companyName);	
	}
	
	public VNextWholesailCustomer(String companyName, String mailAddress) {
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
		return lastname;
	}

	@Override
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String getFirstName() {
		return firstname;
	}

	@Override
	public void setFirstName(String firstname) {
		this.firstname = firstname;
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

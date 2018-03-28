package com.cyberiansoft.test.vnext.utils;

public class VNextRetailCustomer implements VNextCustomer {
	
	private String firstname;
	private String lastname;
	private String companyName;
	private String mailAddress;
	private String customerPhone;
	private String customerAddress1;
	private String customerAddress2;
	private String customerCity;
	private String customerCountry;
	private String customerState;
	private String customerZipCode;
	
	public VNextRetailCustomer() {
		this.setFirstName("");
		this.setLastName("");		
	}
	
	public VNextRetailCustomer(String firstName, String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);		
	}
	
	/*public VNextRetailCustomer(String firstName, String lastName, String customerMail) {
		this.setFirstName(firstName);
		this.setLastName(lastName);	
		this.setMailAddress(customerMail);
	}*/
	
	/*public VNextRetailCustomer(String firstName, String lastName, String companyName, String customerMail,
			String customerPhone, String customerAddress, String customerCountry, String customerState) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setCompanyName(companyName);
		this.setCustomerPhone(customerPhone);
		this.setMailAddress(customerMail);
		this.setCustomerAddress1(customerAddress);
		this.setCustomerCountry(customerCountry);
		this.setCustomerState(customerState);
	}
	
	public VNextRetailCustomer(String firstName, String lastName, String companyName, String customerMail,
			String customerPhone, String customerAddress1, String customerAddress2, String customerCountry, String customerState) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setCompanyName(companyName);
		this.setCustomerPhone(customerPhone);
		this.setMailAddress(customerMail);
		this.setCustomerAddress1(customerAddress);
		this.setCustomerCountry(customerCountry);
		this.setCustomerState(customerState);
	}*/

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
		return firstname + " " + lastname;
	}

	@Override
	public String getCompany() {
		return companyName;
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName = companyName;		
	}
	
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

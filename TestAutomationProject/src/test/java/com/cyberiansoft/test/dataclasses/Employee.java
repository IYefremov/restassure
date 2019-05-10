package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
	
	@JsonProperty("employeeFirstName")
	private String employeeFirstName;

	@JsonProperty("employeeLastName")
	private String employeeLastName;
	
	@JsonProperty("employeePassword")
	private String employeePassword;

	@JsonProperty("employeeEmail")
	private String employeeEmail;

	@JsonProperty("phoneCountryCode")
	private String phoneCountryCode;

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("employeeId")
	private String employeeId;

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public String getEmployeeName() {
		return employeeFirstName + " " + employeeLastName;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public String getEmployeePhoneCountryCode() {
		return phoneCountryCode;
	}

	public String getEmployeePhoneNumber() {
		return phoneNumber;
	}
	
	public String getEmployeePassword() {
		return employeePassword;
	}

	public String getEmployeeID() {
		return employeeId;
	}

}

package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
	
	@JsonProperty("employeeName")
	private String employeeName;
	
	@JsonProperty("employeePassword")
	private String employeePassword;
	
	public String getEmployeeName() {
		return employeeName;
	}
	
	public String getEmployeePassword() {
		return employeePassword;
	}

}

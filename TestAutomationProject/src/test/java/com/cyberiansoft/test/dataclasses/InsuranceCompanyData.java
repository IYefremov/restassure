package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsuranceCompanyData {
	
	@JsonProperty("insuranceCompanyName")
	String insuranceCompanyName;

	@JsonProperty("claimNumber")
	String claimNumber;
	 
	@JsonProperty("policyNumber")
	String policyNumber;
	
	public String getInsuranceCompanyName() {
		 return insuranceCompanyName;
	}
	 
	public String getClaimNumber() {
		 return claimNumber;
	}
	 
	public String getPolicyNumber() {
		 return policyNumber;
	}

}

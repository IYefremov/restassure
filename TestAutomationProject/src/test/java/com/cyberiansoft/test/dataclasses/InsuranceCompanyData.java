package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsuranceCompanyData {
	
	@JsonProperty("insuranceCompanyName")
	String insuranceCompanyName;

	@JsonProperty("claimNumber")
	String claimNumber;
	 
	@JsonProperty("policyNumber")
	String policyNumber;

	@JsonProperty("deductible")
	String deductible;
	
	public String getInsuranceCompanyName() {
		 return insuranceCompanyName;
	}
	 
	public String getClaimNumber() {
		 return claimNumber;
	}
	 
	public String getPolicyNumber() {
		 return policyNumber;
	}

	public String getDeductible() {
		return deductible;
	}

}

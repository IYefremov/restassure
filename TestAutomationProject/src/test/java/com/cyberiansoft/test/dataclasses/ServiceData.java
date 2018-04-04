package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceData {
	
	@JsonProperty("serviceName")
	String serviceName;

	@JsonProperty("servicePrice")
	String servicePrice;
	 
	@JsonProperty("serviceQuantity")
	String serviceQuantity;
	
	@JsonProperty("serviceStatus")
	String serviceStatus;
	 
	public String getServiceName() {
		return serviceName; 
	}
	 
	public String getServicePrice() {
		return servicePrice; 
	}
	 
	public String getServiceQuantity() {
		return serviceQuantity; 
	}
	
	public String getServiceStatus() {
		return serviceStatus; 
	}

}

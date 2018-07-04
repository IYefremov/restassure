package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleInfoData {
	
	@JsonProperty("vinNumber")
	String vinNumber;

	@JsonProperty("stockNumber")
	String stockNumber;
	 
	@JsonProperty("roNumber")
	String roNumber;

	@JsonProperty("mileage")
	String mileage;
	
	public String getVINNumber() {
		 return vinNumber;
	}
	 
	public String getStockNumber() {
		 return stockNumber;
	}
	 
	public String getRoNumber() {
		 return roNumber;
	}

	public String getMileage() {
		return mileage;
	}

}

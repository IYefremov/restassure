package com.cyberiansoft.test.dataclasses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class InspectionData {
	
	 @JsonProperty("inspType")
	 String inspType;

	 @JsonProperty("vihicleInfo")
	 VehicleInfoData vihicleInfo;
	 
	 @JsonProperty("newVinNumber")
	 String newVinNumber;
	 
	 @JsonProperty("service")
	 ServiceData service;
	 
	 @JsonProperty("serviceStatus")
	 String serviceStatus;
	 
	 @JsonProperty("services")
	 List<ServiceData> services;
	 
	 @JsonProperty("insuranceCompany")
	 InsuranceCompanyData insuranceCompany;
	 
	 
	 public String getInspectionType() {
		 return inspType;
	 }
	 
	 public String getVinNumber() {
		 return vihicleInfo.getVINNumber();
	 }
	 
	 public String getNewVinNumber() {
		 return newVinNumber;
	 }
	 
	 public String getServiceStatus() {
		 return serviceStatus;
	 }
	 
	 public String getServiceName() {
		 return service.getServiceName();
	 }
	 
	 public String getServicePrice() {
		 return service.getServicePrice();
	 }
	 
	 public String getServiceNameByIndex(int serviceIndex) {
		 return services.get(serviceIndex).getServiceName();
	 }
	 
	 public String getServiceQuantityByIndex(int serviceIndex) {
		 return services.get(serviceIndex).getServiceQuantity();
	 } 
	 
	 public String getServicePriceByIndex(int serviceIndex) {
		 return services.get(serviceIndex).getServicePrice();
	 } 
	 
	 public String getInsuranceCompanyName() {
		 return insuranceCompany.getInsuranceCompanyName();
	 }
	 
	 public String getInsuranceClaimNumber() {
		 return insuranceCompany.getClaimNumber();
	 }
	 
	 public String getInsurancePolicyNumber() {
		 return insuranceCompany.getPolicyNumber();
	 }
}

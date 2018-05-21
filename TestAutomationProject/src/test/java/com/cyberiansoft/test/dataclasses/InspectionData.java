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

	 @JsonProperty("moneyService")
	 ServiceData moneyService;

	 @JsonProperty("percentageService")
	 ServiceData percentageService;

	 @JsonProperty("services")
	 List<ServiceData> services;

	 @JsonProperty("moneyServices")
	 List<ServiceData> moneyServices;

	 @JsonProperty("percentageServices")
	 List<ServiceData> percentageServices;

	 @JsonProperty("matrixService")
	 MatrixServiceData matrixService;

	 @JsonProperty("insuranceCompany")
	 InsuranceCompanyData insuranceCompany;
	 
	 @JsonProperty("inspPrice")
	 String inspPrice;
	 
	 @JsonProperty("inspApprovedPrice")
	 String inspApprovedPrice;
	 
	 @JsonProperty("declineReason")
	 String declineReason;

	@JsonProperty("inspectionNumber")
	String inspectionNumber;

	@JsonProperty("inspectionStatus")
	String inspectionStatus;

	@JsonProperty("retailCustomer")
	RetailCustomer retailCustomer;
	 
	 public String getInspectionType() {
		 return inspType;
	 }
	 
	 public String getInspectionPrice() {
		 return inspPrice;
	 }
	 
	 public String getInspectionApprovedPrice() {
		 return inspApprovedPrice;
	 }
	 
	 public String getDeclineReason() {
		 return declineReason;
	 }
	 
	 public String getVinNumber() {
		 return vihicleInfo.getVINNumber();
	 }
 
	 public String getNewVinNumber() {
		 return newVinNumber;
	 }
	 
	 public ServiceStatus getServiceStatus() {
		 return service.getServiceStatus();
	 }
	 
	 public String getServiceName() {
		 return service.getServiceName();
	 }
	 
	 public String getServicePrice() {
		 return service.getServicePrice();
	 }

	 public String getMoneyServiceName() {
		return moneyService.getServiceName();
	}

	 public String getMoneyServicePrice() {
		return moneyService.getServicePrice();
	}

	 public String getMoneyServiceQuantity() { return moneyService.getServiceQuantity(); }

	 public String getPercentageServiceName() {
		return percentageService.getServiceName();
	}

	 public String getPercentageServicePrice() {
		return percentageService.getServicePrice();
	}
	 
	 public List<ServiceData> getServicesList() {
		 return services;
	 }

	 public List<ServiceData> getMoneyServicesList() {
		return moneyServices;
	}

	 public List<ServiceData> getPercentageServicesList() {
		return percentageServices;
	}

	 public MatrixServiceData getMatrixServiceData() { return matrixService; }

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

	 public String getInspectionNumber() {
		return inspectionNumber;
	}

	 public void setInspectionNumber(String inspNumber) {

	 	this.inspectionNumber =inspNumber;
	}

	public InspectionStatus getInspectionStatus() {
		return InspectionStatus.getStatus(inspectionStatus);
	}

	public RetailCustomer getInspectionRetailCustomer() {
	 	return retailCustomer;
	}
}

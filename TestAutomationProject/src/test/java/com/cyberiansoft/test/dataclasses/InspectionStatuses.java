package com.cyberiansoft.test.dataclasses;

public enum InspectionStatuses {
	
	NEW("New"),
	APPROVED("Approved"),
	DECLINED("Declined"),
	DRAFT("Draft");

	private final String inspStatus; 
	
	InspectionStatuses(final String inspStatus) { 
       this.inspStatus = inspStatus; 
   } 
	
	public String getInspectionStatusValue() { 
       return inspStatus; 
   } 
}

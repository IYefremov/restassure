package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum InspectionStatus {

	APPROVED ("Approved"),
	NEW( "New"),
	DRAFT("Draft");

	@Getter
	private String statusString;

	InspectionStatus(String statusString) {
		this.statusString = statusString;
	}

}

package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum InspectionStatus {

	APPROVED("Approved"),
	ARCHIVED("Archived"),
	NEW("New"),
	DECLINED("Declined"),
	DRAFT("Draft"),
	SKIPPED("Skipped");

	@Getter
	private String statusString;

	InspectionStatus(String statusString) {
		this.statusString = statusString;
	}

}

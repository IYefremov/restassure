package com.cyberiansoft.test.enums.partsmanagement;

import lombok.Getter;

@Getter
public enum DashboardFilters {

    PAST_DUE_PARTS("Past Due Parts"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    QUOTE_RECEIVED("Quote Received");

    private String filter;

    DashboardFilters(final String filter) {
        this.filter = filter;
    }
}

package com.cyberiansoft.test.enums;

public enum DateUtils {
    FULL_DATE_FORMAT("MM/dd/uuuu"),
    SHORT_DATE_FORMAT("MM/d/uuuu"),
    THE_SHORTEST_DATE_FORMAT("M/d/uuuu"),
    DETAILED_FULL_DATE_FORMAT("EEEE, MMMM dd, uuuu"),
    SHORT_DATE_FORMAT_WITH_COMMA("MMM d, uuuu"),
    ZONE_ID("US/Pacific");

    private String data;

    DateUtils(final String data) {
        this.data = data;
    }

    public String getFormat() {
        return data;
    }
}
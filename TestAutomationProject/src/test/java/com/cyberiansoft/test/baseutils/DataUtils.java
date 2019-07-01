package com.cyberiansoft.test.baseutils;

public enum DataUtils {
    MONEY_SYMBOL("$"),
    FULL_DATE_FORMAT("MM/dd/uuuu"),
    SHORT_DATE_FORMAT("MM/d/uuuu"),
    THE_SHORTEST_DATE_FORMAT("M/d/uuuu"),
    DETAILED_FULL_DATE_FORMAT("EEEE, MMMM dd, uuuu"),
    ZONE_ID("US/Pacific");

    private String data;

    DataUtils(final String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
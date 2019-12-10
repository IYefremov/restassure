package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum TimeFrameValues {

    TIMEFRAME_WEEKTODATE("Week To Date"),
    TIMEFRAME_LASTWEEK("Last Week"),
    TIMEFRAME_MONTHTODATE("Month to Date"),
    TIMEFRAME_LASTMONTH("Last Month"),
    TIMEFRAME_30_DAYS("Last 30 days"),
    TIMEFRAME_90_DAYS("Last 90 days"),
    TIMEFRAME_YEARTODATE("Year To Date"),
    TIMEFRAME_LASTYEAR("Last Year"),
    TIMEFRAME_CUSTOM("Custom"),
    TIMEFRAME_TODAY("Today");

    private String name;

    TimeFrameValues(final String name) {
        this.name = name;
    }
}
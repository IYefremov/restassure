package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOTimesheetsData {

    @JsonProperty("fromDate")
    private String fromDate;

    @JsonProperty("toDate")
    private String toDate;

    @JsonProperty("team")
    private String team;

    @JsonProperty("startingDayTue")
    private String startingDayTue;

    @JsonProperty("fromDate2")
    private String fromDate2;

    @JsonProperty("startingDayMon")
    private String startingDayMon;

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getTeam() {
        return team;
    }

    public String getStartingDayTue() {
        return startingDayTue;
    }

    public String getFromDate2() {
        return fromDate2;
    }

    public String getStartingDayMon() {
        return startingDayMon;
    }
}
package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class VNextBOMonitorData  {

    @JsonProperty("location")
    private String location;

    @JsonProperty("copyright")
    private String copyright;

    @JsonProperty("AMT")
    private String AMT;

    @JsonProperty("titles")
    private String[] titles;

    @JsonProperty("titlesRepeater")
    private String[] titlesRepeater;

    public String getLocation() {
        return location;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getAMT() {
        return AMT;
    }

    public List<String> getTitles() {
        return Arrays.asList(titles);
    }

    public List<String> getTitlesRepeater() {
        return Arrays.asList(titlesRepeater);
    }
}

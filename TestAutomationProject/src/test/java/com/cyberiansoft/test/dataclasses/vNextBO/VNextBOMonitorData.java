package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class VNextBOMonitorData  {

    @JsonProperty("location")
    private String location;

    @JsonProperty("titles")
    private String[] titles;

    @JsonProperty("titlesRepeater")
    private String[] titlesRepeater;

    public String getLocation() {
        return location;
    }

    public List<String> getTitles() {
        return Arrays.asList(titles);
    }

    public List<String> getTitlesRepeater() {
        return Arrays.asList(titlesRepeater);
    }
}

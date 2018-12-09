package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class VNextBOMonitorData  {

    @JsonProperty("location")
    private String location;

    @JsonProperty("searchLocation")
    private String searchLocation;

    @JsonProperty("vin")
    private String vin;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("roNumber")
    private String roNumber;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

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

    public String getSearchLocation() {
        return searchLocation;
    }

    public String getVin() {
        return vin;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getRoNumber() {
        return roNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
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

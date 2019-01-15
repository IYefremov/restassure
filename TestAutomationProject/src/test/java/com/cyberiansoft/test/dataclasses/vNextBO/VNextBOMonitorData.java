package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class VNextBOMonitorData  {

    @JsonProperty("location")
    private String location;

    @JsonProperty("searchLocation")
    private String searchLocation;

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

    @JsonProperty("company")
    private String company;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("employee")
    private String employee;

    @JsonProperty("phase")
    private String phase;

    @JsonProperty("department")
    private String department;

    @JsonProperty("woType")
    private String woType;

    @JsonProperty("woNum")
    private String woNum;

    @JsonProperty("roNum")
    private String roNum;

    @JsonProperty("stockNum")
    private String stockNum;

    @JsonProperty("vinNum")
    private String vinNum;

    @JsonProperty("daysInPhase")
    private String daysInPhase;

    @JsonProperty("daysNum")
    private String daysNum;

    @JsonProperty("daysNumStart")
    private String daysNumStart;

    @JsonProperty("daysInProcess")
    private String daysInProcess;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("fromDate")
    private String fromDate;

    @JsonProperty("toDate")
    private String toDate;

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

    public String getCompany() {
        return company;
    }

    public String getCustomer() {
        return customer;
    }

    public String getEmployee() {
        return employee;
    }

    public String getPhase() {
        return phase;
    }

    public String getDepartment() {
        return department;
    }

    public String getWoType() {
        return woType;
    }

    public String getWoNum() {
        return woNum;
    }

    public String getRoNum() {
        return roNum;
    }

    public String getStockNum() {
        return stockNum;
    }

    public String getVinNum() {
        return vinNum;
    }

    public String getDaysInPhase() {
        return daysInPhase;
    }

    public String getDaysNum() {
        return daysNum;
    }

    public String getDaysNumStart() {
        return daysNumStart;
    }

    public String getDaysInProcess() {
        return daysInProcess;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public List<String> getTitles() {
        return Arrays.asList(titles);
    }

    public List<String> getTitlesRepeater() {
        return Arrays.asList(titlesRepeater);
    }
}

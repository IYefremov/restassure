package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOMonitorEventsData {

    @JsonProperty("eventName")
    private String eventName;

    @JsonProperty("alertName")
    private String alertName;

    @JsonProperty("firstConditionName")
    private String firstConditionName;

    @JsonProperty("firstConditionType")
    private String firstConditionType;

    @JsonProperty("firstConditionCriterion")
    private String firstConditionCriterion;

    @JsonProperty("firstConditionNames")
    private String[] firstConditionNames;

    @JsonProperty("firstConditionTypes")
    private String[] firstConditionTypes;

    @JsonProperty("firstConditionCriteria")
    private String[] firstConditionCriteria;

    public String getEventName() {
        return eventName;
    }

    public String getAlertName() {
        return alertName;
    }

    public String getFirstConditionName() {
        return firstConditionName;
    }

    public String getFirstConditionType() {
        return firstConditionType;
    }

    public String getFirstConditionCriterion() {
        return firstConditionCriterion;
    }

    public String[] getFirstConditionNames() {
        return firstConditionNames;
    }

    public String[] getFirstConditionTypes() {
        return firstConditionTypes;
    }

    public String[] getFirstConditionCriteria() {
        return firstConditionCriteria;
    }
}

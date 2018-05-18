package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InHouseRulesData {

    @JsonProperty("ruleName")
    private String ruleName;

    @JsonProperty("condition")
    private String condition;

    @JsonProperty("condition2")
    private String condition2;

    @JsonProperty("order")
    private String order;

    @JsonProperty("order2")
    private String order2;

    @JsonProperty("ruleDescription")
    private String ruleDescription;

    @JsonProperty("ruleDescription2")
    private String ruleDescription2;

    public String getRuleName() {
        return ruleName;
    }

    public String getCondition() {
        return condition;
    }

    public String getCondition2() {
        return condition2;
    }

    public String getOrder() {
        return order;
    }

    public String getOrder2() {
        return order2;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public String getRuleDescription2() {
        return ruleDescription2;
    }
}

package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DamageData {

    @JsonProperty("damageServiceName")
    String damageServiceName;

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    public String getDamageServiceName() {
        return damageServiceName;
    }

    public ServiceData getMoneyServiceData() {
        return moneyService;
    }

    public ServiceData getPercentageServiceData() {
        return percentageService;
    }
}

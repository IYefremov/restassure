package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DamageData {

    @JsonProperty("damageGroupName")
    String damageGroupName;

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("moneyService")
    List<ServiceData> moneyServices;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    public String getDamageGroupName() {
        return damageGroupName;
    }

    public ServiceData getMoneyServiceData() {
        return moneyService;
    }

    public ServiceData getPercentageServiceData() {
        return percentageService;
    }

    public List<ServiceData> getMoneyServicesData() {
        return moneyServices;
    }
}

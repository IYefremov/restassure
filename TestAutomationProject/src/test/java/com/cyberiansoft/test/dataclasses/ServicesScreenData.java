package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ServicesScreenData {

    @JsonProperty("screenName")
    String screenName;

    @JsonProperty("damageData")
    DamageData damageData;

    @JsonProperty("damagesData")
    List<DamageData> damagesData;

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    @JsonProperty("moneyServices")
    List<ServiceData> moneyServices;

    @JsonProperty("percentageServices")
    List<ServiceData> percentageServices;

    @JsonProperty("bundleService")
    BundleServiceData bundleService;

    @JsonProperty("laborService")
    LaborServiceData laborService;

    @JsonProperty("screenPrice")
    String screenPrice;

    @JsonProperty("screenTotalPrice")
    String screenTotalPrice;

}

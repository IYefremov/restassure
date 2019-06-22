package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class DamageData {

    @JsonProperty("damageGroupName")
    String damageGroupName;

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("moneyServices")
    List<ServiceData> moneyServices;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    @JsonProperty("bundleService")
    BundleServiceData bundleService;

    @JsonProperty("matrixService")
    MatrixServiceData matrixService;

    @JsonProperty("serviceDefaultTechnician")
    ServiceTechnician serviceDefaultTechnician;

    @JsonProperty("serviceNewTechnician")
    ServiceTechnician serviceNewTechnician;
}

package com.cyberiansoft.test.dataclasses;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DamageData {

    @JsonProperty("damageGroupName")
    String damageGroupName;

    @JsonProperty("orderPanelName")
    String orderPanelName;

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("moneyServices")
    List<ServiceData> moneyServices;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    @JsonProperty("partServiceData")
    PartServiceData partServiceData;

    @JsonProperty("bundleService")
    BundleServiceData bundleService;

    @JsonProperty("matrixService")
    MatrixServiceData matrixService;

    @JsonProperty("serviceDefaultTechnician")
    ServiceTechnician serviceDefaultTechnician;

    @JsonProperty("serviceNewTechnician")
    ServiceTechnician serviceNewTechnician;

    @JsonProperty("laborService")
    LaborServiceData laborService;
}

package com.cyberiansoft.test.dataclasses;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BundleServiceData {

    @JsonProperty("bundleServiceName")
    private String bundleServiceName;

    @JsonProperty("service")
    ServiceData service;

    @JsonProperty("laborService")
    LaborServiceData laborService;

    @JsonProperty("services")
    List<ServiceData> services;

    @JsonProperty("moneyServices")
    List<ServiceData> moneyServices;

    @JsonProperty("percentageServices")
    List<ServiceData> percentageServices;

    @JsonProperty("partService")
    List<PartServiceData> partService;

    @JsonProperty("bundleServiceAmount")
    private String bundleServiceAmount;
}

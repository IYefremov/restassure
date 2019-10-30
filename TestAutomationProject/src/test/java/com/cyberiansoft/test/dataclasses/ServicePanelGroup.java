package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServicePanelGroup {

    @JsonProperty("panelGroupName")
    String panelGroupName;

    @JsonProperty("moneyService")
    ServiceData moneyService;

    @JsonProperty("moneyServices")
    List<ServiceData> moneyServices;

    @JsonProperty("percentageService")
    ServiceData percentageService;

    @JsonProperty("laborService")
    LaborServiceData laborService;
}

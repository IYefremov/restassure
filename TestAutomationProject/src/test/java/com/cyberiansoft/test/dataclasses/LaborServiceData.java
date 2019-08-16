package com.cyberiansoft.test.dataclasses;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class LaborServiceData {

    @JsonProperty("serviceName")
    String serviceName;

    @JsonProperty("laborServicePanel")
    String laborServicePanel;

    @JsonProperty("laborServicePart")
    String laborServicePart;

    @JsonProperty("laborServiceRate")
    String laborServiceRate;

    @JsonProperty("laborServiceTime")
    String laborServiceTime;

    @JsonProperty("laborServiceNotes")
    String laborServiceNotes;

    @JsonProperty("laborServicePriceVerify")
    String laborServicePriceVerify;

    @JsonProperty("partServiceDataList")
    List<PartServiceData> partServiceDataList;



    public String getLaborServicePrice() {
        return laborServiceRate + " x " + laborServiceTime;
    }

}

package com.cyberiansoft.test.dataclasses.partservice;

import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartServiceData {
    @JsonProperty("serviceName")
    private String serviceName;
    @JsonProperty("category")
    private String category;
    @JsonProperty("subCategory")
    private String subCategory;
    @JsonProperty("partName")
    private PartName partName;
    @JsonProperty("partPosition")
    private String partPosition;
    @JsonProperty("servicePrice")
    private String servicePrice;
    @JsonProperty("laborServiceDataList")
    private List<LaborServiceData> laborServiceDataList;

}

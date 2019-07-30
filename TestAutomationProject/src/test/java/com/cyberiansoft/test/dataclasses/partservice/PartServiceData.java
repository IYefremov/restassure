package com.cyberiansoft.test.dataclasses.partservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
    private String partName;
    @JsonProperty("partPosition")
    private String partPosition;
    @JsonProperty("servicePrice")
    private String servicePrice;
}

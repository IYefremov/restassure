package com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOPartsData {

    @JsonProperty("service")
    private String service;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;

    @JsonProperty("subcategory")
    private String subcategory;

    @JsonProperty("partName")
    private String partName;

    @JsonProperty("partItems")
    private String[] partItems;
}

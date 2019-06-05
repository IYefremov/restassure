package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicePartData {

    @JsonProperty("serviceCategory")
    private ServicePart serviceCategory;

    @JsonProperty("serviceSubCategory")
    private ServicePart serviceSubCategory;

    @JsonProperty("serviceSubCategoryPart")
    private ServicePart serviceSubCategoryPart;

    @JsonProperty("serviceSubCategoryPosition")
    private ServicePart serviceSubCategoryPosition;

    @JsonProperty("servicePartValue")
    private String servicePartValue;

}

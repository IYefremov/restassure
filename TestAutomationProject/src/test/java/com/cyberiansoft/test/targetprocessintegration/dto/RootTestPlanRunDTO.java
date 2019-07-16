package com.cyberiansoft.test.targetprocessintegration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RootTestPlanRunDTO {

    @JsonProperty("ResourceType")
    private String resourceType;
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("Name")
    private String name;
}

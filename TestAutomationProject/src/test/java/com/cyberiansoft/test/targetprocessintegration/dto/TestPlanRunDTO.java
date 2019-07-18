package com.cyberiansoft.test.targetprocessintegration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestPlanRunDTO {

    @JsonProperty("ResourceType")
    private String resourceType;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("TestCaseRuns")
    private TestCaseRunsDTO testCaseRuns;
}

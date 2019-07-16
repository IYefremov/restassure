package com.cyberiansoft.test.targetprocessintegration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCaseRunDTO {
    @JsonProperty("ResourceType")
    private String resourceType;
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("Executed")
    private Boolean executed;
    @JsonProperty("Passed")
    private Boolean passed;
    @JsonProperty("Comment")
    private String comment;
    @JsonProperty("EndRunDate")
    private String endRunDate;
    @JsonProperty("StartRunDate")
    private String startRunDate;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("EntityType")
    private EntityTypeDTO entityType;
    @JsonProperty("TestPlanRun")
    private TestPlanRunDTO testPlanRun;
    @JsonProperty("RootTestPlanRun")
    private TestPlanRunDTO rootTestPlanRun;
    @JsonProperty("TestCase")
    private TestCaseDTO testCase;
    @JsonProperty("Priority")
    private PriorityDTO priority;
    @JsonProperty("LastExecutor")
    private LastExecutorDTO lastExecutor;
}

package com.cyberiansoft.test.targetprocessintegration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestPlanRunsDTO {

    @JsonProperty("Items")
    private List<TestPlanRunDTO> items = null;
}
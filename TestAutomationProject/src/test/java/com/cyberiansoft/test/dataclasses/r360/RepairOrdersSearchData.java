package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RepairOrdersSearchData {

    @JsonProperty("savedSearch")
    private String savedSearch;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("department")
    private String department;

    @JsonProperty("phase")
    private String phase;

    @JsonProperty("repairStatus")
    private String repairStatus;

    @JsonProperty("flag")
    private String flag;

    @JsonProperty("priority")
    private String priority;
}
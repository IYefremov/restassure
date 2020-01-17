package com.cyberiansoft.test.dataclasses.vNextBO.inspections;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOInspectionsDetailsData {

    @JsonProperty("inspectionId")
    private String inspectionId;

    @JsonProperty("fromDate")
    private String fromDate;

    @JsonProperty("toDate")
    private String toDate;
}

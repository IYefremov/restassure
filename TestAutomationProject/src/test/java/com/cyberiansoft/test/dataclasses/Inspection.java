package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Inspection {

    @JsonProperty("inspectionsData")
    List<InspectionData> inspectionsData;

    public List<InspectionData> getInspectionsData() {
        return inspectionsData;
    }
}

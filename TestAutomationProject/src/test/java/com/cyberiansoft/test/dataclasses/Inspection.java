package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Inspection {

    @JsonProperty("inspectionData")
    InspectionData inspectionData;

    @JsonProperty("inspectionDatas")
    List<InspectionData> inspectionDatas;

    public InspectionData getInspectionData() {
        return inspectionData;
    }

    public List<InspectionData> getInspectionDatasList() {
        return inspectionDatas;
    }
}

package com.cyberiansoft.test.dataclasses.vNextBO.partsManagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOBaseData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class VNextBOPartsManagementData extends VNextBOBaseData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("dashboardItemsNames")
    private String[] dashboardItemsNames;

    @JsonProperty("statusesList")
    private List<String> statusesList;
}

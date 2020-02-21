package com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOBaseData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class VNextBOPartsManagementData extends VNextBOBaseData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("partNumber")
    private String partNumber;

    @JsonProperty("dashboardItemsNames")
    private String[] dashboardItemsNames;

    @JsonProperty("statusesList")
    private List<String> statusesList;

    @JsonProperty("searchData")
    private VNextBOPartsManagementSearchData searchData;

    @JsonProperty("partData")
    private VNextBOPartsData partData;

    @JsonProperty("documentData")
    private VNextBODocumentData documentData;
}

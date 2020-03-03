package com.cyberiansoft.test.dataclasses.vNextBO;

import com.cyberiansoft.test.dataclasses.vNextBO.companyinfo.VNextBOCompanyInfoData;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsData;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementSearchData;
import com.cyberiansoft.test.vnextbo.utils.InspectionsSearchFields;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBONewSmokeData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("labor")
    private String labor;

    @JsonProperty("note")
    private String note;

    @JsonProperty("partItems")
    private String[] partItems;

    @JsonProperty("statuses")
    private String[] statuses;

    @JsonProperty("companyInfo")
    private VNextBOCompanyInfoData companyInfo;

    @JsonProperty("searchFields")
    private InspectionsSearchFields searchFields;

    @JsonProperty("partData")
    private VNextBOPartsData partData;

    @JsonProperty("searchData")
    private VNextBOPartsManagementSearchData searchData;
}
package com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOBaseData;
import com.cyberiansoft.test.dataclasses.vNextBO.addons.VNextBOAddOnsData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang.RandomStringUtils;

import java.util.List;

@Getter
public class VNextBOPartsManagementData extends VNextBOBaseData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("partNumber")
    private String partNumber;

    @JsonProperty("addOn")
    private String addOn;

    @JsonProperty("subscriptions")
    private String[] subscriptions;

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

    @JsonProperty("areaLocationData")
    private VNextBOAreaLocationData areaLocationData;

    @JsonProperty("addOnsData")
    private VNextBOAddOnsData addOnsData;

    public String getRandomNote() {
        return "auto-test-" + RandomStringUtils.randomAlphabetic(5);
    }
}

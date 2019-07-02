package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VNextBOServicesPartsAndLaborBundleData {

    @JsonProperty("serviceName")
    private String serviceName;

    @JsonProperty("serviceType")
    private String serviceType;

    @JsonProperty("serviceDescription")
    private String serviceDescription;

    @JsonProperty("servicePriceType")
    private String servicePriceType;

    @JsonProperty("servicePrice")
    private String servicePrice;

    @JsonProperty("serviceLaborRate")
    private String serviceLaborRate;

    @JsonProperty("serviceDefaultLaborTime")
    private String serviceDefaultLaborTime;

    @JsonProperty("servicePartCategory")
    private String servicePartCategory;

    @JsonProperty("servicePartSubcategory")
    private String servicePartSubcategory;

    @JsonProperty("servicePartName")
    private String servicePartName;

    public List<String> getPartOptionsList() {
        return Arrays.asList(servicePartCategory, servicePartSubcategory, servicePartName);
    }

    public String getJsForAddOnSettings() {
        try {
            List<String> collect = Files
                    .lines(Paths.get("src/test/java/com/cyberiansoft/test/dataclasses/vNextBO/" +
                                    "JsForPartsAndLaborAddOns.txt"), StandardCharsets.UTF_8)
                    .collect(Collectors.toList());
            return StringUtils.substring(collect.get(0), collect.size() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.cyberiansoft.test.dataclasses.partservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MatrixServiceData {
    @JsonProperty("serviceName")
    private String serviceName;
    @JsonProperty("vehicle")
    private String vehicle;
    @JsonProperty("size")
    private String size;
    @JsonProperty("severity")
    private String severity;
    @JsonProperty("price")
    private String price;
    @JsonProperty("partServiceDataList")
    private List<PartServiceData> partServiceDataList;
}

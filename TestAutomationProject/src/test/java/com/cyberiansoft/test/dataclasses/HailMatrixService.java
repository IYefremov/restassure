package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HailMatrixService {

    @JsonProperty("hailMatrixServiceName")
    String hailMatrixServiceName;

    @JsonProperty("hailMatrixSize")
    String hailMatrixSize;

    @JsonProperty("hailMatrixSeverity")
    String hailMatrixSeverity;

    @JsonProperty("matrixAdditionalServices")
    List<ServiceData> matrixAdditionalServices;

    public String getHailMatrixServiceName() {
        return hailMatrixServiceName;
    }

    public String getHailMatrixSize() {
        return hailMatrixSize;
    }

    public String getHailMatrixSeverity() {
        return hailMatrixSeverity;
    }

    public List<ServiceData> getMatrixAdditionalServices() {
        return matrixAdditionalServices;
    }

}

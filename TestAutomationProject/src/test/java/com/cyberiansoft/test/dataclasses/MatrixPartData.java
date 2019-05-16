package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MatrixPartData {

    @JsonProperty("matrixPartName")
    String matrixPartName;

    @JsonProperty("partSize")
    String partSize;

    @JsonProperty("partSeverity")
    String partSeverity;

    @JsonProperty("laborService")
    LaborServiceData laborService;

    @JsonProperty("matrixAdditionalService")
    ServiceData matrixAdditionalService;

    @JsonProperty("matrixAdditionalServices")
    List<ServiceData> matrixAdditionalServices;

    public String getMatrixPartName() {
        return matrixPartName;
    }

    public String getPartSize() {
        return partSize;
    }

    public String getPartSeverity() {
        return partSeverity;
    }

    public List<ServiceData> getMatrixAdditionalServices() {
        return matrixAdditionalServices;
    }

    public ServiceData getMatrixAdditionalService() {
        return matrixAdditionalService;
    }

    public LaborServiceData getMatrixAdditionalLaborService() {
        return laborService;
    }
}

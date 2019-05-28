package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MatrixServiceData {

    @JsonProperty("matrixServiceName")
    String matrixServiceName;

    @JsonProperty("hailMatrixName")
    String hailMatrixName;

    @JsonProperty("hailMatrixServices")
    List<HailMatrixService> hailMatrixServices;

    @JsonProperty("hailMatrixService")
    HailMatrixService hailMatrixService;

    @JsonProperty("matrixPart")
    MatrixPartData matrixPart;

    @JsonProperty("matrixParts")
    List<MatrixPartData> matrixParts;

    public String getMatrixServiceName() {
        return matrixServiceName;
    }

    public String getHailMatrixName() {
        return hailMatrixName;
    }

    public List<HailMatrixService> getHailMatrixServices() {
        return hailMatrixServices;
    }

    public HailMatrixService getHailMatrixService() {
        return hailMatrixService;
    }

    public MatrixPartData getMatrixPartData() {
        return matrixPart;
    }

    public List<MatrixPartData> getMatrixPartsData() {
        return matrixParts;
    }
}

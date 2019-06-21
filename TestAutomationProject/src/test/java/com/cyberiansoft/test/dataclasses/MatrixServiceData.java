package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MatrixServiceData {

    @JsonProperty("matrixServiceName")
    String matrixServiceName;

    @JsonProperty("hailMatrixName")
    String hailMatrixName;

    @JsonProperty("hailMatrixes")
    List<String> hailMatrixes;

    @JsonProperty("vehiclePartsData")
    List<VehiclePartData> vehiclePartsData;

    @JsonProperty("vehiclePartData")
    VehiclePartData vehiclePartData;

}

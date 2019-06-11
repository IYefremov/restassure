package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServiceAdjustmentData {

    @JsonProperty("adjustmentData")
    private AdjustmentData adjustmentData;
    @JsonProperty("adjustmentsData")
    private List<AdjustmentData> adjustmentsData;
    @JsonProperty("adjustmentTotalPercentage")
    private String adjustmentTotalPercentage;
    @JsonProperty("adjustmentTotalAmaunt")
    private String adjustmentTotalAmaunt;
}

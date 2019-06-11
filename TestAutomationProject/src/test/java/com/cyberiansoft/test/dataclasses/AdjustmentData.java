package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdjustmentData {

    @JsonProperty("adjustmentName")
    private String adjustmentName;
    @JsonProperty("adjustmentrice")
    private String adjustmentPrice;
    @JsonProperty("adjustmentQuantity")
    private String adjustmentQuantity;
}

package com.cyberiansoft.test.vnext.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderInfoDto {
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("vin")
    private String vin;
}

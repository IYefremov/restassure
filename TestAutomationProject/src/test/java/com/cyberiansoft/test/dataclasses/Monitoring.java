package com.cyberiansoft.test.dataclasses;

import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Monitoring {
    @JsonProperty("location")
    private String location;
    @JsonProperty("repairOrderData")
    private RepairOrderDto repairOrderData;
    @JsonProperty("stockNumber")
    private String stockNumber;
    @JsonProperty("orderPhaseDto")
    private OrderPhaseDto orderPhaseDto;
    @JsonProperty("orderInfoDto")
    private OrderInfoDto orderInfoDto;
}

package com.cyberiansoft.test.vnext.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepairOrderDto {
    private String phaseName;
    private String vin;
    private String completePercentage;
}

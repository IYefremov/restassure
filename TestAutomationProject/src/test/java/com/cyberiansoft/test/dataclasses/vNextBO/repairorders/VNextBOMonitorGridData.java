package com.cyberiansoft.test.dataclasses.vNextBO.repairorders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOMonitorGridData {

    @JsonProperty("monitorData")
    private VNextBOMonitorData monitorData;
}

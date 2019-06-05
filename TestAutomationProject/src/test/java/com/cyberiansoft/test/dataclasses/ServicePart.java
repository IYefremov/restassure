package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicePart {

    @JsonProperty("partName")
    private String partName;

    @JsonProperty("isPredefined")
    private boolean isPredefined;
}

package com.cyberiansoft.test.vnext.restclient.monitorrolessettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleSettingsDTO {

    @JsonProperty("monitorCanAddService")
    private boolean monitorCanAddService;

    @JsonProperty("monitorCanEditService")
    private boolean monitorCanEditService;

    @JsonProperty("monitorCanRemoveService")
    private boolean monitorCanRemoveService;
}
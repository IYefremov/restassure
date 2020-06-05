package com.cyberiansoft.test.vnext.restclient.monitorrolessettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthorizationDTO {

    @JsonProperty("key")
    private String key;
}
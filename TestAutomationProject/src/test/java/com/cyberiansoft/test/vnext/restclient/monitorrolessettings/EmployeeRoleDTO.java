package com.cyberiansoft.test.vnext.restclient.monitorrolessettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRoleDTO {

    @JsonProperty("description")
    private String description;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}
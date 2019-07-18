package com.cyberiansoft.test.targetprocessintegration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LastExecutorDTO {

    @JsonProperty("ResourceType")
    private String resourceType;
    @JsonProperty("Kind")
    private String kind;
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Login")
    private String login;
}

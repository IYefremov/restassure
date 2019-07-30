package com.cyberiansoft.test.dataclasses.partservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartName {
    @JsonProperty("partNameList")
    private List<String> partNameList;
    @JsonProperty("isMultiSelect")
    private Boolean isMultiSelect;
}

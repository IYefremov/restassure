package com.cyberiansoft.test.vnext.restclient;

import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkOrdersListResponse {

    @JsonProperty("Result")
    private List<WorkOrderDTO> Result = null;
    @JsonProperty("ErrorCode")
    private Integer ErrorCode;
    @JsonProperty("ErrorDescription")
    private Object ErrorDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("Result")
    public List<WorkOrderDTO> getResult() {
        return Result;
    }

    @JsonProperty("Result")
    public void setResult(List<WorkOrderDTO> result) {
        this.Result = result;
    }

    @JsonProperty("ErrorCode")
    public Integer getErrorCode() {
        return ErrorCode;
    }

    @JsonProperty("ErrorCode")
    public void setErrorCode(Integer errorCode) {
        this.ErrorCode = errorCode;
    }

    @JsonProperty("ErrorDescription")
    public Object getErrorDescription() {
        return ErrorDescription;
    }

    @JsonProperty("ErrorDescription")
    public void setErrorDescription(Object errorDescription) {
        this.ErrorDescription = errorDescription;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}

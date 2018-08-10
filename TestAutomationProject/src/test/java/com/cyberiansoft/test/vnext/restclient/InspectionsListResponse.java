package com.cyberiansoft.test.vnext.restclient;

import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Result",
        "ErrorCode",
        "ErrorDescription"
})
public class InspectionsListResponse {

    @JsonProperty("Result")
    private List<InspectionDTO> Result = null;
    @JsonProperty("ErrorCode")
    private Integer ErrorCode;
    @JsonProperty("ErrorDescription")
    private Object ErrorDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Result")
    public List<InspectionDTO> getResult() {
        return Result;
    }

    @JsonProperty("Result")
    public void setResult(List<InspectionDTO> result) {
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

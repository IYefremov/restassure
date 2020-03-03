package com.cyberiansoft.test.vnext.restclient;

import com.cyberiansoft.test.dataclasses.r360.WorkOrderForInvoiceDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Result",
        "ErrorCode",
        "ErrorDescription"
})

public class WorkOrderForInvoiceListResponse {

    @JsonProperty("Result")
    private WorkOrderForInvoiceDTO Result;
    @JsonProperty("ErrorCode")
    private Integer ErrorCode;
    @JsonProperty("ErrorDescription")
    private Object ErrorDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("Result")
    public WorkOrderForInvoiceDTO getResult() {
        return Result;
    }

    @JsonProperty("Result")
    public void setResult(WorkOrderForInvoiceDTO result) {
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

}

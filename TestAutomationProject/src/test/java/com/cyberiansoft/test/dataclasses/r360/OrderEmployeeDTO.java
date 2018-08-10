package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "OrderId",
        "EmployeeId",
        "Amount"
})
public class OrderEmployeeDTO {

    @JsonProperty("OrderId")
    private String OrderId;
    @JsonProperty("EmployeeId")
    private String EmployeeId;
    @JsonProperty("Amount")
    private Integer Amount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("OrderId")
    public String getOrderId() {
        return OrderId;
    }

    @JsonProperty("OrderId")
    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    @JsonProperty("EmployeeId")
    public String getEmployeeId() {
        return EmployeeId;
    }

    @JsonProperty("EmployeeId")
    public void setEmployeeId(String employeeId) {
        this.EmployeeId = employeeId;
    }

    @JsonProperty("Amount")
    public Integer getAmount() {
        return Amount;
    }

    @JsonProperty("Amount")
    public void setAmount(Integer amount) {
        this.Amount = amount;
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

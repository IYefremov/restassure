package com.cyberiansoft.test.dataclasses.r360;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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

}

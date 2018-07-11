package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Invoice {

    @JsonProperty("workOrderData")
    WorkOrderData workOrderData;

    @JsonProperty("workOrdersData")
    List<WorkOrderData> workOrdersData;

    @JsonProperty("invoiceData")
    InvoiceData invoiceData;

    @JsonProperty("creditCardData")
    CreditCardData creditCardData;

    @JsonProperty("newPoNumber")
    String newPoNumber;

    public WorkOrderData getWorkOrderData() { return workOrderData; }

    public List<WorkOrderData> getWorkOrdersData() { return workOrdersData; }

    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    public CreditCardData getCreditCardData() {
        return creditCardData;
    }

    public String getNewPONumber() {
        return newPoNumber;
    }
}

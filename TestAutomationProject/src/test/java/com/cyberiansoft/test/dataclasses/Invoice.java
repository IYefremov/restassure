package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Invoice {

    @JsonProperty("workOrderData")
    WorkOrderData workOrderData;

    @JsonProperty("invoiceData")
    InvoiceData invoiceData;

    @JsonProperty("сreditCardData")
    CreditCardData сreditCardData;

    public WorkOrderData getWorkOrderData() {
        return workOrderData;
    }

    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    public CreditCardData getСreditCardData() {
        return сreditCardData;
    }
}

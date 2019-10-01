package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
public class BOOperationsInvoiceData {

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("amountFrom")
    private String amountFrom;

    @JsonProperty("amountTo")
    private String amountTo;

    @JsonProperty("po")
    private String po;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("poNum")
    private String poNum;

    @JsonProperty("poNotes")
    private String poNotes;

    @JsonProperty("userMail")
    private String userMail;

    @JsonProperty("message")
    private String message;

    @JsonProperty("dateFrom")
    private String dateFrom;

    @JsonProperty("dateTo")
    private String dateTo;

    @JsonProperty("newStatus")
    private String newStatus;

    @JsonProperty("approvedStatus")
    private String approvedStatus;

    @JsonProperty("allStatus")
    private String allStatus;

    @JsonProperty("exportedStatus")
    private String exportedStatus;

    @JsonProperty("voidStatus")
    private String voidStatus;

    @JsonProperty("draftStatus")
    private String draftStatus;

    @JsonProperty("exportFailedStatus")
    private String exportFailedStatus;

    @JsonProperty("timeFrame")
    private String timeFrame;

    public String getEmail() {
        return "test" + RandomStringUtils.randomAlphanumeric(3) + "@domain.com";
    }
}

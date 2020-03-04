package com.cyberiansoft.test.enums.invoices;

import lombok.Getter;

@Getter
public enum InvoiceStatuses {

    ALL("All"),
    APPROVED("Approved"),
    DRAFT("Draft"),
    EXPORT_FAILED("Export Failed"),
    EXPORTED("Exported"),
    NEW("New"),
    VOID("Void");

    private String name;

    InvoiceStatuses(final String name) {
        this.name = name;
    }
}

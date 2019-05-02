package com.cyberiansoft.test.vnext.factories.invoicestypes;

public class InvoiceTypeData {

    private String invoiceTypeID;
    private boolean canBeFinalDraft;

    public InvoiceTypeData(InvoiceTypes inspectionType) {
        switch (inspectionType) {
            case O_KRAMAR:
                invoiceTypeID = "ed2d0ac2-560b-4604-b5e6-0c88afaab796";
                canBeFinalDraft= true;
                break;
            case O_KRAMAR2:
                invoiceTypeID = "ed2d0ac2-560b-4604-b5e6-0c88afaab796";
                canBeFinalDraft= false;
                break;
            case O_KRAMAR_AUTO:
                invoiceTypeID = "ed2d0ac2-560b-4604-b5e6-0c88afaab796";
                canBeFinalDraft= false;
                break;
        }
    }

    public String getInvoiceTypeID() { return invoiceTypeID; };

    public boolean isCanBeFinalDraft() { return canBeFinalDraft; };
}

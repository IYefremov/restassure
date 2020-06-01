package com.cyberiansoft.test.vnext.factories.invoicestypes;

public enum InvoiceTypes {

    O_KRAMAR("O_Kramar"),
    O_KRAMAR2("O_Kramar2"),
    O_KRAMAR_AUTO("O_Kramar_auto"),
    O_KRAMAR_INVOICE("O_Kramar_Invoice");

    private final String invoiceType;

    InvoiceTypes(final String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTypeName() {
        return invoiceType;
    }

    public InvoiceTypes getInvoiceType(){
        for(InvoiceTypes type : values()){
            if(type.getInvoiceTypeName().equals(invoiceType)){
                return type;
            }
        }

        throw new IllegalArgumentException(invoiceType + " is not a valid Invoice Type");
    }
}

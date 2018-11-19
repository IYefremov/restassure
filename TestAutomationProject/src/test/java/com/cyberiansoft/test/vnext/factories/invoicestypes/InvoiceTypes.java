package com.cyberiansoft.test.vnext.factories.invoicestypes;

public enum InvoiceTypes {

    O_KRAMAR("O_Kramar");

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

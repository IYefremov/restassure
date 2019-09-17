package com.cyberiansoft.test.ios10_client.types.invoicestypes;

public enum UATInvoiceTypes implements IInvoicesTypes {

    INVOICE_TEST_CUSTOM1_NEW("Invoice_test_custom1_new_AQA");

    private final String ivoiceType;

    UATInvoiceTypes(final String ivoiceType) {
        this.ivoiceType = ivoiceType;
    }

    public String getInvoiceTypeName() {
        return ivoiceType;
    }

    public UATInvoiceTypes getInvoiceType(){
        for(UATInvoiceTypes type : values()){
            if(type.getInvoiceTypeName().equals(ivoiceType)){
                return type;
            }
        }

        throw new IllegalArgumentException(ivoiceType + " is not a valid Invoice Type");
    }
}

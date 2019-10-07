package com.cyberiansoft.test.ios10_client.types.invoicestypes;

public enum InvoicesTypes implements IInvoicesTypes {

    DEFAULT_INVOICETYPE("Default"),
    CUSTOMER_APPROVALON_INVOICETYPE("Customer_approval_ON"),
    CUSTOMER_APPROVALOFF_INVOICETYPE("Customer_approval_OFF"),
    INVOICE_DEFAULT_TEMPLATE("Invoice_Default_Template"),
    INVOICE_CUSTOM1("Invoice_Custom1"),
    INVOICE_ALLPRO("Invoice_AllPro"),
    INVOICE_DW_HAIL("Invoice_DW_Hail"),
    INVOICE_AUTOWORKLISTNET("Invoice_AutoWorkListNet");

    private final String ivoiceType;

    InvoicesTypes(final String ivoiceType) {
        this.ivoiceType = ivoiceType;
    }

    public String getInvoiceTypeName() {
        return ivoiceType;
    }

    public InvoicesTypes getInvoiceType(){
        for(InvoicesTypes type : values()){
            if(type.getInvoiceTypeName().equals(ivoiceType)){
                return type;
            }
        }

        throw new IllegalArgumentException(ivoiceType + " is not a valid Invoice Type");
    }
}

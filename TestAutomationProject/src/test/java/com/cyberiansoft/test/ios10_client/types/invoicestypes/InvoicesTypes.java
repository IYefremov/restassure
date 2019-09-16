package com.cyberiansoft.test.ios10_client.types.invoicestypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;

public enum InvoicesTypes implements IInvoicesTypes {

    DEFAULT_INVOICETYPE("Default"),
    CUSTOMER_APPROVALON_INVOICETYPE("Customer_approval_ON"),
    CUSTOMER_APPROVALOFF_INVOICETYPE("Customer_approval_OFF"),
    INVOICE_DEFAULT_TEMPLATE("Invoice_Default_Template"),
    INVOICE_CUSTOM1("Invoice_Custom1"),
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

package com.cyberiansoft.test.ios10_client.types.invoicestypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;

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

    public <T extends IBaseWizardScreen>T getFirstVizardScreen() {
        UATInvoiceTypes type = getInvoiceType();
        switch (type) {
            case INVOICE_TEST_CUSTOM1_NEW:
                if (BaseTestCase.mobilePlatform.equals(MobilePlatform.IOS_HD))
                    return (T) new InvoiceInfoScreen();
                else
                    return (T) new RegularInvoiceInfoScreen();
        }
        return null;
    }
}

package com.cyberiansoft.test.ios10_client.types.invoicestypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;

public enum DentWizardInvoiceTypes implements IInvoicesTypes {

    NO_ORDER_TYPE("1 - ** No Order Type **"),
    AUCTION_NO_DISCOUNT_INVOICE("Auction - No Discount Invoice"),
    HAIL("Hail"),
    HAIL_NO_DISCOUNT_INVOICE("Hail No Discount Invoice"),
    DING_SHIELD("Ding Shield");

    private final String ivoiceType;

    DentWizardInvoiceTypes(final String ivoiceType) {
        this.ivoiceType = ivoiceType;
    }

    public String getInvoiceTypeName() {
        return ivoiceType;
    }

    public DentWizardInvoiceTypes getInvoiceType(){
        for(DentWizardInvoiceTypes type : values()){
            if(type.getInvoiceTypeName().equals(ivoiceType)){
                return type;
            }
        }

        throw new IllegalArgumentException(ivoiceType + " is not a valid DentWizardInvoiceTypes");
    }
}

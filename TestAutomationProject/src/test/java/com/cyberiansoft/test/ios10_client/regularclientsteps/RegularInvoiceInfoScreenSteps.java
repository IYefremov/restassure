package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;

public class RegularInvoiceInfoScreenSteps {

    public static void setInvoicePONumber(String poNumber) {
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(poNumber);
    }

    public static void saveInvoiceAsFinal() {
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
    }

    public static void saveInvoiceAsDraft() {
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsDraft();
    }

    public static String getInvoiceNumber() {
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        return  invoiceInfoScreen.getInvoiceNumber();
    }
}

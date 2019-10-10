package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;

public class InvoiceInfoScreenSteps {

    public static void setInvoicePONumber(String poNumber) {
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(poNumber);
    }

    public static String getInvoiceNumber() {
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        return  invoiceInfoScreen.getInvoiceNumber();
    }
}

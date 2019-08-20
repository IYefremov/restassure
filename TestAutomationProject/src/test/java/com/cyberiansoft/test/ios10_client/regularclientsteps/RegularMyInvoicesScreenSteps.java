package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;

public class RegularMyInvoicesScreenSteps {

    public static void selectInvoice(String invoiceNumber) {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.selectInvoice(invoiceNumber);
    }

    public static void selectInvoiceForPayment(String invoiceNumber) {
        selectInvoice(invoiceNumber);
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.clickInvoicePayMenuItem();
    }
}

package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;

public class RegularMyInvoicesScreenSteps {

    public static void selectInvoice(String invoiceID) {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.waitInvoicesScreenLoaded();
        myInvoicesScreen.selectInvoice(invoiceID);
    }

    public static void selectInvoiceForPayment(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.PAY);
    }

    public static void selectSendEmailMenuForInvoice(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
    }
}

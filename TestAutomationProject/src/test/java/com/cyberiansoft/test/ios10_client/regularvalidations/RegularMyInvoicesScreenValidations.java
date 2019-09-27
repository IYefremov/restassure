package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import org.testng.Assert;

public class RegularMyInvoicesScreenValidations {

    public static void verifyInvoicePresent(String invoiceID, boolean isPresent) {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        if (isPresent)
            Assert.assertTrue(myInvoicesScreen.myInvoiceExists(invoiceID));
        else
            Assert.assertFalse(myInvoicesScreen.myInvoiceExists(invoiceID));
    }

    public static void verifyInvoicePONumber(String invoiceID, String expectedPO) {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        Assert.assertEquals(myInvoicesScreen.getInvoicePONumber(invoiceID), expectedPO);
    }

    public static void verifyInvoiceHasApproveIcon(String invoiceID, boolean isPresent) {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        if (isPresent)
            Assert.assertTrue(myInvoicesScreen.isInvoiceHasApproveIcon(invoiceID));
        else
            Assert.assertFalse(myInvoicesScreen.isInvoiceHasApproveIcon(invoiceID));
    }

    public static void verifyInvoicePrice(String invoiceID, String expectedPrice) {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        Assert.assertEquals(myInvoicesScreen.getInvoicePrice(invoiceID), expectedPrice);
    }
}

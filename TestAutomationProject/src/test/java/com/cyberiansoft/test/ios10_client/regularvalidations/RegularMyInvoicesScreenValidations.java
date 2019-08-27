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
}

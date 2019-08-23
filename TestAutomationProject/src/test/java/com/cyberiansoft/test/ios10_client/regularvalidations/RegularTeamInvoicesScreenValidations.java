package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamInvoicesScreen;
import org.testng.Assert;

public class RegularTeamInvoicesScreenValidations {

    public static void verifyTeamInspectionExists(String invoiceID, boolean exsists) {
        RegularTeamInvoicesScreen teamInvoicesScreen = new RegularTeamInvoicesScreen();
        if (exsists)
            Assert.assertTrue(teamInvoicesScreen.isInvoiceExists(invoiceID));
        else
            Assert.assertFalse(teamInvoicesScreen.isInvoiceExists(invoiceID));
    }
}

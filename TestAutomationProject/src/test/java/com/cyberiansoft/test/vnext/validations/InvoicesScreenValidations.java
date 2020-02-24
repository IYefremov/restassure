package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import org.testng.Assert;

public class InvoicesScreenValidations {

    public static void validateInvoiceHasPaymentIcon(String invoiceId, boolean hasIcon) {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        if (hasIcon)
            Assert.assertTrue(invoicesScreen.isInvoiceHasPaymentIcon(invoiceId));
        else
            Assert.assertFalse(invoicesScreen.isInvoiceHasPaymentIcon(invoiceId));
    }

    public static void validateInvoiceHasNotesIcon(String invoiceId, boolean hasIcon) {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        if (hasIcon)
            Assert.assertTrue(invoicesScreen.isInvoiceHasNotesIcon(invoiceId));
        else
            Assert.assertFalse(invoicesScreen.isInvoiceHasNotesIcon(invoiceId));
    }
}

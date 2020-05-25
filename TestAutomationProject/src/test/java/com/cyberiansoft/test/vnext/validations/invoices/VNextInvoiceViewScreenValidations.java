package com.cyberiansoft.test.vnext.validations.invoices;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.invoices.VNextInvoiceViewScreen;
import org.testng.Assert;

public class VNextInvoiceViewScreenValidations {

    public static void verifySignatureIsDisplayed() {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(invoiceViewScreen.getPageFrame());
        Assert.assertTrue(new VNextInvoiceViewScreen().getSignature().isDisplayed(), "Signature hasn't been displayed");
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    }
}

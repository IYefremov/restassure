package com.cyberiansoft.test.vnext.steps.invoices;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.invoices.VNextInvoiceViewScreen;

public class VNextInvoiceViewScreenSteps {

    public static void switchToPrintViewScreenFrame() {

        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(new VNextInvoiceViewScreen().getPageFrame());
    }

    public static void switchToDefaultContent() {

        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    }
}

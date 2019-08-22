package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import org.testng.Assert;

public class RegularInvoiceInfoScreenValidations {

    public static void verifyWorkOrderIsPresentForInvoice(String workOrderId, boolean isPresent) {
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.waitInvoiceInfoScreenLoaded();
        if (isPresent)
            Assert.assertTrue(invoiceInfoScreen.isWOSelected(workOrderId));
        else
            Assert.assertFalse(invoiceInfoScreen.isWOSelected(workOrderId));

    }

    public static void verifyInvoiceTotalValue(String expectedTotalValue) {
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), BackOfficeUtils.getFormattedServicePriceValue(expectedTotalValue));
    }
}

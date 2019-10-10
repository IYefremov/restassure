package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import org.testng.Assert;

public class InvoiceInfoScreenValidations {

    public static void verifyWorkOrderIsPresentForInvoice(String workOrderId, boolean isPresent) {
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.waitInvoiceInfoScreenLoaded();
        if (isPresent)
            Assert.assertTrue(invoiceInfoScreen.isWOSelected(workOrderId));
        else
            Assert.assertFalse(invoiceInfoScreen.isWOSelected(workOrderId));

    }

    public static void verifyInvoiceTotalValue(String expectedTotalValue) {
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), BackOfficeUtils.getFormattedServicePriceValue(expectedTotalValue));
    }

}

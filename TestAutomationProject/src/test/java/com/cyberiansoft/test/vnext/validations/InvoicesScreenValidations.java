package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.enums.InvoiceStatus;
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

    public static void validateInvoiceExists(String invoiceId, boolean isExists) {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        if (isExists)
            Assert.assertTrue(invoicesScreen.isInvoiceExists(invoiceId));
        else
            Assert.assertFalse(invoicesScreen.isInvoiceExists(invoiceId));
    }

    public static void validateInvoiceHasWorkOrder(String invoiceId, String workOrderId, boolean hasWorkOrder) {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        if (hasWorkOrder)
            Assert.assertTrue(invoicesScreen.getInvoiceWorkOrders(invoiceId).contains(workOrderId));
        else
            Assert.assertFalse(invoicesScreen.getInvoiceWorkOrders(invoiceId).contains(workOrderId));
    }

    public static void validateInvoiceStatus(String invoiceId, InvoiceStatus invoiceStatus) {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceId), invoiceStatus.getStatusString());
    }

    public static void validateInvoicePONumber(String invoiceId, String expectedPO) {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceId), expectedPO);
    }

    public static void validateInvoiceCustomer(String invoiceId, AppCustomer expectedCustomer) {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoiceCustomerValue(invoiceId), expectedCustomer.getFullName());
    }
}

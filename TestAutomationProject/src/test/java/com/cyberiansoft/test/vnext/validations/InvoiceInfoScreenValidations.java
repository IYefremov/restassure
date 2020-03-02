package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.interactions.VNextInvoiceInfoScreenInteractions;
import org.testng.Assert;

public class InvoiceInfoScreenValidations {

    public static void validateWorkOrderSelectedForInvoice(String workOrderId, boolean isSelected) {

        if (isSelected)
            Assert.assertTrue(VNextInvoiceInfoScreenInteractions.isWorkOrderSelectedForInvoice(workOrderId));
        else
            Assert.assertFalse(VNextInvoiceInfoScreenInteractions.isWorkOrderSelectedForInvoice(workOrderId));
    }

    public static void validateInvoicePONumber(String expectedPONumber) {
        Assert.assertEquals(VNextInvoiceInfoScreenInteractions.getInvoicePONumberValue(), expectedPONumber);
    }

    public static void validateInvoiceDate(String expectedDateValue) {
        Assert.assertEquals(VNextInvoiceInfoScreenInteractions.getInvoiceDateValue(), expectedDateValue);
    }

    public static void validateInvoiceTotal(String expectedTotalValue) {
        Assert.assertEquals(VNextInvoiceInfoScreenInteractions.getInvoiceTotalAmount(), expectedTotalValue);
    }
}

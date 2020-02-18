package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class InvoiceInfoSteps {

    public static void setInvoicePONumber(String poNumber) {
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        WaitUtils.click(invoiceInfoScreen.getInvoicePO());
        invoiceInfoScreen.getInvoicePO().clear();
        invoiceInfoScreen.getInvoicePO().sendKeys(poNumber);
        BaseUtils.waitABit(500);
    }
}

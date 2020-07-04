package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.invoices.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

public class VNextInvoiceInfoScreenInteractions {

    public static String getInvoicePONumberValue() {
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        return invoiceInfoScreen.getInvoicePO().getAttribute("value");
    }

    public static String getInvoiceDateValue() {
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        return invoiceInfoScreen.getInvoiceDate().getAttribute("value");
    }

    public static String getInvoiceTotalAmount() {
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        WaitUtils.waitUntilElementIsClickable(invoiceInfoScreen.getInvoiceTotalAmount());
        return invoiceInfoScreen.getInvoiceTotalAmount().getText().trim();
    }


    public static boolean isWorkOrderSelectedForInvoice(String workOrderId) {
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        WaitUtils.elementShouldBeVisible(invoiceInfoScreen.getRootElement(), true);
        return invoiceInfoScreen.getInvoiceInfoPanel().findElements(By.xpath(".//div[text()='" + workOrderId + "']")).size() > 0;
    }
}

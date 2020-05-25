package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextSelectWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.invoices.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InvoiceInfoSteps {

    public static void setInvoicePONumber(String poNumber) {
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        WaitUtils.click(invoiceInfoScreen.getInvoicePO());
        invoiceInfoScreen.getInvoicePO().clear();
        invoiceInfoScreen.getInvoicePO().sendKeys(poNumber);
        BaseUtils.waitABit(500);
    }

    public static void clickOnWorkOrder(String workOrderId) {
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        invoiceInfoScreen.clickOnWorkOrder(workOrderId);
    }

    public static void clickAddWorkOrder() {
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        invoiceInfoScreen.getAddOrderBtn().click();
    }

    public static void addWorkOrdersToInvoice(List<String> workOrders) {
        clickAddWorkOrder();
        VNextSelectWorkOrdersScreen selectWorkOrdersScreen = new VNextSelectWorkOrdersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrders.forEach(workOrderId -> {
            selectWorkOrdersScreen.selectWorkOrder(workOrderId);
        });
        selectWorkOrdersScreen.clickAddWorkOrders();
    }

    public static void addWorkOrderToInvoice(String workOrderId) {
        clickAddWorkOrder();
        VNextSelectWorkOrdersScreen selectWorkOrdersScreen = new VNextSelectWorkOrdersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        selectWorkOrdersScreen.selectWorkOrder(workOrderId);
        selectWorkOrdersScreen.clickAddWorkOrders();
    }

    public static void removeWorkOrdersFromInvoice(List<String> workOrders) {
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        workOrders.forEach(workOrderId -> {
            WebElement workOrderPanel = invoiceInfoScreen.getInvoiceWorkOrderPanel(workOrderId);
            workOrderPanel.findElement(By.xpath(".//*[@action='delete-order']")).click();
        });
    }

}

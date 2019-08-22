package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;

public class RegularMyWorkOrdersSteps {

    public static void clickAddWorkOrderButton() {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickAddOrderButton();
    }

    public static void waitMyWorkOrdersLoaded() {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.waitMyWorkOrdersScreenLoaded();
    }

    public static void clickCopyVehicleMenu(String workOrderId) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrder(workOrderId);
        myWorkOrdersScreen.clickrCopyVehicleMenu();
    }

    public static void clickCopyServicesMenu(String workOrderId) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrder(workOrderId);
        myWorkOrdersScreen.clickCopyServices();
    }

    public static void clickCreateInvoiceIconForWO(String workOrderId) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderId);
    }

    public static void clickCreateInvoiceIconAndSelectInvoiceType(IInvoicesTypes invoiceType) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickInvoiceIcon();
        myWorkOrdersScreen.selectInvoiceType(invoiceType);
    }
}

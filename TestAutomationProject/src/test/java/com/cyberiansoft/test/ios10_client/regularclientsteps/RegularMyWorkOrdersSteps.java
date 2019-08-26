package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
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
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.COPY_VEHICLE);
    }

    public static void clickCopyServicesMenu(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.COPY_SERVICES);
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

    public static void selectWorkOrderForApprove(String workOrderId) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrderForApprove(workOrderId);
    }

    public static void selectWorkOrder(String workOrderId) {
        waitMyWorkOrdersLoaded();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrder(workOrderId);
    }

    public static void selectSendEmailMenuForWorkOrder(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
    }
}

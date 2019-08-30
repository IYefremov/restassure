package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
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

    public static void selectWorkOrderForCopyServices(String workOrderId) {
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
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrder(workOrderId);
    }

    public static void selectSendEmailMenuForWorkOrder(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
    }

    public static void selectWorkOrderForCopyVehicle(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.COPY_VEHICLE);
    }

    public static void selectWorkOrderForEdit(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.EDIT);
    }

    public static void selectWorkOrderForNewInspection(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.NEW_INSPECTION);
    }

    public static void openWorkOrderDetails(String workOrderId) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.DETAILS);
    }

    public static void changeCustomerForWorkOrder(String workOrderId, AppCustomer customer) {
        selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_CUSTOMER);
        RegularCustomersScreenSteps.selectCustomer(customer);
        waitMyWorkOrdersLoaded();
    }
}

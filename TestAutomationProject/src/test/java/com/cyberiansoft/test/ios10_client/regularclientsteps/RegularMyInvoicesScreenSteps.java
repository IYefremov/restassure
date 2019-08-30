package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;

public class RegularMyInvoicesScreenSteps {

    public static void waitInvoicesScreenLoaded() {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.waitInvoicesScreenLoaded();
    }

    public static void selectInvoice(String invoiceID) {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        waitInvoicesScreenLoaded();
        myInvoicesScreen.selectInvoice(invoiceID);
    }

    public static void selectInvoiceForPayment(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.PAY);
    }

    public static void selectSendEmailMenuForInvoice(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
    }

    public static void selectInvoiceForChangePO(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_PO);
    }

    public static void selectInvoiceForApprove(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.APPROVE);
    }

    public static void selectInvoiceForEdit(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.EDIT);
    }

    public static void selectInvoiceNotesMenu(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.NOTES);
    }

    public static void changeCustomerForInvoice(String invoiceID, AppCustomer customer) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_CUSTOMER);
        RegularCustomersScreenSteps.selectCustomer(customer);
        waitInvoicesScreenLoaded();
    }
}

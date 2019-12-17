package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularPrintSelectorScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

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

    public static void selectVoidInvoiceMenu(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.VOID);
    }

    public static void selectInvoiceNotesMenu(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.NOTES);
    }

    public static void refreshPicturesForInvoice(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.REFRESH_PICTURES);
        waitInvoicesScreenLoaded();
    }

    public static void showPicturesForInvoice(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SHOW_PICTURES);
    }

    public static void startCreatingNewInspectionFromInvoice(String invoiceID, IInspectionsTypes inspectionsType) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.NEW_INSPECTION);
        RegularInspectionTypesSteps.selectInspectionType(inspectionsType);
    }

    public static void changeCustomerForInvoice(String invoiceID, AppCustomer customer) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_CUSTOMER);
        RegularCustomersScreenSteps.selectCustomer(customer);
        waitInvoicesScreenLoaded();
    }

    public static void voidInvoice(String invoiceID) {
        selectVoidInvoiceMenu(invoiceID);
        Helpers.getAlertTextAndAccept();
    }

    public static void switchToTeamView() {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.switchToTeamView();
    }

    public static void changeInvoicePONumber(String invoiceID, String newPO) {
        selectInvoiceForChangePO(invoiceID);
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.changePO(newPO);
    }

    public static void clickInvoiceApproveIcon(String invoiceID) {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.clickInvoiceApproveIcon(invoiceID);
    }

    public static void approveInvoice(String invoiceID) {
        clickInvoiceApproveIcon(invoiceID);
        RegularApproveInvoicesScreen approveInvoicesScreen = new RegularApproveInvoicesScreen();
        approveInvoicesScreen.selectInvoice(invoiceID);
        approveInvoicesScreen.clickAgreeApproveDisclimer();
        approveInvoicesScreen.clickApproveButton();
        approveInvoicesScreen.drawApprovalSignature();
    }

    public static void clickInvoicesSearchButton() {
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.clickInvoicesSearchButton();
    }

    public static void printInvoice(String invoiceID, String printServerName) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.PRINT);
        RegularPrintSelectorScreen printSelectorScreen = new RegularPrintSelectorScreen();
        printSelectorScreen.checkRemotePrintServerAndSelectPrintServer(printServerName);
        printSelectorScreen.clickPrintSelectorPrintButton();
        printSelectorScreen.clickPrintOptionsPrintButton();
    }

    public static void openInvoiceSummary(String invoiceID) {
        selectInvoice(invoiceID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SUMMARY);
    }
}

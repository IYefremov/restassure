package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.IServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularMyInspectionsSteps {

    public static void startCreatingInspection(AppCustomer appCustomer, IInspectionsTypes inspectionType) {
        waitMyInspectionsScreenLoaded();
        clickAddInspectionButton();
        RegularCustomersScreenSteps.selectCustomer(appCustomer);
        RegularInspectionTypesSteps.selectInspectionType(inspectionType);
    }

    public static void startCreatingInspection(IInspectionsTypes inspectionType) {
        waitMyInspectionsScreenLoaded();
        clickAddInspectionButton();
        RegularInspectionTypesSteps.selectInspectionType(inspectionType);
    }

    public static void clickAddInspectionButton() {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickAddInspectionButton();
    }

    public static void waitMyInspectionsScreenLoaded() {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
    }

    public static void selectInspectionForApprovaViaAction(String inspectionID) {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspectionID);
    }

    public static void createWorkOrderFromInspection(String inspectionID, IWorkOrdersTypes workordertype) {
        selectInspectionForCreatingWO(inspectionID);
        RegularWorkOrderTypesSteps.selectWorkOrderType(workordertype);
    }

    public static void selectInspectionForCreatingWO(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_WORKORDER);
    }

    public static void createServiceRequestFromInspection(String inspectionID, IServiceRequestTypes serviceRequestType) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_SERVICE_REQUEST);
        RegularServiceRequestTypesSteps.selectServiceRequestType(serviceRequestType);
    }

    public static void selectInspection(String inspectionID) {
        waitMyInspectionsScreenLoaded();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickOnInspection(inspectionID);
    }

    public static void selectSendEmailMenuForInspection(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
    }

    public static void selectInspectionNotesMenu(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.NOTES);
    }

    public static void selectInspectionForApprove(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.APPROVE);
    }

    public static void selectInspectionForArchive(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.ARCHIVE);
    }

    public static void selectInspectionForEdit(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.EDIT);
    }

    public static void selectInspectionForCopy(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.COPY);
    }

    public static void selectInspectionForAssign(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.ASSIGN);
    }

    public static void selectShowWOsMenuForInspection(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SHOW_WOS);
    }

    public static void switchToTeamView() {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.switchToTeamView();
    }

    public static void changeCustomerForInspection(String inspectionID, AppCustomer customer) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_CUSTOMER);
        RegularCustomersScreenSteps.selectCustomer(customer);
        waitMyInspectionsScreenLoaded();
    }

    public static void archiveInspection(String inspectionID) {
        selectInspectionForArchive(inspectionID);
        Helpers.getAlertTextAndAccept();
    }

    public static void openInspectionDetails(String inspectionID) {
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.DETAILS);
    }
}

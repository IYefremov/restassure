package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;

public class RegularMyInspectionsSteps {

    public static void startCreatingInspection(AppCustomer appCustomer, IInspectionsTypes inspectionType) {
        waitMyInspectionsScreenLoaded();
        clickAddInspectionButton();
        RegularCustomersScreenSteps.selectCustomer(appCustomer);
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
        selectInspection(inspectionID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_WORKORDER);
        RegularWorkOrderTypesSteps.selectWorkOrderType(workordertype);
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
}

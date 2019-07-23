package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;

public class RegularMyInspectionsSteps {

    public static void startCreatingInspection(AppCustomer appCustomer, IInspectionsTypes inspectionType) {
        waitMyInspectionsScreenLoaded();
        clickAddInspectionButton();
        RegularCustomersScreenSteps.selectCustomer(appCustomer);
        selectInspectionType(inspectionType);
    }

    public static void clickAddInspectionButton() {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickAddInspectionButton();
    }

    public static void waitMyInspectionsScreenLoaded() {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
    }

    public static void selectInspectionType(IInspectionsTypes inspectionType) {
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionType(inspectionType);
    }
}

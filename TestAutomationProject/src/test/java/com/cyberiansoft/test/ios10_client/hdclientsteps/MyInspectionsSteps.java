package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;

public class MyInspectionsSteps {

    public static void startCreatingInspection(AppCustomer appCustomer, IInspectionsTypes inspectionType) {
        waitMyInspectionsScreenLoaded();
        clickAddInspectionButton();
        CustomersScreenSteps.selectCustomer(appCustomer);
        InspectionTypesSteps.selectInspectionType(inspectionType);
    }

    public static void startCreatingInspection(IInspectionsTypes inspectionType) {
        waitMyInspectionsScreenLoaded();
        clickAddInspectionButton();
        InspectionTypesSteps.selectInspectionType(inspectionType);
    }

    public static void clickAddInspectionButton() {
        MyInspectionsScreen myInspectionsScreen = new MyInspectionsScreen();
        myInspectionsScreen.clickAddInspectionButton();
    }

    public static void waitMyInspectionsScreenLoaded() {
        MyInspectionsScreen myInspectionsScreen = new MyInspectionsScreen();
        myInspectionsScreen.waitInspectionsScreenLoaded();
    }
}

package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;

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

    public static void selectInspection(String inspectionID) {
        waitMyInspectionsScreenLoaded();
        MyInspectionsScreen myInspectionsScreen = new MyInspectionsScreen();
        myInspectionsScreen.selectInspectionInTable(inspectionID);
    }

    public static void createWorkOrderFromInspection(String inspectionID, IWorkOrdersTypes workordertype) {
        selectInspectionForCreatingWO(inspectionID);
        WorkOrderTypesSteps.selectWorkOrderType(workordertype);
    }

    public static void selectInspectionForCreatingWO(String inspectionID) {
        selectInspection(inspectionID);
        MenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_WORKORDER);
    }
}

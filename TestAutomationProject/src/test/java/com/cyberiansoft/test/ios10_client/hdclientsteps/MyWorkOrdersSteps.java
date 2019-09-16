package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;

public class MyWorkOrdersSteps {

    public static void startCreatingWorkOrderWithJob(IWorkOrdersTypes workOrdersType, String jobName) {
        startCreatingWorkOrder(workOrdersType);
        MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrderJob(jobName);
    }

    public static void startCreatingWorkOrder(AppCustomer appCustomer, IWorkOrdersTypes workOrdersType) {
        waitMyWorkOrdersLoaded();
        MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
        final String customerValue = myWorkOrdersScreen.getMyWorkOrdersSelectedCustomerValue();
        clickAddWorkOrderButton();
        if (customerValue.equals("Wholesale Mode") | customerValue.equals("Retail Mode"))
            CustomersScreenSteps.selectCustomer(appCustomer);
        WorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void startCreatingWorkOrder(IWorkOrdersTypes workOrdersType) {
        waitMyWorkOrdersLoaded();
        clickAddWorkOrderButton();
        WorkOrderTypesSteps.selectWorkOrderType(workOrdersType);
    }

    public static void clickAddWorkOrderButton() {
        MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
        myWorkOrdersScreen.clickAddOrderButton();
    }

    public static void waitMyWorkOrdersLoaded() {
        MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
        myWorkOrdersScreen.waitWorkOrdersScreenLoaded();
    }
}

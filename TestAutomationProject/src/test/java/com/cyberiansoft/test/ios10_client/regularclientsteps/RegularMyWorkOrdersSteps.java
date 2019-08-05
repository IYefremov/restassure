package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;

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
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrder(workOrderId);
        myWorkOrdersScreen.clickrCopyVehicleMenu();
    }

    public static void clickCopyServicesMenu(String workOrderId) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrder(workOrderId);
        myWorkOrdersScreen.clickCopyServices();
    }
}

package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;

public class RegularTeamWorkOrdersSteps {

    public static void waitTeamWorkOrdersScreenLoaded() {
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.waitTeamWorkOrdersScreenLoaded();
    }

    public static void selectSearchLocation(String location) {
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(location);
        teamWorkOrdersScreen.clickSearchSaveButton();
    }

    public static void openTeamWorkOrderMonitor(String workOrderId) {
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.MONITOR);
    }

    public static void clickCreateInvoiceIconForWO(String workOrderId) {
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderId);
    }

    public static void clickOpenMonitorForWO(String workOrderId) {
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.selectWorkOrder(workOrderId);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.MONITOR);
    }
}

package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;

public class RegularTeamWorkOrdersSteps {

    public static void selectSearchLocation(String location) {
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(location);
        teamWorkOrdersScreen.clickSearchSaveButton();
    }

    public static void openTeamWorkOrderMonitor(String workOrderID) {
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.selectWorkOrder(workOrderID);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.MONITOR);
    }
}

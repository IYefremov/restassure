package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class HomeScreenSteps {
    public static void openCreateNewInspection() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        HomeScreenSteps.openInspections();
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickAddInspectionButton();
    }

    public static void openInspections() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.elementShouldBeVisible(homeScreen.getRootElement(), true);
        homeScreen.clickInspectionsMenuItem();
    }

    public static void openWorkQueue() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.click(homeScreen.getWorkQueue());
    }

    public static void logOut() {
        VNextBaseScreen vNextBaseScreen = new VNextBaseScreen();
        WaitUtils.click(vNextBaseScreen.getLogoutButton());
    }
}

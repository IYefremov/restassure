package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class HomeScreenSteps {
    public static void openCreateMyInspection() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        HomeScreenSteps.openInspections();
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickAddInspectionButton();
    }

    public static void openCreateTeamInspection() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        HomeScreenSteps.openInspections();
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.clickAddInspectionButton();
    }

    public static void openInspections() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.elementShouldBeVisible(homeScreen.getStatuslist(), true);
        homeScreen.clickInspectionsMenuItem();
    }

    public static void openWorkOrders() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.elementShouldBeVisible(homeScreen.getStatuslist(), true);
        homeScreen.clickWorkOrdersMenuItem();
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

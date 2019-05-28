package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextHelpingScreen;

public class HomeScreenSteps {
    public static void openCreateNewInspection() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        HomeScreenSteps.openInspections();
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickAddInspectionButton();
    }

    public static void openInspections() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        homeScreen.clickInspectionsMenuItem();
    }

    public static void openMonitor() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextHelpingScreen vNextHelpingScreen = new VNextHelpingScreen();
        if(vNextHelpingScreen.getOkDismissButton().isDisplayed())
            vNextHelpingScreen.getOkDismissButton().click();
        homeScreen.clickMonitor();
    }
}

package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;

public class HomeScreenSteps {
    public static void openCreateNewInspection() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.clickAddInspectionButton();
    }
}

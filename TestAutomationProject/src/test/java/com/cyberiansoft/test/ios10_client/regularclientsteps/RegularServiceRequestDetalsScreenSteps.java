package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularServiceRequestDetailsScreen;

public class RegularServiceRequestDetalsScreenSteps {

    public static void clickServiceRequestSummaryInspectionsButton() {
        RegularServiceRequestDetailsScreen serviceRequestDetailsScreen = new RegularServiceRequestDetailsScreen();
        serviceRequestDetailsScreen.clickServiceRequestSummaryInspectionsButton();
    }

    public static void clickServiceRequestSummaryOrdersButton() {
        RegularServiceRequestDetailsScreen serviceRequestDetailsScreen = new RegularServiceRequestDetailsScreen();
        serviceRequestDetailsScreen.clickServiceRequestSummaryOrdersButton();
    }

    public static void clickServiceRequestSummaryInvoicesButton() {
        RegularServiceRequestDetailsScreen serviceRequestDetailsScreen = new RegularServiceRequestDetailsScreen();
        serviceRequestDetailsScreen.clickServiceRequestSummaryInvoicesButton();
    }

    public static void navigateBackToServiceRequestsScreen() {
        RegularNavigationSteps.navigateBackScreen();
        RegularServiceRequestSteps.waitServiceRequestScreenLoaded();
    }
}

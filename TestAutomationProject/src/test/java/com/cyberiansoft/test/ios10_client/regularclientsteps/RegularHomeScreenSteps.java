package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;

public class RegularHomeScreenSteps {

    public static void navigateToMyInspectionsScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickMyInspectionsButton();
    }

    public static void navigateToTeamInspectionsScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickTeamInspectionsButton();
    }

    public static void navigateToMyWorkOrdersScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickMyWorkOrdersButton();
    }

    public static void navigateToMyInvoicesScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickMyInvoicesButton();
    }

    public static void navigateToSettingsScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickSettingsButton();
    }

    public static void navigateToCustomersScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickCustomersButton();
    }

    public static void navigateToServiceRequestScreenScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickServiceRequestsButton();
    }

    public static void waitForHomeScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.waitHomeScreenLoaded();
    }
}

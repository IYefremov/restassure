package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;

public class RegularHomeScreenSteps {

    public static void navigateToMyInspectionsScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickMyInspectionsButton();
    }

    public static void navigateToTeamInspectionsScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickMyInspectionsButton();
        RegularMyInspectionsSteps.switchToTeamView();
    }

    public static void navigateToMyWorkOrdersScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickMyWorkOrdersButton();
    }

    public static void navigateToTeamWorkOrdersScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickMyWorkOrdersButton();
        RegularMyWorkOrdersSteps.switchToTeamView();
    }

    public static void navigateToMyInvoicesScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickMyInvoicesButton();
    }

    public static void navigateToTeamInvoicesScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickMyInvoicesButton();
        RegularMyInvoicesScreenSteps.switchToTeamView();
    }

    public static void navigateToSettingsScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickSettingsButton();
    }

    public static void navigateToCustomersScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickCustomersButton();
    }

    public static void navigateToServiceRequestScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickServiceRequestsButton();
    }

    public static void navigateToStatusScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickStatusButton();
    }

    public static void navigateTocarHistoryScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickCarHistoryButton();
    }

    public static void waitForHomeScreen() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.waitHomeScreenLoaded();
    }

    public static void updateMainDataBase() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.updateDatabase();
    }

    public static void logoutUser() {
        RegularHomeScreen homeScreen = new RegularHomeScreen();
        homeScreen.clickLogoutButton();
    }
}

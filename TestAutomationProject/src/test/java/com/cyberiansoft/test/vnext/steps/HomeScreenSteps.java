package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.interactions.GeneralWizardInteractions;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.JavascriptExecutor;

public class HomeScreenSteps {
    public static void openCreateMyInspection() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        HomeScreenSteps.openInspections();
        InspectionSteps.switchToMyInspections();
        if (!GeneralWizardInteractions.isSearchFilterEmpty()) {
            SearchSteps.openSearchMenu();
            SearchSteps.fillTextSearch("");
            SearchSteps.cancelSearch();
        }
        inspectionsScreen.clickAddInspectionButton();
    }

    public static void openCreateMyWorkOrder() {
        HomeScreenSteps.openWorkOrders();
        WorkOrderSteps.switchToMyWorkOrdersView();
        if (!GeneralWizardInteractions.isSearchFilterEmpty()) {
            SearchSteps.openSearchMenu();
            SearchSteps.fillTextSearch("");
            SearchSteps.cancelSearch();
        }
        WorkOrderSteps.clickAddWorkOrderButton();
    }

    public static void openCreateTeamInspection() {
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        HomeScreenSteps.openInspections();
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.clickAddInspectionButton();
    }

    public static void openCreateTeamWorkOrder() {
        HomeScreenSteps.openWorkOrders();
        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrderSteps.clickAddWorkOrderButton();
    }

    public static void openInspections() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.waitUntilElementIsClickable(homeScreen.getRootElement());
        WaitUtils.elementShouldBeVisible(homeScreen.getStatusList(), true);
        homeScreen.clickInspectionsMenuItem();
    }

    public static void openInvoices() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.waitUntilElementIsClickable(homeScreen.getRootElement());
        WaitUtils.elementShouldBeVisible(homeScreen.getStatusList(), true);
        homeScreen.clickInvoicesMenuItem();
    }

    public static void openWorkOrders() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.elementShouldBeVisible(homeScreen.getStatusList(), true);
        homeScreen.clickWorkOrdersMenuItem();
    }

    public static void openWorkQueue() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.click(homeScreen.getWorkQueue());
    }

    public static void openUpdateWork() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.click(homeScreen.getMonitorUpdateWork());
    }

    public static void logOut() {
        VNextBaseScreen vNextBaseScreen = new VNextBaseScreen();
        WaitUtils.click(vNextBaseScreen.getLogoutButton());
    }

    public static void openMonitor() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();

        JavascriptExecutor je = (JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        je.executeScript("arguments[0].scrollIntoView(true);", homeScreen.getMonitor());
        WaitUtils.click(homeScreen.getMonitor());
    }

    public static void openCustomers() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        homeScreen.clickCustomersMenuItem();
    }

    public static void openCreateMyInvoice(String workOrderId) {
        openInvoices();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        InvoiceSteps.switchToMyInvoicesView();
        invoicesScreen.clickAddInvoiceButton();
        WorkOrderSteps.createInvoiceFromWorkOrder(workOrderId);
    }

    public static void openStatus() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.waitUntilElementIsClickable(homeScreen.getRootElement());
        WaitUtils.elementShouldBeVisible(homeScreen.getStatusList(), true);
        homeScreen.clickStatusMenuItem();
    }
}

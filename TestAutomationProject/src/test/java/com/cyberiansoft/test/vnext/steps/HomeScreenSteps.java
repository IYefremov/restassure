package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.interactions.GeneralWizardInteractions;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.steps.commonobjects.dialogs.InformationDialogSteps;
import com.cyberiansoft.test.vnext.steps.invoices.InvoiceSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class HomeScreenSteps {
    public static void openCreateMyInspection() {
        openInspections();
        InspectionSteps.switchToMyInspections();
        if (!GeneralWizardInteractions.isSearchFilterEmpty()) {
            SearchSteps.openSearchMenu();
            SearchSteps.fillTextSearch("");
            SearchSteps.cancelSearch();
        }
        InspectionSteps.clickAddInspectionButton();
    }

    public static void openCreateMyWorkOrder() {
        openWorkOrders();
        WorkOrderSteps.switchToMyWorkOrdersView();
        if (!GeneralWizardInteractions.isSearchFilterEmpty()) {
            SearchSteps.openSearchMenu();
            SearchSteps.fillTextSearch("");
            SearchSteps.cancelSearch();
        }
        WorkOrderSteps.clickAddWorkOrderButton();
    }

    public static void openCreateTeamInspection() {
        openInspections();
        InspectionSteps.switchToTeamInspections();
        InspectionSteps.clickAddInspectionButton();
    }

    public static void openCreateTeamWorkOrder() {
        openWorkOrders();
        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrderSteps.clickAddWorkOrderButton();
    }

    public static void openInspections() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.waitUntilElementIsClickable(homeScreen.getRootElement());
        WaitUtils.elementShouldBeVisible(homeScreen.getStatusList(), true);
        homeScreen.clickInspectionsMenuItem();
    }

    public static void openSettings() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        homeScreen.clickSettingsMenuItem();
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
        ConditionWaiter.create(__ -> vNextBaseScreen.getLogoutButton().isDisplayed()).execute();
        ConditionWaiter.create(__ -> vNextBaseScreen.getLogoutButton().isEnabled()).execute();
        WaitUtils.click(vNextBaseScreen.getLogoutButton());
    }

    public static void clickMonitor() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();

        JavascriptExecutor je = (JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        je.executeScript("arguments[0].scrollIntoView(true);", homeScreen.getMonitor());
        WaitUtils.click(homeScreen.getMonitor());
        BaseUtils.waitABit(500);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
    }

    public static void clickServiceRequests() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();

        JavascriptExecutor je = (JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        je.executeScript("arguments[0].scrollIntoView(true);", homeScreen.getServiceRequests());
        WaitUtils.click(homeScreen.getServiceRequests());
        BaseUtils.waitABit(500);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
    }

    public static void openMonitor() {
        clickMonitor();
        VNextInformationDialog informationDialog = new VNextInformationDialog();
        if (informationDialog.isInformationDialogExists()) {
            informationDialog.clickInformationDialogOKButton();
            SearchSteps.openSearchMenu();
            SearchSteps.fillTextSearch("");
            SearchSteps.cancelSearch();
        }
    }

    public static void openServiceRequests() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.waitUntilElementIsClickable(homeScreen.getRootElement());
        clickServiceRequests();
    }

    public static void openCustomers() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        homeScreen.clickCustomersMenuItem();
    }

    public static void openCreateMyInvoice(String workOrderId) {
        openInvoices();
        InvoiceSteps.switchToMyInvoicesView();
        InvoiceSteps.clickAddInvoice();
        WorkOrderSteps.createInvoiceFromWorkOrder(workOrderId);
    }

    public static void openStatus() {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.waitUntilElementIsClickable(homeScreen.getRootElement());
        WaitUtils.elementShouldBeVisible(homeScreen.getStatusList(), true);
        homeScreen.clickStatusMenuItem();
    }

    public static void startInvoiceCreation() {

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        WaitUtils.waitUntilElementIsClickable(homeScreen.getAddBtn());
        WaitUtils.click(homeScreen.getAddBtn());
        WaitUtils.click(homeScreen.getNewInvoiceBtn());
        InformationDialogSteps.clickOkButton();
    }

    public static void reLoginWithAnotherUser(Employee user) {

        logOut();
        VNextLoginScreen loginScreen = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        loginScreen.userLogin(user.getEmployeeName(), user.getEmployeePassword());
    }
}

package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.WorkOrderListElement;
import org.openqa.selenium.By;
import org.testng.Assert;

public class WorkOrderSteps {

    public static String createSimpleWorkOrder(AppCustomer customer, WorkOrderTypes workOrderType, WorkOrderData workOrderData) {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.clickAddWorkOrderButton();
        CustomersScreenSteps.selectCustomer(customer);
        createWorkOrder(workOrderType);
        VehicleInfoScreenSteps.setVIN("7777777777777");
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        return saveWorkOrder();
    }

    public static void createWorkOrder(WorkOrderTypes workOrderType) {
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList();
        workOrderTypesList.selectWorkOrderType(workOrderType);
        WaitUtils.elementShouldBeVisible(new VNextVehicleInfoScreen().getRootElement(), true);
    }

    public static void createWorkOrder(WorkOrderTypes workOrderType, WorkOrderData workOrderData) {
        createWorkOrder(workOrderType);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
    }

    public static void createWorkOrder(AppCustomer appCustomer, WorkOrderTypes workOrderType, WorkOrderData workOrderData) {
        CustomersScreenSteps.selectCustomer(appCustomer);
        createWorkOrder(workOrderType, workOrderData);
    }

    public static String saveWorkOrder() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String workOrderNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveWorkOrderViaMenu();
        return workOrderNumber;
    }

    public static String saveWorkOrderAsDraft() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String workOrderNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveWorkOrderAsDraft();
        return workOrderNumber;
    }

    public static void trySaveWorkOrder() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> (baseWizardScreen.getNewInspectionNumber() != "" && baseWizardScreen.getNewInspectionNumber() != null));
        baseWizardScreen.clickWizardMenuSaveButton();
    }

    public static void workOrderShouldBePresent(String workOrderId) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        workOrdersScreen.switchToMyWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderId));
    }

    public static void deleteWorkOrder(String workOrderId) {
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.DELETE);
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationDialog.clickInformationDialogDeleteButton();
        WaitUtils.waitUntilElementInvisible(By.xpath("//div[contains(@class, 'checkbox-item-title') and text()='" + workOrderId + "']"));
    }

    public static void openMenu(String workOrderId) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        WaitUtils.getGeneralFluentWait().until((webdriver) -> workOrdersScreen.getWorkOrdersList().size() > 0);
        WaitUtils.waitUntilElementIsClickable(workOrdersScreen.getRootElement());
        WorkOrderListElement workOrder = workOrdersScreen.getWorkOrderElement(workOrderId);
        workOrder.openMenu();
    }

    public static void cancelWorkOrder() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog();
        informationDialog.clickInformationDialogYesButton();
    }

    public static void switchToMyWorkOrdersView() {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        workOrdersScreen.switchToMyWorkordersView();
    }

    public static void switchToTeamWorkOrdersView() {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        workOrdersScreen.switchToTeamWorkordersView();
    }

    public static void clickAddWorkOrderButton() {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        workOrdersScreen.clickAddWorkOrderButton();
    }

    public static void changeCustomer(String workOrderNumber, AppCustomer newCustomer) {
        openMenu(workOrderNumber);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_CUSTOMER);
        CustomersScreenSteps.selectCustomer(newCustomer);
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationDialog.clickInformationDialogYesButton();
    }
}

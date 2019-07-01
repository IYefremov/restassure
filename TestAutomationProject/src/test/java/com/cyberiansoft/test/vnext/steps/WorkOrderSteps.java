package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.List;

public class WorkOrderSteps {

    public static String createSimpleWorkOrder(AppCustomer customer, WorkOrderTypes workOrderType) {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.clickAddWorkOrderButton();
        CustomersSreenSteps.selectCustomer(customer);
        createWorkOrder(workOrderType);
        VehicleInfoScreenSteps.setVIN("7777777777777");
        return saveWorkOrder();
    }

    public static void createWorkOrder(WorkOrderTypes workOrderType) {
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList();
        workOrderTypesList.selectWorkOrderType(workOrderType);
        WaitUtils.elementShouldBeVisible(new VNextVehicleInfoScreen().getRootElement(), true);
    }

    public static String saveWorkOrder() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String workOrderNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveWorkOrderViaMenu();
        return workOrderNumber;
    }

    public static void workOrderShouldBePresent(String workOrderId) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        workOrdersScreen.switchToMyWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderId));
    }

    public static void deleteWorkOrder(String workOrderId) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderId);
        workOrdersMenuScreen.clickDeleteWorkOrderMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogDeleteButton();
        WaitUtils.waitUntilElementInvisible(By.xpath("//div[contains(@class, 'checkbox-item-title') and text()='" + workOrderId + "']"));
    }
}

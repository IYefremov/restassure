package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

import java.util.List;

public class WorkOrderSteps {

    public static String createSimpleWorkOrder(AppCustomer customer, WorkOrderTypes workOrderType) {
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();

        homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(customer);
        createWorkOrder(workOrderType);
        vehicleInfoScreen.setVIN("7777777777777");
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.saveWorkOrderViaMenu();
        return workOrderNumber;
    }

    public static void createWorkOrder(WorkOrderTypes workOrderType) {
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList();
        WaitUtils.elementShouldBeVisible(workOrderTypesList.getTypeslist(), true);
        workOrderTypesList.selectWorkOrderType(workOrderType);
        WaitUtils.elementShouldBeVisible(new VNextVehicleInfoScreen().getRootElement(), true);
    }

    public static void openServiceScreen() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.changeScreen(ScreenType.SERVICES);
    }

    public static String saveWorkOrder() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String workOrderNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveWorkOrderViaMenu();
        return workOrderNumber;
    }

    public static void verifySelectedOrders(List<ServiceData> expectedServiceList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        servicesScreen.switchToSelectedServicesView();
        expectedServiceList.forEach(serviceData -> Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName())));
        servicesScreen.switchToAvalableServicesView();
    }

    public static void workOrderShouldBePresent(String workOrderId) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        workOrdersScreen.switchToMyWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderId));
    }

    public static void selectServices(List<ServiceData> serviceDataList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.selectServices(serviceDataList);
    }

    public static void unselectServices(List<ServiceData> serviceDataList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        servicesScreen.switchToSelectedServicesView();
        serviceDataList.forEach(service -> selectedServicesScreen.uselectService(service.getServiceName()));
        servicesScreen.switchToAvalableServicesView();
    }
}

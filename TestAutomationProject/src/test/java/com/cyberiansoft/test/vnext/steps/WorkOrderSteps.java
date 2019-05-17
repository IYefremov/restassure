package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.driverutils.DriverBuilder;
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
import org.testng.Assert;

import java.util.List;

public class WorkOrderSteps {

    public static String createSimpleWorkOrder(AppCustomer customer, WorkOrderTypes workOrderType) {
        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(customer);
        createWorkOrder(workOrderType);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN("7777777777777");
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.saveWorkOrderViaMenu();
        return workOrderNumber;
    }

    public static void createWorkOrder(WorkOrderTypes workOrderType) {
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(workOrderType);
    }

    public static void openServiceScreen() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen(DriverBuilder.getInstance().getAppiumDriver());
        baseWizardScreen.changeScreen(ScreenType.SERVICES);
    }

    public static String saveWorkOrder() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen(DriverBuilder.getInstance().getAppiumDriver());
        String workOrderNumber = baseWizardScreen.getNewInspectionNumber();
        VNextBaseWizardScreen.workOrderType = WorkOrderTypes.O_KRAMAR;
        baseWizardScreen.saveWorkOrderViaMenu();
        return workOrderNumber;
    }

    public static void verifySelectedOrders(List<ServiceData> expectedServiceList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        servicesScreen.switchToSelectedServicesView();
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        expectedServiceList.forEach(serviceData -> Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName())));
        servicesScreen.switchToAvalableServicesView();
    }

    public static void workOrderShouldBePresent(String workOrderId) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrdersScreen.switchToMyWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderId));
    }

    public static void selectServices(List<ServiceData> serviceDataList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        servicesScreen.selectServices(serviceDataList);
    }

    public static void unselectServices(List<ServiceData> serviceDataList) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        servicesScreen.switchToSelectedServicesView();
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        serviceDataList.forEach(service -> selectedServicesScreen.uselectService(service.getServiceName()));
        servicesScreen.switchToAvalableServicesView();
    }
}

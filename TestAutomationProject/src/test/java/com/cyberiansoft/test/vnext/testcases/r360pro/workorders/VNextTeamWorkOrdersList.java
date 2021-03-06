package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.invoices.InvoiceSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import com.cyberiansoft.test.vnext.validations.WorkOrdersScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamWorkOrdersList extends BaseTestClass {

    @BeforeClass(description = "Team Work Orders List Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWorkOrdersListTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCreatedWOWithTeamSharingDisplaysInTeamWOList(String rowID,
                                                                       String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        CustomersScreenSteps.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrder();

        WorkOrdersScreenValidations.validateWorkOrderExists(woNumber, true);
        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrdersScreenValidations.validateWorkOrderExists(woNumber, true);
        WorkOrderSteps.switchToMyWorkOrdersView();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSearchWOByCustomerName(String rowID,
                                                        String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        CustomersScreenSteps.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        ///HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getServiceData());
        vehicleInfoScreen.saveWorkOrderViaMenu();

        WorkOrdersScreenValidations.validateWorkOrderExists(woNumber, true);
        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrdersScreenValidations.validateWorkOrderExists(woNumber, true);
        SearchSteps.searchByText(testcustomer.getFullName());
        WorkOrdersScreenValidations.validateWorkOrderExists(woNumber, true);
        WorkOrderSteps.switchToMyWorkOrdersView();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamWO(String rowID,
                                            String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        CustomersScreenSteps.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        //HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getServiceData());
        vehicleInfoScreen.saveWorkOrderViaMenu();

        WorkOrdersScreenValidations.validateWorkOrderExists(woNumber, true);
        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrderSteps.openMenu(woNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        VehicleInfoScreenInteractions.selectMakeAndModel(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel());
        WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(woNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        VehicleInfoScreenValidations.validateVehicleInfo(workOrderData.getVehicleInfoData());

        WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.switchToMyWorkOrdersView();
        WorkOrderSteps.openMenu(woNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenValidations.validateVehicleInfo(workOrderData.getVehicleInfoData());
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditWOIfWeHaveNoInternetConnection(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
        final String inspectionId = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.searchInpectionByFreeText(inspectionId);
        InvoiceSteps.openMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.CREATE_WORK_ORDER);

        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR2);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.switchToMyWorkOrdersView();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderId, true);

        AppiumUtils.setNetworkOff();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenInteractions.selectMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(),
                inspectionData.getVehicleInfo().getVehicleModel());
        WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());

        WorkOrderSteps.cancelWorkOrder();
        ScreenNavigationSteps.pressBackButton();
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        Assert.assertEquals(Integer.valueOf(homeScreen.getQueueMessageValue()).intValue(), 1);
        AppiumUtils.setAndroidNetworkOn();
        homeScreen.waitUntilQueueMessageInvisible();
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAllChangesIsSavedAfterReconnectInternet(String rowID,
                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR2, inspectionData);

        final String inspectionId = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        inspectionsScreen.searchInpectionByFreeText(inspectionId);
        InvoiceSteps.openMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.CREATE_WORK_ORDER);

        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR2);
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.switchToMyWorkOrdersView();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderId, true);

        AppiumUtils.setNetworkOff();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenInteractions.selectMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(),
                inspectionData.getVehicleInfo().getVehicleModel());
        WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());
        WorkOrderSteps.cancelWorkOrder();
        ScreenNavigationSteps.pressBackButton();
        VNextHomeScreen homeScreen = new VNextHomeScreen();
        Assert.assertEquals(Integer.valueOf(homeScreen.getQueueMessageValue()).intValue(), 1);
        AppiumUtils.setAndroidNetworkOn();
        homeScreen.waitUntilQueueMessageInvisible();

        StatusScreenSteps.updateMainDB();
        HomeScreenSteps.openWorkOrders();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());
        WorkOrderSteps.cancelWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }
}

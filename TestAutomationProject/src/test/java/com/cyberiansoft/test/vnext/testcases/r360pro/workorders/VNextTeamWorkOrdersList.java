package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamWorkOrdersList extends BaseTestCaseTeamEditionRegistration {

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

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
        workOrdersScreen.switchToTeamWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
        workOrdersScreen.switchToMyWorkordersView();
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSearchWOByCustomerName(String rowID,
                                                        String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
        workOrdersScreen.switchToTeamWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
        workOrdersScreen.searchWorkOrderByFreeText(testcustomer.getFullName());
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
        workOrdersScreen.switchToMyWorkordersView();
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamWO(String rowID,
                                            String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
        workOrdersScreen.switchToTeamWorkordersView();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();

        VehicleInfoScreenInteractions.selectMakeAndModel(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel());
        vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();

        VehicleInfoScreenValidations.validateVehicleInfo(workOrderData.getVehicleInfoData());

        vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersScreen.switchToMyWorkordersView();
        workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        VehicleInfoScreenValidations.validateVehicleInfo(workOrderData.getVehicleInfoData());
        vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditWOIfWeHaveNoInternetConnection(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspNumber);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR2);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersScreen.switchToMyWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));

        AppiumUtils.setNetworkOff();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        VehicleInfoScreenInteractions.selectMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(),
                inspectionData.getVehicleInfo().getVehicleModel());
        vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());

        vehicleInfoScreen.cancelWorkOrder();
        workOrdersScreen.clickBackButton();
        Assert.assertEquals(Integer.valueOf(homeScreen.getQueueMessageValue()).intValue(), 1);
        AppiumUtils.setAndroidNetworkOn();
        homeScreen.waitUntilQueueMessageInvisible();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAllChangesIsSavedAfterReconnectInternet(String rowID,
                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspNumber);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR2);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersScreen.switchToMyWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));

        AppiumUtils.setNetworkOff();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        VehicleInfoScreenInteractions.selectMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(),
                inspectionData.getVehicleInfo().getVehicleModel());
        vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();

        VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.cancelWorkOrder();
        workOrdersScreen.clickBackButton();
        Assert.assertEquals(Integer.valueOf(homeScreen.getQueueMessageValue()).intValue(), 1);
        AppiumUtils.setAndroidNetworkOn();
        homeScreen.waitUntilQueueMessageInvisible();

        VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
        statusScreen.updateMainDB();
        //statusScreen.clickBackButton();
        homeScreen.clickWorkOrdersMenuItem();
        workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();

        VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.cancelWorkOrder();
        workOrdersScreen.clickBackButton();
    }
}

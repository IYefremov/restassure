package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.dataclasses.InspectionStatus;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.*;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.steps.WorkOrderSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamWorkOrdersTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description = "Team Work Orders Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWorkOrdersTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanDeleteWOIfAllowDeleteON(String rowID,
                                                         String description, JSONObject testData) {

        final String workOrderNumber = WorkOrderSteps.createSimpleWorkOrder(testcustomer, WorkOrderTypes.KRAMAR_AUTO);
        WorkOrderSteps.workOrderShouldBePresent(workOrderNumber);
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        workOrdersMenuScreen.clickDeleteWorkOrderMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogDontDeleteButton();
        workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderNumber));
        WorkOrderSteps.deleteWorkOrder(workOrderNumber);
        Assert.assertFalse(workOrdersScreen.isWorkOrderExists(workOrderNumber));
        VNextHomeScreen homeScreen = workOrdersScreen.clickBackButton();

        VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
        statusScreen.updateMainDB();
        workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        Assert.assertFalse(workOrdersScreen.isWorkOrderExists(workOrderNumber));
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantDeleteWOIfAllowDeleteOFF(String rowID,
                                                           String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextCustomersScreen customersScreen = homeScreen.clickNewWorkOrderPopupMenu();

        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO2);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.changeScreen(ScreenType.CLAIM);
        VNextWorkOrderClaimInfoScreen claimInfoScreen = new VNextWorkOrderClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.selectInsuranceCompany(workOrderData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.setClaimNumber(workOrderData.getInsuranceCompanyData().getClaimNumber());
        claimInfoScreen.setPolicyNumber(workOrderData.getInsuranceCompanyData().getPolicyNumber());
        VNextWorkOrdersScreen workOrdersScreen = claimInfoScreen.saveWorkOrderViaMenu();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderNumber));
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        Assert.assertFalse(workOrdersMenuScreen.isDeleteWorkOrderMenuButtonExists());
        workOrdersMenuScreen.clickCloseWorkOrdersMenuButton();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderNumber));
        workOrdersScreen.clickBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTeamWODisplaysInMyWOsListIfWOWasCreatedFromTeamInspection(String rowID,
                                                                                    String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        final String searchtext = "E-357-00295";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(searchtext);
        VNextInspectionsMenuScreen inspectionMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionsScreen.getFirstInspectionNumber());
        inspectionMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderNumber), "Can't find work order: " + workOrderNumber);

        workOrdersScreen.switchToTeamWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderNumber));
        workOrdersScreen.switchToMyWorkordersView();
        workOrdersScreen.clickBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateWOFromTeamInspection(String rowID,
                                                            String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();

        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

        VNextInspectionsMenuScreen inspectionMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectServices(workOrderData.getServicesList());
        VNextWorkOrdersScreen workOrdersScreen = availableServicesScreen.saveWorkOrderViaMenu();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderNumber));

        workOrdersScreen.switchToTeamWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderNumber));
        workOrdersScreen.switchToMyWorkordersView();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName()));
        availableServicesScreen.saveWorkOrderViaMenu();
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateWOOnlyFromApprovedTeamInspection(String rowID,
                                                                        String description, JSONObject testData) {

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();

        inspectionsScreen.switchToTeamInspectionsView();
        VNextInspectionsMenuScreen inspectionMenuScreen = inspectionsScreen.clickOnFirstInspectionWithStatus(InspectionStatus.NEW.getStatus());
        Assert.assertFalse(inspectionMenuScreen.isCreateWorkOrderMenuPresent());
        inspectionMenuScreen.clickCloseInspectionMenuButton();

        inspectionMenuScreen = inspectionsScreen.clickOnFirstInspectionWithStatus(InspectionStatus.APPROVED.getStatus());
        Assert.assertTrue(inspectionMenuScreen.isCreateWorkOrderMenuPresent());
        inspectionMenuScreen.clickCloseInspectionMenuButton();
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAllSelectedServicesFromInspectionDisplaysWhenUserCreateWO(String rowID,
                                                                                    String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.selectInsuranceCompany(workOrderData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectServices(workOrderData.getServicesList());
        availableServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

        VNextInspectionsMenuScreen inspectionMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionMenuScreen.clickApproveInspectionMenuItem();
        VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
        approveScreen.drawSignature();
        approveScreen.clickSaveButton();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        inspectionMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName()));
        VNextWorkOrdersScreen workOrdersScreen = selectedServicesScreen.saveWorkOrderViaMenu();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderNumber), "Can't find work order: " + workOrderNumber);
        workOrdersScreen.clickBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanDeleteWOIfThisWOWasCreatedFromTeamInspectionScreen(String rowID,
                                                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);

        VNextInspectionsMenuScreen inspectionMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionMenuScreen.clickApproveInspectionMenuItem();
        VNextApproveScreen approvescreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
        approvescreen.drawSignature();
        approvescreen.clickSaveButton();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.searchInpectionByFreeText(inspNumber);
        inspectionMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderNumber));
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.deleteWorkOrder(workOrderNumber);
        Assert.assertFalse(workOrdersScreen.isWorkOrderExists(workOrderNumber));

        workOrdersScreen.switchToTeamWorkordersView();
        Assert.assertFalse(workOrdersScreen.isWorkOrderExists(workOrderNumber));
        workOrdersScreen.switchToMyWorkordersView();
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyItIsNotPossibleToEditTeamWorkOrderWithDeviceOnFlyMode(String rowID,
                                                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextCustomersScreen customersScreen = homeScreen.clickNewWorkOrderPopupMenu();

        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        workOrdersScreen.switchToTeamWorkordersView();
        workOrdersScreen.searchWorkOrderByFreeText(workOrderNumber);
        BaseUtils.waitABit(10 * 1000);
        AppiumUtils.setNetworkOff();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        VNextInformationDialog informationDialog = workOrdersMenuScreen.clickEditWorkOrderMenuItemWithAlert();
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.CONNECTION_IS_NOT_AVAILABLE);
        AppiumUtils.setAndroidNetworkOn();
        workOrdersScreen.switchToMyWorkordersView();
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamWorkOrder(String rowID,
                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        final String newvinnumber = "19UUA66278A050105";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        workOrdersScreen.switchToTeamWorkordersView();

        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,newvinnumber);
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersScreen.clickBackButton();
        BaseUtils.waitABit(10*1000);
        homeScreen.clickWorkOrdersMenuItem();
        workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        VehicleInfoScreenValidations.dataFieldShouldHaveValue(VehicleDataField.VIN,newvinnumber);
        vehicleInfoScreen.cancelWorkOrder();
        workOrdersScreen.switchToMyWorkordersView();
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamWorkOrderAddAndRemoveServices(String rowID,
                                                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        final int amountToSelect = 3;
        final int defaultCountForMoneyService = 1;


        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        workOrdersScreen.switchToTeamWorkordersView();

        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        for (int i = 0; i < amountToSelect; i++)
            availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getMoneyServiceData().getServiceName()), amountToSelect);
        for (int i = 0; i < amountToSelect; i++)
            availableServicesScreen.selectService(workOrderData.getPercentageServiceData().getServiceName());
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getPercentageServiceData().getServiceName()), amountToSelect);
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getMoneyServiceData().getServiceName()), amountToSelect);
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getPercentageServiceData().getServiceName()), amountToSelect);

        selectedServicesScreen.saveWorkOrderViaMenu();
        workOrdersScreen.clickBackButton();
        BaseUtils.waitABit(10*1000);
        homeScreen.clickWorkOrdersMenuItem();
        workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getMoneyServiceData().getServiceName()), amountToSelect);
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getPercentageServiceData().getServiceName()), amountToSelect);
        selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getMoneyServiceData().getServiceName()), amountToSelect);
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getPercentageServiceData().getServiceName()), amountToSelect);
        selectedServicesScreen.uselectService(workOrderData.getPercentageServiceData().getServiceName());
        selectedServicesScreen.uselectService(workOrderData.getPercentageServiceData().getServiceName());
        selectedServicesScreen.uselectService(workOrderData.getMoneyServiceData().getServiceName());
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getPercentageServiceData().getServiceName()), defaultCountForMoneyService);
        selectedServicesScreen.switchToAvalableServicesView();
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getMoneyServiceData().getServiceName()), 2);
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getPercentageServiceData().getServiceName()), defaultCountForMoneyService);
        availableServicesScreen.selectService(workOrderData.getPercentageServiceData().getServiceName());
        availableServicesScreen.selectService(workOrderData.getPercentageServiceData().getServiceName());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());

        availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getMoneyServiceData().getServiceName()), amountToSelect);
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getPercentageServiceData().getServiceName()), amountToSelect);
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersScreen.switchToMyWorkordersView();
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyItIsPossibleToAddServiceByOpeningServiceDetailsScreenAndSave(String rowID,
                                                                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        workOrdersScreen.switchToTeamWorkordersView();

        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextServiceDetailsScreen serviceDetailsScreen = availableServicesScreen.openServiceDetailsScreen(workOrderData.getMoneyServiceData().getServiceName());
        serviceDetailsScreen.setServiceAmountValue(workOrderData.getMoneyServiceData().getServicePrice());
        serviceDetailsScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        serviceDetailsScreen = availableServicesScreen.openServiceDetailsScreen(workOrderData.getPercentageServiceData().getServiceName());
        serviceDetailsScreen.setServiceAmountValue(workOrderData.getPercentageServiceData().getServicePrice());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), workOrderData.getWorkOrderPrice());
        availableServicesScreen.saveWorkOrderViaMenu();
        BaseUtils.waitABit(10*1000);
        workOrdersScreen.clickBackButton();
        homeScreen.clickWorkOrdersMenuItem();
        Assert.assertEquals(workOrdersScreen.getWorkOrderPriceValue(workOrderNumber), workOrderData.getWorkOrderPrice(),
                "Price is not valid for work order: " + workOrderNumber);
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanUnselectServiceForCreatedWO(String rowID,
                                                             String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        final String amountTotalEdited = "$26.26";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_3_SERVICE_GROUPING);
        BaseUtils.waitABit(20000);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextGroupServicesScreen groupServicesScreen = new VNextGroupServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        for (DamageData damageData : workOrderData.getDamagesData()) {
            VNextAvailableGroupServicesList availableGroupServicesList = groupServicesScreen.openServiceGroup(damageData.getDamageGroupName());
            for (ServiceData serviceData : damageData.getMoneyServices()) {
                availableGroupServicesList.selectService(serviceData.getServiceName());
            }
            availableGroupServicesList.clickBackButton();
        }

        Assert.assertEquals(groupServicesScreen.getInspectionTotalPriceValue(), workOrderData.getWorkOrderPrice());
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(workOrderNumber);
        vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        groupServicesScreen = new VNextGroupServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedGroupServicesScreen selectedGroupServicesScreen = groupServicesScreen.switchToSelectedGroupServicesView();
        selectedGroupServicesScreen.uselectService(workOrderData.getDamagesData().get(0).getMoneyServices().get(0).getServiceName());
        selectedGroupServicesScreen.switchToGroupServicesScreen();
        Assert.assertEquals(groupServicesScreen.getInspectionTotalPriceValue(), amountTotalEdited);
        groupServicesScreen.switchToSelectedGroupServicesView();
        Assert.assertEquals(selectedGroupServicesScreen.getInspectionTotalPriceValue(), amountTotalEdited);
        selectedGroupServicesScreen.switchToGroupServicesScreen();
        workOrdersScreen = groupServicesScreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workOrdersScreen.getWorkOrderPriceValue(workOrderNumber), amountTotalEdited);
        workOrdersScreen.clickBackButton();
    }
}

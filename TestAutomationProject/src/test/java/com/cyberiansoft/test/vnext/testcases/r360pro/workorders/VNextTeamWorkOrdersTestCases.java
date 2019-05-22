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
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
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
import com.cyberiansoft.test.vnext.steps.WorkOrderSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamWorkOrdersTestCases extends BaseTestCaseTeamEditionRegistration {

	@BeforeClass(description="Team Work Orders Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWorkOrdersTestCasesDataPath();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanDeleteWOIfAllowDeleteON(String rowID,
																	   String description, JSONObject testData) {

		final String woNumber = WorkOrderSteps.createSimpleWorkOrder(testcustomer, WorkOrderTypes.KRAMAR_AUTO);
		WorkOrderSteps.workOrderShouldBePresent(woNumber);
		VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		workOrdersMenuScreen.clickDeleteWorkOrderMenuButton();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickInformationDialogDontDeleteButton();
		workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
		workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);

		workOrdersScreen = workOrdersMenuScreen.deleteWorkOrder();
		Assert.assertFalse(workOrdersScreen.isWorkOrderExists(woNumber));
		VNextHomeScreen homeScreen = workOrdersScreen.clickBackButton();

		VNextStatusScreen statusscreen = homeScreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homeScreen = statusscreen.clickBackButton();
		workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		Assert.assertFalse(workOrdersScreen.isWorkOrderExists(woNumber));
		workOrdersScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantDeleteWOIfAllowDeleteOFF(String rowID,
																	   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersScreen = homeScreen.clickNewWorkOrderPopupMenu();

		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO2);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.changeScreen(ScreenType.CLAIM);
		VNextWorkOrderClaimInfoScreen claimInfoScreen = new VNextWorkOrderClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		claimInfoScreen.selectInsuranceCompany(workOrderData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimInfoScreen.setClaimNumber(workOrderData.getInsuranceCompanyData().getClaimNumber());
		claimInfoScreen.setPolicyNumber(workOrderData.getInsuranceCompanyData().getPolicyNumber());
		VNextWorkOrdersScreen workOrdersScreen = claimInfoScreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		Assert.assertFalse(workOrdersMenuScreen.isDeleteWorkOrderMenuButtonExists());
		workOrdersMenuScreen.clickCloseWorkOrdersMenuButton();
		Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
		workOrdersScreen.clickBackButton();
		
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber), "Can't find work order: " + woNumber);
		
		workOrdersScreen.switchToTeamWorkordersView();
		Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickBackButton();
		
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateWOFromTeamInspection(String rowID,
															String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToMyInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR2);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String inspnumber = vehicleInfoScreen.getNewInspectionNumber();		
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		
		inspectionsScreen.switchToTeamInspectionsView();
		inspectionsScreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);;

		VNextInspectionsMenuScreen inspectionMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionMenuScreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
		vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		servicesscreen.selectServices(workOrderData.getServicesList());
		VNextWorkOrdersScreen workOrdersScreen = servicesscreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
		
		workOrdersScreen.switchToTeamWorkordersView();
		Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
		workOrdersScreen.switchToMyWorkordersView();
		VNextWorkOrdersMenuScreen womenuscreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		vehicleInfoScreen = womenuscreen.clickEditWorkOrderMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName()));
		servicesscreen.saveWorkOrderViaMenu();
		workOrdersScreen.clickBackButton();		
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyAllSelectedServicesFromInspectionDisplaysWhenUserCreateWO(String rowID,
															String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String inspnumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		claiminfoscreen.selectInsuranceCompany(workOrderData.getInsuranceCompanyData().getInsuranceCompanyName());
		claiminfoscreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspservicesscreen.selectServices(workOrderData.getServicesList());
		inspservicesscreen.saveInspectionViaMenu();
		inspectionsScreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);;

		VNextInspectionsMenuScreen inspectionMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.clickSaveButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.searchInpectionByFreeText(inspnumber);
		inspectionMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionMenuScreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
		vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName()));
		VNextWorkOrdersScreen workOrdersScreen = selectedServicesScreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber), "Can't find work order: " + woNumber);
		workOrdersScreen.clickBackButton();		
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanDeleteWOIfThisWOWasCreatedFromTeamInspectionScreen(String rowID,
																					String description, JSONObject testData) {
		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.saveInspectionViaMenu();
		inspectionsScreen.searchInpectionByFreeText(inspNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);;

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
		vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
		
		Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber));
		workOrdersScreen.switchToMyWorkordersView();
		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		workOrdersMenuScreen.clickDeleteWorkOrderMenuButton();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickInformationDialogDeleteButton();
		workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertFalse(workOrdersScreen.isWorkOrderExists(woNumber));
		
		workOrdersScreen.switchToTeamWorkordersView();
		Assert.assertFalse(workOrdersScreen.isWorkOrderExists(woNumber));
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickBackButton();		
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyItIsNotPossibleToEditTeamWorkOrderWithDeviceOnFlyMode(String rowID,
																					String description, JSONObject testData) {
		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersScreen = homeScreen.clickNewWorkOrderPopupMenu();

		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

		workOrdersScreen.switchToTeamWorkordersView();
		workOrdersScreen.searchWorkOrderByFreeText(woNumber);
		BaseUtils.waitABit(10*1000);
		AppiumUtils.setNetworkOff();
		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		VNextInformationDialog informationDialog = workOrdersMenuScreen.clickEditWorkOrderMenuItemWithAlert();
		Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
				VNextAlertMessages.CONNECTION_IS_NOT_AVAILABLE);
		AppiumUtils.setAndroidNetworkOn();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

		workOrdersScreen.switchToTeamWorkordersView();

		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		vehicleInfoScreen.setVIN(newvinnumber);
		workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
		workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		Assert.assertEquals(vehicleInfoScreen.getVINFieldValue(), newvinnumber);
		vehicleInfoScreen.cancelWorkOrder();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

		workOrdersScreen.switchToTeamWorkordersView();

		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < amountToSelect; i++)
			availableServicesScreen.selectService(workOrderData.getMoneyServiceName());
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getMoneyServiceName()), amountToSelect);
		for (int i = 0; i < amountToSelect; i++)
			availableServicesScreen.selectService(workOrderData.getPercentageServiceName());
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getPercentageServiceName()), amountToSelect);
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getMoneyServiceName()), amountToSelect);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getPercentageServiceName()), amountToSelect);

		selectedServicesScreen.saveWorkOrderViaMenu();
		workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getMoneyServiceName()), amountToSelect);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getPercentageServiceName()), amountToSelect);
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getMoneyServiceName()), amountToSelect);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getPercentageServiceName()), amountToSelect);
		selectedServicesScreen.uselectService(workOrderData.getPercentageServiceName());
		selectedServicesScreen.uselectService(workOrderData.getPercentageServiceName());
		selectedServicesScreen.uselectService(workOrderData.getMoneyServiceName());
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getPercentageServiceName()), defaultCountForMoneyService);
		selectedServicesScreen.switchToAvalableServicesView();
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getMoneyServiceName()), 2);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(workOrderData.getPercentageServiceName()), defaultCountForMoneyService);
		availableServicesScreen.selectService(workOrderData.getPercentageServiceName());
		availableServicesScreen.selectService(workOrderData.getPercentageServiceName());
		availableServicesScreen.selectService(workOrderData.getMoneyServiceName());

		availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getMoneyServiceName()), amountToSelect);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(workOrderData.getPercentageServiceName()), amountToSelect);
		workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

		workOrdersScreen.switchToTeamWorkordersView();

		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextServiceDetailsScreen serviceDetailsScreen = availableServicesScreen.openServiceDetailsScreen(workOrderData.getMoneyServiceName());
		serviceDetailsScreen.setServiceAmountValue(workOrderData.getMoneyServicePrice());
		serviceDetailsScreen.setServiceQuantityValue(workOrderData.getMoneyServiceQuantity());
		serviceDetailsScreen.clickServiceDetailsDoneButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		serviceDetailsScreen = availableServicesScreen.openServiceDetailsScreen(workOrderData.getPercentageServiceName());
		serviceDetailsScreen.setServiceAmountValue(workOrderData.getPercentageServicePrice());
		serviceDetailsScreen.clickServiceDetailsDoneButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), workOrderData.getWorkOrderPrice());
		availableServicesScreen.saveWorkOrderViaMenu();
		Assert.assertEquals(workOrdersScreen.getWorkOrderPriceValue(woNumber), workOrderData.getWorkOrderPrice(),
				"Price is not valid for work order: " + woNumber);
		workOrdersScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
		final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextGroupServicesScreen groupServicesScreen = new VNextGroupServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (DamageData damageData : workOrderData.getDamagesData()) {
			VNextAvailableGroupServicesList availableGroupServicesList = groupServicesScreen.openServiceGroup(damageData.getDamageGroupName());
			for (ServiceData serviceData : damageData.getMoneyServicesData()) {
				availableGroupServicesList.selectService(serviceData.getServiceName());
			}
			availableGroupServicesList.clickBackButton();
		}
		
		Assert.assertEquals(groupServicesScreen.getInspectionTotalPriceValue(), workOrderData.getWorkOrderPrice());
		workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
		vehicleInfoScreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		groupServicesScreen = new VNextGroupServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSelectedGroupServicesScreen selectedGroupServicesScreen = groupServicesScreen.switchToSelectedGroupServicesView();
		selectedGroupServicesScreen.uselectService(workOrderData.getDamagesData().get(0).getMoneyServicesData().get(0).getServiceName());
		selectedGroupServicesScreen.switchToGroupServicesScreen();
		Assert.assertEquals(groupServicesScreen.getInspectionTotalPriceValue(), amountTotalEdited);
		groupServicesScreen.switchToSelectedGroupServicesView();
		Assert.assertEquals(selectedGroupServicesScreen.getInspectionTotalPriceValue(), amountTotalEdited);
		selectedGroupServicesScreen.switchToGroupServicesScreen();
		workOrdersScreen = groupServicesScreen.saveWorkOrderViaMenu();
		Assert.assertEquals(workOrdersScreen.getWorkOrderPriceValue(woNumber), amountTotalEdited);
		workOrdersScreen.clickBackButton();


	}
}

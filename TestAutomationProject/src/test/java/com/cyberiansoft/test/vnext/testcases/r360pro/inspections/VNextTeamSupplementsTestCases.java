package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.InspectionStatuses;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamSupplementsTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description = "Team Supplements Test Cases")
	public void settingUp() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getSupplementsTestCasesDataPath();
	}
	
	@AfterClass()
	public void settingDown() {
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementIfAllowSupplementsSetToON(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToMyInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		vehicleInfoScreen = inspectionsMenuScreen.clickAddSupplementInspectionMenuItem();
		vehicleInfoScreen.setVIN(inspectionData.getNewVinNumber());
		vehicleInfoScreen.clickSaveInspectionMenuButton();
		BaseUtils.waitABit(2000);
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.NEW_SUPPLEMENT_WILL_NOT_BE_ADDED);
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateSupplementIfAllowSupplementsSetToOff(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		Assert.assertFalse(inspectionsMenuScreen.isAddSupplementInspectionMenuItemPresent());
		inspectionsMenuScreen.clickCloseInspectionMenuButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementWhenEditInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScree = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScree.selectService(inspectionData.getServiceNameByIndex(0));
		inspectionsScreen = availableServicesScree.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.waitVehicleInfoScreenLoaded();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScree = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScree.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(inspectionData.getServiceNameByIndex(0), inspectionData.getServicePriceByIndex(0));
		availableServicesScree = selectedServicesScreen.switchToAvalableServicesView();
		availableServicesScree.selectService(inspectionData.getServiceNameByIndex(1));

		selectedServicesScreen = availableServicesScree.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(inspectionData.getServiceNameByIndex(1), inspectionData.getServicePriceByIndex(1));
		selectedServicesScreen.setServiceQuantityValue(inspectionData.getServiceNameByIndex(1), inspectionData.getServiceQuantityByIndex(1));
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		Assert.assertTrue(inspectionsMenuScreen.isAddSupplementInspectionMenuItemPresent());
		inspectionsMenuScreen.clickCloseInspectionMenuButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementAfterApproveInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToMyInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.clickSaveButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
	
		inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		vehicleInfoScreen = inspectionsMenuScreen.clickAddSupplementInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScree = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScree.selectService(inspectionData.getServiceData().getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScree.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(inspectionData.getServiceData().getServiceName(), inspectionData.getServiceData().getServicePrice());
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();

		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), inspectionData.getServiceData().getServiceStatus().getStatus());
		inspectionsScreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifySupplementWillNOTCreatedIfUserDontChangePriceOrQuantity(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToMyInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScree = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScree.selectService(inspectionData.getServiceData().getServiceName());
		inspectionsScreen = availableServicesScree.saveInspectionViaMenu();
		inspectionsScreen.clickSearchButtonAndClear();
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		vehicleInfoScreen = inspectionsMenuScreen.clickAddSupplementInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScree = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScree.clickSaveInspectionMenuButton();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.NEW_SUPPLEMENT_WILL_NOT_BE_ADDED);
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		Assert.assertTrue(inspectionsMenuScreen.isAddSupplementInspectionMenuItemPresent());
		inspectionsMenuScreen.clickCloseInspectionMenuButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementsOnlyForApprovedOrNewInspection(String rowID,
            String description, JSONObject testData) {
    	final String declineReason = "Too expensive";

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();

		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);	
		VNextAvailableServicesScreen availableServicesScree = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScree.selectService(inspectionData.getServiceData().getServiceName());
		inspectionsScreen = availableServicesScree.saveInspectionViaMenu();
		
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveServicesScreen.clickApproveAllButton();
		approveServicesScreen.clickSaveButton();
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.clickSaveButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());		
		
		Assert.assertEquals(InspectionStatuses.APPROVED.getInspectionStatusValue(), inspectionsScreen.getInspectionStatusValue(inspectionNumber));	
		inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		vehicleInfoScreen = inspectionsMenuScreen.clickAddSupplementInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScree = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScree.selectService(service.getServiceName());
		inspectionsScreen = availableServicesScree.saveInspectionViaMenu();
		
		Assert.assertEquals(InspectionStatuses.NEW.getInspectionStatusValue(), inspectionsScreen.getInspectionStatusValue(inspectionNumber));	
		inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveServicesScreen.clickDeclineAllButton();
		approveServicesScreen.clickSaveButton();
		VNextDeclineReasonScreen declineReasonScree = new VNextDeclineReasonScreen(DriverBuilder.getInstance().getAppiumDriver());
		declineReasonScree.selectDeclineReason(declineReason);
		approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.clickSaveButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(InspectionStatuses.DECLINED.getInspectionStatusValue(), inspectionsScreen.getInspectionStatusValue(inspectionNumber));
		
		inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		Assert.assertFalse(inspectionsMenuScreen.isAddSupplementInspectionMenuItemPresent());
		inspectionsMenuScreen.clickCloseInspectionMenuButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}
}

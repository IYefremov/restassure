package com.cyberiansoft.test.vnext.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;

public class VNextTeamSupplementsTestCases extends BaseTestCaseTeamEditionRegistration {
	
	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-supplements-testcases-data.json";
	
	@BeforeClass(description = "Setting up new suite")
	public void settingUp() throws Exception {
		System.out.println("xxxxxxxxxxxxxxxxx");
		JSONDataProvider.dataFile = DATA_FILE;
		System.out.println("zzzzzzzzzzzzzzzzzzzz");
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementIfAllowSupplementsSetToON(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.setVIN(inspdata.getNewVinNumber());
		vehicleinfoscreen.clickSaveInspectionMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.NEW_SUPPLEMENT_WILL_NOT_BE_ADDED);
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateSupplementIfAllowSupplementsSetToOff(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertFalse(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementWhenEditInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.selectService(inspdata.getServiceNameByIndex(0));	
		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.setServiceAmountValue(inspdata.getServiceNameByIndex(0), inspdata.getServicePriceByIndex(0));
		VNextSelectServicesScreen selectedservicescreen = inpsctionservicesscreen.clickAddServicesButton();		
		selectedservicescreen.selectService(inspdata.getServiceNameByIndex(1));
		selectedservicescreen.clickSaveSelectedServicesButton();
		inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.setServiceAmountValue(inspdata.getServiceNameByIndex(1), inspdata.getServicePriceByIndex(1));
		inpsctionservicesscreen.setServiceQuantityValue(inspdata.getServiceNameByIndex(1), inspdata.getServiceQuantityByIndex(1));
		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertTrue(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementAfterApproveInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextApproveScreen approvescreen = inspmenu.clickApproveInspectionMenuItem();
		approvescreen.drawSignature();
		approvescreen.clickSaveButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
	
		inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inpsctionservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(inspdata.getServiceName());
		selectservicesscreen.clickSaveSelectedServicesButton();
		inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.setServiceAmountValue(inspdata.getServiceName(), inspdata.getServicePrice());
		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), inspdata.getServiceStatus());
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifySupplementWillNOTCreatedIfUserDontChangePriceOrQuantity(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.selectService(inspdata.getServiceName());	
		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.clickSaveInspectionMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.NEW_SUPPLEMENT_WILL_NOT_BE_ADDED);
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertTrue(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
}

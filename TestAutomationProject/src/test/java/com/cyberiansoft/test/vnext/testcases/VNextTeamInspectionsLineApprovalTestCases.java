package com.cyberiansoft.test.vnext.testcases;

import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.InspectionStatuses;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextApproveServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextDeclineReasonScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;

public class VNextTeamInspectionsLineApprovalTestCases extends BaseTestCaseTeamEditionRegistration {
	
	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-inspections-line-approval-data.json";
	
	@BeforeClass(description = "Team Inspections Line Approval Test Cases")
	public void settingUp() throws Exception {
		JSONDataProvider.dataFile = DATA_FILE;	
	}
	
	@AfterClass()
	public void settingDown() throws Exception {	
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveServiceForInspectionIfLineApprovalEqualsON(String rowID,
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
		
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		Assert.assertTrue(approveservicesscreen.isApproveAllButtonDisplayed());
		Assert.assertTrue(approveservicesscreen.isDeclineAllButtonDisplayed());
		Assert.assertTrue(approveservicesscreen.isSkipAllButtonDisplayed());
		for (ServiceData service : services) {
			Assert.assertTrue(approveservicesscreen.isServicePresentInTheList(service.getServiceName()));
			approveservicesscreen.clickApproveButtonForService(service.getServiceName());
			approveservicesscreen.clickDeclineButtonForService(service.getServiceName());
			approveservicesscreen.clickSkipButtonForService(service.getServiceName());
		}
		approveservicesscreen.clickScreenBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveDeclineAndSkipDifferentServicesInOneInspection(String rowID,
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
		
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickApproveButtonForService(services.get(0).getServiceName());
		approveservicesscreen.clickDeclineButtonForService(services.get(1).getServiceName());
		approveservicesscreen.clickSkipButtonForService(services.get(2).getServiceName());
		approveservicesscreen.clickSaveButton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
				
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
		Assert.assertEquals(inspectionscreen.getInspectionApprovedPriceValue(inspnumber), inspdata.getInspectionApprovedPrice());	
		
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanDeclineAllServiceIfLineApprovalEqualsON(String rowID,
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
		
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickDeclineAllButton();
		approveservicesscreen.clickSaveButton();
		VNextDeclineReasonScreen declinereasonsscreen = new VNextDeclineReasonScreen(appiumdriver);
		declinereasonsscreen.selectDeclineReason(inspdata.getDeclineReason());
		
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.DECLINED.getInspectionStatusValue());
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantApproveOrDeclineServicesIfLineApprovalEqualsOFF(String rowID,
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
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();		
		
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyStateChangedToNewIfUserEditInspection(String rowID,
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
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();		
		
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickApproveAllButton();
		approveservicesscreen.clickSaveButton();
		
		
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickEditInspectionMenuItem();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.NEW.getInspectionStatusValue());
		homescreen = inspectionscreen.clickBackButton();
	}

}

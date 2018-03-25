package com.cyberiansoft.test.vnext.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSONMainDProvider;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextRetailCustomer;

public class VNextTeamSupplementsTestCases extends BaseTestCaseTeamEditionRegistration {
	
	private static final String DATA_FILE = "src\\test\\resources\\test-retail-user.json";
	
	@BeforeClass(description = "Setting up new suite")
	public void settingUp() throws Exception {
		System.out.println("xxxxxxxxxxxxxxxxx");
		JSONDataProvider.dataFile = DATA_FILE;
		System.out.println("zzzzzzzzzzzzzzzzzzzz");
	}
	
	//@Test(testName= "Test Case 71996:Verify user can add supplement if Allow Supplements=ON", 
	//		description = "Verify user can add supplement if Allow Supplements=ON")
	public void testVerifyUserCanAddSupplementIfAllowSupplementsSetToON() {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Retail", "Automation");
		final String inspType = "O_Kramar";
		final String vinnumber = "TEST";
		final String newvinnumber = "TESTNEW";
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		

		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.setVIN(newvinnumber);
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	//@Test(testName= "Test Case 71997:Verify user can't create supplement if allow supplement=OFF", 
	//		description = "Verify user can't create supplement if allow supplement=OFF")
	public void testVerifyUserCantCreateSupplementIfAllowSupplementsSetToOff() {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Retail", "Automation");
		final String inspType = "O_Kramar2";
		final String vinnumber = "TEST";
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertFalse(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	//@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class,
	//		testName= "Test Case 72585:Verify user can't add supplement when edit Inspection", 
	//		description = "Verify user can't add supplement when edit Inspection")
	public void testVerifyUserCanAddSupplementWhenEditInspection(String rowID,
            String description, JSONObject testData) {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Retail", "Automation");
		/*final String inspType = "O_Kramar";
		final String vinnumber = "TEST";
		final String serviceName1 = "Battery Installation";
		final String serviceName2 = "Labor";*/
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(testData.get("inspType").toString());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testData.get("vinnumber").toString());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.selectService(testData.get("serviceName1").toString());	
		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.setServiceAmountValue(testData.get("serviceName1").toString(), "10");
		
		inpsctionservicesscreen.selectService(testData.get("serviceName2").toString());
		inpsctionservicesscreen.setServiceAmountValue(testData.get("serviceName1").toString(), "20");
		inpsctionservicesscreen.setServiceQuantityValue(testData.get("serviceName1").toString(), "2");
		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertTrue(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Factory (dataProvider="fetchData_JSON", dataProviderClass=JSONMainDProvider.class)
    public Object[] SolicitudEmpleo(JSONObject testData){
		System.out.println("===============" + testData.get("testUserFirstName").toString());
		System.out.println("===============" + testData.get("testUserLastName").toString());
		System.out.println("===============" + testData.get("testVIN").toString());
		System.out.println("===============" + testData.get("my"));
		return new Object[] {testData.get("testUserFirstName").toString(), testData.get("testUserLastName").toString()};
    }

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class,
			testName= "Test Case 72586:Verify user can add supplement after approve Inspection, "
					+ "Test Case 72803:Verify Inspectin status changed to New after ceation supplement", 
			description = "Verify user can add supplement after approve Inspection, "
					+ "Verify Inspectin status changed to New after ceation supplement")
	public void testVerifyUserCanAddSupplementAfterApproveInspection(String rowID,
            String description, JSONObject testData) {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Retail", "Automation");
		 
		System.out.println("++++++++++" + testData.get("inspType").toString());
		JSONObject service = (JSONObject) testData.get("service");
		System.out.println("++++++++++" + service.get("serviceName").toString());
		System.out.println("++++++++++" + service.get("servicePrice").toString());
		System.out.println("++++++++++" + service.get("my"));
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(testData.get("inspType").toString());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testData.get("vinnumber").toString());
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
		selectservicesscreen.selectService(service.get("serviceName").toString());
		selectservicesscreen.clickSaveSelectedServicesButton();
		inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.setServiceAmountValue(service.get("serviceName").toString(), service.get("servicePrice").toString());
		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), "New");
		homescreen = inspectionscreen.clickBackButton();
	}
}

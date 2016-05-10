package com.cyberiansoft.test.vnext.testcases;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;

public class vNextInspectionServicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String[] servicesselect = { "Detail", "Bent Wheel" };
	final String testcustomer = "111 111";
	
	@Test(testName= "Test Case 37006:vNext - Show selected services after inspection is saved", 
			description = "Show selected services after inspection is saved")
	public void testShowSelectedServicesAfterInspectionIsSaved() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionfromFirstScreen();
		inspservicesscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		inspectionsscreen = inspservicesscreen.clickBackButtonAndCancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37008:vNext - Show selected services for inspection when navigating from 'Services' screen", 
			description = "Show selected services for inspection when navigating from 'Services' screen")
	public void testShowSelectedServicesForInspectionWhenNavigatingFromServicesScreen() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.swipeScreenRight();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.swipeScreenRight();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		inspectionsscreen = inspservicesscreen.saveInspectionfromFirstScreen();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37011:vNext - Add one service to already selected services when inspection is edited", 
			description = "Add one service to already selected services when inspection is edited")
	public void testAddOneServiceToAlreadySelectedServicesWhenInspectionIsEdited() {
		
		final String thirdservicetoadd = "INV2";
		final String[] thirdservice =  (String[])ArrayUtils.addAll(servicesselect, thirdservicetoadd); 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionfromFirstScreen();
		
		inspservicesscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(thirdservicetoadd);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceAdded(thirdservicetoadd));
		inspectionsscreen = inspservicesscreen.saveInspectionfromFirstScreen();
		inspservicesscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		for (int i=0; i<thirdservice.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(thirdservice[i]));
		inspectionsscreen = inspservicesscreen.clickBackButtonAndCancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}

	@Test(testName= "Test Case 37012:vNext - Add several services to already selected services when inspection is edited", 
			description = "Add several services to already selected services when inspection is edited")
	public void testAddSeveralServicesToAlreadySelectedServicesWhenInspectionIsEdited() {
		
		final String[] secondpart = { "INV2", "Dye" };
		final String[] thirdservice =  (String[])ArrayUtils.addAll(servicesselect, secondpart); 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionfromFirstScreen();
		inspservicesscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(secondpart);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < thirdservice.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(thirdservice[i]));
		inspectionsscreen = inspservicesscreen.saveInspectionfromFirstScreen();
		inspservicesscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		for (int i=0; i<thirdservice.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(thirdservice[i]));
		inspectionsscreen = inspservicesscreen.clickBackButtonAndCancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 38567:vNext - Verify selected services are saved to BO", 
			description = "Verify selected services are saved to BO")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifySelectedServicesAreSavedToBO(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) { 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionfromFirstScreen();
		homescreen = inspectionsscreen.clickBackButton();
		initiateWebDriver();
		webdriver.get(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.searchInspectionByNumber(inspnum);
		inspectionspage.verifyServicesPresentForInspection(inspnum, servicesselect);
		webdriver.quit();
	}
	
	@Test(testName= "Test Case 37028:vNext - Verify letters I,O,Q are trimmed while manual entry, "
			+ "Test Case 37153:vNext - Verify letters less than 17-digit VIN is treated as valid while manual entry", 
			description = "Verify letters I,O,Q are trimmed while manual entry, "
					+ "Verify letters less than 17-digit VIN is treated as valid while manual entry")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyLettersIOQAreTrimmedWhileManualEntry() { 
	
		final String vinnumber = "AI0YQ56ONJ";
		final String vinnumberverify = "A0Y56NJ";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		VNextVehicleInfoScreen vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		vehicleinfoscreen.setVIN(vinnumber);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumberverify);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37086:vNext - Verify not allowed characters are trimmed while manual entry", 
			description = "Verify not allowed characters are trimmed while manual entry")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyNotAllowedCharactersAreTrimmedWhileManualEntry() { 
	
		final String vinnumber = "*90%$2~!$!`\":;\'<>?,./+=_-)(*&^#@\\|";
		final String vinnumberverify = "902";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		VNextVehicleInfoScreen vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		vehicleinfoscreen.setVIN(vinnumber);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumberverify);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37160:vNext - Verify letters are capitalized while manual entry", 
			description = "Verify letters are capitalized while manual entry")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyLettersAreCapitalizedWhileManualEntry() { 
	
		final String vinnumber = "abc458yhgd8bn";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		VNextVehicleInfoScreen vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		vehicleinfoscreen.setVIN(vinnumber);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumber.toUpperCase());
		vehicleinfoscreen.selectType("New");
		inspectionsscreen.swipeScreenLeft();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.MODEL_REQUIRED_MSG);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
}

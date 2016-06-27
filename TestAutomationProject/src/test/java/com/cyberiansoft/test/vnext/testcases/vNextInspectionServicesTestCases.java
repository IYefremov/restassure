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
import com.cyberiansoft.test.vnext.screens.VNextClaimInfoScreen;
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
	
	/*@Test(testName= "Test Case 37006:vNext - Show selected services after inspection is saved", 
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
		Assert.assertTrue(msg.contains(VNextAlertMessages.MODEL_REQUIRED_MSG));
		Assert.assertTrue(msg.contains(VNextAlertMessages.MAKE_REQUIRED_MSG));
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 39552:vNext - Inspections - Verify services are saved when 'Save Inspection' option was used from humburger menu", 
			description = "Verify services are saved when 'Save Inspection' option was used from humburger menu")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyServicesAreSavedWhenSaveInspectionOptionWasUsedFromHumburgerMenu() { 
	
		final String vinnumber = "1GCJC33G22E688420";
		
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
		inspservicesscreen.clickSaveInspectionMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.MODEL_REQUIRED_MSG));
		Assert.assertTrue(msg.contains(VNextAlertMessages.MAKE_REQUIRED_MSG));
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnum = vehicleinfoscreen.getNewInspectionNumber();
		inspectionsscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspservicesscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		inspectionsscreen = inspservicesscreen.clickBackButtonAndCancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
		
	}
	
	@Test(testName= "Test Case 39553:vNext - Inspections - Verify Vehicle/Claim Info are saved when 'Save Inspection' option was used from humburger menu", 
			description = "Verify Vehicle/Claim Info are saved when 'Save Inspection' option was used from humburger menu")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyVehicleClaimAreSavedWhenSaveInspectionOptionWasUsedFromHumburgerMenu() { 
	
		final String vinnumber = "1GCJC33G22E688420";
		final String vehiclemake = "Chevrolet";
		final String vehiclemodel = "Silverado 3500";
		final String vehicleyear = "2002";
		final String vehicletype = "New";
		final String vehiclemilage = "12345";
		final String vehiclelicplate = "Lic 123";
		final String stockno = "stock123";
		final String vehiclerono = "ro12345";
		
		final String insurCompany = "USG";
		final String policynumber = "#ins034-74000056";
		final String claimnumber = "claim09875";
		
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
		inspservicesscreen.clickSaveInspectionMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.MODEL_REQUIRED_MSG));
		Assert.assertTrue(msg.contains(VNextAlertMessages.MAKE_REQUIRED_MSG));
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnum = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.selectType(vehicletype);
		vehicleinfoscreen.setMilage(vehiclemilage);
		vehicleinfoscreen.setLicPlate(vehiclelicplate);
		vehicleinfoscreen.setStockNo(stockno);
		vehicleinfoscreen.setRoNo(vehiclerono);
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.setPolicyNumber(policynumber);
		claiminfoscreen.setClaimNumber(claimnumber);
		claiminfoscreen.selectInsuranceCompany(insurCompany);

		inspectionsscreen = claiminfoscreen.saveInspectionViaMenu();
		inspservicesscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumber);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), vehiclemake);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), vehiclemodel);
		Assert.assertEquals(vehicleinfoscreen.getYear(), vehicleyear);
		Assert.assertEquals(vehicleinfoscreen.getType(), vehicletype);
		Assert.assertEquals(vehicleinfoscreen.getMilage(), vehiclemilage);
		Assert.assertEquals(vehicleinfoscreen.getLicPlate(), vehiclelicplate);
		Assert.assertEquals(vehicleinfoscreen.getStockNo(), stockno);
		Assert.assertEquals(vehicleinfoscreen.getRoNo(), vehiclerono);
		vehicleinfoscreen.swipeScreenLeft();
		claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		Assert.assertEquals(claiminfoscreen.getInsuranceCompany(), insurCompany);
		Assert.assertEquals(claiminfoscreen.getPolicyNumber(), policynumber);
		Assert.assertEquals(claiminfoscreen.getClaimNumber(), claimnumber);
		claiminfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 39549:vNext - Inspections - Navigate to screen with required fields on 'Save Inspection' called from humburger menu (first step)", 
			description = "Navigate to screen with required fields on 'Save Inspection' called from humburger menu (first step)")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testNavigateToScreenWithRequiredFieldsOnSaveInspectionCalledFromHumburgerMenu_FirstStep() { 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.clickCancelInspectionMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.CANCEL_INSPECTION_ALERT);
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.clickSaveInspectionMenuButton();
		informationdlg = new VNextInformationDialog(appiumdriver);
		msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.MODEL_REQUIRED_MSG));
		Assert.assertTrue(msg.contains(VNextAlertMessages.MAKE_REQUIRED_MSG));
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 39550:vNext - Inspections - Exit 'Cancel inspection' state called from humburger menu (first step)", 
			description = "Exit 'Cancel inspection' state called from humburger menu (first step)")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testExitCancelInspectionStateCalledFromHumburgerMenu_FirstStep() { 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.clickCancelInspectionMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.CANCEL_INSPECTION_ALERT);
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.clickCancelInspectionMenuItem();
		informationdlg = new VNextInformationDialog(appiumdriver);
		inspservicesscreen.clickHardwareBackButton();
		inspservicesscreen.clickHardwareBackButton();
		inspservicesscreen.clickHardwareBackButton();
		informationdlg = new VNextInformationDialog(appiumdriver);
		msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.CANCEL_INSPECTION_ALERT);
		inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}*/
}

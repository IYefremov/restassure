package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServicePackagesWebPage;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class vNextInspectionServicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final private String[] servicesselect = { "Dent Repair", "Prior Damage" };
	final private RetailCustomer testcustomer = new RetailCustomer("Retail", "Automation");
	final private String testVIN = "1FMCU0DG4BK830800";
	
	@Test(testName= "Test Case 37006:vNext - Show selected services after inspection is saved", 
			description = "Show selected services after inspection is saved")
	public void testShowSelectedServicesAfterInspectionIsSaved() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(servicesselect);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37008:vNext - Show selected services for inspection when navigating from 'Services' screen", 
			description = "Show selected services for inspection when navigating from 'Services' screen")
	public void testShowSelectedServicesForInspectionWhenNavigatingFromServicesScreen() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(servicesselect);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.swipeScreenRight();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.swipeScreenRight();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37011:vNext - Add one service to already selected services when inspection is edited", 
			description = "Add one service to already selected services when inspection is edited")
	public void testAddOneServiceToAlreadySelectedServicesWhenInspectionIsEdited() {
		
		final String thirdservicetoadd = "Bumper Repair";
		final String[] thirdservice =  ArrayUtils.addAll(servicesselect, thirdservicetoadd);
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(servicesselect);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		final String inspnum = selectedServicesScreen.getNewInspectionNumber();
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspservicesscreen = inspservicesscreen.switchToAvalableServicesView();
		inspservicesscreen.selectService(thirdservicetoadd);
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

		Assert.assertTrue(selectedServicesScreen.isServiceSelected(thirdservicetoadd));
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : thirdservice)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(testName= "Test Case 37012:vNext - Add several services to already selected services when inspection is edited", 
			description = "Add several services to already selected services when inspection is edited")
	public void testAddSeveralServicesToAlreadySelectedServicesWhenInspectionIsEdited() {
		
		final String[] secondpart = { "Bumper Repair", "Other" };
		final String[] thirdservice =  ArrayUtils.addAll(servicesselect, secondpart);
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(servicesselect);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		final String inspnum = selectedServicesScreen.getNewInspectionNumber();
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspservicesscreen = inspservicesscreen.switchToAvalableServicesView();
		inspservicesscreen.selectServices(secondpart);
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

		for (String serviceName : thirdservice)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : thirdservice)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 38567:vNext - Verify selected services are saved to BO", 
			description = "Verify selected services are saved to BO")
	@Parameters({ "user.name", "user.psw" })
	public void testVerifySelectedServicesAreSavedToBO(String deviceuser, String devicepsw) {
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(servicesselect);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionsscreen.clickBackButton();
		BaseUtils.waitABit(30000);
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get("http://capi.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspnum);
		for (String serviceName : servicesselect)
			Assert.assertTrue(inspectionspage.isServicePresentForSelectedInspection(serviceName));
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
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(vinnumber);
		Assert.assertEquals(inspinfoscreen.getVINFieldValue(), vinnumberverify);
		inspectionsscreen = inspinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
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
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumberverify);
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
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
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumber.toUpperCase());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 39552:vNext - Inspections - Verify services are saved when 'Save Inspection' option was used from humburger menu", 
			description = "Verify services are saved when 'Save Inspection' option was used from humburger menu")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyServicesAreSavedWhenSaveInspectionOptionWasUsedFromHumburgerMenu() { 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		final String inspnum = vehicleinfoscreen.getNewInspectionNumber();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(servicesselect);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		selectedServicesScreen.saveInspectionViaMenu();

		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		
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
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
	
		final String inspnum = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.setMilage(vehiclemilage);
		vehicleinfoscreen.setLicPlate(vehiclelicplate);
		vehicleinfoscreen.setStockNo(stockno);
		vehicleinfoscreen.setRoNo(vehiclerono);
		vehicleinfoscreen.swipeScreenLeft();
		
		inspectionsscreen = vehicleinfoscreen.saveInspectionViaMenu();
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumber);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), vehiclemake);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), vehiclemodel);
		Assert.assertEquals(vehicleinfoscreen.getYear(), vehicleyear);
		Assert.assertEquals(vehicleinfoscreen.getType(), vehicletype);
		Assert.assertEquals(vehicleinfoscreen.getMilage(), vehiclemilage);
		Assert.assertEquals(vehicleinfoscreen.getLicPlate(), vehiclelicplate);
		Assert.assertEquals(vehicleinfoscreen.getStockNo(), stockno);
		Assert.assertEquals(vehicleinfoscreen.getRoNo(), vehiclerono);
		/*vehicleinfoscreen.swipeScreenLeft();
		claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		Assert.assertEquals(claiminfoscreen.getInsuranceCompany(), insurCompany);
		Assert.assertEquals(claiminfoscreen.getPolicyNumber(), policynumber);
		Assert.assertEquals(claiminfoscreen.getClaimNumber(), claimnumber);*/
		vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(testName= "Test Case 39550:vNext - Inspections - Exit 'Cancel inspection' state called from humburger menu (first step)", 
			description = "Exit 'Cancel inspection' state called from humburger menu (first step)")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testExitCancelInspectionStateCalledFromHumburgerMenu_FirstStep() { 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.clickCancelMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
		vehicleinfoscreen.setVIN(testVIN);
		vehicleinfoscreen.swipeScreenLeft();
		vehicleinfoscreen.clickCancelMenuItem();
		new VNextInformationDialog(appiumdriver);
		AppiumUtils.clickHardwareBackButton();
		AppiumUtils.clickHardwareBackButton();
		AppiumUtils.clickHardwareBackButton();
		informationdlg = new VNextInformationDialog(appiumdriver);
		msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
		vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 38576:vNext - Show all assigned to Service Package services as available ones", 
			description = "Show all assigned to Service Package services as available ones")
	@Parameters({ "user.name", "user.psw" })
	public void testShowAllAssignedToServicePackageServicesAsAvailableOnes(String deviceuser, String devicepsw) { 
	
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServicePackagesWebPage servicepckgspage = companypage.clickServicePackagesLink();
		String mainWindowHandle = webdriver.getWindowHandle();
		servicepckgspage.clickServicesLinkForServicePackage("All Services");
		List<WebElement> allservices = servicepckgspage.getAllServicePackageItems();
		List<String> allservicestxt = new ArrayList<>();
		for (WebElement lst : allservices)
			allservicestxt.add(lst.getText());
		servicepckgspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		List<WebElement> services = inspservicesscreen.getServicesListItems();
		List<String> servicestxt = new ArrayList<>();
		for (WebElement lst : services)
			servicestxt.add(inspservicesscreen.getServiceListItemName(lst));
		
		for (String srv : allservicestxt) {
			Assert.assertTrue(servicestxt.contains(srv));
		}
		AppiumUtils.clickHardwareBackButton();
		vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 38577:vNext -Verify default price for service is shown correctly", 
			description = "Verify default price for service is shown correctly")
	public void testVerifyDefaultPriceForServiceIsShownCorrectly() { 
		
		final String[] servicestoselect = { "Facility Fee", "Bumper Repair", "Tax", "Dent Repair" };
		final String[] servicesprices = { "20.000%", "$20.00", "25.000%", "$0.00" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (String servicesel : servicestoselect)
			inspservicesscreen.selectService(servicesel);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

		for (int i=0; i< servicestoselect.length; i++)
			Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(servicestoselect[i]), servicesprices[i]);

		selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 41561:vNext - Add the same service multiple times", 
			description = "Add the same service multiple times")
	public void testAddTheSameServiceMultipleTimes() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(servicesselect);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

		for (String serviceName : servicesselect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		inspservicesscreen = selectedServicesScreen.switchToAvalableServicesView();
		inspservicesscreen.selectServices(servicesselect);
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertEquals(selectedServicesScreen.getQuantityOfSelectedService(serviceName), 2);

		final String inspnum = selectedServicesScreen.getNewInspectionNumber();
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicesselect)
			Assert.assertEquals(selectedServicesScreen.getQuantityOfSelectedService(serviceName), 2);

		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 43516:vNext - Edit inspection services", 
			description = "Edit inspection services")
	public void testEditInspectionServices() {
		
		final String[] secondservices = { "Dent Repair", "Scratch" };
		final String[] secondservicesprices = { "20", "222" }; 
		final String[] thirdservices = { "Prior Damage", "Bumper Repair" };
		final String[] thirdservicesprices = { "10", "0" };
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		final String inspnumber = inspinfoscreen.getNewInspectionNumber();
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(secondservices);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (int i = 0; i <  secondservices.length; i++) {
			selectedServicesScreen.setServiceAmountValue(secondservices[i], secondservicesprices[i]);
		}
		Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), "$242.00");
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		selectedServicesScreen.uselectService(secondservices[0]);

		inspservicesscreen = selectedServicesScreen.switchToAvalableServicesView();
		inspservicesscreen.selectServices(thirdservices);
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		
		for (int i = 0; i <  secondservices.length; i++) {
			selectedServicesScreen.setServiceAmountValue(thirdservices[i], thirdservicesprices[i]);
		}

		selectedServicesScreen.setServiceAmountValue(thirdservices[0], "5");

		selectedServicesScreen.setServiceAmountValue(thirdservices[1], "84.55");
		selectedServicesScreen.setServiceQuantityValue(thirdservices[1], "9.15");
		
		Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), "$1000.63");
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37843:vNext - Verify Price Matrix added to service package is available to choose when add/edit inspection", 
			description = "Verify Price Matrix added to service package is available to choose when add/edit inspection")
	public void testVerifyPriceMatrixAddedToServicePackageIsAvailableToChooseWhenAddEditInspection() {
		
		final String matrixservice = "Hail Dent Repair";
		final String[] availablepricematrixes = { "Nationwide Insurance", "Progressive", "State Farm" };
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(availablepricematrixes[2]);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37844:vNext - Verify list of available Price Matrices is loaded when choosing Matrix Service", 
			description = "Verify list of available Price Matrices is loaded when choosing Matrix Service")
	public void testVerifyListOfAvailablePriceMatricesIsLoadedWhenChoosingMatrixService() {
		
		final String matrixservice = "Hail Dent Repair";
		final String[] availablepricematrixes = { "Nationwide Insurance", "Progressive", "State Farm" };
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		for (String pricematrix : availablepricematrixes)
			Assert.assertTrue(pricematrixesscreen.isPriceMatrixExistsInTheList(pricematrix));
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(availablepricematrixes[2]);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37845:vNext - Verify Price Matrix name is shown on 'Select Services' screen after selection,"
			+ "Test Case 43617:vNext - Show Matrix Service name on 'Price Matrices' screen", 
			description = "Verify Price Matrix name is shown on 'Select Services' screen after selection,"
					+ "Test Case 43617:vNext - Show Matrix Service name on 'Price Matrices' screen")
	public void testVerifyPriceMatrixNameIsShownOnSelectServicesScreenAfterSelection() {
		
		final String matrixservice = "Hail Dent Repair";
		final String[] availablepricematrixes = { "Nationwide Insurance", "Progressive", "State Farm" };
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(availablepricematrixes[2]);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 45902:vNext - Verify correct price is shown in Services list after editing service price (Percentage)", 
			description = "Verify correct price is shown in Services list after editing service price (Percentage)")
	public void testVerifyCorrectPriceIsShownInServicesListAfterEditingServicePricePercentage() {
		
		final String servicetoselect = "555";
		final String serviceprice = "0.015%";
		final String servicelastsymbol = "8";
		final String newserviceprice = "0.018%";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(servicetoselect);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(servicetoselect));
		Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(servicetoselect), serviceprice);
		selectedServicesScreen.setServiceAmountValue(servicetoselect, servicelastsymbol);
		Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(servicetoselect), newserviceprice);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 45903:vNext - Edit service price on Visuals screen (Percentage)", 
			description = "Edit service price on Visuals screen (Percentage)")
	public void testEditServicePriceOnVisualsScreenePercentage() {
		
		final String selectdamage = "Price Adjustment";
		final String servicepercentage = "Dent on Body Line";
		final String amount = "55";
		final String amountvalue = "55.000";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		inspinfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickOtherServiceOption();
		selectdamagesscreen.selectAllDamagesTab();
		VNextVisualServicesScreen visualservicesscreen = selectdamagesscreen.clickCustomDamageType(selectdamage);
		visualscreen = visualservicesscreen.selectCustomService(servicepercentage);
		visualscreen.clickCarImage();
		BaseUtils.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(amount);
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amountvalue);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		inspectionsscreen = visualscreen.saveInspectionViaMenu();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 45906:vNext - Verify correct Total is shown after editing Percentage service", 
			description = "Verify correct Total is shown after editing Percentage service")
	public void testVerifyCorrectTotalIsShownAfterEditingPercentageService() {
		
		final String moneyservice = "Bug";
		final String selectdamage = "Price Adjustment";
		final String servicepercentage = "Dent on Body Line";
		final String serviceprice = "$222.00";
		final String amount = "50";
		final String amountvalue = "50.000";
		final String inspprice = "$333.00";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(moneyservice);
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), serviceprice);
		inspservicesscreen.swipeScreensRight(3);
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickOtherServiceOption();
		selectdamagesscreen.selectAllDamagesTab();
		VNextVisualServicesScreen visualservicesscreen = selectdamagesscreen.clickCustomDamageType(selectdamage);
		visualscreen = visualservicesscreen.selectCustomService(servicepercentage);
		visualscreen.clickCarImage();
		BaseUtils.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(amount);
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amountvalue);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickDamageCancelEditingButton();
		Assert.assertEquals(visualscreen.getInspectionTotalPriceValue(), inspprice);
		inspectionsscreen = visualscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getFirstInspectionPrice(), inspprice);
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 45967:vNext - Verify correct price is shown in Total on Visuals screen after editing service price (Money)", 
			description = "Verify correct price is shown in Total on Visuals screen after editing service price (Money)")
	public void testVerifyCorrectPriceIsShownInTotalOnVisualsScreenAfterEditingServicePrice_Money() {
		
		final String selectdamage = "Miscellaneous";
		final String amount = "999999.99";
		final String inspprice = "$999999.99";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		inspinfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		visualscreen.clickDefaultDamageType(selectdamage);
		visualscreen.clickCarImage();
		BaseUtils.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(amount);
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amount);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickDamageCancelEditingButton();
		Assert.assertEquals(visualscreen.getInspectionTotalPriceValue(), inspprice);
		inspectionsscreen = visualscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getFirstInspectionPrice(), inspprice);
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 41562:vNext - Edit service price (Percentage)", 
			description = "Edit service price (Percentage)")
	public void testEditServicePricePercentage() {
		
		final String moneyservice = "Dent Repair";
		final String percentageservice = "Facility Fee";
		final String quantitylastdigit = "8";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(moneyservice);
		inspservicesscreen.selectService(percentageservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(percentageservice, quantitylastdigit);
		Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(percentageservice), "28.000%");
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 41563:vNext - Edit service price (Money)", 
			description = "Edit service price (Money)")
	public void testEditServicePriceMoney() {
		
		final String moneyservice = "Dent Repair";
		final String pricevalue = "3.20";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(moneyservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(moneyservice, pricevalue);
		Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(moneyservice), "$3.20");
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 41571:vNext - INSP - Delete service from Services screen", 
			description = "Delete service from Services screen")
	public void testDeleteServiceFromServicesScreen() {
		
		final String moneyservice1 = "Dent Repair";
		final String moneyservice2 = "Bumper Repair";
		final String percentageservice = "Facility Fee";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		String inspnumber = inspinfoscreen.getNewInspectionNumber();
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(moneyservice1);
		inspservicesscreen.selectService(moneyservice2);
		inspservicesscreen.selectService(percentageservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

		selectedServicesScreen.uselectService(moneyservice1);
		selectedServicesScreen.uselectService(percentageservice);
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(moneyservice2));
		Assert.assertFalse(selectedServicesScreen.isServiceSelected(moneyservice1));
		Assert.assertFalse(selectedServicesScreen.isServiceSelected(percentageservice));
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case:vNext Inspection total price should change when uselect some of the selected service on Services screen", 
			description = "Inspection total price should change when uselect some of the selected service on Services screen")
	public void testInspectionTotalPriceShouldChangeWhenUselectSomeOfTheSelectedServiceOnServicesScreen() { 
		
		final String[] servicestoselect = { "Bumper Repair", "Dent Repair" };
		final String[] servicesprices = { "10", "10" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (String serviceName : servicestoselect)
			inspservicesscreen.selectService(serviceName);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (int i=0; i< servicestoselect.length; i++)
			selectedServicesScreen.setServiceAmountValue(servicestoselect[i], servicesprices[i]);
		Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), "$20.00");

		for (String serviceName : servicestoselect)
			selectedServicesScreen.uselectService(serviceName);
		Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), "$10.00");
		selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case:vNext Services aren't became selected if user unselect them before clicking back button on Select Services screen", 
			description = "Services aren't became selected if user unselect them before clicking back button on Select Services screen")
	public void testServicesArentBecameSelectedIfUserUnselectThemBeforeClickingBackButtonOnServicesScreen() { 
		
		final String[] servicestoselect = { "Bumper Repair", "Dent Repair" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (String serviceName : servicestoselect)
			inspservicesscreen.selectService(serviceName);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : servicestoselect)
			selectedServicesScreen.uselectService(serviceName);

		for (String serviceName : servicestoselect)
			Assert.assertFalse(selectedServicesScreen.isServiceSelected(serviceName));
		selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case :vNext - Total is not set to 0 if user adds Matrix Additional service with negative percentage service",
			description = "Total is not set to 0 if user adds Matrix Additional service with negative percentage service")
	public void testTotalIsNotSetTo0IfUserAddsMatrixAdditionalServiceWithNegativePercentageService() {
		
		final String matrixservice = "Hail Repair";
		final String pricematrix = "State Farm" ;
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String additionalservicename = "Aluminum Upcharge";
		final String additionalservicenprice = "-25";
		
		final String inspectiontotalprice = "$93.75";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(pricematrix);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		//vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservicename);
		VNextServiceDetailsScreen serviceDetailsScreen = vehiclepartinfoscreen.openServiceDetailsScreen(additionalservicename);
		serviceDetailsScreen.setServiceAmountValue(additionalservicenprice);
		serviceDetailsScreen.clickServiceDetailsDoneButton();
		vehiclepartinfoscreen = new VNextVehiclePartInfoPage(appiumdriver);
		Assert.assertEquals(vehiclepartinfoscreen.getMatrixServiceTotalPriceValue(), inspectiontotalprice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), pricematrix);

		Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), inspectiontotalprice);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	/*@Test(testName= "Test Case:  Open and set service details on Select Services screen",
			description = " Open and set service details on Select Services screen")
	public void testOpenAndSetServiceDetailsOnSelectServicesScreen() {
		
		final String serviceName = "Bumper Repair";
		final String servicePrice = "5";
		final String serviceQuantity = "1.99";
		final String serviceTotal = "$9.95";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextServiceDetailsScreen servicedetailsscreen = selectservicesscreen.openServiceDetails(serviceName);
		servicedetailsscreen.setServiceAmountValue(servicePrice);
		servicedetailsscreen.setServiceQuantityValue(serviceQuantity);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		selectservicesscreen = new VNextSelectedServicesScreen(appiumdriver);
		
		selectservicesscreen.clickSaveSelectedServicesButton();		
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), serviceTotal);
		
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}*/
	
	//@Test(testName= "Test Case 47440:vNext - Verify services are saved correctly when saving inspection from Visual screen", 
	//		description = "Verify services are saved correctly when saving inspection from Visual screen")
	public void testVerifyServicesAreSavedCorrectlyWhenSavingInspectionFromVisualScreen() {
		
		final String[] selectdamages = { "Miscellaneous", "Dent Repair" };
		final String[] selectedservices = { "Prior Damage", "Dent Repair" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		claiminfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		for (int i = 0; i < selectdamages.length; i++) {
			visualscreen.clickAddServiceButton();
			visualscreen.clickDefaultDamageType(selectdamages[i]);
			if (i==0)
				visualscreen.clickCarImage();
			else
				visualscreen.clickCarImageSecondTime();
			BaseUtils.waitABit(1000);
		}
		visualscreen.swipeScreenLeft();
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceName : selectedservices) {
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
		}
		
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 68042:Verify sending >100 messages after reconnect Internet", 
			description = "Verify sending >100 messages after reconnect Internet")
	public void testVerifySendingMoreThen100MessagesAfterReconnectInternet() {
		
		final int fakeimagescount = 50;
		final String imagesummaryvalue = "+47";
		final String[] services = { "Bumper Repair", "Facility Fee", "Scratch" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		AppiumUtils.setNetworkOff();
		BaseUtils.waitABit(13000);		
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		final String inspnumber = inspinfoscreen.getNewInspectionNumber();
		VNextAvailableServicesScreen inspservicesscreen =inspinfoscreen.goToInspectionServicesScreen();
		for (String srv : services) {
			inspservicesscreen.selectService(srv);
		}
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String srv : services) {
			VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(srv);
			for (int i = 0; i < fakeimagescount; i++)
				notesscreen.addFakeImageNote();
			notesscreen.clickScreenBackButton();
			selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		}

		//inspservicesscreen.selectAllServices();

		selectedServicesScreen.saveInspectionViaMenu();
		inspservicesscreen.clickScreenBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.clickUpdateAppdata();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogStartSyncButton();
		BaseUtils.waitABit(10000);
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		AppiumUtils.setAndroidNetworkOn();
		statusscreen.clickUpdateAppdata();	
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogStartSyncButton();
		WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 1200);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='" +
				VNextAlertMessages.DATA_HAS_BEEN_DOWNLOADED_SECCESSFULY + "']")));
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		
		homescreen = statusscreen.clickBackButton();
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		Assert.assertTrue(inspectionsscreen.isInspectionExists(inspnumber));
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		inspservicesscreen =inspinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String srv : services) {
			selectedServicesScreen.isServiceSelected(srv);
			Assert.assertEquals(selectedServicesScreen.getSelectedServiceImageSummaryValue(srv), imagesummaryvalue);
		}
		inspservicesscreen.cancelInspection();
		inspservicesscreen.clickScreenBackButton();
	}
}

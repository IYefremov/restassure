package com.cyberiansoft.test.vnext.testcases;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServicePackagesWebPage;
import com.cyberiansoft.test.vnext.screens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectDamagesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextSettingsScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class vNextInspectionServicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String[] servicesselect = { "Dent Repair", "Double Panel" };
	final String testcustomer = "Retail Automation";
	final String testVIN = "1FMCU0DG4BK830800";
	
	@Test(testName= "Test Case 37006:vNext - Show selected services after inspection is saved", 
			description = "Show selected services after inspection is saved")
	public void testShowSelectedServicesAfterInspectionIsSaved() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
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
		
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.swipeScreenRight();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.swipeScreenRight();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37011:vNext - Add one service to already selected services when inspection is edited", 
			description = "Add one service to already selected services when inspection is edited")
	public void testAddOneServiceToAlreadySelectedServicesWhenInspectionIsEdited() {
		
		final String thirdservicetoadd = "Bumper Repair";
		final String[] thirdservice =  (String[])ArrayUtils.addAll(servicesselect, thirdservicetoadd); 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(thirdservicetoadd);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceSelected(thirdservicetoadd));
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i<thirdservice.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(thirdservice[i]));
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}

	@Test(testName= "Test Case 37012:vNext - Add several services to already selected services when inspection is edited", 
			description = "Add several services to already selected services when inspection is edited")
	public void testAddSeveralServicesToAlreadySelectedServicesWhenInspectionIsEdited() {
		
		final String[] secondpart = { "Bumper Repair", "Other" };
		final String[] thirdservice =  (String[])ArrayUtils.addAll(servicesselect, secondpart); 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(secondpart);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < thirdservice.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(thirdservice[i]));
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i<thirdservice.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(thirdservice[i]));
		inspectionsscreen = inspservicesscreen.cancelInspection();
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
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
		homescreen.waitABit(30000);
		initiateWebDriver();
		webdriver.get("http://capi.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspnum);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspectionspage.isServicePresentForSelectedInspection(servicesselect[i]));
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
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumberverify);
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
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
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumber.toUpperCase());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		inspservicesscreen.saveInspectionViaMenu();

		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		inspectionsscreen = inspservicesscreen.cancelInspection();
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
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.clickCancelInspectionMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
		vehicleinfoscreen.setVIN(testVIN);
		vehicleinfoscreen.swipeScreenLeft();
		vehicleinfoscreen.clickCancelInspectionMenuItem();
		informationdlg = new VNextInformationDialog(appiumdriver);
		vehicleinfoscreen.clickHardwareBackButton();
		vehicleinfoscreen.clickHardwareBackButton();
		vehicleinfoscreen.clickHardwareBackButton();
		informationdlg = new VNextInformationDialog(appiumdriver);
		msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
		vehicleinfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 38576:vNext - Show all assigned to Service Package services as available ones", 
			description = "Show all assigned to Service Package services as available ones")
	@Parameters({ "user.name", "user.psw" })
	public void testShowAllAssignedToServicePackageServicesAsAvailableOnes(String deviceuser, String devicepsw) { 
	
		initiateWebDriver();
		webdriver.get(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ServicePackagesWebPage servicepckgspage = companypage.clickServicePackagesLink();
		String mainWindowHandle = webdriver.getWindowHandle();
		servicepckgspage.clickServicesLinkForServicePackage("All Services");
		List<WebElement> allservices = servicepckgspage.getAllServicePackageItems();
		List<String> allservicestxt = new ArrayList<String>();
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicecreen = inspservicesscreen.clickAddServicesButton();
		List<WebElement> services = selectservicecreen.getServicesListItems();
		List<String> servicestxt = new ArrayList<String>();
		for (WebElement lst : services)
			servicestxt.add(selectservicecreen.getServiceListItemName(lst));
		
		for (String srv : allservicestxt) {
			Assert.assertTrue(servicestxt.contains(srv));
		}
		selectservicecreen.clickHardwareBackButton();
		vehicleinfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 38577:vNext -Verify default price for service is shown correctly", 
			description = "Verify default price for service is shown correctly")
	public void testVerifyDefaultPriceForServiceIsShownCorrectly() { 
		
		final String[] servicestoselect = { "Aluminum Upcharge", "Bumper Repair", "Double Panel", "R&I - Sunroof" };
		final String[] servicesprices = { "20.000%", "$20.00", "25.000%", "$0.00" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicecreen = inspservicesscreen.clickAddServicesButton();
		for (String servicesel : servicestoselect)
			selectservicecreen.selectService(servicesel);
		selectservicecreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i< servicestoselect.length; i++)
			Assert.assertEquals(inspservicesscreen.getSelectedServicePriceValue(servicestoselect[i]), servicesprices[i]);
		
		inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
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
		
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceSelected(servicesselect[i]));
		selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(servicesselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertEquals(inspservicesscreen.getQuantityOfSelectedService(servicesselect[i]), 2);

		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertEquals(inspservicesscreen.getQuantityOfSelectedService(servicesselect[i]), 2);

		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 43516:vNext - Edit inspection services", 
			description = "Edit inspection services")
	public void testEditInspectionServices() {
		
		final String[] secondservices = { "Bumper Repair", "PART - Hood" }; 
		final String[] secondservicesprices = { "20", "222" }; 
		final String[] thirdservices = { "Double Panel", "Large Size Dent" };
		final String[] thirdservicesprices = { "10", "0" };
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		final String inspnumber = inspinfoscreen.getNewInspectionNumber();
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(secondservices);
		selectservicesscreen.clickSaveSelectedServicesButton();	
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i = 0; i <  secondservices.length; i++) {
			inspservicesscreen.setServiceAmountValue(secondservices[i], secondservicesprices[i]);
		}
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), "$242.00");
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.uselectService(secondservices[0]);
		
		selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(thirdservices);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		for (int i = 0; i <  secondservices.length; i++) {
			inspservicesscreen.setServiceAmountValue(thirdservices[i], thirdservicesprices[i]);
		}
		
		inspservicesscreen.setServiceAmountValue(thirdservices[0], "5");
		
		inspservicesscreen.setServiceAmountValue(secondservices[1], "84.55");
		inspservicesscreen.setServiceQuantityValue(secondservices[1], "9.15");
		
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), "$812.31");
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
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
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(availablepricematrixes[2]);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		selectservicesscreen = vehiclepartsscreen.clickVehiclePartsBackButton();
		Assert.assertEquals(selectservicesscreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
		
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceSelected(matrixservice));
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
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
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		for (String pricematrix : availablepricematrixes)
			Assert.assertTrue(pricematrixesscreen.isPriceMatrixExistsInTheList(pricematrix));
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(availablepricematrixes[2]);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		selectservicesscreen = vehiclepartsscreen.clickVehiclePartsBackButton();
		Assert.assertEquals(selectservicesscreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
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
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(availablepricematrixes[2]);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		selectservicesscreen = vehiclepartsscreen.clickVehiclePartsBackButton();
		Assert.assertEquals(selectservicesscreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
		
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceSelected(matrixservice));
		Assert.assertEquals(inspservicesscreen.getSelectedServicePriceMatrixValue(matrixservice), availablepricematrixes[2]); 
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
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
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(servicetoselect);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceSelected(servicetoselect));
		Assert.assertEquals(inspservicesscreen.getSelectedServicePriceValue(servicetoselect), serviceprice);
		inspservicesscreen.setServiceAmountValue(servicetoselect, servicelastsymbol);
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertEquals(inspservicesscreen.getSelectedServicePriceValue(servicetoselect), newserviceprice);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
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
		visualscreen.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(amount);
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amountvalue);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		inspectionsscreen = visualscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(moneyservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), serviceprice);
		inspservicesscreen.swipeScreensRight(3);
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickOtherServiceOption();
		selectdamagesscreen.selectAllDamagesTab();
		VNextVisualServicesScreen visualservicesscreen = selectdamagesscreen.clickCustomDamageType(selectdamage);
		visualscreen = visualservicesscreen.selectCustomService(servicepercentage);
		visualscreen.clickCarImage();
		visualscreen.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(amount);
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amountvalue);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickDamageCancelEditingButton();
		Assert.assertEquals(visualscreen.getInspectionTotalPriceValue(), inspprice);
		inspectionsscreen = visualscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getFirstInspectionPrice(), inspprice);
		homescreen = inspectionsscreen.clickBackButton();
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
		visualscreen.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(amount);
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amount);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickDamageCancelEditingButton();
		Assert.assertEquals(visualscreen.getInspectionTotalPriceValue(), inspprice);
		inspectionsscreen = visualscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getFirstInspectionPrice(), inspprice);
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 41562:vNext - Edit service price (Percentage)", 
			description = "Edit service price (Percentage)")
	public void testEditServicePricePercentage() {
		
		final String moneyservice = "Dent Repair";
		final String percentageservice = "Aluminum Upcharge";
		final String quantitylastdigit = "8";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(moneyservice);
		selectservicesscreen.selectService(percentageservice);
		selectservicesscreen.clickSaveSelectedServicesButton();	
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.setServiceAmountValue(percentageservice, quantitylastdigit);
		Assert.assertEquals(inspservicesscreen.getSelectedServicePriceValue(percentageservice), "28.000%");
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
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
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(moneyservice);
		selectservicesscreen.clickSaveSelectedServicesButton();	
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.setServiceAmountValue(moneyservice, pricevalue);	
		Assert.assertEquals(inspservicesscreen.getSelectedServicePriceValue(moneyservice), "$3.20");
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 48947:vNext - Verify Total is correct when adding several money services on Visuals screen", 
			description = "Verify Total is correct when adding several money services on Visuals screen")
	public void testVerifyTotalIsCorrectWhenAddingSeveralMoneyServicesOnVisualsScreen() {
		
		final String selectdamage = "Dent Repair";
		final String[] amounts = { "20.00", "647.99" };
		final String[] quantities = { "10.58", "6" };
		final String inspprice = "$4099.54";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		inspinfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		visualscreen.clickDefaultDamageType(selectdamage);
		visualscreen.clickCarImageACoupleTimes(quantities.length);
		visualscreen.waitABit(1000);
		
		for (int i =0 ; i <  quantities.length; i++) {
			List<WebElement> damagemarkers =  visualscreen.getImageMarkers();
			VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker(damagemarkers.get(i));
			servicedetailsscreen.setServiceAmountValue(amounts[i]);
			servicedetailsscreen.setServiceQuantityValue(quantities[i]);
			servicedetailsscreen.clickServiceDetailsDoneButton();
			visualscreen = new VNextVisualScreen(appiumdriver);
			visualscreen.waitABit(1000);
		}
		visualscreen.clickDamageCancelEditingButton();
		Assert.assertEquals(visualscreen.getInspectionTotalPriceValue(), inspprice);
		inspectionsscreen = visualscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getFirstInspectionPrice(), inspprice);
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 48955:vNext - Verify Total is correct when adding money and percentage services on Visuals screen", 
			description = "Verify Total is correct when adding money and percentage services on Visuals screen")
	public void testVerifyTotalIsCorrectWhenAddingMoneyAndPercentageServicesOnVisualsScreen() {
		
		final String dentamount = "506.35";
		final String dentquantity = "5.00";
		
		final String selectdamage = "Price Adjustment";
		final String servicepercentage = "Double Panel";
		final String inspprice = "$3164.69";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		inspinfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		visualscreen.clickDefaultDamageType(selectdamage);
		visualscreen.clickCarImage();
		visualscreen.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(dentamount);
		servicedetailsscreen.setServiceQuantityValue(dentquantity);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		
		visualscreen.clickAddServiceButton();
		VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickOtherServiceOption();
		selectdamagesscreen.selectAllDamagesTab();
		VNextVisualServicesScreen visualservicesscreen = selectdamagesscreen.clickCustomDamageType(selectdamage);
		visualscreen = visualservicesscreen.selectCustomService(servicepercentage);
		visualscreen.clickCarImageSecondTime();
		
		visualscreen.clickDamageCancelEditingButton();
		Assert.assertEquals(visualscreen.getInspectionTotalPriceValue(), inspprice);
		inspectionsscreen = visualscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getFirstInspectionPrice(), inspprice);
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 41571:vNext - INSP - Delete service from Services screen", 
			description = "Delete service from Services screen")
	public void testDeleteServiceFromServicesScreen() {
		
		final String moneyservice1 = "Dent Repair";
		final String moneyservice2 = "Bumper Repair";
		final String percentageservice = "Aluminum Upcharge";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		String inspnumber = inspinfoscreen.getNewInspectionNumber();
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(moneyservice1);
		selectservicesscreen.selectService(moneyservice2);
		selectservicesscreen.selectService(percentageservice);
		selectservicesscreen.clickSaveSelectedServicesButton();	
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		inspservicesscreen.uselectService(moneyservice1);
		inspservicesscreen.uselectService(percentageservice);
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		Assert.assertTrue(inspservicesscreen.isServiceSelected(moneyservice2));
		Assert.assertFalse(inspservicesscreen.isServiceSelected(moneyservice1));
		Assert.assertFalse(inspservicesscreen.isServiceSelected(percentageservice));
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case:vNext Inspection total price should change when uselect some of the selected service on Services screen", 
			description = "Inspection total price should change when uselect some of the selected service on Services screen")
	public void testInspectionTotalPriceShouldChangeWhenUselectSomeOfTheSelectedServiceOnServicesScreen() { 
		
		final String[] servicestoselect = { "Bumper Repair", "R&I - Sunroof" };
		final String[] servicesprices = { "10", "10" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicecreen = inspservicesscreen.clickAddServicesButton();
		for (String servicesel : servicestoselect)
			selectservicecreen.selectService(servicesel);
		selectservicecreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i< servicestoselect.length; i++)
			inspservicesscreen.setServiceAmountValue(servicestoselect[i], servicesprices[i]);
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), "$20.00");
		
		for (int i=0; i< servicestoselect.length; i++)
			inspservicesscreen.uselectService(servicestoselect[i]);
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), "$0.00");
		inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case:vNext Services aren't became selected if user unselect them before clicking back button on Select Services screen", 
			description = "Services aren't became selected if user unselect them before clicking back button on Select Services screen")
	public void testServicesArentBecameSelectedIfUserUnselectThemBeforeClickingBackButtonOnServicesScreen() { 
		
		final String[] servicestoselect = { "Bumper Repair", "R&I - Sunroof" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicecreen = inspservicesscreen.clickAddServicesButton();
		for (String servicese : servicestoselect)
			selectservicecreen.selectService(servicese);
		for (String servicese : servicestoselect)
			selectservicecreen.unselectService(servicese);
		
		
		inspservicesscreen = selectservicecreen.clickBackButton();
		for (String servicese : servicestoselect)
			Assert.assertFalse(inspservicesscreen.isServiceSelected(servicese));
		inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case:vNext Create inspection with negative price", 
			description = "Create inspection with negative price")
	public void testCreateInspectionWithNegativePrice() { 
		
		final String damagetype = "Dent Repair";
		final String amount = "-9" ;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		vehicleinfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		visualscreen.clickDefaultDamageType(damagetype);
		visualscreen.clickCarImage();
		visualscreen.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(amount);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		
		servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.clickServiceAmountField();
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amount);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		
		visualscreen.clickDamageCancelEditingButton();
		visualscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case :vNext - Total is not set to 0 if user adds Matrix Additional service with negative percentage service",
			description = "Total is not set to 0 if user adds Matrix Additional service with negative percentage service")
	public void testTotalIsNotSetTo0IfUserAddsMatrixAdditionalServiceWithNegativePercentageService() {
		
		final String matrixservice = "Hail Dent Repair";
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
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(pricematrix);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservicename);
		vehiclepartinfoscreen.setAdditionalServicePriceValue(additionalservicename, additionalservicenprice);
		Assert.assertEquals(vehiclepartinfoscreen.getMatrixServiceTotalPriceValue(), inspectiontotalprice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		selectservicesscreen = vehiclepartsscreen.clickVehiclePartsBackButton();
		Assert.assertEquals(selectservicesscreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), pricematrix);
		
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceSelected(matrixservice));
		Assert.assertEquals(inspservicesscreen.getSelectedServicePriceMatrixValue(matrixservice), pricematrix);
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), inspectiontotalprice);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case:  Open and set service details on Select Services screen", 
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
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		VNextServiceDetailsScreen servicedetailsscreen = selectservicesscreen.openServiceDetails(serviceName);
		servicedetailsscreen.setServiceAmountValue(servicePrice);
		servicedetailsscreen.setServiceQuantityValue(serviceQuantity);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		selectservicesscreen = new VNextSelectServicesScreen(appiumdriver);
		
		selectservicesscreen.clickSaveSelectedServicesButton();		
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), serviceTotal);
		
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 47440:vNext - Verify services are saved correctly when saving inspection from Visual screen", 
			description = "Verify services are saved correctly when saving inspection from Visual screen")
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
			visualscreen.clickAddServiceButton();;
			visualscreen.clickDefaultDamageType(selectdamages[i]);
			if (i==0)
				visualscreen.clickCarImage();
			else
				visualscreen.clickCarImageSecondTime();
			visualscreen.waitABit(1000);
		}
		visualscreen.swipeScreenLeft();
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);	
		for (int i = 0; i < selectedservices.length; i++) {
			Assert.assertTrue(inspservicesscreen.isServiceSelected(selectedservices[i]));
		}
		
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 68042:Verify sending >100 messages after reconnect Internet", 
			description = "Verify sending >100 messages after reconnect Internet")
	public void testVerifySendingMoreThen100MessagesAfterReconnectInternet() {
		
		final int fakeimagescount = 50;
		final String imagesummaryvalue = "+47";
		final String[] services = { "Bumper Repair", "Facility Fee", "Aluminum Upcharge" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		setNetworkOff();
		homescreen.waitABit(13000);		
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		final String inspnumber = inspinfoscreen.getNewInspectionNumber();
		VNextInspectionServicesScreen inspservicesscreen =inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectedservicesscreen = inspservicesscreen.clickAddServicesButton();
		for (String srv : services) {			
			VNextServiceDetailsScreen servicedetailsscreen = selectedservicesscreen.openServiceDetails(srv);
			VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
			for (int i = 0; i < fakeimagescount; i++)
				notesscreen.addFakeImageNote();
			notesscreen.clickScreenBackButton();
			servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
			servicedetailsscreen.clickServiceDetailsDoneButton();
			selectedservicesscreen = new VNextSelectServicesScreen(appiumdriver);
		}
		selectedservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		//inspservicesscreen.selectAllServices();
		
		inspservicesscreen.saveInspectionViaMenu();
		inspservicesscreen.clickScreenBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.clickUpdateAppdata();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogStartSyncButton();
		waitABit(10000);
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		setNetworkOn();	
		statusscreen.clickUpdateAppdata();	
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogStartSyncButton();
		waitABit(10000);
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		
		homescreen = statusscreen.clickBackButton();
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		Assert.assertTrue(inspectionsscreen.isInspectionExists(inspnumber));
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		inspservicesscreen =inspinfoscreen.goToInspectionServicesScreen();
		for (String srv : services) {	
			inspservicesscreen.isServiceSelected(srv);
			Assert.assertEquals(inspservicesscreen.getSelectedServiceImageSummaryValue(srv), imagesummaryvalue);
		}
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspservicesscreen.clickScreenBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
	}
}

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
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectDamagesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class vNextInspectionServicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String[] servicesselect = { "Dent Repair", "Double Panel" };
	final String testcustomer = "Oksana Osmak";
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
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
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
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.swipeScreenRight();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		inspservicesscreen.swipeScreenLeft();
		inspservicesscreen.swipeScreenRight();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
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
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(thirdservicetoadd);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceAdded(thirdservicetoadd));
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i<thirdservice.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(thirdservice[i]));
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
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		final String inspnum = inspservicesscreen.getNewInspectionNumber();
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		
		for (int i=0; i < servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(secondpart);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i=0; i < thirdservice.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(thirdservice[i]));
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i<thirdservice.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(thirdservice[i]));
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
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
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
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
		inspservicesscreen.saveInspectionViaMenu();

		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (int i=0; i<servicesselect.length; i++)
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
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
			Assert.assertTrue(inspservicesscreen.isServiceAdded(servicesselect[i]));
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
		
		final String[] secondservices = { "Bumper Repair", "Large Size Dent" }; 
		final String[] thirdservices = { "oosmak %%%%%", "tesla" };
	
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
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), "$242.00");
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(secondservices[0]);
		inspservicesscreen = servicedetailsscreen.deleteService();
		
		selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(thirdservices);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(thirdservices[0]);
		servicedetailsscreen.setServiceAmountValue("5");
		servicedetailsscreen.clickServiceDetailsDoneButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(secondservices[1]);
		servicedetailsscreen.setServiceAmountValue("84.55");
		servicedetailsscreen.setServiceQuantityValue("9.15");
		servicedetailsscreen.clickServiceDetailsDoneButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), "$1161.96");
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37843:vNext - Verify Price Matrix added to service package is available to choose when add/edit inspection", 
			description = "Verify Price Matrix added to service package is available to choose when add/edit inspection")
	public void testVerifyPriceMatrixAddedToServicePackageIsAvailableToChooseWhenAddEditInspection() {
		
		final String matrixservice = "Hail Dent Repair";
		final String[] availablepricematrixes = { "Nationwide Insurance", "Nationwide Insurance-copy",
				"Progressive", "Progressive-copy", "State Farm", "State Farm-copy" };
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		selectservicesscreen = pricematrixesscreen.selectPriceMatrix(availablepricematrixes[2]);
		Assert.assertEquals(selectservicesscreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
		
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceAdded(matrixservice));
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37844:vNext - Verify list of available Price Matrices is loaded when choosing Matrix Service", 
			description = "Verify list of available Price Matrices is loaded when choosing Matrix Service")
	public void testVerifyListOfAvailablePriceMatricesIsLoadedWhenChoosingMatrixService() {
		
		final String matrixservice = "Hail Dent Repair";
		final String[] availablepricematrixes = { "Nationwide Insurance", "Nationwide Insurance-copy",
				"Progressive", "Progressive-copy", "State Farm", "State Farm-copy" };
	
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
		selectservicesscreen = pricematrixesscreen.selectPriceMatrix(availablepricematrixes[2]);
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
		final String[] availablepricematrixes = { "Nationwide Insurance", "Nationwide Insurance-copy",
				"Progressive", "Progressive-copy", "State Farm", "State Farm-copy" };
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		selectservicesscreen = pricematrixesscreen.selectPriceMatrix(availablepricematrixes[2]);
		Assert.assertEquals(selectservicesscreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
		
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceAdded(matrixservice));
		Assert.assertEquals(inspservicesscreen.getSelectedServicePriceMatrixValue(matrixservice), availablepricematrixes[2]); 
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 45902:vNext - Verify correct price is shown in Services list after editing service price (Percentage)", 
			description = "Verify correct price is shown in Services list after editing service price (Percentage)")
	public void testVerifyCorrectPriceIsShownInServicesListAfterEditingServicePricePercentage() {
		
		final String servicetoselect = "555";
		final String serviceprice = "0.015%";
		final char servicelastsymbol = '8';
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
		Assert.assertTrue(inspservicesscreen.isServiceAdded(servicetoselect));
		Assert.assertEquals(inspservicesscreen.getSelectedServicePriceValue(servicetoselect), serviceprice);
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(servicetoselect);
		servicedetailsscreen.clickServiceAmountField();
		servicedetailsscreen.clickKeyboardBackspaceButton();
		servicedetailsscreen.clickKeyboardButton(servicelastsymbol);
		servicedetailsscreen.clickKeyboardDoneButton();
		servicedetailsscreen.clickServiceDetailsDoneButton();
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
		VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickAddServiceButton();
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
		VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickAddServiceButton();
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
		VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickAddServiceButton();
		visualscreen = selectdamagesscreen.clickDefaultDamageType(selectdamage);
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
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(percentageservice);
		servicedetailsscreen.clickServiceAmountField();
		servicedetailsscreen.clickKeyboardBackspaceButton();
		servicedetailsscreen.clickKeyboardButton(quantitylastdigit.charAt(0));
		servicedetailsscreen.clickKeyboardDoneButton();
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), "28.000");
		servicedetailsscreen.clickServiceDetailsDoneButton();
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
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(moneyservice);
		servicedetailsscreen.clickServiceAmountField();
		servicedetailsscreen.clickKeyboardBackspaceButton();
		for (int i = 0; i < pricevalue.length(); i++) {
			servicedetailsscreen.clickKeyboardButton(pricevalue.charAt(i));
		}
		servicedetailsscreen.clickKeyboardDoneButton();
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), pricevalue);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		Assert.assertEquals(inspservicesscreen.getSelectedServicePriceValue(moneyservice), "$3.20");
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
	}
}

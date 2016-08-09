package com.cyberiansoft.test.vnext.testcases;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServicePackagesWebPage;
import com.cyberiansoft.test.vnext.screens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class vNextInspectionServicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String[] servicesselect = { "Detail", "Odor Removal" };
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
		
		final String thirdservicetoadd = "Paint";
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
		
		final String[] secondpart = { "Paint", "Other" };
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
	
		final String vinnumber = "1GCJC33G22E688420";
		
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
		
		final String insurCompany = "USG";
		final String policynumber = "#ins034-74000056";
		final String claimnumber = "claim09875";
		
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
			Assert.assertEquals(inspservicesscreen.getSelectedservicePriceValue(servicestoselect[i]), servicesprices[i]);
		
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
}

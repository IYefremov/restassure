package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LicensesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SinglePageInspectionScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.*;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class iOSRegularSmokeTestCases extends BaseTestCase {

	private String regCode;
	private RegularHomeScreen homescreen;
	
	private final String firstname = "supermy12";
	private final String firstnamenew = "supernewmy12";
	private final String lastname = "super";
	private final String companyname = "supercompany";
	private final String street = "First streer";
	private final String city = "New York";
	private final String zip = "79031";
	private final String phone = "723-1234567";
	private final String mail = "test@cyberiansoft.com";
	private final String state = "Alberta";
	private final String country = "Canada";

	@BeforeClass
	public void setUpSuite() throws Exception {
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		testGetDeviceRegistrationCode(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL(),
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Test_Automation_Regular");
		testRegisterationiOSDdevice();
		ExcelUtils.setDentWizardExcelFile();
	}
	
	public void testGetDeviceRegistrationCode(String backofficeurl,
			String userName, String userPassword, String licensename) throws Exception {

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companyWebPage = backofficeheader.clickCompanyLink();

		ActiveDevicesWebPage devicespage = companyWebPage.clickManageDevicesLink();

		devicespage.setSearchCriteriaByName(licensename);
		regCode = devicespage.getFirstRegCodeInTable();
		DriverBuilder.getInstance().getDriver().quit();
	}

	public void testRegisterationiOSDdevice() throws Exception {
		appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
		//Helpers.waitABit(5000);
		//appiumdriver.removeApp(IOSRegularDeviceInfo.getInstance().getDeviceBundleId(), null);
		//appiumdriver.removeApp(IOSRegularDeviceInfo.getInstance().getDeviceBundleId());
		//appiumdriver.quit();
		//appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
		//appiumdriver.installApp(appPath);
		//appiumdriver.removeApp(bundleid);
		//appiumdriverInicialize();
		//appiumdriver.installApp(app.getAbsolutePath());
		//appiumdriver.launchApp();

		if (appiumdriver.isAppInstalled(IOSRegularDeviceInfo.getInstance().getDeviceBundleId())) {
			Map<String, Object> params = new HashMap<>();
			params.put("bundleId", IOSRegularDeviceInfo.getInstance().getDeviceBundleId());
			appiumdriver.executeScript("mobile: removeApp", params);
			appiumdriver.quit();
			appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
		}
		appiumdriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen(appiumdriver);
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("Dev Environment");
		
		//LoginScreen loginscreen = new LoginScreen(appiumdriver);
		loginscreen.registeriOSDevice(regCode);
		RegularMainScreen mainscr = new RegularMainScreen(appiumdriver);
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
	
	//Test Case 8438:Update database on the device
	@Test(testName = "Test Case 8438:Update database on the device" ,description = "Update Database")
	public void testUpdateDatabase() throws Exception {
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		RegularMainScreen mainscr = new RegularMainScreen(appiumdriver);
		mainscr.updateDatabase();
		RegularHomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen.clickStatusButton();
		homescreen.updateDatabase();
		mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
	}

	//Test Case 8437:Updating VIN decoder
	@Test(testName = "Test Case 8437:Updating VIN decoder", description = "Update VIN")
	public void testUpdateVIN() throws Exception {
		//resrtartApplication();
		//RegularMainScreen mainscr = new RegularMainScreen(appiumdriver);
		RegularMainScreen mainscr = homescreen.clickLogoutButton();
		mainscr.updateVIN();
		RegularHomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen.clickStatusButton();
		homescreen.updateVIN();
		homescreen.clickHomeButton();
		//homescreen.clickLogoutButton();
	}

	//Test Case 8441:Add Retail Customer in regular build
	@Test(testName = "Test Case 8441:Add Retail Customer in regular build", description = "Create retail customer")
	public void testCreateRetailCustomer() throws Exception {

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		RegularAddCustomerScreen addcustomerscreen = customersscreen.clickAddCustomersButton();

		addcustomerscreen.addCustomer(firstname, lastname, companyname, street,
				city, state, zip, country, phone, mail);
		addcustomerscreen.clickSaveBtn();
		Assert.assertTrue(customersscreen.checkCustomerExists(firstname));

		customersscreen.clickHomeButton();
		//homescreen.clickLogoutButton();
	}

	//Test Case 8439:Edit Customer 
	@Test(testName = "Test Case 8439:Edit Customer ", description = "Edit retail customer")
	public void testEditRetailCustomer() throws Exception {
		final String lastname = "superedited";
		final String companyname = "supercompanyedited";
		final String street = "Second streer";
		final String city = "New York";
		final String zip = "79035";
		final String phone = "723-1234576";
		final String mail = "test123@cyberiansoft.com";
		
		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();

		RegularAddCustomerScreen addcustomerscreen = customersscreen.selectCustomerToEdit(firstname);

		addcustomerscreen.editCustomer(firstnamenew, lastname, companyname,
				street, city, city, zip, zip, phone, mail);
		addcustomerscreen.clickSaveBtn();
		Assert.assertTrue(customersscreen.checkCustomerExists(firstnamenew));
		customersscreen.clickHomeButton();
		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
	}

	// Test Case 8460: Delete Customer 
	@Test(testName = "Test Case 8460: Delete Customer", description = "Delete retail customer")
	public void testDeleteRetailCustomer() throws Exception {

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companyWebPage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companyWebPage.clickClientsLink();

		clientspage.deleteUserViaSearch(firstnamenew);

		DriverBuilder.getInstance().getDriver().quit();
	
		RegularMainScreen mainscreen = new RegularMainScreen(appiumdriver);
		mainscreen.updateDatabase();
		RegularHomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		Assert.assertFalse(customersscreen.checkCustomerExists(firstnamenew));
		customersscreen.clickHomeButton();
		//homescreen.clickLogoutButton();
	}
	
	
	//Test Case 8435:Create Retail Inspection (HD Single page)
	@Test(testName = "Test Case 8435:Create Retail Inspection", description = "Create Retail Inspection")
	public void testCreateRetailInspection() throws Exception {
		final String VIN = "ZWERTYASDFEWSDRZG";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		RegularVehicleScreen vehiclescreeen = myinspectionsscreen.selectDefaultInspectionType();
		vehiclescreeen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.clickSave();
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Make is required"));
		
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		RegularVisualInteriorScreen regularVisualInteriorScreen = vehiclescreeen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualInteriorCaption(), RegularVisualInteriorScreen.class);
		regularVisualInteriorScreen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualExteriorCaption(), RegularVisualInteriorScreen.class);
		regularVisualInteriorScreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	//Test Case 8434:Add Services to visual inspection
	@Test(testName = "Test Case 8434:Add Services to visual inspection", description = "Add Services To Visual Inspection")
	public void testAddServicesToVisualInspection() throws Exception {
		final String _inspectionprice = "275";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickEditInspectionButton();
		RegularVehicleScreen vehiclescreen = new RegularVehicleScreen(appiumdriver);
		RegularVisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualInteriorCaption(), RegularVisualInteriorScreen.class);
		visualinteriorscreen.clickServicesToolbarButton();
		Assert.assertTrue(visualinteriorscreen.isInteriorServicePresent("Miscellaneous"));
		Assert.assertTrue(visualinteriorscreen.isInteriorServicePresent("Price Adjustment"));
		Assert.assertTrue(visualinteriorscreen.isInteriorServicePresent("WHEEL REPAIR"));
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		visualinteriorscreen.tapInterior();
		RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.clickServicesBackButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		RegularVisualInteriorScreen.tapInteriorWithCoords(150, 150);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 200);
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualExteriorCaption(), RegularVisualInteriorScreen.class);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		RegularVisualInteriorScreen.tapExterior();
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.clickServicesBackButton();
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionprice));
		visualinteriorscreen.saveWizard();
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionPriceValue(), PricesCalculations.getPriceRepresentation(_inspectionprice));
		myinspectionsscreen.clickHomeButton();
	}

	//Test Case 8433:Change Quantity of services in Visual Inspection 
	@Test(testName = "Test Case 8433:Change Quantity of services in Visual Inspection", description = "Change Quantity Of Services In Visual Inspection")
	public void testChangeQuantityOfServicesInVisualInspection()
			throws Exception {
		final String _quantity = "3.00";
		final String _quantityexterior = "2.00";
		final String _inspectionpricevisual = "275";
		final String _inspectionprice = "325";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickEditInspectionButton();
		RegularVehicleScreen vehiclescreen = new RegularVehicleScreen(appiumdriver);
		RegularVisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualInteriorCaption(), RegularVisualInteriorScreen.class);

		visualinteriorscreen.tapInterior();
		visualinteriorscreen.setCarServiceQuantityValue(_quantity);
		visualinteriorscreen.saveCarServiceDetails();
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionpricevisual));
		visualinteriorscreen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualExteriorCaption(), RegularVisualInteriorScreen.class);

		RegularVisualInteriorScreen.tapExterior();
		visualinteriorscreen.setCarServiceQuantityValue(_quantityexterior);
		visualinteriorscreen.saveCarServiceDetails();
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionprice));
		visualinteriorscreen.saveWizard();
		myinspectionsscreen.clickHomeButton();

	}

	//Test Case 8432:Edit the retail Inspection Notes
	@Test(testName = "Test Case 8432:Edit the retail Inspection Notes", description = "Edit Retail Inspection Notes")
	public void testEditRetailInspectionNotes() throws Exception {
		final String _notes1 = "Test\nTest 2";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickEditInspectionButton();
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		RegularVisualInteriorScreen visualInteriorScreen = vehiclescreeen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualInteriorCaption(), RegularVisualInteriorScreen.class);
		RegularNotesScreen notesscreen = visualInteriorScreen.clickNotesButton();
		notesscreen.setNotes(_notes1);
		//notesscreen.clickDoneButton();
		notesscreen.addQuickNotes();
		notesscreen.clickSaveButton();
		visualInteriorScreen = new RegularVisualInteriorScreen(appiumdriver);
		visualInteriorScreen.clickNotesButton();
		Assert.assertEquals(notesscreen.getNotesAndQuickNotes(), _notes1 + "\n" + notesscreen.quicknotesvalue);
		notesscreen.clickSaveButton();
		visualInteriorScreen = new RegularVisualInteriorScreen(appiumdriver);
		visualInteriorScreen.saveWizard();
		myinspectionsscreen.clickHomeButton();

	}

	//Test Case 8431:Approve inspection on device (Not Line Approval)
	@Test(testName = "Test Case 8431:Approve inspection on device (Not Line Approval)", description = "Approve Inspection On Device")
	public void testApproveInspectionOnDevice() throws Exception {

		final String VIN = "CFRTHASDFEWSDRZWM";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		//customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehiclescreeen.saveWizard();
		String inspection = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.selectInspectionForApprove(inspection);
		// approveinspscreen.selectInspectionToApprove();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspection);
		approveinspscreen.clickApproveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inspection));
		myinspectionsscreen.clickHomeButton();
	}

	// Test Case 8586:Archive and Un-Archive the Inspection
	@Test(testName = "Test Case 8586:Archive and Un-Archive the Inspection", description = "Archive and Un-Archive the Inspection")
	public void testArchiveAndUnArchiveTheInspection() throws Exception {

		final String VIN = "TESTVINNO";
		final String _make = "Acura";
		final String _model = "ILX";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehiclescreeen.saveWizard();
		String myinspetoarchive = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.clickHomeButton();

		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.archiveInspection(myinspetoarchive,
				"Reason 2");
		Assert.assertTrue(myinspectionsscreen.checkInspectionDoesntExists(myinspetoarchive));
		myinspectionsscreen.clickHomeButton();
		
	}

	//Test Case 8430:Create work order with type is assigned to a specific client
	@Test(testName = "Test Case 8430:Create work order with type is assigned to a specific client", description = "Create work order with type is assigned to a specific client ")
	public void testCreateWorkOrderWithTypeIsAssignedToASpecificClient()
			throws Exception {
		final String VIN = "ZWERTYASDFDDXZBVB";
		final String _po = "1111 2222";
		final String summ = "71.40";
		
		final String license = "Iphone_Test_Spec_Client";

		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		LicensesScreen licensesscreen = mainscreen.clickLicenses();
		licensesscreen.clickAddLicenseButtonAndAcceptAlert();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companyWebPage = backofficeheader.clickCompanyLink();
		ActiveDevicesWebPage devicespage = companyWebPage.clickManageDevicesLink();

		devicespage.setSearchCriteriaByName(license);
		regCode = devicespage.getFirstRegCodeInTable();

		DriverBuilder.getInstance().getDriver().quit();
		appiumdriver.manage().timeouts().implicitlyWait(800, TimeUnit.SECONDS);
		RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen(appiumdriver);
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("Dev Environment");
		//LoginScreen loginscreen = new LoginScreen(appiumdriver);
		loginscreen.registeriOSDevice(regCode);
		mainscreen.userLogin(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN, iOSInternalProjectConstants.USER_PASSWORD);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.SPECIFIC_CLIENT_TEST_WO1);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		Assert.assertTrue(servicesscreen.isDefaultServiceIsSelected());
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE));
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(summ));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		ordersummaryscreen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		vehiclescreeen.setVIN(VIN);

		ordersummaryscreen = vehiclescreeen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(summ));
		//ordersummaryscreen.checkApproveAndCreateInvoice();
		//ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		ordersummaryscreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveEmptyPO();
		invoiceinfoscreen.setPO(_po);
		//ordersummaryscreen.clickSaveButton();
		invoiceinfoscreen.clickSaveAsDraft();

		myworkordersscreen.clickHomeButton();
	}

	//Test Case 8429:Creating complex calculation WO
	@Test(testName = "Test Case 8429:Creating complex calculation WO,"
			+ "Test Case 8428:Copy services of WO (regular version)", description = "Createing Complex calculation WO,"
					+ "Copy Cervices Of WO")
	public void testCreateWorkOrderWithTeamSharingOption() throws Exception {

		final String VIN = "1FTRX02W35K097028";
		final String summ = "346.23";
		final String summfinal = "91.80";
		// =======================================
		final String _dye_price = "$10.00";
		final String _dye_adjustments = "$0.50";
		// =======================================
		final String _disk_ex_srvc_price = "$100.00";
		final String _disk_ex_srvc_adjustment = "For_SP_Cl";
		final String _disk_ex_srvc_adjustment_value = "%-5.000";
		final String _disk_ex_srvc_adjustment_value_ed = "-$5.00";
		// ======================================
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String _price = "$100.00";
		final String _discaunt_us = "Discount 10-20$";

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new RegularHomeScreen(appiumdriver);	
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		
		vehiclescreeen.setVIN(VIN);
		/*appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();*/
		String wonum = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE));
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), _dye_price);
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), _dye_adjustments);
		selectedservicescreen.setServiceQuantityValue("3.00");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE));
		// =====================================
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), _disk_ex_srvc_price);
		selectedservicescreen.clickAdjustments();
		Assert.assertEquals(selectedservicescreen.getAdjustmentValue(_disk_ex_srvc_adjustment),
				_disk_ex_srvc_adjustment_value);
		selectedservicescreen.selectAdjustment(_disk_ex_srvc_adjustment);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), _disk_ex_srvc_adjustment_value_ed);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		// =====================================
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen(appiumdriver);
		Assert.assertTrue(selectedservicebundlescreen.checkBundleIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(selectedservicebundlescreen.checkBundleIsNotSelected(iOSInternalProjectConstants.DYE_SERVICE));
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen.setServiceQuantityValue("2.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		// =====================================
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE));

		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		// =====================================
		servicesscreen.selectSubService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		RegularPriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix);
		vehiclePartScreen.setSizeAndSeverity(_size, _severity);
		Assert.assertEquals(vehiclePartScreen.getPrice(), _price);
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.saveSelectedServiceDetails();
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.selectDiscaunt(_discaunt_us);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
//		RegularPriceMatricesScreen priceMatricesScreen = new RegularPriceMatricesScreen(appiumdriver);
//		priceMatricesScreen.clickBackButton();

		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(summ));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.selectWorkOrder(wonum);
		myworkordersscreen.selectCopyServices();
		customersscreen.selectCustomer(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.SPECIFIC_CLIENT_TEST_WO1);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));
		ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(summfinal));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

	}

	//todo
	@Test(testName = "Approve Inspections On Device via Action", description = "Approve Inspections On Device via Action")
	public void testApproveInspectionsOnDeviceViaAction() throws Exception {

		final String VIN = "TESTVINN";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";
		String[] inpections = { "", "" };
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			vehiclescreeen.setMakeAndModel(_make, _model);
			vehiclescreeen.setColor(_color);
			vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
			inpections[i] = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.saveWizard();
		}

		myinspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.selectInspectionForAction(inpections[i]);
		}
		
		myinspectionsscreen.clickApproveInspections();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		//Helpers.waitABit(2000);
		
		//Helpers.acceptAlert();
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		for (int i = 0; i < 2; i++) {
			approveinspscreen.selectInspection(inpections[i]);
			approveinspscreen.clickApproveButton();
		}
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		// }

		// approveinspscreen.clickBackButton();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		for (int i = 0; i < 2; i++) {
			Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inpections[i]));
		}
		myinspectionsscreen.clickHomeButton();
	}

	//todo
	@Test(testName = "Archive Inspections On Device via Action", description = "Archive Inspections On Device via Action")
	public void testArchiveInspectionsOnDeviceViaAction() throws Exception {

		final String VIN = "TESTVINN";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";
		String[] inpections = { "", "" };
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			vehiclescreeen.setMakeAndModel(_make, _model);
			vehiclescreeen.setColor(_color);
			vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
			inpections[i] = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.saveWizard();
		}

		myinspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.selectInspection(inpections[i]);
		}
		myinspectionsscreen.clickArchiveInspections();
		myinspectionsscreen.selectReasonToArchive("Reason 2");
		//myinspectionsscreen.clickDoneButton();
		for (int i = 0; i < 2; i++) {
			Assert.assertTrue(myinspectionsscreen.checkInspectionDoesntExists(inpections[i]));
		}
		myinspectionsscreen.clickHomeButton();
	}

	//Test Case 8467:Approve inspection on back office (full inspection approval)
	@Test(testName = "Test Case 8467:Approve inspection on back office (full inspection approval)", description = "Approve inspection on back office (full inspection approval)")
	public void testApproveInspectionOnBackOfficeFullInspectionApproval() throws Exception {

		final String VIN = "TESTVINNO";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		String inpectionnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

		vehiclescreeen.saveWizard();
		String inpection = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.clickHomeButton();
		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
		

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationsWebPage.clickInspectionsLink();

		inspectionspage.approveInspectionByNumber(inpectionnumber);

		DriverBuilder.getInstance().getDriver().quit();
		
		mainscreen.updateDatabase();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inpection));

		myinspectionsscreen.clickHomeButton();
		
	}

	//Test Case 8463:Approve inspection on back office (line-by-line approval)
	@Test(testName = "Test Case 8463:Approve inspection on back office (line-by-line approval) ", description = "Approve inspection on back office (line-by-line approval)")
	public void testApproveInspectionOnBackOfficeLinebylineApproval() throws Exception {

		final String VIN = "TESTVINNO";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_LA_DA_INSPTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		String inpectionnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

		vehiclescreeen.saveWizard();
		String inpection = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.clickHomeButton();
		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationsWebPage.clickInspectionsLink();

		inspectionspage.approveInspectionLinebylineApprovalByNumber(
				inpectionnumber, iOSInternalProjectConstants.DISC_EX_SERVICE1, iOSInternalProjectConstants.DYE_SERVICE);

		DriverBuilder.getInstance().getDriver().quit();

		mainscreen.updateDatabase();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inpection));

		myinspectionsscreen.clickHomeButton();
	}

	// Test Case 8442: Creating Inspection From Service Request
	@Test(testName = "Test Case 8442: Creating Inspection From Service Request", description = "Creating Inspection From Service Request")
	public void testCreatingInspectionFromServiceRequest() throws Exception {

		final String customer = "Company2";
		final String vin = "TESTVNN1";

		// final String servicerequest = "Fn1 Lm1, VIN#:TESTVNN1";
		final String servicerequest = "Fn1 Lm1";
		final String price = "314.15";

		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		ServiceRequestsWebPage servicerequestpage = operationsWebPage.clickServiceRequestsLink();

		servicerequestpage.clickEditBtn();
		servicerequestpage.clickCustomerTab();
		servicerequestpage.selectCustomer(customer);
		servicerequestpage.clickVehicleInfoTab();
		servicerequestpage.setVIN(vin);
		servicerequestpage.clickServicesTab();
		servicerequestpage.selectSelectServiceType(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicerequestpage.selectSelectServiceType(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicerequestpage.setServiceTypeQuantity(iOSInternalProjectConstants.TEST_TAX_SERVICE, "1");
		servicerequestpage.setServiceTypeQuantity(iOSInternalProjectConstants.WHEEL_SERVICE, "3");
		servicerequestpage.clickSaveBtn();
		DriverBuilder.getInstance().getDriver().quit();


		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		RegularMainScreen mainscreen = new RegularMainScreen(appiumdriver);
		RegularHomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickRefreshButton();
		servicerequestsscreen.selectServiceRequest(servicerequest);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_SR_INSPTYPE);
		String inspnumber = servicerequestsscreen.getInspectionNumber();
		RegularVehicleScreen vehiclescreen = new RegularVehicleScreen(appiumdriver);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(price));
		servicesscreen.saveWizard();
		servicerequestsscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainscreen.updateDatabase();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		operationsWebPage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationsWebPage.clickInspectionsLink();

		inspectionspage.assertInspectionPrice(inspnumber, PricesCalculations.getPriceRepresentation(price));
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	String srtowo = "";
	
	//Test Case 20786:Creating Service Request with Inspection, WO and Appointment required on device
	@Test(testName = "Test Case 20786:Creating Service Request with Inspection, WO and Appointment required on device", description = "Creating Service Request with Inspection, WO and Appointment required on device")
	public void testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice() throws Exception {
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		final String _color = "Red";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";
		final String stock = "Stock1";
		final String _ro = "123";	
		final String licplate = "456";
		//final String _year = "2012";
		
		final String teamname= "Default team";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_EST_WO_REQ_SRTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		/*Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("The VIN is invalid.")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		*/
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		//vehiclescreeen.setYear(_year);

		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		//vehiclescreeen.setLicensePlate(licplate);
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.WHEEL_SERVICE);
		
		
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.selectService("Quest_Req_Serv");
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen =  servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicedetailsscreen.setServiceQuantityValue("3");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		//servicesscreen.setSelectedServiceRequestServicesQuantity(iOSInternalProjectConstants.WHEEL_SERVICE, "3");
		servicesscreen.clickAddServicesButton();
		RegularClaimScreen claimscreen = servicesscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION, RegularClaimScreen.class);
		claimscreen.selectInsuranceCompany("USG");
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Question 'Signature' in section 'Follow up Requested' should be answered.")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		//Helpers.swipeRegularScreenUp();
		questionsscreen.drawRegularSignature();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		//questionsscreen.swipeScreenUp();
		//questionsscreen.swipeScreenUp();
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		srtowo = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srtowo), "On Hold");
		Assert.assertTrue(servicerequestsscreen.getServiceRequestClient(srtowo).contains(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER));
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestDetails(srtowo).contains("WERTYU123"));
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(10*1000);
	
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		
		servicerequestslistpage.verifySearchFieldsAreVisible();
		
		//servicerequestslistpage.selectSearchStatus("All On Scheduled");

		servicerequestslistpage.selectSearchTeam(teamname);
		servicerequestslistpage.selectSearchTechnician("Employee Simple 20%");
		servicerequestslistpage.setSearchFreeText(srtowo);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.verifySearchResultsByServiceName("Test Company (Universal Client)");
		//Assert.assertTrue(servicerequestslistpage.isFirstServiceRequestFromListHasStatusOnHold());
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getVINValueForSelectedServiceRequest(), "WERTYU123");
		Assert.assertEquals(servicerequestslistpage.getCustomerValueForSelectedServiceRequest(), "Test Company (Universal Client)");
		Assert.assertEquals(servicerequestslistpage.getEmployeeValueForSelectedServiceRequest(), "Employee Simple 20% (Default team)");
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("Bundle1_Disc_Ex $150.00 (1.00)"));
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("Quest_Req_Serv $10.00 (1.00)"));
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("Wheel $70.00 (3.00)"));
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	//Test Case 21582:Create Inspection from Service request
	@Test(testName = "Test Case 21582:Create Inspection from Service request", description = "Create Inspection from Service request"/*,
			dependsOnMethods = { "testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice" }*/)
	public void testCreateInspectionFromServiceRequest() throws Exception {
		final String summ= "438.60";
			
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_SR_INSPTYPE);

		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.WHEEL_SERVICE,  "$70.00 x 3.00"));
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.BUNDLE1_DISC_EX, "$150.00"));
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues("Quest_Req_Serv", "$10.00 x 1.00"));
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails("Quest_Req_Serv");
		selectedservicedetailsscreen.answerTaxPoint1Question("Test Answer 1");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		homescreen = servicerequestsscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspnumber));
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionPriceValue(), PricesCalculations.getPriceRepresentation(summ));
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		
		myinspectionsscreen.clickApproveInspections();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		//Helpers.acceptAlert();
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber);		
		approveinspscreen.clickApproveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		//myinspectionsscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 21585:Create WO from Service Request
	@Test(testName = "Test Case 21585:Create WO from Service Request", description = "Create WO from Service Request"/*,
			dependsOnMethods = { "testCreateInspectionFromServiceRequest" }*/)
	public void testCreateWOFromServiceRequest() throws Exception {

		/*appiumdriver.closeApp();
		Thread.sleep(60*1000*15);
		
		appiumdriverInicialize();
		String srnum = "R-00006200";
		RegularMainScreen mainscreen = new RegularMainScreen(appiumdriver);
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		*/
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		homescreen = customersscreen.clickHomeButton();

		//test case
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srtowo);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.WO_FOR_SR);
		String wonumber = servicerequestsscreen.getWorkOrderNumber();
		RegularVehicleScreen vehiclescreen = new RegularVehicleScreen(appiumdriver);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectService("Other");
		
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicedetailsscreen.changeAmountOfBundleService("70");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickBackServicesButton();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
		servicerequestsscreen= new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.woExists(wonumber);
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 16640:Create Inspection from Invoice with two WOs
	@Test(testName = "Test Case 16640:Create Inspection from Invoice with two WOs", description = "Create Inspection from Invoice with two WOs")
	public void testCreateInspectionFromInvoiceWithTwoWOs() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		//final String _year = "2014";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		final String ordersumm = "13.50";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);

		String wonumber1 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		//vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);

		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Cowl, Other");
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ordersumm));
		
		ordersummaryscreen.clickSave();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			
		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getMake(), _make);
		Assert.assertEquals(vehiclescreeen.getModel(), _model);
		//Assert.assertEquals(vehiclescreeen.getYear(), _year);
		ordersummaryscreen = vehiclescreeen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);

		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
		ordersummaryscreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setPO("23");
		invoiceinfoscreen.addWorkOrder(wonumber1);
		Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ordersumm));
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 16664:Create Invoice from WO in "My WOs" list
	@Test(testName = "Test Case 16664:Create Invoice from WO in \"My WOs\" list", description = "Create Invoice from WO in My WOs list")
	public void testCreateInvoiceFromWOInMyWOsList() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		//final String _year = "2012";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber1 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		//vehiclescreeen.setYear(_year);


		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		selectedservicescreen.clickAdjustments();
		selectedservicescreen.selectAdjustment("For_SP_Cl");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$108.50");
		
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.changeBundleQuantity(iOSInternalProjectConstants.WHEEL_SERVICE, "2");
		
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		RegularPriceMatrixScreen pricematrix = selectedservicescreen.selectMatrics(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		pricematrix.selectPriceMatrix("HOOD");
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen(appiumdriver);
		vehiclePartScreen.setSizeAndSeverity("NKL", "VERY LIGHT");
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains("Employee Simple 20%"));
		Assert.assertEquals(vehiclePartScreen.getPrice(), "$100.00");
		vehiclePartScreen.selectDiscaunt("Discount 10-20$");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		vehiclePartScreen.saveVehiclePart();

		pricematrix.clickBackButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);

		ordersummaryscreen.clickSave();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.clickSave();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("PO# is required")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		invoiceinfoscreen.setPO(iOSInternalProjectConstants.USER_PASSWORD);
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		myinvoicesscreen.clickHomeButton();
	}
	
	//Test Case 18426:Don't allow to select billed and not billed orders together in multi selection mode
	@Test(testName = "Test Case 18426:Don't allow to select billed and not billed orders together in multi selection mode", description = "Don't allow to select billed and not billed orders together in multi selection mode")
	public void testDontAlowToSelectBilleAandNotBilledOrdersTogetherInMultiSelectionMode() throws Exception {

		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		//final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		
		//Create WO1
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		/*String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "The VIN is invalid.");
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();*/
		String wonumber1 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		//vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService("VPS1");

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.clickSave();
		
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
		//Create WO2
		myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.clickAddOrderButton();

		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		/*alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "The VIN is invalid.");
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();*/
		String wonumber2 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		//vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);

		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService("VPS1");

		ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.clickSave();
		
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
			
		//Test case
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.approveWorkOrder(wonumber2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("first123");
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();
		
		myworkordersscreen.clickActionButton();		
		myworkordersscreen.selectWorkOrderForAction(wonumber1);
		//myworkordersscreen.woExistsViaSearch(wonumber1);
		myworkordersscreen.clickDoneButton();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();
		
		myworkordersscreen.clickActionButton();
		myworkordersscreen.selectWorkOrderForAction(wonumber2);
		//myworkordersscreen.woExistsViaSearch(wonumber2);
		myworkordersscreen.clickDoneButton();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 18436:Don't allow to change billed orders
	@Test(testName = "Test Case 18436:Don't allow to change billed orders", description = "Don't allow to change billed orders")
	public void testDontAlowToChangeBilledOrders() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		//final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		final String[] menuitemstoverify = { "Edit" , "Notes", "Change\nstatus", "Delete", "Create\nInvoices" };
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		
		//Create WO1
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		/*final String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "The VIN is invalid.");
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();*/
		String wonumber1 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		//vehiclescreeen.setYear(_year);
		//Thread.sleep(2000);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService("VPS1");

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.clickSave();
		
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
	
		
		//Test case
		
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);		
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("first123");
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();

		myworkordersscreen.selectWorkOrder(wonumber1);
		for (int i = 0; i < menuitemstoverify.length; i++) {
			myworkordersscreen.isMenuItemForSelectedWOExists(menuitemstoverify[i]);
		}
		myworkordersscreen.clickDetailspopupMenu();
		servicesscreen.clickCancel();	
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 18439:Change customer for invoice
	@Test(testName = "Test Case 18439:Change customer for invoice", description = "Change customer for invoice")
	public void testChangeCustomerForInvoice() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		//final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";	

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		//Create WO1
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		/*final String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "The VIN is invalid.");
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();*/
		String wonumber1 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		//vehiclescreeen.setYear(_year);
		//Thread.sleep(2000);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		RegularSelectedServiceDetailsScreen selectedservicedetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicedetailsScreen.saveSelectedServiceDetails();
		selectedservicedetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		selectedservicedetailsScreen.saveSelectedServiceDetails();
		selectedservicedetailsScreen = servicesscreen.openCustomServiceDetails("VPS1");
		selectedservicedetailsScreen.saveSelectedServiceDetails();

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.clickSave();
		
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();

		
		//Test case
		
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("first123");
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber(); 
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Helpers.waitABit(60*1000);
		myinvoicesscreen.changeCustomerForInvoive(invoicenumber, iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		Assert.assertEquals(invoiceinfoscreen.getInvoiceCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		invoiceinfoscreen.clickWO(wonumber1);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
 		Assert.assertEquals(vehiclescreeen.getWorkOrderCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickCancelWizard();
		invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.cancelWizard();
		myinvoicesscreen.clickHomeButton();
	}
	
	//Test Case 10498:Regression test: test bug with crash on "Copy Vehicle" 
	@Test(testName = "Test Case 10498:Regression test: test bug with crash on \"Copy Vehicle\"", description = "Regression test: test bug with crash on Copy Vehicle")
	public void testBugWithCrashOnCopyVehicle() throws Exception {

		final String vin = "SHDHBEVDHDHDGDVDG";
		final String vehicleinfo = "Black, BMW, 323i U";
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCarHistoryScreen carhistoryscreen = homescreen.clickCarHistoryButton();
		//carhistoryscreen.searchCar(vin);
			
		carhistoryscreen.clickCarHistoryRowByVehicleInfo(vehicleinfo);
		carhistoryscreen.clickCarHistoryMyWorkOrders();
		RegularMyWorkOrdersScreen myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.selectFirstOrder();
		myworkordersscreen.selectCopyVehicle();
		customersscreen = new RegularCustomersScreen(appiumdriver);
		//customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehiclescreen = new RegularVehicleScreen(appiumdriver);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.cancelWizard();
		myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.clickBackButton();
		carhistoryscreen = new RegularCarHistoryScreen(appiumdriver);
		carhistoryscreen.clickHomeButton();
		carhistoryscreen.clickHomeButton();
	}
	
	//Test Case 16239:Copy Inspections
	@Test(testName = "Test Case 16239:Copy Inspections", description = "Copy Inspections")
	public void testCopyInspections() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "Audi";
		final String _model = "A1";
		//final String _year = "2014";
		final String _color = "Black";
		final String mileage = "55555";
		final String fueltanklevel = "25";
		final String _type = "New";	
		final String stock = "Stock1";
		final String _ro = "123";	

		final String visualjetservice = "Price Adjustment";
		
		//Services variables
		final String _dye_price = "$10.00";
		final String _dye_adjustments = "$0.50";
		// =======================================
		final String _disk_ex_srvc_price = "$100.00";
		final String _disk_ex_srvc_adjustment = "For_SP_Cl";
		final String _disk_ex_srvc_adjustment_value = "%-5.000";
		final String _disk_ex_srvc_adjustment_value_ed = "-$5.00";
		// ======================================
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String _price = "$100.00";
		final String _discaunt_us = "Discount 10-20$";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.FOR_COPY_INSP_INSPTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		/*Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("The VIN is invalid.")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();*/
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		//vehiclescreeen.setYear(_year);
		//Thread.sleep(2000);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		RegularVisualInteriorScreen visualinteriorscreen = vehiclescreeen.selectNextScreen(RegularVisualInteriorScreen.getVisualInteriorCaption(),
				RegularVisualInteriorScreen.class);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.tapInterior();

		visualinteriorscreen = visualinteriorscreen.selectNextScreen("Future Audi Car",
				RegularVisualInteriorScreen.class);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);	
		Helpers.tapRegularCarImage();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$180.50");
		
		visualinteriorscreen.selectNextScreen(RegularVisualInteriorScreen.getVisualExteriorCaption(), RegularVisualInteriorScreen.class);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);	
		RegularVisualInteriorScreen.tapExterior();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$250.50");
			
		visualinteriorscreen.selectNextScreen("Futire Jet Car", RegularVisualInteriorScreen.class);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(visualjetservice);
		Helpers.tapRegularCarImage();
		visualinteriorscreen.selectService(visualjetservice);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$240.50");

		RegularQuestionsScreen questionsscreen = visualinteriorscreen.selectNextScreen("Test Section", RegularQuestionsScreen.class);
		questionsscreen.setEngineCondition("Really Bad");
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.setJustOnePossibleAnswer("One");
		
		questionsscreen.selectNextScreen("Follow up Requested", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.drawRegularSignature();
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.setSampleQuestion("Answers 1");
		
		questionsscreen.selectNextScreen("BATTERY PERFORMANCE", RegularQuestionsScreen.class);
		questionsscreen.setBetteryTerminalsAnswer("Immediate Attention Required");
		questionsscreen.swipeScreenUp();
		questionsscreen.setCheckConditionOfBatteryAnswer("Immediate Attention Required");
		
		questionsscreen.swipeScreenUp();
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.selectTaxPoint("Test Answer 1");
		
		//Select services
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen("Package_for_Monitor", RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE));
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), _dye_price);
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), _dye_adjustments);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE));
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), _disk_ex_srvc_price);
		selectedservicescreen.clickAdjustments();
		Assert.assertEquals(selectedservicescreen.getAdjustmentValue(_disk_ex_srvc_adjustment),
				_disk_ex_srvc_adjustment_value);
		selectedservicescreen.selectAdjustment(_disk_ex_srvc_adjustment);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), _disk_ex_srvc_adjustment_value_ed);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE));
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		// =====================================
		servicesscreen.selectSubService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		RegularPriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix);
		vehiclePartScreen.setSizeAndSeverity(_size, _severity);
		Assert.assertEquals(vehiclePartScreen.getPrice(), _price);
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.selectDiscaunt(_discaunt_us);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickCopyInspection();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
		Assert.assertEquals(vehiclescreeen.getMake(), _make);
		Assert.assertEquals(vehiclescreeen.getModel(), _model);
		
		visualinteriorscreen = vehiclescreeen.selectNextScreen(RegularVisualInteriorScreen.getVisualInteriorCaption(), RegularVisualInteriorScreen.class);
		visualinteriorscreen.selectNextScreen("Future Audi Car", RegularVisualInteriorScreen.class);
		vehiclescreeen.selectNextScreen(RegularVisualInteriorScreen.getVisualExteriorCaption(), RegularVisualInteriorScreen.class);
		visualinteriorscreen.selectNextScreen("Futire Jet Car", RegularVisualInteriorScreen.class);
		visualinteriorscreen.selectNextScreen("Follow up Requested", RegularVisualInteriorScreen.class);
		SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		Assert.assertTrue(singlepageinspectionscreen.isSignaturePresent());
		questionsscreen = questionsscreen.selectNextScreen("BATTERY PERFORMANCE", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		Assert.assertTrue(singlepageinspectionscreen.isAnswerPresent("Test Answer 1"));
		servicesscreen = questionsscreen.selectNextScreen("Package_for_Monitor", RegularServicesScreen.class);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));
		
		
		servicesscreen.clickSave();
		
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionPriceValue(), "$837.99");
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 16507:Create inspection from WO
	@Test(testName = "Test Case 16507:Create inspection from WO", description = "Create inspection from WO")
	public void testCreateInspectionFromWO() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "Buick";
		final String _model = "Electra";
		//final String _year = "2014";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		final String pricevalue = "0";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		//Create WO1
		Instant star = Instant.now();
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE) ;
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);

		String wonumber1 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
	
		//vehiclescreeen.setYear(_year);
		
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);ordersummaryscreen.clickSave();
		
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
		
		//Test case
		myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber1), PricesCalculations.getPriceRepresentation(pricevalue));
		myworkordersscreen.selectWorkOrderNewInspection(wonumber1);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.cancelOrder();
		myworkordersscreen.clickHomeButton();		
	}
	
	@Test(testName = "Test Case 26266:Create Invoice with two WOs and copy vehicle", description = "Create Invoice with two WOs and copy vehicle")
	public void testCreateInvoiceWithTwoWOsAndCopyVehicle() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "Buick";
		final String _model = "Electra";
		//final String _year = "2014";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		final String[] vehicleparts = { "Cowl, Other", "Hood", };
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		//Create WO1
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber1 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		//vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.saveWizardAndAcceptAlert();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getMake(), _make);
		Assert.assertEquals(vehiclescreeen.getModel(), _model);
		//Assert.assertEquals(vehiclescreeen.getYear(), _year);
		ordersummaryscreen = vehiclescreeen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Yes"))
				.click();
		ordersummaryscreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setPO("23");
		invoiceinfoscreen.addWorkOrder(wonumber1);
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23401:Test 'Change customer' option for inspection
	@Test(testName = "Test Case 23401:Test 'Change customer' option for inspection", description = "Test 'Change customer' option for inspection")
	public void testChangeCustomerOptionForInspection() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		//resrtartApplication();
		//RegularMainScreen mainscreen = new RegularMainScreen(appiumdriver);
		//RegularHomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_CHANGE_INSPTYPE);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen(appiumdriver);
		RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(),
				RegularVehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION, RegularClaimScreen.class);
		claimscreen.selectInsuranceCompany("USG");
		RegularQuestionsScreen questionsscreen = claimscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		vehiclescreeen.saveWizard();
		myinspectionsscreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		RegularVisualInteriorScreen visualscreen = new RegularVisualInteriorScreen(appiumdriver);
		vehiclescreeen = visualscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
		Assert.assertEquals(vehiclescreeen.getInspectionCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		vehiclescreeen.saveWizard();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 23402:Test 'Change customer' option for Inspection as change 'Wholesale' to 'Retail' and vice versa'
	@Test(testName = "Test Case 23402:Test 'Change customer' option for Inspection as change 'Wholesale' to 'Retail' and vice versa'", description = "Test 'Change customer' option for Inspection as change 'Wholesale' to 'Retail' and vice versa'")
	public void testChangeCustomerOptionForInspectionAsChangeWholesaleToRetailAndViceVersa() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_CHANGE_INSPTYPE);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen(appiumdriver);
		RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(),
				RegularVehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION, RegularClaimScreen.class);
		claimscreen.selectInsuranceCompany("USG");
		RegularQuestionsScreen questionsscreen = claimscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");	
		vehiclescreeen.saveWizard();
		myinspectionsscreen.selectInspection(inspectionnumber);
		myinspectionsscreen.clickChangeCustomerpopupMenu();		
		myinspectionsscreen.customersPopupSwitchToRetailMode();
		myinspectionsscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
				
		myinspectionsscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		RegularVisualInteriorScreen visualscreen = new RegularVisualInteriorScreen(appiumdriver);
		vehiclescreeen = visualscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
		Assert.assertTrue(vehiclescreeen.getInspectionCustomer().contains(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER));
		vehiclescreeen.saveWizard();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 23421:Test 'Change customer' option for Inspections based on type with preselected Companies
	@Test(testName = "Test Case 23421:Test 'Change customer' option for Inspections based on type with preselected Companies", description = "Test 'Change customer' option for Inspections based on type with preselected Companies")
	public void testChangeCustomerOptionForInspectionsBasedOnTypeWithPreselectedCompanies() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";

		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.TYPEWITHPRESELECTEDCOMPANIES_INSPTYPE);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen(appiumdriver);
		RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(),
				RegularVehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION, RegularClaimScreen.class);
		claimscreen.selectInsuranceCompany("USG");	
		claimscreen.saveWizard();
		myinspectionsscreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		visualInteriorScreen = new RegularVisualInteriorScreen(appiumdriver);
		vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
		Assert.assertEquals(vehiclescreeen.getInspectionCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		vehiclescreeen.saveWizard();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 23422:Test 'Change customer' option for Work Order
	@Test(testName = "Test Case 23422:Test 'Change customer' option for Work Order", description = "Test 'Change customer' option for Work Order")
	public void testChangeCustomerOptionForWorkOrder() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WO_CLIENT_CHANGING_ON);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService("3/4\" - Penny Size");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		
		ordersummaryscreen.saveWizard();
        myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancel();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23423:Test 'Change customer' option for WO based on type with preselected Companies
	@Test(testName = "Test Case 23423:Test 'Change customer' option for WO based on type with preselected Companies", description = "Test 'Change customer' option for WO based on type with preselected Companies")
	public void testChangeCustomerOptionForWOBasedOnTypeWithPreselectedCompanies() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";

		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WO_WITH_PRESELECTED_CLIENTS);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService("3/4\" - Penny Size");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("4");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("4"));
		ordersummaryscreen.saveWizard();
		
		Helpers.waitABit(45*1000);
		//testlogger.log(LogStatus.INFO, wonumber);
		myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancel();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23424:Test 'Change customer' option for WO as change 'Wholesale' to 'Retail' and vice versa'
	@Test(testName = "Test Case 23424:Test 'Change customer' option for WO as change 'Wholesale' to 'Retail' and vice versa'", description = "Test 'Change customer' option for WO as change 'Wholesale' to 'Retail' and vice versa'")
	public void testChangeCustomerOptionForWOAsChangeWholesaleToRetailAndViceVersa() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_CLIENT_CHANGING_ON);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService("3/4\" - Penny Size");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("4");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("4"));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrder(wonumber);	
		myworkordersscreen.clickChangeCustomerPopupMenu();
		myworkordersscreen.customersPopupSwitchToRetailMode();
		myworkordersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myworkordersscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertTrue(vehiclescreeen.getWorkOrderCustomer().contains(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER));
		servicesscreen.clickCancel();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23428:'Block for the same VIN' is ON. Verify 'Duplicate VIN ' message when create 2 WO with one VIN
	@Test(testName = "Test Case 23428:'Block for the same VIN' is ON. Verify 'Duplicate VIN ' message when create 2 WO with one VIN", description = "'Block for the same VIN' is ON. Verify 'Duplicate VIN ' message when create 2 WO with one VIN")
	public void testBlockForTheSameVINIsONVerifyDuplicateVINMessageWhenCreate2WOWithOneVIN() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_VIN_ON);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);

		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("4");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("4"));
		ordersummaryscreen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("You can't create Work Order for type '" + iOSInternalProjectConstants.WOTYPE_BLOCK_VIN_ON + "' for VIN '" + VIN + "' because it was already created"));
		ordersummaryscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23459:'Block for the same Services' is ON. Verify 'Duplicate services message' when create 2 WO with one service
	@Test(testName = "Test Case 23459:'Block for the same Services' is ON. Verify 'Duplicate services message' when create 2 WO with one service", description = "'Block for the same Services' is ON. Verify 'Duplicate services message' when create 2 WO with one service")
	public void testBlockForTheSameServicesIsONVerifyDuplicateServicesMessageWhenCreate2WOWithOneService() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", RegularServicesScreen.class);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.selectSubService("AMoneyService_AdjustHeadlight");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingCancel();
		
		//Helpers.text_exact("Cancel").click();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23432:WO: Test 'Edit' option of 'Duplicate services' message for WO
	@Test(testName="Test Case 23432:WO: Test 'Edit' option of 'Duplicate services' message for WO", description = "'WO: Test 'Edit' option of 'Duplicate services' message for WO")
	public void testEditOptionOfDuplicateServicesMessageForWO() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", RegularServicesScreen.class);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.selectSubService("AMoneyService_AdjustHeadlight");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingEdit();
		ordersummaryscreen.swipeScreenLeft();
		servicesscreen = ordersummaryscreen.selectNextScreen("All Services", RegularServicesScreen.class);
		servicesscreen.removeSelectedService("AMoneyService_AdjustHeadlight");
		Assert.assertFalse(servicesscreen.checkServiceIsSelected("AMoneyService_AdjustHeadlight"));
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23433:WO: Test 'Override' option of 'Duplicate services' message for WO.
	@Test(testName="Test Case 23433:WO: Test 'Override' option of 'Duplicate services' message for WO", description = "'WO: Test 'Override' option of 'Duplicate services' message for WO.")
	public void testOverrideOptionOfDuplicateServicesMessageForWO() throws Exception {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", RegularServicesScreen.class);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.selectSubService("AMoneyService_AdjustHeadlight");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("4");	
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingOverride();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23434:WO: Test 'Cancel' option of 'Duplicate services' message for WO
	@Test(testName="Test Case 23434:WO: Test 'Cancel' option of 'Duplicate services' message for WO", description = "'WO: Test 'Cancel' option of 'Duplicate services' message for WO.")
	public void testCancelOptionOfDuplicateServicesMessageForWO() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";		
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", RegularServicesScreen.class);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.selectSubService("AMoneyService_AdjustHeadlight");
		
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingCancel();
		Assert.assertFalse(myworkordersscreen.woExists(wonumber));

		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23966:Inspections: Test saving inspections with three matrix
	//Test Case 24022:Inspections: Test saving inspection copied from one with 3 matrix price
	@Test(testName="Test Case 23966:Inspections: Test saving inspections with three matrix, "
			+ "Test Case 24022:Inspections: Test saving inspection copied from one with 3 matrix price", description = "'Inspections: Test saving inspections with three matrix")
	public void testSavingInspectionsWithThreeMatrix() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		final String _type = "New";
		final String _po = "1111";
		
		final String _pricematrix1 = "Hood";
		final String _pricematrix2 = "Left Roof Rail";
		final String _pricematrix3 = "Back Glass";
		final String _pricematrix4 = "Front Bumper";
		final String _pricematrix5 = "Roof";
	
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.VITALY_TEST_INSPTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setPO(_po);
		String inspnumber = vehiclescreeen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION, RegularClaimScreen.class);
		claimscreen.selectInsuranceCompany("USG");	
		claimscreen.setClaim("QWERTY");
		claimscreen.setAccidentDate();
		RegularVisualInteriorScreen visualinteriorscreen = claimscreen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualInteriorCaption(), RegularVisualInteriorScreen.class);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
		RegularVisualInteriorScreen.tapInteriorWithCoords(150, 150);
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$520.00");

		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		visualinteriorscreen = questionsscreen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualExteriorCaption(), RegularVisualInteriorScreen.class);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		RegularVisualInteriorScreen.tapExteriorWithCoords(100, 100);
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$570.00");
		Assert.assertEquals(visualinteriorscreen.getSubTotalPrice(), "$100.00");

		RegularPriceMatrixScreen pricematrix = visualinteriorscreen.selectNextScreen("Default", RegularPriceMatrixScreen.class);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix1);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("SR_S5_Mt_Money");
		vehiclePartScreen.selectDiscaunt("SR_S5_Mt_Money_DE_TE");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(vehiclePartScreen.getDiscauntPriceAndValue(iOSInternalProjectConstants.SR_S1_MONEY), "$2,000.00 x 3.00");
		vehiclePartScreen.saveVehiclePart();
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix2);
		vehiclePartScreen.setSizeAndSeverity(RegularPriceMatrixScreen.NKL_SIZE, "VERY LIGHT");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.selectNextScreen("Matrix Labor", RegularPriceMatrixScreen.class);
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix3);
		vehiclePartScreen.setSizeAndSeverity(RegularPriceMatrixScreen.DIME_SIZE, "LIGHT");
		vehiclePartScreen.saveVehiclePart();

		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix4);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Little Service");
		vehiclePartScreen.selectDiscaunt("Disc_Ex_Service1");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		vehiclePartScreen.setTime("1");
		vehiclePartScreen.saveVehiclePart();

		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("All Services", RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Roof");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Front Bumper");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		servicesscreen.clickAddServicesButton();
		
		pricematrix = servicesscreen.selectNextScreen("Test matrix33", RegularPriceMatrixScreen.class);
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix5);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		vehiclePartScreen.saveVehiclePart();

		servicesscreen = pricematrix.selectNextScreen("Test_Package_3PrMatrix", RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService("SR_S2_MoneyDisc_TE");
		servicesscreen.clickAddServicesButton();

		visualinteriorscreen = servicesscreen.selectNextScreen("New_Test_Image", RegularVisualInteriorScreen.class);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		Helpers.tapRegularCarImage();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$13,145.50");
		Assert.assertEquals(visualinteriorscreen.getSubTotalPrice(), "$80.00");
		
		visualinteriorscreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		pricematrix = vehiclescreeen.selectNextScreen("Default", RegularPriceMatrixScreen.class);
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix1, "$6,100.00"));
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix2, "$75.00"));
		
		pricematrix.selectNextScreen("Matrix Labor", RegularPriceMatrixScreen.class);
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix3, "$25.00"));
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix4, "$1,105.50"));
		pricematrix.selectNextScreen("Test matrix33", RegularPriceMatrixScreen.class);
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix5, "$2,000.00"));
		servicesscreen.cancelWizard();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		String copiedinspnumber = vehiclescreeen.getInspectionNumber();
		servicesscreen.saveWizard();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(copiedinspnumber));
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 24075:SR: Test that selected services on SR are copied to Inspection based on SR
	@Test(testName="Test Case 24075:SR: Test that selected services on SR are copied to Inspection based on SR", description = "Test that selected services on SR are copied to Inspection based on SR")
	public void testThatSelectedServicesOnSRAreCopiedToInspectionBasedOnSR() throws Exception {
		final String VIN  = "1D7HW48NX6S507810";
		
		/*RegularSettingsScreen settingscreen =  homescreen.clickSettingsButton();
		settingscreen.setShowAllServicesOn();
		homescreen = settingscreen.clickHomeButton();*/
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		homescreen = customersscreen.clickHomeButton();
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		customersscreen = new RegularCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestsscreen.selectInspectionType("SR_only_Acc_Estimate");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.clickAddServicesButton();
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		servicedetailsscreen.setServiceQuantityValue("14");
		servicedetailsscreen.saveSelectedServiceDetails();
		//servicesscreen.setSelectedServiceRequestServicePrice(iOSInternalProjectConstants.DYE_SERVICE, "14");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSPTYPE_FOR_SR_INSPTYPE);
	
		String inspectnumber = vehiclescreeen.getInspectionNumber();		
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.DYE_SERVICE, "$10.00 x 14.00"));
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.VPS1_SERVICE, "%20.000"));
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.WHEEL_SERVICE, "$70.00 x 1.00"));
			
		servicesscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
	}
	
	//Test Case 24657:WO: Test that auto-saved WO is created correctly
	//Test Case 23435:WO: Test 'Continue' option of 'Auto-saved' WO
	//Test Case 23436:WO: Test 'Discard' option of 'Auto-saved' WO
	@Test(testName="Test Case 24657:WO: Test that auto-saved WO is created correctly, " +
			"Test Case 23435:WO: Test 'Continue' option of 'Auto-saved' WO, " + 
			"Test Case 23436:WO: Test 'Discard' option of 'Auto-saved' WO", description = "WO: Test that auto-saved WO is created correctly")
	public void testThatAutoSavedWOIsCreatedCorrectly()
			throws Exception {
		final String VIN  = "1FMFU18L53LC13897";

		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_CLIENT_CHANGING_ON);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Ford", "Expedition", "2003");
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		Helpers.waitABit(30*1000);
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		RegularMainScreen mainscreen = new RegularMainScreen(appiumdriver);
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertTrue(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.selectContinueWorkOrder(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getWorkOrderNumber(), wonumber);

		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		mainscreen = new RegularMainScreen(appiumdriver);
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertTrue(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.selectDiscardWorkOrder(wonumber);
		Assert.assertFalse(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 21578:SR: Add appointment to Service Request 
	@Test(testName = "Test Case 21578:SR: Add appointment to Service Request", description = "SR: Add appointment to Service Request")
	public void testSRAddAppointmentToServiceRequest() throws Exception {
		final String VIN = "QWERTYUI123";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
					
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
			
		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
			
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_EST_WO_REQ_SRTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			
		vehiclescreeen.setVIN(VIN);
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		//Helpers.swipeScreenUp();
		Helpers.drawRegularQuestionsSignature();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();		
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSave();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();
		final String fromapp = servicerequestsscreen.getFromAppointmetValue();
		final String toapp = servicerequestsscreen.getToAppointmetValue();
		
		servicerequestsscreen.setSubjectAppointmet("SR-APP");
		servicerequestsscreen.setAddressAppointmet("Maidan");
		servicerequestsscreen.setCityAppointmet("Kiev");
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		homescreen = servicerequestsscreen.clickHomeButton();
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");	
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Would you like to reject  selected service request?");
		servicerequestsscreen.clickHomeButton();
	}
	
	//Test Case 24677:SR: Verify 'Summary' action for appointment on SR's calendar
	@Test(testName = "Test Case 24677:SR: Verify 'Summary' action for appointment on SR's calendar", description = "SR: Verify 'Summary' action for appointment on SR's calendar")
	public void testSRVerifySummaryActionForAppointmentOnSRsCalendar() throws Exception {
		final String VIN = "QWERTYUI123";
		final String srappsubject = "SR-APP";
		final String srappaddress = "Maidan";
		final String srappcity = "Kiev";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
				
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
				
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_EST_WO_REQ_SRTYPE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
				
		vehiclescreeen.setVIN(VIN);
		/*Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("The VIN is invalid.")).isDisplayed());
		appiumdriver.findElement(MobileBy.name("Close")).click();*/

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		//Helpers.swipeScreenUp();
		Helpers.drawRegularQuestionsSignature();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSave();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		
		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();
		final String fromapp = servicerequestsscreen.getFromAppointmetValue();
		final String toapp = servicerequestsscreen.getToAppointmetValue();
			
		servicerequestsscreen.setSubjectAppointmet(srappsubject);
		servicerequestsscreen.setAddressAppointmet(srappaddress);
		servicerequestsscreen.setCityAppointmet(srappcity);
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		homescreen = servicerequestsscreen.clickHomeButton();
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);		
		servicerequestsscreen.selectDetailsRequestAction();	

		//String summaryapp = servicerequestsscreen.getSummaryAppointmentsInformation();
		String expectedsummaryapp = "Subject: " + srappsubject + ", In: " + fromapp + ", Out: " + toapp + ", Location: " + srappaddress 
				+ ", " + srappcity + ", US";
		Assert.assertTrue(servicerequestsscreen.isSRSummaryAppointmentsInformation());
		
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Would you like to reject  selected service request?");
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 25403:WO Regular: Verify that only assigned services on Matrix Panel is available as additional services", description = "WO Regular: Verify that only assigned services on Matrix Panel is available as additional services")
	public void testWOVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_PRICE_MATRIX);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mercedes-Benz", "Sprinter", "2014");
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Zayats test pack",
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");	
		servicesscreen.selectPriceMatrices("VP1 zayats");		
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Test service zayats"));
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Wheel"));
		vehiclePartScreen.saveVehiclePart();
		vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Dye"));
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX));
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		servicesscreen =  questionsscreen.selectNextScreen("Zayats test pack",
				RegularServicesScreen.class);
		servicesscreen.cancelWizard();
		
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 25011:Inspections: verify that only assigned services on Matrix Panel is available as additional services", description = "Inspections: verify that only assigned services on Matrix Panel is available as additional services")
	public void testInspectionsVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();

		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_TYPE_FOR_PRICE_MATRIX);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mercedes-Benz", "Sprinter", "2014");
		String inspnum = vehiclescreeen.getInspectionNumber();
		RegularPriceMatrixScreen pricematrix = vehiclescreeen.selectNextScreen("Price Matrix Zayats",
				RegularPriceMatrixScreen.class);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Test service zayats"));
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Wheel"));
		vehiclePartScreen.saveVehiclePart();
		vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Dye"));
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.saveVehiclePart();
		RegularQuestionsScreen questionsscreen = pricematrix.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");

		questionsscreen.saveWizard();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspnum));
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 25421:WO: Verify that on Invoice 'Summary' main service of the panel is displayed as first then additional services", description = "WO HD: Verify that on Invoice 'Summary' main service of the panel is displayed as first then additional services")
	public void testWOVerifyThatOnInvoiceSummaryMainServiceOfThePanelIsDisplayedAsFirstThenAdditionalServices()
			throws Exception {
		
		final String VIN  = "2C3CDXBG2EH174681";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Charger", "2014");
		String wonum = vehiclescreeen.getInspectionNumber();
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Zayats test pack",
				RegularServicesScreen.class);
		servicesscreen.selectSubService("Test service price matrix");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("Price Matrix Zayats");
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.saveVehiclePart();

		vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");

		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		pricematrix.clickBackButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected("Test service price matrix"));
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");	
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForApprove(wonum);
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.clickApproveButton();
		myworkordersscreen.selectWorkOrderForAction(wonum);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("12345");
		final String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		homescreen = myworkordersscreen.clickHomeButton();
		
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickSummaryPopup();
		Assert.assertTrue(myinvoicesscreen.isSummaryPDFExists());
		myinvoicesscreen.clickHomeButton();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26054:WO Monitor: Regular - Create WO for monitor", description = "WO Monitor: Regular - Create WO for monitor")
	public void testWOMonitorCreateWOForMonitor()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Package_for_Monitor", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.woExists(wonum);
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26091:WO Monitor: Regular - Verify that it is not possible to change Service Status before Start Service", description = "WO Monitor: Regular - Verify that it is not possible to change Service Status before Start Service")
	public void testWOMonitorVerifyThatItIsNotPossibleToChangeServiceStatusBeforeStartService()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Package_for_Monitor", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);
		
		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);
		
		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickServiceStatusCell();		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You must start the service before you can change its status."));
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		ordermonitorscreen.setCompletedServiceStatus();
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.WHEEL_SERVICE, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26092:WO Monitor: Regular - Verify that it is not possible to change Phase Status before Start phase", description = "WO Monitor: HD - Verify that it is not possible to change Phase Status before Start phase")
	public void testWOMonitorVerifyThatItIsNotPossibleToChangePhaseStatusBeforeStartPhase()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Package_for_Monitor", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);
		
		homescreen = myworkordersscreen.clickHomeButton();
		
		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();

		teamworkordersscreen.clickOnWO(wonum);
		
		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();

		Assert.assertTrue(ordermonitorscreen.isRepairPhaseExists());
		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonExists());
		ordermonitorscreen.clicksRepairPhaseLine();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You must start the phase before you can change its status."));
		ordermonitorscreen.clickStartPhaseButton();

		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertFalse(ordermonitorscreen.isStartPhaseButtonExists());
		Assert.assertTrue(ordermonitorscreen.isServiceStartDateExists());
		
		ordermonitorscreen.clickServiceStatusCell();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You cannot change the status of services for this phase. You can only change the status of the whole phase."));
		ordermonitorscreen.clickServiceDetailsDoneButton();
		
		ordermonitorscreen.clicksRepairPhaseLine();
		ordermonitorscreen.clickCompletedPhaseCell();
		
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DISC_EX_SERVICE1, "Completed");
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DYE_SERVICE, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}	
	
	@Test(testName="Test Case 26094:WO Monitor: Regular - Verify that Start date is set when Start Service", description = "WO Monitor: Regular - Verify that Start date is set when Start Service")
	public void testWOMonitorVerifyThatStartDateIsSetWhenStartService()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Package_for_Monitor", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);
		
		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);		
		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertFalse(ordermonitorscreen.isServiceStartDateExists());
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isServiceStartDateExists());
		ordermonitorscreen.clickServiceDetailsDoneButton();
		
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}
	
	//@Test(testName="Test Case 26016:WO Monitor: Regular - Verify that for % service message about change status is not shown", description = "WO Monitor: Regular - Verify that for % service message about change status is not shown")
	public void testWOMonitorVerifyThatForPercentServiceMessageAboutChangeStatusIsNotShown()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getInspectionNumber();
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Package_for_Monitor", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);
		
		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);
		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.VPS1_SERVICE);
		ordermonitorscreen.clickServiceDetailsDoneButton();
		
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26013:WO Monitor: Regular - Verify that when change Status for Phase with 'Do not track individual service statuses' ON Phase status is set to all services assigned to phase", 
			description = "WO Monitor: Regular - Verify that when change Status for Phase with 'Do not track individual service statuses' ON Phase status is set to all services assigned to phase")
	public void testWOMonitorVerifyThatWhenChangeStatusForPhaseWithDoNotTrackIndividualServiceStatusesONPhaseStatusIsSetToAllServicesAssignedToPhase()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String _price = "$100.00";
		final String _discaunt_us = "Discount 10-20$";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Package_for_Monitor", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		RegularPriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix);
		vehiclePartScreen.setSizeAndSeverity(_size, _severity);
		Assert.assertEquals(vehiclePartScreen.getPrice(), _price);
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.selectDiscaunt(_discaunt_us);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);
		
		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);
		
		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		ordermonitorscreen.setCompletedServiceStatus();
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.WHEEL_SERVICE, "Completed");

		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonExists());
		ordermonitorscreen.clickStartPhaseButton();
		
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		ordermonitorscreen.clickServiceStatusCell();;
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You cannot change the status of services for this phase. You can only change the status of the whole phase."));
		ordermonitorscreen.clickServiceDetailsDoneButton();
		
		ordermonitorscreen.clickCompletedPhaseCell();
		
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE, "Completed");
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DISC_EX_SERVICE1, "Completed");
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DYE_SERVICE, "Completed");
				
		
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		ordermonitorscreen.verifyServiceStatusInPopup(iOSInternalProjectConstants.DYE_SERVICE, "Completed");
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		ordermonitorscreen.verifyServiceStatusInPopup(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE, "Completed");
		
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}	
	
	@Test(testName="Test Case 26093:WO Monitor: Regular - Verify that %Service is not Started when Start phase", 
			description = "WO Monitor: Regular - Verify that %Service is not Started when Start phase")
	public void testWOMonitorVerifyThatPercentageServiceIsNotStartedWhenStartPhase()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String _price = "$100.00";
		final String _discaunt_us = "Discount 10-20$";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getInspectionNumber();
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Package_for_Monitor", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		RegularPriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix);
		vehiclePartScreen.setSizeAndSeverity(_size, _severity);
		Assert.assertEquals(vehiclePartScreen.getPrice(), _price);
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.selectDiscaunt(_discaunt_us);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		RegularPriceMatricesScreen priceMatricesScreen = new RegularPriceMatricesScreen(appiumdriver);
		priceMatricesScreen.clickBackButton();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);
		
		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);
		
		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		
		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.WHEEL_SERVICE);
		ordermonitorscreen.setCompletedServiceStatus();
		//ordermonitorscreen.clickServiceDetailsDoneButton();
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.WHEEL_SERVICE, "Completed");
		
		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonPresent());
		ordermonitorscreen.clickPhaseStatusCell();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Order Monitor It is impossible to change phase status until you start phase.");
		ordermonitorscreen.clickStartPhase();
		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.DYE_SERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DYE_SERVICE, "Completed");
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DISC_EX_SERVICE1, "Completed");
		
		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		Assert.assertFalse(ordermonitorscreen.isServiceStartDateExists());
		ordermonitorscreen.verifyPanelStatusInPopup(iOSInternalProjectConstants.TEST_TAX_SERVICE, "Completed");
		
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}	
	
	@Test(testName="Test Case 28473:Invoices: HD - Verify that red 'A' icon is present for invoice with customer approval ON and no signature", 
			description = "Invoices: Regular - Verify that red 'A' icon is present for invoice with customer approval ON and no signature")
	public void testRegularVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalONAndNoSignature()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";

		//RegularMainScreen mainscreen = new RegularMainScreen(appiumdriver);
		//homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Zayats test pack", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28476:Invoices: HD&Regular - Verify that grey 'A' icon is present for invoice with customer approval OFF and no signature", 
			description = "Invoices: Regular - Verify that grey 'A' icon is present for invoice with customer approval OFF and no signature")
	public void testRegularVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalOFFAndNoSignature()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";
		//appiumdriverInicialize();
		//RegularMainScreen mainscreen = new RegularMainScreen(appiumdriver);
		//homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
				
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Zayats test pack", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28478:Invoices: Regular - Verify that 'A' icon is not present for invoice when signature exists", 
			description = "Invoices: Regular - Verify that 'A' icon is not present for invoice when signature exists")
	public void testRegularVerifyThatAIconIsNotPresentForInvoiceWhenSignatureExists()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		RegularServicesScreen servicesscreen =  vehiclescreeen.selectNextScreen("Zayats test pack", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumberapproveon = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenumberapproveon));
		myinvoicesscreen.selectInvoiceForApprove(invoicenumberapproveon);
		myinvoicesscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.clickApproveButton();
		approveinspscreen.drawApprovalSignature();
		//approveinspscreen.clickDoneButton();
		myinvoicesscreen = new RegularMyInvoicesScreen(appiumdriver);
		
		Assert.assertFalse(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumberapproveon));
		myinvoicesscreen.clickHomeButton();
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval OFF
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		servicesscreen =  vehiclescreeen.selectNextScreen("Zayats test pack", RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		
		invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumbeapprovaloff = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumbeapprovaloff);
		myinvoicesscreen.selectInvoiceForApprove(invoicenumbeapprovaloff);
		myinvoicesscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.clickApproveButton();
		approveinspscreen.drawApprovalSignature();
		//approveinspscreen.clickDoneButton();
		Assert.assertFalse(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumbeapprovaloff));	
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 29022:SR: Regular - Verify that Reject action is displayed for SR in Status Scheduled (Insp or WO) and assign for Tech", 
			description = "Test Case 29022:SR: Regular - Verify that Reject action is displayed for SR in Status Scheduled (Insp or WO) and assign for Tech")
	public void testSRRegularVerifyThatRejectActionIsDisplayedForSRInStatusScheduledInspOrWOAndAssignForTech()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		//Create first SR
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_ONLY_ACC_ESTIMATE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.clickAddServicesButton();
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("No"))
				.click();

		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber1 = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber1);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();
		//Create second SR
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_WO_AUTO_CREATE);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.saveWizard();

		String srnumber2 = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber2), "Scheduled");	
		servicerequestsscreen.selectServiceRequest(srnumber2);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();

		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 29029:SR: Regular - Verify that Reject action is displayed for SR in Status OnHold (Insp or WO) and assign for Tech", 
			description = "Test Case 29029:SR: Regular - Verify that Reject action is displayed for SR in Status OnHold (Insp or WO) and assign for Tech")
	public void testSRRegularVerifyThatRejectActionIsDisplayedForSRInStatusOnHoldInspOrWOAndAssignForTech()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_ALL_PHASES);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.clickAddServicesButton();
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		questionsscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "On Hold");
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_MODE);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		String inspectnumber = vehiclescreeen.getInspectionNumber();
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickSaveAsFinal();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));
		teaminspectionsscreen.clickActionButton();
		teaminspectionsscreen.selectInspectionForAction(inspectnumber);
		
		teaminspectionsscreen.clickApproveInspections();
		teaminspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspectnumber);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		teaminspectionsscreen.clickBackButton();
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
		boolean onhold = false;
		for (int i= 0; i < 7; i++) {
			servicerequestsscreen = homescreen.clickServiceRequestsButton();
			if (!servicerequestsscreen.getServiceRequestStatus(srnumber).equals("On Hold")) {
				servicerequestsscreen.clickHomeButton();
				Helpers.waitABit(30*1000);
			} else {
				
				onhold = true;
				break;
			}
		}
		
		Assert.assertTrue(onhold);	
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 29082:SR: Regular - Verify that Reject action is not displayed for SR in Status OnHold (Insp or WO) and not assign for Tech", 
			description = "Test Case 29082:SR: Regular - Verify that Reject action is not displayed for SR in Status OnHold (Insp or WO) and not assign for Tech")
	public void testSRRegularVerifyThatRejectActionIsNotDisplayedForSRInStatusOnHoldInspOrWOAndNotAssignForTech()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage srlistwebpage = operationspage.clickNewServiceRequestList();
		srlistwebpage.selectAddServiceRequestsComboboxValue(iOSInternalProjectConstants.SR_ALL_PHASES);
		srlistwebpage.clickAddServiceRequestButton();
		
		srlistwebpage.clickCustomerEditButton();
		srlistwebpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		srlistwebpage.clickDoneButton();
		
		srlistwebpage.clickVehicleInforEditButton();
		srlistwebpage.setServiceRequestVIN(VIN);
		srlistwebpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		srlistwebpage.clickDoneButton();
		
		srlistwebpage.saveNewServiceRequest();
		srlistwebpage.acceptFirstServiceRequestFromList();
		
		DriverBuilder.getInstance().getDriver().quit();

		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		RegularMainScreen mainscr = new RegularMainScreen(appiumdriver);
		mainscr.updateDatabase();
		RegularHomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectCancelAction();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 29083:SR: Regular - Verify that Reject action is not displayed for SR in Status Scheduled (Insp or WO) and not assign for Tech", 
			description = "SR: Regular - Verify that Reject action is not displayed for SR in Status Scheduled (Insp or WO) and not assign for Tech")
	public void testSRRegularVerifyThatRejectActionIsNotDisplayedForSRInStatusScheduledInspOrWOAndNotAssignForTech()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";
		final String[] servicestoadd = { "VPS1", "Dye" };
		final String[] servicestoadd2 = { "Oksi_Service_PP_Panel", "Oksi_Service_PP_Vehicle" };
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage srlistwebpage = operationspage.clickNewServiceRequestList();
		srlistwebpage.selectAddServiceRequestsComboboxValue(iOSInternalProjectConstants.SR_ONLY_ACC_ESTIMATE);
		srlistwebpage.clickAddServiceRequestButton();
		
		srlistwebpage.clickCustomerEditButton();
		srlistwebpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		srlistwebpage.clickDoneButton();
		
		srlistwebpage.clickVehicleInforEditButton();
		srlistwebpage.setServiceRequestVIN(VIN);
		srlistwebpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		srlistwebpage.clickDoneButton();
		srlistwebpage.addServicesToServiceRequest(servicestoadd);
		srlistwebpage.saveNewServiceRequest();
		String srnumber = srlistwebpage.getFirstInTheListServiceRequestNumber();
		srlistwebpage.acceptFirstServiceRequestFromList();
		
		DriverBuilder.getInstance().getDriver().quit();
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularMainScreen mainscr = homescreen.clickLogoutButton();
		mainscr.updateDatabase();
		RegularHomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectCancelAction();
		servicerequestsscreen.clickHomeButton();
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		operationspage = backofficeheader.clickOperationsLink();
		srlistwebpage = operationspage.clickNewServiceRequestList();
		srlistwebpage.selectAddServiceRequestsComboboxValue(iOSInternalProjectConstants.SR_TYPE_WO_AUTO_CREATE);
		srlistwebpage.clickAddServiceRequestButton();
		
		srlistwebpage.clickCustomerEditButton();
		srlistwebpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		srlistwebpage.clickDoneButton();
		
		srlistwebpage.clickVehicleInforEditButton();
		srlistwebpage.setServiceRequestVIN(VIN);
		srlistwebpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		srlistwebpage.clickDoneButton();
		srlistwebpage.addServicesToServiceRequest(servicestoadd2);
		srlistwebpage.saveNewServiceRequest();
		srnumber = srlistwebpage.getFirstInTheListServiceRequestNumber();
		srlistwebpage.acceptFirstServiceRequestFromList();
		
		DriverBuilder.getInstance().getDriver().quit();
		homescreen = new RegularHomeScreen(appiumdriver);
		mainscr = homescreen.clickLogoutButton();
		mainscr.updateDatabase();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectEditServiceRequestAction();
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertTrue(vehiclescreeen.getTechnician() == null);
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.saveWizard();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33165:WO: Regular - Not multiple Service with required Panels is added one time to WO after selecting", 
			description = "WO: Regular - Not multiple Service with required Panels is added one time to WO after selecting")
	public void testWORegularNotMultipleServiceWithRequiredPanelsIsAddedOneTimeToWOAfterSelecting() throws Exception {
				
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
			
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.selectVehiclePart("Deck Lid");
		selectedservicescreen.saveSelectedServiceDetails();
		String alerttext = selectedservicescreen.saveSelectedServiceDetailsWithAlert();
		servicesscreen.clickAddServicesButton();
		Assert.assertTrue(alerttext.contains("You can add only one service '" + iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE + "'"));
		Assert.assertEquals(servicesscreen.getNumberOfSelectedServices(), 1);

		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.selectVehiclePart("Deck Lid");
		selectedservicescreen.saveSelectedServiceDetails();
		alerttext = selectedservicescreen.saveSelectedServiceDetailsWithAlert();
		Assert.assertTrue(alerttext.contains("You can add only one service '" + iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE + "'"));
		Assert.assertEquals(servicesscreen.getNumberOfSelectedServices(), 1);
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 34562:WO: Verify that Bundle Items are shown when create WO from inspection", description = "WO: Verify that Bundle Items are shown when create WO from inspection")
	public void testRegularWOVerifyThatBundleItemsAreShownWhenCreateWOFromInspection()
			throws Exception {
		
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen(appiumdriver);		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();	
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickAddServicesButton();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.approveInspectionApproveAllAndSignature();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber);
		myinspectionsscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen(appiumdriver);
		Assert.assertTrue(selectedservicebundlescreen.checkBundleIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(selectedservicebundlescreen.checkBundleIsNotSelected(iOSInternalProjectConstants.DYE_SERVICE));
		selectedservicebundlescreen.clickServicesIcon();
		Assert.assertTrue(selectedservicebundlescreen.isBundleServiceExists(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(selectedservicebundlescreen.isBundleServiceExists(iOSInternalProjectConstants.DYE_SERVICE));
		selectedservicebundlescreen.clickCloseServicesPopup();
		selectedservicebundlescreen.clickCancel();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}
	

	@Test(testName="Test Case 31743:SR: Regular - Verify that when option 'Allow to close SR' is set to ON action 'Close' is shown for selected SR on status 'Scheduled' or 'On-Hold'", 
			description = "SR Regular - Verify that when option 'Allow to close SR' is set to ON action 'Close' is shown for selected SR on status 'Scheduled' or 'On-Hold'")
	public void testSRRegularVerifyThatWhenOptionAllowToCloseSRIsSetToONActionCloseIsShownForSelectedSROnStatusScheduledOrOnHold()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.clickCancel();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31748:SR: Regular - Verify that when option 'Allow to close SR' is set to OFF action 'Close' is not shown for selected SR on status 'Scheduled' or 'On-Hold'", 
			description = "SR: Regular - Verify that when option 'Allow to close SR' is set to OFF action 'Close' is not shown for selected SR on status 'Scheduled' or 'On-Hold'")
	public void testSRRegularVerifyThatWhenOptionAllowToCloseSRIsSetToOFFActionCloseIsNotShownForSelectedSROnStatusScheduledOrOnHold()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.clickCancel();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31749:SR: Regular - Verify that alert message is shown when select 'Close' action for SR - press No alert message is close", 
			description = "SR: Regular - Verify that alert message is shown when select 'Close' action for SR - press No alert message is close")
	public void testSRRegularVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressNoAlertMessageIsClose()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31750:SR: Regular - Verify that alert message is shown when select 'Close' action for SR - press Yes list of status reasons is shown", 
			description = "SR: Regular - Verify that alert message is shown when select 'Close' action for SR - press Yes list of status reasons is shown")
	public void testSRRegularVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressYesListOfStatusReasonsIsShown()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.clickCancelCloseReasonDialog();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31752:SR: Regular - Verify that when Status reason is selected Question section is shown in case it is assigned to reason on BO", 
			description = "SR: Regular - Verify that when Status reason is selected Question section is shown in case it is assigned to reason on BO")
	public void testSRRegularVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsShownInCaseItIsAssignedToReasonOnBO()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.selectUIAPickerValue("All work is done. Answer questions");
		servicerequestsscreen.clickDoneCloseReasonDialog();
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.answerQuestion2("A3");
		servicerequestsscreen.clickCloseSR();
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31753:SR: Regular - Verify that when Status reason is selected Question section is not shown in case it is not assigned to reason on BO", 
			description = "SR: Regular - Verify that when Status reason is selected Question section is not shown in case it is not assigned to reason on BO")
	public void testSRRegularVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsNotShownInCaseItIsNotAssignedToReasonOnBO()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.selectDoneReason("All work is done. No Questions");
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 30083:SR: Regular - Verify that when create WO from SR message that vehicle parts are required is shown for appropriate services", 
			description = "SR: Regular - Verify that when create WO from SR message that vehicle parts are required is shown for appropriate services")
	public void testSRRegularVerifyThatWhenCreateWOFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_WO_ONLY);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_DISC_20_PERCENT));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSave();
		
		for (int i = 0; i < 4; i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.indexOf("' require"));
			RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openSelectedServiceDetails(servicedetails);
			
			selectedservicedetailsscreen.clickVehiclePartsCell();
			switch (servicedetails) {
			case iOSInternalProjectConstants.SR_DISC_20_PERCENT:
				 selectedservicedetailsscreen.selectVehiclePart("Hood");
	             break;
			case iOSInternalProjectConstants.SR_S1_MONEY_PANEL:
				 selectedservicedetailsscreen.selectVehiclePart("Back Glass");
	             break;     
			case iOSInternalProjectConstants.SR_S1_MONEY:
				 selectedservicedetailsscreen.selectVehiclePart("Hood");
	             break;
			case iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE:
				 selectedservicedetailsscreen.selectVehiclePart("Grill");
	             break;
			}
			
			selectedservicedetailsscreen.saveSelectedServiceDetails();
			selectedservicedetailsscreen.saveSelectedServiceDetails();
			servicesscreen.clickSave();
		}
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryOrdersButton();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen(appiumdriver);
		teamWorkOrdersScreen.woExists(wonumber);
		teamWorkOrdersScreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 30084:SR: Regular - Verify that when create Inspection from SR message that vehicle parts are required is shown for appropriate services", 
			description = "SR: Regular - Verify that when create Inspection from SR message that vehicle parts are required is shown for appropriate services")
	public void testSRRegularVerifyThatWhenCreateInspectionFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_INSP_ONLY);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.INSP_FOR_CALC);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_DISC_20_PERCENT));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		servicesscreen.clickSave();
		
		for (int i = 0; i < 4; i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.indexOf("' require"));
			RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openSelectedServiceDetails(servicedetails);
			
			selectedservicedetailsscreen.clickVehiclePartsCell();
			switch (servicedetails) {
			case iOSInternalProjectConstants.SR_S1_MONEY_PANEL:
				 selectedservicedetailsscreen.selectVehiclePart("Hood");
	             break;
			case iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE:
				 selectedservicedetailsscreen.selectVehiclePart("Back Glass");
	             break;     
			case iOSInternalProjectConstants.SR_DISC_20_PERCENT:
				 selectedservicedetailsscreen.selectVehiclePart("Hood");
	             break;
			case iOSInternalProjectConstants.SR_S1_MONEY:
				 selectedservicedetailsscreen.selectVehiclePart("Grill");
	             break;
			}
			selectedservicedetailsscreen.saveSelectedServiceDetails();
			selectedservicedetailsscreen.saveSelectedServiceDetails();
			servicesscreen.clickSave();
		}
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspnumber));
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 38749:Inspections: Regular - Verify that on inspection approval screen selected price matrix value is shown", 
			description = "Verify that on inspection approval screen selected price matrix value is shown")
	public void testRegularVerifyThatOnInspectionApprovalScreenSelectedPriceMatrixValueIsShown() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		RegularPriceMatrixScreen pricematrix = vehiclescreeen.selectNextScreen("Price Matrix Zayats",
				RegularPriceMatrixScreen.class);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");
		vehiclePartScreen.saveVehiclePart();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);		
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$100.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$100.00");
		
		pricematrix.selectNextScreen("Hail Matrix", RegularPriceMatrixScreen.class);
		vehiclePartScreen = pricematrix.selectPriceMatrix("L QUARTER");
		vehiclePartScreen.setSizeAndSeverity("DIME", "VERY LIGHT");
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$65.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$165.00");

		RegularQuestionsScreen questionsscreen = pricematrix.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspectionnumber);
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove("Dent Removal"));
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove("Test service price matrix"));
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice("Dent Removal"), "$65.00");
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice("Test service price matrix"), "$100.00");
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		pricematrix = vehiclescreeen.selectNextScreen("Price Matrix Zayats", RegularPriceMatrixScreen.class);
		pricematrix.selectPriceMatrix("VP2 zayats");
		Assert.assertEquals(pricematrix.clearVehicleData(), AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
		//pricematrix.clickBackButton();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$0.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$65.00");
		vehiclescreeen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspectionnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice("Dent Removal"), "$65.00");
		Assert.assertFalse(approveinspscreen.isInspectionServiceExistsForApprove("Test service price matrix"));
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 31451:Inspection - Regular: Verify that question section is shown per service for first selected panel when QF is not required", 
			description = "Verify that question section is shown per service for first selected panel when QF is not required")
	public void testRegularVerifyThatQuestionSectionIsShownPerServiceForFirstSelectedPanelWhenQFIsNotRequired() throws Exception {

		final String[] vehicleparts = { "Front Bumper", "Grill", "Hood", "Left Fender" };
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);
		RegularVehicleScreen vehiclecreen = new RegularVehicleScreen(appiumdriver);
		RegularServicesScreen servicesscreen = vehiclecreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.clickSaveButton();
		for (String vehiclepart : vehicleparts)
			selectedservicedetailscreen.selectVehiclePart(vehiclepart);
		selectedservicedetailscreen.clickSaveButton();
		final String selectedhehicleparts = servicesscreen.getListOfSelectedVehicleParts();
		
		for (String vehiclepart : vehicleparts)
			Assert.assertTrue(selectedhehicleparts.contains(vehiclepart));
		servicesscreen.clickSave();
		servicesscreen.clickAddServicesButton();
		for (int i = 0; i < vehicleparts.length; i++) {
			servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE, i);
			Assert.assertFalse(selectedservicedetailscreen.isQuestionFormCellExists());
			selectedservicedetailscreen.clickSaveButton();
		}
		servicesscreen.clickSave();
		Helpers.acceptAlert();
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 31963:Inspections: Regular - Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen", 
			description = "Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen")
	public void testRegularVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen() throws Exception {

		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Inspection_VIN_only");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.getVINField().click();
		Assert.assertTrue(vehiclescreeen.getVINField().isDisplayed());
		Helpers.keyboadrType("\n");
		//vehiclescreeen.clickChangeScreen();
		vehiclescreeen.cancelOrder();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42376:Inspections: Regular - Verify that when edit inspection selected vehicle parts for services are present", 
			description = "Verify that when edit inspection selected vehicle parts for services are present")
	public void testRegularVerifyThatWhenEditInspectionSelectedVehiclePartsForServicesArePresent() throws Exception {

		final String VIN = "1D7HW48NX6S507810";
		final String[] vehicleparts = { "Deck Lid", "Hood", "Roof" };
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Inspection_for_auto_WO_line_appr");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		for (int i=0; i < vehicleparts.length; i++)
			selectedservicedetailscreen.selectVehiclePart(vehicleparts[i]);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		for (int i=0; i < vehicleparts.length; i++) {
			servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.SR_S1_MONEY, i);
			selectedservicedetailscreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			Assert.assertEquals(selectedservicedetailscreen.getVehiclePartValue(), vehicleparts[i]);
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42388:Inspections: Regular - Verify that it is possible to save as Final inspection linked to SR", description = "Verify that it is possible to save as Final inspection linked to SR")
	public void testRegularVerifyThatItIsPossibleToSaveAsFinalInspectionLinkedToSR() throws Exception {
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_ALL_PHASES);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.clickAddServicesButton();
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType("Insp_Draft_Mode");
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveAsDraft();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		teaminspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickSaveAsFinal();
		teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		Assert.assertTrue(teaminspectionsscreen.isInspectionIsApproveButtonExists(inspectionnumber));
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
	}
	
	
	@Test(testName = "Test Case 33117:Inspections: Regular - Verify that when final inspection is copied servises are copied without statuses (approved, declined, skipped)", 
			description = "Verify that when final inspection is copied servises are copied without statuses (approved, declined, skipped)")
	public void testVerifyThatWhenFinalInspectionIsCopiedServisesAreCopiedWithoutStatuses_Approved_Declined_Skipped() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_MODE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_BUNDLE);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.answerQuestion2("A1");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);	
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("Price Matrix Zayats");
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "MEDIUM");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		Helpers.waitABit(4000);//!!!!!
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		
		myinspectionsscreen.clickApproveInspections();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.SR_S4_BUNDLE);
		approveinspscreen.isInspectionServiceExistsForApprove("Test service zayats");
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		servicesscreen = vehiclescreeen.selectNextScreen("Test_pack_for_calc", RegularServicesScreen.class);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S4_BUNDLE));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL));
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE));
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33116:Inspections: Regular - Verify that text notes are copied to new inspections when use copy action", description = "Verify that text notes are copied to new inspections when use copy action")
	public void testVerifyThatTextNotesAreCopiedToNewInspectionsWhenUseCopyAction() throws Exception {
			
		final String VIN  = "1D7HW48NX6S507810";
		final String _notes = "Test for copy";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreeen = myinspectionsscreen.selectDefaultInspectionType();
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularNotesScreen notesscreen = vehiclescreeen.clickNotesButton();
		notesscreen.setNotes(_notes);
		notesscreen.clickSaveButton();
		vehiclescreeen.saveWizard();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		String copiedinspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.saveWizard();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		Assert.assertTrue(myinspectionsscreen.isNotesIconPresentForInspection(copiedinspnumber));
		notesscreen = myinspectionsscreen.openInspectionNotesScreen(copiedinspnumber);
		Assert.assertTrue(notesscreen.isNotesPresent(_notes));
		notesscreen.clickSaveButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33154:Inspections: Regular - Verify that it is possible to approve Team Inspections use multi select", 
			description = "Verify that it is possible to approve Team Inspections use multi select")
	public void testVerifyThatItIsPossibleToApproveTeamInspectionsUseMultiSelect() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		List<String> inspnumbers = new ArrayList<String>();
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i < 3; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_MODE);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			inspnumbers.add(vehiclescreeen.getInspectionNumber());
			RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
					RegularServicesScreen.class);
			servicesscreen.clickToolButton();
			servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_BUNDLE);
			servicesscreen.clickAddServicesButton();
			servicesscreen.clickSaveAsFinal();
			new RegularMyInspectionsScreen(appiumdriver);

		}
		myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 3; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspnumbers.get(i));
		}
		
		teaminspectionsscreen.clickApproveInspections();
		teaminspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		for (int i = 0; i < 3; i++) {
			approveinspscreen.selectInspection(inspnumbers.get(i));
			approveinspscreen.clickApproveAllServicesButton();
			approveinspscreen.clickSaveButton();;
		}
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 3; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspnumbers.get(i));
		}
		teaminspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		Assert.assertTrue(teaminspectionsscreen.isSendEmailInspectionMenuActionExists());
		teaminspectionsscreen.clickCancel();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33869:Inspections: Regular - Verify that Services on Service Package are grouped by type selected on Insp type->Wizard", 
			description = "Verify that Services on Service Package are grouped by type selected on Insp type->Wizard")
	public void testVerifyThatServicesOnServicePackageAreGroupedByTypeSelectedOnInspTypeWizard() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		List<String> inspnumbers = new ArrayList<String>();
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Inspection_group_service");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		inspnumbers.add(vehiclescreeen.getInspectionNumber());
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("Zayats test pack", RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.clickAddServicesButton();
		servicesscreen = servicesscreen.selectNextScreen("Test_pack_for_calc", RegularServicesScreen.class);
		servicesscreen.selectPriceMatrices("Back Glass");
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Vehicle");
		selectedservicedetailscreen.answerQuestion2("A1");	
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickBackServicesButton();
		servicesscreen = servicesscreen.selectNextScreen("SR_FeeBundle", RegularServicesScreen.class);
		servicesscreen.selectPriceMatrices("Price Adjustment");
		servicesscreen.clickToolButton();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("SR_S6_Bl_I1_Percent");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickBackServicesButton();
		servicesscreen.clickSaveAsFinal();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		Helpers.drawRegularQuestionsSignature();
		servicesscreen.clickSaveAsFinal();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSaveAsFinal();
		Helpers.waitForAlert();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name("Question 'Question 2' in section 'Zayats Section1' should be answered.")).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("Close"))
				.click();
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspnumber));
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 44407:Inspections: Regular - Verify that all instances of one service are copied from inspection to WO", 
			description = "Verify that all instances of one service are copied from inspection to WO")
	public void testVerifyThatAllInstancesOfOneServiceAreCopiedFromInspectionToWO() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String firstprice = "43";
		final String secondprice = "33";
		final String secondquantity = "4";
		final String[] vehicleparts = { "Front Bumper", "Grill", "Hood" };
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		servicesscreen.selectService("3/4\" - Penny Size");
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServicePriceValue(firstprice);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickToolButton();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServicePriceValue(secondprice);
		servicedetailsscreen.setServiceQuantityValue(secondquantity);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicedetailsscreen.clickVehiclePartsCell();
		for (String vp: vehicleparts)
			servicedetailsscreen.selectVehiclePart(vp);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.approveInspectionApproveAllAndSignature();
		
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber);
		RegularSelectWorkOrderTypeScreen selectWOTypescreen = new RegularSelectWorkOrderTypeScreen(appiumdriver);
		selectWOTypescreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.searchServiceByName("3/4\" - Penny Size");
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$43.00 x 1.00"));
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$33.00 x 4.00"));
		servicesscreen.searchServiceByName(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.SR_S1_MONEY_PANEL), vehicleparts.length);
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33918:Inspections: Regular - Verify that Assign button is present when select some Tech in case Direct Assign option is set for inspection type", 
			description = "Verify that Assign button is present when select some Tech in case Direct Assign option is set for inspection type")
	public void testVerifyThatAssignButtonIsPresentWhenSelectSomeTechInCaseDirectAssignOptionIsSetForInspectionType() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Inspection_direct_assign");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		myinspectionsscreen.selectInspectionToAssign(inspnumber);
		Assert.assertTrue(myinspectionsscreen.isAssignButtonExists());
		myinspectionsscreen.clickBackButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 34285:Inspections: Regular - Verify that during Line approval ''Select All'' buttons are working correctly", 
			description = "Verify that during Line approval ''Select All'' buttons are working correctly")
	public void testVerifyThatDuringLineApprovalSelectAllButtonsAreWorkingCorrectly() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("123");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber);
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove("Oksi_Service_PP_Panel (Grill)"));
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove("Service_PP_Vehicle_not_multiple (123)"));
		approveinspscreen.clickApproveAllServicesButton();
		Assert.assertEquals(approveinspscreen.getNumberOfActiveApproveButtons(), 2);
		approveinspscreen.clickDeclineAllServicesButton();
		Assert.assertEquals(approveinspscreen.getNumberOfActiveDeclineButtons(), 2);
		approveinspscreen.clickSkipAllServicesButton();
		Assert.assertEquals(approveinspscreen.getNumberOfActiveSkipButtons(), 2);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 30012:Inspections: Regular - Verify that Approve option is not present for approved inspection in multi-select mode", 
			description = "Verify that Approve option is not present for approved inspection in multi-select mode")
	public void testVerifyThatApproveOptionIsNotPresentForApprovedInspectionInMultiselectMode() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		ArrayList<String> inspections = new ArrayList<String>();
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i <2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSPTYPE_FOR_SR_INSPTYPE);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			inspections.add(vehiclescreeen.getInspectionNumber());

			RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
			questionsscreen.swipeScreenUp();
			questionsscreen.selectAnswerForQuestion("Question 2", "A1");

			RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
					RegularServicesScreen.class);
			servicesscreen.saveWizard();
			myinspectionsscreen.selectInspectionForAction(inspections.get(i));
			myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			
			RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
			approveinspscreen.selectInspection(inspections.get(i));
			approveinspscreen.clickApproveAllServicesButton();
			approveinspscreen.clickSaveButton();
			approveinspscreen.clickSingnAndDrawApprovalSignature();
			approveinspscreen.clickDoneButton();
		}
		
		myinspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickCancel();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickCancel();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 30013:Inspections: Regular - Verify that Approve option is present in multi-select mode only one or more not approved inspections are selected", 
			description = "Verify that Approve option is present in multi-select mode only one or more not approved inspections are selected")
	public void testVerifyThatApproveOptionIsPresentInMultiselectModeOnlyOneOrMoreNotApprovedInspectionsAreSelected() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		ArrayList<String> inspections = new ArrayList<String>();
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i <2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSPTYPE_FOR_SR_INSPTYPE);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			inspections.add(vehiclescreeen.getInspectionNumber());

			RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
			questionsscreen.swipeScreenUp();
			questionsscreen.selectAnswerForQuestion("Question 2", "A1");

			RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
					RegularServicesScreen.class);
			servicesscreen.saveWizard();
		}
		myinspectionsscreen.selectInspectionForAction(inspections.get(0));
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspections.get(0));
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		myinspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertTrue(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickCancel();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertTrue(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickCancel();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 30853:Inspections: Regular - Verify that when option ''Draft Mode'' is set to ON - when save inspection provide prompt to a user to select either Draft or Final,"
			+ "Test Case 30855:Inspections: Regular - Verify that for Draft inspection following options are not available (Approve, Create WO, Create SR, Copy inspection)", 
			description = "Verify that when option ''Draft Mode'' is set to ON - when save inspection provide prompt to a user to select either Draft or Final,"
					+ "Verify that for Draft inspection following options are not available (Approve, Create WO, Create SR, Copy inspection)")
	public void testVerifyThatWhenOptionDraftModeIsSetToONWhenSaveInspectionProvidePromptToAUserToSelectEitherDraftOrFinal() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_MODE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Hood");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Grill");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveAsDraft();
		Assert.assertTrue(myinspectionsscreen.isDraftIconPresentForInspection(inspnumber));
		myinspectionsscreen.clickOnInspection(inspnumber);
		Assert.assertFalse(myinspectionsscreen.isApproveInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCreateWOInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCreateServiceRequestInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCopyInspectionMenuActionExists());
		myinspectionsscreen.clickCancel();
		homescreen = myinspectionsscreen.clickHomeButton();
		
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		Assert.assertTrue(teaminspectionsscreen.isDraftIconPresentForInspection(inspnumber));
		
		
		teaminspectionsscreen.clickHomeButton();		
	}
	
	@Test(testName = "Test Case 32286:Inspections: Regular - Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount", 
			description = "Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount")
	public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListColumnApprovedAmount() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)");
		approveinspscreen.clickSaveButton();
		
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
		Helpers.waitABit(10*1000);
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.selectSearchStatus("All active");
		inspectionspage.searchInspectionByNumber(inspnumber);		
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspnumber), "$2,000.00");
		Assert.assertEquals(inspectionspage.getInspectionStatus(inspnumber), "Approved");
		Assert.assertEquals(inspectionspage.getInspectionApprovedTotal(inspnumber), "$2,000.00");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName="Test Case 51336:WO: Regular - Verify that approve WO is working correct under Team WO", 
			description = "WO: Regular - Verify that approve WO is working correct under Team WO")
	public void testWOVerifyThatApproveWOIsWorkingCorrectUnderTeamWO()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		final String wonumber = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.setFilterLocation("All locations");
		teamworkordersscreen.clickSaveFilter();
		Assert.assertTrue(teamworkordersscreen.woExists(wonumber));
		Assert.assertTrue(teamworkordersscreen.isWorkOrderHasApproveIcon(wonumber));
		teamworkordersscreen.approveWorkOrder(wonumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		Assert.assertTrue(teamworkordersscreen.isWorkOrderHasActionIcon(wonumber));
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 27717:Invoices: Regular - Verify that it is posible to add payment from device for draft invoice", 
			description = "Invoices: Regular - Verify that it is posible to add payment from device for draft invoice")
	public void testInvoicesVerifyThatItIsPosibleToAddPaymentFromDeviceForDraftInvoice()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";
		final String _po  = "12345";
		final String cashcheckamount = "100";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");		
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.searchServiceByName(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.searchServiceByName(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword("Zayats", "1111");		
		ordersummaryscreen.clickSave();
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickInvoicePayButton();
		invoiceinfoscreen.changePaynentMethodToCashNormal();
		invoiceinfoscreen.setCashCheckAmountValue(cashcheckamount);
		invoiceinfoscreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myinvoicesscreen.clickHomeButton();
		Helpers.waitABit(10000);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();
	
		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), _po);
		Assert.assertEquals(invoiceswebpage.getInvoicePOPaidValue(invoicenumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);
		Assert.assertEquals(invoicepayments.getPaymentsTypeAmountValue("Cash/Check"), PricesCalculations.getPriceRepresentation(cashcheckamount));
		Assert.assertEquals(invoicepayments.getPaymentsTypeCreatedByValue("Cash/Check"), "Employee Simple 20%");
		
		Assert.assertEquals(invoicepayments.getPaymentsTypeAmountValue("PO/RO"), PricesCalculations.getPriceRepresentation("0"));
		Assert.assertEquals(invoicepayments.getPaymentsTypeCreatedByValue("PO/RO"), "Back Office");
		invoicepayments.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName="Test Case 27739:Invoices: Regular - Verify that payment is send to BO when PO# is changed under My invoice", 
			description = "Invoices: Regular - Verify that payment is send to BO when PO# is changed under My invoice")
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderMyInvoice()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";
		final String _po  = "12345";
		final String newpo  = "New test PO";
		final String cashcheckamount = "100";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");		
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.searchServiceByName(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.searchServiceByName(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword("Zayats", "1111");		
		ordersummaryscreen.clickSave();
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickInvoicePayButton();
		invoiceinfoscreen.changePaynentMethodToCashNormal();
		invoiceinfoscreen.setCashCheckAmountValue(cashcheckamount);
		invoiceinfoscreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickSaveAsDraft();
		myinvoicesscreen = new RegularMyInvoicesScreen(appiumdriver);
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO(newpo);
		myinvoicesscreen.clickHomeButton();
		Helpers.waitABit(10000);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();
	
		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), newpo);
		Assert.assertEquals(invoiceswebpage.getInvoicePOPaidValue(invoicenumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);
		
		Assert.assertEquals(invoicepayments.getPaymentDescriptionTypeAmountValue("PO #: " + newpo), PricesCalculations.getPriceRepresentation("0"));
		invoicepayments.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName="Test Case 27741:Invoices: Regular - Verify that payment is send to BO when PO# is changed under Team invoice", 
			description = "Invoices: Regular - Verify that payment is send to BO when PO# is changed under Team invoice")
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderTeamInvoice()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";
		final String _po  = "12345";
		final String newpo  = "New test PO from Team";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");		
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		
		servicesscreen = new RegularServicesScreen(appiumdriver);
		//servicesscreen.clickCancelButton();
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword("Zayats", "1111");		
		ordersummaryscreen.clickSave();
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		RegularTeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		teaminvoicesscreen.selectInvoice(invoicenumber);
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(newpo);
		teaminvoicesscreen.clickHomeButton();
		
		Helpers.waitABit(5000);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();
	
		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), newpo);
		//Assert.assertEquals(invoiceswebpage.getInvoicePOPaidValue(invoicenumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);
		
		Assert.assertEquals(invoicepayments.getPaymentDescriptionTypeAmountValue("PO #: " + newpo), PricesCalculations.getPriceRepresentation("0"));
		invoicepayments.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName="Test Case 40033:WO Monitor: Verify filter for Team WO that returns only work assigned to tech who is logged in", 
			description = "WO: Regular - Verify filter for Team WO that returns only work assigned to tech who is logged in")
	public void testInvoicesVerifyFilterForTeamWOThatReturnsOnlyWorkAssignedToTechWhoIsLoggedIn()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonum = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.unselecTechnician("Employee Simple 20%");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.switchToTeamView();
		RegularTeamWorkOrdersScreen teamworkordersscreen = new RegularTeamWorkOrdersScreen(appiumdriver);
		teamworkordersscreen.clickOnWO(wonum);
		
		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();		
		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		
		homescreen = mainscreen.userLogin("Zayats", "1111");
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);	

		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();		
		mainscreen = homescreen.clickLogoutButton();
		

		homescreen = mainscreen.userLogin("Inspector", "12345");
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);		
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
        teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();		
		mainscreen = homescreen.clickLogoutButton();
		
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.setSearchWoNumber(wonum);
		repairorderspage.selectSearchLocation("Default Location");
		repairorderspage.clickFindButton();
		
		VendorOrderServicesWebPage vendororderservicespage = repairorderspage.clickOnWorkOrderLinkInTable(wonum);
		vendororderservicespage.changeRepairOrderServiceVendor(iOSInternalProjectConstants.DYE_SERVICE, "Device Team");
		vendororderservicespage.waitABit(1000);
		Assert.assertEquals(vendororderservicespage.getRepairOrderServiceTechnician(iOSInternalProjectConstants.DYE_SERVICE), "Oksi User");
		DriverBuilder.getInstance().getDriver().quit();
		
		
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);		
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		//ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();
		mainscreen = homescreen.clickLogoutButton();
		
		homescreen = mainscreen.userLogin("Zayats", "1111");
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);		
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();		
		mainscreen = homescreen.clickLogoutButton();
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
	
	@Test(testName="Test Case 45251:SR: Regular - Verify multiple inspections and multiple work orders to be tied to a Service Request", 
			description = "SR: Regular - Verify multiple inspections and multiple work orders to be tied to a Service Request")
	public void testSRVerifyMultipleInspectionsAndMultipleWorkOrdersToBeTiedToAServiceRequest()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";
		final String insptype1 = iOSInternalProjectConstants.INS_LINE_APPROVE_OFF;
		final String insptype2 = iOSInternalProjectConstants.INSP_FOR_CALC;
		List<String> inspnumbers = new ArrayList<String>();
		List<String> wonumbers = new ArrayList<String>();
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
				
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_ALL_PHASES);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
				
		vehiclescreeen.setVIN(VIN);
		RegularQuestionsScreen questionsscreen =  vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		vehiclescreeen.clickSave();
		Helpers.getAlertTextAndCancel();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(insptype1);
		inspnumbers.add(vehiclescreeen.getInspectionNumber());
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickSaveAsDraft();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(insptype2);
		inspnumbers.add(vehiclescreeen.getInspectionNumber());	
		questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		for (String inspectnumber : inspnumbers)
			Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));
		
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.WO_DELAY_START);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		wonumbers.add(vehiclescreeen.getWorkOrderNumber());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		wonumbers.add(vehiclescreeen.getWorkOrderNumber());
		ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryOrdersButton();
		RegularTeamWorkOrdersScreen teamworkordersscreen = new RegularTeamWorkOrdersScreen(appiumdriver);
		for (String wonumber : wonumbers)
			Assert.assertTrue(teamworkordersscreen.woExists(wonumber));
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 45128:Inspections: Regular - Verify that service level notes are copied from Inspection to WO when it is auto created after approval", 
			description = "Verify that service level notes are copied from Inspection to WO when it is auto created after approval")
	public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String _price1  = "10";
		final String _price2  = "100";
		final int timetowaitwo = 4;
		final String _pricematrix1 = "Left Front Door";
		final String _pricematrix2 = "Grill";
		final String inspectionnotes = "Inspection notes";
		final String servicenotes = "Service Notes";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen(appiumdriver);
		RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServicePriceValue(_price1);
		servicedetailsscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SALES_TAX);
		
		servicesscreen.clickAddServicesButton();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		RegularNotesScreen notesscreen = vehiclescreeen.clickNotesButton();
		notesscreen.setNotes(inspectionnotes);
		notesscreen.clickSaveButton();

		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");		
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService("3/4\" - Penny Size"));
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);		
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();		
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService(iOSInternalProjectConstants.SALES_TAX));
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);		
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));

		RegularPriceMatrixScreen pricematrix =  servicesscreen.selectNextScreen("Default", RegularPriceMatrixScreen.class);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix1);
		vehiclePartScreen.setSizeAndSeverity("DIME", "VERY LIGHT");
		vehiclePartScreen.setPrice(_price2);
		vehiclePartScreen.saveVehiclePart();
		
		servicesscreen.selectNextScreen("Matrix Labor", RegularPriceMatrixScreen.class);
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix2);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setTime("12");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.saveWizard();
		
		Assert.assertTrue(myinspectionsscreen.isNotesIconPresentForInspection(inspnumber));
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		//SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		homescreen = myinspectionsscreen.clickHomeButton();
		
		RegularTeamWorkOrdersScreen teamwoscreen = homescreen.clickTeamWorkordersButton();
		homescreen = teamwoscreen.clickHomeButton();
		
		for (int i = 0; i < timetowaitwo; i++) {
			Helpers.waitABit(60*1000);
			teamwoscreen = homescreen.clickTeamWorkordersButton();
			homescreen = teamwoscreen.clickHomeButton();
		}
		teamwoscreen = homescreen.clickTeamWorkordersButton();
		teamwoscreen.clickSearchButton();
		teamwoscreen.setSearchType(iOSInternalProjectConstants.WO_TYPE_FOR_MONITOR);
		teamwoscreen.selectSearchLocation("All locations");
		teamwoscreen.clickSearchSaveButton();
		
		final String wonumber = teamwoscreen.getFirstWorkOrderNumberValue();
		teamwoscreen.selectWorkOrderForEidt(wonumber);
		
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getEst(), inspnumber);
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		notesscreen = vehiclescreeen.clickNotesButton();
		Assert.assertTrue(notesscreen.getNotesValue().length() > 0);
		notesscreen.clickSaveButton();
		
		servicesscreen = new RegularServicesScreen(appiumdriver);
		/*servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");		
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();*/
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService("3/4\" - Penny Size"));
		
		/*servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);		
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();		
		servicedetailsscreen.saveSelectedServiceDetails();*/
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService(iOSInternalProjectConstants.SALES_TAX));
		
		/*servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);		
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();*/
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		servicesscreen.cancelWizard();
		teamwoscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32989:Inspections: Regular - Verify that question section is shown per service with must panels when questions are required", 
			description = "Verify that question section is shown per service with must panels when questions are required")
	public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);		
		vehiclescreeen.setVIN(VIN);

		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.selectVehiclePart("Deck Lid");
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.selectVehiclePart("Hood");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE, 0);
		Assert.assertTrue(servicedetailsscreen.isQuestionFormCellExists());
		Assert.assertEquals(servicedetailsscreen.getQuestion2Value(), "A3");
		servicedetailsscreen.saveSelectedServiceDetails();
		
		for (int i = 1; i < 4; i++) {
			servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE, i);
			Assert.assertFalse(servicedetailsscreen.isQuestionFormCellExists());
			servicedetailsscreen.saveSelectedServiceDetails();
		}
		servicesscreen.saveWizard();
		myinspectionsscreen.clickHomeButton();		
	}
	
	@Test(testName = "Test Case 30509:Invoices: Regular - Verify that message 'Invoice PO# shouldn't be empty' is shown for Team Invoices", 
			description = "Verify that message 'Invoice PO# shouldn't be empty' is shown for Team Invoices")
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForTeamInvoices() throws Exception {
		
		final String emptypo = "";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularTeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
		teaminvoicesscreen.selectInvoice(invoicenum);
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(emptypo);
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Invoice PO# shouldn't be empty"));
		teaminvoicesscreen.clickCancel();
		teaminvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 30510:Invoices: Regular - Verify that message 'Invoice PO# shouldn't be empty' is shown for MY Invoices", 
			description = "Verify that message 'Invoice PO# shouldn't be empty' is shown for MY Invoices")
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForMyInvoices() throws Exception {
		
		final String emptypo = "";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String invoicenum = myinvoicesscreen.getFirstInvoiceValue();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO(emptypo);
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Invoice PO# shouldn't be empty"));
		myinvoicesscreen.clickCancel();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33115:WO: Regular - Verify that Tech splits is saved in price matrices", 
			description = "Verify that Tech splits is saved in price matrices")
	public void testWOVerifyThatTechSplitsIsSavedInPriceMatrices() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String pricevalue  = "100";
		final String totalsale = "5";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "MEDIUM");
		Assert.assertEquals(vehiclePartScreen.getTechniciansValue(), defaulttech);
		vehiclePartScreen.setPrice(pricevalue);
		vehiclePartScreen.clickOnTechnicians();
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.selecTechnician(techname);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(vehiclePartScreen.getTechniciansValue(), defaulttech + ", " + techname);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale(totalsale);
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(totalsale));
		ordersummaryscreen.clickSave();
		
		RegularTechRevenueScreen techrevenuescreen = myworkordersscreen.selectWorkOrderTechRevenueMenuItem(wonumber);
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(techname));
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(defaulttech));
		techrevenuescreen.clickBackButton();
		myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 34551:WO: Regular - Verify that it is not possible to change default tech via service type split", 
			description = "Verify that it is not possible to change default tech via service type split")
	public void testWOVerifyThatItIsNotPossibleToChangeDefaultTechViaServiceTypeSplit() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";
		final String totalsale = "5";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(defaulttech));
		selectedservicescreen.selecTechnician(techname);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(defaulttech));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickTechnicianToolbarIcon();
		servicesscreen.changeTechnician("Dent", techname);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(defaulttech));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale(totalsale);
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(totalsale));
		ordersummaryscreen.saveWizard();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));
		
		myworkordersscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 45097:WO: Regular - Verify that when use Copy Services action for WO all service instances should be copied", 
			description = "Verify that when use Copy Services action for WO all service instances should be copied")
	public void testWOVerifyThatWhenUseCopyServicesActionForWOAllServiceInstancesShouldBeCopied() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "5";
		final String[] servicestoadd = { iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL, iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE};
		final String[] vehicleparts = { "Dashboard", "Deck Lid"};
		
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		for (String serviceadd : servicestoadd) {
			//servicesscreen.searchAvailableService(serviceadd);
			servicesscreen.clickToolButton();
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(serviceadd);
			selectedservicescreen.clickVehiclePartsCell();
			for (String vehiclepart : vehicleparts) {
				selectedservicescreen.selectVehiclePart(vehiclepart);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickAddServicesButton();
		}

		for (String serviceadd : servicestoadd) {
			Assert.assertTrue(servicesscreen.checkServiceIsSelected(serviceadd));
		}
		
		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), "$44.00");
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale(totalsale);
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(totalsale));
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForCopyServices(wonumber);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
		for (String serviceadd : servicestoadd) {
			Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(serviceadd), servicestoadd.length);
		}
		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), "$44.00");
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 50250:WO: Regular - Verify that WO number is not duplicated", 
			description = "WO: - Verify that WO number is not duplicated")
	public void testWOVerifyThatWONumberIsNotDuplicated()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po  = "12345";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		final String wonumber1 = vehiclescreeen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		RegularQuestionsScreen questionsscreen = invoiceinfoscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		invoiceinfoscreen.clickSaveAsFinal();
		Helpers.waitABit(10000);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationsWebPage.clickInvoicesLink();
		//invoiceswebpage.waitABit(1000);
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();
		invoiceswebpage.archiveInvoiceByNumber(invoicenumber);
		Assert.assertFalse(invoiceswebpage.isInvoiceNumberExists(invoicenumber));
		webdriver.quit();
		
		//Create second WO
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		final String wonumber2 = vehiclescreeen.getWorkOrderNumber();
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.approveWorkOrder(wonumber2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);	
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber2);
		myworkordersscreen.clickInvoiceIcon();
		invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(_po);
		questionsscreen = invoiceinfoscreen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		homescreen = myworkordersscreen.clickHomeButton();
		homescreen.clickStatusButton();
		homescreen.updateDatabase();
		RegularMainScreen mainscreen = new RegularMainScreen(appiumdriver);
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		//Create third WO
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		final String wonumber3 = vehiclescreeen.getWorkOrderNumber();
		servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
		Helpers.waitABit(10000);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		operationsWebPage = backofficeheader.clickOperationsLink();

		WorkOrdersWebPage workorderspage = operationsWebPage.clickWorkOrdersLink();

		workorderspage.makeSearchPanelVisible();
		workorderspage.setSearchOrderNumber(wonumber3);
		workorderspage.clickFindButton();

		Assert.assertEquals(workorderspage.getWorkOrdersTableRowCount(), 1);
		webdriver.quit();
	}
	
	@Test(testName="Test Case 39573:WO: Regular - Verify that in case valid VIN is decoded, replace existing make and model with new one", 
			description = "WO: - Verify that in case valid VIN is decoded, replace existing make and model with new one")
	public void testWOVerifyThatInCaseValidVINIsDecodedReplaceExistingMakeAndModelWithNewOne()
			throws Exception {
		
		final String[] VINs  = { "2A8GP54L87R279721", "1FMDU32X0PUB50142", "GFFGG"} ;
		final String makes[]  = { "Chrysler", "Ford", null } ;
		final String models[]  = { "Town and Country", "Explorer",  null };

		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		for (int i = 0; i < VINs.length; i++) {
			vehiclescreeen.setVIN(VINs[i]);
			Assert.assertEquals(vehiclescreeen.getMake(), makes[i]);
			Assert.assertEquals(vehiclescreeen.getModel(), models[i]);			
			vehiclescreeen.clearVINCode();
		}

		vehiclescreeen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 34626:WO: Regular - Verify that when service do not have questions and select several panels do not underline anyone", 
			description = "WO: - Verify that when service do not have questions and select several panels do not underline anyone")
	public void testWOVerifyThatWhenServiceDoNotHaveQuestionsAndSelectSeveralPanelsDoNotUnderlineAnyone()
			throws Exception {
		
		final String VIN  = "2A8GP54L87R279721";
		final String[] vehicleparts  = { "Center Rear Passenger Seat", "Dashboard" };
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_WITHOUT_QUESTIONS_PP_PANEL);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.clickVehiclePartsCell();
		for (String vehiclepart : vehicleparts) {
			selectedservicescreen.selectVehiclePart(vehiclepart);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		for (String vehiclepart : vehicleparts) {
			Assert.assertTrue(selectedservicescreen.getVehiclePartValue().contains(vehiclepart));
		}
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.TEST_SERVICE_WITHOUT_QUESTIONS_PP_PANEL), vehicleparts.length);
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 31964:WO: Regular - Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen", 
				description = "Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen")
	public void testWOVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen() throws Exception {
			
		final String VIN = "2A8GP54L87R279721";

			
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_VIN_ONLY);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		Assert.assertTrue(vehiclescreeen.getVINField().isDisplayed());
		vehiclescreeen.clickVINField();
		Assert.assertTrue(vehiclescreeen.getVINField().isDisplayed());
		vehiclescreeen.cancelOrder();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 53824:WO: Regular - Verify that message is shown for Money and Labor service when price is changed to 0$ under WO", 
			description = "Verify that message is shown for Money and Labor service when price is changed to 0$ under WO")
	public void testWOVerifyThatMessageIsShownForMoneyAndLaborServiceWhenPriceIsChangedTo0UnderWO() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "5";
		final String servicezeroprice = "0";
		final String servicecalclaborprice = "12";
		
		
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_SMOKE_MONITOR);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getWorkOrderNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
        vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);
		
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);
		selectedservicescreen.setServicePriceValue(servicezeroprice);
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Order's technician split will be assigned to this order service if you set zero amount."));
		
		selectedservicescreen.clickTechniciansIcon();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Set non-zero amount for service to assign multiple technicians."));
		/*selectedservicescreen.cancelSelectedServiceDetails();
		selectedservicescreen.setServiceRateValue(servicecalclaborprice);
		selectedservicescreen.clickTechniciansIcon();*/
		
		selectedservicescreen.searchTechnician("Manager");
		selectedservicescreen.selecTechnician("Manager 1");
		selectedservicescreen.cancelSearchTechnician();
		selectedservicescreen.searchTechnician("Oksana");
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.cancelSearchTechnician();
		Assert.assertFalse(selectedservicescreen.isTechnicianIsSelected("Manager 1"));
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected("Oksana Zayats"));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.CALC_LABOR);
		selectedservicescreen.clickTechniciansIcon();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Set non-zero amount for service to assign multiple technicians."));
		selectedservicescreen.cancelSelectedServiceDetails();
		selectedservicescreen.setServiceRateValue(servicecalclaborprice);
		selectedservicescreen.clickTechniciansIcon();
		
		selectedservicescreen.searchTechnician("Manager");
		selectedservicescreen.selecTechnician("Manager 1");
		selectedservicescreen.cancelSearchTechnician();
		selectedservicescreen.searchTechnician("Oksana");
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.cancelSearchTechnician();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected("Manager 1"));
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected("Oksana Zayats"));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelWizard();
		homescreen =  myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 45252:WO: Regular - Verify that validation is present for vehicle trim field", 
			description = "Verify that validation is present for vehicle trim field")
	public void testWOVerifyThatValidationIsPresentForVehicleTrimField() throws Exception {
		
		final String VIN  = "TESTVINN";
		final String _make = "Acura";
		final String _model = "CL";
		final String trimvalue = "2.2 Premium";

		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_VEHICLE_TRIM_VALIDATION);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Trim is required"));
		vehiclescreeen.setTrim(trimvalue);
		Assert.assertEquals(vehiclescreeen.getTrim(), trimvalue);

		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		//servicesscreen.saveWorkOrder();
		servicesscreen.saveWizard();
		homescreen =  myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 35375:WO: Regular - Verify that Total sale is not shown when checkmark 'Total sale required' is not set to OFF", 
			description = "Verify that Total sale is not shown when checkmark 'Total sale required' is not set to OFF")
	public void testWOVerifyThatTotalSaleIsNotShownWhenCheckmarkTotalSaleRequiredIsNotSetToOFF() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TOTAL_SALE_NOT_REQUIRED);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getWorkOrderNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.TAX_DISCOUNT);
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		Assert.assertFalse(ordersummaryscreen.isTotalSaleFieldPresent());
		ordersummaryscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
		
		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		ordersummaryscreen = vehiclescreeen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		Assert.assertFalse(ordersummaryscreen.isTotalSaleFieldPresent());
		ordersummaryscreen.saveWizard();
		homescreen = teamworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 40821:WO: Regular - Verify that it is possible to assign tech to order by action Technicians", 
			description = "Verify that it is possible to assign tech to order by action Technicians")
	public void testWOVerifyThatItIsPossibleToAssignTechToOrderByActionTechnicians() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String pricevalue  = "21";
		final String totalsale = "5";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getWorkOrderNumber();

		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen(appiumdriver);
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServiceQuantityValue("2.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Front Bumper");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");
		Assert.assertEquals(vehiclePartScreen.getTechniciansValue(), defaulttech);
		vehiclePartScreen.setPrice(pricevalue);
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.TAX_DISCOUNT);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SALES_TAX);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();
		
		selectedservicescreen = myworkordersscreen.selectWorkOrderTechniciansMenuItem(wonumber);
		//selectedservicescreen.selecTechnician(defaulttech);
		selectedservicescreen.selecTechnician(techname);
		selectedservicescreen.saveSelectedServiceDetails();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Changing default employees for a work order will change split data for all services."));
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getTechnician(), techname + ", " + defaulttech);
		vehiclescreeen.cancelOrder();
		myworkordersscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 58663:Inspections: Regular - Verify that when Panel grouping is used for package for selected Panel only linked services are shown", 
			description = "Verify that when Panel grouping is used for package for selected Panel only linked services are shown")
	public void testInspectionsVerifyThatWhenPanelGroupingIsUsedForPackageForSelectedPanelOnlyLinkedServicesAreShown() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_SERVICE_TYPE_WITH_OUT_REQUIRED);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);		
		vehiclescreeen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.BUFF_SERVICE);
		servicesscreen.clickToolButton();
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE));
		servicesscreen.clickCancelButton();
		servicesscreen.clickBackServicesButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.clickToolButton();
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_VEHICLE));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_PANEL));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_SERVICE));
		servicesscreen.clickCancelButton();
		servicesscreen.clickBackServicesButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		servicesscreen.clickToolButton();
		Assert.assertTrue(servicesscreen.isServiceTypeExists("3/4\" - Penny Size"));
		servicesscreen.clickCancelButton();
		servicesscreen.clickBackServicesButton();
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.saveWizard();
		myinspectionsscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 57101:WO: Regular - Verify that WO is saved correct with selected sub service (no message with incorrect tech split)", 
			description = "Verify that WO is saved correct with selected sub service (no message with incorrect tech split)")
	public void testWOVerifyThatWOIsSavedCorrectWithSelectedSubService_NoMessageWithIncorrectTechSplit() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "10";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getWorkOrderNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption(), RegularOrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		
		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.hideKeyboard();
		servicesscreen.selectServiceSubSrvice("Wash partly");
		servicesscreen.clickToolButton();
		
		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.selectServiceSubSrvice("Wash whole");
		servicesscreen.hideKeyboard();
		servicesscreen.clickAddServicesButton();
		Assert.assertTrue(servicesscreen.isServiceWithSubSrviceSelected(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash partly"));
		Assert.assertTrue(servicesscreen.isServiceWithSubSrviceSelected(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash whole"));
		servicesscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 54542:WO: Regular - Verify that answer services are correctly added for WO when Panel group is set", 
			description = "Verify that answer services are correctly added for WO when Panel group is set")
	public void testWOVerifyThatAnswerServicesAreCorrectlyAddedForWOWhenPanelGroupIsSet() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_PANEL_GROUP);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		questionsscreen = questionsscreen.selectNextScreen("Zayats Section2", RegularQuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Q1", "No - rate 0");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.selectServicePanel("Other");
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE));
		servicesscreen.clickBackServicesButton();
		
		servicesscreen.cancelWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 43408:WO: Regular - Verify that search bar is present for service pack screen", 
			description = "Verify that search bar is present for service pack screen")
	public void testWOVerifyThatSearchBarIsPresentForServicePackScreen() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
				RegularServicesScreen.class);
		servicesscreen.searchServiceByName("test");
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Dashboard");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.searchServiceByName("Tax");
		servicesscreen.selectSubService(iOSInternalProjectConstants.SALES_TAX);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SALES_TAX));
		
		servicesscreen.searchServiceByName("test");
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE));
		
		servicesscreen.cancelWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42178:WO: Regular - Verify that Cancel message is shown for New/Existing WO", 
			description = "Verify that Cancel message is shown for New/Existing WO")
	public void testWOVerifyThatCancelMessageIsShownForNewOrExistingWO() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_SMOKE_TEST);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.clickChangeScreen();
		vehiclescreeen.clickCancel();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, "Stop Work Order Creation\nAny unsaved changes will be lost. Are you sure you want to stop creating this Work Order?");
		vehiclescreeen.clickCancel();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Stop Work Order Creation\nAny unsaved changes will be lost. Are you sure you want to stop creating this Work Order?");
		
		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.clickChangeScreen();
		vehiclescreeen.clickCancel();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, "Stop Work Order Edit\nAny unsaved changes will be lost. Are you sure you want to stop editing this Work Order?");
		vehiclescreeen.clickCancel();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Stop Work Order Edit\nAny unsaved changes will be lost. Are you sure you want to stop editing this Work Order?");
		
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.clickChangeScreen();
		vehiclescreeen.clickCancel();
		
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 35951:SR: Reqular - Verify that Accept/Decline actions are present for tech when 'Technician Acceptance Required' option is ON and status is Proposed", 
			description = "Verify that Accept/Decline actions are present for tech when 'Technician Acceptance Required' option is ON and status is Proposed")
	public void testVerifyThatAcceptDeclineActionsArePresentForTechWhenTechnicianAcceptanceRequiredOptionIsONAndStatusIsProposed()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationsWebPage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestDropDown(iOSInternalProjectConstants.SR_ACCEPT_ON_MOBILE);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();
		
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectAcceptAction();
		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Would you like to accept  selected service request?");
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		Assert.assertTrue(servicerequestsscreen.isServiceRequestOnHold(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isAcceptActionExists());
		Assert.assertFalse(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.clickCancel();
		servicerequestsscreen.clickHomeButton();
	
	}
	
	@Test(testName="Test Case 35953:SR: Regular - Verify that when SR is declined status reason should be selected", 
			description = "Verify that when SR is declined status reason should be selected")
	public void testVerifyThatWhenSRIsDeclinedStatusReasonShouldBeSelected()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationsWebPage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestDropDown(iOSInternalProjectConstants.SR_ACCEPT_ON_MOBILE);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();
		
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectDeclineAction();
		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Would you like to decline  selected service request?");
		servicerequestsscreen.clickDoneCloseReasonDialog();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	
	}
	
	@Test(testName="Test Case 35954:SR: Regular - Verify that SR is not accepted when employee review or update it", 
			description = "Verify that SR is not accepted when employee review or update it")
	public void testVerifyThatSRIsNotAcceptedWhenEmployeeReviewOrUpdateIt()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationsWebPage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestDropDown(iOSInternalProjectConstants.SR_ACCEPT_ON_MOBILE);
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();
		
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectEditServiceRequestAction();
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setTech("Simple 20%");
		RegularQuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", RegularQuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");		
		questionsscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.clickCancel();
		
		servicerequestsscreen.clickHomeButton();
	
	}
	
	@Test(testName="Test Case 36004:SR: Regular - Verify that it is possible to accept/decline Appointment when option 'Appointment Acceptance Required' = ON", 
			description = "Verify that it is possible to accept/decline Appointment when option 'Appointment Acceptance Required' = ON")
	public void testSRVerifyThatItIsPossibleToAcceptDeclineAppointmentWhenOptionAppointmentAcceptanceRequiredEqualsON()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		String startDate = LocalDate.now().plusDays(1).format(formatter);
		String endDate = LocalDate.now().plusDays(2).format(formatter);
	
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationsWebPage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestDropDown(iOSInternalProjectConstants.SR_ACCEPT_ON_MOBILE);
		servicerequestslistpage.clickAddServiceRequestButton();
		
		servicerequestslistpage.clickGeneralInfoEditButton();
		servicerequestslistpage.setServiceRequestGeneralInfoAssignedTo("Employee Simple 20%");
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(servicerequestslistpage.addAppointmentFromSRlist(startDate, endDate, "Employee Simple 20%"));
		DriverBuilder.getInstance().getDriver().quit();
		
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
				
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectAppointmentRequestAction();
		Assert.assertTrue(servicerequestsscreen.isAcceptAppointmentRequestActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineAppointmentRequestActionExists());
		servicerequestsscreen.clickBackButton();
		
		servicerequestsscreen.clickHomeButton();
	
	}
}
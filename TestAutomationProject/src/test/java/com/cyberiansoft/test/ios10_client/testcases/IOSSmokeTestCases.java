package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.core.IOSHDDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.*;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class IOSSmokeTestCases extends BaseTestCase {

	private String regCode;
	public HomeScreen homescreen;
	
	
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
		mobilePlatform = MobilePlatform.IOS_HD;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		testGetDeviceRegistrationCode(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL(),
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		testRegisterationiOSDdevice();
		ExcelUtils.setDentWizardExcelFile();
	}
	
	//@AfterMethod
	public void closeBrowser() {
		if (webdriver != null)
			webdriver.quit();
	}
	
	public void testGetDeviceRegistrationCode(String backofficeurl,
			String userName, String userPassword) throws Exception {

		final String searchlicensecriteria = "Ios_automation";

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);

		ActiveDevicesWebPage devicespage = PageFactory.initElements(webdriver,
				ActiveDevicesWebPage.class);

		devicespage.setSearchCriteriaByName(searchlicensecriteria);
		regCode = devicespage.getFirstRegCodeInTable();

		DriverBuilder.getInstance().getDriver().quit();
	}

	public void testRegisterationiOSDdevice() throws Exception {
		appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
		//appiumdriver.removeApp(bundleid);
		//System.out.println("+++" + appiumdriver.getCapabilities().getCapability("MobileCapabilityType.APP").toString());
		//appiumdriver.installApp(appiumdriver.getCapabilities().getCapability("MobileCapabilityType.APP").toString());
		//appiumdriver.launchApp();
		appiumdriver.removeApp(IOSHDDeviceInfo.getInstance().getDeviceBundleId());
		appiumdriver.quit();
		appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
		//appiumdriver.installApp(appiumdriver.getCapabilities().getCapability("MobileCapabilityType.APP").toString());
		//appiumdriver.launchApp();
		
		SelectEnvironmentPopup selectenvscreen = new SelectEnvironmentPopup(appiumdriver);
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("Dev Environment");
		//LoginScreen loginscreen = new LoginScreen(appiumdriver);
		loginscreen.assertRegisterButtonIsValidCaption();
		loginscreen.registeriOSDevice(regCode);
		MainScreen mainscr = new MainScreen(appiumdriver);
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
	}
	
	//Test Case 8438:Update database on the device
	@Test(testName = "Test Case 8438:Update database on the device" ,description = "Update Database")
	public void testUpdateDatabase() throws Exception {
		homescreen = new HomeScreen(appiumdriver);
		MainScreen mainscr = homescreen.clickLogoutButton();
		mainscr.updateDatabase();
		HomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen.clickStatusButton();
		homescreen.updateDatabase();
		mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
	}

	//Test Case 8437:Updating VIN decoder
	@Test(testName = "Test Case 8437:Updating VIN decoder", description = "Update VIN")
	public void testUpdateVIN() throws Exception {
		homescreen = new HomeScreen(appiumdriver);
		MainScreen mainscr = homescreen.clickLogoutButton();
		mainscr.updateVIN();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen.clickStatusButton();
		homescreen.updateVIN();
		//homescreen.clickLogoutButton();
	}

	//Test Case 8441:Add Retail Customer in regular build
	@Test(testName = "Test Case 8441:Add Retail Customer in regular build", description = "Create retail customer")
	public void testCreateRetailCustomer() throws Exception {

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		AddCustomerScreen addcustomerscreen = customersscreen.clickAddCustomersButton();

		addcustomerscreen.addCustomer(firstname, lastname, companyname, street,
				city, state, zip, country, phone, mail);
		addcustomerscreen.clickSaveBtn();
		customersscreen.selectCustomerWithoutEditing(firstname);
		customersscreen.assertCustomerExists(firstname);

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
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();

		customersscreen.selectCustomerWithoutEditing(firstname);
		AddCustomerScreen addcustomerscreen = customersscreen.selectFirstCustomerToEdit();

		addcustomerscreen.editCustomer(firstnamenew, lastname, companyname,
				street, city, city, zip, zip, phone, mail);
		addcustomerscreen.clickSaveBtn();
		customersscreen.selectCustomerWithoutEditing(firstnamenew);
		customersscreen.assertCustomerExists(firstnamenew);
		customersscreen.clickHomeButton();
		MainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
	}

	// Test Case 8460: Delete Customer 
	@Test(testName = "Test Case 8460: Delete Customer ", description = "Delete retail customer")
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

		//resrtartApplication();		
		MainScreen mainscreen = new MainScreen(appiumdriver);
		mainscreen.updateDatabase();
		HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.selectCustomerWithoutEditing(firstnamenew);
		customersscreen.assertCustomerDoesntExists(firstnamenew);
		customersscreen.clickHomeButton();
		//homescreen.clickLogoutButton();
	}

	//Test Case 8685:Set Inspection to non Single page (HD) 
	@Test(testName = "Test Case 8685:Set Inspection to non Single page (HD) ", description = "Set Inspection To Non Single Page Inspection Type")
	public void testSetInspectionToNonSinglePage() throws Exception {
		
		/*appiumdriverInicialize();
		Thread.sleep(2000);*/
		
		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
	}

	//Test Case 8432:Edit the retail Inspection Notes
	@Test(testName = "Test Case 8432:Edit the retail Inspection Notes", description = "Edit Retail Inspection Notes")
	public void testEditRetailInspectionNotes() throws Exception {
		final String _notes1 = "Test\nTest 2";

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
			
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickEditInspectionButton();
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		NotesScreen notesscreen = vehiclescreeen.clickNotesButton();
		notesscreen.setNotes(_notes1);
		//notesscreen.clickDoneButton();
		notesscreen.addQuickNotes();
		//notesscreen.clickDoneButton();

		notesscreen.clickSaveButton();
		vehiclescreeen.clickNotesButton();
		notesscreen.assertNotesAndQuickNotes(_notes1);
		notesscreen.clickSaveButton();
		vehiclescreeen.cancelOrder();
		myinspectionsscreen.clickHomeButton();

	}

	//Test Case 8431:Approve inspection on device (Not Line Approval)
	@Test(testName = "Test Case 8431:Approve inspection on device (Not Line Approval)", description = "Approve Inspection On Device")
	public void testApproveInspectionOnDevice() throws Exception {

		final String VIN = "CFRTHASDFEWSDRZWM";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		final String inspNumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.clickSaveButton();

		myinspectionsscreen.selectInspectionForApprove(inspNumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspectionForApprove(inspNumber);
		approveinspscreen.clickApproveAfterSelection();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		// approveinspscreen.clickBackButton();
		myinspectionsscreen.assertInspectionIsApproved(inspNumber);
		myinspectionsscreen.clickHomeButton();
	}

	// Test Case 8586:Archive and Un-Archive the Inspection
	@Test(testName = "Test Case 8586:Archive and Un-Archive the Inspection", description = "Archive and Un-Archive the Inspection")
	public void testArchiveAndUnArchiveTheInspection() throws Exception {

		final String VIN = "TESTVINNO";
		final String _make = "Acura";
		final String _model = "ILX";
		final String _color = "Black";

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		//customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		//
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);		
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		String myinspetoarchive = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.clickSaveButton();
		
		myinspectionsscreen.clickHomeButton();

		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.archiveInspection(myinspetoarchive,
				"Reason 2");
		myinspectionsscreen.assertInspectionDoesntExists(myinspetoarchive);
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
		
		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		MainScreen mainscreen = homescreen.clickLogoutButton();
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
		SelectEnvironmentPopup selectenvscreen = new SelectEnvironmentPopup(appiumdriver);
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("Dev Environment");
		loginscreen.registeriOSDevice(regCode);
		mainscreen.userLogin(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN, iOSInternalProjectConstants.USER_PASSWORD);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.SPECIFIC_CLIENT_TEST_WO1);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertDefaultServiceIsSelected();
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertDefaultServiceIsSelected();
		ordersummaryscreen.assertServiceIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(summ));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		ordersummaryscreen.clickSaveButton();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		vehiclescreeen.setVIN(VIN);
		
		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		//ordersummaryscreen.checkApproveAndCreateInvoice();
		//selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		//selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
		invoiceinfoscreen.clickSaveEmptyPO();
		invoiceinfoscreen.setPO(_po);
		//ordersummaryscreen.clickSaveButton();
		invoiceinfoscreen.clickSaveAsDraft();

		myworkordersscreen.clickHomeButton();
	}

	//Test Case 8429:Creating complex calculation WO
	@Test(testName = "Test Case 8429:Creating complex calculation WO", description = "Createing Complex calculation WO")
	public void testCreateWorkOrderWithTeamSharingOption() throws Exception {

		final String VIN = "1FTRX02W35K097028";
		final String summ = "346.23";
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
		homescreen = new HomeScreen(appiumdriver);	
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.saveWorkOrder();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.assertServicePriceValue(_dye_price);
		selectedservicescreen.assertServiceAdjustmentsValue(_dye_adjustments);
		selectedservicescreen.setServiceQuantityValue("3.00");
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE);
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		
		servicesscreen.openServiceDetails(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		selectedservicescreen.assertServicePriceValue(_disk_ex_srvc_price);
		selectedservicescreen.clickAdjustments();
		selectedservicescreen.assertAdjustmentValue(_disk_ex_srvc_adjustment,
				_disk_ex_srvc_adjustment_value);
		selectedservicescreen.selectAdjustment(_disk_ex_srvc_adjustment);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen
				.assertServiceAdjustmentsValue(_disk_ex_srvc_adjustment_value_ed);
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen(appiumdriver);
		selectedservicebundlescreen.assertBundleIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicebundlescreen.assertBundleIsNotSelected(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen.setServiceQuantityValue("2.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		PriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		pricematrix.selectPriceMatrix(_pricematrix);
		pricematrix.setSizeAndSeverity(_size, _severity);
		pricematrix.assertPriceCorrect(_price);
		pricematrix.assertNotesExists();
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		pricematrix.selectDiscaunt(_discaunt_us);
		pricematrix.clickSaveButton();
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen
				.assertServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE);
		ordersummaryscreen
				.assertServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		ordersummaryscreen.assertServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		ordersummaryscreen.assertServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		ordersummaryscreen.assertServiceIsSelected(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		ordersummaryscreen
				.assertServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(summ));
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();

	}

	//Test Case 8428:Copy services of WO (regular version) 
	@Test(testName = "Test Case 8428:Copy services of WO (regular version)", description = "Copy Cervices Of WO")
	public void testCopyCervicesOfWO() throws Exception {
		final String VIN = "ZWERTYAHHFDDXZBCV";
		final String summ = "91.80";
		
		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.selectFirstOrder();
		myworkordersscreen.selectCopyServices();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.SPECIFIC_CLIENT_TEST_WO1);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(summ));
		ordersummaryscreen.clickSaveButton();
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

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);;
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
			VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			vehiclescreeen.setMakeAndModel(_make, _model);
			vehiclescreeen.setColor(_color);
			vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
			inpections[i] = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.clickSaveButton();
		}

		myinspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.selectInspectionForAction(inpections[i]);
		}
		
		myinspectionsscreen.clickApproveInspections();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen(appiumdriver);
		for (int i = 0; i < 2; i++) {
			approveinspscreen.selectInspectionForApprove(inpections[i]);
			approveinspscreen.clickApproveAfterSelection();
		}
		
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		// }

		// approveinspscreen.clickBackButton();
		//myinspectionsscreen.clickDoneButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.assertInspectionIsApproved(inpections[i]);
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
		ArrayList<String> inpections = new ArrayList<String>();
		//String[] inpections = { "", "" };

		/*
		 * mainscreen.userLogin("Vitaly, Lyashenko", iOSInternalProjectConstants.USER_PASSWORD);
		 * SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		 * settingsscreen.setInspectionToNonSinglePageInspection();
		 * settingsscreen.clickHomeButton();
		 */
		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
			VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			vehiclescreeen.setMakeAndModel(_make, _model);
			vehiclescreeen.setColor(_color);
			vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
			inpections.add(vehiclescreeen.getInspectionNumber());
			vehiclescreeen.clickSaveButton();
			
		}

		myinspectionsscreen.archiveInspections(inpections, "Reason 2");

		for (String inspNumber : inpections) {
			myinspectionsscreen.assertInspectionDoesntExists(inspNumber);
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

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		String inpectionnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

		vehiclescreeen.clickSaveButton();
		myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		String inpection = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.clickHomeButton();
		MainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
		Helpers.waitABit(30*1000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();

		inspectionspage.approveInspectionByNumber(inpectionnumber);

		DriverBuilder.getInstance().getDriver().quit();
		
		mainscreen.updateDatabase();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.assertInspectionIsApproved(inpection);

		myinspectionsscreen.clickHomeButton();
		
	}

	//Test Case 8463:Approve inspection on back office (line-by-line approval)
	@Test(testName = "Test Case 8463:Approve inspection on back office (line-by-line approval) ", description = "Approve inspection on back office (line-by-line approval)")
	public void testApproveInspectionOnBackOfficeLinebylineApproval() throws Exception {

		final String VIN = "TESTVINNO";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_LA_DA_INSPTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		String inpectionnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

		vehiclescreeen.clickSaveButton();
		String inpection = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.clickHomeButton();
		MainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
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

		inspectionspage.approveInspectionLinebylineApprovalByNumber(
				inpectionnumber, iOSInternalProjectConstants.DISC_EX_SERVICE1, iOSInternalProjectConstants.DYE_SERVICE);

		DriverBuilder.getInstance().getDriver().quit();

		mainscreen.updateDatabase();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.assertInspectionIsApproved(inpection);

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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsWebPage servicerequestpage = operationspage.clickServiceRequestsLink();

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
			
		
		resrtartApplication();
		MainScreen mainscreen = new MainScreen(appiumdriver);
		HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
	    settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickRefreshButton();
		servicerequestsscreen.selectServiceRequest(servicerequest);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_SR_INSPTYPE);
		String inspnumber = servicerequestsscreen.getInspectionNumber();
		// vehiclescreeen.clickStepsButton();
		// vehiclescreeen.selectServicesAllServicesStep();
		servicerequestsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(price));
		servicesscreen.clickSaveButton();
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
		operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();

		inspectionspage.assertInspectionPrice(inspnumber, PricesCalculations.getPriceRepresentation(price));
		DriverBuilder.getInstance().getDriver().quit();
	}
	
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
		final String _year = "2014";
		
		final String teamname= "Default team";

		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
	
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_EST_WO_REQ_SRTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
		
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		//vehiclescreeen.setLicensePlate(licplate);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicedetailsscreen.setServiceQuantityValue("3");
		servicedetailsscreen.saveSelectedServiceDetails();
		//servicesscreen.setSelectedServiceRequestServicesQuantity(iOSInternalProjectConstants.WHEEL_SERVICE, "3");
		
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.selectService("Quest_Req_Serv");
		servicesscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompany("USG");
		servicesscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Question 'Signature' in section 'Follow up Requested' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.drawSignature();
		servicesscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		//Thread.sleep(4000);

		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(newsrnumber), iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(newsrnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(newsrnumber).contains("WERTYU123"));
		servicerequestsscreen.clickHomeButton();
		
		
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
		servicerequestslistpage.setSearchFreeText(newsrnumber);
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
				
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToSinglePageInspection();
		settingsscreen.clickHomeButton();
				
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(newsrnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_SR_INSPTYPE);
		
		SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen(appiumdriver);
		String inspnumber = singlepageinspectionscreen.getInspectionNumber();
		singlepageinspectionscreen.expandToFullScreeenSevicesSection();
		//Helpers.swipeScreen();
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.WHEEL_SERVICE, "$70.00 x 3.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.BUNDLE1_DISC_EX, "$150.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues("Quest_Req_Serv", "$10.00 x 1.00");
		SelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openServiceDetails("Quest_Req_Serv");
		selectedservicedetailsscreen.answerTaxPoint1Question("Test Answer 1");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		singlepageinspectionscreen = new SinglePageInspectionScreen(appiumdriver);
		singlepageinspectionscreen.collapseFullScreen();
		
		singlepageinspectionscreen.expandToFullScreeenQuestionsSection();

		//singlepageinspectionscreen.swipeScreenLeft();
		//singlepageinspectionscreen.swipeScreenLeft();
		//singlepageinspectionscreen.swipeScreenLeft();
		Assert.assertTrue(singlepageinspectionscreen.isSignaturePresent());		
		singlepageinspectionscreen.swipeScreenRight();
		singlepageinspectionscreen.swipeScreenRight();
		singlepageinspectionscreen.swipeScreenUp();
		
		Assert.assertTrue(singlepageinspectionscreen.isAnswerPresent("Test Answer 1"));
		singlepageinspectionscreen.collapseFullScreen();
		singlepageinspectionscreen.clickSaveButton();
		homescreen = servicerequestsscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.assertInspectionExists(inspnumber);
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionPriceValue(), PricesCalculations.getPriceRepresentation(summ));
		
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		
		myinspectionsscreen.clickApproveInspections();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		//Helpers.acceptAlert();
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspectionForApprove(inspnumber);
		approveinspscreen.clickApproveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		//myinspectionsscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 21585:Create WO from Service Request
	@Test(testName = "Test Case 21585:Create WO from Service Request", description = "Create WO from Service Request"/*,
			dependsOnMethods = { "testCreateInspectionFromServiceRequest" }*/)
	public void testCreateWOFromServiceRequest() throws Exception {

		appiumdriver.closeApp();
		Thread.sleep(60*1000*15);
		
		appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
		MainScreen mainscreen = new MainScreen(appiumdriver);
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		homescreen = settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		homescreen = customersscreen.clickHomeButton();
		
		//test case		
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(newsrnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		servicerequestsscreen.selectWOType(iOSInternalProjectConstants.WO_FOR_SR);
		String wonumber = servicerequestsscreen.getWorkOrderNumber();

		servicerequestsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.WHEEL_SERVICE, "$70.00 x 3.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.BUNDLE1_DISC_EX, "$70.00");
		
		SelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicedetailsscreen.changeAmountOfBundleService("70");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.saveWorkOrder();

		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.woExists(wonumber);
		homescreen = myworkordersscreen.clickHomeButton();

	}
	
	//Test Case 16640:Create Inspection from Invoice with two WOs
	@Test(testName = "Test Case 16640:Create Inspection from Invoice with two WOs", description = "Create Inspection from Invoice with two WOs")
	public void testCreateInspectionFromInvoiceWithTwoWOs() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		
		final String ordersumm = "13.50";
		
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToSinglePageInspection();
		settingsscreen.clickHomeButton();
				
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
		List<MobileElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (MobileElement closebtn : closebtns)
			if (closebtn.isDisplayed())
				closebtn.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Cowl, Other");
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ordersumm));
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			
		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getMake(), _make);
		Assert.assertEquals(vehiclescreeen.getModel(), _model);
		//Assert.assertEquals(vehiclescreeen.getYear(), _year);
		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
		invoiceinfoscreen.setPO("23");
		invoiceinfoscreen.addWorkOrder(wonumber1);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ordersumm));
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 16664:Create Invoice from WO in "My WOs" list
	@Test(testName = "Test Case 16664:Create Invoice from WO in \"My WOs\" list", description = "Create Invoice from WO in My WOs list")
	public void testCreateInvoiceFromWOInMyWOsList() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		final String _year = "2012";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
		
		List<MobileElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (MobileElement closebtn : closebtns)
			if (closebtn.isDisplayed())
				closebtn.click();
		/*element(
				MobileBy.name("Close"))
				.click();*/
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setYear(_year);

		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		selectedservicescreen.clickAdjustments();
		selectedservicescreen.selectAdjustment("For_SP_Cl");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertTotalAmauntIsCorrect("$108.50");
		
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.changeBundleQuantity(iOSInternalProjectConstants.WHEEL_SERVICE, "2");
		
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		PriceMatrixScreen pricematrix = selectedservicescreen.selectMatrics(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		pricematrix.selectPriceMatrix("HOOD");
		pricematrix.setSizeAndSeverity("NKL", "VERY LIGHT");
		pricematrix.assertNotesExists();
		Assert.assertTrue(pricematrix.getTechniciansValue().contains("Employee Simple 20%"));
		pricematrix.assertPriceCorrect("$100.00");
		pricematrix.selectDiscaunt("Discount 10-20$");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		pricematrix.clickSaveButton();
		
		pricematrix.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("PO# is required")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		invoiceinfoscreen.setPO(iOSInternalProjectConstants.USER_PASSWORD);
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		myinvoicesscreen.clickHomeButton();
	}
	
	//Test Case 18426:Don't allow to select billed and not billed orders together in multi selection mode
	@Test(testName = "Test Case 18426:Don't allow to select billed and not billed orders together in multi selection mode", description = "Don't allow to select billed and not billed orders together in multi selection mode")
	public void testDontAlowToSelectBilleAandNotBilledOrdersTogetherInMultiSelectionMode() throws Exception {

		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		/*SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToSinglePageInspection();
		settingsscreen.clickHomeButton();*/
			
		
		//Create WO1
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		CustomersScreen customersscreen = new CustomersScreen(appiumdriver);
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
		List<MobileElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (MobileElement closebtn : closebtns)
			if (closebtn.isDisplayed())
				closebtn.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService("VPS1");
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		//Create WO2
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVINFieldValue(VIN);
		alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
		closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (MobileElement closebtn : closebtns)
			if (closebtn.isDisplayed())
				closebtn.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService("VPS1");
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber2 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();

		//Test case
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("first123");
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();
		
		myworkordersscreen.clickActionButton();
		myworkordersscreen.selectWorkOrderForAction(wonumber1);
		myworkordersscreen.woExists(wonumber1);
		myworkordersscreen.clickDoneButton();
		
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();
		
		myworkordersscreen.clickActionButton();
		myworkordersscreen.selectWorkOrderForAction(wonumber2);
		myworkordersscreen.woExists(wonumber2);
		myworkordersscreen.clickDoneButton();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 18436:Don't allow to change billed orders
	@Test(testName = "Test Case 18436:Don't allow to change billed orders", description = "Don't allow to change billed orders")
	public void testDontAlowToChangeBilledOrders() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		final String[] menuitemstoverify = { "Edit" , "Notes", "Change status", "Delete", "Create Invoices" };
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToSinglePageInspection();
		settingsscreen.clickHomeButton();
			
		
		//Create WO1
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		CustomersScreen customersscreen = new CustomersScreen(appiumdriver);
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
		List<MobileElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (MobileElement closebtn : closebtns)
			if (closebtn.isDisplayed())
				closebtn.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService("VPS1");
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		
		//Test case
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("first123");
		invoiceinfoscreen.clickSaveAsDraft();
		
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();
		
		myworkordersscreen.selectWorkOrder(wonumber1);
		for (int i = 0; i < menuitemstoverify.length; i++) {
			myworkordersscreen.isMenuItemForSelectedWOExists(menuitemstoverify[i]);
		}
		myworkordersscreen.clickDetailspopupMenu();
		servicesscreen.clickCancelButton();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 18439:Change customer for invoice
	@Test(testName = "Test Case 18439:Change customer for invoice", description = "Change customer for invoice")
	public void testChangeCustomerForInvoice() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";	
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		//Create WO1
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
		List<MobileElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (MobileElement closebtn : closebtns)
			if (closebtn.isDisplayed())
				closebtn.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService("VPS1");
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		
		//Test case
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("first123");
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber(); 
		invoiceinfoscreen.clickSaveAsDraft();
		
		myworkordersscreen.clickHomeButton();
		Thread.sleep(60000);
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		
		myinvoicesscreen.changeCustomerForInspection(invoicenumber, iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		Assert.assertEquals(invoiceinfoscreen.getInvoiceCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		invoiceinfoscreen.clickFirstWO();
 		Assert.assertEquals(vehiclescreeen.getWorkOrderCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
 		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.cancelOrder();
		invoiceinfoscreen.cancelInvoice();
		myinvoicesscreen.clickHomeButton();
	}
	
	//Test Case 10498:Regression test: test bug with crash on "Copy Vehicle" 
	@Test(testName = "Test Case 10498:Regression test: test bug with crash on \"Copy Vehicle\"", description = "Regression test: test bug with crash on Copy Vehicle")
	public void testBugWithCrashOnCopyVehicle() throws Exception {

		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		CarHistoryScreen carhistoryscreen = homescreen.clickCarHistoryButton();
		carhistoryscreen.clickFirstCarHistoryInTable();
		carhistoryscreen.clickCarHistoryMyWorkOrders();
		MyWorkOrdersScreen myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.selectFirstOrder();
		myworkordersscreen.selectCopyVehicle();
		CustomersScreen customersscreen = new CustomersScreen(appiumdriver);
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		myworkordersscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
		carhistoryscreen.clickHomeButton();
	}
	
	//Test Case 16239:Copy Inspections
	@Test(testName = "Test Case 16239:Copy Inspections", description = "Copy Inspections")
	public void testCopyInspections() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "Audi";
		final String _model = "A1";
		final String _year = "2016";
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
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);	

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.FOR_COPY_INSP_INSPTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		final String inspNumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.setVINFieldValue(VIN);
		
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
				vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		vehiclescreeen.selectNextScreen(VisualInteriorScreen.getVisualInteriorCaption());
		VisualInteriorScreen visualinteriorscreen = new VisualInteriorScreen(appiumdriver);
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.tapInterior();
		
		visualinteriorscreen.selectNextScreen("Future Audi Car");
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);	
		visualinteriorscreen.tapCarImage();
		visualinteriorscreen.assertPriceIsCorrect("$180.50");
		
		visualinteriorscreen.selectNextScreen(VisualInteriorScreen.getVisualExteriorCaption());
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);	
		visualinteriorscreen.tapExterior();
		visualinteriorscreen.assertPriceIsCorrect("$250.50");
			
		visualinteriorscreen.selectNextScreen("Futire Jet Car");
		visualinteriorscreen.selectService(visualjetservice);
		visualinteriorscreen.tapCarImage();
		visualinteriorscreen.assertPriceIsCorrect("$240.50");
		
		
		//Select services
		visualinteriorscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.assertServicePriceValue(_dye_price);
		selectedservicescreen.assertServiceAdjustmentsValue(_dye_adjustments);
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE);
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.openServiceDetails(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		selectedservicescreen.assertServicePriceValue(_disk_ex_srvc_price);
		selectedservicescreen.clickAdjustments();
		selectedservicescreen.assertAdjustmentValue(_disk_ex_srvc_adjustment,
				_disk_ex_srvc_adjustment_value);
		selectedservicescreen.selectAdjustment(_disk_ex_srvc_adjustment);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen
				.assertServiceAdjustmentsValue(_disk_ex_srvc_adjustment_value_ed);
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		PriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		pricematrix.selectPriceMatrix(_pricematrix);
		pricematrix.setSizeAndSeverity(_size, _severity);
		pricematrix.assertPriceCorrect(_price);
		pricematrix.assertNotesExists();
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		pricematrix.selectDiscaunt(_discaunt_us);
		pricematrix.clickSaveButton();
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);		
		servicesscreen.selectNextScreen("Test Section");

		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.setEngineCondition("Really Bad");
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.setJustOnePossibleAnswer("One");
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.setMultipleAnswersCopy("Test Answer 3");
		questionsscreen.setMultipleAnswersCopy("Test Answer 4");
		questionsscreen.swipeScreenRight();
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen(appiumdriver);
		//questionsscreen.setFreeText("Test Text1");
		
		questionsscreen.selectNextScreen("Follow up Requested");
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.drawSignature();
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.setSampleQuestion("Answers 1");
		
		questionsscreen.selectNextScreen("Ins. Info");
		questionsscreen.selectNextScreen("BATTERY PERFORMANCE");
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.setBetteryTerminalsAnswer("Immediate Attention Required");
		questionsscreen.setCheckConditionOfBatteryAnswer("Immediate Attention Required");
		
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectTaxPoint("Test Answer 1");
		
		servicesscreen.clickSaveButton();
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		myinspectionsscreen.selectInspectionForCopy(inspNumber);
		vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		Assert.assertEquals(vehiclescreeen.getMake(), _make);
		Assert.assertEquals(vehiclescreeen.getModel(), _model);
		Assert.assertEquals(vehiclescreeen.getYear(), _year);
		
		vehiclescreeen.selectNextScreen(VisualInteriorScreen.getVisualInteriorCaption());		
		vehiclescreeen.selectNextScreen("Future Audi Car");
		vehiclescreeen.selectNextScreen(VisualInteriorScreen.getVisualExteriorCaption());	
		vehiclescreeen.selectNextScreen("Futire Jet Car");
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		
		servicesscreen.selectNextScreen("Follow up Requested");
		SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen(appiumdriver);
		Assert.assertTrue(singlepageinspectionscreen.isSignaturePresent());
		singlepageinspectionscreen.selectNextScreen("BATTERY PERFORMANCE");
		questionsscreen.swipeScreenRight();		
		Assert.assertTrue(singlepageinspectionscreen.isAnswerPresent("Test Answer 1"));
		servicesscreen.clickSaveButton();
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspNumber), "$837.99"); 
				//.getFirstInspectionPriceValue(), "$837.99");
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 16507:Create inspection from WO
	@Test(testName = "Test Case 16507:Create inspection from WO", description = "Create inspection from WO")
	public void testCreateInspectionFromWO() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "Buick";
		final String _model = "Electra";
		final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		final String pricevalue = "0";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		//Create WO1
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
		List<MobileElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (MobileElement closebtn : closebtns)
			if (closebtn.isDisplayed())
				closebtn.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
	
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
	
		
		//Test case
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber1), PricesCalculations.getPriceRepresentation(pricevalue));
		myworkordersscreen.selectWorkOrderNewInspection(wonumber1);
		vehiclescreeen.verifyMakeModelyearValues(_make, _model, _year);
		vehiclescreeen.cancelOrder();
		myworkordersscreen.clickHomeButton();
		
	}

	@Test(testName = "Test Case 25526:Create Invoice with two WOs and copy vehicle", description = "Create Invoice with two WOs and copy vehicle")
	public void testCreateInvoiceWithTwoWOsAndCopyVehicle() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String _make = "Buick";
		final String _model = "Electra";
		final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		final String[] vehicleparts = { "Cowl, Other", "Hood", };
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		//Create WO1
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
		List<MobileElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (MobileElement closebtn : closebtns)
			if (closebtn.isDisplayed())
				closebtn.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		//vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber1), "$13.50");
		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getMake(), _make);
		Assert.assertEquals(vehiclescreeen.getModel(), _model);
		//Assert.assertEquals(vehiclescreeen.getYear(), _year);
		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
		invoiceinfoscreen.setPO("23");
		invoiceinfoscreen.addWorkOrder(wonumber1);
		final String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
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
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
		invoiceswebpage.setSearchInvoiceNumber(invoicenum);
		invoiceswebpage.clickFindButton();
		Assert.assertTrue(invoiceswebpage.isInvoiceNumberExists(invoicenum));
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	//Test Case 23401:Test 'Change customer' option for inspection
	@Test(testName = "Test Case 23401:Test 'Change customer' option for inspection", description = "Test 'Change customer' option for inspection")
	public void testChangeCustomerOptionForInspection() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new HomeScreen(appiumdriver);
		MainScreen mainscreen = homescreen.clickLogoutButton();
		HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_CHANGE_INSPTYPE);
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompany("USG");
		claimscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		vehiclescreeen.clickSaveButton();
		myinspectionsscreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		VisualInteriorScreen visualscreen = new VisualInteriorScreen(appiumdriver);
		visualscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getInspectionCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		vehiclescreeen.clickSaveButton();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 23402:Test 'Change customer' option for Inspection as change 'Wholesale' to 'Retail' and vice versa'
	@Test(testName = "Test Case 23402:Test 'Change customer' option for Inspection as change 'Wholesale' to 'Retail' and vice versa'", description = "Test 'Change customer' option for Inspection as change 'Wholesale' to 'Retail' and vice versa'")
	public void testChangeCustomerOptionForInspectionAsChangeWholesaleToRetailAndViceVersa() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_CHANGE_INSPTYPE);
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompany("USG");
		claimscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");	
		vehiclescreeen.clickSaveButton();

		myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		myinspectionsscreen.selectInspectionInTable(inspectionnumber);
		myinspectionsscreen.clickChangeCustomerpopupMenu();		
		myinspectionsscreen.customersPopupSwitchToRetailMode();
		myinspectionsscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
				
		myinspectionsscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		VisualInteriorScreen visualscreen = new VisualInteriorScreen(appiumdriver);
		visualscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		Assert.assertTrue(vehiclescreeen.getInspectionCustomer().contains(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER));
		vehiclescreeen.clickSaveButton();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 23421:Test 'Change customer' option for Inspections based on type with preselected Companies
	@Test(testName = "Test Case 23421:Test 'Change customer' option for Inspections based on type with preselected Companies", description = "Test 'Change customer' option for Inspections based on type with preselected Companies")
	public void testChangeCustomerOptionForInspectionsBasedOnTypeWithPreselectedCompanies() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";

		final String _color = "Black";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.TYPEWITHPRESELECTEDCOMPANIES_INSPTYPE);
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompany("USG");	
		claimscreen.clickSaveButton();
		myinspectionsscreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getInspectionCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		vehiclescreeen.clickSaveButton();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 23422:Test 'Change customer' option for Work Order
	@Test(testName = "Test Case 23422:Test 'Change customer' option for Work Order", description = "Test 'Change customer' option for Work Order")
	public void testChangeCustomerOptionForWorkOrder() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_CLIENT_CHANGING_ON);
		myworkordersscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("3/4\" - Penny Size");
		
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");	
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);	
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancelButton();	
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23423:Test 'Change customer' option for WO based on type with preselected Companies
	@Test(testName = "Test Case 23423:Test 'Change customer' option for WO based on type with preselected Companies", description = "Test 'Change customer' option for WO based on type with preselected Companies")
	public void testChangeCustomerOptionForWOBasedOnTypeWithPreselectedCompanies() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";

		final String _color = "Black";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_WITH_PRESELECTED_CLIENTS);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("3/4\" - Penny Size");
		
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");	
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("4");
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		
		Thread.sleep(45000);
		myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);	
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreeen = new VehicleScreen(appiumdriver); 
		Assert.assertEquals(vehiclescreeen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancelButton();	
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23424:Test 'Change customer' option for WO as change 'Wholesale' to 'Retail' and vice versa'
	@Test(testName = "Test Case 23424:Test 'Change customer' option for WO as change 'Wholesale' to 'Retail' and vice versa'", description = "Test 'Change customer' option for WO as change 'Wholesale' to 'Retail' and vice versa'")
	public void testChangeCustomerOptionForWOAsChangeWholesaleToRetailAndViceVersa() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_CLIENT_CHANGING_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("3/4\" - Penny Size");
		
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");	
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("4");
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.selectWorkOrder(wonumber);	
		myworkordersscreen.clickChangeCustomerPopupMenu();
		myworkordersscreen.customersPopupSwitchToRetailMode();
		myworkordersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.openWorkOrderDetails(wonumber);	
		vehiclescreeen =  new VehicleScreen(appiumdriver);
		Assert.assertTrue(vehiclescreeen.getWorkOrderCustomer().contains(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER));
		servicesscreen.clickCancelButton();		
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23428:'Block for the same VIN' is ON. Verify 'Duplicate VIN ' message when create 2 WO with one VIN
	@Test(testName = "Test Case 23428:'Block for the same VIN' is ON. Verify 'Duplicate VIN ' message when create 2 WO with one VIN", description = "'Block for the same VIN' is ON. Verify 'Duplicate VIN ' message when create 2 WO with one VIN")
	public void testBlockForTheSameVINIsONVerifyDuplicateVINMessageWhenCreate2WOWithOneVIN() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_VIN_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");	
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSaveButton();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("You can't create Work Order for type '" + iOSInternalProjectConstants.WOTYPE_BLOCK_VIN_ON + "' for VIN '" + VIN + "' because it was already created"));
		ordersummaryscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23459:'Block for the same Services' is ON. Verify 'Duplicate services message' when create 2 WO with one service
	@Test(testName = "Test Case 23459:'Block for the same Services' is ON. Verify 'Duplicate services message' when create 2 WO with one service", description = "'Block for the same Services' is ON. Verify 'Duplicate services message' when create 2 WO with one service")
	public void testBlockForTheSameServicesIsONVerifyDuplicateServicesMessageWhenCreate2WOWithOneService() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.selectService("AMoneyService_AdjustHeadlight");
		
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");	
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSaveButton();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingCancel();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23432:WO: Test 'Edit' option of 'Duplicate services' message for WO
	@Test(testName="Test Case 23432:WO: Test 'Edit' option of 'Duplicate services' message for WO", description = "'WO: Test 'Edit' option of 'Duplicate services' message for WO")
	public void testEditOptionOfDuplicateServicesMessageForWO() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.selectService("AMoneyService_AdjustHeadlight");
		
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");	
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSaveButton();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingEdit();
		ordersummaryscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
        servicesscreen.removeSelectedServices("AMoneyService_AdjustHeadlight");
		//servicesscreen.removeSelectedServices("AMoneyService_AdjustHeadlight" + ", $0.00 x 1.00");
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
	}
	
	//Test Case 23433:WO: Test 'Override' option of 'Duplicate services' message for WO.
	@Test(testName="Test Case 23433:WO: Test 'Override' option of 'Duplicate services' message for WO", description = "'WO: Test 'Override' option of 'Duplicate services' message for WO.")
	public void testOverrideOptionOfDuplicateServicesMessageForWO() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.selectService("AMoneyService_AdjustHeadlight");
		
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");	
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("4");
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingOverride();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
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
		
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.selectService("AMoneyService_AdjustHeadlight");
		
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");	
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("4");
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
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
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.VITALY_TEST_INSPTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setPO(_po);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompany("USG");	
		claimscreen.setClaim("QWERTY");
		claimscreen.setAccidentDate();
		claimscreen.selectNextScreen(VisualInteriorScreen
				.getVisualInteriorCaption());
		VisualInteriorScreen visualinteriorscreen = new VisualInteriorScreen(appiumdriver);
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		visualinteriorscreen.tapInteriorWithCoords(1);
		visualinteriorscreen.tapInteriorWithCoords(2);
		visualinteriorscreen.assertPriceIsCorrect("$520.00");
		visualinteriorscreen.assertVisualPriceIsCorrect("$140.00");
		visualinteriorscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(VisualInteriorScreen
				.getVisualExteriorCaption());
		visualinteriorscreen = new VisualInteriorScreen(appiumdriver);
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		VisualInteriorScreen.tapExteriorWithCoords(100, 500);
		visualinteriorscreen.assertPriceIsCorrect("$570.00");
		visualinteriorscreen.assertVisualPriceIsCorrect("$100.00");
		
		visualinteriorscreen.selectNextScreen("Default");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.switchOffOption("PDR");
		pricematrix.selectDiscaunt("SR_S5_Mt_Money");
		pricematrix.selectDiscaunt("SR_S5_Mt_Money_DE_TE");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		pricematrix.clickDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		Assert.assertTrue(pricematrix.getDiscauntPriceAndValue(iOSInternalProjectConstants.SR_S1_MONEY).contains("$2,000.00 x 3.00"));
		pricematrix.selectPriceMatrix(_pricematrix2);
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, "VERY LIGHT");
		
		pricematrix.selectNextScreen("Matrix Labor");
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(_pricematrix3);
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, "LIGHT");
		
		pricematrix.selectPriceMatrix(_pricematrix4);
		pricematrix.switchOffOption("PDR");
		pricematrix.setTime("1");
		pricematrix.selectDiscaunt("Little Service");
		pricematrix.selectDiscaunt("Disc_Ex_Service1");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);

		pricematrix.selectNextScreen("All Services");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		SelectedServiceDetailsScreen selectedservicedetailsscreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicedetailsscreen.clickVehiclePartsCell();
		selectedservicedetailsscreen.selectVehiclePart("Roof");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailsscreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicedetailsscreen.clickVehiclePartsCell();
		selectedservicedetailsscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		pricematrix.selectNextScreen("Test matrix33");
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(_pricematrix5);
		pricematrix.switchOffOption("PDR");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		
		servicesscreen.selectNextScreen("Test_Package_3PrMatrix");
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("SR_S2_MoneyDisc_TE");
		
		servicesscreen.selectNextScreen("New_Test_Image");
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		//Helpers.tapCarImage();
		visualinteriorscreen.tapCarImage();
		visualinteriorscreen.assertPriceIsCorrect("$13,145.50");
		visualinteriorscreen.assertVisualPriceIsCorrect("$80.00");
		
		visualinteriorscreen.clickSaveButton();
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		servicesscreen.selectNextScreen("Default");
		pricematrix = new PriceMatrixScreen(appiumdriver);
		
		Assert.assertTrue(pricematrix.isPriceMatrixSelected(_pricematrix1));
		Assert.assertTrue(pricematrix.isPriceMatrixSelected(_pricematrix2));
		
		pricematrix.selectNextScreen("Matrix Labor");
		Assert.assertTrue(pricematrix.isPriceMatrixSelected(_pricematrix3));
		Assert.assertTrue(pricematrix.isPriceMatrixSelected(_pricematrix4));
		pricematrix.selectNextScreen("Test matrix33");
		Assert.assertTrue(pricematrix.isPriceMatrixSelected(_pricematrix5));
		
		servicesscreen.cancelOrder();
		myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		String copiedinspnumber = vehiclescreeen.getInspectionNumber();
		servicesscreen.clickSaveButton();
		myinspectionsscreen.assertInspectionExists(copiedinspnumber);
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 24075:SR: Test that selected services on SR are copied to Inspection based on SR
	@Test(testName="Test Case 24075:SR: Test that selected services on SR are copied to Inspection based on SR", description = "Test that selected services on SR are copied to Inspection based on SR")
	public void testThatSelectedServicesOnSRAreCopiedToInspectionBasedOnSR() throws Exception {
		final String VIN  = "1D7HW48NX6S507810";

				
		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen(appiumdriver);
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen(appiumdriver);
		
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		CustomersScreen customersscreen = new CustomersScreen(appiumdriver);
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		servicerequestsscreen.selectServiceRequestType("SR_only_Acc_Estimate");
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		servicedetailsscreen.setServiceQuantityValue("14");
		servicedetailsscreen.saveSelectedServiceDetails();
		//servicesscreen.setSelectedServiceRequestServicePrice(iOSInternalProjectConstants.DYE_SERVICE, "14");

		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		questionsscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("No"))
				.click();
	
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(newsrnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSPTYPE_FOR_SR_INSPTYPE);
	
		String inspectnumber = vehiclescreeen.getInspectionNumber();		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.DYE_SERVICE, "$10.00 x 14.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.VPS1_SERVICE, "%20.000");
		servicesscreen.assertServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.WHEEL_SERVICE, "$70.00 x 1.00");
			
		servicesscreen.clickSaveButton();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(newsrnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		MyInspectionsScreen myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		myinspectionsscreen.assertInspectionExists(inspectnumber);
		myinspectionsscreen.clickBackServiceRequest();
		servicerequestsscreen.clickHomeButton();
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
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_CLIENT_CHANGING_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Ford", "Expedition", "2003");
		String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		Helpers.waitABit(40*1000);
		resrtartApplication();
		MainScreen mainscreen = new MainScreen(appiumdriver);
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertTrue(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.selectContinueWorkOrder(wonumber);
		Thread.sleep(30*1000);
		Assert.assertEquals(vehiclescreeen.getInspectionNumber(), wonumber);
		
		resrtartApplication();
		mainscreen = new MainScreen(appiumdriver);
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

		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
			
			
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
			
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_EST_WO_REQ_SRTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
			
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
	
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.drawSignature();
		servicesscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		//Thread.sleep(4000);
		//Helpers.swipeScreen();
		//Thread.sleep(2000);
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSaveButton();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddAppointmentButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();
		//final String fromapp = servicerequestsscreen.getFromAppointmetValue();
		//final String toapp = servicerequestsscreen.getToAppointmetValue();
		
		servicerequestsscreen.setSubjectAppointmet("SR-APP");
		servicerequestsscreen.setAddressAppointmet("Maidan");
		servicerequestsscreen.setCityAppointmet("Kiev");
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.selectCloseAction();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);	
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(newsrnumber), "Scheduled");	
		servicerequestsscreen.selectServiceRequest(newsrnumber);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
	}
	
	//Test Case 24677:SR: Verify 'Summary' action for appointment on SR's calendar
	@Test(testName = "Test Case 24677:SR: Verify 'Summary' action for appointment on SR's calendar", description = "SR: Verify 'Summary' action for appointment on SR's calendar")
	public void testSRVerifySummaryActionForAppointmentOnSRsCalendar() throws Exception {
		final String VIN = "QWERTYUI123";
		final String srappsubject = "SR-APP";
		final String srappaddress = "Maidan";
		final String srappcity = "Kiev";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
				
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
				
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_EST_WO_REQ_SRTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
				
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();
				
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.drawSignature();
		servicesscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSaveButton();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "On Hold");
		servicerequestsscreen.selectServiceRequest(srnumber);
		
		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddAppointmentButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();
		//final String fromapp = servicerequestsscreen.getFromAppointmetValue();
		//final String toapp = servicerequestsscreen.getToAppointmetValue();
			
		servicerequestsscreen.setSubjectAppointmet(srappsubject);
		servicerequestsscreen.setAddressAppointmet(srappaddress);
		servicerequestsscreen.setCityAppointmet(srappcity);
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.selectCloseAction();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);		
		servicerequestsscreen.selectDetailsRequestAction();	
		//String summaryapp = servicerequestsscreen.getSummaryAppointmentsInformation();
		Assert.assertTrue(servicerequestsscreen.isSRSummaryAppointmentsInformation());
		
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 25009:WO HD: Verify that only assigned services on Matrix Panel is available as additional services", description = "WO HD: Verify that only assigned services on Matrix Panel is available as additional services")
	public void testWOVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_PRICE_MATRIX);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mercedes-Benz", "Sprinter", "2014");
		vehiclescreeen.selectNextScreen("Zayats test pack");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("VP1 zayats");				
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.switchOffOption("PDR");
		Assert.assertTrue(pricematrix.isDiscauntPresent(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS));
		Assert.assertTrue(pricematrix.isDiscauntPresent("Wheel"));		
		
		pricematrix.selectPriceMatrix("VP2 zayats");
		pricematrix.switchOffOption("PDR");
		Assert.assertTrue(pricematrix.isDiscauntPresent("Dye"));
		pricematrix.selectDiscaunt("Dye");
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.cancelOrder();
		
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 25011:Inspections HD: verify that only assigned services on Matrix Panel is available as additional services", description = "Inspections: verify that only assigned services on Matrix Panel is available as additional services")
	public void testInspectionsVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
			
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_TYPE_FOR_PRICE_MATRIX);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mercedes-Benz", "Sprinter", "2014");
		String inspnum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Price Matrix Zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.switchOffOption("PDR");
		Assert.assertTrue(pricematrix.isDiscauntPresent(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS));
		Assert.assertTrue(pricematrix.isDiscauntPresent("Wheel"));		
		
		pricematrix.selectPriceMatrix("VP2 zayats");
		pricematrix.switchOffOption("PDR");
		Assert.assertTrue(pricematrix.isDiscauntPresent("Dye"));
		pricematrix.selectDiscaunt("Dye");
		pricematrix.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		vehiclescreeen.clickSaveButton();
		myinspectionsscreen.assertInspectionExists(inspnum);
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 25421:WO HD: Verify that on Invoice 'Summary' main service of the panel is displayed as first then additional services", description = "WO HD: Verify that on Invoice 'Summary' main service of the panel is displayed as first then additional services")
	public void testWOVerifyThatOnInvoiceSummaryMainServiceOfThePanelIsDisplayedAsFirstThenAdditionalServices()
			throws Exception {
		
		final String VIN  = "2C3CDXBG2EH174681";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Charger", "2014");
		String wonum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats test pack");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");		
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.switchOffOption("PDR");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		pricematrix.selectDiscaunt("Wheel");		
		
		pricematrix.selectPriceMatrix("VP2 zayats");
		//pricematrix.switchOffOption("PDR");
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
			
		pricematrix.selectDiscaunt("Dye");
		pricematrix.selectDiscaunt("Wheel");
		pricematrix.selectDiscaunt("Test service zayats");

		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.approveWorkOrderWithoutSignature(wonum, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		myworkordersscreen.selectWorkOrderForAction(wonum);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("12345");
		final String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		homescreen = myworkordersscreen.clickHomeButton();
		
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickSummaryPopup();
		Assert.assertTrue(myinvoicesscreen.isSummaryPDFExists());
		myinvoicesscreen.clickHomeButton();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26054:WO Monitor: HD&Regular - Create WO for monitor", description = "WO Monitor: HD&Regular - Create WO for monitor")
	public void testWOMonitorCreateWOForMonitor()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.woExists(wonum);
		
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26008:WO Monitor: HD - Verify that it is not possible to change Service Status before Start Service", description = "WO Monitor: HD - Verify that it is not possible to change Service Status before Start Service")
	public void testWOMonitorVerifyThatItIsNotPossibleToChangeServiceStatusBeforeStartService()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		Assert.assertTrue(myworkordersscreen.woExists(wonum));
		
		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);
		
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickServiceStatusCell();		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You must start the service before you can change its status."));
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		ordermonitorscreen.setCompletedServiceStatus();
		//ordermonitorscreen.clickServiceDetailsDoneButton();
		ordermonitorscreen.verifyPanelsStatuses(iOSInternalProjectConstants.WHEEL_SERVICE, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26009:WO Monitor: HD - Verify that it is not possible to change Phase Status before Start phase", description = "WO Monitor: HD - Verify that it is not possible to change Phase Status before Start phase")
	public void testWOMonitorVerifyThatItIsNotPossibleToChangePhaseStatusBeforeStartPhase()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		Assert.assertTrue(myworkordersscreen.woExists(wonum));
		
		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);
		
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		Assert.assertTrue(ordermonitorscreen.isRepairPhaseExists());
		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonPresent());
		ordermonitorscreen.clicksRepairPhaseLine();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You must start the phase before you can change its status."));
		ordermonitorscreen.clickStartPhase();
		
		
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertFalse(ordermonitorscreen.isStartPhaseButtonPresent());
		Assert.assertTrue(ordermonitorscreen.isServiceStartDateExists());
		
		ordermonitorscreen.clickServiceStatusCell();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You cannot change the status of services for this phase. You can only change the status of the whole phase."));
		ordermonitorscreen.clickServiceDetailsDoneButton();
		
		ordermonitorscreen.clicksRepairPhaseLine();
		ordermonitorscreen.setCompletedPhaseStatus();
		
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DISC_EX_SERVICE1, "Completed");
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DYE_SERVICE, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26014:WO Monitor: HD - Verify that Start date is set when Start Service", description = "WO Monitor: HD - Verify that Start date is set when Start Service")
	public void testWOMonitorVerifyThatStartDateIsSetWhenStartService()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		Assert.assertTrue(myworkordersscreen.woExists(wonum));
		
		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();

		teamworkordersscreen.clickOnWO(wonum);		
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertFalse(ordermonitorscreen.isServiceStartDateExists());
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isServiceStartDateExists());
		ordermonitorscreen.clickServiceDetailsDoneButton();
		
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}
	
	//@Test(testName="Test Case 26016:WO Monitor: HD - Verify that for % service message about change status is not shown", description = "WO Monitor: HD - Verify that for % service message about change status is not shown")
	public void testWOMonitorVerifyThatForPercentServiceMessageAboutChangeStatusIsNotShown()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		Assert.assertTrue(myworkordersscreen.woExists(wonum));
		
		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		
		teamworkordersscreen.clickOnWO(wonum);		
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.VPS1_SERVICE);
		ordermonitorscreen.clickServiceDetailsDoneButton();
		
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26013:WO Monitor: HD - Verify that when change Status for Phase with 'Do not track individual service statuses' ON Phase status is set to all services assigned to phase", 
			description = "WO Monitor: HD - Verify that when change Status for Phase with 'Do not track individual service statuses' ON Phase status is set to all services assigned to phase")
	public void testWOMonitorVerifyThatWhenChangeStatusForPhaseWithDoNotTrackIndividualServiceStatusesONPhaseStatusIsSetToAllServicesAssignedToPhase()
			throws Exception {
		
		final String VIN  = "1D3HV13T19S825733";
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String _price = "$100.00";
		final String _discaunt_us = "Discount 10-20$";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreeen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		
		servicesscreen.selectService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		PriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		pricematrix.selectPriceMatrix(_pricematrix);
		pricematrix.setSizeAndSeverity(_size, _severity);
		pricematrix.assertPriceCorrect(_price);
		pricematrix.assertNotesExists();
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		pricematrix.selectDiscaunt(_discaunt_us);
		pricematrix.clickSaveButton();
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);		
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		Assert.assertTrue(myworkordersscreen.woExists(wonum));
		
		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
        Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		ordermonitorscreen.setCompletedServiceStatus();
		//ordermonitorscreen.clickServiceDetailsDoneButton();
		ordermonitorscreen.verifyPanelsStatuses(iOSInternalProjectConstants.WHEEL_SERVICE, "Completed");
		
		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonPresent());
		ordermonitorscreen.clickStartPhase();
		
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		ordermonitorscreen.clickServiceStatusCell();;
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You cannot change the status of services for this phase. You can only change the status of the whole phase."));
		ordermonitorscreen.clickServiceDetailsDoneButton();
		
		ordermonitorscreen.clicksRepairPhaseLine();
		ordermonitorscreen.setCompletedPhaseStatus();
		
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE, "Completed");
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DISC_EX_SERVICE1, "Completed");
		ordermonitorscreen.verifyPanelStatus(iOSInternalProjectConstants.DYE_SERVICE, "Completed");
				
		
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		ordermonitorscreen.verifyPanelStatusInPopup(iOSInternalProjectConstants.DYE_SERVICE, "Completed");
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		ordermonitorscreen.verifyPanelStatusInPopup(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26214:SR: HD - Verify that SR is created correctly when select owner on Vehicle info", 
			description = "SR: HD - Verify that SR is created correctly when select owner on Vehicle info")
	public void testSRHDVerifyThatSRIsCreatedCorrectlyWhenSelectOwnerOnVehicleInfo()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String owner = "Avalon";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_CHECKIN_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectOwnerT(owner);
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(VIN));
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26215:SR: HD - Verify that Check In action is present for SR when appropriate SR type has option Check in ON", 
			description = "SR: HD - Verify that Check In action is present for SR when appropriate SR type has option Check in ON")
	public void testSRHDVerifyThatCheckInActionIsPresentForSRWhenAppropriateSRTypeHasOptionCheckInON()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String owner = "Avalon";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_CHECKIN_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectOwnerT(owner);
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(VIN));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26216:SR: HD - Verify that Check In action is changed to Undo Check In after pressing on it and vice versa", 
			description = "SR: HD - Verify that Check In action is changed to Undo Check In after pressing on it and vice versa")
	public void testSRHDVerifyThatCheckInActionIsChangedToUndoCheckInAfterPressingOnItAndViceVersa()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String owner = "Avalon";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_CHECKIN_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectOwnerT(owner);
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(VIN));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectUndoCheckMenu();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26217:SR: HD - Verify that filter 'Not Checked In' is working correctly", 
			description = "SR: HD - Verify that filter 'Not Checked In' is working correctly")
	public void testSRHDVerifyThatFilterNotCheckedInIsWorkingCorrectly()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		final String owner = "Avalon";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_CHECKIN_ON);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectOwnerT(owner);
		vehiclescreeen.selectNextScreen("Package_for_Monitor");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(VIN));
		//servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.applyNotCheckedInFilter();
		Assert.assertEquals(servicerequestsscreen.getFirstServiceRequestNumber(), srnumber);
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.resetFilter();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26662:Invoices: HD - Verify that when ‘Customer approval required’ is set to ON - auto email is not sent when approval does not exist", 
			description = "HD - Verify that when ‘Customer approval required’ is set to ON - auto email is not sent when approval does not exist")
	public void testHDVerifyThatWhenCustomerApprovalRequiredIsSetToONAutoEmailIsNotSentWhenApprovalDoesNotExist()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("Zayats test pack");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		myinvoicesscreen.clickInvoiceApproveButton(invoicenumber);
		
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.drawApprovalSignature();
		approveinspscreen.clickApproveButton();
		myinvoicesscreen.clickHomeButton();

		Thread.sleep(60*1000);
		boolean search = false;
		final String invpoicereportfilenname = invoicenumber + ".pdf";
		for (int i= 0; i < 7; i++) {
			search = MailChecker.searchEmailAndGetAttachment("test.cyberiansoft@gmail.com", "ZZzz11!!",
					"Invoice #1" + invoicenumber + " from Recon Pro Development QA", "reconpro@cyberiansoft.com", invpoicereportfilenname);
			if (!search) {
				Thread.sleep(30*1000);
			} else if (search)
				break;
		}
		if (search) {
			File pdfdoc = new File(invpoicereportfilenname);
			String pdftext = PDFReader.getPDFText(pdfdoc);
			Assert.assertTrue(pdftext.contains(VIN));
			Assert.assertTrue(pdftext.contains(iOSInternalProjectConstants.WHEEL_SERVICE));
			Assert.assertTrue(pdftext.contains(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS));
			Assert.assertTrue(pdftext.contains("75.00"));
		}
	}
	
	@Test(testName="Test Case 26663:Invoices: HD - verify that when ‘Customer approval required’ is set to OFF - auto email is sent when invoice is auto approved", 
			description = "Invoices: HD - verify that when ‘Customer approval required’ is set to OFF - auto email is sent when invoice is auto approved")
	public void testHDVerifyThatWhenCustomerApprovalRequiredIsSetToOffAutoEmailIsSentWhenInvoiceAsAutoApproved()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";
		/*appiumdriverInicialize();
		MainScreen mainscreen = new MainScreen(appiumdriver);
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		*/
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("Zayats test pack");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		myinvoicesscreen.clickHomeButton();

		Thread.sleep(60*1000);
		boolean search = false;
		final String invpoicereportfilenname = invoicenumber + ".pdf";
		for (int i= 0; i < 7; i++) {
			search = MailChecker.searchEmailAndGetAttachment("test.cyberiansoft@gmail.com", "ZZzz11!!",
					"Invoice #" + invoicenumber + " from Recon Pro Development QA", "reconpro@cyberiansoft.com", invpoicereportfilenname);
			if (!search) {
				Thread.sleep(30*1000);
			} else if (search)
				break;
		}
		if (search) {
			File pdfdoc = new File(invpoicereportfilenname);
			String pdftext = PDFReader.getPDFText(pdfdoc);
			Assert.assertTrue(pdftext.contains(VIN));
			Assert.assertTrue(pdftext.contains(iOSInternalProjectConstants.WHEEL_SERVICE));
			Assert.assertTrue(pdftext.contains(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS));
			Assert.assertTrue(pdftext.contains("75.00"));
		} else
			Assert.assertTrue(false, "Can't find e-mail with Invoice # : " + invoicenumber);

	}
	
	@Test(testName="Test Case 26690:Invoices: HD - Verify that print icon is shown next to invoice when it was printed (My Invoices)", 
			description = "Invoices: HD - Verify that print icon is shown next to invoice when it was printed (My Invoices)")
	public void testHDVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedMyInvoices()
			throws Exception {
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String invoicenum = myinvoicesscreen.getFirstInvoiceValue();
		myinvoicesscreen.printInvoice(invoicenum, "TA_Print_Server");
		myinvoicesscreen.clickHomeButton();
		Helpers.waitABit(20000);
		myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertTrue(myinvoicesscreen.isInvoicePrintButtonExists(invoicenum));
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 26691:Invoices: HD - Verify that print icon is shown next to invoice when it was printed (Team Invoices)", 
			description = "Invoices: HD - Verify that print icon is shown next to invoice when it was printed (Team Invoices)")
	public void testHDVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedTeamInvoices()
			throws Exception {
		
		homescreen = new HomeScreen(appiumdriver);
		TeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
		teaminvoicesscreen.printInvoice(invoicenum, "TA_Print_Server");
		teaminvoicesscreen.clickHomeButton();
		Helpers.waitABit(20000);
		teaminvoicesscreen = homescreen.clickTeamInvoices();
		Assert.assertTrue(teaminvoicesscreen.isInvoicePrintButtonExists(invoicenum));
		teaminvoicesscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28473:Invoices: HD - Verify that red 'A' icon is present for invoice with customer approval ON and no signature", 
			description = "Invoices: HD - Verify that red 'A' icon is present for invoice with customer approval ON and no signature")
	public void testHDVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalONAndNoSignature()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";
		appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
		MainScreen mainscreen = new MainScreen(appiumdriver);
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("Zayats test pack");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveRedButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28476:Invoices: HD - Verify that grey 'A' icon is present for invoice with customer approval OFF and no signature", 
			description = "Invoices: HD - Verify that grey 'A' icon is present for invoice with customer approval OFF and no signature")
	public void testHDVerifyThatGreyAIconIsPresentForInvoiceWithCustomerApprovalOFFAndNoSignature()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";
		appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
		MainScreen mainscreen = new MainScreen(appiumdriver);
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("Zayats test pack");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveGreyButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28478:Invoices: HD - Verify that 'A' icon is not present for invoice when signature exists", 
			description = "Invoices: HD - Verify that 'A' icon is not present for invoice when signature exists")
	public void testHDVerifyThatAIconIsNotPresentForInvoiceWhenSignatureExists()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("Zayats test pack");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumberapproveon = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumberapproveon);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumberapproveon));
		myinvoicesscreen.selectInvoiceForApprove(invoicenumberapproveon);
		
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.drawApprovalSignature();
		approveinspscreen.clickApproveButton();
		myinvoicesscreen = new MyInvoicesScreen(appiumdriver);
		
		Assert.assertFalse(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumberapproveon));
		myinvoicesscreen.clickHomeButton();
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval OFF
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INV_PRINT);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("Zayats test pack");
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumbeapprovaloff = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumbeapprovaloff);
		myinvoicesscreen.selectInvoiceForApprove(invoicenumbeapprovaloff);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.drawApprovalSignature();
		approveinspscreen.clickApproveButton();
		Assert.assertFalse(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumbeapprovaloff));	
		myinvoicesscreen.clickHomeButton();
	}	
	
	@Test(testName="Test Case 29022:SR: HD - Verify that Reject action is displayed for SR in Status Scheduled (Insp or WO) and assign for Tech", 
			description = "Test Case 29022:SR: HD - Verify that Reject action is displayed for SR in Status Scheduled (Insp or WO) and assign for Tech")
	public void testSRHDVerifyThatRejectActionIsDisplayedForSRInStatusScheduledInspOrWOAndAssignForTech()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		
		//Create first SR
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_ONLY_ACC_ESTIMATE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber1 = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber1);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();
		servicerequestsscreen.waitServiceRequestsScreenLoaded();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_WO_AUTO_CREATE);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.clickSaveButton();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber2 = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber2), "Scheduled");	
		servicerequestsscreen.selectServiceRequest(srnumber2);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 29029:SR: HD - Verify that Reject action is displayed for SR in Status OnHold (Insp or WO) and assign for Tech", 
			description = "Test Case 29029:SR: HD - Verify that Reject action is displayed for SR in Status OnHold (Insp or WO) and assign for Tech")
	public void testSRHDVerifyThatRejectActionIsDisplayedForSRInStatusOnHoldInspOrWOAndAssignForTech()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_ALL_PHASES);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("No"))
				.click();
		//Thread.sleep(3000);
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "On Hold");
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectCreateInspectionRequestAction();
		//Thread.sleep(2000);
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_MODE);
		String inspectnumber = servicerequestsscreen.getInspectionNumber();
		servicesscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.clickSaveAsFinal();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		//Thread.sleep(3000);
		MyInspectionsScreen myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		myinspectionsscreen.assertInspectionExists(inspectnumber);
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspectnumber);
		
		myinspectionsscreen.clickApproveInspections();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		//Helpers.acceptAlert();
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen(appiumdriver);		
		approveinspscreen.approveInspectionWithSelectionAndSignature(inspectnumber);
		myinspectionsscreen.clickBackServiceRequest();
		
		servicerequestsscreen.clickHomeButton();
		//Thread.sleep(3000);
		servicerequestsscreen.clickHomeButton();
		boolean onhold = false;
		for (int i= 0; i < 7; i++) {
			servicerequestsscreen = homescreen.clickServiceRequestsButton();
			if (!servicerequestsscreen.getServiceRequestStatus(srnumber).equals("On Hold")) {
				servicerequestsscreen.clickHomeButton();
				Thread.sleep(30*1000); 
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
	
	@Test(testName="Test Case 29082:SR: HD - Verify that Reject action is not displayed for SR in Status OnHold (Insp or WO) and not assign for Tech", 
			description = "Test Case 29082:SR: HD - Verify that Reject action is not displayed for SR in Status OnHold (Insp or WO) and not assign for Tech")
	public void testSRHDVerifyThatRejectActionIsNotDisplayedForSRInStatusOnHoldInspOrWOAndNotAssignForTech()
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
		srlistwebpage.selectAddServiceRequestsComboboxValue(iOSInternalProjectConstants.SR_INSP_ONLY);
		srlistwebpage.clickAddServiceRequestButton();
		
		srlistwebpage.clickCustomerEditButton();
		srlistwebpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		srlistwebpage.clickDoneButton();
		
		srlistwebpage.clickVehicleInforEditButton();
		srlistwebpage.setServiceRequestVIN(VIN);
		srlistwebpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		srlistwebpage.clickDoneButton();
		
		srlistwebpage.saveNewServiceRequest();
		String srnumber = srlistwebpage.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();
		
		resrtartApplication();
		MainScreen mainscr = new MainScreen(appiumdriver);
		mainscr.updateDatabase();
		HomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 29083:SR: HD - Verify that Reject action is not displayed for SR in Status Scheduled (Insp or WO) and not assign for Tech", 
			description = "SR: HD - Verify that Reject action is not displayed for SR in Status Scheduled (Insp or WO) and not assign for Tech")
	public void testSRHDVerifyThatRejectActionIsNotDisplayedForSRInStatusScheduledInspOrWOAndNotAssignForTech()
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
		
		MainScreen mainscr = new HomeScreen(appiumdriver).clickLogoutButton();
		mainscr.updateDatabase();
		HomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickHomeButton();
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
		mainscr = new HomeScreen(appiumdriver).clickLogoutButton();
		mainscr.updateDatabase();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectEditServiceRequestAction();
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.clickSaveButton();
		/*Helpers.getAlertTextAndAccept();
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		servicesscreen.clickSaveButton();
		Helpers.getAlertTextAndCancel();*/
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33165:WO: HD - Not multiple Service with required Panels is added one time to WO after selecting", 
			description = "WO: HD - Not multiple Service with required Panels is added one time to WO after selecting")
	public void testWOHDNotMultipleServiceWithRequiredPanelsIsAddedOneTimeToWOAfterSelecting() throws Exception {
				
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
			
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
			
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.searchServiceToSelect(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.selectVehiclePart("Deck Lid");
		selectedservicescreen.saveSelectedServiceDetails();
		String alerttext = selectedservicescreen.saveSelectedServiceDetailsWithAlert();
		Assert.assertTrue(alerttext.contains("You can add only one service '" + iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE + "'"));
		Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE), 1);
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.selectVehiclePart("Deck Lid");
		selectedservicescreen.saveSelectedServiceDetails();
		alerttext = selectedservicescreen.saveSelectedServiceDetailsWithAlert();
		Assert.assertTrue(alerttext.contains("You can add only one service '" + iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE + "'"));
		Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE), 1);
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 34562:WO: Verify that Bundle Items are shown when create WO from inspection", description = "WO: Verify that Bundle Items are shown when create WO from inspection")
	public void testWOVerifyThatBundleItemsAreShownWhenCreateWOFromInspection()
			throws Exception {
		
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		
		
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.approveInspectionApproveAllAndSignature(inspnumber);
		
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber);
		MyWorkOrdersScreen myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		servicesscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.openServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen(appiumdriver);
		selectedservicebundlescreen.assertBundleIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicebundlescreen.assertBundleIsNotSelected(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.clickServicesIcon();
		Assert.assertTrue(selectedservicebundlescreen.isBundleServiceExists(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(selectedservicebundlescreen.isBundleServiceExists(iOSInternalProjectConstants.DYE_SERVICE));
		selectedservicebundlescreen.clickCloseServicesPopup();
		selectedservicebundlescreen.clickCancelBundlePopupButton();
		servicesscreen.cancelOrder();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31743:SR: HD - Verify that when option 'Allow to close SR' is set to ON action 'Close' is shown for selected SR on status 'Scheduled' or 'On-Hold'", 
			description = "SR HD - Verify that when option 'Allow to close SR' is set to ON action 'Close' is shown for selected SR on status 'Scheduled' or 'On-Hold'")
	public void testSRHDVerifyThatWhenOptionAllowToCloseSRIsSetToONActionCloseIsShownForSelectedSROnStatusScheduledOrOnHold()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickSaveButton();

		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31748:SR: Regular - Verify that when option 'Allow to close SR' is set to OFF action 'Close' is not shown for selected SR on status 'Scheduled' or 'On-Hold'", 
			description = "SR: Regular - Verify that when option 'Allow to close SR' is set to OFF action 'Close' is not shown for selected SR on status 'Scheduled' or 'On-Hold'")
	public void testSRRegularVerifyThatWhenOptionAllowToCloseSRIsSetToOFFActionCloseIsNotShownForSelectedSROnStatusScheduledOrOnHold()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickSaveButton();

		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectRejectAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		servicerequestsscreen.waitServiceRequestsScreenLoaded();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31749:SR: HD - Verify that alert message is shown when select 'Close' action for SR - press No alert message is close", 
			description = "SR: HD - Verify that alert message is shown when select 'Close' action for SR - press No alert message is close")
	public void testSRHDVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressNoAlertMessageIsClose()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickSaveButton();

		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31750:SR: HD - Verify that alert message is shown when select 'Close' action for SR - press Yes list of status reasons is shown", 
			description = "SR: HD - Verify that alert message is shown when select 'Close' action for SR - press Yes list of status reasons is shown")
	public void testSRHDVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressYesListOfStatusReasonsIsShown()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickSaveButton();

		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.clickCancelCloseReasonDialog();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31752:SR: HD - Verify that when Status reason is selected Question section is shown in case it is assigned to reason on BO", 
			description = "SR: HD - Verify that when Status reason is selected Question section is shown in case it is assigned to reason on BO")
	public void testSRHDVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsShownInCaseItIsAssignedToReasonOnBO()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickSaveButton();
		
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.selectDoneReason("All work is done. Answer questions");
		QuestionsPopup questionspopup = new QuestionsPopup(appiumdriver);
		questionspopup.answerQuestion2("A3");
		servicerequestsscreen.clickCloseSR();
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31753:SR: HD - Verify that when Status reason is selected Question section is not shown in case it is not assigned to reason on BO", 
			description = "Test Case 31753:SR: HD - Verify that when Status reason is selected Question section is not shown in case it is not assigned to reason on BO")
	public void testSRHDVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsNotShownInCaseItIsNotAssignedToReasonOnBO()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_TYPE_ALLOW_CLOSE_SR);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.clickSaveButton();

		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
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
	
	@Test(testName="Test Case 30083:SR: HD - Verify that when create WO from SR message that vehicle parts are required is shown for appropriate services", 
			description = "SR: HD - Verify that when create WO from SR message that vehicle parts are required is shown for appropriate services")
	public void testSRHDVerifyThatWhenCreateWOFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_WO_ONLY);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		servicerequestsscreen.selectWOType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		
		for (int i = 0; i < 4; i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.indexOf("' require"));
			SelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openServiceDetails(servicedetails);
			
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
			ordersummaryscreen.clickSaveButton();
		}

		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		MyWorkOrdersScreen mywoscreen = servicerequestsscreen.clickServiceRequestSummaryOrdersButton();
		mywoscreen.woExists(wonumber);
		mywoscreen.clickServiceRequestButton();
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 30084:SR: HD - Verify that when create Inspection from SR message that vehicle parts are required is shown for appropriate services", 
			description = "SR: HD - Verify that when create Inspection from SR message that vehicle parts are required is shown for appropriate services")
	public void testSRHDVerifyThatWhenCreateInspectionFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_INSP_ONLY);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		String inspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());

		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicesscreen.clickSaveButton();
		
		for (int i = 0; i < 4; i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.indexOf("' require"));
			SelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openServiceDetails(servicedetails);
			
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
			servicesscreen.clickSaveButton();
		}
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		MyInspectionsScreen myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		myinspectionsscreen.assertInspectionExists(inspnumber);
		myinspectionsscreen.clickBackServiceRequest();
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 38749:Inspections: HD - Verify that on inspection approval screen selected price matrix value is shown", 
			description = "Verify that on inspection approval screen selected price matrix value is shown")
	public void testHDVerifyThatOnInspectionApprovalScreenSelectedPriceMatrixValueIsShown() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ);
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen("Price Matrix Zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("VP2 zayats");
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
		InspectionToolBar toolaber = new InspectionToolBar(appiumdriver);		
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$100.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$100.00");
		
		pricematrix.selectNextScreen("Hail Matrix");
		pricematrix.selectPriceMatrix("L QUARTER");
		pricematrix.setSizeAndSeverity("DIME", "VERY LIGHT");
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$65.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$165.00");
		
		pricematrix.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.clickSaveButton();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspectionForApprove(inspectionnumber);
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove("Dent Removal"));
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX));
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice("Dent Removal"), "$65.00");
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX), "$100.00");
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		pricematrix.selectNextScreen("Price Matrix Zayats");
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("VP2 zayats");
		pricematrix.clearVehicleData();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$0.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$65.00");
		vehiclescreeen.clickSaveButton();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspectionForApprove(inspectionnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice("Dent Removal"), "$65.00");
		Assert.assertFalse(approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX));
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();;
		myinspectionsscreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 31451:Inspection - HD: Verify that question section is shown per service for first selected panel when QF is not required", 
			description = "Verify that question section is shown per service for first selected panel when QF is not required")
	public void testHDVerifyThatQuestionSectionIsShownPerServiceForFirstSelectedPanelWhenQFIsNotRequired() throws Exception {

		final String[] vehicleparts = { "Front Bumper", "Grill", "Hood", "Left Fender" };
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);

		myinspectionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		for (String vehiclepart : vehicleparts)
			selectedservicedetailscreen.selectVehiclePart(vehiclepart);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		final String selectedhehicleparts = selectedservicedetailscreen.getListOfSelectedVehicleParts();
		for (String vehiclepart : vehicleparts)
			Assert.assertTrue(selectedhehicleparts.contains(vehiclepart));
		selectedservicedetailscreen.saveSelectedServiceDetails();
		for (int i = 0; i < vehicleparts.length; i++) {
			servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE, i);
			Assert.assertFalse(selectedservicedetailscreen.isQuestionFormCellExists());
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		servicesscreen.cancelOrder();
		myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 31963:Inspections: HD - Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen", 
			description = "Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen")
	public void testHDVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen() throws Exception {

		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Inspection_VIN_only");
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.getVINField().click();
		Assert.assertTrue(vehiclescreeen.getVINField().isDisplayed());
		vehiclescreeen.getVINField().click();
		Helpers.keyboadrType("\n");
		vehiclescreeen.cancelOrder();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42376:Inspections: HD - Verify that when edit inspection selected vehicle parts for services are present", 
			description = "Verify that when edit inspection selected vehicle parts for services are present")
	public void testHDVerifyThatWhenEditInspectionSelectedVehiclePartsForServicesArePresent() throws Exception {

		final String VIN = "1D7HW48NX6S507810";
		final String[] vehicleparts = { "Deck Lid", "Hood", "Roof" };
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Inspection_for_auto_WO_line_appr");
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		for (int i=0; i < vehicleparts.length; i++)
			selectedservicedetailscreen.selectVehiclePart(vehicleparts[i]);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		servicesscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		for (int i=0; i < vehicleparts.length; i++) {
			servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.SR_S1_MONEY, i);
			selectedservicedetailscreen = new SelectedServiceDetailsScreen(appiumdriver);
			Assert.assertEquals(selectedservicedetailscreen.getVehiclePartValue(), vehicleparts[i]);
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		
		servicesscreen.cancelOrder();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42388:Inspections: HD - Verify that it is possible to save as Final inspection linked to SR", description = "Verify that it is possible to save as Final inspection linked to SR")
	public void testHDVerifyThatItIsPossibleToSaveAsFinalInspectionLinkedToSR() throws Exception {
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_ALL_PHASES);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_MODE);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsDraft();
				
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		MyInspectionsScreen myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		servicesscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		Assert.assertTrue(myinspectionsscreen.isInspectionApproveButtonExists(inspectionnumber));
		myinspectionsscreen.clickServiceRequestButton();
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 33117:Inspections: HD - Verify that when final inspection is copied servises are copied without statuses (approved, declined, skipped)", 
			description = "Verify that when final inspection is copied servises are copied without statuses (approved, declined, skipped)")
	public void testVerifyThatWhenFinalInspectionIsCopiedServisesAreCopiedWithoutStatuses_Approved_Declined_Skipped() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_MODE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.answerQuestion2("A1");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);	
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("Price Matrix Zayats");
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.setSizeAndSeverity("CENT", "MEDIUM");
		pricematrix.selectDiscaunt("Test service zayats");
		pricematrix.clickSaveButton();
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SR_S4_BUNDLE);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S4_BUNDLE);	
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);	
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		
		myinspectionsscreen.clickApproveInspections();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.SR_S4_BUNDLE);
		approveinspscreen.isInspectionServiceExistsForApprove("Test service zayats");
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		approveinspscreen.clickCancelButton();
		//myinspectionsscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33116:Inspections: HD - Verify that text notes are copied to new inspections when use copy action", description = "Verify that text notes are copied to new inspections when use copy action")
	public void testVerifyThatTextNotesAreCopiedToNewInspectionsWhenUseCopyAction() throws Exception {
			
		final String VIN  = "1D7HW48NX6S507810";
		final String _notes = "Test notes for copy";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		VehicleScreen vehiclescreeen = myinspectionsscreen.selectDefaultInspectionType();
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		NotesScreen notesscreen = vehiclescreeen.clickNotesButton();
		notesscreen.setNotes(_notes);
		notesscreen.clickSaveButton();
		vehiclescreeen.clickSaveButton();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);

		vehiclescreeen = new VehicleScreen(appiumdriver);
		String copiedinspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.clickSaveButton();
		
		Assert.assertTrue(myinspectionsscreen.isNotesIconPresentForInspection(copiedinspnumber));
		notesscreen = myinspectionsscreen.openInspectionNotesScreen(copiedinspnumber);
		Assert.assertTrue(notesscreen.isNotesPresent(_notes));
		notesscreen.clickSaveButton();
		myinspectionsscreen.clickHomeButton();
	}	
	
	@Test(testName = "Test Case 33154:Inspections: HD - Verify that it is possible to approve Team Inspections use multi select", 
			description = "Verify that it is possible to approve Team Inspections use multi select")
	public void testVerifyThatItIsPossibleToApproveTeamInspectionsUseMultiSelect() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		List<String> inspnumbers = new ArrayList<String>();
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i < 3; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_MODE);
			VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			inspnumbers.add(vehiclescreeen.getInspectionNumber());
			vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
			ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
			servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_BUNDLE);
			servicesscreen.clickSaveAsFinal();
		}
		myinspectionsscreen.clickHomeButton();
		TeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 3; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspnumbers.get(i));
		}
		
		teaminspectionsscreen.clickApproveInspections();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen(appiumdriver);
		for (int i = 0; i < 3; i++) {
			approveinspscreen.selectInspectionForApprove(inspnumbers.get(i));
			approveinspscreen.clickApproveAllServicesButton();
			approveinspscreen.clickSaveButton();
		}
		approveinspscreen.clickSignButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		teaminspectionsscreen = new TeamInspectionsScreen(appiumdriver);
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 3; i++) {
			myinspectionsscreen.selectInspectionForAction(inspnumbers.get(i));
		}
		teaminspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		Assert.assertTrue(teaminspectionsscreen.isSendEmailInspectionMenuActionExists());
		teaminspectionsscreen.clickActionButton();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33869:IInspections: HD - Verify that Services on Service Package are grouped by type selected on Insp type->Wizard", 
			description = "Verify that Services on Service Package are grouped by type selected on Insp type->Wizard")
	public void testVerifyThatServicesOnServicePackageAreGroupedByTypeSelectedOnInspTypeWizard() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		List<String> inspnumbers = new ArrayList<String>();
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Inspection_group_service");
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		inspnumbers.add(vehiclescreeen.getInspectionNumber());
		vehiclescreeen.selectNextScreen("Zayats test pack");
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectNextScreen("Test_pack_for_calc");
		servicesscreen.selectGroupServiceItem("Back Glass");
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Vehicle");
		servicedetailsscreen.answerQuestion2("A1");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.selectNextScreen("SR_FeeBundle");
		servicesscreen.selectGroupServiceItem("Price Adjustment");
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("SR_S6_Bl_I1_Percent");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.drawSignature();
		servicesscreen.clickSaveAsFinal();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSaveAsFinal();
		
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Question 'Question 2' in section 'Zayats Section1' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen.assertInspectionExists(inspnumber);
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 44407:Inspections: HD - Verify that all instances of one service are copied from inspection to WO", 
			description = "Verify that all instances of one service are copied from inspection to WO")
	public void testHDVerifyThatAllInstancesOfOneServiceAreCopiedFromInspectionToWO() throws Exception {
			
		final String VIN  = "1D7HW48NX6S507810";
		
		final String firstprice = "43";
		final String secondprice = "33";
		final String secondquantity = "4";
		final String[] vehicleparts = { "Front Bumper", "Grill", "Hood" };
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("3/4\" - Penny Size");
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServicePriceValue(firstprice);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.openServiceDetailsByIndex("3/4\" - Penny Size", 1);
		servicedetailsscreen = new SelectedServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.setServicePriceValue(secondprice);
		servicedetailsscreen.setServiceQuantityValue(secondquantity);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicedetailsscreen = new SelectedServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickVehiclePartsCell();
		for (String vp: vehicleparts)
			servicedetailsscreen.selectVehiclePart(vp);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.approveInspectionApproveAllAndSignature(inspnumber);		
		
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber);
		MyWorkOrdersScreen myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		servicesscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$43.00 x 1.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$33.00 x 4.00");
		Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.SR_S1_MONEY_PANEL), vehicleparts.length);
		servicesscreen.cancelOrder();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 33918:Inspections: HD - Verify that Assign button is present when select some Tech in case Direct Assign option is set for inspection type", 
			description = "Verify that Assign button is present when select some Tech in case Direct Assign option is set for inspection type")
	public void testVerifyThatAssignButtonIsPresentWhenSelectSomeTechInCaseDirectAssignOptionIsSetForInspectionType() throws Exception {
			
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Inspection_direct_assign");
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		myinspectionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen.selectInspectionToAssign(inspnumber);
		DevicesPopupScreen devicesscreen = new DevicesPopupScreen(appiumdriver);
		Assert.assertTrue(devicesscreen.isAssignButtonDisplayed());
		devicesscreen.clickCancelButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 34285:Inspections: HD - Verify that during Line approval ''Select All'' buttons are working correctly", 
			description = "Verify that during Line approval ''Select All'' buttons are working correctly")
	public void testVerifyThatDuringLineApprovalSelectAllButtonsAreWorkingCorrectly() throws Exception {
			
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR);
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		myinspectionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Grill");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("123");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.clickSaveButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspectionForApprove(inspnumber);
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
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 30012:Inspections: HD - Verify that Approve option is not present for approved inspection in multi-select mode", 
			description = "Verify that Approve option is not present for approved inspection in multi-select mode")
	public void testVerifyThatApproveOptionIsNotPresentForApprovedInspectionInMultiselectMode() throws Exception {
			
		final String VIN  = "1D7HW48NX6S507810";
		ArrayList<String> inspections = new ArrayList<String>();
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i <2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR);
			VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			inspections.add(vehiclescreeen.getInspectionNumber());
		
			vehiclescreeen.selectNextScreen("Zayats Section1");
			QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
			questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
			myinspectionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
			ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
			servicesscreen.clickSaveButton();
			myinspectionsscreen.selectInspectionForAction(inspections.get(i));
			//myinspectionsscreen.clickApproveInspections();
			SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
			selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen(appiumdriver);
			approveinspscreen.approveInspectionApproveAllAndSignature(inspections.get(i));

		}
		myinspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		
		TeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		teaminspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickActionButton();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 30013:Inspections: HD - Verify that Approve option is present in multi-select mode only one or more not approved inspections are selected", 
			description = "Verify that Approve option is present in multi-select mode only one or more not approved inspections are selected")
	public void testVerifyThatApproveOptionIsPresentInMultiselectModeOnlyOneOrMoreNotApprovedInspectionsAreSelected() throws Exception {
			
		final String VIN  = "1D7HW48NX6S507810";
		ArrayList<String> inspections = new ArrayList<String>();
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i <2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR);
			VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(VIN);
			inspections.add(vehiclescreeen.getInspectionNumber());
		
			vehiclescreeen.selectNextScreen("Zayats Section1");
			QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
			questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
			myinspectionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
			ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
			servicesscreen.clickSaveButton();
		}
		myinspectionsscreen.selectInspectionForAction(inspections.get(0));
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.approveInspectionApproveAllAndSignature(inspections.get(0));

		myinspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertTrue(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		
		TeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		teaminspectionsscreen.clickActionButton();
		Assert.assertTrue(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickActionButton();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 30853:Inspections: HD - Verify that when option ''Draft Mode'' is set to ON - when save inspection provide prompt to a user to select either Draft or Final,"
			+ "Test Case 30855:Inspections: HD - Verify that for Draft inspection following options are not available (Approve, Create WO, Create SR, Copy inspection)", 
			description = "Verify that when option ''Draft Mode'' is set to ON - when save inspection provide prompt to a user to select either Draft or Final,"
					+ "Verify that for Draft inspection following options are not available (Approve, Create WO, Create SR, Copy inspection)")
	public void testVerifyThatWhenOptionDraftModeIsSetToONWhenSaveInspectionProvidePromptToAUserToSelectEitherDraftOrFinal() throws Exception {
			
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_MODE);
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		
		myinspectionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		SelectedServiceDetailsScreen selectedservicedetailsscreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicedetailsscreen.clickVehiclePartsCell();
		selectedservicedetailsscreen.selectVehiclePart("Hood");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailsscreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicedetailsscreen.clickVehiclePartsCell();
		selectedservicedetailsscreen.selectVehiclePart("Grill");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsDraft();
		
		Assert.assertTrue(myinspectionsscreen.isDraftIconPresentForInspection(inspnumber));
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(myinspectionsscreen.isApproveInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCreateWOInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCreateServiceRequestInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCopyInspectionMenuActionExists());
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.clickDoneButton();
		
		homescreen = myinspectionsscreen.clickHomeButton();
		TeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		Assert.assertTrue(teaminspectionsscreen.isDraftIconPresentForInspection(inspnumber));
		teaminspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32286:Inspections: HD - Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount", 
			description = "Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount")
	public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListColumnApprovedAmount() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
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
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspectionForApprove(inspnumber);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)");
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
		Thread.sleep(10000);
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
		Assert.assertEquals(inspectionspage.getInspectionApprovedTotal(inspnumber), "$2000.00");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName="Test Case 51336:WO: HD - Verify that approve WO is working correct under Team WO", 
			description = "WO: HD - Verify that approve WO is working correct under Team WO")
	public void testWOVerifyThatApproveWOIsWorkingCorrectUnderTeamWO()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		final String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.clickSaveButton();
		
		homescreen = myworkordersscreen.clickHomeButton();
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("All locations");
		teamworkordersscreen.clickSearchSaveButton();
		Assert.assertTrue(teamworkordersscreen.woExists(wonumber));
		Assert.assertTrue(teamworkordersscreen.isWorkOrderHasApproveIcon(wonumber));
		teamworkordersscreen.approveWorkOrder(wonumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		Assert.assertTrue(teamworkordersscreen.isWorkOrderHasActionIcon(wonumber));
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 27717:Invoices: HD - Verify that it is posible to add payment from device for draft invoice", 
			description = "Invoices: HD - Verify that it is posible to add payment from device for draft invoice")
	public void testInvoicesVerifyThatItIsPosibleToAddPaymentFromDeviceForDraftInvoice()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";
		final String _po  = "12345";
		final String cashcheckamount = "100";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");	
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);			
		pricematrix.selectPriceMatrix("VP2 zayats");
		pricematrix.switchOffOption("PDR");
		pricematrix.selectDiscaunt("Dye");
		pricematrix.selectDiscaunt("Wheel");
		pricematrix.selectDiscaunt("Test service zayats");
		pricematrix.clickSaveButton();
		
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword("Zayats", "1111");		
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickInvoicePayButton();
		//invoiceinfoscreen.changePaynentMethodToCashNormal();
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
	

	@Test(testName="Test Case 27739:Invoices: HD - Verify that payment is send to BO when PO# is changed under My invoice", 
			description = "Invoices: HD - Verify that payment is send to BO when PO# is changed under My invoice")
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderMyInvoice()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";
		final String _po  = "12345";
		final String newpo  = "New test PO";
		final String cashcheckamount = "100";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");	
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);			
		pricematrix.selectPriceMatrix("VP2 zayats");
		pricematrix.switchOffOption("PDR");
		pricematrix.selectDiscaunt("Dye");
		pricematrix.selectDiscaunt("Wheel");
		pricematrix.selectDiscaunt("Test service zayats");
		pricematrix.clickSaveButton();
		
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword("Zayats", "1111");
		
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickInvoicePayButton();
		//invoiceinfoscreen.changePaynentMethodToCashNormal();
		invoiceinfoscreen.setCashCheckAmountValue(cashcheckamount);
		invoiceinfoscreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickSaveAsDraft();
		
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
	
	@Test(testName="Test Case 27741:Invoices: HD - Verify that payment is send to BO when PO# is changed under Team invoice", 
			description = "Invoices: HD - Verify that payment is send to BO when PO# is changed under Team invoice")
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderTeamInvoice()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";
		final String _po  = "12345";
		final String newpo  = "New test PO from Team";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		


		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");	
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);			
		pricematrix.selectPriceMatrix("VP2 zayats");
		pricematrix.switchOffOption("PDR");
		pricematrix.selectDiscaunt("Dye");
		pricematrix.selectDiscaunt("Wheel");
		pricematrix.selectDiscaunt("Test service zayats");
		pricematrix.clickSaveButton();
		
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword("Zayats", "1111");		
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(iOSInternalProjectConstants.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		TeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		teaminvoicesscreen.selectInvoice(invoicenumber);
		
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(newpo);
		teaminvoicesscreen.clickHomeButton();
		
		Helpers.waitABit(10000);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
	
	@Test(testName="Test Case 40033:WO Monitor: Verify filter for Team WO that returns only work assigned to tech who is logged in,"
			+ "Test Case 40034:WO Monitor: Verify that employee with Manager role may see and change all services of repair order", 
			description = "WO: HD - Verify filter for Team WO that returns only work assigned to tech who is logged in,"
					+ "WO Monitor: Verify that employee with Manager role may see and change all services of repair order")
	public void testInvoicesVerifyFilterForTeamWOThatReturnsOnlyWorkAssignedToTechWhoIsLoggedIn()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.unselecTechnician("Employee Simple 20%");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.clickSaveButton();
		homescreen = myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);
		
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		homescreen = ordermonitorscreen.clickHomeButton();
		homescreen = teamworkordersscreen.clickHomeButton();		
		MainScreen mainscreen = homescreen.clickLogoutButton();
		
		homescreen = mainscreen.userLogin("Zayats", "1111");
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);		
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		homescreen = ordermonitorscreen.clickHomeButton();
		homescreen = teamworkordersscreen.clickHomeButton();		
		mainscreen = homescreen.clickLogoutButton();
		
		homescreen = mainscreen.userLogin("Inspector", "12345");
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);		
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		//Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		homescreen = ordermonitorscreen.clickHomeButton();
		homescreen = teamworkordersscreen.clickHomeButton();		
		mainscreen = homescreen.clickLogoutButton();
		
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.selectSearchLocation("Default Location");
		repairorderspage.setSearchWoNumber(wonum);
		repairorderspage.clickFindButton();
		
		VendorOrderServicesWebPage vendororderservicespage = repairorderspage.clickOnWorkOrderLinkInTable(wonum);
		vendororderservicespage.changeRepairOrderServiceVendor(iOSInternalProjectConstants.DYE_SERVICE, "Device Team");
		vendororderservicespage.waitABit(1000);
		Assert.assertEquals(vendororderservicespage.getRepairOrderServiceTechnician(iOSInternalProjectConstants.DYE_SERVICE), "Oksi User");
		DriverBuilder.getInstance().getDriver().quit();
			
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);		
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();

		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		homescreen = ordermonitorscreen.clickHomeButton();
		homescreen = teamworkordersscreen.clickHomeButton();
		mainscreen = homescreen.clickLogoutButton();
		
		homescreen = mainscreen.userLogin("Zayats", "1111");
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);		
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();

		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		homescreen = ordermonitorscreen.clickHomeButton();
		homescreen = teamworkordersscreen.clickHomeButton();		
		mainscreen = homescreen.clickLogoutButton();
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
	
	@Test(testName="Test Case 45251:SR: HD - Verify multiple inspections and multiple work orders to be tied to a Service Request", 
			description = "SR: HD - Verify multiple inspections and multiple work orders to be tied to a Service Request")
	public void testSRVerifyMultipleInspectionsAndMultipleWorkOrdersToBeTiedToAServiceRequest()
			throws Exception {
		
		final String VIN  = "WDZPE7CD9E5889222";
		final String insptype1 = iOSInternalProjectConstants.INS_LINE_APPROVE_OFF;
		final String insptype2 = iOSInternalProjectConstants.INSP_FOR_CALC;
		List<String> inspnumbers = new ArrayList<String>();
		List<String> wonumbers = new ArrayList<String>();
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
				
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.SR_ALL_PHASES);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
				
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		vehiclescreeen.clickSaveButton();
		Helpers.getAlertTextAndCancel();
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(insptype1);
		inspnumbers.add(vehiclescreeen.getInspectionNumber());
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.clickSaveAsDraft();
		
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(insptype2);
		inspnumbers.add(vehiclescreeen.getInspectionNumber());	
		vehiclescreeen.selectNextScreen("Zayats Section1");
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.clickSaveButton();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();

		MyInspectionsScreen myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		for (String inspectnumber : inspnumbers)
			myinspectionsscreen.assertInspectionExists(inspectnumber);
		
		myinspectionsscreen.clickBackServiceRequest();
		servicerequestsscreen.clickHomeButton();

		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		servicerequestsscreen.selectWOType(iOSInternalProjectConstants.WO_DELAY_START);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		wonumbers.add(vehiclescreeen.getInspectionNumber());
		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.clickSaveButton();
		
		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		servicerequestsscreen.selectWOType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		wonumbers.add(vehiclescreeen.getInspectionNumber());
		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.clickSaveButton();

		servicerequestsscreen = new ServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryOrdersButton();
		MyWorkOrdersScreen myworkordersscreen = new MyWorkOrdersScreen(appiumdriver);
		for (String wonumber : wonumbers)
			Assert.assertTrue(myworkordersscreen.woExists(wonumber));
		
		myworkordersscreen.clickServiceRequestButton();
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 50249:Inspections: HD - Verify that Single-page inspection is saved without crush", 
			description = "Verify that Single-page inspection is saved without crush")
	public void testInspectionsVerifyThatSinglePageInspectionIsSavedWithoutCrush() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String _price  = "100";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_DRAFT_SINGLE_PAGE);
		SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen(appiumdriver);
		String inspnumber = singlepageinspectionscreen.getInspectionNumber();
		
		singlepageinspectionscreen.expandToFullScreeenSevicesSection();
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("PM_New");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("123");
		pricematrix.switchOffOption("PDR");	
		pricematrix.setPrice(_price);
		pricematrix.clickSaveButton();
		servicesscreen.clickSaveAsFinal();
		
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);	
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.clickNavigationBarSaveButton();
		
		servicesscreen.clickSaveAsFinal();
		
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Question 'Is all good?' in section 'Required trafficlight' should be answered."));		
		singlepageinspectionscreen.expandToFullScreeenQuestionsSection();
		
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.answerAllIsGoodQuestion();
		servicesscreen.clickSaveAsFinal();
		
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Question 'Question 2' in section 'Zayats Section1' should be answered."));
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		servicesscreen.clickSaveAsFinal();

		
		myinspectionsscreen.selectInspectionInTable(inspnumber);
		myinspectionsscreen.isApproveInspectionMenuActionExists();
		myinspectionsscreen.clickHomeButton();
		myinspectionsscreen.clickHomeButton();
		settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 45128:Inspections: HD - Verify that service level notes are copied from Inspection to WO when it is auto created after approval", 
			description = "Verify that service level notes are copied from Inspection to WO when it is auto created after approval")
	public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String _price  = "100";
		final int timetowaitwo = 4;
		final String _pricematrix1 = "Left Front Door";
		final String _pricematrix2 = "Grill";
		final String inspectionnotes = "Inspection notes";
		final String servicenotes = "Service Notes";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServicePriceValue("10");
		servicedetailsscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SALES_TAX);
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		myinspectionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		NotesScreen notesscreen = vehiclescreeen.clickNotesButton();
		notesscreen.setNotes(inspectionnotes);
		notesscreen.clickSaveButton();
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		
		servicedetailsscreen = servicesscreen.openServiceDetails("3/4\" - Penny Size");		
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService("3/4\" - Penny Size"));
		
		servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SALES_TAX);		
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();		
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService(iOSInternalProjectConstants.SALES_TAX));
		
		servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);		
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		
		servicesscreen.selectNextScreen("Default");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.setSizeAndSeverity("DIME", "VERY LIGHT");
		pricematrix.setPrice(_price);
		
		servicesscreen.selectNextScreen("Matrix Labor");
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(_pricematrix2);
		pricematrix.switchOffOption("PDR");
		pricematrix.setTime("12");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);		
		pricematrix.clickSaveButton();
		
		Assert.assertTrue(myinspectionsscreen.isNotesIconPresentForInspection(inspnumber));
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspectionForApprove(inspnumber);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		
		homescreen = myinspectionsscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamwoscreen = homescreen.clickTeamWorkordersButton();
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
		notesscreen = vehiclescreeen.clickNotesButton();
		Assert.assertEquals(notesscreen.getNotesValue(), inspectionnotes);
		notesscreen.clickSaveButton();
		Assert.assertEquals(vehiclescreeen.getEst(), inspnumber);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicedetailsscreen = servicesscreen.openServiceDetails("3/4\" - Penny Size");		
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService("3/4\" - Penny Size"));
		
		servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SALES_TAX);		
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();		
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService(iOSInternalProjectConstants.SALES_TAX));
		
		servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);		
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		servicesscreen.cancelOrder();
		teamwoscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32989:Inspections: HD - Verify that question section is shown per service with must panels when questions are required", 
			description = "Verify that question section is shown per service with must panels when questions are required")
	public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);		
		vehiclescreeen.setVIN(VIN);
		
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.selectVehiclePart("Deck Lid");
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.selectVehiclePart("Hood");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE, 0);
		Assert.assertTrue(servicedetailsscreen.isQuestionFormCellExists());
		Assert.assertEquals(servicedetailsscreen.getQuestion2Value(), "A3");
		servicedetailsscreen.saveSelectedServiceDetails();
		
		for (int i = 1; i < 4; i++) {
			servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE, i);
			Assert.assertFalse(servicedetailsscreen.isQuestionFormCellExists());
			servicedetailsscreen.saveSelectedServiceDetails();
		}
		servicesscreen.cancelOrder();
		myinspectionsscreen = new MyInspectionsScreen(appiumdriver);
		myinspectionsscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 26265:Invoices HD: Create Invoice with two WOs and copy vehicle for Retail customer", 
			description = "Create Invoice with two WOs and copy vehicle for Retail customer")
	public void testInvoicesCreateInvoiceWithTwoWOsAndCopyVehicleForRetailCustomer() throws Exception {
		
		final String VIN = "QWERTYUI123";
		final String retailcustomer  = "19319";
		final String _make = "Buick";
		final String _model = "Electra";
		final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		final String[] vehicleparts = { "Cowl, Other", "Hood" };
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		customersscreen = new CustomersScreen(appiumdriver);
		customersscreen.selectCustomerWithoutEditing(retailcustomer);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVINFieldValue(VIN);
		IOSElement alert = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("The VIN# is incorrect.")).isDisplayed());
		alert.findElementByAccessibilityId("Close").click();	
		List<MobileElement> closebtns = appiumdriver.findElementsByAccessibilityId("Close");
		for (MobileElement closebtn : closebtns)
			if (closebtn.isDisplayed())
				closebtn.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Close"))
				.click();
	
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setYear(_year);
		vehiclescreeen.setMileage(mileage);
		vehiclescreeen.setFuelTankLevel(fueltanklevel);
		vehiclescreeen.setType(_type);
		vehiclescreeen.setStock(stock);
		vehiclescreeen.setRO(_ro);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getVehiclePartValue(), "Cowl, Other Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.DYE_SERVICE), 2);
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber1), "$19.00");
		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomerWithoutEditing(retailcustomer);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getMake(), _make);
		Assert.assertEquals(vehiclescreeen.getModel(), _model);		
		//Assert.assertEquals(vehiclescreeen.getYear(), _year);

		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
				MobileBy.name("Yes"))
				.click();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
		invoiceinfoscreen.setPO("23");
		invoiceinfoscreen.addWorkOrder(wonumber1);
		final String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
		Helpers.waitABit(10*1000);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
		invoiceswebpage.setSearchInvoiceNumber(invoicenum);
		invoiceswebpage.clickFindButton();
		Assert.assertTrue(invoiceswebpage.isInvoiceNumberExists(invoicenum));
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	
	@Test(testName = "Test Case 30509:Invoices: HD - Verify that message 'Invoice PO# shouldn't be empty' is shown for Team Invoices", 
			description = "Verify that message 'Invoice PO# shouldn't be empty' is shown for Team Invoices")
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForTeamInvoices() throws Exception {
		
		final String emptypo = "";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		TeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
		teaminvoicesscreen.selectInvoice(invoicenum);
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(emptypo);
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Invoice PO# shouldn't be empty"));
		teaminvoicesscreen.clickCancelButton();
		teaminvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 30510:Invoices: HD - Verify that message 'Invoice PO# shouldn't be empty' is shown for MY Invoices", 
			description = "Verify that message 'Invoice PO# shouldn't be empty' is shown for MY Invoices")
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForMyInvoices() throws Exception {
		
		final String emptypo = "";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String invoicenum = myinvoicesscreen.getFirstInvoiceValue();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO(emptypo);
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Invoice PO# shouldn't be empty"));
		myinvoicesscreen.clickCancelButton();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40023:Invoices: Verify that 'Create invoice' check mark is not shown for WO that is selected for billing", 
			description = "Verify that 'Create invoice' check mark is not shown for WO that is selected for billing")
	public void testInvoicesVerifyThatCreateInvoiceCheckMarkIsNotShownForWOThatIsSelectedForBilling() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FORR_MONITOR_WOTYPE);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup(appiumdriver);
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
		invoiceinfoscreen.clickFirstWO();
		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		Assert.assertFalse(ordersummaryscreen.isApproveAndCreateInvoiceExists());
		ordersummaryscreen.cancelOrder();
		invoiceinfoscreen.cancelInvoice();
		myworkordersscreen.clickHomeButton();		
	}
	
	@Test(testName = "Test Case 33115:WO: HD - Verify that Tech splits is saved in price matrices", 
			description = "Verify that Tech splits is saved in price matrices")
	public void testWOVerifyThatTechSplitsIsSavedInPriceMatrices() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String pricevalue  = "100";
		final String totalsale = "5";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("VP2 zayats");				
		pricematrix.setSizeAndSeverity("CENT", "MEDIUM");
		Assert.assertEquals(pricematrix.getTechniciansValue(), defaulttech);
		pricematrix.setPrice(pricevalue);
		pricematrix.clickOnTechnicians();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.selecTechnician(techname);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(pricematrix.getTechniciansValue(), defaulttech + ", " + techname);
		pricematrix.clickSaveButton();		
		
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.clickSaveButton();
		
		TechRevenueScreen techrevenuescreen = myworkordersscreen.selectWorkOrderTechRevenueMenuItem(wonumber);
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(techname));
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(defaulttech));

		techrevenuescreen.clickHomeButton();
		myworkordersscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 34551:WO: HD - Verify that it is not possible to change default tech via service type split", 
			description = "Verify that it is not possible to change default tech via service type split")
	public void testWOVerifyThatItIsNotPossibleToChangeDefaultTechViaServiceTypeSplit() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";
		final String totalsale = "5";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
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
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.clickSaveButton();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));
		
		myworkordersscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 45097:WO: HD - Verify that when use Copy Services action for WO all service instances should be copied", 
			description = "Verify that when use Copy Services action for WO all service instances should be copied")
	public void testWOVerifyThatWhenUseCopyServicesActionForWOAllServiceInstancesShouldBeCopied() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "5";
		final String[] servicestoadd = { iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL, iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE};
		final String[] vehicleparts = { "Dashboard", "Deck Lid"};
		
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		for (String serviceadd : servicestoadd) {
			servicesscreen.searchAvailableService(serviceadd);
			SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(serviceadd);
			selectedservicescreen.clickVehiclePartsCell();
			for (String vehiclepart : vehicleparts) {
				selectedservicescreen.selectVehiclePart(vehiclepart);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.cancelSearchAvailableService();
		}

		for (String serviceadd : servicestoadd) {
			servicesscreen.assertServiceIsSelected(serviceadd);
		}
		
		servicesscreen.assertSubTotalAmauntIsCorrect("$44.00");
		servicesscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.selectWorkOrderForCopyServices(wonumber);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		for (String serviceadd : servicestoadd) {
			Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(serviceadd), servicestoadd.length);
		}
		servicesscreen.assertSubTotalAmauntIsCorrect("$44.00");
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 50250:WO: HD - Verify that WO number is not duplicated", 
			description = "WO: - Verify that WO number is not duplicated")
	public void testWOVerifyThatWONumberIsNotDuplicated()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _po  = "12345";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		final String wonumber1 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();

		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();
		
		invoiceswebpage.archiveInvoiceByNumber(invoicenumber);
		Assert.assertFalse(invoiceswebpage.isInvoiceNumberExists(invoicenumber));
		webdriver.quit();
		
		//Create second WO
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		final String wonumber2 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);	
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber2);
		myworkordersscreen.clickInvoiceIcon();
		invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(_po);
		invoiceinfoscreen.selectNextScreen("Zayats Section1");
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		invoiceinfoscreen.clickSaveAsFinal();
		homescreen = myworkordersscreen.clickHomeButton();
		
		homescreen.clickStatusButton();
		homescreen.updateDatabase();
		MainScreen mainscreen = new MainScreen(appiumdriver);
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		
		//Create third WO
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		final String wonumber3 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.clickSaveButton();
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
		operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();

		workorderspage.makeSearchPanelVisible();
		workorderspage.setSearchOrderNumber(wonumber3);
		workorderspage.clickFindButton();

		Assert.assertEquals(workorderspage.getWorkOrdersTableRowCount(), 1);
		webdriver.quit();
	}
	
	@Test(testName="Test Case 39573:WO: HD - Verify that in case valid VIN is decoded, replace existing make and model with new one", 
			description = "WO: - Verify that in case valid VIN is decoded, replace existing make and model with new one")
	public void testWOVerifyThatInCaseValidVINIsDecodedReplaceExistingMakeAndModelWithNewOne()
			throws Exception {
		
		final String[] VINs  = { "2A8GP54L87R279721", "1FMDU32X0PUB50142", "GFFGG"} ;
		final String makes[]  = { "Chrysler", "Ford", null } ;
		final String models[]  = { "Town and Country", "Explorer",  null };
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_INVOICE_PRINT);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		for (int i = 0; i < VINs.length; i++) {
			vehiclescreeen.setVIN(VINs[i]);
			Assert.assertEquals(vehiclescreeen.getMake(), makes[i]);
			Assert.assertEquals(vehiclescreeen.getModel(), models[i]);			
			vehiclescreeen.clearVINCode();
		}
		vehiclescreeen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 34626:WO: HD - Verify that when service do not have questions and select several panels do not underline anyone", 
			description = "WO: - Verify that when service do not have questions and select several panels do not underline anyone")
	public void testWOVerifyThatWhenServiceDoNotHaveQuestionsAndSelectSeveralPanelsDoNotUnderlineAnyone()
			throws Exception {
		
		final String VIN  = "2A8GP54L87R279721";
		final String[] vehicleparts  = { "Center Rear Passenger Seat", "Dashboard" };
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_WITHOUT_QUESTIONS_PP_PANEL);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
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
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 31964:WO: HD - Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen", 
			description = "Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen")
	public void testWOVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen() throws Exception {
		
		final String VIN = "2A8GP54L87R279721";

		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_VIN_ONLY);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		Assert.assertTrue(vehiclescreeen.getVINField().isDisplayed());
		vehiclescreeen.clickVINField();
		Assert.assertTrue(vehiclescreeen.getVINField().isDisplayed());
		vehiclescreeen.hideKeyboard();
		vehiclescreeen.cancelOrder();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 53824:WO: HD - Verify that message is shown for Money and Labor service when price is changed to 0$ under WO", 
			description = "Verify that message is shown for Money and Labor service when price is changed to 0$ under WO")
	public void testWOVerifyThatMessageIsShownForMoneyAndLaborServiceWhenPriceIsChangedTo0UnderWO() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "5";
		final String servicezeroprice = "0";
		final String servicecalclaborprice = "12";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_SMOKE_MONITOR);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);
		servicesscreen.selectService(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);
		
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);
		selectedservicescreen.setServicePriceValue(servicezeroprice);
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Order's technician split will be assigned to this order service if you set zero amount."));
		
		selectedservicescreen.clickTechniciansIcon();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Set non-zero amount for service to assign multiple technicians."));
		selectedservicescreen.selecTechnician("Manager 1");
		selectedservicescreen.selecTechnician("Oksana Zayats");
		Assert.assertFalse(selectedservicescreen.isTechnicianIsSelected("Manager 1"));
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected("Oksana Zayats"));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.CALC_LABOR);
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
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.cancelOrder();
		homescreen =  myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 45252:WO: HD - Verify that validation is present for vehicle trim field", 
			description = "Verify that validation is present for vehicle trim field")
	public void testWOVerifyThatValidationIsPresentForVehicleTrimField() throws Exception {
		
		final String VIN  = "TESTVINN";
		final String _make = "Acura";
		final String _model = "CL";
		final String trimvalue = "2.2 Premium";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_VEHICLE_TRIM_VALIDATION);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Trim is required"));
		vehiclescreeen.setTrim(trimvalue);
		Assert.assertEquals(vehiclescreeen.getTrim(), trimvalue);
		
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.clickSaveButton();
		homescreen =  myworkordersscreen.clickHomeButton();
	}
	
	
	@Test(testName = "Test Case 35375:WO: HD - Verify that Total sale is not shown when checkmark 'Total sale required' is not set to OFF", 
			description = "Verify that Total sale is not shown when checkmark 'Total sale required' is not set to OFF")
	public void testWOVerifyThatTotalSaleIsNotShownWhenCheckmarkTotalSaleRequiredIsNotSetToOFF() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TOTAL_SALE_NOT_REQUIRED);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(iOSInternalProjectConstants.TAX_DISCOUNT);
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		Assert.assertFalse(ordersummaryscreen.isTotalSaleFieldPresent());
		ordersummaryscreen.clickSave();
		homescreen = myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		Assert.assertFalse(ordersummaryscreen.isTotalSaleFieldPresent());
		ordersummaryscreen.clickSave();
		
		homescreen = teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40821:WO: HD - Verify that it is possible to assign tech to order by action Technicians", 
			description = "Verify that it is possible to assign tech to order by action Technicians")
	public void testWOVerifyThatItIsPossibleToAssignTechToOrderByActionTechnicians() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String pricevalue  = "21";
		final String totalsale = "5";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen(appiumdriver);
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServiceQuantityValue("2.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
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
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");	
		servicesscreen.selectPriceMatrices("VP2 zayats");	
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
		Assert.assertEquals(pricematrix.getTechniciansValue(), defaulttech);
		pricematrix.setPrice(pricevalue);
		pricematrix.selectDiscaunt("Test service zayats");
		pricematrix.clickSaveButton();		
		servicesscreen.selectService(iOSInternalProjectConstants.TAX_DISCOUNT);
		servicesscreen.selectService(iOSInternalProjectConstants.SALES_TAX);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.clickSaveButton();
		selectedservicescreen = myworkordersscreen.selectWorkOrderTechniciansMenuItem(wonumber);
		//selectedservicescreen.selecTechnician(defaulttech);
		selectedservicescreen.selecTechnician(techname);
		selectedservicescreen.saveSelectedServiceDetails();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Changing default employees for a work order will change split data for all services."));
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getTechnician(), techname + ", " + defaulttech);
		vehiclescreeen.cancelOrder();
		myworkordersscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 58663:Inspections: HD - Verify that when Panel grouping is used for package for selected Panel only linked services are shown", 
			description = "Verify that when Panel grouping is used for package for selected Panel only linked services are shown")
	public void testInspectionsVerifyThatWhenPanelGroupingIsUsedForPackageForSelectedPanelOnlyLinkedServicesAreShown() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_SERVICE_TYPE_WITH_OUT_REQUIRED);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);		
		vehiclescreeen.setVIN(VIN);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.BUFF_SERVICE);
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE));
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_VEHICLE));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_PANEL));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_SERVICE));
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		Assert.assertTrue(servicesscreen.isServiceTypeExists("3/4\" - Penny Size"));

		servicesscreen.cancelOrder();
		myinspectionsscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 57101:WO: HD - Verify that WO is saved correct with selected sub service (no message with incorrect tech split)", 
			description = "Verify that WO is saved correct with selected sub service (no message with incorrect tech split)")
	public void testWOVerifyThatWOIsSavedCorrectWithSelectedSubService_NoMessageWithIncorrectTechSplit() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "10";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		
		servicesscreen.searchServiceToSelect(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.selectServiceSubSrvice(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash partly");
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.selectServiceSubSrvice(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash whole");
		Assert.assertTrue(servicesscreen.isServiceWithSubSrviceSelected(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash partly"));
		Assert.assertTrue(servicesscreen.isServiceWithSubSrviceSelected(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash whole"));
		servicesscreen.saveWorkOrder();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 54542:WO: Regular - Verify that answer services are correctly added for WO when Panel group is set", 
			description = "Verify that answer services are correctly added for WO when Panel group is set")
	public void testWOVerifyThatAnswerServicesAreCorrectlyAddedForWOWhenPanelGroupIsSet() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_PANEL_GROUP);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		questionsscreen.selectNextScreen("Zayats Section2");
		questionsscreen.selectAnswerForQuestion("Q1", "No - rate 0");
		
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		
		servicesscreen.cancelOrder();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "est Case 43408:WO: HD - Verify that search bar is present for service pack screen", 
			description = "Verify that search bar is present for service pack screen")
	public void testWOVerifyThatSearchBarIsPresentForServicePackScreen() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.searchAvailableService("test");
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Dashboard");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.searchAvailableService("Tax");
		servicesscreen.selectService(iOSInternalProjectConstants.SALES_TAX);
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.searchSelectedService(iOSInternalProjectConstants.SALES_TAX);
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.SALES_TAX);
		servicesscreen.cancelSearchSelectedService();
		
		servicesscreen.searchSelectedService("test");
		servicesscreen.assertServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE);
		
		servicesscreen.cancelOrder();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42178:WO: HD - Verify that Cancel message is shown for New/Existing WO", 
			description = "Verify that Cancel message is shown for New/Existing WO")
	public void testWOVerifyThatCancelMessageIsShownForNewOrExistingWO() throws Exception {
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_SMOKE_TEST);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.clickCancelButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, "Stop Work Order Creation\nAny unsaved changes will be lost. Are you sure you want to stop creating this Work Order?");
		vehiclescreeen.clickCancelButton();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Stop Work Order Creation\nAny unsaved changes will be lost. Are you sure you want to stop creating this Work Order?");
		
		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.clickCancelButton();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, "Stop Work Order Edit\nAny unsaved changes will be lost. Are you sure you want to stop editing this Work Order?");
		vehiclescreeen.clickCancelButton();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Stop Work Order Edit\nAny unsaved changes will be lost. Are you sure you want to stop editing this Work Order?");
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.clickCancelButton();
		
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 35951:SR: HD - Verify that Accept/Decline actions are present for tech when 'Technician Acceptance Required' option is ON and status is Proposed", 
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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
		
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectAcceptAction();
		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Would you like to accept  selected service request?");
		Assert.assertTrue(servicerequestsscreen.isServiceRequestOnHold(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isAcceptActionExists());
		Assert.assertFalse(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.clickHomeButton();
	
	}
	
	@Test(testName="Test Case 35953:SR: HD - Verify that when SR is declined status reason should be selected", 
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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
		
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectDeclineAction();
		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Would you like to decline  selected service request?");
		servicerequestsscreen.clickDoneCloseReasonDialog();
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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
		
		
		homescreen = new HomeScreen(appiumdriver);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectEditServiceRequestAction();
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setTech("Simple 20%");
		vehiclescreeen.selectNextScreen("Zayats Section1");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");		
		questionsscreen.clickSaveButton();
				
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);
		
		servicerequestsscreen.clickHomeButton();
	
	}
	
	@Test(testName="Test Case 36004:SR: HD - Verify that it is possible to accept/decline Appointment when option 'Appointment Acceptance Required' = ON", 
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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

		homescreen = new HomeScreen(appiumdriver);
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
				
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectAppointmentRequestAction();
		
		Assert.assertTrue(servicerequestsscreen.isAcceptAppointmentRequestActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineAppointmentRequestActionExists());
		servicerequestsscreen.clickCloseButton();
		
		servicerequestsscreen.clickHomeButton();
	
	}
}
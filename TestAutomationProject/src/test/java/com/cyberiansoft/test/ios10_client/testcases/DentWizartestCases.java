package com.cyberiansoft.test.ios10_client.testcases;

import java.net.MalformedURLException;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.CarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ClaimScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.EnterpriseBeforeDamageScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.InspectionScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MainScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.NotesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.OrderMonitorScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.TeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.VehicleScreen;
import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.core.IOSHDDeviceInfo;
import com.cyberiansoft.test.ios_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios_client.utils.ExcelUtils;
import com.cyberiansoft.test.ios_client.utils.iOSInternalProjectConstants;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.PricesCalculations;
import com.cyberiansoft.test.ios_client.utils.UtilConstants;
import com.relevantcodes.extentreports.LogStatus;

public class DentWizartestCases extends BaseTestCase {

	private String regCode;
	private final String customer = "Abc Rental Center";
	public HomeScreen homescreen;
	private final String buildtype = "hd";
	
	@BeforeClass
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void setUpSuite(String backofficeurl, String userName, String userPassword) throws Exception {
		initTestUser(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
		testGetDeviceRegistrationCode(backofficeurl, userName, userPassword);
		testRegisterationiOSDdevice();
		ExcelUtils.setDentWizardExcelFile();
	}

	//@Test(description = "Get registration code on back-office for iOS device")
	public void testGetDeviceRegistrationCode(String backofficeurl,
			String userName, String userPassword) throws Exception {

		final String searchlicensecriteria = "Mac mini_olkr";
		
		//webdriverInicialize();
		webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);

		ActiveDevicesWebPage devicespage = PageFactory.initElements(webdriver,
				ActiveDevicesWebPage.class);

		devicespage.setSearchCriteriaByName(searchlicensecriteria);
		regCode = devicespage.getFirstRegCodeInTable();
		getWebDriver().quit();
		Thread.sleep(2000);
	}

	//@Test(description = "Register iOS Ddevice")
	public void testRegisterationiOSDdevice() throws Exception {		
		appiumdriverInicialize(buildtype);	
		//appiumdriver.removeApp(bundleid);
		//System.out.println("+++" + appiumdriver.getCapabilities().getCapability("MobileCapabilityType.APP").toString());
		//appiumdriver.installApp(appiumdriver.getCapabilities().getCapability("MobileCapabilityType.APP").toString());
		//appiumdriver.launchApp();
		appiumdriver.removeApp(IOSHDDeviceInfo.getInstance().getDeviceBundleId());
		appiumdriver.quit();
		appiumdriverInicialize(buildtype);
		//appiumdriver.installApp(appiumdriver.getCapabilities().getCapability("MobileCapabilityType.APP").toString());
		//appiumdriver.launchApp();
		LoginScreen loginscreen = new LoginScreen(appiumdriver);
		loginscreen.assertRegisterButtonIsValidCaption();
		loginscreen.registeriOSDevice(regCode);
		MainScreen mainscr = new MainScreen(appiumdriver);
		homescreen = mainscr.userLogin(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
	}
	
	@BeforeMethod
	public void restartApps() throws MalformedURLException, InterruptedException {
		//resrtartApplication();	
		//MainScreen mainscr = new MainScreen(appiumdriver);
		//homescreen = mainscr.userLogin(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
		System.out.println("================================ NEW TESTACASE ====================================");
	}

	/*@Test(testName = "Test Case 10264:Test Valid VIN Check", description = "Test Valid VIN Check")
	public void testValidVINCheck() throws Exception {
		final String tcname = "testValidVINCheck";
		final int tcrow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(tcrow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(tcrow), ExcelUtils.getModel(tcrow), ExcelUtils.getYear(tcrow));
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 10451:Test Top Customers Setting", description = "Test Top Customers Setting")
	public void testTopCustomersSetting() throws Exception {
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setShowTopCustomersOn();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.assertTopCustomersExists();
		Assert.assertTrue(customersscreen.customerIsPresent(UtilConstants.TEST_CUSTOMER_FOR_TRAINING));
		customersscreen.clickHomeButton();
		
		settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setShowTopCustomersOff();
		settingsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10452:Test VIN Duplicate check", description = "Test VIN Duplicate check")
	public void testVINDuplicateCheck() throws Exception {
		
		final String tcname = "testVINDuplicateCheck";
		final int tcrow = ExcelUtils.getTestCaseRow(tcname);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOn();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(customer);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVINAndAndSearch(ExcelUtils.getVIN(tcrow).substring(
				0, 11));
		Thread.sleep(2000);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(tcrow).substring(11, 17));
		vehiclescreeen.verifyExistingWorkOrdersDialogAppears();		
		vehiclescreeen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
		settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10453:Test Vehicle Part requirement", description = "Test Vehicle Part requirement")
	public void testVehiclePartRequirement() throws Exception {
		final String tcname = "testVehiclePartRequirement";
		final int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOn();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(customer);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVINAndAndSearch(ExcelUtils.getVIN(testcaserow));
		Thread.sleep(2000);
		//Assert.assertEquals(searchresult, "Search Complete No vehicle invoice history found");
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		Assert.assertTrue(servicesscreen
				.isServiceTypeExists(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE));
		Assert.assertTrue(servicesscreen
				.isServiceTypeExists(UtilConstants.PDRPANEL_SUBSERVICE));
		Assert.assertTrue(servicesscreen
				.isServiceTypeExists(UtilConstants.PDRVEHICLE_SUBSERVICE));
		Assert.assertTrue(servicesscreen
				.isServiceTypeExists(UtilConstants.PDRPANEL_HAIL_SUBSERVICE));
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.cancelSelectedServiceDetails();
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();

		settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
		Thread.sleep(2000);

	}

	@Test(testName = "Test Case 10455:Test turning multiple Work Orders into a single Invoice", description = "Test turning multiple Work Orders into a single Invoice")
	public void testTurningMultipleWorkOrdersIntoASingleInvoice()
			throws Exception {
	    String tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice1";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Fender", "Left Front Door",
				"Roof" };
		final String[] vehiclepartswheels = { "Left Front Wheel",
				"Right Front Wheel" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(customer);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo1 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheels.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheels[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.clickSaveButton();

		// ==================Create second WO=============
		tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice2";
		testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts2 = { "Hood", "Roof", "Trunk Lid" };
		final String[] vehiclepartspaints = { "Front Bumper", "Rear Bumper" };

		myworkordersscreen.clickAddOrderButton();
		vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo2 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts2.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts2[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$41.66");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), "$41.67");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), "$41.67");	
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaints.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspaints[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen
				.setTechnicianCustomPriceValue(UtilConstants.technicianA, "165");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB,
				"50");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA),
				"$165.00");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB),
				"$50.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());

		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickCreateInvoiceIconForWO(wo1);
		myworkordersscreen.clickCreateInvoiceIconForWO(wo2);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
		invoiceinfoscreen.assertWOIsSelected(wo1);
		invoiceinfoscreen.assertWOIsSelected(wo2);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		Thread.sleep(1000);
		Assert.assertFalse(myworkordersscreen.woExists(wo1));
		Assert.assertFalse(myworkordersscreen.woExists(wo2));
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String wosubstring = wo1 + "," + wo2;
		Assert.assertEquals(myinvoicesscreen.getInvoiceInfoLabel(invoicenumber), wosubstring);
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10651:Test same Order Type required for turning multiple Work Orders into a single Invoice", description = "Test same Order Type required for turning multiple Work Orders into a single Invoice")
	public void testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice()
			throws Exception {
		String tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice1";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Roof" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routecanadaworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo1 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		for (int i = 0; i <= vehicleparts.length; i++) {
			selectedservicescreen.saveSelectedServiceDetails();
		}
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.clickSaveButton();

		// ==================Create second WO=============
		tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice2";
		testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts2 = { "Hood" };

		myworkordersscreen.clickAddOrderButton();
		vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo2 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts2.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts2[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());

		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickCreateInvoiceIconForWO(wo1);
		myworkordersscreen.clickCreateInvoiceIconForWO(wo2);
		myworkordersscreen.clickInvoiceIcon();
		String alerttext = myworkordersscreen
				.selectInvoiceTypeAndAcceptAlert(UtilConstants.NO_ORDER_TYPE);
		Assert.assertEquals(
				alerttext,
				"Invoice type " + UtilConstants.NO_ORDER_TYPE + " doesn't support multiple Work Order types.");

		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 10652:Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker", description = "Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker")
	public void testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker()
			throws Exception {
		String tcname = "testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Fender", "Left Front Door",
				"Left Quarter Panel" };
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.wizprotrackerrouteworkordertype);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.clickSaveButton();

		String insptoapprove = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.selectInspectionForApprove(insptoapprove);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		Helpers.waitABit(1000);
		approveinspscreen.clickApproveButton();
		//approveinspscreen.clickSignButton();
		approveinspscreen.drawSignature AfterSelection();
		approveinspscreen.clickDoneButton();
		// approveinspscreen.clickBackButton();
		myinspectionsscreen.assertInspectionIsApproved(insptoapprove);
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10653:Test Inspections convert to Work Order", description = "Test Inspections convert to Work Order")
	public void testInspectionsConvertToWorkOrder() throws Exception {
		String tcname = "testInspectionsConvertToWorkOrder";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Right Roof Rail", "Right Fender",
				"Right Rear Door" };

		final String[] vehicleparts2 = { "Left Mirror", "Right Mirror" };
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.wizprotrackerrouteworkordertype);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts2.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts2[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));

		servicesscreen.clickSaveButton();
		Thread.sleep(4000);
		String insptoapprove = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.selectInspectionForApprove(insptoapprove);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen(appiumdriver);
		Helpers.waitABit(1000);
		approveinspscreen.clickApproveButton();
		approveinspscreen.drawSignature AfterSelection();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.assertInspectionIsApproved(insptoapprove);
		myinspectionsscreen.selectInspectionInTable (insptoapprove);
		myinspectionsscreen.clickCreateWOButton();
		Helpers.waitABit(1000);
		servicesscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber.substring(0, 1), "O");
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		ordersummaryscreen.clickSaveButton();

		myinspectionsscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.woExists(wonumber);
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10654:Test 'Vehicle' Service does not multiply price entered when selecting multiple panels", description = "Test 'Vehicle' Service does not multiply price entered when selecting multiple panels")
	public void testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels()
			throws Exception {
		String tcname = "testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Hood", "Roof", "Trunk Lid" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(customer);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
		Thread.sleep(2000);
	}

	@Test(testName = "Test Case 10655:Test 'Panel' Service multiplies price entered when selecting multiple panels", description = "Test 'Panel' Service multiplies price entered when selecting multiple panels")
	public void testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels()
			throws Exception {
		String tcname = "testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Fender", "Left Front Door",
				"Left Rear Door" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(customer);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10656:Test Carmax vehicle information requirements", description = "Test Carmax vehicle information requirements")
	public void testCarmaxVehicleInformationRequirements() throws Exception {
		String tcname = "testCarmaxVehicleInformationRequirements";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		Assert.assertTrue(vehiclescreeen.clickSaveWithAlert().contains("Stock# is required"));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
		Assert.assertTrue(vehiclescreeen.clickSaveWithAlert().contains("RO# is required"));
		vehiclescreeen.setRO(ExcelUtils.getRO(testcaserow));
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10657:Test Service Drive requires Advisor", description = "Test Service Drive requires Advisor")
	public void testServiceDriveRequiresAdvisor() throws Exception {
		String tcname = "testServiceDriveRequiresAdvisor";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.servicedriveworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		Assert.assertTrue(vehiclescreeen.clickSaveWithAlert().contains("Advisor is required"));
		vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName= "Test Case 10658:Test Inspection requirments inforced", description = "Test Inspection requirements inforced")
	public void testInspectionRequirementsInforced() throws Exception {
		String tcname = "testInspectionRequirementsInforced";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
	
		CustomersScreen customersscreen = homescreen.clickCustomersButton();

		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.wizardprotrackerrouteinspectiondertype);
		InspectionScreen  inspectionscreen = new InspectionScreen(appiumdriver);
		String alerttext = inspectionscreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("VIN# is required"));
		inspectionscreen.setVIN(ExcelUtils.getVIN(testcaserow));
		Thread.sleep(1000);
		inspectionscreen.saveChanges();
		Thread.sleep(1000);
		alerttext = Helpers.getAlertTextAndAccept();
		//alerttext = inspectionscreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("Advisor is required"));
		inspectionscreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		inspectionscreen.selectNextScreen("Questions");
		inspectionscreen.clickSaveButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10663:Test Inspections can convert to multiple Work Orders", description = "Test Inspections can convert to multiple Work Orders")
	public void testInspectionsCanConvertToMultipleWorkOrders()
			throws Exception {
		String tcname = "testInspectionsCanConvertToMultipleWorkOrders";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();

		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.routecanadaworkordertype);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
		servicesscreen.selectService(UtilConstants.INTERIORBURNS_SUBSERVICE);
		servicesscreen.assertServiceIsSelected(UtilConstants.INTERIORBURNS_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(UtilConstants.INTERIORBURNS_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveButton();
		String inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.selectInspectionInTable (inspnum);
		myinspectionsscreen.clickCreateWOButton();
		servicesscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber.substring(0, 1), "O");
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		ordersummaryscreen.clickSaveButton();
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionNumberValue(), inspnum);
		vehiclescreeen = myinspectionsscreen.showWorkOrdersForInspection(inspnum);
		Assert.assertEquals(vehiclescreeen.getInspectionNumber(), wonumber);
		servicesscreen.clickCancelButton();
		
		myinspectionsscreen.selectInspectionInTable (myinspectionsscreen.getFirstInspectionNumberValue());
		myinspectionsscreen.clickCreateWOButton();
		servicesscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());

		String wonumber2 = ordersummaryscreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber2.substring(0, 1), "O");
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		ordersummaryscreen.clickSaveButton();
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionNumberValue(), inspnum);
		myinspectionsscreen.showWorkOrdersForInspection(inspnum);
		Thread.sleep(10000);
		Assert.assertEquals(myinspectionsscreen.getNumberOfWorkOrdersForIspection(), "2");
		Assert.assertTrue(myinspectionsscreen.isWorkOrderForInspectionExists(wonumber2));
		myinspectionsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.woExists(wonumber);
		myworkordersscreen.woExists(wonumber2);
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10665:Test Archive feature for Inspections", description = "Test Archive feature for Inspections")
	public void testArchiveFeatureForInspections() throws Exception {
		String tcname = "testArchiveFeatureForInspections";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();

		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.routeworkordertype);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.OTHER_SERVICE);
		servicesscreen.selectService(UtilConstants.WINDOWTINT_SUBSERVICE);

		servicesscreen.assertServiceIsSelected(UtilConstants.WINDOWTINT_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveButton();
		String insptoarchive = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.selectInspectionInTable (insptoarchive);

		myinspectionsscreen.clickArchive InspectionButton();
		myinspectionsscreen.clickFilterButton();
		myinspectionsscreen.clickStatusFilter();
		myinspectionsscreen.isFilterStatusSelected("New");
		myinspectionsscreen.isFilterStatusSelected("Approved");
		myinspectionsscreen.clickFilterStatus("New");
		myinspectionsscreen.clickFilterStatus("Approved");
		myinspectionsscreen.clickFilterStatus("Archived");
		myinspectionsscreen.isFilterStatusSelected("Archived");
		myinspectionsscreen.clickCloseFilterDialogButton();
		myinspectionsscreen.clickSaveFilterDialogButton();

		myinspectionsscreen.assertInspectionExists(insptoarchive);
		Assert.assertTrue(myinspectionsscreen.isFilterIsApplied());
		myinspectionsscreen.clearFilter();
		myinspectionsscreen.clickSaveFilterDialogButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 11731:Test even WO level tech split for Wholesale Hail", description = "Test even WO level tech split for Wholesale Hail")
	public void testEvenWOLevelTechSplitForWholesaleHail() throws Exception {
		String tcname = "testEvenWOLevelTechSplitForWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left A Pillar", "Left Fender",
				"Left Rear Door", "Roof" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreeen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.selectTechniciansEvenlyView();
		String alerttext = selectedservicescreen
				.saveSelectedServiceDetailsWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);
		vehiclescreeen.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);

		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelected(UtilConstants.FIXPRICE_SERVICE);

		servicesscreen.openServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
				vehicleparts[0]);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		for (int i = 1; i < vehicleparts.length; i++) {
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[i]
					, "$0.00 x 1.00");
		}
		
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[0]
				, "$105.00 x 1.00");

		servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
		Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
				vehicleparts[1]);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianB));
		selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsNotSelected(UtilConstants.technicianB));
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA),
				PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$70.00");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), "$70.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		for (int i = 2; i < vehicleparts.length; i++) {
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[i]
					, "$0.00 x 1.00");
		}
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[0]
				, "$105.00 x 1.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[1]
				, "$140.00 x 1.00");

		servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
		Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
				vehicleparts[2]);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianB));
		selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsNotSelected(UtilConstants.technicianB));
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.selecTechnician(UtilConstants.technicianD);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$30.00");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianD), "$30.00");
		selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsNotSelected(UtilConstants.technicianA));
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		for (int i = 3; i < vehicleparts.length; i++) {
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[i]
					, "$0.00 x 1.00");
		}
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[0]
				, "$105.00 x 1.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[1]
				, "$140.00 x 1.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[2]
				, "$60.00 x 1.00");

		servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
		Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
				vehicleparts[3]);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[3]
				, "$275.00 x 1.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[0]
				, "$105.00 x 1.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[1]
				, "$140.00 x 1.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE + ", " + vehicleparts[2]
				, "$60.00 x 1.00");

		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();

		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 11732:Test even service level tech split for Wholesale Hail", description = "Test even service level tech split for Wholesale Hail")
	public void testEvenServiceLevelTechSplitForWholesaleHail()
			throws Exception {
		String tcname = "testEvenServiceLevelTechSplitForWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Hood", "Right Quarter Panel",
				"Sunroof" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreeen.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$47.50");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), "$47.50");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_TOTAL_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA),
				"$175.00");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB),
				"$175.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));		
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();

		myworkordersscreen.clickHomeButton();
	} 
	
	@Test(testName = "Test Case 11733:Test Custom WO level tech split for wholesale Hail", description = "Test Custom WO level tech split for wholesale Hail")
	public void testCustomWOLevelTechSplitForWholesaleHail() throws Exception {
		String tcname = "testCustomWOLevelTechSplitForWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Cowl, Other", "Left Fender",
				"Trunk Lid" };
		 
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreeen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		Assert.assertTrueselectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA),
				"%100.00");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, ExcelUtils.getServicePrice(testcaserow));
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA),
				"%85.00");

		String alerttext = selectedservicescreen
				.saveSelectedServiceDetailsWithAlert();
		Assert.assertTrue(alerttext.contains("Total amount is not equal 100%"));
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "15");

		alerttext = selectedservicescreen.saveSelectedServiceDetailsWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

		selectedservicescreen.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		selectedservicescreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_ND_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$93.50");
		Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), "$16.50");
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "50");
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%45.46");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "60");		
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%54.54");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();

		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 11734:Test Custom service level tech split for wholesale Hail", description = "Test Custom service level tech split for wholesale Hail")
	public void testCustomServiceLevelTechSplitForWholesaleHail() throws Exception {
		String tcname = "testCustomServiceLevelTechSplitForWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Fender", "Left Quarter Panel", "Right Rear Door",
				"Trunk Lid" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));

		vehiclescreeen.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 0);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selectTechniciansCustomView();
		Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "45");	
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%36.00");
		String alerttext = selectedservicescreen
				.saveSelectedServiceDetailsWithAlert();
		Assert.assertTrue(alerttext.contains("Split amount should be equal to total amount."));
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "80");	
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%64.00");
		selectedservicescreen.saveSelectedServiceDetails();		
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selectTechniciansCustomView();
		Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "25");	
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%20.00");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "100");	
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%80.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selectTechniciansCustomView();
		Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "50");	
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%40.00");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "75");	
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%60.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selectTechniciansCustomView();
		Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "30");	
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%24.00");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "95");	
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%76.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 11735:Test Customer Discount on Wholesale Hail", description = "Test Customer Discount on Wholesale Hail")
	public void testCustomerDiscountOnWholesaleHail() throws Exception {
		String tcname = "testCustomerDiscountOnWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Hood", "Roof", "Trunk Lid" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreeen.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Customer Discount");
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10668:Test Quick Quote option for Retail Hail", description = "Test Quick Quote option for Retail Hail")
	public void testQuickQuoteOptionForRetailHail() throws Exception {
		String tcname = "testQuickQuoteOptionForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen = new QuestionsScreen(appiumdriver);
		String alerttext = questionsscreen.clickSaveWithAlert();
		Assert.assertTrue(
				alerttext.contains("Question 'Estimate Conditions' in section 'Hail Info' should be answered."));
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.selectOutsideQuestions();

		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
		
		questionsscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		claimscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Quick Quote");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
		pricematrix.assertNotesExists();
		pricematrix.assertTechniciansExists();
		pricematrix.setPrice(ExcelUtils.getServicePrice(testcaserow));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
		pricematrix.setPrice(ExcelUtils.getServicePrice3(testcaserow));
		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.clickSaveButton();
		pricematrix.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		pricematrix.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10733:Test Customer self-pay option for Retail Hail", description = "Test Customer self-pay option for Retail Hail")
	public void testCustomerSelfPayOptionForRetailHail() throws Exception {
		String tcname = "testCustomerSelfPayOptionForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
		
		questionsscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);		
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompany(ExcelUtils.getInsuranceCompany(retailhaildatarow));
		String alerttext = claimscreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("Claim# is required."));
		claimscreen.setClaim(ExcelUtils.getClaim(retailhaildatarow));
		claimscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.HAIL_PDR_NON_CUSTOMARY_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.answerQuestion("DEALER");
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10734:Test Even WO level tech split for Retail Hail", description = "Test Even WO level tech split for Retail Hail")
	public void testEvenWOLevelTechSplitForRetailHail() throws Exception {
		String tcname = "testEvenWOLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		selectedservicescreen.selectTechniciansEvenlyView();
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA),
				"%33.34");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianB),
				"%33.33");
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianC),
				"%33.33");
		String alerttext = selectedservicescreen
				.saveSelectedServiceDetailsWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

		selectedservicescreen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
		
		selectedservicescreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		claimscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.HEAVY_SEVERITY);
		pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianC));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
		selectedservicescreen.unselecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.SEVERE_SEVERITY);
		pricematrix.assertPriceCorrect("$0.00");
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianC));

		pricematrix.setPrice(ExcelUtils.getServicePrice2(testcaserow));
		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
		selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
		selectedservicescreen.unselecTechnician(UtilConstants.technicianC);
		selectedservicescreen.selecTechnician(UtilConstants.technicianD);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		pricematrix.clickSaveButton();

		pricematrix.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		pricematrix.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10735:Test even service level tech split for Retail Hail", description = "Test even service level tech split for Retail Hail")
	public void testEvenServiceLevelTechSplitForRetailHail() throws Exception {
		String tcname = "testEvenServiceLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));	
		
		questionsscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		claimscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
		pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		pricematrix.assertNotesExists();
		pricematrix.assertTechniciansExists();
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.unselecTechnician(UtilConstants.technicianA);

		selectedservicescreen.selecTechnician(UtilConstants.technicianB);

		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), "$60.00");

		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.HLF_SIZE, PriceMatrixScreen.LIGHT_SEVERITY);
		pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));

		pricematrix.clickOnTechnicians();
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.clickDiscaunt(ExcelUtils.getDiscount3(retailhaildatarow));
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.clickSaveButton();
		pricematrix.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		pricematrix.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 10736:Test deductible feature for Retail Hail", description = "Test deductible feature for Retail Hail")
	public void testDeductibleFeatureForRetailHail() throws Exception {
		String tcname = "testDeductibleFeatureForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
		
		questionsscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));
		claimscreen.setDeductible("50");
		Assert.assertEquals(
				claimscreen
						.getDeductibleValue(), "50.00");

		claimscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		pricematrix.assertNotesExists();
		pricematrix.assertTechniciansExists();
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));

		pricematrix.clickSaveButton();
		pricematrix.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		pricematrix.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		ordersummaryscreen.selectWorkOrderDetails("Hail");
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10737:Test Zip Code validator for Retail Hail", description = "Test Zip Code validator for Retail Hail")
	public void testZipCodeValidatorForRetailHail() throws Exception {
		String tcname = "testZipCodeValidatorForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		final String validzip = "83707";

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext
				.contains("Your answer doesn't match the validator 'US Zip Codes'."));
		questionsscreen.clearZip();
		questionsscreen.setOwnerZip(validzip);
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 11727:Test Custom WO level tech split for Retail Hail", description = "Test Custom WO level tech split for Retail Hail")
	public void testCustomWOLevelTechSplitForRetailHail() throws Exception {
		String tcname = "testCustomWOLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selectTechniciansCustomView();
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA),
				"%100.00");

		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA,
				"70");
		String alerttext = selectedservicescreen
				.saveSelectedServiceDetailsWithAlert();
		Assert.assertTrue(alerttext.contains("Total amount is not equal 100%"));

		selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB,
				"30");
		alerttext = selectedservicescreen.saveSelectedServiceDetailsWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

		selectedservicescreen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.selectProperQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));		
		
		questionsscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		claimscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.VERYLIGHT_SEVERITY);
		pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		pricematrix.assertNotesExists();
		pricematrix.assertTechniciansExists();
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.assertServicePriceValue("%25.000");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		pricematrix.assertNotesExists();
		pricematrix.assertTechniciansExists();
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selectTechniciansEvenlyView();
		selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.clickSaveButton();
		pricematrix.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		pricematrix.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 11728:Test Custom service level tech split for Retail Hail", description = "Test Custom service level tech split for Retail Hail")
	public void testCustomServiceLevelTechSplitForRetailHail() throws Exception {
		String tcname = "testCustomServiceLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));	
		
		questionsscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		claimscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		pricematrix.assertNotesExists();
		pricematrix.assertTechniciansExists();
		pricematrix.clickOnTechnicians();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA),
				PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "285");
		String alerttext = selectedservicescreen
				.saveSelectedServiceDetailsWithAlert();
		Assert.assertTrue(alerttext.contains("Split amount should be equal to total amount."));
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "40");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.assertServicePriceValue("%25.000");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
		pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		pricematrix.assertNotesExists();
		pricematrix.assertTechniciansExists();

		pricematrix.clickOnTechnicians();
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "125");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "75");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));

		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "100");
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "45");

		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.clickSaveButton();
		pricematrix.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		pricematrix.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();

	}
	
	@Test(testName = "Test Case 12626:Test Customer Discount on Retail Hail", description = "Test Customer Discount on Retail Hail")
	public void testCustomerDiscountOnRetailHail() throws Exception {
		String tcname = "testCustomerDiscountOnRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));		
		
		questionsscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		claimscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.openServiceDetails("Customer Discount");
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();	

		selectedservicescreen.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();

	}
	
	@Test(testName = "Test Case 12627:Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice", description = "Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice")
	public void testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice()
			throws Exception {
		String tcname = "testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Roof Rail", "Right Roof Rail",
				"Roof" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);
		
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.searchAvailableService(UtilConstants.PAINTROCKERPANELS_SUBSERVICE);
		servicesscreen.openCustomServiceDetails(UtilConstants.PAINTROCKERPANELS_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.searchAvailableService(UtilConstants.WHEEL_SUBSERVICE);
		servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		homescreen = myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		Thread.sleep(2000);
		teamworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		teamworkordersscreen.clickiCreateInvoiceButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
		teamworkordersscreen.clickHomeButton();
		
	}*/
	
	@Test(testName = "Test Case 12628:Test Phase Enforcement for WizardPro Tracker", description = "Test Phase Enforcement for WizardPro Tracker")
	public void testPhaseEnforcementForWizardProTracker()
			throws Exception {
		String tcname = "testPhaseEnforcementForWizardProTracker";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Fender", "Left Front Door",
				"Left Quarter Panel" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		String inspection = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(inspection);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.PDRPANEL_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		//ordermonitorscreen.selectPanel(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		Assert.assertTrue(ordermonitorscreen.isServiceIsActive(UtilConstants.PAINTDOORHANDLE_SUBSERVICE));

		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDRPANEL_SUBSERVICE, "Completed");
		
		ordermonitorscreen.selectPanel(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE, "Completed");
		
		ordermonitorscreen.selectPanel(UtilConstants.WHEEL_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.WHEEL_SUBSERVICE, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection);
		teamworkordersscreen.verifyCreateInvoiceIsActivated(inspection);
		teamworkordersscreen.clickiCreateInvoiceButton();
		teamworkordersscreen.selectWOInvoiceType(UtilConstants.NO_ORDER_TYPE);
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		teamworkordersscreen.clickHomeButton();
		
	}
	
	/*@Test(testName = "Test Case 12630:Test adding services to an order being monitored", description = "Test adding services to an order being monitored")
	public void testAddingServicesToOnOrderBeingMonitored()
			throws Exception {
		String tcname = "testAddingServicesToOnOrderBeingMonitored";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Rear Door",
				"Right Front Door" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };
		final String[] vehiclepartspaint = { "Front Bumper"};
		final String[] vehiclepartstoadd = { "Hood"};
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		String inspection = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();

		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.clickSaveButton();
		Thread.sleep(2000);
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		Thread.sleep(2000);
		teamworkordersscreen.clickOnWO(inspection);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE, "Completed");
		ordermonitorscreen.verifyPanelsStatuses(" Paint - Full Bumper", "Active");
		
		ordermonitorscreen.clickServicesButton();
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice4(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartstoadd.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartstoadd[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveButton();
		Thread.sleep(4000);
		ordermonitorscreen = new OrderMonitorScreen(appiumdriver);
		
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE, "Queued");
		ordermonitorscreen.selectPanel(UtilConstants.PDRPANEL_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDRPANEL_SUBSERVICE, "Completed");
		
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE, "Active");
		ordermonitorscreen.selectPanel(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE, "Completed");
		
		
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE, "Active");	
		ordermonitorscreen.selectPanel(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE, "Completed");

		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection);
		teamworkordersscreen.clickiCreateInvoiceButton();
		teamworkordersscreen.selectWOInvoiceType(UtilConstants.NO_ORDER_TYPE);
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		teamworkordersscreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 12629:Test Start Service feature is accurately capturing times", description = "Test Start Service feature is accurately capturing times")
	public void testStartServiceFeatureIsAccuratelyCapturingTimes()
			throws Exception {
		String tcname = "testStartServiceFeatureIsAccuratelyCapturingTimes";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Right Front Door", "Right Quarter Panel",
				"Right Rear Door", "Right Roof Rail" };
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.DETAIL_SERVICE);
		servicesscreen.selectService(UtilConstants.FRONTLINEREADY_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		Thread.sleep(1000);
		teamworkordersscreen.clickOnWO(wonumber);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		//String servicedisplayname = ordermonitorscreen.selectPanel(UtilConstants.PDRVEHICLE_SUBSERVICE);
		Assert.assertTrue(ordermonitorscreen.isServiceIsActive(UtilConstants.FRONTLINEREADY_SUBSERVICE));
		ordermonitorscreen.selectPanel(UtilConstants.FRONTLINEREADY_SUBSERVICE);
		ordermonitorscreen.clickStartService();
		Thread.sleep(1000);
		ordermonitorscreen.selectPanel(UtilConstants.FRONTLINEREADY_SUBSERVICE);
		ordermonitorscreen.verifyStartServiceDissapeared();
		
		String srvstartdate = ordermonitorscreen.getServiceStartDate().substring(0, 10);
		ordermonitorscreen.clickServiceDetailsDoneButton();
		ordermonitorscreen.verifyServiceStartDateIsSet(UtilConstants.FRONTLINEREADY_SUBSERVICE, srvstartdate);
		
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 12631:Test Quantity does not mulitply price in Route package", description = "Test Quantity does not mulitply price in Route package")
	public void testQuantityDoesNotMulitplyPriceInRoutePackage()
			throws Exception {
		String tcname = "testQuantityDoesNotMulitplyPriceInRoutePackage";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String vehiclepart = "Left Roof Rail";
		final String[] vehiclepartspaint = { "Hood", "Left Roof Rail", "Right Fender" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.setServiceQuantityValue("4");
		//selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickVehiclePartsCell();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		Thread.sleep(1000);
		selectedservicescreen.selectVehiclePart(vehiclepart);

		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.setServiceQuantityValue("5");
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();

		myworkordersscreen.clickHomeButton();		
	}
	
	@Test(testName = "Test Case 12632:Test Delete Work Order function", description = "Test Delete Work Order function")
	public void testDeleteWorkOrderFunction()
			throws Exception {
		String tcname = "testDeleteWorkOrderFunction";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Roof" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routecanadaworkordertype);		
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.deleteWorkOrderViaAction(wo);
		Assert.assertFalse(myworkordersscreen.woExists(wo));
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 12637:Test changing customer on Inspection", description = "Test changing customer on Inspection")
	public void testChangingCustomerOnInspection()
			throws Exception {
		String tcname = "testChangingCustomerOnInspection";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Front Wheel", "Left Rear Wheel" };

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.routeworkordertype);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.DETAIL_SERVICE);
		servicesscreen.selectService(UtilConstants.WASH_N_VAC_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(UtilConstants.WASH_N_VAC_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.openCustomServiceDetails(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.clickVehiclePartsCell();
	
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveButton();
		Thread.sleep(4000);
		String inspectioncustomer = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.changeCustomerForInspection(inspectioncustomer, customer);
		myinspectionsscreen.clickHomeButton();
		
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(customer);
		customersscreen.selectFirstCustomerWithoutEditing();
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.assertInspectionExists(inspectioncustomer);
		myinspectionsscreen.clickHomeButton();
		
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		homescreen = new HomeScreen(appiumdriver);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.assertInspectionDoesntExists(inspectioncustomer);
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12638:Test Retail Hail package quantity multiplier", description = "Test Retail Hail package quantity multiplier")
	public void testRetailHailPackageQuantityMultiplier() throws Exception {
		String tcname = "testRetailHailPackageQuantityMultiplier";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String servicequantity = "3";
		final String servicequantity2 = "4.5";
		final String totalsumm = "$3,738.00";

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
		myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.acceptForReminderNoDrilling();

		questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption());
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
		
		questionsscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		ClaimScreen claimscreen = new ClaimScreen(appiumdriver);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		claimscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.setServiceQuantityValue(servicequantity);	
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.setServiceQuantityValue(servicequantity2);	
		selectedservicescreen.saveSelectedServiceDetails();		
		
		selectedservicescreen.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(totalsumm);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		ordersummaryscreen.selectWorkOrderDetails("Hail No Discount Invoice");
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.assertOrderSummIsCorrect(totalsumm);
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();

	}
	
	@Test(testName = "Test Case 12641:Test custom WO level split for Route package", description = "Test custom WO level split for Route package")
	public void testCustomWOLevelSplitForRoutePackage() throws Exception {
		String tcname = "testCustomWOLevelSplitForRoutePackage";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Right Mirror", "Left Mirror" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.selectTechniciansCustomView();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA, "70");
		selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB, "30");
		String alerttext = selectedservicescreen
				.saveSelectedServiceDetailsWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);
		
		selectedservicescreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
		selectedservicescreen =  servicesscreen.openCustomServiceDetails(UtilConstants.DUELEATHER_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA), "$29.40");
		Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianB), "$12.60");
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "31.50");
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%75.00");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "10.50");
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%25.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
	
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA), "$70.00");
		Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianB), "$30.00");
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "28");
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%28.00");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "67");
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%67.00");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "5");
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%5.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		ordersummaryscreen.selectWorkOrderDetails(UtilConstants.NO_ORDER_TYPE);
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12642:Test even WO level split for Route package", description = "Test even WO level split for Route package")
	public void testEvenWOLevelSplitForRoutePackage() throws Exception {
		String tcname = "testEvenWOLevelSplitForRoutePackage";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Fender", "Left Front Door", "Left Quarter Panel", "Left Rear Door", "Left Roof Rail" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA), "%50.00");
		Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianB), "%50.00");

		String alerttext = selectedservicescreen
				.saveSelectedServiceDetailsWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);
		
		selectedservicescreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.DETAIL_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FRONTLINEREADY_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.OTHER_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));	
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), "$108.00");
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%50.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), "$80.00");
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%33.33");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		ordersummaryscreen.selectWorkOrderDetails(UtilConstants.NO_ORDER_TYPE);
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12644:Test adding notes to a Work Order", description = "Test adding notes to a Work Order")
	public void testAddingNotesToWorkOrder()
			throws Exception {
		String tcname = "testAddingNotesToWorkOrder";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = {  "Hood", "Left Rear Door", "Right Fender" };
		final String[] vehiclepartspaint = {  "Left Rear Door", "Right Fender" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routecanadaworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		NotesScreen notesscreen = vehiclescreeen.clickNotesButton();
		notesscreen.setNotes("Blue fender");
		//notesscreen.clickDoneButton();
		notesscreen.clickSaveButton();
		
		notesscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.clickNotesCell();
		notesscreen.setNotes("Declined right door");
		notesscreen.clickSaveButton();
		
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.clickNotesCell();
		notesscreen.setNotes("Declined hood");
		notesscreen.clickSaveButton();
		Thread.sleep(1000);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
	
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();

		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenum));
		myinvoicesscreen.selectInvoice(invoicenum);
		notesscreen = myinvoicesscreen.clickNotesPopup();
		notesscreen.setNotes("Declined wheel work");
		notesscreen.clickSaveButton();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12645:Test changing the PO# on an invoice", description = "Test changing the PO# on an invoice")
	public void testChangingThePOOnAnInvoice()
			throws Exception {
		String tcname = "testChangingThePOOnAnInvoice";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.servicedriveworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.setTotalSale("1");
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setPO("832145");
		String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();

		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenum));
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO("832710");
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12646:Test editing an Inspection", description = "Test editing an Inspection")
	public void testEditingAnInspection()
			throws Exception {
		String tcname = "testEditingAnInspection";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Front Door", "Left Rear Door", "Right Front Door", "Right Rear Door" };

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.routeworkordertype);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.clickSaveButton();
		Thread.sleep(2000);
		String insptoapprove = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.selectInspectionInTable (insptoapprove);
		myinspectionsscreen.clickEditInspectionButton();
		myinspectionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12647:Test editing a Work Order", description = "Test editing a Work Order")
	public void testEditingWorkOrder()
			throws Exception {
		String tcname = "testEditingWorkOrder";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		String wo = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreeen.setRO(ExcelUtils.getRO(testcaserow));
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.INTERIOR_FABRIC_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00");
		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectService(UtilConstants.INTERIOR_VINIL_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.68 x 1.00");
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.selectWorkOrderForEidt(wo);
		myworkordersscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());

		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEELCOVER2_SUBSERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.68 x 1.00");
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.WHEELCOVER2_SUBSERVICE, "$45.00 x 1.00");
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen.clickSaveButton();
		
		
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19514:Test editing an Invoice in Draft", description = "Test Editing an Invoice in Draft")
	public void testEditingAnInvoiceInDraft()
			throws Exception {
		String tcname = "testEditingAnInvoiceInDraft";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehiclepartspdr = { "Left Fender", "Left Roof Rail", "Right Quarter Panel", "Trunk Lid" };
		final String[] vehiclepartspaint = { "Left Mirror" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails (UtilConstants.CARPETREPAIR_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspdr.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspdr[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();

		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Thread.sleep(1000);
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen.clickFirstWO();
		Thread.sleep(1000);
		invoiceinfoscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		selectedservicescreen = servicesscreen.openServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedservicescreen.removeService();
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehiclePart("Right Mirror");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		ordersummaryscreen.clickSaveButton();
		invoiceinfoscreen.clickSaveAsFinal();
		homescreen = myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19548:Test adding a PO# to an invoice", description = "Test adding a PO# to an invoice")
	public void testAddingAPOToAnInvoice()
			throws Exception {
		String tcname = "testAddingAPOToAnInvoice";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizardprotrackeravisworkordertype);

		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		String inspection = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));		
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.INTERIOR_FABRIC_SERVICE);
		servicesscreen.selectService("Tear/Burn >2\" (Fabric)");                                         
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDR6PANEL_SUBSERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertServiceIsSelected(UtilConstants.PDR6PANEL_SUBSERVICE);
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(inspection);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.PDR6PANEL_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE, "Completed");
		ordermonitorscreen.verifyPanelStatusInPopup("Tear/Burn >2\" (Fabric)", "Active");
		Thread.sleep(4000);
		ordermonitorscreen.selectPanel("Tear/Burn >2\" (Fabric)");
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE, "Completed");
		ordermonitorscreen.verifyPanelStatusInPopup("Tear/Burn >2\" (Fabric)", "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		Thread.sleep(4000);
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection);
		teamworkordersscreen.verifyCreateInvoiceIsActivated(inspection);
		teamworkordersscreen.clickiCreateInvoiceButton();
		teamworkordersscreen.selectNextScreen("AVIS Questions");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		
		questionsscreen.chooseAVISCode("Rental-921");
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();
		teamworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectFirstInvoice(ExcelUtils.getVIN(testcaserow));
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO("170116");
		Assert.assertTrue(myinvoicesscreen.isFirstInvoiceHasInvoiceNumberIcon());
		Assert.assertTrue(myinvoicesscreen.isFirstInvoiceHasInvoiceSharedIcon());
		myinvoicesscreen.verifyFirstInvoicePrice(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19670:Test adding a PO# to an invoice containing multiple Work Orders", description = "Test adding a PO# to an invoice containing multiple Work Orders")
	public void testAddingPOToAnInvoiceContainingMultipleWorkOrders()
			throws Exception {
		String tcname1 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders1";
		String tcname2 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders2";
		String tcname3 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders3";
		
		int testcaserow1 = ExcelUtils.getTestCaseRow(tcname1);
		int testcaserow2 = ExcelUtils.getTestCaseRow(tcname2);
		int testcaserow3 = ExcelUtils.getTestCaseRow(tcname3);
		
		final String[] vehicleparts = { "Left Rear Door", "Right Rear Door" };
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow1));
		String inspection1 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow1)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.clickSaveButton();
		
		//Create second WO
		myworkordersscreen.clickAddOrderButton();
		vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow2));
		String inspection2 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.LEATHERREPAIR_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow2));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow2)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.clickSaveButton();
		
		
		//Create third WO
		myworkordersscreen.clickAddOrderButton();
		vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow3));
		String inspection3 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow3), ExcelUtils.getModel(testcaserow3), ExcelUtils.getYear(testcaserow3));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow3));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow3)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.clickSaveButton();
		
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(inspection1);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.PDRVEHICLE_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDRVEHICLE_SUBSERVICE, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickOnWO(inspection2);
		teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.LEATHERREPAIR_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.LEATHERREPAIR_SUBSERVICE, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickOnWO(inspection3);
		teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.BLACKOUT_SUBSERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		ordermonitorscreen.verifyPanelsStatuses(UtilConstants.BLACKOUT_SUBSERVICE, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();

		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection1);
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection2);
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection3);
		
		teamworkordersscreen.clickiCreateInvoiceButton();
		teamworkordersscreen.selectWOInvoiceType(UtilConstants.NO_ORDER_TYPE);
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();
		teamworkordersscreen.clickHomeButton();
		
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.getFirstInvoice().click();
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO("957884");
		myinvoicesscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 19671:Test Copy Vehicle feature", description = "Test Copy Vehicle feature")
	public void testCopyVehicleFeature()
			throws Exception {
		String tcname = "testCopyVehicleFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		String wo = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.OTHER_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveButton();
		Thread.sleep(1000);
		myworkordersscreen.selectWorkOrderForCopyVehicle(wo);
		vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19672:Test Copy Services feature", description = "Test Copy Services feature")
	public void testCopyServicesFeature()
			throws Exception {
		String tcname1 = "testCopyServicesFeature1";
		String tcname2 = "testCopyServicesFeature2";
		
		int testcaserow1 = ExcelUtils.getTestCaseRow(tcname1);
		int testcaserow2 = ExcelUtils.getTestCaseRow(tcname2);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow1));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));
		String wo = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
		
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveButton();
		myworkordersscreen.selectWorkOrderForCopyServices(wo);
		vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow2));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.BLACKOUT_SUBSERVICE,  
				PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow1)) + " x 1.00");
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 24164:Test Pre-Existing Damage option", description = "Test Pre-Existing Damage option")
	public void testPreExistingDamageOption()
			throws Exception {
		String tcname = "testPreExistingDamageOption";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left A Pillar", "Left Front Door", "Metal Sunroof", "Right Roof Rail" };

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.servicedriveinspectiondertype);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Scratch (Exterior)");
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.checkPreexistingDamage();
		selectedservicescreen.saveSelectedServiceDetails();	
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesscreen.clickSaveButton();

		String inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.selectInspectionInTable (inspnum);
		
		myinspectionsscreen.clickCreateWOButton();
		Helpers.waitABit(1000);
		String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("1");
		ordersummaryscreen.clickSaveButton();
		inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionNumberValue(), inspnum);
		vehiclescreeen = myinspectionsscreen.showWorkOrdersForInspection(inspnum);
		Assert.assertEquals(vehiclescreeen.getInspectionNumber(), wonumber);
		servicesscreen.clickCancelButton();

		myinspectionsscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Thread.sleep(2000);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		testlogger.log(LogStatus.INFO, wonumber);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19673:Test Car History feature", description = "Test Car History feature")
	public void testCarHistoryFeature()
			throws Exception {
		String tcname = "testCarHistoryFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreeen.setRO(ExcelUtils.getRO(testcaserow));
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.INTERIORPLASTIC_SERVICE);
		servicesscreen.selectService(UtilConstants.SCRTCH_1_SECTPLSTC_SERVICE);
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();	
		CarHistoryScreen carhistoryscreen = new CarHistoryScreen(appiumdriver); 
		carhistoryscreen.clickHomeButton();
		carhistoryscreen = homescreen.clickCarHistoryButton();
		carhistoryscreen.searchCar("887340");
		String strtocompare = ExcelUtils.getYear(testcaserow) + ", " + ExcelUtils.getMake(testcaserow) + ", " + ExcelUtils.getModel(testcaserow);
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
		carhistoryscreen.clickFirstCarHistoryInTable();
		MyInvoicesScreen myinvoicesscreen = carhistoryscreen.clickCarHistoryInvoices();
		Assert.assertTrue(myinvoicesscreen.myInvoicesIsDisplayed());
		myinvoicesscreen.clickHomeButton();

		carhistoryscreen.clickSwitchToWeb();
		Thread.sleep(5000);
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
		carhistoryscreen.clickFirstCarHistoryInTable();
		carhistoryscreen.clickCarHistoryInvoices();		

		Assert.assertTrue(myinvoicesscreen.teamInvoicesIsDisplayed());
		Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
		carhistoryscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19676:Test Total Sale requirement", description = "Test Total Sale requirement")
	public void testTotalSaleRequirement()
			throws Exception {
		String tcname = "testTotalSaleRequirement";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Hood", "Left Fender" };
		final String totalsale = "675";

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();

		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizardprotrackerservicedriveworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		ordersummaryscreen.setTotalSale(totalsale);
		Assert.assertTrue(alerttext.contains("Total Sale is required."));
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19678:Test package pricing for read only items", description = "Test package pricing for read only items")
	public void testPackagePricingForReadOnlyItems()
			throws Exception {
		String tcname = "testPackagePricingForReadOnlyItems";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Fender", "Left Roof Rail", "Right Fender" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreeen.setRO(ExcelUtils.getRO(testcaserow));
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.INTERIOR_LEATHER_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CRT_2_RPR_LTHR_SUBSERVICE);
		Assert.assertEquals(selectedservicescreen.getAdjustmentsValue(), "-$14.47");
		selectedservicescreen.setServiceQuantityValue("3.00");
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CUST2PNL_SUBSERVICE);
		Assert.assertEquals(selectedservicescreen.getAdjustmentsValue(), "-$6.55");
		selectedservicescreen.clickVehiclePartsCell();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.clickServiceTypesButton();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
				
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();
		vehiclescreeen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19681:Test price policy for service items from Inspection through Invoice creation", description = "Test price policy for service items from Inspection through Invoice creation")
	public void testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation()
			throws Exception {
		String tcname = "testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Front Door", "Left Rear Door", "Right Fender" };
		final String[] vehiclepartspaint = { "Hood", "Left Fender" };
		 
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.routeworkordertype);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
			
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();	
		servicesscreen.clickSaveButton();

		String inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionPriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		myinspectionsscreen.selectInspectionInTable (inspnum);
		myinspectionsscreen.clickCreateWOButton();
		
		String wonumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		for (int i = 0; i < vehicleparts.length; i++) {
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.PDRVEHICLE_SUBSERVICE + ", " + vehicleparts[i]
					, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)) + " x 1.00");
		}
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.PAINTPANEL_SUBSERVICE + ", " + vehiclepartspaint[i]
					, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)) + " x 1.00");
		}
		//servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.PDRVEHICLE_SUBSERVICE, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)) + " x 1.00");
		//servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.PAINTPANEL_SUBSERVICE, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)) + " x 1.00");
		servicesscreen.clickSaveButton();
		
		Thread.sleep(8000);
		inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionNumberValue(), inspnum);
		vehiclescreeen = myinspectionsscreen.showWorkOrdersForInspection(inspnum);
		Assert.assertEquals(vehiclescreeen.getInspectionNumber(), wonumber);
		servicesscreen.clickCancelButton();

		myinspectionsscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Thread.sleep(1000);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		testlogger.log(LogStatus.INFO, wonumber);
		Thread.sleep(2000);
		myworkordersscreen.clickInvoiceIcon();
		Thread.sleep(1000);
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 10263:Send Multiple Emails", description = "Send Multiple Emails")
	public void testSendMultipleEmails()
			throws Exception {		
		 
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.clickActionButton();
		for (int i = 0; i< 4; i++) {
			myinvoicesscreen.selectInvoiceForActionByIndex(i+1);
		}
		myinvoicesscreen.clickActionButton();
		//myinvoicesscreen.sendEmail();
		myinvoicesscreen.sendSingleEmail(UtilConstants.TEST_EMAIL);
		myinvoicesscreen.clickDoneButton();
		myinvoicesscreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 19683:Test Work Order Discount Override feature", description = "Test Work Order Discount Override feature")
	public void testWorkOrderDiscountOverrideFeature() throws Exception {		
		String tcname = "testWorkOrderDiscountOverrideFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String  _customer   = "Bel Air Auto Auction Inc";
		final String[] vehicleparts = { "Left Fender", "Right Fender"};

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(_customer);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServiceAdjustmentsValue("-$28.50");
		selectedservicescreen.clickVehiclePartsCell();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.WSANDBPANEL_SERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19685:Test completed RO only requirement for invoicing", description = "Test Completed RO only requirement for invoicing")
	public void testCompletedROOnlyRequirementForInvoicing()
			throws Exception {
		String tcname = "testCompletedROOnlyRequirementForInvoicing";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Quarter Panel", "Right Roof Rail", "Trunk Lid" };
		final String[] vehiclepartswheel = { "Right Front Wheel", "Right Rear Wheel" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);
		
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		servicesscreen.searchAvailableService(UtilConstants.PAINTTOWHOOKCOVER_SUBSERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTTOWHOOKCOVER_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
				
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.searchAvailableService(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonumber);
		teamworkordersscreen.selectEditWO();
		Thread.sleep(2000);
		teamworkordersscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		Assert.assertFalse(ordersummaryscreen.isApproveAndCreateInvoiceExists());
		ordersummaryscreen.clickSaveButton();
		Thread.sleep(3000);
		teamworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		Thread.sleep(3000);
		teamworkordersscreen.clickiCreateInvoiceButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 10659:Test Enterprise Work Order question forms inforced", description = "Test Enterprize Work Order question forms inforced")
	public void testEnterprizeWorkOrderQuestionFormsInforced()
			throws Exception {
		String tcname = "testEnterprizeWorkOrderQuestionFormsInforced";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.enterpriseworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.selectNextScreen(EnterpriseBeforeDamageScreen.getEnterpriseBeforeDamageScreenCaption());
		EnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new EnterpriseBeforeDamageScreen(appiumdriver);
		enterprisebeforedamagescreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'VIN' in section 'Enterprise Before Damage' should be answered."));
		enterprisebeforedamagescreen.setVINCapture();
		enterprisebeforedamagescreen.clickSaveButton();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'License Plate' in section 'Enterprise Before Damage' should be answered."));
		enterprisebeforedamagescreen.setLicensePlateCapture();
		
		enterprisebeforedamagescreen.selectNextScreen(UtilConstants.ENTERPRISE_AFTER_REPAIR_SCREEN_CAPTION);
		enterprisebeforedamagescreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12633:Test successful email of pictures using Notes feature", description = "Test successful email of pictures using Notes feature")
	public void testSuccessfulEmailOfPicturesUsingNotesFeature()
			throws Exception {
		String tcname = "testSuccessfulEmailOfPicturesUsingNotesFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.avisworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
		String wo = vehiclescreeen.getInspectionNumber();
		NotesScreen notesscreen = vehiclescreeen.clickNotesButton();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();
	
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.clickNotesButton();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 2);
		notesscreen.clickSaveButton();
		
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDR2PANEL_SUBSERVICE);
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		selectedservicescreen.clickNotesCell();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectNextScreen(EnterpriseBeforeDamageScreen.getEnterpriseBeforeDamageScreenCaption());
		EnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new EnterpriseBeforeDamageScreen(appiumdriver);
		enterprisebeforedamagescreen.setVINCapture();
		enterprisebeforedamagescreen.setLicensePlateCapture();
		
		enterprisebeforedamagescreen.selectNextScreen(UtilConstants.ENTERPRISE_AFTER_REPAIR_SCREEN_CAPTION);
		enterprisebeforedamagescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.selectWorkOrderForAddingNotes(wo);
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 3);
		notesscreen.clickSaveButton();
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wo);
		myworkordersscreen.clickInvoiceIcon();
		myworkordersscreen.selectNextScreen("AVIS Questions");
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		questionsscreen.chooseAVISCode("Other-920");
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.clickSaveAsFinal();
		
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectFirstInvoice(ExcelUtils.getVIN(testcaserow));
		notesscreen =  myinvoicesscreen.clickNotesPopup();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();
		myinvoicesscreen.clickActionButton();
		myinvoicesscreen.selectInvoiceForActionByIndex(1);
		myinvoicesscreen.clickActionButton();
		myinvoicesscreen.sendEmail(UtilConstants.TEST_EMAIL);
		myinvoicesscreen.clickDoneButton();
		myinvoicesscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 12634:Test emailing photos in Economical Inspection", description = "Test emailing photos in Economical Inspection")
	public void testEmailingPhotosInEconomicalInspection()
			throws Exception {
		String tcname = "testEmailingPhotosInEconomicalInspection";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();

		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType (UtilConstants.economicalinspectiondertype);
		VehicleScreen vehiclescreeen = new VehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		//String wo = vehiclescreeen.getInspectionNumber();
		NotesScreen notesscreen = vehiclescreeen.clickNotesButton();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();
	
		vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		vehiclescreeen.selectNextScreen(UtilConstants.HAIL_PHOTOS_SCREEN_CAPTION);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.makeCaptureForQuestion("VIN");
		questionsscreen.makeCaptureForQuestion("Odometer");
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.makeCaptureForQuestion("License Plate Number");
		questionsscreen.makeCaptureForQuestion("Left Front of Vehicle");
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.makeCaptureForQuestion("Right Front of Vehicle");
		questionsscreen.makeCaptureForQuestion("Right Rear of Vehicle");
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.makeCaptureForQuestion("Left Rear of Vehicle");
		questionsscreen.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectProperQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.CANADA, ExcelUtils.getOwnerZip(retailhaildatarow));
		
		questionsscreen.selectNextScreen(UtilConstants.PRICE_MATRIX_SCREEN_CAPTION);
		PriceMatrixScreen pricematrix = new PriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		pricematrix.clickNotesButton();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 2);
		notesscreen.clickSaveButton();
		
		questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("E-Coat");
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.clickNotesCell();
		notesscreen.addNotesCapture();
		notesscreen.clickSaveButton();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveButton();
		Thread.sleep(3000);
		String insptoapprove = myinspectionsscreen.getFirstInspectionNumberValue();
		myinspectionsscreen.selectInspectionInTable (insptoapprove);
		MyInvoicesScreen myinvoicesscreen = new MyInvoicesScreen(appiumdriver);
		myinvoicesscreen.sendEmail(UtilConstants.TEST_EMAIL);
		myinspectionsscreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 12635:Test emailing photos in Auction package", description = "Test emailing photos in Auction package")
	public void testEmailingPhotosInAuctionPackage()
			throws Exception {
		String tcname = "testEmailingPhotosInAuctionPackage";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Hood", "Left Fender", "Right Fender", "Roof" };
		final String firstnote = "Refused paint";
		final String secondnote = "Just 4 panels";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.auctionworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		String wo = vehiclescreeen.getInspectionNumber();
		NotesScreen notesscreen = vehiclescreeen.clickNotesButton();
		notesscreen.setNotes(firstnote);
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();
	
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.clickVehiclePartsCell();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}

		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickNotesCell();
		notesscreen.setNotes(secondnote);
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();
		selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.saveSelectedServiceDetails();
		//selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
		QuestionsScreen questionsscreen = new QuestionsScreen(appiumdriver);
		questionsscreen.chooseConsignor("Unknown Consignor/One Off-718");
		
		questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.selectWorkOrderForAddingNotes(wo);
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 2);
		notesscreen.clickSaveButton();
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wo);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType("Auction - No Discount Invoice");
		invoiceinfoscreen.clickSaveAsFinal();
		
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.clickActionButton();
		myinvoicesscreen.selectInvoiceForActionByIndex(1);
		myinvoicesscreen.clickActionButton();
		myinvoicesscreen.sendEmail(UtilConstants.TEST_EMAIL);
		myinvoicesscreen.clickDoneButton();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12636:Test emailing photos in Service Drive package", description = "Test emailing photos in Service Drive package")
	public void testEmailingPhotosInServiceDrivePackage()
			throws Exception {
		String tcname = "testEmailingPhotosInServiceDrivePackage";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Decklid", "Left A Pillar" };

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.servicedriveworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		
		servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.STAIN_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
					.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("1");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();

		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectFirstInvoice(ExcelUtils.getVIN(testcaserow));
		NotesScreen notesscreen = myinvoicesscreen.clickNotesPopup();
		notesscreen.setNotes("Refused paint");
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();
		
		myinvoicesscreen.clickActionButton();
		myinvoicesscreen.selectInvoiceForActionByIndex(1);
		myinvoicesscreen.clickActionButton();
		myinvoicesscreen.sendEmail(UtilConstants.TEST_EMAIL);
		myinvoicesscreen.clickDoneButton();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 24131:Test PO# saves with active keyboard on WO summary screen", description = "Test PO# saves with active keyboard on WO summary screen")
	public void testPONumberSavesWithActiveKeyboardOnWOSummaryScreen()
			throws Exception {
		String tcname = "testPONumberSavesWithActiveKeyboardOnWOSummaryScreen";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String _po = "998601";
		final String[] vehicleparts = { "Hood", "Left Quarter Panel", "Right Roof Rail" };
		
		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(customer);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
		invoiceinfoscreen.setPO(_po);
		invoiceinfoscreen.clickSaveAsFinal();			
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 24132:Test Total Sale saves with active keyboard on WO summary screen", description = "Test Total Sale saves with active keyboard on WO summary screen")
	public void testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen()
			throws Exception {
		String tcname = "testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		final String totalsale = "675";

		homescreen = new HomeScreen(appiumdriver);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.servicedriveworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		
		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "", description = "Test Invoice Question Answers save with active keyboard on Questions summary screen")
	public void testInvoiceQuestionAnswersSaveWithActiveKeyboardOnQuestionsSummaryScreen ()
			throws Exception {
		String tcname = "testInvoiceQuestionAnswersSaveWithActiveKeyboardOnQuestionsSummaryScreen";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String _po = "998601";
		final String[] vehicleparts = { "Left Front Wheel", "Left Rear Wheel" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.searchCustomer(customer);
		customersscreen.selectFirstCustomerWithoutEditing();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		VehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
		vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption());
		ServicesScreen servicesscreen = new ServicesScreen(appiumdriver);
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.WHEEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));		
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

		servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption());
		OrderSummaryScreen ordersummaryscreen = new OrderSummaryScreen(appiumdriver);
		ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType("Ding Shield");
		invoiceinfoscreen.setPO(_po);
		invoiceinfoscreen.clickSaveAsFinal();		
		myworkordersscreen.clickHomeButton();
	}*/
}

package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.core.IOSHDDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.DentWizardIOSInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.DentWizardInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.DentWizardInvoiceTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.DentWizardWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DentWizartestCases extends BaseTestCase {

	private String regCode;
	private final String customer = "Abc Rental Center";
	public HomeScreen homescreen;
	
	@BeforeClass
	public void setUpSuite() throws Exception {
		mobilePlatform = MobilePlatform.IOS_HD;
		initTestUser(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
		testGetDeviceRegistrationCode(DentWizardIOSInfo.getInstance().getBackOfficeURL(),
				DentWizardIOSInfo.getInstance().getUserName(), DentWizardIOSInfo.getInstance().getUserPassword());
		testRegisterationiOSDdevice();
		ExcelUtils.setDentWizardExcelFile();
	}

	//@Test(description = "Get registration code on back-office for iOS device")
	public void testGetDeviceRegistrationCode(String backofficeurl,
			String userName, String userPassword) throws Exception {

		final String searchlicensecriteria = "Mac mini_olkr";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companyWebPage = backofficeheader.clickCompanyLink();

		ActiveDevicesWebPage devicespage = companyWebPage.clickManageDevicesLink();

		devicespage.setSearchCriteriaByName(searchlicensecriteria);
		regCode = devicespage.getFirstRegCodeInTable();
		DriverBuilder.getInstance().getDriver().quit();
	}

	//@Test(description = "Register iOS Ddevice")
	public void testRegisterationiOSDdevice() throws Exception {		
		AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);

		DriverBuilder.getInstance().getAppiumDriver().removeApp(IOSHDDeviceInfo.getInstance().getDeviceBundleId());
		DriverBuilder.getInstance().getAppiumDriver().quit();
		AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
		SelectEnvironmentPopup selectenvscreen = new SelectEnvironmentPopup();
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("Dev Environment");
		loginscreen.registeriOSDevice(regCode);
		MainScreen mainscr = new MainScreen();
		homescreen = mainscr.userLogin(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.setInsvoicesCustomLayoutOff();
		settingsscreen.clickHomeButton();
		
	}
	
	@BeforeMethod
	public void restartApps() throws InterruptedException {
		//resrtartApplication();	
		//MainScreen mainscr = new MainScreen();
		//homescreen = mainscr.userLogin(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
		System.out.println("================================ NEW TESTACASE ====================================");
	}

	@Test(testName = "Test Case 10264:Test Valid VIN Check", description = "Test Valid VIN Check")
	public void testValidVINCheck() throws Exception {
		final String tcname = "testValidVINCheck";
		final int tcrow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(tcrow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(tcrow), ExcelUtils.getModel(tcrow), ExcelUtils.getYear(tcrow));
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 10451:Test Top Customers Setting", description = "Test Top Customers Setting")
	public void testTopCustomersSetting() throws Exception {
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setShowTopCustomersOn();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
        Assert.assertTrue(customersscreen.isTopCustomersExists());
        Assert.assertTrue(customersscreen.isCustomerExists(UtilConstants.TEST_CUSTOMER_FOR_TRAINING));
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
		customersscreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVINAndAndSearch(ExcelUtils.getVIN(tcrow).substring(
				0, 11));
		vehiclescreen.setVIN(ExcelUtils.getVIN(tcrow).substring(11, 17));
		vehiclescreen.verifyExistingWorkOrdersDialogAppears();		
		vehiclescreen.saveWizard();
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
		customersscreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		//Assert.assertEquals(searchresult, "Search Complete No vehicle invoice history found");
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		Assert.assertTrue(servicesscreen
				.isServiceTypeExists(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE));
		Assert.assertTrue(servicesscreen
				.isServiceTypeExists(UtilConstants.PDRPANEL_SUBSERVICE));
		Assert.assertTrue(servicesscreen
				.isServiceTypeExists(UtilConstants.PDRVEHICLE_SUBSERVICE));
		Assert.assertTrue(servicesscreen
				.isServiceTypeExists(UtilConstants.PDRPANEL_HAIL_SUBSERVICE));
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.cancelSelectedServiceDetails();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

		settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();

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
		customersscreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo1 = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//ServicesScreen servicesscreen = new ServicesScreen();
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheels.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheels[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.saveWizard();

		// ==================Create second WO=============
		tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice2";
		testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts2 = { "Hood", "Roof", "Trunk Lid" };
		final String[] vehiclepartspaints = { "Front Bumper", "Rear Bumper" };

		myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo2 = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
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
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
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
		servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickCreateInvoiceIconForWO(wo1);
		myworkordersscreen.clickCreateInvoiceIconForWO(wo2);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertTrue(invoiceinfoscreen.isWOSelected(wo1));
        Assert.assertTrue(invoiceinfoscreen.isWOSelected(wo2));
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
        myworkordersscreen = new MyWorkOrdersScreen();
		Assert.assertFalse(myworkordersscreen.woExists(wo1));
		Assert.assertFalse(myworkordersscreen.woExists(wo2));
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String wosubstring = wo1 + ", " + wo2;
		System.out.println("+++++" + invoicenumber);
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo1 = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		for (int i = 0; i <= vehicleparts.length; i++) {
			selectedservicescreen.saveSelectedServiceDetails();
		}
        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();

		// ==================Create second WO=============
		tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice2";
		testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts2 = { "Hood" };

		myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo2 = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts2.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts2[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

        servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickCreateInvoiceIconForWO(wo1);
		myworkordersscreen.clickCreateInvoiceIconForWO(wo2);
		myworkordersscreen.clickInvoiceIcon();
		String alerttext = myworkordersscreen
				.selectInvoiceTypeAndAcceptAlert(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		Assert.assertEquals(
				alerttext,
				"Invoice type " + DentWizardInvoiceTypes.NO_ORDER_TYPE.getInvoiceTypeName() + " doesn't support multiple Work Order types.");
		myworkordersscreen = new MyWorkOrdersScreen();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.wizprotrackerrouteunspectiontype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehiclescreen.getInspectionNumber();
        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehicleParts(vehicleparts);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.saveWizard();
		myinspectionsscreen = new MyInspectionsScreen();
		myinspectionsscreen.approveInspectionWithSignature(inspNumber);
        Assert.assertTrue(myinspectionsscreen.isInspectionApproved(inspNumber));
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.wizprotrackerrouteunspectiontype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehiclescreen.getInspectionNumber();
        ServicesScreen servicesscreen =vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehicleParts(vehicleparts);

		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehicleParts(vehicleparts2);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$930.00");

		servicesscreen.saveWizard();
		myinspectionsscreen = new MyInspectionsScreen();
		myinspectionsscreen.approveInspectionWithSignature(inspNumber);
        Assert.assertTrue(myinspectionsscreen.isInspectionApproved(inspNumber));
		myinspectionsscreen.selectInspectionInTable(inspNumber);
		myinspectionsscreen.clickCreateWOButton();
		vehiclescreen = new VehicleScreen();
        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber.substring(0, 1), "O");
        Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		ordersummaryscreen.saveWizard();

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
		customersscreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10655:Test 'Panel' Service multiplies price entered when selecting multiple panels", description = "Test 'Panel' Service multiplies price entered when selecting multiple panels")
	public void testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels()
			throws Exception {
		String tcname = "testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Fender", "Left Front Door",
				"Left Rear Door" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesscreen.cancelWizard();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		Assert.assertTrue(vehiclescreen.clickSaveWithAlert().contains("Stock# is required"));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
		Assert.assertTrue(vehiclescreen.clickSaveWithAlert().contains("RO# is required"));
		vehiclescreen.setRO(ExcelUtils.getRO(testcaserow));
        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.cancelWizard();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		Assert.assertTrue(vehiclescreen.clickSaveWithAlert().contains("Advisor is required"));
		vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName= "Test Case 10658:Test Inspection requirments inforced", description = "Test Inspection requirements inforced")
	public void testInspectionRequirementsInforced() throws Exception {
		String tcname = "testInspectionRequirementsInforced";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
	
		CustomersScreen customersscreen = homescreen.clickCustomersButton();

		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.wizardprotrackerrouteinspectiondertype);
		String alerttext = vehicleScreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("VIN# is required"));
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.clickSave();
		alerttext = Helpers.getAlertTextAndAccept();
		//alerttext = inspectionscreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("Advisor is required"));
		vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
        QuestionsScreen questionsScreen =  vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS);
        questionsScreen.saveWizard();
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

		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.routecanadainspectiontype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		servicesscreen.selectService(UtilConstants.INTERIORBURNS_SUBSERVICE);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(UtilConstants.INTERIORBURNS_SUBSERVICE));
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(UtilConstants.INTERIORBURNS_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionInTable(inspNumber);
		myinspectionsscreen.clickCreateWOButton();
		vehiclescreen = new VehicleScreen();
        servicesscreen = servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES);
        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber.substring(0, 1), "O");
        Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		ordersummaryscreen.saveWizard();
		myinspectionsscreen.showWorkOrdersForInspection(inspNumber);
		vehiclescreen = new VehicleScreen();
		Assert.assertEquals(vehiclescreen.getInspectionNumber(), wonumber);
		servicesscreen.clickCancelButton();
		
		myinspectionsscreen.selectInspectionInTable(inspNumber);
		myinspectionsscreen.clickCreateWOButton();
		vehiclescreen = new VehicleScreen();
        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
        ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		String wonumber2 = ordersummaryscreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber2.substring(0, 1), "O");
        Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		ordersummaryscreen.saveWizard();
		myinspectionsscreen.showWorkOrdersForInspection(inspNumber);
		Thread.sleep(10000);
		Assert.assertTrue(myinspectionsscreen.isWorkOrderForInspectionExists(wonumber));
		myinspectionsscreen.showWorkOrdersForInspection(inspNumber);
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

		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.routeinspectiontype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.OTHER_SERVICE);
		servicesscreen.selectService(UtilConstants.WINDOWTINT_SUBSERVICE);

		Assert.assertTrue(servicesscreen.checkServiceIsSelected(UtilConstants.WINDOWTINT_SUBSERVICE));
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionInTable(inspNumber);

		myinspectionsscreen.clickArchiveInspectionButton();
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

        Assert.assertTrue(myinspectionsscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.selectTechniciansEvenlyView();
		String alerttext = selectedservicescreen
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS);
        ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);

		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(UtilConstants.FIXPRICE_SERVICE));

		servicesscreen.openServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
				vehicleparts[0]);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		for (int i = 1; i < vehicleparts.length; i++) {
			Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[i]
					, "$0.00 x 1.00"));
		}

		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[0]
				, "$105.00 x 1.00"));

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
			Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[i]
					, "$0.00 x 1.00"));
		}
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[0]
				, "$105.00 x 1.00"));
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[1]
				, "$140.00 x 1.00"));

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
			Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[i]
					, "$0.00 x 1.00"));
		}
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[0]
				, "$105.00 x 1.00"));
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[1]
				, "$140.00 x 1.00"));
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[2]
				, "$60.00 x 1.00"));

		servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
		Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
				vehicleparts[3]);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[3]
				, "$275.00 x 1.00"));
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[0]
				, "$105.00 x 1.00"));
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[1]
				, "$140.00 x 1.00"));
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[2]
				, "$60.00 x 1.00"));

        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
        Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.checkCreateInvoice();
		//ordersummaryscreen.clickSaveButton();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype); ;
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS);
        ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		servicesscreen = new ServicesScreen();
        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceinfoscreen =  ordersummaryscreen.checkCreateInvoice();
		//ordersummaryscreen.clickSaveButton();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		//IOSStartScreenRecordingOptions stratrec = IOSStartScreenRecordingOptions.startScreenRecordingOptions();
		//stratrec.withVideoQuality(IOSStartScreenRecordingOptions.VideoQuality.HIGH).withVideoType(IOSStartScreenRecordingOptions.VideoType.H264).build();
		//MobileCommand.startRecordingScreenCommand(stratrec);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
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
				.saveTechnociansViewWithAlert();
		Assert.assertTrue(alerttext.contains("Total amount is not equal 100%"));
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "15");

		alerttext = selectedservicescreen.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS);
        ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%45.45");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "60");		
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%54.55");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.checkCreateInvoice();
		//ordersummaryscreen.clickSaveButton();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));

		QuestionsScreen questionsScreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS);
        ServicesScreen servicesscreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 0);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selectTechniciansCustomView();
		Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "45");	
		Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%36.00");
		String alerttext = selectedservicescreen
				.saveTechnociansViewWithAlert();
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
		selectedservicescreen = new SelectedServiceDetailsScreen();
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
		selectedservicescreen = new SelectedServiceDetailsScreen();
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

        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.checkCreateInvoice();
		//ordersummaryscreen.clickSaveButton();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 11735:Test Customer Discount on Wholesale Hail", description = "Test Customer Discount on Wholesale Hail")
	public void testCustomerDiscountOnWholesaleHail() throws Exception {
		String tcname = "testCustomerDiscountOnWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Hood", "Roof", "Trunk Lid" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
		QuestionsScreen questionsScreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS);
        ServicesScreen servicesscreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
        Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.checkCreateInvoice();
		//ordersummaryscreen.clickSaveButton();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10668:Test Quick Quote option for Retail Hail", description = "Test Quick Quote option for Retail Hail")
	public void testQuickQuoteOptionForRetailHail() throws Exception {
		String tcname = "testQuickQuoteOptionForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        questionsscreen  = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		String alerttext = questionsscreen.clickSaveWithAlert();
		Assert.assertTrue(
				alerttext.contains("Question 'Estimate Conditions' in section 'Hail Info' should be answered."));
		questionsscreen = new QuestionsScreen();
		questionsscreen.selectOutsideQuestions();

		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        ClaimScreen claimscreen = questionsscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        ServicesScreen servicesscreen = claimscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Quick Quote");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
		Assert.assertTrue(pricematrix.isNotesExists());
		Assert.assertTrue(pricematrix.isTechniciansExists());
		pricematrix.setPrice(ExcelUtils.getServicePrice(testcaserow));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
		pricematrix.setPrice(ExcelUtils.getServicePrice3(testcaserow));
		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
        questionsscreen  = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
        OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
        Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10733:Test Customer self-pay option for Retail Hail", description = "Test Customer self-pay option for Retail Hail")
	public void testCustomerSelfPayOptionForRetailHail() throws Exception {
		String tcname = "testCustomerSelfPayOptionForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        ClaimScreen claimscreen = questionsscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(ExcelUtils.getInsuranceCompany(retailhaildatarow));
		String alerttext = claimscreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("Claim# is required."));
		claimscreen.setClaim(ExcelUtils.getClaim(retailhaildatarow));
        ServicesScreen servicesscreen = claimscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.HAIL_PDR_NON_CUSTOMARY_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.answerQuestion("DEALER");
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();

        questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
        OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10734:Test Even WO level tech split for Retail Hail", description = "Test Even WO level tech split for Retail Hail")
	public void testEvenWOLevelTechSplitForRetailHail() throws Exception {
		String tcname = "testEvenWOLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
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
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

        questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        ClaimScreen claimscreen = questionsscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        ServicesScreen servicesscreen = claimscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.HEAVY_SEVERITY);
		Assert.assertEquals(pricematrix.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
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
		Assert.assertEquals(pricematrix.getPrice(), "$0.00");
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianC));

		pricematrix.setPrice(ExcelUtils.getServicePrice2(testcaserow));
		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen();
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
		servicesscreen = new ServicesScreen();
		questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
        OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10735:Test even service level tech split for Retail Hail", description = "Test even service level tech split for Retail Hail")
	public void testEvenServiceLevelTechSplitForRetailHail() throws Exception {
		String tcname = "testEvenServiceLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        ClaimScreen claimscreen = questionsscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        ServicesScreen servicesscreen = claimscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
		Assert.assertEquals(pricematrix.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		Assert.assertTrue(pricematrix.isNotesExists());
		Assert.assertTrue(pricematrix.isTechniciansExists());
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.unselecTechnician(UtilConstants.technicianA);

		selectedservicescreen.selecTechnician(UtilConstants.technicianB);

		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), "$60.00");

		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.HLF_SIZE, PriceMatrixScreen.LIGHT_SEVERITY);
		Assert.assertEquals(pricematrix.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		pricematrix.clickOnTechnicians();
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		pricematrix.clickDiscaunt(ExcelUtils.getDiscount3(retailhaildatarow));
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
        questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
        OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10736:Test deductible feature for Retail Hail", description = "Test deductible feature for Retail Hail")
	public void testDeductibleFeatureForRetailHail() throws Exception {
		String tcname = "testDeductibleFeatureForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        ClaimScreen claimscreen = questionsscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));
		claimscreen.setDeductible("50");
		Assert.assertEquals(
				claimscreen
						.getDeductibleValue(), "50.00");

        ServicesScreen servicesscreen = claimscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		Assert.assertEquals(pricematrix.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		Assert.assertTrue(pricematrix.isNotesExists());
		Assert.assertTrue(pricematrix.isTechniciansExists());
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
		questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
        OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		//ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL);
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10737:Test Zip Code validator for Retail Hail", description = "Test Zip Code validator for Retail Hail")
	public void testZipCodeValidatorForRetailHail() throws Exception {
		String tcname = "testZipCodeValidatorForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		final String validzip = "83707";

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        questionsscreen =vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext
				.contains("Your answer doesn't match the validator 'US Zip Codes'."));
		questionsscreen.clearZip();
		questionsscreen.setOwnerZip(validzip);
        ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 11727:Test Custom WO level tech split for Retail Hail", description = "Test Custom WO level tech split for Retail Hail")
	public void testCustomWOLevelTechSplitForRetailHail() throws Exception {
		String tcname = "testCustomWOLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selectTechniciansCustomView();
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA),
				"%100.00");

		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA,
				"70");
		String alerttext = selectedservicescreen
				.saveTechnociansViewWithAlert();
		Assert.assertTrue(alerttext.contains("Total amount is not equal 100%"));

		selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB,
				"30");
		alerttext = selectedservicescreen.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

        questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectProperQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        ClaimScreen claimscreen = questionsscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        ServicesScreen servicesscreen = claimscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.VERYLIGHT_SEVERITY);
		Assert.assertEquals(pricematrix.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		Assert.assertTrue(pricematrix.isNotesExists());
		Assert.assertTrue(pricematrix.isTechniciansExists());
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen();
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), "%25.000");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		Assert.assertEquals(pricematrix.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		Assert.assertTrue(pricematrix.isNotesExists());
		Assert.assertTrue(pricematrix.isTechniciansExists());
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen();
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
		pricematrix = new PriceMatrixScreen();
		pricematrix.clickSaveButton();
		Helpers.acceptAlert();
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "35.75");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
		questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 11728:Test Custom service level tech split for Retail Hail", description = "Test Custom service level tech split for Retail Hail")
	public void testCustomServiceLevelTechSplitForRetailHail() throws Exception {
		String tcname = "testCustomServiceLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        questionsscreen =vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        ClaimScreen claimscreen = questionsscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        ServicesScreen servicesscreen = claimscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		Assert.assertEquals(pricematrix.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		Assert.assertTrue(pricematrix.isNotesExists());
		Assert.assertTrue(pricematrix.isTechniciansExists());
		pricematrix.clickOnTechnicians();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(
				selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA),
				PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "285");
		String alerttext = selectedservicescreen
				.saveTechnociansViewWithAlert();
		Assert.assertTrue(alerttext.contains("Split amount should be equal to total amount."));
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "40");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen();
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), "%25.000");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
		Assert.assertEquals(pricematrix.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		Assert.assertTrue(pricematrix.isNotesExists());
		Assert.assertTrue(pricematrix.isTechniciansExists());

		pricematrix.clickOnTechnicians();
		selectedservicescreen = new SelectedServiceDetailsScreen();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selectTechniciansCustomView();
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "125");
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "75");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix = new PriceMatrixScreen();
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(
				UtilConstants.technicianB));

		pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedservicescreen = new SelectedServiceDetailsScreen();
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
		
		pricematrix = new PriceMatrixScreen();
		pricematrix.clickSaveButton();
		Helpers.acceptAlert();
		selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "121.25");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

	}
	
	@Test(testName = "Test Case 12626:Test Customer Discount on Retail Hail", description = "Test Customer Discount on Retail Hail")
	public void testCustomerDiscountOnRetailHail() throws Exception {
		String tcname = "testCustomerDiscountOnRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        ClaimScreen claimscreen = questionsscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        ServicesScreen servicesscreen = claimscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.openServiceDetails("Customer Discount");
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

        questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
        Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		ordersummaryscreen.saveWizard();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.searchAvailableService(UtilConstants.PAINTROCKERPANELS_SUBSERVICE);
		servicesscreen.openCustomServiceDetails(UtilConstants.PAINTROCKERPANELS_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesscreen.searchAvailableService(UtilConstants.WHEEL_SUBSERVICE);
		servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSave();
		homescreen = myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		teamworkordersscreen.clickiCreateInvoiceButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
		teamworkordersscreen.clickHomeButton();
		
	}
	
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen =  myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String inspection = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
        OrderSummaryScreen ordersummaryscreen =servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(inspection);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Active");
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDRPANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");

		ordermonitorscreen.selectPanel(UtilConstants.PAINT_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
 		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		
		ordermonitorscreen.selectPanel(UtilConstants.WHEELS_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.WHEEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection);
		
		Assert.assertTrue(teamworkordersscreen.isCreateInvoiceActivated(inspection));
		teamworkordersscreen.clickiCreateInvoiceButton();
        InvoiceInfoScreen invoiceinfoscreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		teamworkordersscreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 12630:Test adding services to an order being monitored", description = "Test adding services to an order being monitored")
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String inspection = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		
		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();


        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(inspection);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses(" Paint - Full Bumper");
		for (String status : statuses)
			Assert.assertEquals(status, "Active");
		
		ordermonitorscreen.clickServicesButton();
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice4(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartstoadd.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartstoadd[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		servicesscreen.clickSave();
		ordermonitorscreen = new OrderMonitorScreen();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Queued");

		ordermonitorscreen.selectPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDRPANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Active");

		ordermonitorscreen.selectPanel(UtilConstants.PAINT_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Active");

		ordermonitorscreen.selectPanel(UtilConstants.WHEELS_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");

		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection);
		teamworkordersscreen.clickiCreateInvoiceButton();
        InvoiceInfoScreen invoiceinfoscreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		teamworkordersscreen = new TeamWorkOrdersScreen();
		teamworkordersscreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 12631:Test Quantity does not mulitply price in Route package", description = "Test Quantity does not mulitply price in Route package")
	public void testQuantityDoesNotMulitplyPriceInRoutePackage()
			throws Exception {
		String tcname = "testQuantityDoesNotMulitplyPriceInRoutePackage";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String vehiclepart = "Left Roof Rail";
		final String[] vehiclepartspaint = { "Hood", "Grill", "Left Fender" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.setServiceQuantityValue("4");
		//selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickVehiclePartsCell();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehiclePart(vehiclepart);

		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.setServiceQuantityValue("5");
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehicleParts(vehiclepartspaint);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehicleParts(vehiclepartswheel);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();

        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		//ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
        myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();		
	}
	
	@Test(testName = "Test Case 12632:Test Delete Work Order function", description = "Test Delete Work Order function")
	public void testDeleteWorkOrderFunction()
			throws Exception {
		String tcname = "testDeleteWorkOrderFunction";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Roof" };
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String workOrderNumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.deleteWorkOrderViaAction(workOrderNumber);
		Assert.assertFalse(myworkordersscreen.woExists(workOrderNumber));
		myworkordersscreen.clickDoneButton();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.routeinspectiontype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehiclescreen.getInspectionNumber();
        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.DETAIL_SERVICE);
		servicesscreen.selectService(UtilConstants.WASH_N_VAC_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(UtilConstants.WASH_N_VAC_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesscreen.openCustomServiceDetails(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.clickVehiclePartsCell();
	
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myinspectionsscreen.changeCustomerForInspection(inspNumber, customer);
		myinspectionsscreen.clickHomeButton();
		
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(customer);
		
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
        Assert.assertTrue(myinspectionsscreen.isInspectionExists(inspNumber));
		myinspectionsscreen.clickHomeButton();
		
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		homescreen = new HomeScreen();
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
        Assert.assertFalse(myinspectionsscreen.isInspectionExists(inspNumber));
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		QuestionsScreen questionsscreen = myworkordersscreen.addWorkWithJobOrder(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		questionsscreen.acceptForReminderNoDrilling();

        VehicleScreen vehiclescreen =  questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        questionsscreen =vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        ClaimScreen claimscreen = questionsscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        ServicesScreen servicesscreen = claimscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.setServiceQuantityValue(servicequantity);	
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.setServiceQuantityValue(servicequantity2);	
		selectedservicescreen.saveSelectedServiceDetails();		
		
		questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
        OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
        Assert.assertEquals(ordersummaryscreen.getOrderSumm(), totalsumm);
        ordersummaryscreen.checkApproveAndSaveWorkOrder();
		//ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL_NO_DISCOUNT_INVOICE);
        Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), totalsumm);
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12641:Test custom WO level split for Route package", description = "Test custom WO level split for Route package")
	public void testCustomWOLevelSplitForRoutePackage() throws Exception {
		String tcname = "testCustomWOLevelSplitForRoutePackage";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Right Mirror", "Left Mirror" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.selectTechniciansCustomView();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		selectedservicescreen.typeTechnicianValue("30");
		//selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB, "30");
		selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA, "70");
		
		String alerttext = selectedservicescreen
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		selectedservicescreen =  servicesscreen.openCustomServiceDetails(UtilConstants.DUELEATHER_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
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
		
		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
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
        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		//ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12642:Test even WO level split for Route package", description = "Test even WO level split for Route package")
	public void testEvenWOLevelSplitForRoutePackage() throws Exception {
		String tcname = "testEvenWOLevelSplitForRoutePackage";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Fender", "Left Front Door", "Left Quarter Panel", "Left Rear Door", "Left Roof Rail" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.clickTech();
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(UtilConstants.technicianA));
		selectedservicescreen.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA), "%50.00");
		Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianB), "%50.00");

		String alerttext = selectedservicescreen
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.DETAIL_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FRONTLINEREADY_SUBSERVICE);

		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.OTHER_SERVICE);
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
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
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

        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		//ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12644:Test adding notes to a Work Order", description = "Test adding notes to a Work Order")
	public void testAddingNotesToWorkOrder()
			throws Exception {
		String tcname = "testAddingNotesToWorkOrder";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = {  "Hood", "Left Rear Door", "Right Fender" };
		final String[] vehiclepartspaint = {  "Dashboard", "Deck Lid" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen =  myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		NotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.setNotes("Blue fender");
		//notesscreen.clickDoneButton();
		notesscreen.clickSaveButton();

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.clickNotesCell();
		notesscreen.setNotes("Declined right door");
		notesscreen.clickSaveButton();
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehicleParts(vehicleparts);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.clickNotesCell();
		notesscreen.setNotes("Declined hood");
		notesscreen.clickSaveButton();
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehicleParts(vehiclepartspaint);

		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.checkCreateInvoice();
		//ordersummaryscreen.clickSaveButton();
        Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
        myworkordersscreen = new MyWorkOrdersScreen();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("1");
        InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.checkCreateInvoice();
		//ordersummaryscreen.clickSaveButton();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.routeinspectiontype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehiclescreen.getInspectionNumber();
        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionInTable(inspNumber);
		myinspectionsscreen.clickEditInspectionButton();
		vehiclescreen = new VehicleScreen();
        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12647:Test editing a Work Order", description = "Test editing a Work Order")
	public void testEditingWorkOrder()
			throws Exception {
		String tcname = "testEditingWorkOrder";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		String wo = vehiclescreen.getInspectionNumber();
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreen.setRO(ExcelUtils.getRO(testcaserow));
        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_FABRIC_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00"));
		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_VINIL_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.67 x 1.00"));

        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForEidt(wo);
		vehiclescreen = new VehicleScreen();
        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEELCOVER2_SUBSERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00"));
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.67 x 1.00"));
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.WHEELCOVER2_SUBSERVICE, "$45.00 x 1.00"));
        ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();

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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails (UtilConstants.CARPETREPAIR_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspdr.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspdr[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();

        OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		//ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
        myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new InvoiceInfoScreen();
		invoiceinfoscreen.clickFirstWO();
		vehiclescreen = new VehicleScreen();
        servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
        selectedservicescreen = servicesscreen.openServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedservicescreen.removeService();
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehiclePart("Right Mirror");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();

		servicesscreen.clickSave();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackeravisworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String workOrderNumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_FABRIC_SERVICE);
		servicesscreen.selectService("Tear/Burn >2\" (Fabric)");                                         
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDR6PANEL_SUBSERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(UtilConstants.PDR6PANEL_SUBSERVICE));
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(workOrderNumber);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses("Tear/Burn >2\" (Fabric)");
		for (String status : statuses)
			Assert.assertEquals(status, "Active");

		ordermonitorscreen.selectPanel("Interior Repair");
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses("Tear/Burn >2\" (Fabric)");
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");

		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickCreateInvoiceIconForTeamWO(workOrderNumber);
		Assert.assertTrue(teamworkordersscreen.isCreateInvoiceActivated(workOrderNumber));
		teamworkordersscreen.clickiCreateInvoiceButton();
        QuestionsScreen questionsscreen = new QuestionsScreen();
        questionsscreen = questionsscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, "AVIS Questions");
		questionsscreen.chooseAVISCode("Rental-921");
		final String invoicenumber = questionsscreen.getInvoiceNumber();
		questionsscreen.clickSaveAsFinal();
		teamworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO("170116");
		myinvoicesscreen.clickHomeButton();
		myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertTrue(myinvoicesscreen.isInvoiceHasInvoiceNumberIcon(invoicenumber));
		Assert.assertTrue(myinvoicesscreen.isInvoiceHasInvoiceSharedIcon(invoicenumber));
		Assert.assertEquals(myinvoicesscreen.getInvoicePrice(invoicenumber), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow1));
		String inspection1 = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));

        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow1)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.saveWizard();
		
		//Create second WO
		myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow2));
		String inspection2 = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));

        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.LEATHERREPAIR_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow2));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow2)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.saveWizard();
		
		
		//Create third WO
		myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow3));
		String inspection3 = vehiclescreen.getInspectionNumber();
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow3), ExcelUtils.getModel(testcaserow3), ExcelUtils.getYear(testcaserow3));

        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow3));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow3)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(inspection1);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDRVEHICLE_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickOnWO(inspection2);
		teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel("Interior Repair");
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.LEATHERREPAIR_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickOnWO(inspection3);
		teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(UtilConstants.PAINT_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.BLACKOUT_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();

		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection1);
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection2);
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection3);
		
		teamworkordersscreen.clickiCreateInvoiceButton();
		InvoiceInfoScreen invoiceinfoscreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		String wo = vehiclescreen.getInspectionNumber();
        ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.OTHER_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myworkordersscreen.copyVehicleForWorkOrder(wo, DentWizardWorkOrdersTypes.carmaxworkordertype);
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		servicesscreen.cancelWizard();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen =  myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow1));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));
		String wo = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
		
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myworkordersscreen.copyServicesForWorkOrder(wo, DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow2));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.BLACKOUT_SUBSERVICE,
				PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow1)) + " x 1.00"));
		servicesscreen.cancelWizard();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.servicedriveinspectiondertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		final String inspNumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.searchAvailableService("Scratch (Exterior)");
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Scratch (Exterior)");
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.checkPreexistingDamage();
		selectedservicescreen.saveSelectedServiceDetails();	
		servicesscreen.cancelSearchAvailableService();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesscreen.saveWizard();

		myinspectionsscreen.selectInspectionInTable(inspNumber);
		myinspectionsscreen.clickCreateWOButton();
        vehiclescreen = new VehicleScreen();
		String wonumber = vehiclescreen.getInspectionNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("1");
		ordersummaryscreen.saveWizard();

		myinspectionsscreen.showWorkOrdersForInspection(inspNumber);
		vehiclescreen = new VehicleScreen();
		Assert.assertEquals(vehiclescreen.getInspectionNumber(), wonumber);
		servicesscreen.clickCancelButton();

		myinspectionsscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
        myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen();
				invoiceinfoscreen.clickSaveAsFinal();
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19673:Test Car History feature", description = "Test Car History feature")
	public void testCarHistoryFeature()
			throws Exception {
		String tcname = "testCarHistoryFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreen.setRO(ExcelUtils.getRO(testcaserow));
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIORPLASTIC_SERVICE);
		servicesscreen.selectService(UtilConstants.SCRTCH_1_SECTPLSTC_SERVICE);

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.checkCreateInvoice();
		//ordersummaryscreen.clickSaveButton();
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		CarHistoryScreen carhistoryscreen = homescreen.clickCarHistoryButton();
		carhistoryscreen.searchCar("887340");
		String strtocompare = ExcelUtils.getYear(testcaserow) + ", " + ExcelUtils.getMake(testcaserow) + ", " + ExcelUtils.getModel(testcaserow);
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
		carhistoryscreen.clickFirstCarHistoryInTable();
		MyInvoicesScreen myinvoicesscreen = carhistoryscreen.clickCarHistoryInvoices();
		Assert.assertTrue(myinvoicesscreen.myInvoicesIsDisplayed());
		myinvoicesscreen.clickBackButton();

		carhistoryscreen.clickSwitchToWeb();
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
		carhistoryscreen.clickFirstCarHistoryInTable();
		TeamInvoicesScreen teamInvoicesScreen = carhistoryscreen.clickCarHistoryTeamInvoices();

		Assert.assertTrue(teamInvoicesScreen.teamInvoicesIsDisplayed());
		Assert.assertTrue(teamInvoicesScreen.isInvoiceExists(invoicenumber));
		teamInvoicesScreen.clickBackButton();
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

		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackerservicedriveworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Total Sale is required."));
		ordersummaryscreen.setTotalSale(totalsale);		
		ordersummaryscreen.clickSave();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19678:Test package pricing for read only items", description = "Test package pricing for read only items")
	public void testPackagePricingForReadOnlyItems()
			throws Exception {
		String tcname = "testPackagePricingForReadOnlyItems";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Fender", "Left Roof Rail", "Right Fender" };

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
		vehiclescreen.setRO(ExcelUtils.getRO(testcaserow));
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.INTERIOR_LEATHER_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CRT_2_RPR_LTHR_SUBSERVICE);
		Assert.assertEquals(selectedservicescreen.getAdjustmentsValue(), "-$14.48");
		selectedservicescreen.setServiceQuantityValue("3.00");
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		Assert.assertEquals(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)), servicesscreen.getTotalAmaunt());
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
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
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.checkCreateInvoice();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.routeinspectiontype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
			
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.PAINTPANEL_SUBSERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();	
		servicesscreen.saveWizard();;

		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspNumber), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		myinspectionsscreen.selectInspectionInTable(inspNumber);
		myinspectionsscreen.clickCreateWOButton();
		vehiclescreen = new VehicleScreen();
		String wonumber = vehiclescreen.getInspectionNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (int i = 0; i < vehicleparts.length; i++) {
			Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.PDRVEHICLE_SUBSERVICE , vehicleparts[i]
					, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)) + " x 1.00"));
		}
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(UtilConstants.PAINTPANEL_SUBSERVICE , vehiclepartspaint[i]
					, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)) + " x 1.00"));
		}
		servicesscreen.saveWizard();

		myinspectionsscreen.showWorkOrdersForInspection(inspNumber);
		vehiclescreen = new VehicleScreen();
		Assert.assertEquals(vehiclescreen.getInspectionNumber(), wonumber);
		servicesscreen.clickCancelButton();

		myinspectionsscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
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
		customersscreen.selectCustomerWithoutEditing(_customer);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), "-$28.50");
		selectedservicescreen.clickVehiclePartsCell();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.selectService(UtilConstants.WSANDBPANEL_SERVICE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		//ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();
		
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesscreen.searchAvailableService(UtilConstants.PAINTTOWHOOKCOVER_SUBSERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTTOWHOOKCOVER_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.clickServiceTypesButton();
				
		servicesscreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesscreen.searchAvailableService(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSave();
		myworkordersscreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonumber);
		teamworkordersscreen.selectEditWO();
		vehiclescreen = new VehicleScreen();
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertFalse(ordersummaryscreen.isApproveAndCreateInvoiceExists());
		ordersummaryscreen.saveWizard();

		teamworkordersscreen.clickCreateInvoiceIconForWO(wonumber);

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
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.enterpriseworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		EnterpriseBeforeDamageScreen enterprisebeforedamagescreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE);
		enterprisebeforedamagescreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'VIN' in section 'Enterprise Before Damage' should be answered."));
		enterprisebeforedamagescreen.setVINCapture();
		enterprisebeforedamagescreen.clickSave();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'License Plate' in section 'Enterprise Before Damage' should be answered."));
		enterprisebeforedamagescreen.setLicensePlateCapture();

		ServicesScreen servicesscreen = enterprisebeforedamagescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12633:Test successful email of pictures using Notes feature", description = "Test successful email of pictures using Notes feature")
	public void testSuccessfulEmailOfPicturesUsingNotesFeature()
			throws Exception {
		String tcname = "testSuccessfulEmailOfPicturesUsingNotesFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.avisworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
		String wo = vehiclescreen.getInspectionNumber();
		NotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickNotesButton();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 2);
		notesscreen.clickSaveButton();
		
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDR2PANEL_SUBSERVICE);
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		selectedservicescreen.clickNotesCell();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();
		selectedservicescreen.saveSelectedServiceDetails();
		EnterpriseBeforeDamageScreen enterprisebeforedamagescreen = servicesscreen.selectNextScreen(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE);
		enterprisebeforedamagescreen.setVINCapture();
		enterprisebeforedamagescreen.setLicensePlateCapture();

		OrderSummaryScreen ordersummaryscreen = enterprisebeforedamagescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForAddingNotes(wo);
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 3);
		notesscreen.clickSaveButton();
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wo);
		myworkordersscreen.clickInvoiceIcon();
		QuestionsScreen questionsscreen = new QuestionsScreen();
		questionsscreen = questionsscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, "AVIS Questions");
        questionsscreen.chooseAVISCode("Other-920");
        final String invoiceNumber = questionsscreen.getInvoiceNumber();
		questionsscreen.clickSaveAsFinal();
		
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoiceNumber);
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
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(DentWizardInspectionsTypes.economicalinspectiondertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehiclescreen.getInspectionNumber();
		NotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();
	
		ClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		QuestionsScreen questionsscreen =  claimscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_PHOTOS_SCREEN_CAPTION);
		questionsscreen.makeCaptureForQuestion("VIN");
		questionsscreen.makeCaptureForQuestion("Odometer");
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen();
		questionsscreen.makeCaptureForQuestion("License Plate Number");
		questionsscreen.makeCaptureForQuestion("Left Front of Vehicle");
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen();
		questionsscreen.makeCaptureForQuestion("Right Front of Vehicle");
		questionsscreen.makeCaptureForQuestion("Right Rear of Vehicle");
		questionsscreen.swipeScreenRight();
		questionsscreen = new QuestionsScreen();
		questionsscreen.makeCaptureForQuestion("Left Rear of Vehicle");
		questionsscreen = questionsscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsscreen.selectProperQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.CANADA, ExcelUtils.getOwnerZip(retailhaildatarow));

		PriceMatrixScreen pricematrix = questionsscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, UtilConstants.PRICE_MATRIX_SCREEN_CAPTION);
		pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		pricematrix.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		pricematrix.clickNotesButton();
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 2);
		notesscreen.clickSaveButton();

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("E-Coat");
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.clickNotesCell();
		notesscreen.addNotesCapture();
		notesscreen.clickSaveButton();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionInTable(inspNumber);
		MyInvoicesScreen myinvoicesscreen = new MyInvoicesScreen();
		myinvoicesscreen.sendEmail(UtilConstants.TEST_EMAIL);
        myinspectionsscreen = new MyInspectionsScreen();
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.auctionworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		String wo = vehiclescreen.getInspectionNumber();
		NotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.setNotes(firstnote);
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
		notesscreen.clickSaveButton();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
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
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.saveSelectedServiceDetails();
		//selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();

		QuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS);
		questionsscreen.chooseConsignor("Unknown Consignor/One Off-718");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForAddingNotes(wo);
		notesscreen.addNotesCapture();
		Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 2);
		notesscreen.clickSaveButton();
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wo);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(DentWizardInvoiceTypes.AUCTION_NO_DISCOUNT_INVOICE);
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
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
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("1");
        InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.checkCreateInvoice();
        Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
        myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoiceNumber);
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
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesscreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		//ordersummaryscreen.clickSaveButton();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedservicescreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedservicescreen.saveSelectedServiceDetails();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();
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
		customersscreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesscreen.selectService(UtilConstants.WHEEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));		
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.DING_SHIELD);
		invoiceinfoscreen.setPO(_po);
		invoiceinfoscreen.clickSaveAsFinal();		
		myworkordersscreen.clickHomeButton();
	}
}

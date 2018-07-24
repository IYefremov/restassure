package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.core.IOSHDDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.*;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSHDCalculationsTestCases extends BaseTestCase {
	
	private String regCode;
	public HomeScreen homescreen;
	
	@BeforeClass
	public void setUpSuite() throws Exception {
		mobilePlatform = MobilePlatform.IOS_HD;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		testGetDeviceRegistrationCode(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL(),
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		testRegisterationiOSDdevice();
		ExcelUtils.setDentWizardExcelFile();
	}
	
	public void testGetDeviceRegistrationCode(String backofficeurl,
			String userName, String userPassword) throws Exception {

		final String searchlicensecriteria = "Vit_Iph";

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

	public void testRegisterationiOSDdevice() throws Exception {
		AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
        DriverBuilder.getInstance().getAppiumDriver().removeApp(IOSHDDeviceInfo.getInstance().getDeviceBundleId());
        DriverBuilder.getInstance().getAppiumDriver().quit();
		AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
		SelectEnvironmentPopup selectenvscreen = new SelectEnvironmentPopup();
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("Dev Environment");
		loginscreen.registeriOSDevice(regCode);
		MainScreen mainscr = new MainScreen();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.setInsvoicesCustomLayoutOff();
		settingsscreen.clickHomeButton();
	}
	
	//Test Case 8553:Create inspection on the device with approval required
	@Test(testName = "Test Case 8553:Create inspection on the device with approval required", description = "Create Inspection On The Device With Approval Required")
	public void testCreateInspectionOnTheDeviceWithApprovalRequired()
				throws Exception {
		final String VIN = "TESTVINNO";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Black";

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen();
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
		VehicleScreen vehiclescreen = new VehicleScreen();
		final String inspNumber = vehiclescreen.getInspectionNumber();
		ClaimScreen claimScreen = vehiclescreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION, ClaimScreen.class);
		VisualInteriorScreen visualinteriorscreen = claimScreen.selectNextScreen(VisualInteriorScreen
					.getVisualInteriorCaption(), VisualInteriorScreen.class);
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(VisualInteriorScreen
					.getVisualExteriorCaption(), VisualInteriorScreen.class);
		visualinteriorscreen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
			
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		visualinteriorscreen.clickSave();
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Make is required"));
			
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehiclescreeen.saveWizard();
		myinspectionsscreen.selectInspectionForApprove(inspNumber);
		//testlogger.log(LogStatus.INFO, "After approve", testlogger.addScreenCapture(createScreenshot(, iOSLogger.loggerdir)));
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspNumber);
		approveinspscreen.clickApproveAfterSelection();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
        Assert.assertTrue(myinspectionsscreen.isInspectionApproved(inspNumber));
			
		myinspectionsscreen.clickHomeButton();
	}
	
	//Test Case 8435:Create Retail Inspection (HD Single page)	
	@Test(testName = "Test Case 8435:Create Retail Inspection (HD Single page)", description = "Create Retail Inspection")
	public void testCreateRetailInspection() throws Exception {
		final String VIN = "ZWERTYASDFEWSDRZG";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen();
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				"Default inspection type");
		VehicleScreen vehiclescreeen = new VehicleScreen();
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
		VisualInteriorScreen visualinteriorscreen = vehiclescreeen.selectNextScreen(VisualInteriorScreen
				.getVisualInteriorCaption(), VisualInteriorScreen.class);
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(VisualInteriorScreen
				.getVisualExteriorCaption(), VisualInteriorScreen.class);
		vehiclescreeen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	//Test Case 8434:Add Services to visual inspection
	@Test(testName = "Test Case 8434:Add Services to visual inspection", description = "Add Services To Visual Inspection")
	public void testAddServicesToVisualInspection() throws Exception {
		final String _inspectionprice = "275";

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen();
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickEditInspectionButton();
		VehicleScreen vehiclescreen = new VehicleScreen();
		final String inspNumber = vehiclescreen.getInspectionNumber();
		VisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(VisualInteriorScreen
				.getVisualInteriorCaption(), VisualInteriorScreen.class);
		Assert.assertTrue(visualinteriorscreen.isInteriorServicePresent("Miscellaneous"));
		Assert.assertTrue(visualinteriorscreen.isInteriorServicePresent("Price Adjustment"));
		Assert.assertTrue(visualinteriorscreen.isInteriorServicePresent("WHEEL REPAIR"));
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		visualinteriorscreen.tapInteriorWithCoords(1);
		visualinteriorscreen.tapInteriorWithCoords(2);
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		visualinteriorscreen.tapInteriorWithCoords(3);
		visualinteriorscreen.tapInteriorWithCoords(4);
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(VisualInteriorScreen
				.getVisualExteriorCaption(), VisualInteriorScreen.class);
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		visualinteriorscreen.tapExterior();
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionprice));	
		visualinteriorscreen.saveWizard();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspNumber), PricesCalculations.getPriceRepresentation(_inspectionprice));
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

		//resrtartApplication();	
		//MainScreen mainscreen = new MainScreen();
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickEditInspectionButton();
		VehicleScreen vehiclescreen = new VehicleScreen();
		VisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(VisualInteriorScreen
				.getVisualInteriorCaption(), VisualInteriorScreen.class);
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		visualinteriorscreen.tapInterior();
		visualinteriorscreen.tapInterior();
		visualinteriorscreen.setCarServiceQuantityValue(_quantity);
		visualinteriorscreen.saveCarServiceDetails();
		Assert.assertEquals(visualinteriorscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(_inspectionpricevisual));
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(VisualInteriorScreen
				.getVisualExteriorCaption(), VisualInteriorScreen.class);
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		visualinteriorscreen.tapExterior();
		visualinteriorscreen.setCarServiceQuantityValue(_quantityexterior);
		visualinteriorscreen.saveCarServiceDetails();

		visualinteriorscreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28580:WO: HD - If fee bundle item price policy = 'Panel' then it will be added once for many associated service instances with same vehicle part.", 
			description = "WO: HD - If fee bundle item price policy = 'Panel' then it will be added once for many associated service instances with same vehicle part.")
	public void testHDIfFeeBundleItemPricePolicyPanelThenItWillBeAddedOnceForManyAssociatedServiceInstancesWithSameVehiclePart()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", ServicesScreen.class);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$33.00");
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.setServicePriceValue("13");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$34.00");
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$67.00");
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	String wonumber28583 = null;
	
	@Test(testName="Test Case 28583:WO: HD - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity", 
			description = "WO: HD - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity")
	public void testHDIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_1()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_FOR_FEE_ITEM_IN_2_PACKS);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		wonumber28583 = vehiclescreeen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", ServicesScreen.class);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Service_in_2_fee_packs");
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$36.00");
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("1");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();				
	}
	
	@Test(testName="Test Case 28583:WO: HD - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity", 
			description = "WO: HD - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity")
	public void testHDIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_2()
			throws Exception {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.makeSearchPanelVisible();
		workorderspage.setSearchOrderNumber(wonumber28583);
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		
		WorkOrderInfoTabWebPage woinfotab = workorderspage.clickWorkOrderInTable(wonumber28583);
		Assert.assertTrue(woinfotab.isServicePriceCorrectForWorkOrder("$36.00"));
		Assert.assertTrue(woinfotab.isServiceSelectedForWorkOrder("Service_in_2_fee_packs"));
		Assert.assertTrue(woinfotab.isServiceSelectedForWorkOrder("Oksi_Test1"));
		Assert.assertTrue(woinfotab.isServiceSelectedForWorkOrder("Oksi_Test2"));
		woinfotab.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName="Test Case 28585:WO: HD - Verify that package price of fee bundle item is override the price of wholesale and retail prices", 
			description = "WO: HD - Verify that package price of fee bundle item is override the price of wholesale and retail prices")
	public void testHDVerifyThatPackagePriceOfFeeBundleItemIsOverrideThePriceOfWholesaleAndRetailPrices()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_FEE_PRICE_OVERRIDE);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", ServicesScreen.class);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Service_for_override");
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$27.00");
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28600:WO: HD - If fee bundle item price policy = 'Vehicle' then it will be added once for many associated service instances", 
			description = "WO: HD - If fee bundle item price policy = 'Vehicle' then it will be added once for many associated service instances.")
	public void testHDIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		ServicesScreen servicesscreen =vehiclescreeen.selectNextScreen("All Services", ServicesScreen.class);
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.setServicePriceValue("13");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$34.00");
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.setServicePriceValue("14");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();

		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$35.00");
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.setServicePriceValue("14");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Left Headlight");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();

		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$35.00");
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28601:WO: HD -  If fee bundle item price policy = 'Service' or 'Flat Fee' then it will be added to WO every time when associated service instance will add to WO.", 
			description = "WO: HD - If fee bundle item price policy = 'Service' or 'Flat Fee' then it will be added to WO every time when associated service instance will add to WO.")
	public void testHDIfFeeBundleItemPricePolicyServiceOrFlatFeeThenItWillBeAddedToWOEveryTimeWhenAssociatedServiceInstanceWillAddToWO()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", ServicesScreen.class);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Service");
		servicedetailsscreen.setServicePriceValue("10");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Hood");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();

		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$22.00");
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Service");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Hood");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$46.00");
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Service");
		servicedetailsscreen.setServicePriceValue("10");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Cowl, Other");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$68.00");
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28602:WO: HD - Verify that for Wholesale and Retail customers fee is added depends on the price accordingly to price of the fee bundle item", 
			description = "WO: HD - Verify that for Wholesale and Retail customers fee is added depends on the price accordingly to price of the fee bundle item")
	public void testHDVerifyThatForWholesaleAndRetailCustomersFeeIsAddedDependsOnThePriceAccordinglyToPriceOfTheFeeBundleItem()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", ServicesScreen.class);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$33.00");
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.setServicePriceValue("13");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$34.00");
		
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$67.00");
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	String wonumber29398 = null;
	
	@Test(testName="Test Case 29398:WO: HD - Verify that Fee Bundle services is calculated for additional matrix services", 
			description = "Verify that Fee Bundle services is calculated for additional matrix services")
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_1()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _pricematrix  = "Roof";
		final String price  = "54";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		wonumber29398 = vehiclescreeen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen("All Services", ServicesScreen.class);
		servicesscreen.selectService("SR_S5_Matrix_DE_TE");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix(_pricematrix);
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice(price);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		pricematrix.selectDiscaunt("SR_S5_Mt_Money");
		pricematrix.selectDiscaunt("SR_S5_Mt_Upcharge_25");
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartTotalPrice(), "$2,117.50");
		pricematrix.clickSaveButton();
		InspectionToolBar toolabar = new InspectionToolBar();
		Assert.assertEquals(toolabar.getInspectionTotalPrice(), "$2,127.50");
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();		
	}
	
	@Test(testName="Test Case 29398:WO: HD - Verify that Fee Bundle services is calculated for additional matrix services", 
			description = "Verify that Fee Bundle services is calculated for additional matrix services")
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_2()
			throws Exception {
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage wopage = operationspage.clickWorkOrdersLink();
		wopage.makeSearchPanelVisible();
		wopage.setSearchOrderNumber(wonumber29398);
		wopage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage woinfowebpage = wopage.clickWorkOrderInTable(wonumber29398);
		Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$2,127.50"));
		Assert.assertTrue(woinfowebpage.isServiceSelectedForWorkOrder("SR_S6_FeeBundle"));

		woinfowebpage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
		
	}
	
	@Test(testName="Test Case 30672:WO: HD - Verify that money value of some percentage service is rounds up after 0.095", 
			description = "HD - Verify that money value of some percentage service is rounds up after 0.095")
	public void testHDVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_095()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String srs1moneyprice  = "40";
		final String srs1moneyflatfeeprice  = "8.25";
		final String discountvalue = "6";
		final String servicetotalprice = "$2.90";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		SelectedServiceDetailsScreen selectedservicedetailsscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailsscreen.setServicePriceValue(srs1moneyprice);
		selectedservicedetailsscreen.clickVehiclePartsCell();
		selectedservicedetailsscreen.selectVehiclePart("Grill");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		
		selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedservicedetailsscreen.setServicePriceValue(srs1moneyflatfeeprice);
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		
		selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicedetailsscreen.setServicePriceValue(discountvalue);
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		
		selectedservicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(selectedservicedetailsscreen.getServiceDetailsPriceValue(), servicetotalprice);
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 30675:WO: HD - Verify that money value of some percentage service is rounds down after 0.003", 
			description = "HD - Verify that money value of some percentage service is rounds down after 0.003")
	public void testHDVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_003()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String srs1moneyprice  = "40";
		final String srs1moneyflatfeeprice  = "1.38";
		final String discountvalue = "6";
		final String servicetotalprice = "$2.48";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		SelectedServiceDetailsScreen selectedservicedetailsscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailsscreen.setServicePriceValue(srs1moneyprice);
		selectedservicedetailsscreen.clickVehiclePartsCell();
		selectedservicedetailsscreen.selectVehiclePart("Grill");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		
		selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedservicedetailsscreen.setServicePriceValue(srs1moneyflatfeeprice);
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		
		selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicedetailsscreen.setServicePriceValue(discountvalue);
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		
		selectedservicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(selectedservicedetailsscreen.getServiceDetailsPriceValue(), servicetotalprice);
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 30676:WO: HD - Verify that money value of some percentage service is rounds up after 0.005", 
			description = "HD - Verify that money value of some percentage service is rounds up after 0.005")
	public void testHDVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_005()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String srs1moneyprice  = "20";
		final String srs1moneyflatfeeprice  = "8.09";
		final String discountvalue = "6";
		final String servicetotalprice = "$1.69";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		SelectedServiceDetailsScreen selectedservicedetailsscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailsscreen.setServicePriceValue(srs1moneyprice);
		selectedservicedetailsscreen.clickVehiclePartsCell();
		selectedservicedetailsscreen.selectVehiclePart("Grill");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		
		selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedservicedetailsscreen.setServicePriceValue(srs1moneyflatfeeprice);
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		
		selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicedetailsscreen.setServicePriceValue(discountvalue);
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		
		selectedservicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(selectedservicedetailsscreen.getServiceDetailsPriceValue(), servicetotalprice);
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	String wonumber31498 = null;
	
	@Test(testName="Test Case 31498:WO: HD - Verify that amount is calculated and rounded correctly", 
			description = "Verify that amount is calculated and rounded correctly")
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_1()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String[] prices  = { "160", "105", "400", "195", "2400", "180", "160", "105", "300" };
		final String discount  = "-25";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		wonumber31498 = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		for (String _price : prices) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
			selectedservicedetailscreen.setServicePriceValue(_price);
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		selectedservicedetailscreen.setServicePriceValue(discount);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService("Tax discount");
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$3,153.94");
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31498:WO: HD - Verify that amount is calculated and rounded correctly", 
			description = "Verify that amount is calculated and rounded correctly")
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_2()
			throws Exception {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage wopage = operationspage.clickWorkOrdersLink();
		wopage.makeSearchPanelVisible();
		wopage.setSearchOrderNumber(wonumber31498);
		wopage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage woinfowebpage = wopage.clickWorkOrderInTable(wonumber31498);
		wopage.waitABit(5000);
		Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$3,153.94"));

		woinfowebpage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	String inspectionnumber32226 = null;
	
	@Test(testName = "Test Case 32226:Inspections: HD - Verify that inspection is saved as declined when all services are skipped or declined", 
			description = "Verify that inspection is saved as declined when all services are skipped or declined")
	public void testHDVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_1() throws Exception {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		final String[] pricematrixes = { "Hood", "ROOF" };
		
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection("Insp_for_auto_WO_line_appr_simple");
		QuestionsScreen questionsscreen = new QuestionsScreen();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");

		VehicleScreen vehiclescreeen = questionsscreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		inspectionnumber32226 = vehiclescreeen.getInspectionNumber();
		PriceMatrixScreen pricematrix = vehiclescreeen.selectNextScreen("Default", PriceMatrixScreen.class);
		for(String pricemrx : pricematrixes) {
			pricematrix.selectPriceMatrix(pricemrx);
			pricematrix.setSizeAndSeverity("DIME", "VERY LIGHT");
		}
		pricematrix.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber32226);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionnumber32226);
		approveinspscreen.clickSkipAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickCancelStatusReasonButton();


		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		//Helpers.acceptAlert();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32226:Inspections: HD - Verify that inspection is saved as declined when all services are skipped or declined", 
			description = "Verify that inspection is saved as declined when all services are skipped or declined")
	public void testHDVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_2() throws Exception {
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
		inspectionspage.selectSearchStatus("Declined");
		inspectionspage.searchInspectionByNumber(inspectionnumber32226);
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspectionnumber32226), "$0.00");
		Assert.assertEquals(inspectionspage.getInspectionReason(inspectionnumber32226), "Decline 1");
		Assert.assertEquals(inspectionspage.getInspectionStatus(inspectionnumber32226), "Declined");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	String inspectionnumber32286 = null;
	
	@Test(testName = "Test Case 32286:Inspections: HD - Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount", 
			description = "Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount")
	public void testHDVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_1() throws Exception {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection("Inspection_for_auto_WO_line_appr");
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		inspectionnumber32286 = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Left Rear Door");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
        servicesscreen = new ServicesScreen();
        servicesscreen.cancelSearchAvailableService();
        servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForAction(inspectionnumber32286);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionnumber32286);
		String servicetoapprove = iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)";
		String servicetodecline = iOSInternalProjectConstants.SR_S1_MONEY_PANEL + " (Left Rear Door)";
		String servicetoskip = iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE + " (Front Bumper)";
		approveinspscreen.selectInspectionServiceToApprove(servicetoapprove);
		approveinspscreen.selectInspectionServiceToDecline(servicetodecline);
		approveinspscreen.selectInspectionServiceToSkip(servicetoskip);
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32286:Inspections: HD - Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount", 
			description = "Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount")
	public void testHDVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_2() throws Exception {
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
		inspectionspage.searchInspectionByNumber(inspectionnumber32286);		
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspectionnumber32286), "$2,000.00");
		Assert.assertEquals(inspectionspage.getInspectionStatus(inspectionnumber32286), "Approved");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	String inspectionnumber32287 = null;
	
	@Test(testName = "Test Case 32287:Inspections: HD - Verify that amount of skipped/declined services are not calc go approved amount BO > inspections list > column ApprovedAmount", 
			description = "Verify that amount of skipped/declined services are not calc go approved amount BO > inspections list > column ApprovedAmount")
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_1() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		inspectionnumber32287 = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
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
        servicesscreen.cancelSearchAvailableService();
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForApprove(inspectionnumber32287);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionnumber32287);
		approveinspscreen.clickSkipAllServicesButton();		
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32287:Inspections: HD - Verify that amount of skipped/declined services are not calc go approved amount BO > inspections list > column ApprovedAmount", 
			description = "Verify that amount of skipped/declined services are not calc go approved amount BO > inspections list > column ApprovedAmount")
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_2() throws Exception {

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
		inspectionspage.selectSearchStatus("Declined");
		inspectionspage.searchInspectionByNumber(inspectionnumber32287);		
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspectionnumber32287), "$0.00");
		Assert.assertEquals(inspectionspage.getInspectionApprovedTotal(inspectionnumber32287), "$0.00");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName = "Test Case 33664:Inspections: HD - Verify that services are marked as strikethrough when exclude from total", 
			description = "Inspections: HD - Verify that services are marked as strikethrough when exclude from total")
	public void testInspectionVerifyServicesAreMarkedAsStrikethroughWhenExcludeFromTotal() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		VehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$1,050.00");
		for (int i = 0; i < 2; i++) {
			selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.selectVehiclePart("Right Door Mirror");
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$2,050.00");
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionnumber);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
		Assert.assertEquals(myinspectionsscreen.getInspectionApprovedPriceValue(inspectionnumber), "$2,050.00");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 38748:Inspections: HD - Verify that value selected on price matrix step is saved and shown during edit mode", 
			description = "Verify that value selected on price matrix step is saved and shown during edit mode")
	public void testHDVerifyThatValueSelectedOnPriceMatrixStepIsSavedAndShownDuringEditMode() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER,
				iOSInternalProjectConstants.INSP_TYPE_FOR_PRICE_MATRIX);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen = vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		PriceMatrixScreen pricematrix = vehiclescreeen.selectNextScreen("Price Matrix Zayats", PriceMatrixScreen.class);
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$55.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$55.00");

		ServicesScreen servicesscreen =pricematrix.selectNextScreen("Zayats test pack", ServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$60.00");

		pricematrix = servicesscreen.selectNextScreen("Hail Matrix", PriceMatrixScreen.class);
		pricematrix.selectPriceMatrix("ROOF");
		pricematrix.setSizeAndSeverity("DIME", "LIGHT");
		pricematrix.setPrice("123");
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$123.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$183.00");

		pricematrix = pricematrix.selectNextScreen("Hail Damage", PriceMatrixScreen.class);
		pricematrix.selectPriceMatrix("Grill");
		pricematrix.setSizeAndSeverity("DIME", "LIGHT");
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$10.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");

		QuestionsScreen questionsscreen = pricematrix.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		vehiclescreeen.saveWizard();
		for (int i = 0; i<2; i++) {
			myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
			pricematrix = pricematrix.selectNextScreen("Price Matrix Zayats", PriceMatrixScreen.class);
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$55.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
			pricematrix = pricematrix.selectNextScreen("Zayats test pack", PriceMatrixScreen.class);
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$5.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");

			pricematrix = pricematrix.selectNextScreen("Hail Matrix", PriceMatrixScreen.class);
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$123.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");

			pricematrix = pricematrix.selectNextScreen("Hail Damage", PriceMatrixScreen.class);
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$10.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
			vehiclescreeen.saveWizard();
		}
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 42184:WO: HD - Verify that message is shown total is over limitation 999999999.999", 
			description = "Verify that message is shown total is over limitation 999999999.999")
	public void testWOVerifyThatMessageIsShownTotalIsOverLimitation()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_SMOKE_TEST);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("98765");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSave();
		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Total amount of work order is huge."));
		Assert.assertTrue(alerttext.contains("Maximum allowed total amount is $999,999,999.99"));
		ordersummaryscreen.swipeScreenLeft();
		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		selectedservicedetailscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServiceQuantityValue("987");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42183:Invoices: HD - Verify that message is shown total is over limitation 999,999,999.999", 
			description = "Verify that message is shown total is over limitation 999,999,999.999")
	public void testInvoicesVerifyThatMessageIsShownTotalIsOverLimitation()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String wo1 = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("987");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();

		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String wo2 = vehiclescreeen.getInspectionNumber();
		questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("987");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		ordersummaryscreen = servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.approveWorkOrderWithoutSignature(wo1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wo1);
		myworkordersscreen.clickInvoiceIcon();
		
		myworkordersscreen.selectInvoiceType("Invoice_Custom1");
		QuestionsScreen questionsScreen = new QuestionsScreen();
		InvoiceInfoScreen invoiceinfoscreen = questionsScreen.selectNextScreen("Info", InvoiceInfoScreen.class);
		invoiceinfoscreen.setPO("123");
		String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		
		myworkordersscreen.approveWorkOrderWithoutSignature(wo2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickHomeButton();
		
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickEditPopup();
		questionsScreen = new QuestionsScreen();
		questionsScreen.selectNextScreen("Info", InvoiceInfoScreen.class);
		invoiceinfoscreen.addWorkOrder(wo2);
		invoiceinfoscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Total amount of invoice is huge."));
		Assert.assertTrue(alerttext.contains("Maximum allowed total amount is $999,999,999.99"));
		ordersummaryscreen.swipeScreenLeft();
		invoiceinfoscreen.cancelInvoice();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42182:Inspection: HD - Verify that message is shown total is over limitation 999999999.999", 
			description = "Inspection: Verify that message is shown total is over limitation 999999999.999")
	public void testInspectionVerifyThatMessageIsShownTotalIsOverLimitation() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_DRAFT_MODE);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("98765");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		
		Assert.assertTrue(alerttext.contains("Total amount of inspection is huge."));
		Assert.assertTrue(alerttext.contains("Maximum allowed total amount is $999,999,999.99"));
		selectedservicedetailscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServiceQuantityValue("987");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsDraft();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42372:Inspections: HD - Verify approved amount for Inspection created from SR", description = "Verify approved amount for Inspection created from SR")
	public void testVerifyApprovedAmountForInspectionCreatedFromSR() throws Exception {
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreeen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		QuestionsScreen questionsscreen = servicesscreen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.createInspectionFromServiceReques(srnumber, iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		vehiclescreeen = visualInteriorScreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		TeamInspectionsScreen teaminspectionsscreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectionnumber));
		teaminspectionsscreen.selectInspectionForApprove(inspectionnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionnumber);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Hood)");
		approveinspscreen.selectInspectionServiceToDecline(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
		teaminspectionsscreen = new TeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionApproved(inspectionnumber));
		Assert.assertEquals(teaminspectionsscreen.getFirstInspectionPriceValue(), "$2,000.00");
		Assert.assertEquals(teaminspectionsscreen.getFirstInspectionTotalPriceValue(), "$2,050.00");
		teaminspectionsscreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();		
	}
	
	String inspnumber47249 = "";
	
	@Test(testName = "Test Case 47249:Inspections: HD - Verify that on Price matrix step sub total value is shown correctly", 
			description = "Verify that on Price matrix step sub total value is shown correctly")
	public void testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly() throws Exception {
			
		final String VIN  = "1D7HW48NX6S507810";
		
		final String _pricematrix1 = "Hood";
		final String defprice = "100";
		final String timevalue = "20";
		
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		VehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);

		vehiclescreeen.setVIN(VIN);

		inspnumber47249 = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		PriceMatrixScreen pricematrix = questionsscreen.selectNextScreen("Default", PriceMatrixScreen.class);
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice(defprice);
		pricematrix.clickDiscaunt("SR_S5_Mt_Upcharge_20");
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.saveSelectedServiceDetails();
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$120.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$170.00");
		pricematrix.clickDiscaunt("SR_S5_Mt_Upcharge_25");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$145.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$195.00");
		pricematrix.clickDiscaunt("SR_S5_Mt_Discount_10");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$130.50");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$180.50");

		pricematrix = pricematrix.selectNextScreen("Matrix Labor", PriceMatrixScreen.class);
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.setSizeAndSeverity("DIME", "VERY LIGHT");
		pricematrix.setTime(timevalue);
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$100.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$280.50");
		pricematrix.clickDiscaunt("SR_S5_Mt_Discount_10");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$90.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$270.50");
		
		pricematrix.clickDiscaunt("SR_S5_Mt_Upcharge_25");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$112.50");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$293.00");
		pricematrix.saveWizard();
		
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspnumber47249), "$293.00");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 47257:WO: HD - Verify that on Price Matrix step sub total is shown correctly", 
			description = "Verify that on Price Matrix step sub total is shown correctly")
	public void testVerifyThatOnPriceMatrixStepSubTotalIsShownCorrectly() throws Exception {
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForApprove(inspnumber47249);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.approveInspectionApproveAllAndSignature(inspnumber47249);
		
		myinspectionsscreen.createWOFromInspection(inspnumber47249,
				iOSInternalProjectConstants.WO_SMOKE_MONITOR);
		VehicleScreen vehicleScreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehicleScreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$293.00");
        Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues("Dent Removal", "$112.50"));
        Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues("Dent Removal", "$130.50"));
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}
	
	String inspnumber48543 = "";
	
	@Test(testName = "Test Case 48543:Inspections: HD - Verify that part services with different configurations are correctly shown for inspection", 
			description = "Inspections: HD - Verify that part services with different configurations are correctly shown for inspection")
	public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_WITH_PART_SERVICES);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		inspnumber48543 = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.clickSaveAsDraft();
		
		myinspectionsscreen.selectInspectionForEdit(inspnumber48543);
		vehiclescreeen = new VehicleScreen();
		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Category");
		ServicePartPopup servicepartpopup = selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Engine");
		servicepartpopup.selectServicePartSubcategory("Filters");
		servicepartpopup.selectServicePartSubcategoryPart("Engine Oil Filter");
		servicepartpopup.selectServicePartSubcategoryPosition("Oil Cooler");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("2.35");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Driver Seat");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Name");
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.setServicePriceValue("2.5");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_None");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		servicepartpopup.selectServicePartCategory("Body");
		servicepartpopup.selectServicePartSubcategory("Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper");
		servicepartpopup.selectServicePartSubcategoryPosition("Rear Upper");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("5.09");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.answerQuestionCheckButton();		
		selectedservicescreen.saveSelectedServiceDetails();
		
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_several");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		servicepartpopup.selectCategory("Air and Fuel Delivery");
		servicepartpopup.selectServicePartSubcategory("Filters");
		servicepartpopup.selectServicePartSubcategoryPart("Air Filter");
		servicepartpopup.selectServicePartSubcategoryPosition("Inner");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("7");
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_SubCategory");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Body");
		Assert.assertEquals(servicepartpopup.getServicePartSubCategoryValue(), "Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper Air Shield");
		servicepartpopup.selectServicePartSubcategoryPosition("Front Lower");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("4.31");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		VisualInteriorScreen visualinteriorscreen = vehiclescreeen.selectNextScreen("Future Audi Car", VisualInteriorScreen.class);
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService("Detail");
		visualinteriorscreen.selectSubService("Oksi_Part_SubCategory");
		visualinteriorscreen.tapCarImage();
		selectedservicescreen = new SelectedServiceDetailsScreen();
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Body");
		Assert.assertEquals(servicepartpopup.getServicePartSubCategoryValue(), "Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper Assembly");
		servicepartpopup.selectServicePartSubcategoryPosition("Front");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("6.43");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		PriceMatrixScreen pricematrix = vehiclescreeen.selectNextScreen("PM_New", PriceMatrixScreen.class);
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice("10");
		pricematrix.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new SelectedServiceDetailsScreen();
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Engine");
		servicepartpopup.selectServicePartSubcategory("Electrical Connectors");
		servicepartpopup.selectServicePartSubcategoryPart("Engine Brake Relay Connector");
		servicepartpopup.saveSelectedServicePart();	
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Brake Relay Connector (N/A)");
		selectedservicescreen.setServicePriceValue("12.44");
		selectedservicescreen.saveSelectedServiceDetails();
		
		pricematrix.clickDiscaunt("Oksi_Part_Name");
		selectedservicescreen = new SelectedServiceDetailsScreen();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Belts and Cooling");
		Assert.assertEquals(servicepartpopup.getServicePartSubCategoryValue(), "Hardware");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("2.13");
		selectedservicescreen.saveSelectedServiceDetails();
		
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$24.57");
		
		pricematrix.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$82.13");
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$49.45");
		
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen.clickHomeButton();
	}	
	
	@Test(testName = "Test Case 48562:WO: HD - Verify that part services are copied from insp to order", 
			description = "WO: HD - Verify that part services are copied from insp to order")
	public void testWOVerifyThatPartServicesAreCopiedFromInspToOrder() throws Exception {
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber48543);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber48543);
		approveinspscreen.selectInspectionForApprove(inspnumber48543);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
	
		myinspectionsscreen.createWOFromInspection(inspnumber48543,
				"WO_with_part_service");
		VehicleScreen vehiclescreeen = new VehicleScreen();
		String wonumber = vehiclescreeen.getInspectionNumber();
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$77.13");
		OrderSummaryScreen ordersummaryscreen = vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		
		ordersummaryscreen.saveWizard();
		homescreen = myinspectionsscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$77.13");
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 48563:WO: HD - Verify that part services with different configurations are correctly shown for WO", 
			description = "WO: HD - Verify that part services with different configurations are correctly shown for WO")
	public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder("WO_with_part_service");
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Category");
		ServicePartPopup servicepartpopup = selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Engine");
		servicepartpopup.selectServicePartSubcategory("Filters");
		servicepartpopup.selectServicePartSubcategoryPart("Engine Oil Filter");
		servicepartpopup.selectServicePartSubcategoryPosition("Oil Cooler");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("2.35");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Driver Seat");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Name");
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.setServicePriceValue("2.5");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_None");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		servicepartpopup.selectServicePartCategory("Body");
		servicepartpopup.selectServicePartSubcategory("Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper");
		servicepartpopup.selectServicePartSubcategoryPosition("Rear Upper");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("5.09");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.answerQuestionCheckButton();		
		selectedservicescreen.saveSelectedServiceDetails();
		
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_several");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		servicepartpopup.selectCategory("Air and Fuel Delivery");
		servicepartpopup.selectServicePartSubcategory("Filters");
		servicepartpopup.selectServicePartSubcategoryPart("Air Filter");
		servicepartpopup.selectServicePartSubcategoryPosition("Inner");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("7");
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_SubCategory");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Body");
		Assert.assertEquals(servicepartpopup.getServicePartSubCategoryValue(), "Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper Air Shield");
		servicepartpopup.selectServicePartSubcategoryPosition("Front Lower");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("4.31");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrix("PM_New");	
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix("VP1 zayats");			
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice("10");
		pricematrix.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new SelectedServiceDetailsScreen();
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Engine");
		servicepartpopup.selectServicePartSubcategory("Electrical Connectors");
		servicepartpopup.selectServicePartSubcategoryPart("Engine Brake Relay Connector");
		servicepartpopup.saveSelectedServicePart();	
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Brake Relay Connector (N/A)");
		selectedservicescreen.setServicePriceValue("12.44");
		selectedservicescreen.saveSelectedServiceDetails();
		
		pricematrix.clickDiscaunt("Oksi_Part_Name");
		selectedservicescreen = new SelectedServiceDetailsScreen();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Belts and Cooling");
		Assert.assertEquals(servicepartpopup.getServicePartSubCategoryValue(), "Hardware");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("2.13");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartTotalPrice(), "$24.57");
		//InspectionToolBar toolaber = new InspectionToolBar();		
		//Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$24.57");
		
		pricematrix.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartTotalPrice(), "$49.45");
		pricematrix.clickSaveButton();
		InspectionToolBar toolaber = new InspectionToolBar();	
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$70.70");
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 45100:WO: HD - Verify rounding in calculation script with price matrix", 
			description = "WO: HD - Verify rounding in calculation script with price matrix")
	public void testWOVerifyRoundingInCalculationScriptWithPriceMatrix() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix("Grill");				
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice("975");
		pricematrix.clickDiscaunt(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		pricematrix.clickDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicedetailscreen.setServicePriceValue("192");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicedetailscreen.setServicePriceValue("-30");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$987.52");
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		ordersummaryscreen.clickSave();
		myworkordersscreen.selectInvoiceType("Invoice_Default_Template");
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen();
		invoiceinfoscreen.setPO("12345");
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSave();
		Helpers.acceptAlert();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();
		
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$987.52");
		homescreen = myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertEquals(myinvoicesscreen.getPriceForInvoice(invoicenumber), "$987.52");
		myinvoicesscreen.clickHomeButton();
	}
	
	String wonumber45224 = null;
	
	@Test(testName = "Test Case 45224:WO: HD - Verify calculation with price matrix Labor type", 
			description = "WO: HD - Verify calculation with price matrix Labor type")
	public void testWOVerifyCalculationWithPriceMatrixLaborType_1() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();		
		customersscreen.swtchToWholesaleMode();
		
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();		
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		wonumber45224 = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicedetailscreen.setServicePriceValue("90");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.changeAmountOfBundleService("100");
		//selectedservicebundlescreen.overrideBundleAmountValue("100");		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Tax discount");
		selectedservicedetailscreen.setServicePriceValue("10");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService("Matrix Service");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix("Test Matrix Labor");
		pricematrix.selectPriceMatrix("123");
		pricematrix.switchOffOption("PDR");
		pricematrix.setTime("100");
		pricematrix.clickDiscaunt(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		pricematrix.clickDiscaunt(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		ordersummaryscreen.clickSave();
		myworkordersscreen.selectInvoiceType("Invoice_AutoWorkListNet");
		questionsscreen = new QuestionsScreen();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		InvoiceInfoScreen invoiceinfoscreen = questionsscreen.selectNextScreen("Info", InvoiceInfoScreen.class);
		invoiceinfoscreen.setPO("12345");
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();
		
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber45224), "$542.68");
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 45224:WO: HD - Verify calculation with price matrix Labor type", 
			description = "WO: HD - Verify calculation with price matrix Labor type")
	public void testWOVerifyCalculationWithPriceMatrixLaborType_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		
		invoicespage.setSearchInvoiceNumber(wonumber45224);
		invoicespage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicespage.clickInvoicePrintPreview(wonumber45224);
		Assert.assertEquals(invoicespage.getPrintPreviewTestMartrixLaborServiceListValue("Matrix Service"), "$100.00");
		Assert.assertEquals(invoicespage.getPrintPreviewTestMartrixLaborServiceNetValue("Matrix Service"), "$112.50");
		Assert.assertEquals(invoicespage.getPrintPreviewTestMartrixLaborServiceListValue("Test service zayats"), "$100.00");
		Assert.assertEquals(invoicespage.getPrintPreviewTestMartrixLaborServiceNetValue("Test service zayats"), "$112.50");
		Assert.assertEquals(invoicespage.getPrintPreviewTestMartrixLaborServiceListValue("SR_Disc_20_Percent (25.000%)"), "$25.00");
		Assert.assertEquals(invoicespage.getPrintPreviewTestMartrixLaborServiceNetValue("SR_Disc_20_Percent (25.000%)"), "$28.13");
		invoicespage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	String invoicenumber42803 = null;
	
	@Test(testName = "Test Case 42803:Invoices: HD - Verify rounding money amount values", 
			description = "Invoices: HD - Verify rounding money amount values")
	public void testInvoicesVerifyRoundingMoneyAmountValues_1() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen =  questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		pricematrix.selectPriceMatrix("Grill");

		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice("100");
		pricematrix.clickDiscaunt(iOSInternalProjectConstants.OKSI_SERVICE_PP_FLAT_FEE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue("23");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue("10");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.answerQuestion2("A3");
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_BUNDLE_PP);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_BUNDLE_PP);
		selectedservicescreen.selectBundle(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE);
		selectedservicescreen.setServicePriceValue("25");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen.selectBundle(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicescreen.setServicePriceValue("5");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Grill");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.changeAmountOfBundleService("30");
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.searchAvailableService("Sales Tax");
		servicesscreen.openCustomServiceDetails("Sales Tax");
		selectedservicescreen.setServicePriceValue("3.8");
		selectedservicescreen.saveSelectedServiceDetails();
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$169.19");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickWorkOrderForApproveButton(wonumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.clickApproveButton();
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		myworkordersscreen.clickInvoiceIcon();
		myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen();
		invoicenumber42803 = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.setPO("12345");
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42803:Invoices: HD - Verify rounding money amount values", 
			description = "Invoices: HD - Verify rounding money amount values")
	public void testInvoicesVerifyRoundingMoneyAmountValues_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		
		invoicespage.setSearchInvoiceNumber(invoicenumber42803);
		invoicespage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicespage.clickInvoiceInternalTechInfo(invoicenumber42803);
		Assert.assertEquals(invoicespage.getTechInfoServicesTableServiceValue("<Tax>", "Test service price matrix"), "4.67000");
		Assert.assertEquals(invoicespage.getTechInfoServicesTableServiceValue("<Tax>", "Test service price matrix (Sev: None; Size: None)"), "3.79600");
		Assert.assertEquals(invoicespage.getTechInfoServicesTableServiceValue("<Tax>", "Oksi_Service_PP_Flat_Fee"), "0.87400");
		invoicespage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName = "Test Case 40463:Inspections: HD - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when appove inspection", 
			description = "Inspections: HD - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when appove inspection")
	public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";

		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		VehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("2000");
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Roof");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_BUNDLE);
		selectedservicedetailscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SR_S4_BUNDLE);
		selectedservicedetailscreen.setServicePriceValue("600");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen.setServicePriceValue("13");
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.answerQuestion2("A3");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$2,688.00");
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Roof)");
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S4_BUNDLE);
		approveinspscreen.selectInspectionServiceToSkip("3/4\" - Penny Size");
		approveinspscreen.selectInspectionServiceToSkip(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE + " (Back Glass)");
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		
		Assert.assertEquals(myinspectionsscreen.getInspectionTotalPriceValue(inspnumber), "$2,688.00");
		Assert.assertEquals(myinspectionsscreen.getInspectionApprovedPriceValue(inspnumber), "$2,650.00");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40466:Inspections: HD - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when decline inspection", 
			description = "Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when decline inspection")
	public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection() throws Exception {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_DRAFT_MODE);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_BUNDLE);
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.answerQuestion2("A3");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.clickSaveAsFinal();
			
		myinspectionsscreen.selectInspectionForApprove(inspectionnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();	
		approveinspscreen.selectInspectionForApprove(inspectionnumber);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		
		
		//approveinspscreen.clickApproveButton();
		myinspectionsscreen.clickFilterButton();
		myinspectionsscreen.clickStatusFilter();
		myinspectionsscreen.clickFilterStatus("Declined");
		//myinspectionsscreen.clickBackButton();
		myinspectionsscreen.clickCloseFilterDialogButton();
		myinspectionsscreen.clickSaveFilterDialogButton();
		Assert.assertEquals(myinspectionsscreen.getInspectionApprovedPriceValue(inspectionnumber), "$0.00");
		Assert.assertEquals(myinspectionsscreen.getInspectionTotalPriceValue(inspectionnumber), "$2,638.00");		
		myinspectionsscreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 38970:Inspections: Reqular - Verify that updated value for required service with 0 Price is saved when package grouped by panels", 
			description = "Inspections: Reqular - Verify that updated value for required service with 0 Price is saved when package grouped by panels")
	public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String serviceprice = "21";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_WITH_0_PRICE);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(),ServicesScreen.class);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE));
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();	
		approveinspscreen.selectInspectionForApprove(inspnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE), "$0.00");
		
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		vehiclescreeen = new VehicleScreen();
		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE);
		selectedservicescreen.setServicePriceValue(serviceprice);
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new ApproveInspectionsScreen();	
		approveinspscreen.selectInspectionForApprove(inspnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE), PricesCalculations.getPriceRepresentation(serviceprice));
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 35030:WO: HD - Verify that for bundle items price policy is applied", 
			description = "WO: HD - Verify that for bundle items price policy is applied")
	public void testWOVerifyThatForBundleItemsPricePolicyIsApplied() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.searchAvailableService("Oksi_Bundle_PP");
		servicesscreen.openCustomServiceDetails("Oksi_Bundle_PP");
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomBundleServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$13.00");
		
		selectedservicedetailscreen = servicesscreen.openCustomBundleServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE);
		selectedservicedetailscreen.setServiceQuantityValue("3");
		selectedservicedetailscreen.setServicePriceValue("10");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicedetailscreen.getVehiclePartValue().contains("Hood"));
		Assert.assertTrue(selectedservicedetailscreen.getVehiclePartValue().contains("Deck Lid"));	
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$73.00");

		selectedservicedetailscreen = servicesscreen.openCustomBundleServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.selectVehiclePart("Driver Seat");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$97.00");
		
		selectedservicedetailscreen = servicesscreen.selectBundleServiceDetails(iOSInternalProjectConstants.SR_S5_MT_DISCOUNT_10);
		//selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$87.30");		
		
		selectedservicedetailscreen = servicesscreen.openCustomBundleServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicedetailscreen.setServicePriceValue("10");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("You can add only one service 'Service_PP_Vehicle_not_multiple'"));
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$96.30");
		
		selectedservicedetailscreen = servicesscreen.openCustomBundleServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_LABOR);
		selectedservicedetailscreen.setServiceTimeValue("3");
		selectedservicedetailscreen.setServiceRateValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$163.80");

		
		
		selectedservicedetailscreen.changeAmountOfBundleService("163.80");
		selectedservicedetailscreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40541:WO: HD - Verify that for Sales Tax Service data is set from DB when create WO for customer with appropriate data", 
			description = "WO: HD - Verify that for Sales Tax Service data is set from DB when create WO for customer with appropriate data")
	public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String customer  = "Avalon";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addOrderWithSelectCustomer(customer, iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);;
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SALES_TAX);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("State Rate"), "%6.250");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("County Rate"), "%0.250");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("City Rate"), "%0.500");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("Special Rate"), "%2.500");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Sales Tax Override");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("State Rate"), "%6.250");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("County Rate"), "%0.250");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("City Rate"), "%0.500");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("Special Rate"), "%2.500");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.searchAvailableService("Test_service_with_QF_PP_Panel");
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Test_service_with_QF_PP_Panel");
		selectedservicedetailscreen.answerQuestion2("A3");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Dashboard");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new VehicleScreen();
		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		selectedservicedetailscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		Assert.assertFalse(selectedservicedetailscreen.isServiceDetailsFieldEditable("State Rate"));
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openServiceDetails("Sales Tax Override");
		selectedservicedetailscreen.setServiceDetailsFieldValue("State Rate", "6");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("State Rate"), "%6.000");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsTotalValue(), "%9.250");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 49680:Invoices: HD - Verify that tax rounding is correctly calculated", 
			description = "Invoices: HD - Verify that tax rounding is correctly calculated")
	public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("79");
		selectedservicedetailscreen.setServiceQuantityValue("4.1");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedservicedetailscreen.setServicePriceValue("20");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.searchServiceToSelect(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicedetailscreen.setServicePriceValue("15");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.searchServiceToSelect("Service_with_default_Tech");
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Service_with_default_Tech");
		selectedservicedetailscreen.setServicePriceValue("3205");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
				
		servicesscreen.searchServiceToSelect(iOSInternalProjectConstants.TAX_DISCOUNT);	
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TAX_DISCOUNT);
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$178.20");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.TAX_DISCOUNT);
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$178.20");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$3,742.10");
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 49915:Inspections: HD - Verify that tax is calc correctly from services with tax exempt YES/No", 
			description = "Inspections: HD - Verify that tax is calc correctly from services with tax exempt YES/No")
	public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo() throws Exception {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();

		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreeen = new VehicleScreen();
		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.searchAvailableService("Money_Tax_Exempt");
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Money_Tax_Exempt");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Driver Seat");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$100.00");
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SALES_TAX);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$100.00");
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$205.00");
		servicesscreen.cancelSearchAvailableService();

		servicesscreen.searchAvailableService("Money_Discount_Exempt");
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Money_Discount_Exempt");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$310.00");
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$289.50");
		servicesscreen.cancelSearchAvailableService();
		
		servicesscreen.searchAvailableService("Money_TE_DE");
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Money_TE_DE");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
			
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$389.50");
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.saveWizard();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspectionnumber), "$389.50");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 49945:HD - Verify that package price overrides client (retail or wholesale)", 
			description = "WO: Verify that package price overrides client (retail or wholesale)")
	public void testWOVerifyThatPackagePriceOverridesClient_RretailOrWholesale() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String wonumber1 = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber1);
		vehiclescreeen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService("Money_Pack_Price");
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$4.07");
		servicesscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String wonumber2 = vehiclescreeen.getInspectionNumber();

		ordersummaryscreen = vehiclescreeen.selectNextScreen(OrderSummaryScreen
				.getOrderSummaryScreenCaption(), OrderSummaryScreen.class);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber2);
		vehiclescreeen = new VehicleScreen();
		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService("Money_Pack_Price");
		toolaber = new InspectionToolBar();		
		servicesscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrder(wonumber2);	
		myworkordersscreen.clickChangeCustomerPopupMenu();
		customersscreen.swtchToRetailMode();
		//customersscreen.searchCustomer("Avalon");
		customersscreen.clickOnCustomer("Avalon");
		myworkordersscreen = new MyWorkOrdersScreen();
		Assert.assertFalse(myworkordersscreen.woExists(wonumber2));
		myworkordersscreen.clickHomeButton();
		
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.selectWorkOrderForEidt(wonumber2);
		vehiclescreeen = new VehicleScreen();
		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$12.00");
		servicesscreen.selectService("Money_Pack_Price");
		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$35.00");
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 50013:WO: HD - Verify that client and job overrides are working fine for WO", 
			description = "WO: Verify that client and job overrides are working fine for WO")
	public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkWithJobOrder(iOSInternalProjectConstants.WO_TYPE_WITH_JOB, "Job for test");
		VehicleScreen vehiclescreeen = new VehicleScreen();
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService("Money_client_override");
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$4.79");
		
		servicesscreen.selectService("Money_job_override");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$13.84");
		servicesscreen.saveWizard();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$13.84");
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 49856:SR: HD - Verify ALM flow when approve both inspections", 
			description = "SR: Verify ALM flow when approve both inspections")
	public void testSRVerifyALMFlowWhenApproveBothInspections()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreeen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		
		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isUndoCheckInActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen =  servicerequestsscreen.selectDetailsRequestAction();
		TeamInspectionsScreen teaminspectionsscreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();

		String inspnumber1 = teaminspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertTrue(teaminspectionsscreen.getInspectionTypeValue(inspnumber1).contains("ALM - Recon Inspection"));
		teaminspectionsscreen.selectInspectionForEdit(inspnumber1);
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("ALM - Statuses", QuestionsScreen.class);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("12");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();

		teaminspectionsscreen.clickBackServiceRequest();
		Helpers.waitABit(1000*60);
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		serviceRequestdetailsScreen.clickBackButton();
		Helpers.waitABit(1000*60);
		teaminspectionsscreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		
		Assert.assertEquals(teaminspectionsscreen.getNumberOfRowsInTeamInspectionsTable(), 2);
		String inspnumber2 = teaminspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertTrue(teaminspectionsscreen.getInspectionTypeValue(inspnumber2).contains( "ALM - Service Inspection"));
		teaminspectionsscreen.selectInspectionForEdit(inspnumber2);
		vehiclescreeen = new VehicleScreen();
		questionsscreen = vehiclescreeen.selectNextScreen("ALM - Statuses", QuestionsScreen.class);
		questionsscreen = new QuestionsScreen();
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();

		teaminspectionsscreen.selectInspectionForAction(inspnumber1);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();		

		approveinspscreen.selectInspectionForApprove(inspnumber1);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();

		teaminspectionsscreen.selectInspectionForAction(inspnumber2);
		selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber2);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();

		teaminspectionsscreen.clickBackServiceRequest();
		Helpers.waitABit(1000*60);
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		teaminspectionsscreen.clickBackServiceRequest();
		Helpers.waitABit(1000*60);
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		Assert.assertTrue(teaminspectionsscreen.isWOIconPresentForInspection(inspnumber1));
		teaminspectionsscreen.clickBackServiceRequest();

		serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		TeamWorkOrdersScreen teamwoscreen = serviceRequestdetailsScreen.clickServiceRequestSummaryOrdersButton();
		String wonumber = teamwoscreen.getFirstWorkOrderNumberValue();
		Assert.assertEquals(teamwoscreen.getPriceValueForWO(wonumber), "$37.00");
		teamwoscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new VehicleScreen();
		Assert.assertEquals(vehiclescreeen.getWorkOrderTypeValue(), "ALM - Recon Facility");
		
		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$12.00 x 1.00"));
        Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$25.00 x 1.00"));
		servicesscreen.cancelWizard();
		teamwoscreen.clickServiceRequestButton();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 49895:SR: HD - Verify ALM flow when approve one inspection and one decline", 
			description = "SR: Verify ALM flow when approve one inspection and one decline")
	public void testSRVerifyALMFlowWhenApproveOneInspectionAndOneDecline()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreeen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isUndoCheckInActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		TeamInspectionsScreen teaminspectionsscreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		String inspnumber1 = teaminspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertTrue(teaminspectionsscreen.getInspectionTypeValue(inspnumber1).contains("ALM - Recon Inspection"));
		teaminspectionsscreen.selectInspectionForEdit(inspnumber1);
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("ALM - Statuses", QuestionsScreen.class);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("12");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();

		teaminspectionsscreen.clickBackServiceRequest();
		Helpers.waitABit(1000*60);
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		teaminspectionsscreen.clickBackServiceRequest();
		Helpers.waitABit(1000*60);
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		
		Assert.assertEquals(teaminspectionsscreen.getNumberOfRowsInTeamInspectionsTable(), 2);
		String inspnumber2 = teaminspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertTrue(teaminspectionsscreen.getInspectionTypeValue(inspnumber2).contains("ALM - Service Inspection"));
		teaminspectionsscreen.selectInspectionForEdit(inspnumber2);
		questionsscreen = vehiclescreeen.selectNextScreen("ALM - Statuses", QuestionsScreen.class);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		teaminspectionsscreen.selectInspectionForAction(inspnumber1);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber1);		
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();

		teaminspectionsscreen.selectInspectionForAction(inspnumber2);
		selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber2);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();


		teaminspectionsscreen.clickBackServiceRequest();
		Helpers.waitABit(1000*60);
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		teaminspectionsscreen.clickBackServiceRequest();
		Helpers.waitABit(1000*60);
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		
		Assert.assertTrue(teaminspectionsscreen.isWOIconPresentForInspection(inspnumber1));
		teaminspectionsscreen.clickBackServiceRequest();

		TeamWorkOrdersScreen teamWorkOrdersScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryOrdersButton();
		String wonumber = teamWorkOrdersScreen.getFirstWorkOrderNumberValue();
		Assert.assertEquals(teamWorkOrdersScreen.getPriceValueForWO(wonumber), "$12.00");
		teamWorkOrdersScreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new VehicleScreen();
		Assert.assertEquals(vehiclescreeen.getWorkOrderTypeValue(), "ALM - Recon Facility");

		servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
        Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$12.00 x 1.00"));
		servicesscreen.cancelWizard();
		teamWorkOrdersScreen.clickServiceRequestButton();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 49896:SR: HD - Verify ALM flow when decline both inspections", 
			description = "SR: Verify ALM flow when decline both inspections")
	public void testSRVerifyALMFlowWhenDeclineBothInspections()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreeen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.saveWizard();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isUndoCheckInActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);

		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		TeamInspectionsScreen  teamInspectionsScreen  = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		String inspnumber1 = teamInspectionsScreen.getFirstInspectionNumberValue();
		Assert.assertTrue(teamInspectionsScreen.getInspectionTypeValue(inspnumber1).contains("ALM - Recon Inspection"));
		teamInspectionsScreen.selectInspectionForEdit(inspnumber1);
		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("ALM - Statuses", QuestionsScreen.class);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("12");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();

		teamInspectionsScreen.clickBackServiceRequest();
		Helpers.waitABit(1000*60);
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		teamInspectionsScreen.clickBackServiceRequest();
		Helpers.waitABit(1000*60);
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		
		Assert.assertEquals(teamInspectionsScreen.getNumberOfRowsInTeamInspectionsTable(), 2);
		String inspnumber2 = teamInspectionsScreen.getFirstInspectionNumberValue();
		Assert.assertTrue(teamInspectionsScreen.getInspectionTypeValue(inspnumber2).contains("ALM - Service Inspection"));
		teamInspectionsScreen.selectInspectionForEdit(inspnumber2);
		questionsscreen = vehiclescreeen.selectNextScreen("ALM - Statuses", QuestionsScreen.class);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		teamInspectionsScreen.selectInspectionForAction(inspnumber1);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber1);
		approveinspscreen.clickDeclineAllServicesButton();	
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 2");
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();


		teamInspectionsScreen.selectInspectionForAction(inspnumber2);
		selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber2);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();


		teamInspectionsScreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));		
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
		ServiceRequestsListWebPage srlist = operationspage.clickNewServiceRequestList();
		srlist.makeSearchPanelVisible();
		srlist.setSearchFreeText(srnumber);
		Assert.assertEquals(srlist.getFirstServiceRequestStatus(), "Request Rejected");
		Assert.assertEquals(srlist.getFirstServiceRequestPhase(), "D");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName = "Test Case 32190:Inspections: HD - Verify that for Declined/Skipped services appropriate icon is shown after approval,"
			+ "Test Case 32191:Inspections: HD Verify that declined/skipped services are marked with text (Declined) or (Skipped) accordingly on Inspection Summary screen", 
			description = "Inspections: Verify that for Declined/Skipped services appropriate icon is shown after approval,"
					+ "Inspections: Verify that declined/skipped services are marked with text (Declined) or (Skipped) accordingly on Inspection Summary screen")
	public void testInspectionsVerifyThatForDeclinedSkippedaServicesAppropriateIconIsShownAfterApproval() throws Exception {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addInspection(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualinteriorScreen = new VisualInteriorScreen();
		VehicleScreen vehiclescreeen = visualinteriorScreen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.selectNextScreen(VehicleScreen.getVehicleScreenCaption(), VehicleScreen.class);
		vehiclescreeen.setVIN(VIN);
		String inspnumber = vehiclescreeen.getInspectionNumber();

		QuestionsScreen questionsscreen = vehiclescreeen.selectNextScreen("Zayats Section1", QuestionsScreen.class);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreeen.selectNextScreen(ServicesScreen.getServicesScreenCaption(), ServicesScreen.class);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.TAX_DISCOUNT);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		PriceMatrixScreen pricematrix = vehiclescreeen.selectNextScreen("Default", PriceMatrixScreen.class);
		pricematrix.selectPriceMatrix("Hood");
		pricematrix.setSizeAndSeverity("DIME", "HEAVY");
		VisualInteriorScreen visualinteriorscreen = pricematrix.selectNextScreen("Future Sport Car", VisualInteriorScreen.class);
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		visualinteriorscreen.tapInteriorWithCoords(1);
		visualinteriorscreen.tapInteriorWithCoords(2);
		pricematrix = visualinteriorscreen.selectNextScreen("Matrix Labor", PriceMatrixScreen.class);
		pricematrix.selectPriceMatrix("Back Glass");
		pricematrix.switchOffOption("PDR");
		pricematrix.setTime("34");
		//pricematrix.clickSaveButton();
		pricematrix.saveWizard();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber);
		approveinspscreen.clickDeclineAllServicesButton();	
		approveinspscreen.selectInspectionServiceToApproveByIndex(iOSInternalProjectConstants.WHEEL_SERVICE, 0);
		//approveinspscreen.selectInspectionServiceToApproveByIndex(iOSInternalProjectConstants.WHEEL_SERVICE, 2);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		approveinspscreen.selectInspectionServiceToApproveByIndex(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE, 0);
		
		
		approveinspscreen.selectInspectionServiceToSkipByIndex(iOSInternalProjectConstants.WHEEL_SERVICE, 1);
		approveinspscreen.selectInspectionServiceToSkip(iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)");
		approveinspscreen.selectInspectionServiceToSkipByIndex(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE, 1);		
		approveinspscreen.selectInspectionServiceToSkip(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
	
		approveinspscreen.clickSaveButton();
		//approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		visualinteriorScreen =new VisualInteriorScreen();
		servicesscreen = visualinteriorScreen.selectNextScreen("Test_pack_for_calc", ServicesScreen.class);
		Assert.assertTrue(servicesscreen.isServiceApproved(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.TAX_DISCOUNT));
		/*servicesscreen.cancelOrder();
		myinspectionsscreen.clickOnInspection(inspnumber);
		EmailScreen mailscreen = myinspectionsscreen.clickSendEmail();
		mailscreen.sendInvoiceOnEmailAddress("test.cyberiansoft@gmail.com");
		myinspectionsscreen.clickHomeButton();
		.closeApp();
		Thread.sleep(30*1000*1);
		boolean search = false;
		final String invpoicereportfilenname = inspnumber + ".pdf";
		for (int i= 0; i < 7; i++) {
			if (!MailChecker.searchEmailAndGetAttachment("test.cyberiansoft@gmail.com", "t!y@hGk8", "Estimate #" + inspnumber + " from Recon Pro Development QA", "ReconPro@cyberianconcepts.com", invpoicereportfilenname)) {
				Thread.sleep(30*1000); 
			} else {
				
				search = true;
				break;
			}
		}
		if (search) {
			File pdfdoc = new File(invpoicereportfilenname);
			String pdftext = PDFReader.getPDFText(pdfdoc);
			Assert.assertTrue(pdftext.contains("(Declined) Disc_Ex_Service1 ($50.00) 1.00"));
			Assert.assertTrue(pdftext.contains("(Declined) Test service zayats ($5.00) (123) 1.00"));
			Assert.assertTrue(pdftext.contains("(Declined) Test service zayats ($5.00) (Sunroof) 1.00"));
			Assert.assertTrue(pdftext.contains("Bundle1_Disc_Ex 1.00"));
			Assert.assertTrue(pdftext.contains("(Declined) Discount 5-10% ($-346.00) (10.000%)"));
			Assert.assertTrue(pdftext.contains("(Declined) SR_S1_Money ($2000.00) (Grill) 1.00"));
			Assert.assertTrue(pdftext.contains("(Skipped) SR_S1_Money_Panel ($1000.00) (Grill) 1.00"));
			Assert.assertTrue(pdftext.contains("(Declined) Tax discount ($165.70) 5.000%"));
			Assert.assertTrue(pdftext.contains("Dent Removal 1.00"));
			Assert.assertTrue(pdftext.contains("Wheel 1.00"));
			Assert.assertTrue(pdftext.contains("(Declined) Wheel ($70.00) 1.00"));
			Assert.assertTrue(pdftext.contains("(Skipped) Wheel ($70.00) 1.00"));
			Assert.assertTrue(pdftext.contains("Wheel 1.00"));
			Assert.assertTrue(pdftext.contains("(Skipped) Dent Removal ($170.00) 1.00"));
		}*/
        DriverBuilder.getInstance().getAppiumDriver().launchApp();
		MainScreen mainscr = new MainScreen();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
	
	@Test(testName = "Jusr wait 2 hours", description = "Jusr wait 2 hours")
	public void testWaitTestCase() {
		int second = 1000;
		int minute = second*60;
		int hour = minute*60;
		
		Helpers.waitABit(3*hour);
	}
	
	//@AfterMethod
	public void closeBrowser() {
		if (webdriver != null)
			webdriver.quit();
	}
}

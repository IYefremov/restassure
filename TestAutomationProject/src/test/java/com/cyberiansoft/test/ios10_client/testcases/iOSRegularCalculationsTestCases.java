package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.EmailScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.*;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class iOSRegularCalculationsTestCases extends BaseTestCase {
	
	private String regCode;
	private RegularHomeScreen homescreen;
	
	@BeforeClass
	public void setUpSuite() throws Exception {
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		testGetDeviceRegistrationCode(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL(),
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		testRegisterationiOSDdevice();
		ExcelUtils.setDentWizardExcelFile();
	}
	
	public void testGetDeviceRegistrationCode(String backofficeurl,
			String userName, String userPassword) throws Exception {

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);

		ActiveDevicesWebPage devicespage = PageFactory.initElements(webdriver,
				ActiveDevicesWebPage.class);

		devicespage.setSearchCriteriaByName("AutomationCalculations_Regular");
		regCode = devicespage.getFirstRegCodeInTable();

		DriverBuilder.getInstance().getDriver().quit();
	}

	public void testRegisterationiOSDdevice() throws Exception {
		appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
		appiumdriver.removeApp(IOSRegularDeviceInfo.getInstance().getDeviceBundleId());
		appiumdriver.quit();
		appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
		RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen(appiumdriver);
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("Dev Environment");
		loginscreen.registeriOSDevice(regCode);
		RegularMainScreen mainscr = new RegularMainScreen(appiumdriver);
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
	
	//Test Case 8553:Create inspection on the device with approval required
	@Test(testName = "Test Case 8553:Create inspection on the device with approval required", description = "Create Inspection On The Device With Approval Required")
	public void testCreateInspectionOnTheDeviceWithApprovalRequired()
				throws Exception {
		final String VIN = "TESTVINNO";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Black";
			
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_NOTLA_TS_INSPTYPE);
		myinspectionsscreen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
		myinspectionsscreen.selectNextScreen(RegularVisualInteriorScreen
					.getVisualInteriorCaption());
		myinspectionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickSaveButton();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
			
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.clickSaveButton();
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Make is required"));
			
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.clickSaveButton();

		myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		//testlogger.log(LogStatus.INFO, "After approve", testlogger.addScreenCapture(createScreenshot(appiumdriver, iOSLogger.loggerdir)));
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickApproveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inspnumber));
			
		myinspectionsscreen.clickHomeButton();
	}

	String inspection8434 = "";
	//Test Case 8435:Create Retail Inspection (HD Single page)
	//Test Case 8434:Add Services to visual inspection
	@Test(testName = "Test Case 8434:Add Services to visual inspection", description = "Add Services To Visual Inspection")
	public void testAddServicesToVisualInspection() throws Exception {
		final String VIN = "ZWERTYASDFEWSDRZG";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";
		final String _inspectionprice = "275";
			
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		RegularVehicleScreen vehiclescreeen = myinspectionsscreen.selectDefaultInspectionType();
		vehiclescreeen.clickSaveButton();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		vehiclescreeen.setVIN(VIN);
		inspection8434 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.clickSaveButton();
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Make is required"));
			
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		vehiclescreeen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehiclescreeen.selectNextScreen(RegularVisualInteriorScreen
					.getVisualInteriorCaption());
		vehiclescreeen.selectNextScreen(RegularVisualInteriorScreen
					.getVisualExteriorCaption());
		
		vehiclescreeen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForEdit(inspection8434);
		myinspectionsscreen.selectNextScreen(RegularVisualInteriorScreen
					.getVisualInteriorCaption());
		RegularVisualInteriorScreen visualinteriorscreen = new RegularVisualInteriorScreen(appiumdriver);
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
		visualinteriorscreen.selectNextScreen(RegularVisualInteriorScreen
					.getVisualExteriorCaption());
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		RegularVisualInteriorScreen.tapExterior();
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.clickServicesBackButton();
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionprice));
		visualinteriorscreen.clickSaveButton();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspection8434), PricesCalculations.getPriceRepresentation(_inspectionprice));
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 8433:Change Quantity of services in Visual Inspection", description = "Change Quantity Of Services In Visual Inspection")
	public void testChangeQuantityOfServicesInVisualInspection()
			throws Exception {
		final String _quantity = "3.00";
		final String _quantityexterior = "2.00";
		final String _inspectionpricevisual = "275";
		final String _inspectionprice = "325";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForEdit(inspection8434);
		myinspectionsscreen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualInteriorCaption());
		RegularVisualInteriorScreen visualinteriorscreen = new RegularVisualInteriorScreen(appiumdriver);
		/*visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);*/
		RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
		visualinteriorscreen.setCarServiceQuantityValue(_quantity);
		visualinteriorscreen.saveCarServiceDetails();
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionpricevisual));
		visualinteriorscreen.selectNextScreen(RegularVisualInteriorScreen
				.getVisualExteriorCaption());
		/*visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);*/
		RegularVisualInteriorScreen.tapExterior();
		visualinteriorscreen.setCarServiceQuantityValue(_quantityexterior);
		visualinteriorscreen.saveCarServiceDetails();
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionprice));

		visualinteriorscreen.clickSaveButton();
		myinspectionsscreen.clickHomeButton();

	}
	
	@Test(testName="Test Case 28580:WO: Regular - If fee bundle item price policy = 'Panel' then it will be added once for many associated service instances with same vehicle part.", 
			description = "WO: Regular - If fee bundle item price policy = 'Panel' then it will be added once for many associated service instances with same vehicle part.")
	public void testRegularIfFeeBundleItemPricePolicyPanelThenItWillBeAddedOnceForManyAssociatedServiceInstancesWithSameVehiclePart()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("All Services");
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$33.00");
		
		servicesscreen.clickToolButton();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("13");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$34.00");
		
		servicesscreen.clickToolButton();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$67.00");
		
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	String wonumber28583 = null;

	@Test(testName="Test Case 28583:WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity", 
			description = "WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity")
	public void testRegularIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_1()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FOR_FEE_ITEM_IN_2_PACKS);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		wonumber28583 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("All Services");
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Service_in_2_fee_packs");
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$36.00");
		servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("1");
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
		
	}
	
	@Test(testName="Test Case 28583:WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity", 
			description = "WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity")
	public void testRegularIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_2()
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
		wopage.setSearchOrderNumber(wonumber28583);
		wopage.unselectInvoiceFromDeviceCheckbox();
		wopage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		
		WorkOrderInfoTabWebPage woinfotab = wopage.clickWorkOrderInTable(wonumber28583);
		Assert.assertTrue(woinfotab.isServicePriceCorrectForWorkOrder("$36.00"));
		Assert.assertTrue(woinfotab.isServiceSelectedForWorkOrder("Service_in_2_fee_packs"));
		Assert.assertTrue(woinfotab.isServiceSelectedForWorkOrder("Oksi_Test1"));
		Assert.assertTrue(woinfotab.isServiceSelectedForWorkOrder("Oksi_Test2"));
		woinfotab.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	
	@Test(testName="Test Case 28585:WO: Regular - Verify that package price of fee bundle item is override the price of wholesale and retail prices", 
			description = "WO: Regular - Verify that package price of fee bundle item is override the price of wholesale and retail prices")
	public void testRegularVerifyThatPackagePriceOfFeeBundleItemIsOverrideThePriceOfWholesaleAndRetailPrices()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_FEE_PRICE_OVERRIDE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("All Services");
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Service_for_override");
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$27.00");
		
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28600:WO: Regular - If fee bundle item price policy = 'Vehicle' then it will be added once for many associated service instances", 
			description = "WO: Regular - If fee bundle item price policy = 'Vehicle' then it will be added once for many associated service instances")
	public void testRegularIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("All Services");
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.setServicePriceValue("13");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$34.00");
		
		servicesscreen.clickToolButton();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.setServicePriceValue("14");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$35.00");
		
		servicesscreen.clickToolButton();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.setServicePriceValue("14");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Left Headlight");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$35.00");
		
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28601:WO: Regular -  If fee bundle item price policy = 'Service' or 'Flat Fee' then it will be added to WO every time when associated service instance will add to WO.", 
			description = "WO: Regular -  If fee bundle item price policy = 'Service' or 'Flat Fee' then it will be added to WO every time when associated service instance will add to WO.")
	public void testRegularIfFeeBundleItemPricePolicyServiceOrFlatFeeThenItWillBeAddedToWOEveryTimeWhenAssociatedServiceInstanceWillAddToWO()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("All Services");
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Service");
		servicedetailsscreen.setServicePriceValue("10");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Hood");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$22.00");
		
		servicesscreen.clickToolButton();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Service");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Hood");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$46.00");
		
		servicesscreen.clickToolButton();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Service");
		servicedetailsscreen.setServicePriceValue("10");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Cowl, Other");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$68.00");
		
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28602:WO: Regular - Verify that for Wholesale and Retail customers fee is added depends on the price accordingly to price of the fee bundle item", 
			description = "WO: Regular - Verify that for Wholesale and Retail customers fee is added depends on the price accordingly to price of the fee bundle item")
	public void testRegularVerifyThatForWholesaleAndRetailCustomersFeeIsAddedDependsOnThePriceAccordinglyToPriceOfTheFeeBundleItem()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		vehiclescreeen.selectNextScreen("All Services");
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$33.00");
		
		servicesscreen.clickToolButton();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("13");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$34.00");
		
		servicesscreen.clickToolButton();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$67.00");
		
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	String wonumber29398 = null;
	
	@Test(testName="Test Case 29398:WO: Regular - Verify that Fee Bundle services is calculated for additional matrix services", 
			description = "Verify that Fee Bundle services is calculated for additional matrix services")
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_1()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _pricematrix  = "Roof";
		final String price  = "54";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		wonumber29398 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("All Services");
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.selectSubService("SR_S5_Matrix_DE_TE");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(_pricematrix);
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice(price);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		pricematrix.selectDiscaunt("SR_S5_Mt_Money");
		pricematrix.selectDiscaunt("SR_S5_Mt_Upcharge_25");
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartSubTotalPrice(), "$2,117.50");
		pricematrix.clickSaveButton();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$2,117.50");
		pricematrix.clickServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$2,127.50");
		servicesscreen.saveWorkOrder();
		myworkordersscreen.clickHomeButton();
		
	}
	
	@Test(testName="Test Case 29398:WO: Regular - Verify that Fee Bundle services is calculated for additional matrix services", 
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
	
	@Test(testName="Test Case 30672:WO: Regular - Verify that money value of some percentage service is rounds up after 0.095", 
			description = "Regular - Verify that money value of some percentage service is rounds up after 0.095")
	public void testRegularVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_095()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String srs1moneyprice  = "40";
		final String srs1moneyflatfeeprice  = "8.25";
		final String discountvalue = "6";
		final String servicetotalprice = "$2.90";
		
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
			
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		//servicesscreen.clickToolButton();
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S1_MONEY);
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		regularselectedservicedetailsscreen.setServicePriceValue(srs1moneyprice);
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Grill");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		regularselectedservicedetailsscreen.setServicePriceValue(srs1moneyflatfeeprice);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		regularselectedservicedetailsscreen.setServicePriceValue(discountvalue);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(regularselectedservicedetailsscreen.getServiceDetailsPriceValue(), servicetotalprice);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 30675:WO: Regular - Verify that money value of some percentage service is rounds down after 0.003", 
			description = "Regular - Verify that money value of some percentage service is rounds down after 0.003")
	public void testRegularVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_003()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String srs1moneyprice  = "40";
		final String srs1moneyflatfeeprice  = "1.38";
		final String discountvalue = "6";
		final String servicetotalprice = "$2.48";
		
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
			
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		//servicesscreen.clickToolButton();
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S1_MONEY);
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		regularselectedservicedetailsscreen.setServicePriceValue(srs1moneyprice);
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Grill");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		regularselectedservicedetailsscreen.setServicePriceValue(srs1moneyflatfeeprice);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		regularselectedservicedetailsscreen.setServicePriceValue(discountvalue);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(regularselectedservicedetailsscreen.getServiceDetailsPriceValue(), servicetotalprice);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 30676:WO: Regular - Verify that money value of some percentage service is rounds up after 0.005", 
			description = "Regular - Verify that money value of some percentage service is rounds up after 0.005")
	public void testRegularVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_005()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String srs1moneyprice  = "20";
		final String srs1moneyflatfeeprice  = "8.09";
		final String discountvalue = "6";
		final String servicetotalprice = "$1.69";
		
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
			
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		//servicesscreen.clickToolButton();
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S1_MONEY);
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		regularselectedservicedetailsscreen.setServicePriceValue(srs1moneyprice);
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Grill");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		regularselectedservicedetailsscreen.setServicePriceValue(srs1moneyflatfeeprice);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		regularselectedservicedetailsscreen.setServicePriceValue(discountvalue);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(regularselectedservicedetailsscreen.getServiceDetailsPriceValue(), servicetotalprice);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	String wonumber31498 = null;
	
	@Test(testName="Test Case 31498:WO: Regular - Verify that amount is calculated and rounded correctly", 
			description = "Verify that amount is calculated and rounded correctly")
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_1()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String[] prices  = { "160", "105", "400", "195", "2400", "180", "160", "105", "300" };
		final String discount  = "-25";
		
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
		wonumber31498 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		for (String _price : prices) {
			servicesscreen.clickToolButton();
			RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
			selectedservicedetailscreen.setServicePriceValue(_price);
			selectedservicedetailscreen.saveSelectedServiceDetails();
			servicesscreen.clickAddServicesButton();
		}
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		selectedservicedetailscreen.setServicePriceValue(discount);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.TAX_DISCOUNT);
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$3,153.94");
		questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();

	}
	
	@Test(testName="Test Case 31498:WO: Regular - Verify that amount is calculated and rounded correctly", 
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
		Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$3,153.94"));

		woinfowebpage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	String inspectionnumber32226 = null;
	
	@Test(testName = "Test Case 32226:Inspections: Regular - Verify that inspection is saved as declined when all services are skipped or declined", 
			description = "Verify that inspection is saved as declined when all services are skipped or declined")
	public void testRegularVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_1() throws Exception {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		final String[] pricematrixes = { "Hood", "ROOF" };
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Insp_for_auto_WO_line_appr_simple");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		questionsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		inspectionnumber32226 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Default");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		for(String pricemrx : pricematrixes) {
			pricematrix.selectPriceMatrix(pricemrx);
			pricematrix.setSizeAndSeverity("DIME", "VERY LIGHT");
			pricematrix.clickSaveButton();
		}
		pricematrix.clickSaveButton();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber32226);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspectionnumber32226);
		approveinspscreen.clickSkipAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickCancelStatusReasonButton();
		
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();

	}
	
	@Test(testName = "Test Case 32226:Inspections: Regular - Verify that inspection is saved as declined when all services are skipped or declined", 
			description = "Verify that inspection is saved as declined when all services are skipped or declined")
	public void testRegularVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_2() throws Exception {
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
	
	@Test(testName = "Test Case 32286:Inspections: Regular - Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount", 
			description = "Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount")
	public void testRegularVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_1() throws Exception {

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
		myinspectionsscreen.selectInspectionType("Inspection_for_auto_WO_line_appr");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		inspectionnumber32286 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);;
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Left Rear Door");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForAction(inspectionnumber32286);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		String servicetoapprove = iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)";
		String servicetodecline = iOSInternalProjectConstants.SR_S1_MONEY_PANEL + " (Left Rear Door)";
		String servicetoskip = iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE + " (Front Bumper)";
		Helpers.waitABit(6000);
		approveinspscreen.selectInspection(inspectionnumber32286);
		approveinspscreen.selectInspectionServiceToApprove(servicetoapprove);
		approveinspscreen.selectInspectionServiceToDecline(servicetodecline);
		approveinspscreen.selectInspectionServiceToSkip(servicetoskip);
		approveinspscreen.clickSaveButton();
		
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32286:Inspections: Regular - Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount", 
			description = "Verify that amount of approved services are shown on BO > inspections list > column ApprovedAmount")
	public void testRegularVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_2() throws Exception {
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

	String inspnumber32287 = null;	
	
	@Test(testName = "Test Case 32287:Inspections: Regular - Verify that amount of skipped/declined services are not calc go approved amount BO > inspections list > column ApprovedAmount", 
			description = "Verify that amount of skipped/declined services are not calc go approved amount BO > inspections list > column ApprovedAmount")
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_1() throws Exception {
		
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
		inspnumber32287 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForApprove(inspnumber32287);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber32287);
		approveinspscreen.clickSkipAllServicesButton();		
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickDoneStatusReasonButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32287:Inspections: Regular - Verify that amount of skipped/declined services are not calc go approved amount BO > inspections list > column ApprovedAmount", 
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
		inspectionspage.searchInspectionByNumber(inspnumber32287);		
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspnumber32287), "$0.00");
		Assert.assertEquals(inspectionspage.getInspectionApprovedTotal(inspnumber32287), "$0.00");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName = "Test Case 33664:Inspections: Regular - Verify that services are marked as strikethrough when exclude from total", 
			description = "Inspections: Regular - Verify that services are marked as strikethrough when exclude from total")
	public void testInspectionVerifyServicesAreMarkedAsStrikethroughWhenExcludeFromTotal() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		myinspectionsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$1,050.00");
		for (int i = 0; i < 2; i++) {
			servicesscreen.clickToolButton();
			selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.selectVehiclePart("Right Door Mirror");
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.saveSelectedServiceDetails();
			servicesscreen.clickAddServicesButton();
		}
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$2,050.00");
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);	
		approveinspscreen.selectInspection(inspectionnumber);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		Assert.assertEquals(approveinspscreen.getInspectionTotalAmount(), "$2,050.00");
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 38748:Inspections: Regular - Verify that value selected on price matrix step is saved and shown during edit mode", 
			description = "Verify that value selected on price matrix step is saved and shown during edit mode")
	public void testRegularVerifyThatValueSelectedOnPriceMatrixStepIsSavedAndShownDuringEditMode() throws Exception {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_TYPE_FOR_PRICE_MATRIX);
		myinspectionsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.setMakeAndModel(_make, _model);
		vehiclescreeen.setColor(_color);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		pricematrix.clickSaveButton();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);		
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$55.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$55.00");
		
		
		pricematrix.selectNextScreen("Zayats test pack");
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.clickAddServicesButton();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$60.00");
		
		servicesscreen.selectNextScreen("Hail Matrix");
		pricematrix.selectPriceMatrix("ROOF");
		pricematrix.setSizeAndSeverity("DIME", "LIGHT");
		pricematrix.setPrice("123");
		pricematrix.clickSaveButton();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$123.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$183.00");
		
		pricematrix.selectNextScreen("Hail Damage");
		pricematrix.selectPriceMatrix("Grill");
		pricematrix.setSizeAndSeverity("DIME", "LIGHT");
		pricematrix.clickSaveButton();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$10.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
		
		pricematrix.clickSaveButton();
		Helpers.acceptAlert();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		vehiclescreeen.clickSaveButton();
		for (int i = 0; i<2; i++) {
			myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
			myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
			pricematrix.selectNextScreen("Price Matrix Zayats");
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$55.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
		
			pricematrix.selectNextScreen("Zayats test pack");
            Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), "$5.00");
            Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$193.00");
		
			pricematrix.selectNextScreen("Hail Matrix");
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$123.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
		
			pricematrix.selectNextScreen("Hail Damage");
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$10.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
			vehiclescreeen.clickSaveButton();
		}
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42184:WO: Regular - Verify that message is shown total is over limitation 999999999.999", 
			description = "Verify that message is shown total is over limitation 999999999.999")
	public void testWOVerifyThatMessageIsShownTotalIsOverLimitation()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_SMOKE_TEST);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("98765");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSave();
		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Total amount of work order is huge."));
		Assert.assertTrue(alerttext.contains("Maximum allowed total amount is $999,999,999.99"));
		ordersummaryscreen.swipeScreenLeft();
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServiceQuantityValue("987");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.saveWorkOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42183:Invoices: Regular - Verify that message is shown total is over limitation 999,999,999.999", 
			description = "Verify that message is shown total is over limitation 999,999,999.999")
	public void testInvoicesVerifyThatMessageIsShownTotalIsOverLimitation()
			throws Exception {
		
		final String VIN  = "JA4LS31H8YP047397";
		
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
		String wo1 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("987");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wo2 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("987");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.approveWorkOrder(wo1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wo1);
		myworkordersscreen.clickInvoiceIcon();
		
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType("Invoice_Custom1");
		invoiceinfoscreen.selectNextScreen("Info");
		invoiceinfoscreen.setPO("123");
		String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		
		myworkordersscreen.approveWorkOrder(wo2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickHomeButton();
		
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen.selectNextScreen("Info");
		invoiceinfoscreen.addWorkOrder(wo2);
		invoiceinfoscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Total amount of invoice is huge."));
		Assert.assertTrue(alerttext.contains("Maximum allowed total amount is $999,999,999.99"));
		ordersummaryscreen.swipeScreenLeft();
		invoiceinfoscreen.clickChangeScreen();
		invoiceinfoscreen.cancelInvoice();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42182:Inspection: Regular - Verify that message is shown total is over limitation 999999999.999", 
			description = "Inspection: Verify that message is shown total is over limitation 999999999.999")
	public void testInspectionVerifyThatMessageIsShownTotalIsOverLimitation() throws Exception {
		
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
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("98765");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		
		Assert.assertTrue(alerttext.contains("Total amount of inspection is huge."));
		Assert.assertTrue(alerttext.contains("Maximum allowed total amount is $999,999,999.99"));
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServiceQuantityValue("987");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsDraft();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42372:Inspections: Regular - Verify approved amount for Inspection created from SR", description = "Verify approved amount for Inspection created from SR")
	public void testVerifyApprovedAmountForInspectionCreatedFromSR() throws Exception {
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Thread.sleep(3000);
		servicerequestsscreen.clickAddButton();
		servicerequestsscreen.selectInspectionType(iOSInternalProjectConstants.SR_ALL_PHASES);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.clickAddServicesButton();
		servicesscreen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.clickSaveButton();
		Assert.assertTrue(appiumdriver.findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		appiumdriver.findElement(
				MobileBy.name("No"))
				.click();
		Thread.sleep(3000);
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		Thread.sleep(2000);
		servicerequestsscreen.selectInspectionType("Insp_for_auto_WO_line_appr_multiselect");
		servicerequestsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);

		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveButton();	
		Thread.sleep(5000);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		Thread.sleep(3000);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		
		teaminspectionsscreen.selectInspectionForApprove(inspectionnumber);
		teaminspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspectionnumber);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Hood)");
		approveinspscreen.selectInspectionServiceToDecline(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		approveinspscreen.clickSaveButton();		
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		teaminspectionsscreen = new RegularTeamInspectionsScreen(appiumdriver);
		Assert.assertTrue(teaminspectionsscreen.checkInspectionIsApproved(inspectionnumber));
		Assert.assertEquals(teaminspectionsscreen.getFirstInspectionAprovedPriceValue(), "$2,000.00");
		Assert.assertEquals(teaminspectionsscreen.getFirstInspectionPriceValue(), "$2,050.00");
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		servicerequestsscreen.clickHomeButton();
	}
	
	String inspnumber47249 = "";
	
	@Test(testName = "Test Case 47249:Inspections: Regular - Verify that on Price matrix step sub total value is shown correctly", 
			description = "Verify that on Price matrix step sub total value is shown correctly")
	public void testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";		
		final String _pricematrix1 = "Hood";
		final String defprice = "100";
		final String timevalue = "20";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		myinspectionsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		
		vehiclescreeen.setVIN(VIN);
		inspnumber47249 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		myinspectionsscreen.selectNextScreen("Default");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice(defprice);
		pricematrix.clickDiscaunt("SR_S5_Mt_Upcharge_20");
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.saveSelectedServiceDetails();	
		pricematrix.clickSaveButton();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$120.00");
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.clickDiscaunt("SR_S5_Mt_Upcharge_25");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$145.00");
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.clickDiscaunt("SR_S5_Mt_Discount_10");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$130.50");
		
		myinspectionsscreen.selectNextScreen("Matrix Labor");
		pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.setSizeAndSeverity("DIME", "VERY LIGHT");
		Helpers.waitABit(500);
		pricematrix.setTime(timevalue);
		pricematrix.clickDiscaunt("SR_S5_Mt_Discount_10");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$90.00");
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.clickDiscaunt("SR_S5_Mt_Upcharge_25");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$112.50");
		pricematrix.clickSaveButton();
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionPriceValue(), "$293.00");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 47257:WO: Regular - Verify that on Price Matrix step sub total is shown correctly", 
			description = "Verify that on Price Matrix step sub total is shown correctly"/*,
			dependsOnMethods = { "testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly" }*/)
	public void testVerifyThatOnPriceMatrixStepSubTotalIsShownCorrectly() throws Exception {
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForApprove(inspnumber47249);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber47249);
		approveinspscreen.approveInspectionApproveAllAndSignature();
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber47249);
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.WO_SMOKE_MONITOR);
		myinspectionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$293.00");
		servicesscreen.searchServiceByName("Dent Removal");
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues("Dent Removal", "$112.50"));
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues("Dent Removal", "$130.50"));
		servicesscreen.cancelOrder();
		myinspectionsscreen.clickHomeButton();
	}
	
	String inspnumber48543 = "";
	
	@Test(testName = "Test Case 48543:Inspections: Regular - Verify that part services with different configurations are correctly shown for inspection", 
			description = "Inspections: Regular - Verify that part services with different configurations are correctly shown for inspection")
	public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_WITH_PART_SERVICES);
		myinspectionsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		inspnumber48543 = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickSaveAsDraft();
		myinspectionsscreen.selectInspectionForEdit(inspnumber48543);
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Category");
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Engine");
		selectedservicescreen.selectServicePartSubcategory("Filters");
		selectedservicescreen.selectServicePartSubcategoryPart("Engine Oil Filter");
		selectedservicescreen.selectServicePartSubcategoryPosition("Oil Cooler");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("2.35");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Driver Seat");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.clickToolButton();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Name");
		//selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.setServicePriceValue("2.5");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.clickToolButton();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_None");
		selectedservicescreen.clickServicePartCell();
		selectedservicescreen.selectServicePartCategory("Body");
		selectedservicescreen.selectServicePartSubcategory("Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper");
		selectedservicescreen.selectServicePartSubcategoryPosition("Rear Upper");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("5.09");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.answerQuestionCheckButton();		
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.clickToolButton();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_several");
		selectedservicescreen.clickServicePartCell();
		selectedservicescreen.selectCategory("Air and Fuel Delivery");
		selectedservicescreen.selectServicePartSubcategory("Filters");
		selectedservicescreen.selectServicePartSubcategoryPart("Air Filter");
		selectedservicescreen.selectServicePartSubcategoryPosition("Inner");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.setServicePriceValue("7");
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.clickToolButton();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_SubCategory");
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Body");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper Air Shield");
		selectedservicescreen.selectServicePartSubcategoryPosition("Front Lower");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.setServicePriceValue("4.31");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		vehiclescreeen.selectNextScreen("Future Audi Car");		
		RegularVisualInteriorScreen visualinteriorscreen = new RegularVisualInteriorScreen(appiumdriver);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService("Detail");
		visualinteriorscreen.selectSubService("Oksi_Part_SubCategory");
		Helpers.tapRegularCarImage();
		selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Body");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper Assembly");
		selectedservicescreen.selectServicePartSubcategoryPosition("Front");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("6.43");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		vehiclescreeen.selectNextScreen("PM_New");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice("10");
		pricematrix.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Engine");
		selectedservicescreen.selectServicePartSubcategory("Electrical Connectors");
		selectedservicescreen.selectServicePartSubcategoryPart("Engine Brake Relay Connector");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Brake Relay Connector (N/A)");
		selectedservicescreen.setServicePriceValue("12.44");
		selectedservicescreen.saveSelectedServiceDetails();
		
		pricematrix.clickDiscaunt("Oksi_Part_Name");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.clickServicePartCell();		
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Belts and Cooling");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Hardware");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("2.13");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartSubTotalPrice(), "$24.57");		
		
		pricematrix.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);	
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$89.13");
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$49.45");
		
		servicesscreen.clickSaveAsFinal();
		Helpers.acceptAlert();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Test Service with req question");
		selectedservicescreen.answerQuestion2("A3");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.selectVehiclePart("Front Bumper");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.saveSelectedServiceDetails();	
		servicesscreen.clickSaveAsFinal();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspnumber48543), "$89.13");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 48562:WO: Regular - Verify that part services are copied from insp to order", 
			description = "WO: Regular - Verify that part services are copied from insp to order")
	public void testWOVerifyThatPartServicesAreCopiedFromInspToOrder() throws Exception {
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber48543);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber48543);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
	
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber48543);
		myinspectionsscreen.selectWorkOrderType("WO_with_part_service");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$77.13");
		vehiclescreeen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		
		ordersummaryscreen.clickSaveButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$77.13");
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 48563:WO: Regular - Verify that part services with different configurations are correctly shown for WO", 
			description = "WO: Regular - Verify that part services with different configurations are correctly shown for WO")
	public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		
		myworkordersscreen.selectWorkOrderType("WO_with_part_service");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		
		ordersummaryscreen.clickSaveButton();
		
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Category");
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Engine");
		selectedservicescreen.selectServicePartSubcategory("Filters");
		selectedservicescreen.selectServicePartSubcategoryPart("Engine Oil Filter");
		selectedservicescreen.selectServicePartSubcategoryPosition("Oil Cooler");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("2.35");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Driver Seat");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.clickToolButton();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Name");
		//selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.setServicePriceValue("2.5");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.clickToolButton();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_None");
		selectedservicescreen.clickServicePartCell();
		selectedservicescreen.selectServicePartCategory("Body");
		selectedservicescreen.selectServicePartSubcategory("Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper");
		selectedservicescreen.selectServicePartSubcategoryPosition("Rear Upper");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("5.09");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.answerQuestionCheckButton();		
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.clickToolButton();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_several");
		selectedservicescreen.clickServicePartCell();
		selectedservicescreen.selectCategory("Air and Fuel Delivery");
		selectedservicescreen.selectServicePartSubcategory("Filters");
		selectedservicescreen.selectServicePartSubcategoryPart("Air Filter");
		selectedservicescreen.selectServicePartSubcategoryPosition("Inner");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.setServicePriceValue("7");
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.clickToolButton();
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_SubCategory");
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Body");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper Air Shield");
		selectedservicescreen.selectServicePartSubcategoryPosition("Front Lower");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.setServicePriceValue("4.31");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("VP1 zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("PM_New");	
		servicesscreen.selectPriceMatrices("VP1 zayats");		
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice("10");
		pricematrix.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Engine");
		selectedservicescreen.selectServicePartSubcategory("Electrical Connectors");
		selectedservicescreen.selectServicePartSubcategoryPart("Engine Brake Relay Connector");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Brake Relay Connector (N/A)");
		selectedservicescreen.setServicePriceValue("12.44");
		selectedservicescreen.saveSelectedServiceDetails();
		
		pricematrix.clickDiscaunt("Oksi_Part_Name");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.clickServicePartCell();		
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Belts and Cooling");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Hardware");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("2.13");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartSubTotalPrice(), "$24.57");		
		
		pricematrix.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartSubTotalPrice(), "$49.45");
		pricematrix.clickBackButton();
		pricematrix.clickBackButton();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);	
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$70.70");
		servicesscreen.saveWorkOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 45100:WO: Regular - Verify rounding in calculation script with price matrix", 
			description = "WO: Regular - Verify rounding in calculation script with price matrix")
	public void testWOVerifyRoundingInCalculationScriptWithPriceMatrix() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		servicesscreen.selectPriceMatrices("Grill");	
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice("975");
		pricematrix.clickDiscaunt(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue("25");
		selectedservicescreen.saveSelectedServiceDetails();
		
		pricematrix.clickDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicescreen.setServicePriceValue("192");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		pricematrix.clickBackButton();
		pricematrix.clickBackButton();
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicescreen.setServicePriceValue("-30");
		selectedservicescreen.saveSelectedServiceDetails();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$987.52");
		
		questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType("Invoice_Default_Template");
		invoiceinfoscreen.setPO("12345");
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveButton();
		Helpers.acceptAlert();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();
		
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$987.52");
		homescreen = myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertEquals(myinvoicesscreen.getPriceForInvoice(invoicenumber), "$987.52");
		myinvoicesscreen.clickHomeButton();		
	}
	
	String invoicenumber45224 = null;
	
	@Test(testName = "Test Case 45224:WO: Regular - Verify calculation with price matrix Labor type", 
			description = "WO: Regular - Verify calculation with price matrix Labor type")
	public void testWOVerifyCalculationWithPriceMatrixLaborType_1() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen(appiumdriver);
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicedetailscreen.setServicePriceValue("90");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.changeAmountOfBundleService("100");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TAX_DISCOUNT);
		selectedservicedetailscreen.setServicePriceValue("10");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectSubService("Matrix Service");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
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
		pricematrix.clickBackButton();
		pricematrix.clickBackButton();
		questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSaveButton();
		Helpers.waitABit(1000);
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType("Invoice_AutoWorkListNet");
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		questionsscreen.selectNextScreen("Info");
		invoiceinfoscreen.setPO("12345");
		invoicenumber45224 = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();
		
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$542.68");
		homescreen = myworkordersscreen.clickHomeButton();
	}
	
	
	@Test(testName = "Test Case 45224:WO: Regular - Verify calculation with price matrix Labor type", 
			description = "WO: Regular - Verify calculation with price matrix Labor type")
	public void testWOVerifyCalculationWithPriceMatrixLaborType_2() throws Exception {
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
		
		invoicespage.setSearchInvoiceNumber(invoicenumber45224);
		invoicespage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicespage.clickInvoicePrintPreview(invoicenumber45224);
		invoicespage.waitABit(4000);
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
	
	@Test(testName = "Test Case 42803:Invoices: Regular - Verify rounding money amount values", 
			description = "Invoices: Regular - Verify rounding money amount values")
	public void testInvoicesVerifyRoundingMoneyAmountValues_1() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		servicesscreen.selectPriceMatrices("Grill");	
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice("100");
		pricematrix.clickDiscaunt(iOSInternalProjectConstants.OKSI_SERVICE_PP_FLAT_FEE);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue("23");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();
		pricematrix.clickBackButton();
		pricematrix.clickBackButton();
		
		servicesscreen.selectSubService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServicePriceValue("10");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.answerQuestion2("A3");
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_BUNDLE_PP);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE);
		selectedservicescreen.setServicePriceValue("25");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicescreen.setServicePriceValue("5");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Grill");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.changeAmountOfBundleService("30");
		servicesscreen.clickSaveButton();
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		selectedservicescreen.setServicePriceValue("3.8");
		selectedservicescreen.saveSelectedServiceDetails();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$169.19");
		
		questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.selectWorkOrderForApprove(wonumber);
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.clickApproveButton();
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(iOSInternalProjectConstants.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("12345");
		invoicenumber42803 = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}
	
	
	@Test(testName = "Test Case 42803:Invoices: Regular - Verify rounding money amount values", 
			description = "Invoices: Regular - Verify rounding money amount values")
	public void testInvoicesVerifyRoundingMoneyAmountValues_2() throws Exception {
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
	
	@Test(testName = "Test Case 38970:Inspections: Reqular - Verify that updated value for required service with 0 Price is saved when package grouped by panels", 
			description = "Inspections: Reqular - Verify that updated value for required service with 0 Price is saved when package grouped by panels")
	public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String serviceprice = "21";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_WITH_0_PRICE);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);	
		approveinspscreen.selectInspection(inspnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE), "$0.00");
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.selectService("Buff");
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE);
		selectedservicescreen.setServicePriceValue(serviceprice);
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickBackServicesButton();
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);	
		approveinspscreen.selectInspection(inspnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE), PricesCalculations.getPriceRepresentation(serviceprice));
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40463:Inspections: Regular - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when appove inspection", 
			description = "Inspections: Regular - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when appove inspection")
	public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";

		
		homescreen = new RegularHomeScreen(appiumdriver);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		myinspectionsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		final String inspnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("2000");
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Roof");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S4_BUNDLE);
		selectedservicedetailscreen.setServicePriceValue("600");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen.setServicePriceValue("13");
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.answerQuestion2("A3");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$2,688.00");
		servicesscreen.clickSaveButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Roof)");
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S4_BUNDLE);
		approveinspscreen.selectInspectionServiceToSkip("3/4\" - Penny Size");
		approveinspscreen.selectInspectionServiceToSkip(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE + " (Back Glass)");
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspnumber), "$2,688.00");
		Assert.assertEquals(myinspectionsscreen.getInspectionApprovedPriceValue(inspnumber), "$2,650.00");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40466:Inspections: Regular - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when decline inspection", 
			description = "Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when decline inspection")
	public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection() throws Exception {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType("Insp_Draft_Mode");
		myinspectionsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();		
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S4_BUNDLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.answerQuestion2("A3");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveAsFinal();
			
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspectionnumber);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		Thread.sleep(1000);
		myinspectionsscreen.clickFilterButton();
		myinspectionsscreen.clickStatusFilter();
		myinspectionsscreen.clickFilterStatus("Declined");
		myinspectionsscreen.clickHomeButton();
		myinspectionsscreen.clickSaveFilterDialogButton();
				
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionAprovedPriceValue(), "$0.00");
		Assert.assertEquals(myinspectionsscreen.getFirstInspectionPriceValue(), "$2,638.00");		
		myinspectionsscreen.clickHomeButton();		
	}
	
	@Test(testName = "Test Case 35030:WO: Regular - Verify that for bundle items price policy is applied", 
			description = "WO: Regular - Verify that for bundle items price policy is applied")
	public void testWOVerifyThatForBundleItemsPricePolicyIsApplied() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.openCustomServiceDetails("Oksi_Bundle_PP");
		
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$13.00");
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE);
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
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.selectVehiclePart("Driver Seat");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$97.00");
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S5_MT_DISCOUNT_10);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$87.30");
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicedetailscreen.setServicePriceValue("10");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("You can add only one service 'Service_PP_Vehicle_not_multiple'"));
		Helpers.waitABit(1000);
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$96.30");
		
		//servicesscreen.selectPriceMatrices(iOSInternalProjectConstants.OKSI_SERVICE_PP_LABOR);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_LABOR);
		selectedservicedetailscreen.setServiceTimeValue("3");
		selectedservicedetailscreen.setServiceRateValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$163.80");
	
		
		
		selectedservicedetailscreen.changeAmountOfBundleService("163.80");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40541:WO: Regular - Verify that for Sales Tax Service data is set from DB when create WO for customer with appropriate data", 
			description = "WO: Regular - Verify that for Sales Tax Service data is set from DB when create WO for customer with appropriate data")
	public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectOnlineCustomer("Avalon");
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
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
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Test_service_with_QF_PP_Panel");
		selectedservicedetailscreen.answerQuestion2("A3");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Dashboard");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSaveButton();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		Assert.assertFalse(selectedservicedetailscreen.isServiceDetailsFieldEditable("State Rate"));
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Sales Tax Override");
		selectedservicedetailscreen.setServiceDetailsFieldValue("State Rate", "6");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("State Rate"), "%6.000");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsTotalValue(), "%9.250");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.saveWorkOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 49680:Invoices: Regular - Verify that tax rounding is correctly calculated", 
			description = "Invoices: Regular - Verify that tax rounding is correctly calculated")
	public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("79");
		selectedservicedetailscreen.setServiceQuantityValue("4.1");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedservicedetailscreen.setServicePriceValue("20");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicedetailscreen.setServicePriceValue("15");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Service_with_default_Tech");
		selectedservicedetailscreen.setServicePriceValue("3205");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
				
		servicesscreen.selectSubService(iOSInternalProjectConstants.TAX_DISCOUNT);	
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TAX_DISCOUNT);
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$178.20");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$3,742.10");
		
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 49915:Inspections: Regular - Verify that tax is calc correctly from services with tax exempt YES/No", 
			description = "Inspections: Regular - Verify that tax is calc correctly from services with tax exempt YES/No")
	public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo() throws Exception {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_CALC);
		myinspectionsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String inspectionnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Money_Tax_Exempt");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Driver Seat");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$100.00");
		
		servicesscreen.clickToolButton();
		servicesscreen.selectSubService(iOSInternalProjectConstants.SALES_TAX);
		servicesscreen.clickAddServicesButton();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$100.00");
		
		servicesscreen.clickToolButton();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$205.00");
		
		servicesscreen.clickToolButton();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Money_Discount_Exempt");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$310.00");
		
		servicesscreen.clickToolButton();
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.clickAddServicesButton();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$289.50");
		
		servicesscreen.clickToolButton();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Money_TE_DE");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
			
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$389.50");
		servicesscreen.clickSaveButton();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspectionnumber), "$389.50");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 49945:WO:Regular - Verify that package price overrides client (retail or wholesale)", 
			description = "WO:Regular - Verify that package price overrides client (retail or wholesale)")
	public void testWOVerifyThatPackagePriceOverridesClient_RretailOrWholesale() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber1 = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber1);
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		
		servicesscreen.selectSubService("Money_Pack_Price");
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$4.07");
		servicesscreen.saveWorkOrder();

		myworkordersscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_MONITOR_DEVICE);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String wonumber2 = vehiclescreeen.getWorkOrderNumber();
		
		vehiclescreeen.selectNextScreen(RegularOrderSummaryScreen
				.getOrderSummaryScreenCaption());
		ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSaveButton();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber2);
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		
		servicesscreen.selectSubService("Money_Pack_Price");
		toolaber = new RegularInspectionToolBar(appiumdriver);		
		servicesscreen.saveWorkOrder();
		
		myworkordersscreen.selectWorkOrder(wonumber2);	
		myworkordersscreen.clickChangeCustomerPopupMenu();
		customersscreen.swtchToRetailMode();
		customersscreen.selectCustomer("19319");
		Helpers.waitABit(3000);
		Assert.assertFalse(myworkordersscreen.woExists(wonumber2));
		myworkordersscreen.clickHomeButton();
		
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.selectWorkOrderForEidt(wonumber2);
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$12.00");
		
		servicesscreen.clickToolButton();
		servicesscreen.selectSubService("Money_Pack_Price");
		servicesscreen.clickAddServicesButton();
		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$35.00");
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 50013:WO: Regular - Verify that client and job overrides are working fine for WO", 
			description = "WO: Regular - Verify that client and job overrides are working fine for WO")
	public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO() throws Exception {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen(appiumdriver);			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(iOSInternalProjectConstants.WO_TYPE_WITH_JOB);
		RegularVehicleScreen vehiclescreeen =myworkordersscreen.selectJob("Job for test");
		vehiclescreeen.setVIN(VIN);
		String wonumber = vehiclescreeen.getWorkOrderNumber();
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.selectSubService("Money_client_override");
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar(appiumdriver);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$4.79");
		
		servicesscreen.selectSubService("Money_job_override");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$13.84");
		servicesscreen.clickSaveButton();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$13.84");
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 49856:SR: Regular - Verify ALM flow when approve both inspections", 
			description = "SR: Regular - Verify ALM flow when approve both inspections")
	public void testSRVerifyALMFlowWhenApproveBothInspections()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveButton();
		
		Helpers.waitABit(5000);
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInAction();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isUndoCheckInActionExists());
		servicerequestsscreen.clickCancel();
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		Helpers.waitABit(4000);
		RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		String inspnumber1 = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getInspectionTypeValue(inspnumber1), "ALM - Recon Inspection");
		myinspectionsscreen.selectInspectionForEdit(inspnumber1);
		vehiclescreeen.selectNextScreen("ALM - Statuses");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("12");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		
		Assert.assertEquals(myinspectionsscreen.getNumberOfRowsInTeamInspectionsTable(), 2);
		String inspnumber2 = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getInspectionTypeValue(inspnumber2), "ALM - Service Inspection");
		myinspectionsscreen.selectInspectionForEdit(inspnumber2);
		vehiclescreeen.selectNextScreen("ALM - Statuses");
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		Helpers.waitABit(2000);
		myinspectionsscreen.selectInspectionForAction(inspnumber1);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber1);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber2);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber2);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		
		Assert.assertTrue(myinspectionsscreen.isWOIconPresentForInspection(inspnumber1));
		servicerequestsscreen.clickHomeButton();
		
		servicerequestsscreen.clickServiceRequestSummaryOrdersButton();
		RegularMyWorkOrdersScreen myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$37.00");
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getWorkOrderTypeValue(), "ALM - Recon Facility");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.searchServiceByName("3/4\" - Penny Size");
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$12.00 x 1.00"));
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$25.00 x 1.00"));
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 49895:SR: Regular - Verify ALM flow when approve one inspection and one decline", 
			description = "SR: Regular - Verify ALM flow when approve one inspection and one decline")
	public void testSRVerifyALMFlowWhenApproveOneInspectionAndOneDecline()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveButton();
		
		Helpers.waitABit(5000);
		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInAction();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isUndoCheckInActionExists());
		servicerequestsscreen.clickCancel();
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		Helpers.waitABit(4000);
		RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		String inspnumber1 = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getInspectionTypeValue(inspnumber1), "ALM - Recon Inspection");
		myinspectionsscreen.selectInspectionForEdit(inspnumber1);
		vehiclescreeen.selectNextScreen("ALM - Statuses");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("12");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		
		Assert.assertEquals(myinspectionsscreen.getNumberOfRowsInTeamInspectionsTable(), 2);
		String inspnumber2 = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getInspectionTypeValue(inspnumber2), "ALM - Service Inspection");
		myinspectionsscreen.selectInspectionForEdit(inspnumber2);
		vehiclescreeen.selectNextScreen("ALM - Statuses");
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		Helpers.waitABit(5000);
		myinspectionsscreen.selectInspectionForAction(inspnumber1);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber1);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber2);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber2);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		
		Assert.assertTrue(myinspectionsscreen.isWOIconPresentForInspection(inspnumber1));
		servicerequestsscreen.clickHomeButton();
		
		servicerequestsscreen.clickServiceRequestSummaryOrdersButton();
		RegularMyWorkOrdersScreen myworkordersscreen = new RegularMyWorkOrdersScreen(appiumdriver);
		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$12.00");
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		Assert.assertEquals(vehiclescreeen.getWorkOrderTypeValue(), "ALM - Recon Facility");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		Assert.assertTrue(servicesscreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$12.00 x 1.00"));
		servicesscreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 49896:SR: Regular - Verify ALM flow when decline both inspections", 
			description = "SR: Regular - Verify ALM flow when decline both inspections")
	public void testSRVerifyALMFlowWhenDeclineBothInspections()
			throws Exception {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		
		servicerequestsscreen.selectServiceRequestType(iOSInternalProjectConstants.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		vehiclescreeen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveButton();

		servicerequestsscreen = new RegularServiceRequestsScreen(appiumdriver);
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInAction();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isUndoCheckInActionExists());
		servicerequestsscreen.clickCancel();
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
		String inspnumber1 = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getInspectionTypeValue(inspnumber1), "ALM - Recon Inspection");
		myinspectionsscreen.selectInspectionForEdit(inspnumber1);
		vehiclescreeen.selectNextScreen("ALM - Statuses");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		servicesscreen = new RegularServicesScreen(appiumdriver);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("12");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(1000*60);
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		
		Assert.assertEquals(myinspectionsscreen.getNumberOfRowsInTeamInspectionsTable(), 2);
		String inspnumber2 = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getInspectionTypeValue(inspnumber2), "ALM - Service Inspection");
		myinspectionsscreen.selectInspectionForEdit(inspnumber2);
		vehiclescreeen.selectNextScreen("ALM - Statuses");
		questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		questionsscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		Helpers.waitABit(5000);
		myinspectionsscreen.selectInspectionForAction(inspnumber1);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber1);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 2");
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber2);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);
		approveinspscreen.selectInspection(inspnumber2);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();
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
	
	@Test(testName = "Test Case 32190:Inspections: Regular - Verify that for Declined/Skipped services appropriate icon is shown after approval,"
			+ "Test Case 32191:Inspections: Regular: Verify that declined/skipped services are marked with text (Declined) or (Skipped) accordingly on Inspection Summary screen", 
			description = "Inspections: Regular - Verify that for Declined/Skipped services appropriate icon is shown after approval,"
					+ "Inspections: Regular: Verify that declined/skipped services are marked with text (Declined) or (Skipped) accordingly on Inspection Summary screen")
	public void testInspectionsVerifyThatForDeclinedSkippedaServicesAppropriateIconIsShownAfterApproval() throws Exception {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new RegularHomeScreen(appiumdriver);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType(iOSInternalProjectConstants.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		myinspectionsscreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN(VIN);
		String inspnumber = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen("Zayats Section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.selectSubService(iOSInternalProjectConstants.TAX_DISCOUNT);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.clickAddServicesButton();
		vehiclescreeen.selectNextScreen("Default");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("Hood");
		pricematrix.setSizeAndSeverity("DIME", "HEAVY");
		pricematrix.clickSaveButton();
		myinspectionsscreen.selectNextScreen("Future Sport Car");
		RegularVisualInteriorScreen visualinteriorscreen = new RegularVisualInteriorScreen(appiumdriver);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		RegularVisualInteriorScreen.tapInteriorWithCoords(150, 200);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 250);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 300);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 350);
		visualinteriorscreen.selectNextScreen("Matrix Labor");
		pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("Back Glass");
		pricematrix.switchOffOption("PDR");
		pricematrix.setTime("34");
		pricematrix.clickSaveButton();
		pricematrix.clickSaveButton();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen(appiumdriver);	
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickDeclineAllServicesButton();	
		approveinspscreen.selectInspectionServiceToApproveByIndex(iOSInternalProjectConstants.WHEEL_SERVICE, 0);
		approveinspscreen.selectInspectionServiceToApproveByIndex(iOSInternalProjectConstants.WHEEL_SERVICE, 2);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		approveinspscreen.selectInspectionServiceToApproveByIndex(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE, 0);
		
		
		approveinspscreen.selectInspectionServiceToSkipByIndex(iOSInternalProjectConstants.WHEEL_SERVICE, 2);
		approveinspscreen.selectInspectionServiceToSkipByIndex(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE, 0);
		approveinspscreen.selectInspectionServiceToSkip(iOSInternalProjectConstants.SR_S1_MONEY_PANEL + " (Grill)");				
		approveinspscreen.selectInspectionServiceToSkip(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
	
		approveinspscreen.clickSaveButton();
		//approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.selectNextScreen("Test_pack_for_calc");
		servicesscreen = new RegularServicesScreen(appiumdriver);
		Assert.assertTrue(servicesscreen.isServiceApproved(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.TAX_DISCOUNT));
		servicesscreen.cancelOrder();
		myinspectionsscreen.clickOnInspection(inspnumber);
		EmailScreen mailscreen = myinspectionsscreen.clickSendEmail();
		mailscreen.sendInvoiceOnEmailAddress("test.cyberiansoft@gmail.com");
		myinspectionsscreen.clickHomeButton();
		appiumdriver.closeApp();
		Thread.sleep(10*1000*1);
		boolean search = false;
		final String invpoicereportfilenname = inspnumber + ".pdf";
		for (int i= 0; i < 7; i++) {
			if (!MailChecker.searchEmailAndGetAttachment("test.cyberiansoft@gmail.com", "ZZzz11!!", "Estimate #" + inspnumber + " from Recon Pro Development QA", "ReconPro@cyberianconcepts.com", invpoicereportfilenname)) {
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
		}
		try {
		appiumdriver.launchApp();
		} catch (UnsupportedCommandException e) {
			appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
		}
		RegularMainScreen mainscr = new RegularMainScreen(appiumdriver);
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
}

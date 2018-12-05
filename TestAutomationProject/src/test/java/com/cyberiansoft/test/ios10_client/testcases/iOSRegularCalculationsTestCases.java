package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.EmailScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;

public class iOSRegularCalculationsTestCases extends BaseTestCase {
	
	private String regCode;
	private RegularHomeScreen homescreen;
	
	@BeforeClass
	public void setUpSuite() {
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		testGetDeviceRegistrationCode(ReconProIOSStageInfo.getInstance().getBackOfficeStageURL(),
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		testRegisterationiOSDdevice();
	}
	
	public void testGetDeviceRegistrationCode(String backofficeurl,
			String userName, String userPassword) {

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companyWebPage = backofficeheader.clickCompanyLink();

		ActiveDevicesWebPage devicespage = companyWebPage.clickManageDevicesLink();

		devicespage.setSearchCriteriaByName("AutomationCalculations_Regular2");
		regCode = devicespage.getFirstRegCodeInTable();

		DriverBuilder.getInstance().getDriver().quit();
	}

	public void testRegisterationiOSDdevice() {
        AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
        DriverBuilder.getInstance().getAppiumDriver().removeApp(IOSRegularDeviceInfo.getInstance().getDeviceBundleId());
        DriverBuilder.getInstance().getAppiumDriver().quit();
		AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
		RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen();
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("QC Environment");
		loginscreen.registeriOSDevice(regCode);
		RegularMainScreen mainscr = new RegularMainScreen();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularSettingsScreen settingscreen =  homescreen.clickSettingsButton();
		settingscreen.setShowAvailableSelectedServicesOn();
		homescreen = settingscreen.clickHomeButton();
	}
	
	//Test Case 8553:Create inspection on the device with approval required
	@Test(testName = "Test Case 8553:Create inspection on the device with approval required", description = "Create Inspection On The Device With Approval Required")
	public void testCreateInspectionOnTheDeviceWithApprovalRequired() {
		final String VIN = "TESTVINNO";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Black";
			
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
        RegularVehicleScreen vehicleScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        RegularClaimScreen claimScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimScreen.waitClaimScreenLoad();
        RegularVisualInteriorScreen visualInteriorScreen = claimScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
        RegularServicesScreen servicesscreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttxt, AlertsCaptions.ALERT_VIN_REQUIRED);
			
		RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.setVIN(VIN);
		vehiclescreen.clickSave();
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttxt, AlertsCaptions.ALERT_MAKE_REQUIRED);
			
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		String inspnumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.saveWizard();

		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		//testlogger.log(LogStatus.INFO, "After approve", testlogger.addScreenCapture(createScreenshot(, iOSLogger.loggerdir)));
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickApproveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inspnumber));
			
		myinspectionsscreen.clickHomeButton();
	}

	private String inspection8434 = "";
	//Test Case 8435:Create Retail Inspection (HD Single page)
	//Test Case 8434:Add Services to visual inspection
	@Test(testName = "Test Case 8434:Add Services to visual inspection", description = "Add Services To Visual Inspection")
	public void testAddServicesToVisualInspection()  {
		final String VIN = "ZWERTYASDFEWSDRZG";
		final String _make = "Acura";
		final String _model = "1.6 EL";
		final String _color = "Red";
		final String _inspectionprice = "275";
			
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.DEFAULT);
		vehiclescreen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttxt, AlertsCaptions.ALERT_VIN_REQUIRED);
		vehiclescreen.setVIN(VIN);
		inspection8434 = vehiclescreen.getInspectionNumber();
		vehiclescreen.clickSave();
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttxt, AlertsCaptions.ALERT_MAKE_REQUIRED);
			
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        RegularVisualInteriorScreen visualInteriorScreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
        visualInteriorScreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
        visualInteriorScreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspection8434);
        vehiclescreen = new RegularVehicleScreen();
        RegularVisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
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
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualinteriorscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		RegularVisualInteriorScreen.tapExterior();
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.clickServicesBackButton();
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionprice));
		visualinteriorscreen.saveWizard();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspection8434), PricesCalculations.getPriceRepresentation(_inspectionprice));
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 8433:Change Quantity of services in Visual Inspection", description = "Change Quantity Of Services In Visual Inspection")
	public void testChangeQuantityOfServicesInVisualInspection() {
		final String _quantity = "3.00";
		final String _quantityexterior = "2.00";
		final String _inspectionpricevisual = "275";
		final String _inspectionprice = "325";
		
		homescreen = new RegularHomeScreen();
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForEdit(inspection8434);
        RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
        RegularVisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);

		/*visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);*/
		RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
		visualinteriorscreen.setCarServiceQuantityValue(_quantity);
		visualinteriorscreen.saveCarServiceDetails();
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionpricevisual));
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		/*visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);*/
		RegularVisualInteriorScreen.tapExterior();
		visualinteriorscreen.setCarServiceQuantityValue(_quantityexterior);
		visualinteriorscreen.saveCarServiceDetails();
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), PricesCalculations.getPriceRepresentation(_inspectionprice));

		visualinteriorscreen.saveWizard();
		myinspectionsscreen.clickHomeButton();

	}
	
	@Test(testName="Test Case 28580:WO: Regular - If fee bundle item price policy = 'Panel' then it will be added once for many associated service instances with same vehicle part.", 
			description = "WO: Regular - If fee bundle item price policy = 'Panel' then it will be added once for many associated service instances with same vehicle part.")
	public void testRegularIfFeeBundleItemPricePolicyPanelThenItWillBeAddedOnceForManyAssociatedServiceInstancesWithSameVehiclePart() {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$33.00");

		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("13");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$34.00");

		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$67.00");
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	private String wonumber28583 = null;

	@Test(testName="Test Case 28583:WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity", 
			description = "WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity")
	public void testRegularIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_1() {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_FEE_ITEM_IN_2_PACKS);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		wonumber28583 = vehiclescreen.getWorkOrderNumber();
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Service_in_2_fee_packs");
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$36.00");
        RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("1");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
		
	}
	
	@Test(testName="Test Case 28583:WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity", 
			description = "WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity")
	public void testRegularIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_2() {
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
		wopage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		wopage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		wopage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
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
	public void testRegularVerifyThatPackagePriceOfFeeBundleItemIsOverrideThePriceOfWholesaleAndRetailPrices() {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FEE_PRICE_OVERRIDE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Service_for_override");
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$27.00");
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 28600:WO: Regular - If fee bundle item price policy = 'Vehicle' then it will be added once for many associated service instances", 
			description = "WO: Regular - If fee bundle item price policy = 'Vehicle' then it will be added once for many associated service instances")
	public void testRegularIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances() {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
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
	
	@Test(testName="Test Case 28601:WO: Regular -  If fee bundle item price policy = 'Service' or 'Flat Fee' then it will be added to WO every time when associated service instance will add to WO.", 
			description = "WO: Regular -  If fee bundle item price policy = 'Service' or 'Flat Fee' then it will be added to WO every time when associated service instance will add to WO.")
	public void testRegularIfFeeBundleItemPricePolicyServiceOrFlatFeeThenItWillBeAddedToWOEveryTimeWhenAssociatedServiceInstanceWillAddToWO() {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Service");
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
	
	@Test(testName="Test Case 28602:WO: Regular - Verify that for Wholesale and Retail customers fee is added depends on the price accordingly to price of the fee bundle item", 
			description = "WO: Regular - Verify that for Wholesale and Retail customers fee is added depends on the price accordingly to price of the fee bundle item")
	public void testRegularVerifyThatForWholesaleAndRetailCustomersFeeIsAddedDependsOnThePriceAccordinglyToPriceOfTheFeeBundleItem() {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$33.00");

		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("13");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$34.00");

		servicedetailsscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Panel");
		servicedetailsscreen.setServicePriceValue("12");
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$67.00");
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	private String wonumber29398 = null;
	
	@Test(testName="Test Case 29398:WO: Regular - Verify that Fee Bundle services is calculated for additional matrix services", 
			description = "Verify that Fee Bundle services is calculated for additional matrix services")
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_1()
			 {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String _pricematrix  = "Roof";
		final String price  = "54";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(VIN);
		wonumber29398 = vehiclescreen.getWorkOrderNumber();
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("SR_S5_Matrix_DE_TE");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setPrice(price);
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		vehiclePartScreen.selectDiscaunt("SR_S5_Mt_Money");
		vehiclePartScreen.selectDiscaunt("SR_S5_Mt_Upcharge_25");
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartSubTotalPrice(), "$2,117.50");
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$2,117.50");
		pricematrix.clickSave();

        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$2,127.50");
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
		
	}
	
	@Test(testName="Test Case 29398:WO: Regular - Verify that Fee Bundle services is calculated for additional matrix services", 
			description = "Verify that Fee Bundle services is calculated for additional matrix services")
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_2() {
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
		wopage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		wopage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		wopage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
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
	public void testRegularVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_095() {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String srs1moneyprice  = "40";
		final String srs1moneyflatfeeprice  = "8.25";
		final String discountvalue = "6";
		final String servicetotalprice = "$2.90";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen();
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
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		
		regularselectedservicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(regularselectedservicedetailsscreen.getServiceDetailsPriceValue(), servicetotalprice);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 30675:WO: Regular - Verify that money value of some percentage service is rounds down after 0.003", 
			description = "Regular - Verify that money value of some percentage service is rounds down after 0.003")
	public void testRegularVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_003() {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String srs1moneyprice  = "40";
		final String srs1moneyflatfeeprice  = "1.38";
		final String discountvalue = "6";
		final String servicetotalprice = "$2.48";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen();
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

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		regularselectedservicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(regularselectedservicedetailsscreen.getServiceDetailsPriceValue(), servicetotalprice);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 30676:WO: Regular - Verify that money value of some percentage service is rounds up after 0.005", 
			description = "Regular - Verify that money value of some percentage service is rounds up after 0.005")
	public void testRegularVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_005() {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String srs1moneyprice  = "20";
		final String srs1moneyflatfeeprice  = "8.09";
		final String discountvalue = "6";
		final String servicetotalprice = "$1.69";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//servicesscreen.clickToolButton();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen();
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

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		regularselectedservicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(regularselectedservicedetailsscreen.getServiceDetailsPriceValue(), servicetotalprice);
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	private String wonumber31498 = null;
	
	@Test(testName="Test Case 31498:WO: Regular - Verify that amount is calculated and rounded correctly", 
			description = "Verify that amount is calculated and rounded correctly")
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_1() {
		
		final String VIN  = "JA4LS31H8YP047397";
		final String[] prices  = { "160", "105", "400", "195", "2400", "180", "160", "105", "300" };
		final String discount  = "-25";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		wonumber31498 = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (String _price : prices) {
			RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
			selectedservicedetailscreen.setServicePriceValue(_price);
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		selectedservicedetailscreen.setServicePriceValue(discount);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.selectService(iOSInternalProjectConstants.TAX_DISCOUNT);
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$3,153.94");
        RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

	}
	
	@Test(testName="Test Case 31498:WO: Regular - Verify that amount is calculated and rounded correctly", 
			description = "Verify that amount is calculated and rounded correctly")
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_2() {
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
		wopage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		wopage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		wopage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		wopage.setSearchOrderNumber(wonumber31498);
		wopage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage woinfowebpage = wopage.clickWorkOrderInTable(wonumber31498);
		Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$3,153.94"));

		woinfowebpage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	private String inspectionnumber32226 = null;
	
	@Test(testName = "Test Case 32226:Inspections: Regular - Verify that inspection is saved as declined when all services are skipped or declined", 
			description = "Verify that inspection is saved as declined when all services are skipped or declined")
	public void testRegularVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_1()  {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		final String[] pricematrixes = { "Hood", "ROOF" };
		
		homescreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularQuestionsScreen questionsscreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_SIMPLE);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		RegularVehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		inspectionnumber32226 = vehiclescreen.getInspectionNumber();
        RegularPriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		for(String pricemrx : pricematrixes) {
			RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(pricemrx);
			vehiclePartScreen.setSizeAndSeverity("DIME", "VERY LIGHT");
			vehiclePartScreen.saveVehiclePart();
		}
		pricematrix.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber32226);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
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
	public void testRegularVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_2()  {
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
		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionspage.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());
		inspectionspage.selectSearchStatus("Declined");
		inspectionspage.searchInspectionByNumber(inspectionnumber32226);
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspectionnumber32226), "$0.00");
		Assert.assertEquals(inspectionspage.getInspectionReason(inspectionnumber32226), "Decline 1");
		Assert.assertEquals(inspectionspage.getInspectionStatus(inspectionnumber32226), "Declined");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	private String inspectionnumber32286 = null;
	
	@Test(testName = "Test Case 32286:Inspections: Regular - Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount")
	public void testRegularVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_1() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		inspectionnumber32286 = vehiclescreen.getInspectionNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Left Rear Door");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForAction(inspectionnumber32286);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		String servicetoapprove = iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)";
		String servicetodecline = iOSInternalProjectConstants.SR_S1_MONEY_PANEL + " (Left Rear Door)";
		String servicetoskip = iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE + " (Front Bumper)";
		approveinspscreen.selectInspection(inspectionnumber32286);
		approveinspscreen.selectInspectionServiceToApprove(servicetoapprove);
		approveinspscreen.selectInspectionServiceToDecline(servicetodecline);
		approveinspscreen.selectInspectionServiceToSkip(servicetoskip);
		approveinspscreen.clickSaveButton();
		
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32286:Inspections: Regular - Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount")
	public void testRegularVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_2() {
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
		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionspage.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());
		inspectionspage.selectSearchStatus("All active");
		inspectionspage.searchInspectionByNumber(inspectionnumber32286);		
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspectionnumber32286), "$2,000.00");
		Assert.assertEquals(inspectionspage.getInspectionStatus(inspectionnumber32286), "Approved");
		DriverBuilder.getInstance().getDriver().quit();	
	}

	private String inspnumber32287 = null;
	
	@Test(testName = "Test Case 32287:Inspections: Regular - Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount")
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_1() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(VIN);
		inspnumber32287 = vehiclescreen.getInspectionNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForApprove(inspnumber32287);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber32287);
		approveinspscreen.clickSkipAllServicesButton();		
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickDoneStatusReasonButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32287:Inspections: Regular - Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount")
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_2() {
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
		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionspage.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());
		inspectionspage.selectSearchStatus("Declined");
		inspectionspage.searchInspectionByNumber(inspnumber32287);		
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspnumber32287), "$0.00");
		Assert.assertEquals(inspectionspage.getInspectionApprovedTotal(inspnumber32287), "$0.00");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName = "Test Case 33664:Inspections: Regular - Verify that services are marked as strikethrough when exclude from total", 
			description = "Inspections: Regular - Verify that services are marked as strikethrough when exclude from total")
	public void testInspectionVerifyServicesAreMarkedAsStrikethroughWhenExcludeFromTotal() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$1,050.00");
		for (int i = 0; i < 2; i++) {
			selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.selectVehiclePart("Right Door Mirror");
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$2,050.00");
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();	
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
	public void testRegularVerifyThatValueSelectedOnPriceMatrixStepIsSavedAndShownDuringEditMode() {
			
		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVehicleScreen vehiclescreen =  myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

        RegularPriceMatrixScreen pricematrix =  vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.PRICE_MATRIX_ZAYATS);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		vehiclePartScreen.saveVehiclePart();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$55.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$55.00");


        RegularServicesScreen servicesscreen = pricematrix.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$60.00");

        pricematrix = servicesscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.HAIL_MATRIX);
		vehiclePartScreen = pricematrix.selectPriceMatrix("ROOF");
		vehiclePartScreen.setSizeAndSeverity("DIME", "LIGHT");
		vehiclePartScreen.setPrice("123");
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$123.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$183.00");
		
		pricematrix.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.HAIL_DAMAGE);
		vehiclePartScreen = pricematrix.selectPriceMatrix("Grill");
		vehiclePartScreen.setSizeAndSeverity("DIME", "LIGHT");
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$10.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
		
		pricematrix.clickSave();
		Helpers.acceptAlert();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		vehiclescreen.saveWizard();
		for (int i = 0; i<2; i++) {
			myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
            vehiclescreen = new RegularVehicleScreen();
            pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.PRICE_MATRIX_ZAYATS);
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$55.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
		
			pricematrix.selectNextScreen(WizardScreenTypes.SERVICES);
            Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), "$5.00");
            Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$193.00");
		
			pricematrix.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.HAIL_MATRIX);
			pricematrix.waitPriceMatrixScreenLoad();
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$123.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
		
			pricematrix.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.HAIL_DAMAGE);
			pricematrix.waitPriceMatrixScreenLoad();
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$10.00");
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$193.00");
            pricematrix.saveWizard();
		}
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42184:WO: Regular - Verify that message is shown total is over limitation 999999999.999", 
			description = "Verify that message is shown total is over limitation 999999999.999")
	public void testWOVerifyThatMessageIsShownTotalIsOverLimitation() {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(VIN);

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("98765");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

        RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSave();
		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Total amount of work order is huge."));
		Assert.assertTrue(alerttext.contains("Maximum allowed total amount is $999,999,999.99"));
		ordersummaryscreen.swipeScreenLeft();
        servicesscreen = ordersummaryscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedservicedetailscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServiceQuantityValue("987");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42183:Invoices: Regular - Verify that message is shown total is over limitation 999,999,999.999", 
			description = "Verify that message is shown total is over limitation 999,999,999.999")
	public void testInvoicesVerifyThatMessageIsShownTotalIsOverLimitation() {
		
		final String VIN  = "JA4LS31H8YP047397";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wo1 = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("987");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

        RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.clickAddOrderButton();
		vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wo2 = vehiclescreen.getWorkOrderNumber();
        questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("999999.00");
		selectedservicedetailscreen.setServiceQuantityValue("987");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.approveWorkOrder(wo1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wo1);
		myworkordersscreen.clickInvoiceIcon();
		
		myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_CUSTOM1);
		RegularInvoiceInfoScreen invoiceinfoscreen = questionsscreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceinfoscreen.setPO("123");
		String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		
		myworkordersscreen.approveWorkOrder(wo2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickHomeButton();
		
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickEditPopup();
		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.waitQuestionsScreenLoaded("Test Section");
		invoiceinfoscreen = questionsscreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceinfoscreen.addWorkOrder(wo2);
		invoiceinfoscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Total amount of invoice is huge."));
		Assert.assertTrue(alerttext.contains("Maximum allowed total amount is $999,999,999.99"));
		ordersummaryscreen.swipeScreenLeft();
		invoiceinfoscreen.cancelWizard();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42182:Inspection: Regular - Verify that message is shown total is over limitation 999999999.999", 
			description = "Inspection: Verify that message is shown total is over limitation 999999999.999")
	public void testInspectionVerifyThatMessageIsShownTotalIsOverLimitation() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVehicleScreen vehiclescreen =  myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(VIN);

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
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

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedservicedetailscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServiceQuantityValue("987");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedServicesScreen.clickSaveAsDraft();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 42372:Inspections: Regular - Verify approved amount for Inspection created from SR", description = "Verify approved amount for Inspection created from SR")
	public void testVerifyApprovedAmountForInspectionCreatedFromSR() {
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
        servicesscreen = new RegularServicesScreen();
        RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.clickSave();
		Assert.assertTrue(Helpers.getAlertTextAndCancel().contains(AlertsCaptions.ALERT_CREATE_APPOINTMENT));

		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
        RegularVisualInteriorScreen visualInteriorScreen =  servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		String inspectionnumber = vehiclescreen.getInspectionNumber();
        questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen();
		
		teaminspectionsscreen.selectInspectionForApprove(inspectionnumber);
		teaminspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspectionnumber);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Hood)");
		approveinspscreen.selectInspectionServiceToDecline(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		approveinspscreen.clickSaveButton();		
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		teaminspectionsscreen = new RegularTeamInspectionsScreen();
		BaseUtils.waitABit(2000);
		Assert.assertTrue(teaminspectionsscreen.checkInspectionIsApproved(inspectionnumber));
		Assert.assertEquals(teaminspectionsscreen.getFirstInspectionAprovedPriceValue(), "$2,000.00");
		Assert.assertEquals(teaminspectionsscreen.getFirstInspectionPriceValue(), "$2,050.00");
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();
	}
	
	private String inspnumber47249 = "";
	
	@Test(testName = "Test Case 47249:Inspections: Regular - Verify that on Price matrix step sub total value is shown correctly", 
			description = "Verify that on Price matrix step sub total value is shown correctly")
	public void testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly()  {
		
		final String VIN  = "1D7HW48NX6S507810";		
		final String _pricematrix1 = "Hood";
		final String defprice = "100";
		final String timevalue = "20";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		inspnumber47249 = vehiclescreen.getInspectionNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularPriceMatrixScreen pricematrix = questionsscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix1);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setPrice(defprice);
		vehiclePartScreen.clickDiscaunt("SR_S5_Mt_Upcharge_20");
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.saveSelectedServiceDetails();
		//vehiclePartScreen.getPriceMatrixVehiclePartSubTotalPrice()
		//vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(vehiclePartScreen.getPriceMatrixVehiclePartSubTotalPrice(), "$120.00");
		//vehiclePartScreen= pricematrix.selectPriceMatrix(_pricematrix1);
		vehiclePartScreen.clickDiscaunt("SR_S5_Mt_Upcharge_25");
		selectedservicescreen.saveSelectedServiceDetails();
		//vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(vehiclePartScreen.getPriceMatrixVehiclePartSubTotalPrice(), "$145.00");
		//vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix1);
		//vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.clickDiscaunt("SR_S5_Mt_Discount_10");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(vehiclePartScreen.getPriceMatrixVehiclePartSubTotalPrice(), "$130.50");
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$130.50");

        pricematrix.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.MATRIX_LABOR);
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix1);
		vehiclePartScreen.setSizeAndSeverity("DIME", "VERY LIGHT");
		vehiclePartScreen.setTime(timevalue);
		vehiclePartScreen.clickDiscaunt("SR_S5_Mt_Discount_10");
		selectedservicescreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$90.00");
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix1);
		vehiclePartScreen.clickDiscaunt("SR_S5_Mt_Upcharge_25");
		selectedservicescreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(pricematrix.getInspectionSubTotalPrice(), "$112.50");
		pricematrix.saveWizard();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspnumber47249), "$293.00");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 47257:WO: Regular - Verify that on Price Matrix step sub total is shown correctly", 
			description = "Verify that on Price Matrix step sub total is shown correctly"/*,
			dependsOnMethods = { "testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly" }*/)
	public void testVerifyThatOnPriceMatrixStepSubTotalIsShownCorrectly() {
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForApprove(inspnumber47249);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber47249);
		approveinspscreen.approveInspectionApproveAllAndSignature();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber47249);
		RegularVehicleScreen vehicleScreen = myinspectionsscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_MONITOR);
        vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreen servicesscreen = new RegularServicesScreen();
        Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$293.00");
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues("Dent Removal", "$112.50"));
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues("Dent Removal", "$130.50"));
		selectedServicesScreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}
	
	private String inspnumber48543 = "";
	
	@Test(testName = "Test Case 48543:Inspections: Regular - Verify that part services with different configurations are correctly shown for inspection", 
			description = "Inspections: Regular - Verify that part services with different configurations are correctly shown for inspection")
	public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_WITH_PART_SERVICES);
		vehiclescreen.setVIN(VIN);
		inspnumber48543 = vehiclescreen.getInspectionNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSaveAsDraft();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionForEdit(inspnumber48543);
		vehiclescreen = new RegularVehicleScreen();
        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Category");
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Engine");
		selectedservicescreen.selectServicePartSubcategory("Filters");
		selectedservicescreen.selectServicePartSubcategoryPart("Engine Oil Filter");
		selectedservicescreen.selectServicePartSubcategoryPosition("Oil Cooler");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("2.35");
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Name");
		//selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.setServicePriceValue("2.5");
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_None");
		selectedservicescreen.clickServicePartCell();
		selectedservicescreen.selectServicePartCategory("Body");
		selectedservicescreen.selectServicePartSubcategory("Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper");
		selectedservicescreen.selectServicePartSubcategoryPosition("Rear Upper");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("5.09");
		//selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.answerQuestionCheckButton();		
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_several");
		selectedservicescreen.clickServicePartCell();
		selectedservicescreen.selectCategory("Air and Fuel Delivery");
		selectedservicescreen.selectServicePartSubcategory("Filters");
		selectedservicescreen.selectServicePartSubcategoryPart("Air Filter");
		selectedservicescreen.selectServicePartSubcategoryPosition("Inner");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.setServicePriceValue("7");
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_SubCategory");
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Body");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper Air Shield");
		selectedservicescreen.selectServicePartSubcategoryPosition("Front Lower");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.setServicePriceValue("4.31");
		selectedservicescreen.saveSelectedServiceDetails();

        RegularVisualInteriorScreen visualinteriorscreen =vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, ScreenNamesConstants.FUTURE_AUDI_CAR);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService("Detail");
		visualinteriorscreen.selectSubService("Oksi_Part_SubCategory");
		Helpers.tapRegularCarImage();
		Helpers.tapRegularCarImage();
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Body");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper Assembly");
		selectedservicescreen.selectServicePartSubcategoryPosition("Front");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("6.43");
		selectedservicescreen.saveSelectedServiceDetails();

        RegularPriceMatrixScreen pricematrix = visualinteriorscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, "PM_New");
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setPrice("10");
		vehiclePartScreen.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Engine");
		selectedservicescreen.selectServicePartSubcategory("Electrical Connectors");
		selectedservicescreen.selectServicePartSubcategoryPart("Engine Brake Relay Connector");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Brake Relay Connector (N/A)");
		selectedservicescreen.setServicePriceValue("12.44");
		selectedservicescreen.saveSelectedServiceDetails();

		vehiclePartScreen.clickDiscaunt("Oksi_Part_Name");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.clickServicePartCell();		
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Belts and Cooling");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Hardware");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("2.13");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartSubTotalPrice(), "$24.57");

		vehiclePartScreen.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();	
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$89.13");
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$49.45");
		servicesscreen.clickSaveAsFinal();
		Helpers.acceptAlert();
		selectedservicescreen = servicesscreen.openGroupServiceDetails("Test Service with req question");
		selectedservicescreen.answerQuestion2("A3");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.selectVehiclePart("Front Bumper");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.saveSelectedServiceDetails();	
		servicesscreen.saveAsFinal();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspnumber48543), "$89.13");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 48562:WO: Regular - Verify that part services are copied from insp to order", 
			description = "WO: Regular - Verify that part services are copied from insp to order")
	public void testWOVerifyThatPartServicesAreCopiedFromInspToOrder() {
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber48543);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber48543);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber48543);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectWorkOrderType(WorkOrdersTypes.WO_WITH_PART_SERVICE);
		String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$77.13");
        RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		
		ordersummaryscreen.saveWizard();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$77.13");
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 48563:WO: Regular - Verify that part services with different configurations are correctly shown for WO", 
			description = "WO: Regular - Verify that part services with different configurations are correctly shown for WO")
	public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();

		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_WITH_PART_SERVICE);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
        vehiclescreen = new RegularVehicleScreen();
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Category");
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Engine");
		selectedservicescreen.selectServicePartSubcategory("Filters");
		selectedservicescreen.selectServicePartSubcategoryPart("Engine Oil Filter");
		selectedservicescreen.selectServicePartSubcategoryPosition("Oil Cooler");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("2.35");
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Name");
		//selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.setServicePriceValue("2.5");
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_None");
		selectedservicescreen.clickServicePartCell();
		selectedservicescreen.selectServicePartCategory("Body");
		selectedservicescreen.selectServicePartSubcategory("Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper");
		selectedservicescreen.selectServicePartSubcategoryPosition("Rear Upper");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("5.09");
		selectedservicescreen.answerQuestionCheckButton();		
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_several");
		selectedservicescreen.clickServicePartCell();
		selectedservicescreen.selectCategory("Air and Fuel Delivery");
		selectedservicescreen.selectServicePartSubcategory("Filters");
		selectedservicescreen.selectServicePartSubcategoryPart("Air Filter");
		selectedservicescreen.selectServicePartSubcategoryPosition("Inner");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.setServicePriceValue("7");
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_SubCategory");
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Body");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Bumper");
		selectedservicescreen.selectServicePartSubcategoryPart("Bumper Air Shield");
		selectedservicescreen.selectServicePartSubcategoryPosition("Front Lower");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.setServicePriceValue("4.31");
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("PM_New");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen =  pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setPrice("10");
		vehiclePartScreen.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.clickServicePartCell();
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Engine");
		selectedservicescreen.selectServicePartSubcategory("Electrical Connectors");
		selectedservicescreen.selectServicePartSubcategoryPart("Engine Brake Relay Connector");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Brake Relay Connector (N/A)");
		selectedservicescreen.setServicePriceValue("12.44");
		selectedservicescreen.saveSelectedServiceDetails();

		vehiclePartScreen.clickDiscaunt("Oksi_Part_Name");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.clickServicePartCell();		
		Assert.assertEquals(selectedservicescreen.getServicePartCategoryValue(), "Belts and Cooling");
		Assert.assertEquals(selectedservicescreen.getServicePartSubCategoryValue(), "Hardware");
		selectedservicescreen.saveSelectedServiceDetails();	
		selectedservicescreen.setServicePriceValue("2.13");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartSubTotalPrice(), "$24.57");

		vehiclePartScreen.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartSubTotalPrice(), "$49.45");
		pricematrix.clickSave();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();	
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$70.70");
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 45100:WO: Regular - Verify rounding in calculation script with price matrix", 
			description = "WO: Regular - Verify rounding in calculation script with price matrix")
	public void testWOVerifyRoundingInCalculationScriptWithPriceMatrix() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();

		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("Grill");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setPrice("975");
		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue("25");
		selectedservicescreen.saveSelectedServiceDetails();

		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicescreen.setServicePriceValue("192");
		selectedservicescreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicescreen.setServicePriceValue("-30");
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$987.52");

        RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO("12345");
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSave();
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
	
	private String invoicenumber45224 = null;
	
	@Test(testName = "Test Case 45224:WO: Regular - Verify calculation with price matrix Labor type", 
			description = "WO: Regular - Verify calculation with price matrix Labor type")
	public void testWOVerifyCalculationWithPriceMatrixLaborType_1() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();

		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicedetailscreen.setServicePriceValue("90");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.changeAmountOfBundleService("100");
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TAX_DISCOUNT);
		selectedservicedetailscreen.setServicePriceValue("10");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.selectService("Matrix Service");
		RegularPriceMatricesScreen priceMatricesScreen = new RegularPriceMatricesScreen();
		RegularPriceMatrixScreen pricematrix = priceMatricesScreen.selectPriceMatrice("Test Matrix Labor");
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("123");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setTime("100");
		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();
        RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		ordersummaryscreen.clickInvoiceType("Invoice_AutoWorkListNet");
		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularInvoiceInfoScreen invoiceinfoscreen = questionsscreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
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
		
		invoicespage.setSearchInvoiceNumber(invoicenumber45224);
		invoicespage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicespage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		invoicespage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
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
	
	private String invoicenumber42803 = null;
	
	@Test(testName = "Test Case 42803:Invoices: Regular - Verify rounding money amount values", 
			description = "Invoices: Regular - Verify rounding money amount values")
	public void testInvoicesVerifyRoundingMoneyAmountValues_1() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();

		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen =  pricematrix.selectPriceMatrix("Grill");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setPrice("100");
		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.OKSI_SERVICE_PP_FLAT_FEE);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue("23");
		selectedservicescreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();
		servicesscreen.selectService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue("10");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.answerQuestion2("A3");
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_BUNDLE_PP);
		servicesscreen.openBundleServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE);
		selectedservicescreen.setServicePriceValue("25");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.openBundleServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicescreen.setServicePriceValue("5");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Grill");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.changeAmountOfBundleService("30");
		servicesscreen.clickSave();
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		selectedservicescreen.setServicePriceValue("3.8");
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$169.19");
        RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForApprove(wonumber);
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.clickApproveButton();
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		myworkordersscreen.clickInvoiceIcon();
        RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("12345");
		invoicenumber42803 = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}
	
	
	@Test(testName = "Test Case 42803:Invoices: Regular - Verify rounding money amount values", 
			description = "Invoices: Regular - Verify rounding money amount values")
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
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicespage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		invoicespage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
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
	public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels() {

		final String VIN  = "1D7HW48NX6S507810";
		final String serviceprice = "21";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_WITH_0_PRICE);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();

		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE), "$0.00");
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();

		myinspectionsscreen.selectInspectionForEdit(inspnumber);
        vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		//servicesscreen.selectServicePanel("Buff");
		RegularSelectedServiceDetailsScreen selectedservicescreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE);
		selectedservicescreen.setServicePriceValue(serviceprice);
		selectedservicescreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		//servicesscreen.clickBackServicesButton();
		selectedServicesScreen.saveWizard();

		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(iOSInternalProjectConstants.SERVICE_REQ_0_PRICE), PricesCalculations.getPriceRepresentation(serviceprice));
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40463:Inspections: Regular - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when appove inspection", 
			description = "Inspections: Regular - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when appove inspection")
	public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection() {
		
		final String VIN  = "1D7HW48NX6S507810";

		
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		selectedServiceBundleScreen.selectBundle("SR_S4_Bl_I1_M");
		selectedservicedetailscreen = selectedServiceBundleScreen.openBundleInfo ("SR_S4_Bl_I2_M");
		selectedservicedetailscreen.setServicePriceValue("200");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen.setServicePriceValue("13");
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.answerQuestion2("A3");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$2,688.00");
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Roof)");
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S4_BUNDLE);
		approveinspscreen.selectInspectionServiceToSkip("3/4\" - Penny Size");
		approveinspscreen.selectInspectionServiceToSkip(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE + " (Back Glass)");
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspnumber), "$2,688.00");
		Assert.assertEquals(myinspectionsscreen.getInspectionApprovedPriceValue(inspnumber), "$2,650.00");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40466:Inspections: Regular - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when decline inspection", 
			description = "Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when decline inspection")
	public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection() {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(VIN);
		String inspectionnumber = vehiclescreen.getInspectionNumber();
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();		
		
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S4_BUNDLE);
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		selectedServiceBundleScreen.selectBundle("SR_S4_Bl_I1_M");
		selectedservicedetailscreen = selectedServiceBundleScreen.openBundleInfo ("SR_S4_Bl_I2_M");
		selectedservicedetailscreen.setServicePriceValue("200");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen.clickVehiclePartsCell();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.answerQuestion2("A3");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspectionnumber);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.clickFilterButton();
		myinspectionsscreen.clickStatusFilter();
		myinspectionsscreen.clickFilterStatus("Declined");
		myinspectionsscreen.clickBackButton();
		myinspectionsscreen.clickSaveFilterDialogButton();
		Assert.assertEquals(myinspectionsscreen.getInspectionApprovedPriceValue(inspectionnumber), "$0.00");
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspectionnumber), "$2,638.00");
		myinspectionsscreen.clickHomeButton();		
	}
	
	@Test(testName = "Test Case 35030:WO: Regular - Verify that for bundle items price policy is applied",
			description = "WO: Regular - Verify that for bundle items price policy is applied")
	public void testWOVerifyThatForBundleItemsPricePolicyIsApplied() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();

		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("Oksi_Bundle_PP");
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		Assert.assertEquals(selectedservicebundlescreen.getServiceDetailsPriceValue(), "$13.00");
		
		selectedservicedetailscreen = selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE);
		selectedservicedetailscreen.setServiceQuantityValue("3");
		selectedservicedetailscreen.setServicePriceValue("10");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedservicedetailscreen.getVehiclePartValue().contains("Hood"));
		Assert.assertTrue(selectedservicedetailscreen.getVehiclePartValue().contains("Deck Lid"));	
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		Assert.assertEquals(selectedservicebundlescreen.getServiceDetailsPriceValue(), "$73.00");
		
		selectedservicedetailscreen = selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.selectVehiclePart("Driver Seat");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		Assert.assertEquals(selectedservicebundlescreen.getServiceDetailsPriceValue(), "$97.00");
		
		selectedservicedetailscreen = selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.SR_S5_MT_DISCOUNT_10);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		Assert.assertEquals(selectedservicebundlescreen.getServiceDetailsPriceValue(), "$87.30");
		
		selectedservicedetailscreen = selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicedetailscreen.setServicePriceValue("10");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("You can add only one service 'Service_PP_Vehicle_not_multiple'"));
		selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		Assert.assertEquals(selectedservicebundlescreen.getServiceDetailsPriceValue(), "$96.30");
		
		//servicesscreen.selectPriceMatrices(iOSInternalProjectConstants.OKSI_SERVICE_PP_LABOR);
		selectedservicedetailscreen = selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.OKSI_SERVICE_PP_LABOR);
		selectedservicedetailscreen.setServiceTimeValue("3");
		selectedservicedetailscreen.setServiceRateValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		Assert.assertEquals(selectedservicebundlescreen.getServiceDetailsPriceValue(), "$163.80");

		selectedservicebundlescreen.changeAmountOfBundleService("163.80");
		selectedservicedetailscreen.saveSelectedServiceDetails();
        servicesscreen = new RegularServicesScreen();
        RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40541:WO: Regular - Verify that for Sales Tax Service data is set from DB when create WO for customer with appropriate data", 
			description = "WO: Regular - Verify that for Sales Tax Service data is set from DB when create WO for customer with appropriate data")
	public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectOnlineCustomer("Avalon");
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
        servicesscreen = new RegularServicesScreen();
        RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        servicesscreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedservicedetailscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		Assert.assertFalse(selectedservicedetailscreen.isServiceDetailsFieldEditable("State Rate"));
		selectedservicedetailscreen.saveSelectedServiceDetails();

		selectedservicedetailscreen = selectedServicesScreen.openCustomServiceDetails("Sales Tax Override");
		selectedservicedetailscreen.setServiceDetailsFieldValue("State Rate", "6");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsFieldValue("State Rate"), "%6.000");
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsTotalValue(), "%9.250");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 49680:Invoices: Regular - Verify that tax rounding is correctly calculated", 
			description = "Invoices: Regular - Verify that tax rounding is correctly calculated")
	public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		
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
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedservicedetailscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.TAX_DISCOUNT);
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), "$178.20");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$3,742.10");

		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 49915:Inspections: Regular - Verify that tax is calc correctly from services with tax exempt YES/No", 
			description = "Inspections: Regular - Verify that tax is calc correctly from services with tax exempt YES/No")
	public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo() {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String inspectionnumber = vehiclescreen.getInspectionNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new RegularVehicleScreen();
        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Money_Tax_Exempt");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Driver Seat");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$100.00");

		servicesscreen.selectSubService(iOSInternalProjectConstants.SALES_TAX);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$100.00");

		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$205.00");

		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Money_Discount_Exempt");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$310.00");

		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$289.50");

		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Money_TE_DE");
		selectedservicedetailscreen.setServicePriceValue("100");
		selectedservicedetailscreen.saveSelectedServiceDetails();	
		selectedservicedetailscreen.selectVehiclePart("Deck Lid");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
			
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$389.50");
		servicesscreen.saveWizard();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspectionnumber), "$389.50");
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 49945:WO:Regular - Verify that package price overrides client (retail or wholesale)", 
			description = "WO:Regular - Verify that package price overrides client (retail or wholesale)")
	public void testWOVerifyThatPackagePriceOverridesClient_RretailOrWholesale() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber1 = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber1);
		wonumber1 = vehiclescreen.getWorkOrderNumber();
        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("Money_Pack_Price");
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$4.07");
		servicesscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		String wonumber2 = vehiclescreen.getWorkOrderNumber();

        ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber2);
		vehiclescreen = new RegularVehicleScreen();
        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		
		servicesscreen.selectSubService("Money_Pack_Price");
		toolaber = new RegularInspectionToolBar();		
		servicesscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrder(wonumber2);	
		myworkordersscreen.clickChangeCustomerPopupMenu();
		customersscreen.swtchToRetailMode();
		customersscreen.selectCustomer("Avalon");
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		Assert.assertFalse(myworkordersscreen.woExists(wonumber2), "Can't find work order: " + wonumber2);
		myworkordersscreen.clickHomeButton();
		
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.selectWorkOrderForEidt(wonumber2);
		vehiclescreen = new RegularVehicleScreen();
        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$12.00");

		servicesscreen.selectSubService("Money_Pack_Price");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$35.00");
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 50013:WO: Regular - Verify that client and job overrides are working fine for WO", 
			description = "WO: Regular - Verify that client and job overrides are working fine for WO")
	public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new RegularHomeScreen();			
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderTypeWithJob(WorkOrdersTypes.WO_TYPE_WITH_JOB, "Job for test");
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService("Money_client_override");
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$4.79");
		
		servicesscreen.selectSubService("Money_job_override");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$13.84");
		servicesscreen.saveWizard();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$13.84");
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 49856:SR: Regular - Verify ALM flow when approve both inspectiontypes",
			description = "SR: Regular - Verify ALM flow when approve both inspectiontypes")
	public void testSRVerifyALMFlowWhenApproveBothInspections() {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.saveWizard();
		servicerequestsscreen = new RegularServiceRequestsScreen();
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
		RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen();
		String inspnumber1 = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getInspectionTypeValue(inspnumber1), "ALM - Recon Inspection");
		myinspectionsscreen.selectInspectionForEdit(inspnumber1);
        RegularQuestionsScreen questionsscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
        servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclescreen = new RegularVehicleScreen();
        questionsscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
        servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();

		myinspectionsscreen.selectInspectionForAction(inspnumber1);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber1);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber2);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen();
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
		RegularMyWorkOrdersScreen myworkordersscreen = new RegularMyWorkOrdersScreen();
		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$37.00");
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderTypeValue(), "ALM - Recon Facility");

        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$12.00 x 1.00"));
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$25.00 x 1.00"));
		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 49895:SR: Regular - Verify ALM flow when approve one inspection and one decline", 
			description = "SR: Regular - Verify ALM flow when approve one inspection and one decline")
	public void testSRVerifyALMFlowWhenApproveOneInspectionAndOneDecline() {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.saveWizard();

		servicerequestsscreen = new RegularServiceRequestsScreen();
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
		RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen();
		String inspnumber1 = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getInspectionTypeValue(inspnumber1), "ALM - Recon Inspection");
		myinspectionsscreen.selectInspectionForEdit(inspnumber1);
		vehiclescreen = new RegularVehicleScreen();
        RegularQuestionsScreen questionsscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
        servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclescreen = new RegularVehicleScreen();
        questionsscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
        servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen.selectInspectionForAction(inspnumber1);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber1);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber2);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen();
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
		RegularMyWorkOrdersScreen myworkordersscreen = new RegularMyWorkOrdersScreen();
		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$12.00");
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderTypeValue(), "ALM - Recon Facility");

        servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$12.00 x 1.00"));
		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();
		servicerequestsscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 49896:SR: Regular - Verify ALM flow when decline both inspectiontypes",
			description = "SR: Regular - Verify ALM flow when decline both inspectiontypes")
	public void testSRVerifyALMFlowWhenDeclineBothInspections() {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");

        RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.saveWizard();

		servicerequestsscreen = new RegularServiceRequestsScreen();
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
		RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen();
		String inspnumber1 = myinspectionsscreen.getFirstInspectionNumberValue();
		Assert.assertEquals(myinspectionsscreen.getInspectionTypeValue(inspnumber1), "ALM - Recon Inspection");
		myinspectionsscreen.selectInspectionForEdit(inspnumber1);
		vehiclescreen = new RegularVehicleScreen();
        RegularQuestionsScreen questionsscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
        servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclescreen = new RegularVehicleScreen();
        questionsscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedservicedetailscreen.setServicePriceValue("25");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen.selectInspectionForAction(inspnumber1);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber1);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason("Decline 2");
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber2);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen();
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
		homescreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
        RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		String inspnumber = vehiclescreen.getInspectionNumber();

        RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

        RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		selectedServiceBundleScreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo (iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedServiceDetailsScreen.setServiceQuantityValue("2");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

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
        RegularPriceMatrixScreen pricematrix = servicesscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("Hood");
		vehiclePartScreen.setSizeAndSeverity("DIME", "HEAVY");
		vehiclePartScreen.saveVehiclePart();

        RegularVisualInteriorScreen visualinteriorscreen = pricematrix.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, "Future Sport Car");
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		RegularVisualInteriorScreen.tapInteriorWithCoords(150, 200);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 250);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 300);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 350);
        pricematrix = visualinteriorscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.MATRIX_LABOR);
		vehiclePartScreen = pricematrix.selectPriceMatrix("Back Glass");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setTime("34");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.saveWizard();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();	
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
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
        servicesscreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.SERVICES);
        RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isServiceApproved(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.TAX_DISCOUNT));
		selectedServicesScreen.cancelWizard();
		myinspectionsscreen.clickOnInspection(inspnumber);
		EmailScreen mailscreen = myinspectionsscreen.clickSendEmail();

		NadaEMailService nada = new NadaEMailService();
		nada.setEmailId("test.cyberiansoft@getnada.com");
		mailscreen.sendInvoiceOnEmailAddress("test.cyberiansoft@getnada.com");
		myinspectionsscreen.clickHomeButton();
        DriverBuilder.getInstance().getAppiumDriver().closeApp();
		final String inspreportfilenname = inspnumber + ".pdf";

		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(inspnumber, inspreportfilenname);
		Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find inspection: " + inspnumber +
				" in mail box " + nada.getEmailId() + ". At time " +
				LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		nada.deleteMessageWithSubject(inspnumber);

		File pdfdoc = new File(inspreportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains("(Declined) Disc_Ex_Service1 ($50.00) 1.00"));
		Assert.assertTrue(pdftext.contains("(Declined) Test service zayats ($5.00) (123) 1.00"));
		Assert.assertTrue(pdftext.contains("(Declined) Test service zayats ($5.00) (Sunroof) 1.00"));
		Assert.assertTrue(pdftext.contains("Bundle1_Disc_Ex 1.00"));
		Assert.assertTrue(pdftext.contains("(Declined) Discount 5-10% ($-346.00) (10.000%)"));
		Assert.assertTrue(pdftext.contains("(Declined) SR_S1_Money ($2000.00) (Grill) 1.00"));
		Assert.assertTrue(pdftext.contains("(Skipped) SR_S1_Money_Panel ($1000.00) (Grill) 1.00"));
		Assert.assertTrue(pdftext.contains("(Declined) Tax discount ($165.70) 5.000%"));
		Assert.assertTrue(pdftext.contains("(Skipped) Dent Removal ($0.00) 1.00"));
		Assert.assertTrue(pdftext.contains("Wheel 1.00"));
		Assert.assertTrue(pdftext.contains("(Declined) Wheel ($70.00) 1.00"));
		Assert.assertTrue(pdftext.contains("(Skipped) Wheel ($70.00) 1.00"));
		Assert.assertTrue(pdftext.contains("Wheel 1.00"));
		Assert.assertTrue(pdftext.contains("(Declined) Dent Removal ($170.00) 1.00"));

		try {
            DriverBuilder.getInstance().getAppiumDriver().launchApp();
		} catch (UnsupportedCommandException e) {

			System.out.println("Can't launch app");
		}
		RegularMainScreen mainscr = new RegularMainScreen();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
}

package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.ServicePartSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import io.appium.java_client.MobileBy;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSHDCalculationsTestCases extends ReconProBaseTestCase {

	private HomeScreen homescreen;
	
	@BeforeClass
	public void setUpSuite() {

		JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCalculationsTestCasesDataPath();

		mobilePlatform = MobilePlatform.IOS_HD;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);


		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Vit_Iph",
				envType);
		MainScreen mainscr = new MainScreen();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.setInsvoicesCustomLayoutOff();
		settingsscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionOnTheDeviceWithApprovalRequired(String rowID,
																				   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen =  myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		final String inspNumber = vehiclescreen.getInspectionNumber();
		ClaimScreen claimScreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimScreen.waitClaimScreenLoad();
		VisualInteriorScreen visualinteriorscreen = claimScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualinteriorscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualinteriorscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		visualinteriorscreen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttxt, AlertsCaptions.ALERT_VIN_REQUIRED);
			
		vehiclescreen = new VehicleScreen();
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		visualinteriorscreen.clickSave();
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttxt, AlertsCaptions.ALERT_MAKE_REQUIRED);
			
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehiclescreen.saveWizard();
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateRetailInspection(String rowID,
																	String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				InspectionsTypes.DEFAULT_INSPECTION_TYPE);
		VehicleScreen vehiclescreen = new VehicleScreen();
		vehiclescreen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttxt, AlertsCaptions.ALERT_VIN_REQUIRED);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		vehiclescreen.clickSave();
		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttxt, AlertsCaptions.ALERT_MAKE_REQUIRED);
		
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		VisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualinteriorscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualinteriorscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		vehiclescreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testAddServicesToVisualInspection(String rowID,
										   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickEditInspectionButton();
		VehicleScreen vehiclescreen = new VehicleScreen();
		final String inspNumber = vehiclescreen.getInspectionNumber();
		VisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		Assert.assertTrue(visualinteriorscreen.isInteriorServicePresent(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE));
		Assert.assertTrue(visualinteriorscreen.isInteriorServicePresent(iOSInternalProjectConstants.PRICE_ADJUSTMENT));
		Assert.assertTrue(visualinteriorscreen.isInteriorServicePresent(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE));
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
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualinteriorscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		visualinteriorscreen.tapExterior();
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		visualinteriorscreen.saveWizard();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspNumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeQuantityOfServicesInVisualInspection(String rowID,
												  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickEditInspectionButton();
		VehicleScreen vehiclescreen = new VehicleScreen();
		VisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(inspectionData.getDamagesData().get(0).getDamageGroupName());
		visualinteriorscreen.selectSubService(inspectionData.getDamagesData().get(0).getMoneyServiceData().getServiceName());
		visualinteriorscreen.tapInterior();
		visualinteriorscreen.tapInterior();
		visualinteriorscreen.setCarServiceQuantityValue(inspectionData.getDamagesData().get(0).getMoneyServiceData().getServiceQuantity());
		visualinteriorscreen.saveCarServiceDetails();
		Assert.assertEquals(visualinteriorscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		visualinteriorscreen = visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(inspectionData.getDamagesData().get(1).getDamageGroupName());
		visualinteriorscreen.selectSubService(inspectionData.getDamagesData().get(1).getMoneyServiceData().getServiceName());
		visualinteriorscreen.tapExterior();
		visualinteriorscreen.setCarServiceQuantityValue(inspectionData.getDamagesData().get(1).getMoneyServiceData().getServiceQuantity());
		visualinteriorscreen.saveCarServiceDetails();

		visualinteriorscreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyPanelThenItWillBeAddedOnceForManyAssociatedServiceInstancesWithSameVehiclePart(String rowID,
															   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		vehiclescreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			servicedetailsscreen.setServicePriceValue(serviceData.getServicePrice());
			servicedetailsscreen.clickVehiclePartsCell();
			servicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			servicedetailsscreen.saveSelectedServiceDetails();
			servicedetailsscreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesscreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	private String wonumber28583 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_1(String rowID,
																															 String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		final String totalSale = "1";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_FEE_ITEM_IN_2_PACKS);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		vehiclescreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		wonumber28583 = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), workOrderData.getServiceData().getServicePrice());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalSale);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();				
	}
	
	@Test(testName="Test Case 28583:WO: HD - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity", 
			description = "WO: HD - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity")
	public void testHDIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_2()
			 {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.makeSearchPanelVisible();
		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workorderspage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		workorderspage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatPackagePriceOfFeeBundleItemIsOverrideThePriceOfWholesaleAndRetailPrices(String rowID,
																																							   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FEE_PRICE_OVERRIDE);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		vehiclescreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances(String rowID,
																										String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		final String questionAnswer = "A3";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		vehiclescreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesscreen =vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			servicedetailsscreen.setServicePriceValue(serviceData.getServicePrice());
			servicedetailsscreen.clickVehiclePartsCell();
			servicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			servicedetailsscreen.saveSelectedServiceDetails();
			servicedetailsscreen.answerQuestion2(questionAnswer);
			servicedetailsscreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesscreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyServiceOrFlatFeeThenItWillBeAddedToWOEveryTimeWhenAssociatedServiceInstanceWillAddToWO(String rowID,
																													String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		vehiclescreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			servicedetailsscreen.setServicePriceValue(serviceData.getServicePrice());
			servicedetailsscreen.clickVehiclePartsCell();
			servicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			servicedetailsscreen.saveSelectedServiceDetails();
			servicedetailsscreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesscreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatForWholesaleAndRetailCustomersFeeIsAddedDependsOnThePriceAccordinglyToPriceOfTheFeeBundleItem(String rowID,
																																	   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		vehiclescreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			servicedetailsscreen.setServicePriceValue(serviceData.getServicePrice());
			servicedetailsscreen.clickVehiclePartsCell();
			servicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			servicedetailsscreen.saveSelectedServiceDetails();
			servicedetailsscreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesscreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	private String wonumber29398 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_1(String rowID,
																						 String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		wonumber29398 = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		servicesscreen.selectService(workOrderData.getMatrixServiceData().getMatrixServiceName());
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartName());
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
		for (ServiceData serviceData : workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalServices())
			pricematrix.selectDiscaunt(serviceData.getServiceName());

		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartTotalPrice(), workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTotalPrice());
		pricematrix.clickSaveButton();
		InspectionToolBar toolabar = new InspectionToolBar();
		Assert.assertEquals(toolabar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();		
	}
	
	@Test(testName="Test Case 29398:WO: HD - Verify that Fee Bundle services is calculated for additional matrix services", 
			description = "Verify that Fee Bundle services is calculated for additional matrix services")
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_2()
			 {
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_095(String rowID,
																						 String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVinNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())  {
			servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedservicedetailsscreen = new SelectedServiceDetailsScreen();
			selectedservicedetailsscreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedservicedetailsscreen.clickVehiclePartsCell();
				selectedservicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			}
			selectedservicedetailsscreen.saveSelectedServiceDetails();

			if (serviceData.getServicePrice2() != null) {
				selectedservicedetailsscreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedservicedetailsscreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			}
		}

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_003(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVinNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())  {
			servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedservicedetailsscreen = new SelectedServiceDetailsScreen();
			selectedservicedetailsscreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedservicedetailsscreen.clickVehiclePartsCell();
				selectedservicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			}
			selectedservicedetailsscreen.saveSelectedServiceDetails();

			if (serviceData.getServicePrice2() != null) {
				selectedservicedetailsscreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedservicedetailsscreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			}
		}

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_005(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVinNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())  {
			servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedservicedetailsscreen = new SelectedServiceDetailsScreen();
			selectedservicedetailsscreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedservicedetailsscreen.clickVehiclePartsCell();
				selectedservicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			}
			selectedservicedetailsscreen.saveSelectedServiceDetails();

			if (serviceData.getServicePrice2() != null) {
				selectedservicedetailsscreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedservicedetailsscreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			}
		}

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	private String wonumber31498 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_1(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		final String totalSale = "2";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		wonumber31498 = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedservicedetailscreen.setServicePriceValue(serviceData.getServicePrice());
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}

		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalSale);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31498:WO: HD - Verify that amount is calculated and rounded correctly", 
			description = "Verify that amount is calculated and rounded correctly")
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_2()
			 {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
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
		wopage.waitABit(5000);
		Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$3,153.94"));

		woinfowebpage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	private String inspectionnumber32226 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_1(String rowID,
															   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		QuestionsScreen questionsscreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_SIMPLE);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");

		VehicleScreen vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		inspectionnumber32226 = vehiclescreen.getInspectionNumber();
		PriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		for(VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
			pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());
			pricematrix.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
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
		approveinspscreen.selectStatusReason(inspectionData.getDeclineReason());
		//Helpers.acceptAlert();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32226:Inspections: HD - Verify that inspection is saved as declined when all services are skipped or declined", 
			description = "Verify that inspection is saved as declined when all services are skipped or declined")
	public void testHDVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_1(String rowID,
																								 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(inspectionData.getVinNumber());

		inspectionnumber32286 = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			servicesscreen.selectService(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen();
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}

        servicesscreen = new ServicesScreen();
        servicesscreen.cancelSearchAvailableService();
        servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForAction(inspectionnumber32286);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionnumber32286);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			approveinspscreen.selectApproveInspectionServiceStatus(serviceData);
		}

		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32286:Inspections: HD - Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount")
	public void testHDVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_2()  {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
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
	
	private String inspectionnumber32287 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_1(String rowID,
																		  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		inspectionnumber32287 = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
        servicesscreen.cancelSearchAvailableService();
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForApprove(inspectionnumber32287);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionnumber32287);
		approveinspscreen.clickSkipAllServicesButton();		
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason(inspectionData.getDeclineReason());
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
		
		myinspectionsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32287:Inspections: HD - Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount")
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_2() {

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
        inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        inspectionspage.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());
		inspectionspage.selectSearchStatus("Declined");
		inspectionspage.searchInspectionByNumber(inspectionnumber32287);		
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspectionnumber32287), "$0.00");
		Assert.assertEquals(inspectionspage.getInspectionApprovedTotal(inspectionnumber32287), "$0.00");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionVerifyServicesAreMarkedAsStrikethroughWhenExcludeFromTotal(String rowID,
																		  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.selectVehicleParts(serviceData.getVehicleParts());
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}

		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
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
		Assert.assertEquals(myinspectionsscreen.getInspectionApprovedPriceValue(inspectionnumber), inspectionData.getInspectionPrice());
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatValueSelectedOnPriceMatrixStepIsSavedAndShownDuringEditMode(String rowID,
																						 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER,
				InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		vehiclescreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			PriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			pricematrix.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			pricematrix.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(),
					priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService() != null)
				pricematrix.selectDiscaunt(priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice() != null)
				pricematrix.setPrice(priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice());
			InspectionToolBar toolaber = new InspectionToolBar();
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(),priceMatrixScreenData.getMatrixScreenPrice());
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), priceMatrixScreenData.getMatrixScreenTotalPrice());
		}



		ServicesScreen servicesscreen =vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		servicesscreen.selectService(inspectionData.getServiceData().getServiceName());
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), inspectionData.getInspectionPrice());



		QuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), priceMatrixScreenData.getMatrixScreenPrice());

		}
		vehiclescreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																	String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		final String totalAmount = "3";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(workOrderData.getVinNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedservicedetailscreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		selectedservicedetailscreen.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity());
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart(workOrderData.getServiceData().getVehiclePart().getVehiclePartName());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalAmount);
		ordersummaryscreen.clickSave();
		
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_WO_IS_HUGE, workOrderData.getWorkOrderPrice()));
		ordersummaryscreen.swipeScreenLeft();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedservicedetailscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity2());
		
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																	String description, JSONObject testData) {

		Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		final String totalAmount = "3";
		final int workOrdersToCreate = 2;
		List<String> workOrders = new ArrayList<>();
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		for (int i = 0; i < workOrdersToCreate; i++) {
			VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
			vehiclescreen.setVIN(invoiceData.getWorkOrderData().getVinNumber());
			workOrders.add(vehiclescreen.getInspectionNumber());

			ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(invoiceData.getWorkOrderData().getServiceData().getServiceName());
			selectedservicedetailscreen.setServicePriceValue(invoiceData.getWorkOrderData().getServiceData().getServicePrice());
			selectedservicedetailscreen.setServiceQuantityValue(invoiceData.getWorkOrderData().getServiceData().getServiceQuantity());

			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.selectVehiclePart(invoiceData.getWorkOrderData().getServiceData().getVehiclePart().getVehiclePartName());
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.saveSelectedServiceDetails();

			OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			ordersummaryscreen.setTotalSale(totalAmount);
			ordersummaryscreen.saveWizard();
		}

		myworkordersscreen.approveWorkOrderWithoutSignature(workOrders.get(0), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		TeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrders.get(0));
		teamWorkOrdersScreen.clickInvoiceIcon();
        QuestionsScreen questionsScreen = teamWorkOrdersScreen.selectInvoiceType(InvoicesTypes.INVOICE_CUSTOM1);
		questionsScreen.waitQuestionsScreenLoaded();
		InvoiceInfoScreen invoiceinfoscreen = questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceinfoscreen.setPO(invoiceData.getInvoiceData().getInvoicePONumber());
		String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();

			myworkordersscreen.approveWorkOrderWithoutSignature(workOrders.get(1), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickHomeButton();

		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenum);

		myinvoicesscreen.clickEditPopup();
		questionsScreen = new QuestionsScreen();
		questionsScreen.waitQuestionsScreenLoaded();
		questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceinfoscreen.addWorkOrder(workOrders.get(1));
		invoiceinfoscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INVOICE_IS_HUGE, invoiceData.getInvoiceData().getInvoiceTotal()));
		invoiceinfoscreen.swipeScreenLeft();
		invoiceinfoscreen.cancelInvoice();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																						  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(inspectionData.getVinNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedservicedetailscreen.setServicePriceValue(inspectionData.getServiceData().getServicePrice());
		selectedservicedetailscreen.setServiceQuantityValue(inspectionData.getServiceData().getServiceQuantity());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart(inspectionData.getServiceData().getVehiclePart().getVehiclePartName());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();

		Assert.assertEquals(alerttext, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INSPECTION_IS_HUGE, inspectionData.getInspectionPrice()));
		selectedservicedetailscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.setServiceQuantityValue(inspectionData.getServiceData().getServiceQuantity2());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsDraft();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyApprovedAmountForInspectionCreatedFromSR(String rowID,
																			String description, JSONObject testData) {

		ServiceRequestData serviceRequestData = JSonDataParser.getTestDataFromJson(testData, ServiceRequestData.class);
		
		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehiclescreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		QuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		VisualInteriorScreen visualInteriorScreen = servicerequestsscreen.createInspectionFromServiceReques(srnumber, InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getInspectionData().getMoneyServiceData().getServiceName());
		SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart(serviceRequestData.getInspectionData().getMoneyServiceData().getVehiclePart().getVehiclePartName());
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
		approveinspscreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getMoneyServiceData());
		approveinspscreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getPercentageServiceData());
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();
		teaminspectionsscreen = new TeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionApproved(inspectionnumber));
		Assert.assertEquals(teaminspectionsscreen.getFirstInspectionPriceValue(), serviceRequestData.getInspectionData().getInspectionPrice());
		Assert.assertEquals(teaminspectionsscreen.getFirstInspectionTotalPriceValue(), serviceRequestData.getInspectionData().getInspectionTotalPrice());
		teaminspectionsscreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();		
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly(String rowID,
																	String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);

		vehiclescreen.setVIN(inspectionData.getVinNumber());

		final String inspectionNumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			PriceMatrixScreen pricematrix = questionsscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			VehiclePartData vehiclePartData = priceMatrixScreenData.getVehiclePartData();
			pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());
			if (vehiclePartData.getVehiclePartSize() != null)
				pricematrix.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
			if (vehiclePartData.getVehiclePartOption() != null)
				pricematrix.switchOffOption(vehiclePartData.getVehiclePartOption());
			if (vehiclePartData.getVehiclePartPrice() != null)
				pricematrix.setPrice(vehiclePartData.getVehiclePartPrice());
			if (vehiclePartData.getVehiclePartTime() != null)
				pricematrix.setTime(vehiclePartData.getVehiclePartTime());
			if (vehiclePartData.getVehiclePartTotalPrice() != null)
				pricematrix.setTime(vehiclePartData.getVehiclePartTime());
			for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
				pricematrix.clickDiscaunt(serviceData.getServiceName());
				SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
				selectedservicescreen.saveSelectedServiceDetails();
				InspectionToolBar toolaber = new InspectionToolBar();
				Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), serviceData.getServicePrice());
				Assert.assertEquals(toolaber.getInspectionTotalPrice(), serviceData.getServicePrice2());
			}
		}
		vehiclescreen.saveWizard();
		
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		myinspectionsscreen.selectInspectionForApprove(inspectionNumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.approveInspectionApproveAllAndSignature(inspectionNumber);

		VehicleScreen vehicleScreen = myinspectionsscreen.createWOFromInspection(inspectionNumber,
				WorkOrdersTypes.WO_SMOKE_MONITOR);
		ServicesScreen servicesscreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(serviceData.getServiceName(),
					serviceData.getServicePrice()));
		}
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_WITH_PART_SERVICES);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSaveAsDraft();
		
		myinspectionsscreen.selectInspectionForEdit(inspectionNumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedservicescreen.clickServicePartCell();
			ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			selectedservicescreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getQuestionData() != null) {
				selectedservicescreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedservicescreen.saveSelectedServiceDetails();
		}

		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), inspectionData.getMoneyServiceData().getServicePartData().getServicePartValue());
		selectedservicescreen.setServicePriceValue(inspectionData.getMoneyServiceData().getServicePrice());
		selectedservicescreen.saveSelectedServiceDetails();

		VisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, ScreenNamesConstants.FUTURE_AUDI_CAR);
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService(inspectionData.getDamageData().getDamageGroupName());
		visualinteriorscreen.selectSubService(inspectionData.getDamageData().getMoneyServiceData().getServiceName());
		visualinteriorscreen.tapCarImage();
		visualinteriorscreen.tapCarImage();
		selectedservicescreen = new SelectedServiceDetailsScreen();
		ServicePartPopup servicepartpopup = selectedservicescreen.clickServicePartCell();
		ServicePartSteps.selectServicePartData(inspectionData.getDamageData().getMoneyServiceData().getServicePartData());
		selectedservicescreen.setServicePriceValue(inspectionData.getDamageData().getMoneyServiceData().getServicePrice());
		selectedservicescreen.saveSelectedServiceDetails();
		final PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreenData();
		PriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
		final VehiclePartData vehiclePartData = priceMatrixScreenData.getVehiclePartData();
		pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		pricematrix.switchOffOption(vehiclePartData.getVehiclePartOption());
		pricematrix.setPrice(vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
			pricematrix.clickDiscaunt(serviceData.getServiceName());
			selectedservicescreen = new SelectedServiceDetailsScreen();
			selectedservicescreen.clickServicePartCell();
			ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			Assert.assertEquals(selectedservicescreen.getServicePartValue(), serviceData.getServicePartData().getServicePartValue());
			selectedservicescreen.setServicePriceValue(serviceData.getServicePrice());
			selectedservicescreen.saveSelectedServiceDetails();
		}

		pricematrix.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue(vehiclePartData.getVehiclePartAdditionalService().getServiceQuantity());
		selectedservicescreen.saveSelectedServiceDetails();
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), vehiclePartData.getVehiclePartTotalPrice());
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
		
		servicesscreen.clickSaveAsFinal();

		myinspectionsscreen.selectInspectionForAction(inspectionNumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionNumber);
		//approveinspscreen.selectInspectionForApprove(inspnumber48543);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneStatusReasonButton();

		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		vehiclescreen = myinspectionsscreen.createWOFromInspection(inspectionNumber,
				WorkOrdersTypes.WO_WITH_PART_SERVICE);
		String wonumber = vehiclescreen.getInspectionNumber();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

		ordersummaryscreen.saveWizard();
		homescreen = myinspectionsscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), workOrderData.getWorkOrderPrice());
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO(String rowID,
																											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_WITH_PART_SERVICE);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			ServicePartPopup servicepartpopup = selectedservicescreen.clickServicePartCell();
			ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			selectedservicescreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getQuestionData() != null) {
				selectedservicescreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedservicescreen.saveSelectedServiceDetails();
		}
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), workOrderData.getMoneyServiceData().getServicePartData().getServicePartValue());
		selectedservicescreen.setServicePriceValue(workOrderData.getMoneyServiceData().getServicePrice());
		selectedservicescreen.saveSelectedServiceDetails();
		
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesscreen.selectPriceMatrix(matrixServiceData.getMatrixServiceName());
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		pricematrix.switchOffOption(vehiclePartData.getVehiclePartOption());
		pricematrix.setPrice(vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
			pricematrix.clickDiscaunt(serviceData.getServiceName());
			selectedservicescreen = new SelectedServiceDetailsScreen();
			selectedservicescreen.clickServicePartCell();
			ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			Assert.assertEquals(selectedservicescreen.getServicePartValue(), serviceData.getServicePartData().getServicePartValue());
			selectedservicescreen.setServicePriceValue(serviceData.getServicePrice());
			selectedservicescreen.saveSelectedServiceDetails();
		}

		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartTotalPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
		pricematrix.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue(vehiclePartData.getVehiclePartAdditionalService().getServiceQuantity());
		selectedservicescreen.saveSelectedServiceDetails();
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(pricematrix.getPriceMatrixVehiclePartTotalPrice(), vehiclePartData.getVehiclePartTotalPrice());

		pricematrix.clickSaveButton();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		servicesscreen.saveWizard();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), workOrderData.getWorkOrderPrice());
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyRoundingInCalculationScriptWithPriceMatrix(String rowID,
																							  String description, JSONObject testData) {

		final String filterBillingValue = "All";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		SettingsScreen settingsScreen = homescreen.clickSettingsButton();
		settingsScreen.setInsvoicesCustomLayoutOff();
		settingsScreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesscreen.selectService(matrixServiceData.getMatrixServiceName());
		servicesscreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		pricematrix.switchOffOption(vehiclePartData.getVehiclePartOption());
		pricematrix.setPrice(vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
			pricematrix.clickDiscaunt(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen();
			selectedservicedetailscreen.setServicePriceValue(serviceData.getServicePrice());
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		pricematrix.clickSaveButton();

		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedservicedetailscreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		ordersummaryscreen.clickSave();
        InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(invoiceData.getInvoicePONumber());
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSave();
		Helpers.acceptAlert();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling(filterBillingValue);
		myworkordersscreen.clickSaveFilter();
		
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), workOrderData.getWorkOrderPrice());
		homescreen = myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertEquals(myinvoicesscreen.getPriceForInvoice(invoicenumber), invoiceData.getInvoiceTotal());
		myinvoicesscreen.clickHomeButton();
	}

	private String invoicenumber45224 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyCalculationWithPriceMatrixLaborType_1(String rowID,
																	String description, JSONObject testData) {
		final String filterBillingValue = "All";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();

		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getServicePrice() != null) {
				SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
				selectedservicedetailscreen.setServicePriceValue(serviceData.getServicePrice());
				selectedservicedetailscreen.saveSelectedServiceDetails();
			} else {
				servicesscreen.selectService(serviceData.getServiceName());
			}
		}

		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesscreen.selectService(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice() != null) {
				SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
				SelectedServiceDetailsScreen selectedservicedetailscreen = selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
				selectedservicebundlescreen.setServicePriceValue(serviceData.getServicePrice());
				selectedservicedetailscreen.saveSelectedServiceDetails();
			} else {
				SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
				selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
			}
		}
		SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailscreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		selectedservicedetailscreen.saveSelectedServiceDetails();

		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesscreen.selectService(matrixServiceData.getMatrixServiceName());
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix(matrixServiceData.getHailMatrixName());
		pricematrix.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
		pricematrix.switchOffOption("PDR");
		pricematrix.setTime(matrixServiceData.getVehiclePartData().getVehiclePartTime());
		for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
			pricematrix.clickDiscaunt(serviceData.getServiceName());
			selectedservicedetailscreen = new SelectedServiceDetailsScreen();
					selectedservicedetailscreen.setServicePriceValue(serviceData.getServicePrice());
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}

		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		ordersummaryscreen.clickSave();
		myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_AUTOWORKLISTNET);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		InvoiceInfoScreen invoiceinfoscreen = questionsscreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceinfoscreen.setPO(invoiceData.getInvoicePONumber());
		invoicenumber45224 = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling(filterBillingValue);
		myworkordersscreen.clickSaveFilter();
		
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), workOrderData.getWorkOrderPrice());
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 45224:WO: HD - Verify calculation with price matrix Labor type", 
			description = "WO: HD - Verify calculation with price matrix Labor type")
	public void testWOVerifyCalculationWithPriceMatrixLaborType_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
        invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicespage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
        invoicespage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoicespage.setSearchInvoiceNumber(invoicenumber45224);
		invoicespage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicespage.clickInvoicePrintPreview(invoicenumber45224);
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyRoundingMoneyAmountValues_1(String rowID,
																  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen =  questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesscreen.selectService(matrixServiceData.getMatrixServiceName());
		PriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());

		pricematrix.switchOffOption(vehiclePartData.getVehiclePartOption());
		pricematrix.setPrice(vehiclePartData.getVehiclePartPrice());
		pricematrix.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.clickSaveButton();

		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedservicescreen = new SelectedServiceDetailsScreen();
			selectedservicescreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedservicescreen.clickVehiclePartsCell();
				selectedservicescreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedservicescreen.saveSelectedServiceDetails();
			}
			if (serviceData.getQuestionData() != null) {
				selectedservicescreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedservicescreen.saveSelectedServiceDetails();
		}


		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesscreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
			selectedservicebundlescreen.setServicePriceValue(serviceData.getServicePrice());
			selectedservicescreen.clickVehiclePartsCell();
			selectedservicescreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
		}

		selectedservicescreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		selectedservicescreen.saveSelectedServiceDetails();


		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickWorkOrderForApproveButton(wonumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.clickApproveButton();
		
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
		myworkordersscreen.clickInvoiceIcon();
        InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoicenumber42803 = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.setPO(invoiceData.getInvoicePONumber());
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42803:Invoices: HD - Verify rounding money amount values", 
			description = "Invoices: HD - Verify rounding money amount values")
	public void testInvoicesVerifyRoundingMoneyAmountValues_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedservicedetailscreen = new SelectedServiceDetailsScreen();
			selectedservicedetailscreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedservicedetailscreen.clickVehiclePartsCell();
				selectedservicedetailscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedservicedetailscreen.saveSelectedServiceDetails();
			}
			if (serviceData.getQuestionData() != null) {
				selectedservicedetailscreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesscreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice()!=null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo (serviceData.getServiceName());
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}

		selectedServiceBundleScreen.saveSelectedServiceDetails();
		
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber);
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			approveinspscreen.selectApproveInspectionServiceStatus(serviceData);
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		
		Assert.assertEquals(myinspectionsscreen.getInspectionTotalPriceValue(inspnumber), inspectionData.getInspectionPrice());
		Assert.assertEquals(myinspectionsscreen.getInspectionApprovedPriceValue(inspnumber), inspectionData.getInspectionApprovedPrice());
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection(String rowID,
																															   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedservicedetailscreen = new SelectedServiceDetailsScreen();
			if(serviceData.getServicePrice() != null)
				selectedservicedetailscreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedservicedetailscreen.clickVehiclePartsCell();
				selectedservicedetailscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedservicedetailscreen.saveSelectedServiceDetails();
			}
			if (serviceData.getQuestionData() != null) {
				selectedservicedetailscreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesscreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice()!=null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo (serviceData.getServiceName());
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}

		selectedServiceBundleScreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsFinal();
			
		myinspectionsscreen.selectInspectionForApprove(inspectionnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();	
		approveinspscreen.selectInspectionForApprove(inspectionnumber);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.selectStatusReason(inspectionData.getDeclineReason());
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		
		
		//approveinspscreen.clickApproveButton();
		myinspectionsscreen.clickFilterButton();
		myinspectionsscreen.clickStatusFilter();
		myinspectionsscreen.clickFilterStatus(InspectionStatuses.DECLINED.getInspectionStatusValue());
		//myinspectionsscreen.clickBackButton();
		myinspectionsscreen.clickCloseFilterDialogButton();
		myinspectionsscreen.clickSaveFilterDialogButton();
		Assert.assertEquals(myinspectionsscreen.getInspectionApprovedPriceValue(inspectionnumber), inspectionData.getInspectionApprovedPrice());
		Assert.assertEquals(myinspectionsscreen.getInspectionTotalPriceValue(inspectionnumber), inspectionData.getInspectionPrice());
		myinspectionsscreen.clickHomeButton();	
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels(String rowID,
																													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_WITH_0_PRICE);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(inspectionData.getServiceData().getServiceName()));
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();	
		approveinspscreen.selectInspectionForApprove(inspnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), inspectionData.getServiceData().getServicePrice());
		
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedservicescreen.setServicePriceValue(inspectionData.getServiceData().getServicePrice2());
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new ApproveInspectionsScreen();	
		approveinspscreen.selectInspectionForApprove(inspnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), PricesCalculations.getPriceRepresentation(inspectionData.getServiceData().getServicePrice2()));
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatForBundleItemsPricePolicyIsApplied(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVinNumber());

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesscreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomBundleServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedservicedetailscreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getServiceQuantity() != null)
				selectedservicedetailscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
			if (serviceData.getVehicleParts() != null) {
				selectedservicedetailscreen.clickVehiclePartsCell();
				selectedservicedetailscreen.selectVehicleParts(serviceData.getVehicleParts());
				selectedservicedetailscreen.saveSelectedServiceDetails();
			}


			selectedservicedetailscreen.saveSelectedServiceDetails();
			if (serviceData.isNotMultiple()) {
				String alerttxt = Helpers.getAlertTextAndAccept();
				Assert.assertEquals(alerttxt, String.format(AlertsCaptions.ALERT_YOU_CAN_ADD_ONLY_ONE_SERVICE, serviceData.getServiceName()));
			}
			Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
		}

		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomBundleServiceDetails(bundleServiceData.getLaborService().getServiceName());
		selectedservicedetailscreen.setServiceTimeValue(bundleServiceData.getLaborService().getLaborServiceTime());
		selectedservicedetailscreen.setServiceRateValue(bundleServiceData.getLaborService().getLaborServiceRate());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), bundleServiceData.getBundleServiceAmount());

		selectedservicedetailscreen.changeAmountOfBundleService(workOrderData.getWorkOrderPrice());
		selectedservicedetailscreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData(String rowID,
																   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String customer  = "Avalon";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(customer, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (TaxServiceData taxServiceData : workOrderData.getTaxServicesData()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(taxServiceData.getTaxServiceName());
			for (ServiceRateData serviceRateData : taxServiceData.getServiceRatesData()) {
				Assert.assertEquals(selectedservicedetailscreen.getServiceRatedValue(serviceRateData), serviceRateData.getServiceRateValue());
			}
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}

		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedservicedetailscreen.answerQuestion(workOrderData.getServiceData().getQuestionData());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart(workOrderData.getServiceData().getVehiclePart().getVehiclePartName());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedservicedetailscreen = servicesscreen.openServiceDetails(workOrderData.getTaxServicesData().get(0).getTaxServiceName());
		Assert.assertFalse(selectedservicedetailscreen.isServiceRateFieldEditable(workOrderData.getTaxServicesData().get(0).getServiceRatesData().get(0)));
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		selectedservicedetailscreen = servicesscreen.openServiceDetails(workOrderData.getTaxServiceData().getTaxServiceName());
		selectedservicedetailscreen.setServiceRateFieldValue(workOrderData.getTaxServiceData().getServiceRateData());
		Assert.assertEquals(selectedservicedetailscreen.getServiceRatedValue(workOrderData.getTaxServiceData().getServiceRateData()), "%" + workOrderData.getTaxServiceData().getServiceRateData().getServiceRateValue());
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsTotalValue(), workOrderData.getTaxServiceData().getTaxServiceTotal());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedservicedetailscreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getServiceQuantity() != null)
				selectedservicedetailscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
			if (serviceData.getVehiclePart() != null) {
				selectedservicedetailscreen.clickVehiclePartsCell();
				selectedservicedetailscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedservicedetailscreen.saveSelectedServiceDetails();
			}
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}

		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedservicedetailscreen.getServiceDetailsPriceValue(), workOrderData.getPercentageServiceData().getServicePrice());
		selectedservicedetailscreen.saveSelectedServiceDetails();
		
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedservicedetailscreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getServiceQuantity() != null)
				selectedservicedetailscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
			if (serviceData.getVehiclePart() != null) {
				selectedservicedetailscreen.clickVehiclePartsCell();
				selectedservicedetailscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedservicedetailscreen.saveSelectedServiceDetails();
			}
			selectedservicedetailscreen.saveSelectedServiceDetails();
			InspectionToolBar toolaber = new InspectionToolBar();
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), serviceData.getServicePrice2());
		}
		servicesscreen.saveWizard();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspectionnumber), inspectionData.getInspectionPrice());
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatPackagePriceOverridesClient_RretailOrWholesale(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> workOrders = new ArrayList<>();
		final String retailCustomer = "Avalon";
		final int workOrderIndexToEdit = 1;
		final String workOrderNewPrice = "$35.00";
		
		homescreen = new HomeScreen();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			CustomersScreen customersscreen = homescreen.clickCustomersButton();
			customersscreen.swtchToWholesaleMode();
			customersscreen.selectCustomerWithoutEditing(workOrderData.getWholesailCustomer().getCompany());

			MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
			vehiclescreen.setVIN(workOrderData.getVinNumber());
			String workOrderNumber = vehiclescreen.getInspectionNumber();
			workOrders.add(workOrderNumber);
			if (workOrderData.getQuestionScreenData() != null) {
				QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
				questionsscreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
			}
			OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			ordersummaryscreen.saveWizard();
			myworkordersscreen.selectWorkOrderForEidt(workOrderNumber);
			vehiclescreen = new VehicleScreen();
			ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesscreen.selectService(workOrderData.getServiceData().getServiceName());
			InspectionToolBar toolaber = new InspectionToolBar();
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
			servicesscreen.saveWizard();

			myworkordersscreen.clickHomeButton();
		}
		MyWorkOrdersScreen myworkordersscreen =homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.selectWorkOrder(workOrders.get(workOrderIndexToEdit));
		myworkordersscreen.clickChangeCustomerPopupMenu();
		CustomersScreen customersscreen = new CustomersScreen();
		customersscreen.swtchToRetailMode();
		customersscreen.clickOnCustomer(retailCustomer);
		myworkordersscreen = new MyWorkOrdersScreen();
		Assert.assertFalse(myworkordersscreen.woExists(workOrders.get(workOrderIndexToEdit)));
		myworkordersscreen.clickHomeButton();
		
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.selectWorkOrderForEidt(workOrders.get(workOrderIndexToEdit));
		VehicleScreen vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getWorkOrderPrice());
		servicesscreen.selectService(testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getServiceData().getServiceName());
		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), workOrderNewPrice);
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkWithJobOrder(WorkOrdersTypes.WO_TYPE_WITH_JOB, workOrderData.getWorkOrderJob());
		vehiclescreen.setVIN(workOrderData.getVinNumber());
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsscreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesscreen.selectService(serviceData.getServiceName());
			InspectionToolBar toolaber = new InspectionToolBar();
			Assert.assertEquals(toolaber.getInspectionTotalPrice(), serviceData.getServicePrice());
		}
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), workOrderData.getWorkOrderPrice());
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatForDeclinedSkippedaServicesAppropriateIconIsShownAfterApproval(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualinteriorScreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehiclescreen = visualinteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVinNumber());
		String inspnumber = vehiclescreen.getInspectionNumber();

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsscreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedservicedetailscreen.clickVehiclePartsCell();
			selectedservicedetailscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedservicedetailscreen.saveSelectedServiceDetails();
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}

		for (ServiceData serviceData : inspectionData.getPercentageServicesList()) {
			servicesscreen.selectService(serviceData.getServiceName());
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesscreen.selectService(bundleServiceData.getBundleServiceName());
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity()!=null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}
		selectedServiceBundleScreen.saveSelectedServiceDetails();

		VisualInteriorScreen visualinteriorscreen = servicesscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, "Future Sport Car");
		visualinteriorscreen.selectService(inspectionData.getServiceData().getServiceName());
		visualinteriorscreen.tapInteriorWithCoords(1);
		visualinteriorscreen.tapInteriorWithCoords(2);

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			PriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			pricematrix.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartSize()!= null)
				pricematrix.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(), priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartOption()!= null) {
				pricematrix.switchOffOption(priceMatrixScreenData.getVehiclePartData().getVehiclePartOption());
				pricematrix.setTime(priceMatrixScreenData.getVehiclePartData().getVehiclePartTime());
			}

		}
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber);
		approveinspscreen.clickDeclineAllServicesButton();	
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.WHEEL_SERVICE);
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY_PANEL+ " (Grill)");
		approveinspscreen.selectInspectionServiceToDecline(iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)");

		approveinspscreen.selectInspectionServiceToSkip(iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)");
		approveinspscreen.selectInspectionServiceToSkip(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
	
		approveinspscreen.clickSaveButton();
		//approveinspscreen.selectStatusReason("Decline 1");
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		BaseUtils.waitABit(10*1000);
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		visualinteriorScreen =new VisualInteriorScreen();
		servicesscreen = visualinteriorScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.TEST_PACK_FOR_CALC);
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(servicesscreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		Assert.assertTrue(servicesscreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(servicesscreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.TAX_DISCOUNT));
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
        //DriverBuilder.getInstance().getAppiumDriver().launchApp();
		//MainScreen mainscr = new MainScreen();
		//homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
	
	@AfterMethod
	public void closeBrowser() {
		if (webdriver != null)
			webdriver.quit();
	}
}

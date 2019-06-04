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
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceName());
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), workOrderData.getServicePrice());
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
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceName());
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
			approveinspscreen.selectInspectionServiceStatus(serviceData);
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
		servicesscreen.selectService(inspectionData.getServiceName());
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
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceName());
		selectedservicedetailscreen.setServicePriceValue(workOrderData.getServicePrice());
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
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(invoiceData.getWorkOrderData().getServiceName());
			selectedservicedetailscreen.setServicePriceValue(invoiceData.getWorkOrderData().getServicePrice());
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
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(inspectionData.getServiceName());
		selectedservicedetailscreen.setServicePriceValue(inspectionData.getServicePrice());
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
		approveinspscreen.selectInspectionServiceStatus(serviceRequestData.getInspectionData().getMoneyServiceData());
		approveinspscreen.selectInspectionServiceStatus(serviceRequestData.getInspectionData().getPercentageServiceData());
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

		/*PriceMatrixScreen pricematrix = questionsscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
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

		pricematrix = pricematrix.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.MATRIX_LABOR);
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
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$293.00");*/
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
	
	private String inspnumber48543 = "";
	
	@Test(testName = "Test Case 48543:Inspections: HD - Verify that part services with different configurations are correctly shown for inspection", 
			description = "Inspections: HD - Verify that part services with different configurations are correctly shown for inspection")
	public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection() {
		
		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_WITH_PART_SERVICES);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		inspnumber48543 = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSaveAsDraft();
		
		myinspectionsscreen.selectInspectionForEdit(inspnumber48543);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Category");
		ServicePartPopup servicepartpopup = selectedservicescreen.clickServicePartCell();
		//Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Engine");
		servicepartpopup.selectServicePartSubcategory("Filters");
		servicepartpopup.selectServicePartSubcategoryPart("Engine Oil Filter");
		servicepartpopup.selectServicePartSubcategoryPosition("Oil Cooler");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("2.35");
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Name");
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.setServicePriceValue("2.5");
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_None");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		servicepartpopup.selectServicePartCategory("Body");
		servicepartpopup.selectServicePartSubcategory("Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper");
		servicepartpopup.selectServicePartSubcategoryPosition("Rear Upper");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("5.09");
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
		//Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Body");
		//Assert.assertEquals(servicepartpopup.getServicePartSubCategoryValue(), "Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper Air Shield");
		servicepartpopup.selectServicePartSubcategoryPosition("Front Lower");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("4.31");
		selectedservicescreen.saveSelectedServiceDetails();

		VisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, ScreenNamesConstants.FUTURE_AUDI_CAR);
		visualinteriorscreen.switchToCustomTab();
		visualinteriorscreen.selectService("Detail");
		visualinteriorscreen.selectSubService("Oksi_Part_SubCategory");
		visualinteriorscreen.tapCarImage();
		visualinteriorscreen.tapCarImage();
		selectedservicescreen = new SelectedServiceDetailsScreen();
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		//Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Body");
		//Assert.assertEquals(servicepartpopup.getServicePartSubCategoryValue(), "Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper Assembly");
		servicepartpopup.selectServicePartSubcategoryPosition("Front");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("6.43");
		selectedservicescreen.saveSelectedServiceDetails();

		PriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, "PM_New");
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice("10");
		pricematrix.clickDiscaunt("Oksi_Part_Category");
		selectedservicescreen = new SelectedServiceDetailsScreen();
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		//Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Engine");
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
	public void testWOVerifyThatPartServicesAreCopiedFromInspToOrder() {
		
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

        VehicleScreen vehiclescreen = myinspectionsscreen.createWOFromInspection(inspnumber48543,
                WorkOrdersTypes.WO_WITH_PART_SERVICE);
		String wonumber = vehiclescreen.getInspectionNumber();
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$77.13");
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		
		ordersummaryscreen.saveWizard();
		homescreen = myinspectionsscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$77.13");
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 48563:WO: HD - Verify that part services with different configurations are correctly shown for WO", 
			description = "WO: HD - Verify that part services with different configurations are correctly shown for WO")
	public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO()  {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_WITH_PART_SERVICE);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Category");
		ServicePartPopup servicepartpopup = selectedservicescreen.clickServicePartCell();
		//Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Engine");
		servicepartpopup.selectServicePartSubcategory("Filters");
		servicepartpopup.selectServicePartSubcategoryPart("Engine Oil Filter");
		servicepartpopup.selectServicePartSubcategoryPosition("Oil Cooler");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("2.35");
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_Name");
		Assert.assertEquals(selectedservicescreen.getServicePartValue(), "Engine Coolant Outlet Housing Bolt (N/A)");
		selectedservicescreen.setServicePriceValue("2.5");
		selectedservicescreen.saveSelectedServiceDetails();
		
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Oksi_Part_None");
		servicepartpopup = selectedservicescreen.clickServicePartCell();
		servicepartpopup.selectServicePartCategory("Body");
		servicepartpopup.selectServicePartSubcategory("Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper");
		servicepartpopup.selectServicePartSubcategoryPosition("Rear Upper");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("5.09");
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
		//Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Body");
		//Assert.assertEquals(servicepartpopup.getServicePartSubCategoryValue(), "Bumper");
		servicepartpopup.selectServicePartSubcategoryPart("Bumper Air Shield");
		servicepartpopup.selectServicePartSubcategoryPosition("Front Lower");
		servicepartpopup.saveSelectedServicePart();	
		selectedservicescreen.setServicePriceValue("4.31");
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
		//Assert.assertEquals(servicepartpopup.getServicePartCategoryValue(), "Engine");
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
	public void testWOVerifyRoundingInCalculationScriptWithPriceMatrix() {
		
		final String VIN  = "1D7HW48NX6S507810";

		SettingsScreen settingsScreen = homescreen.clickSettingsButton();
		settingsScreen.setInsvoicesCustomLayoutOff();
		settingsScreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		ordersummaryscreen.clickSave();
        InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
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
	
	@Test(testName = "Test Case 42803:Invoices: HD - Verify rounding money amount values", 
			description = "Invoices: HD - Verify rounding money amount values")
	public void testInvoicesVerifyRoundingMoneyAmountValues_1() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen =  questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE);
		selectedservicebundlescreen.setServicePriceValue("25");
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		selectedservicebundlescreen.setServicePriceValue("5");
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

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
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
		invoiceinfoscreen.setPO("12345");
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
	
	@Test(testName = "Test Case 40463:Inspections: HD - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when appove inspection", 
			description = "Inspections: HD - Verify that appoved amount is shown on Inspection list in dark green and total in dark gray when appove inspection")
	public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection() {
		
		final String VIN  = "1D7HW48NX6S507810";

		
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		selectedServiceBundleScreen.selectBundle("SR_S4_Bl_I1_M");
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo ("SR_S4_Bl_I2_M");
		selectedServiceDetailsScreen.setServicePriceValue("200");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
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
	public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection() {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(VIN);
		String inspectionnumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		selectedServiceBundleScreen.selectBundle("SR_S4_Bl_I1_M");
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo ("SR_S4_Bl_I2_M");
		selectedServiceDetailsScreen.setServicePriceValue("200");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		//servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
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
	public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels() {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String serviceprice = "21";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_WITH_0_PRICE);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
	public void testWOVerifyThatForBundleItemsPricePolicyIsApplied() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 40541:WO: HD - Verify that for Sales Tax Service data is set from DB when create WO for customer with appropriate data", 
			description = "WO: HD - Verify that for Sales Tax Service data is set from DB when create WO for customer with appropriate data")
	public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData() {
		
		final String VIN  = "1D7HW48NX6S507810";
		final String customer  = "Avalon";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(customer, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
	public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
	public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo() {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();
		
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
	public void testWOVerifyThatPackagePriceOverridesClient_RretailOrWholesale() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		String wonumber1 = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber1);
		vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("Money_Pack_Price");
		InspectionToolBar toolaber = new InspectionToolBar();		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$4.07");
		servicesscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		String wonumber2 = vehiclescreen.getInspectionNumber();

		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.saveWizard();
		
		myworkordersscreen.selectWorkOrderForEidt(wonumber2);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$12.00");
		servicesscreen.selectService("Money_Pack_Price");
		
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$35.00");
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 50013:WO: HD - Verify that client and job overrides are working fine for WO", 
			description = "WO: Verify that client and job overrides are working fine for WO")
	public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO() {
		
		final String VIN  = "1D7HW48NX6S507810";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
			
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkWithJobOrder(WorkOrdersTypes.WO_TYPE_WITH_JOB, "Job for test");
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("Money_client_override");
		InspectionToolBar toolaber = new InspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$4.79");
		
		servicesscreen.selectService("Money_job_override");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$13.84");
		servicesscreen.saveWizard();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber), "$13.84");
		myworkordersscreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 49856:SR: HD - Verify ALM flow when approve both inspectiontypes",
			description = "SR: Verify ALM flow when approve both inspectiontypes")
	public void testSRVerifyALMFlowWhenApproveBothInspections()
			 {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclescreen = new VehicleScreen();
		vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclescreen = new VehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderTypeValue(), "ALM - Recon Facility");
		
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$12.00 x 1.00"));
        Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$25.00 x 1.00"));
		servicesscreen.cancelWizard();
		teamwoscreen.clickServiceRequestButton();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 49895:SR: HD - Verify ALM flow when approve one inspection and one decline", 
			description = "SR: Verify ALM flow when approve one inspection and one decline")
	public void testSRVerifyALMFlowWhenApproveOneInspectionAndOneDecline() {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclescreen = new VehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderTypeValue(), "ALM - Recon Facility");

		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
        Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$12.00 x 1.00"));
		servicesscreen.cancelWizard();
		teamWorkOrdersScreen.clickServiceRequestButton();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();	
	}
	
	@Test(testName="Test Case 49896:SR: HD - Verify ALM flow when decline both inspectiontypes",
			description = "SR: Verify ALM flow when decline both inspectiontypes")
	public void testSRVerifyALMFlowWhenDeclineBothInspections() {
		
		final String VIN = "2A4RR4DE2AR286008";
		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.MULTIPLE_INSPECTION_SERVICE_TYPE_ALM);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ALM_STATUSES);
		questionsscreen.setToYesFinalQuestion();
		questionsscreen.setToYesCompleteQuestion();
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
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
	public void testInspectionsVerifyThatForDeclinedSkippedaServicesAppropriateIconIsShownAfterApproval() {

		final String VIN  = "1D7HW48NX6S507810";		
		homescreen = new HomeScreen();
		
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualinteriorScreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehiclescreen = visualinteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		String inspnumber = vehiclescreen.getInspectionNumber();

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		selectedServiceBundleScreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo (iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedServiceDetailsScreen.setServiceQuantityValue("2");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
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

		PriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		pricematrix.selectPriceMatrix("Hood");
		pricematrix.setSizeAndSeverity("DIME", "HEAVY");
		VisualInteriorScreen visualinteriorscreen = pricematrix.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, "Future Sport Car");
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		visualinteriorscreen.tapInteriorWithCoords(1);
		visualinteriorscreen.tapInteriorWithCoords(2);
		pricematrix = visualinteriorscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.MATRIX_LABOR);
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
		BaseUtils.waitABit(10*1000);
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		visualinteriorScreen =new VisualInteriorScreen();
		servicesscreen = visualinteriorScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.TEST_PACK_FOR_CALC);
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
	
	@AfterMethod
	public void closeBrowser() {
		if (webdriver != null)
			webdriver.quit();
	}
}

package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
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
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.hdvalidations.InvoiceInfoScreenValidations;
import com.cyberiansoft.test.ios10_client.hdvalidations.MyWorkOrdersScreenValidations;
import com.cyberiansoft.test.ios10_client.hdvalidations.PriceMatrixScreenValidations;
import com.cyberiansoft.test.ios10_client.hdvalidations.WizardScreenValidations;
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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSHDCalculationsTestCases extends ReconProBaseTestCase {

	private RetailCustomer johnRetailCustomer = new RetailCustomer("John", "");
	private WholesailCustomer Test_Company_Customer = new WholesailCustomer();

	@BeforeClass
	public void setUpSuite() {

		JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCalculationsTestCasesDataPath();
		Test_Company_Customer.setCompanyName("Test Company");
		mobilePlatform = MobilePlatform.IOS_HD;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);


		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Vit_Iph",
				envType);
		MainScreen mainScreen = new MainScreen();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.setInsvoicesCustomLayoutOff();
		settingsScreen.clickHomeButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateInspectionOnTheDeviceWithApprovalRequired(String rowID,
																	String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.waitClaimScreenLoad();
		NavigationSteps.navigateToVisualInteriorScreen();
		NavigationSteps.navigateToVisualExteriorScreen();
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_VIN_REQUIRED);

		vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		visualInteriorScreen.clickSave();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_MAKE_REQUIRED);

		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehicleScreen.saveWizard();
		myInspectionsScreen.selectInspectionForApprove(inspNumber);
		//testlogger.log(LogStatus.INFO, "After approve", testlogger.addScreenCapture(createScreenshot(, iOSLogger.loggerdir)));
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspNumber);
		approveInspectionsScreen.clickApproveAfterSelection();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneButton();
		Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspNumber));

		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAddServicesToVisualInspection(String rowID,
												  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.DEFAULT_INSPECTION_TYPE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		final String inspNumber = vehicleScreen.getInspectionNumber();
		vehicleScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_VIN_REQUIRED);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehicleScreen.clickSave();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_MAKE_REQUIRED);

		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehicleScreen.saveWizard();
		myInspectionsScreen.selectInspectionForEdit(inspNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		int screenTapCount = 1;
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			NavigationSteps.navigateToScreen(visualScreenData.getScreenName());
			VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
			for (DamageData damageData : visualScreenData.getDamagesData()) {
				visualInteriorScreen.switchToCustomTab();
				visualInteriorScreen.selectService(damageData.getDamageGroupName());
				visualInteriorScreen.selectSubService(damageData.getMoneyService().getServiceName());
				visualInteriorScreen.tapInteriorWithCoords(screenTapCount);
				screenTapCount++;
				visualInteriorScreen.tapInteriorWithCoords(screenTapCount);
				screenTapCount++;
			}
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), visualScreenData.getScreenTotalPrice());
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), visualScreenData.getScreenPrice());
		}
		vehicleScreen.saveWizard();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
		myInspectionsScreen.selectInspectionForEdit(inspNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		screenTapCount = 1;
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			NavigationSteps.navigateToScreen(visualScreenData.getScreenName());
			VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
			for (DamageData damageData : visualScreenData.getDamagesData()) {
				visualInteriorScreen.tapInteriorWithCoords(screenTapCount);
				screenTapCount = screenTapCount + 2;
				visualInteriorScreen.setCarServiceQuantityValue(damageData.getMoneyService().getServiceQuantity());
				visualInteriorScreen.saveCarServiceDetails();
			}
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), visualScreenData.getScreenTotalPrice2());
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), visualScreenData.getScreenPrice2());
		}
		vehicleScreen.saveWizard();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyPanelThenItWillBeAddedOnceForManyAssociatedServiceInstancesWithSameVehiclePart(String rowID,
																																   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		NavigationSteps.navigateToScreen(ScreenNamesConstants.ALL_SERVICES);
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity(String rowID,
																																								   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_FEE_ITEM_IN_2_PACKS);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		final String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(ScreenNamesConstants.ALL_SERVICES);
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getServiceData().getServicePrice());
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workorderspage = new WorkOrdersWebPage(webdriver);
		operationsWebPage.clickWorkOrdersLink();
		workorderspage.makeSearchPanelVisible();
		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workorderspage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		workorderspage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workorderspage.setSearchOrderNumber(workOrderNumber);
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();

		WorkOrderInfoTabWebPage woinfotab = workorderspage.clickWorkOrderInTable(workOrderNumber);
		Assert.assertTrue(woinfotab.isServicePriceCorrectForWorkOrder("$36.00"));
		Assert.assertTrue(woinfotab.isServiceSelectedForWorkOrder("Service_in_2_fee_packs"));
		Assert.assertTrue(woinfotab.isServiceSelectedForWorkOrder("Oksi_Test1"));
		Assert.assertTrue(woinfotab.isServiceSelectedForWorkOrder("Oksi_Test2"));
		woinfotab.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatPackagePriceOfFeeBundleItemIsOverrideThePriceOfWholesaleAndRetailPrices(String rowID,
																									  String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FEE_PRICE_OVERRIDE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		NavigationSteps.navigateToScreen(ScreenNamesConstants.ALL_SERVICES);
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances(String rowID,
																														String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		NavigationSteps.navigateToScreen(ScreenNamesConstants.ALL_SERVICES);
		ServicesScreen servicesScreen = new ServicesScreen();

		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyServiceOrFlatFeeThenItWillBeAddedToWOEveryTimeWhenAssociatedServiceInstanceWillAddToWO(String rowID,
																																		   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		NavigationSteps.navigateToScreen(ScreenNamesConstants.ALL_SERVICES);
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatForWholesaleAndRetailCustomersFeeIsAddedDependsOnThePriceAccordinglyToPriceOfTheFeeBundleItem(String rowID,
																															String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		NavigationSteps.navigateToScreen(ScreenNamesConstants.ALL_SERVICES);
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices(String rowID,
																						 String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(ScreenNamesConstants.ALL_SERVICES);
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(workOrderData.getMatrixServiceData().getMatrixServiceName());
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartName());
		priceMatrixScreen.switchOffOption(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartOption());
		priceMatrixScreen.setPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
		for (ServiceData serviceData : workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalServices())
			priceMatrixScreen.selectDiscaunt(serviceData.getServiceName());

		Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartTotalPrice(), workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTotalPrice());
		priceMatrixScreen.clickSaveButton();
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		servicesScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = new WorkOrdersWebPage(webdriver);
		operationsWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber);
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage workOrderInfoTabWebPage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber);
		Assert.assertTrue(workOrderInfoTabWebPage.isServicePriceCorrectForWorkOrder("$2,127.50"));
		Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("SR_S6_FeeBundle"));

		workOrderInfoTabWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_095(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedselectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
			selectedselectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedselectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedselectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();

			if (serviceData.getServicePrice2() != null) {
				selectedselectedServiceDetailsScreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedselectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
		}

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_003(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedselectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
			selectedselectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedselectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedselectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();

			if (serviceData.getServicePrice2() != null) {
				selectedselectedServiceDetailsScreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedselectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
		}

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_005(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedselectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
			selectedselectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedselectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedselectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();

			if (serviceData.getServicePrice2() != null) {
				selectedselectedServiceDetailsScreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedselectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
		}

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly(String rowID,
																	  String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = new WorkOrdersWebPage(webdriver);
		operationsWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber);
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage workOrderInfoTabWebPage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber);
		workOrdersWebPage.waitABit(5000);
		Assert.assertTrue(workOrderInfoTabWebPage.isServicePriceCorrectForWorkOrder("$3,153.94"));

		workOrderInfoTabWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined(String rowID,
																							   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_SIMPLE);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(ScreenNamesConstants.DEFAULT);
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		for (VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
			priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
			priceMatrixScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
		}
		priceMatrixScreen.saveWizard();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		approveInspectionsScreen.clickSkipAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickCancelStatusReasonButton();


		approveInspectionsScreen.clickDeclineAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.selectStatusReason(inspectionData.getDeclineReason());
		//Helpers.acceptAlert();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		myInspectionsScreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		inspectionsWebPage.selectSearchStatus("Declined");
		inspectionsWebPage.searchInspectionByNumber(inspectionNumber);
		Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber), "$0.00");
		Assert.assertEquals(inspectionsWebPage.getInspectionReason(inspectionNumber), "Decline 1");
		Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber), "Declined");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount(String rowID,
																										  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());

		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			servicesScreen.selectService(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		servicesScreen = new ServicesScreen();
		servicesScreen.cancelSearchAvailableService();
		servicesScreen.saveWizard();

		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);
		}

		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		myInspectionsScreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		inspectionsWebPage.selectSearchStatus("All active");
		inspectionsWebPage.searchInspectionByNumber(inspectionNumber);
		Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber), "$2,000.00");
		Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber), "Approved");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc(String rowID,
																		  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		servicesScreen.cancelSearchAvailableService();
		servicesScreen.saveWizard();

		myInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		approveInspectionsScreen.clickSkipAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.selectStatusReason(inspectionData.getDeclineReason());
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneStatusReasonButton();

		myInspectionsScreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		inspectionsWebPage.selectSearchStatus(InspectionStatuses.DECLINED.getInspectionStatusValue());
		inspectionsWebPage.searchInspectionByNumber(inspectionNumber);
		Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber), "$0.00");
		Assert.assertEquals(inspectionsWebPage.getInspectionApprovedTotal(inspectionNumber), "$0.00");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionVerifyServicesAreMarkedAsStrikethroughWhenExcludeFromTotal(String rowID,
																						 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehicleParts(serviceData.getVehicleParts());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		questionsScreen.saveWizard();

		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		Assert.assertEquals(myInspectionsScreen.getInspectionApprovedPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatValueSelectedOnPriceMatrixStepIsSavedAndShownDuringEditMode(String rowID,
																						  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(Test_Company_Customer, InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
			PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
			priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			priceMatrixScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(),
					priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService() != null)
				priceMatrixScreen.selectDiscaunt(priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice() != null)
				priceMatrixScreen.setPrice(priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice());
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), priceMatrixScreenData.getMatrixScreenPrice());
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), priceMatrixScreenData.getMatrixScreenTotalPrice());
		}

		NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(inspectionData.getServiceData().getServiceName());
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());

		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		vehicleScreen.saveWizard();
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), priceMatrixScreenData.getMatrixScreenPrice());

		}
		vehicleScreen.saveWizard();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																	String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity());

		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart(workOrderData.getServiceData().getVehiclePart().getVehiclePartName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();

		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_WO_IS_HUGE, workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.swipeScreenLeft();
		NavigationSteps.navigateToServicesScreen();
		selectedServiceDetailsScreen = servicesScreen.openServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity2());

		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																		  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final int workOrdersToCreate = 2;
		List<String> workOrders = new ArrayList<>();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		for (int i = 0; i < workOrdersToCreate; i++) {
			MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
			VehicleScreen vehicleScreen = new VehicleScreen();
			vehicleScreen.waitVehicleScreenLoaded();
			vehicleScreen.setVIN(testCaseData.getWorkOrderData().getVehicleInfoData().getVINNumber());
			workOrders.add(vehicleScreen.getInspectionNumber());

			NavigationSteps.navigateToServicesScreen();
			ServicesScreen servicesScreen = new ServicesScreen();
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(testCaseData.getWorkOrderData().getServiceData().getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(testCaseData.getWorkOrderData().getServiceData().getServicePrice());
			selectedServiceDetailsScreen.setServiceQuantityValue(testCaseData.getWorkOrderData().getServiceData().getServiceQuantity());

			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehiclePart(testCaseData.getWorkOrderData().getServiceData().getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			NavigationSteps.navigateToOrderSummaryScreen();
			OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
			orderSummaryScreen.setTotalSale(testCaseData.getWorkOrderData().getWorkOrderTotalSale());
			orderSummaryScreen.saveWizard();
		}

		myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrders.get(0), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myWorkOrdersScreen.waitWorkOrdersScreenLoaded();
		TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrders.get(0));
		teamWorkOrdersScreen.clickInvoiceIcon();
		InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_CUSTOM1);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.waitQuestionsScreenLoaded();
		NavigationSteps.navigateToInvoiceInfoScreen();
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveInvoiceAsDraft();

		teamWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrders.get(1), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		teamWorkOrdersScreen.selectWorkOrderForEidt(workOrders.get(1));
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		vehicleScreen.clickSave();
		teamWorkOrdersScreen.waitTeamWorkOrdersScreenLoaded();
		teamWorkOrdersScreen.clickHomeButton();

		MyInvoicesScreen myinvoicesscreen = homeScreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoiceNumber);

		myinvoicesscreen.clickEditPopup();
		questionsScreen = new QuestionsScreen();
		questionsScreen.waitQuestionsScreenLoaded();
		questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceInfoScreen.addTeamWorkOrder(workOrders.get(1));
		invoiceInfoScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INVOICE_IS_HUGE, testCaseData.getInvoiceData().getInvoiceTotal()));
		invoiceInfoScreen.swipeScreenLeft();
		invoiceInfoScreen.cancelInvoice();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																			String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.setServiceQuantityValue(inspectionData.getServiceData().getServiceQuantity());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart(inspectionData.getServiceData().getVehiclePart().getVehiclePartName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();

		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INSPECTION_IS_HUGE, inspectionData.getInspectionPrice()));
		selectedServiceDetailsScreen = servicesScreen.openServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServiceQuantityValue(inspectionData.getServiceData().getServiceQuantity2());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickSaveAsDraft();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyApprovedAmountForInspectionCreatedFromSR(String rowID,
																   String description, JSONObject testData) {

		ServiceRequestData serviceRequestData = JSonDataParser.getTestDataFromJson(testData, ServiceRequestData.class);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingscreen = homeScreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
		ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		NavigationSteps.navigateToScreen(serviceRequestData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		questionsScreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("No"))
				.click();
		serviceRequestsScreen = new ServiceRequestsScreen();
		String srnumber = serviceRequestsScreen.getFirstServiceRequestNumber();
		ServiceRequestSteps.startCreatingInspectionFromServiceRequest(srnumber, InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		NavigationSteps.navigateToVehicleInfoScreen();
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		NavigationSteps.navigateToServicesScreen();
		servicesScreen.selectService(serviceRequestData.getInspectionData().getMoneyServiceData().getServiceName());
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart(serviceRequestData.getInspectionData().getMoneyServiceData().getVehiclePart().getVehiclePartName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();

		serviceRequestsScreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
		TeamInspectionsScreen teamInspectionsScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionNumber));
		teamInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getMoneyServiceData());
		approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getPercentageServiceData());
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		Assert.assertTrue(teamInspectionsScreen.isInspectionApproved(inspectionNumber));
		Assert.assertEquals(teamInspectionsScreen.getFirstInspectionPriceValue(), serviceRequestData.getInspectionData().getInspectionPrice());
		Assert.assertEquals(teamInspectionsScreen.getFirstInspectionTotalPriceValue(), serviceRequestData.getInspectionData().getInspectionTotalPrice());
		teamInspectionsScreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();
		serviceRequestsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly(String rowID,
																			 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());

		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
			PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
			VehiclePartData vehiclePartData = priceMatrixScreenData.getVehiclePartData();
			priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
			if (vehiclePartData.getVehiclePartSize() != null)
				priceMatrixScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
			if (vehiclePartData.getVehiclePartOption() != null)
				priceMatrixScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
			if (vehiclePartData.getVehiclePartPrice() != null)
				priceMatrixScreen.setPrice(vehiclePartData.getVehiclePartPrice());
			if (vehiclePartData.getVehiclePartTime() != null)
				priceMatrixScreen.setTime(vehiclePartData.getVehiclePartTime());
			if (vehiclePartData.getVehiclePartTotalPrice() != null)
				priceMatrixScreen.setTime(vehiclePartData.getVehiclePartTime());
			for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
				priceMatrixScreen.clickDiscaunt(serviceData.getServiceName());
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
				InspectionToolBar inspectionToolBar = new InspectionToolBar();
				Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), serviceData.getServicePrice());
				Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice2());
			}
		}
		vehicleScreen.saveWizard();

		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		myInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.approveInspectionApproveAllAndSignature(inspectionNumber);
		MyInspectionsSteps.createWorkOrderFromInspection(inspectionNumber,
				WorkOrdersTypes.WO_SMOKE_MONITOR);
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(serviceData.getServiceName(),
					serviceData.getServicePrice()));
		}
		servicesScreen.cancelWizard();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection(String rowID,
																											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_WITH_PART_SERVICES);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.clickSaveAsDraft();
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToServicesScreen();

		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.clickServicePartCell();
			ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), inspectionData.getMoneyServiceData().getServicePartData().getServicePartValue());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getMoneyServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToScreen(ScreenNamesConstants.FUTURE_AUDI_CAR);
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.switchToCustomTab();
		visualInteriorScreen.selectService(inspectionData.getDamageData().getDamageGroupName());
		visualInteriorScreen.selectSubService(inspectionData.getDamageData().getMoneyService().getServiceName());
		visualInteriorScreen.tapCarImage();
		visualInteriorScreen.tapCarImage();
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.clickServicePartCell();
		ServicePartSteps.selectServicePartData(inspectionData.getDamageData().getMoneyService().getServicePartData());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getDamageData().getMoneyService().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		final PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreenData();
		NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
		final VehiclePartData vehiclePartData = priceMatrixScreenData.getVehiclePartData();
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		priceMatrixScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
		priceMatrixScreen.setPrice(vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
			priceMatrixScreen.clickDiscaunt(serviceData.getServiceName());
			selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.clickServicePartCell();
			ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), serviceData.getServicePartData().getServicePartValue());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		priceMatrixScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServiceQuantityValue(vehiclePartData.getVehiclePartAdditionalService().getServiceQuantity());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), vehiclePartData.getVehiclePartTotalPrice());
		Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), vehiclePartData.getVehiclePartSubTotalPrice());

		servicesScreen.clickSaveAsFinal();

		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		//approveInspectionsScreen.selectInspectionForApprove(inspNumber48543);
		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneStatusReasonButton();

		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		MyInspectionsSteps.createWorkOrderFromInspection(inspectionNumber,
				WorkOrdersTypes.WO_WITH_PART_SERVICE);
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

		orderSummaryScreen.saveWizard();
		homeScreen = myInspectionsScreen.clickHomeButton();
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		myWorkOrdersScreen.clickHomeButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_WITH_PART_SERVICE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();

		myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			ServicePartPopup servicepartpopup = selectedServiceDetailsScreen.clickServicePartCell();
			ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), workOrderData.getMoneyServiceData().getServicePartData().getServicePartValue());
		selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getMoneyServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectPriceMatrix(matrixServiceData.getMatrixServiceName());
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		priceMatrixScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
		priceMatrixScreen.setPrice(vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
			priceMatrixScreen.clickDiscaunt(serviceData.getServiceName());
			selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.clickServicePartCell();
			ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), serviceData.getServicePartData().getServicePartValue());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartTotalPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
		priceMatrixScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServiceQuantityValue(vehiclePartData.getVehiclePartAdditionalService().getServiceQuantity());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartTotalPrice(), vehiclePartData.getVehiclePartTotalPrice());

		priceMatrixScreen.clickSaveButton();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		servicesScreen.saveWizard();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyRoundingInCalculationScriptWithPriceMatrix(String rowID,
																	   String description, JSONObject testData) {

		final String filterBillingValue = "All";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
		settingsScreen.setInsvoicesCustomLayoutOff();
		settingsScreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		servicesScreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		priceMatrixScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
		priceMatrixScreen.setPrice(vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
			priceMatrixScreen.clickDiscaunt(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		priceMatrixScreen.clickSaveButton();

		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();

		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		orderSummaryScreen.clickSave();
		InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSave();
		Helpers.acceptAlert();
		questionsScreen.selectAnswerForQuestion(invoiceData.getQuestionScreenData().getQuestionData());
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickFilterButton();
		myWorkOrdersScreen.setFilterBilling(filterBillingValue);
		myWorkOrdersScreen.clickSaveFilter();

		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		homeScreen = myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homeScreen.clickMyInvoices();
		Assert.assertEquals(myinvoicesscreen.getPriceForInvoice(invoiceNumber), invoiceData.getInvoiceTotal());
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyCalculationWithPriceMatrixLaborType(String rowID,
																  String description, JSONObject testData) {
		final String filterBillingValue = "All";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();

		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getServicePrice() != null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else {
				servicesScreen.selectService(serviceData.getServiceName());
			}
		}

		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesScreen.selectService(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice() != null) {
				SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
				selectedservicebundlescreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else {
				SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
				selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
			}
		}
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(matrixServiceData.getHailMatrixName());
		priceMatrixScreen.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
		priceMatrixScreen.switchOffOption(matrixServiceData.getVehiclePartData().getVehiclePartOption());
		priceMatrixScreen.setTime(matrixServiceData.getVehiclePartData().getVehiclePartTime());
		for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
			priceMatrixScreen.clickDiscaunt(serviceData.getServiceName());
			selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		priceMatrixScreen.clickSaveButton();
		servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();

		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		orderSummaryScreen.clickSave();
		InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_AUTOWORKLISTNET);
		questionsScreen.selectAnswerForQuestion(invoiceData.getQuestionScreenData().getQuestionData());
		NavigationSteps.navigateToInvoiceInfoScreen();
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();

		myWorkOrdersScreen.clickFilterButton();
		myWorkOrdersScreen.setFilterBilling(filterBillingValue);
		myWorkOrdersScreen.clickSaveFilter();

		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		myWorkOrdersScreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPage.clickInvoicesLink();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicesWebPage.clickInvoicePrintPreview(invoiceNumber);
		Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceListValue("Matrix Service"), "$100.00");
		Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceNetValue("Matrix Service"), "$112.50");
		Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceListValue("Test service zayats"), "$100.00");
		Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceNetValue("Test service zayats"), "$112.50");
		Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceListValue("SR_Disc_20_Percent (25.000%)"), "$25.00");
		Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceNetValue("SR_Disc_20_Percent (25.000%)"), "$28.13");
		invoicesWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyRoundingMoneyAmountValues(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		PriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());

		priceMatrixScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
		priceMatrixScreen.setPrice(vehiclePartData.getVehiclePartPrice());
		priceMatrixScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		priceMatrixScreen.clickSaveButton();

		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}


		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
			selectedservicebundlescreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		selectedServiceDetailsScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();


		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickWorkOrderForApproveButton(workOrderNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.clickApproveButton();

		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber);
		myWorkOrdersScreen.clickInvoiceIcon();
		InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPage.clickInvoicesLink();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicesWebPage.clickInvoiceInternalTechInfo(invoiceNumber);
		Assert.assertEquals(invoicesWebPage.getTechInfoServicesTableServiceValue("<Tax>", "Test service price matrix"), "4.67000");
		Assert.assertEquals(invoicesWebPage.getTechInfoServicesTableServiceValue("<Tax>", "Test service price matrix (Sev: None; Size: None)"), "3.79600");
		Assert.assertEquals(invoicesWebPage.getTechInfoServicesTableServiceValue("<Tax>", "Oksi_Service_PP_Flat_Fee"), "0.87400");
		invoicesWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection(String rowID,
																															   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice() != null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}

		selectedServiceBundleScreen.saveSelectedServiceDetails();

		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		servicesScreen.saveWizard();
		myInspectionsScreen.selectInspectionForApprove(inspNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspNumber);
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneButton();

		Assert.assertEquals(myInspectionsScreen.getInspectionTotalPriceValue(inspNumber), inspectionData.getInspectionPrice());
		Assert.assertEquals(myInspectionsScreen.getInspectionApprovedPriceValue(inspNumber), inspectionData.getInspectionApprovedPrice());
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection(String rowID,
																													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingscreen = homeScreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();

		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice() != null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}

		selectedServiceBundleScreen.saveSelectedServiceDetails();
		servicesScreen.clickSaveAsFinal();

		myInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		approveInspectionsScreen.clickDeclineAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.selectStatusReason(inspectionData.getDeclineReason());
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneButton();


		//approveInspectionsScreen.clickApproveButton();
		myInspectionsScreen.clickFilterButton();
		myInspectionsScreen.clickStatusFilter();
		myInspectionsScreen.clickFilterStatus(InspectionStatuses.DECLINED.getInspectionStatusValue());
		//myInspectionsScreen.clickBackButton();
		myInspectionsScreen.clickCloseFilterDialogButton();
		myInspectionsScreen.clickSaveFilterDialogButton();
		Assert.assertEquals(myInspectionsScreen.getInspectionApprovedPriceValue(inspectionNumber), inspectionData.getInspectionApprovedPrice());
		Assert.assertEquals(myInspectionsScreen.getInspectionTotalPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels(String rowID,
																												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_WITH_0_PRICE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getServiceData().getServiceName()));
		servicesScreen.saveWizard();
		myInspectionsScreen.selectInspectionForAction(inspNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspNumber);
		Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), inspectionData.getServiceData().getServicePrice());

		approveInspectionsScreen.clickCancelButton();
		approveInspectionsScreen.clickCancelButton();

		myInspectionsScreen.selectInspectionForEdit(inspNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getServiceData().getServicePrice2());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();

		myInspectionsScreen.selectInspectionForAction(inspNumber);
		selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspNumber);
		Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), PricesCalculations.getPriceRepresentation(inspectionData.getServiceData().getServicePrice2()));
		approveInspectionsScreen.clickCancelButton();
		approveInspectionsScreen.clickCancelButton();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatForBundleItemsPricePolicyIsApplied(String rowID,
																   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomBundleServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getServiceQuantity() != null)
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
			if (serviceData.getVehicleParts() != null) {
				selectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedServiceDetailsScreen.selectVehicleParts(serviceData.getVehicleParts());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			if (serviceData.isNotMultiple()) {
				String alertText = Helpers.getAlertTextAndAccept();
				Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_YOU_CAN_ADD_ONLY_ONE_SERVICE, serviceData.getServiceName()));
			}
			Assert.assertEquals(selectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
		}

		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomBundleServiceDetails(bundleServiceData.getLaborService().getServiceName());
		selectedServiceDetailsScreen.setServiceTimeValue(bundleServiceData.getLaborService().getLaborServiceTime());
		selectedServiceDetailsScreen.setServiceRateValue(bundleServiceData.getLaborService().getLaborServiceRate());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceDetailsPriceValue(), bundleServiceData.getBundleServiceAmount());

		selectedServiceDetailsScreen.changeAmountOfBundleService(workOrderData.getWorkOrderPrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData(String rowID,
																											String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final RetailCustomer retailCustomer = new RetailCustomer("Avalon", "");

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(retailCustomer, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (TaxServiceData taxServiceData : workOrderData.getTaxServicesData()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(taxServiceData.getTaxServiceName());
			for (ServiceRateData serviceRateData : taxServiceData.getServiceRatesData()) {
				Assert.assertEquals(selectedServiceDetailsScreen.getServiceRateValue(serviceRateData), serviceRateData.getServiceRateValue());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.answerQuestion(workOrderData.getServiceData().getQuestionData());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart(workOrderData.getServiceData().getVehiclePart().getVehiclePartName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToServicesScreen();
		selectedServiceDetailsScreen = servicesScreen.openServiceDetails(workOrderData.getTaxServicesData().get(0).getTaxServiceName());
		Assert.assertFalse(selectedServiceDetailsScreen.isServiceRateFieldEditable(workOrderData.getTaxServicesData().get(0).getServiceRatesData().get(0)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesScreen.openServiceDetails(workOrderData.getTaxServiceData().getTaxServiceName());
		selectedServiceDetailsScreen.setServiceRateFieldValue(workOrderData.getTaxServiceData().getServiceRateData());
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceRateValue(workOrderData.getTaxServiceData().getServiceRateData()), "%" + workOrderData.getTaxServiceData().getServiceRateData().getServiceRateValue());
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceDetailsTotalValue(), workOrderData.getTaxServiceData().getTaxServiceTotal());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();

		myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getServiceQuantity() != null)
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
			if (serviceData.getVehiclePart() != null) {
				selectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceDetailsPriceValue(), workOrderData.getPercentageServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		HomeScreen homeScreen = new HomeScreen();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.saveWizard();

		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getServiceQuantity() != null)
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
			if (serviceData.getVehiclePart() != null) {
				selectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice2());
		}
		servicesScreen.saveWizard();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatPackagePriceOverridesClient_RretailOrWholesale(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> workOrders = new ArrayList<>();
		final String retailCustomer = "Avalon";
		final int workOrderIndexToEdit = 1;
		final String workOrderNewPrice = "$35.00";

		HomeScreen homeScreen = new HomeScreen();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			CustomersScreen customersScreen = homeScreen.clickCustomersButton();
			customersScreen.swtchToWholesaleMode();
			customersScreen.selectCustomerWithoutEditing(workOrderData.getWholesailCustomer().getCompany());

			MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
			MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
			VehicleScreen vehicleScreen = new VehicleScreen();
			vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
			String workOrderNumber = vehicleScreen.getInspectionNumber();
			workOrders.add(workOrderNumber);
			if (workOrderData.getQuestionScreenData() != null) {
				NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
				QuestionsScreen questionsScreen = new QuestionsScreen();
				questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
			}
			NavigationSteps.navigateToOrderSummaryScreen();
			OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
			orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			orderSummaryScreen.saveWizard();
			myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
			vehicleScreen.waitVehicleScreenLoaded();
			NavigationSteps.navigateToServicesScreen();
			ServicesScreen servicesScreen = new ServicesScreen();
			servicesScreen.selectService(workOrderData.getServiceData().getServiceName());
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
			servicesScreen.saveWizard();

			myWorkOrdersScreen.clickHomeButton();
		}
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.selectWorkOrder(workOrders.get(workOrderIndexToEdit));
		myWorkOrdersScreen.clickChangeCustomerPopupMenu();
		CustomersScreen customersScreen = new CustomersScreen();
		customersScreen.swtchToRetailMode();
		customersScreen.clickOnCustomer(retailCustomer);

		Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(workOrders.get(workOrderIndexToEdit)));
		myWorkOrdersScreen.clickHomeButton();

		customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrders.get(workOrderIndexToEdit));
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getWorkOrderPrice());
		servicesScreen.selectService(testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getServiceData().getServiceName());

		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderNewPrice);
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO(String rowID,
																		 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(WorkOrdersTypes.WO_TYPE_WITH_JOB, workOrderData.getWorkOrderJob());
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesScreen.selectService(serviceData.getServiceName());
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice());
		}
		servicesScreen.saveWizard();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatForDeclinedSkippedaServicesAppropriateIconIsShownAfterApproval(String rowID,
																										String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VisualInteriorScreen visualinteriorScreen = new VisualInteriorScreen();
		visualinteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspNumber = vehicleScreen.getInspectionNumber();

		NavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		for (ServiceData serviceData : inspectionData.getPercentageServicesList()) {
			servicesScreen.selectService(serviceData.getServiceName());
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesScreen.selectService(bundleServiceData.getBundleServiceName());
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}
		selectedServiceBundleScreen.saveSelectedServiceDetails();
		NavigationSteps.navigateToScreen("Future Sport Car");
		VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.selectService(inspectionData.getServiceData().getServiceName());
		visualInteriorScreen.tapInteriorWithCoords(1);
		visualInteriorScreen.tapInteriorWithCoords(2);

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
			PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
			priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartSize() != null)
				priceMatrixScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(), priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartOption() != null) {
				priceMatrixScreen.switchOffOption(priceMatrixScreenData.getVehiclePartData().getVehiclePartOption());
				priceMatrixScreen.setTime(priceMatrixScreenData.getVehiclePartData().getVehiclePartTime());
			}

		}
		servicesScreen.saveWizard();
		myInspectionsScreen.selectInspectionForAction(inspNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspNumber);
		approveInspectionsScreen.clickDeclineAllServicesButton();
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);

		approveInspectionsScreen.clickSaveButton();
		//approveInspectionsScreen.selectStatusReason("Decline 1");
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneButton();
		BaseUtils.waitABit(10 * 1000);
		myInspectionsScreen.selectInspectionForEdit(inspNumber);
		NavigationSteps.navigateToScreen(ScreenNamesConstants.TEST_PACK_FOR_CALC);
		Assert.assertTrue(servicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(servicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(servicesScreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		Assert.assertTrue(servicesScreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(servicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.TAX_DISCOUNT));
		servicesScreen.cancelWizard();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances(String rowID,
																													String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		HomeScreenSteps.navigateToMyWorkOrdersScreen();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		WizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
		WizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
		WorkOrdersSteps.saveWorkOrder();
		MyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

		NavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatDiscountsAreCalculatedCorrectlyOnAllLevels(String rowID,
																		   String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		final String subTotal = "$159.00";
		final String subTotal2 = "$259.00";

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		HomeScreenSteps.navigateToMyWorkOrdersScreen();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.CALC_ORDER);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreenSteps.selectBundleService(workOrderData.getServicesScreen().getBundleService());
		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getServicesScreen().getMatrixService());
		PriceMatrixScreenValidations.verifyPriceMatrixScreenTotalValue(workOrderData.getServicesScreen().getMatrixService().getVehiclePartData().getVehiclePartTotalPrice());
		PriceMatrixScreenSteps.savePriceMatrixData();
		WizardScreenValidations.verifyScreenSubTotalPrice(subTotal);
		ServicesScreenSteps.selectLaborServiceAndSetData(workOrderData.getServicesScreen().getLaborService());
		ServiceDetailsScreenSteps.saveServiceDetails();
		WizardScreenValidations.verifyScreenSubTotalPrice(subTotal2);

		for (ServiceData serviceData : workOrderData.getServicesScreen().getPercentageServices())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		ServicesScreenSteps.waitServicesScreenLoad();
		WizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
		WizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
		NavigationSteps.navigateToOrderSummaryScreen();
		WorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

		WorkOrdersSteps.saveWorkOrder();

		MyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

		NavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatUpchargesAreCalculatedCorrectlyOnAllLevels(String rowID,
																		   String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		final String subTotal = "$241.00";
		final String subTotal2 = "$341.00";

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		HomeScreenSteps.navigateToMyWorkOrdersScreen();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.CALC_ORDER);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreenSteps.selectBundleService(workOrderData.getServicesScreen().getBundleService());
		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getServicesScreen().getMatrixService());
		PriceMatrixScreenValidations.verifyPriceMatrixScreenTotalValue(workOrderData.getServicesScreen().getMatrixService().getVehiclePartData().getVehiclePartTotalPrice());
		PriceMatrixScreenSteps.savePriceMatrixData();
		WizardScreenValidations.verifyScreenSubTotalPrice(subTotal);
		ServicesScreenSteps.selectLaborServiceAndSetData(workOrderData.getServicesScreen().getLaborService());
		ServiceDetailsScreenSteps.saveServiceDetails();
		WizardScreenValidations.verifyScreenSubTotalPrice(subTotal2);

		for (ServiceData serviceData : workOrderData.getServicesScreen().getPercentageServices())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		ServicesScreenSteps.waitServicesScreenLoad();
		WizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
		WizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
		NavigationSteps.navigateToOrderSummaryScreen();
		WorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

		WorkOrdersSteps.saveWorkOrder();

		MyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

		NavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatOnPrintOutOfAllProTemplateAllCalculationDataIsCorrectProductionData(String rowID,
																										  String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String totalColumnText = "TOTAL:";
		final String taxColumnText = "TAX DUE:";
		final String taxServicesTotal = "$6.63";

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		HomeScreenSteps.navigateToMyWorkOrdersScreen();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		WizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
		WizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		NavigationSteps.navigateToOrderSummaryScreen();
		WorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

		WorkOrdersSteps.saveWorkOrder();
		MyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

		MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
		myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		MyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
		MyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(InvoicesTypes.INVOICE_ALLPRO);
		InvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceInfoScreenSteps.getInvoiceNumber();
		InvoiceInfoScreenValidations.verifyInvoiceTotalValue(testCaseData.getInvoiceData().getInvoiceTotal());
		InvoicesSteps.saveInvoiceAsFinal();

		NavigationSteps.navigateBackScreen();

		BaseUtils.waitABit(30*1000);
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPage.clickInvoicesLink();
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();

		String mainWindowHandle = webdriver.getWindowHandle();
		invoicesWebPage.clickInvoicePrintPreview(invoiceNumber);
		Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceListValue(totalColumnText), testCaseData.getInvoiceData().getInvoiceTotal());
		Assert.assertEquals(invoicesWebPage.getPrintPreviewListValue(taxColumnText), taxServicesTotal);
		invoicesWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatOnPrintOutOfAutoWorkListNetTemplateAllCalculationDataIsCorrect_ProdData(String rowID,
																											  String description, JSONObject testData) throws Exception {

		final String listTotal = "2368.00";
		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		HomeScreenSteps.navigateToMyWorkOrdersScreen();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		NavigationSteps.navigateToServicesScreen();
		final MatrixServiceData matrixServiceData = workOrderData.getServicesScreen().getMatrixService();
		ServicesScreenSteps.selectMatrixService(matrixServiceData);
		for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
			PriceMatrixScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
			PriceMatrixScreenValidations.verifyVehiclePartPriceValue(vehiclePartData.getVehiclePartName(),
					vehiclePartData.getVehiclePartTotalPrice());
		}
		PriceMatrixScreenSteps.savePriceMatrixData();

		for (ServiceData serviceData : workOrderData.getServicesScreen().getPercentageServices())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		ServicesScreenSteps.waitServicesScreenLoad();
		WizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
		WizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
		NavigationSteps.navigateToOrderSummaryScreen();
		WorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

		WorkOrdersSteps.saveWorkOrder();

		MyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

		MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
		myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);


		MyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
		MyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(InvoicesTypes.INVOICE_AUTOWORKLISTNET);
		QuestionsScreenSteps.answerQuestion(workOrderData.getQuestionScreenData().getQuestionData());
		NavigationSteps.navigateToInvoiceInfoScreen();

		InvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceInfoScreenSteps.getInvoiceNumber();
		InvoiceInfoScreenValidations.verifyInvoiceTotalValue(testCaseData.getInvoiceData().getInvoiceTotal());
		InvoicesSteps.saveInvoiceAsFinal();

		NavigationSteps.navigateBackScreen();

		BaseUtils.waitABit(30*1000);
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPage.clickInvoicesLink();
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();

		String mainWindowHandle = webdriver.getWindowHandle();
		invoicesWebPage.clickInvoicePrintPreview(invoiceNumber);
		Assert.assertEquals(invoicesWebPage.getPrintPreviewTotalListValue(), BackOfficeUtils.getFormattedServicePriceValue(listTotal));
		Assert.assertEquals(invoicesWebPage.getPrintPreviewTotalNetValue(), BackOfficeUtils.getFormattedServicePriceValue(testCaseData.getInvoiceData().getInvoiceTotal()));

		invoicesWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatOnPrintOutOfDentWizardHailTemplateAllCalculationDataIsCorrect_ProductionData(String rowID,
																												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		HomeScreenSteps.navigateToMyWorkOrdersScreen();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DW_INVOICE_HAIL);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		NavigationSteps.navigateToServicesScreen();

		for (DamageData damageData : workOrderData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreen servicesScreen = new ServicesScreen();
			servicesScreen.clickServiceTypesButton();
		}

		NavigationSteps.navigateToOrderSummaryScreen();
		WorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

		WorkOrdersSteps.saveWorkOrder();
		MyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

		MyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
		MyWorkOrdersSteps.clickInvoiceIcon();

		InvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceInfoScreenSteps.getInvoiceNumber();
		InvoiceInfoScreenValidations.verifyInvoiceTotalValue(testCaseData.getInvoiceData().getInvoiceTotal());
		InvoicesSteps.saveInvoiceAsFinal();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatUpChargesAndDiscountsAreCalculatedCorrectlyOnAllLevels(String rowID,
																		   String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		final String subTotal = "$203.90";
		final String subTotal2 = "$303.90";

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		HomeScreenSteps.navigateToMyWorkOrdersScreen();
		MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.CALC_ORDER);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreenSteps.openCustomServiceDetails(workOrderData.getServicesScreen().getBundleService().getBundleServiceName());
		SelectedServiceBundleScreen selectedServiceBundleScreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : workOrderData.getServicesScreen().getBundleService().getMoneyServices()) {
			selectedServiceBundleScreen.clickServicesIcon();
			selectedServiceBundleScreen.openBundleMoneyServiceDetailsFromServicesScrollElement(serviceData);
			ServiceDetailsScreenSteps.setServiceDetailsDataAndSave(serviceData);

		}

		for (ServiceData serviceData : workOrderData.getServicesScreen().getBundleService().getPercentageServices()) {
			selectedServiceBundleScreen.clickServicesIcon();
			selectedServiceBundleScreen.selectBundlePercentageServiceFromServicesScrollElement(serviceData);

		}
		selectedServiceBundleScreen.changeAmountOfBundleService(workOrderData.getServicesScreen().getBundleService().getBundleServiceAmount());
		ServiceDetailsScreenSteps.saveServiceDetails();

		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getServicesScreen().getMatrixService());
		PriceMatrixScreenValidations.verifyPriceMatrixScreenTotalValue(workOrderData.getServicesScreen().getMatrixService().getVehiclePartData().getVehiclePartTotalPrice());
		PriceMatrixScreenSteps.savePriceMatrixData();
		WizardScreenValidations.verifyScreenSubTotalPrice(subTotal);
		ServicesScreenSteps.selectLaborServiceAndSetData(workOrderData.getServicesScreen().getLaborService());
		ServiceDetailsScreenSteps.saveServiceDetails();
		WizardScreenValidations.verifyScreenSubTotalPrice(subTotal2);

		for (ServiceData serviceData : workOrderData.getServicesScreen().getPercentageServices())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		ServicesScreenSteps.waitServicesScreenLoad();
		WizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
		WizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
		NavigationSteps.navigateToOrderSummaryScreen();
		WorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

		WorkOrdersSteps.saveWorkOrder();

		MyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

		NavigationSteps.navigateBackScreen();

	}

	@AfterMethod
	public void closeBrowser() {
		if (webdriver != null)
			webdriver.quit();
	}
}

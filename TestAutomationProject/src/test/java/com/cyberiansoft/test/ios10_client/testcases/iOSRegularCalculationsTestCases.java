package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
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
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyWorkOrdersScreenValidations;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.DentWizardInvoiceTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class iOSRegularCalculationsTestCases extends ReconProBaseTestCase {

	private RetailCustomer testRetailCustomer = new RetailCustomer("Retail", "Customer");

	@BeforeClass
	public void setUpSuite() {
		JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCalculationsTestCasesDataPath();
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "AutomationCalculations_Regular2",
				envType);

		RegularMainScreen mainScreen = new RegularMainScreen();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularHomeScreenSteps.navigateToSettingsScreen();
		RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
		settingsScreen.setShowAvailableSelectedServicesOn();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateInspectionOnTheDeviceWithApprovalRequired(String rowID,
																	String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		RegularNavigationSteps.navigateToVisualScreen(WizardScreenTypes.VISUAL_INTERIOR);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_VIN_REQUIRED);

		vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehicleScreen.clickSave();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_MAKE_REQUIRED);

		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		String inspnumber = vehicleScreen.getInspectionNumber();
		RegularInspectionsSteps.saveInspection();

		RegularMyInspectionsSteps.selectInspectionForApprove(inspnumber);
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspnumber);
		approveInspectionsScreen.clickApproveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspnumber));

		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAddServicesToVisualInspection(String rowID,
												  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.DEFAULT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_VIN_REQUIRED);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		vehicleScreen.clickSave();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_MAKE_REQUIRED);

		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);

		int tapXCoordInicial = 100;
		int tapYCoordInicial = 100;
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
			RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
			visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
			for (DamageData damageData : visualScreenData.getDamagesData()) {
				visualInteriorScreen.clickServicesToolbarButton();
				visualInteriorScreen.switchToCustomTab();
				visualInteriorScreen.selectService(damageData.getDamageGroupName());
				visualInteriorScreen.selectSubService(damageData.getMoneyService().getServiceName());
				RegularVisualInteriorScreen.tapInteriorWithCoords(tapXCoordInicial, tapYCoordInicial);
				RegularVisualInteriorScreen.tapInteriorWithCoords(tapXCoordInicial, tapYCoordInicial + 50);
				tapYCoordInicial = tapYCoordInicial + 100;
				visualInteriorScreen.selectService(damageData.getDamageGroupName());
			}
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), visualScreenData.getScreenTotalPrice());
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), visualScreenData.getScreenPrice());
		}

		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);

		tapXCoordInicial = 100;
		tapYCoordInicial = 100;
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
			RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
			for (DamageData damageData : visualScreenData.getDamagesData()) {
				visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
				RegularVisualInteriorScreen.tapInteriorWithCoords(tapXCoordInicial, tapYCoordInicial);
				visualInteriorScreen.setCarServiceQuantityValue(damageData.getMoneyService().getServiceQuantity());
				visualInteriorScreen.saveCarServiceDetails();
				tapYCoordInicial = tapYCoordInicial + 100;
			}
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), visualScreenData.getScreenTotalPrice2());
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), visualScreenData.getScreenPrice2());
		}

		RegularInspectionsSteps.saveInspection();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyPanelThenItWillBeAddedOnceForManyAssociatedServiceInstancesWithSameVehiclePart(String rowID,
																																   String description, JSONObject testData) {
		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity(String rowID,
																																								   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_FEE_ITEM_IN_2_PACKS);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getServiceData().getServicePrice());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();


		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = new WorkOrdersWebPage(webdriver);
		operationsWebPageWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber);
		workOrdersWebPage.unselectInvoiceFromDeviceCheckbox();
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();

		WorkOrderInfoTabWebPage workOrderInfoTabWebPage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber);
		Assert.assertTrue(workOrderInfoTabWebPage.isServicePriceCorrectForWorkOrder("$36.00"));
		Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("Service_in_2_fee_packs"));
		Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("Oksi_Test1"));
		Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("Oksi_Test2"));
		workOrderInfoTabWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatPackagePriceOfFeeBundleItemIsOverrideThePriceOfWholesaleAndRetailPrices(String rowID,
																									  String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FEE_PRICE_OVERRIDE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances(String rowID,
																														String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();

		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyServiceOrFlatFeeThenItWillBeAddedToWOEveryTimeWhenAssociatedServiceInstanceWillAddToWO(String rowID,
																																		   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatForWholesaleAndRetailCustomersFeeIsAddedDependsOnThePriceAccordinglyToPriceOfTheFeeBundleItem(String rowID,
																															String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices(String rowID,
																						 String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
		final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(workOrderData.getMatrixServiceData().getMatrixServiceName());
		RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartName());
		vehiclePartScreen.switchOffOption(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartOption());
		vehiclePartScreen.setPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
		for (ServiceData serviceData : workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalServices())
			vehiclePartScreen.selectDiscaunt(serviceData.getServiceName());
		Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartSubTotalPrice(), workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTotalPrice());
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(priceMatrixScreen.getInspectionSubTotalPrice(), workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTotalPrice());
		priceMatrixScreen.clickSave();

		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();


		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = new WorkOrdersWebPage(webdriver);
		operationsWebPageWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber);
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage woinfowebpage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber);
		Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$2,127.50"));
		Assert.assertTrue(woinfowebpage.isServiceSelectedForWorkOrder("SR_S6_FeeBundle"));

		woinfowebpage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_095(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

			if (serviceData.getServicePrice2() != null) {
				RegularServicesScreenSteps.switchToSelectedServices();
				RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
				RegularSelectedServiceDetailsScreen selectedselectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedselectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
				selectedServicesScreen.switchToAvailableServicesTab();
			}
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_003(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			if (serviceData.getServicePrice2() != null) {
				RegularServicesScreenSteps.switchToSelectedServices();
				RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
				RegularSelectedServiceDetailsScreen selectedselectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedselectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
				selectedServicesScreen.switchToAvailableServicesTab();
			}
		}
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_005(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

			if (serviceData.getServicePrice2() != null) {
				RegularServicesScreenSteps.switchToSelectedServices();
				RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
				RegularSelectedServiceDetailsScreen selectedselectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedselectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
				selectedServicesScreen.switchToAvailableServicesTab();
			}
		}
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly(String rowID,
																	  String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = new WorkOrdersWebPage(webdriver);
		operationsWebPageWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber);
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage woinfowebpage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber);
		Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$3,153.94"));

		woinfowebpage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined(String rowID,
																							   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_SIMPLE);
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.DEFAULT);
		RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
		for (VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
			RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
			vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
			vehiclePartScreen.saveVehiclePart();
		}
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.clickSkipAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickCancelStatusReasonButton();

		approveInspectionsScreen.clickDeclineAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.selectStatusReason(inspectionData.getDeclineReason());
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsWebPageWebPage.clickInspectionsLink();
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

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		servicesScreen.waitServicesScreenLoaded();
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);
		}
		approveInspectionsScreen.clickSaveButton();

		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsWebPageWebPage.clickInspectionsLink();
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

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}

		servicesScreen.waitServicesScreenLoaded();
		RegularInspectionsSteps.saveInspection();

		RegularMyInspectionsSteps.selectInspectionForApprove(inspectionNumber);
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.clickSkipAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsWebPageWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		inspectionsWebPage.selectSearchStatus("Declined");
		inspectionsWebPage.searchInspectionByNumber(inspectionNumber);
		Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber), "$0.00");
		Assert.assertEquals(inspectionsWebPage.getInspectionApprovedTotal(inspectionNumber), "$0.00");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionVerifyServicesAreMarkedAsStrikethroughWhenExcludeFromTotal(String rowID,
																						 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), inspectionData.getInspectionPrice());
		RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		Assert.assertEquals(approveInspectionsScreen.getInspectionTotalAmount(), inspectionData.getInspectionPrice());
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatValueSelectedOnPriceMatrixStepIsSavedAndShownDuringEditMode(String rowID,
																						  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
			RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
			RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			vehiclePartScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(),
					priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService() != null)
				vehiclePartScreen.selectDiscaunt(priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice() != null)
				vehiclePartScreen.setPrice(priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice());
			vehiclePartScreen.saveVehiclePart();
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), priceMatrixScreenData.getMatrixScreenPrice());
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), priceMatrixScreenData.getMatrixScreenTotalPrice());
		}
		vehicleScreen.swipeScreenLeft();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(inspectionData.getServiceData().getServiceName());
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), inspectionData.getInspectionPrice());
		servicesScreen.clickSave();
		Helpers.acceptAlert();
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
			RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
			priceMatrixScreen.waitPriceMatrixScreenLoad();
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), priceMatrixScreenData.getMatrixScreenPrice());
		}
		RegularInspectionsSteps.saveInspection();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																	String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectServiceWithServiceData(workOrderData.getServiceData());

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();

		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_WO_IS_HUGE, workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.swipeScreenLeft();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity2());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen.waitSelectedServicesScreenLoaded();
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																		  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final int workOrdersToCreate = 2;
		List<String> workOrders = new ArrayList<>();
		final String locationValue = "All locations";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		for (int i = 0; i < workOrdersToCreate; i++) {
			RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
			RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
			vehicleScreen.setVIN(testCaseData.getWorkOrderData().getVehicleInfoData().getVINNumber());
			workOrders.add(vehicleScreen.getWorkOrderNumber());

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreenSteps.selectServiceWithServiceData(testCaseData.getWorkOrderData().getServiceData());

			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
			orderSummaryScreen.setTotalSale(testCaseData.getWorkOrderData().getWorkOrderTotalSale());
			RegularWorkOrdersSteps.saveWorkOrder();
		}
		
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.approveWorkOrder(workOrders.get(0), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularMyWorkOrdersSteps.switchToTeamView();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.setFilterCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		teamWorkOrdersScreen.setFilterLocation(locationValue);
		teamWorkOrdersScreen.setBilling("All");
		teamWorkOrdersScreen.clickSaveFilter();

		teamWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrders.get(0));
		myWorkOrdersScreen.clickInvoiceIcon();

		RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_CUSTOM1);
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		RegularNavigationSteps.navigateToInvoiceInfoScreen();
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();
		teamWorkOrdersScreen.clickHomeButton();
		homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.approveWorkOrder(workOrders.get(1), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrders.get(1));
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToMyInvoicesScreen();
		RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
		questionsScreen.waitQuestionsScreenLoaded();
		RegularNavigationSteps.navigateToInvoiceInfoScreen();
		invoiceInfoScreen.addTeamWorkOrder(workOrders.get(1));
		invoiceInfoScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INVOICE_IS_HUGE, testCaseData.getInvoiceData().getInvoiceTotal()));
		invoiceInfoScreen.swipeScreenLeft();
		questionsScreen.waitQuestionsScreenLoaded();
		RegularInvoicesSteps.cancelCreatingInvoice();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																			String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
		servicesScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INSPECTION_IS_HUGE, inspectionData.getInspectionPrice()));

		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServiceQuantityValue(inspectionData.getServiceData().getServiceQuantity2());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		RegularInspectionsSteps.saveInspectionAsDraft();
		RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyApprovedAmountForInspectionCreatedFromSR(String rowID,
																   String description, JSONObject testData) {

		ServiceRequestData serviceRequestData = JSonDataParser.getTestDataFromJson(testData, ServiceRequestData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());

		RegularNavigationSteps.navigateToScreen(serviceRequestData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		questionsScreen.clickSave();
		Assert.assertTrue(Helpers.getAlertTextAndCancel().contains(AlertsCaptions.ALERT_CREATE_APPOINTMENT));

		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.waitVehicleScreenLoaded();
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectServiceWithServiceData(serviceRequestData.getInspectionData().getMoneyServiceData());
		servicesScreen.waitServicesScreenLoaded();
		RegularServiceRequestSteps.saveServiceRequest();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectDetailsRequestAction();
		RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();

		teamInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		teamInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getMoneyServiceData());
		approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getPercentageServiceData());
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
		BaseUtils.waitABit(2000);
		Assert.assertTrue(teamInspectionsScreen.checkInspectionIsApproved(inspectionNumber));
		Assert.assertEquals(teamInspectionsScreen.getFirstInspectionAprovedPriceValue(), serviceRequestData.getInspectionData().getInspectionPrice());
		Assert.assertEquals(teamInspectionsScreen.getFirstInspectionPriceValue(), serviceRequestData.getInspectionData().getInspectionTotalPrice());
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly(String rowID,
																			 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());


		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
			RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
			VehiclePartData vehiclePartData = priceMatrixScreenData.getVehiclePartData();
			RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
			if (vehiclePartData.getVehiclePartSize() != null)
				vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
			if (vehiclePartData.getVehiclePartOption() != null)
				vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
			if (vehiclePartData.getVehiclePartPrice() != null)
				vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
			if (vehiclePartData.getVehiclePartTime() != null)
				vehiclePartScreen.setTime(vehiclePartData.getVehiclePartTime());
			if (vehiclePartData.getVehiclePartTotalPrice() != null)
				vehiclePartScreen.setTime(vehiclePartData.getVehiclePartTime());
			for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
				vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
				Assert.assertEquals(vehiclePartScreen.getPriceMatrixVehiclePartSubTotalPrice(), serviceData.getServicePrice());
			}
			vehiclePartScreen.saveVehiclePart();
		}
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		RegularMyInspectionsSteps.selectInspectionForApprove(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.approveInspectionApproveAllAndSignature();
		RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionNumber, WorkOrdersTypes.WO_SMOKE_MONITOR);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), inspectionData.getInspectionPrice());
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData.getServiceName(),
					serviceData.getServicePrice()));
		}
		RegularInspectionsSteps.cancelCreatingInspection();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection(String rowID,
																											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_WITH_PART_SERVICES);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		RegularInspectionsSteps.saveInspectionAsDraft();
		RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();

		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}

		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), inspectionData.getMoneyServiceData().getServicePartData().getServicePartValue());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getMoneyServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.FUTURE_AUDI_CAR);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_AUDI_CAR);
		visualInteriorScreen.clickServicesToolbarButton();
		visualInteriorScreen.switchToCustomTab();
		visualInteriorScreen.selectService(inspectionData.getDamageData().getDamageGroupName());
		visualInteriorScreen.selectSubService(inspectionData.getDamageData().getMoneyService().getServiceName());
		Helpers.tapRegularCarImage();
		Helpers.tapRegularCarImage();
		selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.clickServicePartCell();
		RegularServicePartSteps.selectServicePartData(inspectionData.getDamageData().getMoneyService().getServicePartData());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getDamageData().getMoneyService().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		final PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreenData();
		RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
		RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
		final VehiclePartData vehiclePartData = priceMatrixScreenData.getVehiclePartData();
		RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
		vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
			vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.clickServicePartCell();
			RegularServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), serviceData.getServicePartData().getServicePartValue());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		vehiclePartScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServiceQuantityValue(vehiclePartData.getVehiclePartAdditionalService().getServiceQuantity());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		vehiclePartScreen.saveVehiclePart();
		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), vehiclePartData.getVehiclePartTotalPrice());
		Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
		RegularInspectionsSteps.saveInspectionAsFinal();
		RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), vehiclePartData.getVehiclePartTotalPrice());
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionNumber, WorkOrdersTypes.WO_WITH_PART_SERVICE);
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWizardScreensSteps.clickSaveButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_WITH_PART_SERVICE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}

		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), workOrderData.getMoneyServiceData().getServicePartData().getServicePartValue());
		selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getMoneyServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectPriceMatrices(matrixServiceData.getMatrixServiceName());
		RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
		vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
			vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.clickServicePartCell();
			ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), serviceData.getServicePartData().getServicePartValue());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}


		Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartSubTotalPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
		vehiclePartScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServiceQuantityValue(vehiclePartData.getVehiclePartAdditionalService().getServiceQuantity());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		priceMatrixScreen.clickSave();
		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyRoundingInCalculationScriptWithPriceMatrix(String rowID,
																	   String description, JSONObject testData) {

		final String filterBillingValue = "All";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		servicesScreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
		RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
		vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
			vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		vehiclePartScreen.saveVehiclePart();
		priceMatrixScreen.clickSave();
		RegularServicesScreenSteps.selectServiceWithServiceData(workOrderData.getServiceData());
		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		orderSummaryScreen.clickSave();

		RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSave();
		Helpers.acceptAlert();
		questionsScreen.selectAnswerForQuestion(invoiceData.getQuestionScreenData().getQuestionData());

		invoiceInfoScreen.clickSaveAsFinal();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.clickFilterButton();
		myWorkOrdersScreen.setFilterBilling(filterBillingValue);
		myWorkOrdersScreen.clickSaveFilter();

		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToMyInvoicesScreen();
		RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
		Assert.assertEquals(myInvoicesScreen.getPriceForInvoice(invoiceNumber), invoiceData.getInvoiceTotal());
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyCalculationWithPriceMatrixLaborType(String rowID,
																  String description, JSONObject testData) {
		final String filterBillingValue = "All";
		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getServicePrice() != null) {
				RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			} else {
				RegularServicesScreenSteps.selectService(serviceData.getServiceName());
			}
		}
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice() != null) {
				RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
				selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else {
				RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
			}
		}
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen = new RegularServicesScreen();
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		RegularPriceMatricesScreen priceMatricesScreen = new RegularPriceMatricesScreen();
		RegularPriceMatrixScreen priceMatrixScreen = priceMatricesScreen.selectPriceMatrice(matrixServiceData.getHailMatrixName());
		RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartScreen.switchOffOption(matrixServiceData.getVehiclePartData().getVehiclePartOption());
		vehiclePartScreen.setTime(matrixServiceData.getVehiclePartData().getVehiclePartTime());
		for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
			vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		vehiclePartScreen.saveVehiclePart();
		priceMatrixScreen.clickSave();

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		orderSummaryScreen.clickSave();
		orderSummaryScreen.clickInvoiceType("Invoice_AutoWorkListNet");
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(invoiceData.getQuestionScreenData().getQuestionData());
		RegularNavigationSteps.navigateToInvoiceInfoScreen();
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.clickFilterButton();
		myWorkOrdersScreen.setFilterBilling(filterBillingValue);
		myWorkOrdersScreen.clickSaveFilter();

		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		RegularNavigationSteps.navigateBackScreen();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPageWebPage.clickInvoicesLink();

		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.clickInvoicePrintPreview(invoiceNumber);
		invoicesWebPage.waitABit(4000);
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

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		servicesScreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
		RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
		vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
		vehiclePartScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		priceMatrixScreen.clickSave();

		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		selectedServiceDetailsScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.waitServicesScreenLoaded();
		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectWorkOrderForApprove(workOrderNumber);
		myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.clickApproveButton();
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber);
		myWorkOrdersScreen.clickInvoiceIcon();
		RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		RegularNavigationSteps.navigateBackScreen();


		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPageWebPage.clickInvoicesLink();
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
	public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels(String rowID,
																												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_WITH_0_PRICE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspnumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspnumber);
		Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), inspectionData.getServiceData().getServicePrice());
		approveInspectionsScreen.clickCancelButton();
		approveInspectionsScreen.clickCancelButton();

		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		RegularMyInspectionsSteps.selectInspectionForEdit(inspnumber);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getServiceData().getServicePrice2());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		RegularInspectionsSteps.saveInspection();

		myInspectionsScreen.selectInspectionForAction(inspnumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspnumber);
		Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), PricesCalculations.getPriceRepresentation(inspectionData.getServiceData().getServicePrice2()));
		approveInspectionsScreen.clickCancelButton();
		approveInspectionsScreen.clickCancelButton();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection(String rowID,
																															   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice() != null) {
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspnumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspnumber);
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);

		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspnumber), inspectionData.getInspectionPrice());
		Assert.assertEquals(myInspectionsScreen.getInspectionApprovedPriceValue(inspnumber), inspectionData.getInspectionApprovedPrice());
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection(String rowID,
																													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice() != null) {
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		RegularInspectionsSteps.saveInspectionAsFinal();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.clickDeclineAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.selectStatusReason(inspectionData.getDeclineReason());
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		myInspectionsScreen.clickFilterButton();
		myInspectionsScreen.clickStatusFilter();
		myInspectionsScreen.clickFilterStatus(InspectionStatuses.DECLINED.getInspectionStatusValue());
		myInspectionsScreen.clickBackButton();
		myInspectionsScreen.clickSaveFilterDialogButton();
		Assert.assertEquals(myInspectionsScreen.getInspectionApprovedPriceValue(inspectionNumber), inspectionData.getInspectionApprovedPrice());
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatForBundleItemsPricePolicyIsApplied(String rowID,
																   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesScreen.selectService(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
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
			Assert.assertEquals(selectedServiceBundleScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
		}

		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(bundleServiceData.getLaborService().getServiceName());
		selectedServiceDetailsScreen.setServiceTimeValue(bundleServiceData.getLaborService().getLaborServiceTime());
		selectedServiceDetailsScreen.setServiceRateValue(bundleServiceData.getLaborService().getLaborServiceRate());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedServiceBundleScreen.getServiceDetailsPriceValue(), bundleServiceData.getBundleServiceAmount());

		selectedServiceDetailsScreen.changeAmountOfBundleService(workOrderData.getWorkOrderPrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData(String rowID,
																											String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		RetailCustomer retailCustomer = new RetailCustomer("Avalon", "");

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(retailCustomer, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (TaxServiceData taxServiceData : workOrderData.getTaxServicesData()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(taxServiceData.getTaxServiceName());
			for (ServiceRateData serviceRateData : taxServiceData.getServiceRatesData()) {
				Assert.assertEquals(selectedServiceDetailsScreen.getServiceRateValue(serviceRateData), serviceRateData.getServiceRateValue());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);

		RegularNavigationSteps.navigateToServicesScreen();

		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(workOrderData.getTaxServicesData().get(0).getTaxServiceName());
		Assert.assertFalse(selectedServiceDetailsScreen.isServiceRateFieldEditable(workOrderData.getTaxServicesData().get(0).getServiceRatesData().get(0)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(workOrderData.getTaxServiceData().getTaxServiceName());
		selectedServiceDetailsScreen.setServiceRateFieldValue(workOrderData.getTaxServiceData().getServiceRateData());
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceRateValue(workOrderData.getTaxServiceData().getServiceRateData()), "%" + workOrderData.getTaxServiceData().getServiceRateData().getServiceRateValue());
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceDetailsTotalValue(), workOrderData.getTaxServiceData().getTaxServiceTotal());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen.waitSelectedServicesScreenLoaded();
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.waitWorkOrderSummaryScreenLoad();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceDetailsPriceValue(), workOrderData.getPercentageServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularInspectionsSteps.saveInspection();

		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
		RegularNavigationSteps.navigateToServicesScreen();

		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice2());
		}
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatPackagePriceOverridesClient_RretailOrWholesale(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> workOrders = new ArrayList<>();
		RetailCustomer retailCustomer = new RetailCustomer("Oksana", "Avalon");

		final int workOrderIndexToEdit = 1;
		final String workOrderNewPrice = "$35.00";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
			customersScreen.swtchToWholesaleMode();
			customersScreen.selectCustomerWithoutEditing(workOrderData.getWholesailCustomer().getCompany());

			RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
 			RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
			RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
			vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
			String workOrderNumber = vehicleScreen.getWorkOrderNumber();
			workOrders.add(workOrderNumber);
			if (workOrderData.getQuestionScreenData() != null) {
				RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
				RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
				questionsScreen.swipeScreenUp();
				questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
			}

			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
			orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			RegularWorkOrdersSteps.saveWorkOrder();

			RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesScreen = new RegularServicesScreen();
			servicesScreen.selectService(workOrderData.getServiceData().getServiceName());
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
			RegularWorkOrdersSteps.saveWorkOrder();
			RegularNavigationSteps.navigateBackScreen();
		}
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.changeCustomerForWorkOrder(workOrders.get(workOrderIndexToEdit), retailCustomer);
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrders.get(workOrderIndexToEdit), false);
		RegularNavigationSteps.navigateBackScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		homeScreen.clickMyWorkOrdersButton();
		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrders.get(workOrderIndexToEdit));
		RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
		RegularNavigationSteps.navigateToServicesScreen();

		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getWorkOrderPrice());
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getServiceData().getServiceName());
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderNewPrice);
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO(String rowID,
																		 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(WorkOrdersTypes.WO_TYPE_WITH_JOB, workOrderData.getWorkOrderJob());
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesScreen.selectService(serviceData.getServiceName());
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice());
		}
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatForDeclinedSkippedaServicesAppropriateIconIsShownAfterApproval(String rowID,
																										String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspnumber = vehicleScreen.getInspectionNumber();

		RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}

		for (ServiceData serviceData : inspectionData.getPercentageServicesList()) {
			servicesScreen.selectService(serviceData.getServiceName());
		}
		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesScreen.selectService(bundleServiceData.getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}
		selectedServiceBundleScreen.clickSaveButton();

		RegularNavigationSteps.navigateToScreen("Future Sport Car");
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.clickServicesToolbarButton();
		visualInteriorScreen.selectService(inspectionData.getServiceData().getServiceName());
		RegularVisualInteriorScreen.tapInteriorWithCoords(150, 200);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 250);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 300);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 350);

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
			RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
			RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartSize() != null)
				vehiclePartScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(), priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartOption() != null) {
				vehiclePartScreen.switchOffOption(priceMatrixScreenData.getVehiclePartData().getVehiclePartOption());
				vehiclePartScreen.setTime(priceMatrixScreenData.getVehiclePartData().getVehiclePartTime());
			}
			vehiclePartScreen.saveVehiclePart();
		}
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspnumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspnumber);
		approveInspectionsScreen.clickDeclineAllServicesButton();
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);

		approveInspectionsScreen.clickSaveButton();
		//approveInspectionsScreen.selectStatusReason("Decline 1");
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		BaseUtils.waitABit(10 * 1000);
		RegularMyInspectionsSteps.selectInspectionForEdit(inspnumber);
		visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(selectedServicesScreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		Assert.assertTrue(selectedServicesScreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.TAX_DISCOUNT));
		RegularInspectionsSteps.cancelCreatingInspection();
		RegularNavigationSteps.navigateBackScreen();
	}
}

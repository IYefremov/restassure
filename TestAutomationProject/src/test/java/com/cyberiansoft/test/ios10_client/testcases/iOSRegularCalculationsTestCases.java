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
import com.cyberiansoft.test.ios10_client.hdclientsteps.ServicePartSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VisualInteriorScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class iOSRegularCalculationsTestCases extends ReconProBaseTestCase {

	private RegularHomeScreen homeScreen;
	
	@BeforeClass
	public void setUpSuite() {
		JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCalculationsTestCasesDataPath();
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "AutomationCalculations_Regular2",
				envType);

		RegularMainScreen mainScreen = new RegularMainScreen();
		homeScreen = mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homeScreen.waitHomeScreenLoaded();
		RegularSettingsScreen settingsScreen =  homeScreen.clickSettingsButton();
		settingsScreen.setShowAvailableSelectedServicesOn();
		homeScreen = settingsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionOnTheDeviceWithApprovalRequired(String rowID,
																	String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
			
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
        RegularVehicleScreen vehicleScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        RegularClaimScreen claimScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimScreen.waitClaimScreenLoad();
        RegularVisualInteriorScreen visualInteriorScreen = claimScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
        RegularServicesScreen servicesScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

		myInspectionsScreen.selectInspectionForApprove(inspnumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen =  new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspnumber);
		approveInspectionsScreen.clickApproveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspnumber));
			
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testAddServicesToVisualInspection(String rowID,
												  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
			
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
		RegularVehicleScreen vehicleScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.DEFAULT);
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
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
        vehicleScreen = new RegularVehicleScreen();

        int tapXCoordInicial = 100;
		int tapYCoordInicial = 100;
        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularVisualInteriorScreen visualInteriorScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, visualScreenData.getScreenName());
			visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
			for (DamageData damageData : visualScreenData.getDamagesData()) {
				visualInteriorScreen.clickServicesToolbarButton();
				visualInteriorScreen.switchToCustomTab();
				visualInteriorScreen.selectService(damageData.getDamageGroupName());
				visualInteriorScreen.selectSubService(damageData.getMoneyService().getServiceName());
				RegularVisualInteriorScreen.tapInteriorWithCoords(tapXCoordInicial, tapYCoordInicial);
				RegularVisualInteriorScreen.tapInteriorWithCoords(tapXCoordInicial, tapYCoordInicial+50);
				tapYCoordInicial = tapYCoordInicial + 100;
				visualInteriorScreen.selectService(damageData.getDamageGroupName());
			}
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), visualScreenData.getScreenTotalPrice());
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), visualScreenData.getScreenPrice());
		}

		RegularInspectionsSteps.saveInspection();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		vehicleScreen = new RegularVehicleScreen();

		tapXCoordInicial = 100;
		tapYCoordInicial = 100;
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularVisualInteriorScreen visualInteriorScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, visualScreenData.getScreenName());
			visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
			for (DamageData damageData : visualScreenData.getDamagesData()) {
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
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyPanelThenItWillBeAddedOnceForManyAssociatedServiceInstancesWithSameVehiclePart(String rowID,
																																   String description, JSONObject testData) {
		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			servicedetailsscreen.setServicePriceValue(serviceData.getServicePrice());
			servicedetailsscreen.clickVehiclePartsCell();
			servicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			servicedetailsscreen.saveSelectedServiceDetails();
			servicedetailsscreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	private String workOrderNumber28583 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_1(String rowID,
																																								   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_FEE_ITEM_IN_2_PACKS);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		workOrderNumber28583 = vehicleScreen.getWorkOrderNumber();
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getServiceData().getServicePrice());
        RegularOrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
		
	}
	
	@Test(testName="Test Case 28583:WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity", 
			description = "WO: Regular - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity")
	public void testRegularIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPageWebPage = backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = operationsWebPageWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber28583);
		workOrdersWebPage.unselectInvoiceFromDeviceCheckbox();
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		
		WorkOrderInfoTabWebPage workOrderInfoTabWebPage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber28583);
 		Assert.assertTrue(workOrderInfoTabWebPage.isServicePriceCorrectForWorkOrder("$36.00"));
		Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("Service_in_2_fee_packs"));
		Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("Oksi_Test1"));
		Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("Oksi_Test2"));
		workOrderInfoTabWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatPackagePriceOfFeeBundleItemIsOverrideThePriceOfWholesaleAndRetailPrices(String rowID,
																									  String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_FEE_PRICE_OVERRIDE);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		servicedetailsscreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(),  workOrderData.getWorkOrderPrice());
		
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances(String rowID,
																														String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);

		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.answerQuestion2(serviceData.getQuestionData());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyServiceOrFlatFeeThenItWillBeAddedToWOEveryTimeWhenAssociatedServiceInstanceWillAddToWO(String rowID,
																																		   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatForWholesaleAndRetailCustomersFeeIsAddedDependsOnThePriceAccordinglyToPriceOfTheFeeBundleItem(String rowID,
																															String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	private String workOrderNumber29398 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_1(String rowID,
																						 String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();

		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		workOrderNumber29398 = vehicleScreen.getWorkOrderNumber();
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		myWorkOrdersScreen.clickHomeButton();
		
	}
	
	@Test(testName="Test Case 29398:WO: Regular - Verify that Fee Bundle services is calculated for additional matrix services", 
			description = "Verify that Fee Bundle services is calculated for additional matrix services")
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPageWebPage = backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = operationsWebPageWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber29398);
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage woinfowebpage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber29398);
		Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$2,127.50"));
		Assert.assertTrue(woinfowebpage.isServiceSelectedForWorkOrder("SR_S6_FeeBundle"));

		woinfowebpage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_095(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedselectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedselectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedselectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedselectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();


			if (serviceData.getServicePrice2() != null) {
				RegularSelectedServicesScreen selectedServicesScreen = servicesScreen.switchToSelectedServicesTab();
				selectedselectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedselectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
				selectedServicesScreen.switchToAvailableServicesTab();
			}
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_003(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedselectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedselectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedselectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedselectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();


			if (serviceData.getServicePrice2() != null) {
				RegularSelectedServicesScreen selectedServicesScreen = servicesScreen.switchToSelectedServicesTab();
				selectedselectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedselectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
				selectedServicesScreen.switchToAvailableServicesTab();
			}
		}
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_005(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedselectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedselectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedselectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedselectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();


			if (serviceData.getServicePrice2() != null) {
				RegularSelectedServicesScreen selectedServicesScreen = servicesScreen.switchToSelectedServicesTab();
				selectedselectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				Assert.assertEquals(selectedselectedServiceDetailsScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
				selectedselectedServiceDetailsScreen.saveSelectedServiceDetails();
				selectedServicesScreen.switchToAvailableServicesTab();
			}
		}
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	private String workOrderNumber31498 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_1(String rowID,
																	  String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		workOrderNumber31498 = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());
        RegularOrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

		RegularWorkOrdersSteps.saveWorkOrder();
		myWorkOrdersScreen.clickHomeButton();

	}
	
	@Test(testName="Test Case 31498:WO: Regular - Verify that amount is calculated and rounded correctly", 
			description = "Verify that amount is calculated and rounded correctly")
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPageWebPage = backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = operationsWebPageWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber31498);
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage woinfowebpage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber31498);
		Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$3,153.94"));

		woinfowebpage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	private String inspectionNumber32226 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_1(String rowID,
																							   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homeScreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularQuestionsScreen questionsScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_SIMPLE);
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		
		RegularVehicleScreen vehicleScreen = questionsScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		inspectionNumber32226 = vehicleScreen.getInspectionNumber();
        RegularPriceMatrixScreen priceMatrixScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		for(VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
			RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
			vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
			vehiclePartScreen.saveVehiclePart();
		}
		RegularInspectionsSteps.saveInspection();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber32226);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber32226);
		approveInspectionsScreen.clickSkipAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickCancelStatusReasonButton();
		
		approveInspectionsScreen.clickDeclineAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.selectStatusReason(inspectionData.getDeclineReason());
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		myInspectionsScreen.clickHomeButton();

	}
	
	@Test(testName = "Test Case 32226:Inspections: Regular - Verify that inspection is saved as declined when all services are skipped or declined", 
			description = "Verify that inspection is saved as declined when all services are skipped or declined")
	public void testRegularVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_2()  {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPageWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = operationsWebPageWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		inspectionsWebPage.selectSearchStatus("Declined");
		inspectionsWebPage.searchInspectionByNumber(inspectionNumber32226);
		Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber32226), "$0.00");
		Assert.assertEquals(inspectionsWebPage.getInspectionReason(inspectionNumber32226), "Decline 1");
		Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber32226), "Declined");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	private String inspectionNumber32286 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_1(String rowID,
																										  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homeScreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVehicleScreen vehicleScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		inspectionNumber32286 = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		servicesScreen.waitServicesScreenLoaded();
		RegularInspectionsSteps.saveInspection();
		
		myInspectionsScreen.selectInspectionForAction(inspectionNumber32286);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber32286);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);
		}
		approveInspectionsScreen.clickSaveButton();

		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		myInspectionsScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32286:Inspections: Regular - Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount")
	public void testRegularVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPageWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = operationsWebPageWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		inspectionsWebPage.selectSearchStatus("All active");
		inspectionsWebPage.searchInspectionByNumber(inspectionNumber32286);		
		Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber32286), "$2,000.00");
		Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber32286), "Approved");
		DriverBuilder.getInstance().getDriver().quit();	
	}

	private String inspnumber32287 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_1(String rowID,
																		  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVehicleScreen vehicleScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		inspnumber32287 = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		servicesScreen.waitServicesScreenLoaded();
		RegularInspectionsSteps.saveInspection();
		
		myInspectionsScreen.selectInspectionForApprove(inspnumber32287);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen =  new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspnumber32287);
		approveInspectionsScreen.clickSkipAllServicesButton();		
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32287:Inspections: Regular - Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount")
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPageWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = operationsWebPageWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		inspectionsWebPage.selectSearchStatus("Declined");
		inspectionsWebPage.searchInspectionByNumber(inspnumber32287);		
		Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspnumber32287), "$0.00");
		Assert.assertEquals(inspectionsWebPage.getInspectionApprovedTotal(inspnumber32287), "$0.00");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionVerifyServicesAreMarkedAsStrikethroughWhenExcludeFromTotal(String rowID,
																						 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVisualInteriorScreen visualInteriorScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVehicleScreen vehicleScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehicleParts(serviceData.getVehicleParts());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), inspectionData.getInspectionPrice());
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		RegularInspectionsSteps.saveInspection();
		
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
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatValueSelectedOnPriceMatrixStepIsSavedAndShownDuringEditMode(String rowID,
																						  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVehicleScreen vehicleScreen =  myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularPriceMatrixScreen priceMatrixScreen =  vehicleScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			vehiclePartScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(),
					priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService() != null)
				vehiclePartScreen.selectDiscaunt(priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice() != null)
				vehiclePartScreen.setPrice(priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice());
			vehiclePartScreen.saveVehiclePart();
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(),priceMatrixScreenData.getMatrixScreenPrice());
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), priceMatrixScreenData.getMatrixScreenTotalPrice());
		}
		vehicleScreen.swipeScreenLeft();
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesScreen.selectService(inspectionData.getServiceData().getServiceName());
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), inspectionData.getInspectionPrice());
		servicesScreen.clickSave();
		Helpers.acceptAlert();
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		RegularInspectionsSteps.saveInspection();
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {

			RegularPriceMatrixScreen priceMatrixScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			priceMatrixScreen.waitPriceMatrixScreenLoad();
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(),priceMatrixScreenData.getMatrixScreenPrice());
		}
		RegularInspectionsSteps.saveInspection();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																	String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		
		//customer approval ON
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity());
		
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart(workOrderData.getServiceData().getVehiclePart().getVehiclePartName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

        RegularOrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();
		
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_WO_IS_HUGE, workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.swipeScreenLeft();
        servicesScreen = orderSummaryScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesScreen.switchToSelectedServicesTab();
		selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity2());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen.waitSelectedServicesScreenLoaded();
		RegularWorkOrdersSteps.saveWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																		  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final int workOrdersToCreate = 2;
		List<String> workOrders = new ArrayList<>();
		final String locationValue = "All locations";
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		for (int i = 0; i < workOrdersToCreate; i++) {
			myWorkOrdersScreen.clickAddOrderButton();
			RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
			vehicleScreen.setVIN(testCaseData.getWorkOrderData().getVehicleInfoData().getVINNumber());
			workOrders.add(vehicleScreen.getWorkOrderNumber());


			RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
			selectedServiceDetailsScreen.setServicePriceValue(testCaseData.getWorkOrderData().getServiceData().getServicePrice());
			selectedServiceDetailsScreen.setServiceQuantityValue(testCaseData.getWorkOrderData().getServiceData().getServiceQuantity());

			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehiclePart(testCaseData.getWorkOrderData().getServiceData().getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			RegularOrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			orderSummaryScreen.setTotalSale(testCaseData.getWorkOrderData().getWorkOrderTotalSale());
			RegularWorkOrdersSteps.saveWorkOrder();
		}


		myWorkOrdersScreen.approveWorkOrder(workOrders.get(0), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myWorkOrdersScreen.waitMyWorkOrdersScreenLoaded();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.setFilterCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		teamWorkOrdersScreen.setFilterLocation(locationValue);
		teamWorkOrdersScreen.setBilling("All");
		teamWorkOrdersScreen.clickSaveFilter();
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrders.get(0));
		myWorkOrdersScreen.clickInvoiceIcon();

		teamWorkOrdersScreen.selectInvoiceType(InvoicesTypes.INVOICE_CUSTOM1);
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		RegularInvoiceInfoScreen invoiceInfoScreen = questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		String invoicenum = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();
		teamWorkOrdersScreen.clickHomeButton();
		homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.approveWorkOrder(workOrders.get(1), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrders.get(1));
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		RegularWorkOrdersSteps.saveWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
		
		RegularMyInvoicesScreen myinvoicesscreen = homeScreen.clickMyInvoicesButton();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickEditPopup();
		questionsScreen = new RegularQuestionsScreen();
		questionsScreen.waitQuestionsScreenLoaded();
		invoiceInfoScreen = questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceInfoScreen.addWorkOrder(workOrders.get(1));
		invoiceInfoScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INVOICE_IS_HUGE, testCaseData.getInvoiceData().getInvoiceTotal()));
		invoiceInfoScreen.swipeScreenLeft();
		questionsScreen.waitQuestionsScreenLoaded();
		RegularWizardScreensSteps.cancelWizard();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																			String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVehicleScreen vehicleScreen =  myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());

        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.setServiceQuantityValue(inspectionData.getServiceData().getServiceQuantity());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart(inspectionData.getServiceData().getVehiclePart().getVehiclePartName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INSPECTION_IS_HUGE, inspectionData.getInspectionPrice()));

		RegularSelectedServicesScreen selectedServicesScreen = servicesScreen.switchToSelectedServicesTab();
		selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServiceQuantityValue(inspectionData.getServiceData().getServiceQuantity2());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen.clickSaveAsDraft();
		myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyApprovedAmountForInspectionCreatedFromSR(String rowID,
																   String description, JSONObject testData) {

		ServiceRequestData serviceRequestData = JSonDataParser.getTestDataFromJson(testData, ServiceRequestData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
		serviceRequestsScreen.clickAddButton();
		RegularVehicleScreen vehicleScreen = serviceRequestsScreen.selectServiceRequestType(ServiceRequestTypes.SR_ALL_PHASES);
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());

        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen = new RegularServicesScreen();
        RegularQuestionsScreen questionsScreen = servicesScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, serviceRequestData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		questionsScreen.clickSave();
		Assert.assertTrue(Helpers.getAlertTextAndCancel().contains(AlertsCaptions.ALERT_CREATE_APPOINTMENT));

		serviceRequestsScreen = new RegularServiceRequestsScreen();
		String srnumber = serviceRequestsScreen.getFirstServiceRequestNumber();
		serviceRequestsScreen.selectServiceRequest(srnumber);
		serviceRequestsScreen.selectCreateInspectionRequestAction();
        RegularVisualInteriorScreen visualInteriorScreen =  serviceRequestsScreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
        visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.waitVehicleScreenLoaded();
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceRequestData.getInspectionData().getMoneyServiceData().getServiceName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart(serviceRequestData.getInspectionData().getMoneyServiceData().getVehiclePart().getVehiclePartName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.waitServicesScreenLoaded();
		RegularServiceRequestSteps.saveServiceRequest();
		serviceRequestsScreen.selectServiceRequest(srnumber);
		serviceRequestsScreen.selectDetailsRequestAction();
		serviceRequestsScreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		
		teamInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		teamInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen =  new RegularApproveInspectionsScreen();
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
		teamInspectionsScreen.clickBackButton();
		serviceRequestsScreen.clickBackButton();
		serviceRequestsScreen = new RegularServiceRequestsScreen();
		serviceRequestsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly(String rowID,
																			 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVisualInteriorScreen visualInteriorScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVehicleScreen vehicleScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());


		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularPriceMatrixScreen priceMatrixScreen = questionsScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
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
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		myInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen =  new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.approveInspectionApproveAllAndSignature();
		myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForCreatingWO(inspectionNumber);
		vehicleScreen = myInspectionsScreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_MONITOR);
		vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), inspectionData.getInspectionPrice());
		RegularSelectedServicesScreen selectedServicesScreen = servicesScreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData.getServiceName(),
					serviceData.getServicePrice()));
		}
		RegularInspectionsSteps.cancelCreatingInspection();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection(String rowID,
																											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVehicleScreen vehicleScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_WITH_PART_SERVICES);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		RegularInspectionsSteps.saveInspectionAsDraft();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);

		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.clickServicePartCell();
			RegularServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePartValue(), inspectionData.getMoneyServiceData().getServicePartData().getServicePartValue());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getMoneyServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

        RegularVisualInteriorScreen visualInteriorScreen =vehicleScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, ScreenNamesConstants.FUTURE_AUDI_CAR);
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
        RegularPriceMatrixScreen priceMatrixScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
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
		Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(),  vehiclePartData.getVehiclePartSubTotalPrice());
		servicesScreen.clickSaveAsFinal();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), vehiclePartData.getVehiclePartTotalPrice());
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForCreatingWO(inspectionNumber);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		vehicleScreen = myInspectionsScreen.selectWorkOrderType(WorkOrdersTypes.WO_WITH_PART_SERVICE);
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		RegularOrderSummaryScreen orderSummaryScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWizardScreensSteps.clickSaveButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		homeScreen = myInspectionsScreen.clickHomeButton();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homeScreen = new RegularHomeScreen();			
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickAddOrderButton();

		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_WITH_PART_SERVICE);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularOrderSummaryScreen orderSummaryScreen = questionsScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		
		RegularWorkOrdersSteps.saveWorkOrder();
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen = new RegularVehicleScreen();
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.clickServicePartCell();
			RegularServicePartSteps.selectServicePartData(serviceData.getServicePartData());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
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
		RegularVehiclePartScreen vehiclePartScreen =  priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
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
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyRoundingInCalculationScriptWithPriceMatrix(String rowID,
																	   String description, JSONObject testData) {

		final String filterBillingValue = "All";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		
		homeScreen = new RegularHomeScreen();			
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickAddOrderButton();

		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen = new RegularServicesScreen();
		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();		
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());

        RegularOrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
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
		myWorkOrdersScreen.clickFilterButton();
		myWorkOrdersScreen.setFilterBilling(filterBillingValue);
		myWorkOrdersScreen.clickSaveFilter();
		
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		homeScreen = myWorkOrdersScreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homeScreen.clickMyInvoicesButton();
		Assert.assertEquals(myinvoicesscreen.getPriceForInvoice(invoiceNumber), invoiceData.getInvoiceTotal());
		myinvoicesscreen.clickHomeButton();		
	}
	
	private String invoiceNumber45224 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyCalculationWithPriceMatrixLaborType_1(String rowID,
																  String description, JSONObject testData) {
		final String filterBillingValue = "All";
		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		
		homeScreen = new RegularHomeScreen();			
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickAddOrderButton();

		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getServicePrice() != null) {
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else {
				servicesScreen.selectService(serviceData.getServiceName());
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

        RegularOrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		orderSummaryScreen.clickSave();
		orderSummaryScreen.clickInvoiceType("Invoice_AutoWorkListNet");
		questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(invoiceData.getQuestionScreenData().getQuestionData());
		RegularInvoiceInfoScreen invoiceInfoScreen = questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		invoiceNumber45224 = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickFilterButton();
		myWorkOrdersScreen.setFilterBilling(filterBillingValue);
		myWorkOrdersScreen.clickSaveFilter();
		
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		homeScreen = myWorkOrdersScreen.clickHomeButton();
	}
	
	
	@Test(testName = "Test Case 45224:WO: Regular - Verify calculation with price matrix Labor type", 
			description = "WO: Regular - Verify calculation with price matrix Labor type")
	public void testWOVerifyCalculationWithPriceMatrixLaborType_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPageWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = operationsWebPageWebPage.clickInvoicesLink();
		
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber45224);
		invoicesWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.clickInvoicePrintPreview(invoiceNumber45224);
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
	
	private String invoiceNumber42803 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyRoundingMoneyAmountValues_1(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		
		homeScreen = new RegularHomeScreen();			
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickAddOrderButton();

		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		servicesScreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
		RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		RegularVehiclePartScreen vehiclePartScreen =  priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
		vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
		vehiclePartScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		vehiclePartScreen.saveVehiclePart();
		priceMatrixScreen.clickSave();

		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion2(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
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
        RegularOrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		
		myWorkOrdersScreen.selectWorkOrderForApprove(workOrderNumber);
		myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen =  new RegularApproveInspectionsScreen();
		approveInspectionsScreen.clickApproveButton();
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber);
		myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceInfoScreen invoiceInfoScreen = myWorkOrdersScreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		invoiceNumber42803 = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	
	@Test(testName = "Test Case 42803:Invoices: Regular - Verify rounding money amount values", 
			description = "Invoices: Regular - Verify rounding money amount values")
	public void testInvoicesVerifyRoundingMoneyAmountValues_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPageWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = operationsWebPageWebPage.clickInvoicesLink();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber42803);
		invoicesWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicesWebPage.clickInvoiceInternalTechInfo(invoiceNumber42803);
		Assert.assertEquals(invoicesWebPage.getTechInfoServicesTableServiceValue("<Tax>", "Test service price matrix"), "4.67000");
		Assert.assertEquals(invoicesWebPage.getTechInfoServicesTableServiceValue("<Tax>", "Test service price matrix (Sev: None; Size: None)"), "3.79600");
		Assert.assertEquals(invoicesWebPage.getTechInfoServicesTableServiceValue("<Tax>", "Oksi_Service_PP_Flat_Fee"), "0.87400");
		invoicesWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels(String rowID,
																												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVehicleScreen vehicleScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_WITH_0_PRICE);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularInspectionsSteps.saveInspection();

		myInspectionsScreen.selectInspectionForAction(inspnumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspnumber);
		Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), inspectionData.getServiceData().getServicePrice());
		approveInspectionsScreen.clickCancelButton();
		approveInspectionsScreen.clickCancelButton();

		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		myInspectionsScreen.selectInspectionForEdit(inspnumber);
        vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesScreen.switchToSelectedServicesTab();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getServiceData().getServicePrice2());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		RegularInspectionsSteps.saveInspection();

		myInspectionsScreen.selectInspectionForAction(inspnumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspnumber);
		Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), PricesCalculations.getPriceRepresentation(inspectionData.getServiceData().getServicePrice2()));
		approveInspectionsScreen.clickCancelButton();
		approveInspectionsScreen.clickCancelButton();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection(String rowID,
																															   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		
		homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVisualInteriorScreen visualInteriorScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVehicleScreen vehicleScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion2(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice()!=null) {
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
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection(String rowID,
																													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		homeScreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
		RegularVehicleScreen vehicleScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if(serviceData.getServicePrice() != null)
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null) {
				selectedServiceDetailsScreen.clickVehiclePartsCell();
				selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			if (serviceData.getQuestionData() != null) {
				selectedServiceDetailsScreen.answerQuestion2(serviceData.getQuestionData());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServicePrice()!=null) {
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickSaveAsFinal();
		myInspectionsScreen = new RegularMyInspectionsScreen();
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
		myInspectionsScreen.clickHomeButton();		
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatForBundleItemsPricePolicyIsApplied(String rowID,
																   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homeScreen = new RegularHomeScreen();			
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickAddOrderButton();

		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

        servicesScreen = new RegularServicesScreen();
        RegularOrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData(String rowID,
																											String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String customer  = "Avalon";
		
		homeScreen = new RegularHomeScreen();			
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
			
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickAddOrderButton();
		customersScreen.selectOnlineCustomer(customer);
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (TaxServiceData taxServiceData : workOrderData.getTaxServicesData()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(taxServiceData.getTaxServiceName());
			for (ServiceRateData serviceRateData : taxServiceData.getServiceRatesData()) {
				Assert.assertEquals(selectedServiceDetailsScreen.getServiceRateValue(serviceRateData), serviceRateData.getServiceRateValue());
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

        RegularOrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
		vehicleScreen = new RegularVehicleScreen();
        servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServicesScreen selectedServicesScreen = servicesScreen.switchToSelectedServicesTab();
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
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homeScreen = new RegularHomeScreen();			
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularOrderSummaryScreen orderSummaryScreen = questionsScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.waitWorkOrderSummaryScreenLoad();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
		vehicleScreen = new RegularVehicleScreen();
        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
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
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceDetailsPriceValue(), workOrderData.getPercentageServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();		
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		homeScreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVehicleScreen vehicleScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularInspectionsSteps.saveInspection();
		
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		vehicleScreen = new RegularVehicleScreen();
		servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);

		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
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
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice2());
		}
		RegularInspectionsSteps.saveInspection();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatPackagePriceOverridesClient_RretailOrWholesale(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> workOrders = new ArrayList<>();
		final String retailCustomer = "Avalon";
		final int workOrderIndexToEdit = 1;
		final String workOrderNewPrice = "$35.00";
		
		homeScreen = new RegularHomeScreen();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
			customersScreen.swtchToWholesaleMode();
			customersScreen.selectCustomerWithoutEditing(workOrderData.getWholesailCustomer().getCompany());

			RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
			myWorkOrdersScreen.clickAddOrderButton();
			RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderType(WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
			vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
			String workOrderNumber = vehicleScreen.getWorkOrderNumber();
			workOrders.add(workOrderNumber);
			if (workOrderData.getQuestionScreenData() != null) {
				RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
				questionsScreen.swipeScreenUp();
				questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
			}

			RegularOrderSummaryScreen orderSummaryScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			RegularWorkOrdersSteps.saveWorkOrder();

			myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
			vehicleScreen = new RegularVehicleScreen();
			RegularServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesScreen.selectService(workOrderData.getServiceData().getServiceName());
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
			RegularWorkOrdersSteps.saveWorkOrder();
			myWorkOrdersScreen.clickHomeButton();
		}
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.selectWorkOrder(workOrders.get(workOrderIndexToEdit));
		myWorkOrdersScreen.clickChangeCustomerPopupMenu();
		RegularCustomersScreen customersScreen = new RegularCustomersScreen();
		customersScreen.swtchToRetailMode();
		customersScreen.selectCustomer(retailCustomer);
		myWorkOrdersScreen.waitMyWorkOrdersScreenLoaded();
		Assert.assertFalse(myWorkOrdersScreen.woExists(workOrders.get(workOrderIndexToEdit)), "Can't find work order: " + workOrders.get(workOrderIndexToEdit));
		myWorkOrdersScreen.clickHomeButton();
		
		customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		
		myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrders.get(workOrderIndexToEdit));
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        RegularNavigationSteps.navigateToServicesScreen();

		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getWorkOrderPrice());
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectSubService(testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getServiceData().getServiceName());
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderNewPrice);
		RegularWorkOrdersSteps.saveWorkOrder();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO(String rowID,
																		 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homeScreen = new RegularHomeScreen();			
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
			
		RegularMyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickAddOrderButton();
		RegularVehicleScreen vehicleScreen = myWorkOrdersScreen.selectWorkOrderTypeWithJob(WorkOrdersTypes.WO_TYPE_WITH_JOB, workOrderData.getWorkOrderJob());
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesScreen.selectService(serviceData.getServiceName());
			RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice());
		}
		RegularWorkOrdersSteps.saveWorkOrder();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatForDeclinedSkippedaServicesAppropriateIconIsShownAfterApproval(String rowID,
																										String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		homeScreen = new RegularHomeScreen();
		
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		RegularMyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
        RegularVisualInteriorScreen visualInteriorScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVehicleScreen vehicleScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspnumber = vehicleScreen.getInspectionNumber();

        RegularQuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
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
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity()!=null) {
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}
		selectedServiceBundleScreen.clickSaveButton();

        visualInteriorScreen = servicesScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, "Future Sport Car");
		visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		visualInteriorScreen.clickServicesToolbarButton();
		visualInteriorScreen.selectService(inspectionData.getServiceData().getServiceName());
		RegularVisualInteriorScreen.tapInteriorWithCoords(150, 200);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 250);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 300);
		RegularVisualInteriorScreen.tapInteriorWithCoords(200, 350);

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularPriceMatrixScreen priceMatrixScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartSize()!= null)
				vehiclePartScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(), priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartOption()!= null) {
				vehiclePartScreen.switchOffOption(priceMatrixScreenData.getVehiclePartData().getVehiclePartOption());
				vehiclePartScreen.setTime(priceMatrixScreenData.getVehiclePartData().getVehiclePartTime());
			}
			vehiclePartScreen.saveVehiclePart();
		}
		RegularInspectionsSteps.saveInspection();
		
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
		BaseUtils.waitABit(10*1000);
		myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForEdit(inspnumber);
		visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
		servicesScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.SERVICES);
        RegularSelectedServicesScreen selectedServicesScreen = servicesScreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(selectedServicesScreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		Assert.assertTrue(selectedServicesScreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.TAX_DISCOUNT));
		RegularInspectionsSteps.cancelCreatingInspection();
		myInspectionsScreen.clickHomeButton();
	}
}

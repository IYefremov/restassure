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
		MainScreen mainScreen = new MainScreen();
		homescreen = mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		SettingsScreen settingsScreen = homescreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.setInsvoicesCustomLayoutOff();
		settingsScreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionOnTheDeviceWithApprovalRequired(String rowID,
																				   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		homescreen = new HomeScreen();
		SettingsScreen settingsScreen = homescreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();
			
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen =  myInspectionsScreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		final String inspNumber = vehicleScreen.getInspectionNumber();
		ClaimScreen claimScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimScreen.waitClaimScreenLoad();
		VisualInteriorScreen visualInteriorScreen = claimScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
		visualInteriorScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		visualInteriorScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_VIN_REQUIRED);
			
		vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVinNumber());
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
		ApproveInspectionsScreen approveInspectionsScreen =  new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspNumber);
		approveInspectionsScreen.clickApproveAfterSelection();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspNumber));
			
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testAddServicesToVisualInspection(String rowID,
																	String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		homescreen = new HomeScreen();
		SettingsScreen settingsScreen = homescreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();
		
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		myInspectionsScreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				InspectionsTypes.DEFAULT_INSPECTION_TYPE);
		VehicleScreen vehicleScreen = new VehicleScreen();
		final String inspNumber = vehicleScreen.getInspectionNumber();
		vehicleScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_VIN_REQUIRED);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		vehicleScreen.clickSave();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_MAKE_REQUIRED);
		
		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehicleScreen.saveWizard();
		myInspectionsScreen.selectInspectionForEdit(inspNumber);
		vehicleScreen = new VehicleScreen();
		int screenTapCount = 1;
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			VisualInteriorScreen visualInteriorScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, visualScreenData.getScreenName());
			visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
			for (DamageData damageData : visualScreenData.getDamagesData()) {
				visualInteriorScreen.switchToCustomTab();
				visualInteriorScreen.selectService(damageData.getDamageGroupName());
				visualInteriorScreen.selectSubService(damageData.getMoneyServiceData().getServiceName());
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
		vehicleScreen = new VehicleScreen();
		screenTapCount = 1;
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			VisualInteriorScreen visualInteriorScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, visualScreenData.getScreenName());
			visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
			for (DamageData damageData : visualScreenData.getDamagesData()) {
				visualInteriorScreen.tapInteriorWithCoords(screenTapCount);
				screenTapCount = screenTapCount + 2;
				visualInteriorScreen.setCarServiceQuantityValue(damageData.getMoneyServiceData().getServiceQuantity());
				visualInteriorScreen.saveCarServiceDetails();
			}
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), visualScreenData.getScreenTotalPrice2());
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), visualScreenData.getScreenPrice2());
		}
		vehicleScreen.saveWizard();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyPanelThenItWillBeAddedOnceForManyAssociatedServiceInstancesWithSameVehiclePart(String rowID,
															   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
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
	
	private String workOrderNumber28583 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_1(String rowID,
																															 String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_FOR_FEE_ITEM_IN_2_PACKS);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		workOrderNumber28583 = vehicleScreen.getInspectionNumber();
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getServiceData().getServicePrice());
		OrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();				
	}
	
	@Test(testName="Test Case 28583:WO: HD - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity", 
			description = "WO: HD - If one fee bundle item is related to 2 or more fee bundle packages and assigned service is selected in WO then amount of the fee will be multiple to package quantity")
	public void testHDIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity_2()
			 {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workorderspage = operationsWebPage.clickWorkOrdersLink();
		workorderspage.makeSearchPanelVisible();
		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workorderspage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		workorderspage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		workorderspage.setSearchOrderNumber(workOrderNumber28583);
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		
		WorkOrderInfoTabWebPage woinfotab = workorderspage.clickWorkOrderInTable(workOrderNumber28583);
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
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_FEE_PRICE_OVERRIDE);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());
		
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances(String rowID,
																										String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesScreen =vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);

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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfFeeBundleItemPricePolicyServiceOrFlatFeeThenItWillBeAddedToWOEveryTimeWhenAssociatedServiceInstanceWillAddToWO(String rowID,
																													String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatForWholesaleAndRetailCustomersFeeIsAddedDependsOnThePriceAccordinglyToPriceOfTheFeeBundleItem(String rowID,
																																	   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
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
	
	private String workOrderNumber29398 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_1(String rowID,
																						 String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		workOrderNumber29398 = vehicleScreen.getInspectionNumber();
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
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
	}
	
	@Test(testName="Test Case 29398:WO: HD - Verify that Fee Bundle services is calculated for additional matrix services", 
			description = "Verify that Fee Bundle services is calculated for additional matrix services")
	public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices_2()
			 {
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = operationsWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		workOrdersWebPage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber29398);
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage workOrderInfoTabWebPage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber29398);
		Assert.assertTrue(workOrderInfoTabWebPage.isServicePriceCorrectForWorkOrder("$2,127.50"));
		Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("SR_S6_FeeBundle"));

		workOrderInfoTabWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
		
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_095(String rowID,
																						 String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVinNumber());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())  {
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_003(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVinNumber());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())  {
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_005(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVinNumber());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())  {
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
	
	private String workOrderNumber31498 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_1(String rowID,
																					String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		workOrderNumber31498 = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
		ServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		InspectionToolBar inspectionToolBar = new InspectionToolBar();		
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		OrderSummaryScreen orderSummaryScreen = questionsScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName="Test Case 31498:WO: HD - Verify that amount is calculated and rounded correctly", 
			description = "Verify that amount is calculated and rounded correctly")
	public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly_2()
			 {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
		WorkOrdersWebPage workOrdersWebPage = operationsWebPage.clickWorkOrdersLink();
		workOrdersWebPage.makeSearchPanelVisible();
		workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workOrdersWebPage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		workOrdersWebPage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		workOrdersWebPage.setSearchOrderNumber(workOrderNumber31498);
		workOrdersWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		WorkOrderInfoTabWebPage workOrderInfoTabWebPage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber31498);
		workOrdersWebPage.waitABit(5000);
		Assert.assertTrue(workOrderInfoTabWebPage.isServicePriceCorrectForWorkOrder("$3,153.94"));

		workOrderInfoTabWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	private String inspectionNumber32226 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_1(String rowID,
															   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		SettingsScreen settingsScreen = homescreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		QuestionsScreen questionsScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_SIMPLE);
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		VehicleScreen vehicleScreen = questionsScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		inspectionNumber32226 = vehicleScreen.getInspectionNumber();
		PriceMatrixScreen priceMatrixScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		for(VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
			priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
			priceMatrixScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
		}
		priceMatrixScreen.saveWizard();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber32226);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber32226);
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
	}
	
	@Test(testName = "Test Case 32226:Inspections: HD - Verify that inspection is saved as declined when all services are skipped or declined", 
			description = "Verify that inspection is saved as declined when all services are skipped or declined")
	public void testHDVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = operationsWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
        inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        inspectionsWebPage.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());
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
		
		homescreen = new HomeScreen();
		SettingsScreen settingsScreen = homescreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehicleScreen.setVIN(inspectionData.getVinNumber());

		inspectionNumber32286 = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		
		myInspectionsScreen.selectInspectionForAction(inspectionNumber32286);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber32286);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);
		}

		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		myInspectionsScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32286:Inspections: HD - Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount")
	public void testHDVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount_2()  {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = operationsWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
        inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        inspectionsWebPage.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());
		inspectionsWebPage.selectSearchStatus("All active");
		inspectionsWebPage.searchInspectionByNumber(inspectionNumber32286);		
		Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber32286), "$2,000.00");
		Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber32286), "Approved");
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	private String inspectionNumber32287 = null;

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_1(String rowID,
																		  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		inspectionNumber32287 = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.swipeScreenUp();
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
        servicesScreen.cancelSearchAvailableService();
		servicesScreen.saveWizard();
		
		myInspectionsScreen.selectInspectionForApprove(inspectionNumber32287);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen =  new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber32287);
		approveInspectionsScreen.clickSkipAllServicesButton();		
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.selectStatusReason(inspectionData.getDeclineReason());
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		
		myInspectionsScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 32287:Inspections: HD - Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of skipped/declined services are not calc go approved amount BO > inspectiontypes list > column ApprovedAmount")
	public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc_2() {

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = operationsWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
        inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        inspectionsWebPage.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());
		inspectionsWebPage.selectSearchStatus(InspectionStatuses.DECLINED.getInspectionStatusValue());
		inspectionsWebPage.searchInspectionByNumber(inspectionNumber32287);		
		Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber32287), "$0.00");
		Assert.assertEquals(inspectionsWebPage.getInspectionApprovedTotal(inspectionNumber32287), "$0.00");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionVerifyServicesAreMarkedAsStrikethroughWhenExcludeFromTotal(String rowID,
																		  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehicleScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehicleParts(serviceData.getVehicleParts());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatValueSelectedOnPriceMatrixStepIsSavedAndShownDuringEditMode(String rowID,
																						 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		SettingsScreen settingsScreen = homescreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();
		
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myInspectionsScreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER,
				InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		vehicleScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			PriceMatrixScreen priceMatrixScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			priceMatrixScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(),
					priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService() != null)
				priceMatrixScreen.selectDiscaunt(priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice() != null)
				priceMatrixScreen.setPrice(priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice());
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(),priceMatrixScreenData.getMatrixScreenPrice());
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), priceMatrixScreenData.getMatrixScreenTotalPrice());
		}

		ServicesScreen servicesScreen =vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		servicesScreen.selectService(inspectionData.getServiceData().getServiceName());
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());



		QuestionsScreen questionsScreen = servicesScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		
		vehicleScreen.saveWizard();
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			vehicleScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), priceMatrixScreenData.getMatrixScreenPrice());

		}
		vehicleScreen.saveWizard();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																	String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		vehicleScreen.setVIN(workOrderData.getVinNumber());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity());
		
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart(workOrderData.getServiceData().getVehiclePart().getVehiclePartName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		OrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();
		
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_WO_IS_HUGE, workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.swipeScreenLeft();
		servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServiceDetailsScreen = servicesScreen.openServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity2());
		
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																	String description, JSONObject testData) {

		Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		final int workOrdersToCreate = 2;
		List<String> workOrders = new ArrayList<>();
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		for (int i = 0; i < workOrdersToCreate; i++) {
			VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
			vehicleScreen.setVIN(invoiceData.getWorkOrderData().getVinNumber());
			workOrders.add(vehicleScreen.getInspectionNumber());

			ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(invoiceData.getWorkOrderData().getServiceData().getServiceName());
			selectedServiceDetailsScreen.setServicePriceValue(invoiceData.getWorkOrderData().getServiceData().getServicePrice());
			selectedServiceDetailsScreen.setServiceQuantityValue(invoiceData.getWorkOrderData().getServiceData().getServiceQuantity());

			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.selectVehiclePart(invoiceData.getWorkOrderData().getServiceData().getVehiclePart().getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			OrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			orderSummaryScreen.setTotalSale(invoiceData.getWorkOrderData().getWorkOrderTotalSale());
			orderSummaryScreen.saveWizard();
		}

		myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrders.get(0), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrders.get(0));
		teamWorkOrdersScreen.clickInvoiceIcon();
        QuestionsScreen questionsScreen = teamWorkOrdersScreen.selectInvoiceType(InvoicesTypes.INVOICE_CUSTOM1);
		questionsScreen.waitQuestionsScreenLoaded();
		InvoiceInfoScreen invoiceInfoScreen = questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceInfoScreen.setPO(invoiceData.getInvoiceData().getInvoicePONumber());
		String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();

		myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrders.get(1), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myWorkOrdersScreen.clickHomeButton();

		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoiceNumber);

		myinvoicesscreen.clickEditPopup();
		questionsScreen = new QuestionsScreen();
		questionsScreen.waitQuestionsScreenLoaded();
		questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceInfoScreen.addWorkOrder(workOrders.get(1));
		invoiceInfoScreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INVOICE_IS_HUGE, invoiceData.getInvoiceData().getInvoiceTotal()));
		invoiceInfoScreen.swipeScreenLeft();
		invoiceInfoScreen.cancelInvoice();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
																						  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_DRAFT_MODE);
		vehicleScreen.setVIN(inspectionData.getVinNumber());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyApprovedAmountForInspectionCreatedFromSR(String rowID,
																			String description, JSONObject testData) {

		ServiceRequestData serviceRequestData = JSonDataParser.getTestDataFromJson(testData, ServiceRequestData.class);
		
		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen serviceRequestsScreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehicleScreen = serviceRequestsScreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		QuestionsScreen questionsScreen = servicesScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, serviceRequestData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		questionsScreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("No"))
				.click();
		serviceRequestsScreen = new ServiceRequestsScreen();
		String srnumber = serviceRequestsScreen.getFirstServiceRequestNumber();
		VisualInteriorScreen visualInteriorScreen = serviceRequestsScreen.createInspectionFromServiceReques(srnumber, InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		vehicleScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		ApproveInspectionsScreen approveInspectionsScreen =  new ApproveInspectionsScreen();
		approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
		approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getMoneyServiceData());
		approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getPercentageServiceData());
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.drawSignatureAfterSelection();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		teamInspectionsScreen = new TeamInspectionsScreen();
		Assert.assertTrue(teamInspectionsScreen.isInspectionApproved(inspectionNumber));
		Assert.assertEquals(teamInspectionsScreen.getFirstInspectionPriceValue(), serviceRequestData.getInspectionData().getInspectionPrice());
		Assert.assertEquals(teamInspectionsScreen.getFirstInspectionTotalPriceValue(), serviceRequestData.getInspectionData().getInspectionTotalPrice());
		teamInspectionsScreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();
		serviceRequestsScreen.clickHomeButton();		
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly(String rowID,
																	String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		homescreen = new HomeScreen();
		SettingsScreen settingsScreen = homescreen.clickSettingsButton();
		settingsScreen.setInspectionToNonSinglePageInspection();
		settingsScreen.clickHomeButton();
		
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehicleScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);

		vehicleScreen.setVIN(inspectionData.getVinNumber());

		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			PriceMatrixScreen priceMatrixScreen = questionsScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
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
		ApproveInspectionsScreen approveInspectionsScreen =  new ApproveInspectionsScreen();
		approveInspectionsScreen.approveInspectionApproveAllAndSignature(inspectionNumber);

		myInspectionsScreen.createWOFromInspection(inspectionNumber,
				WorkOrdersTypes.WO_SMOKE_MONITOR);
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(serviceData.getServiceName(),
					serviceData.getServicePrice()));
		}
		servicesScreen.cancelWizard();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_WITH_PART_SERVICES);
		vehicleScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesScreen.clickSaveAsDraft();
		
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		vehicleScreen = new VehicleScreen();
		servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);

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

		VisualInteriorScreen visualInteriorScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, ScreenNamesConstants.FUTURE_AUDI_CAR);
		visualInteriorScreen.switchToCustomTab();
		visualInteriorScreen.selectService(inspectionData.getDamageData().getDamageGroupName());
		visualInteriorScreen.selectSubService(inspectionData.getDamageData().getMoneyServiceData().getServiceName());
		visualInteriorScreen.tapCarImage();
		visualInteriorScreen.tapCarImage();
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.clickServicePartCell();
		ServicePartSteps.selectServicePartData(inspectionData.getDamageData().getMoneyServiceData().getServicePartData());
		selectedServiceDetailsScreen.setServicePriceValue(inspectionData.getDamageData().getMoneyServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		final PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreenData();
		PriceMatrixScreen priceMatrixScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
		final VehiclePartData vehiclePartData = priceMatrixScreenData.getVehiclePartData();
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
		vehicleScreen = myInspectionsScreen.createWOFromInspection(inspectionNumber,
				WorkOrdersTypes.WO_WITH_PART_SERVICE);
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
		OrderSummaryScreen orderSummaryScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

		orderSummaryScreen.saveWizard();
		homescreen = myInspectionsScreen.clickHomeButton();
		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		myWorkOrdersScreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO(String rowID,
																											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_WITH_PART_SERVICE);
		vehicleScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		OrderSummaryScreen orderSummaryScreen = questionsScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
		vehicleScreen = new VehicleScreen();
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		OrderSummaryScreen orderSummaryScreen = questionsScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = myWorkOrdersScreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceInfoScreen.setPO(invoiceData.getInvoicePONumber());
		String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSave();
		Helpers.acceptAlert();
		questionsScreen.selectAnswerForQuestion(invoiceData.getQuestionScreenData().getQuestionData());
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickFilterButton();
		myWorkOrdersScreen.setFilterBilling(filterBillingValue);
		myWorkOrdersScreen.clickSaveFilter();
		
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		homescreen = myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
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

		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();

		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		servicesScreen = new ServicesScreen();
		OrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		
		orderSummaryScreen.clickSave();
		myWorkOrdersScreen.selectInvoiceType(InvoicesTypes.INVOICE_AUTOWORKLISTNET);
		questionsScreen.selectAnswerForQuestion(invoiceData.getQuestionScreenData().getQuestionData());
		InvoiceInfoScreen invoiceInfoScreen = questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceInfoScreen.setPO(invoiceData.getInvoicePONumber());
		invoiceNumber45224 = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		
		myWorkOrdersScreen.clickFilterButton();
		myWorkOrdersScreen.setFilterBilling(filterBillingValue);
		myWorkOrdersScreen.clickSaveFilter();
		
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		homescreen = myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 45224:WO: HD - Verify calculation with price matrix Labor type", 
			description = "WO: HD - Verify calculation with price matrix Labor type")
	public void testWOVerifyCalculationWithPriceMatrixLaborType_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = operationsWebPage.clickInvoicesLink();
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
        invoicesWebPage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber45224);
		invoicesWebPage.clickFindButton();
		String mainWindowHandle = webdriver.getWindowHandle();
		invoicesWebPage.clickInvoicePrintPreview(invoiceNumber45224);
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

		homescreen = new HomeScreen();			
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen =  questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

		OrderSummaryScreen orderSummaryScreen = questionsScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickWorkOrderForApproveButton(workOrderNumber);
		SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
		selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveInspectionsScreen =  new ApproveInspectionsScreen();
		approveInspectionsScreen.clickApproveButton();
		
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber);
		myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceInfoScreen invoiceInfoScreen = myWorkOrdersScreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceNumber42803 = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.setPO(invoiceData.getInvoicePONumber());
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 42803:Invoices: HD - Verify rounding money amount values", 
			description = "Invoices: HD - Verify rounding money amount values")
	public void testInvoicesVerifyRoundingMoneyAmountValues_2() {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = operationsWebPage.clickInvoicesLink();
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
        invoicesWebPage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
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
	public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		
		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehicleScreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		final String inspNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
			if (serviceData.getServicePrice()!=null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo (serviceData.getServiceName());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection(String rowID,
																															   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_DRAFT_MODE);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);

		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if(serviceData.getServicePrice() != null)
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
			if (serviceData.getServicePrice()!=null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo (serviceData.getServiceName());
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
		ApproveInspectionsScreen approveInspectionsScreen =  new ApproveInspectionsScreen();	
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels(String rowID,
																													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_WITH_0_PRICE);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		final String inspNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehicleScreen = new VehicleScreen();
		servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatForBundleItemsPricePolicyIsApplied(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homescreen = new HomeScreen();			
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
			
		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVinNumber());

		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

		OrderSummaryScreen orderSummaryScreen = servicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData(String rowID,
																   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String customer  = "Avalon";
		
		homescreen = new HomeScreen();			
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
			
		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addOrderWithSelectCustomer(customer, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

		OrderSummaryScreen orderSummaryScreen = questionsScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
		vehicleScreen = new VehicleScreen();
		servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		
		homescreen = new HomeScreen();			
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
			
		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		OrderSummaryScreen orderSummaryScreen = questionsScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.saveWizard();
		
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
		vehicleScreen = new VehicleScreen();
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		homescreen = new HomeScreen();
		
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_FOR_CALC);
		vehicleScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesScreen.saveWizard();
		
		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		vehicleScreen = new VehicleScreen();
		servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
			CustomersScreen customersScreen = homescreen.clickCustomersButton();
			customersScreen.swtchToWholesaleMode();
			customersScreen.selectCustomerWithoutEditing(workOrderData.getWholesailCustomer().getCompany());

			MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
			VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkOrder(WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
			vehicleScreen.setVIN(workOrderData.getVinNumber());
			String workOrderNumber = vehicleScreen.getInspectionNumber();
			workOrders.add(workOrderNumber);
			if (workOrderData.getQuestionScreenData() != null) {
				QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
				questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
			}
			OrderSummaryScreen orderSummaryScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			orderSummaryScreen.saveWizard();
			myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
			vehicleScreen = new VehicleScreen();
			ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesScreen.selectService(workOrderData.getServiceData().getServiceName());
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
			servicesScreen.saveWizard();

			myWorkOrdersScreen.clickHomeButton();
		}
		MyWorkOrdersScreen myWorkOrdersScreen =homescreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.selectWorkOrder(workOrders.get(workOrderIndexToEdit));
		myWorkOrdersScreen.clickChangeCustomerPopupMenu();
		CustomersScreen customersScreen = new CustomersScreen();
		customersScreen.swtchToRetailMode();
		customersScreen.clickOnCustomer(retailCustomer);
		myWorkOrdersScreen = new MyWorkOrdersScreen();
		Assert.assertFalse(myWorkOrdersScreen.woExists(workOrders.get(workOrderIndexToEdit)));
		myWorkOrdersScreen.clickHomeButton();
		
		customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		
		myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.selectWorkOrderForEidt(workOrders.get(workOrderIndexToEdit));
		VehicleScreen vehicleScreen = new VehicleScreen();
		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getWorkOrderPrice());
		servicesScreen.selectService(testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getServiceData().getServiceName());
		
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderNewPrice);
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();			
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
			
		MyWorkOrdersScreen myWorkOrdersScreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehicleScreen = myWorkOrdersScreen.addWorkWithJobOrder(WorkOrdersTypes.WO_TYPE_WITH_JOB, workOrderData.getWorkOrderJob());
		vehicleScreen.setVIN(workOrderData.getVinNumber());
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, workOrderData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = questionsScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			servicesScreen.selectService(serviceData.getServiceName());
			InspectionToolBar inspectionToolBar = new InspectionToolBar();
			Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice());
		}
		servicesScreen.saveWizard();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatForDeclinedSkippedaServicesAppropriateIconIsShownAfterApproval(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		
		CustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualinteriorScreen = myInspectionsScreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehicleScreen = visualinteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehicleScreen.setVIN(inspectionData.getVinNumber());
		String inspNumber = vehicleScreen.getInspectionNumber();

		QuestionsScreen questionsScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, inspectionData.getQuestionScreenData().getScreenName());
		questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

		ServicesScreen servicesScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
			if (serviceData.getServiceQuantity()!=null) {
				SelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
		}
		selectedServiceBundleScreen.saveSelectedServiceDetails();

		VisualInteriorScreen visualInteriorScreen = servicesScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, "Future Sport Car");
		visualInteriorScreen.selectService(inspectionData.getServiceData().getServiceName());
		visualInteriorScreen.tapInteriorWithCoords(1);
		visualInteriorScreen.tapInteriorWithCoords(2);

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			PriceMatrixScreen priceMatrixScreen = vehicleScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartSize()!= null)
				priceMatrixScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(), priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
			if (priceMatrixScreenData.getVehiclePartData().getVehiclePartOption()!= null) {
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
		BaseUtils.waitABit(10*1000);
		myInspectionsScreen.selectInspectionForEdit(inspNumber);
		visualinteriorScreen =new VisualInteriorScreen();
		servicesScreen = visualinteriorScreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.TEST_PACK_FOR_CALC);
		Assert.assertTrue(servicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(servicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(servicesScreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		Assert.assertTrue(servicesScreen.isServiceApproved(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(servicesScreen.isServiceDeclinedSkipped(iOSInternalProjectConstants.TAX_DISCOUNT));
		servicesScreen.cancelWizard();
		myInspectionsScreen.clickHomeButton();
        //DriverBuilder.getInstance().getAppiumDriver().launchApp();
		//MainScreen mainScreen = new MainScreen();
		//homescreen = mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
	
	@AfterMethod
	public void closeBrowser() {
		if (webdriver != null)
			webdriver.quit();
	}
}

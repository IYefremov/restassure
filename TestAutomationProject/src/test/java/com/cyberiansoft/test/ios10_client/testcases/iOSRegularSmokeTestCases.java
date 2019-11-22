package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.verifications.ServiceRequestsListVerifications;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.enums.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.enums.ServiceRequestStatus;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LicensesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SinglePageInspectionScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.ordermonitorphases.OrderMonitorPhases;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class iOSRegularSmokeTestCases extends ReconProBaseTestCase {
	
	private WholesailCustomer Specific_Client = new WholesailCustomer();
	private WholesailCustomer ZAZ_Motors = new WholesailCustomer();
	private WholesailCustomer _002_Test_Customer = new WholesailCustomer();
	private WholesailCustomer _003_Test_Customer = new WholesailCustomer();
	private WholesailCustomer _004_Test_Customer = new WholesailCustomer();
	private WholesailCustomer Test_Company_Customer = new WholesailCustomer();
	private RetailCustomer testRetailCustomer = new RetailCustomer("Automation", "Retail Customer");

	@BeforeClass
	public void setUpSuite() {

		Specific_Client.setCompanyName("Specific_Client");
		ZAZ_Motors.setCompanyName("Zaz Motors");
		_002_Test_Customer.setCompanyName("002 - Test Company");
		_003_Test_Customer.setCompanyName("003 - Test Company");
		_004_Test_Customer.setCompanyName("004 - Test Company");
		Test_Company_Customer.setCompanyName("Test Company");
		JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getGeneralSuiteTestCasesDataPath();
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Test_Automation_Regular3",
				envType);

		RegularMainScreen mainScreen = new RegularMainScreen();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularHomeScreenSteps.navigateToSettingsScreen();
		RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
		settingsScreen.setShowAvailableSelectedServicesOn();
		settingsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testUpdateDatabase(String rowID,
								   String description, JSONObject testData) {
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		RegularMainScreen mainScreen = new RegularMainScreen();
		mainScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularHomeScreen homeScreen = new RegularHomeScreen();
		homeScreen.clickStatusButton();
		homeScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testUpdateVIN(String rowID,
							  String description, JSONObject testData) {
		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.updateVIN();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homeScreen.clickStatusButton();
		homeScreen.updateVIN();
		homeScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateRetailCustomer(String rowID,
							  String description, JSONObject testData) {

		RetailCustomer newCustomer = new RetailCustomer("supermy12", "super");
		newCustomer.setCompanyName("supercompany");
		newCustomer.setCustomerAddress1( "First streer");
		newCustomer.setCustomerCity("New York");
		newCustomer.setCustomerZip("79031");
		newCustomer.setCustomerPhone("723-1234567");
		newCustomer.setMailAddress("test@cyberiansoft.com");
		newCustomer.setCustomerState("Alberta");
		newCustomer.setCustomerCountry("Canada");

		RetailCustomer editedCustomer = new RetailCustomer("supernewmy12", "superedited");
		editedCustomer.setCompanyName("supercompanyedited");
		editedCustomer.setCustomerAddress1( "Second streer");
		editedCustomer.setCustomerCity("New York1");
		editedCustomer.setCustomerZip("79035");
		editedCustomer.setCustomerPhone("723-1234576");
		editedCustomer.setMailAddress("test@getnada.com");
		editedCustomer.setCustomerState("California");
		editedCustomer.setCustomerCountry("United States");

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BaseUtils.waitABit(1000);
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeaderPanel.clickCompanyLink();
		ClientsWebPage clientsWebPage = new ClientsWebPage(webdriver);
		companyWebPage.clickClientsLink();
		clientsWebPage.searchClientByName(newCustomer.getFullName());
		if (clientsWebPage.isClientPresentInTable(newCustomer.getFullName()))
			clientsWebPage.archiveFirstClient();
		
		clientsWebPage.searchClientByName(editedCustomer.getFullName());
		if (clientsWebPage.isClientPresentInTable(editedCustomer.getFullName()))
			clientsWebPage.archiveFirstClient();

		DriverBuilder.getInstance().getDriver().quit();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		homeScreen.clickLogoutButton();
		RegularMainScreen mainScreen = new RegularMainScreen();
		mainScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickAddCustomersButton();
		RegularAddCustomerScreen addcustomerscreen = new RegularAddCustomerScreen();
		addcustomerscreen.addCustomer(newCustomer);
		addcustomerscreen.clickSaveBtn();
		Assert.assertTrue(customersScreen.checkCustomerExists(newCustomer.getFirstName()));

		customersScreen.selectCustomerToEdit(newCustomer.getFirstName());
		addcustomerscreen.editCustomer(editedCustomer);
		addcustomerscreen.clickSaveBtn();
		Assert.assertTrue(customersScreen.checkCustomerExists(editedCustomer.getFirstName()));
		customersScreen.clickHomeButton();
		homeScreen.clickLogoutButton();
		mainScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BaseUtils.waitABit(1000);
		backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeaderPanel.clickCompanyLink();
		clientsWebPage = new ClientsWebPage(webdriver);
		companyWebPage.clickClientsLink();

		clientsWebPage.deleteUserViaSearch(editedCustomer.getFullName());

		DriverBuilder.getInstance().getDriver().quit();

		homeScreen.clickLogoutButton();
		mainScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		Assert.assertFalse(customersScreen.checkCustomerExists(editedCustomer.getFirstName()));
		customersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditRetailInspectionNotes(String rowID,
											  String description, JSONObject testData) {
		final String _notes1 = "Test\nTest 2";
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.DEFAULT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularInspectionsSteps.saveInspectionAsDraft();

		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		RegularNavigationSteps.navigateToVisualScreen(WizardScreenTypes.VISUAL_INTERIOR);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.clickNotesButton();
		RegularNotesScreen notesScreen = new RegularNotesScreen();
		notesScreen.setNotes(_notes1);
		notesScreen.addQuickNotes();
		notesScreen.clickSaveButton();
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
		visualInteriorScreen.clickNotesButton();
		Assert.assertEquals(notesScreen.getNotesAndQuickNotes(), _notes1 + "\n" + notesScreen.quicknotesvalue);
		notesScreen.clickSaveButton();
		RegularInspectionsSteps.saveInspectionAsFinal();
		RegularNavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testApproveInspectionOnDevice(String rowID,
											  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		final String inspNumber = vehicleScreen.getInspectionNumber();
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.selectInspectionForApprove(inspNumber);
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspNumber);
		approveInspectionsScreen.clickApproveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspNumber));
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testArchiveAndUnArchiveTheInspection(String rowID,
													 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		final String inspNumber = vehicleScreen.getInspectionNumber();
		RegularInspectionsSteps.saveInspection();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.archiveInspection(inspNumber,
				inspectionData.getArchiveReason());
		Assert.assertTrue(myInspectionsScreen.checkInspectionDoesntExists(inspNumber));
		RegularNavigationSteps.navigateBackScreen();

	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateWorkOrderWithTypeIsAssignedToASpecificClient(String rowID,
																												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		final String license = "Iphone_Test_Spec_Client";
		final String percService = "Test Tax";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		LicensesScreen licensesscreen = mainScreen.clickLicenses();
		licensesscreen.clickAddLicenseButtonAndAcceptAlert();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BaseUtils.waitABit(1000);
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
		backOfficeHeaderPanel.clickCompanyLink();
		ActiveDevicesWebPage devicespage = new ActiveDevicesWebPage(webdriver);
		companyWebPage.clickManageDevicesLink();

		devicespage.setSearchCriteriaByName(license);
		String regCode = devicespage.getFirstRegCodeInTable();

		DriverBuilder.getInstance().getDriver().quit();
		RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen();
		LoginScreen loginscreen = selectenvscreen.selectEnvironment(envType.getEnvironmentTypeName());
		loginscreen.registeriOSDevice(regCode);
		mainScreen.userLogin(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToSettingsScreen();
		RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
		settingsScreen.setShowAvailableSelectedServicesOn();
		settingsScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(Specific_Client, WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
		RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(workOrderData.getMoneyServiceData().getServiceName());
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(percService));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		orderSummaryScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_VIN_REQUIRED);
		RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.clickSave();
		orderSummaryScreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.clickSaveEmptyPO();
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceInfoScreen.clickSaveAsDraft();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateWorkOrderWithTeamSharingOption(String rowID,
														 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrdersData().get(0);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());

		Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), workOrderData.getMoneyServiceData().getServicePrice());
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), workOrderData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		// =====================================

		servicesScreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), workOrderData.getPercentageServiceData().getServicePrice());
		selectedServiceDetailsScreen.clickAdjustments();
		Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentValue(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
				workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
		selectedServiceDetailsScreen.selectAdjustment(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		// =====================================
		servicesScreen.openCustomServiceDetails(workOrderData.getBundleService().getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : workOrderData.getBundleService().getServices()) {
			if (serviceData.isSelected()) {
				Assert.assertTrue(selectedServiceBundleScreen.checkBundleIsSelected(serviceData.getServiceName()));
				selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
			} else {
				Assert.assertTrue(selectedServiceBundleScreen.checkBundleIsNotSelected(serviceData.getServiceName()));
				selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
			}
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();


		// =====================================
		for (ServiceData serviceData : workOrderData.getPercentageServices()) {
			selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}

		// =====================================
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		RegularPriceMatrixScreen priceMatrixScreen = servicesScreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
		RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartScreen.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		Assert.assertEquals(vehiclePartScreen.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
			vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		vehiclePartScreen.saveVehiclePart();

		priceMatrixScreen.clickSave();
		servicesScreen.switchToSelectedServicesTab();
		RegularSelectedServicesScreenValidations.verifyServicesAreSelected(workOrderData.getSelectedServices());

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
		RegularWorkOrdersSteps.saveWorkOrder();

		WorkOrderData workOrderDataCopiedServices = testCaseData.getWorkOrdersData().get(1);
		RegularMyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, Specific_Client, WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
		vehicleScreen.setVIN(workOrderDataCopiedServices.getVehicleInfoData().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		servicesScreen.switchToSelectedServicesTab();
		RegularSelectedServicesScreenValidations.verifyServicesAreSelected(workOrderDataCopiedServices.getSelectedServices());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		orderSummaryScreen.waitWorkOrderSummaryScreenLoad();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderDataCopiedServices.getWorkOrderPrice());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testApproveInspectionsOnDeviceViaAction(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inspectionsID = new ArrayList<>();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
			RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
			vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
			vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
			vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
			inspectionsID.add(vehicleScreen.getInspectionNumber());
			RegularInspectionsSteps.saveInspection();
		}
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.clickActionButton();
		for (String inspectionNumber : inspectionsID) {
			myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		}

		myInspectionsScreen.clickApproveInspections();
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		for (String inspectionNumber : inspectionsID) {
			approveInspectionsScreen.selectInspection(inspectionNumber);
			approveInspectionsScreen.clickApproveButton();
		}
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		for (String inspectionNumber : inspectionsID) {
			Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspectionNumber));
		}
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testArchiveInspectionsOnDeviceViaAction(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inspectionsID = new ArrayList<>();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
			RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
			vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
			vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
			vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
			inspectionsID.add(vehicleScreen.getInspectionNumber());
			RegularInspectionsSteps.saveInspection();
		}
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.clickActionButton();
		for (String inspectionNumber : inspectionsID) {
			myInspectionsScreen.clickOnInspection(inspectionNumber);
		}
		myInspectionsScreen.clickArchiveInspections();
		myInspectionsScreen.selectReasonToArchive(testCaseData.getArchiveReason());
		for (String inspectionNumber : inspectionsID) {
			Assert.assertTrue(myInspectionsScreen.checkInspectionDoesntExists(inspectionNumber));
		}
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testApproveInspectionOnBackOfficeFullInspectionApproval(String rowID,
																		String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		String inpectionnumber = vehicleScreen.getInspectionNumber();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		RegularInspectionsSteps.saveInspection();
		RegularNavigationSteps.navigateBackScreen();

		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.updateDatabase();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsWebPage.clickInspectionsLink();

		inspectionsWebPage.approveInspectionByNumber(inpectionnumber);

		DriverBuilder.getInstance().getDriver().quit();

		mainScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inpectionnumber));

		RegularNavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testApproveInspectionOnBackOfficeLinebylineApproval(String rowID,
																	String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_LA_TS_INSPTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
		RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

		RegularInspectionsSteps.saveInspection();
		RegularNavigationSteps.navigateBackScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.updateDatabase();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
		operationsWebPage.clickInspectionsLink();
		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		inspectionsWebPage.approveInspectionLinebylineApprovalByNumber(
				inspectionNumber, inspectionData.getServicesList());

		DriverBuilder.getInstance().getDriver().quit();

		mainScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspectionNumber));

		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice(String rowID,
																						 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		final String teamName = "Default team";
		final String serviceName = "Test Company (Universal Client)";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer, ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		final VehicleInfoData vehicleInfoData = serviceRequestData.getVihicleInfo();
		vehicleScreen.setVIN(vehicleInfoData.getVINNumber());
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.THE_VIN_IS_INCORRECT);

		vehicleScreen.setMakeAndModel(vehicleInfoData.getVehicleMake(), vehicleInfoData.getVehicleModel());
		vehicleScreen.setColor(vehicleInfoData.getVehicleColor());
		vehicleScreen.setYear(vehicleInfoData.getVehicleYear());

		vehicleScreen.setMileage(vehicleInfoData.getMileage());
		vehicleScreen.setFuelTankLevel(vehicleInfoData.getFuelTankLevel());
		vehicleScreen.setType(vehicleInfoData.getVehicleType());
		vehicleScreen.setStock(vehicleInfoData.getVehicleStock());
		vehicleScreen.setRO(vehicleInfoData.getRoNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();

		for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
			if (serviceData.getServiceQuantity() != null) {
				RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			} else
				RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		}
		servicesScreen.waitServicesScreenLoaded();
		RegularNavigationSteps.navigateToClaimScreen();
		RegularClaimScreen claimScreen = new RegularClaimScreen();
		claimScreen.selectInsuranceCompany(serviceRequestData.getInsuranceCompany().getInsuranceCompanyName());
		servicesScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();

		questionsScreen.drawSignature();
		servicesScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);;
		questionsScreen.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		servicesScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		serviceRequestSscreen.waitForServiceRequestScreenLoad();
		final String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.ON_HOLD.getValue());
		Assert.assertTrue(serviceRequestSscreen.getServiceRequestClient(serviceRequestNumber).contains(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER));
		Assert.assertTrue(serviceRequestSscreen.getServiceRequestDetails(serviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
		serviceRequestSscreen.clickHomeButton();
		Helpers.waitABit(10 * 1000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.makeSearchPanelVisible();
        final ServiceRequestsListVerifications serviceRequestsListVerifications = new ServiceRequestsListVerifications();
        serviceRequestsListVerifications.verifySearchFieldsAreVisible();

		serviceRequestsListInteractions.selectSearchTeam(teamName);
		serviceRequestsListInteractions.selectSearchTechnician("Employee Simple 20%");
		serviceRequestsListInteractions.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		serviceRequestsListInteractions.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
		serviceRequestsListInteractions.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());

		serviceRequestsListInteractions.setSearchFreeText(serviceRequestNumber);
		serviceRequestsListInteractions.clickFindButton();
		serviceRequestsListVerifications.verifySearchResultsByServiceName(serviceName);
		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		Assert.assertEquals(serviceRequestsListInteractions.getVINValueForSelectedServiceRequest(), serviceRequestData.getVihicleInfo().getVINNumber());
		Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Bundle1_Disc_Ex $150.00 (1.00)"));
		Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Quest_Req_Serv $10.00 (1.00)"));
		Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Wheel $70.00 (3.00)"));
		Assert.assertEquals(serviceRequestsListInteractions.getCustomerValueForSelectedServiceRequest(), serviceName);
		Assert.assertEquals(serviceRequestsListInteractions.getEmployeeValueForSelectedServiceRequest(), "Employee Simple 20% (Default team)");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateInspectionFromServiceRequest(String rowID,
													   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.waitForServiceRequestScreenLoad();
		final String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();

		RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_FOR_SR_INSPTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.waitVehicleScreenLoaded();
		String inspectionNumberber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
			Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2()));
			if (serviceData.getQuestionData() != null) {
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				selectedServiceDetailsScreen.answerQuestion2(serviceData.getQuestionData());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(bundleServiceData.getBundleServiceName(), bundleServiceData.getBundleServiceAmount()));
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		RegularServicesScreenSteps.waitServicesScreenLoad();
		RegularServiceRequestSteps.saveServiceRequest();
		homeScreen = serviceRequestSscreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspectionNumberber));
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumberber), inspectionData.getInspectionPrice());
		myInspectionsScreen.clickActionButton();
		myInspectionsScreen.selectInspectionForAction(inspectionNumberber);

		myInspectionsScreen.clickApproveInspections();
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumberber);
		approveInspectionsScreen.clickApproveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateWOFromServiceRequest(String rowID,
											   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		homeScreen = customersScreen.clickHomeButton();

		//test case
		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.selectServiceRequest(serviceRequestSscreen.getFirstServiceRequestNumber());
		serviceRequestSscreen.selectCreateWorkOrderRequestAction();
		RegularWorkOrderTypesSteps.selectWorkOrderType(WorkOrdersTypes.WO_FOR_SR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice2()));
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getBundleService().getBundleServiceName(), PricesCalculations.getPriceRepresentation(workOrderData.getBundleService().getBundleServiceAmount())));

		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(workOrderData.getBundleService().getBundleServiceName());
		selectedServiceDetailsScreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen.waitSelectedServicesScreenLoaded();

		selectedServicesScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.THE_VIN_IS_INVALID_AND_SAVE_WORKORDER);
		serviceRequestSscreen.waitForServiceRequestScreenLoad();
		serviceRequestSscreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateInspectionFromInvoiceWithTwoWOs(String rowID, String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehicleParts(workOrderData.getMoneyServiceData().getVehicleParts());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.waitServicesScreenLoaded();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());

		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularMyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber1, ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehicleScreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
		Assert.assertEquals(vehicleScreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());
		Assert.assertEquals(vehicleScreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		orderSummaryScreen.clickSave();
		orderSummaryScreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceInfoScreen.addWorkOrder(workOrderNumber1);
		Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
		invoiceInfoScreen.clickSaveAsDraft();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateInvoiceFromWOInMyWOsList(String rowID,
												   String description, JSONObject testData) {
		final String employee = "Employee Simple 20%";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		selectedServiceDetailsScreen.clickAdjustments();
		selectedServiceDetailsScreen.selectAdjustment(workOrderData.getPercentageServiceData().
				getServiceAdjustment().getAdjustmentData().getAdjustmentName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getPercentageServiceData().getServicePrice());

		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		for (ServiceData serviceData : workOrderData.getPercentageServices())
			servicesScreen.selectService(serviceData.getServiceName());
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.openCustomServiceDetails(matrixServiceData.getMatrixServiceName());
		RegularPriceMatrixScreen priceMatrixScreen = selectedServiceDetailsScreen.selectMatrics(matrixServiceData.getHailMatrixName());
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(employee));
		Assert.assertEquals(vehiclePartScreen.getPrice(), vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices())
			vehiclePartScreen.selectDiscaunt(serviceData.getServiceName());
		vehiclePartScreen.saveVehiclePart();

		priceMatrixScreen.clickSave();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.clickSave();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
		myWorkOrdersScreen.clickInvoiceIcon();
		RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_PO_IS_REQUIRED);
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();

		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToMyInvoicesScreen();
		RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testDontAlowToSelectBilleAandNotBilledOrdersTogetherInMultiSelectionMode(String rowID,
																						 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> workOrderIDs = new ArrayList<>();
		final String billingFilterValue = "All";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
			RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
			vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
			workOrderIDs.add(vehicleScreen.getWorkOrderNumber());
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesScreen = new RegularServicesScreen();
			for (ServiceData serviceData : workOrderData.getServicesList())
				servicesScreen.selectService(serviceData.getServiceName());
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
		}

		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		for (String workOrderID : workOrderIDs)
			myWorkOrdersScreen.approveWorkOrder(workOrderID, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderIDs.get(0));
		myWorkOrdersScreen.clickInvoiceIcon();
		RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceInfoScreen.clickSaveAsDraft();

		for (String workOrderID : workOrderIDs) {
			myWorkOrdersScreen.clickFilterButton();
			myWorkOrdersScreen.setFilterBilling(billingFilterValue);
			myWorkOrdersScreen.clickSaveFilter();
			myWorkOrdersScreen.clickActionButton();
			myWorkOrdersScreen.selectWorkOrderForAction(workOrderID);
			myWorkOrdersScreen.clickDoneButton();
		}
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testDontAlowToChangeBilledOrders(String rowID,
												 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String[] menuItemsToVerify = {"Edit", "Notes", "Change\nstatus", "Delete", "Create\nInvoices"};
		final String billingFilterValue = "All";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesScreen.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
		myWorkOrdersScreen.clickInvoiceIcon();
		RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceInfoScreen.clickSaveAsDraft();
		myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.clickFilterButton();
		myWorkOrdersScreen.setFilterBilling(billingFilterValue);
		myWorkOrdersScreen.clickSaveFilter();

		myWorkOrdersScreen.selectWorkOrder(workOrderNumber1);
		for (String menuItem : menuItemsToVerify) {
			Assert.assertFalse(myWorkOrdersScreen.isMenuItemForSelectedWOExists(menuItem));
		}
		RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.DETAILS);
		vehicleScreen.waitVehicleScreenLoaded();
		vehicleScreen.clickCancelWizard();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testChangeCustomerForInvoice(String rowID,
											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		WholesailCustomer wholesailCustomer = new WholesailCustomer();
		wholesailCustomer.setCompanyName("Specific_Client");

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesScreen.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
		myWorkOrdersScreen.clickInvoiceIcon();
		RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToMyInvoicesScreen();
		RegularMyInvoicesScreenSteps.changeCustomerForInvoice(invoiceNumber, wholesailCustomer);
		Helpers.waitABit(50 * 1000);
		RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
		Assert.assertEquals(invoiceInfoScreen.getInvoiceCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		invoiceInfoScreen.clickWO(workOrderNumber1);
		RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(Specific_Client);
		RegularMyWorkOrdersSteps.cancelCreatingWorkOrder();
		invoiceInfoScreen.waitInvoiceInfoScreenLoaded();
		invoiceInfoScreen.cancelInvoice();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testBugWithCrashOnCopyVehicle(String rowID,
											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		VehicleInfoData vehicleInfoData = testCaseData.getWorkOrderData().getVehicleInfoData();
		final String vehicleinfo = vehicleInfoData.getVehicleColor() + ", " +
				vehicleInfoData.getVehicleMake() + ", " + vehicleInfoData.getVehicleModel();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularHomeScreenSteps.navigateTocarHistoryScreen();
		RegularCarHistoryScreen carhistoryscreen = new RegularCarHistoryScreen();

		RegularCarHistoryWOsAndInvoicesScreen regularCarHistoryWOsAndInvoicesScreen = carhistoryscreen.clickCarHistoryRowByVehicleInfo(vehicleinfo);
		regularCarHistoryWOsAndInvoicesScreen.clickCarHistoryMyWorkOrders();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectFirstOrder();
		RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.COPY_VEHICLE);
		RegularWorkOrderTypesSteps.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickBackButton();

		carhistoryscreen.clickBackButton();
		carhistoryscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCopyInspections(String rowID,
									String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.FOR_COPY_INSP_INSPTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspNumber = vehicleScreen.getInspectionNumber();
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
			RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
			visualinteriorScreen.clickServicesToolbarButton();
			visualinteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
			Helpers.tapRegularCarImage();
			visualinteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
			if (visualScreenData.getScreenTotalPrice() != null)
				Assert.assertEquals(visualinteriorScreen.getTotalPrice(), visualScreenData.getScreenTotalPrice());
		}

		for (QuestionScreenData questionScreenData : inspectionData.getQuestionScreensData())
			RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();

		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), inspectionData.getMoneyServiceData().getServicePrice());
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), inspectionData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		// =====================================
		servicesScreen.openCustomServiceDetails(inspectionData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), inspectionData.getPercentageServiceData().getServicePrice());
		selectedServiceDetailsScreen.clickAdjustments();
		Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentValue(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
				inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
		selectedServiceDetailsScreen.selectAdjustment(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		// =====================================
		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		// =====================================
		for (ServiceData serviceData : inspectionData.getPercentageServicesList())
			servicesScreen.selectService(serviceData.getServiceName());
		// =====================================
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		RegularPriceMatrixScreen priceMatrixScreen = servicesScreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
		RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartScreen.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		Assert.assertEquals(vehiclePartScreen.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
			vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		vehiclePartScreen.saveVehiclePart();

		priceMatrixScreen.clickSave();
		servicesScreen.switchToSelectedServicesTab();
		RegularSelectedServicesScreenValidations.verifyServicesAreSelected(inspectionData.getSelectedServices());
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.selectInspectionForCopy(inspNumber);
		vehicleScreen = new RegularVehicleScreen();
		Assert.assertEquals(vehicleScreen.getMake(), inspectionData.getVehicleInfo().getVehicleMake());
		Assert.assertEquals(vehicleScreen.getModel(), inspectionData.getVehicleInfo().getVehicleModel());

		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
			RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
			visualinteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
		}
		RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.FOLLOW_UP_REQUESTED);
		RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
		visualinteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FOLLOW_UP_REQUESTED);
		SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen();
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.swipeScreenUp();
		Assert.assertTrue(singlepageinspectionscreen.isSignaturePresent());
		RegularNavigationSteps.navigateToScreen("BATTERY PERFORMANCE");
		questionsScreen.swipeScreenUp();
		questionsScreen.swipeScreenUp();
		Assert.assertTrue(singlepageinspectionscreen.isAnswerPresent("Test Answer 1"));
		RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);

		servicesScreen.switchToSelectedServicesTab();
		RegularSelectedServicesScreenValidations.verifyServicesAreSelected(inspectionData.getSelectedServices());

		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateInspectionFromWO(String rowID,
										   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(Specific_Client, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(testCaseData.getWorkOrderData().getVehicleInfoData().getVINNumber());

		String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularWorkOrdersSteps.saveWorkOrder();

		//Test case
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber1), testCaseData.getWorkOrderData().getWorkOrderPrice());
		RegularMyWorkOrdersSteps.selectWorkOrderForNewInspection(workOrderNumber1);
		vehicleScreen.verifyMakeModelyearValues(testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleMake(),
				testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleModel(), testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleYear());
		vehicleScreen.cancelOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateInvoiceWithTwoWOsAndCopyVehicle(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehicleParts(workOrderData.getMoneyServiceData().getVehicleParts());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.waitServicesScreenLoaded();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularMyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber1, ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehicleScreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
		Assert.assertEquals(vehicleScreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.waitWorkOrderSummaryScreenLoad();
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		orderSummaryScreen.clickSave();

		orderSummaryScreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceInfoScreen.addWorkOrder(workOrderNumber1);
		final String invoicenum = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();
		RegularNavigationSteps.navigateBackScreen();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPage.clickInvoicesLink();
		invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoicenum);
		invoicesWebPage.clickFindButton();
		Assert.assertTrue(invoicesWebPage.isInvoiceDisplayed(invoicenum));
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testChangeCustomerOptionForInspection(String rowID,
													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		WholesailCustomer wholesailCustomer = new WholesailCustomer();
		wholesailCustomer.setCompanyName("003 - Test Company");

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSP_CHANGE_INSPTYPE);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		RegularNavigationSteps.navigateToClaimScreen();
		RegularClaimScreen claimScreen = new RegularClaimScreen();
		claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.changeCustomerForInspection(inspectionNumber, wholesailCustomer);
		BaseUtils.waitABit(1000 * 60);
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
		RegularVisualInteriorScreen visualscreen = new RegularVisualInteriorScreen();
		visualscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(_003_Test_Customer);
		RegularInspectionsSteps.saveInspection();

		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testChangeCustomerOptionForInspectionAsChangeWholesaleToRetailAndViceVersa(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		RetailCustomer retailCustomer = new RetailCustomer("Retail", "Customer");

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSP_CHANGE_INSPTYPE);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		RegularNavigationSteps.navigateToClaimScreen();
		RegularClaimScreen claimScreen = new RegularClaimScreen();
		claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.changeCustomerForInspection(inspectionNumber, retailCustomer);
		RegularNavigationSteps.navigateBackScreen();
		Helpers.waitABit(60 * 1000);
		customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
		RegularVisualInteriorScreen visualscreen = new RegularVisualInteriorScreen();
		visualscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(testRetailCustomer);
		RegularInspectionsSteps.saveInspection();

		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testChangeCustomerOptionForInspectionsBasedOnTypeWithPreselectedCompanies(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		WholesailCustomer wholesailCustomer = new WholesailCustomer();
		wholesailCustomer.setCompanyName("003 - Test Company");

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.TYPEWITHPRESELECTEDCOMPANIES_INSPTYPE);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		RegularNavigationSteps.navigateToClaimScreen();
		RegularClaimScreen claimScreen = new RegularClaimScreen();
		claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.changeCustomerForInspection(inspectionNumber, wholesailCustomer);
		BaseUtils.waitABit(60 * 1000);
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);

		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(_003_Test_Customer);
		RegularInspectionsSteps.saveInspection();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testChangeCustomerOptionForWorkOrder(String rowID,
													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		WholesailCustomer wholesailCustomer = new WholesailCustomer();
		wholesailCustomer.setCompanyName("003 - Test Company");

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersSteps.changeCustomerForWorkOrder(workOrderNumber, wholesailCustomer);
		BaseUtils.waitABit(30*1000);
		RegularMyWorkOrdersSteps.openWorkOrderDetails(workOrderNumber);
		RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(wholesailCustomer);
		servicesScreen.clickCancel();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testChangeCustomerOptionForWOBasedOnTypeWithPreselectedCompanies(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		WholesailCustomer wholesailCustomer = new WholesailCustomer();
		wholesailCustomer.setCompanyName("003 - Test Company");

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_WITH_PRESELECTED_CLIENTS);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.changeCustomerForWorkOrder(workOrderNumber, wholesailCustomer);
		BaseUtils.waitABit(45*1000);
		RegularMyWorkOrdersSteps.openWorkOrderDetails(workOrderNumber);
		RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(wholesailCustomer);
		servicesScreen.clickCancel();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testChangeCustomerOptionForWOAsChangeWholesaleToRetailAndViceVersa(String rowID,
																				   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		RetailCustomer retailCustomer = new RetailCustomer("Retail", "Customer");

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersSteps.changeCustomerForWorkOrder(workOrderNumber, retailCustomer);
		RegularNavigationSteps.navigateBackScreen();
		homeScreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.openWorkOrderDetails(workOrderNumber);
		RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(testRetailCustomer);
		servicesScreen.clickCancel();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testBlockForTheSameVINIsONVerifyDuplicateVINMessageWhenCreate2WOWithOneVIN(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();
		orderSummaryScreen.waitForCustomWarningMessage(String.format(AlertsCaptions.ALERT_YOU_CANT_CREATE_WORK_ORDER_BECAUSE_VIN_EXISTS,
				WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON.getWorkOrderTypeName(), workOrderData.getVehicleInfoData().getVINNumber()), "Cancel");
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testBlockForTheSameServicesIsONVerifyDuplicateServicesMessageWhenCreate2WOWithOneService(String rowID,
																										 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesScreen.selectService(serviceData.getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.waitWorkOrderSummaryScreenLoad();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();
		orderSummaryScreen.closeDublicaterServicesWarningByClickingCancel();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditOptionOfDuplicateServicesMessageForWO(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
		servicesScreen.selectService(workOrderData.getServiceData().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();
		orderSummaryScreen.closeDublicaterServicesWarningByClickingEdit();
		orderSummaryScreen.swipeScreenLeft();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.removeSelectedService(workOrderData.getServiceData().getServiceName());
		Assert.assertFalse(selectedServicesScreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testOverrideOptionOfDuplicateServicesMessageForWO(String rowID,
																  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesScreen.selectService(serviceData.getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();
		orderSummaryScreen.closeDublicaterServicesWarningByClickingOverride();
		orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCancelOptionOfDuplicateServicesMessageForWO(String rowID,
																String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesScreen.selectService(serviceData.getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();
		orderSummaryScreen.closeDublicaterServicesWarningByClickingCancel();
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, false);
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testSavingInspectionsWithThreeMatrix(String rowID,
													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.VITALY_TEST_INSPTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehicleScreen.setType(inspectionData.getVehicleInfo().getVehicleType());
		vehicleScreen.setPO(inspectionData.getVehicleInfo().getPoNumber());
		String inspectionNumberber = vehicleScreen.getInspectionNumber();

		RegularNavigationSteps.navigateToClaimScreen();
		RegularClaimScreen claimScreen = new RegularClaimScreen();
		claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimScreen.setClaim(inspectionData.getInsuranceCompanyData().getClaimNumber());
		claimScreen.setAccidentDate();

		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
			RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
			visualinteriorScreen.clickServicesToolbarButton();
			if (visualScreenData.getDamagesData() != null) {
				visualinteriorScreen.selectService(visualScreenData.getDamagesData().get(0).getDamageGroupName());
				RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
				RegularVisualInteriorScreen.tapInteriorWithCoords(150, 150);
				visualinteriorScreen.selectService(visualScreenData.getDamagesData().get(0).getDamageGroupName());
			} else {
				visualinteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
				RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
				visualinteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
			}
			Assert.assertEquals(visualinteriorScreen.getSubTotalPrice(), visualScreenData.getScreenPrice());
			Assert.assertEquals(visualinteriorScreen.getTotalPrice(), visualScreenData.getScreenTotalPrice());
		}
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularNavigationSteps.navigateToPriceMatrixScreen(priceMatrixScreenData.getMatrixScreenName());
			RegularPriceMatrixScreenSteps.selectPriceMatrixData(priceMatrixScreenData);
		}

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

		}
		RegularServicesScreenSteps.waitServicesScreenLoad();
		RegularNavigationSteps.navigateToServicesScreen();
		servicesScreen.selectService(inspectionData.getServiceData().getServiceName());

		RegularNavigationSteps.navigateToScreen(inspectionData.getVisualScreenData().getScreenName());
		RegularVisualInteriorScreen visualinteriorScreen = new RegularVisualInteriorScreen();
		visualinteriorScreen.clickServicesToolbarButton();
		visualinteriorScreen.selectService(inspectionData.getVisualScreenData().getDamageData().getDamageGroupName());
		Helpers.tapRegularCarImage();
		visualinteriorScreen.selectService(inspectionData.getVisualScreenData().getDamageData().getDamageGroupName());
		Assert.assertEquals(visualinteriorScreen.getSubTotalPrice(), inspectionData.getVisualScreenData().getScreenPrice());
		Assert.assertEquals(visualinteriorScreen.getTotalPrice(), inspectionData.getVisualScreenData().getScreenTotalPrice());
		RegularInspectionsSteps.saveInspection();

		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumberber);
		vehicleScreen.waitVehicleScreenLoaded();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
			for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
				RegularVehiclePartsScreenSteps.verifyIfVehiclePartPriceValue(vehiclePartData);
			}
		}
		RegularInspectionsSteps.cancelCreatingInspection();
		RegularMyInspectionsSteps.selectInspectionForCopy(inspectionNumberber);
		vehicleScreen.waitVehicleScreenLoaded();
		final String copiedinspectionNumberber = vehicleScreen.getInspectionNumber();
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.checkInspectionExists(copiedinspectionNumberber));
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testThatSelectedServicesOnSRAreCopiedToInspectionBasedOnSR(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(_003_Test_Customer, ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			if (serviceData.getServiceQuantity() != null) {
				RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
		servicesScreen.waitServicesScreenLoaded();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());

		RegularNavigationSteps.navigateToServicesScreen();
		servicesScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		serviceRequestSscreen.waitForServiceRequestScreenLoad();
		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
		RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
		String inspectnumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2());

		RegularServiceRequestSteps.saveServiceRequest();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectDetailsRequestAction();
		RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectnumber));
		teamInspectionsScreen.clickBackButton();
		serviceRequestSscreen.clickBackButton();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testThatAutoSavedWOIsCreatedCorrectly(String rowID,
													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		Helpers.waitABit(30 * 1000);
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		RegularMainScreen mainScreen = new RegularMainScreen();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		Assert.assertTrue(myWorkOrdersScreen.isAutosavedWorkOrderExists());
		myWorkOrdersScreen.selectContinueWorkOrder();
		vehicleScreen = new RegularVehicleScreen();
		Assert.assertEquals(vehicleScreen.getWorkOrderNumber(), workOrderNumber);

		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		mainScreen = new RegularMainScreen();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		Assert.assertTrue(myWorkOrdersScreen.isAutosavedWorkOrderExists());
		myWorkOrdersScreen.selectDiscardWorkOrder();
		Assert.assertFalse(myWorkOrdersScreen.isAutosavedWorkOrderExists());
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testSRAddAppointmentToServiceRequest(String rowID,
													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		final String appointmentSubject = "SR-APP";
		final String appointmentAddress = "Maidan";
		final String appointmentCity = "Kiev";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer, ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();

		questionsScreen.drawSignature();
		servicesScreen.clickSave();
		Helpers.waitForAlert();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
		RegularQuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		servicesScreen.clickSave();
		AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		final String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectAppointmentRequestAction();
		serviceRequestSscreen.clickAddButton();
		serviceRequestSscreen.selectTodayFromAppointmet();
		serviceRequestSscreen.selectTodayToAppointmet();

		serviceRequestSscreen.setSubjectAppointmet(appointmentSubject);
		serviceRequestSscreen.setAddressAppointmet(appointmentAddress);
		serviceRequestSscreen.setCityAppointmet(appointmentCity);
		serviceRequestSscreen.saveAppointment();
		serviceRequestSscreen.clickBackButton();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectRejectAction();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testSRVerifySummaryActionForAppointmentOnSRsCalendar(String rowID,
																	 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String appointmentSubject = "SR-APP";
		final String appointmentAddress = "Maidan";
		final String appointmentCity = "Kiev";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer, ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.waitServicesScreenLoad();
		servicesScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();

		questionsScreen.drawSignature();
		servicesScreen.clickSave();
		Helpers.waitForAlert();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
		RegularQuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());

		servicesScreen.clickSave();
		AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		serviceRequestSscreen.waitForServiceRequestScreenLoad();
		final String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);

		serviceRequestSscreen.selectAppointmentRequestAction();
		serviceRequestSscreen.clickAddButton();
		serviceRequestSscreen.selectTodayFromAppointmet();
		serviceRequestSscreen.selectTodayToAppointmet();

		serviceRequestSscreen.setSubjectAppointmet(appointmentSubject);
		serviceRequestSscreen.setAddressAppointmet(appointmentAddress);
		serviceRequestSscreen.setCityAppointmet(appointmentCity);
		serviceRequestSscreen.saveAppointment();
		serviceRequestSscreen.clickBackButton();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectDetailsRequestAction();

		RegularServiceRequestDetalsScreenValidations.verifySRSummaryAppointmentsInformationExists(true);

		serviceRequestSscreen.clickBackButton();
		serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectRejectAction();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_PRICE_MATRIX);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
		servicesScreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
		for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
			RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
			RegularVehiclePartsScreenSteps.saveVehiclePart();
		}
		RegularVehiclePartsScreenSteps.savePriceMatrixData();

		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(matrixServiceData.getMatrixServiceName()));
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
																										  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(inspectionData.getVehicleInfo());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToPriceMatrixScreen(inspectionData.getPriceMatrixScreenData().getMatrixScreenName());
		RegularPriceMatrixScreenSteps.selectPriceMatrixData(inspectionData.getPriceMatrixScreenData());
		//RegularVehiclePartsScreenSteps.saveVehiclePart();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspectionNumber));
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOMonitorVerifyThatItIsNotPossibleToChangeServiceStatusBeforeStartService(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
		vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);

		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
		orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		Assert.assertTrue(orderMonitorScreen.isStartServiceButtonPresent());
		orderMonitorScreen.clickServiceStatusCell();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_MUST_SERVICE_PHASE_BEFORE_CHANGING_STATUS);
		orderMonitorScreen.clickStartService();
		orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		orderMonitorScreen.setCompletedServiceStatus();
		Assert.assertEquals(orderMonitorScreen.getPanelStatus(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName()), orderMonitorData.getMonitorServiceData().getMonitorServiceStatus());
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOMonitorVerifyThatItIsNotPossibleToChangePhaseStatusBeforeStartPhase(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String dyeService = "Dye";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();

		teamWorkOrdersScreen.clickOnWO(workOrderNumber);

		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();

		Assert.assertTrue(orderMonitorScreen.isRepairPhaseExists());
		Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonExists());
		orderMonitorScreen.clicksRepairPhaseLine();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_MUST_START_PHASE_BEFORE_CHANGING_STATUS);
		orderMonitorScreen.clickStartPhaseButton();

		orderMonitorScreen.selectPanel(dyeService);
		Assert.assertFalse(orderMonitorScreen.isStartPhaseButtonExists());
		Assert.assertTrue(orderMonitorScreen.isServiceStartDateExists());

		orderMonitorScreen.clickServiceStatusCell();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
		orderMonitorScreen.clickServiceDetailsDoneButton();

		orderMonitorScreen.clicksRepairPhaseLine();
		orderMonitorScreen.clickCompletedPhaseCell();

		for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorData().getMonitorServicesData())
			Assert.assertEquals(orderMonitorScreen.getPanelStatus(monitorServiceData.getMonitorService().getServiceName()), monitorServiceData.getMonitorServiceStatus());
		teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOMonitorVerifyThatStartDateIsSetWhenStartService(String rowID,
																	  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
		orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		Assert.assertFalse(orderMonitorScreen.isServiceStartDateExists());
		orderMonitorScreen.clickStartService();
		orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		Assert.assertTrue(orderMonitorScreen.isServiceStartDateExists());
		orderMonitorScreen.clickServiceDetailsDoneButton();

		teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOMonitorVerifyThatWhenChangeStatusForPhaseWithDoNotTrackIndividualServiceStatusesONPhaseStatusIsSetToAllServicesAssignedToPhase(String rowID,
																																					 String description, JSONObject testData) {
		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularVehicleInfoScreenSteps.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		}

		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		RegularPriceMatrixScreenSteps.savePriceMatrix();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(workOrderData.getMatrixServiceData().getMatrixServiceName()));
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);

		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorsData().get(0).getMonitorServicesData()) {
			orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService().getServiceName());
			Assert.assertTrue(orderMonitorScreen.isStartServiceButtonPresent());
			orderMonitorScreen.clickStartService();
			orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService());
			orderMonitorScreen.setCompletedServiceStatus();
			RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.COMPLETED);
		}

		Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonPresent());
		orderMonitorScreen.clickStartPhase();
		OrderMonitorData repairPhaseOrderMonitorData = workOrderData.getOrderMonitorsData().get(1);
		orderMonitorScreen.selectPanel(repairPhaseOrderMonitorData.getMonitorServicesData().get(0).getMonitorService());
		orderMonitorScreen.clickServiceStatusCell();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
		orderMonitorScreen.clickServiceDetailsDoneButton();

		orderMonitorScreen.clicksRepairPhaseLine();
		orderMonitorScreen.setCompletedPhaseStatus();

		for (MonitorServiceData monitorServiceData : repairPhaseOrderMonitorData.getMonitorServicesData())
			RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.COMPLETED);

		for (MonitorServiceData monitorServiceData : repairPhaseOrderMonitorData.getMonitorServicesData()) {
			orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService());
			Assert.assertEquals(orderMonitorScreen.getServiceStatusInPopup(monitorServiceData.getMonitorService().getServiceName()), OrderMonitorStatuses.COMPLETED.getValue());
			orderMonitorScreen.waitOrderMonitorScreenLoaded();
		}

		teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.waitTeamWorkOrdersScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalONAndNoSignature(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		orderSummaryScreen.clickSave();

		RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToMyInvoicesScreen();
		RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
		RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
		Assert.assertTrue(myInvoicesScreen.isInvoiceApproveButtonExists(invoiceNumber));
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatGreyAIconIsPresentForInvoiceWithCustomerApprovalOFFAndNoSignature(String rowID,
																								String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		orderSummaryScreen.clickSave();

		RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToMyInvoicesScreen();
		RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
		RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
		Assert.assertTrue(myInvoicesScreen.isInvoiceApproveButtonExists(invoiceNumber));
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAIconIsNotPresentForInvoiceWhenSignatureExists(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
			RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
			RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
			vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesScreen = new RegularServicesScreen();
			for (ServiceData serviceData : workOrderData.getServicesList())
				RegularServicesScreenSteps.selectService(serviceData.getServiceName());
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
			orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			orderSummaryScreen.checkApproveAndCreateInvoice();
			orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			orderSummaryScreen.clickSave();

			RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.valueOf(workOrderData.getInvoiceData().getInvoiceType()));
			invoiceInfoScreen.setPO(workOrderData.getInvoiceData().getPoNumber());
			final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
			invoiceInfoScreen.clickSaveAsFinal();
			RegularNavigationSteps.navigateBackScreen();
			RegularHomeScreenSteps.navigateToMyInvoicesScreen();
			RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
			RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
			RegularMyInvoicesScreenSteps.selectInvoiceForApprove(invoiceNumber);
			myInvoicesScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
			approveInspectionsScreen.clickApproveButton();
			approveInspectionsScreen.drawApprovalSignature();
			myInvoicesScreen.waitInvoicesScreenLoaded();

			Assert.assertFalse(myInvoicesScreen.isInvoiceApproveButtonExists(invoiceNumber));
			RegularNavigationSteps.navigateBackScreen();
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatRejectActionIsDisplayedForSRInStatusScheduledInspOrWOAndAssignForTech(String rowID,
																									String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		//Create first SR
		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehicleScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);

		String serviceRequestNumber1 = serviceRequestSscreen.getFirstServiceRequestNumber();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber1);
		serviceRequestSscreen.selectRejectAction();
		Helpers.acceptAlert();
		//Create second SR
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE);
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServiceRequestSteps.saveServiceRequest();

		String serviceRequestNumber2 = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber2), ServiceRequestStatus.SCHEDULED.getValue());
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber2);
		serviceRequestSscreen.selectRejectAction();
		Helpers.acceptAlert();

		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatRejectActionIsDisplayedForSRInStatusOnHoldInspOrWOAndAssignForTech(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehicleScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.ON_HOLD.getValue());

		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertTrue(serviceRequestSscreen.isRejectActionExists());
		RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_INPECTION);
		RegularInspectionTypesSteps.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		vehicleScreen.waitVehicleScreenLoaded();
		String inspectnumber = vehicleScreen.getInspectionNumber();
		RegularInspectionsSteps.saveInspectionAsFinal();
		serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectDetailsRequestAction();
		RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectnumber));
		teamInspectionsScreen.clickActionButton();
		teamInspectionsScreen.selectInspectionForAction(inspectnumber);

		teamInspectionsScreen.clickApproveInspections();
		teamInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectnumber);
		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
		boolean onhold = false;
		for (int i = 0; i < 7; i++) {
			RegularHomeScreenSteps.navigateToServiceRequestScreen();
			if (!serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber).equals(ServiceRequestStatus.ON_HOLD.getValue())) {
				serviceRequestSscreen.clickHomeButton();
				Helpers.waitABit(30 * 1000);
			} else {

				onhold = true;
				break;
			}
		}

		Assert.assertTrue(onhold);
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectRejectAction();
		Helpers.acceptAlert();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatRejectActionIsNotDisplayedForSRInStatusOnHoldInspOrWOAndNotAssignForTech(String rowID,
																									   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_ALL_PHASES.getServiceRequestTypeName());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertFalse(serviceRequestSscreen.isRejectActionExists());
		serviceRequestSscreen.selectCancelAction();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatRejectActionIsNotDisplayedForSRInStatusScheduledInspOrWOAndNotAssignForTech(String rowID,
																										  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE.getServiceRequestTypeName());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.addServicesToServiceRequest(serviceRequestData.getPercentageServices());
		serviceRequestsListInteractions.saveNewServiceRequest();
		String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();

		DriverBuilder.getInstance().getDriver().quit();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertFalse(serviceRequestSscreen.isRejectActionExists());
		serviceRequestSscreen.selectCancelAction();
		serviceRequestSscreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE.getServiceRequestTypeName());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
		serviceRequestsListInteractions.clickDoneButton();
		serviceRequestsListInteractions.addServicesToServiceRequest(serviceRequestData.getMoneyServices());
		serviceRequestsListInteractions.saveNewServiceRequest();
		serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();

		DriverBuilder.getInstance().getDriver().quit();

		mainScreen = homeScreen.clickLogoutButton();
		mainScreen.updateDatabase();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertFalse(serviceRequestSscreen.isRejectActionExists());
		serviceRequestSscreen.selectEditServiceRequestAction();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		Assert.assertTrue(vehicleScreen.getTechnician().isEmpty());
		vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServiceRequestSteps.saveServiceRequest();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testNotMultipleServiceWithRequiredPanelsIsAddedOneTimeToWOAfterSelecting(String rowID,
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

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.ALERT_YOU_CAN_ADD_ONLY_ONE_SERVICE, serviceData.getServiceName()));
			RegularServicesScreenSteps.waitServicesScreenLoad();
		}
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		Assert.assertEquals(selectedServicesScreen.getNumberOfSelectedServices(), 1);
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testRegularWOVerifyThatBundleItemsAreShownWhenCreateWOFromInspection(String rowID,
																					 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumberber = vehicleScreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(inspectionData.getBundleService().getBundleServiceName());
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(inspectionData.getBundleService().getBundleServiceName()));
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.selectInspectionForApprove(inspectionNumberber);
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumberber);
		approveInspectionsScreen.approveInspectionApproveAllAndSignature();
		RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionNumberber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularNavigationSteps.navigateToServicesScreen();
		servicesScreen.openCustomServiceDetails(inspectionData.getBundleService().getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : inspectionData.getBundleService().getServices()) {
			if (serviceData.isSelected())
				Assert.assertTrue(selectedServiceBundleScreen.checkBundleIsSelected(serviceData.getServiceName()));
			if (!serviceData.isSelected())
				Assert.assertTrue(selectedServiceBundleScreen.checkBundleIsNotSelected(serviceData.getServiceName()));
		}

		selectedServiceBundleScreen.clickServicesIcon();
		for (ServiceData serviceData : inspectionData.getBundleService().getServices())
			Assert.assertTrue(selectedServiceBundleScreen.isBundleServiceExists(serviceData.getServiceName()));
		selectedServiceBundleScreen.clickCloseServicesPopup();
		selectedServiceBundleScreen.clickCancel();
		RegularInspectionsSteps.cancelCreatingInspection();
		RegularNavigationSteps.navigateBackScreen();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenOptionAllowToCloseSRIsSetToONActionCloseIsShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
																														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularServiceRequestSteps.saveServiceRequest();

		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());

		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertTrue(serviceRequestSscreen.isCloseActionExists());
		serviceRequestSscreen.clickCancel();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenOptionAllowToCloseSRIsSetToOFFActionCloseIsNotShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
																															String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularServiceRequestSteps.saveServiceRequest();
		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertFalse(serviceRequestSscreen.isCloseActionExists());
		serviceRequestSscreen.clickCancel();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressNoAlertMessageIsClose(String rowID,
																									  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularServiceRequestSteps.saveServiceRequest();

		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.CLOSE, false);
		RegularMenuItemsScreenSteps.closeMenuScreen();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressYesListOfStatusReasonsIsShown(String rowID,
																											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularServiceRequestSteps.saveServiceRequest();

		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertTrue(serviceRequestSscreen.isCloseActionExists());
		serviceRequestSscreen.selectCloseAction();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		serviceRequestSscreen.clickCancelCloseReasonDialog();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsShownInCaseItIsAssignedToReasonOnBO(String rowID,
																											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		final String answerReason = "All work is done. Answer questions";
		final String answerQuestion = "A3";

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularServiceRequestSteps.saveServiceRequest();

		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertTrue(serviceRequestSscreen.isServiceRequestExists(serviceRequestNumber));
		Assert.assertTrue(serviceRequestSscreen.isCloseActionExists());
		serviceRequestSscreen.selectCloseAction();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		serviceRequestSscreen.selectUIAPickerValue(answerReason);
		serviceRequestSscreen.clickDoneCloseReasonDialog();
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.answerQuestion2(answerQuestion);
		serviceRequestSscreen.clickCloseSR();
		serviceRequestSscreen.waitForServiceRequestScreenLoad();
		Assert.assertFalse(serviceRequestSscreen.isServiceRequestExists(serviceRequestNumber));
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsNotShownInCaseItIsNotAssignedToReasonOnBO(String rowID,
																												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String answerReason = "All work is done. No Questions";

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularServiceRequestSteps.saveServiceRequest();

		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertTrue(serviceRequestSscreen.isServiceRequestExists(serviceRequestNumber));
		Assert.assertTrue(serviceRequestSscreen.isCloseActionExists());
		serviceRequestSscreen.selectCloseAction();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		serviceRequestSscreen.selectDoneReason(answerReason);
		Assert.assertFalse(serviceRequestSscreen.isServiceRequestExists(serviceRequestNumber));
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenCreateWOFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
																												String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String totalSale = "3";

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_WO_ONLY);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		servicesScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectCreateWorkOrderRequestAction();
		RegularWorkOrderTypesSteps.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(totalSale);
		orderSummaryScreen.clickSave();

		for (int i = 0; i < serviceRequestData.getMoneyServices().size(); i++) {
			String alerText = Helpers.getAlertTextAndAccept();
			String servicedetails = alerText.substring(alerText.indexOf("'") + 1, alerText.lastIndexOf("'"));
			RegularServicesScreenSteps.switchToSelectedServices();
			for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
				if (serviceData.getServiceName().equals(servicedetails)) {
					RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openSelectedServiceDetails(serviceData.getServiceName());
					selectedServiceDetailsScreen.clickVehiclePartsCell();
					selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
					selectedServiceDetailsScreen.saveSelectedServiceDetails();
					selectedServiceDetailsScreen.saveSelectedServiceDetails();
					RegularServicesScreenSteps.waitServicesScreenLoad();
				}
			}
			servicesScreen = new RegularServicesScreen();
			servicesScreen.clickSave();
		}
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectDetailsRequestAction();
		RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryOrdersButton();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.isWorkOrderExists(workOrderNumber);
		teamWorkOrdersScreen.clickBackButton();
		serviceRequestSscreen.clickBackButton();
		serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenCreateInspectionFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
																														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_INSP_ONLY);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		servicesScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_FOR_CALC);
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
		String inspectionNumberber = vehicleScreen.getInspectionNumber();

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
		selectedServicesScreen.clickSave();

		for (int i = 0; i < serviceRequestData.getMoneyServices().size(); i++) {
			String alerText = Helpers.getAlertTextAndAccept();
			String servicedetails = alerText.substring(alerText.indexOf("'") + 1, alerText.lastIndexOf("'"));
			RegularServicesScreenSteps.switchToSelectedServices();
			for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
				if (serviceData.getServiceName().equals(servicedetails)) {
					RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openSelectedServiceDetails(serviceData.getServiceName());
					selectedServiceDetailsScreen.clickVehiclePartsCell();
					selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
					selectedServiceDetailsScreen.saveSelectedServiceDetails();
					selectedServiceDetailsScreen.saveSelectedServiceDetails();
					RegularServicesScreenSteps.waitServicesScreenLoad();
				}
			}
			servicesScreen = new RegularServicesScreen();
			servicesScreen.clickSave();
		}
		serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectDetailsRequestAction();
		RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionNumberber));
		teamInspectionsScreen.clickBackButton();
		serviceRequestSscreen.clickBackButton();
		serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatOnInspectionApprovalScreenSelectedPriceMatrixValueIsShown(String rowID,
																						String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(Test_Company_Customer, InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularNavigationSteps.navigateToPriceMatrixScreen(priceMatrixScreenData.getMatrixScreenName());
			RegularPriceMatrixScreenSteps.selectPriceMatrixData(priceMatrixScreenData);
			RegularVehiclePartsScreenSteps.saveVehiclePart();
			RegularWizardScreenValidations.verifyScreenSubTotalPrice(priceMatrixScreenData.getMatrixScreenPrice());
			RegularWizardScreenValidations.verifyScreenTotalPrice(priceMatrixScreenData.getMatrixScreenTotalPrice());
		}

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);

		for (ServiceData serviceData : inspectionData.getServicesToApprovesList()) {
			Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData.getServiceName()));
			Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(serviceData.getServiceName()), serviceData.getServicePrice());
		}
		approveInspectionsScreen.clickCancelButton();
		approveInspectionsScreen.clickCancelButton();
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
		PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreensData().get(0);
		RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
		RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
		Assert.assertEquals(priceMatrixScreen.clearVehicleData(), AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), inspectionData.getInspectionPrice());
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionTotalPrice());
		RegularInspectionsSteps.saveInspection();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServicesToApprovesList().get(0).getServiceName()), inspectionData.getServicesToApprovesList().get(0).getServicePrice());
		Assert.assertFalse(approveInspectionsScreen.isInspectionServiceExistsForApprove(inspectionData.getServicesToApprovesList().get(1).getServiceName()));
		approveInspectionsScreen.clickCancelButton();
		approveInspectionsScreen.clickCancelButton();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatQuestionSectionIsShownPerServiceForFirstSelectedPanelWhenQFIsNotRequired(String rowID,
																									   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.selectServiceVehicleParts(inspectionData.getServiceData().getVehicleParts());
		final String selectedhehicleparts = servicesScreen.getListOfSelectedVehicleParts();

		for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts())
			Assert.assertTrue(selectedhehicleparts.contains(vehiclePartData.getVehiclePartName()));
		servicesScreen.clickSave();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		int i = 0;
		for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
			selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
			i++;
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertFalse(selectedServiceDetailsScreen.isQuestionFormCellExists());
			selectedServiceDetailsScreen.clickSaveButton();
		}
		selectedServicesScreen.clickSave();
		Helpers.acceptAlert();
		RegularInspectionsSteps.cancelCreatingInspection();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
																											   String description, JSONObject testData) {
		final String newLineSymbol = "\n";

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPECTION_VIN_ONLY);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.getVINField().click();
		Assert.assertTrue(vehicleScreen.getVINField().isDisplayed());
		vehicleScreen.getVINField().sendKeys(newLineSymbol);
		vehicleScreen.cancelOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenEditInspectionSelectedVehiclePartsForServicesArePresent(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		int i = 0;
		for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
			selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
			i++;
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(), vehiclePartData.getVehiclePartName());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		RegularInspectionsSteps.cancelCreatingInspection();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatItIsPossibleToSaveAsFinalInspectionLinkedToSR(String rowID,
																			String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
		RegularVehicleInfoScreenSteps.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularWizardScreensSteps.clickSaveButton();
		AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_DRAFT_MODE);

		InspectionData inspectionData = testCaseData.getInspectionData();
		RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
		String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
		RegularInspectionsSteps.saveInspectionAsDraft();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectDetailsRequestAction();
		RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		teamInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		RegularInspectionsSteps.saveInspectionAsFinal();
		Assert.assertTrue(teamInspectionsScreen.isInspectionIsApproveButtonExists(inspectionNumber));
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenFinalInspectionIsCopiedServisesAreCopiedWithoutStatuses_Approved_Declined_Skipped(String rowID,
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
		final String inspectionNumberber = vehicleScreen.getInspectionNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.selectService(inspectionData.getBundleService().getBundleServiceName());
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		RegularServicesScreenSteps.selectMatrixServiceData(inspectionData.getMatrixServiceData());
		servicesScreen.clickSave();
		RegularInspectionsSteps.saveInspectionAsFinal();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.clickActionButton();
		myInspectionsScreen.selectInspectionForAction(inspectionNumberber);

		myInspectionsScreen.clickApproveInspections();
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumberber);
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			if (serviceData.isSelected())
				Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData), "Can't find service:" + serviceData.getServiceName());
			else
				Assert.assertFalse(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData));

		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		RegularMyInspectionsSteps.selectInspectionForCopy(inspectionNumberber);
		RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.TEST_PACK_FOR_CALC);
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			if (serviceData.isSelected())
				Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
		RegularInspectionsSteps.saveInspectionAsFinal();
		myInspectionsScreen.waitMyInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatTextNotesAreCopiedToNewInspectionsWhenUseCopyAction(String rowID,
																				  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		final String notesText = "Test for copy";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.DEFAULT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumberber = vehicleScreen.getInspectionNumber();
		vehicleScreen.clickNotesButton();
		RegularNotesScreen notesScreen = new RegularNotesScreen();
		notesScreen.setNotes(notesText);
		notesScreen.clickSaveButton();
		RegularInspectionsSteps.saveInspectionAsFinal();
		RegularMyInspectionsSteps.selectInspectionForCopy(inspectionNumberber);
		vehicleScreen.waitVehicleScreenLoaded();
		String copiedinspectionNumberber = vehicleScreen.getInspectionNumber();
		RegularInspectionsSteps.saveInspectionAsFinal();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.isNotesIconPresentForInspection(copiedinspectionNumberber));
		RegularMyInspectionsSteps.selectInspectionNotesMenu(copiedinspectionNumberber);
		RegularNotesScreenValidations.verifyTextNotesPresent(notesText);
		RegularNotesScreenSteps.saveNotes();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatItIsPossibleToApproveTeamInspectionsUseMultiSelect(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inpectionsIDs = new ArrayList<>();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.DEFAULT);
			RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
			vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			inpectionsIDs.add(vehicleScreen.getInspectionNumber());
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesScreen = new RegularServicesScreen();
			servicesScreen.selectService(inspectionData.getBundleService().getBundleServiceName());
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			RegularInspectionsSteps.saveInspectionAsFinal();
			RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();

		}
		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToTeamInspectionsScreen();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		teamInspectionsScreen.clickActionButton();
		for (String inspectionID : inpectionsIDs) {
			teamInspectionsScreen.selectInspectionForAction(inspectionID);
		}

		teamInspectionsScreen.clickApproveInspections();
		teamInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		for (String inspectionID : inpectionsIDs) {
			approveInspectionsScreen.selectInspection(inspectionID);
			approveInspectionsScreen.clickApproveAllServicesButton();
			approveInspectionsScreen.clickSaveButton();
		}
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
		teamInspectionsScreen.clickActionButton();
		for (String inspectionID : inpectionsIDs) {
			teamInspectionsScreen.selectInspectionForAction(inspectionID);
		}
		teamInspectionsScreen.clickActionButton();
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, false);
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.SEND_EMAIL, true);
		teamInspectionsScreen.clickCancel();
		teamInspectionsScreen.clickDoneButton();
		teamInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatServicesOnServicePackageAreGroupedByTypeSelectedOnInspTypeWizard(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPECTION_GROUP_SERVICE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumberber = vehicleScreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreensData().get(0));
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.selectService(inspectionData.getServiceData().getServiceName());
		for (ServicesScreenData servicesScreenData : inspectionData.getServicesScreens()) {
			RegularNavigationSteps.navigateToScreen(servicesScreenData.getScreenName());
			servicesScreen.selectServicePanel(servicesScreenData.getDamageData().getDamageGroupName());
			RegularServicesScreenSteps.selectServiceWithServiceData(servicesScreenData.getDamageData().getMoneyService());
		}
		servicesScreen.waitServicesScreenLoaded();
		RegularInspectionsSteps.saveInspectionAsFinal();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.drawSignature();
		RegularInspectionsSteps.saveInspectionAsFinal();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
		RegularQuestionsScreenSteps.answerQuestion(inspectionData.getQuestionScreensData().get(1).getQuestionData());
		RegularInspectionsSteps.saveInspectionAsFinal();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspectionNumberber));
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAllInstancesOfOneServiceAreCopiedFromInspectionToWO(String rowID,
																				  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumberber = vehicleScreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		servicesScreen.waitServicesScreenLoaded();
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.selectInspectionForApprove(inspectionNumberber);
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumberber);
		approveInspectionsScreen.approveInspectionApproveAllAndSignature();
		RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionNumberber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList())
			if (serviceData.isSelected())
				Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData));
			else
				Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());
		RegularInspectionsSteps.cancelCreatingInspection();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAssignButtonIsPresentWhenSelectSomeTechInCaseDirectAssignOptionIsSetForInspectionType(String rowID,
																													String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPECTION_DIRECT_ASSIGN);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumberber = vehicleScreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		vehicleScreen.clickSaveAsFinal();
		RegularMyInspectionsSteps.selectInspectionForAssign(inspectionNumberber);
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.ASSIGN, true);
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatDuringLineApprovalSelectAllButtonsAreWorkingCorrectly(String rowID,
																					String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumberber = vehicleScreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		servicesScreen.waitServicesScreenLoaded();
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspectionNumberber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumberber);

		for (ServiceData serviceData : inspectionData.getServicesList())
			Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData));
		approveInspectionsScreen.clickApproveAllServicesButton();
		Assert.assertEquals(approveInspectionsScreen.getNumberOfActiveApproveButtons(), 2);
		approveInspectionsScreen.clickDeclineAllServicesButton();
		Assert.assertEquals(approveInspectionsScreen.getNumberOfActiveDeclineButtons(), 2);
		approveInspectionsScreen.clickSkipAllServicesButton();
		Assert.assertEquals(approveInspectionsScreen.getNumberOfActiveSkipButtons(), 2);
		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatApproveOptionIsNotPresentForApprovedInspectionInMultiselectMode(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> inspections = new ArrayList<>();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
			RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
			vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			final String inspectionNumber = vehicleScreen.getInspectionNumber();
			inspections.add(inspectionNumber);
			RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
			RegularInspectionsSteps.saveInspection();
			RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
			myInspectionsScreen.selectInspectionForAction(inspectionNumber);
			myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

			RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
			approveInspectionsScreen.selectInspection(inspectionNumber);
			approveInspectionsScreen.clickApproveAllServicesButton();
			approveInspectionsScreen.clickSaveButton();
			approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
			approveInspectionsScreen.clickDoneButton();
			RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
		}
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.clickActionButton();
		for (String inspectionID : inspections) {
			myInspectionsScreen.selectInspectionForAction(inspectionID);
		}
		myInspectionsScreen.clickActionButton();
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, false);
		myInspectionsScreen.clickCancel();
		myInspectionsScreen.clickDoneButton();
		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToTeamInspectionsScreen();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		teamInspectionsScreen.clickActionButton();
		for (String inspectionID : inspections) {
			teamInspectionsScreen.selectInspectionForAction(inspectionID);
		}
		myInspectionsScreen.clickActionButton();
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, false);
		teamInspectionsScreen.clickCancel();
		teamInspectionsScreen.clickDoneButton();
		teamInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatApproveOptionIsPresentInMultiselectModeOnlyOneOrMoreNotApprovedInspectionsAreSelected(String rowID,
																													String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> inspections = new ArrayList<>();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
			RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
			vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			inspections.add(vehicleScreen.getInspectionNumber());
			RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
			RegularInspectionsSteps.saveInspection();
			RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
		}
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspections.get(0));
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspections.get(0));
		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		myInspectionsScreen.clickActionButton();
		for (String inspectionID : inspections) {
			myInspectionsScreen.selectInspectionForAction(inspectionID);
		}
		myInspectionsScreen.clickActionButton();
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, true);
		myInspectionsScreen.clickCancel();
		myInspectionsScreen.clickDoneButton();
		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToTeamInspectionsScreen();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		teamInspectionsScreen.clickActionButton();
		for (String inspectionID : inspections) {
			teamInspectionsScreen.selectInspectionForAction(inspectionID);
		}
		myInspectionsScreen.clickActionButton();
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, true);
		teamInspectionsScreen.clickCancel();
		teamInspectionsScreen.clickDoneButton();
		teamInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenOptionDraftModeIsSetToONWhenSaveInspectionProvidePromptToAUserToSelectEitherDraftOrFinal(String rowID,
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
		final String inspectionNumberber = vehicleScreen.getInspectionNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		RegularInspectionsSteps.saveInspectionAsDraft();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.isDraftIconPresentForInspection(inspectionNumberber));
		myInspectionsScreen.clickOnInspection(inspectionNumberber);
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, false);
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.CREATE_WORKORDER, false);
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.CREATE_SERVICE_REQUEST, false);
		RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.COPY, false);
		myInspectionsScreen.clickCancel();
		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToTeamInspectionsScreen();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		Assert.assertTrue(teamInspectionsScreen.isDraftIconPresentForInspection(inspectionNumberber));

		teamInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatApproveWOIsWorkingCorrectUnderTeamWO(String rowID,
																	 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String locationFilterValue = "All locations";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.setFilterLocation(locationFilterValue);
		teamWorkOrdersScreen.clickSaveFilter();
		Assert.assertTrue(teamWorkOrdersScreen.isWorkOrderExists(workOrderNumber));
		Assert.assertTrue(teamWorkOrdersScreen.isWorkOrderHasApproveIcon(workOrderNumber));
		teamWorkOrdersScreen.approveWorkOrder(workOrderNumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		Assert.assertTrue(teamWorkOrdersScreen.isWorkOrderHasActionIcon(workOrderNumber));
		teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatItIsPosibleToAddPaymentFromDeviceForDraftInvoice(String rowID,
																					   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String cashcheckamount = "100";
		final String expectedPrice = "$0.00";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		RegularPriceMatrixScreenSteps.savePriceMatrix();
		servicesScreen.waitServicesScreenLoaded();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword("Zayats", "1111");
		orderSummaryScreen.clickSave();
		RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();
		RegularNavigationSteps.navigateBackScreen();
		BaseUtils.waitABit(1000 * 60);
		RegularHomeScreenSteps.navigateToMyInvoicesScreen();
		RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
		Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceInfoScreen.clickInvoicePayButton();
		invoiceInfoScreen.changePaynentMethodToCashNormal();
		invoiceInfoScreen.setCashCheckAmountValue(cashcheckamount);
		invoiceInfoScreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceInfoScreen.clickSaveAsDraft();
		RegularNavigationSteps.navigateBackScreen();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPage.clickInvoicesLink();
		invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);

		invoicesWebPage.clickFindButton();

		Assert.assertEquals(invoicesWebPage.getInvoicePONumber(invoiceNumber), invoiceData.getPoNumber());
		Assert.assertEquals(invoicesWebPage.getInvoicePOPaidValue(invoiceNumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicePaymentsWebPage = new InvoicePaymentsTabWebPage(webdriver);
		invoicesWebPage.clickInvoicePayments(invoiceNumber);
		Assert.assertEquals(invoicePaymentsWebPage.getPaymentsTypeAmountValue("Cash/Check"), PricesCalculations.getPriceRepresentation(cashcheckamount));
		Assert.assertEquals(invoicePaymentsWebPage.getPaymentsTypeCreatedByValue("Cash/Check"), "Employee Simple 20%");

		Assert.assertEquals(invoicePaymentsWebPage.getPaymentsTypeAmountValue("PO/RO"), expectedPrice);
		Assert.assertEquals(invoicePaymentsWebPage.getPaymentsTypeCreatedByValue("PO/RO"), "Back Office");
		invoicePaymentsWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderMyInvoice(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String cashcheckamount = "100";
		final String expectedPrice = "$0.00";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		RegularPriceMatrixScreenSteps.savePriceMatrix();
		servicesScreen.waitServicesScreenLoaded();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword("Zayats", "1111");
		orderSummaryScreen.clickSave();
		RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();
		RegularNavigationSteps.navigateBackScreen();
		BaseUtils.waitABit(1000 * 60);
		RegularHomeScreenSteps.navigateToMyInvoicesScreen();
		RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
		RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
		Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceInfoScreen.clickInvoicePayButton();
		invoiceInfoScreen.changePaynentMethodToCashNormal();
		invoiceInfoScreen.setCashCheckAmountValue(cashcheckamount);
		invoiceInfoScreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceInfoScreen.clickSaveAsDraft();
		RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoiceNumber);
		myInvoicesScreen.changePO(invoiceData.getNewPoNumber());
		RegularNavigationSteps.navigateBackScreen();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPage.clickInvoicesLink();
		invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();

		Assert.assertEquals(invoicesWebPage.getInvoicePONumber(invoiceNumber), invoiceData.getNewPoNumber());
		Assert.assertEquals(invoicesWebPage.getInvoicePOPaidValue(invoiceNumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicePaymentsWebPage = new InvoicePaymentsTabWebPage(webdriver);
		invoicesWebPage.clickInvoicePayments(invoiceNumber);

		Assert.assertEquals(invoicePaymentsWebPage.getPaymentDescriptionTypeAmountValue("PO #: " + invoiceData.getNewPoNumber()), expectedPrice);
		invoicePaymentsWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderTeamInvoice(String rowID,
																							 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String expectedPrice = "$0.00";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		RegularPriceMatrixScreenSteps.savePriceMatrix();
		servicesScreen.waitServicesScreenLoaded();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword("Zayats", "1111");
		orderSummaryScreen.clickSave();
		RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();
		RegularNavigationSteps.navigateBackScreen();
		BaseUtils.waitABit(1000 * 60);
		RegularHomeScreenSteps.navigateToTeamInvoicesScreen();
		RegularTeamInvoicesScreen teamInvoicesScreen = new RegularTeamInvoicesScreen();
		teamInvoicesScreen.selectInvoice(invoiceNumber);
		teamInvoicesScreen.clickChangePOPopup();
		teamInvoicesScreen.changePO(invoiceData.getNewPoNumber());
		teamInvoicesScreen.clickHomeButton();

		Helpers.waitABit(5000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPage.clickInvoicesLink();
		invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();

		Assert.assertEquals(invoicesWebPage.getInvoicePONumber(invoiceNumber), invoiceData.getNewPoNumber());
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicePaymentsWebPage = new InvoicePaymentsTabWebPage(webdriver);
		invoicesWebPage.clickInvoicePayments(invoiceNumber);

		Assert.assertEquals(invoicePaymentsWebPage.getPaymentDescriptionTypeAmountValue("PO #: " + invoiceData.getNewPoNumber()), expectedPrice);
		invoicePaymentsWebPage.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyFilterForTeamWOThatReturnsOnlyWorkAssignedToTechWhoIsLoggedIn(String rowID,
																								String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String locationFilterValue = "All locations";
		final String serviceToChangeRepairOrderVendor = "Dye";

		Employee defEmployee = new Employee();
		defEmployee.setEmployeeFirstName("Employee");
		defEmployee.setEmployeeLastName("Simple 20%");
		defEmployee.setEmployeePassword("12345");

		Employee defZayats = new Employee();
		defZayats.setEmployeeFirstName("Oksana");
		defZayats.setEmployeeLastName("Zayats");
		defZayats.setEmployeePassword("1111");

		Employee defInspector = new Employee();
		defInspector.setEmployeeFirstName("Inspector");
		defInspector.setEmployeeLastName("1");
		defInspector.setEmployeePassword("12345");

		ArrayList<Employee> allEmployees = new ArrayList<>();
		allEmployees.add(defEmployee);
		allEmployees.add(defZayats);
		allEmployees.add(defInspector);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getServiceNewTechnician() != null) {
				RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
				RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				if (!serviceData.getServiceDefaultTechnician().isSelected())
					RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
				if (serviceData.getServiceNewTechnician().isSelected())
					RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
				RegularServiceDetailsScreenSteps.saveServiceDetails();
				RegularServiceDetailsScreenSteps.saveServiceDetails();
			} else
				RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		}

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
		RegularNavigationSteps.navigateBackScreen();

		BaseUtils.waitABit(20 * 1000);
		for (Employee employee : allEmployees) {
			RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
			mainScreen.userLogin(employee.getEmployeeFirstName(), employee.getEmployeePassword());
			RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
			RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
			teamWorkOrdersScreen.clickSearchButton();
			teamWorkOrdersScreen.selectSearchLocation(locationFilterValue);
			teamWorkOrdersScreen.clickSearchSaveButton();
			teamWorkOrdersScreen.clickOnWO(workOrderNumber);
			RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
			orderMonitorScreen.checkMyWorkCheckbox();
			for (ServiceData serviceData : workOrderData.getServicesList()) {
				if (serviceData.getServiceDefaultTechnician().getTechnicianFullName().equals(employee.getEmployeeName())) {
					if (serviceData.getServiceDefaultTechnician().isSelected())
						Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
					else
						Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
				} else if (serviceData.getServiceNewTechnician() == null) {
					Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
				} else if (serviceData.getServiceNewTechnician().getTechnicianFullName().equals(employee.getEmployeeName())) {
					if (serviceData.getServiceNewTechnician().isSelected())
						Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
					else
						Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
				} else {
					Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
				}
			}
			RegularNavigationSteps.navigateBackScreen();
			RegularNavigationSteps.navigateBackScreen();
		}

		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();

		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);
		BaseUtils.waitABit(1000);
		backOfficeHeaderPanel.clickMonitorLink();
		RepairOrdersWebPage repairOrdersWebPage = new RepairOrdersWebPage(webdriver);
		monitorWebPage.clickRepairOrdersLink();
		repairOrdersWebPage.makeSearchPanelVisible();
		repairOrdersWebPage.setSearchWoNumber(workOrderNumber);
		repairOrdersWebPage.selectSearchLocation("Default Location");

		repairOrdersWebPage.selectSearchTimeframe("Custom");
		repairOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		repairOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		repairOrdersWebPage.clickFindButton();

		VendorOrderServicesWebPage vendorOrderServicesWebPage = new VendorOrderServicesWebPage(webdriver);
		repairOrdersWebPage.clickOnWorkOrderLinkInTable(workOrderNumber);
		vendorOrderServicesWebPage.changeRepairOrderServiceVendor(serviceToChangeRepairOrderVendor, "Device Team");
		vendorOrderServicesWebPage.waitABit(3000);
		Assert.assertEquals(vendorOrderServicesWebPage.getRepairOrderServiceTechnician(serviceToChangeRepairOrderVendor), "Oksi User");
		DriverBuilder.getInstance().getDriver().quit();

		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		teamWorkOrdersScreen.selectWOMonitor();
		RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getServiceDefaultTechnician().isSelected())
				Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
			else
				Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
		}
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
		mainScreen = homeScreen.clickLogoutButton();

		mainScreen.userLogin(allEmployees.get(1).getEmployeeFirstName(), allEmployees.get(1).getEmployeePassword());
		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
		}
		RegularNavigationSteps.navigateBackScreen();
		RegularNavigationSteps.navigateBackScreen();
		mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testSRVerifyMultipleInspectionsAndMultipleWorkOrdersToBeTiedToAServiceRequest(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inspectionNumberbers = new ArrayList<>();
		List<String> workOrderNumbers = new ArrayList<>();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehicleScreen.clickSave();
		Helpers.getAlertTextAndCancel();
		final String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.valueOf(inspectionData.getInspectionType()));
			vehicleScreen.waitVehicleScreenLoaded();
			inspectionNumberbers.add(vehicleScreen.getInspectionNumber());
			if (inspectionData.isDraft()) {
				RegularInspectionsSteps.saveInspectionAsDraft();
			} else
				RegularServiceRequestSteps.saveServiceRequest();
		}

		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectDetailsRequestAction();
		RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
		for (String inspectnumber : inspectionNumberbers)
			Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectnumber));

		teamInspectionsScreen.clickBackButton();
		serviceRequestSscreen.clickBackButton();
		serviceRequestSscreen = new RegularServiceRequestsScreen();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
			serviceRequestSscreen.selectCreateWorkOrderRequestAction();
			RegularWorkOrderTypesSteps.selectWorkOrderType(WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
			workOrderNumbers.add(vehicleScreen.getWorkOrderNumber());
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
			orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
			RegularWizardScreensSteps.clickSaveButton();
		}
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectDetailsRequestAction();
		RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryOrdersButton();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		for (String workOrderNumber : workOrderNumbers)
			Assert.assertTrue(teamWorkOrdersScreen.isWorkOrderExists(workOrderNumber));
		teamInspectionsScreen.clickBackButton();
		serviceRequestSscreen.clickBackButton();
		serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval(String rowID,
																													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		final int timetowaitwo = 4;
		final String inspectionnotes = "Inspection notes";
		final String servicenotes = "Service Notes";
		final String locationFilterValue = "All locations";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			if (serviceData.getServicePrice() != null)
				RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			else
				RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		}
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);

		visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_SPORT_CAR);
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		vehicleScreen.clickNotesButton();
		RegularNotesScreen notesScreen = new RegularNotesScreen();
		notesScreen.setNotes(inspectionnotes);
		notesScreen.clickSaveButton();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			notesScreen = selectedServiceDetailsScreen.clickNotesCell();
			notesScreen.setNotes(servicenotes);
			notesScreen.clickSaveButton();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		for (ServiceData serviceData : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isNotesIconPresentForSelectedService(serviceData.getServiceName()));

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularNavigationSteps.navigateToPriceMatrixScreen(priceMatrixScreenData.getMatrixScreenName());
			RegularPriceMatrixScreenSteps.selectPriceMatrixData(priceMatrixScreenData);
			RegularVehiclePartsScreenSteps.saveVehiclePart();
		}
		RegularInspectionsSteps.saveInspection();
		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myInspectionsScreen.isNotesIconPresentForInspection(inspectionNumber));
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);
		approveInspectionsScreen.clickApproveAllServicesButton();
		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneButton();
		RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickHomeButton();

		for (int i = 0; i < timetowaitwo; i++) {
			Helpers.waitABit(60 * 1000);
			RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
			teamWorkOrdersScreen.clickHomeButton();
		}
		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.setSearchType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR.getWorkOrderTypeName());
		teamWorkOrdersScreen.selectSearchLocation(locationFilterValue);
		teamWorkOrdersScreen.clickSearchSaveButton();

		final String workOrderNumber = teamWorkOrdersScreen.getFirstWorkOrderNumberValue();
		BaseUtils.waitABit(45 * 1000);
		teamWorkOrdersScreen.clickHomeButton();
		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		teamWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);

		Assert.assertEquals(vehicleScreen.getEst(), inspectionNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		vehicleScreen.clickNotesButton();
		Assert.assertTrue(notesScreen.getNotesValue().length() > 0);
		notesScreen.clickSaveButton();

		servicesScreen.waitServicesScreenLoaded();
		RegularServicesScreenSteps.switchToSelectedServices();
		for (ServiceData serviceData : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isNotesIconPresentForSelectedWorkOrderService(serviceData.getServiceName()));
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		teamWorkOrdersScreen.waitTeamWorkOrdersScreenLoaded();
		teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired(String rowID,
																												String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());

		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), 0);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertTrue(selectedServiceDetailsScreen.isQuestionFormCellExists());
		Assert.assertEquals(selectedServiceDetailsScreen.getQuestion2Value(), inspectionData.getServiceData().getQuestionData().getQuestionAnswer());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		for (int i = 1; i < inspectionData.getServiceData().getVehicleParts().size(); i++) {
			selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertFalse(selectedServiceDetailsScreen.isQuestionFormCellExists());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
		}
		RegularInspectionsSteps.saveInspection();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForTeamAndMyInvoices(String rowID,
																									  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		RegularPriceMatrixScreenSteps.savePriceMatrix();
		servicesScreen.waitServicesScreenLoaded();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.selectEmployeeAndTypePassword("Zayats", "1111");
		orderSummaryScreen.clickSave();
		RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceInfoScreen.setPO(invoiceData.getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsDraft();
		RegularNavigationSteps.navigateBackScreen();
		homeScreen.clickMyInvoicesButton();
		RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
		myInvoicesScreen.selectInvoice(invoiceNumber);
		RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_PO);
		myInvoicesScreen.changePO(invoiceData.getNewPoNumber());
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY);
		myInvoicesScreen.clickCancel();

		myInvoicesScreen.switchToTeamView();

		RegularTeamInvoicesScreen teamInvoicesScreen = new RegularTeamInvoicesScreen();
		teamInvoicesScreen.selectInvoice(invoiceNumber);
		RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_PO);
		teamInvoicesScreen.changePO(invoiceData.getNewPoNumber());
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY);
		teamInvoicesScreen.clickCancel();
		teamInvoicesScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatTechSplitsIsSavedInPriceMatrices(String rowID,
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
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		RegularVehiclePartsScreenSteps.setVehiclePartPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());

		RegularVehiclePartsScreenSteps.verifyVehiclePartTechnicianValue(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
		RegularVehiclePartsScreenSteps.selectVehiclePartTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());
		List<ServiceTechnician> serviceTechnicians = new ArrayList<>();
		serviceTechnicians.add(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
		serviceTechnicians.add(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());
		RegularVehiclePartsScreenSteps.verifyVehiclePartTechniciansValue(serviceTechnicians);
		RegularVehiclePartsScreenSteps.saveVehiclePart();
		vehicleScreen.clickSave();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.clickSave();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		RegularTechRevenueScreen techrevenuescreen = myWorkOrdersScreen.selectWorkOrderTechRevenueMenuItem(workOrderNumber);
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName()));
		techrevenuescreen.clickBackButton();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatItIsNotPossibleToChangeDefaultTechViaServiceTypeSplit(String rowID,
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
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		selectedServiceDetailsScreen.selecTechnician(workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.waitServicesScreenLoaded();
		servicesScreen.clickTechnicianToolbarIcon();
		servicesScreen.changeTechnician("Dent", workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatWhenUseCopyServicesActionForWOAllServiceInstancesShouldBeCopied(String rowID,
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
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));

		Assert.assertEquals(servicesScreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());

		Assert.assertEquals(servicesScreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatWONumberIsNotDuplicated(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

		//customer approval ON
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		final String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();

		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
		myWorkOrdersScreen.clickInvoiceIcon();
		RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
		invoiceInfoScreen.clickSaveAsFinal();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();
		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsWebPage.clickInvoicesLink();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();
		invoicesWebPage.archiveInvoiceByNumber(invoiceNumber);
		Assert.assertFalse(invoicesWebPage.isInvoiceDisplayed(invoiceNumber));
		webdriver.quit();

		//Create second WO
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		final String workOrder2 = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		myWorkOrdersScreen.approveWorkOrder(workOrder2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrder2);
		myWorkOrdersScreen.clickInvoiceIcon();
		RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
		invoiceInfoScreen.clickSaveAsFinal();
		RegularNavigationSteps.navigateBackScreen();
		homeScreen.clickStatusButton();
		homeScreen.updateDatabase();
		RegularMainScreen mainScreen = new RegularMainScreen();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		//Create third WO
		homeScreen.clickMyWorkOrdersButton();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		final String workOrder3 = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();

		WorkOrdersWebPage workorderspage = new WorkOrdersWebPage(webdriver);
		operationsWebPage.clickWorkOrdersLink();

		workorderspage.makeSearchPanelVisible();
		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workorderspage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
		workorderspage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		workorderspage.setSearchOrderNumber(workOrder3);
		workorderspage.clickFindButton();

		Assert.assertEquals(workorderspage.getWorkOrdersTableRowCount(), 1);
		webdriver.quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatInCaseValidVINIsDecodedReplaceExistingMakeAndModelWithNewOne(String rowID,
																							 String description, JSONObject testData) {

		final String[] VINs = {"2A8GP54L87R279721", "1FMDU32X0PUB50142", "GFFGG"};
		final String makes[] = {"Chrysler", "Ford", ""};
		final String models[] = {"Town and Country", "Explorer", ""};


		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		for (int i = 0; i < VINs.length; i++) {
			vehicleScreen.setVIN(VINs[i]);
			Assert.assertEquals(vehicleScreen.getMake(), makes[i]);
			Assert.assertEquals(vehicleScreen.getModel(), models[i]);
			vehicleScreen.clearVINCode();
		}

		vehicleScreen.cancelOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatWhenServiceDoNotHaveQuestionsAndSelectSeveralPanelsDoNotUnderlineAnyone(String rowID,
																										String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

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
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.selectServiceVehicleParts(workOrderData.getServiceData().getVehicleParts());

		for (VehiclePartData vehiclePartData : workOrderData.getServiceData().getVehicleParts()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertTrue(selectedServiceDetailsScreen.getVehiclePartValue().contains(vehiclePartData.getVehiclePartName()));
		}
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(workOrderData.getServiceData().getServiceName()), workOrderData.getServiceData().getVehicleParts().size());
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
																												 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.clickHomeButton();

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_VIN_ONLY);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		Assert.assertTrue(vehicleScreen.getVINField().isDisplayed());
		vehicleScreen.clickVINField();
		Assert.assertTrue(vehicleScreen.getVINField().isDisplayed());
		vehicleScreen.cancelOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatMessageIsShownForMoneyAndLaborServiceWhenPriceIsChangedTo0UnderWO(String rowID,
																								  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String zeroPrice = "0";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_MONITOR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			if (serviceData.getServicePrice().equals(zeroPrice)) {
				RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
				if (serviceData.getServicePrice().equals(zeroPrice))
					AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
				selectedServiceDetailsScreen.clickTechniciansIcon();
				AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
			} else {

				selectedServiceDetailsScreen.clickTechniciansIcon();
				AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
				selectedServiceDetailsScreen.cancelSelectedServiceDetails();
				selectedServiceDetailsScreen.setServiceRateValue(serviceData.getServicePrice());
				selectedServiceDetailsScreen.clickTechniciansIcon();
			}

			selectedServiceDetailsScreen.searchTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFirstName());
			selectedServiceDetailsScreen.selecTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.cancelSearchTechnician();
			selectedServiceDetailsScreen.searchTechnician(serviceData.getServiceNewTechnician().getTechnicianFirstName());
			selectedServiceDetailsScreen.selecTechnician(serviceData.getServiceNewTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.cancelSearchTechnician();
			if (serviceData.getServicePrice().equals(zeroPrice)) {
				Assert.assertFalse(selectedServiceDetailsScreen.isTechnicianSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
				Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceData.getServiceNewTechnician().getTechnicianFullName()));
			} else {
				Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
				Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceData.getServiceNewTechnician().getTechnicianFullName()));
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

		}
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatValidationIsPresentForVehicleTrimField(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String trimvalue = "Sport Plus";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_VEHICLE_TRIM_VALIDATION);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.clickSave();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TRIM_REQUIRED);
		vehicleScreen.setTrim(trimvalue);
		Assert.assertEquals(vehicleScreen.getTrim(), trimvalue);

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatTotalSaleIsNotShownWhenCheckmarkTotalSaleRequiredIsNotSetToOFF(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		final String location = "All locations";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TOTAL_SALE_NOT_REQUIRED);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		Assert.assertFalse(orderSummaryScreen.isTotalSaleFieldPresent());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();

		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(location);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		Assert.assertFalse(orderSummaryScreen.isTotalSaleFieldPresent());
		RegularWizardScreensSteps.clickSaveButton();
		RegularTeamWorkOrdersSteps.waitTeamWorkOrdersScreenLoaded();
		teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatItIsPossibleToAssignTechToOrderByActionTechnicians(String rowID,
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
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());

		RegularServicesScreenSteps.selectBundleService(workOrderData.getBundleService());
		for (ServiceData serviceData : workOrderData.getMoneyServices())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

		RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		RegularVehiclePartsScreenSteps.setVehiclePartPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
		RegularVehiclePartsScreenSteps.verifyVehiclePartTechnicianValue(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
		RegularVehiclePartsScreenSteps.selectVehiclePartAdditionalServiceAndSave(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService());
		RegularVehiclePartsScreenSteps.saveVehiclePart();
		vehicleScreen.clickSave();

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectWorkOrderTechniciansMenuItem(workOrderNumber);
		RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
		RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		Assert.assertEquals(vehicleScreen.getTechnician(), workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName() +
				", " + workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsVerifyThatWhenPanelGroupingIsUsedForPackageForSelectedPanelOnlyLinkedServicesAreShown(String rowID,
																													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_SERVICE_TYPE_WITH_OUT_REQUIRED);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (DamageData damageData : inspectionData.getDamagesData()) {
			servicesScreen.selectServicePanel(damageData.getDamageGroupName());
			for (ServiceData serviceData : damageData.getMoneyServices())
				RegularAvailableServicesScreenValidations.verifyServiceExixts(serviceData.getServiceName(), true);
			servicesScreen.clickBackServicesButton();
			servicesScreen = new RegularServicesScreen();
		}
		RegularInspectionsSteps.saveInspection();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatWOIsSavedCorrectWithSelectedSubService_NoMessageWithIncorrectTechSplit(String rowID,
																									   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices())
			RegularServicesScreenSteps.selectServiceWithSubServices(workOrderData.getDamageData());


		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices())
			Assert.assertTrue(selectedServicesScreen.isServiceWithSubSrviceSelected(workOrderData.getDamageData().getDamageGroupName(),
					serviceData.getServiceName()));
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatAnswerServicesAreCorrectlyAddedForWOWhenPanelGroupIsSet(String rowID,
																						String description, JSONObject testData) {

		final String questionName = "Q1";
		final String questionAswer = "No - rate 0";
		final String questionAswerSecond = "A1";
		final String questionVehiclePart = "Deck Lid";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_PANEL_GROUP);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularNavigationSteps.navigateToScreen("Zayats Section2");
		RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
		questionsScreen.selectAnswerForQuestionWithAdditionalConditions(questionName, questionAswer, questionAswerSecond, questionVehiclePart);

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatSearchBarIsPresentForServicePackScreen(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getVehiclePart() != null)
				RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			else
				RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		}
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatCancelMessageIsShownForNewOrExistingWO(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.clickChangeScreen();
		vehicleScreen.clickCancel();
		AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_CREATION);
		vehicleScreen.clickCancel();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_CREATION);

		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		String workOrderNumber = myWorkOrdersScreen.getFirstWorkOrderNumberValue();
		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.clickChangeScreen();
		vehicleScreen.clickCancel();
		AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_EDIT);
		vehicleScreen.clickCancel();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_EDIT);

		RegularMyWorkOrdersSteps.openWorkOrderDetails(workOrderNumber);
		vehicleScreen.clickChangeScreen();
		vehicleScreen.clickCancel();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatAcceptDeclineActionsArePresentForTechWhenTechnicianAcceptanceRequiredOptionIsONAndStatusIsProposed(String rowID,
																																 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		final String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();


		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		Assert.assertTrue(serviceRequestSscreen.isServiceRequestProposed(serviceRequestNumber));
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertTrue(serviceRequestSscreen.isAcceptActionExists());
		Assert.assertTrue(serviceRequestSscreen.isDeclineActionExists());
		serviceRequestSscreen.selectAcceptAction();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_ACCEPT_SERVICEREQUEST);

		Assert.assertTrue(serviceRequestSscreen.isServiceRequestOnHold(serviceRequestNumber));
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertFalse(serviceRequestSscreen.isAcceptActionExists());
		Assert.assertFalse(serviceRequestSscreen.isDeclineActionExists());
		serviceRequestSscreen.clickCancel();
		serviceRequestSscreen.clickHomeButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenSRIsDeclinedStatusReasonShouldBeSelected(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		final String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();


		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		Assert.assertTrue(serviceRequestSscreen.isServiceRequestProposed(serviceRequestNumber));
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertTrue(serviceRequestSscreen.isAcceptActionExists());
		Assert.assertTrue(serviceRequestSscreen.isDeclineActionExists());
		serviceRequestSscreen.selectDeclineAction();

		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_DECLINE_SERVICEREQUEST);
		serviceRequestSscreen.clickDoneCloseReasonDialog();
		serviceRequestSscreen.waitForServiceRequestScreenLoad();
		Assert.assertFalse(serviceRequestSscreen.isServiceRequestExists(serviceRequestNumber));
		serviceRequestSscreen.clickHomeButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatSRIsNotAcceptedWhenEmployeeReviewOrUpdateIt(String rowID,
																		  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		final String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();


		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		Assert.assertTrue(serviceRequestSscreen.isServiceRequestProposed(serviceRequestNumber));
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectEditServiceRequestAction();
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setTech(serviceRequestData.getVihicleInfo().getDefaultTechnician().getTechnicianFullName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularServiceRequestSteps.saveServiceRequest();
		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		Assert.assertTrue(serviceRequestSscreen.isAcceptActionExists());
		Assert.assertTrue(serviceRequestSscreen.isDeclineActionExists());
		serviceRequestSscreen.clickCancel();

		serviceRequestSscreen.clickHomeButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testSRVerifyThatItIsPossibleToAcceptDeclineAppointmentWhenOptionAppointmentAcceptanceRequiredEqualsON(String rowID,
																													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String technicianValue = "Employee Simple 20%";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		String startDate = LocalDate.now().plusDays(1).format(formatter);
		String endDate = LocalDate.now().plusDays(2).format(formatter);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
		loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backOfficeHeaderPanel.clickOperationsLink();

		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

		serviceRequestsListInteractions.clickGeneralInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestGeneralInfoAssignedTo(technicianValue);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel());
		serviceRequestsListInteractions.clickDoneButton();

		serviceRequestsListInteractions.saveNewServiceRequest();
		final String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
		serviceRequestsListInteractions.addAppointmentFromSRList(startDate, endDate, technicianValue);
        Assert.assertTrue(new ServiceRequestsListVerifications().isAddAppointmentFromSRListClosed(),
                "The Add Appointment dialog hasn't been closed");
		DriverBuilder.getInstance().getDriver().quit();

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();

		serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
		serviceRequestSscreen.selectAppointmentRequestAction();
		Assert.assertTrue(serviceRequestSscreen.isAcceptAppointmentRequestActionExists());
		Assert.assertTrue(serviceRequestSscreen.isDeclineAppointmentRequestActionExists());
		serviceRequestSscreen.clickBackButton();

		serviceRequestSscreen.clickHomeButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyAssignTechToServiceTypeInsteadOfIndividualServices(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_GROUP_SERVICE_TYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		servicesScreen.clickTechnicianToolbarIcon();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.NO_SELECTED_SERVICES);
		for (DamageData damageData : workOrderData.getDamagesData()) {
			RegularServicesScreenSteps.selectPanelServiceData(damageData);
			servicesScreen.clickBackServicesButton();
		}

		servicesScreen.clickTechnicianToolbarIcon();
		RegularServiceTypesScreen serviceTypesScreen = new RegularServiceTypesScreen();
		serviceTypesScreen.waitServiceTypesScreenLoaded();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(serviceData.getServiceName()));

		DamageData moneyServicePanel = workOrderData.getDamagesData().get(0);
		serviceTypesScreen.clickOnPanel(moneyServicePanel.getDamageGroupName());
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selecTechnician(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.clickSaveButton();
		serviceTypesScreen.clickSaveButton();

		servicesScreen.selectServicePanel(moneyServicePanel.getDamageGroupName());
		for (ServiceData serviceData : moneyServicePanel.getMoneyServices()) {
			selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
			selectedServiceDetailsScreen.clickCancel();
			selectedServiceDetailsScreen.clickCancel();
		}
		servicesScreen.clickBackServicesButton();

		DamageData bundleServicePanel = workOrderData.getDamagesData().get(1);
		servicesScreen.clickTechnicianToolbarIcon();
		serviceTypesScreen.clickOnPanel(bundleServicePanel.getDamageGroupName());
		selectedServiceDetailsScreen.selecTechnician(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.clickSaveButton();
		serviceTypesScreen.clickSaveButton();

		servicesScreen.switchToSelectedServicesTab();
		servicesScreen.selectServiceSubSrvice(bundleServicePanel.getBundleService().getBundleServiceName());
		for (ServiceData serviceData : bundleServicePanel.getBundleService().getServices()) {
			RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
			selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
			selectedServiceDetailsScreen.clickTechniciansCell();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
			selectedServiceDetailsScreen.clickCancel();
			selectedServiceDetailsScreen.clickCancel();
		}
		selectedServiceDetailsScreen.clickCancel();
		servicesScreen.switchToAvailableServicesTab();

		servicesScreen.clickTechnicianToolbarIcon();
		serviceTypesScreen.clickOnPanel(workOrderData.getDamageData().getDamageGroupName());
		selectedServiceDetailsScreen.selecTechnician(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.selecTechnician(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.clickSaveButton();
		serviceTypesScreen.clickSaveButton();

		servicesScreen.switchToSelectedServicesTab();
		servicesScreen.selectServiceSubSrvice(workOrderData.getDamageData().getBundleService().getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		selectedServiceBundleScreen.openBundleInfo(workOrderData.getDamageData().getBundleService().getService().getServiceName());
		selectedServiceDetailsScreen.clickTechniciansCell();

		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName()));
		selectedServiceDetailsScreen.clickCancel();
		selectedServiceDetailsScreen.clickCancel();
		selectedServiceBundleScreen.clickCancel();
		servicesScreen.switchToAvailableServicesTab();

		servicesScreen.selectServicePanel(moneyServicePanel.getDamageGroupName());
		RegularServicesScreenSteps.openCustomServiceDetails(moneyServicePanel.getMoneyService().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(moneyServicePanel.getMoneyService().getVehiclePart());
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
		selectedServiceDetailsScreen.clickCancel();
		selectedServiceDetailsScreen.clickCancel();
		servicesScreen.clickBackServicesButton();

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatDefaultTechIsNotChangedWhenResetOrderSplit(String rowID,
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
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getMoneyServices()) {
			RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getServiceDefaultTechnician() != null) {
				RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
				RegularServiceDetailsScreenSteps.saveServiceDetails();
			}
			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}

		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleInfoScreenSteps.clickTech();
		RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
		//selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : workOrderData.getMoneyServices()) {
			selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice2() != null)
				RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice2());
			if (serviceData.getServiceDefaultTechnician() != null) {
				RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
				RegularServiceDetailsScreenSteps.saveServiceDetails();
			} else {
				RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getNewTechnician());
				RegularServiceDetailsScreenSteps.saveServiceDetails();
			}
			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatItIsPossibleToAssignTechWhenWOIsNotStarted(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String defaultLocationValue = "Test Location ZZZ";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.selectLocation(defaultLocationValue);
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getMoneyServices())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.switchToTeamView();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defaultLocationValue);
		teamWorkOrdersScreen.clickSearchSaveButton();

		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
		OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
		MonitorServiceData firstMonitorServiceData = orderMonitorData.getMonitorServicesData().get(0);
		orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
		orderMonitorScreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selecTechnician(firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		orderMonitorScreen = new RegularOrderMonitorScreen();
		orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
		Assert.assertEquals(orderMonitorScreen.getServiceTechnicianValue(), firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
		orderMonitorScreen.clickServiceDetailsDoneButton();

		orderMonitorScreen.clickStartOrderButton();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.WOULD_YOU_LIKE_TO_START_REPAIR_ORDER);
		orderMonitorScreen = new RegularOrderMonitorScreen();

		MonitorServiceData secondMonitorServiceData = orderMonitorData.getMonitorServicesData().get(1);
		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		orderMonitorScreen.clickTech();
		selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selecTechnician(secondMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		orderMonitorScreen.clickStartService();
		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		orderMonitorScreen.setCompletedServiceStatus();
		Assert.assertEquals(orderMonitorScreen.getPanelStatus(secondMonitorServiceData.getMonitorService()), secondMonitorServiceData.getMonitorServiceStatus());
		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		orderMonitorScreen.clickTech();
		orderMonitorScreen.clickServiceDetailsDoneButton();

		orderMonitorScreen.clickBackButton();

		teamWorkOrdersScreen.clickHomeButton();
		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatItIsNotPossibleToAssignTechWhenWOIsOnHold(String rowID,
																		  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String defaultLocationValue = "Test Location ZZZ";
		final String workOrderMonitorStatus = "On Hold";
		final String statusReason = "On Hold new reason";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.selectLocation(defaultLocationValue);
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getMoneyServices())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.switchToTeamView();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defaultLocationValue);
		teamWorkOrdersScreen.clickSearchSaveButton();

		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();

		orderMonitorScreen.changeStatusForWorkOrder(workOrderMonitorStatus, statusReason);
		orderMonitorScreen = new RegularOrderMonitorScreen();
		orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
		orderMonitorScreen.clickTech();

		orderMonitorScreen.clickServiceDetailsCancelButton();
		teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();
		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatTechSplitAssignedFormVehicleScreenIsSetToServicesUnderList(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		}

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularVehicleInfoScreenSteps.clickTech();
		for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenSteps.selectServiceTechnician(technician);
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			selectedServicesScreen.openSelectedServiceDetails(serviceData.getServiceName());
			RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
			for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
				RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(technician);
			RegularServiceDetailsScreenSteps.saveServiceDetails();
			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyPriceMatrixItemDoesntHaveAdditionalServicesItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
																																 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		RegularPriceMatrixScreenSteps.savePriceMatrix();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
		RegularVehicleInfoScreenSteps.clickTech();
		RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
		RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
				workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianPercentageValue());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();
		RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
		RegularNavigationSteps.navigateToServicesScreen();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(),
				PricesCalculations.getPriceRepresentation(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice()));

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyPriceMatrixItemHasMoneyAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
																															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
		RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
		RegularVehiclePartsScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
		RegularSelectedServiceDetailsScreen regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
		Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
		regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

		vehiclePartScreen.selectDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		Assert.assertEquals(vehiclePartScreen.getPriceMatrixTotalPriceValue(), vehiclePartData.getVehiclePartTotalPrice());
		regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPercentageValue());
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
		Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
		regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyPriceMatrixItemHasPercentageAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmountPlusAdditionalPercentage(String rowID,
																																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
		RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
		RegularVehiclePartsScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
		RegularSelectedServiceDetailsScreen regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()), "" +
				vehiclePartData.getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
		Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
		regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

		vehiclePartScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertEquals(vehiclePartScreen.getPriceMatrixTotalPriceValue(), vehiclePartData.getVehiclePartTotalPrice());
		regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPercentageValue());
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
		Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
		regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyIfServiceHasDefaultTechnicianAndItsAmountIs0ThenDefaultTechnicianShouldBeAssignedToTheService_NotTechnicianSplitAtWorkOrderLevel(String rowID,
																																							 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();
		
		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularServiceDetailsScreenSteps.saveServiceDetails();

		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleInfoScreenSteps.clickTech();
		RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician());
		RegularServiceDetailsScreenSteps.cancelServiceDetails();
		RegularServiceDetailsScreenSteps.cancelServiceDetails();

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatCorrectTechSplitAmountIsShownForMatrixServiceWhenChangePriceTo0(String rowID,
																								String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String serviceZeroPrice = "0";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();


		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
		RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
		RegularVehiclePartsScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.clickSave();
		RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
		vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());

		vehiclePartScreen.setPrice(serviceZeroPrice);
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_DEFAULT_TECH_SPLIT_WILL_BE_ASSIGNED_IF_SET_ZERO_AMAUNT);

		vehiclePartScreen.clickOnTechnicians();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_VEHICLE_PART);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()), PricesCalculations.getPriceRepresentation(serviceZeroPrice));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();

		RegularWorkOrdersSteps.cancelCreatingWorkOrder();
		RegularNavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWOVerifyThatTechSplitAmountIsShownCorrectUnderMonitorForServiceWithAdjustments(String rowID,
																								   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		RegularServiceDetailsScreenSteps.selectServiceAdjustment(workOrderData.getServiceData().getServiceAdjustment());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularNavigationSteps.navigateBackScreen();
		RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		Assert.assertEquals(orderMonitorScreen.getMonitorServiceAmauntValue(workOrderData.getServiceData()), workOrderData.getServiceData().getServicePrice2());
		orderMonitorScreen.selectPanel(workOrderData.getServiceData());
		Assert.assertEquals(orderMonitorScreen.getServiceDetailsPriceValue(), BackOfficeUtils.getFormattedServicePriceValue(BackOfficeUtils.getServicePriceValue(workOrderData.getServiceData().getServicePrice())));
		orderMonitorScreen.clickTech();
		RegularSelectedServiceDetailsScreen serviceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertTrue(serviceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(serviceDetailsScreen.getTechnicianPrice(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
				workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertEquals(serviceDetailsScreen.getCustomTechnicianPercentage(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
				workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPercentageValue());

		serviceDetailsScreen.cancelSelectedServiceDetails();
		orderMonitorScreen.clickServiceDetailsDoneButton();
		orderMonitorScreen.clickBackButton();

		teamWorkOrdersScreen.clickHomeButton();
		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatRequiredServicesHasCorrectTech(String rowID,
															 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_REQUIRED_SERVICES_ALL);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		RegularSelectedServiceBundleScreen serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.clickCancel();
		}
		serviceBundleScreen.clickSaveButton();
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleInfoScreenSteps.clickTech();

		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();

		selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
				RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
			RegularServiceDetailsScreenSteps.cancelServiceDetails();
		}
		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen.openSelectedServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		selectedServicesScreen.clickOnSelectedService(workOrderData.getMatrixServiceData().getMatrixServiceName());
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selectPriceMatrices(workOrderData.getMatrixServiceData().getHailMatrixName());
		RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		RegularVehiclePartsScreenSteps.saveVehiclePart();
		selectedServicesScreen.clickSave();
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			String techString = "";
			for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
				techString = techString + ", " + serviceTechnician.getTechnicianFullName();
			techString = techString.replaceFirst(",", "").trim();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
			selectedServiceDetailsScreen.clickCancel();
		}
		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen.waitSelectedServicesScreenLoaded();
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularNavigationSteps.navigateBackScreen();
		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatRequiredBundleItemsHasCorrectDefTech(String rowID,
																   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		RegularSelectedServiceBundleScreen serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		}
		serviceBundleScreen.clickCancel();

		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleInfoScreenSteps.clickTech();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
		//selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			if (serviceData.getServiceNewTechnicians() != null) {
				for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
					RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
			} else {
				RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
			}

			if (serviceData.getVehiclePart() != null)
				RegularServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());

			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}

		serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen.waitSelectedServicesScreenLoaded();
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		RegularNavigationSteps.navigateToServicesScreen();

		RegularServicesScreenSteps.switchToSelectedServices();
		selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			if (serviceData.getServiceNewTechnicians() != null) {
				for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
					RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
			} else {
				RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
			}
			RegularServiceDetailsScreenSteps.cancelServiceDetails();
		}

		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen.waitSelectedServicesScreenLoaded();
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularNavigationSteps.navigateBackScreen();
		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatRequiredBundleItemsAndServiceWithExpensesAnd0PriceHasCorrectDefTechAfterEditWO(String rowID,
																											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();

		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		RegularSelectedServiceBundleScreen serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		}
		serviceBundleScreen.clickCancel();
		RegularNavigationSteps.navigateToVehicleInfoScreen();
		RegularVehicleInfoScreenSteps.clickTech();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);

		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();

		selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			if (serviceData.getServiceNewTechnicians() != null) {
				for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
					RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
			} else {
				RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
			}

			if (serviceData.getVehiclePart() != null)
				RegularServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());

			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}

		serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		serviceBundleScreen.clickSaveButton();

		selectedServicesScreen.switchToAvailableServicesTab();
		servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		RegularServiceDetailsScreenSteps.saveServiceDetails();

		servicesScreen.switchToSelectedServicesTab();
		selectedServicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		selectedServicesScreen.waitSelectedServicesScreenLoaded();
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
		vehicleScreen.waitVehicleScreenLoaded();
		RegularNavigationSteps.navigateToServicesScreen();

		RegularServicesScreenSteps.switchToSelectedServices();
		selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			if (serviceData.getServiceNewTechnicians() != null) {
				for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
					RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
			} else {
				RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
			}
			RegularServiceDetailsScreenSteps.cancelServiceDetails();
		}
		serviceBundleScreen.clickSaveButton();
		RegularServicesScreenSteps.switchToSelectedServices();
		selectedServicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		selectedServicesScreen.waitSelectedServicesScreenLoaded();
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularNavigationSteps.navigateBackScreen();
		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatSelectedServicesHaveCorrectTechSplitWhenChangeIsDuringCreatingWO(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_ALL_SERVICES);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularVehicleInfoScreenSteps.clickTech();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());

		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
			for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
				RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
			RegularServiceDetailsScreenSteps.cancelServiceDetails();
			RegularServiceDetailsScreenSteps.cancelServiceDetails();
		}
		RegularSelectedServicesSteps.switchToAvailableServices();
		RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		RegularVehiclePartsScreenSteps.verifyVehiclePartTechniciansValue(workOrderData.getVehicleInfoData().getNewTechnicians());
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.clickDiscaunt(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
		RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
		RegularServiceDetailsScreenSteps.cancelServiceDetails();
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		vehiclePartScreen.setTime(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTime());
		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();

		servicesScreen.selectService(workOrderData.getBundleService().getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
		RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
		RegularServiceDetailsScreenSteps.cancelServiceDetails();
		selectedServiceBundleScreen.openBundleInfo(workOrderData.getBundleService().getService().getServiceName());
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
		RegularServiceDetailsScreenSteps.cancelServiceDetails();
		selectedServiceBundleScreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
		selectedServiceBundleScreen.clickSaveButton();
		RegularServicesScreenSteps.waitServicesScreenLoad();
		RegularServicesScreenSteps.switchToSelectedServices();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(workOrderData.getBundleService().getBundleServiceName()));
		RegularInspectionsSteps.saveInspectionAsFinal();
		selectedServicesScreen.clickSave();
		RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
		RegularNavigationSteps.navigateBackScreen();
		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfServicePriceIs0AndHasDefTechAssignToServiceDefTech(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularVehicleInfoScreenSteps.clickTech();
		RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician());
		RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(workOrderData.getServiceData().getServiceDefaultTechnician(),
				workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPriceValue());
		RegularServiceDetailsScreenSteps.cancelServiceDetails();
		RegularServiceDetailsScreenSteps.cancelServiceDetails();

		servicesScreen.waitServicesScreenLoaded();
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();

		RegularNavigationSteps.navigateBackScreen();
		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyIfServicePriceIs0AssignWOSplitToServiceInCaseNoDefTech(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		
		RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularVehicleInfoScreenSteps.clickTech();
		RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenValidations.verifyServicePriceValue(workOrderData.getServiceData().getServicePrice());
		RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getNewTechnician());
		RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(workOrderData.getVehicleInfoData().getNewTechnician(),
				workOrderData.getServiceData().getServicePrice());
		RegularServiceDetailsScreenSteps.cancelServiceDetails();
		RegularServiceDetailsScreenSteps.cancelServiceDetails();

		servicesScreen.waitServicesScreenLoaded();
		RegularWorkOrdersSteps.cancelCreatingWorkOrder();

		RegularNavigationSteps.navigateBackScreen();
		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatItIsNotPossibleToChangeStatusForServiceOrPhaseWhenCheckOutRequired(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		RegularPriceMatrixScreenSteps.savePriceMatrix();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.switchToTeamView();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
		orderMonitorScreen.clickServiceStatusCell();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		orderMonitorScreen.clickServiceDetailsCancelButton();

		orderMonitorScreen.clickStartPhase();
		orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
		orderMonitorScreen.clickServiceStatusCell();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		orderMonitorScreen.clickServiceDetailsCancelButton();

		orderMonitorScreen.clickStartPhaseCheckOutButton();
		orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
		orderMonitorScreen.clickServiceStatusCell();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		orderMonitorScreen.clickServiceDetailsCancelButton();

		orderMonitorScreen.changeStatusForStartPhase(OrderMonitorPhases.COMPLETED);
		Assert.assertEquals(orderMonitorScreen.getPanelStatus(workOrderData.getMatrixServiceData().getMatrixServiceName()),
				OrderMonitorPhases.COMPLETED.getrderMonitorPhaseName());
		orderMonitorScreen.clickBackButton();
		RegularNavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatWhenOptionPhaseEnforcementIsOFFAndStartServiceRequiredItIsPossibleToStartServiceFromInactivePhase(String rowID,
																																String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		RegularPriceMatrixScreenSteps.savePriceMatrix();
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.switchToTeamView();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
		Assert.assertTrue(orderMonitorScreen.isStartServiceButtonPresent());
		orderMonitorScreen.clickServiceDetailsDoneButton();

		Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonExists());
		orderMonitorScreen.clickStartPhaseButton();
		Assert.assertFalse(orderMonitorScreen.isStartPhaseButtonExists());
		orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatTechWhoIsNotAssignedToOrderServiceCannotStartOrder(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DELAY_START);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null)
				RegularServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
			RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
			RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
			RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
			RegularServiceDetailsScreenSteps.saveServiceDetails();
			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.switchToTeamView();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();

		orderMonitorScreen.clickStartOrderButton();
		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
		orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyThatIsItImpossibleToChangeStatusForServiceOrPhaseWhenOrderIsNotStarted(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.swtchToWholesaleMode();
		customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DELAY_START);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String workOrderNumber = vehicleScreen.getWorkOrderNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null)
				RegularServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
			RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
			RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
			RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
			RegularServiceDetailsScreenSteps.saveServiceDetails();
			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		RegularWorkOrdersSteps.saveWorkOrder();

		RegularMyWorkOrdersSteps.switchToTeamView();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(workOrderNumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertEquals(orderMonitorScreen.getPanelStatus(serviceData),
					OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());
		orderMonitorScreen.selectPanel(workOrderData.getServicesList().get(workOrderData.getServicesList().size() - 1));
		orderMonitorScreen.clickServiceStatusCell();

		AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_MUST_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
		orderMonitorScreen.clickServiceDetailsCancelButton();
		orderMonitorScreen = new RegularOrderMonitorScreen();
		orderMonitorScreen.clickOrderStartDateButton();
		LocalDate date = LocalDate.now();
		orderMonitorScreen.setOrderStartYearValue(date.getYear() + 1);
		Assert.assertEquals(orderMonitorScreen.getOrderStartYearValue(), Integer.toString(date.getYear()));
		orderMonitorScreen.setOrderStartYearValue(date.getYear() - 1);
		Assert.assertEquals(orderMonitorScreen.getOrderStartYearValue(), Integer.toString(date.getYear() - 1));
		orderMonitorScreen.closeSelectorderDatePicker();
		orderMonitorScreen.waitOrderMonitorScreenLoaded();
		orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();

		homeScreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testUserCanChangeApprovedStatusOfPercentageServiceToDeclinedOnlyUsingDeclineAllOption(String rowID,
																									  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		final String declineReason = "Decline 2";

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularHomeScreenSteps.navigateToMyInspectionsScreen();
		RegularMyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSPECTION_ALL_SERVICES);
		RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
		final String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.selectService(inspectionData.getPercentageServiceData().getServiceName());
		RegularInspectionsSteps.saveInspectionAsFinal();

		RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
		myInspectionsScreen.selectInspectionForAction(inspectionNumber);
		myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
		approveInspectionsScreen.selectInspection(inspectionNumber);

		final String serviceName = inspectionData.getPercentageServiceData().getServiceName();
		Assert.assertTrue(approveInspectionsScreen.isServiceApproved(serviceName));
		Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
		Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

		approveInspectionsScreen.selectInspectionServiceToSkip(serviceName);
		Assert.assertTrue(approveInspectionsScreen.isServiceApproved(serviceName));
		Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
		Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

		approveInspectionsScreen.selectInspectionServiceToDecline(serviceName);
		Assert.assertTrue(approveInspectionsScreen.isServiceApproved(serviceName));
		Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
		Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

		approveInspectionsScreen.clickSkipAllServicesButton();
		Assert.assertFalse(approveInspectionsScreen.isServiceApproved(serviceName));
		Assert.assertTrue(approveInspectionsScreen.isServiceSkipped(serviceName));
		Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

		approveInspectionsScreen.clickDeclineAllServicesButton();
		Assert.assertFalse(approveInspectionsScreen.isServiceApproved(serviceName));
		Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
		Assert.assertTrue(approveInspectionsScreen.isServiceDeclibed(serviceName));

		approveInspectionsScreen.clickSaveButton();
		approveInspectionsScreen.selectStatusReason(declineReason);
		approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
		approveInspectionsScreen.clickDoneStatusReasonButton();
		myInspectionsScreen.clickHomeButton();
	}

}
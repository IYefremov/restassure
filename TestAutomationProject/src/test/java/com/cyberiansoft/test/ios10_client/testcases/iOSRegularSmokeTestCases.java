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
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.enums.ServiceRequestStatus;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.SelectedServicesScreenSteps;
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
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.ordermonitorphases.OrderMonitorPhases;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import io.appium.java_client.MobileBy;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class iOSRegularSmokeTestCases extends ReconProBaseTestCase {

	private RegularHomeScreen homescreen;

	private final String firstname = "supermy12";
	private final String firstnamenew = "supernewmy12";
	private final String lastname = "super";
	private final String companyname = "supercompany";
	private final String street = "First streer";
	private final String city = "New York";
	private final String zip = "79031";
	private final String phone = "723-1234567";
	private final String mail = "test@cyberiansoft.com";
	private final String state = "Alberta";
	private final String country = "Canada";

	@BeforeClass
	public void setUpSuite() {
		JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getGeneralSuiteTestCasesDataPath();
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Test_Automation_Regular3",
				envType);

		RegularMainScreen mainscr = new RegularMainScreen();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularSettingsScreen settingscreen =  homescreen.clickSettingsButton();
		settingscreen.setShowAvailableSelectedServicesOn();
		homescreen = settingscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testUpdateDatabase(String rowID,
																	String description, JSONObject testData) {
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		RegularMainScreen mainscr = new RegularMainScreen();
		mainscr.updateDatabase();
		RegularHomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen.clickStatusButton();
		homescreen.updateDatabase();
		mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testUpdateVIN(String rowID,
								   String description, JSONObject testData) {

		RegularMainScreen mainscr = homescreen.clickLogoutButton();
		mainscr.updateVIN();
		RegularHomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen.clickStatusButton();
		homescreen.updateVIN();
		homescreen.clickHomeButton();
	}

	//Test Case 8441:Add Retail Customer in regular build
	@Test(testName = "Test Case 8441:Add Retail Customer in regular build", description = "Create retail customer")
	public void testCreateRetailCustomer() {

		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen();
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		RegularAddCustomerScreen addcustomerscreen = customersscreen.clickAddCustomersButton();

		addcustomerscreen.addCustomer(firstname, lastname, companyname, street,
				city, state, zip, country, phone, mail);
		addcustomerscreen.clickSaveBtn();
		Assert.assertTrue(customersscreen.checkCustomerExists(firstname));

		customersscreen.clickHomeButton();
		//homescreen.clickLogoutButton();
	}

	//Test Case 8439:Edit Customer
	@Test(testName = "Test Case 8439:Edit Customer ", description = "Edit retail customer")
	public void testEditRetailCustomer() {
		final String lastname = "superedited";
		final String companyname = "supercompanyedited";
		final String street = "Second streer";
		final String city = "New York";
		final String zip = "79035";
		final String phone = "723-1234576";
		final String mail = "test123@cyberiansoft.com";

		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen();
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();

		RegularAddCustomerScreen addcustomerscreen = customersscreen.selectCustomerToEdit(firstname);

		addcustomerscreen.editCustomer(firstnamenew, lastname, companyname,
				street, city, city, zip, zip, phone, mail);
		addcustomerscreen.clickSaveBtn();
		Assert.assertTrue(customersscreen.checkCustomerExists(firstnamenew));
		customersscreen.clickHomeButton();
		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
	}

	// Test Case 8460: Delete Customer 
	@Test(testName = "Test Case 8460: Delete Customer", description = "Delete retail customer")
	public void testDeleteRetailCustomer() {

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companyWebPage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companyWebPage.clickClientsLink();

		clientspage.deleteUserViaSearch(firstnamenew);

		DriverBuilder.getInstance().getDriver().quit();

		RegularMainScreen mainscreen = new RegularMainScreen();
		mainscreen.updateDatabase();
		RegularHomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		Assert.assertFalse(customersscreen.checkCustomerExists(firstnamenew));
		customersscreen.clickHomeButton();
		//homescreen.clickLogoutButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testEditRetailInspectionNotes(String rowID,
								   String description, JSONObject testData) {
		final String _notes1 = "Test\nTest 2";

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersScreen = homescreen.clickCustomersButton();
		customersScreen.swtchToRetailMode();
		customersScreen.clickHomeButton();
		RegularMyInspectionsScreen myInspectionsScreen = homescreen.clickMyInspectionsButton();
		myInspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
		RegularVehicleScreen vehicleScreen = myInspectionsScreen.selectInspectionType(InspectionsTypes.DEFAULT);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		final String inspectionNumber = vehicleScreen.getInspectionNumber();
		vehicleScreen.saveWizard();

		myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
		RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
		RegularVisualInteriorScreen visualInteriorScreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
		RegularNotesScreen notesscreen = visualInteriorScreen.clickNotesButton();
		notesscreen.setNotes(_notes1);
		notesscreen.addQuickNotes();
		notesscreen.clickSaveButton();
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
		visualInteriorScreen.clickNotesButton();
		Assert.assertEquals(notesscreen.getNotesAndQuickNotes(), _notes1 + "\n" + notesscreen.quicknotesvalue);
		notesscreen.clickSaveButton();
		visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.saveWizard();
		myInspectionsScreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testApproveInspectionOnDevice(String rowID,
											  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		//customersscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
		customersscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		final String inspNumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForApprove(inspNumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspNumber);
		approveinspscreen.clickApproveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inspNumber));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testArchiveAndUnArchiveTheInspection(String rowID,
											  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		final String inspNumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.saveWizard();
		myinspectionsscreen.clickHomeButton();

		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.archiveInspection(inspNumber,
				inspectionData.getArchiveReason());
		Assert.assertTrue(myinspectionsscreen.checkInspectionDoesntExists(inspNumber));
		myinspectionsscreen.clickHomeButton();

	}


	@Test(testName = "Test Case 8430:Create work order with type is assigned to a specific client", description = "Create work order with type is assigned to a specific client ")
	public void testCreateWorkOrderWithTypeIsAssignedToASpecificClient() {
		final String VIN = "ZWERTYASDFDDXZBVB";
		final String _po = "1111 2222";
		final String summ = "71.40";

		final String license = "Iphone_Test_Spec_Client";

		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		LicensesScreen licensesscreen = mainscreen.clickLicenses();
		licensesscreen.clickAddLicenseButtonAndAcceptAlert();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companyWebPage = backofficeheader.clickCompanyLink();
		ActiveDevicesWebPage devicespage = companyWebPage.clickManageDevicesLink();

		devicespage.setSearchCriteriaByName(license);
		String regCode = devicespage.getFirstRegCodeInTable();

		DriverBuilder.getInstance().getDriver().quit();
		RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen();
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("Dev Environment");
		//LoginScreen loginscreen = new LoginScreen();
		loginscreen.registeriOSDevice(regCode);
		mainscreen.userLogin(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularSettingsScreen settingscreen =  homescreen.clickSettingsButton();
		settingscreen.setShowAvailableSelectedServicesOn();
		homescreen = settingscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isDefaultServiceIsSelected());
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE));
		RegularOrderSummaryScreen ordersummaryscreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(summ));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		ordersummaryscreen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		vehiclescreen.setVIN(VIN);

		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(summ));
		//ordersummaryscreen.checkApproveAndCreateInvoice();
		//ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		ordersummaryscreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
		invoiceinfoscreen.clickSaveEmptyPO();
		invoiceinfoscreen.setPO(_po);
		//ordersummaryscreen.clickSaveButton();
		invoiceinfoscreen.clickSaveAsDraft();

		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateWorkOrderWithTeamSharingOption(String rowID,
													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrdersData().get(0);

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());

		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), workOrderData.getMoneyServiceData().getServicePrice());
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), workOrderData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedservicescreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================

		servicesscreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), workOrderData.getPercentageServiceData().getServicePrice());
		selectedservicescreen.clickAdjustments();
		Assert.assertEquals(selectedservicescreen.getAdjustmentValue(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
				workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
		selectedservicescreen.selectAdjustment(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================
		servicesscreen.openCustomServiceDetails(workOrderData.getBundleService().getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : workOrderData.getBundleService().getServices()) {
			if (serviceData.isSelected()) {
				Assert.assertTrue(selectedservicebundlescreen.checkBundleIsSelected(serviceData.getServiceName()));
				selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
				selectedservicescreen.setServiceQuantityValue(serviceData.getServiceQuantity());
			}
			else {
				Assert.assertTrue(selectedservicebundlescreen.checkBundleIsNotSelected(serviceData.getServiceName()));
				selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
			}
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();


		// =====================================
		for (ServiceData serviceData : workOrderData.getPercentageServices()) {
			selectedservicescreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedservicescreen.saveSelectedServiceDetails();
		}

		// =====================================
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesscreen.selectSubService(matrixServiceData.getMatrixServiceName());
		RegularPriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartScreen.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		Assert.assertEquals(vehiclePartScreen.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
			vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
			selectedservicescreen = new RegularSelectedServiceDetailsScreen();
			selectedservicescreen.saveSelectedServiceDetails();
		}
		vehiclePartScreen.saveVehiclePart();

		pricematrix.clickSave();
		servicesscreen.switchToSelectedServicesTab();
		SelectedServicesScreenSteps.verifyServicesAreSelected(workOrderData.getSelectedServices());

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();


		WorkOrderData workOrderDataCopiedServices = testCaseData.getWorkOrdersData().get(1);
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.selectWorkOrder(wonum);
		myworkordersscreen.selectCopyServices();
		customersscreen.selectCustomer(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
		vehiclescreen.setVIN(workOrderDataCopiedServices.getVehicleInfoData().getVINNumber());
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.switchToSelectedServicesTab();
		SelectedServicesScreenSteps.verifyServicesAreSelected(workOrderDataCopiedServices.getSelectedServices());
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.waitWorkOrderSummaryScreenLoad();
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), workOrderDataCopiedServices.getWorkOrderPrice());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testApproveInspectionsOnDeviceViaAction(String rowID,
														 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inspectionsID = new ArrayList<>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			myinspectionsscreen.clickAddInspectionButton();
			customersscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
			vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
			vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
			vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
			inspectionsID.add(vehiclescreen.getInspectionNumber());
			vehiclescreen.saveWizard();
		}

		myinspectionsscreen.clickActionButton();
		for (String inspectionNumber : inspectionsID) {
			myinspectionsscreen.selectInspectionForAction(inspectionNumber);
		}

		myinspectionsscreen.clickApproveInspections();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		for (String inspectionNumber : inspectionsID) {
			approveinspscreen.selectInspection(inspectionNumber);
			approveinspscreen.clickApproveButton();
		}
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();

		myinspectionsscreen = new RegularMyInspectionsScreen();
		for (String inspectionNumber : inspectionsID) {
			Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inspectionNumber));
		}
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testArchiveInspectionsOnDeviceViaAction(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inspectionsID = new ArrayList<>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			myinspectionsscreen.clickAddInspectionButton();
			customersscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
			vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
			vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
			vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
			inspectionsID.add(vehiclescreen.getInspectionNumber());
			vehiclescreen.saveWizard();
		}

		myinspectionsscreen.clickActionButton();
		for (String inspectionNumber : inspectionsID) {
			myinspectionsscreen.selectInspection(inspectionNumber);
		}
		myinspectionsscreen.clickArchiveInspections();
		myinspectionsscreen.selectReasonToArchive(testCaseData.getArchiveReason());
		for (String inspectionNumber : inspectionsID) {
			Assert.assertTrue(myinspectionsscreen.checkInspectionDoesntExists(inspectionNumber));
		}
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testApproveInspectionOnBackOfficeFullInspectionApproval(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		String inpectionnumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

		vehiclescreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationsWebPage.clickInspectionsLink();

		inspectionspage.approveInspectionByNumber(inpectionnumber);

		DriverBuilder.getInstance().getDriver().quit();

		mainscreen.updateDatabase();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inpectionnumber));

		myinspectionsscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testApproveInspectionOnBackOfficeLinebylineApproval(String rowID,
																		String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_LA_DA_INSPTYPE);
		String inpectionnumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehiclescreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationsWebPage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		inspectionspage.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());
		inspectionspage.approveInspectionLinebylineApprovalByNumber(
				inpectionnumber, inspectionData.getServicesList());

		DriverBuilder.getInstance().getDriver().quit();

		mainscreen.updateDatabase();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inpectionnumber));

		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice(String rowID,
																	String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		final String teamName= "Default team";
		final String serviceName= "Test Company (Universal Client)";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);

		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		final VehicleInfoData vehicleInfoData = serviceRequestData.getVihicleInfo();
		vehiclescreen.setVIN(vehicleInfoData.getVINNumber());
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.THE_VIN_IS_INCORRECT);

		vehiclescreen.setMakeAndModel(vehicleInfoData.getVehicleMake(), vehicleInfoData.getVehicleModel());
		vehiclescreen.setColor(vehicleInfoData.getVehicleColor());
		vehiclescreen.setYear(vehicleInfoData.getVehicleYear());

		vehiclescreen.setMileage(vehicleInfoData.getMileage());
		vehiclescreen.setFuelTankLevel(vehicleInfoData.getFuelTankLevel());
		vehiclescreen.setType(vehicleInfoData.getVehicleType());
		vehiclescreen.setStock(vehicleInfoData.getVehicleStock());
		vehiclescreen.setRO(vehicleInfoData.getRoNumber());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
			if (serviceData.getServiceQuantity()!= null) {
				RegularSelectedServiceDetailsScreen selectedservicedetailsscreen =  servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
				selectedservicedetailsscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			} else
				servicesscreen.selectService(serviceData.getServiceName());
		}
		RegularClaimScreen claimscreen = servicesscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(serviceRequestData.getInsuranceCompany().getInsuranceCompanyName());
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();

		questionsscreen.drawRegularSignature();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);

		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		final String serviceRequestNumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.ON_HOLD.getValue());
		Assert.assertTrue(servicerequestsscreen.getServiceRequestClient(serviceRequestNumber).contains(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestDetails(serviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
		servicerequestsscreen.clickHomeButton();
		Helpers.waitABit(10*1000);


		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();

		servicerequestslistpage.verifySearchFieldsAreVisible();

		servicerequestslistpage.selectSearchTeam(teamName);
		servicerequestslistpage.selectSearchTechnician("Employee Simple 20%");
		servicerequestslistpage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		servicerequestslistpage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		servicerequestslistpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());

		servicerequestslistpage.setSearchFreeText(serviceRequestNumber);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.verifySearchResultsByServiceName(serviceName);
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getVINValueForSelectedServiceRequest(), serviceRequestData.getVihicleInfo().getVINNumber());
		Assert.assertEquals(servicerequestslistpage.getCustomerValueForSelectedServiceRequest(), serviceName);
		Assert.assertEquals(servicerequestslistpage.getEmployeeValueForSelectedServiceRequest(), "Employee Simple 20% (Default team)");
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("Bundle1_Disc_Ex $150.00 (1.00)"));
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("Quest_Req_Serv $10.00 (1.00)"));
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("Wheel $70.00 (3.00)"));
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionFromServiceRequest(String rowID,
																						 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();

		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_SR_INSPTYPE);
		String inspnumber = vehiclescreen.getInspectionNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
			Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2()));
			if (serviceData.getQuestionData()!= null) {
				RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				selectedservicedetailsscreen.answerQuestion2(serviceData.getQuestionData());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			}
		}

		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(bundleServiceData.getBundleServiceName(), bundleServiceData.getBundleServiceAmount()));
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				selectedservicedetailsscreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			} else
				selectedservicedetailsscreen.selectBundle(serviceData.getServiceName());
		}
		selectedservicedetailsscreen.saveSelectedServiceDetails();

		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		homescreen = servicerequestsscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspnumber));
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspnumber), inspectionData.getInspectionPrice());
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);

		myinspectionsscreen.clickApproveInspections();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickApproveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateWOFromServiceRequest(String rowID,
													   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		homescreen = customersscreen.clickHomeButton();

		//test case
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(servicerequestsscreen.getFirstServiceRequestNumber());
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_SR);
		String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice2()));
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getBundleService().getBundleServiceName(), workOrderData.getBundleService().getBundleServiceAmount()));

		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(workOrderData.getBundleService().getBundleServiceName());
		selectedservicedetailsscreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();

		selectedServicesScreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.THE_VIN_IS_INVALID_AND_SAVE_WORKORDER);
		servicerequestsscreen= new RegularServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.woExists(wonumber);
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionFromInvoiceWithTwoWOs(String rowID, String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		String wonumber1 = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehicleParts(workOrderData.getMoneyServiceData().getVehicleParts());
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), workOrderData.getWorkOrderPrice());

		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehiclescreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
		Assert.assertEquals(vehiclescreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());
		Assert.assertEquals(vehiclescreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.waitWorkOrderSummaryScreenLoad();
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.waitWorkOrderSummaryScreenLoad();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		ordersummaryscreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceinfoscreen.addWorkOrder(wonumber1);
		Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInvoiceFromWOInMyWOsList(String rowID,
														  String description, JSONObject testData) {
		final String employee = "Employee Simple 20%";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonumber1 = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		selectedservicescreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		selectedservicescreen.clickAdjustments();
		selectedservicescreen.selectAdjustment(workOrderData.getPercentageServiceData().
				getServiceAdjustment().getAdjustmentData().getAdjustmentName());
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), workOrderData.getPercentageServiceData().getServicePrice());

		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				selectedservicedetailsscreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			} else
				selectedservicedetailsscreen.selectBundle(serviceData.getServiceName());
		}
		selectedservicescreen.saveSelectedServiceDetails();

		for (ServiceData serviceData : workOrderData.getPercentageServices())
			servicesscreen.selectSubService(serviceData.getServiceName());
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesscreen.openCustomServiceDetails(matrixServiceData.getMatrixServiceName());
		RegularPriceMatrixScreen pricematrix = selectedservicescreen.selectMatrics(matrixServiceData.getHailMatrixName());
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(employee));
		Assert.assertEquals(vehiclePartScreen.getPrice(), vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices())
			vehiclePartScreen.selectDiscaunt(serviceData.getServiceName());
		vehiclePartScreen.saveVehiclePart();

		pricematrix.clickSave();
		servicesscreen = new RegularServicesScreen();

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.clickSave();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_PO_IS_REQUIRED);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();

		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testDontAlowToSelectBilleAandNotBilledOrdersTogetherInMultiSelectionMode(String rowID,
												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> workOrderIDs = new ArrayList<>();
		final String billingFilterValue = "All";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
			vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
			workOrderIDs.add(vehiclescreen.getWorkOrderNumber());
			RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			for (ServiceData serviceData : workOrderData.getServicesList())
				servicesscreen.selectService(serviceData.getServiceName());
			RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			ordersummaryscreen.saveWizard();
		}

		for (String workOrderID : workOrderIDs)
			myworkordersscreen.approveWorkOrder(workOrderID, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.clickCreateInvoiceIconForWO(workOrderIDs.get(0));
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceinfoscreen.clickSaveAsDraft();

		for (String workOrderID : workOrderIDs) {
			myworkordersscreen.clickFilterButton();
			myworkordersscreen.setFilterBilling(billingFilterValue);
			myworkordersscreen.clickSaveFilter();
			myworkordersscreen.clickActionButton();
			myworkordersscreen.selectWorkOrderForAction(workOrderID);
			myworkordersscreen.clickDoneButton();
		}
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testDontAlowToChangeBilledOrders(String rowID,
																						 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String[] menuItemsToVerify = { "Edit" , "Notes", "Change\nstatus", "Delete", "Create\nInvoices" };
		final String billingFilterValue = "All";

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		homescreen = new RegularHomeScreen();
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonumber1 = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling(billingFilterValue);
		myworkordersscreen.clickSaveFilter();

		myworkordersscreen.selectWorkOrder(wonumber1);
		for (String menuItem : menuItemsToVerify) {
			Assert.assertFalse(myworkordersscreen.isMenuItemForSelectedWOExists(menuItem));
		}
		myworkordersscreen.clickDetailspopupMenu();
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickCancelWizard();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerForInvoice(String rowID,
												 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonumber1 = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Helpers.waitABit(60*1000);
		myinvoicesscreen.changeCustomerForInvoive(invoicenumber, iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new RegularInvoiceInfoScreen();
		Assert.assertEquals(invoiceinfoscreen.getInvoiceCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		invoiceinfoscreen.clickWO(wonumber1);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.cancelWizard();

		invoiceinfoscreen.cancelInvoice();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testBugWithCrashOnCopyVehicle(String rowID,
											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		VehicleInfoData vehicleInfoData = testCaseData.getWorkOrderData().getVehicleInfoData();
		final String vehicleinfo = vehicleInfoData.getVehicleColor() + ", " +
				vehicleInfoData.getVehicleMake() + ", " + vehicleInfoData.getVehicleModel();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		homescreen = new RegularHomeScreen();
		RegularCarHistoryScreen carhistoryscreen = homescreen.clickCarHistoryButton();

		RegularCarHistoryWOsAndInvoicesScreen regularCarHistoryWOsAndInvoicesScreen = carhistoryscreen.clickCarHistoryRowByVehicleInfo(vehicleinfo);
		regularCarHistoryWOsAndInvoicesScreen.clickCarHistoryMyWorkOrders();
		RegularMyWorkOrdersScreen myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.selectFirstOrder();
		myworkordersscreen.selectCopyVehicle();
		new RegularCustomersScreen();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.cancelWizard();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.clickBackButton();

		carhistoryscreen.clickBackButton();
		carhistoryscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCopyInspections(String rowID,
											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.FOR_COPY_INSP_INSPTYPE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspNumber = vehiclescreen.getInspectionNumber();
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularVisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR,  visualScreenData.getScreenName());
			visualinteriorscreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
			visualinteriorscreen.clickServicesToolbarButton();
			visualinteriorscreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
			Helpers.tapRegularCarImage();
			visualinteriorscreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
			if (visualScreenData.getScreenTotalPrice() != null)
				Assert.assertEquals(visualinteriorscreen.getTotalPrice(), visualScreenData.getScreenTotalPrice());
		}

		for (QuestionScreenData questionScreenData : inspectionData.getQuestionScreensData())
			RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), inspectionData.getMoneyServiceData().getServicePrice());
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), inspectionData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================
		servicesscreen.openCustomServiceDetails(inspectionData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), inspectionData.getPercentageServiceData().getServicePrice());
		selectedservicescreen.clickAdjustments();
		Assert.assertEquals(selectedservicescreen.getAdjustmentValue(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
				inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
		selectedservicescreen.selectAdjustment(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================
		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				selectedservicedetailsscreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			} else
				selectedservicedetailsscreen.selectBundle(serviceData.getServiceName());
		}
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================
		for (ServiceData serviceData : inspectionData.getPercentageServicesList())
			servicesscreen.selectService(serviceData.getServiceName());
		// =====================================
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		servicesscreen.selectSubService(matrixServiceData.getMatrixServiceName());
		RegularPriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartScreen.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		Assert.assertEquals(vehiclePartScreen.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
			vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
			selectedservicescreen = new RegularSelectedServiceDetailsScreen();
			selectedservicescreen.saveSelectedServiceDetails();
		}
		vehiclePartScreen.saveVehiclePart();

		pricematrix.clickSave();
		servicesscreen.switchToSelectedServicesTab();
		SelectedServicesScreenSteps.verifyServicesAreSelected(inspectionData.getSelectedServices());

		servicesscreen.saveWizard();

		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspection(inspNumber);
		myinspectionsscreen.clickCopyInspection();
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getMake(), inspectionData.getVehicleInfo().getVehicleMake());
		Assert.assertEquals(vehiclescreen.getModel(), inspectionData.getVehicleInfo().getVehicleModel());

		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularVisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, visualScreenData.getScreenName());
			visualinteriorscreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
		}

		RegularVisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, ScreenNamesConstants.FOLLOW_UP_REQUESTED);
		visualinteriorscreen.waitVisualScreenLoaded(ScreenNamesConstants.FOLLOW_UP_REQUESTED);
		SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		questionsscreen.swipeScreenUp();
		Assert.assertTrue(singlepageinspectionscreen.isSignaturePresent());
		questionsscreen = questionsscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, "BATTERY PERFORMANCE");
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		Assert.assertTrue(singlepageinspectionscreen.isAnswerPresent("Test Answer 1"));
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);

		servicesscreen.switchToSelectedServicesTab();
		SelectedServicesScreenSteps.verifyServicesAreSelected(inspectionData.getSelectedServices());

		servicesscreen.saveWizard();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspNumber), inspectionData.getInspectionPrice());
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionFromWO(String rowID,
									String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE) ;
		vehiclescreen.setVIN(testCaseData.getWorkOrderData().getVehicleInfoData().getVINNumber());

		String wonumber1 = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();

		//Test case
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber1), testCaseData.getWorkOrderData().getWorkOrderPrice());
		myworkordersscreen.selectWorkOrderNewInspection(wonumber1);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.verifyMakeModelyearValues(testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleMake(),
				testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleModel(), testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleYear());
		vehiclescreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInvoiceWithTwoWOsAndCopyVehicle(String rowID,
										   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonumber1 = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehicleParts(workOrderData.getMoneyServiceData().getVehicleParts());
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehiclescreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
		Assert.assertEquals(vehiclescreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());

		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.waitWorkOrderSummaryScreenLoad();
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		ordersummaryscreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceinfoscreen.addWorkOrder(wonumber1);
		final String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
		invoiceswebpage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoiceswebpage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		invoiceswebpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoiceswebpage.setSearchInvoiceNumber(invoicenum);
		invoiceswebpage.clickFindButton();
		Assert.assertTrue(invoiceswebpage.isInvoiceDisplayed(invoicenum));
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerOptionForInspection(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_CHANGE_INSPTYPE);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		vehiclescreen.saveWizard();
		myinspectionsscreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		BaseUtils.waitABit(1000*60);
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		RegularVisualInteriorScreen visualscreen = new RegularVisualInteriorScreen();
		visualscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		vehiclescreen = visualscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		Assert.assertEquals(vehiclescreen.getInspectionCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		vehiclescreen.saveWizard();

		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerOptionForInspectionAsChangeWholesaleToRetailAndViceVersa(String rowID,
													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_CHANGE_INSPTYPE);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspection(inspectionnumber);
		myinspectionsscreen.clickChangeCustomerpopupMenu();
		myinspectionsscreen.customersPopupSwitchToRetailMode();
		myinspectionsscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
		myinspectionsscreen.clickHomeButton();
		Helpers.waitABit(60*1000);
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		RegularVisualInteriorScreen visualscreen = new RegularVisualInteriorScreen();
		visualscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		vehiclescreen = visualscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		Assert.assertTrue(vehiclescreen.getInspectionCustomer().contains(iOSInternalProjectConstants.RETAIL_CUSTOMER));
		vehiclescreen.saveWizard();

		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerOptionForInspectionsBasedOnTypeWithPreselectedCompanies(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.TYPEWITHPRESELECTEDCOMPANIES_INSPTYPE);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimscreen.saveWizard();
		myinspectionsscreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		BaseUtils.waitABit(60*1000);
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		Assert.assertEquals(vehiclescreen.getInspectionCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		vehiclescreen.saveWizard();

		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerOptionForWorkOrder(String rowID,
													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(workOrderData.getMoneyServiceData().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

		ordersummaryscreen.saveWizard();
		myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancel();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerOptionForWOBasedOnTypeWithPreselectedCompanies(String rowID,
													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_WITH_PRESELECTED_CLIENTS);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(workOrderData.getMoneyServiceData().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
		ordersummaryscreen.saveWizard();

		Helpers.waitABit(45*1000);
		myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancel();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerOptionForWOAsChangeWholesaleToRetailAndViceVersa(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(workOrderData.getMoneyServiceData().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrder(wonumber);
		myworkordersscreen.clickChangeCustomerPopupMenu();
		myworkordersscreen.customersPopupSwitchToRetailMode();
		myworkordersscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);
		myworkordersscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertTrue(vehiclescreen.getWorkOrderCustomer().contains(iOSInternalProjectConstants.RETAIL_CUSTOMER));
		servicesscreen.clickCancel();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testBlockForTheSameVINIsONVerifyDuplicateVINMessageWhenCreate2WOWithOneVIN(String rowID,
																				   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.clickSave();
		ordersummaryscreen.waitForCustomWarningMessage(String.format(AlertsCaptions.ALERT_YOU_CANT_CREATE_WORK_ORDER_BECAUSE_VIN_EXISTS,
				WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON.getWorkOrderTypeName(), workOrderData.getVehicleInfoData().getVINNumber()), "Cancel");
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testBlockForTheSameServicesIsONVerifyDuplicateServicesMessageWhenCreate2WOWithOneService(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectSubService(serviceData.getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.waitWorkOrderSummaryScreenLoad();
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingCancel();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testEditOptionOfDuplicateServicesMessageForWO(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(workOrderData.getMoneyServiceData().getServiceName());
		servicesscreen.selectSubService(workOrderData.getServiceData().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingEdit();
		ordersummaryscreen.swipeScreenLeft();
		servicesscreen = ordersummaryscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedServicesScreen.removeSelectedService(workOrderData.getServiceData().getServiceName());
		Assert.assertFalse(selectedServicesScreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));
		selectedServicesScreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testOverrideOptionOfDuplicateServicesMessageForWO(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectSubService(serviceData.getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingOverride();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCancelOptionOfDuplicateServicesMessageForWO(String rowID,
																  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectSubService(serviceData.getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingCancel();
		Assert.assertFalse(myworkordersscreen.woExists(wonumber));

		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSavingInspectionsWithThreeMatrix(String rowID,
																String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.VITALY_TEST_INSPTYPE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setType(inspectionData.getVehicleInfo().getVehicleType());
		vehiclescreen.setPO(inspectionData.getVehicleInfo().getPoNumber());
		String inspnumber = vehiclescreen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimscreen.setClaim(inspectionData.getInsuranceCompanyData().getClaimNumber());
		claimscreen.setAccidentDate();

		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			RegularVisualInteriorScreen visualinteriorscreen = claimscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, visualScreenData.getScreenName());
			visualinteriorscreen.clickServicesToolbarButton();
			if (visualScreenData.getDamagesData() != null) {
				visualinteriorscreen.selectSubService(visualScreenData.getDamagesData().get(0).getDamageGroupName());
				RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
				RegularVisualInteriorScreen.tapInteriorWithCoords(150, 150);
				visualinteriorscreen.selectService(visualScreenData.getDamagesData().get(0).getDamageGroupName());
			} else {
				visualinteriorscreen.selectSubService(visualScreenData.getDamageData().getDamageGroupName());
				RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
				visualinteriorscreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
			}
			Assert.assertEquals(visualinteriorscreen.getSubTotalPrice(), visualScreenData.getScreenPrice());
			Assert.assertEquals(visualinteriorscreen.getTotalPrice(), visualScreenData.getScreenTotalPrice());
		}
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularPriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);
		}

		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}

		servicesscreen = servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(inspectionData.getServiceData().getServiceName());

		RegularVisualInteriorScreen visualinteriorscreen = servicesscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, inspectionData.getVisualScreenData().getScreenName());
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectSubService(inspectionData.getVisualScreenData().getDamageData().getDamageGroupName());
		Helpers.tapRegularCarImage();
		visualinteriorscreen.selectService(inspectionData.getVisualScreenData().getDamageData().getDamageGroupName());
		Assert.assertEquals(visualinteriorscreen.getSubTotalPrice(), inspectionData.getVisualScreenData().getScreenPrice());
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), inspectionData.getVisualScreenData().getScreenTotalPrice());
		vehiclescreen.saveWizard();

		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		vehiclescreen = new RegularVehicleScreen();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
				RegularVehiclePartsScreenSteps.verifyIfVehiclePartContainsPriceValue(vehiclePartData);
			}
		}
		servicesscreen.cancelWizard();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreen = new RegularVehicleScreen();
		String copiedinspnumber = vehiclescreen.getInspectionNumber();
		servicesscreen.saveWizard();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(copiedinspnumber));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testThatSelectedServicesOnSRAreCopiedToInspectionBasedOnSR(String rowID,
													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		homescreen = customersscreen.clickHomeButton();
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		customersscreen = new RegularCustomersScreen();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehiclescreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			if (serviceData.getServiceQuantity() != null) {
				RegularSelectedServiceDetailsScreen servicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
				servicedetailsscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				servicedetailsscreen.saveSelectedServiceDetails();
			}
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());

		RegularNavigationSteps.navigateToServicesScreen();
		servicesscreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
		String inspectnumber = vehiclescreen.getInspectionNumber();
		servicesscreen = servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2());

		selectedServicesScreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testThatAutoSavedWOIsCreatedCorrectly(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		Helpers.waitABit(30*1000);
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		RegularMainScreen mainscreen = new RegularMainScreen();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertTrue(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.selectContinueWorkOrder();
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderNumber(), wonumber);

		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		mainscreen = new RegularMainScreen();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertTrue(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.selectDiscardWorkOrder();
		Assert.assertFalse(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSRAddAppointmentToServiceRequest(String rowID,
													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		final String appointmentSubject = "SR-APP";
		final String appointmentAddress = "Maidan";
		final String appointmentCity = "Kiev";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);

		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();

		questionsscreen.drawRegularSignature();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
		RegularQuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		servicesscreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();

		servicerequestsscreen.setSubjectAppointmet(appointmentSubject);
		servicerequestsscreen.setAddressAppointmet(appointmentAddress);
		servicerequestsscreen.setCityAppointmet(appointmentCity);
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		homescreen = servicerequestsscreen.clickHomeButton();
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSRVerifySummaryActionForAppointmentOnSRsCalendar(String rowID,
													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String appointmentSubject = "SR-APP";
		final String appointmentAddress = "Maidan";
		final String appointmentCity = "Kiev";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);

		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();

		questionsscreen.drawRegularSignature();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
		RegularQuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());

		servicesscreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);

		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();

		servicerequestsscreen.setSubjectAppointmet(appointmentSubject);
		servicerequestsscreen.setAddressAppointmet(appointmentAddress);
		servicerequestsscreen.setCityAppointmet(appointmentCity);
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		homescreen = servicerequestsscreen.clickHomeButton();
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();

		Assert.assertTrue(servicerequestsscreen.isSRSummaryAppointmentsInformation());

		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
																	 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_PRICE_MATRIX);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesscreen.selectSubService(matrixServiceData.getMatrixServiceName());
		servicesscreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
		for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData())
			RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
		RegularVehiclePartsScreenSteps.savePriceMatrixData();

		servicesscreen = new RegularServicesScreen();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(matrixServiceData.getMatrixServiceName()));
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		servicesscreen =  servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		servicesscreen.cancelWizard();

		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();

		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(inspectionData.getVehicleInfo());
		String inspnum = vehiclescreen.getInspectionNumber();
		RegularPriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(inspectionData.getPriceMatrixScreenData());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		vehiclescreen.saveWizard();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspnum));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOMonitorVerifyThatItIsNotPossibleToChangeServiceStatusBeforeStartService(String rowID,
																										  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);

		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);

		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
		ordermonitorscreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_YOU_MUST_SERVICE_PHASE_BEFORE_CHANGING_STATUS);
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		ordermonitorscreen.setCompletedServiceStatus();
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName()), orderMonitorData.getMonitorServiceData().getMonitorServiceStatus());
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOMonitorVerifyThatItIsNotPossibleToChangePhaseStatusBeforeStartPhase(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);

		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamworkordersscreen.clickSearchSaveButton();

		teamworkordersscreen.clickOnWO(wonum);

		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();

		Assert.assertTrue(ordermonitorscreen.isRepairPhaseExists());
		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonExists());
		ordermonitorscreen.clicksRepairPhaseLine();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_YOU_MUST_START_PHASE_BEFORE_CHANGING_STATUS);
		ordermonitorscreen.clickStartPhaseButton();

		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertFalse(ordermonitorscreen.isStartPhaseButtonExists());
		Assert.assertTrue(ordermonitorscreen.isServiceStartDateExists());

		ordermonitorscreen.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
		ordermonitorscreen.clickServiceDetailsDoneButton();

		ordermonitorscreen.clicksRepairPhaseLine();
		ordermonitorscreen.clickCompletedPhaseCell();

		for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorData().getMonitorServicesData())
			Assert.assertEquals(ordermonitorscreen.getPanelStatus(monitorServiceData.getMonitorService().getServiceName()), monitorServiceData.getMonitorServiceStatus());
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOMonitorVerifyThatStartDateIsSetWhenStartService(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);

		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);
		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
		ordermonitorscreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		Assert.assertFalse(ordermonitorscreen.isServiceStartDateExists());
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		Assert.assertTrue(ordermonitorscreen.isServiceStartDateExists());
		ordermonitorscreen.clickServiceDetailsDoneButton();

		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26013:WO Monitor: Regular - Verify that when change Status for Phase with 'Do not track individual service statuses' ON Phase status is set to all services assigned to phase",
			description = "WO Monitor: Regular - Verify that when change Status for Phase with 'Do not track individual service statuses' ON Phase status is set to all services assigned to phase")
	public void testWOMonitorVerifyThatWhenChangeStatusForPhaseWithDoNotTrackIndividualServiceStatusesONPhaseStatusIsSetToAllServicesAssignedToPhase() {

		final String VIN  = "1D3HV13T19S825733";
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String _price = "$100.00";
		final String _discaunt_us = "Discount 10-20$";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);

		servicesscreen.selectSubService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		RegularPriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix);
		vehiclePartScreen.setSizeAndSeverity(_size, _severity);
		Assert.assertEquals(vehiclePartScreen.getPrice(), _price);
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.selectDiscaunt(_discaunt_us);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();
		servicesscreen = new RegularServicesScreen();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);

		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);

		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();

		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		ordermonitorscreen.setCompletedServiceStatus();
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.WHEEL_SERVICE), "Completed");

		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonExists());
		ordermonitorscreen.clickStartPhaseButton();

		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		ordermonitorscreen.clickServiceStatusCell();;
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You cannot change the status of services for this phase. You can only change the status of the whole phase."));
		ordermonitorscreen.clickServiceDetailsDoneButton();

		ordermonitorscreen.clicksRepairPhaseLine();
		ordermonitorscreen.clickCompletedPhaseCell();

		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE), "Completed");
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DISC_EX_SERVICE1), "Completed");
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DYE_SERVICE), "Completed");

		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertEquals(ordermonitorscreen.getServiceStatusInPopup(iOSInternalProjectConstants.DYE_SERVICE), "Completed");
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		Assert.assertEquals(ordermonitorscreen.getServiceStatusInPopup(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE), "Completed");
		ordermonitorscreen = new RegularOrderMonitorScreen();
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26093:WO Monitor: Regular - Verify that %Service is not Started when Start phase",
			description = "WO Monitor: Regular - Verify that %Service is not Started when Start phase")
	public void testWOMonitorVerifyThatPercentageServiceIsNotStartedWhenStartPhase() {

		final String VIN  = "1D3HV13T19S825733";
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String _price = "$100.00";
		final String _discaunt_us = "Discount 10-20$";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreen.getInspectionNumber();
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);

		servicesscreen.selectSubService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		RegularPriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix);
		vehiclePartScreen.setSizeAndSeverity(_size, _severity);
		Assert.assertEquals(vehiclePartScreen.getPrice(), _price);
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.selectDiscaunt(_discaunt_us);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		RegularPriceMatricesScreen priceMatricesScreen = new RegularPriceMatricesScreen();
		priceMatricesScreen.clickBackButton();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));
		RegularOrderSummaryScreen ordersummaryscreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);

		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);

		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();

		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.WHEEL_SERVICE);
		ordermonitorscreen.setCompletedServiceStatus();
		//ordermonitorscreen.clickServiceDetailsDoneButton();
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.WHEEL_SERVICE), "Completed");

		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonPresent());
		ordermonitorscreen.clickPhaseStatusCell();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Order Monitor It is impossible to change phase status until you start phase.");
		ordermonitorscreen.clickStartPhase();
		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.DYE_SERVICE);
		ordermonitorscreen.setCompletedPhaseStatus();
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DYE_SERVICE), "Completed");
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DISC_EX_SERVICE1), "Completed");

		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		Assert.assertFalse(ordermonitorscreen.isServiceStartDateExists());
		Assert.assertEquals(ordermonitorscreen.getPanelStatusInPopup(iOSInternalProjectConstants.TEST_TAX_SERVICE), "Completed");

		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalONAndNoSignature(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatGreyAIconIsPresentForInvoiceWithCustomerApprovalOFFAndNoSignature(String rowID,
																									 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAIconIsNotPresentForInvoiceWhenSignatureExists(String rowID,
																									  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INV_PRINT);
			vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
			RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			for (ServiceData serviceData : workOrderData.getServicesList())
				RegularServicesScreenSteps.selectService(serviceData.getServiceName());
			RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			ordersummaryscreen.clickSave();

			RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.valueOf(workOrderData.getInvoiceData().getInvoiceType()));
			invoiceinfoscreen.setPO(workOrderData.getInvoiceData().getPoNumber());
			final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenumber));
			myinvoicesscreen.selectInvoiceForApprove(invoicenumber);
			myinvoicesscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
			approveinspscreen.clickApproveButton();
			approveinspscreen.drawApprovalSignature();
			myinvoicesscreen = new RegularMyInvoicesScreen();

			Assert.assertFalse(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumber));
			myinvoicesscreen.clickHomeButton();
		}
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRejectActionIsDisplayedForSRInStatusScheduledInspOrWOAndAssignForTech(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		//Create first SR
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);

		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber1 = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber1);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();
		//Create second SR
		servicerequestsscreen = new RegularServiceRequestsScreen();
		vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();

		String srnumber2 = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber2), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber2);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();

		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRejectActionIsDisplayedForSRInStatusOnHoldInspOrWOAndAssignForTech(String rowID,
																									String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.ON_HOLD.getValue());

		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		String inspectnumber = vehiclescreen.getInspectionNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSaveAsFinal();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));
		teaminspectionsscreen.clickActionButton();
		teaminspectionsscreen.selectInspectionForAction(inspectnumber);

		teaminspectionsscreen.clickApproveInspections();
		teaminspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspectnumber);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		teaminspectionsscreen = new RegularTeamInspectionsScreen();
		teaminspectionsscreen.clickBackButton();
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();
		boolean onhold = false;
		for (int i= 0; i < 7; i++) {
			servicerequestsscreen = homescreen.clickServiceRequestsButton();
			if (!servicerequestsscreen.getServiceRequestStatus(srnumber).equals(ServiceRequestStatus.ON_HOLD.getValue())) {
				servicerequestsscreen.clickHomeButton();
				Helpers.waitABit(30*1000);
			} else {

				onhold = true;
				break;
			}
		}

		Assert.assertTrue(onhold);
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRejectActionIsNotDisplayedForSRInStatusOnHoldInspOrWOAndNotAssignForTech(String rowID,
																							 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage srlistwebpage = operationspage.clickNewServiceRequestList();
		srlistwebpage.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_ALL_PHASES.getServiceRequestTypeName());
		srlistwebpage.clickAddServiceRequestButton();

		srlistwebpage.clickCustomerEditButton();
		srlistwebpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		srlistwebpage.clickDoneButton();

		srlistwebpage.clickVehicleInforEditButton();
		srlistwebpage.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		srlistwebpage.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
		srlistwebpage.clickDoneButton();

		srlistwebpage.saveNewServiceRequest();
		srlistwebpage.acceptFirstServiceRequestFromList();

		RegularMainScreen mainscr = homescreen.clickLogoutButton();
		mainscr.updateDatabase();
		RegularHomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectCancelAction();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRejectActionIsNotDisplayedForSRInStatusScheduledInspOrWOAndNotAssignForTech(String rowID,
																									   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage srlistwebpage = operationspage.clickNewServiceRequestList();
		srlistwebpage.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE.getServiceRequestTypeName());
		srlistwebpage.clickAddServiceRequestButton();

		srlistwebpage.clickCustomerEditButton();
		srlistwebpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		srlistwebpage.clickDoneButton();

		srlistwebpage.clickVehicleInforEditButton();
		srlistwebpage.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		srlistwebpage.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
		srlistwebpage.clickDoneButton();
		srlistwebpage.addServicesToServiceRequest(serviceRequestData.getPercentageServices());
		srlistwebpage.saveNewServiceRequest();
		String srnumber = srlistwebpage.getFirstInTheListServiceRequestNumber();
		srlistwebpage.acceptFirstServiceRequestFromList();

		DriverBuilder.getInstance().getDriver().quit();

		RegularMainScreen mainscr = homescreen.clickLogoutButton();
		mainscr.updateDatabase();
		RegularHomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectCancelAction();
		servicerequestsscreen.clickHomeButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		operationspage = backofficeheader.clickOperationsLink();
		srlistwebpage = operationspage.clickNewServiceRequestList();
		srlistwebpage.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE.getServiceRequestTypeName());
		srlistwebpage.clickAddServiceRequestButton();

		srlistwebpage.clickCustomerEditButton();
		srlistwebpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		srlistwebpage.clickDoneButton();

		srlistwebpage.clickVehicleInforEditButton();
		srlistwebpage.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		srlistwebpage.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
		srlistwebpage.clickDoneButton();
		srlistwebpage.addServicesToServiceRequest(serviceRequestData.getMoneyServices());
		srlistwebpage.saveNewServiceRequest();
		srnumber = srlistwebpage.getFirstInTheListServiceRequestNumber();
		srlistwebpage.acceptFirstServiceRequestFromList();

		DriverBuilder.getInstance().getDriver().quit();

		homescreen = new RegularHomeScreen();
		mainscr = homescreen.clickLogoutButton();
		mainscr.updateDatabase();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectEditServiceRequestAction();
		RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
		Assert.assertTrue(vehiclescreen.getTechnician() == null);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testNotMultipleServiceWithRequiredPanelsIsAddedOneTimeToWOAfterSelecting(String rowID,
																										  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();

		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			Assert.assertEquals(Helpers.getAlertTextAndAccept(), String.format(AlertsCaptions.ALERT_YOU_CAN_ADD_ONLY_ONE_SERVICE, serviceData.getServiceName()));
		}
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		Assert.assertEquals(selectedServicesScreen.getNumberOfSelectedServices(), 1);
		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testRegularWOVerifyThatBundleItemsAreShownWhenCreateWOFromInspection(String rowID,
																						 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(inspectionData.getBundleService().getBundleServiceName());
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen();
		selectedservicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(inspectionData.getBundleService().getBundleServiceName()));
		selectedServicesScreen.saveWizard();
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.approveInspectionApproveAllAndSignature();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber);
		myinspectionsscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.openCustomServiceDetails(inspectionData.getBundleService().getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		for (ServiceData serviceData : inspectionData.getBundleService().getServices()) {
			if (serviceData.isSelected())
				Assert.assertTrue(selectedservicebundlescreen.checkBundleIsSelected(serviceData.getServiceName()));
			if (!serviceData.isSelected())
				Assert.assertTrue(selectedservicebundlescreen.checkBundleIsNotSelected(serviceData.getServiceName()));
		}

		selectedservicebundlescreen.clickServicesIcon();
		for (ServiceData serviceData : inspectionData.getBundleService().getServices())
			Assert.assertTrue(selectedservicebundlescreen.isBundleServiceExists(serviceData.getServiceName()));
		selectedservicebundlescreen.clickCloseServicesPopup();
		selectedservicebundlescreen.clickCancel();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenOptionAllowToCloseSRIsSetToONActionCloseIsShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
																					 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());

		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.clickCancel();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenOptionAllowToCloseSRIsSetToOFFActionCloseIsNotShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
																														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.clickCancel();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressNoAlertMessageIsClose(String rowID,
																															String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);

		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressYesListOfStatusReasonsIsShown(String rowID,
																									  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.clickCancelCloseReasonDialog();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsShownInCaseItIsAssignedToReasonOnBO(String rowID,
																											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		final String answerReason = "All work is done. Answer questions";
		final String answerQuestion = "A3";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.selectUIAPickerValue(answerReason);
		servicerequestsscreen.clickDoneCloseReasonDialog();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		questionsscreen.answerQuestion2(answerQuestion);
		servicerequestsscreen.clickCloseSR();
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsNotShownInCaseItIsNotAssignedToReasonOnBO(String rowID,
																											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String answerReason = "All work is done. No Questions";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.selectDoneReason(answerReason);
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenCreateWOFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
																												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String totalSale = "3";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_WO_ONLY);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();

		servicerequestsscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
		String wonumber = vehiclescreen.getWorkOrderNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
		RegularOrderSummaryScreen ordersummaryscreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalSale);
		ordersummaryscreen.clickSave();

		for (int i = 0; i < serviceRequestData.getMoneyServices().size(); i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.lastIndexOf("'"));
			selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
			for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
				if (serviceData.getServiceName().equals(servicedetails)) {
					RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = selectedServicesScreen.openSelectedServiceDetails(serviceData.getServiceName());
					selectedservicedetailsscreen.clickVehiclePartsCell();
					selectedservicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
					selectedservicedetailsscreen.saveSelectedServiceDetails();
					selectedservicedetailsscreen.saveSelectedServiceDetails();
				}
			}
			servicesscreen = new RegularServicesScreen();
			servicesscreen.clickSave();
		}
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryOrdersButton();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
		teamWorkOrdersScreen.woExists(wonumber);
		teamWorkOrdersScreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenCreateInspectionFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
																												String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_INSP_ONLY);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
		String inspnumber = vehiclescreen.getInspectionNumber();

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
		selectedServicesScreen.clickSave();

		for (int i = 0; i < serviceRequestData.getMoneyServices().size(); i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.lastIndexOf("'"));
			selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
			for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
				if (serviceData.getServiceName().equals(servicedetails)) {
					RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = selectedServicesScreen.openSelectedServiceDetails(serviceData.getServiceName());
					selectedservicedetailsscreen.clickVehiclePartsCell();
					selectedservicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
					selectedservicedetailsscreen.saveSelectedServiceDetails();
					selectedservicedetailsscreen.saveSelectedServiceDetails();
				}
			}
			servicesscreen = new RegularServicesScreen();
			servicesscreen.clickSave();
		}
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspnumber));
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatOnInspectionApprovalScreenSelectedPriceMatrixValueIsShown(String rowID,
																						String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData())
			RegularPriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);

		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspectionnumber);

		for (ServiceData serviceData : inspectionData.getServicesToApprovesList()) {
			Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove(serviceData.getServiceName()));
			Assert.assertEquals(approveinspscreen.getInspectionServicePrice(serviceData.getServiceName()), serviceData.getServicePrice());
		}
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new RegularVehicleScreen();
		PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreensData().get(0);
		RegularPriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
		pricematrix.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
		Assert.assertEquals(pricematrix.clearVehicleData(), AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
		RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), inspectionData.getInspectionPrice());
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionTotalPrice());
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspectionnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(inspectionData.getServicesToApprovesList().get(0).getServiceName()), inspectionData.getServicesToApprovesList().get(0).getServicePrice());
		Assert.assertFalse(approveinspscreen.isInspectionServiceExistsForApprove(inspectionData.getServicesToApprovesList().get(1).getServiceName()));
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatQuestionSectionIsShownPerServiceForFirstSelectedPanelWhenQFIsNotRequired(String rowID,
																						String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehicleParts(inspectionData.getServiceData().getVehicleParts());
		final String selectedhehicleparts = servicesscreen.getListOfSelectedVehicleParts();

		for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts())
			Assert.assertTrue(selectedhehicleparts.contains(vehiclePartData.getVehiclePartName()));
		servicesscreen.clickSave();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		int i = 0;
		for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
			selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
			i++;
			RegularSelectedServiceDetailsScreen selectedservicedetailscreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertFalse(selectedservicedetailscreen.isQuestionFormCellExists());
			selectedservicedetailscreen.clickSaveButton();
		}
		selectedServicesScreen.clickSave();
		Helpers.acceptAlert();
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
																									   String description, JSONObject testData) {
		final String newLineSymbol = "\n";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPECTION_VIN_ONLY);
		vehiclescreen.getVINField().click();
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.getVINField().sendKeys(newLineSymbol);
		vehiclescreen.cancelOrder();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenEditInspectionSelectedVehiclePartsForServicesArePresent(String rowID,
																									   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		int i = 0;
		for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
			selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
			i++;
			RegularSelectedServiceDetailsScreen selectedservicedetailscreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertEquals(selectedservicedetailscreen.getVehiclePartValue(), vehiclePartData.getVehiclePartName());
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		selectedServicesScreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatItIsPossibleToSaveAsFinalInspectionLinkedToSR(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();

		InspectionData inspectionData = testCaseData.getInspectionData();

		servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
		servicesscreen.clickSaveAsDraft();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen();
		teaminspectionsscreen.selectInspectionForEdit(inspectionnumber);
		servicesscreen = new RegularServicesScreen();
		servicesscreen.clickSaveAsFinal();
		teaminspectionsscreen = new RegularTeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionIsApproveButtonExists(inspectionnumber));
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenFinalInspectionIsCopiedServisesAreCopiedWithoutStatuses_Approved_Declined_Skipped(String rowID,
																			String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(inspectionData.getBundleService().getBundleServiceName());
		RegularSelectedServiceDetailsScreen servicedetailsscreen = new RegularSelectedServiceDetailsScreen();
		servicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
		servicedetailsscreen.saveSelectedServiceDetails();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		RegularServicesScreenSteps.selectMatrixServiceData(inspectionData.getMatrixServiceData());
		servicesscreen.clickSave();
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);

		myinspectionsscreen.clickApproveInspections();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			if (serviceData.isSelected())
				Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove(serviceData), "Can't find service:" + serviceData.getServiceName());
			else
				Assert.assertFalse(approveinspscreen.isInspectionServiceExistsForApprove(serviceData));

		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.TEST_PACK_FOR_CALC);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			if (serviceData.isSelected())
				Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
		selectedServicesScreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatTextNotesAreCopiedToNewInspectionsWhenUseCopyAction(String rowID,
																				  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		final String notesText = "Test for copy";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.DEFAULT);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularNotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.setNotes(notesText);
		notesscreen.clickSaveButton();
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreen = new RegularVehicleScreen();
		String copiedinspnumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.saveWizard();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myinspectionsscreen.isNotesIconPresentForInspection(copiedinspnumber));
		notesscreen = myinspectionsscreen.openInspectionNotesScreen(copiedinspnumber);
		Assert.assertTrue(notesscreen.isNotesPresent(notesText));
		notesscreen.clickSaveButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatItIsPossibleToApproveTeamInspectionsUseMultiSelect(String rowID,
																				  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inpectionsIDs = new ArrayList<>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			myinspectionsscreen.clickAddInspectionButton();
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
			vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			inpectionsIDs.add(vehiclescreen.getInspectionNumber());
			RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesscreen.selectService(inspectionData.getBundleService().getBundleServiceName());
			RegularSelectedServiceDetailsScreen servicedetailsscreen = new RegularSelectedServiceDetailsScreen();
			servicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
			servicedetailsscreen.saveSelectedServiceDetails();
			servicesscreen = new RegularServicesScreen();
			servicesscreen.clickSaveAsFinal();
			new RegularMyInspectionsScreen();

		}
		myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (String inspectionID : inpectionsIDs) {
			teaminspectionsscreen.selectInspectionForAction(inspectionID);
		}

		teaminspectionsscreen.clickApproveInspections();
		teaminspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		for (String inspectionID : inpectionsIDs) {
			approveinspscreen.selectInspection(inspectionID);
			approveinspscreen.clickApproveAllServicesButton();
			approveinspscreen.clickSaveButton();;
		}
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		teaminspectionsscreen = new RegularTeamInspectionsScreen();
		teaminspectionsscreen.clickActionButton();
		for (String inspectionID : inpectionsIDs) {
			teaminspectionsscreen.selectInspectionForAction(inspectionID);
		}
		teaminspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		Assert.assertTrue(teaminspectionsscreen.isSendEmailInspectionMenuActionExists());
		teaminspectionsscreen.clickCancel();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatServicesOnServicePackageAreGroupedByTypeSelectedOnInspTypeWizard(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPECTION_GROUP_SERVICE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreensData().get(0));
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(inspectionData.getServiceData().getServiceName());
		for (ServicesScreenData servicesScreenData : inspectionData.getServicesScreens()) {
			servicesscreen = servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES, servicesScreenData.getScreenName());
			servicesscreen.selectServicePanel(servicesScreenData.getDamageData().getDamageGroupName());
			RegularServicesScreenSteps.selectServiceWithServiceData(servicesScreenData.getDamageData().getMoneyService());
		}
		servicesscreen = new RegularServicesScreen();
		servicesscreen.clickSaveAsFinal();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		questionsscreen.drawRegularSignature();
		servicesscreen.clickSaveAsFinal();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
		RegularQuestionsScreenSteps.answerQuestion(inspectionData.getQuestionScreensData().get(1).getQuestionData());
		servicesscreen.clickSaveAsFinal();

		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspnumber));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAllInstancesOfOneServiceAreCopiedFromInspectionToWO(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.approveInspectionApproveAllAndSignature();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionForCreatingWO(inspnumber);
		RegularSelectWorkOrderTypeScreen selectWOTypescreen = new RegularSelectWorkOrderTypeScreen();
		selectWOTypescreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : inspectionData.getServicesList())
			if (serviceData.isSelected())
				Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData));
			else
				Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());
		selectedServicesScreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAssignButtonIsPresentWhenSelectSomeTechInCaseDirectAssignOptionIsSetForInspectionType(String rowID,
																				  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPECTION_DIRECT_ASSIGN);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		vehiclescreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionToAssign(inspnumber);
		Assert.assertTrue(myinspectionsscreen.isAssignButtonExists());
		myinspectionsscreen.clickBackButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatDuringLineApprovalSelectAllButtonsAreWorkingCorrectly(String rowID,
																					String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);

		for (ServiceData serviceData : inspectionData.getServicesList())
			Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove(serviceData));
		approveinspscreen.clickApproveAllServicesButton();
		Assert.assertEquals(approveinspscreen.getNumberOfActiveApproveButtons(), 2);
		approveinspscreen.clickDeclineAllServicesButton();
		Assert.assertEquals(approveinspscreen.getNumberOfActiveDeclineButtons(), 2);
		approveinspscreen.clickSkipAllServicesButton();
		Assert.assertEquals(approveinspscreen.getNumberOfActiveSkipButtons(), 2);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatApproveOptionIsNotPresentForApprovedInspectionInMultiselectMode(String rowID,
																					String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> inspections = new ArrayList<>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			myinspectionsscreen.clickAddInspectionButton();
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
			vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			final String inspectionNumber = vehiclescreen.getInspectionNumber();
			inspections.add(inspectionNumber);
			RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
			vehiclescreen.saveWizard();
			myinspectionsscreen.selectInspectionForAction(inspectionNumber);
			myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

			RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
			approveinspscreen.selectInspection(inspectionNumber);
			approveinspscreen.clickApproveAllServicesButton();
			approveinspscreen.clickSaveButton();
			approveinspscreen.clickSingnAndDrawApprovalSignature();
			approveinspscreen.clickDoneButton();
			myinspectionsscreen = new RegularMyInspectionsScreen();
		}

		myinspectionsscreen.clickActionButton();
		for (String inspectionID : inspections) {
			myinspectionsscreen.selectInspectionForAction(inspectionID);
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickCancel();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (String inspectionID : inspections) {
			teaminspectionsscreen.selectInspectionForAction(inspectionID);
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickCancel();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatApproveOptionIsPresentInMultiselectModeOnlyOneOrMoreNotApprovedInspectionsAreSelected(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> inspections = new ArrayList<>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			myinspectionsscreen.clickAddInspectionButton();
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
			vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			inspections.add(vehiclescreen.getInspectionNumber());
			RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
			vehiclescreen.saveWizard();
		}
		myinspectionsscreen.selectInspectionForAction(inspections.get(0));
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspections.get(0));
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.clickActionButton();
		for (String inspectionID : inspections) {
			myinspectionsscreen.selectInspectionForAction(inspectionID);
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertTrue(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickCancel();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (String inspectionID : inspections) {
			teaminspectionsscreen.selectInspectionForAction(inspectionID);
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertTrue(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickCancel();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenOptionDraftModeIsSetToONWhenSaveInspectionProvidePromptToAUserToSelectEitherDraftOrFinal(String rowID,
																													String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		servicesscreen.clickSaveAsDraft();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myinspectionsscreen.isDraftIconPresentForInspection(inspnumber));
		myinspectionsscreen.clickOnInspection(inspnumber);
		Assert.assertFalse(myinspectionsscreen.isApproveInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCreateWOInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCreateServiceRequestInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCopyInspectionMenuActionExists());
		myinspectionsscreen.clickCancel();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		Assert.assertTrue(teaminspectionsscreen.isDraftIconPresentForInspection(inspnumber));

		teaminspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatApproveWOIsWorkingCorrectUnderTeamWO(String rowID,
																	 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String locationFilterValue = "All locations";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.setFilterLocation(locationFilterValue);
		teamworkordersscreen.clickSaveFilter();
		Assert.assertTrue(teamworkordersscreen.woExists(wonumber));
		Assert.assertTrue(teamworkordersscreen.isWorkOrderHasApproveIcon(wonumber));
		teamworkordersscreen.approveWorkOrder(wonumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		Assert.assertTrue(teamworkordersscreen.isWorkOrderHasActionIcon(wonumber));
		teamworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatItIsPosibleToAddPaymentFromDeviceForDraftInvoice(String rowID,
																	 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String cashcheckamount = "100";
		final String expectedPrice = "$0.00";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());

		servicesscreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword("Zayats", "1111");
		ordersummaryscreen.clickSave();
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceinfoscreen.setPO(invoiceData.getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		BaseUtils.waitABit(1000*60);
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new RegularInvoiceInfoScreen();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceinfoscreen.clickInvoicePayButton();
		invoiceinfoscreen.changePaynentMethodToCashNormal();
		invoiceinfoscreen.setCashCheckAmountValue(cashcheckamount);
		invoiceinfoscreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myinvoicesscreen.clickHomeButton();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoiceswebpage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoiceswebpage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		invoiceswebpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);

		invoiceswebpage.clickFindButton();

		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), invoiceData.getPoNumber());
		Assert.assertEquals(invoiceswebpage.getInvoicePOPaidValue(invoicenumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);
		Assert.assertEquals(invoicepayments.getPaymentsTypeAmountValue("Cash/Check"), PricesCalculations.getPriceRepresentation(cashcheckamount));
		Assert.assertEquals(invoicepayments.getPaymentsTypeCreatedByValue("Cash/Check"), "Employee Simple 20%");

		Assert.assertEquals(invoicepayments.getPaymentsTypeAmountValue("PO/RO"), expectedPrice);
		Assert.assertEquals(invoicepayments.getPaymentsTypeCreatedByValue("PO/RO"), "Back Office");
		invoicepayments.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderMyInvoice(String rowID,
																					   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String cashcheckamount = "100";
		final String expectedPrice = "$0.00";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());

		servicesscreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword("Zayats", "1111");
		ordersummaryscreen.clickSave();
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceinfoscreen.setPO(invoiceData.getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		BaseUtils.waitABit(1000*60);
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new RegularInvoiceInfoScreen();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceinfoscreen.clickInvoicePayButton();
		invoiceinfoscreen.changePaynentMethodToCashNormal();
		invoiceinfoscreen.setCashCheckAmountValue(cashcheckamount);
		invoiceinfoscreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceinfoscreen.clickSaveAsDraft();
		myinvoicesscreen = new RegularMyInvoicesScreen();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO(invoiceData.getNewPoNumber());
		myinvoicesscreen.clickHomeButton();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoiceswebpage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoiceswebpage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		invoiceswebpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();

		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), invoiceData.getNewPoNumber());
		Assert.assertEquals(invoiceswebpage.getInvoicePOPaidValue(invoicenumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);

		Assert.assertEquals(invoicepayments.getPaymentDescriptionTypeAmountValue("PO #: " + invoiceData.getNewPoNumber()), expectedPrice);
		invoicepayments.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderTeamInvoice(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String expectedPrice = "$0.00";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());

		servicesscreen = new RegularServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword("Zayats", "1111");
		ordersummaryscreen.clickSave();
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceinfoscreen.setPO(invoiceData.getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		BaseUtils.waitABit(1000*60);
		RegularTeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		teaminvoicesscreen.selectInvoice(invoicenumber);
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(invoiceData.getNewPoNumber());
		teaminvoicesscreen.clickHomeButton();

		Helpers.waitABit(5000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoiceswebpage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoiceswebpage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		invoiceswebpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();

		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), invoiceData.getNewPoNumber());
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);

		Assert.assertEquals(invoicepayments.getPaymentDescriptionTypeAmountValue("PO #: " + invoiceData.getNewPoNumber()), expectedPrice);
		invoicepayments.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(testName="Test Case 40033:WO Monitor: Verify filter for Team WO that returns only work assigned to tech who is logged in",
			description = "WO: Regular - Verify filter for Team WO that returns only work assigned to tech who is logged in")
	public void testInvoicesVerifyFilterForTeamWOThatReturnsOnlyWorkAssignedToTechWhoIsLoggedIn() {

		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		final String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);

		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.unselecTechnician("Employee Simple 20%");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		//servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen.clickTechniciansIcon();
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.selectSubService(iOSInternalProjectConstants.VPS1_SERVICE);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.saveWizard();

		BaseUtils.waitABit(20*1000);
		myworkordersscreen.switchToTeamView();
		RegularTeamWorkOrdersScreen teamworkordersscreen = new RegularTeamWorkOrdersScreen();
		teamworkordersscreen.clickOnWO(wonum);

		RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();
		RegularMainScreen mainscreen = homescreen.clickLogoutButton();

		homescreen = mainscreen.userLogin("Zayats", "1111");
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);

		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();
		mainscreen = homescreen.clickLogoutButton();


		homescreen = mainscreen.userLogin("Inspector", "12345");
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();
		mainscreen = homescreen.clickLogoutButton();

		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		MonitorWebPage monitorpage = backofficeheader.clickMonitorLink();
		RepairOrdersWebPage repairorderspage = monitorpage.clickRepairOrdersLink();
		repairorderspage.makeSearchPanelVisible();
		repairorderspage.setSearchWoNumber(wonum);
		repairorderspage.selectSearchLocation("Default Location");

		repairorderspage.selectSearchTimeframe("Custom");
		repairorderspage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		repairorderspage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		repairorderspage.clickFindButton();

		VendorOrderServicesWebPage vendororderservicespage = repairorderspage.clickOnWorkOrderLinkInTable(wonum);
		vendororderservicespage.changeRepairOrderServiceVendor(iOSInternalProjectConstants.DYE_SERVICE, "Device Team");
		vendororderservicespage.waitABit(1000);
		Assert.assertEquals(vendororderservicespage.getRepairOrderServiceTechnician(iOSInternalProjectConstants.DYE_SERVICE), "Oksi User");
		DriverBuilder.getInstance().getDriver().quit();


		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		//ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();
		mainscreen = homescreen.clickLogoutButton();

		homescreen = mainscreen.userLogin("Zayats", "1111");
		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();
		mainscreen = homescreen.clickLogoutButton();
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSRVerifyMultipleInspectionsAndMultipleWorkOrdersToBeTiedToAServiceRequest(String rowID,
																							 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inspnumbers = new ArrayList<>();
		List<String> wonumbers = new ArrayList<>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.clickSave();
		Helpers.getAlertTextAndCancel();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			servicerequestsscreen.selectServiceRequest(srnumber);
			servicerequestsscreen.selectCreateInspectionRequestAction();
			servicerequestsscreen.selectInspectionType(InspectionsTypes.valueOf(inspectionData.getInspectionType()));
			inspnumbers.add(vehiclescreen.getInspectionNumber());
			if (inspectionData.isDraft()) {
				RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
				servicesscreen.clickSaveAsDraft();
			} else
				vehiclescreen.saveWizard();
			servicerequestsscreen = new RegularServiceRequestsScreen();
		}

		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen();
		for (String inspectnumber : inspnumbers)
			Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));

		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			servicerequestsscreen.selectServiceRequest(srnumber);
			servicerequestsscreen.selectCreateWorkOrderRequestAction();
			servicerequestsscreen.selectWorkOrderType(WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
			wonumbers.add(vehiclescreen.getWorkOrderNumber());
			RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
			ordersummaryscreen.saveWizard();
		}
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryOrdersButton();
		RegularTeamWorkOrdersScreen teamworkordersscreen = new RegularTeamWorkOrdersScreen();
		for (String wonumber : wonumbers)
			Assert.assertTrue(teamworkordersscreen.woExists(wonumber));
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		final int timetowaitwo = 4;
		final String inspectionnotes = "Inspection notes";
		final String servicenotes = "Service Notes";
		final String locationFilterValue = "All locations";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();

		RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			if (serviceData.getServicePrice() != null)
				RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			else
				RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		}
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspnumber);

		visualInteriorScreen = new RegularVisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_SPORT_CAR);
		vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		RegularNotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.setNotes(inspectionnotes);
		notesscreen.clickSaveButton();

		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			RegularSelectedServiceDetailsScreen servicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			notesscreen = servicedetailsscreen.clickNotesCell();
			notesscreen.setNotes(servicenotes);
			notesscreen.clickSaveButton();
			servicedetailsscreen.saveSelectedServiceDetails();
		}
		for (ServiceData serviceData : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isNotesIconPresentForSelectedService(serviceData.getServiceName()));

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			RegularPriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);

		}

		vehiclescreen.saveWizard();

		Assert.assertTrue(myinspectionsscreen.isNotesIconPresentForInspection(inspnumber));
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamwoscreen = homescreen.clickTeamWorkordersButton();
		teamwoscreen.clickHomeButton();

		for (int i = 0; i < timetowaitwo; i++) {
			Helpers.waitABit(60*1000);
			teamwoscreen = homescreen.clickTeamWorkordersButton();
			teamwoscreen.clickHomeButton();
		}
		teamwoscreen = homescreen.clickTeamWorkordersButton();
		teamwoscreen.clickSearchButton();
		teamwoscreen.setSearchType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR.getWorkOrderTypeName());
		teamwoscreen.selectSearchLocation(locationFilterValue);
		teamwoscreen.clickSearchSaveButton();

		final String wonumber = teamwoscreen.getFirstWorkOrderNumberValue();
		teamwoscreen.selectWorkOrderForEidt(wonumber);

		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getEst(), inspnumber);
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		notesscreen = vehiclescreen.clickNotesButton();
		Assert.assertTrue(notesscreen.getNotesValue().length() > 0);
		notesscreen.clickSaveButton();

		servicesscreen = new RegularServicesScreen();
		RegularSelectedServicesScreen regularSelectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : inspectionData.getServicesList())
			Assert.assertTrue(regularSelectedServicesScreen.isNotesIconPresentForSelectedWorkOrderService(serviceData.getServiceName()));
		servicesscreen.cancelWizard();
		teamwoscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired(String rowID,
																													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();

		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), 0);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertTrue(servicedetailsscreen.isQuestionFormCellExists());
		Assert.assertEquals(servicedetailsscreen.getQuestion2Value(), inspectionData.getServiceData().getQuestionData().getQuestionAnswer());
		servicedetailsscreen.saveSelectedServiceDetails();

		for (int i = 1; i < inspectionData.getServiceData().getVehicleParts().size(); i++) {
			selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
			servicedetailsscreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertFalse(servicedetailsscreen.isQuestionFormCellExists());
			servicedetailsscreen.saveSelectedServiceDetails();
		}
		selectedServicesScreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForTeamInvoices(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		BaseUtils.waitABit(1000*60);
		RegularTeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
		teaminvoicesscreen.selectInvoice(invoicenum);
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(invoiceData.getPoNumber());
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY);
		teaminvoicesscreen.clickCancel();
		teaminvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForMyInvoices(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String invoicenum = myinvoicesscreen.getFirstInvoiceValue();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO(invoiceData.getPoNumber());
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY);
		myinvoicesscreen.clickCancel();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatTechSplitsIsSavedInPriceMatrices(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclescreen.clickSave();
		servicesscreen = new RegularServicesScreen();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.clickSave();

		RegularTechRevenueScreen techrevenuescreen = myworkordersscreen.selectWorkOrderTechRevenueMenuItem(wonumber);
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName()));
		techrevenuescreen.clickBackButton();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatItIsNotPossibleToChangeDefaultTechViaServiceTypeSplit(String rowID,
																 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		selectedservicescreen.selecTechnician(workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());
		Assert.assertTrue(selectedservicescreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.clickTechnicianToolbarIcon();
		servicesscreen.changeTechnician("Dent", workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());
		RegularSelectedServicesScreen regularSelectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedservicescreen = regularSelectedServicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		regularSelectedServicesScreen = new RegularSelectedServicesScreen();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));

		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatWhenUseCopyServicesActionForWOAllServiceInstancesShouldBeCopied(String rowID,
																					  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();


		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));

		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForCopyServices(wonumber);
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());

		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatWONumberIsNotDuplicated(String rowID,
																								String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		final String wonumber1 = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
		invoiceinfoscreen.clickSaveAsFinal();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationsWebPage.clickInvoicesLink();
		invoiceswebpage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoiceswebpage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		invoiceswebpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();
		invoiceswebpage.archiveInvoiceByNumber(invoicenumber);
		Assert.assertFalse(invoiceswebpage.isInvoiceDisplayed(invoicenumber));
		webdriver.quit();

		//Create second WO
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		final String workOrder2 = vehiclescreen.getWorkOrderNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrder(workOrder2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.clickCreateInvoiceIconForWO(workOrder2);
		myworkordersscreen.clickInvoiceIcon();
		myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		homescreen = myworkordersscreen.clickHomeButton();
		homescreen.clickStatusButton();
		homescreen.updateDatabase();
		RegularMainScreen mainscreen = new RegularMainScreen();
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		//Create third WO
		homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		final String workOrder3 = vehiclescreen.getWorkOrderNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		operationsWebPage = backofficeheader.clickOperationsLink();

		WorkOrdersWebPage workorderspage = operationsWebPage.clickWorkOrdersLink();

		workorderspage.makeSearchPanelVisible();
		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workorderspage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		workorderspage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		workorderspage.setSearchOrderNumber(workOrder3);
		workorderspage.clickFindButton();

		Assert.assertEquals(workorderspage.getWorkOrdersTableRowCount(), 1);
		webdriver.quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatInCaseValidVINIsDecodedReplaceExistingMakeAndModelWithNewOne(String rowID,
														String description, JSONObject testData) {

		final String[] VINs  = { "2A8GP54L87R279721", "1FMDU32X0PUB50142", "GFFGG"} ;
		final String makes[]  = { "Chrysler", "Ford", null } ;
		final String models[]  = { "Town and Country", "Explorer",  null };


		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		for (int i = 0; i < VINs.length; i++) {
			vehiclescreen.setVIN(VINs[i]);
			Assert.assertEquals(vehiclescreen.getMake(), makes[i]);
			Assert.assertEquals(vehiclescreen.getModel(), models[i]);
			vehiclescreen.clearVINCode();
		}

		vehiclescreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatWhenServiceDoNotHaveQuestionsAndSelectSeveralPanelsDoNotUnderlineAnyone(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehicleParts(workOrderData.getServiceData().getVehicleParts());

		for (VehiclePartData vehiclePartData : workOrderData.getServiceData().getVehicleParts()) {
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertTrue(selectedservicescreen.getVehiclePartValue().contains(vehiclePartData.getVehiclePartName()));
		}
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(workOrderData.getServiceData().getServiceName()), workOrderData.getServiceData().getVehicleParts().size());
		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
																										String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_VIN_ONLY);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.clickVINField();
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.cancelOrder();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatMessageIsShownForMoneyAndLaborServiceWhenPriceIsChangedTo0UnderWO(String rowID,
																												 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String zeroPrice = "0";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_MONITOR);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			if (serviceData.getServicePrice().equals(zeroPrice)) {
				RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
				if (serviceData.getServicePrice().equals(zeroPrice))
					Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
				selectedServiceDetailsScreen.clickTechniciansIcon();
				Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
			} else {

				selectedServiceDetailsScreen.clickTechniciansIcon();
				Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
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
			RegularServiceDetailsScreenSteps.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
			RegularServiceDetailsScreenSteps.verifyServiceTechnicianIsSelected(serviceData.getServiceNewTechnician());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

		}
		servicesscreen.cancelWizard();
		homescreen =  myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatValidationIsPresentForVehicleTrimField(String rowID,
																								  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String trimvalue = "Sport Plus";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_VEHICLE_TRIM_VALIDATION);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TRIM_REQUIRED);
		vehiclescreen.setTrim(trimvalue);
		Assert.assertEquals(vehiclescreen.getTrim(), trimvalue);

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();
		homescreen =  myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatTotalSaleIsNotShownWhenCheckmarkTotalSaleRequiredIsNotSetToOFF(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TOTAL_SALE_NOT_REQUIRED);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertFalse(ordersummaryscreen.isTotalSaleFieldPresent());
		ordersummaryscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertFalse(ordersummaryscreen.isTotalSaleFieldPresent());
		ordersummaryscreen.saveWizard();
		homescreen = teamworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatItIsPossibleToAssignTechToOrderByActionTechnicians(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());

		RegularServicesScreenSteps.selectBundleService(workOrderData.getBundleService());
		for (ServiceData serviceData : workOrderData.getMoneyServices())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

		RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		RegularVehiclePartsScreenSteps.setVehiclePartPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
		RegularVehiclePartsScreenSteps.verifyVehiclePartTechnicianValue(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
		RegularVehiclePartsScreenSteps.selectVehiclepartAdditionalService(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService());
		RegularVehiclePartsScreenSteps.saveVehiclePart();

		vehiclescreen.clickSave();
		servicesscreen = new RegularServicesScreen();

		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		RegularSelectedServiceDetailsScreen selectedservicescreen = myworkordersscreen.selectWorkOrderTechniciansMenuItem(wonumber);
		selectedservicescreen.selecTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName());
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getTechnician(), workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName() +
				", " + workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName());
		vehiclescreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatWhenPanelGroupingIsUsedForPackageForSelectedPanelOnlyLinkedServicesAreShown(String rowID,
																				   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();

		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_SERVICE_TYPE_WITH_OUT_REQUIRED);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (DamageData damageData : inspectionData.getDamagesData()) {
			servicesscreen.selectServicePanel(damageData.getDamageGroupName());
			for (ServiceData serviceData : damageData.getMoneyServices())
				Assert.assertTrue(servicesscreen.isServiceTypeExists(serviceData.getServiceName()));
			servicesscreen.clickBackServicesButton();
			servicesscreen = new RegularServicesScreen();
		}
		servicesscreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatWOIsSavedCorrectWithSelectedSubService_NoMessageWithIncorrectTechSplit(String rowID,
																													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices())
			RegularServicesScreenSteps.selectServiceWithSubServices(workOrderData.getDamageData());


		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices())
			Assert.assertTrue(selectedServicesScreen.isServiceWithSubSrviceSelected(workOrderData.getDamageData().getDamageGroupName(),
					serviceData.getServiceName()));
		selectedServicesScreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatAnswerServicesAreCorrectlyAddedForWOWhenPanelGroupIsSet(String rowID,
																									   String description, JSONObject testData) {

		final String questionName = "Q1";
		final String questionAswer = "No - rate 0";
		final String questionAswerSecond = "A1";
		final String questionVehiclePart = "Deck Lid";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_PANEL_GROUP);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, "Zayats Section2");
		questionsscreen.selectAnswerForQuestionWithAdditionalConditions(questionName, questionAswer, questionAswerSecond, questionVehiclePart);

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));
		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		selectedServicesScreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatSearchBarIsPresentForServicePackScreen(String rowID,
																						String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getVehiclePart() != null)
				RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
			else
				RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		}
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));

		selectedServicesScreen.cancelWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatCancelMessageIsShownForNewOrExistingWO(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.clickChangeScreen();
		vehiclescreen.clickCancel();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.STOP_WORKORDER_CREATION);
		vehiclescreen.clickCancel();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.STOP_WORKORDER_CREATION);

		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickChangeScreen();
		vehiclescreen.clickCancel();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.STOP_WORKORDER_EDIT);
		vehiclescreen.clickCancel();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.STOP_WORKORDER_EDIT);

		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickChangeScreen();
		vehiclescreen.clickCancel();

		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAcceptDeclineActionsArePresentForTechWhenTechnicianAcceptanceRequiredOptionIsONAndStatusIsProposed(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationsWebPage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();


		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectAcceptAction();

		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_ACCEPT_SERVICEREQUEST);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestOnHold(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isAcceptActionExists());
		Assert.assertFalse(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.clickCancel();
		servicerequestsscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenSRIsDeclinedStatusReasonShouldBeSelected(String rowID,
																																 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationsWebPage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();


		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectDeclineAction();

		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_DECLINE_SERVICEREQUEST);
		servicerequestsscreen.clickDoneCloseReasonDialog();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatSRIsNotAcceptedWhenEmployeeReviewOrUpdateIt(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationsWebPage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
		servicerequestslistpage.clickAddServiceRequestButton();
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();


		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectEditServiceRequestAction();
		RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.setTech(serviceRequestData.getVihicleInfo().getDefaultTechnician().getTechnicianFullName());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.clickCancel();

		servicerequestsscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
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
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationsWebPage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickGeneralInfoEditButton();
		servicerequestslistpage.setServiceRequestGeneralInfoAssignedTo(technicianValue);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(servicerequestslistpage.addAppointmentFromSRlist(startDate, endDate, technicianValue));
		DriverBuilder.getInstance().getDriver().quit();

		homescreen = new RegularHomeScreen();
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectAppointmentRequestAction();
		Assert.assertTrue(servicerequestsscreen.isAcceptAppointmentRequestActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineAppointmentRequestActionExists());
		servicerequestsscreen.clickBackButton();

		servicerequestsscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyAssignTechToServiceTypeInsteadOfIndividualServices(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescren =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_GROUP_SERVICE_TYPE);
		vehiclescren.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		RegularServicesScreen servicesscreen = vehiclescren.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickTechnicianToolbarIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.NO_SELECTED_SERVICES);
		for (DamageData damageData : workOrderData.getDamagesData()) {
			RegularServicesScreenSteps.selectPanelServiceData(damageData);
			servicesscreen.clickBackServicesButton();
		}

		servicesscreen.clickTechnicianToolbarIcon();
		RegularServiceTypesScreen serviceTypesScreen = new RegularServiceTypesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(serviceData.getServiceName()));

		DamageData moneyServicePanel = workOrderData.getDamagesData().get(0);
		serviceTypesScreen.clickOnPanel(moneyServicePanel.getDamageGroupName());
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = new RegularSelectedServiceDetailsScreen();
		selectedservicedetailscreen.selecTechnician(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
		selectedservicedetailscreen.clickSaveButton();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.selectServicePanel(moneyServicePanel.getDamageGroupName());
		for (ServiceData serviceData : moneyServicePanel.getMoneyServices()) {
			selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedservicedetailscreen.clickTechniciansIcon();
			Assert.assertTrue(selectedservicedetailscreen.isTechnicianSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
			selectedservicedetailscreen.clickCancel();
			selectedservicedetailscreen.clickCancel();
		}
		servicesscreen.clickBackServicesButton();

		DamageData bundleServicePanel = workOrderData.getDamagesData().get(1);
		servicesscreen.clickTechnicianToolbarIcon();
		serviceTypesScreen.clickOnPanel(bundleServicePanel.getDamageGroupName());
		selectedservicedetailscreen.selecTechnician(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
		selectedservicedetailscreen.clickSaveButton();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.switchToSelectedServicesTab();
		servicesscreen.selectServiceSubSrvice(bundleServicePanel.getBundleService().getBundleServiceName());
		for (ServiceData serviceData : bundleServicePanel.getBundleService().getServices()) {
			RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
			selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
			selectedservicedetailscreen.clickTechniciansCell();
			Assert.assertTrue(selectedservicedetailscreen.isTechnicianSelected(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
			selectedservicedetailscreen.clickCancel();
			selectedservicedetailscreen.clickCancel();
		}
		selectedservicedetailscreen.clickCancel();
		servicesscreen.switchToAvailableServicesTab();

		servicesscreen.clickTechnicianToolbarIcon();
		serviceTypesScreen.clickOnPanel(workOrderData.getDamageData().getDamageGroupName());
		selectedservicedetailscreen.selecTechnician(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName());
		selectedservicedetailscreen.selecTechnician(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName());
		selectedservicedetailscreen.clickSaveButton();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.switchToSelectedServicesTab();
		servicesscreen.selectServiceSubSrvice(workOrderData.getDamageData().getBundleService().getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		selectedservicebundlescreen.openBundleInfo(workOrderData.getDamageData().getBundleService().getService().getServiceName());
		selectedservicedetailscreen.clickTechniciansCell();

		Assert.assertTrue(selectedservicedetailscreen.isTechnicianSelected(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertTrue(selectedservicedetailscreen.isTechnicianSelected(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName()));
		selectedservicedetailscreen.clickCancel();
		selectedservicedetailscreen.clickCancel();
		selectedservicebundlescreen.clickCancel();
		servicesscreen.switchToAvailableServicesTab();

		servicesscreen.selectServicePanel(moneyServicePanel.getDamageGroupName());
		RegularServicesScreenSteps.openCustomServiceDetails(moneyServicePanel.getMoneyService().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(moneyServicePanel.getMoneyService().getVehiclePart());
		selectedservicedetailscreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicedetailscreen.isTechnicianSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
		selectedservicedetailscreen.clickCancel();
		selectedservicedetailscreen.clickCancel();
		servicesscreen.clickBackServicesButton();

		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		RegularOrderSummaryScreen ordersummaryscreen = vehiclescren.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatDefaultTechIsNotChangedWhenResetOrderSplit(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getMoneyServices()) {
			RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getServiceDefaultTechnician() != null) {
				RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				RegularServiceDetailsScreenSteps.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
				RegularServiceDetailsScreenSteps.saveServiceDetails();
			}
			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}

		vehiclescreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician(workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianFullName());
		selectedservicescreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		selectedservicescreen.clickSaveButton();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : workOrderData.getMoneyServices()) {
			selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice2() != null)
				RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice2());
			if (serviceData.getServiceDefaultTechnician() != null) {
				RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				RegularServiceDetailsScreenSteps.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
				RegularServiceDetailsScreenSteps.saveServiceDetails();
			} else {
				RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				RegularServiceDetailsScreenSteps.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getNewTechnician());
				RegularServiceDetailsScreenSteps.saveServiceDetails();
			}
			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}

		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatItIsPossibleToAssignTechWhenWOIsNotStarted(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String defaultLocationValue = "Test Location ZZZ";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(defaultLocationValue);
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getMoneyServices())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defaultLocationValue);
		teamWorkOrdersScreen.clickSearchSaveButton();

		teamWorkOrdersScreen.clickOnWO(wonumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
		OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
		MonitorServiceData firstMonitorServiceData = orderMonitorData.getMonitorServicesData().get(0);
		orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
		orderMonitorScreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician(firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
		selectedservicescreen.saveSelectedServiceDetails();
		orderMonitorScreen = new RegularOrderMonitorScreen();
		orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
		Assert.assertEquals(orderMonitorScreen.getTechnicianValue(), firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
		orderMonitorScreen.clickServiceDetailsDoneButton();

		orderMonitorScreen.clickStartOrderButton();
		Assert.assertTrue(Helpers.getAlertTextAndAccept().contains(AlertsCaptions.WOULD_YOU_LIKE_TO_START_REPAIR_ORDER));
		orderMonitorScreen = new RegularOrderMonitorScreen();

		MonitorServiceData secondMonitorServiceData = orderMonitorData.getMonitorServicesData().get(1);
		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		orderMonitorScreen.clickTech();
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician(secondMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
		selectedservicescreen.saveSelectedServiceDetails();
		orderMonitorScreen = new RegularOrderMonitorScreen();

		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		orderMonitorScreen.clickStartService();
		orderMonitorScreen = new RegularOrderMonitorScreen();
		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		orderMonitorScreen.setCompletedServiceStatus();
		Assert.assertEquals(orderMonitorScreen.getPanelStatus(secondMonitorServiceData.getMonitorService()), secondMonitorServiceData.getMonitorServiceStatus());
		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		orderMonitorScreen.clickTech();
		orderMonitorScreen.clickServiceDetailsDoneButton();

		orderMonitorScreen.clickBackButton();

		teamWorkOrdersScreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatItIsNotPossibleToAssignTechWhenWOIsOnHold(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String defaultLocationValue = "Test Location ZZZ";
		final String workOrderMonitorStatus = "On Hold";
		final String statusReason = "On Hold new reason";

				homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(defaultLocationValue);
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getMoneyServices())
			RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defaultLocationValue);
		teamWorkOrdersScreen.clickSearchSaveButton();


		teamWorkOrdersScreen.clickOnWO(wonumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();

		orderMonitorScreen.changeStatusForWorkOrder(workOrderMonitorStatus, statusReason);
		orderMonitorScreen = new RegularOrderMonitorScreen();
		orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
		orderMonitorScreen.clickTech();

		orderMonitorScreen.clickServiceDetailsDoneButton();
		teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatTechSplitAssignedFormVehicleScreenIsSetToServicesUnderList(String rowID,
																		  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		}

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
			selectedServiceDetailsScreen.selecTechnician(technician.getTechnicianFullName());
		selectedServiceDetailsScreen.clickSaveButton();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);


		vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			selectedServiceDetailsScreen = selectedServicesScreen.openSelectedServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.clickTechniciansIcon();
			for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
				Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(technician.getTechnicianFullName()));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServicesScreen = new RegularSelectedServicesScreen();
		}
		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyPriceMatrixItemDoesntHaveAdditionalServicesItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName()),
				workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianPercentageValue());
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertEquals(servicesscreen.getTotalAmaunt(),
				PricesCalculations.getPriceRepresentation(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice()));

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyPriceMatrixItemHasMoneyAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
																																 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		servicesscreen = new RegularServicesScreen();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyPriceMatrixItemHasPercentageAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmountPlusAdditionalPercentage(String rowID,
																															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
		selectedservicescreen.saveSelectedServiceDetails();

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
		servicesscreen = new RegularServicesScreen();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyIfServiceHasDefaultTechnicianAndItsAmountIs0ThenDefaultTechnicianShouldBeAssignedToTheService_NotTechnicianSplitAtWorkOrderLevel(String rowID,
																																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularServiceDetailsScreenSteps.saveServiceDetails();

		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		selectedservicescreen.saveSelectedServiceDetailsWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		RegularSelectedServiceDetailsScreen serviceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		serviceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		serviceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName());
		serviceDetailsScreen.cancelSelectedServiceDetails();
		serviceDetailsScreen.cancelSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatCorrectTechSplitAmountIsShownForMatrixServiceWhenChangePriceTo0(String rowID,
																																							 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String serviceZeroPrice = "0";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
		RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
		RegularVehiclePartsScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.clickSave();
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		vehiclePartScreen = pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());

		vehiclePartScreen.setPrice(serviceZeroPrice);
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_DEFAULT_TECH_SPLIT_WILL_BE_ASSIGNED_IF_SET_ZERO_AMAUNT);

		vehiclePartScreen.clickOnTechnicians();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_VEHICLE_PART);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()), PricesCalculations.getPriceRepresentation(serviceZeroPrice));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();

		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatTechSplitAmountIsShownCorrectUnderMonitorForServiceWithAdjustments(String rowID,
																								String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		RegularServiceDetailsScreenSteps.selectServiceAdjustment(workOrderData.getServiceData().getServiceAdjustment());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularServiceDetailsScreenSteps.saveServiceDetails();


		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = homescreen.clickTeamWorkordersButton();
		teamWorkOrdersScreen.clickOnWO(wonumber);
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
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRequiredServicesHasCorrectTech(String rowID,
																								   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_REQUIRED_SERVICES_ALL);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		RegularSelectedServiceBundleScreen serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.clickCancel();
		}
		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		vehiclescreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();

		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			selectedServiceDetailsScreen.selecTechnician(serviceTechnician.getTechnicianFullName());
		selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			String techString =  "";
			for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
				techString = techString + ", " + serviceTechnician.getTechnicianFullName();
			techString = techString.replaceFirst(",", "").trim();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
			selectedServiceDetailsScreen.clickCancel();
		}
		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServiceDetailsScreen = selectedServicesScreen.openSelectedServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularServiceDetailsScreenSteps.saveServiceDetails();
		selectedServicesScreen.clickOnSelectedService(workOrderData.getMatrixServiceData().getMatrixServiceName());
		RegularPriceMatrixScreen priceMatrixScreen = selectedServiceDetailsScreen.selectPriceMatrices(workOrderData.getMatrixServiceData().getHailMatrixName());
		RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		RegularVehiclePartsScreenSteps.saveVehiclePart();
		selectedServicesScreen.clickSave();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			String techString =  "";
			for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
				techString = techString + ", " + serviceTechnician.getTechnicianFullName();
			techString = techString.replaceFirst(",", "").trim();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
			selectedServiceDetailsScreen.clickCancel();
		}
		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRequiredBundleItemsHasCorrectDefTech(String rowID,
															 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		RegularSelectedServiceBundleScreen serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		}
		serviceBundleScreen.clickCancel();

		selectedServicesScreen = new RegularSelectedServicesScreen();
		vehiclescreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			selectedServiceDetailsScreen.selecTechnician(serviceTechnician.getTechnicianFullName());
		selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());

		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			if (serviceData.getServiceNewTechnicians() != null) {
				String techString =  "";
				for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
					techString = techString + ", " + serviceTechnician.getTechnicianFullName();
				techString = techString.replaceFirst(",", "").trim();
				Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
			} else {
				Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			}

			if (serviceData.getVehiclePart() != null)
				RegularServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());

			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}

		serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		serviceBundleScreen.clickSaveButton();


		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			if (serviceData.getServiceNewTechnicians() != null) {
				String techString = "";
				for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
					techString = techString + ", " + serviceTechnician.getTechnicianFullName();
				techString = techString.replaceFirst(",", "").trim();
				Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
			} else {
				Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			}
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		}

		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRequiredBundleItemsAndServiceWithExpensesAnd0PriceHasCorrectDefTechAfterEditWO(String rowID,
																   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		RegularSelectedServiceBundleScreen serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		}
		serviceBundleScreen.clickCancel();

		selectedServicesScreen = new RegularSelectedServicesScreen();
		vehiclescreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			selectedServiceDetailsScreen.selecTechnician(serviceTechnician.getTechnicianFullName());
		selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			if (serviceData.getServiceNewTechnicians() != null) {
				String techString =  "";
				for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
					techString = techString + ", " + serviceTechnician.getTechnicianFullName();
				techString = techString.replaceFirst(",", "").trim();
				Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
			} else {
				Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			}

			if (serviceData.getVehiclePart() != null)
				RegularServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());

			RegularServiceDetailsScreenSteps.saveServiceDetails();
		}

		serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		serviceBundleScreen.clickSaveButton();


		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.switchToAvailableServicesTab();
		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesscreen.switchToSelectedServicesTab();
		selectedServicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceTechnician.getTechnicianFullName()));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			if (serviceData.getServiceNewTechnicians() != null) {
				String techString = "";
				for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
					techString = techString + ", " + serviceTechnician.getTechnicianFullName();
				techString = techString.replaceFirst(",", "").trim();
				Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
			} else {
				Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			}
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		}

		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceTechnician.getTechnicianFullName()));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatSelectedServicesHaveCorrectTechSplitWhenChangeIsDuringCreatingWO(String rowID,
																											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_ALL_SERVICES);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			selectedServiceDetailsScreen.selecTechnician(serviceTechnician.getTechnicianFullName());
		selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();


		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.clickTechniciansIcon();
			for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
				Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceTechnician.getTechnicianFullName()));
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		}
		selectedServicesScreen.switchToAvailableServicesTab();
		RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		RegularVehiclePartsScreenSteps.verifyVehiclePartTechniciansValue(workOrderData.getVehicleInfoData().getNewTechnicians());
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.clickDiscaunt(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
		selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.clickTechniciansIcon();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceTechnician.getTechnicianFullName()));
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		vehiclePartScreen.setTime(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTime());
		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();
		servicesscreen = new RegularServicesScreen();

		servicesscreen.selectService(workOrderData.getBundleService().getBundleServiceName());
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		selectedservicebundlescreen.clickTechniciansIcon();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(serviceTechnician.getTechnicianFullName()));
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen = selectedservicebundlescreen.openBundleInfo(workOrderData.getBundleService().getService().getServiceName());
		String techString = "";
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			techString = techString + ", " + serviceTechnician.getTechnicianFullName();
		techString = techString.replaceFirst(",", "").trim();
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedservicebundlescreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
		selectedservicebundlescreen.clickSaveButton();
		servicesscreen = new RegularServicesScreen();
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(workOrderData.getBundleService().getBundleServiceName()));
		selectedServicesScreen.clickSaveAsFinal();
		selectedServicesScreen.clickSave();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));
		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfServicePriceIs0AndHasDefTechAssignToServiceDefTech(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selecTechnician(workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();


		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
				workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPriceValue());
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfServicePriceIs0AssignWOSplitToServiceInCaseNoDefTech(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selecTechnician(workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), workOrderData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianFullName()));
		Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianFullName()), workOrderData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatItIsNotPossibleToChangeStatusForServiceOrPhaseWhenCheckOutRequired(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();

		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		RegularOrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
		ordermonitorscreen.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		ordermonitorscreen.clickServiceDetailsCancelButton();

		ordermonitorscreen.clickStartPhase();
		ordermonitorscreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
		ordermonitorscreen.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		ordermonitorscreen.clickServiceDetailsCancelButton();

		ordermonitorscreen.clickStartPhaseCheckOutButton();
		ordermonitorscreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
		ordermonitorscreen.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		ordermonitorscreen.clickServiceDetailsCancelButton();

		ordermonitorscreen.changeStatusForStartPhase(OrderMonitorPhases.COMPLETED);
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(workOrderData.getMatrixServiceData().getMatrixServiceName()),
				OrderMonitorPhases.COMPLETED.getrderMonitorPhaseName());
		ordermonitorscreen.clickBackButton();
		homescreen = teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenOptionPhaseEnforcementIsOFFAndStartServiceRequiredItIsPossibleToStartServiceFromInactivePhase(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			RegularServicesScreenSteps.selectService(serviceData.getServiceName());
		RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();

		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		RegularOrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
		Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickServiceDetailsDoneButton();

		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonExists());
		ordermonitorscreen.clickStartPhaseButton();
		Assert.assertFalse(ordermonitorscreen.isStartPhaseButtonExists());
		ordermonitorscreen.clickBackButton();
		homescreen = teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatTechWhoIsNotAssignedToOrderServiceCannotStartOrder(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_DELAY_START);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();


		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		RegularOrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();

		ordermonitorscreen.clickStartOrderButton();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
		ordermonitorscreen = new RegularOrderMonitorScreen();

		ordermonitorscreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatIsItImpossibleToChangeStatusForServiceOrPhaseWhenOrderIsNotStarted(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_DELAY_START);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		RegularOrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertEquals(ordermonitorscreen.getPanelStatus(serviceData),
					OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());
		ordermonitorscreen.selectPanel(workOrderData.getServicesList().get(workOrderData.getServicesList().size()-1));
		ordermonitorscreen.clickServiceStatusCell();

		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_MUST_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
		ordermonitorscreen.clickServiceDetailsCancelButton();
		ordermonitorscreen = new RegularOrderMonitorScreen();
		ordermonitorscreen.clickOrderStartDateButton();
		LocalDate date = LocalDate.now();
		ordermonitorscreen.setOrderStartYearValue(date.getYear()+1);
		Assert.assertEquals(ordermonitorscreen.getOrderStartYearValue(), Integer.toString(date.getYear()));
		ordermonitorscreen.setOrderStartYearValue(date.getYear()-1);
		Assert.assertEquals(ordermonitorscreen.getOrderStartYearValue(), Integer.toString(date.getYear()-1));
		ordermonitorscreen.closeSelectorderDatePicker();
		ordermonitorscreen = new RegularOrderMonitorScreen();
		ordermonitorscreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();

		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

}
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
		//resrtartApplication();
		//RegularMainScreen mainscr = new RegularMainScreen();
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
		vehicleScreen.setVIN(inspectionData.getVinNumber());
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
		vehiclescreen.setVIN(inspectionData.getVinNumber());
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
		vehiclescreen.setVIN(inspectionData.getVinNumber());
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

	//Test Case 8430:Create work order with type is assigned to a specific client
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
		vehiclescreen.setVIN(workOrderData.getVinNumber());

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
		vehiclescreen.setVIN(workOrderDataCopiedServices.getVinNumber());
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.switchToSelectedServicesTab();
		SelectedServicesScreenSteps.verifyServicesAreSelected(workOrderDataCopiedServices.getSelectedServices());
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.waitWorkOrderSummaryScreenLoad();
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), workOrderDataCopiedServices.getWorkOrderPrice());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

	}

	//todo
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
			vehiclescreen.setVIN(inspectionData.getVinNumber());
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

	//todo
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
			vehiclescreen.setVIN(inspectionData.getVinNumber());
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
		vehiclescreen.setVIN(inspectionData.getVinNumber());
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
		vehiclescreen.setVIN(inspectionData.getVinNumber());
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

	String srtowo = "";

	//Test Case 20786:Creating Service Request with Inspection, WO and Appointment required on device
	@Test(testName = "Test Case 20786:Creating Service Request with Inspection, WO and Appointment required on device", description = "Creating Service Request with Inspection, WO and Appointment required on device")
	public void testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice() {
		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		final String _color = "Red";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";
		final String stock = "Stock1";
		final String _ro = "123";
		final String licplate = "456";
		//final String _year = "2012";

		final String teamname= "Default team";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();


		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);

		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		vehiclescreen.setVIN(VIN);
		/*Assert.assertTrue(.findElement(
				MobileBy.name("The VIN is invalid.")).isDisplayed());
		.findElement(
				MobileBy.name("Close"))
				.click();
		*/
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		//vehiclescreen.setYear(_year);

		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);
		//vehiclescreen.setLicensePlate(licplate);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.WHEEL_SERVICE);


		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.selectService("Quest_Req_Serv");
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen =  servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicedetailsscreen.setServiceQuantityValue("3");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		RegularClaimScreen claimscreen = servicesscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany("USG");
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Question 'Signature' in section 'Follow up Requested' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Close"))
				.click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		//Helpers.swipeRegularScreenUp();
		questionsscreen.drawRegularSignature();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Close"))
				.click();
		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		srtowo = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srtowo), "On Hold");
		Assert.assertTrue(servicerequestsscreen.getServiceRequestClient(srtowo).contains(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestDetails(srtowo).contains("WERTYU123"));
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

		servicerequestslistpage.selectSearchTeam(teamname);
		servicerequestslistpage.selectSearchTechnician("Employee Simple 20%");
		servicerequestslistpage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		servicerequestslistpage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		servicerequestslistpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());

		servicerequestslistpage.setSearchFreeText(srtowo);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.verifySearchResultsByServiceName("Test Company (Universal Client)");
		//Assert.assertTrue(servicerequestslistpage.isFirstServiceRequestFromListHasStatusOnHold());
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getVINValueForSelectedServiceRequest(), "WERTYU123");
		Assert.assertEquals(servicerequestslistpage.getCustomerValueForSelectedServiceRequest(), "Test Company (Universal Client)");
		Assert.assertEquals(servicerequestslistpage.getEmployeeValueForSelectedServiceRequest(), "Employee Simple 20% (Default team)");
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("Bundle1_Disc_Ex $150.00 (1.00)"));
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("Quest_Req_Serv $10.00 (1.00)"));
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("Wheel $70.00 (3.00)"));
		DriverBuilder.getInstance().getDriver().quit();
	}

	//Test Case 21582:Create Inspection from Service request
	@Test(testName = "Test Case 21582:Create Inspection from Service request", description = "Create Inspection from Service request"/*,
			dependsOnMethods = { "testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice" }*/)
	public void testCreateInspectionFromServiceRequest()  {
		final String summ= "438.60";

		homescreen = new RegularHomeScreen();
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();

		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_SR_INSPTYPE);
		String inspnumber = vehiclescreen.getInspectionNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.WHEEL_SERVICE,  "$70.00 x 3.00"));
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.BUNDLE1_DISC_EX, "$70.00"));
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicedetailsscreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicedetailsscreen.changeBundleQuantity(iOSInternalProjectConstants.WHEEL_SERVICE, "2");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues("Quest_Req_Serv", "$10.00 x 1.00"));
		selectedservicedetailsscreen = selectedServicesScreen.openCustomServiceDetails("Quest_Req_Serv");
		selectedservicedetailsscreen.answerTaxPoint1Question("Test Answer 1");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		homescreen = servicerequestsscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspnumber));
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspnumber), PricesCalculations.getPriceRepresentation(summ));
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);

		myinspectionsscreen.clickApproveInspections();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		//Helpers.acceptAlert();
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickApproveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		//myinspectionsscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}

	//Test Case 21585:Create WO from Service Request
	@Test(testName = "Test Case 21585:Create WO from Service Request", description = "Create WO from Service Request"/*,
			dependsOnMethods = { "testCreateInspectionFromServiceRequest" }*/)
	public void testCreateWOFromServiceRequest() {

		/*.closeApp();
		Thread.sleep(60*1000*15);

		Inicialize();
		String srnum = "R-00006200";
		RegularMainScreen mainscreen = new RegularMainScreen();
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		*/
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		homescreen = customersscreen.clickHomeButton();

		//test case
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srtowo);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_SR);
		String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//servicesscreen.selectServicePanel("Other");
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicedetailsscreen.changeAmountOfBundleService("70");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();

		selectedServicesScreen.clickSave();
		Helpers.waitForAlert();
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();
		servicerequestsscreen= new RegularServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.woExists(wonumber);
		homescreen = myworkordersscreen.clickHomeButton();
	}

	//Test Case 16640:Create Inspection from Invoice with two WOs
	@Test(testName = "Test Case 16640:Create Inspection from Invoice with two WOs", description = "Create Inspection from Invoice with two WOs")
	public void testCreateInspectionFromInvoiceWithTwoWOs() {

		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		//final String _year = "2014";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";
		final String stock = "Stock1";
		final String _ro = "123";
		final String ordersumm = "13.50";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);

		String wonumber1 = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		//vehiclescreen.setYear(_year);
		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);

		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Cowl, Other");
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ordersumm));

		ordersummaryscreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehiclescreen.getMake(), _make);
		Assert.assertEquals(vehiclescreen.getModel(), _model);
		//Assert.assertEquals(vehiclescreen.getYear(), _year);
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();
		ordersummaryscreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
		invoiceinfoscreen.setPO("23");
		invoiceinfoscreen.addWorkOrder(wonumber1);
		Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ordersumm));
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 16664:Create Invoice from WO in "My WOs" list
	@Test(testName = "Test Case 16664:Create Invoice from WO in \"My WOs\" list", description = "Create Invoice from WO in My WOs list")
	public void testCreateInvoiceFromWOInMyWOsList() {

		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		//final String _year = "2012";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);
		String wonumber1 = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		//vehiclescreen.setYear(_year);


		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		selectedservicescreen.clickAdjustments();
		selectedservicescreen.selectAdjustment("For_SP_Cl");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$108.50");

		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.changeBundleQuantity(iOSInternalProjectConstants.WHEEL_SERVICE, "2");

		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		RegularPriceMatrixScreen pricematrix = selectedservicescreen.selectMatrics(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		pricematrix.selectPriceMatrix("HOOD");
		RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.setSizeAndSeverity("NKL", "VERY LIGHT");
		Assert.assertTrue(vehiclePartScreen.isNotesExists());
		Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains("Employee Simple 20%"));
		Assert.assertEquals(vehiclePartScreen.getPrice(), "$100.00");
		vehiclePartScreen.selectDiscaunt("Discount 10-20$");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		vehiclePartScreen.saveVehiclePart();

		pricematrix.clickSave();
		servicesscreen = new RegularServicesScreen();

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		ordersummaryscreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("PO# is required")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Close"))
				.click();
		invoiceinfoscreen.setPO(iOSInternalProjectConstants.USER_PASSWORD);
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();

		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		myinvoicesscreen.clickHomeButton();
	}

	//Test Case 18426:Don't allow to select billed and not billed orders together in multi selection mode
	@Test(testName = "Test Case 18426:Don't allow to select billed and not billed orders together in multi selection mode", description = "Don't allow to select billed and not billed orders together in multi selection mode")
	public void testDontAlowToSelectBilleAandNotBilledOrdersTogetherInMultiSelectionMode() {

		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		//final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";
		final String stock = "Stock1";
		final String _ro = "123";

		//Create WO1
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);
		String wonumber1 = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		//vehiclescreen.setYear(_year);
		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService("VPS1");

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.clickSave();

		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();
		//Create WO2
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.clickAddOrderButton();

		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);
		/*alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "The VIN is invalid.");
		.findElement(
				MobileBy.name("Close"))
				.click();
		.findElement(
				MobileBy.name("Close"))
				.click();*/
		String wonumber2 = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		//vehiclescreen.setYear(_year);
		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);

		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService("VPS1");

		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.clickSave();

		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();

		//Test case
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.approveWorkOrder(wonumber2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("first123");
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();

		myworkordersscreen.clickActionButton();
		myworkordersscreen.selectWorkOrderForAction(wonumber1);
		//myworkordersscreen.woExistsViaSearch(wonumber1);
		myworkordersscreen.clickDoneButton();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();

		myworkordersscreen.clickActionButton();
		myworkordersscreen.selectWorkOrderForAction(wonumber2);
		//myworkordersscreen.woExistsViaSearch(wonumber2);
		myworkordersscreen.clickDoneButton();
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 18436:Don't allow to change billed orders
	@Test(testName = "Test Case 18436:Don't allow to change billed orders", description = "Don't allow to change billed orders")
	public void testDontAlowToChangeBilledOrders() {

		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		//final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";
		final String stock = "Stock1";
		final String _ro = "123";
		final String[] menuitemstoverify = { "Edit" , "Notes", "Change\nstatus", "Delete", "Create\nInvoices" };

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		//Create WO1
		homescreen = new RegularHomeScreen();
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);
		/*final String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "The VIN is invalid.");
		.findElement(
				MobileBy.name("Close"))
				.click();
		.findElement(
				MobileBy.name("Close"))
				.click();*/
		String wonumber1 = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		//vehiclescreen.setYear(_year);
		//Thread.sleep(2000);
		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectSubService("VPS1");

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.clickSave();

		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();


		//Test case

		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("first123");
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling("All");
		myworkordersscreen.clickSaveFilter();

		myworkordersscreen.selectWorkOrder(wonumber1);
		for (int i = 0; i < menuitemstoverify.length; i++) {
			myworkordersscreen.isMenuItemForSelectedWOExists(menuitemstoverify[i]);
		}
		myworkordersscreen.clickDetailspopupMenu();
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickCancelWizard();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 18439:Change customer for invoice
	@Test(testName = "Test Case 18439:Change customer for invoice", description = "Change customer for invoice")
	public void testChangeCustomerForInvoice() {

		final String VIN = "QWERTYUI123";
		final String _make = "BMW";
		final String _model = "323i U";
		final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";
		final String stock = "Stock1";
		final String _ro = "123";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		//Create WO1
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);
		String wonumber1 = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		//vehiclescreen.setYear(_year);

		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicedetailsScreen.saveSelectedServiceDetails();
		selectedservicedetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		selectedservicedetailsScreen.saveSelectedServiceDetails();
		selectedservicedetailsScreen = servicesscreen.openCustomServiceDetails("VPS1");
		selectedservicedetailsScreen.saveSelectedServiceDetails();

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizardAndAcceptAlert();

		//Test case

		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("first123");
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

	//Test Case 10498:Regression test: test bug with crash on "Copy Vehicle"
	@Test(testName = "Test Case 10498:Regression test: test bug with crash on \"Copy Vehicle\"", description = "Regression test: test bug with crash on Copy Vehicle")
	public void testBugWithCrashOnCopyVehicle() {

		final String vin = "SHDHBEVDHDHDGDVDG";
		final String vehicleinfo = "Black, BMW, 323i U";

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		homescreen = new RegularHomeScreen();
		RegularCarHistoryScreen carhistoryscreen = homescreen.clickCarHistoryButton();
		//carhistoryscreen.searchCar(vin);

		RegularCarHistoryWOsAndInvoicesScreen regularCarHistoryWOsAndInvoicesScreen = carhistoryscreen.clickCarHistoryRowByVehicleInfo(vehicleinfo);
		regularCarHistoryWOsAndInvoicesScreen.clickCarHistoryMyWorkOrders();
		RegularMyWorkOrdersScreen myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.selectFirstOrder();
		myworkordersscreen.selectCopyVehicle();
		customersscreen = new RegularCustomersScreen();
		//customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.cancelWizard();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.clickBackButton();

		carhistoryscreen.clickBackButton();
		carhistoryscreen.clickHomeButton();
	}

	//Test Case 16239:Copy Inspections
	@Test(testName = "Test Case 16239:Copy Inspections", description = "Copy Inspections")
	public void testCopyInspections() {

		final String VIN = "QWERTYUI123";
		final String _make = "Audi";
		final String _model = "A1";
		//final String _year = "2014";
		final String _color = "Black";
		final String mileage = "55555";
		final String fueltanklevel = "25";
		final String _type = "New";
		final String stock = "Stock1";
		final String _ro = "123";

		final String visualjetservice = "Price Adjustment";

		//Services variables
		final String _dye_price = "$10.00";
		final String _dye_adjustments = "$0.50";
		// =======================================
		final String _disk_ex_srvc_price = "$100.00";
		final String _disk_ex_srvc_adjustment = "For_SP_Cl";
		final String _disk_ex_srvc_adjustment_value = "%-5.000";
		final String _disk_ex_srvc_adjustment_value_ed = "-$5.00";
		// ======================================
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String _price = "$100.00";
		final String _discaunt_us = "Discount 10-20$";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.FOR_COPY_INSP_INSPTYPE);
		vehiclescreen.setVIN(VIN);
		/*Assert.assertTrue(.findElement(
				MobileBy.name("The VIN is invalid.")).isDisplayed());
		.findElement(
				MobileBy.name("Close"))
				.click();*/
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		//vehiclescreen.setYear(_year);
		//Thread.sleep(2000);
		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);
		final String inspNumber = vehiclescreen.getInspectionNumber();
		RegularVisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		visualinteriorscreen.tapInterior();

		visualinteriorscreen = visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR,  ScreenNamesConstants.FUTURE_AUDI_CAR);
		visualinteriorscreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_AUDI_CAR);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		Helpers.tapRegularCarImage();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$180.50");

		visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualinteriorscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		RegularVisualInteriorScreen.tapExterior();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$250.50");

		visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR,  ScreenNamesConstants.FUTiRE_JET_CAR);
		visualinteriorscreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTiRE_JET_CAR);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectSubService(_discaunt_us);
		Helpers.tapRegularCarImage();
		visualinteriorscreen.selectService(visualjetservice);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$240.50");


		RegularQuestionsScreen questionsscreen = visualinteriorscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.TEST_SECTION);
		questionsscreen.setEngineCondition("Really Bad");
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.setJustOnePossibleAnswer("One");

		questionsscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.FOLLOW_UP_REQUESTED);
		questionsscreen.swipeScreenUp();
		questionsscreen.drawRegularSignature();
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.setSampleQuestion("Answers 1");

		questionsscreen.selectNextScreen(WizardScreenTypes.QUESTIONS,"BATTERY PERFORMANCE");
		questionsscreen.setBetteryTerminalsAnswer("Immediate Attention Required");
		questionsscreen.swipeScreenUp();
		questionsscreen.setCheckConditionOfBatteryAnswer("Immediate Attention Required");

		questionsscreen.swipeScreenUp();
		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.selectTaxPoint("Test Answer 1");

		//Select services
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), _dye_price);
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), _dye_adjustments);
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================
		//servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), _disk_ex_srvc_price);
		selectedservicescreen.clickAdjustments();
		Assert.assertEquals(selectedservicescreen.getAdjustmentValue(_disk_ex_srvc_adjustment),
				_disk_ex_srvc_adjustment_value);
		selectedservicescreen.selectAdjustment(_disk_ex_srvc_adjustment);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), _disk_ex_srvc_adjustment_value_ed);
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue("2.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================
		servicesscreen.selectService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
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

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));

		selectedServicesScreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickCopyInspection();
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getMake(), _make);
		Assert.assertEquals(vehiclescreen.getModel(), _model);

		visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualinteriorscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
		visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, ScreenNamesConstants.FUTURE_AUDI_CAR);
		visualinteriorscreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_AUDI_CAR);
		visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualinteriorscreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
		visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR,  ScreenNamesConstants.FUTiRE_JET_CAR);
		visualinteriorscreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTiRE_JET_CAR);
		visualinteriorscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, ScreenNamesConstants.FOLLOW_UP_REQUESTED);
		visualinteriorscreen.waitVisualScreenLoaded(ScreenNamesConstants.FOLLOW_UP_REQUESTED);
		SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen();
		questionsscreen.swipeScreenUp();
		Assert.assertTrue(singlepageinspectionscreen.isSignaturePresent());
		questionsscreen = questionsscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, "BATTERY PERFORMANCE");
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		Assert.assertTrue(singlepageinspectionscreen.isAnswerPresent("Test Answer 1"));
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DYE_SERVICE));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_TAX_SERVICE));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));

		selectedServicesScreen.clickSave();
		Helpers.getAlertTextAndAccept();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspNumber), "$837.99");
		myinspectionsscreen.clickHomeButton();
	}

	//Test Case 16507:Create inspection from WO
	@Test(testName = "Test Case 16507:Create inspection from WO", description = "Create inspection from WO")
	public void testCreateInspectionFromWO() {

		final String VIN = "QWERTYUI123";
		final String _make = "Buick";
		final String _model = "Electra";
		//final String _year = "2014";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";
		final String stock = "Stock1";
		final String _ro = "123";
		final String pricevalue = "0";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		//Create WO1
		Instant star = Instant.now();
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE) ;
		vehiclescreen.setVIN(VIN);

		String wonumber1 = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		//vehiclescreen.setYear(_year);

		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);ordersummaryscreen.clickSave();

		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();

		//Test case
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber1), PricesCalculations.getPriceRepresentation(pricevalue));
		myworkordersscreen.selectWorkOrderNewInspection(wonumber1);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 26266:Create Invoice with two WOs and copy vehicle", description = "Create Invoice with two WOs and copy vehicle")
	public void testCreateInvoiceWithTwoWOsAndCopyVehicle() {

		final String VIN = "QWERTYUI123";
		final String _make = "Buick";
		final String _model = "Electra";
		//final String _year = "2014";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";
		final String stock = "Stock1";
		final String _ro = "123";
		final String[] vehicleparts = { "Cowl, Other", "Hood", };

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		//Create WO1
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);
		String wonumber1 = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		//vehiclescreen.setYear(_year);
		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizardAndAcceptAlert();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehiclescreen.getMake(), _make);
		Assert.assertEquals(vehiclescreen.getModel(), _model);
		//Assert.assertEquals(vehiclescreen.getYear(), _year);
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Yes"))
				.click();
		ordersummaryscreen.selectDefaultInvoiceType();
		RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
		invoiceinfoscreen.setPO("23");
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

	//Test Case 23401:Test 'Change customer' option for inspection
	@Test(testName = "Test Case 23401:Test 'Change customer' option for inspection", description = "Test 'Change customer' option for inspection")
	public void testChangeCustomerOptionForInspection() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		//resrtartApplication();
		//RegularMainScreen mainscreen = new RegularMainScreen();
		//RegularHomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_CHANGE_INSPTYPE);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany("USG");
		RegularQuestionsScreen questionsscreen = claimscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
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

	//Test Case 23402:Test 'Change customer' option for Inspection as change 'Wholesale' to 'Retail' and vice versa'
	@Test(testName = "Test Case 23402:Test 'Change customer' option for Inspection as change 'Wholesale' to 'Retail' and vice versa'", description = "Test 'Change customer' option for Inspection as change 'Wholesale' to 'Retail' and vice versa'")
	public void testChangeCustomerOptionForInspectionAsChangeWholesaleToRetailAndViceVersa() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_CHANGE_INSPTYPE);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany("USG");
		RegularQuestionsScreen questionsscreen = claimscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspection(inspectionnumber);
		myinspectionsscreen.clickChangeCustomerpopupMenu();
		myinspectionsscreen.customersPopupSwitchToRetailMode();
		myinspectionsscreen.selectCustomer(iOSInternalProjectConstants.RETAIL_CUSTOMER);

		myinspectionsscreen.clickHomeButton();
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

	//Test Case 23421:Test 'Change customer' option for Inspections based on type with preselected Companies
	@Test(testName = "Test Case 23421:Test 'Change customer' option for Inspections based on type with preselected Companies", description = "Test 'Change customer' option for Inspections based on type with preselected Companies")
	public void testChangeCustomerOptionForInspectionsBasedOnTypeWithPreselectedCompanies() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";

		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVisualInteriorScreen visualInteriorScreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.TYPEWITHPRESELECTEDCOMPANIES_INSPTYPE);
		RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany("USG");
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

	//Test Case 23422:Test 'Change customer' option for Work Order
	@Test(testName = "Test Case 23422:Test 'Change customer' option for Work Order", description = "Test 'Change customer' option for Work Order")
	public void testChangeCustomerOptionForWorkOrder()  {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService("3/4\" - Penny Size");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");

		ordersummaryscreen.saveWizard();
		myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancel();
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 23423:Test 'Change customer' option for WO based on type with preselected Companies
	@Test(testName = "Test Case 23423:Test 'Change customer' option for WO based on type with preselected Companies", description = "Test 'Change customer' option for WO based on type with preselected Companies")
	public void testChangeCustomerOptionForWOBasedOnTypeWithPreselectedCompanies() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";

		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_WITH_PRESELECTED_CLIENTS);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService("3/4\" - Penny Size");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("4");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("4"));
		ordersummaryscreen.saveWizard();

		Helpers.waitABit(45*1000);
		//testlogger.log(LogStatus.INFO, wonumber);
		myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancel();
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 23424:Test 'Change customer' option for WO as change 'Wholesale' to 'Retail' and vice versa'
	@Test(testName = "Test Case 23424:Test 'Change customer' option for WO as change 'Wholesale' to 'Retail' and vice versa'", description = "Test 'Change customer' option for WO as change 'Wholesale' to 'Retail' and vice versa'")
	public void testChangeCustomerOptionForWOAsChangeWholesaleToRetailAndViceVersa() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService("3/4\" - Penny Size");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("4");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("4"));
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

	//Test Case 23428:'Block for the same VIN' is ON. Verify 'Duplicate VIN ' message when create 2 WO with one VIN
	@Test(testName = "Test Case 23428:'Block for the same VIN' is ON. Verify 'Duplicate VIN ' message when create 2 WO with one VIN", description = "'Block for the same VIN' is ON. Verify 'Duplicate VIN ' message when create 2 WO with one VIN")
	public void testBlockForTheSameVINIsONVerifyDuplicateVINMessageWhenCreate2WOWithOneVIN() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("4");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("4"));
		ordersummaryscreen.clickSave();
		ordersummaryscreen.waitForCustomWarningMessage(String.format(AlertsCaptions.ALERT_YOU_CANT_CREATE_WORK_ORDER_BECAUSE_VIN_EXISTS,
				WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON.getWorkOrderTypeName(), VIN), "Cancel");
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 23459:'Block for the same Services' is ON. Verify 'Duplicate services message' when create 2 WO with one service
	@Test(testName = "Test Case 23459:'Block for the same Services' is ON. Verify 'Duplicate services message' when create 2 WO with one service", description = "'Block for the same Services' is ON. Verify 'Duplicate services message' when create 2 WO with one service")
	public void testBlockForTheSameServicesIsONVerifyDuplicateServicesMessageWhenCreate2WOWithOneService() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.selectSubService("AMoneyService_AdjustHeadlight");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingCancel();

		//Helpers.text_exact("Cancel").click();
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 23432:WO: Test 'Edit' option of 'Duplicate services' message for WO
	@Test(testName="Test Case 23432:WO: Test 'Edit' option of 'Duplicate services' message for WO", description = "'WO: Test 'Edit' option of 'Duplicate services' message for WO")
	public void testEditOptionOfDuplicateServicesMessageForWO() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.selectSubService("AMoneyService_AdjustHeadlight");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingEdit();
		ordersummaryscreen.swipeScreenLeft();
		servicesscreen = ordersummaryscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedServicesScreen.removeSelectedService("AMoneyService_AdjustHeadlight");
		Assert.assertFalse(selectedServicesScreen.checkServiceIsSelected("AMoneyService_AdjustHeadlight"));
		selectedServicesScreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 23433:WO: Test 'Override' option of 'Duplicate services' message for WO.
	@Test(testName="Test Case 23433:WO: Test 'Override' option of 'Duplicate services' message for WO", description = "'WO: Test 'Override' option of 'Duplicate services' message for WO.")
	public void testOverrideOptionOfDuplicateServicesMessageForWO() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.selectSubService("AMoneyService_AdjustHeadlight");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingOverride();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 23434:WO: Test 'Cancel' option of 'Duplicate services' message for WO
	@Test(testName="Test Case 23434:WO: Test 'Cancel' option of 'Duplicate services' message for WO", description = "'WO: Test 'Cancel' option of 'Duplicate services' message for WO.")
	public void testCancelOptionOfDuplicateServicesMessageForWO() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(VIN);
		String wonumber = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.selectSubService("AMoneyService_AdjustHeadlight");

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("4");
		ordersummaryscreen.clickSave();
		ordersummaryscreen.closeDublicaterServicesWarningByClickingCancel();
		Assert.assertFalse(myworkordersscreen.woExists(wonumber));

		myworkordersscreen.clickHomeButton();
	}

	//Test Case 23966:Inspections: Test saving inspectiontypes with three matrix
	//Test Case 24022:Inspections: Test saving inspection copied from one with 3 matrix price
	@Test(testName="Test Case 23966:Inspections: Test saving inspectiontypes with three matrix, "
			+ "Test Case 24022:Inspections: Test saving inspection copied from one with 3 matrix price", description = "'Inspections: Test saving inspectiontypes with three matrix")
	public void testSavingInspectionsWithThreeMatrix() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";
		final String _type = "New";
		final String _po = "1111";

		final String _pricematrix1 = "Hood";
		final String _pricematrix2 = "Left Roof Rail";
		final String _pricematrix3 = "Back Glass";
		final String _pricematrix4 = "Front Bumper";
		final String _pricematrix5 = "Roof";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.VITALY_TEST_INSPTYPE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		vehiclescreen.setType(_type);
		vehiclescreen.setPO(_po);
		String inspnumber = vehiclescreen.getInspectionNumber();

		RegularClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany("USG");
		claimscreen.setClaim("QWERTY");
		claimscreen.setAccidentDate();
		RegularVisualInteriorScreen visualinteriorscreen = claimscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		RegularVisualInteriorScreen.tapInteriorWithCoords(100, 100);
		RegularVisualInteriorScreen.tapInteriorWithCoords(150, 150);
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);

		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$520.00");

		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		visualinteriorscreen = questionsscreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		RegularVisualInteriorScreen.tapExteriorWithCoords(100, 100);
		visualinteriorscreen.selectService(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$570.00");
		Assert.assertEquals(visualinteriorscreen.getSubTotalPrice(), "$100.00");

		RegularPriceMatrixScreen pricematrix = visualinteriorscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix1);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("SR_S5_Mt_Money");
		vehiclePartScreen.selectDiscaunt("SR_S5_Mt_Money_DE_TE");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue("3");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(vehiclePartScreen.getDiscauntPriceAndValue(iOSInternalProjectConstants.SR_S1_MONEY), "$2,000.00 x 3.00");
		vehiclePartScreen.saveVehiclePart();
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix2);
		vehiclePartScreen.setSizeAndSeverity(RegularPriceMatrixScreen.NKL_SIZE, "VERY LIGHT");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.MATRIX_LABOR);
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix3);
		vehiclePartScreen.setSizeAndSeverity(RegularPriceMatrixScreen.DIME_SIZE, "LIGHT");
		vehiclePartScreen.saveVehiclePart();

		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix4);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Little Service");
		vehiclePartScreen.selectDiscaunt("Disc_Ex_Service1");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		vehiclePartScreen.setTime("1");
		vehiclePartScreen.saveVehiclePart();

		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Roof");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();

		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen();
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Front Bumper");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();

		pricematrix = servicesscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, "Test matrix33");
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix5);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY);
		vehiclePartScreen.saveVehiclePart();

		servicesscreen = pricematrix.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("SR_S2_MoneyDisc_TE");

		visualinteriorscreen = servicesscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, "New_Test_Image");
		visualinteriorscreen.clickServicesToolbarButton();
		visualinteriorscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		Helpers.tapRegularCarImage();
		visualinteriorscreen.selectService(iOSInternalProjectConstants.WHEEL_REPAIR_SERVICE);
		Assert.assertEquals(visualinteriorscreen.getTotalPrice(), "$13,145.50");
		Assert.assertEquals(visualinteriorscreen.getSubTotalPrice(), "$80.00");

		visualinteriorscreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		vehiclescreen = new RegularVehicleScreen();
		pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix1, "$6,100.00"));
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix2, "$75.00"));

		pricematrix.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.MATRIX_LABOR);
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix3, "$25.00"));
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix4, "$1,105.50"));
		pricematrix.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, "Test matrix33");
		Assert.assertTrue(pricematrix.isPriceMatrixContainsPriceValue(_pricematrix5, "$2,000.00"));
		servicesscreen.cancelWizard();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreen = new RegularVehicleScreen();
		String copiedinspnumber = vehiclescreen.getInspectionNumber();
		servicesscreen.saveWizard();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(copiedinspnumber));
		myinspectionsscreen.clickHomeButton();
	}

	//Test Case 24075:SR: Test that selected services on SR are copied to Inspection based on SR
	@Test(testName="Test Case 24075:SR: Test that selected services on SR are copied to Inspection based on SR", description = "Test that selected services on SR are copied to Inspection based on SR")
	public void testThatSelectedServicesOnSRAreCopiedToInspectionBasedOnSR() {
		final String VIN  = "1D7HW48NX6S507810";

		/*RegularSettingsScreen settingscreen =  homescreen.clickSettingsButton();
		settingscreen.setShowAllServicesOn();
		homescreen = settingscreen.clickHomeButton();*/
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		homescreen = customersscreen.clickHomeButton();
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		customersscreen = new RegularCustomersScreen();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		RegularSelectedServiceDetailsScreen servicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		servicedetailsscreen.setServiceQuantityValue("14");
		servicedetailsscreen.saveSelectedServiceDetails();
		//servicesscreen.setSelectedServiceRequestServicePrice(iOSInternalProjectConstants.DYE_SERVICE, "14");

		RegularQuestionsScreen questionsscreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
		String inspectnumber = vehiclescreen.getInspectionNumber();
		servicesscreen = servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.DYE_SERVICE, "$10.00 x 14.00"));
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.VPS1_SERVICE, "%20.000"));
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(iOSInternalProjectConstants.WHEEL_SERVICE, "$70.00 x 1.00"));

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

	//Test Case 24657:WO: Test that auto-saved WO is created correctly
	//Test Case 23435:WO: Test 'Continue' option of 'Auto-saved' WO
	//Test Case 23436:WO: Test 'Discard' option of 'Auto-saved' WO
	@Test(testName="Test Case 24657:WO: Test that auto-saved WO is created correctly, " +
			"Test Case 23435:WO: Test 'Continue' option of 'Auto-saved' WO, " +
			"Test Case 23436:WO: Test 'Discard' option of 'Auto-saved' WO", description = "WO: Test that auto-saved WO is created correctly")
	public void testThatAutoSavedWOIsCreatedCorrectly() {
		final String VIN  = "1FMFU18L53LC13897";

		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen();
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Ford", "Expedition", "2003");
		String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		Helpers.waitABit(30*1000);
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		RegularMainScreen mainscreen = new RegularMainScreen();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertTrue(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.selectContinueWorkOrder("Auto Save");
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderNumber(), wonumber);

		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		mainscreen = new RegularMainScreen();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertTrue(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.selectDiscardWorkOrder("Auto Save");
		Assert.assertFalse(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.clickHomeButton();
	}

	//Test Case 21578:SR: Add appointment to Service Request
	@Test(testName = "Test Case 21578:SR: Add appointment to Service Request", description = "SR: Add appointment to Service Request")
	public void testSRAddAppointmentToServiceRequest() {
		final String VIN = "QWERTYUI123";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);

		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		vehiclescreen.setVIN(VIN);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		//Helpers.swipeScreenUp();
		Helpers.drawRegularQuestionsSignature();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Close"))
				.click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSave();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();
		final String fromapp = servicerequestsscreen.getFromAppointmetValue();
		final String toapp = servicerequestsscreen.getToAppointmetValue();

		servicerequestsscreen.setSubjectAppointmet("SR-APP");
		servicerequestsscreen.setAddressAppointmet("Maidan");
		servicerequestsscreen.setCityAppointmet("Kiev");
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		homescreen = servicerequestsscreen.clickHomeButton();
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		servicerequestsscreen.clickHomeButton();
	}

	//Test Case 24677:SR: Verify 'Summary' action for appointment on SR's calendar
	@Test(testName = "Test Case 24677:SR: Verify 'Summary' action for appointment on SR's calendar", description = "SR: Verify 'Summary' action for appointment on SR's calendar")
	public void testSRVerifySummaryActionForAppointmentOnSRsCalendar() {
		final String VIN = "QWERTYUI123";
		final String srappsubject = "SR-APP";
		final String srappaddress = "Maidan";
		final String srappcity = "Kiev";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);

		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		vehiclescreen.setVIN(VIN);
		/*Assert.assertTrue(.findElement(
				MobileBy.name("The VIN is invalid.")).isDisplayed());
		.findElement(MobileBy.name("Close")).click();*/

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		//Helpers.swipeScreenUp();
		Helpers.drawRegularQuestionsSignature();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Close"))
				.click();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSave();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);

		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();
		final String fromapp = servicerequestsscreen.getFromAppointmetValue();
		final String toapp = servicerequestsscreen.getToAppointmetValue();

		servicerequestsscreen.setSubjectAppointmet(srappsubject);
		servicerequestsscreen.setAddressAppointmet(srappaddress);
		servicerequestsscreen.setCityAppointmet(srappcity);
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		homescreen = servicerequestsscreen.clickHomeButton();
		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();

		//String summaryapp = servicerequestsscreen.getSummaryAppointmentsInformation();
		String expectedsummaryapp = "Subject: " + srappsubject + ", In: " + fromapp + ", Out: " + toapp + ", Location: " + srappaddress
				+ ", " + srappcity + ", US";
		Assert.assertTrue(servicerequestsscreen.isSRSummaryAppointmentsInformation());

		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 25403:WO Regular: Verify that only assigned services on Matrix Panel is available as additional services", description = "WO Regular: Verify that only assigned services on Matrix Panel is available as additional services")
	public void testWOVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices() {

		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_PRICE_MATRIX);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mercedes-Benz", "Sprinter Passenger", "2014");
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		servicesscreen.selectPriceMatrices("VP1 zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Test service zayats"));
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Wheel"));
		vehiclePartScreen.saveVehiclePart();
		vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Dye"));
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();
		servicesscreen = new RegularServicesScreen();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX));
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		servicesscreen =  questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		servicesscreen.cancelWizard();

		myworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 25011:Inspections: verify that only assigned services on Matrix Panel is available as additional services", description = "Inspections: verify that only assigned services on Matrix Panel is available as additional services")
	public void testInspectionsVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices() {

		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();

		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mercedes-Benz", "Sprinter Passenger", "2014");
		String inspnum = vehiclescreen.getInspectionNumber();
		RegularPriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.PRICE_MATRIX_ZAYATS);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Test service zayats"));
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Wheel"));
		vehiclePartScreen.saveVehiclePart();
		vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		Assert.assertTrue(vehiclePartScreen.isDiscauntPresent("Dye"));
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.saveVehiclePart();
		RegularQuestionsScreen questionsscreen = pricematrix.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");

		questionsscreen.saveWizard();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspnum));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 25421:WO: Verify that on Invoice 'Summary' main service of the panel is displayed as first then additional services", description = "WO HD: Verify that on Invoice 'Summary' main service of the panel is displayed as first then additional services")
	public void testWOVerifyThatOnInvoiceSummaryMainServiceOfThePanelIsDisplayedAsFirstThenAdditionalServices() {

		final String VIN  = "2C3CDXBG2EH174681";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Charger", "2014");
		String wonum = vehiclescreen.getInspectionNumber();
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		servicesscreen.selectSubService("Test service price matrix");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		pricematrix.selectPriceMatrix("Price Matrix Zayats");
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.saveVehiclePart();

		vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");

		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickBackButton();
		pricematrix.clickBackButton();
		servicesscreen = new RegularServicesScreen();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected("Test service price matrix"));
		RegularOrderSummaryScreen ordersummaryscreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForApprove(wonum);
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.clickApproveButton();
		myworkordersscreen.selectWorkOrderForAction(wonum);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("12345");
		final String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		homescreen = myworkordersscreen.clickHomeButton();

		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickSummaryPopup();
		Assert.assertTrue(myinvoicesscreen.isSummaryPDFExists());
		myinvoicesscreen.clickHomeButton();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26054:WO Monitor: Regular - Create WO for monitor", description = "WO Monitor: Regular - Create WO for monitor")
	public void testWOMonitorCreateWOForMonitor() {

		final String VIN  = "1D3HV13T19S825733";

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
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();

		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26091:WO Monitor: Regular - Verify that it is not possible to change Service Status before Start Service", description = "WO Monitor: Regular - Verify that it is not possible to change Service Status before Start Service")
	public void testWOMonitorVerifyThatItIsNotPossibleToChangeServiceStatusBeforeStartService() {

		final String VIN  = "1D3HV13T19S825733";

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
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
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
		ordermonitorscreen.clickServiceStatusCell();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You must start the service before you can change its status."));
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		ordermonitorscreen.setCompletedServiceStatus();
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.WHEEL_SERVICE), "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26092:WO Monitor: Regular - Verify that it is not possible to change Phase Status before Start phase", description = "WO Monitor: HD - Verify that it is not possible to change Phase Status before Start phase")
	public void testWOMonitorVerifyThatItIsNotPossibleToChangePhaseStatusBeforeStartPhase() {

		final String VIN  = "1D3HV13T19S825733";

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
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
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

		Assert.assertTrue(ordermonitorscreen.isRepairPhaseExists());
		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonExists());
		ordermonitorscreen.clicksRepairPhaseLine();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You must start the phase before you can change its status."));
		ordermonitorscreen.clickStartPhaseButton();

		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertFalse(ordermonitorscreen.isStartPhaseButtonExists());
		Assert.assertTrue(ordermonitorscreen.isServiceStartDateExists());

		ordermonitorscreen.clickServiceStatusCell();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You cannot change the status of services for this phase. You can only change the status of the whole phase."));
		ordermonitorscreen.clickServiceDetailsDoneButton();

		ordermonitorscreen.clicksRepairPhaseLine();
		ordermonitorscreen.clickCompletedPhaseCell();

		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DISC_EX_SERVICE1), "Completed");
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DYE_SERVICE), "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26094:WO Monitor: Regular - Verify that Start date is set when Start Service", description = "WO Monitor: Regular - Verify that Start date is set when Start Service")
	public void testWOMonitorVerifyThatStartDateIsSetWhenStartService() {

		final String VIN  = "1D3HV13T19S825733";

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
		Assert.assertFalse(ordermonitorscreen.isServiceStartDateExists());
		ordermonitorscreen.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isServiceStartDateExists());
		ordermonitorscreen.clickServiceDetailsDoneButton();

		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	//@Test(testName="Test Case 26016:WO Monitor: Regular - Verify that for % service message about change status is not shown", description = "WO Monitor: Regular - Verify that for % service message about change status is not shown")
	public void testWOMonitorVerifyThatForPercentServiceMessageAboutChangeStatusIsNotShown() {

		final String VIN  = "1D3HV13T19S825733";

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
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
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
		ordermonitorscreen.selectPanelToChangeStatus(iOSInternalProjectConstants.VPS1_SERVICE);
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

	@Test(testName="Test Case 28473:Invoices: HD - Verify that red 'A' icon is present for invoice with customer approval ON and no signature",
			description = "Invoices: Regular - Verify that red 'A' icon is present for invoice with customer approval ON and no signature")
	public void testRegularVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalONAndNoSignature() {

		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";

		//RegularMainScreen mainscreen = new RegularMainScreen();
		//homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName="Test Case 28476:Invoices: HD&Regular - Verify that grey 'A' icon is present for invoice with customer approval OFF and no signature",
			description = "Invoices: Regular - Verify that grey 'A' icon is present for invoice with customer approval OFF and no signature")
	public void testRegularVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalOFFAndNoSignature() {

		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";
		//Inicialize();
		//RegularMainScreen mainscreen = new RegularMainScreen();
		//homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName="Test Case 28478:Invoices: Regular - Verify that 'A' icon is not present for invoice when signature exists",
			description = "Invoices: Regular - Verify that 'A' icon is not present for invoice when signature exists")
	public void testRegularVerifyThatAIconIsNotPresentForInvoiceWhenSignatureExists() {

		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		RegularServicesScreen servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumberapproveon = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenumberapproveon));
		myinvoicesscreen.selectInvoiceForApprove(invoicenumberapproveon);
		myinvoicesscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.clickApproveButton();
		approveinspscreen.drawApprovalSignature();
		//approveinspscreen.clickDoneButton();
		myinvoicesscreen = new RegularMyInvoicesScreen();

		Assert.assertFalse(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumberapproveon));
		myinvoicesscreen.clickHomeButton();

		myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval OFF
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		servicesscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumbeapprovaloff = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen.clickHomeButton();
		myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumbeapprovaloff);
		myinvoicesscreen.selectInvoiceForApprove(invoicenumbeapprovaloff);
		myinvoicesscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.clickApproveButton();
		approveinspscreen.drawApprovalSignature();
		myinvoicesscreen = new RegularMyInvoicesScreen();
		Assert.assertFalse(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumbeapprovaloff));
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName="Test Case 29022:SR: Regular - Verify that Reject action is displayed for SR in Status Scheduled (Insp or WO) and assign for Tech",
			description = "Test Case 29022:SR: Regular - Verify that Reject action is displayed for SR in Status Scheduled (Insp or WO) and assign for Tech")
	public void testSRRegularVerifyThatRejectActionIsDisplayedForSRInStatusScheduledInspOrWOAndAssignForTech() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		//Create first SR
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("No"))
				.click();

		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber1 = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber1);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();
		//Create second SR
		servicerequestsscreen = new RegularServiceRequestsScreen();
		vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();

		String srnumber2 = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber2), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber2);
		servicerequestsscreen.selectRejectAction();
		Helpers.acceptAlert();

		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 29029:SR: Regular - Verify that Reject action is displayed for SR in Status OnHold (Insp or WO) and assign for Tech",
			description = "Test Case 29029:SR: Regular - Verify that Reject action is displayed for SR in Status OnHold (Insp or WO) and assign for Tech")
	public void testSRRegularVerifyThatRejectActionIsDisplayedForSRInStatusOnHoldInspOrWOAndAssignForTech() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		questionsscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "On Hold");

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
			if (!servicerequestsscreen.getServiceRequestStatus(srnumber).equals("On Hold")) {
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

	@Test(testName="Test Case 29082:SR: Regular - Verify that Reject action is not displayed for SR in Status OnHold (Insp or WO) and not assign for Tech",
			description = "Test Case 29082:SR: Regular - Verify that Reject action is not displayed for SR in Status OnHold (Insp or WO) and not assign for Tech")
	public void testSRRegularVerifyThatRejectActionIsNotDisplayedForSRInStatusOnHoldInspOrWOAndNotAssignForTech() {

		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";

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
		srlistwebpage.setServiceRequestVIN(VIN);
		srlistwebpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		srlistwebpage.clickDoneButton();

		srlistwebpage.saveNewServiceRequest();
		srlistwebpage.acceptFirstServiceRequestFromList();

		//DriverBuilder.getInstance().getDriver().quit();
		//DriverBuilder.getInstance().getAppiumDriver().closeApp();
		//DriverBuilder.getInstance().getAppiumDriver().launchApp();
		//RegularMainScreen mainscr = new RegularMainScreen();
		//mainscr.updateDatabase();
		//RegularHomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
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

	@Test(testName="Test Case 29083:SR: Regular - Verify that Reject action is not displayed for SR in Status Scheduled (Insp or WO) and not assign for Tech",
			description = "SR: Regular - Verify that Reject action is not displayed for SR in Status Scheduled (Insp or WO) and not assign for Tech")
	public void testSRRegularVerifyThatRejectActionIsNotDisplayedForSRInStatusScheduledInspOrWOAndNotAssignForTech() {

		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";
		final String[] servicestoadd = { "VPS1", "Dye" };
		final String[] servicestoadd2 = { "Oksi_Service_PP_Panel", "Oksi_Service_PP_Vehicle" };

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
		srlistwebpage.setServiceRequestVIN(VIN);
		srlistwebpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		srlistwebpage.clickDoneButton();
		srlistwebpage.addServicesToServiceRequest(servicestoadd);
		srlistwebpage.saveNewServiceRequest();
		String srnumber = srlistwebpage.getFirstInTheListServiceRequestNumber();
		srlistwebpage.acceptFirstServiceRequestFromList();

		DriverBuilder.getInstance().getDriver().quit();

		//homescreen = new RegularHomeScreen();
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
		srlistwebpage.setServiceRequestVIN(VIN);
		srlistwebpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		srlistwebpage.clickDoneButton();
		srlistwebpage.addServicesToServiceRequest(servicestoadd2);
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

	@Test(testName = "Test Case 33165:WO: Regular - Not multiple Service with required Panels is added one time to WO after selecting",
			description = "WO: Regular - Not multiple Service with required Panels is added one time to WO after selecting")
	public void testWORegularNotMultipleServiceWithRequiredPanelsIsAddedOneTimeToWOAfterSelecting()  {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();

		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart("Back Glass");
		selectedServiceDetailsScreen.selectVehiclePart("Deck Lid");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		String alerttext = selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
		Assert.assertTrue(alerttext.contains("You can add only one service '" + iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE + "'"));

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart("Back Glass");
		selectedServiceDetailsScreen.selectVehiclePart("Deck Lid");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		alerttext = selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
		Assert.assertTrue(alerttext.contains("You can add only one service '" + iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE + "'"));
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		Assert.assertEquals(selectedServicesScreen.getNumberOfSelectedServices(), 1);
		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 34562:WO: Verify that Bundle Items are shown when create WO from inspection", description = "WO: Verify that Bundle Items are shown when create WO from inspection")
	public void testRegularWOVerifyThatBundleItemsAreShownWhenCreateWOFromInspection() {

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
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen();
		selectedservicedetailsscreen.changeAmountOfBundleService("70");
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
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
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		Assert.assertTrue(selectedservicebundlescreen.checkBundleIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(selectedservicebundlescreen.checkBundleIsNotSelected(iOSInternalProjectConstants.DYE_SERVICE));
		selectedservicebundlescreen.clickServicesIcon();
		Assert.assertTrue(selectedservicebundlescreen.isBundleServiceExists(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(selectedservicebundlescreen.isBundleServiceExists(iOSInternalProjectConstants.DYE_SERVICE));
		selectedservicebundlescreen.clickCloseServicesPopup();
		selectedservicebundlescreen.clickCancel();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}


	@Test(testName="Test Case 31743:SR: Regular - Verify that when option 'Allow to close SR' is set to ON action 'Close' is shown for selected SR on status 'Scheduled' or 'On-Hold'",
			description = "SR Regular - Verify that when option 'Allow to close SR' is set to ON action 'Close' is shown for selected SR on status 'Scheduled' or 'On-Hold'")
	public void testSRRegularVerifyThatWhenOptionAllowToCloseSRIsSetToONActionCloseIsShownForSelectedSROnStatusScheduledOrOnHold() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");

		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.clickCancel();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 31748:SR: Regular - Verify that when option 'Allow to close SR' is set to OFF action 'Close' is not shown for selected SR on status 'Scheduled' or 'On-Hold'",
			description = "SR: Regular - Verify that when option 'Allow to close SR' is set to OFF action 'Close' is not shown for selected SR on status 'Scheduled' or 'On-Hold'")
	public void testSRRegularVerifyThatWhenOptionAllowToCloseSRIsSetToOFFActionCloseIsNotShownForSelectedSROnStatusScheduledOrOnHold() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.saveWizard();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.clickCancel();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 31749:SR: Regular - Verify that alert message is shown when select 'Close' action for SR - press No alert message is close",
			description = "SR: Regular - Verify that alert message is shown when select 'Close' action for SR - press No alert message is close")
	public void testSRRegularVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressNoAlertMessageIsClose() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);

		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 31750:SR: Regular - Verify that alert message is shown when select 'Close' action for SR - press Yes list of status reasons is shown",
			description = "SR: Regular - Verify that alert message is shown when select 'Close' action for SR - press Yes list of status reasons is shown")
	public void testSRRegularVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressYesListOfStatusReasonsIsShown() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.clickCancelCloseReasonDialog();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 31752:SR: Regular - Verify that when Status reason is selected Question section is shown in case it is assigned to reason on BO",
			description = "SR: Regular - Verify that when Status reason is selected Question section is shown in case it is assigned to reason on BO")
	public void testSRRegularVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsShownInCaseItIsAssignedToReasonOnBO() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.selectUIAPickerValue("All work is done. Answer questions");
		servicerequestsscreen.clickDoneCloseReasonDialog();
		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.answerQuestion2("A3");
		servicerequestsscreen.clickCloseSR();
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 31753:SR: Regular - Verify that when Status reason is selected Question section is not shown in case it is not assigned to reason on BO",
			description = "SR: Regular - Verify that when Status reason is selected Question section is not shown in case it is not assigned to reason on BO")
	public void testSRRegularVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsNotShownInCaseItIsNotAssignedToReasonOnBO() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), "Scheduled");
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.selectDoneReason("All work is done. No Questions");
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 30083:SR: Regular - Verify that when create WO from SR message that vehicle parts are required is shown for appropriate services",
			description = "SR: Regular - Verify that when create WO from SR message that vehicle parts are required is shown for appropriate services")
	public void testSRRegularVerifyThatWhenCreateWOFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_WO_ONLY);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		//servicesscreen.selectService(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();

		servicerequestsscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		String wonumber = vehiclescreen.getWorkOrderNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE));
		//Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_DISC_20_PERCENT));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		RegularOrderSummaryScreen ordersummaryscreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("3");
		ordersummaryscreen.clickSave();

		for (int i = 0; i < 3; i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.lastIndexOf("'"));
			selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
			RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = selectedServicesScreen.openSelectedServiceDetails(servicedetails);

			selectedservicedetailsscreen.clickVehiclePartsCell();
			switch (servicedetails) {
				//case iOSInternalProjectConstants.SR_DISC_20_PERCENT:
				//	 selectedservicedetailsscreen.selectVehiclePart("Hood");
				//     break;
				case iOSInternalProjectConstants.SR_S1_MONEY_PANEL:
					selectedservicedetailsscreen.selectVehiclePart("Back Glass");
					break;
				case iOSInternalProjectConstants.SR_S1_MONEY:
					selectedservicedetailsscreen.selectVehiclePart("Hood");
					break;
				case iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE:
					selectedservicedetailsscreen.selectVehiclePart("Grill");
					break;
			}

			selectedservicedetailsscreen.saveSelectedServiceDetails();
			selectedservicedetailsscreen.saveSelectedServiceDetails();
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

	@Test(testName="Test Case 30084:SR: Regular - Verify that when create Inspection from SR message that vehicle parts are required is shown for appropriate services",
			description = "SR: Regular - Verify that when create Inspection from SR message that vehicle parts are required is shown for appropriate services")
	public void testSRRegularVerifyThatWhenCreateInspectionFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices() {

		final String VIN = "2A4RR4DE2AR286008";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_INSP_ONLY);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		//servicesscreen.selectService(iOSInternalProjectConstants.SR_DISC_20_PERCENT);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE));
		//Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_DISC_20_PERCENT));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		selectedServicesScreen.clickSave();

		for (int i = 0; i < 3; i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.lastIndexOf("'"));
			RegularSelectedServiceDetailsScreen selectedservicedetailsscreen = selectedServicesScreen.openSelectedServiceDetails(servicedetails);

			selectedservicedetailsscreen.clickVehiclePartsCell();
			switch (servicedetails) {
				case iOSInternalProjectConstants.SR_S1_MONEY_PANEL:
					selectedservicedetailsscreen.selectVehiclePart("Hood");
					break;
				case iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE:
					selectedservicedetailsscreen.selectVehiclePart("Back Glass");
					break;
				//case iOSInternalProjectConstants.SR_DISC_20_PERCENT:
				//	 selectedservicedetailsscreen.selectVehiclePart("Hood");
				//     break;
				case iOSInternalProjectConstants.SR_S1_MONEY:
					selectedservicedetailsscreen.selectVehiclePart("Grill");
					break;
			}
			selectedservicedetailsscreen.saveSelectedServiceDetails();
			selectedservicedetailsscreen.saveSelectedServiceDetails();
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

	@Test(testName = "Test Case 38749:Inspections: Regular - Verify that on inspection approval screen selected price matrix value is shown",
			description = "Verify that on inspection approval screen selected price matrix value is shown")
	public void testRegularVerifyThatOnInspectionApprovalScreenSelectedPriceMatrixValueIsShown() {

		final String VIN = "111111111111111";
		final String _make = "Acura";
		final String _model = "CL";
		final String _color = "Black";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		RegularPriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.PRICE_MATRIX_ZAYATS);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");
		vehiclePartScreen.saveVehiclePart();
		RegularInspectionToolBar toolaber = new RegularInspectionToolBar();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$100.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$100.00");

		pricematrix.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.HAIL_MATRIX);
		vehiclePartScreen = pricematrix.selectPriceMatrix("L QUARTER");
		vehiclePartScreen.setSizeAndSeverity("DIME", "VERY LIGHT");
		vehiclePartScreen.saveVehiclePart();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$65.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$165.00");

		RegularQuestionsScreen questionsscreen = pricematrix.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		questionsscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);

		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspectionnumber);
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove("Dent Removal"));
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove("Test service price matrix"));
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice("Dent Removal"), "$65.00");
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice("Test service price matrix"), "$100.00");
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new RegularVehicleScreen();
		pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.PRICE_MATRIX_ZAYATS);
		pricematrix.selectPriceMatrix("VP2 zayats");
		Assert.assertEquals(pricematrix.clearVehicleData(), AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
		//pricematrix.clickBackButton();
		Assert.assertEquals(toolaber.getInspectionSubTotalPrice(), "$0.00");
		Assert.assertEquals(toolaber.getInspectionTotalPrice(), "$65.00");
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspectionnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice("Dent Removal"), "$65.00");
		Assert.assertFalse(approveinspscreen.isInspectionServiceExistsForApprove("Test service price matrix"));
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 31451:Inspection - Regular: Verify that question section is shown per service for first selected panel when QF is not required",
			description = "Verify that question section is shown per service for first selected panel when QF is not required")
	public void testRegularVerifyThatQuestionSectionIsShownPerServiceForFirstSelectedPanelWhenQFIsNotRequired() {

		final String[] vehicleparts = { "Front Bumper", "Grill", "Hood", "Left Fender" };

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.clickSaveButton();
		for (String vehiclepart : vehicleparts)
			selectedservicedetailscreen.selectVehiclePart(vehiclepart);
		selectedservicedetailscreen.clickSaveButton();
		final String selectedhehicleparts = servicesscreen.getListOfSelectedVehicleParts();

		for (String vehiclepart : vehicleparts)
			Assert.assertTrue(selectedhehicleparts.contains(vehiclepart));
		servicesscreen.clickSave();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServicesScreen.openServiceDetailsByIndex(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE, i);
			Assert.assertFalse(selectedservicedetailscreen.isQuestionFormCellExists());
			selectedservicedetailscreen.clickSaveButton();
		}
		selectedServicesScreen.clickSave();
		Helpers.acceptAlert();
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 31963:Inspections: Regular - Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen",
			description = "Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen")
	public void testRegularVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen() {

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPECTION_VIN_ONLY);
		vehiclescreen.getVINField().click();
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.getVINField().sendKeys("\n");
		//vehiclescreen.clickChangeScreen();
		vehiclescreen.cancelOrder();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 42376:Inspections: Regular - Verify that when edit inspection selected vehicle parts for services are present",
			description = "Verify that when edit inspection selected vehicle parts for services are present")
	public void testRegularVerifyThatWhenEditInspectionSelectedVehiclePartsForServicesArePresent() {

		final String VIN = "1D7HW48NX6S507810";
		final String[] vehicleparts = { "Deck Lid", "Hood", "Roof" };

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		String inspectionnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A2");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		for (int i=0; i < vehicleparts.length; i++)
			selectedservicedetailscreen.selectVehiclePart(vehicleparts[i]);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (int i=0; i < vehicleparts.length; i++) {
			selectedServicesScreen.openServiceDetailsByIndex(iOSInternalProjectConstants.SR_S1_MONEY, i);
			selectedservicedetailscreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertEquals(selectedservicedetailscreen.getVehiclePartValue(), vehicleparts[i]);
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		selectedServicesScreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 42388:Inspections: Regular - Verify that it is possible to save as Final inspection linked to SR", description = "Verify that it is possible to save as Final inspection linked to SR")
	public void testRegularVerifyThatItIsPossibleToSaveAsFinalInspectionLinkedToSR() {
		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Dakota", "2006");
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name(AlertsCaptions.ALERT_CREATE_APPOINTMENT)).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("No"))
				.click();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickSaveAsDraft();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen();
		teaminspectionsscreen.selectInspectionForEdit(inspectionnumber);
		//vehiclescreen = new RegularVehicleScreen();
		//servicesscreen = vehiclescreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(),
		//		RegularServicesScreen.class);
		servicesscreen = new RegularServicesScreen();
		servicesscreen.clickSaveAsFinal();
		teaminspectionsscreen = new RegularTeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionIsApproveButtonExists(inspectionnumber));
		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
	}


	@Test(testName = "Test Case 33117:Inspections: Regular - Verify that when final inspection is copied servises are copied without statuses (approved, declined, skipped)",
			description = "Verify that when final inspection is copied servises are copied without statuses (approved, declined, skipped)")
	public void testVerifyThatWhenFinalInspectionIsCopiedServisesAreCopiedWithoutStatuses_Approved_Declined_Skipped() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_BUNDLE);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = new RegularSelectedServiceDetailsScreen();
		servicedetailsscreen.changeAmountOfBundleService("100");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.answerQuestion2("A1");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();

		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		RegularPriceMatricesScreen priceMatricesScreen = new RegularPriceMatricesScreen();
		RegularPriceMatrixScreen pricematrix = priceMatricesScreen.selectPriceMatrice("Price Matrix Zayats");
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "MEDIUM");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();

		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.clickSave();
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);

		myinspectionsscreen.clickApproveInspections();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.SR_S4_BUNDLE);
		approveinspscreen.isInspectionServiceExistsForApprove("Test service zayats");
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		approveinspscreen.isInspectionServiceExistsForApprove(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);

		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.TEST_PACK_FOR_CALC);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SR_S4_BUNDLE));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE));
		selectedServicesScreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 33116:Inspections: Regular - Verify that text notes are copied to new inspectiontypes when use copy action", description = "Verify that text notes are copied to new inspectiontypes when use copy action")
	public void testVerifyThatTextNotesAreCopiedToNewInspectionsWhenUseCopyAction() {

		final String VIN  = "1D7HW48NX6S507810";
		final String _notes = "Test for copy";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.DEFAULT);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularNotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.setNotes(_notes);
		notesscreen.clickSaveButton();
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreen = new RegularVehicleScreen();
		String copiedinspnumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.saveWizard();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myinspectionsscreen.isNotesIconPresentForInspection(copiedinspnumber));
		notesscreen = myinspectionsscreen.openInspectionNotesScreen(copiedinspnumber);
		Assert.assertTrue(notesscreen.isNotesPresent(_notes));
		notesscreen.clickSaveButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 33154:Inspections: Regular - Verify that it is possible to approve Team Inspections use multi select",
			description = "Verify that it is possible to approve Team Inspections use multi select")
	public void testVerifyThatItIsPossibleToApproveTeamInspectionsUseMultiSelect() {

		final String VIN  = "1D7HW48NX6S507810";
		List<String> inspnumbers = new ArrayList<String>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i < 3; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
			vehiclescreen.setVIN(VIN);
			inspnumbers.add(vehiclescreen.getInspectionNumber());
			RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesscreen.selectService(iOSInternalProjectConstants.SR_S4_BUNDLE);
			RegularSelectedServiceDetailsScreen servicedetailsscreen = new RegularSelectedServiceDetailsScreen();
			servicedetailsscreen.changeAmountOfBundleService("100");
			servicedetailsscreen.saveSelectedServiceDetails();
			servicesscreen = new RegularServicesScreen();
			servicesscreen.clickSaveAsFinal();
			new RegularMyInspectionsScreen();

		}
		myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 3; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspnumbers.get(i));
		}

		teaminspectionsscreen.clickApproveInspections();
		teaminspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		for (int i = 0; i < 3; i++) {
			approveinspscreen.selectInspection(inspnumbers.get(i));
			approveinspscreen.clickApproveAllServicesButton();
			approveinspscreen.clickSaveButton();;
		}
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		teaminspectionsscreen = new RegularTeamInspectionsScreen();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 3; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspnumbers.get(i));
		}
		teaminspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		Assert.assertTrue(teaminspectionsscreen.isSendEmailInspectionMenuActionExists());
		teaminspectionsscreen.clickCancel();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 33869:Inspections: Regular - Verify that Services on Service Package are grouped by type selected on Insp type->Wizard",
			description = "Verify that Services on Service Package are grouped by type selected on Insp type->Wizard")
	public void testVerifyThatServicesOnServicePackageAreGroupedByTypeSelectedOnInspTypeWizard() {

		final String VIN  = "1D7HW48NX6S507810";
		List<String> inspnumbers = new ArrayList<>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPECTION_GROUP_SERVICE);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		inspnumbers.add(vehiclescreen.getInspectionNumber());
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.waitQuestionsScreenLoaded(ScreenNamesConstants.ZAYATS_SECTION1);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen = servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.TEST_PACK_FOR_CALC);
		servicesscreen.selectPriceMatrices("Back Glass");
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("Oksi_Service_PP_Vehicle");
		selectedservicedetailscreen.answerQuestion2("A1");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		//servicesscreen.clickBackServicesButton();
		servicesscreen = servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES, "SR_FeeBundle");
		servicesscreen.selectPriceMatrices("Price Adjustment");
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails("SR_S6_Bl_I1_Percent");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.clickBackServicesButton();
		servicesscreen.clickSaveAsFinal();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		Helpers.drawRegularQuestionsSignature();
		servicesscreen.clickSaveAsFinal();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Question 'Tax_Point_1' in section 'BATTERY PERFORMANCE' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Close"))
				.click();
		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.selectTaxPoint("Test Answer 1");
		servicesscreen.clickSaveAsFinal();
		Helpers.waitForAlert();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Question 'Question 2' in section 'Zayats Section1' should be answered.")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver().findElement(
				MobileBy.name("Close"))
				.click();
		questionsscreen = new RegularQuestionsScreen();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspnumber));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 44407:Inspections: Regular - Verify that all instances of one service are copied from inspection to WO",
			description = "Verify that all instances of one service are copied from inspection to WO")
	public void testVerifyThatAllInstancesOfOneServiceAreCopiedFromInspectionToWO() {

		final String VIN  = "1D7HW48NX6S507810";
		final String firstprice = "43";
		final String secondprice = "33";
		final String secondquantity = "4";
		final String[] vehicleparts = { "Front Bumper", "Grill", "Hood" };

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServicePriceValue(firstprice);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServicePriceValue(secondprice);
		servicedetailsscreen.setServiceQuantityValue(secondquantity);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		servicedetailsscreen.clickVehiclePartsCell();
		for (String vp: vehicleparts)
			servicedetailsscreen.selectVehiclePart(vp);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
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
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$43.00 x 1.00"));
		Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues("3/4\" - Penny Size", "$33.00 x 4.00"));
		Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.SR_S1_MONEY_PANEL), vehicleparts.length);
		selectedServicesScreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 33918:Inspections: Regular - Verify that Assign button is present when select some Tech in case Direct Assign option is set for inspection type",
			description = "Verify that Assign button is present when select some Tech in case Direct Assign option is set for inspection type")
	public void testVerifyThatAssignButtonIsPresentWhenSelectSomeTechInCaseDirectAssignOptionIsSetForInspectionType() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPECTION_DIRECT_ASSIGN);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new RegularMyInspectionsScreen();
		myinspectionsscreen.selectInspectionToAssign(inspnumber);
		Assert.assertTrue(myinspectionsscreen.isAssignButtonExists());
		myinspectionsscreen.clickBackButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 34285:Inspections: Regular - Verify that during Line approval ''Select All'' buttons are working correctly",
			description = "Verify that during Line approval ''Select All'' buttons are working correctly")
	public void testVerifyThatDuringLineApprovalSelectAllButtonsAreWorkingCorrectly() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL);
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("Grill");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		servicedetailsscreen.clickVehiclePartsCell();
		servicedetailsscreen.selectVehiclePart("123");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove("Oksi_Service_PP_Panel (Grill)"));
		Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove("Service_PP_Vehicle_not_multiple (123)"));
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

	@Test(testName = "Test Case 30012:Inspections: Regular - Verify that Approve option is not present for approved inspection in multi-select mode",
			description = "Verify that Approve option is not present for approved inspection in multi-select mode")
	public void testVerifyThatApproveOptionIsNotPresentForApprovedInspectionInMultiselectMode() {

		final String VIN  = "1D7HW48NX6S507810";
		ArrayList<String> inspections = new ArrayList<String>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i <2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
			vehiclescreen.setVIN(VIN);
			inspections.add(vehiclescreen.getInspectionNumber());

			RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
			questionsscreen.swipeScreenUp();
			questionsscreen.selectAnswerForQuestion("Question 2", "A1");

			RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesscreen.saveWizard();
			myinspectionsscreen.selectInspectionForAction(inspections.get(i));
			myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

			RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
			approveinspscreen.selectInspection(inspections.get(i));
			approveinspscreen.clickApproveAllServicesButton();
			approveinspscreen.clickSaveButton();
			approveinspscreen.clickSingnAndDrawApprovalSignature();
			approveinspscreen.clickDoneButton();
			myinspectionsscreen = new RegularMyInspectionsScreen();
		}

		myinspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickCancel();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickCancel();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 30013:Inspections: Regular - Verify that Approve option is present in multi-select mode only one or more not approved inspectiontypes are selected",
			description = "Verify that Approve option is present in multi-select mode only one or more not approved inspectiontypes are selected")
	public void testVerifyThatApproveOptionIsPresentInMultiselectModeOnlyOneOrMoreNotApprovedInspectionsAreSelected() {

		final String VIN  = "1D7HW48NX6S507810";
		ArrayList<String> inspections = new ArrayList<String>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (int i = 0; i <2; i++) {
			myinspectionsscreen.clickAddInspectionButton();
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
			vehiclescreen.setVIN(VIN);
			inspections.add(vehiclescreen.getInspectionNumber());

			RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
			questionsscreen.swipeScreenUp();
			questionsscreen.selectAnswerForQuestion("Question 2", "A1");

			RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesscreen.saveWizard();
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
		for (int i = 0; i < 2; i++) {
			myinspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertTrue(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickCancel();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (int i = 0; i < 2; i++) {
			teaminspectionsscreen.selectInspectionForAction(inspections.get(i));
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertTrue(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickCancel();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 30853:Inspections: Regular - Verify that when option ''Draft Mode'' is set to ON - when save inspection provide prompt to a user to select either Draft or Final,"
			+ "Test Case 30855:Inspections: Regular - Verify that for Draft inspection following options are not available (Approve, Create WO, Create SR, Copy inspection)",
			description = "Verify that when option ''Draft Mode'' is set to ON - when save inspection provide prompt to a user to select either Draft or Final,"
					+ "Verify that for Draft inspection following options are not available (Approve, Create WO, Create SR, Copy inspection)")
	public void testVerifyThatWhenOptionDraftModeIsSetToONWhenSaveInspectionProvidePromptToAUserToSelectEitherDraftOrFinal() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Hood");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		regularselectedservicedetailsscreen = new RegularSelectedServiceDetailsScreen();
		regularselectedservicedetailsscreen.clickVehiclePartsCell();
		regularselectedservicedetailsscreen.selectVehiclePart("Grill");
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
		regularselectedservicedetailsscreen.saveSelectedServiceDetails();
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

	@Test(testName = "Test Case 32286:Inspections: Regular - Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount")
	public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListColumnApprovedAmount() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Grill");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.saveWizard();

		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)");
		approveinspscreen.clickSaveButton();

		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
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
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.selectSearchStatus("All active");
		inspectionspage.selectSearchTimeframe("Custom");
		inspectionspage.setTimeFrame(BackOfficeUtils.getCurrentDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());
		inspectionspage.searchInspectionByNumber(inspnumber);
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspnumber), "$2,000.00");
		Assert.assertEquals(inspectionspage.getInspectionStatus(inspnumber), "Approved");
		Assert.assertEquals(inspectionspage.getInspectionApprovedTotal(inspnumber), "$2,000.00");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(testName="Test Case 51336:WO: Regular - Verify that approve WO is working correct under Team WO",
			description = "WO: Regular - Verify that approve WO is working correct under Team WO")
	public void testWOVerifyThatApproveWOIsWorkingCorrectUnderTeamWO() {

		final String VIN  = "JA4LS31H8YP047397";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
		RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.setFilterLocation("All locations");
		teamworkordersscreen.clickSaveFilter();
		Assert.assertTrue(teamworkordersscreen.woExists(wonumber));
		Assert.assertTrue(teamworkordersscreen.isWorkOrderHasApproveIcon(wonumber));
		teamworkordersscreen.approveWorkOrder(wonumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		Assert.assertTrue(teamworkordersscreen.isWorkOrderHasActionIcon(wonumber));
		teamworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 27717:Invoices: Regular - Verify that it is posible to add payment from device for draft invoice",
			description = "Invoices: Regular - Verify that it is posible to add payment from device for draft invoice")
	public void testInvoicesVerifyThatItIsPosibleToAddPaymentFromDeviceForDraftInvoice() {

		final String VIN  = "WDZPE7CD9E5889222";
		final String _po  = "12345";
		final String cashcheckamount = "100";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();

		servicesscreen = new RegularServicesScreen();
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword("Zayats", "1111");
		ordersummaryscreen.clickSave();
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		BaseUtils.waitABit(1000*60);
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new RegularInvoiceInfoScreen();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickInvoicePayButton();
		invoiceinfoscreen.changePaynentMethodToCashNormal();
		invoiceinfoscreen.setCashCheckAmountValue(cashcheckamount);
		invoiceinfoscreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
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

		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), _po);
		Assert.assertEquals(invoiceswebpage.getInvoicePOPaidValue(invoicenumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);
		Assert.assertEquals(invoicepayments.getPaymentsTypeAmountValue("Cash/Check"), PricesCalculations.getPriceRepresentation(cashcheckamount));
		Assert.assertEquals(invoicepayments.getPaymentsTypeCreatedByValue("Cash/Check"), "Employee Simple 20%");

		Assert.assertEquals(invoicepayments.getPaymentsTypeAmountValue("PO/RO"), PricesCalculations.getPriceRepresentation("0"));
		Assert.assertEquals(invoicepayments.getPaymentsTypeCreatedByValue("PO/RO"), "Back Office");
		invoicepayments.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(testName="Test Case 27739:Invoices: Regular - Verify that payment is send to BO when PO# is changed under My invoice",
			description = "Invoices: Regular - Verify that payment is send to BO when PO# is changed under My invoice")
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderMyInvoice() {

		final String VIN  = "WDZPE7CD9E5889222";
		final String _po  = "12345";
		final String newpo  = "New test PO";
		final String cashcheckamount = "100";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();

		servicesscreen = new RegularServicesScreen();
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword("Zayats", "1111");
		ordersummaryscreen.clickSave();
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		BaseUtils.waitABit(1000*60);
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new RegularInvoiceInfoScreen();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickInvoicePayButton();
		invoiceinfoscreen.changePaynentMethodToCashNormal();
		invoiceinfoscreen.setCashCheckAmountValue(cashcheckamount);
		invoiceinfoscreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), _po);
		invoiceinfoscreen.clickSaveAsDraft();
		myinvoicesscreen = new RegularMyInvoicesScreen();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO(newpo);
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

		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), newpo);
		Assert.assertEquals(invoiceswebpage.getInvoicePOPaidValue(invoicenumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);

		Assert.assertEquals(invoicepayments.getPaymentDescriptionTypeAmountValue("PO #: " + newpo), PricesCalculations.getPriceRepresentation("0"));
		invoicepayments.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(testName="Test Case 27741:Invoices: Regular - Verify that payment is send to BO when PO# is changed under Team invoice",
			description = "Invoices: Regular - Verify that payment is send to BO when PO# is changed under Team invoice")
	public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderTeamInvoice() {

		final String VIN  = "WDZPE7CD9E5889222";
		final String _po  = "12345";
		final String newpo  = "New test PO from Team";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.selectDiscaunt("Dye");
		vehiclePartScreen.selectDiscaunt("Wheel");
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();

		servicesscreen = new RegularServicesScreen();
		//servicesscreen.clickCancelButton();
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S4_Bl_I1_M);
		//servicesscreen.searchServiceByName(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.selectEmployeeAndTypePassword("Zayats", "1111");
		ordersummaryscreen.clickSave();
		RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		BaseUtils.waitABit(1000*60);
		RegularTeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		teaminvoicesscreen.selectInvoice(invoicenumber);
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(newpo);
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

		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), newpo);
		//Assert.assertEquals(invoiceswebpage.getInvoicePOPaidValue(invoicenumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);

		Assert.assertEquals(invoicepayments.getPaymentDescriptionTypeAmountValue("PO #: " + newpo), PricesCalculations.getPriceRepresentation("0"));
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
		//servicesscreen.selectSubService(iOSInternalProjectConstants.DYE_SERVICE);

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

	@Test(testName="Test Case 45251:SR: Regular - Verify multiple inspectiontypes and multiple work orders to be tied to a Service Request",
			description = "SR: Regular - Verify multiple inspectiontypes and multiple work orders to be tied to a Service Request")
	public void testSRVerifyMultipleInspectionsAndMultipleWorkOrdersToBeTiedToAServiceRequest() {

		final String VIN  = "WDZPE7CD9E5889222";
		List<String> inspnumbers = new ArrayList<>();
		List<String> wonumbers = new ArrayList<>();

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(VIN);
		RegularQuestionsScreen questionsscreen =  vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		vehiclescreen.clickSave();
		Helpers.getAlertTextAndCancel();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(InspectionsTypes.INS_LINE_APPROVE_OFF);
		inspnumbers.add(vehiclescreen.getInspectionNumber());
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen = new RegularServicesScreen();
		servicesscreen.clickSaveAsDraft();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		inspnumbers.add(vehiclescreen.getInspectionNumber());
		questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.waitQuestionsScreenLoaded(ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectDetailsRequestAction();
		servicerequestsscreen.clickServiceRequestSummaryInspectionsButton();
		RegularTeamInspectionsScreen teaminspectionsscreen = new RegularTeamInspectionsScreen();
		for (String inspectnumber : inspnumbers)
			Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));

		teaminspectionsscreen.clickBackButton();
		servicerequestsscreen.clickBackButton();
		servicerequestsscreen = new RegularServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		servicerequestsscreen.selectWorkOrderType(WorkOrdersTypes.WO_DELAY_START);
		wonumbers.add(vehiclescreen.getWorkOrderNumber());
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateWorkOrderRequestAction();
		servicerequestsscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		wonumbers.add(vehiclescreen.getWorkOrderNumber());
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.saveWizard();
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

	@Test(testName = "Test Case 45128:Inspections: Regular - Verify that service level notes are copied from Inspection to WO when it is auto created after approval",
			description = "Verify that service level notes are copied from Inspection to WO when it is auto created after approval")
	public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval() {

		final String VIN  = "1D7HW48NX6S507810";
		final String _price1  = "10";
		final String _price2  = "100";
		final int timetowaitwo = 4;
		final String _pricematrix1 = "Left Front Door";
		final String _pricematrix2 = "Grill";
		final String inspectionnotes = "Inspection notes";
		final String servicenotes = "Service Notes";

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
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServicePriceValue(_price1);
		servicedetailsscreen.saveSelectedServiceDetails();

		servicesscreen.selectSubService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SALES_TAX);
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
		servicedetailsscreen = selectedServicesScreen.openCustomServiceDetails("3/4\" - Penny Size");
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();

		servicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();

		servicedetailsscreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServicesScreen.isNotesIconPresentForSelectedService(iOSInternalProjectConstants.SALES_TAX));
		Assert.assertTrue(selectedServicesScreen.isNotesIconPresentForSelectedService("3/4\" - Penny Size"));
		Assert.assertTrue(selectedServicesScreen.isNotesIconPresentForSelectedService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));

		RegularPriceMatrixScreen pricematrix =  selectedServicesScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix1);
		vehiclePartScreen.setSizeAndSeverity("DIME", "VERY LIGHT");
		vehiclePartScreen.setPrice(_price2);
		vehiclePartScreen.saveVehiclePart();

		servicesscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.MATRIX_LABOR);
		vehiclePartScreen = pricematrix.selectPriceMatrix(_pricematrix2);
		vehiclePartScreen.switchOffOption("PDR");
		vehiclePartScreen.setTime("12");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.saveWizard();

		Assert.assertTrue(myinspectionsscreen.isNotesIconPresentForInspection(inspnumber));
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		//SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		myinspectionsscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
		approveinspscreen.selectInspection(inspnumber);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.clickSingnAndDrawApprovalSignature();
		approveinspscreen.clickDoneButton();

		homescreen = myinspectionsscreen.clickHomeButton();

		RegularTeamWorkOrdersScreen teamwoscreen = homescreen.clickTeamWorkordersButton();
		homescreen = teamwoscreen.clickHomeButton();

		for (int i = 0; i < timetowaitwo; i++) {
			Helpers.waitABit(60*1000);
			teamwoscreen = homescreen.clickTeamWorkordersButton();
			homescreen = teamwoscreen.clickHomeButton();
		}
		teamwoscreen = homescreen.clickTeamWorkordersButton();
		teamwoscreen.clickSearchButton();
		teamwoscreen.setSearchType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR.getWorkOrderTypeName());
		teamwoscreen.selectSearchLocation("All locations");
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
		/*servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();*/
		RegularSelectedServicesScreen regularSelectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(regularSelectedServicesScreen.isNotesIconPresentForSelectedWorkOrderService("3/4\" - Penny Size"));

		/*servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();*/
		Assert.assertTrue(regularSelectedServicesScreen.isNotesIconPresentForSelectedWorkOrderService(iOSInternalProjectConstants.SALES_TAX));

		/*servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();*/
		Assert.assertTrue(regularSelectedServicesScreen.isNotesIconPresentForSelectedWorkOrderService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		servicesscreen.cancelWizard();
		teamwoscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 32989:Inspections: Regular - Verify that question section is shown per service with must panels when questions are required",
			description = "Verify that question section is shown per service with must panels when questions are required")
	public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();

		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(VIN);

		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.selectVehiclePart("Deck Lid");
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.selectVehiclePart("Hood");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedServicesScreen.openServiceDetailsByIndex(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE, 0);
		Assert.assertTrue(servicedetailsscreen.isQuestionFormCellExists());
		Assert.assertEquals(servicedetailsscreen.getQuestion2Value(), "A3");
		servicedetailsscreen.saveSelectedServiceDetails();

		for (int i = 1; i < 4; i++) {
			selectedServicesScreen.openServiceDetailsByIndex(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE, i);
			Assert.assertFalse(servicedetailsscreen.isQuestionFormCellExists());
			servicedetailsscreen.saveSelectedServiceDetails();
		}
		selectedServicesScreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 30509:Invoices: Regular - Verify that message 'Invoice PO# shouldn't be empty' is shown for Team Invoices",
			description = "Verify that message 'Invoice PO# shouldn't be empty' is shown for Team Invoices")
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForTeamInvoices() {

		final String emptypo = "";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		BaseUtils.waitABit(1000*60);
		RegularTeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
		teaminvoicesscreen.selectInvoice(invoicenum);
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(emptypo);
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Invoice PO# shouldn't be empty"));
		teaminvoicesscreen.clickCancel();
		teaminvoicesscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 30510:Invoices: Regular - Verify that message 'Invoice PO# shouldn't be empty' is shown for MY Invoices",
			description = "Verify that message 'Invoice PO# shouldn't be empty' is shown for MY Invoices")
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForMyInvoices() {

		final String emptypo = "";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String invoicenum = myinvoicesscreen.getFirstInvoiceValue();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO(emptypo);
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Invoice PO# shouldn't be empty"));
		myinvoicesscreen.clickCancel();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 33115:WO: Regular - Verify that Tech splits is saved in price matrices",
			description = "Verify that Tech splits is saved in price matrices")
	public void testWOVerifyThatTechSplitsIsSavedInPriceMatrices() {

		final String VIN  = "1D7HW48NX6S507810";
		final String pricevalue  = "100";
		final String totalsale = "5";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "MEDIUM");
		Assert.assertEquals(vehiclePartScreen.getTechniciansValue(), defaulttech);
		vehiclePartScreen.setPrice(pricevalue);
		vehiclePartScreen.clickOnTechnicians();
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician(techname);
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(vehiclePartScreen.getTechniciansValue(), defaulttech + ", " + techname);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();
		servicesscreen = new RegularServicesScreen();

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(totalsale));
		ordersummaryscreen.clickSave();

		RegularTechRevenueScreen techrevenuescreen = myworkordersscreen.selectWorkOrderTechRevenueMenuItem(wonumber);
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(techname));
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(defaulttech));
		techrevenuescreen.clickBackButton();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 34551:WO: Regular - Verify that it is not possible to change default tech via service type split",
			description = "Verify that it is not possible to change default tech via service type split")
	public void testWOVerifyThatItIsNotPossibleToChangeDefaultTechViaServiceTypeSplit() {

		final String VIN  = "1D7HW48NX6S507810";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";
		final String totalsale = "5";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(defaulttech));
		selectedservicescreen.selecTechnician(techname);
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(defaulttech));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.clickTechnicianToolbarIcon();
		servicesscreen.changeTechnician("Dent", techname);
		RegularSelectedServicesScreen regularSelectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedservicescreen = regularSelectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(defaulttech));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		regularSelectedServicesScreen = new RegularSelectedServicesScreen();
		RegularQuestionsScreen questionsscreen = regularSelectedServicesScreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(totalsale));
		ordersummaryscreen.saveWizard();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));

		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 45097:WO: Regular - Verify that when use Copy Services action for WO all service instances should be copied",
			description = "Verify that when use Copy Services action for WO all service instances should be copied")
	public void testWOVerifyThatWhenUseCopyServicesActionForWOAllServiceInstancesShouldBeCopied() {

		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "5";
		final String[] servicestoadd = { iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL, iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE};
		final String[] vehicleparts = { "Dashboard", "Deck Lid"};


		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (String serviceadd : servicestoadd) {
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(serviceadd);
			selectedservicescreen.clickVehiclePartsCell();
			for (String vehiclepart : vehicleparts) {
				selectedservicescreen.selectVehiclePart(vehiclepart);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
		}
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (String serviceadd : servicestoadd) {
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceadd));
		}

		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), "$44.00");
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(totalsale));
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForCopyServices(wonumber);
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (String serviceadd : servicestoadd) {
			Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(serviceadd), servicestoadd.length);
		}
		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), "$44.00");
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 50250:WO: Regular - Verify that WO number is not duplicated",
			description = "WO: - Verify that WO number is not duplicated")
	public void testWOVerifyThatWONumberIsNotDuplicated() {

		final String VIN  = "JA4LS31H8YP047397";
		final String _po  = "12345";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(VIN);

		final String wonumber1 = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.approveWorkOrder(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		RegularQuestionsScreen questionsscreen = invoiceinfoscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
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
		vehiclescreen.setVIN(VIN);

		final String wonumber2 = vehiclescreen.getWorkOrderNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrder(wonumber2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber2);
		myworkordersscreen.clickInvoiceIcon();
		myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(_po);
		questionsscreen = invoiceinfoscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		homescreen = myworkordersscreen.clickHomeButton();
		homescreen.clickStatusButton();
		homescreen.updateDatabase();
		RegularMainScreen mainscreen = new RegularMainScreen();
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		//Create third WO
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(VIN);

		final String wonumber3 = vehiclescreen.getWorkOrderNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		ordersummaryscreen.setTotalSale("5");
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
		workorderspage.setSearchOrderNumber(wonumber3);
		workorderspage.clickFindButton();

		Assert.assertEquals(workorderspage.getWorkOrdersTableRowCount(), 1);
		webdriver.quit();
	}

	@Test(testName="Test Case 39573:WO: Regular - Verify that in case valid VIN is decoded, replace existing make and model with new one",
			description = "WO: - Verify that in case valid VIN is decoded, replace existing make and model with new one")
	public void testWOVerifyThatInCaseValidVINIsDecodedReplaceExistingMakeAndModelWithNewOne() {

		final String[] VINs  = { "2A8GP54L87R279721", "1FMDU32X0PUB50142", "GFFGG"} ;
		final String makes[]  = { "Chrysler", "Ford", null } ;
		final String models[]  = { "Town and Country", "Explorer",  null };


		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval ON
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

	@Test(testName="Test Case 34626:WO: Regular - Verify that when service do not have questions and select several panels do not underline anyone",
			description = "WO: - Verify that when service do not have questions and select several panels do not underline anyone")
	public void testWOVerifyThatWhenServiceDoNotHaveQuestionsAndSelectSeveralPanelsDoNotUnderlineAnyone() {
		final String VIN  = "2A8GP54L87R279721";
		final String[] vehicleparts  = { "Center Rear Passenger Seat", "Dashboard" };

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval ON
		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_WITHOUT_QUESTIONS_PP_PANEL);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.clickVehiclePartsCell();
		for (String vehiclepart : vehicleparts) {
			selectedservicescreen.selectVehiclePart(vehiclepart);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		for (String vehiclepart : vehicleparts) {
			Assert.assertTrue(selectedservicescreen.getVehiclePartValue().contains(vehiclepart));
		}
		selectedservicescreen.saveSelectedServiceDetails();
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.TEST_SERVICE_WITHOUT_QUESTIONS_PP_PANEL), vehicleparts.length);
		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 31964:WO: Regular - Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen",
			description = "Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen")
	public void testWOVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen() {

		final String VIN = "2A8GP54L87R279721";


		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickAddOrderButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_VIN_ONLY);
		vehiclescreen.setVIN(VIN);
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.clickVINField();
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.cancelOrder();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 53824:WO: Regular - Verify that message is shown for Money and Labor service when price is changed to 0$ under WO",
			description = "Verify that message is shown for Money and Labor service when price is changed to 0$ under WO")
	public void testWOVerifyThatMessageIsShownForMoneyAndLaborServiceWhenPriceIsChangedTo0UnderWO() {

		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "5";
		final String servicezeroprice = "0";
		final String servicecalclaborprice = "12";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_MONITOR);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//servicesscreen.selectService(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);

		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);
		selectedservicescreen.setServicePriceValue(servicezeroprice);
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Order's technician split will be assigned to this order service if you set zero amount."));

		selectedservicescreen.clickTechniciansIcon();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Set non-zero amount for service to assign multiple technicians."));

		selectedservicescreen.searchTechnician("Manager");
		selectedservicescreen.selecTechnician("Manager 1");
		selectedservicescreen.cancelSearchTechnician();
		selectedservicescreen.searchTechnician("Oksana");
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.cancelSearchTechnician();
		Assert.assertFalse(selectedservicescreen.isTechnicianIsSelected("Manager 1"));
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected("Oksana Zayats"));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.CALC_LABOR);
		selectedservicescreen.clickTechniciansIcon();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Set non-zero amount for service to assign multiple technicians."));
		selectedservicescreen.cancelSelectedServiceDetails();
		selectedservicescreen.setServiceRateValue(servicecalclaborprice);
		selectedservicescreen.clickTechniciansIcon();

		selectedservicescreen.searchTechnician("Manager");
		selectedservicescreen.selecTechnician("Manager 1");
		selectedservicescreen.cancelSearchTechnician();
		selectedservicescreen.searchTechnician("Oksana");
		selectedservicescreen.selecTechnician("Oksana Zayats");
		selectedservicescreen.cancelSearchTechnician();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected("Manager 1"));
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected("Oksana Zayats"));
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelWizard();
		homescreen =  myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 45252:WO: Regular - Verify that validation is present for vehicle trim field",
			description = "Verify that validation is present for vehicle trim field")
	public void testWOVerifyThatValidationIsPresentForVehicleTrimField() {

		final String VIN  = "TESTVINN";
		final String _make = "Acura";
		final String _model = "CL";
		final String trimvalue = "2.2 Premium";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_VEHICLE_TRIM_VALIDATION);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Trim is required"));
		vehiclescreen.setTrim(trimvalue);
		Assert.assertEquals(vehiclescreen.getTrim(), trimvalue);

		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//servicesscreen.saveWorkOrder();
		servicesscreen.saveWizard();
		homescreen =  myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 35375:WO: Regular - Verify that Total sale is not shown when checkmark 'Total sale required' is not set to OFF",
			description = "Verify that Total sale is not shown when checkmark 'Total sale required' is not set to OFF")
	public void testWOVerifyThatTotalSaleIsNotShownWhenCheckmarkTotalSaleRequiredIsNotSetToOFF() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TOTAL_SALE_NOT_REQUIRED);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.TAX_DISCOUNT);
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
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

	@Test(testName = "Test Case 40821:WO: Regular - Verify that it is possible to assign tech to order by action Technicians",
			description = "Verify that it is possible to assign tech to order by action Technicians")
	public void testWOVerifyThatItIsPossibleToAssignTechToOrderByActionTechnicians() {

		final String VIN  = "1D7HW48NX6S507810";
		final String pricevalue  = "21";
		final String totalsale = "5";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService("3/4\" - Penny Size");
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue("2.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.selectSubService(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Front Bumper");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");
		Assert.assertEquals(vehiclePartScreen.getTechniciansValue(), defaulttech);
		vehiclePartScreen.setPrice(pricevalue);
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();
		servicesscreen = new RegularServicesScreen();

		servicesscreen.selectSubService(iOSInternalProjectConstants.TAX_DISCOUNT);
		servicesscreen.selectSubService(iOSInternalProjectConstants.SALES_TAX);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();

		selectedservicescreen = myworkordersscreen.selectWorkOrderTechniciansMenuItem(wonumber);
		//selectedservicescreen.selecTechnician(defaulttech);
		selectedservicescreen.selecTechnician(techname);
		selectedservicescreen.saveSelectedServiceDetails();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Changing default employees for a work order will change split data for all services."));

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		Assert.assertEquals(vehiclescreen.getTechnician(), techname + ", " + defaulttech);
		vehiclescreen.cancelOrder();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 58663:Inspections: Regular - Verify that when Panel grouping is used for package for selected Panel only linked services are shown",
			description = "Verify that when Panel grouping is used for package for selected Panel only linked services are shown")
	public void testInspectionsVerifyThatWhenPanelGroupingIsUsedForPackageForSelectedPanelOnlyLinkedServicesAreShown() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();

		RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(InspectionsTypes.INSP_SERVICE_TYPE_WITH_OUT_REQUIRED);
		vehiclescreen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectServicePanel(iOSInternalProjectConstants.BUFF_SERVICE);
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE));
		servicesscreen.clickBackServicesButton();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.selectServicePanel(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_VEHICLE));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_PANEL));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_SERVICE));
		servicesscreen.clickBackServicesButton();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.selectServicePanel(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		Assert.assertTrue(servicesscreen.isServiceTypeExists("3/4\" - Penny Size"));
		servicesscreen.clickBackServicesButton();
		servicesscreen = new RegularServicesScreen();
		servicesscreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 57101:WO: Regular - Verify that WO is saved correct with selected sub service (no message with incorrect tech split)",
			description = "Verify that WO is saved correct with selected sub service (no message with incorrect tech split)")
	public void testWOVerifyThatWOIsSavedCorrectWithSelectedSubService_NoMessageWithIncorrectTechSplit() {

		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "10";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.hideKeyboard();
		servicesscreen.selectServiceSubSrvice(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE + " (Wash partly)");
		//servicesscreen.clickToolButton();

		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.selectServiceSubSrvice(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE + " (Wash whole)");
		servicesscreen.hideKeyboard();
		//servicesscreen.clickAddServicesButton();


		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.isServiceWithSubSrviceSelected(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash partly"));
		Assert.assertTrue(selectedServicesScreen.isServiceWithSubSrviceSelected(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash whole"));
		selectedServicesScreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 54542:WO: Regular - Verify that answer services are correctly added for WO when Panel group is set",
			description = "Verify that answer services are correctly added for WO when Panel group is set")
	public void testWOVerifyThatAnswerServicesAreCorrectlyAddedForWOWhenPanelGroupIsSet() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_PANEL_GROUP);
		vehiclescreen.setVIN(VIN);
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		questionsscreen = questionsscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, "Zayats Section2");
		questionsscreen.selectAnswerForQuestionWithAdditionalConditions("Q1", "No - rate 0", "A1", "Deck Lid");

		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//servicesscreen.selectServicePanel("Other");
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE));
		//servicesscreen.clickBackServicesButton();

		selectedServicesScreen.cancelWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 43408:WO: Regular - Verify that search bar is present for service pack screen",
			description = "Verify that search bar is present for service pack screen")
	public void testWOVerifyThatSearchBarIsPresentForServicePackScreen() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		//servicesscreen.searchServiceByName("test");
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Dashboard");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		//servicesscreen.searchServiceByName("Tax");
		servicesscreen.selectSubService(iOSInternalProjectConstants.SALES_TAX);

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.SALES_TAX));
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE));

		selectedServicesScreen.cancelWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 42178:WO: Regular - Verify that Cancel message is shown for New/Existing WO",
			description = "Verify that Cancel message is shown for New/Existing WO")
	public void testWOVerifyThatCancelMessageIsShownForNewOrExistingWO() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.clickChangeScreen();
		vehiclescreen.clickCancel();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, "Stop Work Order Creation\nAny unsaved changes will be lost. Are you sure you want to stop creating this Work Order?");
		vehiclescreen.clickCancel();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Stop Work Order Creation\nAny unsaved changes will be lost. Are you sure you want to stop creating this Work Order?");

		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickChangeScreen();
		vehiclescreen.clickCancel();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, "Stop Work Order Edit\nAny unsaved changes will be lost. Are you sure you want to stop editing this Work Order?");
		vehiclescreen.clickCancel();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Stop Work Order Edit\nAny unsaved changes will be lost. Are you sure you want to stop editing this Work Order?");

		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickChangeScreen();
		vehiclescreen.clickCancel();

		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 35951:SR: Reqular - Verify that Accept/Decline actions are present for tech when 'Technician Acceptance Required' option is ON and status is Proposed",
			description = "Verify that Accept/Decline actions are present for tech when 'Technician Acceptance Required' option is ON and status is Proposed")
	public void testVerifyThatAcceptDeclineActionsArePresentForTechWhenTechnicianAcceptanceRequiredOptionIsONAndStatusIsProposed() {

		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";

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
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
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

	@Test(testName="Test Case 35953:SR: Regular - Verify that when SR is declined status reason should be selected",
			description = "Verify that when SR is declined status reason should be selected")
	public void testVerifyThatWhenSRIsDeclinedStatusReasonShouldBeSelected() {

		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";

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
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
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

	@Test(testName="Test Case 35954:SR: Regular - Verify that SR is not accepted when employee review or update it",
			description = "Verify that SR is not accepted when employee review or update it")
	public void testVerifyThatSRIsNotAcceptedWhenEmployeeReviewOrUpdateIt() {

		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";

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
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
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
		vehiclescreen.setTech("Simple 20%");
		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.clickCancel();

		servicerequestsscreen.clickHomeButton();

	}

	@Test(testName="Test Case 36004:SR: Regular - Verify that it is possible to accept/decline Appointment when option 'Appointment Acceptance Required' = ON",
			description = "Verify that it is possible to accept/decline Appointment when option 'Appointment Acceptance Required' = ON")
	public void testSRVerifyThatItIsPossibleToAcceptDeclineAppointmentWhenOptionAppointmentAcceptanceRequiredEqualsON() {

		final String VIN = "2A4RR4DE2AR286008";
		final String _make = "Chrysler";
		final String _model = "Town and Country";

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
		servicerequestslistpage.setServiceRequestGeneralInfoAssignedTo("Employee Simple 20%");
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(_make, _model);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(servicerequestslistpage.addAppointmentFromSRlist(startDate, endDate, "Employee Simple 20%"));
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

	@Test(testName = "Test Case 34427:WO: Regular - Verify Assign tech to service type, instead of individual services",
			description = "Verify Assign tech to service type, instead of individual services")
	public void testWOVerifyAssignTechToServiceTypeInsteadOfIndividualServices() {

		final String VIN  = "1D7HW48NX6S507810";
		final String defaulttech  = "Employee Simple 20%";


		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescren =  myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_GROUP_SERVICE_TYPE);
		vehiclescren.setVIN(VIN);

		RegularServicesScreen servicesscreen = vehiclescren.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickTechnicianToolbarIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), "Services\n" +
				"No selected services.");

		servicesscreen.selectServicePanel(iOSInternalProjectConstants.BUFF_SERVICE);
		RegularSelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Hood");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedservicedetailscreen.saveSelectedServiceDetails();

		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Front Bumper");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickBackServicesButton();

		servicesscreen.selectServicePanel(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicedetailscreen.setServicePriceValue("80");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();
		servicesscreen.clickBackServicesButton();

		servicesscreen.selectServicePanel("Wash Detail");
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		RegularPriceMatricesScreen priceMatricesScreen = new RegularPriceMatricesScreen();
		RegularPriceMatrixScreen pricematrix = priceMatricesScreen.selectPriceMatrice("Price Matrix Zayats");
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP2 zayats");
		vehiclePartScreen.setSizeAndSeverity("DIME", "MEDIUM");
		vehiclePartScreen.setPrice("150");
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		vehiclePartScreen.selectDiscaunt("Test service zayats");
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();

		servicesscreen.clickBackServicesButton();
		servicesscreen.clickTechnicianToolbarIcon();
		RegularServiceTypesScreen serviceTypesScreen = new RegularServiceTypesScreen();
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(iOSInternalProjectConstants.BUFF_SERVICE));
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(iOSInternalProjectConstants.SR_S1_MONEY));
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE));
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(iOSInternalProjectConstants.SR_S1_MONEY_PANEL));
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE));
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(iOSInternalProjectConstants.DYE_SERVICE));
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists("Detail"));
		Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX));

		serviceTypesScreen.clickOnPanel(iOSInternalProjectConstants.BUFF_SERVICE);
		selectedservicedetailscreen.selecTechnician(defaulttech);
		selectedservicedetailscreen.clickSaveButton();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.selectServicePanel(iOSInternalProjectConstants.BUFF_SERVICE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedservicedetailscreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicedetailscreen.isTechnicianIsSelected(defaulttech));
		selectedservicedetailscreen.clickCancel();
		selectedservicedetailscreen.clickCancel();

		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedservicedetailscreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicedetailscreen.isTechnicianIsSelected(defaulttech));
		selectedservicedetailscreen.clickCancel();
		selectedservicedetailscreen.clickCancel();
		servicesscreen.clickBackServicesButton();

		servicesscreen.clickTechnicianToolbarIcon();
		serviceTypesScreen.clickOnPanel(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		selectedservicedetailscreen.selecTechnician("Inspector 1");
		selectedservicedetailscreen.clickSaveButton();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.switchToSelectedServicesTab();
		servicesscreen.selectServiceSubSrvice(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicedetailscreen.clickTechniciansCell();

		Assert.assertTrue(selectedservicedetailscreen.isTechnicianIsSelected("Inspector 1"));
		selectedservicedetailscreen.clickCancel();
		selectedservicedetailscreen.clickCancel();
		selectedservicebundlescreen.clickCancel();
		servicesscreen.switchToAvailableServicesTab();

		servicesscreen.clickTechnicianToolbarIcon();
		serviceTypesScreen.clickOnPanel("WHEEL REPAIR");
		selectedservicedetailscreen.selecTechnician("Inspector 1");
		selectedservicedetailscreen.selecTechnician("Man-Insp 1");
		selectedservicedetailscreen.clickSaveButton();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.switchToSelectedServicesTab();
		servicesscreen.selectServiceSubSrvice(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicedetailscreen.clickTechniciansCell();

		Assert.assertTrue(selectedservicedetailscreen.isTechnicianIsSelected("Inspector 1"));
		Assert.assertTrue(selectedservicedetailscreen.isTechnicianIsSelected("Man-Insp 1"));
		selectedservicedetailscreen.clickCancel();
		selectedservicedetailscreen.clickCancel();
		selectedservicebundlescreen.clickCancel();
		servicesscreen.switchToAvailableServicesTab();

		servicesscreen.selectServicePanel(iOSInternalProjectConstants.BUFF_SERVICE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicedetailscreen.isTechnicianIsSelected(defaulttech));
		selectedservicedetailscreen.clickCancel();
		selectedservicedetailscreen.clickCancel();
		servicesscreen.clickBackServicesButton();

		RegularQuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		RegularOrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 50029:WO: Regular - Verify that default tech is not changed when reset order split",
			description = "Verify that default tech is not changed when reset order split")
	public void testWOVerifyThatDefaultTechIsNotChangedWhenResetOrderSplit() {

		final String VIN = "1D7HW48NX6S507810";
		final String pricevalue = "21";
		final String defaulttech = "Oksi Employee";
		final String techname = "Oksana Zayats";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);

		RegularQuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		RegularServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAULT_TECH_OKSI);
		selectedServiceDetailsScreen.setServicePriceValue(pricevalue);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(defaulttech));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesscreen.selectSubService("3/4\" - Penny Size");

		vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician(techname);
		selectedservicescreen.unselecTechnician("Employee Simple 20%");
		selectedservicescreen.clickSaveButton();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), "Changing default employees for a work order will change split data for all services.");
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAULT_TECH_OKSI);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(defaulttech));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedServiceDetailsScreen.setServicePriceValue(pricevalue);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(selectedservicescreen.isTechnicianIsSelected(techname));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 53502:WO Monitor: Regular - Verify that it is possible to assign tech when WO is not started," +
			"Test Case 53503:WO Monitor: Regular - Verify that it is possible to assign tech when Service is not completed and vice versa",
			description = "Verify that it is possible to assign tech when WO is not started," +
					"Verify that it is possible to assign tech when Service is not completed and vice versa")
	public void testWOVerifyThatItIsPossibleToAssignTechWhenWOIsNotStarted() {

		final String VIN = "1D7HW48NX6S507810";
		final String techname = "Oksi Test User";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.selectLocation("Test Location ZZZ");
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedServiceDetailsScreen.setServicePriceValue("123");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.setServicePriceValue("2000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart("Grill");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedServiceDetailsScreen.setServicePriceValue("1000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedServiceDetailsScreen.setServicePriceValue("1000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart("Grill");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedServiceDetailsScreen.setServicePriceValue("1000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart("Grill");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation("Test Location ZZZ");
		teamWorkOrdersScreen.clickSearchSaveButton();


		teamWorkOrdersScreen.clickOnWO(wonumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
		orderMonitorScreen.selectPanel("3/4\" - Penny Size");
		orderMonitorScreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician(techname);
		selectedservicescreen.saveSelectedServiceDetails();
		orderMonitorScreen = new RegularOrderMonitorScreen();
		orderMonitorScreen.selectPanel("3/4\" - Penny Size");
		Assert.assertEquals(orderMonitorScreen.getTechnicianValue(), techname);
		orderMonitorScreen.clickServiceDetailsDoneButton();

		orderMonitorScreen.clickStartOrderButton();
		Assert.assertTrue(Helpers.getAlertTextAndAccept().contains("Would you like to start repair order on"));
		orderMonitorScreen = new RegularOrderMonitorScreen();

		orderMonitorScreen.selectPanel(iOSInternalProjectConstants.SR_S1_MONEY_PANEL + " (Grill)");
		orderMonitorScreen.clickTech();
		selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician("Manager 1");
		selectedservicescreen.saveSelectedServiceDetails();
		orderMonitorScreen = new RegularOrderMonitorScreen();
		final String SR_S1_MONEY_PANEL = iOSInternalProjectConstants.SR_S1_MONEY_PANEL + " (Grill)";
		orderMonitorScreen.selectPanel(SR_S1_MONEY_PANEL);
		orderMonitorScreen.clickStartService();
		orderMonitorScreen = new RegularOrderMonitorScreen();
		orderMonitorScreen.selectPanel(SR_S1_MONEY_PANEL);
		orderMonitorScreen.setCompletedServiceStatus();
		Assert.assertEquals(orderMonitorScreen.getPanelStatus(SR_S1_MONEY_PANEL), "Completed");
		orderMonitorScreen.selectPanel(SR_S1_MONEY_PANEL);
		orderMonitorScreen.clickTech();
		orderMonitorScreen.clickServiceDetailsDoneButton();

		orderMonitorScreen.clickBackButton();

		teamWorkOrdersScreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 53505:WO Monitor: Regular - Verify that it is not possible to assign tech when WO is On Hold",
			description = "Verify that it is not possible to assign tech when WO is On Hold")
	public void testWOVerifyThatItIsNotPossibleToAssignTechWhenWOIsOnHold() {

		final String VIN = "1D7HW48NX6S507810";
		final String techname = "Oksi Test User";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.selectLocation("Test Location ZZZ");
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedServiceDetailsScreen.setServicePriceValue("123");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.setServicePriceValue("2000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart("Grill");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedServiceDetailsScreen.setServicePriceValue("1000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedServiceDetailsScreen.setServicePriceValue("1000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart("Grill");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		selectedServiceDetailsScreen.setServicePriceValue("1000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.selectVehiclePart("Grill");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation("Test Location ZZZ");
		teamWorkOrdersScreen.clickSearchSaveButton();


		teamWorkOrdersScreen.clickOnWO(wonumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();

		orderMonitorScreen.changeStatusForWorkOrder("On Hold", "On Hold new reason");
		orderMonitorScreen = new RegularOrderMonitorScreen();
		orderMonitorScreen.selectPanel("3/4\" - Penny Size");
		orderMonitorScreen.clickTech();

		orderMonitorScreen.clickServiceDetailsDoneButton();
		teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 66362:WO: Regular - Verify that Tech split assigned form Vehicle screen is set to services under list",
			description = "Verify that Tech split assigned form Vehicle screen is set to services under list")
	public void testWOVerifyThatTechSplitAssignedFormVehicleScreenIsSetToServicesUnderList() {

		final String VIN = "1D7HW48NX6S507810";
		final String techname1 = "Inspector 1";
		final String techname2 = "Man-Insp 1";
		final List<String> servicesList = new ArrayList<>();
		servicesList.add(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesList.add(iOSInternalProjectConstants.DYE_SERVICE);
		servicesList.add(iOSInternalProjectConstants.WHEEL_SERVICE);

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (String serviceName : servicesList) {
			servicesscreen.selectService(serviceName);
		}

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selecTechnician(techname1);
		selectedServiceDetailsScreen.selecTechnician(techname2);
		selectedServiceDetailsScreen.clickSaveButton();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), "Changing default employees for a work order will change split data for all services.");


		vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (String serviceName : servicesList) {
			selectedServiceDetailsScreen = selectedServicesScreen.openSelectedServiceDetails(serviceName);
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(techname1));
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(techname2));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServicesScreen = new RegularSelectedServicesScreen();
		}

		selectedServicesScreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 68296:WO: Regular - Verify price matrix item doesn't have additional services - its main service's tech split amount is equal to main service's amount",
			description = "Verify price matrix item doesn't have additional services - its main service's tech split amount is equal to main service's amount")
	public void testWOVerifyPriceMatrixItemDoesntHaveAdditionalServicesItsMainServicesTechSplitAmountIsEqualToMainServicesAmount() {

		final String VIN = "1D7HW48NX6S507810";
		final String techname1 = "Inspector 1";
		final String techname2 = "Man-Insp 1";
		final String matrixServicePrice = "100";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.CALC_PRICE_MATRIX);
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("Back Glass");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");
		vehiclePartScreen.setPrice(matrixServicePrice);
		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();
		servicesscreen = new RegularServicesScreen();
		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage("Employee Simple 20%"), "%100.00");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$100.00");

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 68297:WO: HD - Verify price matrix item has money additional service - its main service's tech split amount is equal to main service's amount",
			description = "Verify price matrix item has money additional service - its main service's tech split amount is equal to main service's amount")
	public void testWOVerifyPriceMatrixItemHasMoneyAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmount() {

		final String VIN = "1D7HW48NX6S507810";
		final String matrixServicePrice = "100";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.CALC_PRICE_MATRIX);
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("Back Glass");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");
		vehiclePartScreen.setPrice(matrixServicePrice);
		RegularSelectedServiceDetailsScreen regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage("Employee Simple 20%"), "%100.00");
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice("Employee Simple 20%"), "$100.00");
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
		Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
		regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

		vehiclePartScreen.selectDiscaunt("Calc_Money_PP_Panel");
		Assert.assertEquals(vehiclePartScreen.getPriceMatrixTotalPriceValue(), "$110.00");
		regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage("Employee Simple 20%"), "%100.00");
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice("Employee Simple 20%"), "$100.00");
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
		Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
		regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();
		servicesscreen = new RegularServicesScreen();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$110.00");

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 68298:WO: Regular - price matrix item has percentage additional service - its main service's tech split amount is equal to main service's amount + additional percentage service's amount",
			description = "Price matrix item has percentage additional service - its main service's tech split amount is equal to main service's amount + additional percentage service's amount")
	public void testWOVerifyPriceMatrixItemHasPercentageAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmountPlusAdditionalPercentage() {

		final String VIN = "1D7HW48NX6S507810";
		final String matrixServicePrice = "100";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.CALC_PRICE_MATRIX);
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("Back Glass");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");
		vehiclePartScreen.setPrice(matrixServicePrice);
		RegularSelectedServiceDetailsScreen regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage("Employee Simple 20%"), "%100.00");
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice("Employee Simple 20%"), "$100.00");
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
		Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
		regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

		vehiclePartScreen.clickDiscaunt("Calc_Discount");
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue("-20");
		selectedservicescreen.saveSelectedServiceDetails();

		Assert.assertEquals(vehiclePartScreen.getPriceMatrixTotalPriceValue(), "$80.00");
		regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage("Employee Simple 20%"), "%100.00");
		Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice("Employee Simple 20%"), "$80.00");
		Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
		Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
		regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();
		servicesscreen = new RegularServicesScreen();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$80.00");

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 81676:WO: if service has default technician and its amount is 0 then default technician should be assigned to the service (not technician split at work order level)",
			description = "Verify if service has default technician and its amount is 0 then default technician should be assigned to the service (not technician split at work order level)")
	public void testWOVerifyIfServiceHasDefaultTechnicianAndItsAmountIs0ThenDefaultTechnicianShouldBeAssignedToTheService_NotTechnicianSplitAtWorkOrderLevel() {

		final String VIN = "1D7HW48NX6S507810";
		final String servicePrice = "0";
		final String defaulttech  = "Employee Simple 20%";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen serviceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		serviceDetailsScreen.setServicePriceValue(servicePrice);
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		serviceDetailsScreen.saveSelectedServiceDetails();
		serviceDetailsScreen.selectVehiclePart("Grill");
		serviceDetailsScreen.saveSelectedServiceDetails();
		serviceDetailsScreen.saveSelectedServiceDetails();

		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.selecTechnician("Ded Talash");
		selectedservicescreen.saveSelectedServiceDetailsWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedServicesScreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		serviceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		serviceDetailsScreen.isTechnicianIsSelected(defaulttech);
		serviceDetailsScreen.cancelSelectedServiceDetails();
		serviceDetailsScreen.cancelSelectedServiceDetails();
		servicesscreen = new RegularServicesScreen();

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 81677:WO: Verify that correct tech split amount is shown for matrix service when change price to 0",
			description = "Verify that correct tech split amount is shown for matrix service when change price to 0")
	public void testWOVerifyThatCorrectTechSplitAmountIsShownForMatrixServiceWhenChangePriceTo0() {

		final String VIN = "1D7HW48NX6S507810";

		final String servicePrice = "5";
		final String serviceZeroPrice = "0";
		final String defaulttech  = "Employee Simple 20%";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();

		RegularOrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectSubService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
		RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");
		vehiclePartScreen.setSizeAndSeverity("CENT", "MEDIUM");
		vehiclePartScreen.setPrice(servicePrice);
		vehiclePartScreen.clickSave();
		vehiclePartScreen = pricematrix.selectPriceMatrix("VP1 zayats");

		vehiclePartScreen.setPrice(serviceZeroPrice);
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_DEFAULT_TECH_SPLIT_WILL_BE_ASSIGNED_IF_SET_ZERO_AMAUNT);

		vehiclePartScreen.clickOnTechnicians();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_VEHICLE_PART);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(defaulttech));
		Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(defaulttech), PricesCalculations.getPriceRepresentation(serviceZeroPrice));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		vehiclePartScreen = new RegularVehiclePartScreen();
		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();

		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 81699:WO: Monitor - Verify that tech split amount is shown correct under monitor for service with Adjustments",
			description = "Verify that tech split amount is shown correct under monitor for service with Adjustments")
	public void testWOVerifyThatTechSplitAmountIsShownCorrectUnderMonitorForServiceWithAdjustments() {

		final String VIN = "1D7HW48NX6S507810";
		final String servicePrice = "2000";
		final String vehiclePart = "Hood";
		final String adjustment = "OWO_Discount";
		final String defaulttech  = "Oksana Zayats";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen serviceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		serviceDetailsScreen.setServicePriceValue(servicePrice);
		serviceDetailsScreen.clickAdjustments();
		serviceDetailsScreen.selectAdjustment(adjustment);
		serviceDetailsScreen.saveSelectedServiceDetails();

		serviceDetailsScreen.clickVehiclePartsCell();
		serviceDetailsScreen.selectVehiclePart(vehiclePart);
		serviceDetailsScreen.saveSelectedServiceDetails();
		serviceDetailsScreen.saveSelectedServiceDetails();


		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = homescreen.clickTeamWorkordersButton();
		teamWorkOrdersScreen.clickOnWO(wonumber);
		RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		Assert.assertEquals(orderMonitorScreen.getMonitorServiceAmauntValue(iOSInternalProjectConstants.SR_S1_MONEY + " (Hood)"), "$1,600.00 x 1.00");
		orderMonitorScreen.selectPanel(iOSInternalProjectConstants.SR_S1_MONEY + " (Hood)");
		Assert.assertEquals(orderMonitorScreen.getServiceDetailsPriceValue(), "$2,000.00");
		orderMonitorScreen.clickTech();
		serviceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		Assert.assertTrue(serviceDetailsScreen.isTechnicianIsSelected(defaulttech));
		Assert.assertEquals(serviceDetailsScreen.getTechnicianPrice(defaulttech), "$1,600.00");
		Assert.assertEquals(serviceDetailsScreen.getCustomTechnicianPercentage(defaulttech), "%100.00");

		serviceDetailsScreen.cancelSelectedServiceDetails();
		orderMonitorScreen.clickServiceDetailsDoneButton();
		orderMonitorScreen.clickBackButton();

		teamWorkOrdersScreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 82189:WO: Verify that required services has correct tech",
			description = "Verify that required services has correct tech")
	public void testVerifyThatRequiredServicesHasCorrectTech() {

		final String VIN = "1D7HW48NX6S507810";
		final String vehiclePart = "Hood";
		final String defaulttech = "Oksana Zayats";

		final String tech1 = "Inspector 1";
		final String tech2 = "Man-Insp 1";

		List<String> services = new ArrayList<>();
		services.add("SR_S6_BI_I3_Money");
		services.add("Oksi_Service_PP_Labor");

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_REQUIRED_SERVICES_ALL);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		RegularSelectedServiceBundleScreen serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails("Calc_Bundle");

		for (String srvName : services) {
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(srvName);
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
			selectedServiceDetailsScreen.clickCancel();
		}
		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		vehiclescreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();

		selectedServiceDetailsScreen.selecTechnician(tech1);
		selectedServiceDetailsScreen.selecTechnician(tech2);
		selectedServiceDetailsScreen.unselecTechnician(defaulttech);
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails("Calc_Bundle");

		for (String srvName : services) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(srvName);
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
			selectedServiceDetailsScreen.clickCancel();
		}
		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServiceDetailsScreen = selectedServicesScreen.openSelectedServiceDetails("Calc_Money_PP_Vehicle");
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen.clickOnSelectedService("Calc_Price_Matrix");
		RegularPriceMatrixScreen priceMatrixScreen = selectedServiceDetailsScreen.selectPriceMatrices("Calc_Matrix");
		RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix("Back Glass");
		vehiclePartScreen.setSizeAndSeverity("CENT", "LIGHT");
		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails("Calc_Bundle");

		for (String srvName : services) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(srvName);
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
			selectedServiceDetailsScreen.clickCancel();
		}
		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 82190:WO: Verify that required bundle items has correct def tech",
			description = "Verify that required bundle items has correct def tech")
	public void testVerifyThatRequiredBundleItemsHasCorrectDefTech() {

		final String VIN = "1D7HW48NX6S507810";
		final String vehiclePart1 = "Hood";
		final String vehiclePart2 = "Grill";
		final String defaulttech = "Oksi Employee";

		final String tech1 = "Inspector 1";
		final String tech2 = "Man-Insp 1";

		final String bundleService = "Bundle_with_def_tech_required_service";
		final String service1 = "Oksi_Service_PP_Vehicle";
		final String service2 = "Service_with_default_Tech_oksi";
		final String service3 = "Service_with_default_Tech";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		RegularSelectedServiceBundleScreen serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleService);

		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Oksana Zayats");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.clickCancel();

		selectedServicesScreen = new RegularSelectedServicesScreen();
		vehiclescreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();

		selectedServiceDetailsScreen.selecTechnician(tech1);
		selectedServiceDetailsScreen.selecTechnician(tech2);
		selectedServiceDetailsScreen.unselecTechnician("Oksana Zayats");
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleService);
		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart1);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart2);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		serviceBundleScreen.changeAmountOfBundleService("25");
		serviceBundleScreen.clickSaveButton();


		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleService);
		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Oksi Employee");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 82196:WO: Verify that required bundle items and service with expenses and 0 price has correct def tech after edit WO",
			description = "Verify that required bundle items and service with expenses and 0 price has correct def tech after edit WO")
	public void testVerifyThatRequiredBundleItemsAndServiceWithExpensesAnd0PriceHasCorrectDefTechAfterEditWO() {

		final String VIN = "1D7HW48NX6S507810";
		final String vehiclePart1 = "Hood";
		final String vehiclePart2 = "Grill";
		final String defaulttech = "Oksi Employee";

		final String tech1 = "Inspector 1";
		final String tech2 = "Man-Insp 1";

		final String bundleService = "Bundle_with_def_tech_required_service";
		final String service1 = "Oksi_Service_PP_Vehicle";
		final String service2 = "Service_with_default_Tech_oksi";
		final String service3 = "Service_with_default_Tech";
		final String zaroPrice = "0";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		RegularSelectedServiceBundleScreen serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleService);

		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Oksana Zayats");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.clickCancel();

		selectedServicesScreen = new RegularSelectedServicesScreen();
		vehiclescreen = selectedServicesScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.clickTech();
		selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();

		selectedServiceDetailsScreen.selecTechnician(tech1);
		selectedServiceDetailsScreen.selecTechnician(tech2);
		selectedServiceDetailsScreen.unselecTechnician("Oksana Zayats");
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleService);
		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart1);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart2);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		serviceBundleScreen.changeAmountOfBundleService("25");
		serviceBundleScreen.clickSaveButton();


		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.switchToAvailableServicesTab();
		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("Money_Service_with_expences");
		selectedServiceDetailsScreen.setServicePriceValue(zaroPrice);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesscreen.switchToSelectedServicesTab();
		selectedServicesScreen.openCustomServiceDetails("Money_Service_with_expences");
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech1));
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech2));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new RegularVehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

		serviceBundleScreen = selectedServicesScreen.openSelectBundleServiceDetails(bundleService);
		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Oksi Employee");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		serviceBundleScreen.clickSaveButton();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.openCustomServiceDetails("Money_Service_with_expences");
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech1));
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech2));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServicesScreen = new RegularSelectedServicesScreen();
		selectedServicesScreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 82197:WO - Verify that selected services has correct tech split when change is during creating WO",
			description = "Verify that selected services has correct tech split when change is during creating WO")
	public void testVerifyThatSelectedServicesHaveCorrectTechSplitWhenChangeIsDuringCreatingWO() {

		final String VIN = "1D7HW48NX6S507810";;
		final String defaulttech = "Oksana Zayats";

		final String tech1 = "Vladimir Avsievich";
		final String tech2 = "Nikolay Lomeko";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_ALL_SERVICES);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getWorkOrderNumber();
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selecTechnician(tech1);
		selectedServiceDetailsScreen.selecTechnician(tech2);
		selectedServiceDetailsScreen.unselecTechnician(defaulttech);
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();


		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("Turbo Service");
		servicesscreen.selectService("Vovan Service");

		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails("Turbo Service");
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech1));
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech2));
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails("Vovan Service");
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech1));
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech2));
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServicesScreen.switchToAvailableServicesTab();

		servicesscreen.selectSubService("Matrix Service");
		RegularPriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices("Test Matrix Labor");
		RegularVehiclePartScreen vehiclePartScreen =  pricematrix.selectPriceMatrix("Back Glass");
		vehiclePartScreen.setSizeAndSeverity("DIME", "LIGHT");
		Assert.assertEquals(vehiclePartScreen.getTechniciansValue(), tech1 + ", " + tech2);
		vehiclePartScreen.clickDiscaunt("Oksi_Service_PP_Vehicle");
		selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech1));
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech2));
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		vehiclePartScreen.setTime("2");
		vehiclePartScreen.clickSave();
		vehiclePartScreen.clickSave();
		servicesscreen = new RegularServicesScreen();

		servicesscreen.selectService(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		RegularSelectedServiceBundleScreen selectedservicebundlescreen = new RegularSelectedServiceBundleScreen();
		selectedservicebundlescreen.clickTechniciansIcon();
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech1));
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech2));
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen = selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedservicebundlescreen.changeAmountOfBundleService("70");
		selectedservicebundlescreen.clickSaveButton();
		servicesscreen = new RegularServicesScreen();
		selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		selectedServicesScreen.clickSaveAsFinal();
		selectedServicesScreen.clickSave();
		myworkordersscreen = new RegularMyWorkOrdersScreen();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));
		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 82200:WO - If service price is 0 and has def tech assign to service def tech",
			description = "Verify If service price is 0 and has def tech assign to service def tech")
	public void testVerifyIfServicePriceIs0AndHasDefTechAssignToServiceDefTech() {

		final String VIN = "1D7HW48NX6S507810";;
		final String defaulttech = "Oksana Zayats";

		final String tech1 = "Vladimir Avsievich";
		final String techDefSelected = "Employee Simple 20%";
		final String serviceZaroPrice = "0";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selecTechnician(tech1);
		selectedServiceDetailsScreen.unselecTechnician(defaulttech);
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();


		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);

		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart("Grill");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.setServicePriceValue(serviceZaroPrice);
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(techDefSelected));
		Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(techDefSelected), "$0.00");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 82201:WO - If service price is 0 assign WO split to service in case no def tech",
			description = "Verify If service price is 0 assign WO split to service in case no def tech")
	public void testVerifyIfServicePriceIs0AssignWOSplitToServiceInCaseNoDefTech() {

		final String VIN = "1D7HW48NX6S507810";;
		final String defaulttech = "Oksana Zayats";

		final String tech1 = "Vladimir Avsievich";
		final String serviceZaroPrice = "0";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.clickTech();
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.selecTechnician(tech1);
		selectedServiceDetailsScreen.unselecTechnician(defaulttech);
		selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), "$0.00");
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianIsSelected(tech1));
		Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(tech1), "$0.00");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		servicesscreen = new RegularServicesScreen();
		servicesscreen.cancelWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName="Test Case 26115:WO Monitor: Verify that it is not possible to change status for Service or Phase when Check out required",
			description = "WO Monitor: Verify that it is not possible to change status for Service or Phase when Check out required")
	public void testVerifyThatItIsNotPossibleToChangeStatusForServiceOrPhaseWhenCheckOutRequired() {

		final String VIN  = "1D3HV13T19S825733";
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String defLocation = "Test Location check out required";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation(defLocation);
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
		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.saveSelectedServiceDetails();
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();


		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();


		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defLocation);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		RegularOrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		ordermonitorscreen.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		ordermonitorscreen.clickServiceDetailsCancelButton();

		ordermonitorscreen.clickStartPhase();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		ordermonitorscreen.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		ordermonitorscreen.clickServiceDetailsCancelButton();

		ordermonitorscreen.clickStartPhaseCheckOutButton();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		ordermonitorscreen.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		ordermonitorscreen.clickServiceDetailsCancelButton();

		ordermonitorscreen.changeStatusForStartPhase(OrderMonitorPhases.COMPLETED);
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE),
				OrderMonitorPhases.COMPLETED.getrderMonitorPhaseName());
		ordermonitorscreen.clickBackButton();
		homescreen = teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName="Test Case 29181:WO Monitor: Verify that when option 'Phase enforcement' is OFF and 'Start service required' it possible to start service from inactive phase",
			description = "WO Monitor: Verify that when option 'Phase enforcement' is OFF and 'Start service required' it possible to start service from inactive phase")
	public void testVerifyThatWhenOptionPhaseEnforcementIsOFFAndStartServiceRequiredItIsPossibleToStartServiceFromInactivePhase() {

		final String VIN = "1D3HV13T19S825733";
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String defLocation = "Test Location ZZZ";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation(defLocation);
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
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
		vehiclePartScreen.clickDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
		selectedservicescreen.saveSelectedServiceDetails();
		vehiclePartScreen.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		vehiclePartScreen.saveVehiclePart();
		pricematrix.clickSave();


		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		myworkordersscreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();


		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defLocation);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		RegularOrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(ordermonitorscreen.isStartServiceButtonPresent());
		ordermonitorscreen.clickServiceDetailsDoneButton();

		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonExists());
		ordermonitorscreen.clickStartPhaseButton();
		Assert.assertFalse(ordermonitorscreen.isStartPhaseButtonExists());
		ordermonitorscreen.clickBackButton();
		homescreen = teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName="Test Case 30546:WO Monitor:  Verify that Tech who is not assigned to order service cannot start order",
			description = "WO Monitor: Verify that Tech who is not assigned to order service cannot start order")
	public void testVerifyThatTechWhoIsNotAssignedToOrderServiceCannotStartOrder() {

		final String VIN = "1D3HV13T19S825733";
		final String defServicePrice = "5";
		final String defaultTech = "Employee Simple 20%";
		final String newtech = "Vladimir Avsievich";
		final String vehiclePart = "Hood";
		final String defLocation = "Test Location ZZZ";

		homescreen = new RegularHomeScreen();


		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_DELAY_START);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation(defLocation);
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedServiceDetailsScreen.setServicePriceValue(defServicePrice);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		selectedServiceDetailsScreen.selecTechnician(newtech);
		selectedServiceDetailsScreen.unselecTechnician(defaultTech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		selectedServiceDetailsScreen.selecTechnician(newtech);
		selectedServiceDetailsScreen.unselecTechnician(defaultTech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.clickTechniciansIcon();
		selectedServiceDetailsScreen.selecTechnician(newtech);
		selectedServiceDetailsScreen.unselecTechnician(defaultTech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.clickTechniciansIcon();
		selectedServiceDetailsScreen.selecTechnician(newtech);
		selectedServiceDetailsScreen.unselecTechnician(defaultTech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();


		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defLocation);
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

	@Test(testName="Test Case 30547:WO Monitor: Verify that is it impossible to change status for service/phase when order is not started," +
			"Test Case 30548:WO Monitor: Verify that when order is not started Start date can be changed but not for future",
			description = "WO Monitor: Verify that is it impossible to change status for service/phase when order is not started," +
					"Verify that when order is not started Start date can be changed but not for future")
	public void testVerifyThatIsItImpossibleToChangeStatusForServiceOrPhaseWhenOrderIsNotStarted() {

		final String VIN = "1D3HV13T19S825733";
		final String defServicePrice = "5";
		final String defaultTech = "Oksana Zayats";
		final String newtech = "Vladimir Avsievich";
		final String vehiclePart = "Hood";
		final String defLocation = "Test Location ZZZ";

		homescreen = new RegularHomeScreen();
		RegularMainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		myworkordersscreen.clickAddOrderButton();
		RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_DELAY_START);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation(defLocation);
		String wonum = vehiclescreen.getWorkOrderNumber();
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedServiceDetailsScreen.setServicePriceValue(defServicePrice);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		selectedServiceDetailsScreen.selecTechnician(newtech);
		selectedServiceDetailsScreen.unselecTechnician(defaultTech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		selectedServiceDetailsScreen.selecTechnician(newtech);
		selectedServiceDetailsScreen.unselecTechnician(defaultTech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.clickTechniciansIcon();
		selectedServiceDetailsScreen.selecTechnician(newtech);
		selectedServiceDetailsScreen.unselecTechnician(defaultTech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.clickTechniciansIcon();
		selectedServiceDetailsScreen.selecTechnician(newtech);
		selectedServiceDetailsScreen.unselecTechnician(defaultTech);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		RegularTeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrders();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defLocation);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		RegularOrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();

		Assert.assertEquals(ordermonitorscreen.getPanelStatus("3/4\" - Penny Size"),
				OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE),
				OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE +
						" (" + vehiclePart + ")"),
				OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.SR_S1_MONEY +
						" (" + vehiclePart + ")"),
				OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());

		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.SR_S1_MONEY +
				" (" + vehiclePart + ")");

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
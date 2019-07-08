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
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.ServiceRequestStatus;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.OrderMonitorServiceDetailsPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.WorkOrderTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class IOSSmokeTestCases extends ReconProBaseTestCase {

	public HomeScreen homescreen;


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
		mobilePlatform = MobilePlatform.IOS_HD;
		initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Ios_automation",
				envType);
		MainScreen mainscr = new MainScreen();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.setInsvoicesCustomLayoutOff();
		settingsscreen.clickHomeButton();
	}

	@AfterMethod
	public void closeBrowser() {
		if (webdriver != null)
			webdriver.quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testUpdateDatabase(String rowID,
								   String description, JSONObject testData) {
		homescreen = new HomeScreen();
		MainScreen mainscr = homescreen.clickLogoutButton();
		mainscr.updateDatabase();
		HomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen.clickStatusButton();
		homescreen.updateDatabase();
		mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testUpdateVIN(String rowID,
							  String description, JSONObject testData) {
		homescreen = new HomeScreen();
		MainScreen mainscr = homescreen.clickLogoutButton();
		mainscr.updateVIN();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen.clickStatusButton();
		homescreen.updateVIN();
		//homescreen.clickLogoutButton();
	}

	//Test Case 8441:Add Retail Customer in regular build
	@Test(testName = "Test Case 8441:Add Retail Customer in regular build", description = "Create retail customer")
	public void testCreateRetailCustomer()  {

		//resrtartApplication();
		//MainScreen mainscreen = new MainScreen();
		//HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USER_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		AddCustomerScreen addcustomerscreen = customersscreen.clickAddCustomersButton();

		addcustomerscreen.addCustomer(firstname, lastname, companyname, street,
				city, state, zip, country, phone, mail);
		addcustomerscreen.clickSaveBtn();
		customersscreen.selectCustomerWithoutEditing(firstname);
		Assert.assertTrue(customersscreen.isCustomerExists(firstname));

		customersscreen.clickHomeButton();
		//homescreen.clickLogoutButton();
	}

	//Test Case 8439:Edit Customer
	@Test(testName = "Test Case 8439:Edit Customer ", description = "Edit retail customer")
	public void testEditRetailCustomer()  {
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
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();

		customersscreen.selectCustomerWithoutEditing(firstname);
		AddCustomerScreen addcustomerscreen = customersscreen.selectFirstCustomerToEdit();

		addcustomerscreen.editCustomer(firstnamenew, lastname, companyname,
				street, city, city, zip, zip, phone, mail);
		addcustomerscreen.clickSaveBtn();
		customersscreen.selectCustomerWithoutEditing(firstnamenew);
		Assert.assertTrue(customersscreen.isCustomerExists(firstnamenew));
		customersscreen.clickHomeButton();
		MainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
	}

	// Test Case 8460: Delete Customer 
	@Test(testName = "Test Case 8460: Delete Customer ", description = "Delete retail customer")
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

		//resrtartApplication();
		MainScreen mainscreen = new MainScreen();
		mainscreen.updateDatabase();
		HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.selectCustomerWithoutEditing(firstnamenew);
		Assert.assertFalse(customersscreen.isCustomerExists(firstnamenew));
		customersscreen.clickHomeButton();
		//homescreen.clickLogoutButton();
	}

	@Test(testName = "Test Case 8685:Set Inspection to non Single page (HD) ", description = "Set Inspection To Non Single Page Inspection Type")
	public void testSetInspectionToNonSinglePage() {

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testEditRetailInspectionNotes(String rowID,
											  String description, JSONObject testData) {
		final String _notes1 = "Test\nTest 2";

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectFirstInspection();
		myinspectionsscreen.clickEditInspectionButton();
		VehicleScreen vehiclescreen = new VehicleScreen();
		NotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.setNotes(_notes1);
		notesscreen.addQuickNotes();

		notesscreen.clickSaveButton();
		vehiclescreen.clickNotesButton();
		Assert.assertEquals(notesscreen.getNotesValue(), _notes1 + "\n" + notesscreen.quicknotesvalue);
		notesscreen.clickSaveButton();
		vehiclescreen.cancelOrder();
		myinspectionsscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testApproveInspectionOnDevice(String rowID,
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
		VehicleScreen vehiclescreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		final String inspNumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.saveWizard();

		myinspectionsscreen.selectInspectionForApprove(inspNumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspNumber);
		approveinspscreen.clickApproveAfterSelection();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen = new MyInspectionsScreen();
		Assert.assertTrue(myinspectionsscreen.isInspectionApproved(inspNumber));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testArchiveAndUnArchiveTheInspection(String rowID,
													 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String archiveReason = "Reason 2";

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		String myinspetoarchive = vehiclescreen.getInspectionNumber();
		vehiclescreen.saveWizard();

		myinspectionsscreen.clickHomeButton();

		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.archiveInspection(myinspetoarchive,
				archiveReason);
		Assert.assertFalse(myinspectionsscreen.isInspectionExists (myinspetoarchive));
		myinspectionsscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 8430:Create work order with type is assigned to a specific client", description = "Create work order with type is assigned to a specific client ")
	public void testCreateWorkOrderWithTypeIsAssignedToASpecificClient() {
		final String VIN = "ZWERTYASDFDDXZBVB";
		final String _po = "1111 2222";
		final String summ = "71.40";

		final String license = "Iphone_Test_Spec_Client";

		homescreen = new HomeScreen();
		MainScreen mainscreen = homescreen.clickLogoutButton();
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

		SelectEnvironmentPopup selectenvscreen = new SelectEnvironmentPopup();
		LoginScreen loginscreen = selectenvscreen.selectEnvironment("Dev Environment");
		loginscreen.registeriOSDevice(regCode);
		mainscreen.userLogin(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN, iOSInternalProjectConstants.USER_PASSWORD);

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER,
				WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertTrue(servicesscreen.checkDefaultServiceIsSelected());
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE));
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertTrue(ordersummaryscreen.checkDefaultServiceIsSelected());
		Assert.assertTrue(ordersummaryscreen.checkServiceIsSelected(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(summ));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		ordersummaryscreen.clickSave();
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		vehiclescreen.setVIN(VIN);

		ordersummaryscreen =vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.clickSave();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
		invoiceinfoscreen.clickSaveEmptyPO();
		invoiceinfoscreen.setPO(_po);
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateWorkOrderWithTeamSharingOption(String rowID,
														 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrdersData().get(0);

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);

		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String workOrderNumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		servicesscreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), workOrderData.getMoneyServiceData().getServicePrice());
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), workOrderData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedservicescreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
		// =====================================
		servicesscreen.selectService(workOrderData.getPercentageServiceData().getServiceName());

		servicesscreen.openServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), workOrderData.getPercentageServiceData().getServicePrice());
		selectedservicescreen.clickAdjustments();
		Assert.assertEquals(selectedservicescreen.getAdjustmentValue(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
				workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
		selectedservicescreen.selectAdjustment(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedservicescreen.saveSelectedServiceDetails();
		// =====================================
		servicesscreen.selectService(workOrderData.getBundleService().getBundleServiceName());
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
		for (ServiceData serviceData : workOrderData.getBundleService().getServices()) {
			if (serviceData.isSelected()) {
				Assert.assertTrue(selectedservicebundlescreen.checkBundleIsSelected(serviceData.getServiceName()));
				selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
				selectedservicescreen.setServiceQuantityValue(serviceData.getServiceQuantity());
			} else {
				Assert.assertTrue(selectedservicebundlescreen.checkBundleIsNotSelected(serviceData.getServiceName()));
				selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
			}
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(workOrderData.getBundleService().getBundleServiceName()));
		// =====================================
		for (ServiceData serviceData : workOrderData.getPercentageServices()) {
			servicesscreen.selectService(serviceData.getServiceName());
			Assert.assertTrue(servicesscreen.checkServiceIsSelected(serviceData.getServiceName()));
		}
		// =====================================
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesscreen.selectService(matrixServiceData.getMatrixServiceName());
		PriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
		pricematrix.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
		pricematrix.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		Assert.assertEquals(pricematrix.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
		Assert.assertTrue(pricematrix.isNotesExists());
		for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
			pricematrix.selectDiscaunt(serviceData.getServiceName());
		}
		pricematrix.clickSaveButton();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(matrixServiceData.getMatrixServiceName()));

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		for (ServiceData serviceData : workOrderData.getSelectedServices()) {
			Assert.assertTrue(ordersummaryscreen.checkServiceIsSelected(serviceData.getServiceName()));
		}
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
		ordersummaryscreen.saveWizard();

		WorkOrderData workOrderDataCopiedServices = testCaseData.getWorkOrdersData().get(1);
		vehiclescreen = myworkordersscreen.copyServicesForWorkOrder(workOrderNumber, iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER,
				WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
		vehiclescreen.setVIN(workOrderDataCopiedServices.getVehicleInfoData().getVINNumber());
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.verifyServicesAreSelected(workOrderDataCopiedServices.getSelectedServices());
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), workOrderDataCopiedServices.getWorkOrderPrice());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testApproveInspectionsOnDeviceViaAction(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inspectionsID = new ArrayList<>();

		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			VehicleScreen vehiclescreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
					InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
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
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		for (String inspectionNumber : inspectionsID) {
			approveinspscreen.selectInspectionForApprove(inspectionNumber);
			approveinspscreen.clickApproveAfterSelection();
		}

		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();

		myinspectionsscreen = new MyInspectionsScreen();
		for (String inspectionNumber : inspectionsID) {
			Assert.assertTrue(myinspectionsscreen.isInspectionApproved(inspectionNumber));
		}
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testArchiveInspectionsOnDeviceViaAction(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inspectionsID = new ArrayList<>();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			VehicleScreen vehiclescreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
					InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
			vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
			vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
			vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
			inspectionsID.add(vehiclescreen.getInspectionNumber());
			vehiclescreen.saveWizard();

		}
		myinspectionsscreen = new MyInspectionsScreen();
		myinspectionsscreen.archiveInspections(inspectionsID, testCaseData.getArchiveReason());

		for (String inspectionNumber : inspectionsID) {
			Assert.assertFalse(myinspectionsscreen.isInspectionExists(inspectionNumber));
		}
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testApproveInspectionOnBackOfficeFullInspectionApproval(String rowID,
																		String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
		String inpectionnumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
		vehiclescreen.saveWizard();

		myinspectionsscreen.clickHomeButton();
		MainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
		Helpers.waitABit(60*1000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();

		inspectionspage.approveInspectionByNumber(inpectionnumber);

		DriverBuilder.getInstance().getDriver().quit();

		mainscreen.updateDatabase();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.isInspectionApproved(inpectionnumber));

		myinspectionsscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testApproveInspectionOnBackOfficeLinebylineApproval(String rowID,
																	String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER,
				InspectionsTypes.INSP_LA_DA_INSPTYPE);
		String inpectionnumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

		vehiclescreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
		MainScreen mainscreen = homescreen.clickLogoutButton();
		mainscreen.updateDatabase();
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

		inspectionspage.approveInspectionLinebylineApprovalByNumber(
				inpectionnumber, iOSInternalProjectConstants.DISC_EX_SERVICE1, iOSInternalProjectConstants.DYE_SERVICE);

		DriverBuilder.getInstance().getDriver().quit();

		mainscreen.updateDatabase();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.isInspectionApproved(inpectionnumber));

		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice(String rowID,
																						 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		final String teamName= "Default team";
		final String serviceName= "Test Company (Universal Client)";

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequestWithSelectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER,
				ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
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

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
			if (serviceData.getServiceQuantity() != null) {
				servicesscreen.selectService(serviceData.getServiceName());
				SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
				servicedetailsscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				servicedetailsscreen.saveSelectedServiceDetails();
			} else
				servicesscreen.selectService(serviceData.getServiceName());
		}

		ClaimScreen claimscreen = servicesscreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(serviceRequestData.getInsuranceCompany().getInsuranceCompanyName());
		servicesscreen.clickSave();
		Helpers.waitForAlert();
			String alertText = Helpers.getAlertTextAndAccept();
			Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		QuestionsScreen questionsscreen = new QuestionsScreen();
		questionsscreen.drawSignature();
		questionsscreen.clickSave();

		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
		questionsscreen = new QuestionsScreen();
		questionsscreen.selectAnswerForQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(newsrnumber), iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(newsrnumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
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

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();

		servicerequestslistpage.verifySearchFieldsAreVisible();

		servicerequestslistpage.selectSearchTeam(teamName);
		servicerequestslistpage.selectSearchTechnician("Employee Simple 20%");
		servicerequestslistpage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		servicerequestslistpage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		servicerequestslistpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		servicerequestslistpage.setSearchFreeText(newsrnumber);
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

		final String questionValue = "Test Answer 1";

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToSinglePageInspection();
		settingsscreen.clickHomeButton();

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.createInspectionFromServiceReques(newsrnumber, InspectionsTypes.INSP_FOR_SR_INSPTYPE);

		SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen();
		String inspnumber = singlepageinspectionscreen.getInspectionNumber();
		singlepageinspectionscreen.expandToFullScreeenSevicesSection();
		BaseUtils.waitABit(2000);
		ServicesScreen servicesscreen = new ServicesScreen();
		for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
			Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2()));
			if (serviceData.getQuestionData()!= null) {
				SelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
				selectedservicedetailsscreen.answerQuestion(serviceData.getQuestionData());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			}
		}
		BundleServiceData bundleServiceData = inspectionData.getBundleService();
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(bundleServiceData.getBundleServiceName(), bundleServiceData.getBundleServiceAmount()));
		SelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				selectedservicedetailsscreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
				selectedservicedetailsscreen.saveSelectedServiceDetails();
			} else
				selectedservicedetailsscreen.selectBundle(serviceData.getServiceName());
		}
		selectedservicedetailsscreen.saveSelectedServiceDetails();


		singlepageinspectionscreen = new SinglePageInspectionScreen();
		singlepageinspectionscreen.collapseFullScreen();

		singlepageinspectionscreen.expandToFullScreeenQuestionsSection();

		Assert.assertTrue(singlepageinspectionscreen.isSignaturePresent());
		singlepageinspectionscreen.swipeScreenRight();
		singlepageinspectionscreen.swipeScreenRight();
		singlepageinspectionscreen.swipeScreenUp();

		Assert.assertTrue(singlepageinspectionscreen.isAnswerPresent(questionValue));
		singlepageinspectionscreen.collapseFullScreen();
		singlepageinspectionscreen.clickSaveButton();
		servicerequestsscreen = new ServiceRequestsScreen();
		homescreen = servicerequestsscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		Assert.assertTrue(myinspectionsscreen.isInspectionExists(inspnumber));
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspnumber), inspectionData.getInspectionPrice());

		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);

		myinspectionsscreen.clickApproveInspections();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber);
		approveinspscreen.clickApproveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateWOFromServiceRequest(String rowID,
											   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		homescreen = settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		homescreen = customersscreen.clickHomeButton();
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.clickHomeButton();
		//test case
		boolean createWOExists = false;
		final int timaoutMinules = 15;
		int iterator = 0;
		while((iterator < timaoutMinules) | (!createWOExists)) {

			servicerequestsscreen = homescreen.clickServiceRequestsButton();
			servicerequestsscreen.selectServiceRequest(newsrnumber);
			createWOExists = servicerequestsscreen.isCreateWorkOrderActionExists();
			if (!createWOExists) {
				servicerequestsscreen.selectServiceRequest(newsrnumber);
				servicerequestsscreen.clickHomeButton();
				Helpers.waitABit(1000*30);
			} else {
				servicerequestsscreen.selectCreateWorkOrderRequestAction();
				WorkOrderTypesPopup workOrderTypesPopup= new WorkOrderTypesPopup();
				workOrderTypesPopup.selectWorkOrderType(WorkOrdersTypes.WO_FOR_SR.getWorkOrderTypeName());
				break;
			}

		}

		VehicleScreen vehiclescreen = new VehicleScreen();
		String wonumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice2()));
		Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(workOrderData.getBundleService().getBundleServiceName(), PricesCalculations.getPriceRepresentation(workOrderData.getBundleService().getBundleServiceAmount())));

		SelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openServiceDetails(workOrderData.getBundleService().getBundleServiceName());
		selectedservicedetailsscreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		servicesscreen.clickSave();
		Helpers.waitForAlert();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.THE_VIN_IS_INVALID_AND_SAVE_WORKORDER);
		servicerequestsscreen = new ServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));
		homescreen = myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionFromInvoiceWithTwoWOs(String rowID, String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertEquals(ordersummaryscreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSave();

		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.copyVehicleForWorkOrder(wonumber1, iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehiclescreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
		Assert.assertEquals(vehiclescreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());
		Assert.assertEquals(vehiclescreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
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
		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		selectedServiceDetailsScreen.clickAdjustments();
		selectedServiceDetailsScreen.selectAdjustment(workOrderData.getPercentageServiceData().
				getServiceAdjustment().getAdjustmentData().getAdjustmentName());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), workOrderData.getPercentageServiceData().getServicePrice());

		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			if (serviceData.getServiceQuantity() != null) {
				selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			} else
				selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		for (ServiceData serviceData : workOrderData.getPercentageServices())
			servicesscreen.selectService(serviceData.getServiceName());
		MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		servicesscreen.selectService(matrixServiceData.getMatrixServiceName());
		PriceMatrixScreen pricematrix = selectedServiceDetailsScreen.selectMatrics(matrixServiceData.getHailMatrixName());
		VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		pricematrix.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
		Assert.assertTrue(pricematrix.isNotesExists());
		Assert.assertTrue(pricematrix.getTechniciansValue().contains(employee));
		Assert.assertEquals(pricematrix.getPrice(), vehiclePartData.getVehiclePartPrice());
		for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices())
			pricematrix.selectDiscaunt(serviceData.getServiceName());

		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSave();

		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen =  myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_PO_IS_REQUIRED_REGULAR);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testDontAlowToSelectBilleAandNotBilledOrdersTogetherInMultiSelectionMode(String rowID,
																						 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> workOrderIDs = new ArrayList<>();
		final String billingFilterValue = "All";
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
			vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
			workOrderIDs.add(vehiclescreen.getInspectionNumber());
			ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());
			OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			ordersummaryscreen.clickSave();
		}

		for (String workOrderID : workOrderIDs)
			myworkordersscreen.approveWorkOrderWithoutSignature(workOrderID, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.clickCreateInvoiceIconForWO(workOrderIDs.get(0));
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceinfoscreen.clickSaveAsDraft();

		myworkordersscreen.clickFilterButton();
		myworkordersscreen.setFilterBilling(billingFilterValue);
		myworkordersscreen.clickSaveFilter();

		myworkordersscreen.selectWorkOrder(wonumber1);
		for (String menuItem : menuItemsToVerify) {
			Assert.assertFalse(myworkordersscreen.isMenuItemForSelectedWOExists(menuItem), "Find menu: " + menuItem);
		}
		myworkordersscreen.clickDetailspopupMenu();
		vehiclescreen = new VehicleScreen();
		vehiclescreen.clickCancelButton();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerForInvoice(String rowID,
											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();

		myworkordersscreen.clickHomeButton();
		BaseUtils.waitABit(60000);
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();

		myinvoicesscreen.changeCustomerForInvoice(invoicenumber, iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new InvoiceInfoScreen();
		Assert.assertEquals(invoiceinfoscreen.getInvoiceCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
		invoiceinfoscreen.clickFirstWO();
		vehiclescreen = new VehicleScreen();
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

		homescreen = new HomeScreen();
		CarHistoryScreen carhistoryscreen = homescreen.clickCarHistoryButton();
		carhistoryscreen.clickFirstCarHistoryInTable();
		carhistoryscreen.clickCarHistoryMyWorkOrders();
		MyWorkOrdersScreen myworkordersscreen = new MyWorkOrdersScreen();
		String woNumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		VehicleScreen vehicleScreen = myworkordersscreen.copyVehicleForWorkOrder(woNumber, iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		ServicesScreen servicesscreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.cancelWizard();
		myworkordersscreen.clickBackToCarHystoryScreen();
		carhistoryscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCopyInspections(String rowID,
									String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.FOR_COPY_INSP_INSPTYPE);
		final String inspNumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			VisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, visualScreenData.getScreenName());
			visualinteriorscreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
			visualinteriorscreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
			visualinteriorscreen.tapInterior();
			if (visualScreenData.getScreenTotalPrice() != null)
				Assert.assertEquals(visualinteriorscreen.getTotalAmaunt(), visualScreenData.getScreenTotalPrice());
		}
		for (QuestionScreenData questionScreenData : inspectionData.getQuestionScreensData())
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(inspectionData.getMoneyServiceData().getServiceName()));
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), inspectionData.getMoneyServiceData().getServicePrice());
		Assert.assertEquals(selectedservicescreen.getServiceAdjustmentsValue(), inspectionData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(inspectionData.getMoneyServiceData().getServiceName()));
		// =====================================
		servicesscreen.selectService(inspectionData.getPercentageServiceData().getServiceName());
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(inspectionData.getPercentageServiceData().getServiceName()));
		servicesscreen.openServiceDetails(inspectionData.getPercentageServiceData().getServiceName());
		Assert.assertEquals(selectedservicescreen.getServicePriceValue(), inspectionData.getPercentageServiceData().getServicePrice());
		selectedservicescreen.clickAdjustments();
		Assert.assertEquals(selectedservicescreen.getAdjustmentValue(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
				inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
		selectedservicescreen.selectAdjustment(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getAdjustmentsValue(), inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(inspectionData.getPercentageServiceData().getServiceName()));
		// =====================================
		ServicesScreenSteps.selectBundleService(inspectionData.getBundleService());
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(inspectionData.getBundleService().getBundleServiceName()));
		// =====================================
		for (ServiceData serviceData : inspectionData.getPercentageServicesList())
			servicesscreen.selectService(serviceData.getServiceName());
		// =====================================
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		servicesscreen.selectService(matrixServiceData.getMatrixServiceName());
		PriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
		pricematrix.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
		pricematrix.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		Assert.assertEquals(pricematrix.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
		Assert.assertTrue(pricematrix.isNotesExists());
		for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
			pricematrix.selectDiscaunt(serviceData.getServiceName());
		}
		pricematrix.clickSaveButton();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(matrixServiceData.getMatrixServiceName()));
		servicesscreen.saveWizard();
		myinspectionsscreen = new MyInspectionsScreen();
		myinspectionsscreen.selectInspectionForCopy(inspNumber);
		vehiclescreen = new VehicleScreen();
		final String copiedInspection = vehiclescreen.getInspectionNumber();
		Assert.assertEquals(vehiclescreen.getMake(), inspectionData.getVehicleInfo().getVehicleMake());
		Assert.assertEquals(vehiclescreen.getModel(), inspectionData.getVehicleInfo().getVehicleModel());

		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			VisualInteriorScreen visualinteriorscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, visualScreenData.getScreenName());
			visualinteriorscreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
		}

		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		ServicesScreenSteps.verifyServicesAreSelected(inspectionData.getSelectedServices());

		servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS,ScreenNamesConstants.FOLLOW_UP_REQUESTED);
		QuestionsScreen questionScreen = new QuestionsScreen();
		questionScreen.waitQuestionsScreenLoaded();
		SinglePageInspectionScreen singlepageinspectionscreen = new SinglePageInspectionScreen();
		Assert.assertTrue(singlepageinspectionscreen.isSignaturePresent());
		singlepageinspectionscreen.selectNextScreen("BATTERY PERFORMANCE");
		vehiclescreen.swipeScreenRight();
		Assert.assertTrue(singlepageinspectionscreen.isAnswerPresent("Test Answer 1"));
		servicesscreen.clickSave();

		myinspectionsscreen = new MyInspectionsScreen();
		Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(copiedInspection), inspectionData.getInspectionPrice());
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionFromWO(String rowID,
										   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(testCaseData.getWorkOrderData().getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.saveWizard();

		//Test case
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber1), testCaseData.getWorkOrderData().getWorkOrderPrice());
		myworkordersscreen.selectWorkOrderNewInspection(wonumber1);
		vehiclescreen = new VehicleScreen();
		vehiclescreen.verifyMakeModelyearValues(testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleMake(),
				testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleModel(), testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleYear());
		vehiclescreen.cancelOrder();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInvoiceWithTwoWOsAndCopyVehicle(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSave();
		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber1), workOrderData.getWorkOrderPrice());
		myworkordersscreen.copyVehicleForWorkOrder(wonumber1, iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehiclescreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
		Assert.assertEquals(vehiclescreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		invoiceinfoscreen.addWorkOrder(wonumber1);
		final String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		myworkordersscreen = invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
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

		homescreen = new HomeScreen();
		MainScreen mainscreen = homescreen.clickLogoutButton();
		HomeScreen homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualscreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				InspectionsTypes.INSP_CHANGE_INSPTYPE);
		VehicleScreen vehiclescreen = visualscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		ClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		vehiclescreen.saveWizard();
		myinspectionsscreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		BaseUtils.waitABit(90*1000);
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		visualscreen = new VisualInteriorScreen();
		visualscreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
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

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualscreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				InspectionsTypes.INSP_CHANGE_INSPTYPE);
		VehicleScreen vehiclescreen = visualscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		ClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		vehiclescreen.saveWizard();

		myinspectionsscreen.selectInspectionInTable(inspectionnumber);
		myinspectionsscreen.clickChangeCustomerpopupMenu();
		myinspectionsscreen.customersPopupSwitchToRetailMode();
		myinspectionsscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);

		myinspectionsscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();
		BaseUtils.waitABit(60*1000);
		myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		visualscreen = new VisualInteriorScreen();
		visualscreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
		vehiclescreen =visualscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		Assert.assertTrue(vehiclescreen.getInspectionCustomer().contains(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER));
		vehiclescreen.saveWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerOptionForInspectionsBasedOnTypeWithPreselectedCompanies(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				InspectionsTypes.TYPEWITHPRESELECTEDCOMPANIES_INSPTYPE);
		VehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();

		ClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimscreen.saveWizard();
		myinspectionsscreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		BaseUtils.waitABit(30*1000);
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
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

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(workOrderData.getMoneyServiceData().getServiceName());

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.saveWizard();
		myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new VehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancelButton();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerOptionForWOBasedOnTypeWithPreselectedCompanies(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				WorkOrdersTypes.WO_WITH_PRESELECTED_CLIENTS);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(workOrderData.getMoneyServiceData().getServiceName());

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.saveWizard();

		BaseUtils.waitABit(45000);
		myworkordersscreen.changeCustomerForWorkOrder(wonumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new VehicleScreen();
		Assert.assertEquals(vehiclescreen.getWorkOrderCustomer(), iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicesscreen.clickCancelButton();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testChangeCustomerOptionForWOAsChangeWholesaleToRetailAndViceVersa(String rowID,
																				   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(workOrderData.getMoneyServiceData().getServiceName());

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrder(wonumber);
		myworkordersscreen.clickChangeCustomerPopupMenu();
		myworkordersscreen.customersPopupSwitchToRetailMode();
		myworkordersscreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
		customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen =  new VehicleScreen();
		Assert.assertTrue(vehiclescreen.getWorkOrderCustomer().contains(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER));
		servicesscreen.clickCancelButton();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testBlockForTheSameVINIsONVerifyDuplicateVINMessageWhenCreate2WOWithOneVIN(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();


		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.clickSave();
		ordersummaryscreen.waitForCustomWarningMessage(String.format(AlertsCaptions.ALERT_YOU_CANT_CREATE_WORK_ORDER_BECAUSE_VIN_EXISTS,
				WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON.getWorkOrderTypeName(), workOrderData.getVehicleInfoData().getVINNumber()), "Cancel");

		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testBlockForTheSameServicesIsONVerifyDuplicateServicesMessageWhenCreate2WOWithOneService(String rowID,
																										 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();


		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.clickSave();
		ordersummaryscreen.waitForCustomWarningMessage("Duplicate services", "Cancel");
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testEditOptionOfDuplicateServicesMessageForWO(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();


		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
		servicesscreen.selectService(workOrderData.getServiceData().getServiceName());

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.clickSave();
		ordersummaryscreen.waitForCustomWarningMessage("Duplicate services", "Edit");
		servicesscreen = ordersummaryscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.removeSelectedServices(workOrderData.getServiceData().getServiceName());
		Assert.assertFalse(servicesscreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testOverrideOptionOfDuplicateServicesMessageForWO(String rowID,
																  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();


		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSave();
		ordersummaryscreen.waitForCustomWarningMessage("Duplicate services", "Override");
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = new MyWorkOrdersScreen();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));

		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCancelOptionOfDuplicateServicesMessageForWO(String rowID,
																String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();


		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSave();
		ordersummaryscreen.waitForCustomWarningMessage("Duplicate services", "Cancel");
		myworkordersscreen = new MyWorkOrdersScreen();
		Assert.assertFalse(myworkordersscreen.woExists(wonumber));
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSavingInspectionsWithThreeMatrix(String rowID,
													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER,
				InspectionsTypes.VITALY_TEST_INSPTYPE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehiclescreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
		vehiclescreen.setType(inspectionData.getVehicleInfo().getVehicleType());
		vehiclescreen.setPO(inspectionData.getVehicleInfo().getPoNumber());
		String inspnumber = vehiclescreen.getInspectionNumber();
		ClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
		claimscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimscreen.setClaim(inspectionData.getInsuranceCompanyData().getClaimNumber());
		claimscreen.setAccidentDate();

		for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
			VisualInteriorScreen visualinteriorscreen = claimscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, visualScreenData.getScreenName());
			visualinteriorscreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
			if (visualScreenData.getDamagesData() != null) {
				visualinteriorscreen.selectService(visualScreenData.getDamagesData().get(0).getDamageGroupName());
				visualinteriorscreen.tapInteriorWithCoords(1);
				visualinteriorscreen.tapInteriorWithCoords(2);
			} else {
				visualinteriorscreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
				VisualInteriorScreen.tapExteriorWithCoords(100, 500);
			}
			Assert.assertEquals(visualinteriorscreen.getTotalAmaunt(), visualScreenData.getScreenTotalPrice());
			Assert.assertEquals(visualinteriorscreen.getSubTotalAmaunt(), visualScreenData.getScreenPrice());
		}
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);
		}

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ALL_SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		servicesscreen.selectService(inspectionData.getServiceData().getServiceName());

		VisualInteriorScreen visualinteriorscreen = servicesscreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR, inspectionData.getVisualScreenData().getScreenName());
		visualinteriorscreen.selectService(inspectionData.getVisualScreenData().getDamageData().getDamageGroupName());
		visualinteriorscreen.tapCarImage();
		Assert.assertEquals(visualinteriorscreen.getTotalAmaunt(), inspectionData.getVisualScreenData().getScreenTotalPrice());
		Assert.assertEquals(visualinteriorscreen.getSubTotalAmaunt(),  inspectionData.getVisualScreenData().getScreenPrice());
		visualinteriorscreen.saveWizard();
		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		vehiclescreen = new VehicleScreen();

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			PriceMatrixScreen pricematrix = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
			for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
				PriceMatrixScreenSteps.verifyIfVehiclePartContainsPriceValue(vehiclePartData);
			}
		}

		servicesscreen.cancelWizard();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreen = new VehicleScreen();
		String copiedinspnumber = vehiclescreen.getInspectionNumber();
		servicesscreen.saveWizard();
		Assert.assertTrue(myinspectionsscreen.isInspectionExists(copiedinspnumber));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testThatSelectedServicesOnSRAreCopiedToInspectionBasedOnSR(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		homescreen = new HomeScreen();

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequestWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehiclescreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			if (serviceData.getServiceQuantity() != null) {
				SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
				servicedetailsscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
				servicedetailsscreen.saveSelectedServiceDetails();
			}
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());

		vehiclescreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.createInspectionFromServiceReques(newsrnumber, InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
		String inspectnumber = vehiclescreen.getInspectionNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			servicesscreen.checkServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2());
		servicesscreen.saveWizard();
		servicerequestsscreen = new ServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(newsrnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		TeamInspectionsScreen teaminspectionsscreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));
		teaminspectionsscreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testThatAutoSavedWOIsCreatedCorrectly(String rowID,
													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
				workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
		String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		Helpers.waitABit(40*1000);
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		MainScreen mainscreen = new MainScreen();
		mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		Assert.assertTrue(myworkordersscreen.isAutosavedWorkOrderExists());
		myworkordersscreen.selectContinueWorkOrder();
		BaseUtils.waitABit(30*1000);
		Assert.assertEquals(vehiclescreen.getInspectionNumber(), wonumber);

		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		mainscreen = new MainScreen();
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

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequestWithSelectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER,
				ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		vehiclescreen.setVINFieldValue(serviceRequestData.getVihicleInfo().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSave();
		String alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		QuestionsScreen questionsscreen = new QuestionsScreen();
		questionsscreen.drawSignature();
		servicesscreen.clickSave();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);

		QuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		servicesscreen.clickSave();
		alertText = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddAppointmentButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();

		servicerequestsscreen.setSubjectAppointmet(appointmentSubject);
		servicerequestsscreen.setAddressAppointmet(appointmentAddress);
		servicerequestsscreen.setCityAppointmet(appointmentCity);
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.selectCloseAction();
		servicerequestsscreen = new ServiceRequestsScreen();
		String newsrnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(newsrnumber),  ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(newsrnumber);
		servicerequestsscreen.selectRejectAction();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		servicerequestsscreen = new ServiceRequestsScreen();
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequestWithSelectCustomer(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER,
				ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);

		vehiclescreen.setVINFieldValue(serviceRequestData.getVihicleInfo().getVINNumber());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSave();
		String alertText = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		QuestionsScreen questionsscreen = new QuestionsScreen();
		questionsscreen.drawSignature();
		servicesscreen.clickSave();
		alertText =  Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);

		QuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
		servicesscreen.clickSave();
		alertText = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.ON_HOLD.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);

		servicerequestsscreen.selectAppointmentRequestAction();
		servicerequestsscreen.clickAddAppointmentButton();
		servicerequestsscreen.selectTodayFromAppointmet();
		servicerequestsscreen.selectTodayToAppointmet();

		servicerequestsscreen.setSubjectAppointmet(appointmentSubject);
		servicerequestsscreen.setAddressAppointmet(appointmentAddress);
		servicerequestsscreen.setCityAppointmet(appointmentCity);
		servicerequestsscreen.saveAppointment();
		servicerequestsscreen.selectCloseAction();
		servicerequestsscreen = new ServiceRequestsScreen();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		Assert.assertTrue(servicerequestsscreen.isSRSummaryAppointmentsInformation());

		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectRejectAction();
		alertText = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alertText, AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_PRICE_MATRIX);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		servicesscreen = new ServicesScreen();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(workOrderData.getMatrixServiceData().getMatrixServiceName()));
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		vehiclescreen.cancelWizard();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
																										  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		VehicleInfoScreenSteps.verifyMakeModelyearValues(inspectionData.getVehicleInfo());
		String inspnum = vehiclescreen.getInspectionNumber();
		PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(inspectionData.getPriceMatrixScreenData());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		vehiclescreen.saveWizard();
		Assert.assertTrue(myinspectionsscreen.isInspectionExists(inspnum));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 25421:WO HD: Verify that on Invoice 'Summary' main service of the panel is displayed as first then additional services", description = "WO HD: Verify that on Invoice 'Summary' main service of the panel is displayed as first then additional services")
	public void testWOVerifyThatOnInvoiceSummaryMainServiceOfThePanelIsDisplayedAsFirstThenAdditionalServices() {

		final String VIN  = "2C3CDXBG2EH174681";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Charger", "2014");
		String wonum = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.switchOffOption("PDR");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		pricematrix.selectDiscaunt("Wheel");

		pricematrix.selectPriceMatrix("VP2 zayats");
		//pricematrix.switchOffOption("PDR");
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");

		pricematrix.selectDiscaunt("Dye");
		pricematrix.selectDiscaunt("Wheel");
		pricematrix.selectDiscaunt("Test service zayats");

		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX));
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.approveWorkOrderWithoutSignature(wonum, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.selectWorkOrderForAction(wonum);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
		invoiceinfoscreen.setPO("12345");
		final String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
		homescreen = myworkordersscreen.clickHomeButton();

		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenum);
		SummaryScreen summaryScreen = myinvoicesscreen.clickSummaryPopup();
		Assert.assertTrue(summaryScreen.isSummaryPDFExists());
		summaryScreen.clickBackButton();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26054:WO Monitor: Create WO for monitor", description = "WO Monitor: Create WO for monitor")
	public void testWOMonitorCreateWOForMonitor() {

		final String VIN  = "1D3HV13T19S825733";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.woExists(wonum);
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOMonitorVerifyThatItIsNotPossibleToChangeServiceStatusBeforeStartService(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		Assert.assertTrue(myworkordersscreen.woExists(wonum));

		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);

		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
		OrderMonitorServiceDetailsPopup serviceDetailsPopup = ordermonitorscreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
		serviceDetailsPopup.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_YOU_MUST_SERVICE_PHASE_BEFORE_CHANGING_STATUS);
		serviceDetailsPopup.clickStartService();
		serviceDetailsPopup = ordermonitorscreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		serviceDetailsPopup.setCompletedServiceStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		for (String status : statuses)
			Assert.assertEquals(status, orderMonitorData.getMonitorServiceData().getMonitorServiceStatus());
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOMonitorVerifyThatItIsNotPossibleToChangePhaseStatusBeforeStartPhase(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		Assert.assertTrue(myworkordersscreen.woExists(wonum));

		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);

		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		Assert.assertTrue(ordermonitorscreen.isRepairPhaseExists());
		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonPresent());
		ordermonitorscreen.clicksRepairPhaseLine();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_YOU_MUST_START_PHASE_BEFORE_CHANGING_STATUS);
		ordermonitorscreen.clickStartPhase();


		OrderMonitorServiceDetailsPopup serviceDetailsPopup = ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertFalse(serviceDetailsPopup.isStartPhaseButtonPresent());
		Assert.assertTrue(serviceDetailsPopup.isServiceStartDateExists());

		serviceDetailsPopup.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
		serviceDetailsPopup.clickServiceDetailsDoneButton();

		ordermonitorscreen.clicksRepairPhaseLine();
		ordermonitorscreen.setCompletedPhaseStatus();
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		Assert.assertTrue(myworkordersscreen.woExists(wonum));

		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamworkordersscreen.clickSearchSaveButton();

		teamworkordersscreen.clickOnWO(wonum);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
		OrderMonitorServiceDetailsPopup serviceDetailsPopup =  ordermonitorscreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		Assert.assertFalse(serviceDetailsPopup.isServiceStartDateExists());
		serviceDetailsPopup.clickStartService();
		ordermonitorscreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
		Assert.assertTrue(serviceDetailsPopup.isServiceStartDateExists());
		serviceDetailsPopup.clickServiceDetailsDoneButton();
		teamworkordersscreen = ordermonitorscreen.clickBackButton();;
		teamworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26013:WO Monitor: HD - Verify that when change Status for Phase with 'Do not track individual service statuses' ON Phase status is set to all services assigned to phase",
			description = "WO Monitor: HD - Verify that when change Status for Phase with 'Do not track individual service statuses' ON Phase status is set to all services assigned to phase")
	public void testWOMonitorVerifyThatWhenChangeStatusForPhaseWithDoNotTrackIndividualServiceStatusesONPhaseStatusIsSetToAllServicesAssignedToPhase() {

		final String VIN  = "1D3HV13T19S825733";
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String _price = "$100.00";
		final String _discaunt_us = "Discount 10-20$";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation("Test Location ZZZ");
		String wonum = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);

		servicesscreen.selectService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		PriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		pricematrix.selectPriceMatrix(_pricematrix);
		pricematrix.setSizeAndSeverity(_size, _severity);
		Assert.assertEquals(pricematrix.getPrice(), _price);
		Assert.assertTrue(pricematrix.isNotesExists());
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		pricematrix.selectDiscaunt(_discaunt_us);
		pricematrix.clickSaveButton();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();
		Assert.assertTrue(myworkordersscreen.woExists(wonum));

		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();
		teamworkordersscreen.clickOnWO(wonum);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		OrderMonitorServiceDetailsPopup serviceDetailsPopup = ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
		serviceDetailsPopup.clickStartService();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		serviceDetailsPopup.setCompletedServiceStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(iOSInternalProjectConstants.WHEEL_SERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");

		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonPresent());
		ordermonitorscreen.clickStartPhase();

		serviceDetailsPopup = ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		serviceDetailsPopup.clickServiceStatusCell();;
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("You cannot change the status of services for this phase. You can only change the status of the whole phase."));
		serviceDetailsPopup.clickServiceDetailsDoneButton();

		ordermonitorscreen.clicksRepairPhaseLine();
		ordermonitorscreen.setCompletedPhaseStatus();

		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE), "Completed");
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DISC_EX_SERVICE1), "Completed");
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DYE_SERVICE), "Completed");

		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertEquals(ordermonitorscreen.getPanelStatusInPopup(), "Completed");
		ordermonitorscreen.clickDoneIcon();
		ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		Assert.assertEquals(ordermonitorscreen.getPanelStatusInPopup(), "Completed");
		ordermonitorscreen.clickDoneIcon();
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26214:SR: HD - Verify that SR is created correctly when select owner on Vehicle info",
			description = "SR: HD - Verify that SR is created correctly when select owner on Vehicle info")
	public void testSRHDVerifyThatSRIsCreatedCorrectlyWhenSelectOwnerOnVehicleInfo() {

		final String VIN = "2A4RR4DE2AR286008";
		final String owner = "Avalon";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreen.selectOwnerT(owner);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);

		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(VIN));
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26215:SR: HD - Verify that Check In action is present for SR when appropriate SR type has option Check in ON",
			description = "SR: HD - Verify that Check In action is present for SR when appropriate SR type has option Check in ON")
	public void testSRHDVerifyThatCheckInActionIsPresentForSRWhenAppropriateSRTypeHasOptionCheckInON() {

		final String VIN = "2A4RR4DE2AR286008";
		final String owner = "Avalon";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreen.selectOwnerT(owner);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);

		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(VIN));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26216:SR: HD - Verify that Check In action is changed to Undo Check In after pressing on it and vice versa",
			description = "SR: HD - Verify that Check In action is changed to Undo Check In after pressing on it and vice versa")
	public void testSRHDVerifyThatCheckInActionIsChangedToUndoCheckInAfterPressingOnItAndViceVersa() {

		final String VIN = "2A4RR4DE2AR286008";
		final String owner = "Avalon";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreen.selectOwnerT(owner);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(VIN));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectUndoCheckMenu();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26217:SR: HD - Verify that filter 'Not Checked In' is working correctly",
			description = "SR: HD - Verify that filter 'Not Checked In' is working correctly")
	public void testSRHDVerifyThatFilterNotCheckedInIsWorkingCorrectly() {

		final String VIN = "2A4RR4DE2AR286008";
		final String owner = "Avalon";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Chrysler", "Town and Country", "2010");
		vehiclescreen.selectOwnerT(owner);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);

		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(VIN));
		//servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.applyNotCheckedInFilter();
		Assert.assertEquals(servicerequestsscreen.getFirstServiceRequestNumber(), srnumber);

		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.resetFilter();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26662:Invoices: HD - Verify that when ‘Customer approval required’ is set to ON - auto email is not sent when approval does not exist",
			description = "HD - Verify that when ‘Customer approval required’ is set to ON - auto email is not sent when approval does not exist")
	public void testHDVerifyThatWhenCustomerApprovalRequiredIsSetToONAutoEmailIsNotSentWhenApprovalDoesNotExist()
			throws Exception {

		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		myinvoicesscreen.clickInvoiceApproveButton(invoicenumber);

		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.drawApprovalSignature();
		approveinspscreen.clickApproveButton();
		myinvoicesscreen.clickHomeButton();

		final String invpoicereportfilenname = invoicenumber + ".pdf";

		NadaEMailService nada = new NadaEMailService();
		nada.setEmailId("test.cyberiansoft@getnada.club");
		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(invoicenumber, invpoicereportfilenname);
		Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoicenumber +
				" in mail box " + nada.getEmailId() + ". At time " +
				LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		nada.deleteMessageWithSubject(invoicenumber);

		File pdfdoc = new File(invpoicereportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains(VIN));
		Assert.assertTrue(pdftext.contains(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(pdftext.contains(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS));
		Assert.assertTrue(pdftext.contains("75.00"));
	}

	@Test(testName="Test Case 26663:Invoices: HD - verify that when ‘Customer approval required’ is set to OFF - auto email is sent when invoice is auto approved",
			description = "Invoices: HD - verify that when ‘Customer approval required’ is set to OFF - auto email is sent when invoice is auto approved")
	public void testHDVerifyThatWhenCustomerApprovalRequiredIsSetToOffAutoEmailIsSentWhenInvoiceAsAutoApproved()
			throws Exception {

		final String VIN  = "JA4LS31H8YP047397";
		final String _po = "123";
		/*Inicialize();
		MainScreen mainscreen = new MainScreen();
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
		*/

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Mitsubishi", "Montero Sport", "2000");
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS);
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		myinvoicesscreen.clickHomeButton();


		final String invpoicereportfilenname = invoicenumber + ".pdf";
		NadaEMailService nada = new NadaEMailService();
		nada.setEmailId("test.cyberiansoft@getnada.club");
		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(invoicenumber, invpoicereportfilenname);
		Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoicenumber +
				" in mail box " + nada.getEmailId() + ". At time " +
				LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		nada.deleteMessageWithSubject(invoicenumber);

		File pdfdoc = new File(invpoicereportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains(VIN));
		Assert.assertTrue(pdftext.contains(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertTrue(pdftext.contains(iOSInternalProjectConstants.TEST_SERVICE_ZAYATS));
		Assert.assertTrue(pdftext.contains("75.00"));
	}

	@Test(testName="Test Case 26690:Invoices: HD - Verify that print icon is shown next to invoice when it was printed (My Invoices)",
			description = "Invoices: HD - Verify that print icon is shown next to invoice when it was printed (My Invoices)")
	public void testHDVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedMyInvoices() {

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String invoicenum = myinvoicesscreen.getFirstInvoiceValue();
		myinvoicesscreen.printInvoice(invoicenum, "TA_Print_Server");
		myinvoicesscreen.clickHomeButton();
		Helpers.waitABit(20000);
		myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertTrue(myinvoicesscreen.isInvoicePrintButtonExists(invoicenum));
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26691:Invoices: HD - Verify that print icon is shown next to invoice when it was printed (Team Invoices)",
			description = "Invoices: HD - Verify that print icon is shown next to invoice when it was printed (Team Invoices)")
	public void testHDVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedTeamInvoices() {

		homescreen = new HomeScreen();
		TeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
		teaminvoicesscreen.printInvoice(invoicenum, "TA_Print_Server");
		teaminvoicesscreen.clickHomeButton();
		Helpers.waitABit(20000);
		teaminvoicesscreen = homescreen.clickTeamInvoices();
		Assert.assertTrue(teaminvoicesscreen.isInvoicePrintButtonExists(invoicenum));
		teaminvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalONAndNoSignature(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		for (ServiceData serviceData : workOrderData.getServicesList())
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveRedButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatGreyAIconIsPresentForInvoiceWithCustomerApprovalOFFAndNoSignature(String rowID,
																									  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		for (ServiceData serviceData : workOrderData.getServicesList())
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();

		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.myInvoiceExists(invoicenumber);
		Assert.assertTrue(myinvoicesscreen.isInvoiceApproveGreyButtonExists(invoicenumber));
		myinvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAIconIsNotPresentForInvoiceWhenSignatureExists(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
			vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
			ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
			for (ServiceData serviceData : workOrderData.getServicesList())
				ServicesScreenSteps.selectService(serviceData.getServiceName());
			OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			ordersummaryscreen.checkApproveAndCreateInvoice();
			SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
			selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			ordersummaryscreen.clickSave();

			InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.valueOf(workOrderData.getInvoiceData().getInvoiceType()));
			invoiceinfoscreen.setPO(workOrderData.getInvoiceData().getPoNumber());
			final String invoicenumberapproveon = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen = new MyWorkOrdersScreen();
			myworkordersscreen.clickHomeButton();
			MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			myinvoicesscreen.myInvoiceExists(invoicenumberapproveon);
			Assert.assertTrue(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumberapproveon));
			SummaryScreen summaryScreen = myinvoicesscreen.clickSummaryPopup();
			summaryScreen.clickBackButton();
			myinvoicesscreen.selectInvoiceForApprove(invoicenumberapproveon);

			selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
			approveinspscreen.drawApprovalSignature();
			approveinspscreen.clickApproveButton();
			myinvoicesscreen = new MyInvoicesScreen();

			Assert.assertFalse(myinvoicesscreen.isInvoiceApproveButtonExists(invoicenumberapproveon));
			myinvoicesscreen.clickVoidInvoiceMenu();
			Helpers.getAlertTextAndAccept();
			myinvoicesscreen.clickHomeButton();
		}
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRejectActionIsDisplayedForSRInStatusScheduledInspOrWOAndAssignForTech(String rowID,
																									String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		//Create first SR
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);

		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber1 = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.rejectServiceRequest(srnumber1);
		vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
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
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.ON_HOLD.getValue());

		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.createInspectionFromServiceReques(srnumber, InspectionsTypes.INSP_DRAFT_MODE);
		String inspectnumber = vehiclescreen.getInspectionNumber();
		servicesscreen = servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSaveAsFinal();
		servicerequestsscreen = new ServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		TeamInspectionsScreen teaminspectionsscreen = new TeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));
		teaminspectionsscreen.clickActionButton();
		teaminspectionsscreen.selectInspectionForAction(inspectnumber);

		teaminspectionsscreen.clickApproveInspections();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		//Helpers.acceptAlert();
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.approveInspectionWithSelectionAndSignature(inspectnumber);
		teaminspectionsscreen = new TeamInspectionsScreen();
		teaminspectionsscreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();

		servicerequestsscreen.clickHomeButton();
		boolean onhold = false;
		for (int i= 0; i < 7; i++) {
			servicerequestsscreen = homescreen.clickServiceRequestsButton();
			if (!servicerequestsscreen.getServiceRequestStatus(srnumber).equals(ServiceRequestStatus.ON_HOLD.getValue())) {
				servicerequestsscreen.clickHomeButton();
				BaseUtils.waitABit(30*1000);
			} else {

				onhold = true;
				break;
			}
		}
		Assert.assertTrue(onhold);
		servicerequestsscreen.rejectServiceRequest(srnumber);
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
		srlistwebpage.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_INSP_ONLY.getServiceRequestTypeName());
		srlistwebpage.clickAddServiceRequestButton();

		srlistwebpage.clickCustomerEditButton();
		srlistwebpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		srlistwebpage.clickDoneButton();

		srlistwebpage.clickVehicleInforEditButton();
		srlistwebpage.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		srlistwebpage.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
		srlistwebpage.clickDoneButton();

		srlistwebpage.saveNewServiceRequest();
		String srnumber = srlistwebpage.getFirstInTheListServiceRequestNumber();
		DriverBuilder.getInstance().getDriver().quit();

		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();
		MainScreen mainscr = new MainScreen();
		mainscr.updateDatabase();
		HomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		serviceRequestdetailsScreen.clickBackButton();
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

		MainScreen mainscr = new HomeScreen().clickLogoutButton();
		mainscr.updateDatabase();
		HomeScreen homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		serviceRequestdetailsScreen.clickBackButton();
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
		srlistwebpage.addServicesToServiceRequest(serviceRequestData.getPercentageServices());
		srlistwebpage.saveNewServiceRequest();
		srnumber = srlistwebpage.getFirstInTheListServiceRequestNumber();
		srlistwebpage.acceptFirstServiceRequestFromList();

		DriverBuilder.getInstance().getDriver().quit();
		mainscr = new HomeScreen().clickLogoutButton();
		mainscr.updateDatabase();
		homescreen = mainscr.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isRejectActionExists());
		servicerequestsscreen.selectEditServiceRequestAction();
		VehicleScreen vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testNotMultipleServiceWithRequiredPanelsIsAddedOneTimeToWOAfterSelecting(String rowID,
																						 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);
			Assert.assertEquals(Helpers.getAlertTextAndAccept(), String.format(AlertsCaptions.ALERT_YOU_CAN_ADD_ONLY_ONE_SERVICE, serviceData.getServiceName()));
			Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems( serviceData.getServiceName()), 1);
		}
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testRegularWOVerifyThatBundleItemsAreShownWhenCreateWOFromInspection(String rowID,
																					 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addOInspectionWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());;

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		servicesscreen.openCustomServiceDetails(inspectionData.getBundleService().getBundleServiceName());
		SelectedServiceDetailsScreen selectedservicedetailsscreen = new SelectedServiceDetailsScreen();
		selectedservicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
		selectedservicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(inspectionData.getBundleService().getBundleServiceName()));
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

		myinspectionsscreen.approveInspectionAllServices(inspnumber,
				iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myinspectionsscreen.createWOFromInspection(inspnumber,
				WorkOrdersTypes.WO_TYPE_FOR_CALC);
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.openServiceDetails(inspectionData.getBundleService().getBundleServiceName());
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
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
		selectedservicebundlescreen.clickCancelBundlePopupButton();
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenOptionAllowToCloseSRIsSetToONActionCloseIsShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
																														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
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
	public void testVerifyThatWhenOptionAllowToCloseSRIsSetToOFFActionCloseIsNotShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
																															String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectRejectAction();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
		servicerequestsscreen.waitServiceRequestsScreenLoaded();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressNoAlertMessageIsClose(String rowID,
																									  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber),  ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);

		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressYesListOfStatusReasonsIsShown(String rowID,
																											  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
		servicerequestsscreen.clickCancelCloseReasonDialog();
		servicerequestsscreen = new ServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsShownInCaseItIsAssignedToReasonOnBO(String rowID,
																											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		final String answerReason = "All work is done. Answer questions";
		final String answerQuestion = "A3";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
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
		servicerequestsscreen.clickDoneButton();
		QuestionsPopup questionspopup = new QuestionsPopup();
		questionspopup.answerQuestion2(answerQuestion);
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

		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.saveWizard();

		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srnumber), ServiceRequestStatus.SCHEDULED.getValue());
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		Assert.assertTrue(servicerequestsscreen.isCloseActionExists());
		servicerequestsscreen.selectCloseAction();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
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

		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_WO_ONLY);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.createWorkOrderFromServiceRequest(srnumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		VehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			Assert.assertTrue(servicesscreen.checkServiceIsSelected(serviceData.getServiceName()));
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalSale);
		String wonumber = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.clickSave();

		for (int i = 0; i < serviceRequestData.getMoneyServices().size(); i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.indexOf("' require"));
			for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
				if (serviceData.getServiceName().equals(servicedetails)) {
					SelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openServiceDetails(servicedetails);
					selectedservicedetailsscreen.clickVehiclePartsCell();
					selectedservicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
					selectedservicedetailsscreen.saveSelectedServiceDetails();
					selectedservicedetailsscreen.saveSelectedServiceDetails();
				}
			}
			ordersummaryscreen.clickSave();
		}
		servicerequestsscreen = new ServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		TeamWorkOrdersScreen teamwoscreen = serviceRequestdetailsScreen.clickServiceRequestSummaryOrdersButton();
		teamwoscreen.woExists(wonumber);
		teamwoscreen.clickServiceRequestButton();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen = new ServiceRequestsScreen();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenCreateInspectionFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
																														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_INSP_ONLY);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.createInspectionFromServiceReques(srnumber, InspectionsTypes.INSP_FOR_CALC);
		VehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
		String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : serviceRequestData.getMoneyServices())
			Assert.assertTrue(servicesscreen.checkServiceIsSelected(serviceData.getServiceName()));
		servicesscreen.clickSave();

		for (int i = 0; i < serviceRequestData.getMoneyServices().size(); i++) {
			alerttext = Helpers.getAlertTextAndAccept();
			String servicedetails = alerttext.substring(alerttext.indexOf("'")+1, alerttext.indexOf("' require"));
			for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
				if (serviceData.getServiceName().equals(servicedetails)) {
					SelectedServiceDetailsScreen selectedservicedetailsscreen = servicesscreen.openServiceDetails(servicedetails);
					selectedservicedetailsscreen.clickVehiclePartsCell();
					selectedservicedetailsscreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
					selectedservicedetailsscreen.saveSelectedServiceDetails();
					selectedservicedetailsscreen.saveSelectedServiceDetails();
				}
			}
			servicesscreen.clickSave();
		}
		servicerequestsscreen = new ServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		TeamInspectionsScreen teaminspectionsscreen = new TeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspnumber));
		teaminspectionsscreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatOnInspectionApprovalScreenSelectedPriceMatrixValueIsShown(String rowID,
																						String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();
		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData())
			PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionnumber);
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList()) {
			Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove(serviceData.getServiceName()));
			Assert.assertEquals(approveinspscreen.getInspectionServicePrice(serviceData.getServiceName()), serviceData.getServicePrice());
		}

		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();
		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new VehicleScreen();
		PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreensData().get(0);
		PriceMatrixScreen priceMatrixScreen = vehiclescreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenData.getMatrixScreenName());
		priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
		Assert.assertEquals(priceMatrixScreen.clearVehicleData(), AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
		InspectionToolBar inspectionToolBar = new InspectionToolBar();
		Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), inspectionData.getInspectionPrice());
		Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionTotalPrice());
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspectionnumber);
		selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspectionnumber);
		Assert.assertEquals(approveinspscreen.getInspectionServicePrice(inspectionData.getServicesToApprovesList().get(0).getServiceName()), inspectionData.getServicesToApprovesList().get(0).getServicePrice());
		Assert.assertFalse(approveinspscreen.isInspectionServiceExistsForApprove(inspectionData.getServicesToApprovesList().get(1).getServiceName()));
		approveinspscreen.clickCancelButton();
		approveinspscreen.clickCancelButton();;
		myinspectionsscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatQuestionSectionIsShownPerServiceForFirstSelectedPanelWhenQFIsNotRequired(String rowID,
																									   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();;

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehicleScreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_CALC);
		vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		ServicesScreen servicesscreen = vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
		ServiceDetailsScreenSteps.slectServiceVehicleParts(inspectionData.getServiceData().getVehicleParts());
		SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen();
		final String selectedhehicleparts = selectedservicedetailscreen.getListOfSelectedVehicleParts();
		for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts())
			Assert.assertTrue(selectedhehicleparts.contains(vehiclePartData.getVehiclePartName()));
		selectedservicedetailscreen.saveSelectedServiceDetails();
		int i = 0;
		for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
			servicesscreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
			i++;
			Assert.assertFalse(selectedservicedetailscreen.isQuestionFormCellExists());
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
																											   String description, JSONObject testData) {
		final String newLineSymbol = "\n";

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSPECTION_VIN_ONLY);
		vehiclescreen.getVINField().click();
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.getVINField().click();
		vehiclescreen.getVINField().sendKeys(newLineSymbol);
		vehiclescreen.cancelOrder();
		myinspectionsscreen = new MyInspectionsScreen();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenEditInspectionSelectedVehiclePartsForServicesArePresent(String rowID,
																						  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspectionnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
		servicesscreen.saveWizard();

		myinspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		int i = 0;
		for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
			servicesscreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
			i++;
			SelectedServiceDetailsScreen selectedservicedetailscreen = new SelectedServiceDetailsScreen();
			Assert.assertEquals(selectedservicedetailscreen.getVehiclePartValue(), vehiclePartData.getVehiclePartName());
			selectedservicedetailscreen.saveSelectedServiceDetails();
		}
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatItIsPossibleToSaveAsFinalInspectionLinkedToSR(String rowID,
																			String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);

		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.createInspectionFromServiceReques(srnumber, InspectionsTypes.INSP_DRAFT_MODE);
		InspectionData inspectionData = testCaseData.getInspectionData();

		String inspectionnumber = vehiclescreen.getInspectionNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
		servicesscreen.clickSaveAsDraft();
		servicerequestsscreen = new ServiceRequestsScreen();
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		TeamInspectionsScreen teaminspectionsscreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
		teaminspectionsscreen.selectInspectionForEdit(inspectionnumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSaveAsFinal();
		teaminspectionsscreen = new TeamInspectionsScreen();
		Assert.assertTrue(teaminspectionsscreen.isInspectionApproveButtonExists(inspectionnumber));
		teaminspectionsscreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenFinalInspectionIsCopiedServisesAreCopiedWithoutStatuses_Approved_Declined_Skipped(String rowID,
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
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		servicesscreen.selectService(inspectionData.getBundleService().getBundleServiceName());
		SelectedServiceDetailsScreen servicedetailsscreen = new SelectedServiceDetailsScreen();
		servicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
		servicedetailsscreen.saveSelectedServiceDetails();

		ServicesScreenSteps.selectMatrixServiceData(inspectionData.getMatrixServiceData());

		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new MyInspectionsScreen();
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);

		myinspectionsscreen.clickApproveInspections();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber);
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			if (serviceData.isSelected())
				Assert.assertTrue(approveinspscreen.isInspectionServiceExistsForApprove(serviceData), "Can't find service:" + serviceData.getServiceName());
			else
				Assert.assertFalse(approveinspscreen.isInspectionServiceExistsForApprove(serviceData));
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.waitInspectionsScreenLoaded();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.TEST_PACK_FOR_CALC);
		for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
			if (serviceData.isSelected())
				Assert.assertTrue(servicesscreen.checkServiceIsSelected(serviceData.getServiceName()));
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen = new MyInspectionsScreen();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatTextNotesAreCopiedToNewInspectionsWhenUseCopyAction(String rowID,
																				  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		final String notesText = "Test for copy";

		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.selectDefaultInspectionType();
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		NotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.setNotes(notesText);
		notesscreen.clickSaveButton();
		vehiclescreen.saveWizard();
		myinspectionsscreen.selectInspectionForCopy(inspnumber);

		vehiclescreen = new VehicleScreen();
		String copiedinspnumber = vehiclescreen.getInspectionNumber();
		vehiclescreen.saveWizard();

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

		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_DRAFT_MODE);
			vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			inpectionsIDs.add(vehiclescreen.getInspectionNumber());
			ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesscreen.selectService(inspectionData.getBundleService().getBundleServiceName());
			SelectedServiceDetailsScreen servicedetailsscreen = new SelectedServiceDetailsScreen();
			servicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
			servicedetailsscreen.saveSelectedServiceDetails();
			servicesscreen = new ServicesScreen();
			servicesscreen.clickSaveAsFinal();
			myinspectionsscreen = new MyInspectionsScreen();
		}
		myinspectionsscreen.clickHomeButton();
		TeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (String inspectionID : inpectionsIDs) {
			teaminspectionsscreen.selectInspectionForAction(inspectionID);
		}

		teaminspectionsscreen.clickApproveInspections();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		for (String inspectionID : inpectionsIDs) {
			approveinspscreen.selectInspectionForApprove(inspectionID);
			approveinspscreen.clickApproveAllServicesButton();
			approveinspscreen.clickSaveButton();
		}
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		teaminspectionsscreen = new TeamInspectionsScreen();
		teaminspectionsscreen.clickActionButton();
		for (String inspectionID : inpectionsIDs) {
			myinspectionsscreen.selectInspectionForAction(inspectionID);
		}
		teaminspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		Assert.assertTrue(teaminspectionsscreen.isSendEmailInspectionMenuActionExists());
		teaminspectionsscreen.clickActionButton();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatServicesOnServicePackageAreGroupedByTypeSelectedOnInspTypeWizard(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setInspectionToNonSinglePageInspection();
		settingscreen.clickHomeButton();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSPECTION_GROUP_SERVICE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreensData().get(0));

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		ServicesScreenSteps.selectService(inspectionData.getServiceData().getServiceName());
		for (ServicesScreenData servicesScreenData : inspectionData.getServicesScreens()) {
			servicesscreen.selectNextScreen(WizardScreenTypes.SERVICES, servicesScreenData.getScreenName());
			servicesscreen.selectGroupServiceItem(servicesScreenData.getDamageData().getDamageGroupName());
			ServicesScreenSteps.selectServiceWithServiceData(servicesScreenData.getDamageData().getMoneyService());
		}
		servicesscreen.clickSave();
		servicesscreen.clickFinalPopup();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
		QuestionsScreen questionsscreen = new QuestionsScreen();
		questionsscreen.drawSignature();
		questionsscreen.clickSave();
		questionsscreen.clickFinalPopup();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
		QuestionsScreenSteps.answerQuestion(inspectionData.getQuestionScreensData().get(1).getQuestionData());
		questionsscreen.clickSave();
		questionsscreen.clickFinalPopup();
		Assert.assertTrue(myinspectionsscreen.isInspectionExists(inspnumber));
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAllInstancesOfOneServiceAreCopiedFromInspectionToWO(String rowID,
																				  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		servicesscreen.saveWizard();

		myinspectionsscreen.selectInspectionForApprove(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.approveInspectionApproveAllAndSignature(inspnumber);

		myinspectionsscreen.createWOFromInspection(inspnumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList())
			if (serviceData.isSelected())
				Assert.assertTrue(servicesscreen.checkServiceIsSelectedWithServiceValues(serviceData));
			else
				Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatAssignButtonIsPresentWhenSelectSomeTechInCaseDirectAssignOptionIsSetForInspectionType(String rowID,
																													String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSPECTION_DIRECT_ASSIGN);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickSaveAsFinal();
		myinspectionsscreen.selectInspectionToAssign(inspnumber);
		DevicesPopupScreen devicesscreen = new DevicesPopupScreen();
		Assert.assertTrue(devicesscreen.isAssignButtonDisplayed());
		devicesscreen.clickCancelButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatDuringLineApprovalSelectAllButtonsAreWorkingCorrectly(String rowID,
																					String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);
		servicesscreen.saveWizard();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber);
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
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatApproveOptionIsNotPresentForApprovedInspectionInMultiselectMode(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> inspections = new ArrayList<>();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
			vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			final String inspectionNumber = vehiclescreen.getInspectionNumber();
			inspections.add(inspectionNumber);
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
			ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesscreen.saveWizard();
			myinspectionsscreen.selectInspectionForAction(inspectionNumber);
			//myinspectionsscreen.clickApproveInspections();
			SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
			selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
			ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
			approveinspscreen.approveInspectionApproveAllAndSignature(inspectionNumber);
			myinspectionsscreen = new MyInspectionsScreen();
		}
		myinspectionsscreen.clickActionButton();
		for (String inspectionID : inspections) {
			myinspectionsscreen.selectInspectionForAction(inspectionID);
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();

		TeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (String inspectionID : inspections) {
			teaminspectionsscreen.selectInspectionForAction(inspectionID);
		}
		teaminspectionsscreen.clickActionButton();
		Assert.assertFalse(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickActionButton();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatApproveOptionIsPresentInMultiselectModeOnlyOneOrMoreNotApprovedInspectionsAreSelected(String rowID,
																													String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> inspections = new ArrayList<>();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
			vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
			inspections.add(vehiclescreen.getInspectionNumber());
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
			ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
			servicesscreen.saveWizard();
		}
		myinspectionsscreen.selectInspectionForAction(inspections.get(0));
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen = new ApproveInspectionsScreen();
		approveinspscreen.approveInspectionApproveAllAndSignature(inspections.get(0));
		myinspectionsscreen = new MyInspectionsScreen();
		myinspectionsscreen.clickActionButton();
		for (String inspectionID : inspections) {
			myinspectionsscreen.selectInspectionForAction(inspectionID);
		}
		myinspectionsscreen.clickActionButton();
		Assert.assertTrue(myinspectionsscreen.isApproveInspectionMenuActionExists());
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.clickDoneButton();
		homescreen = myinspectionsscreen.clickHomeButton();

		TeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		teaminspectionsscreen.clickActionButton();
		for (String inspectionID : inspections) {
			teaminspectionsscreen.selectInspectionForAction(inspectionID);
		}
		teaminspectionsscreen.clickActionButton();
		Assert.assertTrue(teaminspectionsscreen.isApproveInspectionMenuActionExists());
		teaminspectionsscreen.clickActionButton();
		teaminspectionsscreen.clickDoneButton();
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenOptionDraftModeIsSetToONWhenSaveInspectionProvidePromptToAUserToSelectEitherDraftOrFinal(String rowID,
																														   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_DRAFT_MODE);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		String inspnumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);
		servicesscreen.clickSaveAsDraft();
		myinspectionsscreen = new MyInspectionsScreen();
		Assert.assertTrue(myinspectionsscreen.isDraftIconPresentForInspection(inspnumber));
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		myinspectionsscreen.clickActionButton();
		Assert.assertFalse(myinspectionsscreen.isApproveInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCreateWOInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCreateServiceRequestInspectionMenuActionExists());
		Assert.assertFalse(myinspectionsscreen.isCopyInspectionMenuActionExists());
		myinspectionsscreen.clickActionButton();
		myinspectionsscreen.clickDoneButton();

		homescreen = myinspectionsscreen.clickHomeButton();
		TeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
		Assert.assertTrue(teaminspectionsscreen.isDraftIconPresentForInspection(inspnumber));
		teaminspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 32286:Inspections: HD - Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount",
			description = "Verify that amount of approved services are shown on BO > inspectiontypes list > column ApprovedAmount")
	public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListColumnApprovedAmount() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
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
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber);
		approveinspscreen.clickDeclineAllServicesButton();
		approveinspscreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)");
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
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
		inspectionspage.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted());

		inspectionspage.searchInspectionByNumber(inspnumber);
		Assert.assertEquals(inspectionspage.getInspectionAmountApproved(inspnumber), "$2,000.00");
		Assert.assertEquals(inspectionspage.getInspectionStatus(inspnumber), "Approved");
		Assert.assertEquals(inspectionspage.getInspectionApprovedTotal(inspnumber), "$2000.00");
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatApproveWOIsWorkingCorrectUnderTeamWO(String rowID,
																	 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String locationFilterValue = "All locations";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		homescreen = myworkordersscreen.clickHomeButton();
		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation(locationFilterValue);
		teamworkordersscreen.clickSearchSaveButton();
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());

		servicesscreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword("Zayats", "1111");
		ordersummaryscreen.clickSave();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceinfoscreen.setPO(invoiceData.getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();

		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new InvoiceInfoScreen();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceinfoscreen.clickInvoicePayButton();
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		servicesscreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword("Zayats", "1111");

		ordersummaryscreen.clickSave();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceinfoscreen.setPO(invoiceData.getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();

		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.selectInvoice(invoicenumber);
		myinvoicesscreen.clickEditPopup();
		invoiceinfoscreen = new InvoiceInfoScreen();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceinfoscreen.clickInvoicePayButton();
		invoiceinfoscreen.setCashCheckAmountValue(cashcheckamount);
		invoiceinfoscreen.clickInvoicePayDialogButon();
		Assert.assertEquals(invoiceinfoscreen.getInvoicePOValue(), invoiceData.getPoNumber());
		invoiceinfoscreen.clickSaveAsDraft();

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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());

		servicesscreen = new ServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword("Zayats", "1111");
		ordersummaryscreen.clickSave();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
		InvoiceData invoiceData = testCaseData.getInvoiceData();
		invoiceinfoscreen.setPO(invoiceData.getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.clickSaveAsDraft();
		homescreen = myworkordersscreen.clickHomeButton();
		BaseUtils.waitABit(5*1000);
		TeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		teaminvoicesscreen.selectInvoice(invoicenumber);

		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(invoiceData.getNewPoNumber());
		teaminvoicesscreen.clickHomeButton();

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
		invoiceswebpage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		invoiceswebpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();

		Assert.assertEquals(invoiceswebpage.getInvoicePONumber(invoicenumber), invoiceData.getNewPoNumber());
		//Assert.assertEquals(invoiceswebpage.getInvoicePOPaidValue(invoicenumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
		String mainWindowHandle = webdriver.getWindowHandle();
		InvoicePaymentsTabWebPage invoicepayments = invoiceswebpage.clickInvoicePayments(invoicenumber);

		Assert.assertEquals(invoicepayments.getPaymentDescriptionTypeAmountValue("PO #: " + invoiceData.getNewPoNumber()), expectedPrice);
		invoicepayments.closeNewTab(mainWindowHandle);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(testName="Test Case 40033:WO Monitor: Verify filter for Team WO that returns only work assigned to tech who is logged in,"
			+ "Test Case 40034:WO Monitor: Verify that employee with Manager role may see and change all services of repair order",
			description = "WO: HD - Verify filter for Team WO that returns only work assigned to tech who is logged in,"
					+ "WO Monitor: Verify that employee with Manager role may see and change all services of repair order")
	public void testInvoicesVerifyFilterForTeamWOThatReturnsOnlyWorkAssignedToTechWhoIsLoggedIn() {

		final String VIN  = "WDZPE7CD9E5889222";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		final String wonum = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);

		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		TechniciansPopup techniciansPopup = selectedservicescreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician("Oksana Zayats");
		techniciansPopup.unselecTechnician("Employee Simple 20%");
		techniciansPopup.saveTechViewDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.WHEEL_SERVICE);
		techniciansPopup = selectedservicescreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician("Oksana Zayats");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
		BaseUtils.waitABit(1000*20);

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);

		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.checkMyWorkCheckbox();
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DISC_EX_SERVICE1));
		Assert.assertTrue(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.WHEEL_SERVICE));
		Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		homescreen = teamworkordersscreen.clickHomeButton();
		MainScreen mainscreen = homescreen.clickLogoutButton();

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
		//Assert.assertFalse(ordermonitorscreen.isServicePresent(iOSInternalProjectConstants.DYE_SERVICE));
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
		repairorderspage.selectSearchLocation("Default Location");
		repairorderspage.selectSearchTimeframe("Custom");
		repairorderspage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		repairorderspage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		repairorderspage.setSearchWoNumber(wonum);
		repairorderspage.clickFindButton();

		VendorOrderServicesWebPage vendororderservicespage = repairorderspage.clickOnWorkOrderLinkInTable(wonum);
		vendororderservicespage.changeRepairOrderServiceVendor(iOSInternalProjectConstants.DYE_SERVICE, "Device Team");
		vendororderservicespage.waitABit(3000);
		Assert.assertEquals(vendororderservicespage.getRepairOrderServiceTechnician(iOSInternalProjectConstants.DYE_SERVICE), "Oksi User");
		DriverBuilder.getInstance().getDriver().quit();

		teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonum);
		ordermonitorscreen = teamworkordersscreen.selectWOMonitor();

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

	@Test(testName="Test Case 45251:SR: HD - Verify multiple inspectiontypes and multiple work orders to be tied to a Service Request",
			description = "SR: HD - Verify multiple inspectiontypes and multiple work orders to be tied to a Service Request")
	public void testSRVerifyMultipleInspectionsAndMultipleWorkOrdersToBeTiedToAServiceRequest() {

		final String VIN  = "WDZPE7CD9E5889222";
		List<String> inspnumbers = new ArrayList<>();
		List<String> wonumbers = new ArrayList<>();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(VIN);
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		vehiclescreen.clickSave();
		Helpers.getAlertTextAndCancel();
		servicerequestsscreen = new ServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.createInspectionFromServiceReques(srnumber, InspectionsTypes.INS_LINE_APPROVE_OFF);
		inspnumbers.add(vehiclescreen.getInspectionNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen = new ServicesScreen();
		servicesscreen.clickSaveAsDraft();

		servicerequestsscreen = new ServiceRequestsScreen();
		servicerequestsscreen.createInspectionFromServiceReques(srnumber, InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen = new VehicleScreen();
		inspnumbers.add(vehiclescreen.getInspectionNumber());
		questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();

		TeamInspectionsScreen teaminspectionsscreen = new TeamInspectionsScreen();
		for (String inspectnumber : inspnumbers)
			Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));

		teaminspectionsscreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();

		servicerequestsscreen.createWorkOrderFromServiceRequest(srnumber, WorkOrdersTypes.WO_DELAY_START);
		wonumbers.add(vehiclescreen.getInspectionNumber());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		servicerequestsscreen.createWorkOrderFromServiceRequest(srnumber, WorkOrdersTypes.WO_MONITOR_DEVICE);
		wonumbers.add(vehiclescreen.getInspectionNumber());
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		servicerequestsscreen.selectServiceRequest(srnumber);
		serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		TeamWorkOrdersScreen teamwoscreen = serviceRequestdetailsScreen.clickServiceRequestSummaryOrdersButton();
		for (String wonumber : wonumbers)
			Assert.assertTrue(teamwoscreen.woExists(wonumber));
		serviceRequestdetailsScreen = teamwoscreen.clickServiceRequestButton();
		serviceRequestdetailsScreen.clickBackButton();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 50249:Inspections: HD - Verify that Single-page inspection is saved without crush",
			description = "Verify that Single-page inspection is saved without crush")
	public void testInspectionsVerifyThatSinglePageInspectionIsSavedWithoutCrush() {

		final String VIN  = "1D7HW48NX6S507810";
		final String _price  = "100";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		SinglePageInspectionScreen singlepageinspectionscreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_DRAFT_SINGLE_PAGE);
		String inspnumber = singlepageinspectionscreen.getInspectionNumber();
		singlepageinspectionscreen.expandToFullScreeenSevicesSection();
		ServicesScreen servicesscreen = new ServicesScreen();
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("PM_New");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix("123");
		pricematrix.switchOffOption("PDR");
		pricematrix.setPrice(_price);
		pricematrix.clickSaveButton();
		pricematrix.clickSave();
		pricematrix.clickFinalPopup();
		//servicesscreen = new ServicesScreen();
		//servicesscreen.clickSave();

		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("VIN# is required"));
		VehicleScreen vehiclescreen = new VehicleScreen();
		vehiclescreen.setVIN(VIN);
		vehiclescreen.clickSave();
		vehiclescreen.clickSave();
		vehiclescreen.clickFinalPopup();

		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Question 'Is all good?' in section 'Required trafficlight' should be answered."));
		singlepageinspectionscreen.expandToFullScreeenQuestionsSection();

		QuestionsScreen questionsscreen = new QuestionsScreen();
		questionsscreen.answerAllIsGoodQuestion();
		questionsscreen.clickSave();
		questionsscreen.clickSave();
		questionsscreen.clickFinalPopup();

		alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Question 'Question 2' in section 'Zayats Section1' should be answered."));
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		questionsscreen.clickSaveAsFinal();
		myinspectionsscreen = new MyInspectionsScreen();
		myinspectionsscreen.selectInspectionInTable(inspnumber);
		myinspectionsscreen.isApproveInspectionMenuActionExists();
		myinspectionsscreen.clickArchiveInspectionButton();
		myinspectionsscreen.selectReasonToArchive("Reason 1");
		myinspectionsscreen.clickHomeButton();
		settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 45128:Inspections: HD - Verify that service level notes are copied from Inspection to WO when it is auto created after approval",
			description = "Verify that service level notes are copied from Inspection to WO when it is auto created after approval")
	public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval() {

		final String VIN  = "1D7HW48NX6S507810";
		final String _price  = "100";
		final int timetowaitwo = 4;
		final String _pricematrix1 = "Left Front Door";
		final String _pricematrix2 = "Grill";
		final String inspectionnotes = "Inspection notes";
		final String servicenotes = "Service Notes";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		VehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);;
		vehiclescreen.setVIN(VIN);
		final String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServicePriceValue("10");
		servicedetailsscreen.saveSelectedServiceDetails();

		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SALES_TAX);
		servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.saveWizard();

		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_SPORT_CAR);
		vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		NotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.setNotes(inspectionnotes);
		notesscreen.clickSaveButton();

		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicedetailsscreen = servicesscreen.openServiceDetails("3/4\" - Penny Size");
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService("3/4\" - Penny Size"));

		servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService(iOSInternalProjectConstants.SALES_TAX));

		servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		notesscreen = servicedetailsscreen.clickNotesCell();
		notesscreen.setNotes(servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));

		PriceMatrixScreen pricematrix = servicesscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.DEFAULT);
		pricematrix.selectPriceMatrix(_pricematrix1);
		pricematrix.setSizeAndSeverity("DIME", "VERY LIGHT");
		pricematrix.setPrice(_price);

		pricematrix =servicesscreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, ScreenNamesConstants.MATRIX_LABOR);
		pricematrix.selectPriceMatrix(_pricematrix2);
		pricematrix.switchOffOption("PDR");
		pricematrix.setTime("12");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		pricematrix.clickSaveButton();

		Assert.assertTrue(myinspectionsscreen.isNotesIconPresentForInspection(inspnumber));
		myinspectionsscreen.selectInspectionForAction(inspnumber);
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.selectInspectionForApprove(inspnumber);
		approveinspscreen.clickApproveAllServicesButton();
		approveinspscreen.clickSaveButton();
		approveinspscreen.drawSignatureAfterSelection();
		approveinspscreen.clickDoneButton();

		homescreen = myinspectionsscreen.clickHomeButton();

		TeamWorkOrdersScreen teamwoscreen = homescreen.clickTeamWorkordersButton();
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
		vehiclescreen = new VehicleScreen();
		notesscreen = vehiclescreen.clickNotesButton();
		Assert.assertEquals(notesscreen.getNotesValue(), inspectionnotes);
		notesscreen.clickSaveButton();
		Assert.assertEquals(vehiclescreen.getEst(), inspnumber);
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicedetailsscreen = servicesscreen.openServiceDetails("3/4\" - Penny Size");
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService("3/4\" - Penny Size"));

		servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SALES_TAX);
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService(iOSInternalProjectConstants.SALES_TAX));

		servicedetailsscreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		notesscreen = servicedetailsscreen.clickNotesCell();
		Assert.assertEquals(notesscreen.getNotesValue(), servicenotes);
		notesscreen.clickSaveButton();
		servicedetailsscreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE));
		servicesscreen.cancelWizard();
		teamwoscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 32989:Inspections: HD - Verify that question section is shown per service with must panels when questions are required",
			description = "Verify that question section is shown per service with must panels when questions are required")
	public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE);
		servicedetailsscreen.answerQuestion2("A3");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.selectVehiclePart("Back Glass");
		servicedetailsscreen.selectVehiclePart("Deck Lid");
		servicedetailsscreen.selectVehiclePart("Driver Seat");
		servicedetailsscreen.selectVehiclePart("Hood");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();

		servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE, 0);
		Assert.assertTrue(servicedetailsscreen.isQuestionFormCellExists());
		Assert.assertEquals(servicedetailsscreen.getQuestion2Value(), "A3");
		servicedetailsscreen.saveSelectedServiceDetails();

		for (int i = 1; i < 4; i++) {
			servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE, i);
			Assert.assertFalse(servicedetailsscreen.isQuestionFormCellExists());
			servicedetailsscreen.saveSelectedServiceDetails();
		}
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 26265:Invoices HD: Create Invoice with two WOs and copy vehicle for Retail customer",
			description = "Create Invoice with two WOs and copy vehicle for Retail customer")
	public void testInvoicesCreateInvoiceWithTwoWOsAndCopyVehicleForRetailCustomer() {

		final String VIN = "QWERTYUI123";
		final String retailcustomer  = "19319";
		final String _make = "Buick";
		final String _model = "Electra";
		final String _year = "2012";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";
		final String stock = "Stock1";
		final String _ro = "123";
		final String[] vehicleparts = { "Cowl, Other", "Hood" };

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToRetailMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(retailcustomer,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);

		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		vehiclescreen.setYear(_year);
		vehiclescreen.setMileage(mileage);
		vehiclescreen.setFuelTankLevel(fueltanklevel);
		vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(selectedservicescreen.getVehiclePartValue(), "Cowl, Other Hood");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.DYE_SERVICE), 2);
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		String wonumber1 = ordersummaryscreen.getWorkOrderNumber();
		ordersummaryscreen.saveWorkOrderWithInvalidVIN();

		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		Assert.assertEquals(myworkordersscreen.getPriceValueForWO(wonumber1), "$19.00");
		myworkordersscreen.copyVehicleForWorkOrder(wonumber1, retailcustomer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehiclescreen.getMake(), _make);
		Assert.assertEquals(vehiclescreen.getModel(), _model);
		//Assert.assertEquals(vehiclescreen.getYear(), _year);
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		Assert.assertTrue(DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
						MobileBy.name("Warning!")).isDisplayed());
		DriverBuilder.getInstance().getAppiumDriver()
				.findElement(
						MobileBy.name("Yes"))
				.click();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
		invoiceinfoscreen.setPO("23");
		final String invoicenum = invoiceinfoscreen.getInvoiceNumber();
		invoiceinfoscreen.addWorkOrder(wonumber1);
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
		Helpers.waitABit(30*1000);

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


	@Test(testName = "Test Case 30509:Invoices: HD - Verify that message 'Invoice PO# shouldn't be empty' is shown for Team Invoices",
			description = "Verify that message 'Invoice PO# shouldn't be empty' is shown for Team Invoices")
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForTeamInvoices()  {

		final String emptypo = "";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		TeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
		teaminvoicesscreen.selectInvoice(invoicenum);
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(emptypo);
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Invoice PO# shouldn't be empty"));
		teaminvoicesscreen.clickCancelButton();
		teaminvoicesscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 30510:Invoices: HD - Verify that message 'Invoice PO# shouldn't be empty' is shown for MY Invoices",
			description = "Verify that message 'Invoice PO# shouldn't be empty' is shown for MY Invoices")
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForMyInvoices() {

		final String emptypo = "";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String invoicenum = myinvoicesscreen.getFirstInvoiceValue();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO(emptypo);
		String alerttxt = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttxt.contains("Invoice PO# shouldn't be empty"));
		myinvoicesscreen.clickCancelButton();
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 40023:Invoices: Verify that 'Create invoice' check mark is not shown for WO that is selected for billing",
			description = "Verify that 'Create invoice' check mark is not shown for WO that is selected for billing")
	public void testInvoicesVerifyThatCreateInvoiceCheckMarkIsNotShownForWOThatIsSelectedForBilling() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);

		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.checkApproveAndCreateInvoice();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.clickSave();
		InvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectDefaultInvoiceType();
		invoiceinfoscreen.clickFirstWO();
		vehiclescreen = new VehicleScreen();
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertFalse(ordersummaryscreen.isApproveAndCreateInvoiceExists());
		ordersummaryscreen.clickCancelButton();
		Helpers.acceptAlert();
		invoiceinfoscreen = new InvoiceInfoScreen();
		invoiceinfoscreen.cancelInvoice();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 33115:WO: HD - Verify that Tech splits is saved in price matrices",
			description = "Verify that Tech splits is saved in price matrices")
	public void testWOVerifyThatTechSplitsIsSavedInPriceMatrices() {

		final String VIN  = "1D7HW48NX6S507810";
		final String pricevalue  = "100";
		final String totalsale = "5";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);;
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix("VP2 zayats");
		pricematrix.setSizeAndSeverity("CENT", "MEDIUM");
		Assert.assertEquals(pricematrix.getTechniciansValue(), defaulttech);
		pricematrix.setPrice(pricevalue);
		TechniciansPopup techniciansPopup = pricematrix.openTechniciansPopup();
		techniciansPopup.selecTechnician(techname);
		techniciansPopup.saveTechViewDetails();
		Assert.assertEquals(pricematrix.getTechniciansValue(), defaulttech + ", " + techname);
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
		QuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();

		TechRevenueScreen techrevenuescreen = myworkordersscreen.selectWorkOrderTechRevenueMenuItem(wonumber);
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(techname));
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(defaulttech));
		techrevenuescreen.clickBackButton();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 34551:WO: HD - Verify that it is not possible to change default tech via service type split",
			description = "Verify that it is not possible to change default tech via service type split")
	public void testWOVerifyThatItIsNotPossibleToChangeDefaultTechViaServiceTypeSplit()  {

		final String VIN  = "1D7HW48NX6S507810";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";
		final String totalsale = "5";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		TechniciansPopup techniciansPopup = selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		techniciansPopup.selecTechnician(techname);
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		techniciansPopup.saveTechViewDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickTechnicianToolbarIcon();
		servicesscreen.changeTechnician("Dent", techname);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		techniciansPopup = selectedservicescreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		techniciansPopup.cancelSearchTechnician();
		selectedservicescreen.clickCancelSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		QuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();
		Assert.assertTrue(myworkordersscreen.woExists(wonumber));

		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 45097:WO: HD - Verify that when use Copy Services action for WO all service instances should be copied",
			description = "Verify that when use Copy Services action for WO all service instances should be copied")
	public void testWOVerifyThatWhenUseCopyServicesActionForWOAllServiceInstancesShouldBeCopied()  {

		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "5";
		final String[] servicestoadd = { iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL, iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE};
		final String[] vehicleparts = { "Dashboard", "Deck Lid"};


		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (String serviceadd : servicestoadd) {
			servicesscreen.searchAvailableService(serviceadd);
			SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(serviceadd);
			selectedservicescreen.clickVehiclePartsCell();
			for (String vehiclepart : vehicleparts) {
				selectedservicescreen.selectVehiclePart(vehiclepart);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.cancelSearchAvailableService();
		}

		for (String serviceadd : servicestoadd) {
			Assert.assertTrue(servicesscreen.checkServiceIsSelected(serviceadd));
		}

		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), "$44.00");
		QuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.copyServicesForWorkOrder(wonumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (String serviceadd : servicestoadd) {
			Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(serviceadd), servicestoadd.length);
		}
		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), "$44.00");
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 50250:WO: HD - Verify that WO number is not duplicated",
			description = "WO: - Verify that WO number is not duplicated")
	public void testWOVerifyThatWONumberIsNotDuplicated() {

		final String VIN  = "JA4LS31H8YP047397";
		final String _po  = "12345";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval ON
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(VIN);

		final String wonumber1 = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(_po);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		QuestionsScreen questionsscreen = invoiceinfoscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoiceswebpage = operationspage.clickInvoicesLink();
		invoiceswebpage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoiceswebpage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		invoiceswebpage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoiceswebpage.setSearchInvoiceNumber(invoicenumber);
		invoiceswebpage.clickFindButton();
		invoiceswebpage.archiveInvoiceByNumber(invoicenumber);
		Assert.assertFalse(invoiceswebpage.isInvoiceDisplayed(invoicenumber));
		webdriver.quit();

		//Create second WO
		myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(VIN);

		final String wonumber2 = vehiclescreen.getInspectionNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber2);
		myworkordersscreen.clickInvoiceIcon();
		myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(_po);
		questionsscreen = invoiceinfoscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		invoiceinfoscreen.clickSaveAsFinal();
		myworkordersscreen = new MyWorkOrdersScreen();
		homescreen = myworkordersscreen.clickHomeButton();
		homescreen.clickStatusButton();
		homescreen.updateDatabase();
		MainScreen mainscreen = new MainScreen();
		homescreen = mainscreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

		//Create third WO
		myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(VIN);

		final String wonumber3 = vehiclescreen.getInspectionNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		ordersummaryscreen.setTotalSale("5");
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation("5"));
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();
		Helpers.waitABit(10000);

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
				ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
		backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();

		workorderspage.makeSearchPanelVisible();
		workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		workorderspage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		workorderspage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		workorderspage.setSearchOrderNumber(wonumber3);

		workorderspage.clickFindButton();

		Assert.assertEquals(workorderspage.getWorkOrdersTableRowCount(), 1);
		webdriver.quit();
	}

	@Test(testName="Test Case 39573:WO: HD - Verify that in case valid VIN is decoded, replace existing make and model with new one",
			description = "WO: - Verify that in case valid VIN is decoded, replace existing make and model with new one")
	public void testWOVerifyThatInCaseValidVINIsDecodedReplaceExistingMakeAndModelWithNewOne() {

		final String[] VINs  = { "2A8GP54L87R279721", "1FMDU32X0PUB50142", "GFFGG"} ;
		final String makes[]  = { "Chrysler", "Ford", null } ;
		final String models[]  = { "Town and Country", "Explorer",  null };

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		for (int i = 0; i < VINs.length; i++) {
			vehiclescreen.setVIN(VINs[i]);
			Assert.assertEquals(vehiclescreen.getMake(), makes[i]);
			Assert.assertEquals(vehiclescreen.getModel(), models[i]);
			vehiclescreen.clearVINCode();
		}
		vehiclescreen.cancelOrder();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 34626:WO: HD - Verify that when service do not have questions and select several panels do not underline anyone",
			description = "WO: - Verify that when service do not have questions and select several panels do not underline anyone")
	public void testWOVerifyThatWhenServiceDoNotHaveQuestionsAndSelectSeveralPanelsDoNotUnderlineAnyone() {

		final String VIN  = "2A8GP54L87R279721";
		final String[] vehicleparts  = { "Center Rear Passenger Seat", "Dashboard" };

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_WITHOUT_QUESTIONS_PP_PANEL);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.clickVehiclePartsCell();
		for (String vehiclepart : vehicleparts) {
			selectedservicescreen.selectVehiclePart(vehiclepart);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		for (String vehiclepart : vehicleparts) {
			Assert.assertTrue(selectedservicescreen.getVehiclePartValue().contains(vehiclepart));
		}
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(iOSInternalProjectConstants.TEST_SERVICE_WITHOUT_QUESTIONS_PP_PANEL), vehicleparts.length);
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 31964:WO: HD - Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen",
			description = "Verify that keyboard is not shown over the VIN when it is entered in case only VIN is present on Vehicle screen")
	public void testWOVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen() {

		final String VIN = "2A8GP54L87R279721";


		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER, WorkOrdersTypes.WO_VIN_ONLY);
		vehiclescreen.setVIN(VIN);
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.clickVINField();
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.hideKeyboard();
		vehiclescreen.cancelOrder();
		myworkordersscreen = new MyWorkOrdersScreen();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 53824:WO: HD - Verify that message is shown for Money and Labor service when price is changed to 0$ under WO",
			description = "Verify that message is shown for Money and Labor service when price is changed to 0$ under WO")
	public void testWOVerifyThatMessageIsShownForMoneyAndLaborServiceWhenPriceIsChangedTo0UnderWO() {

		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "5";
		final String servicezeroprice = "0";
		final String servicecalclaborprice = "12";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_SMOKE_MONITOR);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);
		servicesscreen.selectService(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);

		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.AMONEYVEHICLEFF_WASHING);
		selectedservicescreen.setServicePriceValue(servicezeroprice);
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Order's technician split will be assigned to this order service if you set zero amount."));

		TechniciansPopup techniciansPopup = selectedservicescreen.clickTechniciansIcon();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Set non-zero amount for service to assign multiple technicians."));
		techniciansPopup.selecTechnician("Manager 1");
		techniciansPopup.selecTechnician("Oksana Zayats");
		Assert.assertFalse(techniciansPopup.isTechnicianIsSelected("Manager 1"));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Oksana Zayats"));
		techniciansPopup.saveTechViewDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();

		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.CALC_LABOR);
		selectedservicescreen.clickTechniciansIcon();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Set non-zero amount for service to assign multiple technicians."));

		selectedservicescreen.cancelSelectedServiceDetails();
		//selectedservicescreen.cancelSelectedServiceDetails();
		selectedservicescreen.setServiceRateValue(servicecalclaborprice);
		techniciansPopup = selectedservicescreen.clickTechniciansIcon();
		techniciansPopup.searchTechnician("Manager");
		techniciansPopup.selecTechnician("Manager 1");
		techniciansPopup.searchTechnician("Oksana");
		techniciansPopup.selecTechnician("Oksana Zayats");
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Manager 1"));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Oksana Zayats"));
		techniciansPopup.saveTechViewDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.cancelWizard();
		homescreen =  myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 45252:WO: HD - Verify that validation is present for vehicle trim field",
			description = "Verify that validation is present for vehicle trim field")
	public void testWOVerifyThatValidationIsPresentForVehicleTrimField() {

		final String VIN  = "TESTVINN";
		final String _make = "Acura";
		final String _model = "CL";
		final String trimvalue = "2.2 Premium";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_VEHICLE_TRIM_VALIDATION);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Trim is required"));
		vehiclescreen.setTrim(trimvalue);
		Assert.assertEquals(vehiclescreen.getTrim(), trimvalue);
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.saveWizard();
		homescreen =  myworkordersscreen.clickHomeButton();
	}


	@Test(testName = "Test Case 35375:WO: HD - Verify that Total sale is not shown when checkmark 'Total sale required' is not set to OFF",
			description = "Verify that Total sale is not shown when checkmark 'Total sale required' is not set to OFF")
	public void testWOVerifyThatTotalSaleIsNotShownWhenCheckmarkTotalSaleRequiredIsNotSetToOFF() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TOTAL_SALE_NOT_REQUIRED);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);;
		servicesscreen.selectService(iOSInternalProjectConstants.TAX_DISCOUNT);
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertFalse(ordersummaryscreen.isTotalSaleFieldPresent());
		ordersummaryscreen.clickSave();
		homescreen = myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		Assert.assertFalse(ordersummaryscreen.isTotalSaleFieldPresent());
		ordersummaryscreen.clickSave();
		teamworkordersscreen = new TeamWorkOrdersScreen();
		homescreen = teamworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 40821:WO: HD - Verify that it is possible to assign tech to order by action Technicians",
			description = "Verify that it is possible to assign tech to order by action Technicians")
	public void testWOVerifyThatItIsPossibleToAssignTechToOrderByActionTechnicians()  {

		final String VIN  = "1D7HW48NX6S507810";
		final String pricevalue  = "21";
		final String totalsale = "5";
		final String defaulttech  = "Employee Simple 20%";
		final String techname  = "Oksana Zayats";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("3/4\" - Penny Size");
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServiceQuantityValue("2.00");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.selectService(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
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
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		servicesscreen.selectPriceMatrices("VP2 zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
		Assert.assertEquals(pricematrix.getTechniciansValue(), defaulttech);
		pricematrix.setPrice(pricevalue);
		pricematrix.selectDiscaunt("Test service zayats");
		pricematrix.clickSaveButton();
		servicesscreen.selectService(iOSInternalProjectConstants.TAX_DISCOUNT);
		servicesscreen.selectService(iOSInternalProjectConstants.SALES_TAX);
		servicesscreen.searchAvailableService(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Back Glass");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		ordersummaryscreen.saveWizard();
		TechniciansPopup techniciansPopup = myworkordersscreen.selectWorkOrderTechniciansMenuItem(wonumber);
		//selectedservicescreen.selecTechnician(defaulttech);
		techniciansPopup.selecTechnician(techname);
		techniciansPopup.saveTechViewDetails();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Changing default employees for a work order will change split data for all services."));
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		Assert.assertEquals(vehiclescreen.getTechnician(), techname + ", " + defaulttech);
		vehiclescreen.cancelOrder();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 58663:Inspections: HD - Verify that when Panel grouping is used for package for selected Panel only linked services are shown",
			description = "Verify that when Panel grouping is used for package for selected Panel only linked services are shown")
	public void testInspectionsVerifyThatWhenPanelGroupingIsUsedForPackageForSelectedPanelOnlyLinkedServicesAreShown() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_SERVICE_TYPE_WITH_OUT_REQUIRED);
		vehiclescreen.setVIN(VIN);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.BUFF_SERVICE);
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_PANEL));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_SERVICE));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE));
		servicesscreen.clickServiceTypesButton();

		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.DYE_SERVICE);
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_VEHICLE));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_PANEL));
		Assert.assertTrue(servicesscreen.isServiceTypeExists(iOSInternalProjectConstants.CALC_MONEY_PP_SERVICE));
		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		Assert.assertTrue(servicesscreen.isServiceTypeExists("3/4\" - Penny Size"));
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 57101:WO: HD - Verify that WO is saved correct with selected sub service (no message with incorrect tech split)",
			description = "Verify that WO is saved correct with selected sub service (no message with incorrect tech split)")
	public void testWOVerifyThatWOIsSavedCorrectWithSelectedSubService_NoMessageWithIncorrectTechSplit() {

		final String VIN  = "1D7HW48NX6S507810";
		final String totalsale = "10";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(totalsale);
		Assert.assertEquals(ordersummaryscreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(totalsale));
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.searchServiceToSelect(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.selectServiceSubSrvice(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash partly");
		servicesscreen.cancelSearchAvailableService();
		servicesscreen.selectService(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE);
		servicesscreen.selectServiceSubSrvice(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash whole");
		Assert.assertTrue(servicesscreen.isServiceWithSubSrviceSelected(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash partly"));
		Assert.assertTrue(servicesscreen.isServiceWithSubSrviceSelected(iOSInternalProjectConstants.SERVICE_WITH_SUB_SERVICE, "Wash whole"));
		servicesscreen.saveWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 54542:WO: Regular - Verify that answer services are correctly added for WO when Panel group is set",
			description = "Verify that answer services are correctly added for WO when Panel group is set")
	public void testWOVerifyThatAnswerServicesAreCorrectlyAddedForWOWhenPanelGroupIsSet() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_PANEL_GROUP);
		vehiclescreen.setVIN(VIN);
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		questionsscreen = questionsscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, "Zayats Section2");
		questionsscreen.selectAnswerForQuestionWithAdditionalConditions("Q1", "No - rate 0", "A1", "Deck Lid");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE));

		servicesscreen.cancelWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "est Case 43408:WO: HD - Verify that search bar is present for service pack screen",
			description = "Verify that search bar is present for service pack screen")
	public void testWOVerifyThatSearchBarIsPresentForServicePackScreen() {

		final String VIN  = "1D7HW48NX6S507810";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.searchAvailableService("test");
		SelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE);
		selectedservicescreen.clickVehiclePartsCell();
		selectedservicescreen.selectVehiclePart("Dashboard");
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.cancelSearchAvailableService();

		servicesscreen.searchAvailableService("Tax");
		servicesscreen.selectService(iOSInternalProjectConstants.SALES_TAX);
		servicesscreen.cancelSearchAvailableService();

		servicesscreen.searchSelectedService(iOSInternalProjectConstants.SALES_TAX);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.SALES_TAX));
		servicesscreen.cancelSearchSelectedService();

		servicesscreen.searchSelectedService("test");
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.TEST_SERVICE_WITH_QF_PP_VEHICLE));
		servicesscreen.cancelWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 42178:WO: HD - Verify that Cancel message is shown for New/Existing WO",
			description = "Verify that Cancel message is shown for New/Existing WO")
	public void testWOVerifyThatCancelMessageIsShownForNewOrExistingWO()  {

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.clickCancelButton();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, "Stop Work Order Creation\nAny unsaved changes will be lost. Are you sure you want to stop creating this Work Order?");
		vehiclescreen.clickCancelButton();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Stop Work Order Creation\nAny unsaved changes will be lost. Are you sure you want to stop creating this Work Order?");

		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		vehiclescreen.clickCancelButton();
		alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, "Stop Work Order Edit\nAny unsaved changes will be lost. Are you sure you want to stop editing this Work Order?");
		vehiclescreen.clickCancelButton();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Stop Work Order Edit\nAny unsaved changes will be lost. Are you sure you want to stop editing this Work Order?");
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new VehicleScreen();
		vehiclescreen.clickCancelButton();

		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(testName="Test Case 35951:SR: HD - Verify that Accept/Decline actions are present for tech when 'Technician Acceptance Required' option is ON and status is Proposed",
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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


		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectAcceptAction();

		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Would you like to accept selected service request?");
		servicerequestsscreen = new ServiceRequestsScreen();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestOnHold(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isAcceptActionExists());
		Assert.assertFalse(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.clickHomeButton();

	}

	@Test(testName="Test Case 35953:SR: HD - Verify that when SR is declined status reason should be selected",
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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


		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectDeclineAction();

		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Would you like to decline selected service request?");
		servicerequestsscreen.clickDoneCloseReasonDialog();
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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


		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectEditServiceRequestAction();
		VehicleScreen vehiclescreen = new VehicleScreen();
		vehiclescreen.setTech("Simple 20%");
		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);;
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");
		questionsscreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);

		servicerequestsscreen.clickHomeButton();

	}

	@Test(testName="Test Case 36004:SR: HD - Verify that it is possible to accept/decline Appointment when option 'Appointment Acceptance Required' = ON",
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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

		homescreen = new HomeScreen();
		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();

		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectAppointmentRequestAction();

		Assert.assertTrue(servicerequestsscreen.isAcceptAppointmentRequestActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineAppointmentRequestActionExists());
		servicerequestsscreen.clickCloseButton();

		servicerequestsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 50029:WO: HD - Verify that default tech is not changed when reset order split",
			description = "Verify that default tech is not changed when reset order split")
	public void testWOVerifyThatDefaultTechIsNotChangedWhenResetOrderSplit() {

		final String VIN = "1D7HW48NX6S507810";
		final String pricevalue = "21";
		final String defaulttech = "Oksi Employee";
		final String techname = "Oksana Zayats";

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAULT_TECH_OKSI);
		selectedServiceDetailsScreen.setServicePriceValue(pricevalue);
		TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesscreen.selectService("3/4\" - Penny Size");


		vehiclescreen = questionsscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(techname);
		techniciansPopup.unselecTechnician("Employee Simple 20%");
		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(alerttext, "Changing default employees for a work order will change split data for all services.");
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		selectedServiceDetailsScreen = servicesscreen.openServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAULT_TECH_OKSI);
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen = servicesscreen.openServiceDetails("3/4\" - Penny Size");
		selectedServiceDetailsScreen.setServicePriceValue(pricevalue);
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(techname));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesscreen= new ServicesScreen();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 34427:WO: HD - Verify Assign tech to service type, instead of individual services",
			description = "Verify Assign tech to service type, instead of individual services")
	public void testWOVerifyAssignTechToServiceTypeInsteadOfIndividualServices() {

		final String VIN  = "1D7HW48NX6S507810";
		final String defaulttech  = "Employee Simple 20%";


		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		VehicleScreen vehiclescren =  myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_GROUP_SERVICE_TYPE);
		vehiclescren.setVIN(VIN);

		ServicesScreen servicesscreen = vehiclescren.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickTechnicianToolbarIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), "Services\n" +
				"No selected services.");

		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.BUFF_SERVICE);
		SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
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

		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
		selectedservicebundlescreen.selectBundle(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.DYE_SERVICE);
		selectedservicedetailscreen.setServicePriceValue("80");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.saveSelectedServiceDetails();

		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectGroupServiceItem("Wash Detail");
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Price Matrix Zayats");
		servicesscreen.selectPriceMatrices("VP2 zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.setSizeAndSeverity("DIME", "MEDIUM");
		pricematrix.setPrice("150");
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		pricematrix.selectDiscaunt("Test service zayats");
		pricematrix.clickSaveButton();

		servicesscreen = new ServicesScreen();
		servicesscreen.clickTechnicianToolbarIcon();
		ServiceTypesScreen serviceTypesScreen = new ServiceTypesScreen();
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

		TechniciansPopup techniciansPopup = serviceTypesScreen.clickOnPanel(iOSInternalProjectConstants.BUFF_SERVICE);
		techniciansPopup.selecTechnician(defaulttech);
		techniciansPopup.saveTechViewDetails();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.clickServiceTypesButton();
		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.BUFF_SERVICE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		techniciansPopup = selectedservicedetailscreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		techniciansPopup.cancelSearchTechnician();
		selectedservicedetailscreen.cancelSelectedServiceDetails();

		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		techniciansPopup = selectedservicedetailscreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		techniciansPopup.cancelSearchTechnician();
		selectedservicedetailscreen.cancelSelectedServiceDetails();
		servicesscreen.clickServiceTypesButton();

		servicesscreen.clickTechnicianToolbarIcon();
		techniciansPopup = serviceTypesScreen.clickOnPanel(iOSInternalProjectConstants.MISCELLANEOUS_SERVICE);
		techniciansPopup.selecTechnician("Inspector 1");
		techniciansPopup.saveTechViewDetails();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.openServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicebundlescreen = new SelectedServiceBundleScreen();
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.DYE_SERVICE);
		techniciansPopup = selectedservicedetailscreen.clickTechniciansCell();

		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Inspector 1"));
		techniciansPopup.cancelSearchTechnician();
		selectedservicedetailscreen.cancelSelectedServiceDetails();
		selectedservicebundlescreen.clickCancelBundlePopupButton();

		servicesscreen.clickTechnicianToolbarIcon();
		techniciansPopup = serviceTypesScreen.clickOnPanel("WHEEL REPAIR");
		techniciansPopup.selecTechnician("Inspector 1");
		techniciansPopup.selecTechnician("Man-Insp 1");
		techniciansPopup.saveTechViewDetails();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.openServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		selectedservicebundlescreen = new SelectedServiceBundleScreen();
		selectedservicebundlescreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		techniciansPopup = selectedservicedetailscreen.clickTechniciansCell();

		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Inspector 1"));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Man-Insp 1"));
		techniciansPopup.cancelSearchTechnician();
		selectedservicedetailscreen.cancelSelectedServiceDetails();
		selectedservicebundlescreen.clickCancelBundlePopupButton();

		servicesscreen.selectGroupServiceItem(iOSInternalProjectConstants.BUFF_SERVICE);
		selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_PP_VEHICLE_NOT_MULTIPLE);
		selectedservicedetailscreen.saveSelectedServiceDetails();
		selectedservicedetailscreen.selectVehiclePart("Back Glass");
		selectedservicedetailscreen.saveSelectedServiceDetails();
		techniciansPopup = selectedservicedetailscreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		techniciansPopup.cancelSearchTechnician();
		selectedservicedetailscreen.cancelSelectedServiceDetails();

		QuestionsScreen questionsscreen = servicesscreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A1");

		OrderSummaryScreen ordersummaryscreen = questionsscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 53502:WO Monitor: HD - Verify that it is possible to assign tech when WO is not started," +
			"Test Case 53503:WO Monitor: HD - Verify that it is possible to assign tech when Service is not completed and vice versa",
			description = "Verify that it is possible to assign tech when WO is not started," +
					"Verify that it is possible to assign tech when Service is not completed and vice versa")
	public void testWOVerifyThatItIsPossibleToAssignTechWhenWOIsNotStarted() {

		final String VIN = "1D7HW48NX6S507810";
		final String techname = "Oksi Test User";

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.selectLocation("Test Location ZZZ");
		final String wonumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
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

		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();

		teamworkordersscreen.clickOnWO(wonumber);
		OrderMonitorScreen orderMonitorScreen = teamworkordersscreen.selectWOMonitor();
		Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
		OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel("3/4\" - Penny Size");
		TechniciansPopup techniciansPopup = serviceDetailsPopup.clickTech();
		techniciansPopup.selecTechnician(techname);
		techniciansPopup.saveTechViewDetails();
		orderMonitorScreen = new OrderMonitorScreen();
		orderMonitorScreen.selectPanel("3/4\" - Penny Size");
		Assert.assertEquals(serviceDetailsPopup.getTechnicianValue(), techname);
		serviceDetailsPopup.clickServiceDetailsDoneButton();

		orderMonitorScreen = new OrderMonitorScreen();
		orderMonitorScreen.clickStartOrderButton();
		//System.out.println(Helpers.getAlertTextAndAccept());
		Assert.assertTrue(Helpers.getAlertTextAndAccept().contains("Would you like to start repair order on"));
		orderMonitorScreen = new OrderMonitorScreen();

		orderMonitorScreen.selectPanel(iOSInternalProjectConstants.SR_S1_MONEY_PANEL);
		serviceDetailsPopup.clickTech();
		techniciansPopup.selecTechnician("Manager 1");
		techniciansPopup.saveTechViewDetails();
		orderMonitorScreen = new OrderMonitorScreen();
		final String SR_S1_MONEY_PANEL = iOSInternalProjectConstants.SR_S1_MONEY_PANEL;
		serviceDetailsPopup = orderMonitorScreen.selectPanel(SR_S1_MONEY_PANEL);
		serviceDetailsPopup.clickStartService();
		serviceDetailsPopup = orderMonitorScreen.selectPanel(SR_S1_MONEY_PANEL);
		serviceDetailsPopup.setCompletedServiceStatus();
		Assert.assertEquals(orderMonitorScreen.getPanelStatus(SR_S1_MONEY_PANEL), "Completed");
		orderMonitorScreen.selectPanel(SR_S1_MONEY_PANEL);
		serviceDetailsPopup.clickTech();
		serviceDetailsPopup.clickServiceDetailsDoneButton();

		TeamWorkOrdersScreen teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 53505:WO Monitor: HD - Verify that it is not possible to assign tech when WO is On Hold",
			description = "Verify that it is not possible to assign tech when WO is On Hold")
	public void testWOVerifyThatItIsNotPossibleToAssignTechWhenWOIsOnHold() {

		final String VIN = "1D7HW48NX6S507810";
		final String techname = "Oksi Test User";

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.selectLocation("Test Location ZZZ");
		final String wonumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
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

		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation("Test Location ZZZ");
		teamworkordersscreen.clickSearchSaveButton();

		teamworkordersscreen.clickOnWO(wonumber);
		OrderMonitorScreen orderMonitorScreen = teamworkordersscreen.selectWOMonitor();
		Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
		orderMonitorScreen.changeStatusForWorkOrder("On Hold", "On Hold new reason");

		OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel("3/4\" - Penny Size");
		serviceDetailsPopup.clickTech();
		serviceDetailsPopup.clickServiceDetailsCancelButton();

		TeamWorkOrdersScreen teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(testName = "Test Case 66362:WO: HD - Verify that Tech split assigned form Vehicle screen is set to services under list",
			description = "Verify that Tech split assigned form Vehicle screen is set to services under list")
	public void testWOVerifyThatTechSplitAssignedFormVehicleScreenIsSetToServicesUnderList() {

		final String VIN = "1D7HW48NX6S507810";
		final String techname1 = "Inspector 1";
		final String techname2 = "Man-Insp 1";
		final List<String> servicesList = new ArrayList<>();
		servicesList.add(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesList.add(iOSInternalProjectConstants.DYE_SERVICE);
		servicesList.add(iOSInternalProjectConstants.WHEEL_SERVICE);

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (String serviceName : servicesList) {
			servicesscreen.selectService(serviceName);
		}

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(techname1);
		techniciansPopup.selecTechnician(techname2);
		techniciansPopup.saveTechnociansViewWithAlert();

		vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (String serviceName : servicesList) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openServiceDetails(serviceName);
			techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(techname1));
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(techname2));
			techniciansPopup.saveTechViewDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen = new ServicesScreen();
		}

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(testName = "Test Case 68296:WO: HD - Verify price matrix item doesn't have additional services - its main service's tech split amount is equal to main service's amount",
			description = "Verify price matrix item doesn't have additional services - its main service's tech split amount is equal to main service's amount")
	public void testWOVerifyPriceMatrixItemDoesntHaveAdditionalServicesItsMainServicesTechSplitAmountIsEqualToMainServicesAmount() {

		final String VIN = "1D7HW48NX6S507810";
		final String matrixServicePrice = "100";

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.CALC_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Back Glass");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
		pricematrix.setPrice(matrixServicePrice);
		pricematrix.clickSave();
		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(techniciansPopup.getTechnicianPercentage("Employee Simple 20%"), "%100.00");
		techniciansPopup.cancelTechViewDetails();
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

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();

		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.CALC_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Back Glass");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
		pricematrix.setPrice(matrixServicePrice);
		TechniciansPopup techniciansPopup = pricematrix.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage("Employee Simple 20%"), "%100.00");
		Assert.assertEquals(techniciansPopup.getTechnicianPrice("Employee Simple 20%"), "$100.00");
		Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
		Assert.assertFalse(techniciansPopup.isCustomTabSelected());
		techniciansPopup.cancelTechViewDetails();

		pricematrix.selectDiscaunt("Calc_Money_PP_Panel");
		Assert.assertEquals(pricematrix.getPriceMatrixTotalPriceValue(), "$110.00");
		techniciansPopup = pricematrix.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage("Employee Simple 20%"), "%100.00");
		Assert.assertEquals(techniciansPopup.getTechnicianPrice("Employee Simple 20%"), "$100.00");
		Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
		Assert.assertFalse(techniciansPopup.isCustomTabSelected());
		techniciansPopup.cancelTechViewDetails();

		pricematrix.clickSave();
		servicesscreen = new ServicesScreen();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), "$110.00");

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 68298:WO: Regular - price matrix item has percentage additional service - its main service's tech split amount is equal to main service's amount + additional percentage service's amount",
			description = "Price matrix item has percentage additional service - its main service's tech split amount is equal to main service's amount + additional percentage service's amount")
	public void testWOVerifyPriceMatrixItemHasPercentageAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmountPlusAdditionalPercentage() {

		final String VIN = "1D7HW48NX6S507810";
		final String matrixServicePrice = "100";

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();

		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.CALC_PRICE_MATRIX);
		servicesscreen.selectPriceMatrices("Back Glass");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
		pricematrix.setPrice(matrixServicePrice);
		TechniciansPopup techniciansPopup = pricematrix.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage("Employee Simple 20%"), "%100.00");
		Assert.assertEquals(techniciansPopup.getTechnicianPrice("Employee Simple 20%"), "$100.00");
		Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
		Assert.assertFalse(techniciansPopup.isCustomTabSelected());
		techniciansPopup.cancelTechViewDetails();

		pricematrix.clickDiscaunt("Calc_Discount");
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue("-20");
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(pricematrix.getPriceMatrixTotalPriceValue(), "$80.00");
		techniciansPopup = pricematrix.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected("Employee Simple 20%"));
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage("Employee Simple 20%"), "%100.00");
		Assert.assertEquals(techniciansPopup.getTechnicianPrice("Employee Simple 20%"), "$80.00");
		Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
		Assert.assertFalse(techniciansPopup.isCustomTabSelected());
		techniciansPopup.cancelTechViewDetails();

		pricematrix.clickSave();
		servicesscreen = new ServicesScreen();
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

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();

		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen serviceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		serviceDetailsScreen.setServicePriceValue(servicePrice);
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		serviceDetailsScreen.saveSelectedServiceDetails();
		serviceDetailsScreen.selectVehiclePart("Grill");
		serviceDetailsScreen.saveSelectedServiceDetails();
		serviceDetailsScreen.saveSelectedServiceDetails();

		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician("Ded Talash");
		techniciansPopup.saveTechnociansViewWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		servicesscreen.openServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);
		techniciansPopup = serviceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		techniciansPopup.isTechnicianIsSelected(defaulttech);
		techniciansPopup.clickCancelButton();
		serviceDetailsScreen.clickCancelSelectedServiceDetails();
		servicesscreen = new ServicesScreen();

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

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();

		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_SERVICE_PRICE_MATRIX);
		servicesscreen.selectServicePriceMatrices("Price Matrix Zayats");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.setSizeAndSeverity("CENT", "MEDIUM");
		pricematrix.setPrice(servicePrice);
		pricematrix.selectPriceMatrix("VP1 zayats");
		pricematrix.setPrice(serviceZeroPrice);

		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_DEFAULT_TECH_SPLIT_WILL_BE_ASSIGNED_IF_SET_ZERO_AMAUNT);
		pricematrix.clickOnTechnicians();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_VEHICLE_PART);
		TechniciansPopup techniciansPopup = new TechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(defaulttech), PricesCalculations.getPriceRepresentation(serviceZeroPrice));
		techniciansPopup.saveTechViewDetails();
		pricematrix = new PriceMatrixScreen();
		pricematrix.clickSaveButton();

		servicesscreen = new ServicesScreen();
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

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen serviceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		serviceDetailsScreen.setServicePriceValue(servicePrice);
		serviceDetailsScreen.clickAdjustments();
		serviceDetailsScreen.selectAdjustment(adjustment);
		serviceDetailsScreen.saveSelectedServiceDetails();

		serviceDetailsScreen.clickVehiclePartsCell();
		serviceDetailsScreen.selectVehiclePart(vehiclePart);
		serviceDetailsScreen.saveSelectedServiceDetails();
		serviceDetailsScreen.saveSelectedServiceDetails();


		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		TeamWorkOrdersScreen teamWorkOrdersScreen = homescreen.clickTeamWorkordersButton();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation("All locations");
		teamWorkOrdersScreen.setSearchWorkOrderNumber(wonumber);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonumber);
		OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		Assert.assertEquals(orderMonitorScreen.getMonitorServiceAmauntValue(iOSInternalProjectConstants.SR_S1_MONEY), "$1,600.00 x 1.00");
		OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(iOSInternalProjectConstants.SR_S1_MONEY);
		Assert.assertEquals(serviceDetailsPopup.getServiceDetailsPriceValue(), "$2,000.00");
		TechniciansPopup techniciansPopup = serviceDetailsPopup.clickTech();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(defaulttech));
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(defaulttech), "$1,600.00");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(defaulttech), "%100.00");

		techniciansPopup.cancelTechViewDetails();
		serviceDetailsPopup.clickServiceDetailsDoneButton();
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

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_MONITOR_REQUIRED_SERVICES_ALL);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceBundleScreen serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails("Calc_Bundle");

		for (String srvName : services) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(srvName);
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
			selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}
		serviceBundleScreen.clickCancelBundlePopupButton();
		servicesscreen = new ServicesScreen();
		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(tech1);
		techniciansPopup.selecTechnician(tech2);
		techniciansPopup.unselecTechnician(defaulttech);
		techniciansPopup.saveTechnociansViewWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails("Calc_Bundle");

		for (String srvName : services) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(srvName);
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
			selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}
		serviceBundleScreen.clickCancelBundlePopupButton();
		servicesscreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openServiceDetails("Calc_Money_PP_Vehicle");
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesscreen.clickOnSelectService("Calc_Price_Matrix");
		servicesscreen.selectServicePriceMatrices("Calc_Matrix");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix("Back Glass");
		pricematrix.setSizeAndSeverity("CENT", "LIGHT");
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails("Calc_Bundle");

		for (String srvName : services) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(srvName);
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
			selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}
		serviceBundleScreen.clickCancelBundlePopupButton();
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

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

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceBundleScreen serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleService);


		SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Oksana Zayats");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
		selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();
		serviceBundleScreen.clickCancelBundlePopupButton();

		servicesscreen = new ServicesScreen();
		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(tech1);
		techniciansPopup.selecTechnician(tech2);
		techniciansPopup.unselecTechnician("Oksana Zayats");
		techniciansPopup.saveTechnociansViewWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleService);

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart1);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
		selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart2);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		serviceBundleScreen.changeAmountOfBundleService("25");
		serviceBundleScreen.saveSelectedServiceDetails();

		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleService);

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Oksi Employee");
		selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();
		serviceBundleScreen.clickCancelBundlePopupButton();
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

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

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceBundleScreen serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleService);


		SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Oksana Zayats");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
		selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();
		serviceBundleScreen.clickCancelBundlePopupButton();

		servicesscreen = new ServicesScreen();
		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(tech1);
		techniciansPopup.selecTechnician(tech2);
		techniciansPopup.unselecTechnician("Oksana Zayats");
		techniciansPopup.saveTechnociansViewWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleService);

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart1);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), defaulttech);
		selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart2);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		serviceBundleScreen.changeAmountOfBundleService("25");
		serviceBundleScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("Money_Service_with_expences");
		selectedServiceDetailsScreen.setServicePriceValue(zaroPrice);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesscreen.openServiceDetails("Money_Service_with_expences");
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech1));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech2));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleService);

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service1);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service2);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Oksi Employee");
		selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();

		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(service3);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), "Employee Simple 20%");
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		serviceBundleScreen.waitUntilBundlePopupOpened();
		serviceBundleScreen.clickCancelBundlePopupButton();

		servicesscreen.openServiceDetails("Money_Service_with_expences");
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech1));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech2));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

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

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_ALL_SERVICES);
		vehiclescreen.setVIN(VIN);
		final String wonumber = vehiclescreen.getInspectionNumber();
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(tech1);
		techniciansPopup.selecTechnician(tech2);
		techniciansPopup.unselecTechnician(defaulttech);
		techniciansPopup.saveTechnociansViewWithAlert();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.selectService("Turbo Service");
		servicesscreen.selectService("Vovan Service");
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openServiceDetails("Turbo Service");
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech1));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech2));
		techniciansPopup.clickCancelButton();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openServiceDetails("Vovan Service");
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech1));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech2));
		techniciansPopup.clickCancelButton();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		servicesscreen.selectService("Matrix Service");
		servicesscreen.selectServicePriceMatrices("Test Matrix Labor");
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.selectPriceMatrix("Back Glass");
		pricematrix.setSizeAndSeverity("DIME", "LIGHT");
		Assert.assertEquals(pricematrix.getTechniciansValue(), tech1 + ", " + tech2);
		pricematrix.clickDiscaunt("Oksi_Service_PP_Vehicle");
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech1));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech2));
		techniciansPopup.cancelTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		pricematrix.setTime("2");
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();

		servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.BUNDLE1_DISC_EX);
		SelectedServiceBundleScreen serviceBundleScreen = new SelectedServiceBundleScreen();
		techniciansPopup =serviceBundleScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech1));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech2));
		techniciansPopup.clickCancelButton();
		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), tech1 + ", " + tech2);
		selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
		serviceBundleScreen.changeAmountOfBundleService("70");
		serviceBundleScreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.BUNDLE1_DISC_EX));
		servicesscreen.clickSave();
		servicesscreen.clickFinalPopup();
		servicesscreen.clickSave();
		myworkordersscreen = new MyWorkOrdersScreen();
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

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_ALL_SERVICES);
		vehiclescreen.setVIN(VIN);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(tech1);
		techniciansPopup.unselecTechnician(defaulttech);
		techniciansPopup.saveTechnociansViewWithAlert();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SERVICE_WITH_DEFAUT_TECH);

		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart("Grill");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.setServicePriceValue(serviceZaroPrice);
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(techDefSelected));
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(techDefSelected), "$0.00");
		techniciansPopup.cancelTechViewDetails();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		servicesscreen = new ServicesScreen();
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

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(VIN);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(tech1);
		techniciansPopup.unselecTechnician(defaulttech);
		techniciansPopup.saveTechnociansViewWithAlert();

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, ScreenNamesConstants.ZAYATS_SECTION1);
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Question 2", "A3");

		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");

		Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), "$0.00");
		selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(tech1));
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(tech1), "$0.00");
		techniciansPopup.cancelTechViewDetails();
		selectedServiceDetailsScreen.cancelSelectedServiceDetails();

		servicesscreen = new ServicesScreen();
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation(defLocation);
		String wonum = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);

		servicesscreen.selectService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		PriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		pricematrix.selectPriceMatrix(_pricematrix);
		pricematrix.setSizeAndSeverity(_size, _severity);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		pricematrix.clickSaveButton();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();

		TeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defLocation);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		OrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		OrderMonitorServiceDetailsPopup serviceDetailsPopup = ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		serviceDetailsPopup.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		serviceDetailsPopup.clickServiceDetailsCancelButton();

		ordermonitorscreen.clickStartPhase();
		serviceDetailsPopup = ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		serviceDetailsPopup.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		serviceDetailsPopup.clickServiceDetailsCancelButton();

		ordermonitorscreen.clickStartPhaseCheckOutButton();
		serviceDetailsPopup = ordermonitorscreen.selectPanel(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		serviceDetailsPopup.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
		serviceDetailsPopup.clickServiceDetailsCancelButton();

		ordermonitorscreen.changeStatusForStartPhase(OrderMonitorPhases.COMPLETED);
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE),
				OrderMonitorPhases.COMPLETED.getrderMonitorPhaseName());
		ordermonitorscreen.clickBackButton();
		homescreen = teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName="Test Case 29181:WO Monitor: Verify that when option 'Phase enforcement' is OFF and 'Start service required' it possible to start service from inactive phase",
			description = "WO Monitor: Verify that when option 'Phase enforcement' is OFF and 'Start service required' it possible to start service from inactive phase")
	public void testVerifyThatWhenOptionPhaseEnforcementIsOFFAndStartServiceRequiredItIsPossibleToStartServiceFromInactivePhase() {

		final String VIN  = "1D3HV13T19S825733";
		final String _pricematrix = "HOOD";
		final String _size = "NKL";
		final String _severity = "VERY LIGHT";
		final String defLocation = "Test Location ZZZ";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		vehiclescreen.selectLocation(defLocation);
		String wonum = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(iOSInternalProjectConstants.DISC_EX_SERVICE1);
		servicesscreen.selectService(iOSInternalProjectConstants.DYE_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.WHEEL_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.TEST_TAX_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		servicesscreen.selectService(iOSInternalProjectConstants.VPS1_SERVICE);

		servicesscreen.selectService(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE);
		PriceMatrixScreen pricematrix = servicesscreen.selectServicePriceMatrices(iOSInternalProjectConstants.HAIL_MATRIX_SERVICE);
		pricematrix.selectPriceMatrix(_pricematrix);
		pricematrix.setSizeAndSeverity(_size, _severity);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DYE_SERVICE);
		pricematrix.selectDiscaunt(iOSInternalProjectConstants.DISCOUNT_5_10_SERVICE);
		pricematrix.clickSaveButton();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(iOSInternalProjectConstants.DENT_REMOVAL_SERVICE));

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();

		TeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defLocation);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		OrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		OrderMonitorServiceDetailsPopup serviceDetailsPopup = ordermonitorscreen.selectPanel(iOSInternalProjectConstants.WHEEL_SERVICE);
		Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
		serviceDetailsPopup.clickServiceDetailsDoneButton();

		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonPresent());
		ordermonitorscreen.clickStartPhase();
		Assert.assertFalse(ordermonitorscreen.isStartPhaseButtonPresent());
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
		final String defLocation = "All locations";

		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_DELAY_START);
		vehiclescreen.setVIN(VIN);
		String wonum = vehiclescreen.getInspectionNumber();

		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedServiceDetailsScreen.setServicePriceValue(defServicePrice);
		TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician(newtech);
		techniciansPopup.unselecTechnician(defaultTech);
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician(newtech);
		techniciansPopup.unselecTechnician(defaultTech);
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician(newtech);
		techniciansPopup.unselecTechnician(defaultTech);
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician(newtech);
		techniciansPopup.unselecTechnician(defaultTech);
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		TeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defLocation);
		teamWorkOrdersScreen.setSearchWorkOrderNumber(wonum);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);

		OrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		ordermonitorscreen.clickStartOrderButton();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_CANT_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
		ordermonitorscreen = new OrderMonitorScreen();

		ordermonitorscreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();

	}

	@Test(testName="Test Case 30547:WO Monitor: Verify that is it impossible to change status for service/phase when order is not started",
			description = "WO Monitor: Verify that is it impossible to change status for service/phase when order is not started")
	public void testVerifyThatIsItImpossibleToChangeStatusForServiceOrPhaseWhenOrderIsNotStarted() {

		final String VIN = "1D3HV13T19S825733";
		final String defServicePrice = "5";
		final String defaultTech = "Oksana Zayats";
		final String newtech = "Vladimir Avsievich";
		final String vehiclePart = "Hood";
		final String defLocation = "All locations";

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_DELAY_START);
		vehiclescreen.setVIN(VIN);
		String wonum = vehiclescreen.getInspectionNumber();

		vehiclescreen.verifyMakeModelyearValues("Dodge", "Ram Pickup 1500", "2009");
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		selectedServiceDetailsScreen.setServicePriceValue(defServicePrice);
		TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician(newtech);
		techniciansPopup.unselecTechnician(defaultTech);
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE);
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician(newtech);
		techniciansPopup.unselecTechnician(defaultTech);
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician(newtech);
		techniciansPopup.unselecTechnician(defaultTech);
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(iOSInternalProjectConstants.SR_S1_MONEY);
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		selectedServiceDetailsScreen.selectVehiclePart(vehiclePart);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician(newtech);
		techniciansPopup.unselecTechnician(defaultTech);
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale("5");
		ordersummaryscreen.saveWizard();

		TeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(defLocation);
		teamWorkOrdersScreen.setSearchWorkOrderNumber(wonum);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);

		OrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		Assert.assertEquals(ordermonitorscreen.getPanelStatus("3/4\" - Penny Size"),
				OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.SR_S1_MONEY_FLATFEE),
				OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.SR_S1_MONEY_VEHICLE),
				OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());
		Assert.assertEquals(ordermonitorscreen.getPanelStatus(iOSInternalProjectConstants.SR_S1_MONEY),
				OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());

		OrderMonitorServiceDetailsPopup serviceDetailsPopup = ordermonitorscreen.selectPanel(iOSInternalProjectConstants.SR_S1_MONEY);
		serviceDetailsPopup.clickServiceStatusCell();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(),
				AlertsCaptions.YOU_MUST_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
		serviceDetailsPopup.clickServiceDetailsCancelButton();

		ordermonitorscreen.clickOrderStartDateButton();
		LocalDate date = LocalDate.now();
		ordermonitorscreen.setOrderStartYearValue(date.getYear()+1);
		Assert.assertEquals(ordermonitorscreen.getOrderStartYearValue(), Integer.toString(date.getYear()));
		ordermonitorscreen.setOrderStartYearValue(date.getYear()-1);
		Assert.assertEquals(ordermonitorscreen.getOrderStartYearValue(), Integer.toString(date.getYear()-1));
		ordermonitorscreen.closeSelectorderDatePicker();

		ordermonitorscreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();

		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}
}
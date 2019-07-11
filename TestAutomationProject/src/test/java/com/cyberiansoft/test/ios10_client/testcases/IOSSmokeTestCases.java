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
import com.mashape.unirest.http.exceptions.UnirestException;
import io.appium.java_client.MobileBy;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSRVerifyThatSRIsCreatedCorrectlyWhenSelectOwnerOnVehicleInfo(String rowID,
																	  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String owner = "Avalon";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehiclescreen.selectOwnerT(owner);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());

		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSRVerifyThatCheckInActionIsPresentForSRWhenAppropriateSRTypeHasOptionCheckInON(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String owner = "Avalon";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehiclescreen.selectOwnerT(owner);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());

		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSRVerifyThatCheckInActionIsChangedToUndoCheckInAfterPressingOnItAndViceVersa(String rowID,
																								   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String owner = "Avalon";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehiclescreen.selectOwnerT(owner);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectUndoCheckMenu();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSRVerifyThatFilterNotCheckedInIsWorkingCorrectly(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		final String owner = "Avalon";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		vehiclescreen.selectOwnerT(owner);
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.PACKAGE_FOR_MONITOR);
		servicesscreen.selectService(serviceRequestData.getMoneyService().getServiceName());

		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
		servicerequestsscreen = new ServiceRequestsScreen();
		String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestClient(srnumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
		Assert.assertTrue(servicerequestsscreen.getServiceRequestVehicleInfo(srnumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
		servicerequestsscreen.applyNotCheckedInFilter();
		Assert.assertEquals(servicerequestsscreen.getFirstServiceRequestNumber(), srnumber);

		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCheckInMenu();
		Assert.assertFalse(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.resetFilter();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestExists(srnumber));
		servicerequestsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenCustomerApprovalRequiredIsSetToONAutoEmailIsNotSentWhenApprovalDoesNotExist(String rowID,
																	 String description, JSONObject testData) throws Exception {

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
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES, ScreenNamesConstants.ZAYATS_TEST_PACK);
		for (ServiceData serviceData : workOrderData.getServicesList())
			servicesscreen.selectService(serviceData.getServiceName());
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
		myinvoicesscreen.clickInvoiceApproveButton(invoicenumber);

		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		ApproveInspectionsScreen approveinspscreen =  new ApproveInspectionsScreen();
		approveinspscreen.drawApprovalSignature();
		approveinspscreen.clickApproveButton();
		myinvoicesscreen.clickHomeButton();

		final String invpoicereportfilenname = invoicenumber + ".pdf";

		NadaEMailService nada = new NadaEMailService();
		nada.setEmailId(ReconProIOSStageInfo.getInstance().getTestMail());

		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(invoicenumber, invpoicereportfilenname);
		Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoicenumber +
				" in mail box " + nada.getEmailId() + ". At time " +
				LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		nada.deleteMessageWithSubject(invoicenumber);

		File pdfdoc = new File(invpoicereportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains(workOrderData.getVehicleInfoData().getVINNumber()));
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(pdftext.contains(serviceData.getServiceName()));
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenCustomerApprovalRequiredIsSetToOffAutoEmailIsSentWhenInvoiceAsAutoApproved(String rowID,
																											  String description, JSONObject testData) throws Exception {

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
			servicesscreen.selectService(serviceData.getServiceName());
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
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
		myinvoicesscreen.clickHomeButton();


		final String invpoicereportfilenname = invoicenumber + ".pdf";
		NadaEMailService nada = new NadaEMailService();
		nada.setEmailId(ReconProIOSStageInfo.getInstance().getTestMail());
		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(invoicenumber, invpoicereportfilenname);
		Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoicenumber +
				" in mail box " + nada.getEmailId() + ". At time " +
				LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		nada.deleteMessageWithSubject(invoicenumber);

		File pdfdoc = new File(invpoicereportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains(workOrderData.getVehicleInfoData().getVINNumber()));
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(pdftext.contains(serviceData.getServiceName()));
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedMyInvoices(String rowID,
																											 String description, JSONObject testData) throws Exception {


		final String printServerName = "TA_Print_Server";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String invoicenum = myinvoicesscreen.getFirstInvoiceValue();
		myinvoicesscreen.printInvoice(invoicenum, printServerName);
		myinvoicesscreen.clickHomeButton();
		Helpers.waitABit(20000);
		myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertTrue(myinvoicesscreen.isInvoicePrintButtonExists(invoicenum));
		myinvoicesscreen.clickHomeButton();
	}

	@Test(testName="Test Case 26691:Invoices: HD - Verify that print icon is shown next to invoice when it was printed (Team Invoices)",
			description = "Invoices: HD - Verify that print icon is shown next to invoice when it was printed (Team Invoices)")
	public void testHDVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedTeamInvoices() {

		final String printServerName = "TA_Print_Server";
		homescreen = new HomeScreen();
		TeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
		teaminvoicesscreen.printInvoice(invoicenum, printServerName);
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
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSRVerifyMultipleInspectionsAndMultipleWorkOrdersToBeTiedToAServiceRequest(String rowID,
																							  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<String> inspnumbers = new ArrayList<>();
		List<String> wonumbers = new ArrayList<>();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
		VehicleScreen vehiclescreen = servicerequestsscreen.addServiceRequest(ServiceRequestTypes.SR_ALL_PHASES);
		vehiclescreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.clickSave();
		Helpers.getAlertTextAndCancel();
		servicerequestsscreen = new ServiceRequestsScreen();
		final String srnumber = servicerequestsscreen.getFirstServiceRequestNumber();
		for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
			servicerequestsscreen.createInspectionFromServiceReques(srnumber,InspectionsTypes.valueOf(inspectionData.getInspectionType()));
			inspnumbers.add(vehiclescreen.getInspectionNumber());
			if (inspectionData.isDraft()) {
				ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
				servicesscreen = new ServicesScreen();
				servicesscreen.clickSaveAsDraft();
			} else
				vehiclescreen.saveWizard();
			servicerequestsscreen = new ServiceRequestsScreen();
		}

		servicerequestsscreen.selectServiceRequest(srnumber);
		ServiceRequestdetailsScreen serviceRequestdetailsScreen = servicerequestsscreen.selectDetailsRequestAction();
		serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();

		TeamInspectionsScreen teaminspectionsscreen = new TeamInspectionsScreen();
		for (String inspectnumber : inspnumbers)
			Assert.assertTrue(teaminspectionsscreen.isInspectionExists(inspectnumber));

		teaminspectionsscreen.clickBackServiceRequest();
		serviceRequestdetailsScreen.clickBackButton();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			servicerequestsscreen.createWorkOrderFromServiceRequest(srnumber, WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
			wonumbers.add(vehiclescreen.getInspectionNumber());
			OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
			ordersummaryscreen.saveWizard();
		}

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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval(String rowID,
																													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		final int timetowaitwo = 4;
		final String inspectionNotes = "Inspection notes";
		final String serviceNotes = "Service Notes";
		final String locationFilterValue = "All locations";
		final String searchStatus = "New";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		SettingsScreen settingsscreen = homescreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VisualInteriorScreen visualInteriorScreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
		visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_SPORT_CAR);
		VehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);;
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspnumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			if (serviceData.getServicePrice() != null)
				ServicesScreenSteps.selectServiceWithServiceData(serviceData);
			else
				ServicesScreenSteps.selectService(serviceData.getServiceName());
		}
		servicesscreen.saveWizard();

		myinspectionsscreen.selectInspectionForEdit(inspnumber);
		visualInteriorScreen = new VisualInteriorScreen();
		visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_SPORT_CAR);
		vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		NotesScreen notesscreen = vehiclescreen.clickNotesButton();
		notesscreen.setNotes(inspectionNotes);
		notesscreen.clickSaveButton();

		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
			notesscreen = servicedetailsscreen.clickNotesCell();
			notesscreen.setNotes(serviceNotes);
			notesscreen.clickSaveButton();
			servicedetailsscreen.saveSelectedServiceDetails();
		}
		for (ServiceData serviceData : inspectionData.getServicesList())
			Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedService(serviceData.getServiceName()));

		for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
			PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);
		}
		vehiclescreen.saveWizard();

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
		teamwoscreen.selectSearchStatus(searchStatus);
		teamwoscreen.setSearchType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR.getWorkOrderTypeName());
		teamwoscreen.selectSearchLocation(locationFilterValue);
		teamwoscreen.clickSearchSaveButton();

		final String wonumber = teamwoscreen.getFirstWorkOrderNumberValue();
		teamwoscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		notesscreen = vehiclescreen.clickNotesButton();
		Assert.assertEquals(notesscreen.getNotesValue(), inspectionNotes);
		notesscreen.clickSaveButton();
		Assert.assertEquals(vehiclescreen.getEst(), inspnumber);
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
			notesscreen = servicedetailsscreen.clickNotesCell();
			Assert.assertEquals(notesscreen.getNotesValue(), serviceNotes);
			notesscreen.clickSaveButton();
			servicedetailsscreen.saveSelectedServiceDetails();
			Assert.assertTrue(servicesscreen.isNotesIconPresentForSelectedWorkOrderService(serviceData.getServiceName()));
		}
		servicesscreen.cancelWizard();
		teamwoscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired(String rowID,
																												String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_FOR_CALC);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());

		servicesscreen.openServiceDetailsByIndex(iOSInternalProjectConstants.OKSI_SERVICE_PP_VEHICLE, 0);
		SelectedServiceDetailsScreen servicedetailsscreen = new SelectedServiceDetailsScreen();
		Assert.assertTrue(servicedetailsscreen.isQuestionFormCellExists());
		Assert.assertEquals(servicedetailsscreen.getQuestion2Value(), inspectionData.getServiceData().getQuestionData().getQuestionAnswer());
		servicedetailsscreen.saveSelectedServiceDetails();

		for (int i = 1; i < inspectionData.getServiceData().getVehicleParts().size(); i++) {
			servicesscreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
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


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForTeamInvoices(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		TeamInvoicesScreen teaminvoicesscreen = homescreen.clickTeamInvoices();
		final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
		teaminvoicesscreen.selectInvoice(invoicenum);
		teaminvoicesscreen.clickChangePOPopup();
		teaminvoicesscreen.changePO(invoiceData.getPoNumber());
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY);
		teaminvoicesscreen.clickCancelButton();
		teaminvoicesscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForMyInvoices(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InvoiceData invoiceData = testCaseData.getInvoiceData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

		MyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		final String invoicenum = myinvoicesscreen.getFirstInvoiceValue();
		myinvoicesscreen.selectInvoice(invoicenum);
		myinvoicesscreen.clickChangePOPopup();
		myinvoicesscreen.changePO(invoiceData.getPoNumber());
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY);
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatTechSplitsIsSavedInPriceMatrices(String rowID,
																 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);;
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		PriceMatrixScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		PriceMatrixScreenSteps.setVehiclePartPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
		PriceMatrixScreenSteps.verifyVehiclePartTechnicianValue(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
		PriceMatrixScreenSteps.selectVehiclePartTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());

		List<ServiceTechnician> serviceTechnicians = new ArrayList<>();
		serviceTechnicians.add(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
		serviceTechnicians.add(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());
		PriceMatrixScreenSteps.verifyVehiclePartTechniciansValue(serviceTechnicians);
		PriceMatrixScreenSteps.savePriceMatrixData();

		servicesscreen = new ServicesScreen();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		TechRevenueScreen techrevenuescreen = myworkordersscreen.selectWorkOrderTechRevenueMenuItem(wonumber);
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName()));
		techrevenuescreen.clickBackButton();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatItIsNotPossibleToChangeDefaultTechViaServiceTypeSplit(String rowID,
																					  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
		ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		techniciansPopup.selecTechnician(workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesscreen.clickTechnicianToolbarIcon();
		servicesscreen.changeTechnician("Dent", workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());

		selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		techniciansPopup.cancelSearchTechnician();
		selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
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


		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(servicesscreen.checkServiceIsSelected(serviceData.getServiceName()));

		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.copyServicesForWorkOrder(wonumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());
		Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatWONumberIsNotDuplicated(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		//customer approval ON
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		final String wonumber1 = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber1);
		myworkordersscreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
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
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		final String wonumber2 = vehiclescreen.getInspectionNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.approveWorkOrderWithoutSignature(wonumber2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

		myworkordersscreen.clickCreateInvoiceIconForWO(wonumber2);
		myworkordersscreen.clickInvoiceIcon();
		myworkordersscreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
		invoiceinfoscreen.setPO(testCaseData.getInvoiceData().getPoNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
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
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		final String wonumber3 = vehiclescreen.getInspectionNumber();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);

		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatInCaseValidVINIsDecodedReplaceExistingMakeAndModelWithNewOne(String rowID,
																							 String description, JSONObject testData) {

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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatWhenServiceDoNotHaveQuestionsAndSelectSeveralPanelsDoNotUnderlineAnyone(String rowID,
																										String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		ServiceDetailsScreenSteps.slectServiceVehicleParts(workOrderData.getServiceData().getVehicleParts());

		for (VehiclePartData vehiclePartData : workOrderData.getServiceData().getVehicleParts()) {
			SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
			Assert.assertTrue(selectedservicescreen.getVehiclePartValue().contains(vehiclePartData.getVehiclePartName()));
		}
		ServiceDetailsScreenSteps.saveServiceDetails();
		Assert.assertEquals(servicesscreen.getNumberOfServiceSelectedItems(workOrderData.getServiceData().getServiceName()), workOrderData.getServiceData().getVehicleParts().size());
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
																												 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER, WorkOrdersTypes.WO_VIN_ONLY);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.clickVINField();
		Assert.assertTrue(vehiclescreen.getVINField().isDisplayed());
		vehiclescreen.hideKeyboard();
		vehiclescreen.cancelOrder();
		myworkordersscreen = new MyWorkOrdersScreen();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatMessageIsShownForMoneyAndLaborServiceWhenPriceIsChangedTo0UnderWO(String rowID,
																								  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String zeroPrice = "0";

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_SMOKE_MONITOR);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
			if (serviceData.getServicePrice().equals(zeroPrice)) {
				ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
				if (serviceData.getServicePrice().equals(zeroPrice))
					Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
				selectedservicescreen.clickTechniciansIcon();
				Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
			} else {
				selectedservicescreen.clickTechniciansIcon();
				Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
				selectedservicescreen.cancelSelectedServiceDetails();
				selectedservicescreen.setServiceRateValue(serviceData.getServicePrice());
				selectedservicescreen.clickTechniciansIcon();
			}
			TechniciansPopup techniciansPopup = selectedservicescreen.clickTechniciansIcon();
			techniciansPopup.searchTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFirstName());
			techniciansPopup.selecTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			techniciansPopup.searchTechnician(serviceData.getServiceNewTechnician().getTechnicianFirstName());
			techniciansPopup.selecTechnician(serviceData.getServiceNewTechnician().getTechnicianFullName());
			techniciansPopup.clearSerchTechnician();
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceNewTechnician().getTechnicianFullName()));
			techniciansPopup.saveTechViewDetails();
			selectedservicescreen.saveSelectedServiceDetails();
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_VEHICLE_TRIM_VALIDATION);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.clickSave();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TRIM_REQUIRED);
		vehiclescreen.setTrim(trimvalue);
		Assert.assertEquals(vehiclescreen.getTrim(), trimvalue);
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		vehiclescreen.saveWizard();
		homescreen =  myworkordersscreen.clickHomeButton();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatTotalSaleIsNotShownWhenCheckmarkTotalSaleRequiredIsNotSetToOFF(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TOTAL_SALE_NOT_REQUIRED);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);;
		ServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatItIsPossibleToAssignTechToOrderByActionTechnicians(String rowID,
																				   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			ServicesScreenSteps.selectService(serviceData.getServiceName());

		ServicesScreenSteps.selectBundleService(workOrderData.getBundleService());
		for (ServiceData serviceData : workOrderData.getMoneyServices())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		PriceMatrixScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		PriceMatrixScreenSteps.setVehiclePartPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
		PriceMatrixScreenSteps.verifyVehiclePartTechnicianValue(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
		PriceMatrixScreenSteps.selectVehiclepartAdditionalService(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService());
		PriceMatrixScreenSteps.savePriceMatrixData();

		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		TechniciansPopup techniciansPopup = myworkordersscreen.selectWorkOrderTechniciansMenuItem(wonumber);
		techniciansPopup.selecTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName());
		techniciansPopup.saveTechViewDetails();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		Assert.assertEquals(vehiclescreen.getTechnician(), workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName() +
				", " + workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName());
		vehiclescreen.cancelOrder();
		myworkordersscreen = new MyWorkOrdersScreen();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testInspectionsVerifyThatWhenPanelGroupingIsUsedForPackageForSelectedPanelOnlyLinkedServicesAreShown(String rowID,
																													 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
		MyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		VehicleScreen vehiclescreen = myinspectionsscreen.addInspection(InspectionsTypes.INSP_SERVICE_TYPE_WITH_OUT_REQUIRED);
		vehiclescreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (DamageData damageData : inspectionData.getDamagesData()) {
			servicesscreen.selectGroupServiceItem(damageData.getDamageGroupName());
			for (ServiceData serviceData : damageData.getMoneyServices())
				Assert.assertTrue(servicesscreen.isServiceTypeExists(serviceData.getServiceName()));
			servicesscreen.clickServiceTypesButton();
		}
		servicesscreen.cancelWizard();
		myinspectionsscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatWOIsSavedCorrectWithSelectedSubService_NoMessageWithIncorrectTechSplit(String rowID,
																									   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices()) {
			servicesscreen.selectService(workOrderData.getDamageData().getDamageGroupName());
			servicesscreen.selectServiceSubSrvice(workOrderData.getDamageData().getDamageGroupName(), serviceData.getServiceName());
		}
		for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices())
			Assert.assertTrue(servicesscreen.isServiceWithSubSrviceSelected(workOrderData.getDamageData().getDamageGroupName(), serviceData.getServiceName()));
		servicesscreen.saveWizard();
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

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_PANEL_GROUP);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		QuestionsScreen questionsscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.QUESTIONS, "Zayats Section2");
		questionsscreen.selectAnswerForQuestionWithAdditionalConditions(questionName, questionAswer, questionAswerSecond, questionVehiclePart);
		ServicesScreen servicesscreen = questionsscreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));

		servicesscreen.cancelWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatSearchBarIsPresentForServicePackScreen(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getVehiclePart() != null)
				ServicesScreenSteps.selectServiceWithServiceData(serviceData);
			else
				ServicesScreenSteps.selectService(serviceData.getServiceName());
		}
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(servicesscreen.checkServiceIsSelected(serviceData.getServiceName()));
		servicesscreen.cancelWizard();
		homescreen = myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatCancelMessageIsShownForNewOrExistingWO(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.clickCancelButton();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.STOP_WORKORDER_CREATION);
		vehiclescreen.clickCancelButton();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.STOP_WORKORDER_CREATION);

		String wonumber = myworkordersscreen.getFirstWorkOrderNumberValue();
		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		vehiclescreen.clickCancelButton();
		Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.STOP_WORKORDER_EDIT);
		vehiclescreen.clickCancelButton();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.STOP_WORKORDER_EDIT);
		myworkordersscreen.openWorkOrderDetails(wonumber);
		vehiclescreen = new VehicleScreen();
		vehiclescreen.clickCancelButton();

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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_ACCEPT_SERVICEREQUEST);
		servicerequestsscreen = new ServiceRequestsScreen();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestOnHold(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertFalse(servicerequestsscreen.isAcceptActionExists());
		Assert.assertFalse(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_DECLINE_SERVICEREQUEST);
		servicerequestsscreen.clickDoneCloseReasonDialog();
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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
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


		homescreen = new HomeScreen();

		CustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

		ServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		Assert.assertTrue(servicerequestsscreen.isServiceRequestProposed(srnumber));
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectEditServiceRequestAction();
		VehicleScreen vehiclescreen = new VehicleScreen();
		vehiclescreen.setTech(serviceRequestData.getVihicleInfo().getDefaultTechnician().getTechnicianFullName());
		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
		vehiclescreen.saveWizard();
		servicerequestsscreen.selectServiceRequest(srnumber);
		Assert.assertTrue(servicerequestsscreen.isAcceptActionExists());
		Assert.assertTrue(servicerequestsscreen.isDeclineActionExists());
		servicerequestsscreen.selectServiceRequest(srnumber);

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
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
		servicerequestslistpage.clickAddServiceRequestButton();

		servicerequestslistpage.clickGeneralInfoEditButton();
		servicerequestslistpage.setServiceRequestGeneralInfoAssignedTo(technicianValue);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
				serviceRequestData.getVihicleInfo().getVehicleModel());
		servicerequestslistpage.clickDoneButton();

		servicerequestslistpage.saveNewServiceRequest();
		final String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		Assert.assertTrue(servicerequestslistpage.addAppointmentFromSRlist(startDate, endDate, technicianValue));
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatDefaultTechIsNotChangedWhenResetOrderSplit(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		for (ServiceData serviceData : workOrderData.getMoneyServices()) {
			ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getServiceDefaultTechnician() != null) {
				ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				TechniciansPopup techniciansPopup = new TechniciansPopup();
				Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
				techniciansPopup.saveTechViewDetails();
			}
			ServiceDetailsScreenSteps.saveServiceDetails();
		}

		vehiclescreen = vehiclescreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianFullName());
		techniciansPopup.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechViewDetails();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getMoneyServices()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice2() != null)
				ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice2());
			if (serviceData.getServiceDefaultTechnician() != null) {
				ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				techniciansPopup = new TechniciansPopup();
				Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
				techniciansPopup.saveTechViewDetails();
			} else {
				ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				techniciansPopup = new TechniciansPopup();
				Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianFullName()));
				techniciansPopup.saveTechViewDetails();
			}
			ServiceDetailsScreenSteps.saveServiceDetails();
		}
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyAssignTechToServiceTypeInsteadOfIndividualServices(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();


		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		VehicleScreen vehiclescren =  myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_GROUP_SERVICE_TYPE);
		vehiclescren.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		ServicesScreen servicesscreen = vehiclescren.selectNextScreen(WizardScreenTypes.SERVICES);
		servicesscreen.clickTechnicianToolbarIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.NO_SELECTED_SERVICES);
		for (DamageData damageData : workOrderData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			servicesscreen.clickServiceTypesButton();
		}

		servicesscreen = new ServicesScreen();
		servicesscreen.clickTechnicianToolbarIcon();
		ServiceTypesScreen serviceTypesScreen = new ServiceTypesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(serviceData.getServiceName()));

		DamageData moneyServicePanel = workOrderData.getDamagesData().get(0);

		TechniciansPopup techniciansPopup = serviceTypesScreen.clickOnPanel(moneyServicePanel.getDamageGroupName());
		techniciansPopup.selecTechnician(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechViewDetails();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.selectGroupServiceItem(moneyServicePanel.getDamageGroupName());
		for (ServiceData serviceData : moneyServicePanel.getMoneyServices()) {
			SelectedServiceDetailsScreen selectedservicedetailscreen = servicesscreen.openCustomServiceDetails(serviceData.getServiceName());
			techniciansPopup = selectedservicedetailscreen.clickTechniciansIcon();
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
			techniciansPopup.cancelSearchTechnician();
			selectedservicedetailscreen.cancelSelectedServiceDetails();
		}
		servicesscreen.clickServiceTypesButton();

		DamageData bundleServicePanel = workOrderData.getDamagesData().get(1);
		servicesscreen.clickTechnicianToolbarIcon();
		techniciansPopup = serviceTypesScreen.clickOnPanel(bundleServicePanel.getDamageGroupName());
		techniciansPopup.selecTechnician(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechViewDetails();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.openServiceDetails(bundleServicePanel.getBundleService().getBundleServiceName());
		for (ServiceData serviceData : bundleServicePanel.getBundleService().getServices()) {
			SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
			selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
			ServiceDetailsScreenSteps.clickServiceTechniciansCell();
			techniciansPopup = new TechniciansPopup();
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
			techniciansPopup.cancelSearchTechnician();
			ServiceDetailsScreenSteps.cancelServiceDetails();
			selectedservicebundlescreen.clickCancelBundlePopupButton();
		}

		servicesscreen.clickTechnicianToolbarIcon();
		techniciansPopup = serviceTypesScreen.clickOnPanel(workOrderData.getDamageData().getDamageGroupName());
		techniciansPopup.selecTechnician(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName());
		techniciansPopup.selecTechnician(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName());
		techniciansPopup.saveTechViewDetails();
		serviceTypesScreen.clickSaveButton();

		servicesscreen.openServiceDetails(workOrderData.getDamageData().getBundleService().getBundleServiceName());
		SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
		selectedservicebundlescreen.openBundleInfo(workOrderData.getDamageData().getBundleService().getService().getServiceName());
		ServiceDetailsScreenSteps.clickServiceTechniciansCell();

		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName()));
		techniciansPopup.cancelSearchTechnician();
		ServiceDetailsScreenSteps.saveServiceDetails();
		selectedservicebundlescreen.clickCancelBundlePopupButton();

		servicesscreen.selectGroupServiceItem(moneyServicePanel.getDamageGroupName());
		ServicesScreenSteps.openCustomServiceDetails(moneyServicePanel.getMoneyService().getServiceName());
		ServiceDetailsScreenSteps.slectServiceVehiclePart(moneyServicePanel.getMoneyService().getVehiclePart());
		ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
		techniciansPopup.cancelSearchTechnician();
		ServiceDetailsScreenSteps.saveServiceDetails();

		QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

		OrderSummaryScreen ordersummaryscreen = vehiclescren.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatItIsPossibleToAssignTechWhenWOIsNotStarted(String rowID,
																		   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String defaultLocationValue = "Test Location ZZZ";

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(defaultLocationValue);
		final String wonumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getMoneyServices())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation(defaultLocationValue);
		teamworkordersscreen.clickSearchSaveButton();

		teamworkordersscreen.clickOnWO(wonumber);
		OrderMonitorScreen orderMonitorScreen = teamworkordersscreen.selectWOMonitor();
		OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
		MonitorServiceData firstMonitorServiceData = orderMonitorData.getMonitorServicesData().get(0);
		Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
		OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
		TechniciansPopup techniciansPopup = serviceDetailsPopup.clickTech();
		techniciansPopup.selecTechnician(firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechViewDetails();
		orderMonitorScreen = new OrderMonitorScreen();
		orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
		Assert.assertEquals(serviceDetailsPopup.getTechnicianValue(), firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
		serviceDetailsPopup.clickServiceDetailsDoneButton();

		orderMonitorScreen = new OrderMonitorScreen();
		orderMonitorScreen.clickStartOrderButton();
		Assert.assertTrue(Helpers.getAlertTextAndAccept().contains(AlertsCaptions.WOULD_YOU_LIKE_TO_START_REPAIR_ORDER));
		orderMonitorScreen = new OrderMonitorScreen();

		MonitorServiceData secondMonitorServiceData = orderMonitorData.getMonitorServicesData().get(1);
		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		serviceDetailsPopup.clickTech();
		techniciansPopup.selecTechnician(secondMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechViewDetails();
		orderMonitorScreen = new OrderMonitorScreen();
		serviceDetailsPopup = orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		serviceDetailsPopup.clickStartService();
		serviceDetailsPopup = orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		serviceDetailsPopup.setCompletedServiceStatus();
		Assert.assertEquals(orderMonitorScreen.getPanelStatus(secondMonitorServiceData.getMonitorService()), secondMonitorServiceData.getMonitorServiceStatus());
		orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
		serviceDetailsPopup.clickTech();
		serviceDetailsPopup.clickServiceDetailsDoneButton();

		TeamWorkOrdersScreen teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
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

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(defaultLocationValue);
		final String wonumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getMoneyServices())
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);

		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();
		myworkordersscreen.clickHomeButton();

		TeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickSearchButton();
		teamworkordersscreen.selectSearchLocation(defaultLocationValue);
		teamworkordersscreen.clickSearchSaveButton();

		teamworkordersscreen.clickOnWO(wonumber);
		OrderMonitorScreen orderMonitorScreen = teamworkordersscreen.selectWOMonitor();
		Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
		orderMonitorScreen.changeStatusForWorkOrder(workOrderMonitorStatus, statusReason);

		OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
		serviceDetailsPopup.clickTech();
		serviceDetailsPopup.clickServiceDetailsCancelButton();

		TeamWorkOrdersScreen teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
		teamWorkOrdersScreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatTechSplitAssignedFormVehicleScreenIsSetToServicesUnderList(String rowID,
																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

		VehicleScreen vehiclescreen = myworkordersscreen.addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		}

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
			techniciansPopup.selecTechnician(technician.getTechnicianFullName());
		techniciansPopup.saveTechViewDetails();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

		vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
			techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
			for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
				Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(technician.getTechnicianFullName()));
			techniciansPopup.saveTechViewDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen = new ServicesScreen();
		}

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyPriceMatrixItemDoesntHaveAdditionalServicesItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
																																 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(techniciansPopup.getTechnicianPercentage(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName()),
				workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianPercentageValue());
		techniciansPopup.cancelTechViewDetails();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice()));

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyPriceMatrixItemHasMoneyAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
																															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();

		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
		PriceMatrixScreenSteps.selectVehiclePart(vehiclePartData);
		PriceMatrixScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		TechniciansPopup techniciansPopup = priceMatrixScreen.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
		Assert.assertFalse(techniciansPopup.isCustomTabSelected());
		techniciansPopup.cancelTechViewDetails();

		priceMatrixScreen.selectDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		Assert.assertEquals(priceMatrixScreen.getPriceMatrixTotalPriceValue(), vehiclePartData.getVehiclePartTotalPrice());
		techniciansPopup = priceMatrixScreen.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPercentageValue());
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
		Assert.assertFalse(techniciansPopup.isCustomTabSelected());
		techniciansPopup.cancelTechViewDetails();

		priceMatrixScreen.clickSave();
		servicesscreen = new ServicesScreen();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyPriceMatrixItemHasPercentageAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmountPlusAdditionalPercentage(String rowID,
																																						   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_SMOKE_TEST);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();

		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
		PriceMatrixScreenSteps.selectVehiclePart(vehiclePartData);
		PriceMatrixScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		TechniciansPopup techniciansPopup = priceMatrixScreen.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
		Assert.assertFalse(techniciansPopup.isCustomTabSelected());
		techniciansPopup.cancelTechViewDetails();

		priceMatrixScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		SelectedServiceDetailsScreen selectedservicescreen = new SelectedServiceDetailsScreen();
		selectedservicescreen.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
		selectedservicescreen.saveSelectedServiceDetails();
		Assert.assertEquals(priceMatrixScreen.getPriceMatrixTotalPriceValue(), vehiclePartData.getVehiclePartTotalPrice());
		techniciansPopup = priceMatrixScreen.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPercentageValue());
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
				vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
		Assert.assertFalse(techniciansPopup.isCustomTabSelected());
		techniciansPopup.cancelTechViewDetails();

		priceMatrixScreen.clickSave();
		servicesscreen = new ServicesScreen();
		Assert.assertEquals(servicesscreen.getTotalAmaunt(),  workOrderData.getWorkOrderPrice());

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyIfServiceHasDefaultTechnicianAndItsAmountIs0ThenDefaultTechnicianShouldBeAssignedToTheService_NotTechnicianSplitAtWorkOrderLevel(String rowID,
																																							 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();

		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		ServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		ServiceDetailsScreenSteps.saveServiceDetails();

		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechnociansViewWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		SelectedServiceDetailsScreen serviceDetailsScreen = servicesscreen.openServiceDetails(workOrderData.getServiceData().getServiceName());
		techniciansPopup = serviceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName());
		techniciansPopup.clickCancelButton();
		serviceDetailsScreen.clickCancelSelectedServiceDetails();
		servicesscreen = new ServicesScreen();

		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatCorrectTechSplitAmountIsShownForMatrixServiceWhenChangePriceTo0(String rowID,
																								String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();
		final String serviceZeroPrice = "0";

		homescreen = new HomeScreen();
		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();

		OrderSummaryScreen ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();


		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
		PriceMatrixScreenSteps.selectVehiclePart(vehiclePartData);
		PriceMatrixScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
		priceMatrixScreen.setPrice(serviceZeroPrice);

		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_DEFAULT_TECH_SPLIT_WILL_BE_ASSIGNED_IF_SET_ZERO_AMAUNT);
		priceMatrixScreen.clickOnTechnicians();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_VEHICLE_PART);
		TechniciansPopup techniciansPopup = new TechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()), PricesCalculations.getPriceRepresentation(serviceZeroPrice));
		techniciansPopup.saveTechViewDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.clickSaveButton();

		servicesscreen = new ServicesScreen();
		servicesscreen.cancelWizard();
		myworkordersscreen.clickHomeButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testWOVerifyThatTechSplitAmountIsShownCorrectUnderMonitorForServiceWithAdjustments(String rowID,
																								   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		ServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		ServiceDetailsScreenSteps.selectServiceAdjustment(workOrderData.getServiceData().getServiceAdjustment());
		ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		ServiceDetailsScreenSteps.saveServiceDetails();

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		TeamWorkOrdersScreen teamWorkOrdersScreen = homescreen.clickTeamWorkordersButton();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation("All locations");
		teamWorkOrdersScreen.setSearchWorkOrderNumber(wonumber);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonumber);
		OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
		Assert.assertEquals(orderMonitorScreen.getMonitorServiceAmauntValue(workOrderData.getServiceData()), workOrderData.getServiceData().getServicePrice2());
		OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getServiceData());
		Assert.assertEquals(serviceDetailsPopup.getServiceDetailsPriceValue(),
				BackOfficeUtils.getFormattedServicePriceValue(BackOfficeUtils.getServicePriceValue(workOrderData.getServiceData().getServicePrice())));
		TechniciansPopup techniciansPopup = serviceDetailsPopup.clickTech();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
				workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPriceValue());
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
				workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPercentageValue());

		techniciansPopup.cancelTechViewDetails();
		serviceDetailsPopup.clickServiceDetailsDoneButton();
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

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_MONITOR_REQUIRED_SERVICES_ALL);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		SelectedServiceBundleScreen serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}
		serviceBundleScreen.clickCancelBundlePopupButton();
		servicesscreen = new ServicesScreen();
		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			techniciansPopup.selecTechnician(serviceTechnician.getTechnicianFullName());
		techniciansPopup.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechnociansViewWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			String techString =  "";
			for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
				techString = techString + ", " + serviceTechnician.getTechnicianFullName();
			techString = techString.replaceFirst(",", "").trim();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
			selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}
		serviceBundleScreen.clickCancelBundlePopupButton();
		servicesscreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openServiceDetails(workOrderData.getServiceData().getServiceName());
		ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		ServiceDetailsScreenSteps.saveServiceDetails();
		servicesscreen.clickOnSelectService(workOrderData.getMatrixServiceData().getMatrixServiceName());
		servicesscreen.selectServicePriceMatrices(workOrderData.getMatrixServiceData().getHailMatrixName());
		PriceMatrixScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		PriceMatrixScreenSteps.savePriceMatrixData();
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

		for (ServiceData serviceData : bundleServiceData.getServices()) {
			selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			String techString =  "";
			for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
				techString = techString + ", " + serviceTechnician.getTechnicianFullName();
			techString = techString.replaceFirst(",", "").trim();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRequiredBundleItemsHasCorrectDefTech(String rowID,
																   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		SelectedServiceBundleScreen serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}
		serviceBundleScreen.clickCancelBundlePopupButton();

		servicesscreen = new ServicesScreen();
		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			techniciansPopup.selecTechnician(serviceTechnician.getTechnicianFullName());
		techniciansPopup.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechnociansViewWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
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
				ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());

			ServiceDetailsScreenSteps.saveServiceDetails();
		}

		serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		serviceBundleScreen.saveSelectedServiceDetails();

		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
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
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}
		serviceBundleScreen.clickCancelBundlePopupButton();
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatRequiredBundleItemsAndServiceWithExpensesAnd0PriceHasCorrectDefTechAfterEditWO(String rowID,
																											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		BundleServiceData bundleServiceData = workOrderData.getBundleService();
		SelectedServiceBundleScreen serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
			Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}

		serviceBundleScreen.clickCancelBundlePopupButton();

		servicesscreen = new ServicesScreen();
		vehiclescreen = servicesscreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			techniciansPopup.selecTechnician(serviceTechnician.getTechnicianFullName());
		techniciansPopup.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechnociansViewWithAlert();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);

		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
		for (ServiceData serviceData : bundleServiceData.getServices()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
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
				ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());

			ServiceDetailsScreenSteps.saveServiceDetails();
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}

		serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
		serviceBundleScreen.saveSelectedServiceDetails();

		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesscreen.openServiceDetails(workOrderData.getServiceData().getServiceName());
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceTechnician.getTechnicianFullName()));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

		myworkordersscreen.selectWorkOrderForEidt(wonumber);
		vehiclescreen = new VehicleScreen();
		servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		serviceBundleScreen = servicesscreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
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
			serviceBundleScreen.waitUntilBundlePopupOpened();
		}

		serviceBundleScreen.clickCancelBundlePopupButton();

		servicesscreen.openServiceDetails(workOrderData.getServiceData().getServiceName());
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceTechnician.getTechnicianFullName()));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		servicesscreen.saveWizard();

		myworkordersscreen.clickHomeButton();
		homescreen.clickLogoutButton();
		mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatSelectedServicesHaveCorrectTechSplitWhenChangeIsDuringCreatingWO(String rowID,
																							   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_ALL_SERVICES);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		final String wonumber = vehiclescreen.getInspectionNumber();
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			techniciansPopup.selecTechnician(serviceTechnician.getTechnicianFullName());
		techniciansPopup.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechnociansViewWithAlert();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList())
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openServiceDetails(serviceData.getServiceName());
			selectedServiceDetailsScreen.clickTechniciansIcon();
			for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
				Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceTechnician.getTechnicianFullName()));
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
			selectedServiceDetailsScreen.cancelSelectedServiceDetails();
		}

		ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
		PriceMatrixScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
		PriceMatrixScreenSteps.verifyVehiclePartTechniciansValue(workOrderData.getVehicleInfoData().getNewTechnicians());
		PriceMatrixScreen pricematrix = new PriceMatrixScreen();
		pricematrix.clickDiscaunt(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceTechnician.getTechnicianFullName()));
		techniciansPopup.cancelTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		pricematrix.setTime(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTime());
		pricematrix.clickSaveButton();
		servicesscreen = new ServicesScreen();

		servicesscreen.openCustomServiceDetails(workOrderData.getBundleService().getBundleServiceName());
		SelectedServiceBundleScreen serviceBundleScreen = new SelectedServiceBundleScreen();
		techniciansPopup =serviceBundleScreen.clickTechniciansIcon();
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceTechnician.getTechnicianFullName()));
		techniciansPopup.clickCancelButton();
		selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(workOrderData.getBundleService().getService().getServiceName());
		String techString = "";
		for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
			techString = techString + ", " + serviceTechnician.getTechnicianFullName();
		techString = techString.replaceFirst(",", "").trim();
		Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
		selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
		serviceBundleScreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
		serviceBundleScreen.saveSelectedServiceDetails();
		servicesscreen = new ServicesScreen();
		Assert.assertTrue(servicesscreen.checkServiceIsSelected(workOrderData.getBundleService().getBundleServiceName()));
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyIfServicePriceIs0AndHasDefTechAssignToServiceDefTech(String rowID,
																			   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		homescreen = new HomeScreen();
		MainScreen mainScreen = homescreen.clickLogoutButton();
		mainScreen.userLogin("Zayats", "1111");

		MyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		VehicleScreen vehiclescreen = ((MyWorkOrdersScreen) myworkordersscreen).addOrderWithSelectCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER,
				WorkOrdersTypes.WO_TYPE_FOR_CALC);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		TechniciansPopup techniciansPopup = vehiclescreen.clickTech();
		techniciansPopup.selecTechnician(workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianFullName());
		techniciansPopup.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
		techniciansPopup.saveTechnociansViewWithAlert();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		ServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
		ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
		ServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
		ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
		Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
				workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPriceValue());
		techniciansPopup.cancelTechViewDetails();
		ServiceDetailsScreenSteps.cancelServiceDetails();

		servicesscreen = new ServicesScreen();
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
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		servicesscreen = new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();

		TeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatWhenOptionPhaseEnforcementIsOFFAndStartServiceRequiredItIsPossibleToStartServiceFromInactivePhase(String rowID,
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
			ServicesScreenSteps.selectService(serviceData.getServiceName());
		ServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
		servicesscreen= new ServicesScreen();
		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.checkApproveAndSaveWorkOrder();
		SelectEmployeePopup selectemployeepopup = new SelectEmployeePopup();
		selectemployeepopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
		ordersummaryscreen.saveWizard();

		TeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);
		OrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		OrderMonitorServiceDetailsPopup serviceDetailsPopup = ordermonitorscreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
		Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
		serviceDetailsPopup.clickServiceDetailsDoneButton();

		Assert.assertTrue(ordermonitorscreen.isStartPhaseButtonPresent());
		ordermonitorscreen.clickStartPhase();
		Assert.assertFalse(ordermonitorscreen.isStartPhaseButtonPresent());
		ordermonitorscreen.clickBackButton();
		homescreen = teamWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatTechWhoIsNotAssignedToOrderServiceCannotStartOrder(String rowID,
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
		VehicleScreen vehiclescreen = myworkordersscreen.addWorkOrder(WorkOrdersTypes.WO_DELAY_START);
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null)
				ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
			ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
			TechniciansPopup techniciansPopup = new TechniciansPopup();
			techniciansPopup.selecTechnician(serviceData.getServiceNewTechnician().getTechnicianFullName());
			techniciansPopup.unselecTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			techniciansPopup.saveTechViewDetails();
			ServiceDetailsScreenSteps.saveServiceDetails();
		}

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		TeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyThatIsItImpossibleToChangeStatusForServiceOrPhaseWhenOrderIsNotStarted(String rowID,
																								 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

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
		vehiclescreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		vehiclescreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
		String wonum = vehiclescreen.getInspectionNumber();

		ServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			if (serviceData.getServicePrice() != null)
				ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
			if (serviceData.getVehiclePart() != null)
				ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
			ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
			TechniciansPopup techniciansPopup = new TechniciansPopup();
			techniciansPopup.selecTechnician(serviceData.getServiceNewTechnician().getTechnicianFullName());
			techniciansPopup.unselecTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
			ServiceDetailsScreenSteps.saveServiceDetails();
			ServiceDetailsScreenSteps.saveServiceDetails();
		}

		OrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
		ordersummaryscreen.saveWizard();

		TeamWorkOrdersScreen teamWorkOrdersScreen = myworkordersscreen.switchToTeamWorkOrdersView();
		teamWorkOrdersScreen.clickSearchButton();
		teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
		teamWorkOrdersScreen.setSearchWorkOrderNumber(wonum);
		teamWorkOrdersScreen.clickSearchSaveButton();
		teamWorkOrdersScreen.clickOnWO(wonum);

		OrderMonitorScreen ordermonitorscreen = teamWorkOrdersScreen.selectWOMonitor();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertEquals(ordermonitorscreen.getPanelStatus(serviceData), OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());

		OrderMonitorServiceDetailsPopup serviceDetailsPopup = ordermonitorscreen.selectPanel(workOrderData.getServicesList().get(workOrderData.getServicesList().size()-1));
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
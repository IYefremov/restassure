package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LicensesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NewTestCases extends BaseTestCase {

	private String regCode;
	private RegularHomeScreen homescreen;
	private String userLogin = "User";
	private String userPassword = "1111";

	@BeforeClass
	@Parameters({ "backoffice.url", "user.name", "user.psw", "license.name" })
	public void setUpSuite(String backofficeurl, String userName, String userPassword, String licensename) {
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(userLogin, userPassword);
		testGetDeviceRegistrationCode(backofficeurl, userName, userPassword, licensename);
		testRegisterationiOSDdevice();
	}
	
	public void testGetDeviceRegistrationCode(String backofficeurl,
			String userName, String userPassword, String licensename) {

		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(userName, userPassword);

		ActiveDevicesWebPage devicespage = PageFactory.initElements(webdriver,
				ActiveDevicesWebPage.class);

		devicespage.setSearchCriteriaByName(licensename);
		regCode = devicespage.getFirstRegCodeInTable();
		DriverBuilder.getInstance().getDriver().quit();
	}

	public void testRegisterationiOSDdevice()  {
		AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
		DriverBuilder.getInstance().getAppiumDriver().removeApp(IOSRegularDeviceInfo.getInstance().getDeviceBundleId());
		DriverBuilder.getInstance().getAppiumDriver().quit();
		AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
		LoginScreen loginscreen = new LoginScreen();
		loginscreen.registeriOSDevice(regCode);
		RegularMainScreen mainscr = new RegularMainScreen();
		homescreen = mainscr.userLogin(userLogin, userPassword);
	}

	@Test(testName = "Test Case 59643:iOS: Invoices - Send Multiple Emails", description = "Invoices - Send Multiple Emails")
	public void testInvoicesSendMultipleEmails() {
			
		final int numberInvoicesToSelect = 4;
		final String mailaddress = "test@cyberiansoft.com";
			
		homescreen = new RegularHomeScreen();
			
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		myinvoicesscreen.clickActionButton();
		myinvoicesscreen.selectInvoices(numberInvoicesToSelect);
		myinvoicesscreen.clickActionButton();
		myinvoicesscreen.sendSingleEmail(mailaddress);
		myinvoicesscreen.clickDoneButton();
		myinvoicesscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 59645:Create Invoice with two WOs and copy vehicle", description = "Create Invoice with two WOs and copy vehicle")
	public void testCreateInvoiceWithTwoWOsAndCopyVehicle(){
		
		final String VIN = "QWERTYUI123";
		final String _make = "Buick";
		final String _model = "Electra";
		final String _year = "2014";
		final String _color = "Black";
		final String mileage = "77777";
		final String fueltanklevel = "25";
		final String _type = "Used";	
		final String stock = "Stock1";
		final String _ro = "123";
		final String[] vehicleparts = { "Cowl, Other", "Hood", };
		
		final String poNomber = "23";
		
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
		
		String wonumber1 = vehiclescreen.getInspectionNumber();
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		vehiclescreen.setYear(_year);
		vehiclescreen.setMileage(mileage);
		//vehiclescreen.setFuelTankLevel(fueltanklevel);
		//vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Dye_Panel");
		selectedservicescreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedservicescreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedservicescreen.saveSelectedServiceDetails();
		selectedservicescreen.saveSelectedServiceDetails();

		RegularOrderSummaryScreen ordersummaryscreen = servicesscreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen.saveWizard();
		myworkordersscreen.selectWorkOrderForApprove(wonumber1);
		myworkordersscreen.clickApproveButton();
		
		//myworkordersscreen.searchWO(wonumber1);
		myworkordersscreen.selectWorkOrder(wonumber1);
		myworkordersscreen.selectCopyVehicle();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		myworkordersscreen.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehiclescreen.getMake(), _make);
		Assert.assertEquals(vehiclescreen.getModel(), _model);
		Assert.assertEquals(vehiclescreen.getYear(), _year);
		ordersummaryscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
		ordersummaryscreen = new RegularOrderSummaryScreen();
		ordersummaryscreen.checkApproveAndCreateInvoice();
		ordersummaryscreen.clickSave();
		
		ordersummaryscreen.selectDefaultInvoiceType();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		RegularInvoiceInfoScreen invoiceinfoscreen =questionsscreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceinfoscreen.setPO(poNomber);
		invoiceinfoscreen.addWorkOrder(wonumber1);
		invoiceinfoscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		Helpers.drawRegularQuestionsSignature();
		questionsscreen.clickSave();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Engine Condition' in section 'Test Section' should be answered."));
		questionsscreen.selectAnswerForQuestion("Engine Condition", "Pretty Good");
		invoiceinfoscreen = questionsscreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
		invoiceinfoscreen.clickSaveAsDraft();
		myworkordersscreen.clickHomeButton();
	}
	
	String srtowo = "";
	
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	@Test(testName = "Test Case 59646:Creating Service Request with Inspection, WO and Appointment required on device", description = "Creating Service Request with Inspection, WO and Appointment required on device")
	public void testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice(String backofficeurl, String userName, String userPassword) {
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
		final String _year = "2015";
		
		final String teamname= "Default team";

		homescreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		
		servicerequestsscreen.clickAddButton();
		customersscreen.selectCustomer(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectServiceRequestType(ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		vehiclescreen.setVIN(VIN);
		vehiclescreen.setMakeAndModel(_make, _model);
		vehiclescreen.setColor(_color);
		vehiclescreen.setYear(_year);
		vehiclescreen.setMileage(mileage);
		//vehiclescreen.setFuelTankLevel(fueltanklevel);
		//vehiclescreen.setType(_type);
		vehiclescreen.setStock(stock);
		vehiclescreen.setRO(_ro);
		//vehiclescreen.setLicensePlate(licplate);

		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServiceDetailsScreen servicedetailsscreen = servicesscreen.openCustomServiceDetails("3/4\" - Penny Size");
		servicedetailsscreen.setServiceQuantityValue("3");
		servicedetailsscreen.saveSelectedServiceDetails();
		servicesscreen.selectService("SR_Money_Vehicle");
		servicesscreen.selectService("SR_S4_Bundle");
		
		servicesscreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);

		srtowo = servicerequestsscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(servicerequestsscreen.getServiceRequestStatus(srtowo), "On Hold");
		Assert.assertTrue(servicerequestsscreen.getServiceRequestClient(srtowo).contains(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER));
		//Assert.assertTrue(servicerequestsscreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(servicerequestsscreen.getServiceRequestDetails(srtowo).contains("WERTYU123"));
		srtowo = servicerequestsscreen.getFirstServiceRequestNumber();
		servicerequestsscreen.clickHomeButton();
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(userName, userPassword);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		
		servicerequestslistpage.verifySearchFieldsAreVisible();
		
		servicerequestslistpage.selectSearchTeam(teamname);
		servicerequestslistpage.selectSearchTechnician("Test User");
		servicerequestslistpage.setSearchFreeText(srtowo);
		servicerequestslistpage.clickFindButton();
		servicerequestslistpage.verifySearchResultsByServiceName(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		servicerequestslistpage.selectFirstServiceRequestFromList();
		Assert.assertEquals(servicerequestslistpage.getVINValueForSelectedServiceRequest(), "WERTYU123");
		Assert.assertEquals(servicerequestslistpage.getCustomerValueForSelectedServiceRequest(), iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		Assert.assertEquals(servicerequestslistpage.getEmployeeValueForSelectedServiceRequest(), "Test User (Default team)");
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("SR_S4_Bundle $350.00 (1.00)"));
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("SR_Money_Vehicle $200.00 (1.00)"));
		Assert.assertTrue(servicerequestslistpage.isServiceIsPresentForForSelectedServiceRequest("3/4\" - Penny Size $18.00 (3.00)"));
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	
	@Test(testName = "Test Case 59642:iOS: Creating Inspection From Service Request", description = "Creating Inspection From Service Request")
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void testSRCreatingInspectionFromServiceRequest(String backofficeurl, String userName, String userPassword) {
			
		final String VIN = "2A4RR4DE2AR286008";
		String[] services = { "Dye_Panel", "PPV_service"};
		final String servicePrice = "1";
		final String serviceQuantity = "3";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(userName, userPassword);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		ServiceRequestsListWebPage servicerequestslistpage = operationspage.clickNewServiceRequestList();
		servicerequestslistpage.makeSearchPanelVisible();
		servicerequestslistpage.selectAddServiceRequestsComboboxValue("SR_Smoke_Test");
		servicerequestslistpage.clickAddServiceRequestButton();
		
		servicerequestslistpage.clickCustomerEditButton();
		servicerequestslistpage.selectServiceRequestCustomer("Alex Zakaulov");
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.clickVehicleInforEditButton();
		servicerequestslistpage.setServiceRequestVIN(VIN);
		servicerequestslistpage.clickDoneButton();
		
		servicerequestslistpage.addServicesToServiceRequest(services);
		for (String serviceName : services)
			servicerequestslistpage.setServiePriceAndQuantity(serviceName, servicePrice, serviceQuantity);
		servicerequestslistpage.saveNewServiceRequest();
		String srnumber = servicerequestslistpage.getFirstInTheListServiceRequestNumber();
		servicerequestslistpage.acceptFirstServiceRequestFromList();
		DriverBuilder.getInstance().getDriver().quit();
		
		homescreen = new RegularHomeScreen();
		RegularServiceRequestsScreen servicerequestsscreen = homescreen.clickServiceRequestsButton();
		servicerequestsscreen.clickRefreshButton();
		servicerequestsscreen.selectServiceRequest(srnumber);
		servicerequestsscreen.selectCreateInspectionRequestAction();
		RegularVehicleScreen vehiclescreen = servicerequestsscreen.selectInspectionType(InspectionsTypes.INSP_SMOKE_TEST);
		RegularServicesScreen servicesscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.SERVICES);
		RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
		for (String serviceName : services)
			Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceName, PricesCalculations.getPriceRepresentation(servicePrice) +
					" x " + BackOfficeUtils.getFullPriceRepresentation(serviceQuantity)));
		servicesscreen.saveWizard();
		servicerequestsscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 8430:Create work order with type is assigned to a specific client", description = "Create work order with type is assigned to a specific client ")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "license.name" })
	public void testCreateWorkOrderWithTypeIsAssignedToASpecificClient(String backofficeurl, String userName, String userPassword, String licensename)
	{
		
		RegularMainScreen mainscreen = homescreen.clickLogoutButton();
		BaseUtils.waitABit(2000);
		LicensesScreen licensesscreen = mainscreen.clickLicenses();
		licensesscreen.clickAddLicenseButtonAndAcceptAlert();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(userName, userPassword);

		ActiveDevicesWebPage devicespage = PageFactory.initElements(webdriver,
				ActiveDevicesWebPage.class);

		devicespage.setSearchCriteriaByName(licensename);
		regCode = devicespage.getFirstRegCodeInTable();

		DriverBuilder.getInstance().getDriver().quit();
		LoginScreen loginscreen = new LoginScreen();
		loginscreen.registeriOSDevice(regCode);
		BaseUtils.waitABit(2000);
		RegularMainScreen mainscr = new RegularMainScreen();
		homescreen = mainscr.userLogin(userLogin, userPassword);
		
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.clickHomeButton();
	}
}
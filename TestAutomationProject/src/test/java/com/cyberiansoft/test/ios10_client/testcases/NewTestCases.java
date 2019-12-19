package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.verifications.ServiceRequestsListVerifications;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
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
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NewTestCases extends BaseTestCase {

	private String regCode;
	private String userLogin = "User";
	private String userPassword = "1111";
	private WholesailCustomer ZAZ_Motors = new WholesailCustomer();

	@BeforeClass
	@Parameters({ "backoffice.url", "user.name", "user.psw", "license.name" })
	public void setUpSuite(String backofficeurl, String userName, String userPassword, String licensename) {
		ZAZ_Motors.setCompanyName("Zaz Motors");
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
		mainscr.userLogin(userLogin, userPassword);
	}

	@Test(testName = "Test Case 59643:iOS: Invoices - Send Multiple Emails", description = "Invoices - Send Multiple Emails")
	public void testInvoicesSendMultipleEmails() {
			
		final int numberInvoicesToSelect = 4;
		final String mailaddress = "test@cyberiansoft.com";
			
		RegularHomeScreen homeScreen = new RegularHomeScreen();

		RegularHomeScreenSteps.navigateToMyInvoicesScreen();
		RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
		myInvoicesScreen.clickActionButton();
		myInvoicesScreen.selectInvoices(numberInvoicesToSelect);
		myInvoicesScreen.clickActionButton();
		RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
		RegularEmailScreenSteps.sendSingleEmailToAddress(UtilConstants.TEST_EMAIL);
		myInvoicesScreen.clickDoneButton();
		myInvoicesScreen.clickHomeButton();
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
		
		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homeScreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.clickHomeButton();
		
		//Create WO1
		RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
		RegularMyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(VIN);
		
		String wonumber1 = vehicleScreen.getInspectionNumber();
		vehicleScreen.setMakeAndModel(_make, _model);
		vehicleScreen.setColor(_color);
		vehicleScreen.setYear(_year);
		vehicleScreen.setMileage(mileage);
		//vehicleScreen.setFuelTankLevel(fueltanklevel);
		//vehicleScreen.setType(_type);
		vehicleScreen.setStock(stock);
		vehicleScreen.setRO(_ro);

		RegularNavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.openCustomServiceDetails("Dye_Panel");
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularWorkOrdersSteps.saveWorkOrder();
		RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
		myWorkOrdersScreen.selectWorkOrderForApprove(wonumber1);
		myWorkOrdersScreen.clickApproveButton();
		
		//myWorkOrdersScreen.searchWO(wonumber1);
		RegularMyWorkOrdersSteps.startCopyingVehicleForWorkOrder(wonumber1, ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
		Assert.assertEquals(vehicleScreen.getMake(), _make);
		Assert.assertEquals(vehicleScreen.getModel(), _model);
		Assert.assertEquals(vehicleScreen.getYear(), _year);
		RegularNavigationSteps.navigateToOrderSummaryScreen();
		RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
		orderSummaryScreen.checkApproveAndCreateInvoice();
		orderSummaryScreen.clickSave();
		
		orderSummaryScreen.selectDefaultInvoiceType();
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
		RegularNavigationSteps.navigateToInvoiceInfoScreen();
		RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
		invoiceInfoScreen.setPO(poNomber);
		invoiceInfoScreen.addWorkOrder(wonumber1);
		invoiceInfoScreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Signature' in section 'Follow up Requested' should be answered."));
		Helpers.drawRegularQuestionsSignature();
		questionsscreen.clickSave();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'Engine Condition' in section 'Test Section' should be answered."));
		questionsscreen.selectAnswerForQuestion("Engine Condition", "Pretty Good");
		RegularNavigationSteps.navigateToInvoiceInfoScreen();
		invoiceInfoScreen.clickSaveAsDraft();
		myWorkOrdersScreen.clickHomeButton();
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

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularCustomersScreen customersscreen = homeScreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		RegularNavigationSteps.navigateBackScreen();


		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		RegularServiceRequestSteps.startCreatingServicerequest(ZAZ_Motors, ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
		RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
		vehicleScreen.setVIN(VIN);
		vehicleScreen.setMakeAndModel(_make, _model);
		vehicleScreen.setColor(_color);
		vehicleScreen.setYear(_year);
		vehicleScreen.setMileage(mileage);
		//vehicleScreen.setFuelTankLevel(fueltanklevel);
		//vehicleScreen.setType(_type);
		vehicleScreen.setStock(stock);
		vehicleScreen.setRO(_ro);
		RegularNavigationSteps.navigateToServicesScreen();

		RegularServicesScreen servicesScreen = new RegularServicesScreen();
		RegularServicesScreenSteps.openCustomServiceDetails("3/4\" - Penny Size");
		RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServiceQuantityValue("3");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.selectService("SR_Money_Vehicle");
		servicesScreen.selectService("SR_S4_Bundle");
		
		servicesScreen.clickSave();
		String alerttext = Helpers.getAlertTextAndCancel();
		Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CREATE_APPOINTMENT);

		srtowo = serviceRequestSscreen.getFirstServiceRequestNumber();
		Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(srtowo), "On Hold");
		Assert.assertTrue(serviceRequestSscreen.getServiceRequestClient(srtowo).contains(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER));
		//Assert.assertTrue(serviceRequestsScreen.getServiceRequestEmployee(srnumber).contains(iOSInternalProjectConstants.USERSIMPLE_LOGIN));
		Assert.assertTrue(serviceRequestSscreen.getServiceRequestDetails(srtowo).contains("WERTYU123"));
		srtowo = serviceRequestSscreen.getFirstServiceRequestNumber();
		RegularNavigationSteps.navigateBackScreen();
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(userName, userPassword);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backofficeheader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.makeSearchPanelVisible();

        final ServiceRequestsListVerifications serviceRequestsListVerifications = new ServiceRequestsListVerifications();
        serviceRequestsListVerifications.verifySearchFieldsAreVisible();
		
		serviceRequestsListInteractions.selectSearchTeam(teamname);
		serviceRequestsListInteractions.selectSearchTechnician("Test User");
		serviceRequestsListInteractions.setSearchFreeText(srtowo);
		serviceRequestsListInteractions.clickFindButton();
        serviceRequestsListVerifications.verifySearchResultsByServiceName(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		serviceRequestsListInteractions.selectFirstServiceRequestFromList();
		Assert.assertEquals(serviceRequestsListInteractions.getVINValueForSelectedServiceRequest(), "WERTYU123");
		Assert.assertEquals(serviceRequestsListInteractions.getCustomerValueForSelectedServiceRequest(), iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
		Assert.assertEquals(serviceRequestsListInteractions.getEmployeeValueForSelectedServiceRequest(), "Test User (Default team)");
		Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("SR_S4_Bundle $350.00 (1.00)"));
		Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("SR_Money_Vehicle $200.00 (1.00)"));
		Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("3/4\" - Penny Size $18.00 (3.00)"));
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
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backofficeheader.clickOperationsLink();
		ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
		operationsWebPage.clickNewServiceRequestList();
		serviceRequestsListInteractions.makeSearchPanelVisible();
		serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue("SR_Smoke_Test");
		serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
		
		serviceRequestsListInteractions.clickCustomerEditButton();
		serviceRequestsListInteractions.selectServiceRequestCustomer("Alex Zakaulov");
		serviceRequestsListInteractions.clickDoneButton();
		
		serviceRequestsListInteractions.clickVehicleInfoEditButton();
		serviceRequestsListInteractions.setServiceRequestVIN(VIN);
		serviceRequestsListInteractions.clickDoneButton();
		
		serviceRequestsListInteractions.addServicesToServiceRequest(services);
		for (String serviceName : services)
			serviceRequestsListInteractions.setServiePriceAndQuantity(serviceName, servicePrice, serviceQuantity);
		serviceRequestsListInteractions.saveNewServiceRequest();
		String srnumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
		serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
		DriverBuilder.getInstance().getDriver().quit();

		RegularHomeScreenSteps.navigateToServiceRequestScreen();
		RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
		serviceRequestSscreen.clickRefreshButton();
		RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(srnumber, InspectionsTypes.INSP_SMOKE_TEST);
		NavigationSteps.navigateToServicesScreen();
		RegularServicesScreenSteps.switchToSelectedServices();
		RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
		for (String serviceName : services)
			Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceName, PricesCalculations.getPriceRepresentation(servicePrice) +
					" x " + BackOfficeUtils.getFullPriceRepresentation(serviceQuantity)));
		RegularServiceRequestSteps.saveServiceRequest();
		serviceRequestSscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 8430:Create work order with type is assigned to a specific client", description = "Create work order with type is assigned to a specific client ")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "license.name" })
	public void testCreateWorkOrderWithTypeIsAssignedToASpecificClient(String backofficeurl, String userName, String userPassword, String licensename)
	{

		RegularHomeScreen homeScreen = new RegularHomeScreen();
		RegularHomeScreenSteps.logoutUser();
		BaseUtils.waitABit(2000);
		RegularMainScreen mainScreen = new RegularMainScreen();
		LicensesScreen licensesscreen = mainScreen.clickLicenses();
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
		mainscr.userLogin(userLogin, userPassword);
		
		RegularCustomersScreen customersscreen = homeScreen.clickCustomersButton();
		customersscreen.clickHomeButton();
	}
}
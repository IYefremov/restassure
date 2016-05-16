package com.cyberiansoft.test.ios_client.testcases;

import io.appium.java_client.ios.IOSElement;

import java.net.MalformedURLException;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.EnterpriseBeforeDamageScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.LoginScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularCarHistoryScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularClaimScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularEnterpriseBeforeDamageScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularInspectionScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularOrderMonitorScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularServicesScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularVehicleScreen;
import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.ios_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios_client.utils.ExcelUtils;
import com.cyberiansoft.test.ios_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.PricesCalculations;
import com.cyberiansoft.test.ios_client.utils.UtilConstants;
import com.relevantcodes.extentreports.LogStatus;

public class DentWizardRegularVersionTestCases extends BaseTestCase {

		private String regCode;
		private final String customer = "Abc Rental Center";
		public RegularHomeScreen homescreen;
		
		@BeforeClass
		@Parameters({ "backoffice.url", "user.name", "user.psw" })
		public void setUpSuite(String backofficeurl, String userName, String userPassword) throws Exception {
			initTestUser(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
			testGetDeviceRegistrationCode(backofficeurl, userName, userPassword);
			testRegisterationiOSDdevice();
			ExcelUtils.setDentWizardExcelFile();
		}

		//@Test(description = "Get registration code on back-office for iOS device")
		public void testGetDeviceRegistrationCode(String backofficeurl,
				String userName, String userPassword) throws Exception {

			final String searchlicensecriteria = "Mac mini_olkr";
			
			//webdriverInicialize();
			webdriverGotoWebPage(backofficeurl);

			BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
					BackOfficeLoginWebPage.class);
			loginpage.UserLogin(userName, userPassword);

			ActiveDevicesWebPage devicespage = PageFactory.initElements(webdriver,
					ActiveDevicesWebPage.class);

			devicespage.setSearchCriteriaByName(searchlicensecriteria);
			regCode = devicespage.getFirstRegCodeInTable();
			getWebDriver().quit();
			Thread.sleep(2000);
		}

		//@Test(description = "Register iOS Ddevice")
		public void testRegisterationiOSDdevice() throws Exception {		
			appiumdriverInicialize();	
			appiumdriver.removeApp(bundleid);
			appiumdriverInicialize();
			LoginScreen loginscreen = new LoginScreen(appiumdriver);
			loginscreen.assertRegisterButtonIsValidCaption();
			loginscreen.registeriOSDevice(regCode);
			RegularMainScreen mainscr = new RegularMainScreen(appiumdriver);
			homescreen = mainscr.userLogin(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);	
		}
		
		@BeforeMethod
		public void restartApps() throws MalformedURLException, InterruptedException {
			//resrtartApplication();	
			//MainScreen mainscr = new MainScreen(appiumdriver);
			//homescreen = mainscr.userLogin(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
			System.out.println("================================ NEW TESTACASE ====================================");
		}

		@Test(testName = "Test Case 10264:Test Valid VIN Check", description = "Test Valid VIN Check")
		public void testValidVINCheck() throws Exception {
			final String tcname = "testValidVINCheck";
			final int tcrow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
			//customersscreen.selectFirstCustomerWithoutEditing();
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(tcrow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(tcrow), ExcelUtils.getModel(tcrow), ExcelUtils.getYear(tcrow));
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.cancelOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10451:Test Top Customers Setting", description = "Test Top Customers Setting")
		public void testTopCustomersSetting() throws Exception {
			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setShowTopCustomersOn();
			settingsscreen.clickHomeButton();
			
			RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
			customersscreen.assertTopCustomersExists();
			Assert.assertTrue(customersscreen.customerIsPresent(UtilConstants.TEST_CUSTOMER_FOR_TRAINING));
			Assert.assertTrue(customersscreen.customerIsPresent(customer));
			customersscreen.clickHomeButton();
			
			homescreen.clickSettingsButton();
			settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setShowTopCustomersOff();
			settingsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10452:Test VIN Duplicate check", description = "Test VIN Duplicate check")
		public void testVINDuplicateCheck() throws Exception {
			
			final String tcname = "testVINDuplicateCheck";
			final int tcrow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setCheckDuplicatesOn();
			settingsscreen.clickHomeButton();

			RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
			customersscreen.swtchToWholesaleMode();
			customersscreen.selectCustomerWithoutEditing(customer);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVINAndAndSearch(ExcelUtils.getVIN(tcrow).substring(
					0, 11));
			Thread.sleep(2000);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(tcrow).substring(11, 17));
			vehiclescreeen.verifyExistingWorkOrdersDialogAppears();		
			vehiclescreeen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
			homescreen.clickSettingsButton();
			settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setCheckDuplicatesOff();
			settingsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10453:Test Vehicle Part requirement", description = "Test Vehicle Part requirement")
		public void testVehiclePartRequirement() throws Exception {
			final String tcname = "testVehiclePartRequirement";
			final int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setCheckDuplicatesOn();
			settingsscreen.clickHomeButton();

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.swtchToWholesaleMode();
			customersscreen.selectCustomerWithoutEditing(customer);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVINAndAndSearch(ExcelUtils.getVIN(testcaserow));
			Thread.sleep(2000);
			//Assert.assertEquals(searchresult, "Search Complete No vehicle invoice history found");
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			servicesscreen
					.assertServiceTypeExists(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
			servicesscreen.assertServiceTypeExists(UtilConstants.PDRPANEL_SUBSERVICE);
			servicesscreen.assertServiceTypeExists(UtilConstants.PDRVEHICLE_SUBSERVICE);
			servicesscreen.assertServiceTypeExists(UtilConstants.PDRPANEL_HAIL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickCancelButton();
			servicesscreen.clickBackServicesButton();
			servicesscreen.cancelOrder();
			myworkordersscreen.clickHomeButton();

			homescreen.clickSettingsButton();
			settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setCheckDuplicatesOff();
			settingsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10455:Test turning multiple Work Orders into a single Invoice", description = "Test turning multiple Work Orders into a single Invoice")
		public void testTurningMultipleWorkOrdersIntoASingleInvoice()
				throws Exception {
		    String tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice1";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Fender", "Left Front Door",
					"Roof" };
			final String[] vehiclepartswheels = { "Left Front Wheel",
					"Right Front Wheel" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.swtchToWholesaleMode();
			customersscreen.selectCustomerWithoutEditing(customer);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			String wo1 = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheels.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartswheels[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.clickSaveButton();

			// ==================Create second WO=============
			tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice2";
			testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts2 = { "Hood", "Roof", "Trunk Lid" };
			final String[] vehiclepartspaints = { "Front Bumper", "Rear Bumper" };

			myworkordersscreen.clickAddOrderButton();
			vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			String wo2 = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts2.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts2[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$41.66");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), "$41.67");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), "$41.67");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaints.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartspaints[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.selectTechniciansCustomView();
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen
					.setTechnicianCustomPriceValue(UtilConstants.technicianA, "165");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB,
					"50");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA),
					"$165.00");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB),
					"$50.00");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());

			ordersummaryscreen.clickSaveButton();
			final String[] wos = {wo1, wo2};
			myworkordersscreen.clickCreateInvoiceIconForWOsViaSearch(wos);
			myworkordersscreen.clickInvoiceIcon();
			RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
			invoiceinfoscreen.assertWOIsSelected(wo1);
			invoiceinfoscreen.assertWOIsSelected(wo2);
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();
			Thread.sleep(1000);
			Assert.assertFalse(myworkordersscreen.woExistsViaSearch(wo1));
			Assert.assertFalse(myworkordersscreen.woExistsViaSearch(wo2));
			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			Assert.assertTrue(myinvoicesscreen.areWOsForInvoiceExists(invoicenum, wo1, wo2));
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10651:Test same Order Type required for turning multiple Work Orders into a single Invoice", description = "Test same Order Type required for turning multiple Work Orders into a single Invoice")
		public void testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice()
				throws Exception {
			String tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice1";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Roof" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routecanadaworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			String wo1 = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			for (int i = 0; i <= vehicleparts.length; i++) {
				selectedservicescreen.saveSelectedServiceDetails();
			}
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.clickSaveButton();

			// ==================Create second WO=============
			tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice2";
			testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts2 = { "Hood" };

			myworkordersscreen.clickAddOrderButton();
			vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			String wo2 = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts2.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts2[i]);
			}
			for (int i = 0; i <= vehicleparts2.length; i++) {
				selectedservicescreen.saveSelectedServiceDetails();
			}
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());

			ordersummaryscreen.clickSaveButton();
			final String[] wos = {wo1, wo2};
			myworkordersscreen.clickCreateInvoiceIconForWOsViaSearch(wos);
			
			myworkordersscreen.clickInvoiceIcon();
			String alerttext = myworkordersscreen
					.selectInvoiceTypeAndAcceptAlert(UtilConstants.NO_ORDER_TYPE);
			Assert.assertEquals(
					alerttext,
					"Invoice type " + UtilConstants.NO_ORDER_TYPE + " doesn't support multiple Work Order types.");
			myworkordersscreen.clickCancel();
			myworkordersscreen.cancelInvoice();
			//myworkordersscreen.acceptAlertByCoords();
			myworkordersscreen.clickHomeButton();

		}
		
		@Test(testName = "Test Case 10652:Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker", description = "Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker")
		public void testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker()
				throws Exception {
			String tcname = "testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Fender", "Left Front Door",
					"Left Quarter Panel" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();
			
			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.wizprotrackerrouteworkordertype);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();

			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			String insptoapprove = myinspectionsscreen.getFirstInspectionNumberValue();
			myinspectionsscreen.selectInspectionForApprove(insptoapprove);			
			RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
			// myinspectionsscreen.selectInspectionToApprove(inspection);
			// approveinspscreen.selectInspectionToApprove();
			approveinspscreen.drawApprovalSignature ();
			approveinspscreen.clickApproveButton();
			// approveinspscreen.clickBackButton();
			myinspectionsscreen.assertInspectionIsApproved(insptoapprove);
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10653:Test Inspections convert to Work Order", description = "Test Inspections convert to Work Order")
		public void testInspectionsConvertToWorkOrder() throws Exception {
			String tcname = "testInspectionsConvertToWorkOrder";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Right Roof Rail", "Right Fender",
					"Right Rear Door" };

			final String[] vehicleparts2 = { "Left Mirror", "Right Mirror" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();
			
			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.wizprotrackerrouteworkordertype);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String wonumber = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts2.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts2[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));

			servicesscreen.clickSaveButton();
			Thread.sleep(4000);
			String insptoapprove = myinspectionsscreen.getFirstInspectionNumberValue();
			myinspectionsscreen.selectInspectionForApprove(insptoapprove);
			RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen(appiumdriver);
			approveinspscreen.drawApprovalSignature ();
			approveinspscreen.clickApproveButton();
			myinspectionsscreen.assertInspectionIsApproved(insptoapprove);
			myinspectionsscreen.selectInspectionType (insptoapprove);
			myinspectionsscreen.clickCreateWOButton();
			String wonumber2 = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			
			Assert.assertEquals(wonumber.substring(0, 1), "E");
			Assert.assertEquals(wonumber2.substring(0, 1), "O");
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			ordersummaryscreen.clickSaveButton();

			myinspectionsscreen.clickHomeButton();
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			myworkordersscreen.woExistsViaSearch(wonumber);
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10654:Test 'Vehicle' Service does not multiply price entered when selecting multiple panels", description = "Test 'Vehicle' Service does not multiply price entered when selecting multiple panels")
		public void testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels()
				throws Exception {
			String tcname = "testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Hood", "Roof", "Trunk Lid" };
			
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(customer);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

			servicesscreen.cancelOrder();
			myworkordersscreen.clickHomeButton();

			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setCheckDuplicatesOff();
			settingsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10655:Test 'Panel' Service multiplies price entered when selecting multiple panels", description = "Test 'Panel' Service multiplies price entered when selecting multiple panels")
		public void testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels()
				throws Exception {
			String tcname = "testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Fender", "Left Front Door",
					"Left Rear Door" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(customer);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

			servicesscreen.cancelOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10656:Test Carmax vehicle information requirements", description = "Test Carmax vehicle information requirements")
		public void testCarmaxVehicleInformationRequirements() throws Exception {
			String tcname = "testCarmaxVehicleInformationRequirements";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.clickChangeScreen();
			Assert.assertEquals(vehiclescreeen.clickSaveWithAlert(),
					"Warning! Stock# is required");
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreeen.clickChangeScreen();
			Assert.assertEquals(vehiclescreeen.clickSaveWithAlert(),
					"Warning! RO# is required");
			vehiclescreeen.setRO(ExcelUtils.getRO(testcaserow));
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.cancelOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10657:Test Service Drive requires Advisor", description = "Test Service Drive requires Advisor")
		public void testServiceDriveRequiresAdvisor() throws Exception {
			String tcname = "testServiceDriveRequiresAdvisor";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.servicedriveworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.clickChangeScreen();
			Assert.assertEquals(vehiclescreeen.clickSaveWithAlert(),
					AlertsCaptions.ALERT_ADVISOR_REQUIRED);
			vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.cancelOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName= "Test Case 10658:Test Inspection requirments inforced", description = "Test Inspection requirements inforced")
		public void testInspectionRequirementsInforced() throws Exception {
			String tcname = "testInspectionRequirementsInforced";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.wizardprotrackerrouteinspectiondertype);
			RegularInspectionScreen  inspectionscreen = new RegularInspectionScreen(appiumdriver);
			inspectionscreen.clickChangeScreen();
			String alerttext = inspectionscreen.clickSaveWithAlert();
			Assert.assertEquals(AlertsCaptions.ALERT_VIN_REQUIRED, alerttext);
			inspectionscreen.setVIN(ExcelUtils.getVIN(testcaserow));
			inspectionscreen.clickChangeScreen();
			alerttext = inspectionscreen.clickSaveWithAlert();
			//alerttext = Helpers.getAlertTextAndAccept();
			//alerttext = inspectionscreen.clickSaveWithAlert();
			Assert.assertEquals(AlertsCaptions.ALERT_ADVISOR_REQUIRED, alerttext);
			inspectionscreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			inspectionscreen.clickChangeScreen();
			//inspectionscreen.saveChanges();
			inspectionscreen.clickSaveButton();
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10663:Test Inspections can convert to multiple Work Orders", description = "Test Inspections can convert to multiple Work Orders")
		public void testInspectionsCanConvertToMultipleWorkOrders()
				throws Exception {
			String tcname = "testInspectionsCanConvertToMultipleWorkOrders";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();
			
			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.routecanadaworkordertype);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			//String wonumber = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.INTERIORBURNS_SUBSERVICE);

			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			String inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
			myinspectionsscreen.selectInspectionType (inspnum);
			myinspectionsscreen.clickCreateWOButton();
			String wonumber = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			Assert.assertEquals(wonumber.substring(0, 1), "O");
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			ordersummaryscreen.clickSaveButton();
			Assert.assertEquals(myinspectionsscreen.getFirstInspectionNumberValue(), inspnum);
			vehiclescreeen = myinspectionsscreen.showWorkOrdersForInspection(inspnum);
			Assert.assertEquals(vehiclescreeen.getInspectionNumber(), wonumber);
			servicesscreen.cancelOrder();
			
			myinspectionsscreen.selectInspectionType (myinspectionsscreen.getFirstInspectionNumberValue());
			myinspectionsscreen.clickCreateWOButton();
			String wonumber2 = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());

			
			Assert.assertEquals(wonumber2.substring(0, 1), "O");
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			ordersummaryscreen.clickSaveButton();
			Assert.assertEquals(myinspectionsscreen.getFirstInspectionNumberValue(), inspnum);
			myinspectionsscreen.showWorkOrdersForInspection(inspnum);
			Thread.sleep(10000);
			
			Assert.assertEquals(myinspectionsscreen.getNumberOfWorkOrdersForIspection(), 2);
			Assert.assertTrue(myinspectionsscreen.isWorkOrderForInspectionExists(wonumber2));
			myinspectionsscreen.clickHomeButton();

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			myworkordersscreen.woExistsViaSearch(wonumber);
			myworkordersscreen.woExistsViaSearch(wonumber2);
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10665:Test Archive feature for Inspections", description = "Test Archive feature for Inspections")
		public void testArchiveFeatureForInspections() throws Exception {
			String tcname = "testArchiveFeatureForInspections";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			
			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.routeworkordertype);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.OTHER_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			String insptoarchive = myinspectionsscreen.getFirstInspectionNumberValue();
			myinspectionsscreen.selectInspectionType (insptoarchive);

			myinspectionsscreen.clickArchive InspectionButton();
			myinspectionsscreen.clickFilterButton();
			myinspectionsscreen.clickStatusFilter();
			myinspectionsscreen.assertFilterStatusIsSelected("New");
			myinspectionsscreen.assertFilterStatusIsSelected("Approved");
			myinspectionsscreen.clickFilterStatus("New");
			myinspectionsscreen.clickFilterStatus("Approved");
			myinspectionsscreen.clickFilterStatus("Archived");
			myinspectionsscreen.assertFilterStatusIsSelected("Archived");
			myinspectionsscreen.clickHomeButton();
			myinspectionsscreen.clickSaveFilterDialogButton();

			myinspectionsscreen.assertInspectionExists(insptoarchive);
			Assert.assertEquals(myinspectionsscreen.assertFilterIsApplied(), true);
			myinspectionsscreen.clearFilter();
			myinspectionsscreen.clickSaveFilterDialogButton();
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11731:Test even WO level tech split for Wholesale Hail", description = "Test even WO level tech split for Wholesale Hail")
		public void testEvenWOLevelTechSplitForWholesaleHail() throws Exception {
			String tcname = "testEvenWOLevelTechSplitForWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left A Pillar", "Left Fender",
					"Left Rear Door", "Roof" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreeen.clickTech();
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.selectTechniciansEvenlyView();
			String alerttext = selectedservicescreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);
			Helpers.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);

			selectedservicescreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}			
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.assertServiceIsSelected(UtilConstants.FIXPRICE_SERVICE);

			servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
					vehicleparts[0]);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , "$105.00 x 1.00");
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , "$0.00 x 1.00");
			servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
			Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
					vehicleparts[1]);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianB);
			selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
			selectedservicescreen.assertTechnicianIsNotSelected(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA),
					PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$70.00");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), "$70.00");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$0.00 x 1.00");
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00");
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$140.00 x 1.00");

			servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
			Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
					vehicleparts[2]);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianB);
			selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
			selectedservicescreen.assertTechnicianIsNotSelected(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.selecTechnician(UtilConstants.technicianD);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$30.00");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianD), "$30.00");
			selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
			selectedservicescreen.assertTechnicianIsNotSelected(UtilConstants.technicianA);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00");
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$140.00 x 1.00");
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$60.00 x 1.00");
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$0.00 x 1.00");

			servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
			Assert.assertEquals(selectedservicescreen.getVehiclePartValue(),
					vehicleparts[3]);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$275.00 x 1.00");
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00");
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$140.00 x 1.00");
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$60.00 x 1.00");

			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11732:Test even service level tech split for Wholesale Hail", description = "Test even service level tech split for Wholesale Hail")
		public void testEvenServiceLevelTechSplitForWholesaleHail()
				throws Exception {
			String tcname = "testEvenServiceLevelTechSplitForWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Hood", "Right Quarter Panel",
					"Sunroof" };
			RegularHomeScreen homescreen = new RegularHomeScreen(appiumdriver);
			//homescreen.clickCustomersButton();
			//RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			//customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();

			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
			Helpers.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$47.50");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), "$47.50");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();

			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_TOTAL_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA),
					"$175.00");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB),
					"$175.00");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));		
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11733:Test Custom WO level tech split for wholesale Hail", description = "Test Custom WO level tech split for wholesale Hail")
		public void testCustomWOLevelTechSplitForWholesaleHail() throws Exception {
			String tcname = "testCustomWOLevelTechSplitForWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Cowl, Other", "Left Fender",
					"Trunk Lid" };
			 
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreeen.clickTech();
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selectTechniciansCustomView();
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA),
					"%100.00");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, ExcelUtils.getServicePrice(testcaserow));
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA),
					"%85.00");

			String alerttext = selectedservicescreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(alerttext,
					AlertsCaptions.ALERT_TOTAL_AMAUNT_NOT_EQUAL100);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "15");

			alerttext = selectedservicescreen.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

			Helpers.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

			selectedservicescreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();

			servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_ND_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.clickTechniciansIcon();
			Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), "$93.50");
			Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), "$16.50");
			selectedservicescreen.selectTechniciansCustomView();
			selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "50");
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%45.46");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "60");		
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%54.54");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();

			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11734:Test Custom service level tech split for wholesale Hail", description = "Test Custom service level tech split for wholesale Hail")
		public void testCustomServiceLevelTechSplitForWholesaleHail() throws Exception {
			String tcname = "testCustomServiceLevelTechSplitForWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Fender", "Left Quarter Panel", "Right Rear Door",
					"Trunk Lid" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));

			Helpers.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

			selectedservicescreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 0);
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.selectTechniciansCustomView();
			Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "45");	
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%36.00");
			String alerttext = selectedservicescreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(alerttext,
					AlertsCaptions.ALERT_SPLIT_TOTAL_AMAUNT_NOT_EQUAL);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "80");	
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%64.00");
			selectedservicescreen.saveSelectedServiceDetails();		
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.selectTechniciansCustomView();
			Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "25");	
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%20.00");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "100");	
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%80.00");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.selectTechniciansCustomView();
			Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "50");	
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%40.00");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "75");	
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%60.00");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.selectTechniciansCustomView();
			Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "30");	
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%24.00");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "95");	
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%76.00");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11735:Test Customer Discount on Wholesale Hail", description = "Test Customer Discount on Wholesale Hail")
		public void testCustomerDiscountOnWholesaleHail() throws Exception {
			String tcname = "testCustomerDiscountOnWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Hood", "Roof", "Trunk Lid" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wholesailhailworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
			Helpers.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			selectedservicescreen = servicesscreen.openCustomServiceDetails("Customer Discount");
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10668:Test Quick Quote option for Retail Hail", description = "Test Quick Quote option for Retail Hail")
		public void testQuickQuoteOptionForRetailHail() throws Exception {
			String tcname = "testQuickQuoteOptionForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			RegularHomeScreen homescreen = new RegularHomeScreen(appiumdriver);
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen = new RegularQuestionsScreen(appiumdriver);
			vehiclescreeen.clickChangeScreen();
			String alerttext = questionsscreen.clickSaveWithAlert();
			Assert.assertEquals(
					alerttext, "Warning! Question 'Estimate Conditions' in section 'Hail Info' should be answered.");
			questionsscreen = new RegularQuestionsScreen(appiumdriver);
			questionsscreen.selectOutsideQuestions();

			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
			
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
			RegularClaimScreen claimscreen = new RegularClaimScreen(appiumdriver);
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Quick Quote");
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
			pricematrix.assertNotesExists();
			pricematrix.assertTechniciansExists();
			pricematrix.setPrice(ExcelUtils.getServicePrice(testcaserow));

			pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
			pricematrix.setPrice(ExcelUtils.getServicePrice3(testcaserow));
			pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.clickBackButton();
			pricematrix.clickBackButton();
			Helpers.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10733:Test Customer self-pay option for Retail Hail", description = "Test Customer self-pay option for Retail Hail")
		public void testCustomerSelfPayOptionForRetailHail() throws Exception {
			String tcname = "testCustomerSelfPayOptionForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularHomeScreen homescreen = new RegularHomeScreen(appiumdriver);
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.selectOutsideQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
			
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);		
			RegularClaimScreen claimscreen = new RegularClaimScreen(appiumdriver);
			claimscreen.selectInsuranceCompany(ExcelUtils.getInsuranceCompany(retailhaildatarow));
			claimscreen.clickChangeScreen();
			String alerttext = claimscreen.clickSaveWithAlert();
			Assert.assertEquals(alerttext, AlertsCaptions.ALERT_CLAIM_REQUIRED);
			claimscreen.setClaim(ExcelUtils.getClaim(retailhaildatarow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.HAIL_PDR_NON_CUSTOMARY_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.answerQuestion("DEALER");
			selectedservicescreen.saveSelectedServiceDetails();

			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();

			Helpers.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10734:Test Even WO level tech split for Retail Hail", description = "Test Even WO level tech split for Retail Hail")
		public void testEvenWOLevelTechSplitForRetailHail() throws Exception {
			String tcname = "testEvenWOLevelTechSplitForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularHomeScreen homescreen = new RegularHomeScreen(appiumdriver);
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.clickTech();
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			selectedservicescreen.selectTechniciansEvenlyView();
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA),
					"%33.34");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianB),
					"%33.33");
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianC),
					"%33.33");
			String alerttext = selectedservicescreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);
			Thread.sleep(2000);
			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.selectOtherQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
			
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
			RegularClaimScreen claimscreen = new RegularClaimScreen(appiumdriver);
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.HEAVY_SEVERITY);
			pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianB));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianC));

			pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
			selectedservicescreen.unselecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix.clickSaveButton();
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.SEVERE_SEVERITY);
			pricematrix.assertPriceCorrect("$0.00");
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianB));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianC));

			pricematrix.setPrice(ExcelUtils.getServicePrice2(testcaserow));
			pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
			selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
			selectedservicescreen.unselecTechnician(UtilConstants.technicianC);
			selectedservicescreen.selecTechnician(UtilConstants.technicianD);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();

			pricematrix.clickSaveButton();
			pricematrix.clickBackButton();
			pricematrix.clickBackButton();
			Helpers.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10735:Test even service level tech split for Retail Hail", description = "Test even service level tech split for Retail Hail")
		public void testEvenServiceLevelTechSplitForRetailHail() throws Exception {
			String tcname = "testEvenServiceLevelTechSplitForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularHomeScreen homescreen = new RegularHomeScreen(appiumdriver);
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.selectOtherQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));	
			
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
			RegularClaimScreen claimscreen = new RegularClaimScreen(appiumdriver);
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
			pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			pricematrix.assertNotesExists();
			pricematrix.assertTechniciansExists();
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianA));

			pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.unselecTechnician(UtilConstants.technicianA);

			selectedservicescreen.selecTechnician(UtilConstants.technicianB);

			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), "$60.00");

			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix.clickSaveButton();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.HLF_SIZE, PriceMatrixScreen.LIGHT_SEVERITY);
			pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianA));

			pricematrix.clickOnTechnicians();
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.clickDiscaunt(ExcelUtils.getDiscount3(retailhaildatarow));
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.clickSaveButton();
			pricematrix.clickBackButton();
			pricematrix.clickBackButton();
			Helpers.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10736:Test deductible feature for Retail Hail", description = "Test deductible feature for Retail Hail")
		public void testDeductibleFeatureForRetailHail() throws Exception {
			String tcname = "testDeductibleFeatureForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularHomeScreen homescreen = new RegularHomeScreen(appiumdriver);
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.selectOtherQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
			
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
			RegularClaimScreen claimscreen = new RegularClaimScreen(appiumdriver);
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));
			claimscreen.setDeductible("50");
			Assert.assertEquals(
					claimscreen
							.getDeductibleValue(), "50.00");

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
			pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			pricematrix.assertNotesExists();
			pricematrix.assertTechniciansExists();
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianA));

			pricematrix.clickSaveButton();
			pricematrix.clickBackButton();
			pricematrix.clickBackButton();
			Helpers.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			ordersummaryscreen.selectWorkOrderDetails("Hail");
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();

		}
		
		@Test(testName = "Test Case 10737:Test Zip Code validator for Retail Hail", description = "Test Zip Code validator for Retail Hail")
		public void testZipCodeValidatorForRetailHail() throws Exception {
			String tcname = "testZipCodeValidatorForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			final String validzip = "83707";

			RegularHomeScreen homescreen = new RegularHomeScreen(appiumdriver);
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.selectOutsideQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext
					.contains("Your answer doesn't match the validator 'US Zip Codes'."));
			questionsscreen.regularClearZip();
			questionsscreen.setRegularSetFieldValue((IOSElement) appiumdriver.findElementByXPath("//UIAScrollView[2]/UIAScrollView[1]/UIATableView[1]/UIATableCell[@name=\"Owner Zip_TextView_Cell\"]/UIATextView[@name=\"Owner Zip_TextView\"]"), validzip);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.cancelOrder();
			Thread.sleep(2000);
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11727:Test Custom WO level tech split for Retail Hail", description = "Test Custom WO level tech split for Retail Hail")
		public void testCustomWOLevelTechSplitForRetailHail() throws Exception {
			String tcname = "testCustomWOLevelTechSplitForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularHomeScreen homescreen = new RegularHomeScreen(appiumdriver);
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.clickTech();
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selectTechniciansCustomView();
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA),
					"%100.00");

			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA,
					"70");
			String alerttext = selectedservicescreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(alerttext,
					AlertsCaptions.ALERT_TOTAL_AMAUNT_NOT_EQUAL100);

			selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB,
					"30");
			alerttext = selectedservicescreen.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);

			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.selectProperQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));		
			
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
			RegularClaimScreen claimscreen = new RegularClaimScreen(appiumdriver);
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.VERYLIGHT_SEVERITY);
			pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			pricematrix.assertNotesExists();
			pricematrix.assertTechniciansExists();
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianB));

			pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.assertServicePriceValue("%25.000");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
			pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			pricematrix.assertNotesExists();
			pricematrix.assertTechniciansExists();
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianB));

			pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.selectTechniciansEvenlyView();
			selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianB), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.clickSaveButton();
			pricematrix.clickBackButton();
			pricematrix.clickBackButton();
			Helpers.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11728:Test Custom service level tech split for Retail Hail", description = "Test Custom service level tech split for Retail Hail")
		public void testCustomServiceLevelTechSplitForRetailHail() throws Exception {
			String tcname = "testCustomServiceLevelTechSplitForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.selectOtherQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));	
			
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
			RegularClaimScreen claimscreen = new RegularClaimScreen(appiumdriver);
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
			pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			pricematrix.assertNotesExists();
			pricematrix.assertTechniciansExists();
			pricematrix.clickOnTechnicians();
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selectTechniciansCustomView();
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedservicescreen.getTechnicianPrice(UtilConstants.technicianA),
					PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "285");
			String alerttext = selectedservicescreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(alerttext, AlertsCaptions.ALERT_SPLIT_TOTAL_AMAUNT_NOT_EQUAL);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "40");
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianB));

			pricematrix.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.assertServicePriceValue("%25.000");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
			pricematrix.assertPriceCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			pricematrix.assertNotesExists();
			pricematrix.assertTechniciansExists();

			pricematrix.clickOnTechnicians();
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selectTechniciansCustomView();
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "125");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "75");
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(pricematrix.getTechniciansValue().contains(
					UtilConstants.technicianB));

			pricematrix.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));

			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.selectTechniciansCustomView();
			selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "100");
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "45");

			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.clickSaveButton();
			pricematrix.clickBackButton();
			pricematrix.clickBackButton();
			Helpers.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12626:Test Customer Discount on Retail Hail", description = "Test Customer Discount on Retail Hail")
		public void testCustomerDiscountOnRetailHail() throws Exception {
			String tcname = "testCustomerDiscountOnRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.selectOutsideQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));		
			
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
			RegularClaimScreen claimscreen = new RegularClaimScreen(appiumdriver);
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("Customer Discount");
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();	

			Helpers.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12627:Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice", description = "Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice")
		public void testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice()
				throws Exception {
			String tcname = "testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Roof Rail", "Right Roof Rail",
					"Roof" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
			
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);
			
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String wonumber = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTROCKERPANELS_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			
			ordersummaryscreen.clickSaveButton();
			homescreen = myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			Thread.sleep(2000);
			System.out.println("++++++" + wonumber);
			teamworkordersscreen.clickCreateInvoiceForWO(wonumber);
			teamworkordersscreen.clickiCreateInvoiceButton();
			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
			teamworkordersscreen.clickHomeButton();			
		}
		
		@Test(testName = "Test Case 12628:Test Phase Enforcement for WizardPro Tracker", description = "Test Phase Enforcement for WizardPro Tracker")
		public void testPhaseEnforcementForWizardProTracker()
				throws Exception {
			String tcname = "testPhaseEnforcementForWizardProTracker";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Fender", "Left Front Door",
					"Left Quarter Panel" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			String inspection = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			selectedservicescreen =  servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();

			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(inspection);
			teamworkordersscreen.selectWOMonitor();
			RegularOrderMonitorScreen ordermonitorscreen = new RegularOrderMonitorScreen(appiumdriver);
			ordermonitorscreen.selectPanel(UtilConstants.PDRPANEL_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			//ordermonitorscreen.selectPanel(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
			Assert.assertTrue(ordermonitorscreen.isServiceIsActive(UtilConstants.PAINTDOORHANDLE_SUBSERVICE));

			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDRPANEL_SUBSERVICE, "Completed");
			
			ordermonitorscreen.selectPanel(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE, "Completed");
			
			ordermonitorscreen.selectPanel(UtilConstants.WHEEL_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.WHEEL_SUBSERVICE, "Completed");
			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			
			teamworkordersscreen.clickCreateInvoiceForWO(inspection);
			teamworkordersscreen.verifyCreateInvoiceIsActivated(inspection);
			teamworkordersscreen.clickiCreateInvoiceButton();
			teamworkordersscreen.selectWOInvoiceType(UtilConstants.NO_ORDER_TYPE);
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			teamworkordersscreen.clickHomeButton();			
		}

		@Test(testName = "Test Case 12630:Test adding services to an order being monitored", description = "Test adding services to an order being monitored")
		public void testAddingServicesToOnOrderBeingMonitored()
				throws Exception {
			String tcname = "testAddingServicesToOnOrderBeingMonitored";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Rear Door",
					"Right Front Door" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };
			final String[] vehiclepartspaint = { "Front Bumper"};
			final String[] vehiclepartstoadd = { "Hood"};
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			String inspection = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			for (int i = 0; i <= vehiclepartspaint.length; i++) {
				selectedservicescreen.saveSelectedServiceDetails();
			}
			servicesscreen.clickBackServicesButton();
			
			
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			selectedservicescreen =  servicesscreen.openCustomServiceDetails(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();

			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.clickSaveButton();
			Thread.sleep(2000);
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			Thread.sleep(2000);
			teamworkordersscreen.clickOnWO(inspection);
			teamworkordersscreen.selectWOMonitor();
			RegularOrderMonitorScreen ordermonitorscreen = new RegularOrderMonitorScreen(appiumdriver);
			ordermonitorscreen.selectPanel(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE, "Completed");
			ordermonitorscreen.verifyPanelsStatuses("Paint - Full Bumper", "Active");
			
			ordermonitorscreen.clickServicesButton();
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice4(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartstoadd.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartstoadd[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			Thread.sleep(5000);
			ordermonitorscreen = new RegularOrderMonitorScreen(appiumdriver);
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE, "Queued");
			ordermonitorscreen.selectPanel(UtilConstants.PDRPANEL_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDRPANEL_SUBSERVICE, "Completed");
			
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE, "Active");
			ordermonitorscreen.selectPanel(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE, "Completed");
			
			
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE, "Active");	
			ordermonitorscreen.selectPanel(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE, "Completed");

			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			
			teamworkordersscreen.clickCreateInvoiceForWO(inspection);
			teamworkordersscreen.clickiCreateInvoiceButton();
			teamworkordersscreen.selectWOInvoiceType(UtilConstants.NO_ORDER_TYPE);
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			teamworkordersscreen.clickHomeButton();			
		}
		
		@Test(testName = "Test Case 12629:Test Start Service feature is accurately capturing times", description = "Test Start Service feature is accurately capturing times")
		public void testStartServiceFeatureIsAccuratelyCapturingTimes()
				throws Exception {
			String tcname = "testStartServiceFeatureIsAccuratelyCapturingTimes";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String wonumber = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.DETAIL_SERVICE );
			RegularSelectedServiceDetailsScreen selectedservicescreen =servicesscreen.openCustomServiceDetails(UtilConstants.FRONTLINEREADY_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			Thread.sleep(1000);
			teamworkordersscreen.clickOnWO(wonumber);
			teamworkordersscreen.selectWOMonitor();
			RegularOrderMonitorScreen ordermonitorscreen = new RegularOrderMonitorScreen(appiumdriver);
			Assert.assertTrue(ordermonitorscreen.isServiceIsActive(UtilConstants.FRONTLINEREADY_SUBSERVICE));
			ordermonitorscreen.selectPanel(UtilConstants.FRONTLINEREADY_SUBSERVICE);
			Thread.sleep(1000);
			ordermonitorscreen.clickStartService();
			Thread.sleep(3000);
			ordermonitorscreen.selectPanel(UtilConstants.FRONTLINEREADY_SUBSERVICE);
			ordermonitorscreen.verifyStartServiceDissapeared();
			String srvstartdate = ordermonitorscreen.getServiceStartDate().substring(0, 10);
			ordermonitorscreen.clickServiceDetailsDoneButton();
			ordermonitorscreen.verifyServiceStartDateIsSet(UtilConstants.FRONTLINEREADY_SUBSERVICE, srvstartdate);
			
			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			teamworkordersscreen.clickHomeButton();		
		}
		
		@Test(testName = "Test Case 12631:Test Quantity does not mulitply price in Route package", description = "Test Quantity does not mulitply price in Route package")
		public void testQuantityDoesNotMulitplyPriceInRoutePackage()
				throws Exception {
			String tcname = "testQuantityDoesNotMulitplyPriceInRoutePackage";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String vehiclepart = "Left Roof Rail";
			final String[] vehiclepartspaint = { "Hood", "Left Roof Rail", "Right Fender" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.setServiceQuantityValue("4");
			//selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.clickVehiclePartsCell();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			Thread.sleep(1000);
			selectedservicescreen.selectVehiclePart(vehiclepart);

			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.setServiceQuantityValue("5");
			selectedservicescreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
			invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();		
		}
		
		@Test(testName = "Test Case 12632:Test Delete Work Order function", description = "Test Delete Work Order function")
		public void testDeleteWorkOrderFunction()
				throws Exception {
			String tcname = "testDeleteWorkOrderFunction";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Roof" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routecanadaworkordertype);		
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			String wo = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.deleteWorkOrderViaActionAndSearch(wo);
			Assert.assertFalse(myworkordersscreen.woExistsViaSearch(wo));
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12637:Test changing customer on Inspection", description = "Test changing customer on Inspection")
		public void testChangingCustomerOnInspection()
				throws Exception {
			String tcname = "testChangingCustomerOnInspection";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Front Wheel", "Left Rear Wheel" };

			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen(appiumdriver);
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			
			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.routeworkordertype);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.DETAIL_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WASH_N_VAC_SERVICE);
			//servicesscreen.openServiceDetails(UtilConstants.WASH_N_VAC_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.clickVehiclePartsCell();
		
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			Thread.sleep(4000);
		
			String inspectioncustomer = myinspectionsscreen.getFirstInspectionNumberValue();
			myinspectionsscreen.changeCustomerForInspection(inspectioncustomer, customer);
			myinspectionsscreen.clickHomeButton();
			
			homescreen.clickCustomersButton();
			customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(customer);
			homescreen = new RegularHomeScreen(appiumdriver);
			homescreen.clickMyInspectionsButton();
			myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
			myinspectionsscreen.assertInspectionExists(inspectioncustomer);
			myinspectionsscreen.clickHomeButton();
			
			homescreen.clickCustomersButton();
			customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			homescreen = new RegularHomeScreen(appiumdriver);
			homescreen.clickMyInspectionsButton();
			myinspectionsscreen = new RegularMyInspectionsScreen(appiumdriver);
			myinspectionsscreen.assertInspectionDoesntExists(inspectioncustomer);
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12638:Test Retail Hail package quantity multiplier", description = "Test Retail Hail package quantity multiplier")
		public void testRetailHailPackageQuantityMultiplier() throws Exception {
			String tcname = "testRetailHailPackageQuantityMultiplier";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String servicequantity = "3";
			final String servicequantity2 = "4.5";
			final String totalsumm = "$3,738.00";

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.retailhailworkordertype);
			myworkordersscreen.selectWorkOrderJob(UtilConstants.WO_TYPE_JOB);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.acceptForReminderNoDrilling();

			Helpers.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption());
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.selectOutsideQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
			
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
			RegularClaimScreen claimscreen = new RegularClaimScreen(appiumdriver);
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.setServiceQuantityValue(servicequantity);	
			selectedservicescreen.saveSelectedServiceDetails();
			
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.setServiceQuantityValue(servicequantity2);	
			selectedservicescreen.saveSelectedServiceDetails();		
			//servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(totalsumm);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			ordersummaryscreen.selectWorkOrderDetails("Hail No Discount Invoice");
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.assertOrderSummIsCorrect(totalsumm);
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12641:Test custom WO level split for Route package", description = "Test custom WO level split for Route package")
		public void testCustomWOLevelSplitForRoutePackage() throws Exception {
			String tcname = "testCustomWOLevelSplitForRoutePackage";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Right Mirror", "Left Mirror" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.clickTech();
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.selectTechniciansCustomView();
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA, "70");
			selectedservicescreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB, "30");
			String alerttext = selectedservicescreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);
			
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
			selectedservicescreen =  servicesscreen.openCustomServiceDetails(UtilConstants.DUELEATHER_SUBSERVICE);

			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);

			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.clickTechniciansIcon();
			Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA), "$29.40");
			Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianB), "$12.60");
			selectedservicescreen.selectTechniciansCustomView();
			selectedservicescreen.unselecTechnician(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "31.50");
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%75.00");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "10.50");
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%25.00");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();

			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);

			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.clickVehiclePartsCell();
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.clickTechniciansIcon();
			Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA), "$70.00");
			Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianB), "$30.00");
			selectedservicescreen.selectTechniciansCustomView();
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "28");
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%28.00");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "67");
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%67.00");
			selectedservicescreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "5");
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%5.00");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			ordersummaryscreen.selectWorkOrderDetails(UtilConstants.NO_ORDER_TYPE);
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12642:Test even WO level split for Route package", description = "Test even WO level split for Route package")
		public void testEvenWOLevelSplitForRoutePackage() throws Exception {
			String tcname = "testEvenWOLevelSplitForRoutePackage";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Fender", "Left Front Door", "Left Quarter Panel", "Left Rear Door", "Left Roof Rail" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.clickTech();
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.assertTechnicianIsSelected(UtilConstants.technicianA);
			selectedservicescreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianA), "%50.00");
			Assert.assertEquals(selectedservicescreen.getTechnicianPercentage(UtilConstants.technicianB), "%50.00");

			String alerttext = selectedservicescreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.ALERT_CHANGE_DEFAULT_EMPLOYEES);
			
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.DETAIL_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.FRONTLINEREADY_SUBSERVICE);

			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.OTHER_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));	
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.unselecTechnician(UtilConstants.technicianB);
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), "$108.00");
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%50.00");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);

			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.clickTechniciansIcon();
			selectedservicescreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(selectedservicescreen.getTechnicianPrice(UtilConstants.technicianC), "$80.00");
			Assert.assertEquals(selectedservicescreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%33.33");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			ordersummaryscreen.selectWorkOrderDetails(UtilConstants.NO_ORDER_TYPE);
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12644:Test adding notes to a Work Order", description = "Test adding notes to a Work Order")
		public void testAddingNotesToWorkOrder()
				throws Exception {
			String tcname = "testAddingNotesToWorkOrder";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = {  "Hood", "Left Rear Door", "Right Fender" };
			final String[] vehiclepartspaint = {  "Left Rear Door", "Right Fender" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routecanadaworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.clickNotesButton();
			RegularNotesScreen notesscreen = new RegularNotesScreen(appiumdriver);
			notesscreen.setNotes("Blue fender");
			//notesscreen.clickDoneButton();
			notesscreen.clickSaveButton();
			
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.clickNotesCell();
			notesscreen.setNotes("Declined right door");
			notesscreen.clickSaveButton();
			
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.clickNotesCell();
			notesscreen.setNotes("Declined hood");
			notesscreen.clickSaveButton();
			Thread.sleep(1000);
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenum));
			myinvoicesscreen.selectInvoice(invoicenum);
			myinvoicesscreen.clickNotesPopup();
			notesscreen = new RegularNotesScreen(appiumdriver);
			notesscreen.setNotes("Declined wheel work");
			notesscreen.clickSaveButton();
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12645:Test changing the PO# on an invoice", description = "Test changing the PO# on an invoice")
		public void testChangingThePOOnAnInvoice()
				throws Exception {
			String tcname = "testChangingThePOOnAnInvoice";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.servicedriveworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.setTotalSale("1");
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setPO("832145");
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenum));
			myinvoicesscreen.selectInvoice(invoicenum);
			myinvoicesscreen.clickChangePOPopup();
			myinvoicesscreen.changePO("832710");
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12646:Test editing an Inspection", description = "Test editing an Inspection")
		public void testEditingAnInspection()
				throws Exception {
			String tcname = "testEditingAnInspection";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Front Door", "Left Rear Door", "Right Front Door", "Right Rear Door" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			
			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.routeworkordertype);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			Thread.sleep(2000);
			String insptoapprove = myinspectionsscreen.getFirstInspectionNumberValue();
			myinspectionsscreen.selectInspectionType (insptoapprove);
			myinspectionsscreen.clickEditInspectionButton();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12647:Test editing a Work Order", description = "Test editing a Work Order")
		public void testEditingWorkOrder()
				throws Exception {
			String tcname = "testEditingWorkOrder";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String wo = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreeen.setRO(ExcelUtils.getRO(testcaserow));
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.INTERIOR_FABRIC_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE);
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00");
			servicesscreen.clickBackServicesButton();
			servicesscreen.selectService(UtilConstants.INTERIOR_VINIL_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE);
			selectedservicescreen.saveSelectedServiceDetails();
			
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.68 x 1.00");
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.selectWorkOrderForEidt(wo);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());

			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEELCOVER2_SUBSERVICE);
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.INTERIOR_FABRIC_SERVICE);
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00");
			servicesscreen.clickBackServicesButton();
			servicesscreen.selectService(UtilConstants.INTERIOR_VINIL_SERVICE);
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.68 x 1.00");
			servicesscreen.clickBackServicesButton();
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.WHEELCOVER2_SUBSERVICE, "$45.00 x 1.00");
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickCancelSearchButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19514:Test editing an Invoice in Draft", description = "Test Editing an Invoice in Draft")
		public void testEditingAnInvoiceInDraft()
				throws Exception {
			String tcname = "testEditingAnInvoiceInDraft";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehiclepartspdr = { "Left Fender", "Left Roof Rail", "Right Quarter Panel", "Trunk Lid" };
			final String[] vehiclepartspaint = { "Left Mirror" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String wonum = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails (UtilConstants.CARPETREPAIR_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspdr.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartspdr[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
			invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsDraft();

			myworkordersscreen.clickHomeButton();
			
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			myinvoicesscreen.selectInvoice(invoicenum);
			System.out.println("++++" + invoicenum);
			invoiceinfoscreen = myinvoicesscreen.clickEditPopup();
			Thread.sleep(5000);
			invoiceinfoscreen.clickOnWO(wonum);
			Thread.sleep(2000);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
			selectedservicescreen.removeService();
			servicesscreen.clickToolButton();
			servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			selectedservicescreen.selectVehiclePart("Right Mirror");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickAddServicesButton();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.clickSaveButton();
			invoiceinfoscreen.clickSaveAsFinal();
			homescreen = myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19548:Test adding a PO# to an invoice", description = "Test adding a PO# to an invoice")
		public void testAddingAPOToAnInvoice()
				throws Exception {
			String tcname = "testAddingAPOToAnInvoice";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizardprotrackeravisworkordertype);

			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			String inspection = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));		
			
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.INTERIOR_FABRIC_SERVICE);
			servicesscreen.selectSubService("Tear/Burn >2\" (Fabric)");                                         
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDR6PANEL_SUBSERVICE);
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.assertServiceIsSelected(UtilConstants.PDR6PANEL_SUBSERVICE);
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			servicesscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(inspection);
			RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
			ordermonitorscreen.selectPanel(UtilConstants.PDR6PANEL_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE, "Completed");
			ordermonitorscreen.verifyPanelStatusInPopup("Tear/Burn >2\" (Fabric)", "Active");
			Thread.sleep(4000);
			ordermonitorscreen.selectPanel("Tear/Burn >2\" (Fabric)");
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE, "Completed");
			ordermonitorscreen.verifyPanelStatusInPopup("Tear/Burn >2\" (Fabric)", "Completed");
			Thread.sleep(4000);
			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			Thread.sleep(2000);
			teamworkordersscreen.clickCreateInvoiceForWO(inspection);
			teamworkordersscreen.verifyCreateInvoiceIsActivated(inspection);
			teamworkordersscreen.clickiCreateInvoiceButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			Helpers.selectNextScreen("AVIS Questions");
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			
			questionsscreen.chooseAVISCode("Rental-921");						
			invoiceinfoscreen.clickSaveAsFinal();
			teamworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			myinvoicesscreen.selectInvoice(invoicenum);
			myinvoicesscreen.clickChangePOPopup();
			myinvoicesscreen.changePO("170116");
			Assert.assertTrue(myinvoicesscreen.isInvoiceHasInvoiceNumberIcon(invoicenum));
			Assert.assertTrue(myinvoicesscreen.isInvoiceHasInvoiceSharedIcon(invoicenum));
			myinvoicesscreen.verifyInvoicePrice(invoicenum, PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19670:Test adding a PO# to an invoice containing multiple Work Orders", description = "Test adding a PO# to an invoice containing multiple Work Orders")
		public void testAddingPOToAnInvoiceContainingMultipleWorkOrders()
				throws Exception {
			String tcname1 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders1";
			String tcname2 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders2";
			String tcname3 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders3";
			
			int testcaserow1 = ExcelUtils.getTestCaseRow(tcname1);
			int testcaserow2 = ExcelUtils.getTestCaseRow(tcname2);
			int testcaserow3 = ExcelUtils.getTestCaseRow(tcname3);
			
			final String[] vehicleparts = { "Left Rear Door", "Right Rear Door" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow1));
			String inspection1 = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow1)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			
			//Create second WO
			myworkordersscreen.clickAddOrderButton();
			vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow2));
			String inspection2 = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.LEATHERREPAIR_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow2));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow2)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			
			
			//Create third WO
			myworkordersscreen.clickAddOrderButton();
			vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);

			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow3));
			String inspection3 = vehiclescreeen.getInspectionNumber();
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow3), ExcelUtils.getModel(testcaserow3), ExcelUtils.getYear(testcaserow3));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow3));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow3)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(inspection1);
			RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
			ordermonitorscreen.selectPanel(UtilConstants.PDRVEHICLE_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDRVEHICLE_SUBSERVICE, "Completed");
			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			teamworkordersscreen.clickOnWO(inspection2);
			teamworkordersscreen.selectWOMonitor();
			ordermonitorscreen.selectPanel(UtilConstants.LEATHERREPAIR_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.LEATHERREPAIR_SUBSERVICE, "Completed");
			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			teamworkordersscreen.clickOnWO(inspection3);
			teamworkordersscreen.selectWOMonitor();
			ordermonitorscreen.selectPanel(UtilConstants.BLACKOUT_SUBSERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.BLACKOUT_SUBSERVICE, "Completed");
			teamworkordersscreen = ordermonitorscreen.clickBackButton();

			teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickCreateInvoiceForWO(inspection1);
			teamworkordersscreen.clickCreateInvoiceForWO(inspection2);
			teamworkordersscreen.clickCreateInvoiceForWO(inspection3);
			
			teamworkordersscreen.clickiCreateInvoiceButton();
			teamworkordersscreen.selectWOInvoiceType(UtilConstants.NO_ORDER_TYPE);
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();
			teamworkordersscreen.clickHomeButton();
			
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			myinvoicesscreen.selectInvoice(invoicenum);
			myinvoicesscreen.clickChangePOPopup();
			myinvoicesscreen.changePO("957884");
			myinvoicesscreen.clickHomeButton();	
		}
		
		@Test(testName = "Test Case 19671:Test Copy Vehicle feature", description = "Test Copy Vehicle feature")
		public void testCopyVehicleFeature()
				throws Exception {
			String tcname = "testCopyVehicleFeature";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String wo = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.OTHER_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			Thread.sleep(1000);
			myworkordersscreen.selectWorkOrderForCopyVehicle(wo);
			vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			servicesscreen.cancelOrder();
			myworkordersscreen.clickCancelSearchButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19672:Test Copy Services feature", description = "Test Copy Services feature")
		public void testCopyServicesFeature()
				throws Exception {
			String tcname1 = "testCopyServicesFeature1";
			String tcname2 = "testCopyServicesFeature2";
			
			int testcaserow1 = ExcelUtils.getTestCaseRow(tcname1);
			int testcaserow2 = ExcelUtils.getTestCaseRow(tcname2);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow1));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));
			String wo = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
			
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			myworkordersscreen.selectWorkOrderForCopyServices(wo);
			vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow2));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			servicesscreen.assertServiceIsSelectedAndVisible(UtilConstants.BLACKOUT_SUBSERVICE, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow1)) + " x 1.00");
			servicesscreen.clickBackServicesButton();
			servicesscreen.cancelOrder();
			myworkordersscreen.clickCancelSearchButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 24164:Test Pre-Existing Damage option", description = "Test Pre-Existing Damage option")
		public void testPreExistingDamageOption()
				throws Exception {
			String tcname = "testPreExistingDamageOption";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left A Pillar", "Left Front Door", "Metal Sunroof", "Right Roof Rail" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			
			RegularSettingsScreen settingsscreen = homescreen.clickSettingsButton();
			settingsscreen.setShowAllServicesOn();
			homescreen = settingsscreen.clickHomeButton();
			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.servicedriveinspectiondertype);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails("Scratch (Exterior)");
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.checkPreexistingDamage();
			selectedservicescreen.saveSelectedServiceDetails();	
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			servicesscreen.clickSaveButton();

			String inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
			myinspectionsscreen.selectInspectionType (inspnum);
			
			myinspectionsscreen.clickCreateWOButton();
			String wonumber = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();	
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.setTotalSale("1");
			ordersummaryscreen.clickSaveButton();		
			inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
			Assert.assertEquals(myinspectionsscreen.getFirstInspectionNumberValue(), inspnum);
			vehiclescreeen = myinspectionsscreen.showWorkOrdersForInspection(inspnum);
			Assert.assertEquals(vehiclescreeen.getInspectionNumber(), wonumber);
			servicesscreen.cancelOrder();

			myinspectionsscreen.clickHomeButton();
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			Thread.sleep(2000);
			myworkordersscreen.clickCreateInvoiceIconForWOViaSearch(wonumber);
			myworkordersscreen.clickInvoiceIcon();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickCancelSearchButton();
			homescreen = myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19676:Test Total Sale requirement", description = "Test Total Sale requirement")
		public void testTotalSaleRequirement()
				throws Exception {
			String tcname = "testTotalSaleRequirement";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Hood", "Left Fender" };
			final String totalsale = "675";

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizardprotrackerservicedriveworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.clickSaveButton();
			String alerttext = Helpers.getAlertTextAndAccept();
			ordersummaryscreen.setTotalSale(totalsale);
			Assert.assertEquals(alerttext,  AlertsCaptions.ALERT_TOTAL_SALE_REQUIRED);
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19678:Test package pricing for read only items", description = "Test package pricing for read only items")
		public void testPackagePricingForReadOnlyItems()
				throws Exception {
			String tcname = "testPackagePricingForReadOnlyItems";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Fender", "Left Roof Rail", "Right Fender" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreeen.setRO(ExcelUtils.getRO(testcaserow));
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.INTERIOR_LEATHER_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CRT_2_RPR_LTHR_SUBSERVICE);
			Assert.assertEquals(selectedservicescreen.getAdjustmentsValue(), "-$14.48");
			selectedservicescreen.setServiceQuantityValue("3.00");
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.CUST2PNL_SUBSERVICE);
			Assert.assertEquals(selectedservicescreen.getAdjustmentsValue(), "-$6.55");
			selectedservicescreen.clickVehiclePartsCell();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();

			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
					
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.clickSaveAsFinal();
			vehiclescreeen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19681:Test price policy for service items from Inspection through Invoice creation", description = "Test price policy for service items from Inspection through Invoice creation")
		public void testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation()
				throws Exception {
			String tcname = "testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Front Door", "Left Rear Door", "Right Fender" };
			final String[] vehiclepartspaint = { "Hood", "Left Fender" };
			 
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.routeworkordertype);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
				
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();	
			servicesscreen.clickSaveButton();
			Thread.sleep(1000);
			String inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
			Assert.assertEquals(myinspectionsscreen.getFirstInspectionPriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			myinspectionsscreen.selectInspectionType (inspnum);
			myinspectionsscreen.clickCreateWOButton();
			
			String wonumber = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);			
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.PDRVEHICLE_SUBSERVICE, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)) + " x 1.00");
			servicesscreen.clickBackServicesButton();
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			servicesscreen.assertServiceIsSelectedWithServiceValues(UtilConstants.PAINTPANEL_SUBSERVICE, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)) + " x 1.00");
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSaveButton();
			
			inspnum = myinspectionsscreen.getFirstInspectionNumberValue();
			Assert.assertEquals(myinspectionsscreen.getFirstInspectionNumberValue(), inspnum);
			vehiclescreeen = myinspectionsscreen.showWorkOrdersForInspection(inspnum);
			Assert.assertEquals(vehiclescreeen.getInspectionNumber(), wonumber);
			servicesscreen.cancelOrder();

			myinspectionsscreen.clickHomeButton();
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			Thread.sleep(1000);
			myworkordersscreen.clickCreateInvoiceIconForWOViaSearch(wonumber);
			myworkordersscreen.clickInvoiceIcon();
			Thread.sleep(1000);
			RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickCancelSearchButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10263:Send Multiple Emails", description = "Send Multiple Emails")
		public void testSendMultipleEmails()
				throws Exception {		
			 
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			myinvoicesscreen.clickActionButton();
			for (int i = 0; i< 4; i++) {
				myinvoicesscreen.selectInvoiceForActionByIndex(i+1);
			}
			myinvoicesscreen.clickActionButton();
			//myinvoicesscreen.sendEmail();
			myinvoicesscreen.sendSingleEmail(UtilConstants.TEST_EMAIL);
			myinvoicesscreen.clickDoneButton();
			myinvoicesscreen.clickHomeButton();			
		}
		
		@Test(testName = "Test Case 19683:Test Work Order Discount Override feature", description = "Test Work Order Discount Override feature")
		public void testWorkOrderDiscountOverrideFeature() throws Exception {		
			String tcname = "testWorkOrderDiscountOverrideFeature";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String  _customer   = "Bel Air Auto Auction Inc";
			final String[] vehicleparts = { "Left Fender", "Right Fender"};

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(_customer);			
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServiceAdjustmentsValue("-$28.50");
			selectedservicescreen.clickVehiclePartsCell();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WSANDBPANEL_SERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			selectedservicescreen.selectVehiclePart("Hood");
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
				
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
			invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19685:Test completed RO only requirement for invoicing", description = "Test Completed RO only requirement for invoicing")
		public void testCompletedROOnlyRequirementForInvoicing()
				throws Exception {
			String tcname = "testCompletedROOnlyRequirementForInvoicing";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Quarter Panel", "Right Roof Rail", "Trunk Lid" };
			final String[] vehiclepartswheel = { "Right Front Wheel", "Right Rear Wheel" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.wizprotrackerrouteworkordertype);
			
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String wonumber = vehiclescreeen.getInspectionNumber();
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			new SelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTTOWHOOKCOVER_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
					
			servicesscreen.selectService(UtilConstants.WHEELS_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedservicescreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(wonumber);
			teamworkordersscreen.selectEditWO();
			Thread.sleep(2000);
			Helpers.selectNextScreen(OrderSummaryScreen
					.getOrderSummaryScreenCaption());
			Assert.assertFalse(ordersummaryscreen.checkApproveAndCreateInvoiceExists());
			ordersummaryscreen.clickSaveButton();
			Thread.sleep(3000);
			teamworkordersscreen.clickCreateInvoiceForWO(wonumber);
			Thread.sleep(3000);
			teamworkordersscreen.clickiCreateInvoiceButton();
			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
			teamworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10659:Test Enterprise Work Order question forms inforced", description = "Test Enterprize Work Order question forms inforced")
		public void testEnterprizeWorkOrderQuestionFormsInforced()
				throws Exception {
			String tcname = "testEnterprizeWorkOrderQuestionFormsInforced";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.enterpriseworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			Helpers.selectNextScreen(RegularEnterpriseBeforeDamageScreen.getEnterpriseBeforeDamageScreenCaption());
			RegularEnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new RegularEnterpriseBeforeDamageScreen(appiumdriver);
			enterprisebeforedamagescreen.clickSaveButton();
			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertEquals(alerttext, "Warning! Question 'VIN' in section 'Enterprise Before Damage' should be answered.");
			enterprisebeforedamagescreen.setVINCapture();
			enterprisebeforedamagescreen.clickSaveButton();
			alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertEquals(alerttext, "Warning! Question 'License Plate' in section 'Enterprise Before Damage' should be answered.");
			enterprisebeforedamagescreen.setLicensePlateCapture();
			
			Helpers.selectNextScreen(UtilConstants.ENTERPRISE_AFTER_REPAIR_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12633:Test successful email of pictures using Notes feature", description = "Test successful email of pictures using Notes feature")
		public void testSuccessfulEmailOfPicturesUsingNotesFeature()
				throws Exception {
			String tcname = "testSuccessfulEmailOfPicturesUsingNotesFeature";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.avisworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
			String wo = vehiclescreeen.getInspectionNumber();
			RegularNotesScreen notesscreen = vehiclescreeen.clickNotesButton();
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
			notesscreen.clickSaveButton();
		
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.clickNotesButton();
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 2);
			notesscreen.clickSaveButton();
			
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDR2PANEL_SUBSERVICE);
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			selectedservicescreen.clickNotesCell();
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
			notesscreen.clickSaveButton();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(EnterpriseBeforeDamageScreen.getEnterpriseBeforeDamageScreenCaption());
			RegularEnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new RegularEnterpriseBeforeDamageScreen(appiumdriver);
			enterprisebeforedamagescreen.setVINCapture();
			enterprisebeforedamagescreen.setLicensePlateCapture();
			
			Helpers.selectNextScreen(UtilConstants.ENTERPRISE_AFTER_REPAIR_SCREEN_CAPTION);
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.selectWorkOrderForAddingNotes(wo);
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 3);
			notesscreen.clickSaveButton();
			
			myworkordersscreen.clickCreateInvoiceIconForWO(wo);
			myworkordersscreen.clickInvoiceIcon();
			InvoiceInfoScreen invoiceinfoscreen = new InvoiceInfoScreen(appiumdriver);
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			Helpers.selectNextScreen("AVIS Questions");
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			questionsscreen.chooseAVISCode("Other-920");			
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickCancelSearchButton();
			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			myinvoicesscreen.selectInvoice(invoicenum);
			notesscreen =  myinvoicesscreen.clickNotesPopup();
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
			notesscreen.clickSaveButton();
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.selectInvoiceForActionByIndex(0);
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.sendEmail(UtilConstants.TEST_EMAIL);
			myinvoicesscreen.clickDoneButton();
			myinvoicesscreen.clickHomeButton();	
		}
		
		@Test(testName = "Test Case 12634:Test emailing photos in Economical Inspection", description = "Test emailing photos in Economical Inspection")
		public void testEmailingPhotosInEconomicalInspection()
				throws Exception {
			String tcname = "testEmailingPhotosInEconomicalInspection";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			
			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();

			myinspectionsscreen.clickAddInspectionButton();
			myinspectionsscreen.selectInspectionType (UtilConstants.economicalinspectiondertype);
			RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			//String wo = vehiclescreeen.getInspectionNumber();
			RegularNotesScreen notesscreen = vehiclescreeen.clickNotesButton();
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
			notesscreen.clickSaveButton();
		
			Helpers.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION);
			Helpers.selectNextScreen(UtilConstants.HAIL_PHOTOS_SCREEN_CAPTION);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			questionsscreen.makeCaptureForQuestionRegular("VIN");
			Thread.sleep(2000);
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Odometer");
			Thread.sleep(2000);
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("License Plate Number");
			Thread.sleep(2000);
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Left Front of Vehicle");
			Thread.sleep(2000);
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Right Front of Vehicle");
			Thread.sleep(2000);
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Right Rear of Vehicle");
			Thread.sleep(2000);
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Left Rear of Vehicle");
			Helpers.selectNextScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectProperQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.CANADA, ExcelUtils.getOwnerZip(retailhaildatarow));
			
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.clickToolButton();
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails("E-Coat");
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.clickNotesCell();
			notesscreen.addNotesCapture();
			notesscreen.clickSaveButton();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickAddServicesButton();
			
			Helpers.selectNextScreen(UtilConstants.PRICE_MATRIX_SCREEN_CAPTION);
			RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
			pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
			pricematrix.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
			pricematrix.clickSaveButton();
			pricematrix.clickNotesButton();
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 2);
			notesscreen.clickSaveButton();
			pricematrix.clickSaveButton();
			
			Thread.sleep(13000);
			String insptoapprove = myinspectionsscreen.getFirstInspectionNumberValue();
			myinspectionsscreen.selectInspectionType (insptoapprove);
			RegularMyInvoicesScreen myinvoicesscreen = new RegularMyInvoicesScreen(appiumdriver);
			Thread.sleep(5000);
			myinvoicesscreen.sendEmail(UtilConstants.TEST_EMAIL);
			myinspectionsscreen.clickHomeButton();			
		}
		
		@Test(testName = "Test Case 12635:Test emailing photos in Auction package", description = "Test emailing photos in Auction package")
		public void testEmailingPhotosInAuctionPackage()
				throws Exception {
			String tcname = "testEmailingPhotosInAuctionPackage";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Hood", "Left Fender", "Right Fender", "Roof" };
			final String firstnote = "Refused paint";
			final String secondnote = "Just 4 panels";

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.auctionworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			String wo = vehiclescreeen.getInspectionNumber();
			RegularNotesScreen notesscreen = vehiclescreeen.clickNotesButton();
			notesscreen.setNotes(firstnote);
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
			notesscreen.clickSaveButton();
		
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.clickVehiclePartsCell();
			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}

			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.clickNotesCell();
			notesscreen.setNotes(secondnote);
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
			notesscreen.clickSaveButton();
			selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			Helpers.selectNextScreen(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			Helpers.screenIsDisplayed(UtilConstants.QUESTIONS_SCREEN_CAPTION);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
			questionsscreen.chooseConsignor("Unknown Consignor/One Off-718");
			
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.selectWorkOrderForAddingNotes(wo);
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 2);
			notesscreen.clickSaveButton();
			
			myworkordersscreen.clickCreateInvoiceIconForWO(wo);
			myworkordersscreen.clickInvoiceIcon();
			RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType("Auction - No Discount Invoice");
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickCancelSearchButton();
			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.selectInvoiceForActionByIndex(0);
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.sendEmail(UtilConstants.TEST_EMAIL);
			myinvoicesscreen.clickDoneButton();
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12636:Test emailing photos in Service Drive package", description = "Test emailing photos in Service Drive package")
		public void testEmailingPhotosInServiceDrivePackage()
				throws Exception {
			String tcname = "testEmailingPhotosInServiceDrivePackage";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Decklid", "Left A Pillar" };

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.servicedriveworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
				
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			
			servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
			selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.STAIN_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
						.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.setTotalSale("1");
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
			myinvoicesscreen.selectInvoice(invoicenum);
			RegularNotesScreen notesscreen = myinvoicesscreen.clickNotesPopup();
			notesscreen.setNotes("Refused paint");
			notesscreen.addNotesCapture();
			Assert.assertEquals(notesscreen.getNumberOfAddedPhotos(), 1);
			notesscreen.clickSaveButton();
			
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.selectInvoiceForActionByIndex(0);
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.sendEmail(UtilConstants.TEST_EMAIL);
			myinvoicesscreen.clickDoneButton();
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 24131:Test PO# saves with active keyboard on WO summary screen", description = "Test PO# saves with active keyboard on WO summary screen")
		public void testPONumberSavesWithActiveKeyboardOnWOSummaryScreen()
				throws Exception {
			String tcname = "testPONumberSavesWithActiveKeyboardOnWOSummaryScreen";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String _po = "998601";
			final String[] vehicleparts = { "Hood", "Left Quarter Panel", "Right Roof Rail" };
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.routeusworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedservicescreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedservicescreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedservicescreen.saveSelectedServiceDetails();
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.assertTotalAmauntIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.assertOrderSummIsCorrect(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(UtilConstants.NO_ORDER_TYPE);
			invoiceinfoscreen.setPOWithoutHidingkeyboard(_po);
			invoiceinfoscreen.clickSaveAsFinal();			
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 24132:Test Total Sale saves with active keyboard on WO summary screen", description = "Test Total Sale saves with active keyboard on WO summary screen")
		public void testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen()
				throws Exception {
			String tcname = "testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			final String totalsale = "675";

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.servicedriveworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			RegularSelectedServiceDetailsScreen selectedservicescreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
			selectedservicescreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedservicescreen.assertServicePriceValue(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedservicescreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.setTotalSaleWithoutHidingkeyboard(totalsale);
			ordersummaryscreen.clickSaveButton();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19673:Test Car History feature", description = "Test Car History feature")
		public void testCarHistoryFeature()
				throws Exception {
			String tcname = "testCarHistoryFeature";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen(appiumdriver);
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreeen = myworkordersscreen.selectWorkOrderType(UtilConstants.carmaxworkordertype);
			vehiclescreeen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreeen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreeen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreeen.setRO(ExcelUtils.getRO(testcaserow));
			Helpers.selectNextScreen(RegularServicesScreen.getServicesScreenCaption());
			RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
			servicesscreen.selectService(UtilConstants.INTERIORPLASTIC_SERVICE);
			servicesscreen.selectService(UtilConstants.SCRTCH_1_SECTPLSTC_SERVICE);
			servicesscreen.clickBackServicesButton();
			Helpers.selectNextScreen(RegularOrderSummaryScreen
					.getOrderSummaryScreenCaption());
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen(appiumdriver);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSaveButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
			String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();	
			RegularCarHistoryScreen carhistoryscreen = new RegularCarHistoryScreen(appiumdriver); 
			carhistoryscreen.clickHomeButton();
			carhistoryscreen = homescreen.clickCarHistoryButton();
			carhistoryscreen.searchCar("887340");
			String strtocompare = ExcelUtils.getYear(testcaserow) + ", " + ExcelUtils.getMake(testcaserow) + ", " + ExcelUtils.getModel(testcaserow);
			Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
			Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
			carhistoryscreen.clickFirstCarHistoryInTable();
			RegularMyInvoicesScreen myinvoicesscreen = carhistoryscreen.clickCarHistoryInvoices();
			Assert.assertTrue(myinvoicesscreen.myInvoicesIsDisplayed());
			myinvoicesscreen.clickHomeButton();
			carhistoryscreen.clickHomeButton();
			
			carhistoryscreen.clickSwitchToWeb();
			Thread.sleep(5000);
			Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
			Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
			carhistoryscreen.clickFirstCarHistoryInTable();
			carhistoryscreen.clickCarHistoryInvoices();		

			Assert.assertTrue(myinvoicesscreen.teamInvoicesIsDisplayed());
			Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenumber));
			myinvoicesscreen.clickHomeButton();
			carhistoryscreen.clickCancelSearchButton();
			carhistoryscreen.clickHomeButton();
		}
}

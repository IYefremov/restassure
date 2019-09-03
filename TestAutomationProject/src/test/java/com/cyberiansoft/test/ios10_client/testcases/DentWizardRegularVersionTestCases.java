package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.DentWizardInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.DentWizardInvoiceTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.DentWizardWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import io.appium.java_client.ios.IOSElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DentWizardRegularVersionTestCases extends ReconProDentWizardBaseTestCase {

	public RegularHomeScreen homescreen;
		
	@BeforeClass
	public void setUpSuite() throws Exception {
		mobilePlatform = MobilePlatform.IOS_REGULAR;
		initTestUser(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);

		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
				ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(),
				"DW_Automation3", envType);

		RegularMainScreen mainscr = new RegularMainScreen();
		mainscr.userLogin(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
		RegularSettingsScreen settingscreen = homescreen.clickSettingsButton();
		settingscreen.setShowAvailableSelectedServicesOn();
		settingscreen.setInsvoicesCustomLayoutOff();
		homescreen = settingscreen.clickHomeButton();
		ExcelUtils.setDentWizardExcelFile();
	}

	@BeforeMethod
	public void setUpCustomer() {
		if (!homescreen.getActiveCustomerValue().equals(UtilConstants.TEST_CUSTOMER_FOR_TRAINING)) {
			RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
			customersscreen.swtchToWholesaleMode();
			homescreen = customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		}
	}

		@Test(testName = "Test Case 10264:Test Valid VIN Check", description = "Test Valid VIN Check")
		public void testValidVINCheck() {
			final String tcname = "testValidVINCheck";
			final int tcrow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(tcrow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(tcrow), ExcelUtils.getModel(tcrow), ExcelUtils.getYear(tcrow));
			RegularNavigationSteps.navigateToServicesScreen();
			RegularWorkOrdersSteps.cancelCreatingWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10451:Test Top Customers Setting", description = "Test Top Customers Setting")
		public void testTopCustomersSetting() {
			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen();
			settingsscreen.setShowTopCustomersOn();
			settingsscreen.clickHomeButton();
			
			RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
            Assert.assertTrue(customersscreen.checkTopCustomersExists());
			Assert.assertTrue(customersscreen.checkCustomerExists(UtilConstants.TEST_CUSTOMER_FOR_TRAINING));
			customersscreen.clickHomeButton();
			
			homescreen.clickSettingsButton();
			settingsscreen = new RegularSettingsScreen();
			settingsscreen.setShowTopCustomersOff();
			settingsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10452:Test VIN Duplicate check", description = "Test VIN Duplicate check")
		public void testVINDuplicateCheck() {
			
			final String tcname = "testVINDuplicateCheck";
			final int tcrow = ExcelUtils.getTestCaseRow(tcname);

			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen();
			settingsscreen.setCheckDuplicatesOn();
			settingsscreen.clickHomeButton();

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			String searchresult = vehiclescreen.setVINAndAndSearch(ExcelUtils.getVIN(tcrow).substring(
					0, 11));
			Assert.assertEquals(searchresult, "No vehicle invoice history found");
			vehiclescreen.setVINValue(ExcelUtils.getVIN(tcrow).substring(10, 17));
			String msg = vehiclescreen.getExistingWorkOrdersDialogMessage();
			Assert.assertTrue(msg.contains("Existing work orders were found"), msg);
			if (DriverBuilder.getInstance().getAppiumDriver().findElementsByAccessibilityId("Close").size() > 0)
				DriverBuilder.getInstance().getAppiumDriver().findElementByAccessibilityId("Close").click();
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
			homescreen.clickSettingsButton();
			settingsscreen = new RegularSettingsScreen();
			settingsscreen.setCheckDuplicatesOff();
			settingsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10453:Test Vehicle Part requirement", description = "Test Vehicle Part requirement")
		public void testVehiclePartRequirement() {
			final String tcname = "testVehiclePartRequirement";
			final int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen();

			settingsscreen.setInsvoicesCustomLayoutOff();
			settingsscreen.setShowAvailableSelectedServicesOn();
			settingsscreen.setCheckDuplicatesOn();
			settingsscreen.clickHomeButton();

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			Assert.assertTrue(servicesscreen
					.isServiceTypeExists(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE));
			Assert.assertTrue(servicesscreen.isServiceTypeExists(UtilConstants.PDRPANEL_SUBSERVICE));
			Assert.assertTrue(servicesscreen.isServiceTypeExists(UtilConstants.PDRVEHICLE_SUBSERVICE));
			Assert.assertTrue(servicesscreen.isServiceTypeExists(UtilConstants.PDRPANEL_HAIL_SUBSERVICE));
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickCancelButton();
			servicesscreen.clickBackServicesButton();
			RegularWorkOrdersSteps.cancelCreatingWorkOrder();
			myworkordersscreen.clickHomeButton();

			homescreen.clickSettingsButton();
			settingsscreen = new RegularSettingsScreen();
			settingsscreen.setCheckDuplicatesOff();
			settingsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10455:Test turning multiple Work Orders into a single Invoice", description = "Test turning multiple Work Orders into a single Invoice")
		public void testTurningMultipleWorkOrdersIntoASingleInvoice() {
		    String tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice1";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Fender", "Left Front Door",
					"Roof" };
			final String[] vehiclepartswheels = { "Left Front Wheel",
					"Right Front Wheel" };

			RegularSettingsScreen settingsScreen = homescreen.clickSettingsButton();
			settingsScreen.setInsvoicesCustomLayoutOff();
			settingsScreen.clickHomeButton();
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));

			String wo1 = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheels.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheels[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();

			// ==================Create second WO=============
			tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice2";
			testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts2 = { "Hood", "Roof", "Trunk Lid" };
			final String[] vehiclepartspaints = { "Front Bumper", "Rear Bumper" };

			myworkordersscreen.clickAddOrderButton();
			vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			String wo2 = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts2.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts2[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$41.66");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), "$41.67");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), "$41.67");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaints.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaints[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen
					.setTechnicianCustomPriceValue(UtilConstants.technicianA, "165");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB,
					"50");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA),
					"$165.00");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB),
					"$50.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			final String[] wos = {wo1, wo2};
			myworkordersscreen.clickCreateInvoiceIconForWOs(wos);
			myworkordersscreen.clickInvoiceIcon();
			RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
            Assert.assertTrue(invoiceinfoscreen.isWOSelected(wo1));
			Assert.assertTrue(invoiceinfoscreen.isWOSelected(wo2));
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen = new RegularMyWorkOrdersScreen();
			Assert.assertFalse(myworkordersscreen.woExists(wo1));
			Assert.assertFalse(myworkordersscreen.woExists(wo2));
			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			final String wosvalue = wo1 + ", " +  wo2;
			Assert.assertEquals(wosvalue, myinvoicesscreen.getWOsForInvoice(invoicenum));
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10651:Test same Order Type required for turning multiple Work Orders into a single Invoice", description = "Test same Order Type required for turning multiple Work Orders into a single Invoice")
		public void testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice() {
			String tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice1";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Roof" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routecanadaworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			String wo1 = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			for (int i = 0; i <= vehicleparts.length; i++) {
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();

			// ==================Create second WO=============
			tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice2";
			testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts2 = { "Hood" };

			myworkordersscreen.clickAddOrderButton();
			vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			String wo2 = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts2.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts2[i]);
			}
			for (int i = 0; i <= vehicleparts2.length; i++) {
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			final String[] wos = {wo1, wo2};
			myworkordersscreen.clickCreateInvoiceIconForWOs(wos);
			
			myworkordersscreen.clickInvoiceIcon();
			String alerttext = myworkordersscreen
					.selectInvoiceTypeAndAcceptAlert(DentWizardInvoiceTypes.NO_ORDER_TYPE);
			Assert.assertEquals(
					alerttext,
					"Invoice type " + DentWizardInvoiceTypes.NO_ORDER_TYPE.getInvoiceTypeName() + " doesn't support multiple Work Order types.");
			myworkordersscreen.clickCancel();
			myworkordersscreen.clickHomeButton();

		}
		
		@Test(testName = "Test Case 10652:Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker", description = "Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker")
		public void testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker() {
			String tcname = "testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Fender", "Left Front Door",
					"Left Quarter Panel" };

			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen();
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();
			
			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen();
			myinspectionsscreen.clickAddInspectionButton();
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.wizprotrackerrouteinspectiontype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			final String inspNumber = vehiclescreen.getInspectionNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularInspectionsSteps.saveInspection();
			RegularMyInspectionsSteps.selectInspectionForApprove(inspNumber);
			RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();

			approveinspscreen.clickApproveButton();
			approveinspscreen.clickSignButton();
			approveinspscreen.drawApprovalSignature();
			myinspectionsscreen = new RegularMyInspectionsScreen();
			Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inspNumber));
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10653:Test Inspections convert to Work Order", description = "Test Inspections convert to Work Order")
		public void testInspectionsConvertToWorkOrder() {
			String tcname = "testInspectionsConvertToWorkOrder";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Right Fender", "Right Roof Rail", 
					"Right Rear Door" };

			final String[] vehicleparts2 = { "Left Mirror", "Right Mirror" };
			
			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen();
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();
			
			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen();
			myinspectionsscreen.clickAddInspectionButton();
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.wizprotrackerrouteinspectiontype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String inpnumber = vehiclescreen.getInspectionNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts2.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts2[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
            Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), "$930.00");

			RegularInspectionsSteps.saveInspection();
			RegularMyInspectionsSteps.selectInspectionForApprove(inpnumber);
			RegularApproveInspectionsScreen approveinspscreen =  new RegularApproveInspectionsScreen();
			approveinspscreen.clickApproveButton();
			approveinspscreen.clickSignButton();
			approveinspscreen.drawApprovalSignature();
			myinspectionsscreen = new RegularMyInspectionsScreen();
			Assert.assertTrue(myinspectionsscreen.isInspectionIsApproved(inpnumber));
			myinspectionsscreen.clickOnInspection(inpnumber);
			myinspectionsscreen.clickCreateWOButton();
			vehiclescreen = new RegularVehicleScreen();
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			
			Assert.assertEquals(inpnumber.substring(0, 1), "E");
			Assert.assertEquals(workOrderNumber.substring(0, 1), "O");
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			RegularWizardScreensSteps.clickSaveButton();
			myinspectionsscreen.waitMyInspectionsScreenLoaded();
			myinspectionsscreen.clickHomeButton();
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			myworkordersscreen.woExists(workOrderNumber);
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10654:Test 'Vehicle' Service does not multiply price entered when selecting multiple panels", description = "Test 'Vehicle' Service does not multiply price entered when selecting multiple panels")
		public void testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels() {
			String tcname = "testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Hood", "Roof", "Trunk Lid" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
            Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

			RegularWorkOrdersSteps.cancelCreatingWorkOrder();
			myworkordersscreen.clickHomeButton();

			homescreen.clickSettingsButton();
			RegularSettingsScreen settingsscreen = new RegularSettingsScreen();
			settingsscreen.setCheckDuplicatesOff();
			settingsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10655:Test 'Panel' Service multiplies price entered when selecting multiple panels", description = "Test 'Panel' Service multiplies price entered when selecting multiple panels")
		public void testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels() {
			String tcname = "testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Fender", "Left Front Door",
					"Left Rear Door" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
            Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

			RegularWorkOrdersSteps.cancelCreatingWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10656:Test Carmax vehicle information requirements", description = "Test Carmax vehicle information requirements")
		public void testCarmaxVehicleInformationRequirements() {
			String tcname = "testCarmaxVehicleInformationRequirements";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.carmaxworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.clickChangeScreen();
			Assert.assertTrue(vehiclescreen.clickSaveWithAlert().contains("Stock# is required"));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreen.clickChangeScreen();
			Assert.assertTrue(vehiclescreen.clickSaveWithAlert().contains("RO# is required"));
			vehiclescreen.setRO(ExcelUtils.getRO(testcaserow));
			RegularNavigationSteps.navigateToServicesScreen();
			RegularWorkOrdersSteps.cancelCreatingWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10657:Test Service Drive requires Advisor", description = "Test Service Drive requires Advisor")
		public void testServiceDriveRequiresAdvisor() {
			String tcname = "testServiceDriveRequiresAdvisor";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen();
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.servicedriveworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.clickChangeScreen();
			Assert.assertTrue(vehiclescreen.clickSaveWithAlert().contains("Advisor is required"));
			vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			RegularNavigationSteps.navigateToServicesScreen();
			RegularWorkOrdersSteps.cancelCreatingWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName= "Test Case 10658:Test Inspection requirments inforced", description = "Test Inspection requirements inforced")
		public void testInspectionRequirementsInforced() {
			String tcname = "testInspectionRequirementsInforced";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
			myinspectionsscreen.clickAddInspectionButton();
            RegularVehicleScreen vehicleScreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.wizardprotrackerrouteinspectiondertype);
			vehicleScreen.clickSave();
			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext.contains("VIN# is required"));
			vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehicleScreen.clickSave();
			alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext.contains("Advisor is required"));
			vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			RegularInspectionsSteps.saveInspection();
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10663:Test Inspections can convert to multiple Work Orders", description = "Test Inspections can convert to multiple Work Orders")
		public void testInspectionsCanConvertToMultipleWorkOrders() {
			String tcname = "testInspectionsCanConvertToMultipleWorkOrders";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularSettingsScreen settingsscreen = homescreen.clickSettingsButton();
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();
			
			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen();
			myinspectionsscreen.clickAddInspectionButton();
			RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.routecanadainspectiontype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			final String inspNumber = vehiclescreen.getInspectionNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.INTERIORBURNS_SUBSERVICE);

			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularInspectionsSteps.saveInspection();
			RegularMyInspectionsSteps.selectInspectionForCreatingWO(inspNumber);
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();;
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			Assert.assertEquals(workOrderNumber.substring(0, 1), "O");
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			RegularWizardScreensSteps.clickSaveButton();
			RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspNumber);
			vehiclescreen = new RegularVehicleScreen();
			Assert.assertEquals(vehiclescreen.getWorkOrderNumber(), workOrderNumber);
			servicesscreen.clickCancel();

			RegularMyInspectionsSteps.selectInspectionForCreatingWO(inspNumber);
			String workOrderNumber2 = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			Assert.assertEquals(workOrderNumber2.substring(0, 1), "O");
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();
			RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspNumber);

			Assert.assertEquals(myinspectionsscreen.getNumberOfWorkOrdersForIspection(), 2);
			Assert.assertTrue(myinspectionsscreen.isWorkOrderForInspectionExists(workOrderNumber2));
			myinspectionsscreen.clickHomeButton();

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			Assert.assertTrue(myworkordersscreen.woExists(workOrderNumber));
			Assert.assertTrue(myworkordersscreen.woExists(workOrderNumber2));
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10665:Test Archive feature for Inspections", description = "Test Archive feature for Inspections")
		public void testArchiveFeatureForInspections() {
			String tcname = "testArchiveFeatureForInspections";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularSettingsScreen settingsscreen = homescreen.clickSettingsButton();
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();

			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
			myinspectionsscreen.clickAddInspectionButton();
            RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.routeinspectiontype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			final String inspNumber = vehiclescreen.getInspectionNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.OTHER_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularInspectionsSteps.saveInspection();
			myinspectionsscreen.clickOnInspection(inspNumber);
			myinspectionsscreen.clickArchiveInspectionButton();
			myinspectionsscreen.clickFilterButton();
			myinspectionsscreen.clickStatusFilter();
			Assert.assertTrue(myinspectionsscreen.checkFilterStatusIsSelected("New"));
			Assert.assertTrue(myinspectionsscreen.checkFilterStatusIsSelected("Approved"));
			myinspectionsscreen.clickFilterStatus("New");
			myinspectionsscreen.clickFilterStatus("Approved");
			myinspectionsscreen.clickFilterStatus("Archived");
			Assert.assertTrue(myinspectionsscreen.checkFilterStatusIsSelected("Archived"));
			myinspectionsscreen.clickBackButton();
			myinspectionsscreen.clickSaveFilterDialogButton();

			Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspNumber));
			Assert.assertEquals(myinspectionsscreen.checkFilterIsApplied(), true);
			myinspectionsscreen.clearFilter();
			myinspectionsscreen.clickSaveFilterDialogButton();
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11731:Test even WO level tech split for Wholesale Hail", description = "Test even WO level tech split for Wholesale Hail")
		public void testEvenWOLevelTechSplitForWholesaleHail() {
			String tcname = "testEvenWOLevelTechSplitForWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left A Pillar", "Left Fender",
					"Left Rear Door", "Roof" };


			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreen.clickTech();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.selectTechniciansEvenlyView();
			String alerttext = selectedServiceDetailsScreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);

			selectedServiceDetailsScreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}			
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(UtilConstants.FIXPRICE_SERVICE));
			selectedServicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
					vehicleparts[0]);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , "$105.00 x 1.00"));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , "$0.00 x 1.00"));

			selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
			Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
					vehicleparts[1]);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianB));
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianNotSelected(UtilConstants.technicianB));
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA),
					PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$70.00");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), "$70.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$0.00 x 1.00"));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00"));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$140.00 x 1.00"));

			selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
			Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
					vehicleparts[2]);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianB));
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianNotSelected(UtilConstants.technicianB));
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianD);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$30.00");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianD), "$30.00");
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianNotSelected(UtilConstants.technicianA));
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00"));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$140.00 x 1.00"));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$60.00 x 1.00"));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$0.00 x 1.00"));

			selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
			Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
					vehicleparts[3]);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$275.00 x 1.00"));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00"));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$140.00 x 1.00"));
			Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$60.00 x 1.00"));

			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11732:Test even service level tech split for Wholesale Hail", description = "Test even service level tech split for Wholesale Hail")
		public void testEvenServiceLevelTechSplitForWholesaleHail() {
			String tcname = "testEvenServiceLevelTechSplitForWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Hood", "Right Quarter Panel",
					"Sunroof" };
			
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();

			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$47.50");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), "$47.50");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_TOTAL_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA),
					"$175.00");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB),
					"$175.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen = new RegularServicesScreen();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11733:Test Custom WO level tech split for wholesale Hail", description = "Test Custom WO level tech split for wholesale Hail")
		public void testCustomWOLevelTechSplitForWholesaleHail() {
			String tcname = "testCustomWOLevelTechSplitForWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Cowl, Other", "Left Fender",
					"Trunk Lid" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreen.clickTech();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA),
					"%100.00");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, ExcelUtils.getServicePrice(testcaserow));
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA),
					"%85.00");

			String alerttext = selectedServiceDetailsScreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertTrue(alerttext.contains("Total amount is not equal 100%"));
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "15");

			alerttext = selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

			selectedServiceDetailsScreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_ND_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$93.50");
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), "$16.50");
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "50");
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%45.45");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "60");		
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%54.55");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen = new RegularServicesScreen();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11734:Test Custom service level tech split for wholesale Hail", description = "Test Custom service level tech split for wholesale Hail")
		public void testCustomServiceLevelTechSplitForWholesaleHail() {
			String tcname = "testCustomServiceLevelTechSplitForWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Fender", "Left Quarter Panel", "Right Rear Door",
					"Trunk Lid" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

			selectedServiceDetailsScreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
			selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 0);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "45");	
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%36.00");
			String alerttext = selectedServiceDetailsScreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertTrue(alerttext.contains("Split amount should be equal to total amount."));
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "80");	
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%64.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();		
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "25");	
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%20.00");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "100");	
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%80.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "50");	
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%40.00");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "75");	
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%60.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "30");	
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%24.00");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "95");	
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%76.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen = new RegularServicesScreen();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			invoiceinfoscreen.clickSaveAsFinal();
            myworkordersscreen = new RegularMyWorkOrdersScreen();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11735:Test Customer Discount on Wholesale Hail", description = "Test Customer Discount on Wholesale Hail")
		public void testCustomerDiscountOnWholesaleHail() {
			String tcname = "testCustomerDiscountOnWholesaleHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Hood", "Roof", "Trunk Lid" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("Customer Discount");
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen = new RegularServicesScreen();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10668:Test Quick Quote option for Retail Hail", description = "Test Quick Quote option for Retail Hail")
		public void testQuickQuoteOptionForRetailHail() {
			String tcname = "testQuickQuoteOptionForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.clickSave();
			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(
					alerttext.contains("Question 'Estimate Conditions' in section 'Hail Info' should be answered."));
			questionsscreen = new RegularQuestionsScreen();
			questionsscreen.selectOutsideQuestions();

			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Quick Quote");
            RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
			Assert.assertTrue(vehiclePartScreen.isNotesExists());
			Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
            vehiclePartScreen.setPrice(ExcelUtils.getServicePrice(testcaserow));

            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            //vehiclePartScreen.saveVehiclePart();
            vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
            vehiclePartScreen.setPrice(ExcelUtils.getServicePrice3(testcaserow));
            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			pricematrix.clickSave();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10733:Test Customer self-pay option for Retail Hail", description = "Test Customer self-pay option for Retail Hail")
		public void testCustomerSelfPayOptionForRetailHail() {
			String tcname = "testCustomerSelfPayOptionForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
        	vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectOutsideQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			claimscreen.selectInsuranceCompany(ExcelUtils.getInsuranceCompany(retailhaildatarow));
			claimscreen.clickChangeScreen();
			String alerttext = claimscreen.clickSaveWithAlert();
			Assert.assertTrue(alerttext.contains("Claim# is required."));
			claimscreen.setClaim(ExcelUtils.getClaim(retailhaildatarow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.HAIL_PDR_NON_CUSTOMARY_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.answerQuestion("DEALER");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10734:Test Even WO level tech split for Retail Hail", description = "Test Even WO level tech split for Retail Hail")
		public void testEvenWOLevelTechSplitForRetailHail() {
			String tcname = "testEvenWOLevelTechSplitForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.clickTech();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			selectedServiceDetailsScreen.selectTechniciansEvenlyView();
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA),
					"%33.34");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianB),
					"%33.33");
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianC),
					"%33.33");
			String alerttext = selectedServiceDetailsScreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectOtherQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
            RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.HEAVY_SEVERITY);
			Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianB));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianC));

            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            vehiclePartScreen.saveVehiclePart();
            vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.SEVERE_SEVERITY);
			Assert.assertEquals(vehiclePartScreen.getPrice(), "$0.00");
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianB));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianC));

            vehiclePartScreen.setPrice(ExcelUtils.getServicePrice2(testcaserow));
            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianC);
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianD);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            vehiclePartScreen.saveVehiclePart();
			pricematrix.clickSave();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10735:Test even service level tech split for Retail Hail", description = "Test even service level tech split for Retail Hail")
		public void testEvenServiceLevelTechSplitForRetailHail() {
			String tcname = "testEvenServiceLevelTechSplitForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectOtherQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
            RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
			Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			Assert.assertTrue(vehiclePartScreen.isNotesExists());
			Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianA));

            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);

			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);

			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), "$60.00");

			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            vehiclePartScreen.saveVehiclePart();

            vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.HLF_SIZE, PriceMatrixScreen.LIGHT_SEVERITY);
			Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianA));

            vehiclePartScreen.clickOnTechnicians();
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount3(retailhaildatarow));
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

            vehiclePartScreen.saveVehiclePart();
			pricematrix.clickSave();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10736:Test deductible feature for Retail Hail", description = "Test deductible feature for Retail Hail")
		public void testDeductibleFeatureForRetailHail() {
			String tcname = "testDeductibleFeatureForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectOtherQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));
			claimscreen.setDeductible("50");
			Assert.assertEquals(
					claimscreen
							.getDeductibleValue(), "50.00");

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
            RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
			Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			Assert.assertTrue(vehiclePartScreen.isNotesExists());
			Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianA));
            vehiclePartScreen.saveVehiclePart();
            pricematrix.clickSave();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
            RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL);
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();

		}
		
		@Test(testName = "Test Case 10737:Test Zip Code validator for Retail Hail", description = "Test Zip Code validator for Retail Hail")
		public void testZipCodeValidatorForRetailHail() {
			String tcname = "testZipCodeValidatorForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			final String validzip = "83707";

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectOutsideQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext
					.contains("Your answer doesn't match the validator 'US Zip Codes'."));
			questionsscreen.clearZip();
			questionsscreen.setRegularSetFieldValue((IOSElement) DriverBuilder.getInstance().getAppiumDriver().findElementByAccessibilityId("Owner Zip_TextView"), validzip);
			RegularNavigationSteps.navigateToServicesScreen();
			RegularWorkOrdersSteps.cancelCreatingWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11727:Test Custom WO level tech split for Retail Hail", description = "Test Custom WO level tech split for Retail Hail")
		public void testCustomWOLevelTechSplitForRetailHail() {
			String tcname = "testCustomWOLevelTechSplitForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);


			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.clickTech();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA),
					"%100.00");

			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA,
					"70");
			String alerttext = selectedServiceDetailsScreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertTrue(alerttext.contains("Total amount is not equal 100%"));
			selectedServiceDetailsScreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB,
					"30");
			alerttext = selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);
			vehiclescreen = new RegularVehicleScreen();
			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectProperQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
            RegularVehiclePartScreen vehiclePartScreen =  pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.VERYLIGHT_SEVERITY);
			Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			Assert.assertTrue(vehiclePartScreen.isNotesExists());
			Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianB));

            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), "%25.000");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Helpers.acceptAlert();
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "35.75");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			pricematrix = new RegularPriceMatrixScreen();
            vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
			Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			Assert.assertTrue(vehiclePartScreen.isNotesExists());
			Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianB));
            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.selectTechniciansEvenlyView();
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            vehiclePartScreen.saveVehiclePart();
            pricematrix.clickSave();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 11728:Test Custom service level tech split for Retail Hail", description = "Test Custom service level tech split for Retail Hail")
		public void testCustomServiceLevelTechSplitForRetailHail() {
			String tcname = "testCustomServiceLevelTechSplitForRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);


			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectOtherQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
			Assert.assertTrue(servicesscreen.priceMatricesPopupIsDisplayed());
			RegularPriceMatrixScreen pricematrix = servicesscreen.selectPriceMatrices("Hail Estimating");
            RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
			Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			Assert.assertTrue(vehiclePartScreen.isNotesExists());
			Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
            vehiclePartScreen.clickOnTechnicians();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(
					selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA),
					PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "285");
			String alerttext = selectedServiceDetailsScreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertTrue(alerttext.contains("Split amount should be equal to total amount."));
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "40");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianB));

            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), "%25.000");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Helpers.acceptAlert();
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "121.25");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

            //vehiclePartScreen.saveVehiclePart();
            vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
			Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			Assert.assertTrue(vehiclePartScreen.isNotesExists());
			Assert.assertTrue(vehiclePartScreen.isTechniciansExists());

            vehiclePartScreen.clickOnTechnicians();
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "125");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "75");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianA));
			Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
					UtilConstants.technicianB));

            vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));

			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "100");
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "45");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
            vehiclePartScreen.saveVehiclePart();
            pricematrix.clickSave();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12626:Test Customer Discount on Retail Hail", description = "Test Customer Discount on Retail Hail")
		public void testCustomerDiscountOnRetailHail() {
			String tcname = "testCustomerDiscountOnRetailHail";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectOutsideQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails("Customer Discount");
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.switchToAvailableServicesTab();
			servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12627:Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice", description = "Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice")
		public void testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice() {
			String tcname = "testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Roof Rail", "Right Roof Rail",
					"Roof" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };

			
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTROCKERPANELS_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen = new RegularServicesScreen();
			servicesscreen.clickBackServicesButton();
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			RegularTeamWorkOrdersScreen teamworkordersscreen = myworkordersscreen.switchToTeamWorkOrders();

			teamworkordersscreen.clickCreateInvoiceForWO(workOrderNumber);
			teamworkordersscreen.clickiCreateInvoiceButton();
			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
			teamworkordersscreen.clickHomeButton();			
		}
		
		@Test(testName = "Test Case 12628:Test Phase Enforcement for WizardPro Tracker", description = "Test Phase Enforcement for WizardPro Tracker")
		public void testPhaseEnforcementForWizardProTracker() {
			String tcname = "testPhaseEnforcementForWizardProTracker";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Fender", "Left Front Door",
					"Left Quarter Panel" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			selectedServiceDetailsScreen =  servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();

			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(workOrderNumber);
			teamworkordersscreen.selectWOMonitor();
			Helpers.waitABit(3000);
			RegularOrderMonitorScreen ordermonitorscreen = new RegularOrderMonitorScreen();
			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			for (int i = 0; i < vehicleparts.length; i++)
                Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.PDRPANEL_SUBSERVICE + " (" + vehicleparts[i] + ")"), "Completed");
			
			//ordermonitorscreen.selectPanel(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE, "Active");	

			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.PAINT_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE, "Completed");

			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.WHEELS_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			for (int i = 0; i < vehiclepartswheel.length; i++)
                Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.WHEEL_SUBSERVICE + " (" + vehiclepartswheel[i] + ")"), "Completed");

			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			
			teamworkordersscreen.clickCreateInvoiceForWO(workOrderNumber);
			teamworkordersscreen.verifyCreateInvoiceIsActivated(workOrderNumber);
			teamworkordersscreen.clickiCreateInvoiceButton();
            RegularInvoiceInfoScreen invoiceinfoscreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
			Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			teamworkordersscreen.clickHomeButton();			
		}

		@Test(testName = "Test Case 12630:Test adding services to an order being monitored", description = "Test adding services to an order being monitored")
		public void testAddingServicesToOnOrderBeingMonitored() {
			String tcname = "testAddingServicesToOnOrderBeingMonitored";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Rear Door",
					"Right Front Door" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };
			final String[] vehiclepartspaint = { "Front Bumper"};
			final String[] vehiclepartstoadd = { "Hood"};

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			for (int i = 0; i <= vehiclepartspaint.length; i++) {
				selectedServiceDetailsScreen.saveSelectedServiceDetails();
			}
			servicesscreen.clickBackServicesButton();
			
			
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			selectedServiceDetailsScreen =  servicesscreen.openCustomServiceDetails(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();

			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(workOrderNumber);
			teamworkordersscreen.selectWOMonitor();
			RegularOrderMonitorScreen ordermonitorscreen = new RegularOrderMonitorScreen();
			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			for (int i = 0; i < vehicleparts.length; i++)
                Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE + " (" + vehicleparts[i] + ")"), "Completed");
			for (int i = 0; i < vehiclepartspaint.length; i++)
                Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.PAINTFULLBAMPER_SUBSERVICE + " (" + vehiclepartspaint[i] + ")"), "Active");
			
			
			ordermonitorscreen.clickServicesButton();
			servicesscreen.waitServicesScreenLoaded();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice4(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartstoadd.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartstoadd[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen.clickSave();
			ordermonitorscreen = new RegularOrderMonitorScreen();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE, "Queued");
			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			for (int i = 0; i < vehiclepartstoadd.length; i++)
                Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.PDRPANEL_SUBSERVICE + " (" + vehiclepartstoadd[i] + ")"), "Completed");
			
			for (int i = 0; i < vehiclepartspaint.length; i++)
                Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.PAINTFULLBAMPER_SUBSERVICE + " (" + vehiclepartspaint[i] + ")"), "Active");
			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.PAINT_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			for (int i = 0; i < vehiclepartspaint.length; i++)
                Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.PAINTFULLBAMPER_SUBSERVICE + " (" + vehiclepartspaint[i] + ")"), "Completed");
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE, "Active");		
			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.WHEELS_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			for (int i = 0; i < vehiclepartswheel.length; i++)
                Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE + " (" + vehiclepartswheel[i] + ")"), "Completed");

			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			
			teamworkordersscreen.clickCreateInvoiceForWO(workOrderNumber);
			teamworkordersscreen.clickiCreateInvoiceButton();
            RegularInvoiceInfoScreen invoiceinfoscreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
            Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			teamworkordersscreen.clickHomeButton();			
		}
		
		@Test(testName = "Test Case 12629:Test Start Service feature is accurately capturing times", description = "Test Start Service feature is accurately capturing times")
		public void testStartServiceFeatureIsAccuratelyCapturingTimes() {
			String tcname = "testStartServiceFeatureIsAccuratelyCapturingTimes";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.DETAIL_SERVICE );
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen =servicesscreen.openCustomServiceDetails(UtilConstants.FRONTLINEREADY_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(workOrderNumber);
			teamworkordersscreen.selectWOMonitor();
			RegularOrderMonitorScreen ordermonitorscreen = new RegularOrderMonitorScreen();
			Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.FRONTLINEREADY_SUBSERVICE), "Active");
			ordermonitorscreen.selectPanel(UtilConstants.FRONTLINEREADY_SUBSERVICE);
			ordermonitorscreen.clickStartService();
			ordermonitorscreen.selectPanel(UtilConstants.FRONTLINEREADY_SUBSERVICE);
			Assert.assertTrue(ordermonitorscreen.isStartServiceDissapeared());
			String srvstartdate = ordermonitorscreen.getServiceStartDate().substring(0, 10);
			ordermonitorscreen.clickServiceDetailsDoneButton();
			Assert.assertEquals(ordermonitorscreen.getServiceFinishDate(UtilConstants.FRONTLINEREADY_SUBSERVICE).substring(0, 10), srvstartdate);
			
			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			teamworkordersscreen.clickHomeButton();		
		}
		
		@Test(testName = "Test Case 12631:Test Quantity does not mulitply price in Route package", description = "Test Quantity does not mulitply price in Route package")
		public void testQuantityDoesNotMulitplyPriceInRoutePackage() {
			String tcname = "testQuantityDoesNotMulitplyPriceInRoutePackage";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String vehiclepart = "Left Roof Rail";
			final String[] vehiclepartspaint = { "Hood", "Left Roof Rail", "Right Fender" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.setServiceQuantityValue("4");
			//selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepart);

			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.setServiceQuantityValue("5");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
            Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();		
		}
		
		@Test(testName = "Test Case 12632:Test Delete Work Order function", description = "Test Delete Work Order function")
		public void testDeleteWorkOrderFunction() {
			String tcname = "testDeleteWorkOrderFunction";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Roof" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routecanadaworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			String wo = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.deleteWorkOrderViaActionAndSearch(wo);
			Assert.assertFalse(myworkordersscreen.woExists(wo));
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12637:Test changing customer on Inspection", description = "Test changing customer on Inspection")
		public void testChangingCustomerOnInspection() {
			String tcname = "testChangingCustomerOnInspection";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Front Wheel", "Left Rear Wheel" };
			WholesailCustomer wholesailCustomer = new WholesailCustomer();
			wholesailCustomer.setCompanyName("Abc Rental Center");

			RegularSettingsScreen settingsscreen = homescreen.clickSettingsButton();
			settingsscreen.setShowAllServicesOn();
			settingsscreen.clickHomeButton();

			homescreen.clickMyInspectionsButton();
			RegularMyInspectionsScreen myinspectionsscreen = new RegularMyInspectionsScreen();
			myinspectionsscreen.clickAddInspectionButton();
            RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.routeinspectiontype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			final String inspNumber = vehiclescreen.getInspectionNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.DETAIL_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WASH_N_VAC_SERVICE);
			//servicesscreen.openServiceDetails(UtilConstants.WASH_N_VAC_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.clickVehiclePartsCell();
		
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularInspectionsSteps.saveInspection();
			RegularMyInspectionsSteps.changeCustomerForInspection(inspNumber, wholesailCustomer);
			myinspectionsscreen.clickHomeButton();
			
			homescreen.clickCustomersButton();
			RegularCustomersScreen customersscreen = new RegularCustomersScreen();
			customersscreen.selectCustomerWithoutEditing("Abc Rental Center");
			homescreen = new RegularHomeScreen();
			homescreen.clickMyInspectionsButton();
			myinspectionsscreen = new RegularMyInspectionsScreen();
			Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspNumber));
			myinspectionsscreen.clickHomeButton();
			
			homescreen.clickCustomersButton();
			customersscreen = new RegularCustomersScreen();
			customersscreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
			homescreen = new RegularHomeScreen();
			homescreen.clickMyInspectionsButton();
			myinspectionsscreen = new RegularMyInspectionsScreen();
			Assert.assertTrue(myinspectionsscreen.checkInspectionDoesntExists(inspNumber));
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12638:Test Retail Hail package quantity multiplier", description = "Test Retail Hail package quantity multiplier")
		public void testRetailHailPackageQuantityMultiplier() {
			String tcname = "testRetailHailPackageQuantityMultiplier";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String servicequantity = "3";
			final String servicequantity2 = "4.5";
			final String totalsumm = "$3,738.00";

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularQuestionsScreen questionsscreen = myworkordersscreen.selectWorkOrderTypeWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
			questionsscreen.acceptForReminderNoDrilling();

			RegularNavigationSteps.navigateToVehicleInfoScreen();
			RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectOutsideQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			claimscreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.setServiceQuantityValue(servicequantity);	
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.setServiceQuantityValue(servicequantity2);	
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), totalsumm);
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
            RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL_NO_DISCOUNT_INVOICE);
            Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), totalsumm);
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12641:Test custom WO level split for Route package", description = "Test custom WO level split for Route package")
		public void testCustomWOLevelSplitForRoutePackage() {
			String tcname = "testCustomWOLevelSplitForRoutePackage";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Mirror", "Right Mirror" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.clickTech();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA, "70");
			selectedServiceDetailsScreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB, "30");
			String alerttext = selectedServiceDetailsScreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
			selectedServiceDetailsScreen =  servicesscreen.openCustomServiceDetails(UtilConstants.DUELEATHER_SUBSERVICE);

			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);

			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA), "$29.40");
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianB), "$12.60");
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "31.50");
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%75.00");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "10.50");
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%25.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);

			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA), "$70.00");
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianB), "$30.00");
			selectedServiceDetailsScreen.selectTechniciansCustomView();
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "28");
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%28.00");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "67");
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%67.00");
			selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "5");
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%5.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
            RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
            Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12642:Test even WO level split for Route package", description = "Test even WO level split for Route package")
		public void testEvenWOLevelSplitForRoutePackage() {
			String tcname = "testEvenWOLevelSplitForRoutePackage";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehicleparts = { "Left Fender", "Left Front Door", "Left Quarter Panel", "Left Rear Door", "Left Roof Rail" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.clickTech();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA), "%50.00");
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianB), "%50.00");

			String alerttext = selectedServiceDetailsScreen
					.saveSelectedServiceDetailsWithAlert();
			Assert.assertEquals(
					alerttext,
					AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.DETAIL_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.FRONTLINEREADY_SUBSERVICE);

			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.OTHER_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));	
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), "$108.00");
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%50.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);

			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.clickTechniciansIcon();
			selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
			Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), "$80.00");
			Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%33.33");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
            RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
            Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12644:Test adding notes to a Work Order", description = "Test adding notes to a Work Order")
		public void testAddingNotesToWorkOrder() {
			String tcname = "testAddingNotesToWorkOrder";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = {  "Hood", "Left Rear Door", "Right Fender" };
			final String[] vehiclepartspaint = {  "Left Rear Door", "Right Fender" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routecanadaworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.clickNotesButton();
			RegularNotesScreen notesScreen = new RegularNotesScreen();
			notesScreen.setNotes("Blue fender");
			//notesScreen.clickDoneButton();
			notesScreen.clickSaveButton();

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.clickNotesCell();
			notesScreen.setNotes("Declined right door");
			notesScreen.clickSaveButton();
			
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.clickNotesCell();
			notesScreen.setNotes("Declined hood");
			notesScreen.clickSaveButton();

			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
            Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenum));
			RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoicenum);
			notesScreen.setNotes("Declined wheel work");
			notesScreen.clickSaveButton();
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12645:Test changing the PO# on an invoice", description = "Test changing the PO# on an invoice")
		public void testChangingThePOOnAnInvoice() {
			String tcname = "testChangingThePOOnAnInvoice";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.servicedriveworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.setTotalSale("1");
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			invoiceinfoscreen.setPO("832145");
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenum));
			RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoicenum);
			myinvoicesscreen.changePO("832710");
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12646:Test editing an Inspection", description = "Test editing an Inspection")
		public void testEditingAnInspection() {
			String tcname = "testEditingAnInspection";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Front Door", "Left Rear Door", "Right Front Door", "Right Rear Door" };

			RegularSettingsScreen settingsscreen = homescreen.clickSettingsButton();
			settingsscreen.setShowAllServicesOn();
			homescreen = settingsscreen.clickHomeButton();
			
			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
			myinspectionsscreen.clickAddInspectionButton();
            RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.routeinspectiontype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			final String inspNumber = vehiclescreen.getInspectionNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularInspectionsSteps.saveInspection();
			RegularMyInspectionsSteps.selectInspectionForEdit(inspNumber);
			vehiclescreen = new RegularVehicleScreen();
			RegularNavigationSteps.navigateToServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularInspectionsSteps.saveInspection();
			myinspectionsscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12647:Test editing a Work Order", description = "Test editing a Work Order")
		public void testEditingWorkOrder() {
			String tcname = "testEditingWorkOrder";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.carmaxworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String wo = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreen.setRO(ExcelUtils.getRO(testcaserow));
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.INTERIOR_FABRIC_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE);
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00"));
			selectedServicesScreen.switchToAvailableServicesTab();
			servicesscreen.clickBackServicesButton();
            servicesscreen.selectServicePanel(UtilConstants.INTERIOR_VINIL_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE);
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.switchToSelectedServicesTab();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.67 x 1.00"));
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			RegularMyWorkOrdersSteps.selectWorkOrderForEdit(wo);

			vehiclescreen = new RegularVehicleScreen();
			RegularNavigationSteps.navigateToServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEELCOVER2_SUBSERVICE);
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();

			selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00"));

            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.67 x 1.00"));

            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.WHEELCOVER2_SUBSERVICE, "$45.00 x 1.00"));
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19514:Test editing an Invoice in Draft", description = "Test Editing an Invoice in Draft")
		public void testEditingAnInvoiceInDraft() {
			String tcname = "testEditingAnInvoiceInDraft";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			
			final String[] vehiclepartspdr = { "Left Fender", "Left Roof Rail", "Right Quarter Panel", "Trunk Lid" };
			final String[] vehiclepartspaint = { "Left Mirror" };
			final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String wonum = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
 			servicesscreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails (UtilConstants.CARPETREPAIR_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspdr.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspdr[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
            Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsDraft();

			myworkordersscreen.clickHomeButton();
			
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoicenum);
			invoiceinfoscreen.clickOnWO(wonum);
			vehiclescreen = new RegularVehicleScreen();
			RegularNavigationSteps.navigateToServicesScreen();
            RegularSelectedServicesScreen regularSelectedServicesScreen = servicesscreen.switchToSelectedServicesTab();

			//servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			//servicesscreen.searchServiceByName(UtilConstants.PAINTMIRROR_SUBSERVICE);
			selectedServiceDetailsScreen = regularSelectedServicesScreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
			selectedServiceDetailsScreen.removeService();
            regularSelectedServicesScreen = new RegularSelectedServicesScreen();
            regularSelectedServicesScreen.switchToAvailableServicesTab();
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			servicesscreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			selectedServiceDetailsScreen.selectVehiclePart("Right Mirror");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.clickSave();
			invoiceinfoscreen = new RegularInvoiceInfoScreen();
			invoiceinfoscreen.clickSaveAsFinal();
			homescreen = myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19548:Test adding a PO# to an invoice", description = "Test adding a PO# to an invoice")
		public void testAddingAPOToAnInvoice() {
			String tcname = "testAddingAPOToAnInvoice";
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularSettingsScreen settingsScreen = homescreen.clickSettingsButton();
			settingsScreen.setInsvoicesCustomLayoutOff();
			settingsScreen.clickHomeButton();
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizardprotrackeravisworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.INTERIOR_FABRIC_SERVICE);
			servicesscreen.selectService("Tear/Burn >2\" (Fabric)");
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDR6PANEL_SUBSERVICE);
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
            Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(UtilConstants.PDR6PANEL_SUBSERVICE));
			Assert.assertEquals(servicesscreen.getSubTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(workOrderNumber);
			RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE, "Completed");
            Assert.assertEquals(ordermonitorscreen.getPanelStatus("Tear/Burn >2\" (Fabric)"), "Active");
			ordermonitorscreen.selectPanelToChangeStatus("Interior Repair");
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE, "Completed");
            Assert.assertEquals(ordermonitorscreen.getPanelStatus("Tear/Burn >2\" (Fabric)"), "Completed");
			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			teamworkordersscreen.clickCreateInvoiceForWO(workOrderNumber);
			teamworkordersscreen.verifyCreateInvoiceIsActivated(workOrderNumber);
			teamworkordersscreen.clickiCreateInvoiceButton();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			RegularNavigationSteps.navigateToScreen("AVIS Questions");
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
			
			questionsscreen.chooseAVISCode("Rental-921");						
			invoiceinfoscreen.clickSaveAsFinal();
			teamworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoicenum);
			myinvoicesscreen.changePO("170116");
            myinvoicesscreen.clickBackButton();
            homescreen.clickMyInvoicesButton();
            Assert.assertEquals(myinvoicesscreen.getInvoicePrice(invoicenum), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
            Assert.assertTrue(myinvoicesscreen.isInvoiceHasInvoiceSharedIcon(invoicenum));
			Assert.assertTrue(myinvoicesscreen.isInvoiceHasInvoiceNumberIcon(invoicenum));
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19670:Test adding a PO# to an invoice containing multiple Work Orders", description = "Test adding a PO# to an invoice containing multiple Work Orders")
		public void testAddingPOToAnInvoiceContainingMultipleWorkOrders() {
			String tcname1 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders1";
			String tcname2 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders2";
			String tcname3 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders3";
			
			int testcaserow1 = ExcelUtils.getTestCaseRow(tcname1);
			int testcaserow2 = ExcelUtils.getTestCaseRow(tcname2);
			int testcaserow3 = ExcelUtils.getTestCaseRow(tcname3);
			
			final String[] vehicleparts = { "Left Rear Door", "Right Rear Door" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow1));
			String workOrderNumber1 = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow1)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularWorkOrdersSteps.saveWorkOrder();
			
			//Create second WO
			myworkordersscreen = new RegularMyWorkOrdersScreen();
			myworkordersscreen.clickAddOrderButton();
			myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow2));
			String workOrderNumber2 = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));

			RegularNavigationSteps.navigateToServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.LEATHERREPAIR_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow2));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow2)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularWorkOrdersSteps.saveWorkOrder();

			//Create third WO
			myworkordersscreen.clickAddOrderButton();
			myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow3));
			String workOrderNumber3 = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow3), ExcelUtils.getModel(testcaserow3), ExcelUtils.getYear(testcaserow3));

			RegularNavigationSteps.navigateToServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow3));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow3)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(workOrderNumber1);
			RegularOrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			for (int i = 0; i < vehicleparts.length; i++)
                Assert.assertEquals(ordermonitorscreen.getPanelStatus(UtilConstants.PDRVEHICLE_SUBSERVICE + " (" + vehicleparts[i] + ")"), "Completed");
	
			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			teamworkordersscreen.clickOnWO(workOrderNumber2);
			teamworkordersscreen.selectWOMonitor();
			ordermonitorscreen.selectPanelToChangeStatus("Interior Repair");
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.LEATHERREPAIR_SUBSERVICE, "Completed");
			teamworkordersscreen = ordermonitorscreen.clickBackButton();
			teamworkordersscreen.clickOnWO(workOrderNumber3);
			teamworkordersscreen.selectWOMonitor();
			ordermonitorscreen.selectPanelToChangeStatus(UtilConstants.PAINT_SERVICE);
			ordermonitorscreen.setCompletedPhaseStatus();
			ordermonitorscreen.verifyPanelsStatuses(UtilConstants.BLACKOUT_SUBSERVICE, "Completed");
			teamworkordersscreen = ordermonitorscreen.clickBackButton();

			teamworkordersscreen.clickCreateInvoiceForWO(workOrderNumber1);
			teamworkordersscreen.clickCreateInvoiceForWO(workOrderNumber2);
			teamworkordersscreen.clickCreateInvoiceForWO(workOrderNumber3);

			teamworkordersscreen.clickiCreateInvoiceButton();
            RegularInvoiceInfoScreen invoiceinfoscreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();
			teamworkordersscreen.clickHomeButton();
			
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoicenum);
			myinvoicesscreen.changePO("957884");
			myinvoicesscreen.clickHomeButton();	
		}
		
		@Test(testName = "Test Case 19671:Test Copy Vehicle feature", description = "Test Copy Vehicle feature")
		public void testCopyVehicleFeature() {
			String tcname = "testCopyVehicleFeature";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.OTHER_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularWorkOrdersSteps.saveWorkOrder();
			RegularMyWorkOrdersSteps.selectWorkOrderForCopyVehicle(workOrderNumber);
			myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.carmaxworkordertype);
			vehiclescreen.waitVehicleScreenLoaded();
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19672:Test Copy Services feature", description = "Test Copy Services feature")
		public void testCopyServicesFeature() {
			String tcname1 = "testCopyServicesFeature1";
			String tcname2 = "testCopyServicesFeature2";
			
			int testcaserow1 = ExcelUtils.getTestCaseRow(tcname1);
			int testcaserow2 = ExcelUtils.getTestCaseRow(tcname2);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow1));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));
			String wo = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
			
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularWorkOrdersSteps.saveWorkOrder();
			RegularMyWorkOrdersSteps.selectWorkOrderForCopyServices(wo);
			myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow2));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));
			RegularNavigationSteps.navigateToServicesScreen();
			//servicesscreen.selectService(UtilConstants.PAINT_SERVICE);
			RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
			Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(UtilConstants.BLACKOUT_SUBSERVICE));
			Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(UtilConstants.BLACKOUT_SUBSERVICE), new String(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow1)) + " x 1.00").replaceAll(" ", ""));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 24164:Test Pre-Existing Damage option", description = "Test Pre-Existing Damage option")
		public void testPreExistingDamageOption() {
			String tcname = "testPreExistingDamageOption";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left A Pillar", "Left Front Door", "Metal Sunroof", "Right Roof Rail" };
			
			RegularSettingsScreen settingsscreen = homescreen.clickSettingsButton();
			settingsscreen.setShowAllServicesOn();
			homescreen = settingsscreen.clickHomeButton();
			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
			myinspectionsscreen.clickAddInspectionButton();
            RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.servicedriveinspectiondertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			final String inspectionNumber = vehiclescreen.getInspectionNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("Scratch (Exterior)");
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.checkPreexistingDamage();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();	
			servicesscreen.clickBackServicesButton();
			Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularInspectionsSteps.saveInspection();
			myinspectionsscreen.clickOnInspection(inspectionNumber);
			myinspectionsscreen.clickCreateWOButton();
			vehiclescreen = new RegularVehicleScreen();
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToServicesScreen();
            Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			servicesscreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();	
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.setTotalSale("1");
			RegularWorkOrdersSteps.saveWorkOrder();
			//Assert.assertEquals(myinspectionsscreen.getFirstInspectionNumberValue(), inspectionNumber);
			RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspectionNumber);
			vehiclescreen = new RegularVehicleScreen();
			Assert.assertEquals(vehiclescreen.getWorkOrderNumber(), workOrderNumber);
			servicesscreen.clickCancel();

			myinspectionsscreen.clickHomeButton();
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			myworkordersscreen.clickCreateInvoiceIconForWOViaSearch(workOrderNumber);
			myworkordersscreen.clickInvoiceIcon();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			invoiceinfoscreen.clickSaveAsFinal();
			homescreen = myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19676:Test Total Sale requirement", description = "Test Total Sale requirement")
		public void testTotalSaleRequirement() {
			String tcname = "testTotalSaleRequirement";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Hood", "Left Fender" };
			final String totalsale = "675";
			
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizardprotrackerservicedriveworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.clickSave();
			String alerttext = Helpers.getAlertTextAndAccept();
			ordersummaryscreen.setTotalSale(totalsale);
			Assert.assertTrue(alerttext.contains("Total Sale is required."));
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19678:Test package pricing for read only items", description = "Test package pricing for read only items")
		public void testPackagePricingForReadOnlyItems() {
			String tcname = "testPackagePricingForReadOnlyItems";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Fender", "Left Roof Rail", "Right Fender" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.carmaxworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreen.setRO(ExcelUtils.getRO(testcaserow));
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.INTERIOR_LEATHER_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.CRT_2_RPR_LTHR_SUBSERVICE);
			Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentsValue(), "-$14.48");
			selectedServiceDetailsScreen.setServiceQuantityValue("3.00");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
            Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.CUST2PNL_SUBSERVICE);
			Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentsValue(), "-$6.55");
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			servicesscreen.clickBackServicesButton();
			Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			invoiceinfoscreen.clickSaveAsFinal();
			vehiclescreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19681:Test price policy for service items from Inspection through Invoice creation", description = "Test price policy for service items from Inspection through Invoice creation")
		public void testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation() {
			String tcname = "testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Front Door", "Left Rear Door", "Right Fender" };
			final String[] vehiclepartspaint = { "Hood", "Left Fender" };

			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
			myinspectionsscreen.clickAddInspectionButton();
            RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.routeinspectiontype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String inspectionNumber = vehiclescreen.getInspectionNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
				
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehiclepartspaint.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularInspectionsSteps.saveInspection();
			Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspectionNumber), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularMyInspectionsSteps.selectInspectionForCreatingWO(inspectionNumber);
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToServicesScreen();

			RegularSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesTab();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.PDRVEHICLE_SUBSERVICE, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)) + " x 1.00"));

            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.PAINTPANEL_SUBSERVICE, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)) + " x 1.00"));
			RegularInspectionsSteps.saveInspection();
			Assert.assertEquals(myinspectionsscreen.getInspectionPriceValue(inspectionNumber), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspectionNumber);
			Assert.assertEquals(vehiclescreen.getWorkOrderNumber(), workOrderNumber);
			servicesscreen.clickCancel();

			myinspectionsscreen.clickHomeButton();
			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
			myworkordersscreen.clickCreateInvoiceIconForWO(workOrderNumber);
			myworkordersscreen.clickInvoiceIcon();
			RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10263:Send Multiple Emails", description = "Send Multiple Emails")
		public void testSendMultipleEmails() {

			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.selectInvoices(3);

			//for (int i = 0; i< 4; i++) {
			//	myinvoicesscreen.selectInvoiceForActionByIndex(i+1);
			//}
			myinvoicesscreen.clickActionButton();
			RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
			RegularEmailScreenSteps.sendSingleEmailToAddress(UtilConstants.TEST_EMAIL);
			myinvoicesscreen.clickDoneButton();
			myinvoicesscreen.clickHomeButton();			
		}
		
		@Test(testName = "Test Case 19683:Test Work Order Discount Override feature", description = "Test Work Order Discount Override feature")
		public void testWorkOrderDiscountOverrideFeature() {
			String tcname = "testWorkOrderDiscountOverrideFeature";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Fender", "Right Fender"};


			RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
			customersscreen.swtchToWholesaleMode();
			homescreen = customersscreen.selectCustomerWithoutEditing("Bel Air Auto Auction Inc");

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();



			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), "-$28.50");
			selectedServiceDetailsScreen.clickVehiclePartsCell();

			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WSANDBPANEL_SERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			selectedServiceDetailsScreen.selectVehiclePart("Hood");
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
            Assert.assertEquals(servicesscreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
            Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19685:Test completed RO only requirement for invoicing", description = "Test Completed RO only requirement for invoicing")
		public void testCompletedROOnlyRequirementForInvoicing() {
			String tcname = "testCompletedROOnlyRequirementForInvoicing";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Left Quarter Panel", "Right Roof Rail", "Trunk Lid" };
			final String[] vehiclepartswheel = { "Right Front Wheel", "Right Rear Wheel" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			String workOrderNumber = vehiclescreen.getWorkOrderNumber();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTTOWHOOKCOVER_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			for (int i = 0; i < vehiclepartswheel.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();

			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
			
			RegularTeamWorkOrdersScreen teamworkordersscreen = homescreen.clickTeamWorkordersButton();
			teamworkordersscreen.clickOnWO(workOrderNumber);
			teamworkordersscreen.selectEditWO();
			vehiclescreen = new RegularVehicleScreen();
			vehiclescreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
			Assert.assertFalse(ordersummaryscreen.checkApproveAndCreateInvoiceExists());
			RegularWorkOrdersSteps.saveWorkOrder();
			teamworkordersscreen.clickCreateInvoiceForWO(workOrderNumber);
			teamworkordersscreen.clickiCreateInvoiceButton();
			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
			teamworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 10659:Test Enterprise Work Order question forms inforced", description = "Test Enterprize Work Order question forms inforced")
		public void testEnterprizeWorkOrderQuestionFormsInforced() {
			String tcname = "testEnterprizeWorkOrderQuestionFormsInforced";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.enterpriseworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			RegularNavigationSteps.navigateToScreen(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE.getDefaultScreenTypeName());
			RegularEnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new RegularEnterpriseBeforeDamageScreen();
			enterprisebeforedamagescreen.clickSave();
			String alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext.contains("Question 'VIN' in section 'Enterprise Before Damage' should be answered."));
			enterprisebeforedamagescreen.setVINCapture();
			enterprisebeforedamagescreen.clickSave();
			alerttext = Helpers.getAlertTextAndAccept();
			Assert.assertTrue(alerttext.contains("Question 'License Plate' in section 'Enterprise Before Damage' should be answered."));
			enterprisebeforedamagescreen.setLicensePlateCapture();

			RegularNavigationSteps.navigateToScreen(UtilConstants.ENTERPRISE_AFTER_REPAIR_SCREEN_CAPTION);
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12633:Test successful email of pictures using Notes feature", description = "Test successful email of pictures using Notes feature")
		public void testSuccessfulEmailOfPicturesUsingNotesFeature() {
			String tcname = "testSuccessfulEmailOfPicturesUsingNotesFeature";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.avisworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
			String wo = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.clickNotesButton();
			RegularNotesScreen notesScreen = new RegularNotesScreen();
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
			notesScreen.clickSaveButton();
			vehiclescreen = new RegularVehicleScreen();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.clickNotesButton();
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 2);
			notesScreen.clickSaveButton();
			
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDR2PANEL_SUBSERVICE);
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			selectedServiceDetailsScreen.clickNotesCell();
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
			notesScreen.clickSaveButton();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToScreen(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE.getDefaultScreenTypeName());
			RegularEnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new RegularEnterpriseBeforeDamageScreen();
			enterprisebeforedamagescreen.setVINCapture();
			enterprisebeforedamagescreen.setLicensePlateCapture();

			RegularNavigationSteps.navigateToScreen(UtilConstants.ENTERPRISE_AFTER_REPAIR_SCREEN_CAPTION);
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.selectWorkOrderForAddingNotes(wo);
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 3);
			notesScreen.clickSaveButton();
			
			myworkordersscreen.clickCreateInvoiceIconForWO(wo);
			myworkordersscreen.clickInvoiceIcon();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			RegularNavigationSteps.navigateToScreen("AVIS Questions");
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
			questionsscreen.chooseAVISCode("Other-920");			
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoicenum);
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
			notesScreen.clickSaveButton();
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.selectInvoice(invoicenum);
			myinvoicesscreen.clickActionButton();
			RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
			RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
			myinvoicesscreen.clickDoneButton();
			myinvoicesscreen.clickHomeButton();	
		}
		
		@Test(testName = "Test Case 12634:Test emailing photos in Economical Inspection", description = "Test emailing photos in Economical Inspection")
		public void testEmailingPhotosInEconomicalInspection() {
			String tcname = "testEmailingPhotosInEconomicalInspection";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();

			myinspectionsscreen.clickAddInspectionButton();
            RegularVehicleScreen vehiclescreen = myinspectionsscreen.selectInspectionType(DentWizardInspectionsTypes.economicalinspectiondertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			final String inspNumber = vehiclescreen.getInspectionNumber();
			vehiclescreen.clickNotesButton();
			RegularNotesScreen notesScreen = new RegularNotesScreen();
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
			notesScreen.clickSaveButton();

			RegularNavigationSteps.navigateToClaimScreen();
			RegularClaimScreen claimscreen = new RegularClaimScreen();
			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_PHOTOS_SCREEN_CAPTION);
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
			questionsscreen.makeCaptureForQuestionRegular("VIN");
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Odometer");
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("License Plate Number");
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Left Front of Vehicle");
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Right Front of Vehicle");
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Right Rear of Vehicle");
			questionsscreen.swipeScreenUp();
			questionsscreen.makeCaptureForQuestionRegular("Left Rear of Vehicle");
			RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
			questionsscreen.selectProperQuestions();
			
			int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
			questionsscreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
					ExcelUtils.getownerState(retailhaildatarow), UtilConstants.CANADA, ExcelUtils.getOwnerZip(retailhaildatarow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails("E-Coat");
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.clickNotesCell();
			notesScreen.addNotesCapture();
			notesScreen.clickSaveButton();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			RegularNavigationSteps.navigateToScreen(UtilConstants.PRICE_MATRIX_SCREEN_CAPTION);
			RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
            RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
            vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
            vehiclePartScreen.saveVehiclePart();
            pricematrix.clickNotesButton();
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 2);
			notesScreen.clickSaveButton();
			RegularInspectionsSteps.saveInspection();

			myinspectionsscreen.clickOnInspection(inspNumber);
			RegularMyInvoicesScreen myinvoicesscreen = new RegularMyInvoicesScreen();
			RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
			RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
			myinspectionsscreen.clickHomeButton();			
		}
		
		@Test(testName = "Test Case 12635:Test emailing photos in Auction package", description = "Test emailing photos in Auction package")
		public void testEmailingPhotosInAuctionPackage() {
			String tcname = "testEmailingPhotosInAuctionPackage";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Hood", "Left Fender", "Right Fender", "Roof" };
			final String firstnote = "Refused paint";
			final String secondnote = "Just 4 panels";

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.auctionworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			String wo = vehiclescreen.getWorkOrderNumber();
			vehiclescreen.clickNotesButton();
			RegularNotesScreen notesScreen = new RegularNotesScreen();
			notesScreen.setNotes(firstnote);
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
			notesScreen.clickSaveButton();
			vehiclescreen = new RegularVehicleScreen();
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}

			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.clickNotesCell();
			notesScreen.setNotes(secondnote);
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
			notesScreen.clickSaveButton();
			selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();

			RegularNavigationSteps.navigateToScreen(WizardScreenTypes.QUESTIONS.getDefaultScreenTypeName());
			RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen();
			questionsscreen.chooseConsignor("Unknown Consignor/One Off-718");

			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.selectWorkOrderForAddingNotes(wo);
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 2);
			notesScreen.clickSaveButton();
			
			myworkordersscreen.clickCreateInvoiceIconForWO(wo);
			myworkordersscreen.clickInvoiceIcon();
			RegularInvoiceInfoScreen invoiceinfoscreen = myworkordersscreen.selectInvoiceType(DentWizardInvoiceTypes.AUCTION_NO_DISCOUNT_INVOICE);
			final  String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();
			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.selectInvoice(invoicenum);
			myinvoicesscreen.clickActionButton();
			RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
			RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
			myinvoicesscreen.clickDoneButton();
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 12636:Test emailing photos in Service Drive package", description = "Test emailing photos in Service Drive package")
		public void testEmailingPhotosInServiceDrivePackage() {
			String tcname = "testEmailingPhotosInServiceDrivePackage";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String[] vehicleparts = { "Decklid", "Left A Pillar" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.servicedriveworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectService(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.clickVehiclePartsCell();
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			
			
			servicesscreen.selectService(UtilConstants.INTERIOR_SERVICE);
			selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.STAIN_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.setTotalSale("1");
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
            Assert.assertEquals(invoiceinfoscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
			String invoicenum = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();

			myworkordersscreen.clickHomeButton();
			RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoicesButton();
			RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoicenum);
			RegularNotesScreen notesScreen = new RegularNotesScreen();
			notesScreen.setNotes("Refused paint");
			notesScreen.addNotesCapture();
			Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
			notesScreen.clickSaveButton();
			
			myinvoicesscreen.clickActionButton();
			myinvoicesscreen.selectInvoice(invoicenum);
			myinvoicesscreen.clickActionButton();
			RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
			RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
			myinvoicesscreen.clickDoneButton();
			myinvoicesscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 24131:Test PO# saves with active keyboard on WO summary screen", description = "Test PO# saves with active keyboard on WO summary screen")
		public void testPONumberSavesWithActiveKeyboardOnWOSummaryScreen() {
			String tcname = "testPONumberSavesWithActiveKeyboardOnWOSummaryScreen";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			final String _po = "998601";
			final String[] vehicleparts = { "Hood", "Left Quarter Panel", "Right Roof Rail" };

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.routeusworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PDR_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();

			Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
			for (int i = 0; i < vehicleparts.length; i++) {
				selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
			}
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
            Assert.assertEquals(servicesscreen.getTotalAmaunt(), (PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow))));

			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
            Assert.assertEquals(ordersummaryscreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = ordersummaryscreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
			invoiceinfoscreen.setPOWithoutHidingkeyboard(_po);
			invoiceinfoscreen.clickSaveAsFinal();			
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 24132:Test Total Sale saves with active keyboard on WO summary screen", description = "Test Total Sale saves with active keyboard on WO summary screen")
		public void testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen() {
			String tcname = "testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);
			final String totalsale = "675";

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.servicedriveworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
			RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesscreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
			selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
			selectedServiceDetailsScreen.saveSelectedServiceDetails();
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.setTotalSaleWithoutHidingkeyboard(totalsale);
			RegularWorkOrdersSteps.saveWorkOrder();
			myworkordersscreen.clickHomeButton();
		}
		
		@Test(testName = "Test Case 19673:Test Car History feature", description = "Test Car History feature")
		public void testCarHistoryFeature()
				throws Exception {
			String tcname = "testCarHistoryFeature";		
			int testcaserow = ExcelUtils.getTestCaseRow(tcname);

			RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();

			myworkordersscreen.clickAddOrderButton();
			RegularVehicleScreen vehiclescreen = myworkordersscreen.selectWorkOrderType(DentWizardWorkOrdersTypes.carmaxworkordertype);
			vehiclescreen.setVIN(ExcelUtils.getVIN(testcaserow));
			vehiclescreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
			vehiclescreen.setStock(ExcelUtils.getStock(testcaserow));
			vehiclescreen.setRO(ExcelUtils.getRO(testcaserow));
			RegularNavigationSteps.navigateToServicesScreen();
			RegularServicesScreen servicesscreen = new RegularServicesScreen();
			servicesscreen.selectServicePanel(UtilConstants.INTERIORPLASTIC_SERVICE);
			servicesscreen.selectService(UtilConstants.SCRTCH_1_SECTPLSTC_SERVICE);
			servicesscreen.clickBackServicesButton();
			RegularNavigationSteps.navigateToOrderSummaryScreen();
			RegularOrderSummaryScreen ordersummaryscreen = new RegularOrderSummaryScreen();
			ordersummaryscreen.checkApproveAndCreateInvoice();
			ordersummaryscreen.clickSave();
			RegularInvoiceInfoScreen invoiceinfoscreen = new RegularInvoiceInfoScreen();
			String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.clickSaveAsFinal();	
			RegularCarHistoryScreen carhistoryscreen = new RegularCarHistoryScreen();
			carhistoryscreen.clickHomeButton();
			carhistoryscreen = homescreen.clickCarHistoryButton();
			carhistoryscreen.searchCar("887340");
			String strtocompare = ExcelUtils.getYear(testcaserow) + ", " + ExcelUtils.getMake(testcaserow) + ", " + ExcelUtils.getModel(testcaserow);
			Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
			Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
			RegularCarHistoryWOsAndInvoicesScreen carHistoryWOsAndInvoicesScreen = carhistoryscreen.clickFirstCarHistoryInTable();
			RegularMyInvoicesScreen myinvoicesscreen = carHistoryWOsAndInvoicesScreen.clickCarHistoryInvoices();
			Assert.assertTrue(myinvoicesscreen.myInvoicesIsDisplayed());
			myinvoicesscreen.clickBackButton();
			carHistoryWOsAndInvoicesScreen = new RegularCarHistoryWOsAndInvoicesScreen();
			carHistoryWOsAndInvoicesScreen.clickBackButton();
			
			carhistoryscreen.clickSwitchToWeb();
			Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
			Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
			carHistoryWOsAndInvoicesScreen = carhistoryscreen.clickFirstCarHistoryInTable();
			carHistoryWOsAndInvoicesScreen.clickCarHistoryInvoices();

			Assert.assertTrue(myinvoicesscreen.teamInvoicesIsDisplayed());
			Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenumber));
			myinvoicesscreen.clickBackButton();
			carHistoryWOsAndInvoicesScreen = new RegularCarHistoryWOsAndInvoicesScreen();
			carHistoryWOsAndInvoicesScreen.clickBackButton();
			carhistoryscreen.clickHomeButton();
		}
}

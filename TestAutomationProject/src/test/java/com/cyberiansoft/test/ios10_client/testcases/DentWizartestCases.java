package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.hdvalidations.AvailableServicesScreenValidations;
import com.cyberiansoft.test.ios10_client.hdvalidations.VehicleInfoValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.DentWizardInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.DentWizardInvoiceTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.DentWizardWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class DentWizartestCases extends ReconProDentWizardBaseTestCase {


	private final String customer = "Abc Rental Center";
	
	@BeforeClass
	public void setUpSuite() throws Exception {
		JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getDentWizardSuiteTestCasesDataPath();
		mobilePlatform = MobilePlatform.IOS_HD;
		initTestUser(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);

		DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
                ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(),
                "Mac mini_olkr", envType);
		MainScreen mainscr = new MainScreen();
		mainscr.userLogin(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.setInsvoicesCustomLayoutOff();
		settingsscreen.clickHomeButton();

		ExcelUtils.setDentWizardExcelFile();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateInspectionOnTheDeviceWithApprovalRequired(String rowID,
																	String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testTopCustomersSetting(String rowID,
																	String description, JSONObject testData) {

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setShowTopCustomersOn();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        Assert.assertTrue(customersScreen.isTopCustomersExists());
        Assert.assertTrue(customersScreen.isCustomerExists(UtilConstants.TEST_CUSTOMER_FOR_TRAINING));
		customersScreen.clickHomeButton();
		
		settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setShowTopCustomersOff();
		settingsscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVINDuplicateCheck(String rowID,
																	String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOn();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVINAndAndSearch(workOrderData.getVehicleInfoData().getVINNumber().substring(
				0, 11));
		vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber().substring(11, 17));
		vehicleScreen.verifyExistingWorkOrdersDialogAppears();		
		vehicleScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
		settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVehiclePartRequirement(String rowID,
																	String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		List<String> sericesToValidate = new ArrayList<>();
		sericesToValidate.add("PDR Panel (Non-Customary)");
		sericesToValidate.add("PDR Vehicle");
		sericesToValidate.add("PDR - Panel (Hail)");

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOn();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		NavigationSteps.navigateToServicesScreen();
		for (String serviceName : sericesToValidate)
			AvailableServicesScreenValidations.verifyServiceExixts(serviceName, true);
		ServicesScreenSteps.selectPanelServiceData(workOrderData.getDamageData());
		WorkOrdersSteps.cancelCreatingWorkOrder();
		myWorkOrdersScreen.clickHomeButton();

		settingsscreen = homeScreen.clickSettingsButton();
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

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInsvoicesCustomLayoutOff();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo1 = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.clickServiceTypesButton();
		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheels.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheels[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		orderSummaryScreen.saveWizard();

		// ==================Create second WO=============
		tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice2";
		testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts2 = { "Hood", "Roof", "Trunk Lid" };
		final String[] vehiclepartspaints = { "Front Bumper", "Rear Bumper" };

		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo2 = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts2.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts2[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), "$41.66");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianB), "$41.67");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianC), "$41.67");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.selectService(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaints.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaints[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selectTechniciansCustomView();
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup
				.setTechnicianCustomPriceValue(UtilConstants.technicianA, "165");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB,
				"50");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA),
				"$165.00");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianB),
				"$50.00");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		NavigationSteps.navigateToOrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(wo1);
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(wo2);
		myWorkOrdersScreen.clickInvoiceIcon();
		InvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        Assert.assertTrue(invoiceInfoScreen.isWOSelected(wo1));
        Assert.assertTrue(invoiceInfoScreen.isWOSelected(wo2));
		final String invoicenumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
        myWorkOrdersScreen = new MyWorkOrdersScreen();
		Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(wo1));
		Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(wo2));
		myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		final String wosubstring = wo1 + ", " + wo2;
		Assert.assertEquals(myInvoicesScreen.getInvoiceInfoLabel(invoicenumber), wosubstring);
		myInvoicesScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10651:Test same Order Type required for turning multiple Work Orders into a single Invoice", description = "Test same Order Type required for turning multiple Work Orders into a single Invoice")
	public void testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice() {
		String tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice1";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Roof" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo1 = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
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
		servicesScreen = new ServicesScreen();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();

		// ==================Create second WO=============
		tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice2";
		testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts2 = { "Hood" };

		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String wo2 = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts2.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts2[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(wo1);
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(wo2);
		myWorkOrdersScreen.clickInvoiceIcon();
		InvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(
				alerttext,
				"Invoice type " + DentWizardInvoiceTypes.NO_ORDER_TYPE.getInvoiceTypeName() + " doesn't support multiple Work Order types.");
		myWorkOrdersScreen.clickHomeButton();

	}

	@Test(testName = "Test Case 10652:Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker", description = "Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker")
	public void testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker() {
		String tcname = "testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Fender", "Left Front Door",
				"Left Quarter Panel" };

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizprotrackerrouteunspectiontype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehicleParts(vehicleparts);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.saveWizard();
		myInspectionsScreen = new MyInspectionsScreen();
		myInspectionsScreen.approveInspectionWithSignature(inspNumber);
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspNumber));
		myInspectionsScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10653:Test Inspections convert to Work Order", description = "Test Inspections convert to Work Order")
	public void testInspectionsConvertToWorkOrder() {
		String tcname = "testInspectionsConvertToWorkOrder";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Right Roof Rail", "Right Fender",
				"Right Rear Door" };

		final String[] vehicleparts2 = { "Left Mirror", "Right Mirror" };

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizprotrackerrouteunspectiontype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehicleParts(vehicleparts);

		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.selectService(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehicleParts(vehicleparts2);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), "$930.00");

		servicesScreen.saveWizard();
		myInspectionsScreen = new MyInspectionsScreen();
		myInspectionsScreen.approveInspectionWithSignature(inspNumber);
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspNumber));
		myInspectionsScreen.selectInspectionInTable(inspNumber);
		myInspectionsScreen.clickCreateWOButton();
		vehicleScreen = new VehicleScreen();
		NavigationSteps.navigateToServicesScreen();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		String wonumber = orderSummaryScreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		orderSummaryScreen.saveWizard();

		myInspectionsScreen.clickHomeButton();
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.isWorkOrderPresent(wonumber);
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10654:Test 'Vehicle' Service does not multiply price entered when selecting multiple panels", description = "Test 'Vehicle' Service does not multiply price entered when selecting multiple panels")
	public void testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels(){
		String tcname = "testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Hood", "Roof", "Trunk Lid" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen = new ServicesScreen();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();

		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10655:Test 'Panel' Service multiplies price entered when selecting multiple panels", description = "Test 'Panel' Service multiplies price entered when selecting multiple panels")
	public void testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels() {
		String tcname = "testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Fender", "Left Front Door",
				"Left Rear Door" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen = new ServicesScreen();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();

		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10656:Test Carmax vehicle information requirements", description = "Test Carmax vehicle information requirements")
	public void testCarmaxVehicleInformationRequirements() {
		String tcname = "testCarmaxVehicleInformationRequirements";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("Stock# is required"));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
		Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("RO# is required"));
		vehicleScreen.setRO(ExcelUtils.getRO(testcaserow));
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();

		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		settingsscreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10657:Test Service Drive requires Advisor", description = "Test Service Drive requires Advisor")
	public void testServiceDriveRequiresAdvisor() {
		String tcname = "testServiceDriveRequiresAdvisor";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("Advisor is required"));
		vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName= "Test Case 10658:Test Inspection requirments inforced", description = "Test Inspection requirements inforced")
	public void testInspectionRequirementsInforced() {
		String tcname = "testInspectionRequirementsInforced";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();

		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizardprotrackerrouteinspectiondertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		String alerttext = vehicleScreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("VIN# is required"));
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.clickSave();
		alerttext = Helpers.getAlertTextAndAccept();
		//alerttext = inspectionscreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("Advisor is required"));
		vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		NavigationSteps.navigateToQuestionsScreen();
        QuestionsScreen questionsScreen =  new QuestionsScreen();
        questionsScreen.saveWizard();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10663:Test Inspections can convert to multiple Work Orders", description = "Test Inspections can convert to multiple Work Orders")
	public void testInspectionsCanConvertToMultipleWorkOrders() {
		String tcname = "testInspectionsCanConvertToMultipleWorkOrders";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();

		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routecanadainspectiontype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		servicesScreen.selectService(UtilConstants.INTERIORBURNS_SUBSERVICE);
		Assert.assertTrue(servicesScreen.checkServiceIsSelected(UtilConstants.INTERIORBURNS_SUBSERVICE));
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(UtilConstants.INTERIORBURNS_SUBSERVICE);

		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		myInspectionsScreen.selectInspectionInTable(inspNumber);
		myInspectionsScreen.clickCreateWOButton();
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		String wonumber = orderSummaryScreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		orderSummaryScreen.saveWizard();
		myInspectionsScreen.showWorkOrdersForInspection(inspNumber);
		Assert.assertEquals(vehicleScreen.getInspectionNumber(), wonumber);
		servicesScreen.clickCancelButton();
		
		myInspectionsScreen.selectInspectionInTable(inspNumber);
		myInspectionsScreen.clickCreateWOButton();
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		String wonumber2 = orderSummaryScreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber2.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		orderSummaryScreen.saveWizard();
		myInspectionsScreen.showWorkOrdersForInspection(inspNumber);
		BaseUtils.waitABit(10000);
		Assert.assertTrue(myInspectionsScreen.isWorkOrderForInspectionExists(wonumber));
		myInspectionsScreen.showWorkOrdersForInspection(inspNumber);
		Assert.assertTrue(myInspectionsScreen.isWorkOrderForInspectionExists(wonumber2));
		myInspectionsScreen.clickHomeButton();

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.isWorkOrderPresent(wonumber);
		myWorkOrdersScreen.isWorkOrderPresent(wonumber2);
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10665:Test Archive feature for Inspections", description = "Test Archive feature for Inspections")
	public void testArchiveFeatureForInspections() {
		String tcname = "testArchiveFeatureForInspections";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();

		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehicleScreen.getInspectionNumber();

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.OTHER_SERVICE);
		servicesScreen.selectService(UtilConstants.WINDOWTINT_SUBSERVICE);

		Assert.assertTrue(servicesScreen.checkServiceIsSelected(UtilConstants.WINDOWTINT_SUBSERVICE));
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		myInspectionsScreen.selectInspectionInTable(inspNumber);

		myInspectionsScreen.clickArchiveInspectionButton();
		myInspectionsScreen.clickFilterButton();
		myInspectionsScreen.clickStatusFilter();
		myInspectionsScreen.isFilterStatusSelected("New");
		myInspectionsScreen.isFilterStatusSelected("Approved");
		myInspectionsScreen.clickFilterStatus("New");
		myInspectionsScreen.clickFilterStatus("Approved");
		myInspectionsScreen.clickFilterStatus("Archived");
		myInspectionsScreen.isFilterStatusSelected("Archived");
		myInspectionsScreen.clickCloseFilterDialogButton();
		myInspectionsScreen.clickSaveFilterDialogButton();

        Assert.assertTrue(myInspectionsScreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
		Assert.assertTrue(myInspectionsScreen.isFilterIsApplied());
		myInspectionsScreen.clearFilter();
		myInspectionsScreen.clickSaveFilterDialogButton();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 11731:Test even WO level tech split for Wholesale Hail", description = "Test even WO level tech split for Wholesale Hail")
	public void testEvenWOLevelTechSplitForWholesaleHail() {
		String tcname = "testEvenWOLevelTechSplitForWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left A Pillar", "Left Fender",
				"Left Rear Door", "Roof" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
		TechniciansPopup techniciansPopup = vehicleScreen.clickTech();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.selectTechniciansEvenlyView();
		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);

		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesScreen.checkServiceIsSelected(UtilConstants.FIXPRICE_SERVICE));

		servicesScreen.openServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
				vehicleparts[0]);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		for (int i = 1; i < vehicleparts.length; i++) {
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[i]
					, "$0.00 x 1.00"));
		}

		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[0]
				, "$105.00 x 1.00"));

		servicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
		Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
				vehicleparts[1]);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianB));
		techniciansPopup.unselecTechnician(UtilConstants.technicianB);
		Assert.assertTrue(techniciansPopup.isTechnicianIsNotSelected(UtilConstants.technicianB));
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA),
				PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), "$70.00");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianC), "$70.00");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		for (int i = 2; i < vehicleparts.length; i++) {
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[i]
					, "$0.00 x 1.00"));
		}
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[0]
				, "$105.00 x 1.00"));
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[1]
				, "$140.00 x 1.00"));

		servicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
		Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
				vehicleparts[2]);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianB));
		techniciansPopup.unselecTechnician(UtilConstants.technicianB);
		Assert.assertTrue(techniciansPopup.isTechnicianIsNotSelected(UtilConstants.technicianB));
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		techniciansPopup.selecTechnician(UtilConstants.technicianD);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), "$30.00");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianD), "$30.00");
		techniciansPopup.unselecTechnician(UtilConstants.technicianA);
		Assert.assertTrue(techniciansPopup.isTechnicianIsNotSelected(UtilConstants.technicianA));
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		for (int i = 3; i < vehicleparts.length; i++) {
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[i]
					, "$0.00 x 1.00"));
		}
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[0]
				, "$105.00 x 1.00"));
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[1]
				, "$140.00 x 1.00"));
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[2]
				, "$60.00 x 1.00"));

		servicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
		Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
				vehicleparts[3]);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen = new ServicesScreen();
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[3]
				, "$275.00 x 1.00"));
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[0]
				, "$105.00 x 1.00"));
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[1]
				, "$140.00 x 1.00"));
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE , vehicleparts[2]
				, "$60.00 x 1.00"));

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
		invoiceInfoScreen.clickSaveAsFinal();

		myWorkOrdersScreen.clickHomeButton();

	}

	@Test(testName = "Test Case 11732:Test even service level tech split for Wholesale Hail", description = "Test even service level tech split for Wholesale Hail")
	public void testEvenServiceLevelTechSplitForWholesaleHail() {
		String tcname = "testEvenServiceLevelTechSplitForWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Hood", "Right Quarter Panel",
				"Sunroof" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), "$47.50");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianB), "$47.50");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.RANDI_TOTAL_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA),
				"$175.00");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianB),
				"$175.00");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceInfoScreen =  orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
		invoiceInfoScreen.clickSaveAsFinal();

		myWorkOrdersScreen.clickHomeButton();
	} 
	
	@Test(testName = "Test Case 11733:Test Custom WO level tech split for wholesale Hail", description = "Test Custom WO level tech split for wholesale Hail")
	public void testCustomWOLevelTechSplitForWholesaleHail() {
		String tcname = "testCustomWOLevelTechSplitForWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Cowl, Other", "Left Fender",
				"Trunk Lid" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
		TechniciansPopup techniciansPopup = vehicleScreen.clickTech();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selectTechniciansCustomView();
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPercentage(UtilConstants.technicianA),
				"%100.00");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianA, ExcelUtils.getServicePrice(testcaserow));
		Assert.assertEquals(
				techniciansPopup.getTechnicianPercentage(UtilConstants.technicianA),
				"%85.00");

		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertTrue(alerttext.contains("Total amount is not equal 100%"));
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "15");

		alerttext = techniciansPopup.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.openCustomServiceDetails(UtilConstants.RANDI_ND_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), "$93.50");
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(UtilConstants.technicianB), "$16.50");
		techniciansPopup.selectTechniciansCustomView();
		techniciansPopup.unselecTechnician(UtilConstants.technicianA);
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianC, "50");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianC), "%45.45");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "60");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianB), "%54.55");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
		invoiceInfoScreen.clickSaveAsFinal();

		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 11734:Test Custom service level tech split for wholesale Hail", description = "Test Custom service level tech split for wholesale Hail")
	public void testCustomServiceLevelTechSplitForWholesaleHail() {
		String tcname = "testCustomServiceLevelTechSplitForWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Fender", "Left Quarter Panel", "Right Rear Door",
				"Trunk Lid" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		servicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 0);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selectTechniciansCustomView();
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "45");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianB), "%36.00");
		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertTrue(alerttext.contains("Split amount should be equal to total amount."));
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianA, "80");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianA), "%64.00");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		servicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
		techniciansPopup= selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selectTechniciansCustomView();
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "25");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianB), "%20.00");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianA, "100");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianA), "%80.00");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		servicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		techniciansPopup= selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selectTechniciansCustomView();
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "50");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianB), "%40.00");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianA, "75");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianA), "%60.00");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		servicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selectTechniciansCustomView();
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "30");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianB), "%24.00");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianA, "95");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianA), "%76.00");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 11735:Test Customer Discount on Wholesale Hail", description = "Test Customer Discount on Wholesale Hail")
	public void testCustomerDiscountOnWholesaleHail() {
		String tcname = "testCustomerDiscountOnWholesaleHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Hood", "Roof", "Trunk Lid" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails("Customer Discount");
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10668:Test Quick Quote option for Retail Hail", description = "Test Quick Quote option for Retail Hail")
	public void testQuickQuoteOptionForRetailHail() {
		String tcname = "testQuickQuoteOptionForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
        VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.waitQuestionsScreenLoaded();
		String alerttext = questionsScreen.clickSaveWithAlert();
		Assert.assertTrue(
				alerttext.contains("Question 'Estimate Conditions' in section 'Hail Info' should be answered."));
		questionsScreen = new QuestionsScreen();
		questionsScreen.selectOutsideQuestions();

		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Quick Quote");
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
		Assert.assertTrue(priceMatrixScreen.isNotesExists());
		Assert.assertTrue(priceMatrixScreen.isTechniciansExists());
		priceMatrixScreen.setPrice(ExcelUtils.getServicePrice(testcaserow));

		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
		priceMatrixScreen.setPrice(ExcelUtils.getServicePrice3(testcaserow));
		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.clickSaveButton();
		servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10733:Test Customer self-pay option for Retail Hail", description = "Test Customer self-pay option for Retail Hail")
	public void testCustomerSelfPayOptionForRetailHail() {
		String tcname = "testCustomerSelfPayOptionForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.selectInsuranceCompany(ExcelUtils.getInsuranceCompany(retailhaildatarow));
		String alerttext = claimScreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("Claim# is required."));
		claimScreen.setClaim(ExcelUtils.getClaim(retailhaildatarow));
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.HAIL_PDR_NON_CUSTOMARY_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.answerQuestion("DEALER");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_PAIMENT_INFO_SCREEN_CAPTION);
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10734:Test Even WO level tech split for Retail Hail", description = "Test Even WO level tech split for Retail Hail")
	public void testEvenWOLevelTechSplitForRetailHail() {
		String tcname = "testEvenWOLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		TechniciansPopup techniciansPopup = vehicleScreen.clickTech();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		techniciansPopup.selectTechniciansEvenlyView();
		Assert.assertEquals(
				techniciansPopup.getTechnicianPercentage(UtilConstants.technicianA),
				"%33.34");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPercentage(UtilConstants.technicianB),
				"%33.33");
		Assert.assertEquals(
				techniciansPopup.getTechnicianPercentage(UtilConstants.technicianC),
				"%33.33");
		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.HEAVY_SEVERITY);
		Assert.assertEquals(priceMatrixScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianB));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianC));

		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.unselecTechnician(UtilConstants.technicianB);
		techniciansPopup.unselecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.SEVERE_SEVERITY);
		Assert.assertEquals(priceMatrixScreen.getPrice(), "$0.00");
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianB));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianC));

		priceMatrixScreen.setPrice(ExcelUtils.getServicePrice2(testcaserow));
		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.unselecTechnician(UtilConstants.technicianA);
		techniciansPopup.unselecTechnician(UtilConstants.technicianB);
		techniciansPopup.unselecTechnician(UtilConstants.technicianC);
		techniciansPopup.selecTechnician(UtilConstants.technicianD);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		priceMatrixScreen.clickSaveButton();
		servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10735:Test even service level tech split for Retail Hail", description = "Test even service level tech split for Retail Hail")
	public void testEvenServiceLevelTechSplitForRetailHail() {
		String tcname = "testEvenServiceLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
		Assert.assertEquals(priceMatrixScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		Assert.assertTrue(priceMatrixScreen.isNotesExists());
		Assert.assertTrue(priceMatrixScreen.isTechniciansExists());
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianA));

		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.unselecTechnician(UtilConstants.technicianA);

		techniciansPopup.selecTechnician(UtilConstants.technicianB);

		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianB), "$60.00");

        techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.HLF_SIZE, PriceMatrixScreen.LIGHT_SEVERITY);
		Assert.assertEquals(priceMatrixScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianA));
		techniciansPopup = priceMatrixScreen.openTechniciansPopup();
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.saveTechViewDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount3(retailhaildatarow));
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.clickSaveButton();
		servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10736:Test deductible feature for Retail Hail", description = "Test deductible feature for Retail Hail")
	public void testDeductibleFeatureForRetailHail() {
		String tcname = "testDeductibleFeatureForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));
		claimScreen.setDeductible("50");
		Assert.assertEquals(
				claimScreen
						.getDeductibleValue(), "50.00");

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		Assert.assertEquals(priceMatrixScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		Assert.assertTrue(priceMatrixScreen.isNotesExists());
		Assert.assertTrue(priceMatrixScreen.isTechniciansExists());
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianA));
		priceMatrixScreen.clickSaveButton();
		servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		//orderSummaryScreen.clickSaveButton();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL);
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 10737:Test Zip Code validator for Retail Hail", description = "Test Zip Code validator for Retail Hail")
	public void testZipCodeValidatorForRetailHail() {
		String tcname = "testZipCodeValidatorForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		final String validzip = "83707";

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext
				.contains("Your answer doesn't match the validator 'US Zip Codes'."));
		questionsScreen.clearZip();
		questionsScreen.setOwnerZip(validzip);
		NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 11727:Test Custom WO level tech split for Retail Hail", description = "Test Custom WO level tech split for Retail Hail")
	public void testCustomWOLevelTechSplitForRetailHail() {
		String tcname = "testCustomWOLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		TechniciansPopup techniciansPopup= vehicleScreen.clickTech();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selectTechniciansCustomView();
		Assert.assertEquals(
				techniciansPopup.getTechnicianPercentage(UtilConstants.technicianA),
				"%100.00");

		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.setTechnicianCustomPercentageValue(UtilConstants.technicianA,
				"70");
		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertTrue(alerttext.contains("Total amount is not equal 100%"));

		techniciansPopup.setTechnicianCustomPercentageValue(UtilConstants.technicianB,
				"30");
		alerttext = techniciansPopup.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectProperQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.VERYLIGHT_SEVERITY);
		Assert.assertEquals(priceMatrixScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		Assert.assertTrue(priceMatrixScreen.isNotesExists());
		Assert.assertTrue(priceMatrixScreen.isTechniciansExists());
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianB));

		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), "%25.000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		Assert.assertEquals(priceMatrixScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		Assert.assertTrue(priceMatrixScreen.isNotesExists());
		Assert.assertTrue(priceMatrixScreen.isTechniciansExists());
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianB));

		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selectTechniciansEvenlyView();
		techniciansPopup.unselecTechnician(UtilConstants.technicianA);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianB), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		techniciansPopup.unselecTechnician(UtilConstants.technicianB);
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianC), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.clickSaveButton();
		Helpers.acceptAlert();
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "35.75");
		techniciansPopup.saveTechViewDetails();
		priceMatrixScreen.clickSaveButton();
		servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();

	}

	@Test(testName = "Test Case 11728:Test Custom service level tech split for Retail Hail", description = "Test Custom service level tech split for Retail Hail")
	public void testCustomServiceLevelTechSplitForRetailHail() {
		String tcname = "testCustomServiceLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectOtherQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
		Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
		PriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		Assert.assertEquals(priceMatrixScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		Assert.assertTrue(priceMatrixScreen.isNotesExists());
		Assert.assertTrue(priceMatrixScreen.isTechniciansExists());
		TechniciansPopup techniciansPopup = priceMatrixScreen.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selectTechniciansCustomView();
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(
				techniciansPopup.getTechnicianPrice(UtilConstants.technicianA),
				PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianA, "285");
		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertTrue(alerttext.contains("Split amount should be equal to total amount."));
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "40");
		techniciansPopup.saveTechViewDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianB));

		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), "%25.000");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
		Assert.assertEquals(priceMatrixScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		Assert.assertTrue(priceMatrixScreen.isNotesExists());
		Assert.assertTrue(priceMatrixScreen.isTechniciansExists());

		techniciansPopup = priceMatrixScreen.openTechniciansPopup();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selectTechniciansCustomView();
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianA, "125");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "75");
		techniciansPopup.saveTechViewDetails();
		priceMatrixScreen = new PriceMatrixScreen();
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianA));
		Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
				UtilConstants.technicianB));

		priceMatrixScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));

		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selectTechniciansCustomView();
		techniciansPopup.unselecTechnician(UtilConstants.technicianA);
		//techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "100");
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianC, "45");

		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.clickSaveButton();
		Helpers.acceptAlert();
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "121.25");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();

	}
	
	@Test(testName = "Test Case 12626:Test Customer Discount on Retail Hail", description = "Test Customer Discount on Retail Hail")
	public void testCustomerDiscountOnRetailHail() {
		String tcname = "testCustomerDiscountOnRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));


		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.openServiceDetails("Customer Discount");
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();

	}
	
	@Test(testName = "Test Case 12627:Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice", description = "Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice")
	public void testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice() {
		String tcname = "testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Roof Rail", "Right Roof Rail",
				"Roof" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.searchAvailableService(UtilConstants.PAINTROCKERPANELS_SUBSERVICE);
		servicesScreen.openCustomServiceDetails(UtilConstants.PAINTROCKERPANELS_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.cancelSearchAvailableService();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesScreen.searchAvailableService(UtilConstants.WHEEL_SUBSERVICE);
		servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		String wonumber = orderSummaryScreen.getWorkOrderNumber();
		orderSummaryScreen.clickSave();
		myWorkOrdersScreen = new MyWorkOrdersScreen();
		homeScreen = myWorkOrdersScreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homeScreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickCreateInvoiceIconForWO(wonumber);
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

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String inspection = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesScreen.selectService(UtilConstants.WHEEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homeScreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(inspection);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectMainPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Active");
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDRPANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");

		ordermonitorscreen.selectMainPanel(UtilConstants.PAINT_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
 		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		
		ordermonitorscreen.selectMainPanel(UtilConstants.WHEELS_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.WHEEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection);
		
		Assert.assertTrue(teamworkordersscreen.isCreateInvoiceActivated(inspection));
		teamworkordersscreen.clickiCreateInvoiceButton();
        InvoiceInfoScreen invoiceInfoScreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceInfoScreen.clickSaveAsFinal();
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

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String inspection = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		
		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesScreen.selectService(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();


		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homeScreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(inspection);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectMainPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses("Paint - Full Bumper");
		for (String status : statuses)
			Assert.assertEquals(status, "Active");
		
		ordermonitorscreen.clickServicesButton();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice4(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartstoadd.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartstoadd[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen = new ServicesScreen();
		servicesScreen.clickSave();
		ordermonitorscreen = new OrderMonitorScreen();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Queued");

		ordermonitorscreen.selectMainPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDRPANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Active");

		ordermonitorscreen.selectMainPanel(UtilConstants.PAINT_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Active");

		ordermonitorscreen.selectMainPanel(UtilConstants.WHEELS_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");

		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection);
		teamworkordersscreen.clickiCreateInvoiceButton();
        InvoiceInfoScreen invoiceInfoScreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceInfoScreen.clickSaveAsFinal();
		teamworkordersscreen = new TeamWorkOrdersScreen();
		teamworkordersscreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 12631:Test Quantity does not mulitply price in Route package", description = "Test Quantity does not mulitply price in Route package")
	public void testQuantityDoesNotMulitplyPriceInRoutePackage() {
		String tcname = "testQuantityDoesNotMulitplyPriceInRoutePackage";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String vehiclepart = "Left Roof Rail";
		final String[] vehiclepartspaint = { "Hood", "Grill", "Left Fender" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Left Rear Wheel" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.setServiceQuantityValue("4");
		//selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehiclePart(vehiclepart);

		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.selectService(UtilConstants.PAINTPANEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedServiceDetailsScreen.setServiceQuantityValue("5");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehicleParts(vehiclepartspaint);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesScreen.selectService(UtilConstants.WHEEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehicleParts(vehiclepartswheel);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		//orderSummaryScreen.clickSaveButton();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceInfoScreen.clickSaveAsFinal();
        myWorkOrdersScreen = new MyWorkOrdersScreen();
		myWorkOrdersScreen.clickHomeButton();		
	}
	
	@Test(testName = "Test Case 12632:Test Delete Work Order function", description = "Test Delete Work Order function")
	public void testDeleteWorkOrderFunction() {
		String tcname = "testDeleteWorkOrderFunction";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Roof" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.deleteWorkOrderViaAction(workOrderNumber);
		Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
		myWorkOrdersScreen.clickDoneButton();
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(testName = "Test Case 12637:Test changing customer on Inspection", description = "Test changing customer on Inspection")
	public void testChangingCustomerOnInspection() {
		String tcname = "testChangingCustomerOnInspection";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Front Wheel", "Left Rear Wheel" };

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.DETAIL_SERVICE);
		servicesScreen.selectService(UtilConstants.WASH_N_VAC_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(UtilConstants.WASH_N_VAC_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesScreen.openCustomServiceDetails(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.clickVehiclePartsCell();
	
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		myInspectionsScreen.changeCustomerForInspection(inspNumber, customer);
		myInspectionsScreen.clickHomeButton();
		
		customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionExists(inspNumber));
		myInspectionsScreen.clickHomeButton();
		
		customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        Assert.assertFalse(myInspectionsScreen.isInspectionExists(inspNumber));
		myInspectionsScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12638:Test Retail Hail package quantity multiplier", description = "Test Retail Hail package quantity multiplier")
	public void testRetailHailPackageQuantityMultiplier() {
		String tcname = "testRetailHailPackageQuantityMultiplier";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String servicequantity = "3";
		final String servicequantity2 = "4.5";
		final String totalsumm = "$3,738.00";

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.acceptForReminderNoDrilling();

		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectOutsideQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToClaimScreen();
		ClaimScreen claimScreen = new ClaimScreen();
		claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.setServiceQuantityValue(servicequantity);	
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		servicesScreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedServiceDetailsScreen.setServiceQuantityValue(servicequantity2);	
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), totalsumm);
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
		//orderSummaryScreen.clickSaveButton();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL_NO_DISCOUNT_INVOICE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), totalsumm);
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen = new MyWorkOrdersScreen();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12641:Test custom WO level split for Route package", description = "Test custom WO level split for Route package")
	public void testCustomWOLevelSplitForRoutePackage() {
		String tcname = "testCustomWOLevelSplitForRoutePackage";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Right Mirror", "Left Mirror" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		TechniciansPopup techniciansPopup= vehicleScreen.clickTech();
		techniciansPopup.selectTechniciansCustomView();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		techniciansPopup.setTechnicianCustomPercentageValue(UtilConstants.technicianA, "30");
		//selectedServiceDetailsScreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB, "30");
		techniciansPopup.setTechnicianCustomPercentageValue(UtilConstants.technicianA, "70");
		
		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen =  servicesScreen.openCustomServiceDetails(UtilConstants.DUELEATHER_SUBSERVICE);

		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);

		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(techniciansPopup.getTechnicianPercentage(UtilConstants.technicianA), "$29.40");
		Assert.assertEquals(techniciansPopup.getTechnicianPercentage(UtilConstants.technicianB), "$12.60");
		techniciansPopup.selectTechniciansCustomView();
		techniciansPopup.unselecTechnician(UtilConstants.technicianA);
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianC, "31.50");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianC), "%75.00");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "10.50");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianB), "%25.00");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
	
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);

		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		Assert.assertEquals(techniciansPopup.getTechnicianPercentage(UtilConstants.technicianA), "$70.00");
		Assert.assertEquals(techniciansPopup.getTechnicianPercentage(UtilConstants.technicianB), "$30.00");
		techniciansPopup.selectTechniciansCustomView();
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianC, "28");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianC), "%28.00");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianA, "67");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianA), "%67.00");
		techniciansPopup.setTechnicianCustomPriceValue(UtilConstants.technicianB, "5");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianB), "%5.00");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		
		servicesScreen.clickServiceTypesButton();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		//orderSummaryScreen.clickSaveButton();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen = new MyWorkOrdersScreen();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12642:Test even WO level split for Route package", description = "Test even WO level split for Route package")
	public void testEvenWOLevelSplitForRoutePackage() {
		String tcname = "testEvenWOLevelSplitForRoutePackage";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehicleparts = { "Left Fender", "Left Front Door", "Left Quarter Panel", "Left Rear Door", "Left Roof Rail" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		TechniciansPopup techniciansPopup = vehicleScreen.clickTech();
		Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(UtilConstants.technicianA));
		techniciansPopup.selecTechnician(UtilConstants.technicianB);
		Assert.assertEquals(techniciansPopup.getTechnicianPercentage(UtilConstants.technicianA), "%50.00");
		Assert.assertEquals(techniciansPopup.getTechnicianPercentage(UtilConstants.technicianB), "%50.00");

		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.DETAIL_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FRONTLINEREADY_SUBSERVICE);

		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.OTHER_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.unselecTechnician(UtilConstants.technicianB);
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(UtilConstants.technicianC), "$108.00");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianC), "%50.00");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);

		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
		techniciansPopup.selecTechnician(UtilConstants.technicianC);
		Assert.assertEquals(techniciansPopup.getTechnicianPrice(UtilConstants.technicianC), "$80.00");
		Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(UtilConstants.technicianC), "%33.33");
		techniciansPopup.saveTechViewDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		//orderSummaryScreen.clickSaveButton();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen = new MyWorkOrdersScreen();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12644:Test adding notes to a Work Order", description = "Test adding notes to a Work Order")
	public void testAddingNotesToWorkOrder() {
		String tcname = "testAddingNotesToWorkOrder";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = {  "Hood", "Left Rear Door", "Right Fender" };
		final String[] vehiclepartspaint = {  "Dashboard", "Deck Lid" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		NotesScreen notesScreen = vehicleScreen.clickNotesButton();
		notesScreen.setNotes("Blue fender");
		//notesScreen.clickDoneButton();
		notesScreen.clickSaveButton();

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.clickNotesCell();
		notesScreen.setNotes("Declined right door");
		notesScreen.clickSaveButton();
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehicleParts(vehicleparts);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.selectService(UtilConstants.PAINTPANEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.clickNotesCell();
		notesScreen.setNotes("Declined hood");
		notesScreen.clickSaveButton();
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehicleParts(vehiclepartspaint);

		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		String invoicenum = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
        myWorkOrdersScreen = new MyWorkOrdersScreen();
		myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		Assert.assertTrue(myInvoicesScreen.myInvoiceExists(invoicenum));
		myInvoicesScreen.selectInvoice(invoicenum);
		notesScreen = myInvoicesScreen.clickNotesPopup();
		notesScreen.setNotes("Declined wheel work");
		notesScreen.clickSaveButton();
		myInvoicesScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12645:Test changing the PO# on an invoice", description = "Test changing the PO# on an invoice")
	public void testChangingThePOOnAnInvoice() {
		String tcname = "testChangingThePOOnAnInvoice";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale("1");
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
		invoiceInfoScreen.setPO("832145");
		String invoicenum = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();

		myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		Assert.assertTrue(myInvoicesScreen.myInvoiceExists(invoicenum));
		myInvoicesScreen.selectInvoice(invoicenum);
		myInvoicesScreen.clickChangePOPopup();
		myInvoicesScreen.changePO("832710");
		myInvoicesScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12646:Test editing an Inspection", description = "Test editing an Inspection")
	public void testEditingAnInspection() {
		String tcname = "testEditingAnInspection";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Front Door", "Left Rear Door", "Right Front Door", "Right Rear Door" };

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.saveWizard();
		myInspectionsScreen.selectInspectionInTable(inspNumber);
		myInspectionsScreen.clickEditInspectionButton();
		vehicleScreen = new VehicleScreen();
		NavigationSteps.navigateToServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		myInspectionsScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12647:Test editing a Work Order", description = "Test editing a Work Order")
	public void testEditingWorkOrder() {
		String tcname = "testEditingWorkOrder";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		String wo = vehicleScreen.getInspectionNumber();
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
		vehicleScreen.setRO(ExcelUtils.getRO(testcaserow));
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_FABRIC_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00"));
		servicesScreen.clickServiceTypesButton();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_VINIL_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.67 x 1.00"));

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.selectWorkOrderForEidt(wo);
		vehicleScreen = new VehicleScreen();
		NavigationSteps.navigateToServicesScreen();

		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WHEELCOVER2_SUBSERVICE);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00"));
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.67 x 1.00"));
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.WHEELCOVER2_SUBSERVICE, "$45.00 x 1.00"));
		NavigationSteps.navigateToOrderSummaryScreen();
		orderSummaryScreen.saveWizard();

		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19514:Test editing an Invoice in Draft", description = "Test Editing an Invoice in Draft")
	public void testEditingAnInvoiceInDraft() {
		String tcname = "testEditingAnInvoiceInDraft";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		final String[] vehiclepartspdr = { "Left Fender", "Left Roof Rail", "Right Quarter Panel", "Trunk Lid" };
		final String[] vehiclepartspaint = { "Left Mirror" };
		final String[] vehiclepartswheel = { "Left Front Wheel", "Right Front Wheel" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails (UtilConstants.CARPETREPAIR_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspdr.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspdr[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.selectService(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		//orderSummaryScreen.clickSaveButton();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		final String invoicenumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveInvoiceAsDraft();
		myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		myInvoicesScreen.selectInvoice(invoicenumber);
		myInvoicesScreen.clickEditPopup();
		invoiceInfoScreen.clickFirstWO();
		NavigationSteps.navigateToServicesScreen();
        selectedServiceDetailsScreen = servicesScreen.openServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedServiceDetailsScreen.removeService();
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.selectService(UtilConstants.PAINTMIRROR_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehiclePart("Right Mirror");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesScreen.selectService(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();

		servicesScreen.clickSave();
		invoiceInfoScreen = new InvoiceInfoScreen();
		invoiceInfoScreen.clickSaveAsFinal();
		homeScreen = myInvoicesScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19548:Test adding a PO# to an invoice", description = "Test adding a PO# to an invoice")
	public void testAddingAPOToAnInvoice() {
		String tcname = "testAddingAPOToAnInvoice";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackeravisworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_FABRIC_SERVICE);
		servicesScreen.selectService("Tear/Burn >2\" (Fabric)");                                         
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDR6PANEL_SUBSERVICE);
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(servicesScreen.checkServiceIsSelected(UtilConstants.PDR6PANEL_SUBSERVICE));
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homeScreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(workOrderNumber);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectMainPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses("Tear/Burn >2\" (Fabric)");
		for (String status : statuses)
			Assert.assertEquals(status, "Active");

		ordermonitorscreen.selectMainPanel("Interior Repair");
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = ordermonitorscreen.getPanelsStatuses("Tear/Burn >2\" (Fabric)");
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");

		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickCreateInvoiceIconForTeamWO(workOrderNumber);
		Assert.assertTrue(teamworkordersscreen.isCreateInvoiceActivated(workOrderNumber));
		teamworkordersscreen.clickiCreateInvoiceButton();
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
		NavigationSteps.navigateToScreen("AVIS Questions");
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.waitQuestionsScreenLoaded();
		questionsScreen.chooseAVISCode("Rental-921");
		questionsScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);

		final String invoicenumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		teamworkordersscreen.clickHomeButton();
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		myInvoicesScreen.selectInvoice(invoicenumber);
		myInvoicesScreen.clickChangePOPopup();
		myInvoicesScreen.changePO("170116");
		myInvoicesScreen.clickHomeButton();
		myInvoicesScreen = homeScreen.clickMyInvoices();
		Assert.assertTrue(myInvoicesScreen.isInvoiceHasInvoiceNumberIcon(invoicenumber));
		Assert.assertTrue(myInvoicesScreen.isInvoiceHasInvoiceSharedIcon(invoicenumber));
		Assert.assertEquals(myInvoicesScreen.getInvoicePrice(invoicenumber), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		myInvoicesScreen.clickHomeButton();
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

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow1));
		String inspection1 = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow1)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		servicesScreen.saveWizard();
		
		//Create second WO
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow2));
		String inspection2 = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));

		NavigationSteps.navigateToServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.LEATHERREPAIR_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow2));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow2)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		servicesScreen.saveWizard();
		
		
		//Create third WO
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow3));
		String inspection3 = vehicleScreen.getInspectionNumber();
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow3), ExcelUtils.getModel(testcaserow3), ExcelUtils.getYear(testcaserow3));

		NavigationSteps.navigateToServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow3));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow3)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		servicesScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homeScreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(inspection1);
		OrderMonitorScreen ordermonitorscreen = teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectMainPanel(UtilConstants.PDR_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		List<String> statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.PDRVEHICLE_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickOnWO(inspection2);
		teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectMainPanel("Interior Repair");
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.LEATHERREPAIR_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();
		teamworkordersscreen.clickOnWO(inspection3);
		teamworkordersscreen.selectWOMonitor();
		ordermonitorscreen.selectMainPanel(UtilConstants.PAINT_SERVICE);
		ordermonitorscreen.clickChangeStatusCell();
		ordermonitorscreen.setCompletedPhaseStatus();
		statuses = ordermonitorscreen.getPanelsStatuses(UtilConstants.BLACKOUT_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = ordermonitorscreen.clickBackButton();

		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection1);
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection2);
		teamworkordersscreen.clickCreateInvoiceIconForWO(inspection3);
		
		teamworkordersscreen.clickiCreateInvoiceButton();
		InvoiceInfoScreen invoiceInfoScreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		invoiceInfoScreen.clickSaveAsFinal();
		teamworkordersscreen.clickHomeButton();
		
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		myInvoicesScreen.getFirstInvoice().click();
		myInvoicesScreen.clickChangePOPopup();
		myInvoicesScreen.changePO("957884");
		myInvoicesScreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 19671:Test Copy Vehicle feature", description = "Test Copy Vehicle feature")
	public void testCopyVehicleFeature() {
		String tcname = "testCopyVehicleFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.OTHER_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		MyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber, DentWizardWorkOrdersTypes.carmaxworkordertype);
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19672:Test Copy Services feature", description = "Test Copy Services feature")
	public void testCopyServicesFeature() {
		String tcname1 = "testCopyServicesFeature1";
		String tcname2 = "testCopyServicesFeature2";

		HomeScreen homeScreen = new HomeScreen();
		int testcaserow1 = ExcelUtils.getTestCaseRow(tcname1);
		int testcaserow2 = ExcelUtils.getTestCaseRow(tcname2);

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow1));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));
		String workOrderNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
		
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		MyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, DentWizardWorkOrdersTypes.routeusworkordertype);
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow2));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));
		NavigationSteps.navigateToServicesScreen();
		Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.BLACKOUT_SUBSERVICE,
				PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow1)) + " x 1.00"));
		servicesScreen.cancelWizard();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 24164:Test Pre-Existing Damage option", description = "Test Pre-Existing Damage option")
	public void testPreExistingDamageOption() {
		String tcname = "testPreExistingDamageOption";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left A Pillar", "Left Front Door", "Metal Sunroof", "Right Roof Rail" };

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.servicedriveinspectiondertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.searchAvailableService("Scratch (Exterior)");
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails("Scratch (Exterior)");
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.checkPreexistingDamage("Pre-existing damage");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();	
		servicesScreen.cancelSearchAvailableService();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesScreen.saveWizard();

		myInspectionsScreen.selectInspectionInTable(inspNumber);
		myInspectionsScreen.clickCreateWOButton();
        vehicleScreen = new VehicleScreen();
		String wonumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale("1");
		orderSummaryScreen.saveWizard();

		myInspectionsScreen.showWorkOrdersForInspection(inspNumber);
		vehicleScreen = new VehicleScreen();
		Assert.assertEquals(vehicleScreen.getInspectionNumber(), wonumber);
		servicesScreen.clickCancelButton();

		myInspectionsScreen.clickHomeButton();
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(wonumber);
        myWorkOrdersScreen.clickInvoiceIcon();
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
				invoiceInfoScreen.clickSaveAsFinal();
		homeScreen = myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19673:Test Car History feature", description = "Test Car History feature")
	public void testCarHistoryFeature() {
		String tcname = "testCarHistoryFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
		vehicleScreen.setRO(ExcelUtils.getRO(testcaserow));
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIORPLASTIC_SERVICE);
		servicesScreen.selectService(UtilConstants.SCRTCH_1_SECTPLSTC_SERVICE);

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
		String invoicenumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickHomeButton();
		CarHistoryScreen carhistoryscreen = homeScreen.clickCarHistoryButton();
		carhistoryscreen.searchCar("887340");
		String strtocompare = ExcelUtils.getYear(testcaserow) + ", " + ExcelUtils.getMake(testcaserow) + ", " + ExcelUtils.getModel(testcaserow);
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
		carhistoryscreen.clickFirstCarHistoryInTable();
		MyInvoicesScreen myInvoicesScreen = carhistoryscreen.clickCarHistoryInvoices();
		Assert.assertTrue(myInvoicesScreen.myInvoicesIsDisplayed());
		myInvoicesScreen.clickBackButton();

		carhistoryscreen.clickSwitchToWeb();
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
		Assert.assertEquals(carhistoryscreen.getFirstCarHistoryDetailsInTable(), strtocompare);
		carhistoryscreen.clickFirstCarHistoryInTable();
		TeamInvoicesScreen teamInvoicesScreen = carhistoryscreen.clickCarHistoryTeamInvoices();

		Assert.assertTrue(teamInvoicesScreen.teamInvoicesIsDisplayed());
		Assert.assertTrue(teamInvoicesScreen.isInvoiceExists(invoicenumber));
		teamInvoicesScreen.clickBackButton();
		carhistoryscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19676:Test Total Sale requirement", description = "Test Total Sale requirement")
	public void testTotalSaleRequirement() {
		String tcname = "testTotalSaleRequirement";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Hood", "Left Fender" };
		final String totalsale = "675";

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();

		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackerservicedriveworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Total Sale is required."));
		orderSummaryScreen.setTotalSale(totalsale);		
		orderSummaryScreen.clickSave();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19678:Test package pricing for read only items", description = "Test package pricing for read only items")
	public void testPackagePricingForReadOnlyItems() {
		String tcname = "testPackagePricingForReadOnlyItems";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Fender", "Left Roof Rail", "Right Fender" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
		vehicleScreen.setRO(ExcelUtils.getRO(testcaserow));
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.INTERIOR_LEATHER_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.CRT_2_RPR_LTHR_SUBSERVICE);
		Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentsValue(), "-$14.48");
		selectedServiceDetailsScreen.setServiceQuantityValue("3.00");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		Assert.assertEquals(PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)), servicesScreen.getTotalAmaunt());
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.CUST2PNL_SUBSERVICE);
		Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentsValue(), "-$6.55");
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		servicesScreen.clickServiceTypesButton();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19681:Test price policy for service items from Inspection through Invoice creation", description = "Test price policy for service items from Inspection through Invoice creation")
	public void testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation() {
		String tcname = "testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Front Door", "Left Rear Door", "Right Fender" };
		final String[] vehiclepartspaint = { "Hood", "Left Fender" };

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
			
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.selectService(UtilConstants.PAINTPANEL_SUBSERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();	
		servicesScreen.saveWizard();;

		Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspNumber), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		myInspectionsScreen.selectInspectionInTable(inspNumber);
		myInspectionsScreen.clickCreateWOButton();
		vehicleScreen = new VehicleScreen();
		String wonumber = vehicleScreen.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		for (int i = 0; i < vehicleparts.length; i++) {
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.PDRVEHICLE_SUBSERVICE , vehicleparts[i]
					, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)) + " x 1.00"));
		}
		for (int i = 0; i < vehiclepartspaint.length; i++) {
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(UtilConstants.PAINTPANEL_SUBSERVICE , vehiclepartspaint[i]
					, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)) + " x 1.00"));
		}
		servicesScreen.saveWizard();

		myInspectionsScreen.showWorkOrdersForInspection(inspNumber);
		vehicleScreen = new VehicleScreen();
		Assert.assertEquals(vehicleScreen.getInspectionNumber(), wonumber);
		servicesScreen.clickCancelButton();

		myInspectionsScreen.clickHomeButton();
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(wonumber);
		myWorkOrdersScreen.clickInvoiceIcon();
		InvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 10263:Send Multiple Emails", description = "Send Multiple Emails")
	public void testSendMultipleEmails() {

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		myInvoicesScreen.clickActionButton();
		for (int i = 0; i< 4; i++) {
			myInvoicesScreen.selectInvoiceForActionByIndex(i+1);
		}
		myInvoicesScreen.clickActionButton();
		//myInvoicesScreen.sendEmail();
		myInvoicesScreen.sendSingleEmail(UtilConstants.TEST_EMAIL);
		myInvoicesScreen.clickDoneButton();
		myInvoicesScreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 19683:Test Work Order Discount Override feature", description = "Test Work Order Discount Override feature")
	public void testWorkOrderDiscountOverrideFeature() {
		String tcname = "testWorkOrderDiscountOverrideFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String  _customer   = "Bel Air Auto Auction Inc";
		final String[] vehicleparts = { "Left Fender", "Right Fender"};

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(_customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), "-$28.50");
		selectedServiceDetailsScreen.clickVehiclePartsCell();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.selectService(UtilConstants.WSANDBPANEL_SERVICE);
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		selectedServiceDetailsScreen.selectVehiclePart("Hood");
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		//orderSummaryScreen.clickSaveButton();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
		invoiceInfoScreen.clickSaveAsFinal();
		myWorkOrdersScreen = new MyWorkOrdersScreen();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 19685:Test completed RO only requirement for invoicing", description = "Test Completed RO only requirement for invoicing")
	public void testCompletedROOnlyRequirementForInvoicing() {
		String tcname = "testCompletedROOnlyRequirementForInvoicing";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Left Quarter Panel", "Right Roof Rail", "Trunk Lid" };
		final String[] vehiclepartswheel = { "Right Front Wheel", "Right Rear Wheel" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRPANEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		servicesScreen.searchAvailableService(UtilConstants.PAINTTOWHOOKCOVER_SUBSERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTTOWHOOKCOVER_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.cancelSearchAvailableService();
		servicesScreen.clickServiceTypesButton();
				
		servicesScreen.selectGroupServiceItem(UtilConstants.WHEELS_SERVICE);
		servicesScreen.searchAvailableService(UtilConstants.WHEEL_SUBSERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		for (int i = 0; i < vehiclepartswheel.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		String wonumber = orderSummaryScreen.getWorkOrderNumber();
		orderSummaryScreen.clickSave();
		myWorkOrdersScreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homeScreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(wonumber);
		teamworkordersscreen.selectEditWO();
		vehicleScreen.waitVehicleScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		Assert.assertFalse(orderSummaryScreen.isApproveAndCreateInvoiceExists());
		orderSummaryScreen.saveWizard();

		teamworkordersscreen.clickCreateInvoiceIconForWO(wonumber);

		teamworkordersscreen.clickiCreateInvoiceButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
		teamworkordersscreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 10659:Test Enterprise Work Order question forms inforced", description = "Test Enterprize Work Order question forms inforced")
	public void testEnterprizeWorkOrderQuestionFormsInforced() {
		String tcname = "testEnterprizeWorkOrderQuestionFormsInforced";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.enterpriseworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		NavigationSteps.navigateToScreen(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE.getDefaultScreenTypeName());
		EnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new EnterpriseBeforeDamageScreen();
		enterprisebeforedamagescreen.clickSave();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'VIN' in section 'Enterprise Before Damage' should be answered."));
		enterprisebeforedamagescreen.setVINCapture();
		enterprisebeforedamagescreen.clickSave();
		alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains("Question 'License Plate' in section 'Enterprise Before Damage' should be answered."));
		enterprisebeforedamagescreen.setLicensePlateCapture();

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12633:Test successful email of pictures using Notes feature", description = "Test successful email of pictures using Notes feature")
	public void testSuccessfulEmailOfPicturesUsingNotesFeature() {
		String tcname = "testSuccessfulEmailOfPicturesUsingNotesFeature";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.avisworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
		String wo = vehicleScreen.getInspectionNumber();
		NotesScreen notesScreen = vehicleScreen.clickNotesButton();
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
		notesScreen.clickSaveButton();

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.clickNotesButton();
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 2);
		notesScreen.clickSaveButton();
		
		servicesScreen.selectService(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDR2PANEL_SUBSERVICE);
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		selectedServiceDetailsScreen.clickNotesCell();
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
		notesScreen.clickSaveButton();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToScreen(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE.getDefaultScreenTypeName());
		EnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new EnterpriseBeforeDamageScreen();
		enterprisebeforedamagescreen.setVINCapture();
		enterprisebeforedamagescreen.setLicensePlateCapture();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.selectWorkOrderForAddingNotes(wo);
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 3);
		notesScreen.clickSaveButton();
		
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(wo);
		myWorkOrdersScreen.clickInvoiceIcon();
		QuestionsScreen questionsScreen = new QuestionsScreen();
		NavigationSteps.navigateToScreen("AVIS Questions");
        questionsScreen.chooseAVISCode("Other-920");
        final String invoiceNumber = questionsScreen.getInvoiceNumber();
		questionsScreen.clickSaveAsFinal();
		
		myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		myInvoicesScreen.selectInvoice(invoiceNumber);
		notesScreen =  myInvoicesScreen.clickNotesPopup();
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
		notesScreen.clickSaveButton();
		myInvoicesScreen.clickActionButton();
		myInvoicesScreen.selectInvoiceForActionByIndex(1);
		myInvoicesScreen.clickActionButton();
		myInvoicesScreen.sendEmail(UtilConstants.TEST_EMAIL);
		myInvoicesScreen.clickDoneButton();
		myInvoicesScreen.clickHomeButton();	
	}
	
	@Test(testName = "Test Case 12634:Test emailing photos in Economical Inspection", description = "Test emailing photos in Economical Inspection")
	public void testEmailingPhotosInEconomicalInspection() {
		String tcname = "testEmailingPhotosInEconomicalInspection";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		
		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.economicalinspectiondertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		final String inspNumber = vehicleScreen.getInspectionNumber();
		NotesScreen notesScreen = vehicleScreen.clickNotesButton();
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
		notesScreen.clickSaveButton();

		NavigationSteps.navigateToScreen(UtilConstants.HAIL_PHOTOS_SCREEN_CAPTION);
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.makeCaptureForQuestion("VIN");
		questionsScreen.makeCaptureForQuestion("Odometer");
		questionsScreen.swipeScreenRight();
		questionsScreen = new QuestionsScreen();
		questionsScreen.makeCaptureForQuestion("License Plate Number");
		questionsScreen.makeCaptureForQuestion("Left Front of Vehicle");
		questionsScreen.swipeScreenRight();
		questionsScreen = new QuestionsScreen();
		questionsScreen.makeCaptureForQuestion("Right Front of Vehicle");
		questionsScreen.makeCaptureForQuestion("Right Rear of Vehicle");
		questionsScreen.swipeScreenRight();
		questionsScreen = new QuestionsScreen();
		questionsScreen.makeCaptureForQuestion("Left Rear of Vehicle");
		NavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
		questionsScreen.selectProperQuestions();
		
		int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
		questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow), 
				ExcelUtils.getownerState(retailhaildatarow), UtilConstants.CANADA, ExcelUtils.getOwnerZip(retailhaildatarow));

		NavigationSteps.navigateToScreen(UtilConstants.PRICE_MATRIX_SCREEN_CAPTION);
		PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
		priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
		priceMatrixScreen.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
		priceMatrixScreen.clickNotesButton();
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 2);
		notesScreen.clickSaveButton();

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails("E-Coat");
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.clickNotesCell();
		notesScreen.addNotesCapture();
		notesScreen.clickSaveButton();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.saveWizard();
		myInspectionsScreen.selectInspectionInTable(inspNumber);
		MyInvoicesScreen myInvoicesScreen = new MyInvoicesScreen();
		myInvoicesScreen.sendEmail(UtilConstants.TEST_EMAIL);
        myInspectionsScreen = new MyInspectionsScreen();
		myInspectionsScreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 12635:Test emailing photos in Auction package", description = "Test emailing photos in Auction package")
	public void testEmailingPhotosInAuctionPackage() {
		String tcname = "testEmailingPhotosInAuctionPackage";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Hood", "Left Fender", "Right Fender", "Roof" };
		final String firstnote = "Refused paint";
		final String secondnote = "Just 4 panels";

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.auctionworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		String wo = vehicleScreen.getInspectionNumber();
		NotesScreen notesScreen = vehicleScreen.clickNotesButton();
		notesScreen.setNotes(firstnote);
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
		notesScreen.clickSaveButton();

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
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
		selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		//selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();

		NavigationSteps.navigateToQuestionsScreen();
		QuestionsScreen questionsScreen = new QuestionsScreen();
		questionsScreen.chooseConsignor("Unknown Consignor/One Off-718");

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.selectWorkOrderForAddingNotes(wo);
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 2);
		notesScreen.clickSaveButton();
		
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(wo);
		myWorkOrdersScreen.clickInvoiceIcon();
		InvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.AUCTION_NO_DISCOUNT_INVOICE);
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
		invoiceInfoScreen.clickSaveAsFinal();
		
		myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		myInvoicesScreen.clickActionButton();
		myInvoicesScreen.selectInvoiceForActionByIndex(1);
		myInvoicesScreen.clickActionButton();
		myInvoicesScreen.sendEmail(UtilConstants.TEST_EMAIL);
		myInvoicesScreen.clickDoneButton();
		myInvoicesScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 12636:Test emailing photos in Service Drive package", description = "Test emailing photos in Service Drive package")
	public void testEmailingPhotosInServiceDrivePackage() {
		String tcname = "testEmailingPhotosInServiceDrivePackage";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String[] vehicleparts = { "Decklid", "Left A Pillar" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.PDR_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.clickVehiclePartsCell();
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		servicesScreen.clickServiceTypesButton();
		
		
		servicesScreen.selectService(UtilConstants.INTERIOR_SERVICE);
		selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.STAIN_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale("1");
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
        myWorkOrdersScreen = new MyWorkOrdersScreen();
		myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		myInvoicesScreen.selectInvoice(invoiceNumber);
		NotesScreen notesScreen = myInvoicesScreen.clickNotesPopup();
		notesScreen.setNotes("Refused paint");
		notesScreen.addNotesCapture();
		Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
		notesScreen.clickSaveButton();
		
		myInvoicesScreen.clickActionButton();
		myInvoicesScreen.selectInvoiceForActionByIndex(1);
		myInvoicesScreen.clickActionButton();
		myInvoicesScreen.sendEmail(UtilConstants.TEST_EMAIL);
		myInvoicesScreen.clickDoneButton();
		myInvoicesScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 24131:Test PO# saves with active keyboard on WO summary screen", description = "Test PO# saves with active keyboard on WO summary screen")
	public void testPONumberSavesWithActiveKeyboardOnWOSummaryScreen() {
		String tcname = "testPONumberSavesWithActiveKeyboardOnWOSummaryScreen";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String _po = "998601";
		final String[] vehicleparts = { "Hood", "Left Quarter Panel", "Right Roof Rail" };
		
		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PDR_SERVICE);
		servicesScreen.selectService(UtilConstants.PDRVEHICLE_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		//orderSummaryScreen.clickSaveButton();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		invoiceInfoScreen.setPO(_po);
		invoiceInfoScreen.clickSaveAsFinal();			
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "Test Case 24132:Test Total Sale saves with active keyboard on WO summary screen", description = "Test Total Sale saves with active keyboard on WO summary screen")
	public void testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen() {
		String tcname = "testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);
		final String totalsale = "675";

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectGroupServiceItem(UtilConstants.PAINT_SERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.setTotalSale(totalsale);
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
	}
	
	@Test(testName = "", description = "Test Invoice Question Answers save with active keyboard on Questions summary screen")
	public void testInvoiceQuestionAnswersSaveWithActiveKeyboardOnQuestionsSummaryScreen () {
		String tcname = "testInvoiceQuestionAnswersSaveWithActiveKeyboardOnQuestionsSummaryScreen";		
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		final String _po = "998601";
		final String[] vehicleparts = { "Left Front Wheel", "Left Rear Wheel" };

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
		vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.selectService(UtilConstants.WHEELS_SERVICE);
		servicesScreen.selectService(UtilConstants.WHEEL_SUBSERVICE);
		SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
		selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));		
		selectedServiceDetailsScreen.saveSelectedServiceDetails();

		Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
		for (int i = 0; i < vehicleparts.length; i++) {
			selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
		}
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		selectedServiceDetailsScreen.saveSelectedServiceDetails();
		Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.DING_SHIELD);
		invoiceInfoScreen.setPO(_po);
		invoiceInfoScreen.clickSaveAsFinal();		
		myWorkOrdersScreen.clickHomeButton();
	}
}

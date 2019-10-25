package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.hdvalidations.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularOrderMonitorScreenSteps;
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testTurningMultipleWorkOrdersIntoASingleInvoice(String rowID,
																String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<WorkOrderData> workOrdersData = testCaseData.getWorkOrdersData();

		WorkOrderData workOrderData1 = workOrdersData.get(0);

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInsvoicesCustomLayoutOff();
		settingsscreen.clickHomeButton();
		
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData1.getVehicleInfoData().getVINNumber());

		String workOrderNumber1 = VehicleInfoScreenSteps.getInspectionNumber();
		VehicleInfoValidations.validateVehicleInfoData(workOrderData1.getVehicleInfoData());
		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : workOrderData1.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData1.getWorkOrderPrice()));
		orderSummaryScreen.saveWizard();

		// ==================Create second WO=============
		WorkOrderData workOrderData2 = workOrdersData.get(1);
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData2.getVehicleInfoData().getVINNumber());

		String workOrderNumber2 = VehicleInfoScreenSteps.getInspectionNumber();
		VehicleInfoValidations.validateVehicleInfoData(workOrderData2.getVehicleInfoData());

		NavigationSteps.navigateToServicesScreen();

		int i = 1;
		for (DamageData damageData : workOrderData2.getDamagesData()) {
			ServicesScreenSteps.selectGroupServiceItem(damageData);
			ServicesScreenSteps.openCustomServiceDetails(damageData.getMoneyService().getServiceName());
			ServiceDetailsScreenSteps.setServiceDetailsData(damageData.getMoneyService());
			ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
			if (i == 1) {

				TechniciansPopupValidations.verifyServiceTechnicianIsSelected(damageData.getMoneyService().getServiceDefaultTechnician());
				for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
					TechniciansPopupSteps.selectServiceTechnician(serviceTechnician);

				TechniciansPopupValidations.verifyServiceTechnicianPriceValue(damageData.getMoneyService().getServiceDefaultTechnician(),
						damageData.getMoneyService().getServiceDefaultTechnician().getTechnicianPriceValue());
				TechniciansPopupValidations.verifyServiceTechnicianIsSelected(damageData.getMoneyService().getServiceDefaultTechnician());
				for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
					TechniciansPopupValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
							serviceTechnician.getTechnicianPriceValue());
				i++;
			} else {
				TechniciansPopupSteps.selectTechniciansCustomView();
				for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
					TechniciansPopupSteps.selectServiceTechnician(serviceTechnician);

				TechniciansPopupSteps.setTechnicianCustomPriceValue(damageData.getMoneyService().getServiceDefaultTechnician());
				for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
					TechniciansPopupSteps.setTechnicianCustomPriceValue(serviceTechnician);


				TechniciansPopupValidations.verifyServiceTechnicianPriceValue(damageData.getMoneyService().getServiceDefaultTechnician(),
						damageData.getMoneyService().getServiceDefaultTechnician().getTechnicianPriceValue());
				TechniciansPopupValidations.verifyServiceTechnicianIsSelected(damageData.getMoneyService().getServiceDefaultTechnician());
				for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
					TechniciansPopupValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
							serviceTechnician.getTechnicianPriceValue());
			}
			TechniciansPopupSteps.saveTechViewDetails();
			ServiceDetailsScreenSteps.saveServiceDetails();
			ServicesScreenSteps.clickServiceTypesButton();
		}
		NavigationSteps.navigateToOrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
		myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber2);
		myWorkOrdersScreen.clickInvoiceIcon();
		InvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        Assert.assertTrue(invoiceInfoScreen.isWOSelected(workOrderNumber1));
        Assert.assertTrue(invoiceInfoScreen.isWOSelected(workOrderNumber2));
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.clickSaveAsFinal();
		Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber1));
		Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber2));
		myWorkOrdersScreen.clickHomeButton();
		MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
		final String wosubstring = workOrderNumber1 + ", " + workOrderNumber2;
		Assert.assertEquals(myInvoicesScreen.getInvoiceInfoLabel(invoiceNumber), wosubstring);
		myInvoicesScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice(String rowID,
																						String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<WorkOrderData> workOrdersData = testCaseData.getWorkOrdersData();
		List<String> workOrders = new ArrayList<>();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();

		for (WorkOrderData workOrderData : workOrdersData) {
			MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
			VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

			workOrders.add(VehicleInfoScreenSteps.getInspectionNumber());
			VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

			NavigationSteps.navigateToServicesScreen();
			for (DamageData damageData : workOrderData.getDamagesData()) {
				ServicesScreenSteps.selectPanelServiceData(damageData);
				ServicesScreenSteps.clickServiceTypesButton();
			}
			NavigationSteps.navigateToOrderSummaryScreen();
			WorkOrdersSteps.saveWorkOrder();
		}

		myWorkOrdersScreen.clickCreateInvoiceIconForWOs(workOrders);
		myWorkOrdersScreen.clickInvoiceIcon();
		InvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(
				alerttext,
				"Invoice type " + DentWizardInvoiceTypes.NO_ORDER_TYPE.getInvoiceTypeName() + " doesn't support multiple Work Order types.");
		NavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker(String rowID,
																								String description, JSONObject testData) {
		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizprotrackerrouteunspectiontype);
		VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspNumber = VehicleInfoScreenSteps.getInspectionNumber();
		VehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : inspectionData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}
		InspectionsSteps.saveInspection();
		myInspectionsScreen.approveInspectionWithSignature(inspNumber);
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspNumber));
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsConvertToWorkOrder(String rowID,
												  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizprotrackerrouteunspectiontype);
		VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		final String inspNumber = VehicleInfoScreenSteps.getInspectionNumber();
		VehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : inspectionData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}
		WizardScreenValidations.verifyScreenSubTotalPrice(inspectionData.getInspectionPrice());
		InspectionsSteps.saveInspection();

		myInspectionsScreen = new MyInspectionsScreen();
		myInspectionsScreen.approveInspectionWithSignature(inspNumber);
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspNumber));
		myInspectionsScreen.selectInspectionInTable(inspNumber);
		myInspectionsScreen.clickCreateWOButton();
		VehicleInfoScreenSteps.waitVehicleScreenLoaded();
		NavigationSteps.navigateToServicesScreen();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		String wonumber = orderSummaryScreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(testCaseData.getWorkOrderData().getWorkOrderPrice()));
		orderSummaryScreen.saveWizard();

		myInspectionsScreen.clickHomeButton();
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		myWorkOrdersScreen.isWorkOrderPresent(wonumber);
		myWorkOrdersScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels(String rowID,
																						 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : workOrderData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}
		WizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		WorkOrdersSteps.cancelCreatingWorkOrder();
		NavigationSteps.navigateBackScreen();

		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels(String rowID,
																				  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : workOrderData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}
		WizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));

		WorkOrdersSteps.cancelCreatingWorkOrder();
		NavigationSteps.navigateBackScreen();

		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCarmaxVehicleInformationRequirements(String rowID,
														 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

		VehicleScreen vehicleScreen = new VehicleScreen();
		Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("Stock# is required"));
		VehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
		Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("RO# is required"));
		VehicleInfoScreenSteps.setRoNumber(workOrderData.getVehicleInfoData().getRoNumber());
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.cancelWizard();
		NavigationSteps.navigateBackScreen();

		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setCheckDuplicatesOff();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServiceDriveRequiresAdvisor(String rowID,
												String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("Advisor is required"));
		VehicleInfoScreenSteps.selectAdvisor(workOrderData.getVehicleInfoData().getVehicleAdvisor());
		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.cancelWizard();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionRequirementsInforced(String rowID,
												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();

		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizardprotrackerrouteinspectiondertype);
		VehicleScreen vehicleScreen = new VehicleScreen();
		String alerttext = vehicleScreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("VIN# is required"));
		VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		vehicleScreen.clickSave();
		alerttext = Helpers.getAlertTextAndAccept();
		//alerttext = inspectionscreen.clickSaveWithAlert();
		Assert.assertTrue(alerttext.contains("Advisor is required"));
		VehicleInfoScreenSteps.selectAdvisor(inspectionData.getVehicleInfo().getVehicleAdvisor());
		NavigationSteps.navigateToQuestionsScreen();
        QuestionsScreen questionsScreen =  new QuestionsScreen();
        questionsScreen.saveWizard();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionsCanConvertToMultipleWorkOrders(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();

		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routecanadainspectiontype);
		VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
		final String inspNumber = VehicleInfoScreenSteps.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : inspectionData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}
		InspectionsSteps.saveInspection();
		myInspectionsScreen.selectInspectionInTable(inspNumber);
		myInspectionsScreen.clickCreateWOButton();
		VehicleInfoScreenSteps.waitVehicleScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		String wonumber = orderSummaryScreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		orderSummaryScreen.saveWizard();
		myInspectionsScreen.showWorkOrdersForInspection(inspNumber);
		VehicleScreen vehicleScreen = new VehicleScreen();
		Assert.assertEquals(vehicleScreen.getInspectionNumber(), wonumber);
		vehicleScreen.clickCancelButton();
		
		myInspectionsScreen.selectInspectionInTable(inspNumber);
		myInspectionsScreen.clickCreateWOButton();
		VehicleInfoScreenSteps.waitVehicleScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		String wonumber2 = orderSummaryScreen.getWorkOrderNumber();
		Assert.assertEquals(wonumber2.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testArchiveFeatureForInspections(String rowID,
												 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
		VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
		final String inspNumber = VehicleInfoScreenSteps.getInspectionNumber();

		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : inspectionData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}
		InspectionsSteps.saveInspection();
		myInspectionsScreen.selectInspectionInTable(inspNumber);

		myInspectionsScreen.clickArchiveInspectionButton();
		myInspectionsScreen.clickFilterButton();
		myInspectionsScreen.clickStatusFilter();
		myInspectionsScreen.isFilterStatusSelected(InspectionStatus.NEW.getStatus());
		myInspectionsScreen.isFilterStatusSelected(InspectionStatus.APPROVED.getStatus());
		myInspectionsScreen.clickFilterStatus(InspectionStatus.NEW.getStatus());
		myInspectionsScreen.clickFilterStatus(InspectionStatus.APPROVED.getStatus());
		myInspectionsScreen.clickFilterStatus(InspectionStatus.ARCHIVED.getStatus());
		myInspectionsScreen.isFilterStatusSelected(InspectionStatus.ARCHIVED.getStatus());
		myInspectionsScreen.clickCloseFilterDialogButton();
		myInspectionsScreen.clickSaveFilterDialogButton();

        Assert.assertTrue(myInspectionsScreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
		Assert.assertTrue(myInspectionsScreen.isFilterIsApplied());
		myInspectionsScreen.clearFilter();
		myInspectionsScreen.clickSaveFilterDialogButton();
		myInspectionsScreen.clickHomeButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEvenWOLevelTechSplitForWholesaleHail(String rowID,
														 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
		VehicleInfoScreenSteps.clickTech();
		TechniciansPopupValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician());
		TechniciansPopupSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
		TechniciansPopup techniciansPopup = new TechniciansPopup();
		techniciansPopup.selectTechniciansEvenlyView();
		String alerttext = techniciansPopup
				.saveTechnociansViewWithAlert();
		Assert.assertEquals(
				alerttext,
				AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
		Assert.assertTrue(servicesScreen.checkServiceIsSelected(UtilConstants.FIXPRICE_SERVICE));

		for (VehiclePartData vehiclePartData : workOrderData.getMoneyServiceData().getVehicleParts()) {
			servicesScreen.openServiceDetails(workOrderData.getMoneyServiceData().getServiceName(),
					vehiclePartData);

			ServiceDetailsScreenSteps.setServicePriceValue(vehiclePartData.getVehiclePartPrice());

			if (vehiclePartData.getServiceDefaultTechnicians() != null) {
				ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
				for (ServiceTechnician serviceTechnician : vehiclePartData.getServiceDefaultTechnicians())
					TechniciansPopupValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
				TechniciansPopupSteps.unselectServiceTechnician(vehiclePartData.getServiceDefaultTechnicians().get(1));
				TechniciansPopupValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceDefaultTechnicians().get(0),
						vehiclePartData.getVehiclePartPrice());

				TechniciansPopupSteps.selectServiceTechnician(vehiclePartData.getServiceNewTechnician());
				TechniciansPopupValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceDefaultTechnicians().get(0),
						vehiclePartData.getServiceDefaultTechnicians().get(0).getTechnicianPriceValue());
				TechniciansPopupValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceNewTechnician(),
						vehiclePartData.getServiceNewTechnician().getTechnicianPriceValue());
				TechniciansPopupSteps.saveTechViewDetails();
			}
			ServiceDetailsScreenSteps.saveServiceDetails();
		}

		for (VehiclePartData vehiclePartData : workOrderData.getMoneyServiceData().getVehicleParts()) {
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), vehiclePartData.getVehiclePartName(),
					vehiclePartData.getVehiclePartTotalPrice()));
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), vehiclePartData.getVehiclePartName(),
					vehiclePartData.getVehiclePartTotalPrice()));
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), vehiclePartData.getVehiclePartName(),
					vehiclePartData.getVehiclePartTotalPrice()));
			Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), vehiclePartData.getVehiclePartName(),
					vehiclePartData.getVehiclePartTotalPrice()));
		}

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
		invoiceInfoScreen.clickSaveAsFinal();
		NavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEvenServiceLevelTechSplitForWholesaleHail(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());VehicleScreen vehicleScreen = new VehicleScreen();

		NavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getMoneyServices()) {
			ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
			ServiceDetailsScreenSteps.setServiceDetailsData(serviceData);
			ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
			TechniciansPopupValidations.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
			for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
				TechniciansPopupSteps.selectServiceTechnician(serviceTechnician);

			TechniciansPopupValidations.verifyServiceTechnicianPriceValue(serviceData.getServiceDefaultTechnician(),
					serviceData.getServiceDefaultTechnician().getTechnicianPriceValue());
			for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
				TechniciansPopupValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
						serviceTechnician.getTechnicianPriceValue());
			TechniciansPopupSteps.saveTechViewDetails();
			ServiceDetailsScreenSteps.saveServiceDetails();
		}
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		InvoiceInfoScreen invoiceInfoScreen =  orderSummaryScreen.checkCreateInvoice();
		invoiceInfoScreen.clickSaveAsFinal();

		NavigationSteps.navigateBackScreen();
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCustomServiceLevelTechSplitForWholesaleHail(String rowID,
																String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
		for (VehiclePartData vehiclePartData : workOrderData.getMoneyServiceData().getVehicleParts()) {
			ServicesScreen servicesScreen = new ServicesScreen();
			servicesScreen.openServiceDetails(workOrderData.getMoneyServiceData().getServiceName(),
					vehiclePartData);
			ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
			TechniciansPopupSteps.selectTechniciansCustomView();
			TechniciansPopupValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceDefaultTechnician(),
					workOrderData.getMoneyServiceData().getServicePrice());
			TechniciansPopupSteps.selectServiceTechnician(vehiclePartData.getServiceNewTechnician());
			TechniciansPopupSteps.setTechnicianCustomPriceValue(vehiclePartData.getServiceNewTechnician());

			TechniciansPopupValidations.verifyServiceTechnicianCustomPercentageValue(vehiclePartData.getServiceNewTechnician(),
					vehiclePartData.getServiceNewTechnician().getTechnicianPercentageValue());
			TechniciansPopupSteps.saveTechViewDetails();
			Assert.assertTrue(Helpers.getAlertTextAndAccept().contains("Split amount should be equal to total amount."));
			TechniciansPopupSteps.setTechnicianCustomPriceValue(vehiclePartData.getServiceDefaultTechnician());

			TechniciansPopupValidations.verifyServiceTechnicianCustomPercentageValue(vehiclePartData.getServiceDefaultTechnician(),
					vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
			TechniciansPopupSteps.saveTechViewDetails();
			ServiceDetailsScreenSteps.saveServiceDetails();
		}
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		//orderSummaryScreen.clickSaveButton();
		invoiceInfoScreen.clickSaveAsFinal();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCustomerDiscountOnWholesaleHail(String rowID,
													String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		NavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.checkCreateInvoice();
		invoiceInfoScreen.clickSaveAsFinal();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testQuickQuoteOptionForRetailHail(String rowID,
												  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);

		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreenSteps.waitQuestionsScreenLoaded();
		NavigationSteps.navigateToVehicleInfoScreen();

		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
		}
		NavigationSteps.navigateToClaimScreen();
		ClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());
		NavigationSteps.navigateToServicesScreen();
		final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		ServicesScreenSteps.selectMatrixService(matrixServiceData);
		for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
			PriceMatrixScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
			PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
			Assert.assertTrue(priceMatrixScreen.isNotesExists());
			Assert.assertTrue(priceMatrixScreen.isTechniciansExists());
		}
		PriceMatrixScreenSteps.savePriceMatrixData();
		ServicesScreenSteps.waitServicesScreenLoad();

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.saveWizard();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCustomerSelfPayOptionForRetailHail(String rowID,
													   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreenSteps.waitQuestionsScreenLoaded();
		NavigationSteps.navigateToVehicleInfoScreen();

		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
		}
		NavigationSteps.navigateToClaimScreen();
		ClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());
		NavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getMoneyServices()) {
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.saveWizard();
		NavigationSteps.navigateBackScreen();
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
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEvenServiceLevelTechSplitForRetailHail(String rowID,
														   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreenSteps.waitQuestionsScreenLoaded();
		NavigationSteps.navigateToVehicleInfoScreen();

		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
		}
		NavigationSteps.navigateToClaimScreen();
		ClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());

		NavigationSteps.navigateToServicesScreen();
		final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		ServicesScreenSteps.selectMatrixService(matrixServiceData);
		for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
			PriceMatrixScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
			PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
			Assert.assertEquals(priceMatrixScreen.getPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
			Assert.assertTrue(priceMatrixScreen.isNotesExists());
			for (ServiceTechnician serviceTechnician : vehiclePartData.getServiceDefaultTechnicians())
				Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
			for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
				PriceMatrixScreenSteps.openVehiclePartAdditionalServiceDetails(serviceData);
				if (serviceData.getServiceDefaultTechnicians() != null) {
					ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
					for (ServiceTechnician serviceTechnician : serviceData.getServiceDefaultTechnicians()) {
						TechniciansPopupSteps.unselectServiceTechnician(serviceTechnician);
					}
					for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians()) {
						TechniciansPopupSteps.selectServiceTechnician(serviceTechnician);
						TechniciansPopupValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
								serviceTechnician.getTechnicianPriceValue());
					}
					TechniciansPopupSteps.saveTechViewDetails();
				}
				if (serviceData.getServicePrice() != null)
					ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
				ServiceDetailsScreenSteps.saveServiceDetails();
			}
		}
		PriceMatrixScreenSteps.savePriceMatrixData();

		ServicesScreenSteps.waitServicesScreenLoad();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.saveWizard();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testDeductibleFeatureForRetailHail(String rowID,
												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreenSteps.waitQuestionsScreenLoaded();
		NavigationSteps.navigateToVehicleInfoScreen();

		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
		}
		NavigationSteps.navigateToClaimScreen();
		ClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());
		ClaimScreen claimScreen = new ClaimScreen();
		Assert.assertEquals(claimScreen.getDeductibleValue(), workOrderData.getInsuranceCompanyData().getDeductible());

		NavigationSteps.navigateToServicesScreen();
		final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		ServicesScreenSteps.selectMatrixService(matrixServiceData);
		for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
			PriceMatrixScreenSteps.selectVehiclePart(vehiclePartData);
			PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
			Assert.assertEquals(priceMatrixScreen.getPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
			Assert.assertTrue(priceMatrixScreen.isNotesExists());
			for (ServiceTechnician serviceTechnician : vehiclePartData.getServiceDefaultTechnicians())
				Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
		}
		PriceMatrixScreenSteps.savePriceMatrixData();
		ServicesScreenSteps.waitServicesScreenLoad();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL);
		invoiceInfoScreen.clickSaveAsFinal();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testZipCodeValidatorForRetailHail(String rowID,
												  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreenSteps.waitQuestionsScreenLoaded();
		NavigationSteps.navigateToVehicleInfoScreen();
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
		}

		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext
				.contains("Your answer doesn't match the validator 'US Zip Codes'."));
		QuestionsScreenSteps.clearTextQuestion();
		QuestionsScreenSteps.answerQuestion(workOrderData.getQuestionScreenData().getQuestionData());
		NavigationSteps.navigateToServicesScreen();
		WizardScreensSteps.cancelWizard();
		NavigationSteps.navigateBackScreen();
	}

	@Test(testName = "Test Case 11727:Test Custom WO level tech split for Retail Hail", description = "Test Custom WO level tech split for Retail Hail")
	public void testCustomWOLevelTechSplitForRetailHail() {
		String tcname = "testCustomWOLevelTechSplitForRetailHail";
		int testcaserow = ExcelUtils.getTestCaseRow(tcname);

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		homeScreen.clickMyWorkOrdersButton();
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
		NavigationSteps.navigateBackScreen();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCustomServiceLevelTechSplitForRetailHail(String rowID,
															 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreenSteps.waitQuestionsScreenLoaded();
		NavigationSteps.navigateToVehicleInfoScreen();

		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
		}
		NavigationSteps.navigateToClaimScreen();
		ClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());

		NavigationSteps.navigateToServicesScreen();

		final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
		ServicesScreenSteps.selectMatrixService(matrixServiceData);
		for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
			PriceMatrixScreenSteps.selectVehiclePart(vehiclePartData);
			PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
			Assert.assertEquals(priceMatrixScreen.getPrice(), PricesCalculations.getPriceRepresentation(vehiclePartData.getVehiclePartSubTotalPrice()));
			Assert.assertTrue(priceMatrixScreen.isNotesExists());
			priceMatrixScreen.clickOnTechnicians();
			TechniciansPopupValidations.verifyServiceTechnicianIsSelected(vehiclePartData.getServiceDefaultTechnician());
			TechniciansPopupSteps.selectTechniciansCustomView();
			TechniciansPopupSteps.selectServiceTechnician(vehiclePartData.getServiceNewTechnician());
			TechniciansPopupValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceDefaultTechnician(),
					vehiclePartData.getVehiclePartSubTotalPrice());
			TechniciansPopupSteps.setTechnicianCustomPriceValue(vehiclePartData.getServiceDefaultTechnician());
			TechniciansPopupSteps.setTechnicianCustomPriceValue(vehiclePartData.getServiceNewTechnician());
			TechniciansPopupSteps.saveTechViewDetails();

			Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
					vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
			Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(
					vehiclePartData.getServiceNewTechnician().getTechnicianFullName()));
			for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
				PriceMatrixScreenSteps.openVehiclePartAdditionalServiceDetails(serviceData);
				BaseUtils.waitABit(1000);
				if (serviceData.getServicePrice2() != null) {
					ServiceDetailsScreenValidations.verifyServicePrice(serviceData.getServicePrice2());
					ServiceDetailsScreenSteps.saveServiceDetails();
					ServiceDetailsScreenSteps.saveServiceDetails();
					Helpers.acceptAlert();
					TechniciansPopupSteps.setTechnicianCustomPriceValue(serviceData.getServiceDefaultTechnician());
					TechniciansPopupSteps.saveTechViewDetails();
				}
				if (serviceData.getServicePrice() != null) {
					ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
					ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
					TechniciansPopupSteps.selectTechniciansCustomView();
					TechniciansPopupSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
					for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians()) {
						if (!serviceTechnician.isSelected())
							TechniciansPopupSteps.selectServiceTechnician(serviceTechnician);
						TechniciansPopupSteps.setTechnicianCustomPriceValue(serviceTechnician);
					}

					TechniciansPopupSteps.saveTechViewDetails();
					ServiceDetailsScreenSteps.saveServiceDetails();
				}
			}
		}
		PriceMatrixScreenSteps.savePriceMatrixData();
		ServicesScreenSteps.waitServicesScreenLoad();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.saveWizard();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCustomerDiscountOnRetailHail(String rowID,
												 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);

		homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreenSteps.waitQuestionsScreenLoaded();
		NavigationSteps.navigateToVehicleInfoScreen();

		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
		}
		NavigationSteps.navigateToClaimScreen();
		ClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());

		NavigationSteps.navigateToServicesScreen();
		ServicesScreen servicesScreen = new ServicesScreen();
		servicesScreen.openServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
		ServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getPercentageServiceData().getServicePrice());
		ServiceDetailsScreenSteps.saveServiceDetails();

		servicesScreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
		ServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getMoneyServiceData().getServicePrice());
		ServiceDetailsScreenSteps.saveServiceDetails();

        servicesScreen.waitServicesScreenLoaded();
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		orderSummaryScreen.saveWizard();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice(String rowID,
																								   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.BMW_ROCKVILLE_CUSTOMER);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
		String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();

		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : workOrderData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.clickSave();
		NavigationSteps.navigateBackScreen();
		
		TeamWorkOrdersScreen teamworkordersscreen = homeScreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickCreateInvoiceIconForWO(workOrderNumber);
		teamworkordersscreen.clickiCreateInvoiceButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertTrue(alerttext.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
		NavigationSteps.navigateBackScreen();
		
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testPhaseEnforcementForWizardProTracker(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
		String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();

		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : workOrderData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homeScreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(workOrderNumber);
		OrderMonitorScreen orderMonitorScreen = teamworkordersscreen.selectWOMonitor();
		for (DamageData damageData : workOrderData.getDamagesData()) {
			orderMonitorScreen.selectMainPanel(damageData.getDamageGroupName());
			orderMonitorScreen.clickChangeStatusCell();
			orderMonitorScreen.setCompletedPhaseStatus();
			if (damageData.getMoneyService().getVehicleParts() != null) {
				OrderMonitorScreenValidations.verifyServicesStatus(damageData.getMoneyService(), OrderMonitorStatuses.COMPLETED);
			} else {
				OrderMonitorScreenValidations.verifyServiceStatus(damageData.getMoneyService(), OrderMonitorStatuses.COMPLETED);
			}
		}
		teamworkordersscreen = orderMonitorScreen.clickBackButton();
		teamworkordersscreen.clickCreateInvoiceIconForWO(workOrderNumber);
		Assert.assertTrue(teamworkordersscreen.isCreateInvoiceActivated(workOrderNumber));
		teamworkordersscreen.clickiCreateInvoiceButton();
        InvoiceInfoScreen invoiceInfoScreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
		Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		invoiceInfoScreen.clickSaveAsFinal();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAddingServicesToOnOrderBeingMonitored(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
		String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();

		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : workOrderData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.clickHomeButton();
		
		TeamWorkOrdersScreen teamworkordersscreen = homeScreen.clickTeamWorkordersButton();
		teamworkordersscreen.clickOnWO(workOrderNumber);
		OrderMonitorScreen orderMonitorScreen = teamworkordersscreen.selectWOMonitor();

		final DamageData pdrDamage = workOrderData.getDamagesData().get(workOrderData.getDamagesData().size() - 1);
		orderMonitorScreen.selectMainPanel(pdrDamage.getDamageGroupName());
		orderMonitorScreen.clickChangeStatusCell();
		orderMonitorScreen.setCompletedPhaseStatus();
		OrderMonitorScreenValidations.verifyServicesStatus(pdrDamage.getMoneyService(), OrderMonitorStatuses.COMPLETED);
		final DamageData paintDamage = workOrderData.getDamagesData().get(0);
		OrderMonitorScreenValidations.verifyServicesStatus(paintDamage.getMoneyService(), OrderMonitorStatuses.ACTIVE);

		orderMonitorScreen.clickServicesButton();
		ServicesScreenSteps.selectPanelServiceData(workOrderData.getDamageData());
		ServicesScreenSteps.clickServiceTypesButton();
		WizardScreensSteps.clickSaveButton();


		OrderMonitorScreenValidations.verifyServicesStatus(paintDamage.getMoneyService(), OrderMonitorStatuses.QUEUED);
		final DamageData pdrDamageNew = workOrderData.getDamageData();
		orderMonitorScreen.selectMainPanel(pdrDamageNew.getDamageGroupName());
		orderMonitorScreen.clickChangeStatusCell();
		orderMonitorScreen.setCompletedPhaseStatus();
		OrderMonitorScreenValidations.verifyServicesStatus(pdrDamageNew.getMoneyService(), OrderMonitorStatuses.COMPLETED);

		OrderMonitorScreenValidations.verifyServicesStatus(paintDamage.getMoneyService(), OrderMonitorStatuses.ACTIVE);

		orderMonitorScreen.selectMainPanel(paintDamage.getDamageGroupName());
		orderMonitorScreen.clickChangeStatusCell();
		orderMonitorScreen.setCompletedPhaseStatus();
		OrderMonitorScreenValidations.verifyServicesStatus(paintDamage.getMoneyService(), OrderMonitorStatuses.COMPLETED);

		final DamageData wheelsDamage = workOrderData.getDamagesData().get(workOrderData.getDamagesData().size() - 2);
		OrderMonitorScreenValidations.verifyServicesStatus(wheelsDamage.getMoneyService(), OrderMonitorStatuses.ACTIVE);

		orderMonitorScreen.selectMainPanel(wheelsDamage.getDamageGroupName());
		orderMonitorScreen.clickChangeStatusCell();
		orderMonitorScreen.setCompletedPhaseStatus();
		OrderMonitorScreenValidations.verifyServicesStatus(wheelsDamage.getMoneyService(), OrderMonitorStatuses.COMPLETED);

		teamworkordersscreen = orderMonitorScreen.clickBackButton();
		teamworkordersscreen.clickCreateInvoiceIconForWO(workOrderNumber);
		teamworkordersscreen.clickiCreateInvoiceButton();
        InvoiceInfoScreen invoiceInfoScreen = teamworkordersscreen.selectWOInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		invoiceInfoScreen.clickSaveAsFinal();
		NavigationSteps.navigateBackScreen();
		
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testQuantityDoesNotMulitplyPriceInRoutePackage(String rowID,
															   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : workOrderData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}

		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.checkApproveAndSaveWorkOrder();
		//orderSummaryScreen.clickSaveButton();
		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		invoiceInfoScreen.clickSaveAsFinal();
        NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testDeleteWorkOrderFunction(String rowID,
											String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
		final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();

		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : workOrderData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
		orderSummaryScreen.saveWizard();
		myWorkOrdersScreen.deleteWorkOrderViaAction(workOrderNumber);
		Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
		myWorkOrdersScreen.clickDoneButton();
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testChangingCustomerOnInspection(String rowID,
												 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		InspectionData inspectionData = testCaseData.getInspectionData();
		WholesailCustomer wholesailCustomer = new WholesailCustomer();
		wholesailCustomer.setCompanyName("Abc Rental Center");

		HomeScreen homeScreen = new HomeScreen();
		SettingsScreen settingsscreen = homeScreen.clickSettingsButton();
		settingsscreen.setInspectionToNonSinglePageInspection();
		settingsscreen.clickHomeButton();

		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
		MyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
		VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
		final String inspNumber = VehicleInfoScreenSteps.getInspectionNumber();
		NavigationSteps.navigateToServicesScreen();
		for (DamageData damageData : inspectionData.getDamagesData()) {
			ServicesScreenSteps.selectPanelServiceData(damageData);
			ServicesScreenSteps.clickServiceTypesButton();
		}
		InspectionsSteps.saveInspection();
		myInspectionsScreen.changeCustomerForInspection(inspNumber, customer);
		NavigationSteps.navigateBackScreen();
		
		customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(customer);
		
		myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionExists(inspNumber));
		NavigationSteps.navigateBackScreen();
		
		customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        Assert.assertFalse(myInspectionsScreen.isInspectionExists(inspNumber));
		NavigationSteps.navigateBackScreen();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testRetailHailPackageQuantityMultiplier(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		WorkOrderData workOrderData = testCaseData.getWorkOrderData();

		HomeScreen homeScreen = new HomeScreen();
		CustomersScreen customersScreen = homeScreen.clickCustomersButton();
		customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
		
		homeScreen.clickMyWorkOrdersButton();
		MyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
		QuestionsScreenSteps.waitQuestionsScreenLoaded();
		NavigationSteps.navigateToVehicleInfoScreen();

		VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
		VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

		for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
			QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
		}
		NavigationSteps.navigateToClaimScreen();
		ClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());

		NavigationSteps.navigateToServicesScreen();
		for (ServiceData serviceData : workOrderData.getMoneyServices()) {
			ServicesScreenSteps.selectServiceWithServiceData(serviceData);
		}
		NavigationSteps.navigateToOrderSummaryScreen();
		OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndSaveWorkOrder();

		InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL_NO_DISCOUNT_INVOICE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
		invoiceInfoScreen.clickSaveAsFinal();
		NavigationSteps.navigateBackScreen();
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
		vehicleScreen.selectAdvisor(UtilConstants.TRAINING_ADVISOR);

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
		OrderMonitorScreen orderMonitorScreen = teamworkordersscreen.selectWOMonitor();
		orderMonitorScreen.selectMainPanel(UtilConstants.PDR_SERVICE);
		orderMonitorScreen.clickChangeStatusCell();
		orderMonitorScreen.setCompletedPhaseStatus();
		List<String> statuses = orderMonitorScreen.getPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = orderMonitorScreen.getPanelsStatuses("Tear/Burn >2\" (Fabric)");
		for (String status : statuses)
			Assert.assertEquals(status, "Active");

		orderMonitorScreen.selectMainPanel("Interior Repair");
		orderMonitorScreen.clickChangeStatusCell();
		orderMonitorScreen.setCompletedPhaseStatus();
		statuses = orderMonitorScreen.getPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		statuses = orderMonitorScreen.getPanelsStatuses("Tear/Burn >2\" (Fabric)");
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");

		teamworkordersscreen = orderMonitorScreen.clickBackButton();
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
		OrderMonitorScreen orderMonitorScreen = teamworkordersscreen.selectWOMonitor();
		orderMonitorScreen.selectMainPanel(UtilConstants.PDR_SERVICE);
		orderMonitorScreen.clickChangeStatusCell();
		orderMonitorScreen.setCompletedPhaseStatus();
		List<String> statuses = orderMonitorScreen.getPanelsStatuses(UtilConstants.PDRVEHICLE_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = orderMonitorScreen.clickBackButton();
		teamworkordersscreen.clickOnWO(inspection2);
		teamworkordersscreen.selectWOMonitor();
		orderMonitorScreen.selectMainPanel("Interior Repair");
		orderMonitorScreen.clickChangeStatusCell();
		orderMonitorScreen.setCompletedPhaseStatus();
		statuses = orderMonitorScreen.getPanelsStatuses(UtilConstants.LEATHERREPAIR_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = orderMonitorScreen.clickBackButton();
		teamworkordersscreen.clickOnWO(inspection3);
		teamworkordersscreen.selectWOMonitor();
		orderMonitorScreen.selectMainPanel(UtilConstants.PAINT_SERVICE);
		orderMonitorScreen.clickChangeStatusCell();
		orderMonitorScreen.setCompletedPhaseStatus();
		statuses = orderMonitorScreen.getPanelsStatuses(UtilConstants.BLACKOUT_SUBSERVICE);
		for (String status : statuses)
			Assert.assertEquals(status, "Completed");
		teamworkordersscreen = orderMonitorScreen.clickBackButton();

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
		vehicleScreen.selectAdvisor(UtilConstants.TRAINING_ADVISOR);
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
		vehicleScreen.selectAdvisor(UtilConstants.TRAINING_ADVISOR);
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
		vehicleScreen.selectAdvisor(UtilConstants.TRAINING_ADVISOR);

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
		vehicleScreen.selectAdvisor(UtilConstants.TRAINING_ADVISOR);

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

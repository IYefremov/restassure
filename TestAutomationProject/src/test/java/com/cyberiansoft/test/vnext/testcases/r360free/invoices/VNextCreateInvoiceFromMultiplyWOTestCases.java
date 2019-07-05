package com.cyberiansoft.test.vnext.testcases.r360free.invoices;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNextCreateInvoiceFromMultiplyWOTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	@BeforeClass(description="R360 Create Invoice FromMultiply WO Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getCreateInvoiceFromMultipleWOsTestCasesDataPath();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateInvoiceFromMultiplyWO(String rowID,
												   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(testcustomer);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,workOrderData.getVinNumber());
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());

			vehicleInfoScreen.saveWorkOrderViaMenu();
		}

		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders) {
			Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(woNumber));
		}
		invoiceinfoscren.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		ArrayList<String> woNumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(woNumbers.size(), testCaseData.getWorkOrdersData().size());
		for (String woNumber : woNumbers)
			Assert.assertTrue(workOrders.contains(woNumber.trim()));
		invoicesscreen.clickBackButton();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateInvoiceWithMultipleWOAndDifferentCustomers(String rowID,
															 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			if (!customersscreen.isCustomerExists(workOrderData.getWorlOrderRetailCustomer())) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(workOrderData.getWorlOrderRetailCustomer());
				customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,workOrderData.getVinNumber());
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.saveWorkOrderViaMenu();
		}

		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(testCaseData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer());
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(0)));
		Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(1)));
		Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(2)));
		invoiceinfoscren.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		ArrayList<String> woNumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(woNumbers.size(), 1);
		for (String woNumber : woNumbers) {
			Assert.assertTrue(woNumber.contains(testCaseData.getWorkOrdersData().get(0).getVinNumber()));
		}
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateInvoiceWithAFewWO(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final int wosToCreate = 2;
		Map<String, List<String>> workOrdersMap = new HashMap<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			if (!customersscreen.isCustomerExists(workOrderData.getWorlOrderRetailCustomer())) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(workOrderData.getWorlOrderRetailCustomer());
				customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			for (int i = 0; i < wosToCreate; i++) {
				customersscreen = workordersscreen.clickAddWorkOrderButton();
				customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
				VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
				VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,workOrderData.getVinNumber());
				VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
				vehicleVINHistoryScreen.clickBackButton();
				workOrdersMap.computeIfAbsent(workOrderData.getWorlOrderRetailCustomer().getFullName(), k -> new ArrayList<>()).add(vehicleInfoScreen.getNewInspectionNumber());
				vehicleInfoScreen.saveWorkOrderViaMenu();
			}
		}

		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			for (int j = 0; j < wosToCreate; j++) {
				workordersscreen.selectWorkOrder(workOrdersMap.get(workOrderData.getWorlOrderRetailCustomer().getFullName()).get(j));
			}
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		BaseUtils.waitABit(20000);
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(testCaseData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer());
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int j = 0; j < wosToCreate; j++) {
			Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrdersMap.get(
					testCaseData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer().getFullName()).get(j)));
		}

		for (int i = 1; i < testCaseData.getWorkOrdersData().size(); i++) {
			for (int j = 0; j < wosToCreate; j++) {
				Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrdersMap.get(
						testCaseData.getWorkOrdersData().get(i).getWorlOrderRetailCustomer().getFullName()).get(j)));
			}
		}

		invoiceinfoscren.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		ArrayList<String> wonumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(wonumbers.size(), wosToCreate);

		invoicesscreen.clickBackButton();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCancelCreatingInvoiceWithMultiplyWOOnInfoScreen(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			if (!customersscreen.isCustomerExists(workOrderData.getWorlOrderRetailCustomer())) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(workOrderData.getWorlOrderRetailCustomer());
				customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,workOrderData.getVinNumber());
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.saveWorkOrderViaMenu();
		}

		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(testCaseData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer());
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(0)));
		Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(1)));
		Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(2)));
		invoiceinfoscren.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		invoiceinfoscren.clickInvoiceInfoBackButton();
		VNextInformationDialog informationdialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(informationdialog.clickInformationDialogNoButtonAndGetMessage(),
				VNextAlertMessages.CANCEL_CREATING_INVOICE);
		invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscren.clickInvoiceInfoBackButton();
		informationdialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());

		Assert.assertEquals(informationdialog.clickInformationDialogYesButtonAndGetMessage(),
				VNextAlertMessages.CANCEL_CREATING_INVOICE);
		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders)
			workordersscreen.selectWorkOrder(woNumber);

		workordersscreen.clickCreateInvoiceIcon();
		informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.clickScreenBackButton();
		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		workordersscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalAmountDisplaysForAllSelectedWOWithTheSameCustomer(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,workOrderData.getVinNumber());
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
			servicesscreen.switchToAvalableServicesView();
			servicesscreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
			VNextSelectedServicesScreen selectservicesscreen =  servicesscreen.switchToSelectedServicesView();
			selectservicesscreen.setServiceAmountValue(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());
			workordersscreen = vehicleInfoScreen.saveWorkOrderViaMenu();
		}

		for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders) {
			Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(woNumber));
		}
		invoiceinfoscren.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		Assert.assertEquals(invoiceinfoscren.getInvoiceTotalAmount(), testCaseData.getInvoiceData().getInvoiceTotal());
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalAmountDisplaysForAllSelectedWOWithDifferentCustomers(String rowID,
																				 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		ArrayList<String> workOrders = new ArrayList<>();


		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			if (!customersscreen.isCustomerExists(workOrderData.getWorlOrderRetailCustomer())) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(workOrderData.getWorlOrderRetailCustomer());
				customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,workOrderData.getVinNumber());
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
			servicesscreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
			VNextSelectedServicesScreen selectservicesscreen =  servicesscreen.switchToSelectedServicesView();
			selectservicesscreen.setServiceAmountValue(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
			workOrders.add(vehicleInfoScreen.getNewInspectionNumber());
			workordersscreen = vehicleInfoScreen.saveWorkOrderViaMenu();
		}

		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(testCaseData.getWorkOrdersData().get(1).getWorlOrderRetailCustomer());
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 1; i < testCaseData.getWorkOrdersData().size(); i++) {
			Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(i)));
		}
		invoiceinfoscren.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		Assert.assertEquals(invoiceinfoscren.getInvoiceTotalAmount(), testCaseData.getInvoiceData().getInvoiceTotal());
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		invoicesscreen.clickBackButton();
	}
} 
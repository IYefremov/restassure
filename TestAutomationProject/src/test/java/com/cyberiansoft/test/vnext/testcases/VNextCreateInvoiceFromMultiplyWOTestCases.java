package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.Invoice;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
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

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360-create-invoice-from-multiple-wos-testcases-data.json";

	@BeforeClass(description="R360 Create Invoice FromMultiply WO Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateInvoiceFromMultiplyWO(String rowID,
												   String description, JSONObject testData) {

		Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		ArrayList<String> workOrders = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(testcustomer);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleinfoscreen.setVIN(invoiceData.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());

			vehicleinfoscreen.saveWorkOrderViaMenu();
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
		invoiceinfoscren.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		ArrayList<String> woNumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(woNumbers.size(), invoiceData.getWorkOrdersData().size());
		for (String woNumber : woNumbers)
			Assert.assertTrue(workOrders.contains(woNumber.trim()));
		invoicesscreen.clickBackButton();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateInvoiceWithMultipleWOAndDifferentCustomers(String rowID,
															 String description, JSONObject testData) {

		Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		ArrayList<String> workOrders = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			if (!customersscreen.isCustomerExists(workOrderData.getWorlOrderRetailCustomer())) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(workOrderData.getWorlOrderRetailCustomer());
				customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.saveWorkOrderViaMenu();
		}

		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(invoiceData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer());
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(0)));
		Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(1)));
		Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(2)));
		invoiceinfoscren.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		ArrayList<String> woNumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(woNumbers.size(), 1);
		for (String woNumber : woNumbers) {
			Assert.assertTrue(woNumber.contains(invoiceData.getWorkOrdersData().get(0).getVinNumber()));
		}
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateInvoiceWithAFewWO(String rowID,
														  String description, JSONObject testData) {

		Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		final int wosToCreate = 2;
		Map<String, List<String>> workOrdersMap = new HashMap<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			if (!customersscreen.isCustomerExists(workOrderData.getWorlOrderRetailCustomer())) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(workOrderData.getWorlOrderRetailCustomer());
				customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			for (int i = 0; i < wosToCreate; i++) {
				customersscreen = workordersscreen.clickAddWorkOrderButton();
				customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
				VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
				vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
				vehicleinfoscreen.clickScreenForwardButton();
				VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
				vehicleVINHistoryScreen.clickBackButton();
				workOrdersMap.computeIfAbsent(workOrderData.getWorlOrderRetailCustomer().getFullName(), k -> new ArrayList<>()).add(vehicleinfoscreen.getNewInspectionNumber());
				vehicleinfoscreen.saveWorkOrderViaMenu();
			}
		}

		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			for (int j = 0; j < wosToCreate; j++) {
				workordersscreen.selectWorkOrder(workOrdersMap.get(workOrderData.getWorlOrderRetailCustomer().getFullName()).get(j));
			}
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(invoiceData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer());
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int j = 0; j < wosToCreate; j++) {
			Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrdersMap.get(
					invoiceData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer().getFullName()).get(j)));
		}

		for (int i = 1; i < invoiceData.getWorkOrdersData().size(); i++) {
			for (int j = 0; j < wosToCreate; j++) {
				Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrdersMap.get(
						invoiceData.getWorkOrdersData().get(i).getWorlOrderRetailCustomer().getFullName()).get(j)));
			}
		}

		invoiceinfoscren.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		ArrayList<String> wonumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(wonumbers.size(), wosToCreate);

		invoicesscreen.clickBackButton();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCancelCreatingInvoiceWithMultiplyWOOnInfoScreen(String rowID,
														  String description, JSONObject testData) {

		Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		ArrayList<String> workOrders = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			if (!customersscreen.isCustomerExists(workOrderData.getWorlOrderRetailCustomer())) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(workOrderData.getWorlOrderRetailCustomer());
				customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.saveWorkOrderViaMenu();
		}

		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(invoiceData.getWorkOrdersData().get(0).getWorlOrderRetailCustomer());
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(0)));
		Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(1)));
		Assert.assertFalse(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(2)));
		invoiceinfoscren.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
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

		Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		ArrayList<String> workOrders = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			vehicleinfoscreen.swipeScreenLeft();
			vehicleinfoscreen.swipeScreenLeft();
			VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
			servicesscreen.switchToAvalableServicesView();
			servicesscreen.selectService(workOrderData.getMoneyServiceName());
			VNextSelectedServicesScreen selectservicesscreen =  servicesscreen.switchToSelectedServicesView();
			selectservicesscreen.setServiceAmountValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServicePrice());
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
			workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
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
		invoiceinfoscren.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
		Assert.assertEquals(invoiceinfoscren.getInvoiceTotalAmount(), invoiceData.getInvoiceData().getInvoiceTotal());
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalAmountDisplaysForAllSelectedWOWithDifferentCustomers(String rowID,
																				 String description, JSONObject testData) {

		Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		ArrayList<String> workOrders = new ArrayList<>();


		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			if (!customersscreen.isCustomerExists(workOrderData.getWorlOrderRetailCustomer())) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(workOrderData.getWorlOrderRetailCustomer());
				customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (WorkOrderData workOrderData : invoiceData.getWorkOrdersData()) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(workOrderData.getWorlOrderRetailCustomer());
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleVINHistoryScreen.clickBackButton();
			vehicleinfoscreen.swipeScreenLeft();
			vehicleinfoscreen.swipeScreenLeft();
			VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
			servicesscreen.selectService(workOrderData.getMoneyServiceName());
			VNextSelectedServicesScreen selectservicesscreen =  servicesscreen.switchToSelectedServicesView();
			selectservicesscreen.setServiceAmountValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServicePrice());
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
			workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		}

		workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String woNumber : workOrders) {
			workordersscreen.selectWorkOrder(woNumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(invoiceData.getWorkOrdersData().get(1).getWorlOrderRetailCustomer());
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 1; i < invoiceData.getWorkOrdersData().size(); i++) {
			Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(workOrders.get(i)));
		}
		invoiceinfoscren.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
		Assert.assertEquals(invoiceinfoscren.getInvoiceTotalAmount(), invoiceData.getInvoiceData().getInvoiceTotal());
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		invoicesscreen.clickBackButton();
	}
} 
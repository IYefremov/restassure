package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class VNextCreateInvoiceFromMultiplyWOTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final RetailCustomer testcustomer = new RetailCustomer("Retail", "Automation");
	final private String testVIN = "1FMCU0DG4BK830800";
	
	
	@Test(testName= "Test Case 65591:Verify user can create Invoice from multiply WO", 
			description = "Verify user can create Invoice from multiply WO")
	public void testVerifyUserCanCreateInvoiceFromMultiplyWO() {
		
		final String ponumber = "123po";
		ArrayList<String> workOrders = new ArrayList<>();
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (int i = 0; i<3; i++) {
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(testcustomer);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(testVIN);
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(appiumdriver);
			vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());

			vehicleinfoscreen.saveWorkOrderViaMenu();
		}
		
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		for (String wonumber : workOrders) {
			workordersscreen.selectWorkOrder(wonumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(appiumdriver);
		for (String wonumber : workOrders) {
			invoiceinfoscren.isWorkOrderSelectedForInvoice(wonumber);
		}
		invoiceinfoscren.setInvoicePONumber(ponumber);
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		ArrayList<String> wonumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(wonumbers.size(), workOrders.size());
		for (String wonumber : wonumbers)
			Assert.assertTrue(workOrders.contains(wonumber.trim()));
		invoicesscreen.clickBackButton();
	}

	
	@Test(testName= "Test Case 65640:Verify user can't create Invoice with Multiple WO and different Customers", 
			description = "Verify user can't create Invoice with Multiple WO and different Customers")
	public void testVerifyUserCantCreateInvoiceWithMultipleWOAndDifferentCustomers() {
		
		final String ponumber = "123po";
		ArrayList<String> workOrders = new ArrayList<>();
		final RetailCustomer[] testcustomers = { new RetailCustomer("Test", "Custonmer1"), new RetailCustomer("Test", "Custonmer2"), new RetailCustomer("Test", "Custonmer3") };
		final  String invoicedetails = "1FMCU0DG4BK830800";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (RetailCustomer customer : testcustomers) {
			if (!customersscreen.isCustomerExists(customer)) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(customer);
				customersscreen = new VNextCustomersScreen(appiumdriver);
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (RetailCustomer customer : testcustomers) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(customer);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(testVIN);
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(appiumdriver);
			vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.saveWorkOrderViaMenu();
		}
		
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		for (String wonumber : workOrders) {
			workordersscreen.selectWorkOrder(wonumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(testcustomers[0]);
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(appiumdriver);
		for (String wonumber : workOrders) {
			invoiceinfoscren.isWorkOrderSelectedForInvoice(wonumber);
		}
		invoiceinfoscren.setInvoicePONumber(ponumber);
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		ArrayList<String> wonumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(wonumbers.size(), 1);
		for (String wonumber : wonumbers) {
			Assert.assertTrue(wonumber.contains(invoicedetails));
		} 
		invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 65645:Verify user can create Invoice with a few WO", 
			description = "Verify user can create Invoice with a few WO")
	public void testVerifyUserCantCreateInvoiceWithAFewWO() {
		
		final String ponumber = "123po";
		ArrayList<String> workOrders = new ArrayList<>();
		final RetailCustomer[] testcustomers = { new RetailCustomer("Test", "Custonmer1"), new RetailCustomer("Test", "Custonmer2"), new RetailCustomer("Test", "Custonmer3") };
				
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (RetailCustomer customer : testcustomers) {
			if (!customersscreen.isCustomerExists(customer)) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(customer);
				customersscreen = new VNextCustomersScreen(appiumdriver);
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (RetailCustomer customer : testcustomers) {
			for (int i = 0; i < 2; i++) {
				customersscreen = workordersscreen.clickAddWorkOrderButton();
				customersscreen.selectCustomer(customer);
				VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
				vehicleinfoscreen.setVIN(testVIN);
				vehicleinfoscreen.clickScreenForwardButton();
				VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(appiumdriver);
				vehicleVINHistoryScreen.clickBackButton();
				workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
				vehicleinfoscreen.saveWorkOrderViaMenu();
			}
		}
		
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		for (String wonumber : workOrders) {
			workordersscreen.selectWorkOrder(wonumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(testcustomers[0]);
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(appiumdriver);
		for (String wonumber : workOrders) {
			invoiceinfoscren.isWorkOrderSelectedForInvoice(wonumber);
		}
		invoiceinfoscren.setInvoicePONumber(ponumber);
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		ArrayList<String> wonumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(wonumbers.size(), 2);
		for (String wonumber : wonumbers) {

			Assert.assertTrue(workOrders.contains(wonumber));
		} 
		invoicesscreen.clickBackButton();
	}
	

	@Test(testName= "Test Case 65646:Verify user can cancel creating Invoice with Multiply WO on 'Info' screen, "
			+ "Test Case 65647:Verify user can cancel creating Invoice with Multiply WO on 'Select Customer' screen", 
			description = "Verify user can cancel creating Invoice with Multiply WO on 'Info' screen, "
					+ "Verify user can cancel creating Invoice with Multiply WO on 'Select Customer' screen")
	public void testVerifyUserCanCancelCreatingInvoiceWithMultiplyWOOnInfoScreen() {
		
		final String ponumber = "123po";
		ArrayList<String> workOrders = new ArrayList<>();
		final RetailCustomer[] testcustomers = { new RetailCustomer("Test", "Custonmer1"), new RetailCustomer("Test", "Custonmer2"), new RetailCustomer("Test", "Custonmer3") };
				
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (RetailCustomer customer : testcustomers) {
			if (!customersscreen.isCustomerExists(customer)) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(customer);
				customersscreen = new VNextCustomersScreen(appiumdriver);
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (RetailCustomer customer : testcustomers) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(customer);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(testVIN);
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(appiumdriver);
			vehicleVINHistoryScreen.clickBackButton();
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.saveWorkOrderViaMenu();
		}
		
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		for (String wonumber : workOrders) {
			workordersscreen.selectWorkOrder(wonumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(testcustomers[0]);
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(appiumdriver);
		for (String wonumber : workOrders) {
			invoiceinfoscren.isWorkOrderSelectedForInvoice(wonumber);
		}
		invoiceinfoscren.setInvoicePONumber(ponumber);
		invoiceinfoscren.clickInvoiceInfoBackButton();
		VNextInformationDialog informationdialog = new VNextInformationDialog(appiumdriver);
		Assert.assertEquals(informationdialog.clickInformationDialogNoButtonAndGetMessage(), 
				VNextAlertMessages.CANCEL_CREATING_INVOICE);
		invoiceinfoscren = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscren.clickInvoiceInfoBackButton();
		informationdialog = new VNextInformationDialog(appiumdriver);
		
		Assert.assertEquals(informationdialog.clickInformationDialogYesButtonAndGetMessage(), 
				VNextAlertMessages.CANCEL_CREATING_INVOICE);
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		for (String wonumber : workOrders)
			workordersscreen.selectWorkOrder(wonumber);
		
		workordersscreen.clickCreateInvoiceIcon();
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.clickScreenBackButton();
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		workordersscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 66102:Verify total amount displays for all selected WO with same customer", 
			description = "Verify total amount displays for all selected WO with same customer")
	public void testVerifyTotalAmountDisplaysForAllSelectedWOWithTheSameCustomer() {
		
		final String ponumber = "123po";
		final String serviceName = "Bumper Repair";
		final String[] servicePrices = { "10", "14.50", "12.50" };
		final String totalAmount = "$37.00";
		ArrayList<String> workOrders = new ArrayList<>();
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (String servicePrice : servicePrices) {
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(testcustomer);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(testVIN);
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(appiumdriver);
			vehicleVINHistoryScreen.clickBackButton();
			vehicleinfoscreen.swipeScreenLeft();
			VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
			servicesscreen.switchToAvalableServicesView();
			servicesscreen.selectService(serviceName);
			VNextSelectedServicesScreen selectservicesscreen =  servicesscreen.switchToSelectedServicesView();
			selectservicesscreen.setServiceAmountValue(serviceName, servicePrice);
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
			workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		}

		for (String wonumber : workOrders) {
			workordersscreen.selectWorkOrder(wonumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(appiumdriver);
		for (String wonumber : workOrders) {
			invoiceinfoscren.isWorkOrderSelectedForInvoice(wonumber);
		}
		invoiceinfoscren.setInvoicePONumber(ponumber);
		Assert.assertEquals(invoiceinfoscren.getInvoiceTotalAmount(), totalAmount);
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 66113:Verify total amount for selected WO with different customers", 
			description = "Verify total amount for selected WO with different customers")
	public void testVerifyTotalAmountDisplaysForAllSelectedWOWithDifferentCustomers() {
		
		final String ponumber = "123po";
		ArrayList<String> workOrders = new ArrayList<>();
		final RetailCustomer[] testcustomers = { new RetailCustomer("Test", "Custonmer1"), new RetailCustomer("Test", "Custonmer2"), new RetailCustomer("Test", "Custonmer3") };
		final String serviceName = "Bumper Repair";
		final String[] servicePrices = { "10", "14.50", "12.50" };
		final String totalAmount = "$14.50";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (RetailCustomer customer : testcustomers) {
			if (!customersscreen.isCustomerExists(customer)) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer(customer);
				customersscreen = new VNextCustomersScreen(appiumdriver);
			}
		}
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (int i = 0; i < testcustomers.length; i++) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(testcustomers[i]);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(testVIN);
			vehicleinfoscreen.clickScreenForwardButton();
			VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(appiumdriver);
			vehicleVINHistoryScreen.clickBackButton();
			vehicleinfoscreen.swipeScreenLeft();
			VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
			servicesscreen.selectService(serviceName);
			VNextSelectedServicesScreen selectservicesscreen =  servicesscreen.switchToSelectedServicesView();
			selectservicesscreen.setServiceAmountValue(serviceName, servicePrices[i]);
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
			workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		}
		
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		for (String wonumber : workOrders) {
			workordersscreen.selectWorkOrder(wonumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(testcustomers[1]);
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(appiumdriver);
		for (String wonumber : workOrders) {
			invoiceinfoscren.isWorkOrderSelectedForInvoice(wonumber);
		}
		invoiceinfoscren.setInvoicePONumber(ponumber);
		Assert.assertEquals(invoiceinfoscren.getInvoiceTotalAmount(), totalAmount);
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoiceAsFinal();
		invoicesscreen.clickBackButton();
	}
}

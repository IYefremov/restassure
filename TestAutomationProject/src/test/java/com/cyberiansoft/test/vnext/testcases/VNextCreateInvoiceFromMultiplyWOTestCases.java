package com.cyberiansoft.test.vnext.testcases;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrdersScreen;

public class VNextCreateInvoiceFromMultiplyWOTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String testcustomer = "Retail Automation";
	final String testVIN = "1FMCU0DG4BK830800";
	ArrayList<String> workOrders = new ArrayList<String>();
	
	
	@Test(testName= "Test Case 65591:Verify user can create Invoice from multiply WO", 
			description = "Verify user can create Invoice from multiply WO")
	public void testVerifyUserCanCreateInvoiceFromMultiplyWO() {
		
		final String ponumber = "123po";
		ArrayList<String> workOrders = new ArrayList<String>();
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (int i = 0; i<3; i++) {
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(testcustomer);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(testVIN);
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
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoice();
		ArrayList<String> wonumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(wonumbers.size(), workOrders.size());
		for (String wonumber : wonumbers) {
			Assert.assertTrue(workOrders.contains(wonumber));
		} 
		invoicesscreen.clickBackButton();
	}

	
	@Test(testName= "Test Case 65640:Verify user can't create Invoice with Multiple WO and different Customers", 
			description = "Verify user can't create Invoice with Multiple WO and different Customers")
	public void testVerifyUserCantCreateInvoiceWithMultipleWOAndDifferentCustomers() {
		

		final String ponumber = "123po";
		ArrayList<String> workOrders = new ArrayList<String>();
		final String[] testcustomers = { "Custonmer1", "Custonmer2", "Custonmer3" };
		
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		for (String customer : testcustomers) {
			if (!customersscreen.isCustomerExists("Test " + customer)) {
				VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
				newcustomerscreen.createNewCustomer("Test", customer);
				customersscreen = new VNextCustomersScreen(appiumdriver);
			}
		}
		homescreen = customersscreen.clickBackButton();
		
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (String customer : testcustomers) {
			customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer("Test " + customer);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(testVIN);
			workOrders.add(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.saveWorkOrderViaMenu();
		}
		
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		for (String wonumber : workOrders) {
			workordersscreen.selectWorkOrder(wonumber);
		}
		workordersscreen.clickCreateInvoiceIcon();
		customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer("Test " + testcustomers[0]);
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(appiumdriver);
		for (String wonumber : workOrders) {
			invoiceinfoscren.isWorkOrderSelectedForInvoice(wonumber);
		}
		invoiceinfoscren.setInvoicePONumber(ponumber);
		final String invoiceNumber = invoiceinfoscren.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscren.saveInvoice();
		ArrayList<String> wonumbers = invoicesscreen.getInvoiceWorkOrders(invoiceNumber);
		Assert.assertEquals(wonumbers.size(), 1);
		for (String wonumber : wonumbers) {
			Assert.assertTrue(workOrders.contains(wonumber));
		} 
		invoicesscreen.clickBackButton();
	}
}

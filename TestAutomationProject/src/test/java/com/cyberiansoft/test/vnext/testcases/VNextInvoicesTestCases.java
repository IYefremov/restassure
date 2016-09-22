package com.cyberiansoft.test.vnext.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrdersScreen;

public class VNextInvoicesTestCases  extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String testcustomer = "Oksana Osmak";
	final String testVIN = "1FMCU0DG4BK830800";
	
	@Test(testName= "Test Case 45878:vNext: Create Invoice with matrix service", 
			description = "Create Invoice with matrix service")
	public void testCreateInvoiceWithMatrixService() {
		final String VIN = "19UUA66278A050105";
		final String _make = "Acura";
		final String _model = "TL";
		final String color = "Red";
		final String year = "2008";
		final String percservices = "Aluminum Upcharge"; 
		final String moneyservices = "Dent Repair"; 
		final String matrixservice = "Hail Dent Repair";
		final String matrixsubservice = "State Farm";
		final String moneyserviceprice = "58";
		final String moneyservicequant = "1";
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String additionalservice = "Aluminum Upcharge";
		final String ponumber = "123po";
		final String wopriceexp = "$267.81";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
		vehicleinfoscreen.populateVehicleInfoDataOnCreateWOWizard(VIN, color);
		
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), year);
		vehicleinfoscreen.swipeScreenLeft();
		VNextInspectionServicesScreen servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = servicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(percservices);
		selectservicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		selectservicesscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
		Assert.assertEquals(selectservicesscreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextServiceDetailsScreen servicedetailsscreen = servicesscreen.openServiceDetailsScreen(moneyservices);
		servicedetailsscreen.setServiceAmountValue(moneyserviceprice);
		servicedetailsscreen.setServiceQuantityValue(moneyservicequant);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextVehiclePartsScreen vehiclepartsscreen = servicesscreen.openMatrixServiceVehiclePartsScreen(matrixservice);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		servicesscreen = vehiclepartsscreen.clickVehiclePartsBackButton();
		final String wonumber = servicesscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		final String woprice = workordersscreen.getWorkOrderPriceValue(wonumber);
		Assert.assertEquals(woprice, wopriceexp);
		VNextInvoiceInfoScreen invoiceinfoscreen = workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		invoiceinfoscreen.addQuickNoteToInvoice("Alum Deck");
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumber), woprice);
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 46415:vNext: Create Invoice with image and text note", 
			description = "Create Invoice with matrix service")
	public void testCreateInvoiceWithTextNote() {
		final String VIN = "19UUA66278A050105";
		final String _make = "Acura";
		final String _model = "TL";
		final String color = "Red";
		final String year = "2008";
		final String wonote = "Only text Note"; 
		final String notetext = "Invoice text note"; 
		final String matrixservice = "Hail Dent Repair";
		final String matrixsubservice = "State Farm";
		final String moneyserviceprice = "58";
		final String moneyservicequant = "1";
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String additionalservice = "Aluminum Upcharge";
		final String ponumber = "123po";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
		vehicleinfoscreen.populateVehicleInfoDataOnCreateWOWizard(VIN, color);
		
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), year);
		
		VNextNotesScreen notesscreen = vehicleinfoscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(wonote);
		notesscreen.clickNotesBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		final String wonumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		Assert.assertEquals(workordersscreen.getWorkOrderPriceValue(wonumber), "$0.00");
		VNextInvoiceInfoScreen invoiceinfoscreen = workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		invoiceinfoscreen.addTextNoteToInvoice(notetext);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumber), "$0.00");
		homescreen = invoicesscreen.clickBackButton();
	}

}

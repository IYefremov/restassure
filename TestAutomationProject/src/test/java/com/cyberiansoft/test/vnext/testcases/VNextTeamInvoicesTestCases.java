package com.cyberiansoft.test.vnext.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;

public class VNextTeamInvoicesTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@Test(testName= "Test Case 64266:Verify user can create Invoice in status 'New',"
			+ "Test Case 64494:Verify user can approve Invoice after creating", 
			description = "Verify user can create Invoice in status 'New',"
					+ "Verify user can approve Invoice after creating")
	public void testVerifyUserCanCreateInvoiceInStatusNew() {
		
		final String vinnumber = "TEST";
		final String customer = "Anastasia";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(customer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(wotype);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(invoiceType);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		homescreen = invoicesscreen.clickBackButton();
		
		initiateWebDriver();
		webdriverGotoWebPage("https://reconpro.cyberianconcepts.com/Company/Invoices.aspx");
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("olexandr.kramar@cyberiansoft.com", "test12345");

		InvoicesWebPage invoicespage = PageFactory.initElements(webdriver,
				InvoicesWebPage.class);
		
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		Assert.assertEquals(invoicespage.getInvoiceStatus(invoicenumber), VNextInspectionStatuses.NEW);
		getWebDriver().quit();
	}
	
	@Test(testName= "Test Case 66780:Verify user can void Invoice", 
			description = "Verify user can void Invoice")
	public void testVerifyUserCanVoidInvoice() {
		
		final String vinnumber = "TEST";
		final String customer = "Anastasia";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(customer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(wotype);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(invoiceType);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoice();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.clickVoidInvoiceMenuItem();
		VNextInformationDialog informationdialog = new VNextInformationDialog(appiumdriver);
		Assert.assertEquals(informationdialog.clickInformationDialogDontVoidButtonAndGetMessage(), 
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoicenumber));
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertTrue(invoicesscreen.isInvoiceExists(invoicenumber));
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.clickVoidInvoiceMenuItem();
		informationdialog = new VNextInformationDialog(appiumdriver);
		Assert.assertEquals(informationdialog.clickInformationDialogVoidButtonAndGetMessage(), 
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoicenumber));
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertFalse(invoicesscreen.isInvoiceExists(invoicenumber));
		homescreen = invoicesscreen.clickBackButton();
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		
		invoicesscreen = homescreen.clickInvoicesMenuItem();
		Assert.assertFalse(invoicesscreen.isInvoiceExists(invoicenumber));
		homescreen = invoicesscreen.clickBackButton();
		
		initiateWebDriver();
		webdriverGotoWebPage("https://reconpro.cyberianconcepts.com");

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("olexandr.kramar@cyberiansoft.com", "test12345");
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
		invoicespage.clickFindButton();
		
		Assert.assertTrue(invoicespage.isInvoiceNumberExists(invoicenumber));
		Assert.assertEquals(invoicespage.getInvoiceStatus(invoicenumber), 
				WebConstants.InvoiceStatuses.INVOICESTATUS_VOID.getName());
		getWebDriver().quit();
	}

}

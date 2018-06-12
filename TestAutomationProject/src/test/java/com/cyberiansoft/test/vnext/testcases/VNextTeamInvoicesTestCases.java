package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.utils.MailChecker;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

public class VNextTeamInvoicesTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@BeforeClass(description="Team Invoices Test Cases")
	public void beforeClass() throws Exception {
	}
	
	@Test(testName= "Test Case 64266:Verify user can create Invoice in status 'New',"
			+ "Test Case 64494:Verify user can approve Invoice after creating", 
			description = "Verify user can create Invoice in status 'New',"
					+ "Verify user can approve Invoice after creating")
	public void testVerifyUserCanCreateInvoiceInStatusNew() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		homescreen = invoicesscreen.clickBackButton();
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage("https://reconpro.cyberianconcepts.com/Company/Invoices.aspx");
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("olexandr.kramar@cyberiansoft.com", "test12345");

		InvoicesWebPage invoicespage = PageFactory.initElements(webdriver,
				InvoicesWebPage.class);
		
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		Assert.assertEquals(invoicespage.getInvoiceStatus(invoicenumber), VNextInspectionStatuses.NEW);
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName= "Test Case 66780:Verify user can void Invoice, "
			+ "Test Case 66781:Verify user can Cancel Voiding Invoices, "
			+ "Test Case 66782:Update DB after Voiting Invoice, "
			+ "Test Case 66783:Verify Invoice status on BO after Voiding Invoice", 
			description = "Verify user can void Invoice, "
					+ "Verify user can Cancel Voiding Invoices, "
					+ "Update DB after Voiting Invoice, "
					+ "Verify Invoice status on BO after Voiding Invoice")
	public void testVerifyUserCanVoidInvoice() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		
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
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage("https://reconpro.cyberianconcepts.com");

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
		DriverBuilder.getInstance().getDriver().quit();
	}
	
	@Test(testName= "Test Case 67279:Verify saved PO displays on 'Change PO#' pop-up, "
			+ "Test Case 67281:Verify PO# is not changed if user click 'don't save', "
			+ "Test Case 67280:Verify PO# is saved if user save changes, "
			+ "Test Case 67282:Verify user can't pass >50 symbols on PO field", 
			description = "Verify saved PO displays on 'Change PO#' pop-up,"
					+ "Test Case 67281:Verify PO# is not changed if user click 'don't save', "
					+ "Test Case 67280:Verify PO# is saved if user save changes, "
					+ "Verify user can't pass >50 symbols on PO field")
	public void testVerifySavedPODisplaysOnCChangePONumberPopup() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "ABC123";
		final String newponumber = "123TEST";
		final String longponumber = "12345678901234567890123456789012345678901234567890123";
		

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextChangeInvoicePONumberDialog changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeinvoiceponumberdialog.setInvoicePONumber(newponumber);
		changeinvoiceponumberdialog.clickDontSaveButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), ponumber);
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		invoicesscreen = changeinvoiceponumberdialog.changeInvoicePONumber(newponumber);
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), newponumber);
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		invoicesscreen = changeinvoiceponumberdialog.changeInvoicePONumber(longponumber);
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), longponumber.substring(0, 50) );
		invoicesscreen.clickBackButton();
	}

	@Test(testName= "Test Case 67283:Verify we hide popup and keyboard if user click hardware back button", 
			description = "Verify we hide popup and keyboard if user click hardware back button")
	public void testVerifyWeHidePopupAndKeyboardIfUserClickHardwareBackButton() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "ABC123";
		

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeInvoicePONumberDialog.setInvoicePONumber(ponumber);
		//AppiumUtils.clickHardwareBackButton();
		AppiumUtils.clickHardwareBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67284:Verify on action 'Change PO#' user can't erase PO#, "
			+ "Test Case 67285:Verify 'PO#' is capitalized, "
			+ "Test Case 67286:Verify Change PO is available only if option 'PO Visible' = YES on invoice type", 
			description = "Verify on action 'Change PO#' user can't erase PO#, "
					+ "Verify 'PO#' is capitalized, "
					+ "Verify Change PO is available only if option 'PO Visible' = YES on invoice type")
	public void testVerifyOnActionChangePONumberUserCantErasePONumber() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar2";
		final String ponumber = "ABC123";
		final String newponumber = "abcd123";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(wotype);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
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
		VNextChangeInvoicePONumberDialog changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeinvoiceponumberdialog.setInvoicePONumber(newponumber);
		changeinvoiceponumberdialog.clickDontSaveButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), ponumber);
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeinvoiceponumberdialog.setInvoicePONumber("");
		changeinvoiceponumberdialog.clickSaveButton();
		Assert.assertTrue(changeinvoiceponumberdialog.isPONUmberShouldntBeEmptyErrorAppears());
		invoicesscreen = changeinvoiceponumberdialog.changeInvoicePONumber(newponumber);
		
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), newponumber.toUpperCase());
		
		invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67287:Verify Change PO is not available if option 'PO Visible' = NO on invoice type", 
			description = "Verify Change PO is not available if option 'PO Visible' = NO on invoice type")
	public void testVerifyChangePOIsNotAvailableIfOptionPOVisibleEqualsNOOnInvoiceType() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar_auto";

		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoice();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		Assert.assertFalse(invoicemenuscreen.isInvoiceChangePONumberMenuItemExists());
		invoicesscreen = invoicemenuscreen.clickCloseInvoiceMenuButton();
		
		invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 68777:Verify user can refresh deleted pictures from My Invoice list, "
			+ "Test Case 68778:Verify user can refresh picture for Invoice without pictures", 
			description = "Verify user can refresh deleted pictures from My Invoice list, "
					+ "Verify user can refresh picture for Invoice without pictures")
	public void testVerifyUserCanRefreshDeletedPicturesFromMyInvoiceList() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.refreshInvoicePictures();
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);		
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesscreen.addFakeImageNote();
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.refreshInvoicePictures();
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 68781:Verify deleted pictures doesn't displays after updating application DB (My Invoice list)", 
			description = "Verify deleted pictures doesn't displays after updating application DB (My Invoice list)")
	public void testVerifyDeletedPicturesDoesntDisplaysAfterUpdatingApplicationDB_MyInvoiceList() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);	
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesscreen.addImageToNotesFromGallery();
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		homescreen = invoicesscreen.clickBackButton();

		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		
		invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.clickBackButton();
	}

	@Test(testName= "Test Case 68785:Verify user can refresh deleted pictures after approving Invoice", 
			description = "Verify user can refresh deleted pictures after approving Invoice")
	public void testVerifyUserCanRefreshDeletedPicturesAfterApprovingInvoice() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);	
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesscreen.addFakeImageNote();
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.refreshInvoicePictures();
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 68926:Verify user can add Notes for Team Invoice", 
			description = "Verify user can add Notes for Team Invoice")
	public void testVerifyUserCanAddNotesForTeamInvoice() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final String notetext = "Test";
		final String quicknote = "1 note";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		invoicesscreen.switchToTeamInvoicesView();
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);	
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		notesscreen.setNoteText(notetext);
		notesscreen.addQuickNote(quicknote);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetext + "\n" + quicknote);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.switchToMyInvoicesView();
		invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 68664:Verify user doesn't see Invoice with Team Sharing = NO", 
			description = "Verify user doesn't see Invoice with Team Sharing = NO")
	public void testVerifyUserDoesntSeeInvoiceWithTeamSharingEqualsNO() {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar2";
		final String ponumber = "12345";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen.switchToTeamInvoicesView();
		Assert.assertFalse(invoicesscreen.isInvoiceExists(invoicenumber));
		invoicesscreen.switchToMyInvoicesView();
		Assert.assertTrue(invoicesscreen.isInvoiceExists(invoicenumber));
		invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 72052:R360 client: Verify email Invoice for multiple items with the same customer, "
			+ "Test Case 72059:R360 client: Verify possibility to email My Invoice from action menu", 
			description = "Verify email Invoice for multiple items with the same customer, "
					+ "Verify possibility to email My Invoice from action menu")
	public void testVerifyEmailInvoiceForMultipleItemsWithTheSameCustomer() throws IOException {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<String>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (!customersscreen.isCustomerExists(testcustomer)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(testcustomer);
			} else {
				customersscreen.selectCustomer(testcustomer);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.sentToEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());
		System.out.println("++++++++++++" + VNextConfigInfo.getInstance().getUserCapiMail());
		BaseUtils.waitABit(10000);
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		homescreen = invoicesscreen.clickBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);
		final int iterations = 7;
		int currentIteration = 0;
		for (String invoiceNumber : invoices) {
			boolean found = false;
			for (int i = 0; i < iterations; i++)
				if (!MailChecker.searchEmailAndVerifyAttachmentExists(VNextConfigInfo.getInstance().getUserCapiMail(),
						VNextConfigInfo.getInstance().getUserCapiMailPassword(), "Invoice " + invoiceNumber, "reconpro+main@cyberiansoft.com", invoiceNumber + ".pdf") || (currentIteration > iterations)) {
					BaseUtils.waitABit(45*1000);
			} else {
				found = true;
				break;
			}
			Assert.assertTrue(found, "Can't find mail with " + invoiceNumber + " invoice");
				
		}
	}
	
	@Test(testName= "Test Case 72053:R360 client: Verify Cancel Email invoice", 
			description = "Verify Cancel Email invoice")
	public void testVerifyCancelEmailInvoice() throws IOException {
		
		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<String>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (!customersscreen.isCustomerExists(testcustomer)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(testcustomer);
			} else {
				customersscreen.selectCustomer(testcustomer);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		AppiumUtils.clickHardwareBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 72054:R360 client: Verify email Invoice with different customer", 
			description = "Verify email Invoice with different customer")
	public void testVerifyEmailInvoiceWithDifferentCustomer() throws IOException {
		
		final String vinnumber = "TEST";
		final RetailCustomer customer1 = new RetailCustomer("RetailCustomer", "RetailLast");
		final RetailCustomer customer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int invoicesToCreate = 3;
		String[] invoices = new String[invoicesToCreate];

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
	
		for (int i = 0; i < invoices.length; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (i == 0) {
				if (!customersscreen.isCustomerExists(customer1)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer1);
				} else {
					customersscreen.selectCustomer(customer1);
				}
			} else {
				if (!customersscreen.isCustomerExists(customer2)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer2);
				} else {
					customersscreen.selectCustomer(customer2);
				}
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices[i] = invoiceinfoscreen.getInvoiceNumber();
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextCustomersScreen customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(customer2);	
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.sentToEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		homescreen = invoicesscreen.clickBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);
		final int iterations = 7;
		int currentIteration = 0;
		for (int i = 1; i < invoices.length; i ++) {
			boolean found = false;
			for (int j = 0; j < iterations; j++)
				if (!MailChecker.searchEmailAndVerifyAttachmentExists(VNextConfigInfo.getInstance().getUserCapiMail(),
						VNextConfigInfo.getInstance().getUserCapiMailPassword(), "Invoice " + invoices[i], "reconpro+main@cyberiansoft.com", invoices[i] + ".pdf") || (currentIteration > iterations)) {
					BaseUtils.waitABit(45*1000);
			} else {
				found = true;
				break;
			}
			Assert.assertTrue(found, "Can't find mail with " + invoices[i] + " invoice");
				
		}
	}
	
	@Test(testName= "Test Case 72055:R360 client: Verify Email My Invoice with populated CC address", 
			description = "Verify Email My Invoice with populated CC address")
	public void testVerifyEmailMyInvoiceWithPopulatedCCAddress() throws IOException {
		
		final String vinnumber = "TEST";
		final RetailCustomer customer = new RetailCustomer("RetailCustomer", "RetailLast");
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<String>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (!customersscreen.isCustomerExists(customer)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer);
			} else {
				customersscreen.selectCustomer(customer);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.sentToEmailAddress("fake.mailmy@mymail.com");
		emailscren.sentToCCEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());
		
		emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		homescreen = invoicesscreen.clickBackButton();

		final int iterations = 7;
		int currentIteration = 0;
		for (String invoiceNumber : invoices) {
			boolean found = false;
			for (int j = 0; j < iterations; j++)
				if (!MailChecker.searchEmailAndVerifyAttachmentExists(VNextConfigInfo.getInstance().getUserCapiMail(),
						VNextConfigInfo.getInstance().getUserCapiMailPassword(), "Invoice " + invoiceNumber, "reconpro+main@cyberiansoft.com", invoiceNumber + ".pdf") || (currentIteration > iterations)) {
					BaseUtils.waitABit(45*1000);
			} else {
				found = true;
				break;
			}
			Assert.assertTrue(found, "Can't find mail with " + invoiceNumber + " invoice");
				
		}
	}
	
	@Test(testName= "Test Case 72057:R360 client: Verify To field is required to Email My Invoice", 
			description = "Verify To field is required to Email My Invoice")
	public void testVerifyToFieldIsRequiredToEmailMyInvoice() throws IOException {
		
		final String vinnumber = "TEST";
		final RetailCustomer customer = new RetailCustomer("RetailCustomer", "RetailLast");
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<String>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (!customersscreen.isCustomerExists(customer)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer);
			} else {
				customersscreen.selectCustomer(customer);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.clickToEmailAddressRemoveButton();
		emailscren.sentToCCEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		
		emailscren.sentToBCCEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());		
		msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 72058:R360 client: Verify Email address is validated on Email My invoice list", 
			description = "Verify Email address is validated on Email My invoice list")
	public void testVerifyEmailAddressIsValidatedOnEmailMyInvoiceList() throws IOException {
		
		final String vinnumber = "TEST";
		final RetailCustomer customer = new RetailCustomer("RetailCustomer", "RetailLast");
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		final String mailsymbol = "@";
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<String>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (!customersscreen.isCustomerExists(customer)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer);
			} else {
				customersscreen.selectCustomer(customer);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextConfigInfo.getInstance().getUserCapiMail().substring(0, VNextConfigInfo.getInstance().getUserCapiMail().indexOf(mailsymbol));
		emailscren.sentToEmailAddress(wrongMail);		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 71740:R360 client: Verify email Team Invoice for multiple items with the same customer, "
			+ "Test Case 71764:R360 client: Verify possibility to email Team Invoice from action menu", 
			description = "Verify email Team Invoice for multiple items with the same customer, "
					+ "Verify possibility to email Team Invoice from action menu")
	public void testVerifyEmailTeamInvoiceForMultipleItemsWithTheSameCustomer() throws IOException {
		
		final String vinnumber = "TEST";
		final RetailCustomer customer = new RetailCustomer("RetailCustomer", "RetailLast");
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<String>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (!customersscreen.isCustomerExists(customer)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer);
			} else {
				customersscreen.selectCustomer(customer);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.sentToEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToMyInvoicesView();
		homescreen = invoicesscreen.clickBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);
		final int iterations = 7;
		int currentIteration = 0;
		for (String invoiceNumber : invoices) {
			boolean found = false;
			for (int i = 0; i < iterations; i++)
				if (!MailChecker.searchEmailAndVerifyAttachmentExists(VNextConfigInfo.getInstance().getUserCapiMail(),
						VNextConfigInfo.getInstance().getUserCapiMailPassword(), "Invoice " + invoiceNumber, "reconpro+main@cyberiansoft.com", invoiceNumber + ".pdf") || (currentIteration > iterations)) {
					BaseUtils.waitABit(45*1000);
			} else {
				found = true;
				break;
			}
			Assert.assertTrue(found, "Can't find mail with " + invoiceNumber + " invoice");
				
		}
	}
	
	@Test(testName= "Test Case 71752:R360 client: Verify Cancel Email team invoice", 
			description = "Verify Cancel Email team invoice")
	public void testVerifyCancelEmailTeamInvoice() throws IOException {
		
		final String vinnumber = "TEST";
		final RetailCustomer customer = new RetailCustomer("RetailCustomer", "RetailLast");
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<String>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (!customersscreen.isCustomerExists(customer)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer);
			} else {
				customersscreen.selectCustomer(customer);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		AppiumUtils.clickHardwareBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToMyInvoicesView();
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 71762:R360 client: Verify To field is required to Email Team Invoice", 
			description = "Verify To field is required to Email Team Invoice")
	public void testVerifyToFieldIsRequiredToEmailTeamInvoice() throws IOException {
		
		final String vinnumber = "TEST";
		final RetailCustomer customer = new RetailCustomer("RetailCustomer", "RetailLast");
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<String>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (!customersscreen.isCustomerExists(customer)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer);
			} else {
				customersscreen.selectCustomer(customer);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.clickToEmailAddressRemoveButton();
		emailscren.sentToCCEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		
		emailscren.sentToBCCEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());		
		msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToMyInvoicesView();
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 71763:R360 client: Verify Email address is validated on Email Team invoice list", 
			description = "Verify Email address is validated on Email Team invoice list")
	public void testVerifyEmailAddressIsValidatedOnEmailTeamInvoiceList() throws IOException {
		
		final String vinnumber = "TEST";
		final RetailCustomer customer = new RetailCustomer("RetailCustomer", "RetailLast");
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		final String mailsymbol = "@";
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<String>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (!customersscreen.isCustomerExists(customer)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer);
			} else {
				customersscreen.selectCustomer(customer);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextConfigInfo.getInstance().getUserCapiMail().substring(0, VNextConfigInfo.getInstance().getUserCapiMail().indexOf(mailsymbol));
		emailscren.sentToEmailAddress(wrongMail);		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToTeamInvoicesView();
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Task 72850:Automate Test Case 71753:R360 client: Verify email Team Invoice with different customer", 
			description = "Verify email Team Invoice with different customer")
	public void testVerifyEmailTeamInvoiceWithDifferentCustomer() throws IOException {
		
		final String vinnumber = "TEST";
		final RetailCustomer customer1 = new RetailCustomer("RetailCustomer", "RetailLast");
		final RetailCustomer customer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";
		
		final int invoicesToCreate = 3;
		String[] invoices = new String[invoicesToCreate];

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
	
		for (int i = 0; i < invoices.length; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (i == 0) {
				if (!customersscreen.isCustomerExists(customer1)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer1);
				} else {
					customersscreen.selectCustomer(customer1);
				}
			} else {
				if (!customersscreen.isCustomerExists(customer2)) {
					VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
					newcustomerscreen.createNewCustomer(customer2);
				} else {
					customersscreen.selectCustomer(customer2);
				}
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();
		
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(invoiceType);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(ponumber);
			invoices[i] = invoiceinfoscreen.getInvoiceNumber();
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextCustomersScreen customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(customer2);	
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.sentToEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToMyInvoicesView();
		homescreen = invoicesscreen.clickBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);
		final int iterations = 7;
		int currentIteration = 0;
		for (int i = 1; i < invoices.length; i ++) {
			boolean found = false;
			for (int j = 0; j < iterations; j++)
				if (!MailChecker.searchEmailAndVerifyAttachmentExists(VNextConfigInfo.getInstance().getUserCapiMail(),
						VNextConfigInfo.getInstance().getUserCapiMailPassword(), "Invoice " + invoices[i], "reconpro+main@cyberiansoft.com", invoices[i] + ".pdf") || (currentIteration > iterations)) {
					BaseUtils.waitABit(45*1000);
			} else {
				found = true;
				break;
			}
			Assert.assertTrue(found, "Can't find mail with " + invoices[i] + " invoice");
				
		}
	}

	@Test(testName= "Test Case 75364:Verify 'approve' icon doesn't displays after approve Invoice",
			description = "Verify 'approve' icon doesn't displays after approve Invoice")
	public void testVerifyApproveIconDoesntDisplaysAfterApproveInvoice() {

		final String vinnumber = "TEST";
		final String wotype = "O_Kramar";
		final String invoiceType = "O_Kramar";
		final String ponumber = "12345";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
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
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		Assert.assertFalse(invoicesscreen.isInvoiceHasApproveIcon(invoicenumber));
		homescreen = invoicesscreen.clickBackButton();

	}

}

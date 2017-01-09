package com.cyberiansoft.test.bo.testcases;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoiceEditTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoiceEmailActivityTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicePaymentsTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.SendInvoiceCustomEmailTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.WorkOrdersWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;

public class BackOfficeOperationsInvoiceTestCases extends BaseTestCase {
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		Thread.sleep(2000);
	}
	
	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
		
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		backofficeheader.clickLogout();
	}
	
	@Test(description = "Test Case 15161:Operation - Invoice: Search")
	public void testOperationInvoiceSearch() throws Exception {
		
		final String customer = "000 My Company";
		final String ponumber = "234";
		
		final String amountfrom = "174";
		final String amountto = "176";
		final String invoicenumber = "I-049-00106";
		final String usermail = "olexandr.kramar@cyberiansoft.com";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.clickFindButton();
		
		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		invoicespage.verifyInvoicesTableColumnsAreVisible();
		Assert.assertEquals("1", invoicespage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", invoicespage.getGoToPageFieldValue());
		
		//String initialpagenumber = invoicespage.getLastPageNumber();
		invoicespage.setPageSize("1");
		Assert.assertEquals(1, invoicespage.getInvoicesTableRowCount());
		String numberofrows = invoicespage.getLastPageNumber();
		
		String lastpagenumber = invoicespage.getLastPageNumber();
		invoicespage.clickGoToLastPage(browsertype);
		Assert.assertEquals(lastpagenumber, invoicespage.getGoToPageFieldValue());
		
		invoicespage.clickGoToFirstPage();
		Assert.assertEquals("1", invoicespage.getGoToPageFieldValue());
		
		invoicespage.clickGoToNextPage();
		Assert.assertEquals("2", invoicespage.getGoToPageFieldValue());
		
		invoicespage.clickGoToPreviousPage();
		Assert.assertEquals("1", invoicespage.getGoToPageFieldValue());

		invoicespage.setPageSize("999");
		if (Integer.valueOf(numberofrows) < 500) {
			Assert.assertEquals(Integer.valueOf(numberofrows), Integer.valueOf(invoicespage.getInvoicesTableRowCount()));
		} else 
			Assert.assertEquals(Integer.valueOf(500), Integer.valueOf(invoicespage.getInvoicesTableRowCount()));
		
		
		invoicespage.verifySearchFieldsAreVisible();
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
		invoicespage.selectSearchCustomer(customer);
		invoicespage.setSearchPONumber(ponumber);
		invoicespage.setSearchAmountFrom(amountfrom);
		invoicespage.setSearchAmountTo(amountto);
		invoicespage.clickFindButton();
		Thread.sleep(1000);
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(invoicespage.getInvoicesTableRowCount()));
		Assert.assertTrue(invoicespage.isInvoiceNumberExists(invoicenumber));
		Assert.assertTrue(invoicespage.sendInvoiceEmail(invoicenumber, usermail));
		Thread.sleep(1000);
	}

	@Test(testName = "Test Case 24750:Operations: Invoice editor - verify Add PO is present and payment is added", 
		description = "Operations: Invoice editor - verify Add PO is present and payment is added")
	public void testOperationInvoiceEditorVerifyAddPOIsPresentAndPaymentIsAdded() throws Exception {
	
		final String po = "#123";
		final String notes = "Test note for payments";
		final String invoicenumber = "I-046-00068";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		
		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		if (!invoicespage.getInvoiceStatus(invoicenumber).equals(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName())) {
			invoicespage.changeInvoiceStatus(invoicenumber, WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
		}

		String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(5000);
		
		InvoiceEditTabWebPage invoiceedittab = invoicespage.clickEditInvoice(invoicenumber);		
		invoiceedittab.clcikAddPO();		
		invoiceedittab.clickAddPOPayButton();
		Assert.assertTrue(invoiceedittab.getPONumberField().getAttribute("style").contains("solid red"));
		invoiceedittab.setPONumber(po);
		invoiceedittab.setPONotes(notes);
		String alerttext = invoiceedittab.clickAddPOPayButtonAndAcceptPayment();
		Assert.assertEquals(alerttext, "PO/RO payment has processed.");
		invoiceedittab.closeNewTab(mainWindowHandle);
		mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(3000);
		InvoicePaymentsTabWebPage invoicepaymentstab = invoicespage.clickInvoicePayments(invoicenumber);
		for (String activeHandle : webdriver.getWindowHandles()) {
	        if (!activeHandle.equals(mainWindowHandle)) {
	        	webdriver.switchTo().window(activeHandle);
	        }
	    }
		Thread.sleep(5000);
		Assert.assertTrue(invoicepaymentstab.getInvoicesPaymentsLastTableRowPaidColumnValue().contains(BackOfficeUtils.getShortCurrentDateFormatted()));
		Assert.assertTrue(invoicepaymentstab.getInvoicesPaymentsLastTableRowDescriptionColumnValue().contains(po));		
		invoicepaymentstab.clickNotesForInvoicesPaymentsLastTableRow();
		Assert.assertEquals(invoicepaymentstab.getInvoicePaymentNoteValue(), notes);
		invoicepaymentstab.clickClosePaymentsPopup();
		invoicespage.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 24751:Operations: Invoice editor - verify that 'Approve invoice after payment' checkbox is disabled after checked it and Invoice is Approved", 
			description = "Operations: Invoice editor - verify that 'Approve invoice after payment' checkbox is disabled after checked it and Invoice is Approved")
	public void testOperationInvoiceEditorVerifyThatApproveInvoiceAfterPaymentCheckboxIsDisabledAfterCheckedItAndInvoiceIsApproved() throws Exception {
		
		final String po = "#123";
		final String notes = "Test note for payments";
		final String invoicenumber = "I-046-00068";
		
		final String ponum = "#222";
		final String ponotes = "test222";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		
		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		if (!invoicespage.getInvoiceStatus(invoicenumber).equals(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName())) {
			invoicespage.changeInvoiceStatus(invoicenumber, WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
		}
		final String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(3000);
		InvoiceEditTabWebPage invoiceedittab = invoicespage.clickEditInvoice(invoicenumber);		
		invoiceedittab.clcikAddPO();		
		invoiceedittab.clickAddPOPayButton();
		Assert.assertTrue(invoiceedittab.getPONumberField().getAttribute("style").contains("solid red"));
		invoiceedittab.setPONumber(po);
		invoiceedittab.setPONotes(notes);
		String alerttext = invoiceedittab.clickAddPOPayButtonAndAcceptPayment();
		Assert.assertEquals(alerttext, "PO/RO payment has processed.");
	
		invoiceedittab.clcikAddPO();
		invoiceedittab.setPONumber(ponum);
		invoiceedittab.setPONotes(ponotes);
		invoiceedittab.checkApproveInvoiceAfterPayment();
		alerttext = invoiceedittab.clickAddPOPayButtonAndAcceptPayment();
		Assert.assertEquals(alerttext, "PO/RO payment has processed.");		
		invoiceedittab.clcikAddPO();		
		Assert.assertFalse(invoiceedittab.getCheckApproveInvoiceAfterPayment().isEnabled());
		invoiceedittab.closeNewTab(mainWindowHandle);
		
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		Assert.assertTrue(invoicespage.isInvoiceNumberExists(invoicenumber));
		Assert.assertEquals(invoicespage.getInvoiceStatus(invoicenumber), WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED.getName());
		invoicespage.changeInvoiceStatus(invoicenumber, WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
	}
	
	//@Test(testName = "Test Case 28574:Operation - Invoice: Edit - Technician", 
		//	description = "Operation - Invoice: Edit - Technician")
	public void testOperationInvoiceEditTechnician() throws Exception {

		final String invoicenumber = "I-000-00243";
		
		final String po = "#123";
		final String notes = "Test note for payments";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		
		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		final String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(5000);
		InvoiceEditTabWebPage invoiceedittab = invoicespage.clickEditInvoice(invoicenumber);
		invoiceedittab.clickTechniciansLink();
		invoiceedittab.unselectAllTechnicians();
		
	}
	
	@Test(testName = "Test Case 28578:Operation - Invoice: Edit - Click here to edit notes", 
			description = "Operation - Invoice: Edit - Click here to edit notes")
	public void testOperationInvoiceEditClickHereToEditNotes() throws Exception {

		final String invoicenumber = "I-046-00065";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		
		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		
		if (!invoicespage.getInvoiceStatus(invoicenumber).equals(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName())) {
			invoicespage.changeInvoiceStatus(invoicenumber, WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
		}
		//final String invoicenumber = invoicespage.getFirstInvoiceNumberInTable();
		final String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(2000);
		InvoiceEditTabWebPage invoiceedittab = invoicespage.clickEditInvoice(invoicenumber);
		final String oldivoicenotesvalue = invoiceedittab.getInvoiceNotesValue();
		invoiceedittab.setEditableNotes(BackOfficeUtils.getCurrentDateFormatted());
		invoiceedittab.closeNewTab(mainWindowHandle);
		
		invoiceedittab = invoicespage.clickEditInvoice(invoicenumber);
		Assert.assertEquals(oldivoicenotesvalue, invoiceedittab.getInvoiceNotesValue());
		invoiceedittab.setEditableNotes(BackOfficeUtils.getCurrentDateFormatted());
		invoiceedittab.clickSaveInvoiceButton();
		invoiceedittab.closeNewTab(mainWindowHandle);
		
		invoiceedittab = invoicespage.clickEditInvoice(invoicenumber);
		Assert.assertEquals(BackOfficeUtils.getCurrentDateFormatted(), invoiceedittab.getInvoiceNotesValue());
		invoiceedittab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 28594:Operation - Invoice : Sent mail in Mail Activity", 
			description = "Operation - Invoice : Sent mail in Mail Activity")
	public void testOperationInvoiceSentMailInMailActivity() throws Exception {

		final String usermail = "olexandr.kramar@cyberiansoft.com";
				
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
		invoicespage.clickFindButton();
		final String invoicenumber = invoicespage.getFirstInvoiceNumberInTable();
		
		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		final String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(2000);
		Assert.assertTrue(invoicespage.sendInvoiceEmail(invoicenumber, usermail));
		Thread.sleep(30*1000);
		InvoiceEmailActivityTabWebPage invoiceemailactivitytab = invoicespage.clickEmailActivity(invoicenumber);
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowRecipientsValue().contains(usermail));
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowSentTimeValue().contains(BackOfficeUtils.getShortCurrentDateFormatted()));
		Assert.assertEquals("true", invoiceemailactivitytab.getFirstRowSendCheckboxValue());
		invoiceemailactivitytab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 28596:Operation - Invoice : Sent Custom mail in Mail Activity", 
			description = "Operation - Invoice : Sent Custom mail in Mail Activity")
	public void testOperationInvoiceSentCustomMailInMailActivity() throws Exception {
		
		final String usermail = "olexandr.kramar@cyberiansoft.com";
		final String message = "Mail Message";
		final String invoicenumber = "I-000-00242";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		//final String invoicenumber = invoicespage.getFirstInvoiceNumberInTable();
		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		final String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(2000);

		SendInvoiceCustomEmailTabWebPage sendinvoicecustomemailtab = invoicespage.clickSendCustomEmail(invoicenumber);
		sendinvoicecustomemailtab.setEmailSubjectValue(invoicenumber);
		sendinvoicecustomemailtab.setEmailToValue(usermail);
		sendinvoicecustomemailtab.setEmailMessageValue(message);
		sendinvoicecustomemailtab.unselectIncludeInvoicePDFCheckbox();
		sendinvoicecustomemailtab.clickSendEmailButton();
		
		sendinvoicecustomemailtab.closeNewTab(mainWindowHandle);
		
		Thread.sleep(30*1000);
		InvoiceEmailActivityTabWebPage invoiceemailactivitytab = invoicespage.clickEmailActivity(invoicenumber);
		Assert.assertEquals(usermail, invoiceemailactivitytab.getFirstRowRecipientsValue());
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowSentTimeValue().contains(BackOfficeUtils.getShortCurrentDateFormatted()));
		Assert.assertEquals("true", invoiceemailactivitytab.getFirstRowSendCheckboxValue());
		invoiceemailactivitytab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 42737:Operation - Invoice: Edit - Customer", 
			description = "Operation - Invoice: Edit - Customer")
	public void testOperationInvoiceEditCustomer() throws Exception {
		
		final String ponum = "123";
		final String invoicecustomername = "000 My Company";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		
		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();
		
		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		if (invoicenumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, ponum);
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}
		
		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		
		final String mainWindowHandle = webdriver.getWindowHandle();
		InvoiceEditTabWebPage invoiceeditpage = invoicespage.clickEditInvoice(invoicenumber);
		invoiceeditpage.changeInvoiceWholesaleCustomer(invoicecustomername);
		invoiceeditpage.waitABit(1500);
		Assert.assertEquals(invoicecustomername, invoiceeditpage.getInvoiceCustomer());
		invoiceeditpage.closeNewTab(mainWindowHandle);
	}
}

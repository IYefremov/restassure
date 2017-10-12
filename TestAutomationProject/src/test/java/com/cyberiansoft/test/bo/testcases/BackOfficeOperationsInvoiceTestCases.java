package com.cyberiansoft.test.bo.testcases;

import java.awt.AWTException;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ExportInvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoiceEditTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoiceEmailActivityTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicePaymentsTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.SendInvoiceCustomEmailTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.WorkOrdersWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.Retry;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.utils.WebConstants.InvoiceStatuses;

public class BackOfficeOperationsInvoiceTestCases extends BaseTestCase {

	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl, String userName, String userPassword)
			throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		Thread.sleep(2000);
	}

	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		try {
			backofficeheader.clickLogout();
		} catch (Exception e) {
		}
	}

	@Test(description = "Test Case 15161:Operation - Invoice: Search")
	public void testOperationInvoiceSearch() throws Exception {

		final String customer = "000 My Company";
		final String ponumber = "234";

		final String amountfrom = "0";
		final String amountto = "8";
		final String invoicenumber = "I-062-00007";
		final String usermail = "olexandr.kramar@cyberiansoft.com";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		invoicespage.verifyInvoicesTableColumnsAreVisible();
		Assert.assertEquals("1", invoicespage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", invoicespage.getGoToPageFieldValue());

		// String initialpagenumber = invoicespage.getLastPageNumber();
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
			Assert.assertEquals(Integer.valueOf(numberofrows),
					Integer.valueOf(invoicespage.getInvoicesTableRowCount()));
		} else
			Assert.assertEquals(Integer.valueOf(500), Integer.valueOf(invoicespage.getInvoicesTableRowCount()));

		invoicespage.verifySearchFieldsAreVisible();
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
		invoicespage.selectSearchCustomer(customer);
		invoicespage.setSearchAmountFrom(amountfrom);
		invoicespage.setSearchAmountTo(amountto);
		invoicespage.clickFindButton();
		Thread.sleep(1000);
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(invoicespage.getInvoicesTableRowCount()));
	}

	// @Test(testName = "Test Case 24750:Operations: Invoice editor - verify Add
	// PO is present and payment is added", description = "Operations: Invoice
	// editor - verify Add PO is present and payment is added")
	public void testOperationInvoiceEditorVerifyAddPOIsPresentAndPaymentIsAdded() throws Exception {

		final String po = "#123";
		final String notes = "Test note for payments";
		final String invoicenumber = "I-046-00068";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		if (!invoicespage.getInvoiceStatus(invoicenumber)
				.equals(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName())) {
			invoicespage.changeInvoiceStatus(invoicenumber, WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
		}

		String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(5000);
		// TODO when webdriver renewed
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
		invoiceedittab.waitABit(1000);
		InvoicePaymentsTabWebPage invoicepaymentstab = invoicespage.clickInvoicePayments(invoicenumber);
		Assert.assertTrue(invoicepaymentstab.getInvoicesPaymentsLastTableRowPaidColumnValue()
				.contains(BackOfficeUtils.getShortCurrentTimeWithTimeZone()));
		Assert.assertTrue(invoicepaymentstab.getInvoicesPaymentsLastTableRowDescriptionColumnValue().contains(po));
		invoicepaymentstab.clickNotesForInvoicesPaymentsLastTableRow();
		Assert.assertEquals(invoicepaymentstab.getInvoicePaymentNoteValue(), notes);
		invoicepaymentstab.clickClosePaymentsPopup();
		invoicespage.closeNewTab(mainWindowHandle);
	}

	// @Test(testName = "Test Case 24751:Operations: Invoice editor - verify
	// that 'Approve invoice after payment' checkbox is disabled after checked
	// it and Invoice is Approved", description = "Operations: Invoice editor -
	// verify that 'Approve invoice after payment' checkbox is disabled after
	// checked it and Invoice is Approved")
	public void testOperationInvoiceEditorVerifyThatApproveInvoiceAfterPaymentCheckboxIsDisabledAfterCheckedItAndInvoiceIsApproved()
			throws Exception {

		final String po = "#123";
		final String notes = "Test note for payments";
		final String invoicenumber = "I-046-00068";

		final String ponum = "#222";
		final String ponotes = "test222";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		if (!invoicespage.getInvoiceStatus(invoicenumber)
				.equals(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName())) {
			invoicespage.changeInvoiceStatus(invoicenumber, WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
		}
		final String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(3000);
		// TODO when webdriver renewed
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
		Assert.assertEquals(invoicespage.getInvoiceStatus(invoicenumber),
				WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED.getName());
		invoicespage.changeInvoiceStatus(invoicenumber, WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
	}

	// @Test(testName = "Test Case 28574:Operation - Invoice: Edit -
	// Technician",
	// description = "Operation - Invoice: Edit - Technician")
	public void testOperationInvoiceEditTechnician() throws Exception {

		final String invoicenumber = "I-000-00243";

		final String po = "#123";
		final String notes = "Test note for payments";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
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

	// @Test(testName = "Test Case 28578:Operation - Invoice: Edit - Click here
	// to edit notes", description = "Operation - Invoice: Edit - Click here to
	// edit notes")
	public void testOperationInvoiceEditClickHereToEditNotes() throws Exception {

		final String invoicenumber = "I-000-00243";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());

		if (!invoicespage.getInvoiceStatus(invoicenumber)
				.equals(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName())) {
			invoicespage.changeInvoiceStatus(invoicenumber, WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
		}
		// final String invoicenumber =
		// invoicespage.getFirstInvoiceNumberInTable();
		final String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(2000);
		invoicespage.selectActionForFirstInvoice("Edit", false);
		InvoiceEditTabWebPage invoiceedittab = invoicespage.clickEditInvoice(invoicenumber);
		final String oldivoicenotesvalue = invoiceedittab.getInvoiceNotesValue();
		// TODO when webdriver version will be updated
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

	@Test(testName = "Test Case 28594:Operation - Invoice : Sent mail in Mail Activity", description = "Operation - Invoice : Sent mail in Mail Activity", retryAnalyzer = Retry.class)
	public void testOperationInvoiceSentMailInMailActivity() throws Exception {

		final String usermail = "olexandr.kramar@cyberiansoft.com";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
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
		Thread.sleep(30 * 1000);
		InvoiceEmailActivityTabWebPage invoiceemailactivitytab = invoicespage.clickEmailActivity(invoicenumber);
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowRecipientsValue().contains(usermail));
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowSentTimeValue()
				.contains(BackOfficeUtils.getShortCurrentTimeWithTimeZone()));
		Assert.assertEquals("true", invoiceemailactivitytab.getFirstRowSendCheckboxValue());
		invoiceemailactivitytab.closeNewTab(mainWindowHandle);
	}

	@Test(testName = "Test Case 28596:Operation - Invoice : Sent Custom mail in Mail Activity", description = "Operation - Invoice : Sent Custom mail in Mail Activity", retryAnalyzer = Retry.class)
	public void testOperationInvoiceSentCustomMailInMailActivity() throws Exception {

		final String usermail = "olexandr.kramar@cyberiansoft.com";
		final String message = "Mail Message";
		final String invoicenumber = "I-000-00242";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
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

		Thread.sleep(30 * 1000);
		invoicespage.clickFindButton();
		InvoiceEmailActivityTabWebPage invoiceemailactivitytab = invoicespage.clickEmailActivity(invoicenumber);
		Assert.assertEquals(usermail, invoiceemailactivitytab.getFirstRowRecipientsValue());
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowSentTimeValue()
				.contains(BackOfficeUtils.getShortCurrentTimeWithTimeZone()));
		Assert.assertEquals("true", invoiceemailactivitytab.getFirstRowSendCheckboxValue());
		invoiceemailactivitytab.closeNewTab(mainWindowHandle);
	}

	@Test(testName = "Test Case 43708:Operation - Invoice: Status - Approved")
	public void checkOperationInvoiceStatusApproved() throws InterruptedException {
		final String ponum = "123";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("New");
		workorderspage.clickFindButton();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.checkFirstWorkOrderCheckBox();
		workorderspage.addInvoiceDescription("test");
		workorderspage.clickCreateInvoiceButton();

		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);

		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		if (invoicenumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, ponum);
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		String status = "";
		for (InvoiceStatuses stat : WebConstants.InvoiceStatuses.values()) {
			try {
				invoicespage.selectSearchStatus(stat);
				invoicespage.clickFindButton();
				status = invoicespage.getInvoiceStatus(invoicenumber);
				if (!status.isEmpty())
					break;
			} catch (Exception e) {
			}
		}
		if (status.equals("New")) {
			invoicespage.changeInvoiceStatus(invoicenumber, "Approved");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Approved"));
		} else {
			invoicespage.changeInvoiceStatus(invoicenumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("New"));
			invoicespage.changeInvoiceStatus(invoicenumber, "Approved");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Approved"));
		}
	}

	@Test(testName = "Test Case 43712:Operation - Invoice: Status - Exported")
	public void checkOperationInvoiceStatusExported() throws InterruptedException {
		final String ponum = "123";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.checkFirstWorkOrderCheckBox();
		workorderspage.addInvoiceDescription("test");
		workorderspage.clickCreateInvoiceButton();

		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);

		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		if (invoicenumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, ponum);
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		String status = "";
		for (InvoiceStatuses stat : WebConstants.InvoiceStatuses.values()) {
			try {
				invoicespage.selectSearchStatus(stat);
				invoicespage.clickFindButton();
				status = invoicespage.getInvoiceStatus(invoicenumber);
				if (!status.isEmpty())
					break;
			} catch (Exception e) {
			}
		}
		if (status.equals("New")) {
			invoicespage.changeInvoiceStatus(invoicenumber, "Exported");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_EXPORTED);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Exported"));
		} else {
			invoicespage.changeInvoiceStatus(invoicenumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("New"));
			invoicespage.changeInvoiceStatus(invoicenumber, "Exported");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_EXPORTED);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Exported"));
		}
	}

	@Test(testName = "Test Case 43713:Operation - Invoice: Status - Void")
	public void checkOperationInvoiceStatusVoid() throws InterruptedException {
		final String ponum = "123";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.checkFirstWorkOrderCheckBox();
		workorderspage.addInvoiceDescription("test");
		workorderspage.clickCreateInvoiceButton();

		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);

		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		if (invoicenumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, ponum);
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		String status = "";
		for (InvoiceStatuses stat : WebConstants.InvoiceStatuses.values()) {
			try {
				invoicespage.selectSearchStatus(stat);
				invoicespage.clickFindButton();
				status = invoicespage.getInvoiceStatus(invoicenumber);
				if (!status.isEmpty())
					break;
			} catch (Exception e) {
			}
		}
		if (status.equals("New")) {
			invoicespage.changeInvoiceStatus(invoicenumber, "Void");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Void"));
		} else {
			invoicespage.changeInvoiceStatus(invoicenumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("New"));
			invoicespage.changeInvoiceStatus(invoicenumber, "Void");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Void"));
		}
	}

	@Test(testName = "Test Case 43710:Operation - Invoice: Status - Export Failed")
	public void checkOperationInvoiceStatusExportFailed() throws InterruptedException {
		final String ponum = "123";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.checkFirstWorkOrderCheckBox();
		workorderspage.addInvoiceDescription("test");
		workorderspage.clickCreateInvoiceButton();

		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);

		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		if (invoicenumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, ponum);
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		String status = "";
		for (InvoiceStatuses stat : WebConstants.InvoiceStatuses.values()) {
			try {
				invoicespage.selectSearchStatus(stat);
				invoicespage.clickFindButton();
				status = invoicespage.getInvoiceStatus(invoicenumber);
				if (!status.isEmpty())
					break;
			} catch (Exception e) {
			}
		}
		if (status.equals("New")) {
			invoicespage.changeInvoiceStatus(invoicenumber, "Export Failed");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_EXPORT_FAILED);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Export Failed"));
		} else {
			invoicespage.changeInvoiceStatus(invoicenumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("New"));
			invoicespage.changeInvoiceStatus(invoicenumber, "Export Failed");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_EXPORT_FAILED);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Export Failed"));
		}
	}

	@Test(testName = "Test Case 43709:Operation - Invoice: Status - Draft")
	public void checkOperationInvoiceStatusDraft() throws InterruptedException {
		final String ponum = "123";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("New");
		workorderspage.clickFindButton();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.checkFirstWorkOrderCheckBox();
		workorderspage.addInvoiceDescription("test");
		workorderspage.clickCreateInvoiceButton();

		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);

		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		if (invoicenumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, ponum);
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		String status = "";
		for (InvoiceStatuses stat : WebConstants.InvoiceStatuses.values()) {
			try {
				invoicespage.selectSearchStatus(stat);
				invoicespage.clickFindButton();
				status = invoicespage.getInvoiceStatus(invoicenumber);
				if (!status.isEmpty())
					break;
			} catch (Exception e) {
			}
		}
		if (status.equals("New")) {
			invoicespage.changeInvoiceStatus(invoicenumber, "Draft");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Draft"));
		} else {
			invoicespage.changeInvoiceStatus(invoicenumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("New"));
			invoicespage.changeInvoiceStatus(invoicenumber, "Draft");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
			invoicespage.clickFindButton();
			Assert.assertTrue(invoicespage.getInvoiceStatus(invoicenumber).equals("Draft"));
		}
	}

	@Test(testName = "Test Case 43689:Operation - Invoice: Edit - Mark As Paid")
	public void checkOperationInvoiceEditMarkAsPaid() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);

		invoicespage.clickFindButton();
		invoicespage.selectActionForFirstInvoice("Mark as Paid", false);
		invoicespage.clickFindButton();
		Assert.assertTrue(invoicespage.firstInvoiceMarkedAsPaid());
	}

	// @Test(testName = "Test Case 43217:Operation - Invoice: Edit - Vehicle
	// Info", retryAnalyzer = Retry.class)
	public void checkOperationInvoiceEditVehicleInfo() throws InterruptedException, AWTException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		// WorkOrdersWebPage workorderspage =
		// operationspage.clickWorkOrdersLink();
		// workorderspage.unselectInvoiceFromDeviceCheckbox();
		// workorderspage.selectSearchStatus("All");
		// workorderspage.clickFindButton();
		//
		// String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		// String invoicenumber =
		// workorderspage.getWorkOrderInvoiceNumber(wonum);
		// if (invoicenumber.equals("")) {
		// workorderspage.createInvoiceFromWorkOrder(wonum, ponum);
		// workorderspage.setSearchOrderNumber(wonum);
		// workorderspage.clickFindButton();
		// invoicenumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		// }

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();

		@SuppressWarnings("unused")
		InvoiceEditTabWebPage invoiceeditpage = invoicespage.clickEditFirstInvoice();
	}

	@Test(testName = "Test Case 43692:Operation - Invoice: Edit - Change Invoice")
	public void checkOperationInvoiceEditChangeInvoice() throws InterruptedException, AWTException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();

		invoicespage.selectActionForFirstInvoice("Change Invoice#", false);
		Assert.assertTrue(invoicespage.checkInvoiceFrameOpened());
		Assert.assertTrue(invoicespage.isInvoiceAbleToChange());
		String newInvoiceNumber = invoicespage.getFirstInvoiceNumberInTable();
	}

	@Test(testName = "Test Case 43693:Operation - Invoice: Edit - Download JSON")
	public void checkOperationInvoiceDownloadJSON() throws InterruptedException, AWTException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();

		invoicespage.selectActionForFirstInvoice("Download JSON", false);
	}

	@Test(testName = "Test Case 43724:Operation - Invoice: Edit - Tech. Info")
	public void checkOperationInvoiceEditTechInfo() throws InterruptedException, AWTException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String newTab = invoicespage.selectActionForFirstInvoice("Tech. Info", false);
		Assert.assertTrue(invoicespage.isWindowOpened());
		invoicespage.closeTab(newTab);
	}

	@Test(testName = "Test Case 43699:Operation - Invoice: Edit - Recalc Tech Split")
	public void checkOperationInvoiceEditRecalcTechSplit() throws InterruptedException, AWTException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		invoicespage.selectActionForFirstInvoice("Recalc Tech Split", false);
		Assert.assertTrue(invoicespage.recalcTechSplitProceed());
	}

	@Test(testName = "Automate Test Case 28594:Operation - Invoice : Sent mail in Mail Activity")
	public void checkOperationInvoiceSentMailInMailActivity() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String emailWindow = invoicespage.selectActionForFirstInvoice("Email Activity", false);
		int emailActivities = invoicespage.countEmailActivities(emailWindow);
		invoicespage.selectActionForFirstInvoice("Send Email", true);
		Assert.assertTrue(invoicespage.isSendEmailBoxOpened());
		invoicespage.setEmailAndSend("test123@domain.com");
		emailWindow = invoicespage.selectActionForFirstInvoice("Email Activity", false);
		Assert.assertTrue(emailActivities < invoicespage.countEmailActivities(emailWindow));
	}

	@Test(testName = "Automate Test Case 28596:Operation - Invoice : Sent Custom mail in Mail Activity")
	public void checkOperationInvoiceSentCustomMailInMailActivity() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String emailActivityWindow = invoicespage.selectActionForFirstInvoice("Email Activity", false);
		int emailActivities = invoicespage.countEmailActivities(emailActivityWindow);
		String emailWindow = invoicespage.selectActionForFirstInvoice("Send Custom Email", true);
		invoicespage.setCustomEmailAndSend("test123@domain.com", emailWindow);
		emailActivityWindow = invoicespage.selectActionForFirstInvoice("Email Activity", false);
		int emailActivitiesAfter = invoicespage.countEmailActivities(emailActivityWindow);
		Assert.assertTrue(emailActivities < emailActivitiesAfter);
	}

	@Test(testName = "Test Case 43724:Operation - Invoice: Edit - Internal Tech. Info")
	public void checkOperationInvoiceEditInternalTechInfo() throws InterruptedException, AWTException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		String newTab = invoicespage.selectActionForFirstInvoice("Internal Tech. Info", false);
		Assert.assertTrue(invoicespage.isWindowOpened());
		invoicespage.closeTab(newTab);
	}

	// @Test(testName = "Test Case 28933:Operations - Invoice: Archive",
	// retryAnalyzer = Retry.class)
	public void checkOperationInvoiceArchive() throws InterruptedException, AWTException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.clickFindButton();

	}

	// @Test(testName = "Test Case 29198:Operation - Invoice: Year/Make/Model
	// Search")
	public void checkOperationInvoiceYearMakeModelSearch() throws InterruptedException, AWTException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		// TODO bug
		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus("All");
		invoicespage.setSearchByYear("2007");
		invoicespage.clickFindButton();
		Assert.assertTrue(invoicespage.checkSearchResult());
		invoicespage.setSearchByYear("All");
		invoicespage.setSearchByMake("Jeep");
		invoicespage.clickFindButton();
		Assert.assertTrue(invoicespage.checkSearchResult());
		invoicespage.setSearchByMake("");
		invoicespage.setSearchByModel("Patriot");
		invoicespage.clickFindButton();
		Assert.assertTrue(invoicespage.checkSearchResult());
		invoicespage.setSearchByModel("");
		invoicespage.setSearchByYear("2007");
		invoicespage.setSearchByMake("Jeep");
		invoicespage.setSearchByModel("Patriot");
		Assert.assertTrue(invoicespage.checkSearchResult());
	}

	@Test(testName = "Test Case 43688:Operation - Invoice: Edit - Print Preview (Server)")
	public void checkOperationInvoiceEditPrintPreview() throws InterruptedException, AWTException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();

		String newTab = invoicespage.selectActionForFirstInvoice("Print preview (server)", false);
		Assert.assertTrue(invoicespage.isWindowOpened());
		invoicespage.closeTab(newTab);
	}

	@Test(testName = "Test Case 43691:Operation - Invoice: Edit - Pay")
	public void checkOperationInvoiceEditPay() throws InterruptedException, AWTException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		try {
			invoicespage.clickFindButton();
			invoicespage.selectActionForFirstInvoice("Mark as Unpaid", false);
		} catch (Exception e) {
		}
		invoicespage.clickFindButton();
		invoicespage.selectActionForFirstInvoice("Pay", false);
		invoicespage.checkPayBoxContent();
		invoicespage.clickFindButton();
		invoicespage.selectActionForFirstInvoice("Mark as Unpaid", false);

	}

	@Test(testName = "Test Case 43694:Operation - Invoice: Edit - Audit Log")
	public void checkOperationInvoiceEditAuditLog() throws InterruptedException, AWTException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String auditLogWindow = invoicespage.selectActionForFirstInvoice("Payments", false);
		Assert.assertTrue(invoicespage.checkAuditLogWindowContent(auditLogWindow));
	}

	@Test(testName = "Test Case 60615:Operation - Invoice: Search operation")
	public void checkOperationInvoiceSearchOperation() throws InterruptedException, AWTException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		Assert.assertTrue(invoicespage.checkInvoiceTableInfo());
		Assert.assertTrue(invoicespage.checkInvoiceTablePagination());
		Assert.assertTrue(invoicespage.checkInvoicesSearchFields());
		Assert.assertTrue(invoicespage.checkInvoicesSearchResults());
	}

	@Test(testName = "Test Case 64968:Operations - Invoice: Export")
	public void checkOperationsInvoiceExport() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchStatus("New");
		invoicespage.clickFindButton();
		invoicespage.selectIvoicesFromTop(3);
		String mainWindow = invoicespage.getMainWindow();
		invoicespage.clickExportButton();
		ExportInvoicesWebPage exportInvoicesPage = invoicespage.switchToExportInvoicesWindow(mainWindow);
		Assert.assertTrue(exportInvoicesPage.allInvoicesAreAbleToExport());
	}
}

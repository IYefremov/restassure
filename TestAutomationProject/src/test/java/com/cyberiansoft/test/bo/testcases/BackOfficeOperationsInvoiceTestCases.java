package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.utils.WebConstants.InvoiceStatuses;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BackOfficeOperationsInvoiceTestCases extends BaseTestCase {

	@Test(description = "Test Case 15161:Operation - Invoice: Search")
	public void testOperationInvoiceSearch() {

		final String customer = "000 My Company";
        final String amountfrom = "0";
		final String amountto = "8";

        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		invoicespage.verifyInvoicesTableColumnsAreVisible();
		Assert.assertEquals("1", invoicespage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", invoicespage.getGoToPageFieldValue());

		// String initialpagenumber = invoicespage.getLastPageNumber();
		invoicespage.setPageSize("1");
		Assert.assertEquals(1, invoicespage.getInvoicesTableRowCount());
		int numberofrows = Integer.valueOf(invoicespage.getLastPageNumber());

		String lastpagenumber = invoicespage.getLastPageNumber();
		invoicespage.clickGoToLastPage(browsertype.getBrowserTypeString());
		Assert.assertEquals(lastpagenumber, invoicespage.getGoToPageFieldValue());

		invoicespage.clickGoToFirstPage();
		Assert.assertEquals("1", invoicespage.getGoToPageFieldValue());

		invoicespage.clickGoToNextPage();
		Assert.assertEquals("2", invoicespage.getGoToPageFieldValue());

		invoicespage.clickGoToPreviousPage();
		Assert.assertEquals("1", invoicespage.getGoToPageFieldValue());

		invoicespage.setPageSize("999");
		if (numberofrows < 500) {
			Assert.assertEquals(numberofrows, invoicespage.getInvoicesTableRowCount());
		} else
			Assert.assertEquals(500, invoicespage.getInvoicesTableRowCount());

		invoicespage.verifySearchFieldsAreVisible();
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
		invoicespage.selectSearchCustomer(customer);
		invoicespage.setSearchAmountFrom(amountfrom);
		invoicespage.setSearchAmountTo(amountto);
		invoicespage.clickFindButton();
		Assert.assertEquals(invoicespage.getInvoicesTableRowCount(), 4);
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
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
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

//	 @Test(testName = "Test Case 24751:Operations: Invoice editor - verify that 'Approve invoice after payment' checkbox is disabled after checked it and Invoice is Approved", description = "Operations: Invoice editor - verify that 'Approve invoice after payment' checkbox is disabled after checked it and Invoice is Approved")
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
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
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
		InvoiceEditTabWebPage invoiceEditTab = invoicespage.clickEditInvoice(invoicenumber);
		invoiceEditTab.clcikAddPO();
		invoiceEditTab.clickAddPOPayButton();
		Assert.assertTrue(invoiceEditTab.getPONumberField().getAttribute("style").contains("solid red"));
		invoiceEditTab.setPONumber(po);
		invoiceEditTab.setPONotes(notes);
		String alerttext = invoiceEditTab.clickAddPOPayButtonAndAcceptPayment();
		Assert.assertEquals(alerttext, "PO/RO payment has processed.");

		invoiceEditTab.clcikAddPO();
		invoiceEditTab.setPONumber(ponum);
		invoiceEditTab.setPONotes(ponotes);
		invoiceEditTab.checkApproveInvoiceAfterPayment();
		alerttext = invoiceEditTab.clickAddPOPayButtonAndAcceptPayment();
		Assert.assertEquals(alerttext, "PO/RO payment has processed.");
		invoiceEditTab.clcikAddPO();
		Assert.assertFalse(invoiceEditTab.getCheckApproveInvoiceAfterPayment().isEnabled());
		invoiceEditTab.closeNewTab(mainWindowHandle);

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
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		final String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(5000);
		InvoiceEditTabWebPage invoiceEditTab = invoicespage.clickEditInvoice(invoicenumber);
		invoiceEditTab.clickTechniciansLink();
		invoiceEditTab.unselectAllTechnicians();
	}

	// @Test(testName = "Test Case 28578:Operation - Invoice: Edit - Click here
	// to edit notes", description = "Operation - Invoice: Edit - Click here to
	// edit notes")
	public void testOperationInvoiceEditClickHereToEditNotes() throws Exception {

		final String invoicenumber = "I-000-00243";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
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

	@Test(testName = "Test Case 28594:Operation - Invoice : Sent mail in Mail Activity", description = "Operation - Invoice : Sent mail in Mail Activity")
	public void testOperationInvoiceSentMailInMailActivity() throws Exception {

		final String usermail = "olexandr.kramar@cyberiansoft.com";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
		invoicespage.clickFindButton();
		final String invoicenumber = invoicespage.getFirstInvoiceName();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		final String mainWindowHandle = webdriver.getWindowHandle();
		Thread.sleep(2000);
		Assert.assertTrue(invoicespage.isInvoiceEmailSent(invoicenumber, usermail));
		Thread.sleep(30 * 1000);
		InvoiceEmailActivityTabWebPage invoiceemailactivitytab = invoicespage.clickEmailActivity(invoicenumber);
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowRecipientsValue().contains(usermail));
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowSentTimeValue()
				.contains(BackOfficeUtils.getShortCurrentTimeWithTimeZone()));
		Assert.assertEquals("true", invoiceemailactivitytab.getFirstRowSendCheckboxValue());
		invoiceemailactivitytab.closeNewTab(mainWindowHandle);
	}

	//@Test(testName = "Test Case 28596:Operation - Invoice : Sent Custom mail in Mail Activity", description = "Operation - Invoice : Sent Custom mail in Mail Activity"/**/)
	public void testOperationInvoiceSentCustomMailInMailActivity() throws Exception {

		final String usermail = "olexandr.kramar@cyberiansoft.com";
		final String message = "Mail Message";
		final String invoicenumber = "I-223-00005";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
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

		workorderspage.setSearchOrderNumber(wonum);
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
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
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
            Assert.assertEquals("Approved", invoicespage.getInvoiceStatus(invoicenumber));
		} else {
			invoicespage.changeInvoiceStatus(invoicenumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
            Assert.assertEquals("New", invoicespage.getInvoiceStatus(invoicenumber));
			invoicespage.changeInvoiceStatus(invoicenumber, "Approved");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED);
			invoicespage.clickFindButton();
            Assert.assertEquals("Approved", invoicespage.getInvoiceStatus(invoicenumber));
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
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
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
            Assert.assertEquals("Exported", invoicespage.getInvoiceStatus(invoicenumber));
		} else {
			invoicespage.changeInvoiceStatus(invoicenumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
            Assert.assertEquals("New", invoicespage.getInvoiceStatus(invoicenumber));
			invoicespage.changeInvoiceStatus(invoicenumber, "Exported");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_EXPORTED);
			invoicespage.clickFindButton();
            Assert.assertEquals("Exported", invoicespage.getInvoiceStatus(invoicenumber));
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
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
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
            Assert.assertEquals("Void", invoicespage.getInvoiceStatus(invoicenumber));
		} else {
			invoicespage.changeInvoiceStatus(invoicenumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
            Assert.assertEquals("New", invoicespage.getInvoiceStatus(invoicenumber));
			invoicespage.changeInvoiceStatus(invoicenumber, "Void");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
			invoicespage.clickFindButton();
            Assert.assertEquals("Void", invoicespage.getInvoiceStatus(invoicenumber));
		}
	}

	@Test(testName = "Test Case 43710:Operation - Invoice: Status - Export Failed")
	public void checkOperationInvoiceStatusExportFailed() throws InterruptedException {
		final String ponum = "123";

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		WorkOrdersWebPage workOrdersPage = operationspage.clickWorkOrdersLink();
		workOrdersPage.unselectInvoiceFromDeviceCheckbox();
		workOrdersPage.selectSearchStatus("All");
		workOrdersPage.clickFindButton();

		workOrdersPage.unselectInvoiceFromDeviceCheckbox();
		workOrdersPage.checkFirstWorkOrderCheckBox();
		workOrdersPage.addInvoiceDescription("test");
		workOrdersPage.clickCreateInvoiceButton();

		String wonum = workOrdersPage.getFirstWorkOrderNumberInTheTable();
		String invoiceNumber = workOrdersPage.getWorkOrderInvoiceNumber(wonum);

		workOrdersPage.selectSearchStatus("All");
		workOrdersPage.clickFindButton();

		if (invoiceNumber.equals("")) {
			workOrdersPage.createInvoiceFromWorkOrder(wonum, ponum);
			workOrdersPage.setSearchOrderNumber(wonum);
			workOrdersPage.clickFindButton();
			invoiceNumber = workOrdersPage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.setSearchInvoiceNumber(invoiceNumber);
		String status = "";
		for (InvoiceStatuses stat : WebConstants.InvoiceStatuses.values()) {
			try {
				invoicespage.selectSearchStatus(stat);
				invoicespage.clickFindButton();
				status = invoicespage.getInvoiceStatus(invoiceNumber);
				if (!status.isEmpty())
					break;
			} catch (Exception e) {
			}
		}
		if (status.equals("New")) {
			invoicespage.changeInvoiceStatus(invoiceNumber, "Export Failed");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_EXPORT_FAILED);
			invoicespage.clickFindButton();
            Assert.assertEquals("Export Failed", invoicespage.getInvoiceStatus(invoiceNumber));
		} else {
			invoicespage.changeInvoiceStatus(invoiceNumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
            Assert.assertEquals("New", invoicespage.getInvoiceStatus(invoiceNumber));
			invoicespage.changeInvoiceStatus(invoiceNumber, "Export Failed");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_EXPORT_FAILED);
			invoicespage.clickFindButton();
            Assert.assertEquals("Export Failed", invoicespage.getInvoiceStatus(invoiceNumber));
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

		workorderspage.setSearchOrderNumber(wonum);
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
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		String status = "";
		for (InvoiceStatuses stat : WebConstants.InvoiceStatuses.values()) {
			try {
				invoicespage.selectSearchStatus(stat);
				invoicespage.clickFindButton();
				status = invoicespage.getInvoiceStatus(invoicenumber);
				if (!status.isEmpty())
					break;
			} catch (Exception ignored) {}
		}
		if (status.equals("New")) {
			invoicespage.changeInvoiceStatus(invoicenumber, "Draft");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
			invoicespage.clickFindButton();
            Assert.assertEquals("Draft", invoicespage.getInvoiceStatus(invoicenumber));
		} else {
			invoicespage.changeInvoiceStatus(invoicenumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
            Assert.assertEquals("New", invoicespage.getInvoiceStatus(invoicenumber));
			invoicespage.changeInvoiceStatus(invoicenumber, "Draft");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
			invoicespage.clickFindButton();
            Assert.assertEquals("Draft", invoicespage.getInvoiceStatus(invoicenumber));
		}
	}

	//todo fails unexpectedly with batch run. No fail, if only this test runs
	@Test(testName = "Test Case 43689:Operation - Invoice: Edit - Mark As Paid")
	public void checkOperationInvoiceEditMarkAsPaid() {
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		InvoicesWebPage invoicesPage = backofficeHeader
                .clickOperationsLink()
                .clickInvoicesLink();
		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicesPage.selectBillingOption(WebConstants.BillingValues.NO_PAYMENT_INFO);

		invoicesPage.clickFindButton();
        String firstInvoiceNameBefore = invoicesPage.getFirstInvoiceName();
        invoicesPage.selectActionForFirstInvoice("Mark as Paid", false);

        invoicesPage
                .selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS)
                .selectBillingOption(WebConstants.BillingValues.PAID)
                .insertInvoice(firstInvoiceNameBefore);
		invoicesPage.clickFindButton();
		Assert.assertEquals(firstInvoiceNameBefore, invoicesPage.getFirstInvoiceName(), "Names differ!");
		Assert.assertTrue(invoicesPage.isFirstInvoiceMarkedAsPaid());
	}

	// @Test(testName = "Test Case 43217:Operation - Invoice: Edit - Vehicle
	// Info")
	public void checkOperationInvoiceEditVehicleInfo() {
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
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();

		@SuppressWarnings("unused")
		InvoiceEditTabWebPage invoiceeditpage = invoicespage.clickEditFirstInvoice();
	}

	@Test(testName = "Test Case 43692:Operation - Invoice: Edit - Change Invoice")
	public void checkOperationInvoiceEditChangeInvoice() {
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        InvoicesWebPage invoicesPage = backofficeHeader
                .clickOperationsLink()
                .clickInvoicesLink();
        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.clickFindButton();
        String firstInvoiceNameBefore = invoicesPage.getFirstInvoiceName();

		invoicesPage.selectActionForFirstInvoice("Change Invoice#", false);
		Assert.assertTrue(invoicesPage.checkInvoiceFrameOpened());
		Assert.assertTrue(invoicesPage.isCloseButtonClicked());
		Assert.assertEquals(firstInvoiceNameBefore, invoicesPage.getFirstInvoiceName(),
                "The invoice name has been changed although the 'Close' button was clicked!");

        invoicesPage.selectActionForFirstInvoice("Change Invoice#", false);
        Assert.assertTrue(invoicesPage.checkInvoiceFrameOpened());
        Assert.assertTrue(invoicesPage.isChangeButtonClicked());
	}

	@Test(testName = "Test Case 43693:Operation - Invoice: Edit - Download JSON")
	public void checkOperationInvoiceDownloadJSON() {
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        InvoicesWebPage invoicesPage = backofficeHeader
                .clickOperationsLink()
                .clickInvoicesLink();
        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.clickFindButton();
		invoicesPage.selectActionForFirstInvoice("Download JSON", false);
	}

	@Test(testName = "Test Case 43724:Operation - Invoice: Edit - Tech. Info")
	public void checkOperationInvoiceEditTechInfo() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String newTab = invoicespage.selectActionForFirstInvoice("Tech. Info", false);
		Assert.assertTrue(invoicespage.isWindowOpened());
		invoicespage.closeTab(newTab);
	}

	@Test(testName = "Test Case 43699:Operation - Invoice: Edit - Recalc Tech Split")
	public void checkOperationInvoiceEditRecalcTechSplit() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		invoicespage.selectActionForFirstInvoice("Recalc Tech Split", false);
		Assert.assertTrue(invoicespage.recalcTechSplitProceed());
	}

	@Test(testName = "Automate Test Case 28594:Operation - Invoice : Sent mail in Mail Activity"/**/)
	public void checkOperationInvoiceSentMailInMailActivity() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String emailWindow = invoicespage.selectActionForFirstInvoice("Email Activity", false);
		int emailActivities = invoicespage.countEmailActivities(emailWindow);
		invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		invoicespage.selectActionForFirstInvoice("Send Email", false);
		Assert.assertTrue(invoicespage.isSendEmailBoxOpened());
		invoicespage.setEmailAndSend("test123@domain.com");
		invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		emailWindow = invoicespage.selectActionForFirstInvoice("Email Activity", false);
		Assert.assertTrue(emailActivities < invoicespage.countEmailActivities(emailWindow));
	}

	@Test(testName = "Automate Test Case 28596:Operation - Invoice : Sent Custom mail in Mail Activity")
	public void checkOperationInvoiceSentCustomMailInMailActivity() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String emailActivityWindow = invoicespage.selectActionForFirstInvoice("Email Activity", false);
		int emailActivities = invoicespage.countEmailActivities(emailActivityWindow);
		invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String emailWindow = invoicespage.selectActionForFirstInvoice("Send Custom Email", false);
		invoicespage.setCustomEmailAndSend("test123@domain.com", emailWindow);
		invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		emailActivityWindow = invoicespage.selectActionForFirstInvoice("Email Activity", false);
		int emailActivitiesAfter = invoicespage.countEmailActivities(emailActivityWindow);
		Assert.assertTrue(emailActivities < emailActivitiesAfter);
	}

	@Test(testName = "Test Case 43724:Operation - Invoice: Edit - Internal Tech. Info")
	public void checkOperationInvoiceEditInternalTechInfo() {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		String newTab = invoicespage.selectActionForFirstInvoice("Internal Tech. Info", false);
		Assert.assertTrue(invoicespage.isWindowOpened());
		invoicespage.closeTab(newTab);
	}

	// @Test(testName = "Test Case 28933:Operations - Invoice: Archive")
	public void checkOperationInvoiceArchive() {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.clickFindButton();

	}

	//@Test(testName = "Test Case 29198:Operation - Invoice: Year/Make/Model/Search")
	public void checkOperationInvoiceYearMakeModelSearch() {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		// TODO bug
		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
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
	public void checkOperationInvoiceEditPrintPreview() {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();

		String newTab = invoicespage.selectActionForFirstInvoice("Print preview (server)", false);
		Assert.assertTrue(invoicespage.isWindowOpened());
		invoicespage.closeTab(newTab);
	}

	@Test(testName = "Test Case 43691:Operation - Invoice: Edit - Pay")
	public void checkOperationInvoiceEditPay() throws InterruptedException {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		operationspage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		try {
			invoicespage.clickFindButton();
			invoicespage.selectActionForFirstInvoice("Mark as Unpaid", false);
		} catch (Exception e) {
		}
		invoicespage.clickFindButton();
		invoicespage.selectActionForFirstInvoice("Pay", false);
		invoicespage.checkPayBoxContent();
		invoicespage.clickFindButton();
		try{
		invoicespage.selectActionForFirstInvoice("Mark as Unpaid", false);
		}catch(Exception e){}
	}

	@Test(testName = "Test Case 43694:Operation - Invoice: Edit - Audit Log")
	public void checkOperationInvoiceEditAuditLog() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage;
		operationsPage = backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationsPage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String auditLogWindow = invoicespage.selectActionForFirstInvoice("Payments", false);
		Assert.assertTrue(invoicespage.checkAuditLogWindowContent(auditLogWindow));
	}

	@Test(testName = "Test Case 60615:Operation - Invoice: Search operation")
	public void checkOperationInvoiceSearchOperation() throws InterruptedException {

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
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backofficeHeader.clickOperationsLink();
		InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
		invoicesPage.selectSearchStatus("New");
		invoicesPage.clickFindButton();
		invoicesPage.selectIvoicesFromTop(3);
		String mainWindow = invoicesPage.getMainWindow();
		invoicesPage.clickExportButton();
		ExportInvoicesWebPage exportInvoicesPage = invoicesPage.switchToExportInvoicesWindow(mainWindow);
		Assert.assertTrue(exportInvoicesPage.allInvoicesAreAbleToExport());
	}
}

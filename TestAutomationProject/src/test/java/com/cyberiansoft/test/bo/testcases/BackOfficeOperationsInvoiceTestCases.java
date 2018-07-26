package com.cyberiansoft.test.bo.testcases;

import com.automation.remarks.testng.VideoListener;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.utils.WebConstants.InvoiceStatuses;
import com.cyberiansoft.test.dataclasses.bo.BOOperationsInvoiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(VideoListener.class)
public class BackOfficeOperationsInvoiceTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOOperationsInvoiceData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInvoiceSearch(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

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
		invoicespage.selectSearchCustomer(data.getCustomer());
		invoicespage.setSearchAmountFrom(data.getAmountFrom());
		invoicespage.setSearchAmountTo(data.getAmountTo());
		invoicespage.clickFindButton();
		Assert.assertEquals(invoicespage.getInvoicesTableRowCount(), 4);
	}

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInvoiceEditorVerifyAddPOIsPresentAndPaymentIsAdded(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		if (!invoicespage.getInvoiceStatus(data.getInvoiceNumber())
				.equals(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName())) {
			invoicespage.changeInvoiceStatus(data.getInvoiceNumber(), WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
		}

		String mainWindowHandle = webdriver.getWindowHandle();
		// TODO when webdriver renewed
		InvoiceEditTabWebPage invoiceedittab = invoicespage.clickEditInvoice(data.getInvoiceNumber());
		invoiceedittab.clcikAddPO();
		invoiceedittab.clickAddPOPayButton();
		Assert.assertTrue(invoiceedittab.getPONumberField().getAttribute("style").contains("solid red"));
		invoiceedittab.setPONumber(data.getPo());
		invoiceedittab.setPONotes(data.getNotes());
		String alerttext = invoiceedittab.clickAddPOPayButtonAndAcceptPayment();
		Assert.assertEquals(alerttext, "PO/RO payment has processed.");
		invoiceedittab.closeNewTab(mainWindowHandle);
		mainWindowHandle = webdriver.getWindowHandle();
		invoiceedittab.waitABit(1000);
		InvoicePaymentsTabWebPage invoicepaymentstab = invoicespage.clickInvoicePayments(data.getInvoiceNumber());
		Assert.assertTrue(invoicepaymentstab.getInvoicesPaymentsLastTableRowPaidColumnValue()
				.contains(BackOfficeUtils.getShortCurrentTimeWithTimeZone()));
		Assert.assertTrue(invoicepaymentstab.getInvoicesPaymentsLastTableRowDescriptionColumnValue().contains(data.getPo()));
		invoicepaymentstab.clickNotesForInvoicesPaymentsLastTableRow();
		Assert.assertEquals(invoicepaymentstab.getInvoicePaymentNoteValue(), data.getNotes());
		invoicepaymentstab.clickClosePaymentsPopup();
		invoicespage.closeNewTab(mainWindowHandle);
	}

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInvoiceEditorVerifyThatApproveInvoiceAfterPaymentCheckboxIsDisabledAfterCheckedItAndInvoiceIsApproved(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		if (!invoicespage.getInvoiceStatus(data.getInvoiceNumber())
				.equals(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName())) {
			invoicespage.changeInvoiceStatus(data.getInvoiceNumber(), WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
		}
		final String mainWindowHandle = webdriver.getWindowHandle();
		InvoiceEditTabWebPage invoiceEditTab = invoicespage.clickEditInvoice(data.getInvoiceNumber());
		invoiceEditTab.clcikAddPO();
		invoiceEditTab.clickAddPOPayButton();
		Assert.assertTrue(invoiceEditTab.getPONumberField().getAttribute("style").contains("solid red"));
		invoiceEditTab.setPONumber(data.getPo());
		invoiceEditTab.setPONotes(data.getNotes());
		String alerttext = invoiceEditTab.clickAddPOPayButtonAndAcceptPayment();
		Assert.assertEquals(alerttext, "PO/RO payment has processed.");

		invoiceEditTab.clcikAddPO();
		invoiceEditTab.setPONumber(data.getPoNum());
		invoiceEditTab.setPONotes(data.getPoNotes());
		invoiceEditTab.checkApproveInvoiceAfterPayment();
		alerttext = invoiceEditTab.clickAddPOPayButtonAndAcceptPayment();
		Assert.assertEquals(alerttext, "PO/RO payment has processed.");
		invoiceEditTab.clcikAddPO();
		Assert.assertFalse(invoiceEditTab.getCheckApproveInvoiceAfterPayment().isEnabled());
		invoiceEditTab.closeNewTab(mainWindowHandle);

		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED);
		invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();
		Assert.assertTrue(invoicespage.isInvoiceNumberExists(data.getInvoiceNumber()));
		Assert.assertEquals(invoicespage.getInvoiceStatus(data.getInvoiceNumber()),
				WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED.getName());
		invoicespage.changeInvoiceStatus(data.getInvoiceNumber(), WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
	}

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInvoiceEditTechnician(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		final String mainWindowHandle = webdriver.getWindowHandle();
		InvoiceEditTabWebPage invoiceEditTab = invoicespage.clickEditInvoice(data.getInvoiceNumber());
		invoiceEditTab.clickTechniciansLink();
		invoiceEditTab.unselectAllTechnicians();
	}

    //    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInvoiceEditClickHereToEditNotes(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
		invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());

		if (!invoicespage.getInvoiceStatus(data.getInvoiceNumber())
				.equals(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName())) {
			invoicespage.changeInvoiceStatus(data.getInvoiceNumber(), WebConstants.InvoiceStatuses.INVOICESTATUS_NEW.getName());
		}
		// final String invoiceNumber =
		// invoicespage.getFirstInvoiceNumberInTable();
		final String mainWindowHandle = webdriver.getWindowHandle();
        invoicespage.selectEditOption();
		InvoiceEditTabWebPage invoiceedittab = invoicespage.clickEditInvoice(data.getInvoiceNumber());
		final String oldivoicenotesvalue = invoiceedittab.getInvoiceNotesValue();
		// TODO when webdriver version will be updated
		invoiceedittab.setEditableNotes(BackOfficeUtils.getCurrentDateFormatted());
		invoiceedittab.closeNewTab(mainWindowHandle);

		invoiceedittab = invoicespage.clickEditInvoice(data.getInvoiceNumber());
		Assert.assertEquals(oldivoicenotesvalue, invoiceedittab.getInvoiceNotesValue());
		invoiceedittab.setEditableNotes(BackOfficeUtils.getCurrentDateFormatted());
		invoiceedittab.clickSaveInvoiceButton();
		invoiceedittab.closeNewTab(mainWindowHandle);

		invoiceedittab = invoicespage.clickEditInvoice(data.getInvoiceNumber());
		Assert.assertEquals(BackOfficeUtils.getCurrentDateFormatted(), invoiceedittab.getInvoiceNotesValue());
		invoiceedittab.closeNewTab(mainWindowHandle);
	}

	//todo fails. To be fixed
    //    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInvoiceSentMailInMailActivity(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
		invoicespage.clickFindButton();
		final String invoicenumber = invoicespage.getFirstInvoiceName();

		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		final String mainWindowHandle = webdriver.getWindowHandle();
		Assert.assertTrue(invoicespage.isInvoiceEmailSent(invoicenumber, data.getUserMail()));
		InvoiceEmailActivityTabWebPage invoiceemailactivitytab = invoicespage.clickEmailActivity(data.getInvoiceNumber());
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowRecipientsValue().contains(data.getUserMail()));
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowSentTimeValue()
				.contains(BackOfficeUtils.getShortCurrentTimeWithTimeZone()));
		Assert.assertEquals("true", invoiceemailactivitytab.getFirstRowSendCheckboxValue());
		invoiceemailactivitytab.closeNewTab(mainWindowHandle);
	}

    //    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOperationInvoiceSentCustomMailInMailActivity(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
		invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();
		Assert.assertTrue(invoicespage.invoicesTableIsVisible());
		final String mainWindowHandle = webdriver.getWindowHandle();

		SendInvoiceCustomEmailTabWebPage sendinvoicecustomemailtab = invoicespage.clickSendCustomEmail(data.getInvoiceNumber());
		sendinvoicecustomemailtab.setEmailSubjectValue(data.getInvoiceNumber());
		sendinvoicecustomemailtab.setEmailToValue(data.getUserMail());
		sendinvoicecustomemailtab.setEmailMessageValue(data.getMessage());
		sendinvoicecustomemailtab.unselectIncludeInvoicePDFCheckbox();
		sendinvoicecustomemailtab.clickSendEmailButton();

		sendinvoicecustomemailtab.closeNewTab(mainWindowHandle);

		invoicespage.clickFindButton();
		InvoiceEmailActivityTabWebPage invoiceemailactivitytab = invoicespage.clickEmailActivity(data.getInvoiceNumber());
		Assert.assertEquals(data.getUserMail(), invoiceemailactivitytab.getFirstRowRecipientsValue());
		Assert.assertTrue(invoiceemailactivitytab.getFirstRowSentTimeValue()
				.contains(BackOfficeUtils.getShortCurrentTimeWithTimeZone()));
		Assert.assertEquals("true", invoiceemailactivitytab.getFirstRowSendCheckboxValue());
		invoiceemailactivitytab.closeNewTab(mainWindowHandle);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceStatusApproved(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("New");
		workorderspage.clickFindButton();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.checkFirstWorkOrderCheckBox();
		workorderspage.addInvoiceDescription("test");
		workorderspage.clickCreateInvoiceButton();

		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoiceNumber = workorderspage.getWorkOrderInvoiceNumber(wonum);

		workorderspage.setSearchOrderNumber(wonum);
		workorderspage.selectSearchStatus("All");		
		workorderspage.clickFindButton();

		if (invoiceNumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, data.getPoNum());
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoiceNumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.setSearchInvoiceNumber(invoiceNumber);
		invoicespage.clickFindButton();
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
			invoicespage.changeInvoiceStatus(invoiceNumber, "Approved");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED);
			invoicespage.clickFindButton();
            Assert.assertEquals("Approved", invoicespage.getInvoiceStatus(invoiceNumber));
		} else {
			invoicespage.changeInvoiceStatus(invoiceNumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
            Assert.assertEquals("New", invoicespage.getInvoiceStatus(invoiceNumber));
			invoicespage.changeInvoiceStatus(invoiceNumber, "Approved");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_APPROVED);
			invoicespage.clickFindButton();
            Assert.assertEquals("Approved", invoicespage.getInvoiceStatus(invoiceNumber));
		}
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceStatusExported(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.checkFirstWorkOrderCheckBox();
		workorderspage.addInvoiceDescription("test");
		workorderspage.clickCreateInvoiceButton();

		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoiceNumber = workorderspage.getWorkOrderInvoiceNumber(wonum);

		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		if (invoiceNumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, data.getPoNum());
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoiceNumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backOfficeHeader.clickOperationsLink();
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
			invoicespage.changeInvoiceStatus(invoiceNumber, "Exported");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_EXPORTED);
			invoicespage.clickFindButton();
            Assert.assertEquals("Exported", invoicespage.getInvoiceStatus(invoiceNumber));
		} else {
			invoicespage.changeInvoiceStatus(invoiceNumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
            Assert.assertEquals("New", invoicespage.getInvoiceStatus(invoiceNumber));
			invoicespage.changeInvoiceStatus(invoiceNumber, "Exported");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_EXPORTED);
			invoicespage.clickFindButton();
            Assert.assertEquals("Exported", invoicespage.getInvoiceStatus(invoiceNumber));
		}
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceStatusVoid(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.checkFirstWorkOrderCheckBox();
		workorderspage.addInvoiceDescription("test");
		workorderspage.clickCreateInvoiceButton();

		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoiceNumber = workorderspage.getWorkOrderInvoiceNumber(wonum);

		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		if (invoiceNumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, data.getPoNum());
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoiceNumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backOfficeHeader.clickOperationsLink();
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
			invoicespage.changeInvoiceStatus(invoiceNumber, "Void");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
			invoicespage.clickFindButton();
            Assert.assertEquals("Void", invoicespage.getInvoiceStatus(invoiceNumber));
		} else {
			invoicespage.changeInvoiceStatus(invoiceNumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
            Assert.assertEquals("New", invoicespage.getInvoiceStatus(invoiceNumber));
			invoicespage.changeInvoiceStatus(invoiceNumber, "Void");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
			invoicespage.clickFindButton();
            Assert.assertEquals("Void", invoicespage.getInvoiceStatus(invoiceNumber));
		}
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceStatusExportFailed(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

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
			workOrdersPage.createInvoiceFromWorkOrder(wonum, data.getPoNum());
			workOrdersPage.setSearchOrderNumber(wonum);
			workOrdersPage.clickFindButton();
			invoiceNumber = workOrdersPage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backOfficeHeader.clickOperationsLink();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceStatusDraft(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		WorkOrdersWebPage workorderspage = operationspage.clickWorkOrdersLink();
		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.selectSearchStatus("New");
		workorderspage.clickFindButton();

		workorderspage.unselectInvoiceFromDeviceCheckbox();
		workorderspage.checkFirstWorkOrderCheckBox();
		workorderspage.addInvoiceDescription("test");
		workorderspage.clickCreateInvoiceButton();

		String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		String invoiceNumber = workorderspage.getWorkOrderInvoiceNumber(wonum);

		workorderspage.setSearchOrderNumber(wonum);
		workorderspage.selectSearchStatus("All");
		workorderspage.clickFindButton();

		if (invoiceNumber.equals("")) {
			workorderspage.createInvoiceFromWorkOrder(wonum, data.getPoNum());
			workorderspage.setSearchOrderNumber(wonum);
			workorderspage.clickFindButton();
			invoiceNumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		}

		operationspage = backOfficeHeader.clickOperationsLink();
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
			} catch (Exception ignored) {}
		}
		if (status.equals("New")) {
			invoicespage.changeInvoiceStatus(invoiceNumber, "Draft");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
			invoicespage.clickFindButton();
            Assert.assertEquals("Draft", invoicespage.getInvoiceStatus(invoiceNumber));
		} else {
			invoicespage.changeInvoiceStatus(invoiceNumber, "New");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_NEW);
			invoicespage.clickFindButton();
            Assert.assertEquals("New", invoicespage.getInvoiceStatus(invoiceNumber));
			invoicespage.changeInvoiceStatus(invoiceNumber, "Draft");
			invoicespage.refreshPage();
			invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
			invoicespage.clickFindButton();
            Assert.assertEquals("Draft", invoicespage.getInvoiceStatus(invoiceNumber));
		}
	}

	//todo fails with batch run ???
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditMarkAsPaid(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        InvoicesWebPage invoicesPage = backOfficeHeader
                .clickOperationsLink()
                .clickInvoicesLink();
		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicesPage.selectBillingOption(WebConstants.BillingValues.NO_PAYMENT_INFO);

		invoicesPage.clickFindButton();
        String firstInvoiceNameBefore = invoicesPage.getFirstInvoiceName();
        invoicesPage.selectMarkAsPaidOption();
        invoicesPage.handlePaymentNote();

        invoicesPage
                .selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS)
                .selectBillingOption(WebConstants.BillingValues.PAID)
                .insertInvoice(firstInvoiceNameBefore);
		invoicesPage.clickFindButton();
		Assert.assertEquals(firstInvoiceNameBefore, invoicesPage.getFirstInvoiceName(), "Names differ!");
		Assert.assertTrue(invoicesPage.isFirstInvoiceMarkedAsPaid());
	}

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditVehicleInfo(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		// WorkOrdersWebPage workorderspage =
		// operationspage.clickWorkOrdersLink();
		// workorderspage.unselectInvoiceFromDeviceCheckbox();
		// workorderspage.selectSearchStatus("All");
		// workorderspage.clickFindButton();
		//
		// String wonum = workorderspage.getFirstWorkOrderNumberInTheTable();
		// String invoiceNumber =
		// workorderspage.getWorkOrderInvoiceNumber(wonum);
		// if (invoiceNumber.equals("")) {
		// workorderspage.createInvoiceFromWorkOrder(wonum, data.getPoNum());
		// workorderspage.setSearchOrderNumber(wonum);
		// workorderspage.clickFindButton();
		// invoiceNumber = workorderspage.getWorkOrderInvoiceNumber(wonum);
		// }

		operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();

		@SuppressWarnings("unused")
		InvoiceEditTabWebPage invoiceeditpage = invoicespage.clickEditFirstInvoice();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditChangeInvoice(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        InvoicesWebPage invoicesPage = backOfficeHeader
                .clickOperationsLink()
                .clickInvoicesLink();
        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.clickFindButton();
        String firstInvoiceNameBefore = invoicesPage.getFirstInvoiceName();

		invoicesPage.selectChangeInvoiceOption();
		Assert.assertTrue(invoicesPage.checkInvoiceFrameOpened());
		Assert.assertTrue(invoicesPage.isCloseButtonClicked());
		Assert.assertEquals(firstInvoiceNameBefore, invoicesPage.getFirstInvoiceName(),
                "The invoice name has been changed although the 'Close' button was clicked!");

        invoicesPage.selectChangeInvoiceOption();
        Assert.assertTrue(invoicesPage.checkInvoiceFrameOpened());
        Assert.assertTrue(invoicesPage.isChangeButtonClicked());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceDownloadJSON(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        InvoicesWebPage invoicesPage = backOfficeHeader
                .clickOperationsLink()
                .clickInvoicesLink();
        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.clickFindButton();
        invoicesPage.selectDownloadJsonOption();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditTechInfo(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String newTab = invoicespage.selectTechInfoOption();
		Assert.assertTrue(invoicespage.isWindowOpened());
		invoicespage.closeTab(newTab);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditRecalcTechSplit(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();
		invoicespage.selectRecalcTechSplitOption();
		Assert.assertTrue(invoicespage.recalcTechSplitProceed());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceSentMailInMailActivity(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String emailWindow = invoicespage.selectEmailActivityOption();
        int emailActivities = invoicespage.countEmailActivities(emailWindow);
		invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		invoicespage.selectSendEmailOption();
		Assert.assertTrue(invoicespage.isSendEmailBoxOpened());
		invoicespage.setEmailAndSend("test123@domain.com");
		invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		emailWindow = invoicespage.selectEmailActivityOption();
		Assert.assertTrue(emailActivities < invoicespage.countEmailActivities(emailWindow));
	}

	//todo fails. To be fixed
//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceSentCustomMailInMailActivity(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String emailActivityWindow = invoicespage.selectEmailActivityOption();
		int emailActivities = invoicespage.countEmailActivities(emailActivityWindow);
		invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String emailWindow = invoicespage.selectSendCustomEmailOption();
		invoicespage.setCustomEmailAndSend("test123@domain.com", emailWindow);
		invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		emailActivityWindow = invoicespage.selectEmailActivityOption();
		int emailActivitiesAfter = invoicespage.countEmailActivities(emailActivityWindow);
		Assert.assertTrue(emailActivities < emailActivitiesAfter);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditInternalTechInfo(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();
		String newTab = invoicespage.selectInternalTechInfoOption();
		Assert.assertTrue(invoicespage.isWindowOpened());
		invoicespage.closeTab(newTab);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceArchive(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.clickFindButton();

	}

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceYearMakeModelSearch(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		// TODO bug
		operationspage = backOfficeHeader.clickOperationsLink();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditPrintPreview(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		// invoicespage.setSearchInvoiceNumber(data.getInvoiceNumber());
		invoicespage.clickFindButton();

		String newTab = invoicespage.selectPrintPreviewServerOption();
		Assert.assertTrue(invoicespage.isWindowOpened());
		invoicespage.closeTab(newTab);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditPay(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		try {
			invoicespage.clickFindButton();
			invoicespage.selectMarkAsUnpaidOption();
		} catch (Exception e) {
		}
		invoicespage.clickFindButton();
		invoicespage.selectPayOption();
		invoicespage.checkPayBoxContent();
		invoicespage.clickFindButton();
		try{
		invoicespage.selectMarkAsUnpaidOption();
		}catch(Exception e){}
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditAuditLog(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage;
		operationsPage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationsPage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String auditLogWindow = invoicespage.selectPaymentsOption();
		Assert.assertTrue(invoicespage.checkAuditLogWindowContent(auditLogWindow));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceSearchOperation(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		Assert.assertTrue(invoicespage.checkInvoiceTableInfo());
		Assert.assertTrue(invoicespage.checkInvoiceTablePagination());
		Assert.assertTrue(invoicespage.checkInvoicesSearchFields());
		Assert.assertTrue(invoicespage.checkInvoicesSearchResults());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationsInvoiceExport(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
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

package com.cyberiansoft.test.bo.testcases;

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
import org.testng.annotations.Test;

//@Listeners(VideoListener.class)
public class BackOfficeOperationsInvoiceTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOOperationsInvoiceData.json";

    @BeforeClass
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
		Assert.assertEquals(invoicespage.getInvoicesTableRowCount(), 1);
		int numberofrows = Integer.valueOf(invoicespage.getLastPageNumber());

		String lastpagenumber = invoicespage.getLastPageNumber();
		invoicespage.clickGoToLastPage(browserType.getBrowserTypeString());
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
		Assert.assertEquals(invoicespage.getInvoicesTableRowCount(), 24);
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
		Assert.assertTrue(invoicespage.isInvoiceDisplayed(data.getInvoiceNumber()));
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

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
        invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());

        String invoiceNumber = "";
        try {
            invoiceNumber = invoicesPage.getFirstInvoiceNameIfDisplayed();
        } catch (Exception ignored) {}

        if (invoiceNumber.isEmpty()) {
            invoicesPage.selectSearchStatus(data.getAllStatus());
            invoicesPage.clickFindButton();
            invoiceNumber = invoicesPage.getFirstInvoiceName();
            invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
            invoicesPage.refreshPage();

            invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());
        }
        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getApprovedStatus());
        invoicesPage.refreshPage();

        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.selectSearchStatus(data.getApprovedStatus());
        invoicesPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesPage.clickFindButton();
        Assert.assertTrue(invoicesPage.isInvoiceDisplayed(invoiceNumber), "The invoice is not displayed");
        Assert.assertEquals(invoicesPage.getInvoiceStatus(invoiceNumber), data.getApprovedStatus());

        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceStatusExported(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

        InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
        invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());

        String invoiceNumber = "";
        try {
            invoiceNumber = invoicesPage.getFirstInvoiceNameIfDisplayed();
        } catch (Exception ignored) {}

        if (invoiceNumber.isEmpty()) {
            invoicesPage.selectSearchStatus(data.getAllStatus());
            invoicesPage.clickFindButton();
            invoiceNumber = invoicesPage.getFirstInvoiceName();
            invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
            invoicesPage.refreshPage();

            invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());
        }
        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getExportedStatus());
        invoicesPage.refreshPage();

        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.selectSearchStatus(InvoiceStatuses.INVOICESTATUS_EXPORTED);
        invoicesPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesPage.clickFindButton();
        Assert.assertTrue(invoicesPage.isInvoiceDisplayed(invoiceNumber), "The invoice is not displayed");
        Assert.assertEquals(invoicesPage.getInvoiceStatus(invoiceNumber), data.getExportedStatus());

        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceStatusVoid(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

        InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
        invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());

        String invoiceNumber = "";
        try {
            invoiceNumber = invoicesPage.getFirstInvoiceNameIfDisplayed();
        } catch (Exception ignored) {}

        if (invoiceNumber.isEmpty()) {
            invoicesPage.selectSearchStatus(data.getAllStatus());
            invoicesPage.clickFindButton();
            invoiceNumber = invoicesPage.getFirstInvoiceName();
            invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
            invoicesPage.refreshPage();

            invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());
        }
        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getVoidStatus());
        invoicesPage.refreshPage();

        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.selectSearchStatus(data.getVoidStatus());
        invoicesPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesPage.clickFindButton();
        Assert.assertTrue(invoicesPage.isInvoiceDisplayed(invoiceNumber), "The invoice is not displayed");
        Assert.assertEquals(invoicesPage.getInvoiceStatus(invoiceNumber), data.getVoidStatus());

        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceStatusDraft(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

        InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
        invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());

        String invoiceNumber = "";
        try {
            invoiceNumber = invoicesPage.getFirstInvoiceNameIfDisplayed();
        } catch (Exception ignored) {}

        if (invoiceNumber.isEmpty()) {
            invoicesPage.selectSearchStatus(data.getAllStatus());
            invoicesPage.clickFindButton();
            invoiceNumber = invoicesPage.getFirstInvoiceName();
            invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
            invoicesPage.refreshPage();

            invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());
        }
        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getDraftStatus());
        invoicesPage.refreshPage();

        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.selectSearchStatus(data.getDraftStatus());
        invoicesPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesPage.clickFindButton();
        Assert.assertTrue(invoicesPage.isInvoiceDisplayed(invoiceNumber), "The invoice is not displayed");
        Assert.assertEquals(invoicesPage.getInvoiceStatus(invoiceNumber), data.getDraftStatus());

        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceStatusExportFailed(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

        InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
        invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());

        String invoiceNumber = "";
        try {
            invoiceNumber = invoicesPage.getFirstInvoiceNameIfDisplayed();
        } catch (Exception ignored) {}

        if (invoiceNumber.isEmpty()) {
            invoicesPage.selectSearchStatus(data.getAllStatus());
            invoicesPage.clickFindButton();
            invoiceNumber = invoicesPage.getFirstInvoiceName();
            invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
            invoicesPage.refreshPage();

            invoicesPage.findInvoice(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS, data.getNewStatus());
        }
        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getExportFailedStatus());
        invoicesPage.refreshPage();

        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.selectSearchStatus(data.getExportFailedStatus());
        invoicesPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesPage.clickFindButton();
        Assert.assertTrue(invoicesPage.isInvoiceDisplayed(invoiceNumber), "The invoice is not displayed");
        Assert.assertEquals(invoicesPage.getInvoiceStatus(invoiceNumber), data.getExportFailedStatus());

        invoicesPage.changeInvoiceStatus(invoiceNumber, data.getNewStatus());
	}

	//todo fails with batch run ???
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditMarkAsPaid(String rowID, String description, JSONObject testData) {
        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.clickFindButton();

        String invoiceNumber = invoicesPage.getFirstInvoiceName();
        invoicesPage.refreshPage();

        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesPage.clickFindButton();

        Assert.assertTrue(invoicesPage.isInvoiceDisplayed(invoiceNumber), "The invoice is not displayed");
        if (invoicesPage.isFirstInvoiceMarkedAsPaid()) {
            invoicesPage.selectMarkAsUnpaidOption();
        }
        invoicesPage.selectMarkAsPaidOption();
        invoicesPage.handlePaymentNote();

        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesPage.clickFindButton();
        Assert.assertTrue(invoicesPage.isFirstInvoiceMarkedAsPaid(), "The invoice isn't marked as Paid");
    }

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditVehicleInfo(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

		OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
        InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.clickFindButton();

        String invoiceNumber = invoicesPage.getFirstInvoiceName();
        invoicesPage.refreshPage();

		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicesPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesPage.clickFindButton();

        Assert.assertTrue(invoicesPage.isInvoiceDisplayed(invoiceNumber), "The invoice is not displayed");
        if (invoicesPage.isFirstInvoiceMarkedAsPaid()) {
            invoicesPage.selectMarkAsUnpaidOption();
        }
        invoicesPage.selectMarkAsPaidOption();
        invoicesPage.handlePaymentNote();

        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesPage.clickFindButton();
        Assert.assertTrue(invoicesPage.isFirstInvoiceMarkedAsPaid(), "The invoice isn't marked as Paid");
//        InvoiceEditTabWebPage invoiceeditpage = invoicesPage.clickEditFirstInvoice();
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

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceDownloadJSON(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        InvoicesWebPage invoicesPage = backOfficeHeader
                .clickOperationsLink()
                .clickInvoicesLink();
        invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicesPage.clickFindButton();
        invoicesPage.selectDownloadJsonOption();
        invoicesPage.handleAlertForEdgeBrowser();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditTechInfo(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
		invoicesPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicesPage.clickFindButton();
		String newTab = invoicesPage.selectTechInfoOption();
		Assert.assertTrue(invoicesPage.isWindowOpened());
		invoicesPage.closeTab(newTab);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceEditRecalcTechSplit(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
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
		invoicespage.selectSendEmailOption();
		Assert.assertTrue(invoicespage.isSendEmailBoxOpened());
        String email = data.getEmail();
        invoicespage.setEmailAndSend(email);
		invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
		String emailWindow = invoicespage.selectEmailActivityOption();
        Assert.assertTrue(invoicespage.isEmailDisplayed(emailWindow, email));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceSentCustomMailInMailActivity(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
        InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
        invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
        invoicespage.clickFindButton();
        String email = data.getEmail();
        String emailWindow = invoicespage.selectSendCustomEmailOption();
        invoicespage.setCustomEmailAndSend(email, emailWindow);
        invoicespage.refreshPage();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS);
		invoicespage.clickFindButton();
        emailWindow = invoicespage.selectEmailActivityOption();
        Assert.assertTrue(invoicespage.isEmailDisplayed(emailWindow, email));
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

	//todo TC is not ready!!! TODO
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationInvoiceArchive(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        backOfficeHeader.clickOperationsLink();
        OperationsWebPage operationspage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.clickFindButton();
        System.out.println();
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
        invoicespage.clickFindButton();
        invoicespage.chooseMarkAsUnpaidOptionIfPresent();
        invoicespage.selectPayOption();
		invoicespage.checkPayBoxContent();
		invoicespage.clickFindButton();
        invoicespage.chooseMarkAsUnpaidOptionIfPresent();
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
		Assert.assertTrue(invoicespage.isInvoiceFound(
		        data.getInvoiceNumber(), data.getCustomer(), data.getDateFrom(), data.getDateTo()));
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkOperationsInvoiceExport(String rowID, String description, JSONObject testData) {

        BOOperationsInvoiceData data = JSonDataParser.getTestDataFromJson(testData, BOOperationsInvoiceData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();
		InvoicesWebPage invoicesPage = operationsPage.clickInvoicesLink();
		invoicesPage.selectSearchStatus(data.getNewStatus());
		invoicesPage.clickFindButton();
		invoicesPage.selectIvoicesFromTop(3);
		String mainWindow = invoicesPage.getMainWindow();
		invoicesPage.clickExportButton();
		ExportInvoicesWebPage exportInvoicesPage = invoicesPage.switchToExportInvoicesWindow(mainWindow);
		Assert.assertTrue(exportInvoicesPage.allInvoicesAreAbleToExport());
	}
}
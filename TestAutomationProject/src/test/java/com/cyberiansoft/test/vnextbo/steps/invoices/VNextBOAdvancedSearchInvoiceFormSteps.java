package com.cyberiansoft.test.vnextbo.steps.invoices;

import com.cyberiansoft.test.vnextbo.interactions.invoices.VNextBOAdvancedSearchInvoiceFormInteractions;
import com.cyberiansoft.test.vnextbo.interactions.invoices.VNextBOInvoicesPageInteractions;
import com.cyberiansoft.test.vnextbo.utils.WebConstants;
import com.cyberiansoft.test.vnextbo.verifications.invoices.VNextBOAdvancedSearchInvoiceFormValidations;

public class VNextBOAdvancedSearchInvoiceFormSteps {

    public static void searchByCustomTimeFrameWithFromDateAndStatus(String fromDate, String status) {
        final VNextBOAdvancedSearchInvoiceFormInteractions searchInvoiceFormInteractions = new VNextBOAdvancedSearchInvoiceFormInteractions();
        new VNextBOInvoicesPageInteractions().clickAdvancedSearchCaret();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsOpened();

        searchInvoiceFormInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM.getName());
        searchInvoiceFormInteractions.setFromDate(fromDate);
        searchInvoiceFormInteractions.setStatus(status);
        searchInvoiceFormInteractions.clickSearchButton();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsClosed();
    }

    public static void searchByCustomTimeFrameAndStatus(String fromDate, String toDate, String status) {
        final VNextBOAdvancedSearchInvoiceFormInteractions searchInvoiceFormInteractions = new VNextBOAdvancedSearchInvoiceFormInteractions();
        new VNextBOInvoicesPageInteractions().clickAdvancedSearchCaret();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsOpened();

        searchInvoiceFormInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM.getName());
        searchInvoiceFormInteractions.setFromDate(fromDate);
        searchInvoiceFormInteractions.setToDate(toDate);
        searchInvoiceFormInteractions.setStatus(status);
        searchInvoiceFormInteractions.clickSearchButton();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsClosed();
    }

    public static void searchByStatus(String status) {
        final VNextBOAdvancedSearchInvoiceFormInteractions searchInvoiceFormInteractions = new VNextBOAdvancedSearchInvoiceFormInteractions();
        new VNextBOInvoicesPageInteractions().clickAdvancedSearchCaret();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsOpened();
        searchInvoiceFormInteractions.setStatus(status);
        searchInvoiceFormInteractions.clickSearchButton();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsClosed();
    }

    public static void searchByInvoiceAndStatus(String invoice, String status) {
        final VNextBOAdvancedSearchInvoiceFormInteractions searchInvoiceFormInteractions = new VNextBOAdvancedSearchInvoiceFormInteractions();
        new VNextBOInvoicesPageInteractions().clickAdvancedSearchCaret();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsOpened();
        searchInvoiceFormInteractions.setInvoiceNumber(invoice);
        searchInvoiceFormInteractions.setStatus(status);
        searchInvoiceFormInteractions.clickSearchButton();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsClosed();
    }
}

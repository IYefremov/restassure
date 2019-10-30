package com.cyberiansoft.test.vnextbo.steps.invoices;

import com.cyberiansoft.test.vnextbo.interactions.invoices.VNextBOAdvancedSearchInvoiceFormInteractions;
import com.cyberiansoft.test.vnextbo.interactions.invoices.VNextBOInvoicesPageInteractions;
import com.cyberiansoft.test.vnextbo.utils.WebConstants;
import com.cyberiansoft.test.vnextbo.verifications.invoices.VNextBOAdvancedSearchInvoiceFormValidations;

public class VNextBOAdvancedSearchInvoiceFormSteps {

    public static void searchByCustomTimeFrameWithFromDateAndStatus(String fromDate, String status) {
        VNextBOInvoicesPageInteractions.clickAdvancedSearchCaret();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsOpened();

        VNextBOAdvancedSearchInvoiceFormInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM.getName());
        VNextBOAdvancedSearchInvoiceFormInteractions.setFromDate(fromDate);
        VNextBOAdvancedSearchInvoiceFormInteractions.setStatus(status);
        VNextBOAdvancedSearchInvoiceFormInteractions.clickSearchButton();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsClosed();
    }

    public static void searchByCustomTimeFrameAndStatus(String fromDate, String toDate, String status) {
        VNextBOInvoicesPageInteractions.clickAdvancedSearchCaret();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsOpened();

        VNextBOAdvancedSearchInvoiceFormInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM.getName());
        VNextBOAdvancedSearchInvoiceFormInteractions.setFromDate(fromDate);
        VNextBOAdvancedSearchInvoiceFormInteractions.setToDate(toDate);
        VNextBOAdvancedSearchInvoiceFormInteractions.setStatus(status);
        VNextBOAdvancedSearchInvoiceFormInteractions.clickSearchButton();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsClosed();
    }

    public static void searchByStatus(String status) {
        VNextBOInvoicesPageInteractions.clickAdvancedSearchCaret();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsOpened();
        VNextBOAdvancedSearchInvoiceFormInteractions.setStatus(status);
        VNextBOAdvancedSearchInvoiceFormInteractions.clickSearchButton();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsClosed();
    }

    public static void searchByInvoiceAndStatus(String invoice, String status) {
        VNextBOInvoicesPageInteractions.clickAdvancedSearchCaret();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsOpened();
        VNextBOAdvancedSearchInvoiceFormInteractions.setInvoiceNumber(invoice);
        VNextBOAdvancedSearchInvoiceFormInteractions.setStatus(status);
        VNextBOAdvancedSearchInvoiceFormInteractions.clickSearchButton();

        VNextBOAdvancedSearchInvoiceFormValidations.verifyAdvancedSearchDialogIsClosed();
    }
}

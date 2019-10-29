package com.cyberiansoft.test.vnextbo.interactions.invoices;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAdvancedSearchInvoiceForm;
import org.openqa.selenium.WebElement;

public class VNextBOAdvancedSearchInvoiceFormInteractions {

    private VNextBOAdvancedSearchInvoiceForm advancedSearchInvoiceForm;

    public VNextBOAdvancedSearchInvoiceFormInteractions() {
        advancedSearchInvoiceForm = new VNextBOAdvancedSearchInvoiceForm();
    }

    public void setInvoiceNumber(String invoice) {
        Utils.clearAndType(advancedSearchInvoiceForm.getInvoiceInputField(), invoice);
    }

    public void setStatus(String status) {
        Utils.clickElement(advancedSearchInvoiceForm.getStatusCombobox());
        Utils.selectOptionInDropDown(advancedSearchInvoiceForm.getStatusDropDown(),
                advancedSearchInvoiceForm.getStatusListBoxOptions(), status);
    }

    public void clickSearchButton() {
        Utils.clickElement(advancedSearchInvoiceForm.getSubmitButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public void setTimeFrame(String timeFrame) {
        Utils.clickElement(advancedSearchInvoiceForm.getTimeFrameListBox());
        Utils.selectOptionInDropDown(advancedSearchInvoiceForm.getTimeFrameDropDown(),
                advancedSearchInvoiceForm.getTimeFrameListBoxOptions(), timeFrame, true);
    }

    public void setFromDate(String date) {
        setDate(advancedSearchInvoiceForm.getFromDateField(), date);
    }

    public void setToDate(String date) {
        setDate(advancedSearchInvoiceForm.getToDateField(), date);
    }

    private void setDate(WebElement dateField, String date) {
        Utils.clickElement(dateField);
        Utils.clearAndType(dateField, date);
    }
}

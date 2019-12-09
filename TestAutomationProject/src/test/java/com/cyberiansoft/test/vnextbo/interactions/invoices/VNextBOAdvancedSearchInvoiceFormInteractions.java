package com.cyberiansoft.test.vnextbo.interactions.invoices;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAdvancedSearchInvoiceForm;
import org.openqa.selenium.WebElement;

public class VNextBOAdvancedSearchInvoiceFormInteractions {

    public static void setInvoiceNumber(String invoice) {
        Utils.clearAndType(new VNextBOAdvancedSearchInvoiceForm().getInvoiceInputField(), invoice);
    }

    public static void setStatus(String status) {
        Utils.clickElement(new VNextBOAdvancedSearchInvoiceForm().getStatusCombobox());
        Utils.selectOptionInDropDown(new VNextBOAdvancedSearchInvoiceForm().getStatusDropDown(),
                new VNextBOAdvancedSearchInvoiceForm().getStatusListBoxOptions(), status);
    }

    public static void clickSearchButton() {
        Utils.clickElement(new VNextBOAdvancedSearchInvoiceForm().getSubmitButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void setTimeFrame(String timeFrame) {
        Utils.clickElement(new VNextBOAdvancedSearchInvoiceForm().getTimeFrameListBox());
        Utils.selectOptionInDropDown(new VNextBOAdvancedSearchInvoiceForm().getTimeFrameDropDown(),
                new VNextBOAdvancedSearchInvoiceForm().getTimeFrameListBoxOptions(), timeFrame, true);
        WaitUtilsWebDriver.waitABit(2000);
    }

    public static void setFromDate(String date) {
        setDate(new VNextBOAdvancedSearchInvoiceForm().getFromDateField(), date);
    }

    public static void setToDate(String date) {
        setDate(new VNextBOAdvancedSearchInvoiceForm().getToDateField(), date);
    }

    private static void setDate(WebElement dateField, String date) {
        Utils.clickElement(dateField);
        Utils.clearAndType(dateField, date);
    }
}

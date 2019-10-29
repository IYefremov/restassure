package com.cyberiansoft.test.vnextbo.interactions.invoices;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInvoicesWebPage;
import com.cyberiansoft.test.vnextbo.verifications.invoices.VNextBOInvoicesPageValidations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class VNextBOInvoicesPageInteractions {

    private VNextBOInvoicesWebPage invoicesWebPage;

    public VNextBOInvoicesPageInteractions() {
        invoicesWebPage = new VNextBOInvoicesWebPage();
    }

    public void clickHeaderIconVoidButton() {
        Utils.clickElement(invoicesWebPage.getHeaderIconVoidButton());
    }

    public void clickHeaderIconUnvoidButton() {
        Utils.clickElement(invoicesWebPage.getHeaderIconUnvoidButton());
    }

    public void selectInvoiceInTheList(String invoice) {
        Utils.clickElement(invoicesWebPage.getInvoiceByName(invoice));
        WaitUtilsWebDriver.waitForLoading();
    }

    public void clickCheckbox(String... invoiceNames) {
        for (String invoiceName : invoiceNames) {
            Utils.clickElement(invoicesWebPage.getInvoiceByName(invoiceName).findElement(By.xpath(".//../../..//input")));
            WaitUtilsWebDriver.waitABit(1000);
        }
    }

    public String getSelectedInvoiceCustomerName() {
        return invoicesWebPage.getInvoiceDetailsPanel().findElement(By.xpath(".//h5[@data-bind='text: customer.clientName']")).getText();
    }

    public String getSelectedInvoiceNote() {
        return invoicesWebPage.getInvoiceDetailsPanel().findElement(By.xpath(".//p[@data-bind='text: note']")).getText();
    }

    public String getFirstInvoiceName() {
        return getInvoiceName(0);
    }

    public String getInvoiceName(int index) {
        return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(invoicesWebPage.getInvoiceNumbers()).get(index).getText();
    }

    public String[] getFirstInvoiceNames(int number) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(invoicesWebPage.getInvoiceNumbers());
        String[] invoices = new String[number];
        for (int i = 0; i < number; i++) {
            invoices[i] = invoicesWebPage.getInvoiceNumbers().get(i).getText();
        }
        return invoices;
    }

    public void clickFirstInvoice() {
        Utils.clickElement(WaitUtilsWebDriver.waitForVisibilityOfAllOptions(invoicesWebPage.getInvoiceNumbers()).get(0));
        WaitUtilsWebDriver.waitForLoading();
    }

    public void clickVoidButton() {
        Utils.clickElement(invoicesWebPage.getVoidButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public void clickUnvoidButton() {
        Utils.clickElement(invoicesWebPage.getUnvoidButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public void scrollInvoices() {
        while (true) {
            scrollDownToLastInvoice();
            WaitUtilsWebDriver.waitABit(1000);
            try {
                WaitUtilsWebDriver.waitForVisibility(invoicesWebPage.getProgressMessage(), 5);
                break;
            } catch (Exception ignored) {}
        }
    }

    private void scrollDownToLastInvoice() {
        final List<WebElement> invoicesList = invoicesWebPage.getInvoicesList();
        final WebElement invoice = WaitUtilsWebDriver.waitForVisibility(invoicesList.get(invoicesList.size() - 1));
        Utils.scrollToElement(invoice);
    }

    public String getCheckedItemsNote() {
        return WaitUtilsWebDriver.waitForVisibility(invoicesWebPage.getCheckedItemsNote()).getText();
    }

    public void clickAdvancedSearchCaret() {
        Utils.clickElement(invoicesWebPage.getAdvancedSearchCaret());
    }

    public void setLocation(String location) {
        if (VNextBOInvoicesPageValidations.isLocationExpanded()) {
            selectLocation(location);
        } else {
            Utils.clickElement(invoicesWebPage.getLocationElement());
            selectLocation(location);
        }
    }

    private void selectLocation(String location) {
        Utils.clickElement(invoicesWebPage.getLocationExpanded().findElement(By.xpath(".//label[text()='" + location + "']")));
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertTrue(VNextBOInvoicesPageValidations.isLocationSelected(location), "The location hasn't been selected");
        closeLocationDropDown();
    }

    public void closeLocationDropDown() {
        try {
            WaitUtilsWebDriver.waitForInvisibility(invoicesWebPage.getLocationExpanded());
        } catch (Exception e) {
            Utils.clickElement(invoicesWebPage.getLocationElement());
            WaitUtilsWebDriver.waitForInvisibility(invoicesWebPage.getLocationExpanded());
        }
    }

    public void clickClearSearchIconIfDisplayed() {
        if (VNextBOInvoicesPageValidations.isClearSearchIconDisplayed()) {
            Utils.clickElement(invoicesWebPage.getClearSearchIcon());
            WaitUtilsWebDriver.waitForLoading();
        }
    }
}

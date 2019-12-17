package com.cyberiansoft.test.vnextbo.interactions.invoices;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInvoicesWebPage;
import com.cyberiansoft.test.vnextbo.validations.invoices.VNextBOInvoicesPageValidations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class VNextBOInvoicesPageInteractions {

    public static void clickHeaderIconVoidButton() {
        Utils.clickElement(new VNextBOInvoicesWebPage().getHeaderIconVoidButton());
    }

    public static void clickHeaderIconUnvoidButton() {
        Utils.clickElement(new VNextBOInvoicesWebPage().getHeaderIconUnvoidButton());
    }

    public static void selectInvoiceInTheList(String invoice) {
        Utils.clickElement(new VNextBOInvoicesWebPage().getInvoiceByName(invoice));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickCheckbox(String... invoiceNames) {
        for (String invoiceName : invoiceNames) {
            Utils.clickElement(new VNextBOInvoicesWebPage().getInvoiceByName(invoiceName).findElement(By.xpath(".//../../..//input")));
            WaitUtilsWebDriver.waitABit(1000);
        }
    }

    public static String getSelectedInvoiceCustomerName() {
        return new VNextBOInvoicesWebPage().getInvoiceDetailsPanel().findElement(By.xpath(".//h5[@data-bind='text: customer.clientName']")).getText();
    }

    public static String getSelectedInvoiceNote() {
        return new VNextBOInvoicesWebPage().getInvoiceDetailsPanel().findElement(By.xpath(".//p[@data-bind='text: note']")).getText();
    }

    public static String getFirstInvoiceName() {
        return getInvoiceName(0);
    }

    public static String getInvoiceName(int index) {
        return WaitUtilsWebDriver.waitForVisibility(new VNextBOInvoicesWebPage().getInvoiceNumbers().get(index)).getText();
    }

    public static String getInvoiceName(int index, int timeOut) {
        return WaitUtilsWebDriver.waitForVisibility(
                new VNextBOInvoicesWebPage().getInvoiceNumbers().get(index), timeOut).getText();
    }

    public static String[] getFirstInvoicesNames(int number) {
        if (VNextBOInvoicesPageValidations.isMinimumNumberOfInvoicesDisplayed(number, 5)) {
            return new String[]{
                    VNextBOInvoicesPageInteractions.getInvoiceName(0, 1),
                    VNextBOInvoicesPageInteractions.getInvoiceName(1, 1),
                    VNextBOInvoicesPageInteractions.getInvoiceName(2, 1)
            };
        }
        return null;
    }

    public static String[] getFirstInvoiceNames(int number) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOInvoicesWebPage().getInvoiceNumbers());
        String[] invoices = new String[number];
        for (int i = 0; i < number; i++) {
            invoices[i] = new VNextBOInvoicesWebPage().getInvoiceNumbers().get(i).getText();
        }
        return invoices;
    }

    public static void clickFirstInvoice() {
        Utils.clickElement(WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOInvoicesWebPage().getInvoiceNumbers()).get(0));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickVoidButton() {
        Utils.clickElement(new VNextBOInvoicesWebPage().getVoidButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickUnvoidButton() {
        Utils.clickElement(new VNextBOInvoicesWebPage().getUnvoidButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void scrollInvoices() {
        while (true) {
            scrollDownToLastInvoice();
            WaitUtilsWebDriver.waitABit(1000);
            try {
                WaitUtilsWebDriver.waitForVisibility(new VNextBOInvoicesWebPage().getProgressMessage(), 5);
                break;
            } catch (Exception ignored) {}
        }
    }

    private static void scrollDownToLastInvoice() {
        final List<WebElement> invoicesList = new VNextBOInvoicesWebPage().getInvoicesList();
        final WebElement invoice = WaitUtilsWebDriver.waitForVisibility(invoicesList.get(invoicesList.size() - 1));
        Utils.scrollToElement(invoice);
    }

    public static String getCheckedItemsNote() {
        return WaitUtilsWebDriver.waitForVisibility(new VNextBOInvoicesWebPage().getCheckedItemsNote()).getText();
    }

    public static void clickAdvancedSearchCaret() {
        Utils.clickElement(new VNextBOInvoicesWebPage().getAdvancedSearchCaret());
    }

    public static void setLocation(String location) {
        if (VNextBOInvoicesPageValidations.isLocationExpanded()) {
            selectLocation(location);
        } else {
            Utils.clickElement(new VNextBOInvoicesWebPage().getLocationElement());
            selectLocation(location);
        }
    }

    private static void selectLocation(String location) {
        Utils.clickElement(new VNextBOInvoicesWebPage().getLocationExpanded().findElement(By.xpath(".//label[text()='" + location + "']")));
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertTrue(VNextBOInvoicesPageValidations.isLocationSelected(location), "The location hasn't been selected");
        closeLocationDropDown();
    }

    public static void closeLocationDropDown() {
        final VNextBOInvoicesWebPage invoicesWebPage = new VNextBOInvoicesWebPage();
        try {
            WaitUtilsWebDriver.waitForInvisibility(invoicesWebPage.getLocationExpanded());
        } catch (Exception e) {
            Utils.clickElement(invoicesWebPage.getLocationElement());
            WaitUtilsWebDriver.waitForInvisibility(invoicesWebPage.getLocationExpanded());
        }
    }

    public static void clickClearSearchIconIfDisplayed() {
        if (VNextBOInvoicesPageValidations.isClearSearchIconDisplayed()) {
            Utils.clickElement(new VNextBOInvoicesWebPage().getClearSearchIcon());
            WaitUtilsWebDriver.waitForLoading();
        }
    }

    public static void searchByText(String text) {
        Utils.sendKeysWithEnter(new VNextBOInvoicesWebPage().getSearchInputField(), text);
    }

    public static void clickApproveInvoiceButton() {
        Utils.clickElement(new VNextBOInvoicesWebPage().getApproveInvoiceButton());
    }

    public static void clickApproveInvoiceIcon() {
        Utils.clickElement(new VNextBOInvoicesWebPage().getApproveInvoiceIcon());
    }

    public static void clickRollbackApprovalButton() {
        Utils.clickElement(new VNextBOInvoicesWebPage().getRollbackApprovalButton());
    }

    public static void clickRollbackApprovalIcon() {
        Utils.clickElement(new VNextBOInvoicesWebPage().getRollbackApprovalIcon());
    }

    public static String getInvoiceStatusByName(String invoice) {
        return WaitUtilsWebDriver.waitForVisibility(new VNextBOInvoicesWebPage().getInvoiceStatusByName(invoice)).getText().trim();
    }
}

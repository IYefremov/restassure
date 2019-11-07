package com.cyberiansoft.test.vnextbo.validations.invoices;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInvoicesWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextBOInvoicesPageValidations {

    public static boolean areHeaderIconsDisplayed() {
        final VNextBOInvoicesWebPage invoicesWebPage = new VNextBOInvoicesWebPage();
        WaitUtilsWebDriver.waitForVisibility(invoicesWebPage.getHeaderIcons());
        return invoicesWebPage.getHeaderIconVoidButton().isDisplayed() &&
                invoicesWebPage.getHeaderIconApproveButton().isDisplayed() &&
                invoicesWebPage.getHeaderIconArchiveButton().isDisplayed();
    }

    public static boolean isInvoiceDisplayed(String invoice) {
        final List<WebElement> invoiceNumbers = new VNextBOInvoicesWebPage().getInvoiceNumbers();
        WaitUtilsWebDriver.waitABit(1000);
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(invoiceNumbers);
        return invoiceNumbers
                .stream()
                .anyMatch(n -> n.getText().equals(invoice));
    }

    public static boolean isLocationExpanded() {
        return Utils.isElementDisplayed(new VNextBOInvoicesWebPage().getLocationExpanded());
    }

    public static boolean isLocationSelected(String location) {
        return Utils.isElementDisplayed(By
                .xpath("//div[@class='add-notes-item menu-item active']//label[text()='" + location + "']"));
    }

    public static boolean isClearSearchIconDisplayed() {
        return Utils.isElementDisplayed(new VNextBOInvoicesWebPage().getClearSearchIcon(), 3);
    }

    public static boolean isRollbackApprovalButtonDisplayed() {
        return Utils.isElementDisplayed(new VNextBOInvoicesWebPage().getRollbackApprovalButton(), 10);
    }

    public static boolean isRollbackApprovalIconDisplayed() {
        return Utils.isElementDisplayed(new VNextBOInvoicesWebPage().getRollbackApprovalIcon(), 10);
    }

    public static boolean isApproveButtonDisplayed() {
        return Utils.isElementDisplayed(new VNextBOInvoicesWebPage().getApproveInvoiceButton(), 10);
    }

    public static boolean isApproveIconDisplayed() {
        return Utils.isElementDisplayed(new VNextBOInvoicesWebPage().getApproveInvoiceIcon(), 10);
    }
}

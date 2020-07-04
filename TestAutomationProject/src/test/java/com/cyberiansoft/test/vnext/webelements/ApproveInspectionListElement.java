package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ApproveInspectionListElement implements IWebElement {

    private WebElement rootElement;
    private String idFieldLocator = ".//*[@class='checkbox-item-title']";
    private String approveButtonLocator = ".//button[@value='1']";
    private String deleteButtonLocator = ".//button[@value='2']";
    private String declineButtonLocator = ".//button[@value='3']";
    private String approvedAmountField = ".//div[@class='entity-item-approved-amount']";
    private String approvedIcon = ".//button[contains(@class, 'status-button-group-approve')]";
    private String servicesStatusLocator = ".//div[contains(@class, 'checkbox-item-arrow-icon ')]";

    public ApproveInspectionListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getId() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(idFieldLocator)).getText());
    }

    public void clickOnInspection() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(idFieldLocator)), true);
        clickListElement(idFieldLocator);
    }

    public void clickDeclineButton() {
        clickListElement(declineButtonLocator);
    }

    public void clickApproveButton() {
        clickListElement(approveButtonLocator);
    }

    public void clickDeleteButton() {
        clickListElement(deleteButtonLocator);
    }

    public String getInspectionApprovedAmount() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(idFieldLocator)), true);
        return rootElement.findElement(By.xpath(approvedAmountField)).getText().trim();
    }

    public boolean isApproveIconForInspectionSelected() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(idFieldLocator)), true);
        return rootElement.findElement(By.xpath(approvedIcon)).getAttribute("class").contains("item-checked");
    }

    public boolean isInspectionServicesStatusesCanBeChanged() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(idFieldLocator)), true);
        return !rootElement.findElement(By.xpath(servicesStatusLocator)).getAttribute("class").contains("not-visible");
    }

    private void clickListElement(String elementLocator) {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(idFieldLocator)), true);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            rootElement.findElement(By.xpath(elementLocator)).click();
            return true;
        });
    }


}

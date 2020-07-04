package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ApproveServicesListElement implements IWebElement {

    private WebElement rootElement;
    private String nameFieldLocator = ".//div[@class='item-title']";
    private String declineButton = ".//label[contains(@for, 'decline')]";
    private String skipButton = ".//label[contains(@for, 'skip')]";
    private String approveButton = ".//label[contains(@for, 'approve')]";
    private String servicePriceElement = ".//*[@class='entity-item-amount']";

    public ApproveServicesListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getName() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(nameFieldLocator)).getText().trim());
    }

    public String getServicePrice() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(servicePriceElement)).getText().trim());
    }

    public void clickApproveButton() {
        clickServiceButton(approveButton);
    }

    public void clickDeclineButton() {
        clickServiceButton(declineButton);
    }

    public void clickSkipButton() {
        clickServiceButton(skipButton);
    }

    private void clickServiceButton(String elementLocator) {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(nameFieldLocator)), true);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            rootElement.findElement(By.xpath(elementLocator)).click();
            return true;
        });
    }

}

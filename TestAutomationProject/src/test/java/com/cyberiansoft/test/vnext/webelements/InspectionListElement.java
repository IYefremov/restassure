package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class InspectionListElement implements IWebElement {
    private WebElement rootElement;
    private String idFieldLocator = ".//div[@class='checkbox-item-content-main']/div[1]";

    public InspectionListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getId() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(idFieldLocator)).getText());
    }

    public void openMenu() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(idFieldLocator)), true);
        WaitUtils.waitUntilElementIsClickable(rootElement.findElement(By.xpath(idFieldLocator)));
        WaitUtils.getGeneralFluentWait().until(driver -> {
            rootElement.findElement(By.xpath(idFieldLocator)).click();
            return true;
        });
    }
}

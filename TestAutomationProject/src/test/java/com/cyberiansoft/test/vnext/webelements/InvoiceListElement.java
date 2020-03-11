package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter
public class InvoiceListElement implements IWebElement {
    private WebElement rootElement;
    private String idFieldLocator = ".//div[@class='checkbox-item-content-main']/div[1]";
    private String selectFieldLocator = ".//*[@action='check-item']";

    public InvoiceListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getId() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(idFieldLocator)).getText());
    }

    public void openMenu() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(idFieldLocator)), true);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            rootElement.findElement(By.xpath(idFieldLocator)).click();
            return true;
        });
    }

    public void select() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(idFieldLocator)), true);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            rootElement.findElement(By.xpath(selectFieldLocator)).click();
            return true;
        });
    }
}

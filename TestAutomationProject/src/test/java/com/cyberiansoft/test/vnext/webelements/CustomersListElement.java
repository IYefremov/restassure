package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter
public class CustomersListElement implements IWebElement {
    private WebElement rootElement;
    private String fullNameieldLocator = ".//p[@class='list-item-text list-item-name']";

    public CustomersListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getCustomerFullName() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(fullNameieldLocator)).getText().trim());
    }

    public void selectCustomer() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(fullNameieldLocator)), true);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            rootElement.findElement(By.xpath(fullNameieldLocator)).click();
            return true;
        });
    }
}

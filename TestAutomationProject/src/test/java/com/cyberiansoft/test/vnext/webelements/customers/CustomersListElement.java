package com.cyberiansoft.test.vnext.webelements.customers;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter
public class CustomersListElement implements IWebElement {
    private WebElement rootElement;
    private String fullNameFieldLocator = ".//p[@class='list-item-text list-item-name']";

    public CustomersListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getCustomerFullName() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(fullNameFieldLocator)).getText().trim());
    }

    public void selectCustomer() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(fullNameFieldLocator)), true);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            rootElement.findElement(By.xpath(fullNameFieldLocator)).click();
            return true;
        });
    }
}

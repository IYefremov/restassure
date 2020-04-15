package com.cyberiansoft.test.vnext.webelements.customers;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter
public class CustomerContactListElement implements IWebElement {
    private WebElement rootElement;
    private String contactNameXPath = ".//p[@class='list-item-text list-item-name']";

    public CustomerContactListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getContactName() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(contactNameXPath)).getText().trim());
    }
}

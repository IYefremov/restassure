package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ServiceListItem implements IWebElement {
    private WebElement rootElement;
    private String addButtonLocator = ".//input[@action='select-item']";
    private String priceLocator = ".//div[contains(@class,'checkbox-item-subtitle checkbox-item-price')]";
    private String serviceNameLocator = ".//div[@class='checkbox-item-title']";

    public ServiceListItem(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void addService() {
        WaitUtils.click(rootElement.findElement(By.xpath(addButtonLocator)));
    }

    public String getServiceName() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(serviceNameLocator)).getText());
    }
}

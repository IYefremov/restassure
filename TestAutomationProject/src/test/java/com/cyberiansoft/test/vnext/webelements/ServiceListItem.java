package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ServiceListItem implements IWebElement {
    private WebElement rootElement;
    private String priceLocator = ".//div[contains(@class,'checkbox-item-subtitle checkbox-item-price')]";
    private String serviceNameLocator = ".//div[@class='checkbox-item-title']";
    private String serviceDescriptionLocator = ".//div[@class='checkbox-item-description']";
    private String servicePartInfoLocator = ".//div[@class='part-info-desc-name']";
    private String addServiceLocator = ".//*[@action='add-service']";

    public ServiceListItem(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void addService() {
        WaitUtils.click(rootElement);
    }

    public String getServiceName() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(serviceNameLocator)).getText().trim());
    }

    public String getServiceDescription() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(serviceDescriptionLocator)).getText());
    }

    public String getServicePrice() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(priceLocator)).getText().trim());
    }

    public String getServicePartInfo() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(servicePartInfoLocator)).getText().trim());
    }

    public int getNumberOfAddedServices() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> Integer.parseInt(rootElement.findElement(By.xpath(addServiceLocator)).getAttribute("data-counter")));
    }

    public void clickAddService() {
        rootElement.findElement(By.xpath(addServiceLocator)).click();
    }

    public void openServiceDetails() {
        rootElement.findElement(By.xpath(serviceNameLocator)).click();
    }

}


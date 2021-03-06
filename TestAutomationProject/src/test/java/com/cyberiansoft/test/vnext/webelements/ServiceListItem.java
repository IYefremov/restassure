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
    private String servicePartInfoLocator = ".//div[@class='checkbox-item-description']";
    private String addServiceLocator = ".//input[contains(@class, 'plus-checkbox')]";
    private String unSelectServiceLocator = ".//*[@action='delete-service']";
    private String matrixServiceLocator = ".//div[@class='checkbox-item-subtitle checkbox-item-price']";

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
        return rootElement.findElement(By.xpath(servicePartInfoLocator)).getText().trim();
    }

    public String getNumberOfAddedServices() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(addServiceLocator)).getAttribute("data-counter"));
    }

    public void clickAddService() {
        rootElement.findElement(By.xpath(addServiceLocator)).click();
    }

    public void clickDeleteService() {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            rootElement.findElement(By.xpath(unSelectServiceLocator)).click();
            return true;
        });
    }

    public void openServiceDetails() {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            rootElement.findElement(By.xpath(serviceNameLocator)).click();
            return true;
        });
    }

    public String getMatrixServiceValue() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(matrixServiceLocator)).getText().trim());
    }

}


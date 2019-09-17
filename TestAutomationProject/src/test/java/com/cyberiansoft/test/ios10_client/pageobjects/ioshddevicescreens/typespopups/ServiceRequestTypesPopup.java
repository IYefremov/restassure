package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServiceRequestTypesPopup extends BaseTypePopup {

    final String typeIdentificatorString = "ServiceRequestTypeSelector";

    public ServiceRequestTypesPopup() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);

    }

    public void selectServiceRequestType(String serviceRequestType) {
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(typeIdentificatorString)));
        selectType(typeIdentificatorString, serviceRequestType);
    }
}

package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class ServiceRequestTypesPopup extends BaseTypePopup {

    final String typeIdentificatorString = "ServiceRequestTypeSelector";

    public ServiceRequestTypesPopup(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(typeIdentificatorString)));
    }

    public void selectServiceRequestType(String serviceRequestType) {
        selectType(typeIdentificatorString, serviceRequestType);
    }
}

package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSHDBaseScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class ServiceRequestTypesPopup extends iOSHDBaseScreen {

    public ServiceRequestTypesPopup(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("ServiceRequestTypeSelector")));
    }

    public void selectServiceRequestType(String serviceRequestType) {
        IOSElement srtypetable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'ServiceRequestTypeSelector' and type = 'XCUIElementTypeTable'"));
        if (!srtypetable.findElementByAccessibilityId(serviceRequestType).isDisplayed()) {
            swipeTableUp(srtypetable.findElementByAccessibilityId(serviceRequestType),
                    srtypetable);
        }
        appiumdriver.findElementByAccessibilityId(serviceRequestType).click();
    }
}

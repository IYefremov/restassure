package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSHDBaseScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class WorkOrderTypesPopup extends iOSHDBaseScreen {

    public WorkOrderTypesPopup(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("OrderTypeSelector")));
    }

    public void selectWorkOrderType(String workOrderType) {
        IOSElement wostable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'"));
        if (!wostable.findElementByAccessibilityId(workOrderType).isDisplayed()) {
            swipeTableUp(wostable.findElementByAccessibilityId(workOrderType),
                        wostable);
        }
        appiumdriver.findElementByAccessibilityId(workOrderType).click();
    }
}

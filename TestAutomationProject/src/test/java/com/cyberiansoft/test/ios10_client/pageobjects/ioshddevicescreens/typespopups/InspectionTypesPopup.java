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

public class InspectionTypesPopup extends iOSHDBaseScreen {

    public InspectionTypesPopup(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("InspectionTypeSelector")));
    }

    public void selectInspectionType(String inspectiontype) {
        IOSElement inptypetable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'InspectionTypeSelector' and type = 'XCUIElementTypeTable'"));
        if (!inptypetable.findElementByAccessibilityId(inspectiontype).isDisplayed()) {
            swipeTableUp(inptypetable.findElementByAccessibilityId(inspectiontype),
                    inptypetable);
        }
        appiumdriver.findElementByAccessibilityId(inspectiontype).click();
    }
}

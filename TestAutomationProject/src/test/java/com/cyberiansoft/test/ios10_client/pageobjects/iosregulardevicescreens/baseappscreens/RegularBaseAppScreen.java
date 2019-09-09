package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.iOSRegularBaseScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularBaseAppScreen extends iOSRegularBaseScreen {

    @iOSXCUITFindBy(accessibility = "Done")
    private IOSElement donebtn;

    public RegularBaseAppScreen() {
        super();

    }

    public RegularHomeScreen clickHomeButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
        wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Back"))).click();
        return new RegularHomeScreen();
    }

    public void clickPickerWheelDoneButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        WebElement doneBtn = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Done")));
        doneBtn.click();
    }
}

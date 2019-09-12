package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegularApproveInvoicesScreen extends iOSRegularBaseScreen {

    @iOSXCUITFindBy(accessibility ="Approve")
    private IOSElement approvebtn;

    @iOSXCUITFindBy(accessibility ="Agree")
    private IOSElement agreebtn;

    @iOSXCUITFindBy(accessibility ="Done")
    private IOSElement donebtn;

    public RegularApproveInvoicesScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void clickApproveButton() {
        approvebtn.click();
    }

    public void selectInvoice(String invoiceNumber) {
        WaitUtils.elementShouldBeVisible(appiumdriver.findElementByAccessibilityId("Approve Invoices"), true);
        appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.AccessibilityId(invoiceNumber)).click();
    }

    public void clickAgreeApproveDisclimer() {
        agreebtn.click();
    }

    public void clickDoneButton() {
        donebtn.click();
    }

    public void drawApprovalSignature() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("TouchInputView")));
        MobileElement element = (MobileElement) appiumdriver.findElementByAccessibilityId("TouchInputView");
        int xx = element.getLocation().getX();

        int yy = element.getLocation().getY();
        TouchAction action = new TouchAction(appiumdriver);
        action.press(PointOption.point(xx + 100,yy + 100)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).
                moveTo(PointOption.point(xx + 200, yy + 200)).release().perform();
        clickDoneButton();
    }
}

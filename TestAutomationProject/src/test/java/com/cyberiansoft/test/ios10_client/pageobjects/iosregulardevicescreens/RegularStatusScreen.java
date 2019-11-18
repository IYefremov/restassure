package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class RegularStatusScreen extends iOSRegularBaseScreen {

    public RegularStatusScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void clickResendData() {
        appiumdriver.findElementByAccessibilityId("Resend Data").click();
    }

    public void selectInspectionsToResendData() {
        appiumdriver.findElementByAccessibilityId("Inspections").click();
    }

    public void selectWorkOrdersToResendData() {
        appiumdriver.findElementByAccessibilityId("Work Orders").click();
    }

    public void clickDoneButton() {
        appiumdriver.findElementByAccessibilityId("Done").click();
    }
}

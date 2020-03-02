package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularBaseServicesScreen extends RegularBaseWizardScreen {

    @iOSXCUITFindBy(accessibility = "Available")
    private IOSElement availabletab;

    public RegularBaseServicesScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void switchToAvailableServicesTab() {
        availabletab.click();
    }

    public void switchToSelectedServicesTab() {
        appiumdriver.findElement(MobileBy.iOSNsPredicateString("type='XCUIElementTypeButton' and name CONTAINS 'Selected'")).click();
    }
}

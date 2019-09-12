package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularBaseTypeScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

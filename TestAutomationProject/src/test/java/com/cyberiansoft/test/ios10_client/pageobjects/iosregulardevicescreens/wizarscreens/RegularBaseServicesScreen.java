package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularBaseTypeScreen;
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

    @iOSXCUITFindBy(accessibility = "Selected")
    private IOSElement selectedtab;

    public RegularBaseServicesScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public RegularServicesScreen switchToAvailableServicesTab() {
        availabletab.click();
        return new RegularServicesScreen();
    }

    public RegularSelectedServicesScreen switchToSelectedServicesTab() {
        appiumdriver.findElementByXPath("//XCUIElementTypeButton[contains(@name, 'Selected')]").click();
        return new RegularSelectedServicesScreen();
    }

    public void clickSaveAsFinal() {
        clickSave();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Final")));
        appiumdriver.findElement(MobileBy.AccessibilityId("Final")).click();
    }

    public <T extends RegularBaseTypeScreen> T saveAsFinal() {
        clickSaveAsFinal();
        return getTypeScreenFromContext();
    }

    public <T extends RegularBaseTypeScreen> T saveAsDraft() {
        clickSaveAsDraft();
        return getTypeScreenFromContext();
    }

    public void clickSaveAsDraft() {
        clickSave();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Draft")));
        appiumdriver.findElement(MobileBy.AccessibilityId("Draft")).click();
    }
}

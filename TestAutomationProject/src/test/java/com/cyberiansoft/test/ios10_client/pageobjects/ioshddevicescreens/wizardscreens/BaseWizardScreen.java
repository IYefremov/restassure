package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSHDBaseScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseWizardScreen extends iOSHDBaseScreen implements IBaseWizardScreen {

    public BaseWizardScreen() {
        super();
    }

    public <T extends IBaseWizardScreen> T selectNextScreen(WizardScreenTypes wizardScreenType) {
        IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
        navbar.findElementByIosNsPredicate("label CONTAINS '/'").click();
        appiumdriver.findElementByAccessibilityId(wizardScreenType.getDefaultScreenTypeName()).click();
        return (T) WizardScreensFactory.getWizardScreenType(wizardScreenType);
    }

    public <T extends IBaseWizardScreen> T selectNextScreen(WizardScreenTypes wizardScreenType, String screenName) {
        WaitUtils.waitUntilElementIsClickable(appiumdriver.findElement(MobileBy.AccessibilityId("WizardStepsButton"))).click();
        appiumdriver.findElementByAccessibilityId(screenName).click();
        return (T) WizardScreensFactory.getWizardScreenType(wizardScreenType);
    }

    public void clickSave() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Save")));
        appiumdriver.findElementByAccessibilityId("Save").click();
    }

    public void clickFinalPopup() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Final")));
        appiumdriver.findElementByAccessibilityId("Final").click();
    }

    public void clickSaveAsFinal() {
        clickSave();
        clickFinalPopup();
    }

    public void saveWizard() {
        clickSave();
    }

    public void cancelWizard() {
        clickCancelButton();
        acceptAlert();
    }

    public void waitForCustomWarningMessage(String warningMessage, String buttonToClick) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(warningMessage)));
        appiumdriver.findElementByAccessibilityId(buttonToClick).click();
    }

}


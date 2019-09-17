package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSHDBaseScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseWizardScreen extends iOSHDBaseScreen implements IBaseWizardScreen {

    public BaseWizardScreen() {
        super();
    }

    public void selectNextScreen(WizardScreenTypes wizardScreenType) {
        IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
        navbar.findElementByIosNsPredicate("label CONTAINS '/'").click();
        appiumdriver.findElementByAccessibilityId(wizardScreenType.getDefaultScreenTypeName()).click();
    }

    public void selectNextScreen(String screenName) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("WizardStepsButton"))).click();
        appiumdriver.findElementByAccessibilityId(screenName).click();
    }

    public void waitScreenLoaded(String screenName) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'viewPrompt' and label = '" + screenName+ "'")));
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


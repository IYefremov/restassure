package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.iOSRegularBaseScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularBaseWizardScreen extends iOSRegularBaseScreen implements IBaseWizardScreen {

    public RegularBaseWizardScreen() {
        super();
    }

    public  void clickFinalButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Final"))).click();
    }

    public void waitScreenLoaded(String screenName) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'viewPrompt' and label = '" + screenName+ "'")));
    }

    public  void clickDraftButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Draft"))).click();
    }

    public void selectNextScreen(WizardScreenTypes wizardScreenType) {
        selectNextScreen(wizardScreenType.getDefaultScreenTypeName());
    }

    public void selectNextScreen(String screenName) {
        clickChangeScreen();
        appiumdriver.findElementByAccessibilityId(screenName).click();
    }

    public void clickSave() {
        if (!elementExists("Save"))
            clickChangeScreen();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Save")));
        appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
    }

    public void clickChangeScreen() {
        WaitUtils.waitUntilElementIsClickable(appiumdriver.findElement(MobileBy.AccessibilityId("WizardStepsButton"))).click();
    }

    public void clickCancelWizard() {
        clickChangeScreen();
        appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
    }

    public IOSElement getInspectionNumberLabel() {
        IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
        return (IOSElement) toolbar.findElementByIosNsPredicate("name CONTAINS 'E-'");
    }

    public String getInspectionNumber() {
        return getInspectionNumberLabel().getText();
    }

    public void waitForCustomWarningMessage(String warningMessage, String buttonToClick) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(warningMessage)));
        appiumdriver.findElement(MobileBy.iOSNsPredicateString("name == '" + buttonToClick + "' AND visible == 1")).click();
    }

    public void clickNotesButton() {
        appiumdriver.findElement(MobileBy.AccessibilityId("Compose")).click();
    }
}

package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.iOSRegularBaseScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.ITypeScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularBaseWizardScreen extends iOSRegularBaseScreen implements IBaseWizardScreen {

    public static TypeScreenContext typeContext;

    public RegularBaseWizardScreen() {
        super();
    }

    public <T extends IBaseWizardScreen> T selectNextScreen(WizardScreenTypes wizardScreenType) {
        clickChangeScreen();
        appiumdriver.findElementByAccessibilityId(wizardScreenType.getDefaultScreenTypeName()).click();
        return (T) RegularWizardScreensFactory.getWizardScreenType(wizardScreenType);
    }

    public <T extends IBaseWizardScreen> T selectNextScreen(WizardScreenTypes wizardScreenType, String screenName) {
        clickChangeScreen();
        appiumdriver.findElementByAccessibilityId(screenName).click();
        return (T) RegularWizardScreensFactory.getWizardScreenType(wizardScreenType);
    }

    public void clickSave() {
        if (!elementExists("Save"))
            clickChangeScreen();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Save")));
        appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
    }

    public void clickChangeScreen() {
        if (elementExists("WizardStepsButton"))
            appiumdriver.findElementByAccessibilityId("WizardStepsButton").click();
        else
            //appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[2]").click();
            appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").findElement(MobileBy.iOSNsPredicateString("name contains '/'")).click();
    }

    public <T extends ITypeScreen> T saveWizard() {
        clickSave();
        return getTypeScreenFromContext();
    }

    public void clickCancelWizard() {
        clickChangeScreen();
        appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
    }

    public <T extends ITypeScreen> T cancelWizard() {
        clickCancelWizard();
        acceptAlert();
        return getTypeScreenFromContext();
    }

    public <T extends ITypeScreen> T getTypeScreenFromContext()  {
        return (T) RegularTypesScreenFactory.getTypeScreen(typeContext);
    }

    public IOSElement getInspectionNumberLabel() {
        IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
        return (IOSElement) toolbar.findElementByIosNsPredicate("name CONTAINS 'E-'");
    }

    public String getInspectionNumber() {
        return getInspectionNumberLabel().getText();
    }
}

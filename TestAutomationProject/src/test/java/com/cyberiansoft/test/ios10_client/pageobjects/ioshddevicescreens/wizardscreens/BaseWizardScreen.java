package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSHDBaseScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.ITypeScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseWizardScreen extends iOSHDBaseScreen implements IBaseWizardScreen {

    public static TypeScreenContext typeContext;

    public BaseWizardScreen() {
        super();
    }

    public <T extends IBaseWizardScreen> T selectNextScreen(WizardScreenTypes wizardScreenType) {
        IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
        navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'").click();
        appiumdriver.findElementByAccessibilityId(wizardScreenType.getDefaultScreenTypeName()).click();
        return (T) WizardScreensFactory.getWizardScreenType(wizardScreenType);
    }

    public <T extends IBaseWizardScreen> T selectNextScreen(WizardScreenTypes wizardScreenType, String screenName) {
        IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'"))).click();
        //navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'").click();
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

    public <T extends BaseTypeScreen> T clickSaveAsFinal() {
        clickSave();
        clickFinalPopup();
//        clickSave();
        return getTypeScreenFromContext();
    }

    public <T extends ITypeScreen> T saveWizard() {
        clickSave();
        return getTypeScreenFromContext();
    }

    public <T extends ITypeScreen> T cancelWizard() {
        clickCancelButton();
        acceptAlert();
        return getTypeScreenFromContext();
    }

    public <T extends ITypeScreen> T getTypeScreenFromContext() {
        return (T) TypesScreenFactory.getTypeScreen(typeContext);
    }

    public void waitForCustomWarningMessage(String warningMessage, String buttonToClick) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(warningMessage)));
        appiumdriver.findElementByAccessibilityId(buttonToClick).click();
    }

}


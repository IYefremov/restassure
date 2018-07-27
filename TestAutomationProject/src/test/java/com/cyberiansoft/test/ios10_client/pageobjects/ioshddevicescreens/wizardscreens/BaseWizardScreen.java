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

    /*public <T extends IBaseWizardScreen> T selectNextScreen(String screenname, Class<T> type) {
        IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
        navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'").click();
        appiumdriver.findElementByAccessibilityId(screenname).click();

        if (type == InvoiceInfoScreen.class)
            return (T) new InvoiceInfoScreen();
        else if (type == VehicleScreen.class)
            return (T) new VehicleScreen();
        else if (type == ServicesScreen.class)
            return (T) new ServicesScreen();
        else if (type == OrderSummaryScreen.class)
            return (T) new OrderSummaryScreen();
        else if (type == ClaimScreen.class)
            return (T) new ClaimScreen();
        else if (type == QuestionsScreen.class)
            return (T) new QuestionsScreen();
        else if (type == QuestionAnswerScreen.class)
            return (T) new QuestionAnswerScreen();
        else if (type == EnterpriseBeforeDamageScreen.class)
            return (T) new EnterpriseBeforeDamageScreen();
        else if (type == PriceMatrixScreen.class)
            return (T) new PriceMatrixScreen();
        else if (type == VisualInteriorScreen.class)
            return (T) new VisualInteriorScreen();
        return null;
    }*/

    public <T extends IBaseWizardScreen> T selectNextScreen(WizardScreenTypes wizardScreenType) {
        IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
        navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'").click();
        appiumdriver.findElementByAccessibilityId(wizardScreenType.getDefaultScreenTypeName()).click();
        return (T) WizardScreensFactory.getWizardScreenType(wizardScreenType);
    }

    public <T extends IBaseWizardScreen> T selectNextScreen(WizardScreenTypes wizardScreenType, String screenName) {
        IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
        navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'").click();
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

}


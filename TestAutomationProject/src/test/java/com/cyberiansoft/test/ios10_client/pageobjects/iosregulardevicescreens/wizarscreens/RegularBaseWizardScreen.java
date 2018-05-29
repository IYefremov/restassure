package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.iOSRegularBaseScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

public class RegularBaseWizardScreen extends iOSRegularBaseScreen {

    public static TypeScreenContext typeContext;

    public RegularBaseWizardScreen(AppiumDriver driver) {
        super(driver);
    }

    public <T extends RegularBaseWizardScreen> T selectNextScreen(String screenname, Class<T> type) {
        clickChangeScreen();
        appiumdriver.findElementByAccessibilityId(screenname).click();
        if (type == RegularInvoiceInfoScreen.class)
            return (T) new RegularInvoiceInfoScreen(appiumdriver);
        else if (type == RegularVehicleScreen.class)
            return (T) new RegularVehicleScreen(appiumdriver);
        else if (type == RegularServicesScreen.class)
            return (T) new RegularServicesScreen(appiumdriver);
        else if (type == RegularOrderSummaryScreen.class)
            return (T) new RegularOrderSummaryScreen(appiumdriver);
        else if (type == RegularClaimScreen.class)
            return (T) new RegularClaimScreen(appiumdriver);
        else if (type == RegularQuestionsScreen.class)
            return (T) new RegularQuestionsScreen(appiumdriver);
        else if (type == RegularEnterpriseBeforeDamageScreen.class)
            return (T) new RegularEnterpriseBeforeDamageScreen(appiumdriver);
        else if (type == RegularPriceMatrixScreen.class)
            return (T) new RegularPriceMatrixScreen(appiumdriver);
        else if (type == RegularVisualInteriorScreen.class)
            return (T) new RegularVisualInteriorScreen(appiumdriver);
        return null;
    }

    public void clickSave() {
        if (!elementExists("Save"))
            clickChangeScreen();
        appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
    }

    public void clickChangeScreen() {
        if (elementExists("WizardStepsButton"))
            appiumdriver.findElementByAccessibilityId("WizardStepsButton").click();
        else
            //appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[2]").click();
            appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").findElement(MobileBy.iOSNsPredicateString("name contains '/'")).click();
    }

    public <T extends IRegularTypeScreen> T saveWizard() {
        clickSave();
        return getTypeScreenFromContext();
    }

    public void clickCancelWizard() {
        clickChangeScreen();
        appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
    }

    public <T extends IRegularTypeScreen> T cancelWizard() {
        clickCancelWizard();
        acceptAlert();
        return getTypeScreenFromContext();
    }

    public <T extends IRegularTypeScreen> T getTypeScreenFromContext()  {
        switch (typeContext) {
            case WORKORDER:
                return (T) new RegularMyWorkOrdersScreen(appiumdriver);
            case INSPECTION:
                return (T) new RegularMyInspectionsScreen(appiumdriver);
            case INVOICE:
                return (T) new RegularMyInvoicesScreen(appiumdriver);
            case SERVICEREQUEST:
                return (T) new RegularServiceRequestsScreen(appiumdriver);
            case TEAMWORKORDER:
                return (T) new RegularTeamWorkOrdersScreen(appiumdriver);
            case TEAMINSPECTION:
                return (T) new RegularTeamInspectionsScreen(appiumdriver);
            case INVOICEINFO:
                return (T) new RegularInvoiceInfoScreen(appiumdriver);
        }
        return null;
    }
}

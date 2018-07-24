package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.iOSRegularBaseScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.ITypeScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularBaseWizardScreen extends iOSRegularBaseScreen implements IBaseWizardScreen {

    public static TypeScreenContext typeContext;

    public RegularBaseWizardScreen() {
        super();
    }

    public <T extends IBaseWizardScreen> T selectNextScreen(String screenname, Class<T> type) {
        clickChangeScreen();
        appiumdriver.findElementByAccessibilityId(screenname).click();
        if (type == RegularInvoiceInfoScreen.class)
            return (T) new RegularInvoiceInfoScreen();
        else if (type == RegularVehicleScreen.class)
            return (T) new RegularVehicleScreen();
        else if (type == RegularServicesScreen.class)
            return (T) new RegularServicesScreen();
        else if (type == RegularOrderSummaryScreen.class)
            return (T) new RegularOrderSummaryScreen();
        else if (type == RegularClaimScreen.class)
            return (T) new RegularClaimScreen();
        else if (type == RegularQuestionsScreen.class)
            return (T) new RegularQuestionsScreen();
        else if (type == RegularEnterpriseBeforeDamageScreen.class)
            return (T) new RegularEnterpriseBeforeDamageScreen();
        else if (type == RegularPriceMatrixScreen.class)
            return (T) new RegularPriceMatrixScreen();
        else if (type == RegularVisualInteriorScreen.class)
            return (T) new RegularVisualInteriorScreen();
        return null;
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

    public <T extends ITypeScreen> T getTypeScreenFromContext()  {
        switch (typeContext) {
            case WORKORDER:
                return (T) new RegularMyWorkOrdersScreen();
            case INSPECTION:
                return (T) new RegularMyInspectionsScreen();
            case INVOICE:
                return (T) new RegularMyInvoicesScreen();
            case SERVICEREQUEST:
                return (T) new RegularServiceRequestsScreen();
            case TEAMWORKORDER:
                return (T) new RegularTeamWorkOrdersScreen();
            case TEAMINSPECTION:
                return (T) new RegularTeamInspectionsScreen();
            case INVOICEINFO:
                return (T) new RegularInvoiceInfoScreen();
        }
        return null;
    }

    public IOSElement getInspectionNumberLabel() {
        IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
        return (IOSElement) toolbar.findElementByIosNsPredicate("name CONTAINS 'E-'");
        //return regularinspnumberlabel;
    }

    public String getInspectionNumber() {
        return getInspectionNumberLabel().getText();
    }
}

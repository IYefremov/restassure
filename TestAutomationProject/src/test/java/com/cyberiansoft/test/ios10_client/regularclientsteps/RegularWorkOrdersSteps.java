package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularWorkOrdersSteps {

    public static void saveWorkOrder() {
        RegularWizardScreensSteps.clickSaveButton();
        RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
    }

    public static void cancelCreatingWorkOrder() {
        RegularWizardScreensSteps.cancelWizard();
        RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
    }

    public static void cancelWorkOrder() {
        RegularWizardScreensSteps.cancelWizard();
        RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
    }

}

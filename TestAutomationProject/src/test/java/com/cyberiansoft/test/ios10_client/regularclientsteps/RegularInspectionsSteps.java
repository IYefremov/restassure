package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import org.openqa.selenium.Alert;

public class RegularInspectionsSteps {

    public static void saveInspection() {
        RegularWizardScreensSteps.clickSaveButton();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
    }

    public static void cancelCreatingInspection() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.clickCancelWizard();
        Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
        alert.accept();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
    }


    public static void saveInspectionAsFinal() {
        RegularWizardScreensSteps.clickSaveButton();
        RegularWizardScreensSteps.clickFinalButton();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
    }
}

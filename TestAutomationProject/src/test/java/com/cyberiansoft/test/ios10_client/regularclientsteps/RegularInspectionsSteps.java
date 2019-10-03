package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.Alert;

public class RegularInspectionsSteps {

    public static void saveInspection() {
        RegularWizardScreensSteps.clickSaveButton();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
    }

    public static void cancelCreatingInspection() {
        RegularWizardScreensSteps.cancelWizard();
        Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
        alert.accept();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
    }


    public static void saveInspectionAsFinal() {
        RegularWizardScreensSteps.clickSaveButton();
        RegularWizardScreensSteps.clickFinalButton();
    }

    public static void saveInspectionAsDraft() {
        RegularWizardScreensSteps.clickSaveButton();
        RegularWizardScreensSteps.clickDraftButton();
    }
}

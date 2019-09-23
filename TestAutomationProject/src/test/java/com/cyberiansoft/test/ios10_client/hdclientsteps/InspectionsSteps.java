package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;

public class InspectionsSteps {

    public static void saveInspection() {
        WizardScreensSteps.clickSaveButton();
        MyInspectionsSteps.waitMyInspectionsScreenLoaded();
    }

    public static void cancelCreatingInspection() {
        WizardScreensSteps.cancelWizard();
        MyInspectionsSteps.waitMyInspectionsScreenLoaded();
    }


    public static void saveInspectionAsFinal() {
        WizardScreensSteps.clickSaveButton();
        WizardScreensSteps.clickFinalButton();
    }

    public static void saveInspectionAsDraft() {
        WizardScreensSteps.clickSaveButton();
        WizardScreensSteps.clickDraftButton();
    }
}

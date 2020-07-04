package com.cyberiansoft.test.ios10_client.hdclientsteps;

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

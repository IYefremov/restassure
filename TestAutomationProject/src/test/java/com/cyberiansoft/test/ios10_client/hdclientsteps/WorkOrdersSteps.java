package com.cyberiansoft.test.ios10_client.hdclientsteps;

public class WorkOrdersSteps {

    public static void saveWorkOrder() {
        WizardScreensSteps.clickSaveButton();
    }

    public static void cancelCreatingWorkOrder() {
        WizardScreensSteps.cancelWizard();
        MyWorkOrdersSteps.waitMyWorkOrdersLoaded();
    }
}

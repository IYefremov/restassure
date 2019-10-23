package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import org.openqa.selenium.Alert;

public class WorkOrdersSteps {

    public static void saveWorkOrder() {
        WizardScreensSteps.clickSaveButton();
        MyWorkOrdersSteps.waitMyWorkOrdersLoaded();
    }

    public static void cancelCreatingWorkOrder() {
        WizardScreensSteps.cancelWizard();
        MyWorkOrdersSteps.waitMyWorkOrdersLoaded();
    }
}

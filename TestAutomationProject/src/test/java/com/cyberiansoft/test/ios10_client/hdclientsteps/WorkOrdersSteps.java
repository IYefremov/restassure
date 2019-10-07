package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.Alert;

public class WorkOrdersSteps {

    public static void saveWorkOrder() {
        WizardScreensSteps.clickSaveButton();
        MyWorkOrdersSteps.waitMyWorkOrdersLoaded();
    }

    public static void cancelCreatingWorkOrder() {
        WizardScreensSteps.cancelWizard();
        Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
        alert.accept();
        MyWorkOrdersSteps.waitMyWorkOrdersLoaded();
    }
}

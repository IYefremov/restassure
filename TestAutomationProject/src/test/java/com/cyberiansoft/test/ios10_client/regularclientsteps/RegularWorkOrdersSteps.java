package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.Alert;

public class RegularWorkOrdersSteps {

    public static void saveWorkOrder() {
        RegularWizardScreensSteps.clickSaveButton();
    }

    public static void cancelCreatingWorkOrder() {
        RegularWizardScreensSteps.cancelWizard();
        Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
        alert.accept();
    }

}

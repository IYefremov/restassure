package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import org.openqa.selenium.Alert;

public class RegularInspectionsSteps {

    public static void saveInspecion() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.clickSave();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
    }

    public static void cancelCreatingInspection() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.clickCancelWizard();
        Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
        alert.accept();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
    }
}

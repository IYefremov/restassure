package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import org.openqa.selenium.Alert;

public class WizardScreensSteps {

    public static void clickSaveButton() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.clickSave();
    }

    public static void clickFinalButton() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.clickFinalPopup();
    }

    public static void clickDraftButton() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.clickDraftPopup();
    }

    public static void clickCancelButton() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.clickCancelButton();
    }

    public static void cancelWizard() {
        clickCancelButton();
        Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
        alert.accept();
    }

    public static void clickNotesButton() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.clickNotesButton();
    }



}

package com.cyberiansoft.test.vnextbo.validations.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import org.testng.Assert;

public class VNextBOModalDialogValidations {

    public static void verifyDialogIsDisplayed() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOModalDialog().getDialogContent(), 2);
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getDialogContent()),
                "Modal dialog hasn't been opened");
    }

    public static void verifyNoButtonIsDisplayed() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOModalDialog().getNoButton(), 2);
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getNoButton()),
                "Modal dialog hasn't had \"No\" button");
    }

    public static void verifyYesButtonIsDisplayed() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOModalDialog().getYesButton(), 2);
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getYesButton()),
                "Modal dialog hasn't had \"Yes\" button");
    }

    public static void verifyCloseButtonIsDisplayed() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOModalDialog().getCloseButton(), 2);
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getCloseButton()),
                "Modal dialog hasn't had \"Close\" x-icon");
    }

    public static void verifyOkButtonIsDisplayed() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOModalDialog().getConfirmOKButton(), 2);
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getConfirmOKButton()),
                "Modal dialog hasn't had \"Ok\" button");
    }

    public static void verifyCancelButtonIsDisplayed() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOModalDialog().getCancelButton(), 2);
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getCancelButton()),
                "Modal dialog hasn't had \"Cancel\" button");
    }

    public static void verifyDialogIsClosed(VNextBOModalDialog confirmationDialog) {

        Assert.assertFalse(Utils.isElementDisplayed(confirmationDialog.getDialogContent()),
                "Modal dialog hasn't been closed");
    }
}
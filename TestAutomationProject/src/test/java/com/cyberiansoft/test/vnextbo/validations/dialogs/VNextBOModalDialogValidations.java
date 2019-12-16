package com.cyberiansoft.test.vnextbo.validations.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import org.testng.Assert;

public class VNextBOModalDialogValidations {

    public static void verifyDialogIsDisplayed() {

        WaitUtilsWebDriver.waitForVisibility(new VNextBOModalDialog().getDialogContent());
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getDialogContent()),
                "Modal dialog hasn't been opened");
    }

    public static void verifyNoButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getNoButton()),
                "Modal dialog hasn't had \"No\" button");
    }

    public static void verifyYesButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getYesButton()),
                "Modal dialog hasn't had \"Yes\" button");
    }

    public static void verifyCloseButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getCloseButton()),
                "Modal dialog hasn't had \"Close\" x-icon");
    }

    public static void verifyOkButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getConfirmOKButton()),
                "Modal dialog hasn't had \"Ok\" button");
    }

    public static void verifyCancelButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getCancelButton()),
                "Modal dialog hasn't had \"Cancel\" button");
    }

    public static void verifyDialogIsClosed(VNextBOModalDialog confirmationDialog) {

        Assert.assertTrue(Utils.isElementNotDisplayed(confirmationDialog.getDialogContent()),
                "Modal dialog hasn't been closed");
    }
}
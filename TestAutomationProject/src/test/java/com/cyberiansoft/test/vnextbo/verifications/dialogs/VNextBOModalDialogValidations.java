package com.cyberiansoft.test.vnextbo.verifications.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import org.testng.Assert;

public class VNextBOModalDialogValidations {

    public static void isDialogDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getDialogContent()),
                "Modal dialog hasn't been opened");
    }

    public static void isNoButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getNoButton()),
                "Modal dialog hasn't had \"No\" button");
    }

    public static void isYesButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getYesButton()),
                "Modal dialog hasn't had \"Yes\" button");
    }

    public static void isCloseButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getCloseButton()),
                "Modal dialog hasn't had \"Close\" x-icon");
    }

    public static void isOkButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getConfirmOKButton()),
                "Modal dialog hasn't had \"Ok\" button");
    }

    public static void isCancelButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog().getCancelButton()),
                "Modal dialog hasn't had \"Cancel\" button");
    }

    public static void isDialogClosed(VNextBOModalDialog confirmationDialog) {

        Assert.assertTrue(Utils.isElementNotDisplayed(confirmationDialog.getDialogContent()),
                "Modal dialog hasn't been closed");
    }
}
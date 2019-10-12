package com.cyberiansoft.test.vnextbo.verifications.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import org.testng.Assert;

public class VNextBOModalDialogValidations {

    public static void isDialogDisplayed()
    {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(confirmationDialog.dialogContent),
                "Modal dialog hasn't been opened");
    }

    public static void isNoButtonDisplayed()
    {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(confirmationDialog.cancelNoButton),
                "Modal dialog hasn't had \"No\" button");
    }

    public static void isYesButtonDisplayed()
    {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(confirmationDialog.confirmYesButton),
                "Modal dialog hasn't had \"Yes\" button");
    }

    public static void isCloseButtonDisplayed()
    {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(confirmationDialog.closeButton),
                "Modal dialog hasn't had \"Close\" x-icon");
    }

    public static void isOkButtonDisplayed()
    {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(confirmationDialog.confirmOKButton),
                "Modal dialog hasn't had \"Ok\" button");
    }

    public static void isDialogClosed(VNextBOModalDialog confirmationDialog )
    {
        Assert.assertTrue(Utils.isElementNotDisplayed(confirmationDialog.dialogContent),
                "Modal dialog hasn't been closed");
    }
}
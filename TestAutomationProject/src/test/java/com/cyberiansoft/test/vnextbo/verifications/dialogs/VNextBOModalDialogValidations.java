package com.cyberiansoft.test.vnextbo.verifications.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import org.testng.Assert;

public class VNextBOModalDialogValidations {

    public static void isDialogDisplayed()
    {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog(DriverBuilder.getInstance().getDriver()).dialogContent),
                "Modal dialog hasn't been opened");
    }

    public static void isNoButtonDisplayed()
    {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog(DriverBuilder.getInstance().getDriver()).cancelNoButton),
                "Modal dialog hasn't had \"No\" button");
    }

    public static void isYesButtonDisplayed()
    {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog(DriverBuilder.getInstance().getDriver()).confirmYesButton),
                "Modal dialog hasn't had \"Yes\" button");
    }

    public static void isCloseButtonDisplayed()
    {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog(DriverBuilder.getInstance().getDriver()).closeButton),
                "Modal dialog hasn't had \"Close\" x-icon");
    }

    public static void isOkButtonDisplayed()
    {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOModalDialog(DriverBuilder.getInstance().getDriver()).confirmOKButton),
                "Modal dialog hasn't had \"Ok\" button");
    }

    public static void isDialogClosed(VNextBOModalDialog confirmationDialog )
    {
        Assert.assertTrue(Utils.isElementNotDisplayed(confirmationDialog.dialogContent),
                "Modal dialog hasn't been closed");
    }
}
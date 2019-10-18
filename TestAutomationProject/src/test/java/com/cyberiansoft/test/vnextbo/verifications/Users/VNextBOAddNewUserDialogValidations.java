package com.cyberiansoft.test.vnextbo.verifications.Users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.Users.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.steps.Users.VNextBOAddNewUserDialogSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOAddNewUserDialogValidations extends VNextBOBaseWebPageValidations {

    public static void isErrorMessageDisplayed(String errorMessageText)
    {
        Assert.assertTrue(VNextBOAddNewUserDialogSteps.getErrorMessages().contains(errorMessageText),
                "Error message \"" + errorMessageText + " \"hasn't been displayed");
    }

    public static void isDialogClosed(VNexBOAddNewUserDialog vNexBOAddNewUserDialog)
    {
        Assert.assertTrue(Utils.isElementNotDisplayed(vNexBOAddNewUserDialog.dialogContent),
                "Add New User dialog hasn't been closed");
    }

    public static void isEmailFieldDisabled()
    {
        VNexBOAddNewUserDialog vNexBOAddNewUserDialog = new VNexBOAddNewUserDialog(DriverBuilder.getInstance().getDriver());
        Assert.assertFalse(Utils.isElementClickable(vNexBOAddNewUserDialog.userMailFld),
                "Email field hasn't been disabled");
    }
}
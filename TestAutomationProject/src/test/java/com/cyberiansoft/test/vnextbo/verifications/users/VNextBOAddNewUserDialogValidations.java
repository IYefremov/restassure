package com.cyberiansoft.test.vnextbo.verifications.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOAddNewUserDialogSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOAddNewUserDialogValidations extends VNextBOBaseWebPageValidations {

    public static void isErrorMessageDisplayed(String errorMessageText) {

        Assert.assertTrue(VNextBOAddNewUserDialogSteps.getErrorMessages().contains(errorMessageText),
                "Error message \"" + errorMessageText + " \"hasn't been displayed");
    }

    public static void isDialogClosed(VNexBOAddNewUserDialog vNexBOAddNewUserDialog) {

        Assert.assertTrue(Utils.isElementNotDisplayed(vNexBOAddNewUserDialog.dialogContent),
                "Add New User dialog hasn't been closed");
    }

    public static void isEmailFieldDisabled() {

        Assert.assertFalse(Utils.isElementClickable(new VNexBOAddNewUserDialog().userMailFld),
                "Email field hasn't been disabled");
    }
}
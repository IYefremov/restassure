package com.cyberiansoft.test.vnextbo.validations.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOAddNewUserDialogSteps;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class VNextBOAddNewUserDialogValidations extends VNextBOBaseWebPageValidations {

    public static void verifyErrorMessageIsDisplayed(String errorMessageText) {
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.visibilityOfAllElements(new VNexBOAddNewUserDialog().errorMessagesList));
        Assert.assertTrue(VNextBOAddNewUserDialogSteps.getErrorMessages().contains(errorMessageText),
                "Error message \"" + errorMessageText + " \"hasn't been displayed");
    }

    public static void verifyDialogIsClosed(VNexBOAddNewUserDialog vNexBOAddNewUserDialog) {
        Assert.assertFalse(Utils.isElementDisplayed(vNexBOAddNewUserDialog.dialogContent),
                "Add New User dialog hasn't been closed");
    }

    public static void verifyEmailFieldIsDisabled() {
        Assert.assertFalse(Utils.isElementClickable(new VNexBOAddNewUserDialog().userMailFld),
                "Email field hasn't been disabled");
    }
}
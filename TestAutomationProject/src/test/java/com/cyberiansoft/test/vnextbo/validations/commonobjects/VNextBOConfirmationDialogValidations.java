package com.cyberiansoft.test.vnextbo.validations.commonobjects;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.general.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOConfirmationDialog;
import org.testng.Assert;

public class VNextBOConfirmationDialogValidations {

    public static boolean isConfirmationDialogOpened(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOConfirmationDialog().getConfirmDialog(), expected, 4);
    }

    public static void verifyDialogMessageIsDisplayed(String message) {
        Assert.assertTrue(VNextBOConfirmationDialogInteractions.getConfirmationDialogMessage().contains(message),
                "The message hasn't been displayed");
    }
}

package com.cyberiansoft.test.vnextbo.validations.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOUserProfileDialog;
import org.testng.Assert;

public class VNextBOUserProfileDialogValidations {

    public static void verifyUserProfileDialogIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getUserProfileDialog()),
                "User profile dialog hasn't been displayed");
        else Assert.assertFalse(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getUserProfileDialog()),
                "User profile dialog hasn't been closed");
    }

    public static void verifyEmailInputFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getEmailInputField()),
                "Email input field hasn't been displayed");
    }

    public static void verifyPasswordInputFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getPasswordInputField()),
                "Password input field hasn't been displayed");
    }

    public static void verifyConfirmPasswordInputFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getConfirmPasswordInputField()),
                "Confirm password input field hasn't been displayed");
    }

    public static void verifyFirstNameInputFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getFirstNameInputField()),
                "First name input field hasn't been displayed");
    }

    public static void verifyLastNameInputFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getLastNameInputField()),
                "Last name input field hasn't been displayed");
    }

    public static void verifyPhoneInputFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getPhoneInputField()),
                "Phone input field hasn't been displayed");
    }

    public static void verifySaveButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getSaveButton()),
                "Save button hasn't been displayed");
    }

    public static void verifyXButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOUserProfileDialog().getXButton()),
                "Close button hasn't been displayed");
    }

    public static void verifyAllDialogElementsAreDisplayed() {

        verifyUserProfileDialogIsDisplayed(true);
        verifyEmailInputFieldIsDisplayed();
        verifyPasswordInputFieldIsDisplayed();
        verifyConfirmPasswordInputFieldIsDisplayed();
        verifyFirstNameInputFieldIsDisplayed();
        verifyLastNameInputFieldIsDisplayed();
        verifyPhoneInputFieldIsDisplayed();
        verifySaveButtonIsDisplayed();
        verifyXButtonIsDisplayed();
    }
}

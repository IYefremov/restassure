package com.cyberiansoft.test.vnextbo.steps.dialogs;

import com.cyberiansoft.test.vnextbo.interactions.general.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOConfirmationDialogValidations;

public class VNextBOConfirmationDialogSteps {

    public static void getMessageAndReject(String message) {
        VNextBOConfirmationDialogValidations.isConfirmationDialogOpened(true);
        VNextBOConfirmationDialogValidations.verifyDialogMessageIsDisplayed(message);
        VNextBOConfirmationDialogInteractions.clickNoButton();
    }

    public static void getMessageAndConfirm(String message) {
        VNextBOConfirmationDialogValidations.isConfirmationDialogOpened(true);
        VNextBOConfirmationDialogValidations.verifyDialogMessageIsDisplayed(message);
        VNextBOConfirmationDialogInteractions.clickYesButton();
    }
}

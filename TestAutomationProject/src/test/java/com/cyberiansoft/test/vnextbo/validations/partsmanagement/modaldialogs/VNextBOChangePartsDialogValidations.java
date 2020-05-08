package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOChangePartsDialog;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOChangePartsDialogSteps;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOChangePartsDialogValidations {

    public static void verifyInitialSelectStatusPromptIsDisplayed() {
        Assert.assertEquals(VNextBOChangePartsDialogSteps.getSelectStatusFieldOption(), "Select status",
                "The initial 'Select status' prompt hasn't been displayed");
    }

    public static boolean isSubmitButtonEnabled() {
        return Utils.attributeEquals(new VNextBOChangePartsDialog().getSubmitButton(), "disabled", "false");
    }

    public static boolean isCancelButtonEnabled() {
        final WebElement cancelButton = new VNextBOChangePartsDialog().getCancelButton();
        return Utils.getAttribute(cancelButton, "disabled") == null && Utils.isElementClickable(cancelButton);
    }

    public static void verifyInitialDialogSettings() {
        VNextBOChangePartsDialogValidations.verifyInitialSelectStatusPromptIsDisplayed();
        Assert.assertFalse(isSubmitButtonEnabled(), "The 'Submit' button hasn't been disabled");
        Assert.assertTrue(isCancelButtonEnabled(), "The 'Cancel' button hasn't been enabled");
        Assert.assertEquals(
                VNextBOChangePartsDialogSteps.getSelectStatusErrorNotification(), VNextBOAlertMessages.NO_STATUS_SELECTED,
                "The '" + VNextBOAlertMessages.NO_STATUS_SELECTED + "' notification  hasn't been displayed");
    }
}

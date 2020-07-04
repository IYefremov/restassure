package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersDialog;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogSteps;
import org.testng.Assert;

public class VNextBOPartsProvidersDialogValidations {

    public static boolean isPartsProvidersModalDialogOpened() {
        return Utils.isElementWithAttributeContainingValueDisplayed(new VNextBOPartsProvidersDialog()
                .getPartsProvidersModal(), "style", "display: block;", 5);
    }

    public static void verifyProvidersListIsEmpty() {
        Assert.assertEquals(VNextBOPartsProvidersDialogSteps.getOptionsListSize(), 0,
                "The providers list isn't empty");
    }

    public static void verifyNotificationText(String warning) {
        Assert.assertTrue(Utils.getText(new VNextBOPartsProvidersDialog().getNotificationMessage(), 0).contains(warning),
                "The notification message hasn't been displayed properly");
    }
}

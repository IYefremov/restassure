package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialog;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialogSteps;
import org.testng.Assert;

public class VNextBOPartAddNewDocumentDialogValidations {

    public static void verifyAddNewDocumentDialogIsOpened(boolean opened) {
        final boolean visible = WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOPartAddNewDocumentDialog().getAddNewDocumentDialog(), opened, 2);
        Assert.assertTrue(visible, "The 'Add New Document' dialog hasn't been opened/closed");
    }

    public static boolean isWarningMessageDisplayed() {
        return WaitUtilsWebDriver.waitForAttributeToContain(
                new VNextBOPartAddNewDocumentDialog().getWarningMessage(), "class", "invalid", 3);
    }

    public static boolean isSaveButtonDisabled() {
        return Utils.attributeContains(new VNextBOPartAddNewDocumentDialog().getSaveButton(), "disabled", "true");
    }

    public static void verifySavedButtonIsDisabled() {
        Assert.assertTrue(isSaveButtonDisabled(), "The 'Save' button isn't disabled");
    }

    public static void verifyTypeFieldValue(String expectedValue) {
        Assert.assertEquals(VNextBOPartAddNewDocumentDialogSteps.getTypeFieldValue(), expectedValue,
                "The document 'Type' value isn't set properly");
    }
}

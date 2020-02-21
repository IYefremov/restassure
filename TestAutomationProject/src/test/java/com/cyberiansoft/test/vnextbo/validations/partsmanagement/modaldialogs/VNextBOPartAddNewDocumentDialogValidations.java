package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartAddNewDocumentDialog;
import org.testng.Assert;

public class VNextBOPartAddNewDocumentDialogValidations {

    public static void verifyAddNewDocumentDialogIsOpened(boolean opened) {
        final boolean visible = WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOPartAddNewDocumentDialog().getAddNewDocumentDialog(), opened);
        Assert.assertTrue(visible, "The 'Add New Document' dialog hasn't been opened/closed");
    }
}

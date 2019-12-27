package com.cyberiansoft.test.vnextbo.validations.quicknotes;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.quicknotes.VNextBONewNotesDialog;
import org.testng.Assert;

public class VNextBONewNotesDialogValidations {

    public static void verifyErrorMessageIsDisplayedAndCorrect() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBONewNotesDialog().getErrorMessage()),
                "Error message hasn't been displayed");
        Assert.assertEquals(Utils.getText(new VNextBONewNotesDialog().getErrorMessage()), "Text is required");
    }
}

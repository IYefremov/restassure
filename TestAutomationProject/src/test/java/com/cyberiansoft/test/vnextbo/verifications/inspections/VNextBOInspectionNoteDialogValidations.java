package com.cyberiansoft.test.vnextbo.verifications.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionNoteDialog;
import org.testng.Assert;

public class VNextBOInspectionNoteDialogValidations {

    public static void isInspectionNoteTextDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionNoteDialog().noteText),
                "Notes dialog  hasn't been opened");
    }

    public static void isNoteDialogClosed(VNextBOInspectionNoteDialog noteDialog) {

        Assert.assertTrue(Utils.isElementNotDisplayed(noteDialog.dialogContainer),
                "Notes dialog  hasn't been closed");
    }
}
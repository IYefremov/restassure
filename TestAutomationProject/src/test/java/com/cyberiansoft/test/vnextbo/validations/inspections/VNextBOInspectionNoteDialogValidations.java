package com.cyberiansoft.test.vnextbo.validations.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionNoteDialog;
import org.testng.Assert;

public class VNextBOInspectionNoteDialogValidations {

    public static void isInspectionNoteTextDisplayed()
    {
        VNextBOInspectionNoteDialog noteDialog = new VNextBOInspectionNoteDialog();
        Assert.assertTrue(Utils.isElementDisplayed(noteDialog.noteText),
                "Notes dialog  hasn't been opened");
    }

    public static void isNoteDialogClosed(VNextBOInspectionNoteDialog noteDialog)
    {
        Assert.assertTrue(Utils.isElementNotDisplayed(noteDialog.dialogContainer),
                "Notes dialog  hasn't been closed");
    }
}
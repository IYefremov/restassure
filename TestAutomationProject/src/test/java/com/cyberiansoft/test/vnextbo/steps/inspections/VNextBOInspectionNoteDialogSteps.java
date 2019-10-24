package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionNoteDialog;

public class VNextBOInspectionNoteDialogSteps {

    public static void closeInspectionNote()
    {
        Utils.clickElement(new VNextBOInspectionNoteDialog().closeDialogButton);
    }
}
package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionNoteDialog;

public class VNextBOInspectionNoteDialogSteps {

    public static void closeInspectionNote()
    {
        VNextBOInspectionNoteDialog noteDialog = new VNextBOInspectionNoteDialog(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(noteDialog.closeDialogButton);
    }
}
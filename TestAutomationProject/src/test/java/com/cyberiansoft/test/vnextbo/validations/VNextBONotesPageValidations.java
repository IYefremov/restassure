package com.cyberiansoft.test.vnextbo.validations;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOOrderServiceNotesDialog;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOEditNotesDialog;

public class VNextBONotesPageValidations {

    public static boolean isEditOrderServiceNotesBlockDisplayed() {
        return Utils.isElementDisplayed(new VNextBOOrderServiceNotesDialog().getRepairNotesList());
    }

    public static boolean isRoEditNotesModalDialogDisplayed() {
        return Utils.isElementDisplayed(new VNextBOEditNotesDialog().getRoEditNotesModalDialog());
    }

    public static boolean isRoEditNotesModalDialogHidden() {
        return Utils.isElementNotDisplayed(new VNextBOEditNotesDialog().getRoEditNotesModalDialog());
    }

    public static boolean isRONoteTextAreaDisplayed() {
        return Utils.isElementDisplayed(new VNextBOOrderServiceNotesDialog().getRoNoteTextArea(), 5);
    }
}
package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPMNotesDialogInteractions;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPMNotesDialogValidations;

public class VNextBOPMNotesDialogSteps {

    public static void addNote(String note) {
        VNextBOPMNotesDialogInteractions.clickAddNewNoteButton();
        VNextBOPMNotesDialogInteractions.setNote(note);
    }

    public static void closeNoteDialog() {
        VNextBOPMNotesDialogInteractions.clickCloseNoteDialogButton();
        VNextBOPMNotesDialogValidations.verifyNotesDialogIsOpened(false);
    }
}

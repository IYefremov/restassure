package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBONotesDialogNew;

public class VNextBONotesDialogStepsNew {

    public static void closeDialogWithXIcon() {

        VNextBONotesDialogNew notesDialog = new VNextBONotesDialogNew();
        Utils.clickElement(notesDialog.getCloseDialogXIcon());
        WaitUtilsWebDriver.waitForInvisibility(notesDialog.getNotesDialog());
    }

    public static void saveNote() {

        VNextBONotesDialogNew notesDialog = new VNextBONotesDialogNew();
        Utils.clickElement(notesDialog.getNoteSaveButton());
        closeDialogWithXIcon();
    }

    public static void addNote(String noteText, boolean saveNote) {

        VNextBONotesDialogNew notesDialog = new VNextBONotesDialogNew();
        WaitUtilsWebDriver.waitForVisibility(notesDialog.getNotesDialog());
        Utils.clickElement(notesDialog.getAddNewNoteButton());
        Utils.clearAndType(notesDialog.getNoteTextArea(), noteText);
        if (saveNote) saveNote();
        else closeDialogWithXIcon();
    }
}

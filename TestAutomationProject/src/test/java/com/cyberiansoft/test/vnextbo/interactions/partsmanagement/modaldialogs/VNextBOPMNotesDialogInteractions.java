package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPMNotesDialog;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class VNextBOPMNotesDialogInteractions {

    public static void clickAddNewNoteButton() {
        final VNextBOPMNotesDialog notesDialog = new VNextBOPMNotesDialog();
        Utils.clickElement(notesDialog.getAddNewNoteButton());
    }

    public static void setNote(String note) {
        Utils.clearAndType(new VNextBOPMNotesDialog().getNoteField(), note);
    }

    public static void clickSaveNoteButton() {
        Utils.clickElement(new VNextBOPMNotesDialog().getSaveNoteButton());
    }

    public static void clickCancelNoteButton() {
        Utils.clickElement(new VNextBOPMNotesDialog().getCancelNoteButton());
    }

    public static void waitForNotesListToBeUpdated(int expected) {
        try {
            WaitUtilsWebDriver.getWebDriverWait(4).until((ExpectedCondition<Boolean>) driver ->
                    VNextBOPMNotesDialogInteractions.getNotesListSize() == expected);
        } catch (Exception ignored) {}
    }

    public static void clickCloseNoteDialogButton() {
        Utils.clickElement(new VNextBOPMNotesDialog().getCLoseButton());
    }

    public static int getNotesListSize() {
        try {
            return WaitUtilsWebDriver
                    .waitForVisibilityOfAllOptions(new VNextBOPMNotesDialog().getNotesList(), 2).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public static List<String> getNotesListValues() {
        return Utils.getText(new VNextBOPMNotesDialog().getNotesList());
    }
}

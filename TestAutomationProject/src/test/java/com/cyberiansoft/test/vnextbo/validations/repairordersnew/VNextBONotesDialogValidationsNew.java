package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBONotesDialogNew;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBONotesDialogValidationsNew {

    public static void verifyNoteInTheNotesList(String noteText, boolean shouldBeInTheList) {

        VNextBONotesDialogNew notesDialog = new VNextBONotesDialogNew();
        List<String> notesList = notesDialog.getNotesList().stream().
                map(WebElement::getText).collect(Collectors.toList());
        if (shouldBeInTheList) Assert.assertTrue(notesList.contains(noteText), "Note hasn't been presented in the dialog");
        else Assert.assertFalse(notesList.contains(noteText), "Note has been presented in the dialog");
    }
}

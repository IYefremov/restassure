package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.notes.NoteListMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class NotesSteps {

    public static void addNewNote(String noteText) {
        NoteListMenuScreen noteListMenuScreen = new NoteListMenuScreen();
        VNextNotesScreen notesScreen = new VNextNotesScreen();

        WaitUtils.elementShouldBeVisible(noteListMenuScreen.getAddNewNoteButton(), true);
        noteListMenuScreen.getAddNewNoteButton().click();
        WaitUtils.elementShouldBeVisible(notesScreen.getRootNotesElement(), true);
        notesScreen.expandNote();
        notesScreen.enterNoteText(noteText);
        GeneralSteps.pressBackButton();
    }

    public static void verifyNoteIsPresent(String noteText) {
        NoteListMenuScreen noteListMenuScreen = new NoteListMenuScreen();
        WaitUtils.elementShouldBeVisible(noteListMenuScreen.getRootElement(), true);
        Assert.assertTrue(noteListMenuScreen.getNoteListElements()
                .stream().anyMatch((element) -> element.getNoteText().contains(noteText)));
    }
}

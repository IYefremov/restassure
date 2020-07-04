package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.notes.NoteListMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class NotesValidations {

    public static void verifyNoPicturesPresent() {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        Assert.assertEquals(notesScreen.getPictureElementList().size(), 0);
    }

    public static void verifyNumberOfPicturesPresent(int expectedNumber) {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        WaitUtils.getGeneralFluentWait(5, 300)
                .until(driver -> notesScreen.getPictureElementList().size() == expectedNumber);
        Assert.assertEquals(notesScreen.getPictureElementList().size(), expectedNumber);
    }

    public static void verifyNotePresentInList(String noteText) {
        NoteListMenuScreen noteListMenuScreen = new NoteListMenuScreen();
        WaitUtils.collectionSizeIsGreaterThan(noteListMenuScreen.getNoteListElements(), 0);
        Assert.assertNotNull(noteListMenuScreen.getNoteByText(noteText));
    }

    public static void verifyPicturesPresent() {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        WaitUtils.collectionSizeIsGreaterThan(notesScreen.getPictureElementList(), 0);
    }

    public static void verifyNoteIsPresent(String noteText) {
        VNextNotesScreen noteScreen = new VNextNotesScreen();
        WaitUtils.elementShouldBeVisible(noteScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(noteScreen.getNoteEditField());
        Assert.assertEquals(noteScreen.getNoteEditField().getAttribute("value").replace("\n", " "), noteText.replace("\n", " "));
    }
}

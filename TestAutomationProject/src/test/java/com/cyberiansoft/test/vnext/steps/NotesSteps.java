package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.notes.NoteListMenuScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.VNextRepairOrderNoteScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

public class NotesSteps {

    public static void setNoteText(String noteText) {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        WaitUtils.elementShouldBeVisible(notesScreen.getRootElement(), true);
        notesScreen.setNoteText(noteText);
        BaseUtils.waitABit(2000);
    }

    public static void verifyNoteIsPresent(String noteText) {
        VNextNotesScreen noteScreen = new VNextNotesScreen();
        WaitUtils.elementShouldBeVisible(noteScreen.getRootElement(), true);
        WaitUtils.elementShouldBeVisible(noteScreen.getNoteEditField(), true);
        WaitUtils.getGeneralFluentWait().until(webDriver -> {
            Assert.assertTrue(noteScreen.getNoteEditField().getAttribute("value").contains(noteText));
            return true;
        });

    }

    public static void addQuickNote(String quickNoteText) {
        VNextNotesScreen notesScreen = new VNextNotesScreen();

        notesScreen.switchToQuickNotes();
        WaitUtils.collectionSizeIsGreaterThan(notesScreen.getQuickNotesList(), 0);
        notesScreen.selectQuickNote(quickNoteText);
    }

    public static void addPhotoFromCamera() {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        WaitUtils.elementShouldBeVisible(notesScreen.getTakePictureButton(), true);
        notesScreen.getTakePictureButton().click();
        GeneralSteps.takeCameraPicture();
    }

    public static void verifyPicturesPresent() {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        WaitUtils.collectionSizeIsGreaterThan(notesScreen.getPictureElementList(), 0);
    }

    public static void deleteAllPictures() {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        By removePictureButton = By.xpath("//span[contains(@class,'button-delete')]");
        //TODO: Probably will fail when deleteing multiple images as it modifies collection on which we using foreach
        notesScreen.getPictureElementList().forEach(pictureElement -> {
            pictureElement.click();
            WaitUtils.waitUntilElementIsClickable(removePictureButton);
            WaitUtils.click(removePictureButton);
            GeneralSteps.confirmDialog();
        });
    }

    public static void verifyNoPicturesPresent() {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        Assert.assertEquals(notesScreen.getPictureElementList().size(), 0);
    }

    public static void addRepairOrderNote() {
        NoteListMenuScreen noteListMenuScreen = new NoteListMenuScreen();
        WaitUtils.waitUntilElementIsClickable(noteListMenuScreen.getAddNewNoteButton());
        noteListMenuScreen.getAddNewNoteButton().click();
    }

    public static void verifyNotePresentInList(String noteText) {
        NoteListMenuScreen noteListMenuScreen = new NoteListMenuScreen();
        WaitUtils.collectionSizeIsGreaterThan(noteListMenuScreen.getNoteListElements(), 0);
        Assert.assertNotNull(noteListMenuScreen.getNoteByText(noteText));
    }

    public static void setRepairOrderNoteText(String noteText) {
        VNextRepairOrderNoteScreen noteScreen = new VNextRepairOrderNoteScreen();
        WaitUtils.elementShouldBeVisible(noteScreen.getRootElement(), true);
        noteScreen.getCommentTest().sendKeys(noteText);
    }
}

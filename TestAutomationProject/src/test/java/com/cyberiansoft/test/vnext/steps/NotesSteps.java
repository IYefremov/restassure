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
        WaitUtils.getGeneralFluentWait().until(driver -> {
            notesScreen.setNoteText(noteText);
            return true;
        });
        BaseUtils.waitABit(2000);
    }

    public static void tapNoteTextAndClear() {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        WaitUtils.elementShouldBeVisible(notesScreen.getRootElement(), true);
        notesScreen.getNoteEditField().click();
        BaseUtils.waitABit(500);
        notesScreen.getClearNoteButton().click();
    }

    public static void verifyNoteIsPresent(String noteText) {
        VNextNotesScreen noteScreen = new VNextNotesScreen();
        WaitUtils.elementShouldBeVisible(noteScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(noteScreen.getNoteEditField());
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
        WaitUtils.click(notesScreen.getTakePictureButton());
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

    public static void deletePictures(int numberToDelete) {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        By removePictureButton = By.xpath("//span[contains(@class,'button-delete')]");
        WaitUtils.click(notesScreen.getPictureElement());
        for (int i = 0; i < numberToDelete; i++) {
            WaitUtils.waitUntilElementIsClickable(removePictureButton);
            WaitUtils.click(removePictureButton);
            GeneralSteps.confirmDialog();
        }
        WaitUtils.elementShouldBeVisible(notesScreen.getRootElement(), true);
        notesScreen.clickScreenBackButton();
    }

    public static void verifyNoPicturesPresent() {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        Assert.assertEquals(notesScreen.getPictureElementList().size(), 0);
    }

    public static void verifyNumberOfPicturesPresent(int expectedNumber) {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        Assert.assertEquals(notesScreen.getPictureElementList().size(), expectedNumber);
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

    public static String addQuickNotesByCount(int count) {
        StringBuilder note = new StringBuilder();
        String result = "";
        final String quickNoteText = "Test Quick Note 1";
        for (int i = 1; i <= count; i++) {
            NotesSteps.addQuickNote(quickNoteText);
            note.append("\n");
            note.append(quickNoteText) ;
        }
        return result;
    }

    public static String addQuickNotesFromListByCount(int count) {
        VNextNotesScreen noteScreen = new VNextNotesScreen();
        StringBuilder stringBuilder = new StringBuilder();
        noteScreen.getQuickNotesList().stream().limit(count).forEach(element ->  {
            NotesSteps.addQuickNote(element.getText());
            stringBuilder.append(element.getText()).append("\n");
        });
        return stringBuilder.toString();
    }
}

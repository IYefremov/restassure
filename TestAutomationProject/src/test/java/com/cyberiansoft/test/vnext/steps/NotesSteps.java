package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.notes.NoteListMenuScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.VNextRepairOrderNoteScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

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

    public static void deleteAllPictures() {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        By removePictureButton = By.xpath("//span[contains(@class,'slide-remove')]");
        //TODO: Probably will fail when deleteing multiple images as it modifies collection on which we using foreach
        int picturesNumber = notesScreen.getPictureElementList().size();
        notesScreen.getPictureElementList().get(0).click();
        for (int i = 0; i < picturesNumber; i++) {
            WaitUtils.waitUntilElementIsClickable(removePictureButton);
            WaitUtils.click(removePictureButton);
            GeneralSteps.confirmDialog();
        }
        WaitUtils.elementShouldBeVisible(notesScreen.getRootElement(), true);
        /*notesScreen.getPictureElementList().forEach(pictureElement -> {
            WaitUtils.waitUntilElementIsClickable(removePictureButton);
            WaitUtils.click(removePictureButton);
            GeneralSteps.confirmDialog();
        });*/
    }

    public static void deletePictures(int numberToDelete) {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        By removePictureButton = By.xpath("//span[contains(@class,'slide-remove')]");
        WaitUtils.click(notesScreen.getPictureElement());
        for (int i = 0; i < numberToDelete; i++) {
            WaitUtils.waitUntilElementIsClickable(removePictureButton);
            WaitUtils.click(removePictureButton);
            GeneralSteps.confirmDialog();
        }
        WaitUtils.elementShouldBeVisible(notesScreen.getRootElement(), true);
        notesScreen.clickScreenBackButton();
    }

    public static void addRepairOrderNote() {
        NoteListMenuScreen noteListMenuScreen = new NoteListMenuScreen();
        WaitUtils.waitUntilElementIsClickable(noteListMenuScreen.getAddNewNoteButton());
        noteListMenuScreen.getAddNewNoteButton().click();
    }

    public static void setRepairOrderNoteText(String noteText) {
        VNextRepairOrderNoteScreen noteScreen = new VNextRepairOrderNoteScreen();
        WaitUtils.elementShouldBeVisible(noteScreen.getRootElement(), true);
        noteScreen.getCommentTest().sendKeys(noteText);
    }

    public static List<String> getListOfQuickNotes(int count) {
        VNextNotesScreen notesScreen = new VNextNotesScreen();
        notesScreen.switchToQuickNotes();
        List<String> quickNotesList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            quickNotesList.add(notesScreen.getQuickNotesList().get(i).getText());
        }
        ScreenNavigationSteps.pressBackButton();
        return  quickNotesList;
    }

    public static String addQuickNotesFromListByCount(int count) {
        List<String> quickNotesList = getListOfQuickNotes(count);
        StringBuilder stringBuilder = new StringBuilder();
        quickNotesList.forEach(notesText -> {
            stringBuilder.append(notesText.trim()).append("\n");
            NotesSteps.addQuickNote(notesText);
        });
        return stringBuilder.toString();
    }
}

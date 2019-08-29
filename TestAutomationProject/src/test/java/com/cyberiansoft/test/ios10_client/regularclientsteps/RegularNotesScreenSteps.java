package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;

public class RegularNotesScreenSteps {

    public static void setTextNotes(String textNotes) {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.setNotes(textNotes);
    }

    public static void addImageNote() {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        switchToPhotosView();
        notesScreen.clickLibraryButton();
        notesScreen.clickMommentsLibrary();
        notesScreen.selectPhotoFromLibrary();
        notesScreen.clickCancel();
    }

    public static void switchToPhotosView() {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.clickPhotoTab();
    }

    public static void saveNotes() {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.clickSaveButton();
    }
}

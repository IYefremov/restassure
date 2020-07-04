package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;

public class RegularNotesScreenSteps {

    public static void setTextNotes(String textNotes) {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.setNotes(textNotes);
    }

    public static void addImageNote() {
        int oneImageNumber = 1;
        switchToPhotosView();
        addImegesFromLibrary(oneImageNumber);
        clickCancelGalleryButton();
    }

    public static void addImageNotes(int numberOfImages) {
        switchToPhotosView();
        addImegesFromLibrary(numberOfImages);
        clickCancelGalleryButton();
    }

    public static void addImegesFromLibrary(int numberOfImages) {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.clickLibraryButton();
        for (int i =0; i < numberOfImages; i++) {
            addPhotoFromMomentsLibrary();
        }
    }

    public static void clickCancelGalleryButton() {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.clickCancelGallery();
    }

    public static void clickBackButton() {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.clickBackButton();
    }

    public static void deleteImageNotes(int numberOfImages) {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        switchToPhotosView();
        for (int i =0; i < numberOfImages; i++) {
            notesScreen.clickOnImage();
            notesScreen.clickDeleteImageButton();
        }
    }

    private static void addPhotoFromMomentsLibrary() {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.clickMomentsLibrary();
        notesScreen.selectPhotoFromLibrary();
    }

    public static void switchToPhotosView() {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.clickPhotoTab();
    }

    public static void saveNotes() {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.clickSaveButton();
    }

    public static void clickCancelButton() {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.clickCancel();
    }
}

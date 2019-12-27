package com.cyberiansoft.test.vnextbo.steps.quicknotes;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.quicknotes.VNextBONewNotesDialog;

public class VNextBONewNotesDialogSteps {

    public static void clickAddButton() {

        Utils.clickElement(new VNextBONewNotesDialog().getAddButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void clickUpdateButton() {

        Utils.clickElement(new VNextBONewNotesDialog().getUpdateButton());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void closeDialog() {

        Utils.clickElement(new VNextBONewNotesDialog().getCloseButton());
    }

    public static void populateDescriptionField(String noteDescription) {

        Utils.clearAndType(new VNextBONewNotesDialog().getDescriptionTextArea(), noteDescription);
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void addNote(String noteDescription) {

        populateDescriptionField(noteDescription);
        clickAddButton();
    }

    public static void addNoteWithoutSave(String noteDescription) {

        populateDescriptionField(noteDescription);
        closeDialog();
    }

    public static void updateNote(String noteDescription) {

        populateDescriptionField(noteDescription);
        clickUpdateButton();
    }
}

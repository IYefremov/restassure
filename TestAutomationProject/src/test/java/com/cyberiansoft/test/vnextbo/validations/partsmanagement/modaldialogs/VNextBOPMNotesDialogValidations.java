package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPMNotesDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPMNotesDialog;
import org.testng.Assert;

public class VNextBOPMNotesDialogValidations {

    public static void verifyNotesDialogIsOpened(boolean opened) {
        final boolean condition = WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOPMNotesDialog().getNotesDialog(), opened, 4);
        Assert.assertTrue(condition, "The PM notes dialog hasn't been opened");
    }

    public static void verifyNotesListHasBeenUpdated(int expected) {
        VNextBOPMNotesDialogInteractions.waitForNotesListToBeUpdated(expected);
        Assert.assertEquals(VNextBOPMNotesDialogInteractions.getNotesListSize(), expected,
                "The notes list size hasn't been updated");
    }

    public static void verifyNotesListHasNotBeenUpdated(int expected) {
        VNextBOPMNotesDialogInteractions.waitForNotesListToBeUpdated(expected);
        Assert.assertNotEquals(VNextBOPMNotesDialogInteractions.getNotesListSize(), expected,
                "The notes list size has been updated");
    }

    public static void verifyNewNoteHasBeenAdded(String note) {
        Assert.assertEquals(VNextBOPMNotesDialogInteractions.getNotesListValues().get(0), note,
                "The note hasn't been added");
    }

    public static void verifyNewNoteHasNotBeenAdded(String note) {
        Assert.assertNotEquals(VNextBOPMNotesDialogInteractions.getNotesListValues().get(0), note,
                "The note has been added after cancelling");
    }
}

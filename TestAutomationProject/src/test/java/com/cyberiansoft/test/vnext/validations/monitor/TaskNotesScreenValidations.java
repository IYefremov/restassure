package com.cyberiansoft.test.vnext.validations.monitor;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.monitoring.TaskNotesScreen;
import org.testng.Assert;

public class TaskNotesScreenValidations {

    public static void verifyNoteIsAdded(String noteText) {

        Assert.assertTrue(Utils.isElementDisplayed(new TaskNotesScreen().getAddedNote()),
                "Note hasn't been added");
        Assert.assertTrue(new TaskNotesScreen().getAddedNote().getText().contains(noteText),
                "Note text hasn't been correct");
    }
}

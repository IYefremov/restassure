package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.TaskNotesScreen;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.validations.monitor.TaskNotesScreenValidations;
import org.openqa.selenium.By;

public class TaskNotesScreenSteps {

    public static void addNote(String noteText) {

        TaskNotesScreen notesScreen = new TaskNotesScreen();
        WaitUtils.click(notesScreen.getAddNewNoteButton());
        notesScreen.getCommentTextarea().sendKeys(noteText);
        TopScreenPanelSteps.goToThePreviousScreen();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        TaskNotesScreenValidations.verifyNoteIsAdded(noteText);
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}

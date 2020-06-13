package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.TaskDetailsScreen;
import com.cyberiansoft.test.vnext.steps.NotesSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.validations.monitor.SelectTeamScreenValidations;
import org.openqa.selenium.By;

public class TaskDetailsScreenSteps {

    public static void changeVendorTeam(String team) {

        WaitUtils.click(new TaskDetailsScreen().getVendorTeamField());
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        SelectTeamScreenValidations.verifySelectTeamScreenIsOpened();
        SelectTeamScreenSteps.selectTeam(team);
    }

    public static void addNote(String noteText) {

        WaitUtils.click(new TaskDetailsScreen().getSelectCommentField());
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        NotesSteps.addNote();
        NotesSteps.setRepairOrderNoteText("test note");
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}

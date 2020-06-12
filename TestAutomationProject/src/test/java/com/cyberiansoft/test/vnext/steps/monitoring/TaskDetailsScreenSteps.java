package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.TaskDetailsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.validations.monitor.SelectTeamScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.SelectTechnicianScreenValidations;
import org.openqa.selenium.By;

public class TaskDetailsScreenSteps {

    public static void changeVendorTeamWithTechnician(String team, String technician) {

        WaitUtils.click(new TaskDetailsScreen().getVendorTeamField());
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        SelectTeamScreenValidations.verifySelectTeamScreenIsOpened();
        SelectTeamScreenSteps.selectTeam(team);
        SelectTechnicianScreenValidations.verifySelectTechnicianScreenIsOpened();
        SelectTechnicianScreenSteps.selectTechnician(technician);
    }

    public static void addNote(String noteText) {

        WaitUtils.click(new TaskDetailsScreen().getSelectCommentField());
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        TaskNotesScreenSteps.addNote(noteText);
    }
}

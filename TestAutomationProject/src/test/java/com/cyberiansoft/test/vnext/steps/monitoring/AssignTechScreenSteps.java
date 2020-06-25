package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.TaskDetailsScreen;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

public class AssignTechScreenSteps {

    public static void changeVendorTeam(String team) {

        WaitUtils.click(new TaskDetailsScreen().getVendorTeamField());
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        VendorTeamsScreenSteps.selectTeam(team);
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}

package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.SelectTeamScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class SelectTeamScreenSteps {

    public static void selectTeam(String teamName) {

        WaitUtils.collectionSizeIsGreaterThan(new SelectTeamScreen().getTeamsRecordsList(), 0);
        WaitUtils.click(new SelectTeamScreen().getTeamsRecordsList().
                stream()
                .filter(team -> team.getRecordText().contains(teamName))
                .findFirst().get().getRootElement());
    }
}

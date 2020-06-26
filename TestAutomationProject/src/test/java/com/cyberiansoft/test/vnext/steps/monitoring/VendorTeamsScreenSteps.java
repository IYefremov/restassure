package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.VendorTeamsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class VendorTeamsScreenSteps {

    public static void selectTeam(String teamName) {

        WaitUtils.collectionSizeIsGreaterThan(new VendorTeamsScreen().getTeamsRecordsList(), 0);
        WaitUtils.click(new VendorTeamsScreen().getTeamsRecordsList().
                stream()
                .filter(team -> team.getRecordText().contains(teamName))
                .findFirst().get().getRootElement());
    }
}

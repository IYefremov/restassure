package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class MonitorSearchSteps {

    public static void verifySearchResultsAreEmpty() {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        WaitUtils.elementShouldBeVisible(commonFilterScreen.getNothingFoundLable(), true);
    }

}

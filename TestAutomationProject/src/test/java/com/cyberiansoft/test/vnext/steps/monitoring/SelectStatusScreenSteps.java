package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.SelectStatusScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class SelectStatusScreenSteps {

    public static void selectActiveStatus() {

        WaitUtils.click(new SelectStatusScreen().getActiveStatus());
    }
}

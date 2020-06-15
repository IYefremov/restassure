package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.SelectTaskScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class SelectTaskScreenSteps {

    public static void selectTestManualTask() {

        WaitUtils.click(new SelectTaskScreen().getTestManualTaskItem());
    }
}

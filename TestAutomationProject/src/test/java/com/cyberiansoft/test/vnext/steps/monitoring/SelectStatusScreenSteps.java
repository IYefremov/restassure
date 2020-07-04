package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.enums.ServiceOrTaskStatus;
import com.cyberiansoft.test.vnext.screens.monitoring.SelectStatusScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

public class SelectStatusScreenSteps {

    public static void selectStatus(ServiceOrTaskStatus status) {

        WaitUtils.click(new SelectStatusScreen().getStatusRecord(status));
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
    }
}

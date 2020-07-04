package com.cyberiansoft.test.vnext.validations.monitor;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.monitoring.SelectTeamScreen;
import org.testng.Assert;

public class SelectTeamScreenValidations {

    public static void verifySelectTeamScreenIsOpened() {

        Assert.assertTrue(Utils.isElementDisplayed(new SelectTeamScreen().getRootElement()),
                "'Select team screen' hasn't been opened");
    }
}

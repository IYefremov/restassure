package com.cyberiansoft.test.vnext.validations.monitor;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.monitoring.SelectStatusScreen;
import org.testng.Assert;

public class SelectStatusScreenValidations {

    public static void verifySelectStatusScreenIsOpened() {

        Assert.assertTrue(Utils.isElementDisplayed(new SelectStatusScreen().getRootElement()),
                "'Select status screen' hasn't been opened");
    }
}

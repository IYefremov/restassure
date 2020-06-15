package com.cyberiansoft.test.vnext.validations.monitor;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.monitoring.SelectTechnicianScreen;
import org.testng.Assert;

public class SelectTechnicianScreenValidations {

    public static void verifySelectTechnicianScreenIsOpened() {

        Assert.assertTrue(Utils.isElementDisplayed(new SelectTechnicianScreen().getRootElement()),
                "'Select technician screen' hasn't been opened");
    }
}

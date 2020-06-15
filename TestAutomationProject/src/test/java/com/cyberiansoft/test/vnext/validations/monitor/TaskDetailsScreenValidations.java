package com.cyberiansoft.test.vnext.validations.monitor;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.monitoring.TaskDetailsScreen;
import org.testng.Assert;

public class TaskDetailsScreenValidations {

    public static void verifyAddNewTaskPageIsOpened() {

        Assert.assertTrue(Utils.isElementDisplayed(new TaskDetailsScreen().getRootElement()),
                "'Add new task screen' hasn't been opened");
    }

    public static void verifyVendorTeamIsCorrect(String teamName) {

        Assert.assertEquals(Utils.getInputFieldValue(new TaskDetailsScreen().getVendorTeamField()), teamName,
                "'Vendor Team' field has contained incorrect value");
    }

    public static void verifyTechnicianIsCorrect(String technician) {

        Assert.assertEquals(Utils.getInputFieldValue(new TaskDetailsScreen().getTechnicianField()), technician,
                "'Assigned Technician' field has contained incorrect value");
    }
}

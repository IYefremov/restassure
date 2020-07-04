package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOLogInfoDialogNew;
import org.testng.Assert;

public class VNextBOLogInfoDialogValidationsNew {

    public static void verifyFirstRecordServiceIsCorrect(String expectedService) {

        Assert.assertEquals(Utils.getText(new VNextBOLogInfoDialogNew().getFirstRecordService()), expectedService,
                "First log record service hasn't been correct");
    }

    public static void verifyFirstRecordPhaseIsCorrect(String expectedPhase) {

        Assert.assertEquals(Utils.getText(new VNextBOLogInfoDialogNew().getFirstRecordPhase()), expectedPhase,
                "First log record phase hasn't been correct");
    }

    public static void verifyFirstRecordStatusIsCorrect(String expectedStatus) {

        ConditionWaiter.create(15000, 1000, __ -> Utils.getText(new VNextBOLogInfoDialogNew().getFirstRecordStatus()).equals(expectedStatus)).execute();
        Assert.assertEquals(Utils.getText(new VNextBOLogInfoDialogNew().getFirstRecordStatus()), expectedStatus,
                "First log record status hasn't been correct");
    }

    public static void verifyFirstRecordNoteIsCorrect(String expectedNote) {

        Assert.assertTrue(Utils.getText(new VNextBOLogInfoDialogNew().getFirstRecordNote()).contains(expectedNote),
                "First log record note hasn't been correct");
    }
}

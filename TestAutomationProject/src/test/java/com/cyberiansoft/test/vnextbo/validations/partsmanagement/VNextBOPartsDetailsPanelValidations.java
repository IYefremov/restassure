package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import org.testng.Assert;

public class VNextBOPartsDetailsPanelValidations {

    public static void verifyDetailsPanelIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getPartsDetailsTable()),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyPartStatusIsCorrect(int partNumber, String expectedStatus) {

        Assert.assertEquals(Utils.getText(new VNextBOPartsDetailsPanel().getPartStatusField().get(partNumber)), expectedStatus,
                "Status hasn't been correct");
    }

    public static void verifyActionsMenuIsDisplayed(int partNumber) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getActionsDropDownMenu().get(partNumber)),
                "Actions menu hasn't been displayed");
    }

    public static void verifyPartsAmountIsCorrect(int expectedPartsAmount) {

        Assert.assertEquals(VNextBOPartsDetailsPanelSteps.getPartsListSize(), expectedPartsAmount,
                "Parts amount hasn't been correct");
    }

    public static void verifyAddLaborButtonIsDisplayed(int partNumber) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getAddLaborButton().get(partNumber)),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyLaborsAmountIsCorrect(int partNumber, int expectedLaborsAmount) {

        Assert.assertEquals(VNextBOPartsDetailsPanelSteps.getLaborsAmountForPartByNumberInList(partNumber), expectedLaborsAmount,
                "Labors amount hasn't been correct");
    }
}

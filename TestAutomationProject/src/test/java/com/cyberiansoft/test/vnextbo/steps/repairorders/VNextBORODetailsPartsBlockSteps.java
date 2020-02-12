package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBORODetailsPartsBlockInteractions;
import org.testng.Assert;

public class VNextBORODetailsPartsBlockSteps {

    public static void updatePartStatus(String roWindow, String partName, String expectedStatus) {
        VNextBORODetailsPartsBlockInteractions.updatePartsBlock(roWindow);
        final String displayedStatus = VNextBORODetailsPartsBlockInteractions.getPartStatusByPartName(partName);
        Assert.assertEquals(displayedStatus, expectedStatus, "The status hasn't been updated");
    }
}

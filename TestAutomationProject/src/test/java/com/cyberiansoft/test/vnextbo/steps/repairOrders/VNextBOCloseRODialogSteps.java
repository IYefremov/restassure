package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOCloseRODialogInteractions;
import org.testng.Assert;

public class VNextBOCloseRODialogSteps {

    public static void closeROWithReason(String reason) {
        final VNextBOCloseRODialogInteractions closeRODialogInteractions = new VNextBOCloseRODialogInteractions();
        Assert.assertTrue(closeRODialogInteractions.isCloseRODialogDisplayed(),
                "The close RO dialog hasn't been displayed");
        closeRODialogInteractions.setReason(reason);
        closeRODialogInteractions.clickCloseROButton();
        Assert.assertTrue(closeRODialogInteractions.isCloseRODialogClosed(),
                "The close RO dialog hasn't been closed");
    }
}

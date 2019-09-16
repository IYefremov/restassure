package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOCloseRODialogInteractions;
import org.testng.Assert;

public class VNextBOCloseRODialogSteps {

    private VNextBOCloseRODialogInteractions closeRODialogInteractions;

    public VNextBOCloseRODialogSteps() {
        closeRODialogInteractions = new VNextBOCloseRODialogInteractions();
    }

    public void closeROWithReason(String reason) {
        Assert.assertTrue(closeRODialogInteractions.isCloseRODialogDisplayed(),
                "The close RO dialog hasn't been displayed");
        closeRODialogInteractions.setReason(reason);
        closeRODialogInteractions.clickCloseROButton();
        Assert.assertTrue(closeRODialogInteractions.isCloseRODialogClosed(),
                "The close RO dialog hasn't been closed");
    }
}

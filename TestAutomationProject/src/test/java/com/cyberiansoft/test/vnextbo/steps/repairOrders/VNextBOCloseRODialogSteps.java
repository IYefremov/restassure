package com.cyberiansoft.test.vnextbo.steps.repairOrders;

import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOCloseRODialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.verifications.repairOrders.VNextBOCloseRODialogValidations;
import org.testng.Assert;

public class VNextBOCloseRODialogSteps {

    public static void closeROWithReason(String reason) {
        final VNextBORODetailsPageInteractions detailsPageInteractions = new VNextBORODetailsPageInteractions();
        final String previousStatus = detailsPageInteractions.getRoStatusValue();
        System.out.println(previousStatus);
        final VNextBOCloseRODialogInteractions closeRODialogInteractions = new VNextBOCloseRODialogInteractions();
        Assert.assertTrue(VNextBOCloseRODialogValidations.isCloseRODialogDisplayed(),
                "The close RO dialog hasn't been displayed");
        closeRODialogInteractions.setReason(reason);
        closeRODialogInteractions.clickCloseROButton();
        Assert.assertTrue(VNextBOCloseRODialogValidations.isCloseRODialogClosed(),
                "The close RO dialog hasn't been closed");
        detailsPageInteractions.updateRoStatusValue(previousStatus);
    }
}

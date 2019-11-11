package com.cyberiansoft.test.vnextbo.steps.repairorders;

import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOCloseRODialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOCloseRODialogValidations;
import org.testng.Assert;

public class VNextBOCloseRODialogSteps {

    public static void closeROWithReason(String reason) {
        final String previousStatus = VNextBORODetailsPageInteractions.getRoStatusValue();
        System.out.println(previousStatus);
        Assert.assertTrue(VNextBOCloseRODialogValidations.isCloseRODialogDisplayed(),
                "The close RO dialog hasn't been displayed");
        VNextBOCloseRODialogInteractions.setReason(reason);
        VNextBOCloseRODialogInteractions.clickCloseROButton();
        Assert.assertTrue(VNextBOCloseRODialogValidations.isCloseRODialogClosed(),
                "The close RO dialog hasn't been closed");
        VNextBORODetailsPageInteractions.updateRoStatusValue(previousStatus);
    }
}

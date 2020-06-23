package com.cyberiansoft.test.bo.validations.operations.servicerequestslist;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.operations.servicerequestslist.BOSRDetailsHeaderPanel;
import org.testng.Assert;

public class BOSRDetailsHeaderPanelValidations {

    public static void verifyCheckInButtonIsNotDisplayed() {
        Assert.assertTrue(Utils.isElementInvisible(new BOSRDetailsHeaderPanel().getCheckInButton()),
                "The 'Check-In' button has been displayed");
    }

    public static void verifyUndoCheckInButtonIsNotDisplayed() {
        Assert.assertTrue(Utils.isElementInvisible(new BOSRDetailsHeaderPanel().getUndoCheckInButton()),
                "The 'Undo Check-In' button has been displayed");
    }
}

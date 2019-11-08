package com.cyberiansoft.test.vnextbo.validations.termsconditionspolicy;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOPrivacyPolicyDialog;
import org.testng.Assert;

public class VNextBOPrivacyPolicyDialogValidations {

    public static void verifyPrivacyPolicyDialogIsClosed() {
        Assert.assertTrue(Utils.isElementNotDisplayed(new VNextBOPrivacyPolicyDialog().getPrivacyPolicyDialog()),
                "The Privacy Policy dialog hasn't been closed");
    }
}

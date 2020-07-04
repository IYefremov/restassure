package com.cyberiansoft.test.vnextbo.validations.termsconditionspolicy;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOTermsAndConditionsDialog;
import org.testng.Assert;

public class VNextBOTermsAndConditionsDialogValidations {

    public static void verifyTermsAndConditionsDialogIsClosed() {
        Assert.assertTrue(Utils.isElementNotDisplayed(new VNextBOTermsAndConditionsDialog().getTermsAndConditionsDialog()),
                "The Terms and Conditions dialog hasn't been closed");
    }
}

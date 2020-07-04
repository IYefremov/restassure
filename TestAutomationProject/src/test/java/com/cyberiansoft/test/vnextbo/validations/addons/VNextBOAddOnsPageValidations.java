package com.cyberiansoft.test.vnextbo.validations.addons;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.enums.addons.IntegrationStatus;
import com.cyberiansoft.test.vnextbo.screens.addons.VNextBOAddOnsPage;
import org.testng.Assert;

public class VNextBOAddOnsPageValidations {

    public static void verifyAddOnIntegrationStatus(String addOn, IntegrationStatus expectedStatus) {
        Assert.assertEquals(expectedStatus.name(), Utils.getText(new VNextBOAddOnsPage().getIntegrationStatus(addOn)),
                "The add-on status is not set properly");
    }
}
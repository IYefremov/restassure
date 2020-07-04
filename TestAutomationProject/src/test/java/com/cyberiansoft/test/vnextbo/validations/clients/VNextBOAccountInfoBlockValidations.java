package com.cyberiansoft.test.vnextbo.validations.clients;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOAccountInfoBlock;

public class VNextBOAccountInfoBlockValidations {

    public static boolean isPoNumberUpfrontRequiredCheckboxClickable(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeClickable(new VNextBOAccountInfoBlock().getPoNumberUpfrontRequiredCheckbox(), expected, 3);
    }
}

package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.interactions.ErrorDialogInteractions;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class GeneralValidations {
    public static void errorDialogShouldBePresent(Boolean shouldBePresent, String errorMessage) {
        WaitUtils.assertEquals(ErrorDialogInteractions.isErrorDialogPresent(), shouldBePresent);
        if (shouldBePresent && errorMessage != null)
            WaitUtils.assertEquals(ErrorDialogInteractions.getErrorDialogText(), errorMessage);
    }
}

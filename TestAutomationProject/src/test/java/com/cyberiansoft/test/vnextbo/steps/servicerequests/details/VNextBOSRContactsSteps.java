package com.cyberiansoft.test.vnextbo.steps.servicerequests.details;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.details.VNextBOSRContacts;

public class VNextBOSRContactsSteps {

    public static String getOwnerName() {
        return Utils.getText(new VNextBOSRContacts().getOwnerName());
    }

    public static String getAdvisorName() {
        return Utils.getText(new VNextBOSRContacts().getAdvisorName());
    }
}

package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;

public class RegularServiceRequestSteps {

    public static void saveServiceRequest() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.clickSave();
        RegularServiceRequestsScreen serviceRequestsScreen = new RegularServiceRequestsScreen();
        serviceRequestsScreen.waitForServiceRequestScreenLoad();
    }
}

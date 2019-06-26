package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;

public class RegularNavigationSteps {

    public static void navigateToServicesScreen() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.SERVICES);
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
    }
}

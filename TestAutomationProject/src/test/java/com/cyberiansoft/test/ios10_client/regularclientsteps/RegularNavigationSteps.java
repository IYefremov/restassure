package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularBaseAppScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularClaimScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;

public class RegularNavigationSteps {

    public static void navigateToServicesScreen() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.SERVICES);
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
    }

    public static void navigateToClaimScreen() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.CLAIM);
    }

    public static void navigateBackScreen() {
        RegularBaseAppScreen baseAppScreen = new RegularBaseAppScreen();
        baseAppScreen.clickHomeButton();
    }
}

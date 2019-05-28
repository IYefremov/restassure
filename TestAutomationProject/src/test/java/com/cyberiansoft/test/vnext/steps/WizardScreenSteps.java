package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextHelpingScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class WizardScreenSteps {

    public static void navigateToServicesScreen() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.changeScreen(ScreenType.SERVICES);
    }

    public static void navigateToVisualScreen() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.changeScreen(ScreenType.VISUAL);
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        WaitUtils.elementShouldBeVisible(visualScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(visualScreen.getRootElement());
        GeneralSteps.dismissHelpingScreenIfPresent();
    }
}

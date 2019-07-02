package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class WizardScreenSteps {
    public static void navigateToWizardScreen(ScreenType screenType) {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.changeScreen(screenType);
        WaitUtils.getGeneralWebdriverWait().until(webDriver -> {
                    WaitUtils.elementShouldBeVisible(baseWizardScreen.getShowTopBarPopover(), true);
                    return baseWizardScreen.getShowTopBarPopover().getText().contains(screenType.getScreenIdentificator());
                }
        );
        WaitUtils.elementShouldBeVisible(baseWizardScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(baseWizardScreen.getRootElement());
        GeneralSteps.dismissHelpingScreenIfPresent();
    }
}

package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;

public class ScreenNavigationSteps {
    public static void pressBackButton() {
        VNextBaseScreen vNextBaseScreen = new VNextBaseScreen();
        vNextBaseScreen.clickScreenBackButton();
    }

    public static void pressForwardButton() {
        VNextBaseScreen vNextBaseScreen = new VNextBaseScreen();
        vNextBaseScreen.clickScreenForwardButton();
    }

    public static void acceptScreen() {
        WizardScreenSteps.saveAction();
    }

    public static void pressHardwareBackButton() {
        ScreenNavigationSteps.pressBackButton();
    }
}

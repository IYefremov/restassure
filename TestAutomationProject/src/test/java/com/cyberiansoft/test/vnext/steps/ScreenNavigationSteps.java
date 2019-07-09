package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;

public class ScreenNavigationSteps {
    public static void pressBackButton() {
        VNextBaseScreen vNextBaseScreen = new VNextBaseScreen();
        vNextBaseScreen.clickScreenBackButton();
    }

    public static void pressHardwareBackButton() {
        AppiumUtils.clickHardwareBackButton();
    }
}

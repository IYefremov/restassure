package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVisualInteriorScreen;

public class RegularVisualScreenSteps {

    public static void waitVisualScreenLoaded(String visualScreenName) {
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(visualScreenName);
    }
}

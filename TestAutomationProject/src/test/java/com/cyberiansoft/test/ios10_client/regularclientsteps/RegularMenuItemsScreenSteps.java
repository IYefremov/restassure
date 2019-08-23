package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMenuScreen;

public class RegularMenuItemsScreenSteps {

    public static void closeMenuScreen() {
        RegularMenuScreen menuScreen = new RegularMenuScreen();
        menuScreen.closeMenuScreen();
    }

    public static void clickMenuItem(ReconProMenuItems menuItem) {
        RegularMenuScreen menuScreen = new RegularMenuScreen();
        menuScreen.clickMenuItem(menuItem);
    }
}

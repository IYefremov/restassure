package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MenuScreen;

public class MenuItemsScreenSteps {

    public static void closeMenuScreen() {
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.closeMenuScreen();
    }

    public static void clickMenuItem(ReconProMenuItems menuItem) {
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.clickMenuItem(menuItem);
    }
}

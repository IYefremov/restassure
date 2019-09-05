package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class ServiceDetailsInteractions {
    public static void waitPageReady() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.elementShouldBeVisible(serviceDetailsScreen.getRootElement(), true);
    }

    public static void openTechnicianMenu() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.click(serviceDetailsScreen.getTechnicianField());
    }

    public static void clickIncreaseQuantity() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.click(serviceDetailsScreen.getIncreaseQuantityButton());
    }

    public static void clickDecreaseQuantity() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.click(serviceDetailsScreen.getDecreaseQuantityButton());
    }

    public static void openPartServiceDetails() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.click(serviceDetailsScreen.getPartServiceDetailButton());
    }

    public static String getVehiclePartValue() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        return serviceDetailsScreen.getVehiclePartEditBox().getAttribute("value");
    }

    public static void openVehiclePartSelection() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.click(serviceDetailsScreen.getVehiclePartEditBox());
    }
}

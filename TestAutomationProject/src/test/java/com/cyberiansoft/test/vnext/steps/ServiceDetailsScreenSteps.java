package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.interactions.ServiceDetailsInteractions;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class ServiceDetailsScreenSteps {

    public static void changeServicePrice(String newServicePrice) {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.elementShouldBeVisible(serviceDetailsScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(serviceDetailsScreen.getRootElement());
        serviceDetailsScreen.setServiceAmountValue(newServicePrice);
    }

    public static void closeServiceDetailsScreen() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
    }


    public static void openTechniciansScreen() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.openTechnicianMenu();
    }

    public static void increaseServiceQuantity() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.clickIncreaseQuantity();

    }

    public static void decreaseServiceQuantity() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.clickDecreaseQuantity();
    }
}

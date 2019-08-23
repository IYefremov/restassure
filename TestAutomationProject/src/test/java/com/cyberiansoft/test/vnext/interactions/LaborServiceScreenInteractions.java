package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.LaborServiceScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.WebElement;

public class LaborServiceScreenInteractions {
    public static void clickAddlaborService() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.click(serviceDetailsScreen.getLaborServicesButton());
    }

    public static void clickAddPartService() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.click(serviceDetailsScreen.getPartServicesButton());
    }

    public static void switchToAvailableView() {
        LaborServiceScreen laborServiceScreen = new LaborServiceScreen();
        WaitUtils.click(laborServiceScreen.getAvailableServiceButton());
    }

    public static void selectService(String serviceName) {
        LaborServiceScreen laborServiceScreen = new LaborServiceScreen();
        WebElement desiredService = WaitUtils.getGeneralFluentWait().until((driver) -> laborServiceScreen.getServiceList().stream().filter(service -> service.getText().contains(serviceName)).findFirst().orElseThrow(() -> new RuntimeException("Service not found in list")));
        WaitUtils.click(desiredService);

    }

    public static void acceptDetailsScreen() {
        LaborServiceScreen laborServiceScreen = new LaborServiceScreen();
        WaitUtils.click(laborServiceScreen.getSaveButton());
    }
}

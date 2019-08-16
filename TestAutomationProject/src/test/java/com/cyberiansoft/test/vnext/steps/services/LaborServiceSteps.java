package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.vnext.interactions.LaborServiceScreenInteractions;
import com.cyberiansoft.test.vnext.steps.SearchSteps;

public class LaborServiceSteps {
    public static void selectService(String service) {
        LaborServiceScreenInteractions.switchToAvailableView();
        SearchSteps.textSearch(service);
        LaborServiceScreenInteractions.selectService(service);
        LaborServiceScreenInteractions.acceptDetailsScreen();
    }

    public static void addPartService() {
        LaborServiceScreenInteractions.clickAddPartService();
    }
}

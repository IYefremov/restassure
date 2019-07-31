package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.interactions.GeneralWizardInteractions;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;

public class CommonSearchSteps {
    public static void textSearch(String serviceName) {
        GeneralWizardInteractions.openSearchFilter();
        GeneralWizardInteractions.setSearchText(serviceName);
        ScreenNavigationSteps.pressHardwareBackButton();
    }
}

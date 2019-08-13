package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class LaborServiceScreenInteractions {
    public static void clickAddlaborService() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.click(serviceDetailsScreen.getLaborServicesButton());
    }
}

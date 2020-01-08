package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextUpdateWorkScreen;

public class UpdateWorkSteps {

    public static void searchRepairOrder(String searchText) {
        VNextUpdateWorkScreen updateWorkScreen = new VNextUpdateWorkScreen();
        updateWorkScreen.setSearchVehicleInfo(searchText);
        updateWorkScreen.clickSearchButton();
    }
}

package com.cyberiansoft.test.vnext.interactions.services;

import com.cyberiansoft.test.vnext.screens.wizardscreens.services.QuestionServiceListScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class QuestionServiceListInteractions {
    public static void switchToNeededToSetupView() {
        QuestionServiceListScreen questionServiceListScreen = new QuestionServiceListScreen();
        questionServiceListScreen.getNeedToSetupServiceButton().click();
    }

    public static void switchToSelectedView() {
        QuestionServiceListScreen questionServiceListScreen = new QuestionServiceListScreen();
        questionServiceListScreen.getSelectedServiceScreen().click();
    }

    public static void openServiceDetails(String serviceName) {
        QuestionServiceListScreen questionServiceListScreen = new QuestionServiceListScreen();
        WaitUtils.click(questionServiceListScreen.getServiceList()
                .stream()
                .filter(service -> service.getText().contains(serviceName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service not found " + serviceName)));
    }
}

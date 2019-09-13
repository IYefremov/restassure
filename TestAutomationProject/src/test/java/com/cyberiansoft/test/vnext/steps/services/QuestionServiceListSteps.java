package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.vnext.interactions.services.QuestionServiceListInteractions;

public class QuestionServiceListSteps {
    public static void switchToNeedToSetupServiceView() {
        QuestionServiceListInteractions.switchToNeededToSetupView();
    }

    public static void switchToSelectedServiceView() {
        QuestionServiceListInteractions.switchToSelectedView();
    }

    public static void openServiceDetails(String serviceName) {
        QuestionServiceListInteractions.openServiceDetails(serviceName);
    }
}

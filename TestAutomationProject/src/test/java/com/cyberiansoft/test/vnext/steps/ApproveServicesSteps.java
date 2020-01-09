package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.screens.VNextApproveServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextDeclineReasonScreen;

public class ApproveServicesSteps {

    public static void clickApproveAllButton() {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        approveServicesScreen.clickApproveAllButton();
        approveServicesScreen.clickApproveAllButton();
    }

    public static void clickDeclineAllButton() {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        approveServicesScreen.clickDeclineAllButton();
    }

    public static void saveApprovedServices() {
        ListSelectPageInteractions.saveListPage();
    }

    public static void selectDeclineReason(String declineReason) {
        VNextDeclineReasonScreen declineReasonScree = new VNextDeclineReasonScreen();
        declineReasonScree.selectDeclineReason(declineReason);
    }

}

package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.screens.VNextApproveServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextDeclineReasonScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ApproveServicesListElement;

public class ApproveServicesSteps {

    public static void clickApproveAllButton() {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        WaitUtils.elementShouldBeVisible(approveServicesScreen.getApproveAllBtn(), true);
        WaitUtils.click(approveServicesScreen.getApproveAllBtn());
    }

    public static void clickDeclineAllButton() {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        WaitUtils.elementShouldBeVisible(approveServicesScreen.getDeclineAllBtn(), true);
        WaitUtils.click(approveServicesScreen.getDeclineAllBtn());
    }

    public static void saveApprovedServices() {
        ListSelectPageInteractions.saveListPage();
    }

    public static void selectDeclineReason(String declineReason) {
        VNextDeclineReasonScreen declineReasonScree = new VNextDeclineReasonScreen();
        declineReasonScree.selectDeclineReason(declineReason);
    }

    public static void setServiceStatus(String serviceName, ServiceStatus serviceStatus) {
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen();
        WaitUtils.waitUntilElementIsClickable(approveServicesScreen.getRootElement());
        ApproveServicesListElement service = approveServicesScreen.getServiceElement(serviceName);
        switch (serviceStatus) {
            case APPROVED:
                service.clickApproveButton();
                break;
            case DECLINED:
            case SKIPPED:
                service.clickDeclineButton();
                break;
        }
    }

}

package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.enums.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOCurrentPhasePanelInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOCurrentPhasePanel;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class VNextBOCurrentPhasePanelValidations {

    public static boolean areStartServicesIconsDisplayedForWO(String orderNumber) {
        final List<WebElement> startServicesIcons = new VNextBOCurrentPhasePanel().getStartServicesIcons(orderNumber);
        WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver -> startServicesIcons.size() > 0);
        return Utils.areElementsDisplayed(startServicesIcons, 0);
    }

    public static void verifyOrderIsNotCompleted(String orderNumber, String phase) {
        final String status = Utils.getText(new VNextBOROWebPage().getPhaseMenu(orderNumber));
        if (status.equals(OrderMonitorServiceStatuses.COMPLETED.getValue())) {
            VNextBOROPageSteps.openRODetailsPage(orderNumber);
            final List<String> allServicesId = VNextBORODetailsPageSteps.getAllPhaseServicesId(phase);
            VNextBORODetailsPageSteps.setServiceStatusForMultipleServicesByServiceId(
                    allServicesId, OrderMonitorServiceStatuses.ACTIVE.getValue());
            VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        }
    }

    public static void verifyOrderCanBeStarted(String orderNumber) {
        if (!areStartServicesIconsDisplayedForWO(orderNumber)) {
            VNextBOCurrentPhasePanelInteractions.completeCurrentPhase(orderNumber);
        }
        WaitUtilsWebDriver.getWebDriverWait(3).until((ExpectedCondition<Boolean>) driver ->
                areStartServicesIconsDisplayedForWO(orderNumber));
    }
}

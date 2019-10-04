package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderStatuses;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularOrderMonitorScreen;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import org.testng.Assert;

public class RegularOrderMonitorScreenSteps {

    public static void setServiceStatus(ServiceData serviceData, OrderMonitorStatuses orderMonitorStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectPanel(serviceData);
        if (orderMonitorStatus.equals(OrderMonitorStatuses.COMPLETED))
            orderMonitorScreen.setCompletedServiceStatus();
    }

    public static void startWorkOrder() {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.clickStartOrderButton();
        Assert.assertTrue(Helpers.getAlertTextAndAccept().contains(AlertsCaptions.WOULD_YOU_LIKE_TO_START_REPAIR_ORDER));
    }

    public static void selectWorkOrderPhaseStatus(OrderMonitorStatuses orderPhaseStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.openOrderPhasesList();
        orderMonitorScreen.selectOrderPhaseStatus(orderPhaseStatus);
    }
}

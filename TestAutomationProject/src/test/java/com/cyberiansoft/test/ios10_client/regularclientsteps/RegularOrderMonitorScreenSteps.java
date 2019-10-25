package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.OrderMonitorData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderStatuses;
import com.cyberiansoft.test.enums.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularOrderMonitorScreen;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import org.testng.Assert;

public class RegularOrderMonitorScreenSteps {

    public static void setServiceStatus(ServiceData serviceData, OrderMonitorServiceStatuses orderMonitorServiceStatus) {
        selectServicePanel(serviceData);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        clickServiceStatusCell();
        orderMonitorScreen.selectServiceStatus(orderMonitorServiceStatus);
    }


    public static void selectServicePanel(ServiceData serviceData) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectPanel(serviceData);
    }

    public static void clickServiceStatusCell() {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.clickServiceStatusCell();
    }

    public static void clickServiceDetailsCancelButton() {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.clickServiceDetailsCancelButton();
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

    public static void changePhaseStatus(OrderMonitorData orderMonitorData, OrderMonitorStatuses orderPhaseStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        selectOrderPhase(orderMonitorData);
        orderMonitorScreen.clickChangeStatus();
        orderMonitorScreen.selectOrderPhaseStatus(orderPhaseStatus);
    }

    public static void selectOrderPhase(OrderMonitorData orderMonitorData) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectOrderPhase(orderMonitorData.getPhaseName());
    }

    public static void clickStartService() {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.clickStartService();
    }
}

package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.OrderMonitorData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.enums.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularOrderMonitorScreen;

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
    }

    public static void selectWorkOrderPhaseStatus(OrderMonitorStatuses orderPhaseStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.openOrderPhasesList();
        orderMonitorScreen.selectOrderPhaseStatus(orderPhaseStatus);
    }

    public static void changePhaseStatus(OrderMonitorData orderMonitorData, OrderMonitorStatuses orderPhaseStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        selectOrderPhase(orderMonitorData);
        clickPhaseChangeStatus();
        orderMonitorScreen.selectOrderPhaseStatus(orderPhaseStatus);
    }

    public static void clickPhaseChangeStatus() {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.clickChangeStatus();
    }

    public static void selectOrderPhase(OrderMonitorData orderMonitorData) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectOrderPhase(orderMonitorData.getPhaseName());
    }

    public static void assignTechnicianToOrderPhase(OrderMonitorData orderMonitorData, ServiceTechnician serviceTechnician) {
        selectOrderPhase(orderMonitorData);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.clickAssignTechnician();
        if (orderMonitorData.getPhaseVendor() != null)
            orderMonitorScreen.selectPhaseVendor(orderMonitorData.getPhaseVendor());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
        RegularServiceDetailsScreenSteps.saveServiceDetails();
    }

    public static void clickStartService() {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.clickStartService();
    }

}

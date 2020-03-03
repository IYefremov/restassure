package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.enums.monitor.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.enums.monitor.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularOrderMonitorScreen;
import org.testng.Assert;

public class RegularOrderMonitorScreenValidations {

    public static void verifyServiceStatus(ServiceData serviceData, OrderMonitorServiceStatuses expectedStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(serviceData), expectedStatus.getValue());
    }

    public static void verifyServiceTeamValue(ServiceData serviceData, String expectedTeamValue) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getServiceTeamValue(serviceData), expectedTeamValue);
    }

    public static void verifyServiceStatus(ServiceData serviceData, VehiclePartData vehiclePartData, OrderMonitorServiceStatuses expectedStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(serviceData, vehiclePartData), expectedStatus.getValue());
    }

    public static void verifyOrderPhaseStatus(OrderMonitorStatuses expectedStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getOrderMonitorPhaseStatusValue(), expectedStatus.getValue());
    }

    public static void verifyOrderPhaseStatus(OrderMonitorData orderMonitorData, OrderMonitorStatuses expectedStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getOrderMonitorPhaseStatusValue(orderMonitorData.getPhaseName()), expectedStatus.getValue());
    }

    public static void verifyOrderPhasePresent(OrderMonitorData orderMonitorData, boolean isPresent) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        if (isPresent)
            Assert.assertTrue(orderMonitorScreen.isOrderPhaseExists(orderMonitorData.getPhaseName()));
        else
            Assert.assertFalse(orderMonitorScreen.isOrderPhaseExists(orderMonitorData.getPhaseName()));
    }

    public static void verifyServiceCompletedDatePresent(boolean isPresent) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        if (isPresent)
            Assert.assertTrue(orderMonitorScreen.isServiceCompletedDateExists());
        else
            Assert.assertFalse(orderMonitorScreen.isServiceCompletedDateExists());
    }

    public static void verifyServiceDurationPresent(boolean isPresent) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        if (isPresent)
            Assert.assertTrue(orderMonitorScreen.isServiceDurationExists());
        else
            Assert.assertFalse(orderMonitorScreen.isServiceDurationExists());
    }

    public static void verifytartFinishDateLabelPresentForService(ServiceData serviceData, boolean isPresent) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        if (isPresent)
            Assert.assertTrue(orderMonitorScreen.isStartFinishDateLabelPresentForService(serviceData));
        else
            Assert.assertFalse(orderMonitorScreen.isStartFinishDateLabelPresentForService(serviceData));
    }

    public static void verifyDurationLabelPresentForService(ServiceData serviceData, boolean isPresent) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        if (isPresent)
            Assert.assertTrue(orderMonitorScreen.isDurationLabelPresentForService(serviceData));
        else
            Assert.assertFalse(orderMonitorScreen.isDurationLabelPresentForService(serviceData));
    }

    public static void verifyStartOrderButtonPresent(boolean isPresent) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        if (isPresent)
            Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
        else
            Assert.assertFalse(orderMonitorScreen.isStartOrderButtonExists());
    }

    public static void verifyServiceTechnicianValue(ServiceTechnician expectedTechnician) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getServiceTechnicianValue(), expectedTechnician.getTechnicianFullName());
    }

}

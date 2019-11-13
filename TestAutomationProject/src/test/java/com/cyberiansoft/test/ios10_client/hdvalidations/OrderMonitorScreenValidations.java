package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.dataclasses.OrderMonitorData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.OrderMonitorScreen;
import org.testng.Assert;

import java.util.List;

public class OrderMonitorScreenValidations {

    public static void verifyServiceStatus(ServiceData serviceData, OrderMonitorStatuses expectedStatus) {
        OrderMonitorScreen orderMonitorScreen = new OrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(serviceData), expectedStatus.getValue());
    }

    public static void verifyServicesStatus(ServiceData serviceData, OrderMonitorStatuses expectedStatus) {
        OrderMonitorScreen orderMonitorScreen = new OrderMonitorScreen();
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
        List<String> statuses = orderMonitorScreen.getPanelsStatuses(serviceData.getServiceName());
        for (String status : statuses)
            Assert.assertEquals(status, expectedStatus.getValue());
    }

    public static void verifyOrderPhaseStatus(OrderMonitorStatuses expectedStatus) {
        OrderMonitorScreen orderMonitorScreen = new OrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getOrderMonitorPhaseStatusValue(), expectedStatus.getValue());
    }

    public static void verifyOrderPhasePresent(OrderMonitorData orderMonitorData, boolean isPresent) {
        OrderMonitorScreen orderMonitorScreen = new OrderMonitorScreen();
        if (isPresent)
            Assert.assertTrue(orderMonitorScreen.isPhaseExists(orderMonitorData.getPhaseName()));
        else
            Assert.assertFalse(orderMonitorScreen.isPhaseExists(orderMonitorData.getPhaseName()));
    }
}

package com.cyberiansoft.test.vnext.validations.monitor;

import com.cyberiansoft.test.enums.monitor.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.vnext.screens.monitoring.VNextMonitorServiceDetailsScreen;
import org.testng.Assert;

public class MonitorServiceDetailsScreenValidations {

    public static void verifyServiceTechnicianValue(String expectedServiceTechnician) {
        VNextMonitorServiceDetailsScreen monitorServiceDetailsScreen = new VNextMonitorServiceDetailsScreen();
        Assert.assertTrue(monitorServiceDetailsScreen.getTechnicianFld().getAttribute("value").contains(expectedServiceTechnician),
                "Service doesn't have technician: " + expectedServiceTechnician);
    }

    public static void verifyServiceStatus(OrderMonitorServiceStatuses expectedServiceStatus) {
        VNextMonitorServiceDetailsScreen monitorServiceDetailsScreen = new VNextMonitorServiceDetailsScreen();
        Assert.assertEquals(monitorServiceDetailsScreen.getSelectStatusFld().getAttribute("value").toUpperCase(),
                expectedServiceStatus.getValue().toUpperCase());
    }
}

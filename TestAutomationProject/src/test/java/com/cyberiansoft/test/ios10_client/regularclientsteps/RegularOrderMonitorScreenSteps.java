package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularOrderMonitorScreen;

public class RegularOrderMonitorScreenSteps {

    public static void setSericeStatus(ServiceData serviceData, OrderMonitorStatuses orderMonitorStatus) {
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectPanel(serviceData);
        if (orderMonitorStatus.equals(OrderMonitorStatuses.COMPLETED))
            orderMonitorScreen.setCompletedServiceStatus();
    }
}

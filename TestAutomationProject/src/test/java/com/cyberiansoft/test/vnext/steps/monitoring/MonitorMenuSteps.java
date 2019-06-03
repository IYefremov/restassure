package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderMenuScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderSelectServiceScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MonitorMenuSteps {
    public static void selectMenuItem(MenuItems menuItem) {
        RepairOrderMenuScreen repairOrderMenuScreen = new RepairOrderMenuScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> repairOrderMenuScreen.getMenuItems().size() > 0);
        repairOrderMenuScreen.selectMenuItem(menuItem);
    }

    //TODO: Probably move to another class?
    public static void selectServices(List<ServiceData> serviceDataList) {
        RepairOrderSelectServiceScreen repairOrderSelectServiceScreen = new RepairOrderSelectServiceScreen();
        repairOrderSelectServiceScreen.selectServices(serviceDataList.stream().map(ServiceData::getServiceName).collect(Collectors.toList()));
        repairOrderSelectServiceScreen.completeScreen();
    }
}

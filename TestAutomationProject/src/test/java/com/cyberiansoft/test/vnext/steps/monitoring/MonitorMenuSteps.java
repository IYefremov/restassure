package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderSelectServiceScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MonitorMenuSteps {
    //TODO: Probably move to another class?
    public static void selectServices(List<ServiceData> serviceDataList) {
        RepairOrderSelectServiceScreen repairOrderSelectServiceScreen = new RepairOrderSelectServiceScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> repairOrderSelectServiceScreen.getServiceList().size() > 0);
        repairOrderSelectServiceScreen.selectServices(serviceDataList.stream().map(ServiceData::getServiceName).collect(Collectors.toList()));
        repairOrderSelectServiceScreen.completeScreen();
    }
}

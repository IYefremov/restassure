package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.enums.monitor.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.vnext.screens.monitoring.SelectServiceStatusScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.VNextMonitorServiceDetailsScreen;
import com.cyberiansoft.test.vnext.steps.GeneralListSteps;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import org.apache.velocity.util.StringUtils;

public class MonitorServiceDetailsScreenSteps {

    public static void changeServiceStatus(OrderMonitorServiceStatuses newServiceStatus) {
        VNextMonitorServiceDetailsScreen monitorServiceDetailsScreen = new VNextMonitorServiceDetailsScreen();
        monitorServiceDetailsScreen.getSelectStatusFld().click();
        SelectServiceStatusScreen selectServiceStatusScreen = new SelectServiceStatusScreen();
        selectServiceStatusScreen.selectServiceStatusByText(StringUtils.capitalizeFirstLetter(newServiceStatus.getValue()));

    }

    public static void changeServiceTechnician(ServiceTechnician serviceTechnician) {
        VNextMonitorServiceDetailsScreen monitorServiceDetailsScreen = new VNextMonitorServiceDetailsScreen();
        monitorServiceDetailsScreen.getTechnicianFld().click();
        GeneralListSteps.selectListItem(serviceTechnician.getTechnicianFullName());
    }

    public static void completeService() {
        VNextMonitorServiceDetailsScreen monitorServiceDetailsScreen = new VNextMonitorServiceDetailsScreen();
        monitorServiceDetailsScreen.getCompleteService().click();
        GeneralSteps.confirmDialog();
    }
}

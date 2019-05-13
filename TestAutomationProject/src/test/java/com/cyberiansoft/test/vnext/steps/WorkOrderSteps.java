package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.testng.Assert;

public class WorkOrderSteps {
    public static String createWorkOrder(WorkOrderTypes workOrderTypes) {
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(workOrderTypes);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.saveWorkOrderViaMenu();
        return woNumber;
    }

    public static void workOrderShouldBePresent(String workOrderId) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrdersScreen.switchToMyWorkordersView();
        Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderId));
    }
}

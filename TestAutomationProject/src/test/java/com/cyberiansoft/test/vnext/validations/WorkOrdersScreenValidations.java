package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderStatuses;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import org.testng.Assert;

public class WorkOrdersScreenValidations {

    public static void validateWorkOrderCustomerValue(String workOrderId, AppCustomer appCustomer) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(workOrderId), appCustomer.getFullName());
    }

    public static void validateWorkOrderPriceValue(String workOrderId, String expectedPrice) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        Assert.assertEquals(workOrdersScreen.getWorkOrderPriceValue(workOrderId), expectedPrice);
    }

    public static void validateWorkOrderStatus(String workOrderId, WorkOrderStatuses expectedStatus) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        Assert.assertEquals(workOrdersScreen.getWorkOrderStatusValue(workOrderId), expectedStatus.getWorkOrderStatusValue());
    }

    public static void validateWorkOrderExists(String workOrderId, boolean exists) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        if (exists)
            Assert.assertTrue(workOrdersScreen.isWorkOrderExists(workOrderId));
        else
            Assert.assertFalse(workOrdersScreen.isWorkOrderExists(workOrderId));
    }
}

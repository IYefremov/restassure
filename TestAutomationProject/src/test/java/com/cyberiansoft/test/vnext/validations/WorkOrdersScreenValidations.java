package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import org.testng.Assert;

public class WorkOrdersScreenValidations {

    public static void validateWorkOrderCustomerValue(String workOrderId, AppCustomer appCustomer) {
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(workOrderId), appCustomer.getFullName());
    }
}

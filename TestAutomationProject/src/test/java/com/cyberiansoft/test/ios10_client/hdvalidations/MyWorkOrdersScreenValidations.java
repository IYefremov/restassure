package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.ios10_client.hdclientsteps.MyWorkOrdersSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import org.testng.Assert;

public class MyWorkOrdersScreenValidations {

    public static void verifyWorkOrderTotalPrice(String workOrderID, String expectedTotalPrice) {
        MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderID), expectedTotalPrice);
    }

    public static void verifyWorkOrderPresent(String workOrderID, boolean isPresent) {
        MyWorkOrdersSteps.waitMyWorkOrdersLoaded();
        MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
        if (isPresent)
            Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderID));
        else
            Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(workOrderID));
    }
}

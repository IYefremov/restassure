package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import org.testng.Assert;

public class RegularMyWorkOrdersScreenValidations {

    public static void verifyWorkOrderTotalPrice(String workOrderID, String expectedTotalPrice) {
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderID), expectedTotalPrice);
    }
}

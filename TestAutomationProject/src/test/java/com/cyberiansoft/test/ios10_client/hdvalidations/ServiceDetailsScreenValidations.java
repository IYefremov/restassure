package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import org.testng.Assert;

public class ServiceDetailsScreenValidations {

    public static void verifyServicePrice(String expectedPrice) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), expectedPrice);
    }
}

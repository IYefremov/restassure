package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;

public class OrderSummaryScreenSteps {

    public static void waitOrderSummaryScreenLoaded() {
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.waitOrderSummaryScreenLoaded();
    }

    public static void setTotalSale(String totalSale) {
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(totalSale);
    }
}

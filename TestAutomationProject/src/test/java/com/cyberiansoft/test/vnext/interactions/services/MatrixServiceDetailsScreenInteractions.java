package com.cyberiansoft.test.vnext.interactions.services;

import com.cyberiansoft.test.vnext.screens.MatrixServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class MatrixServiceDetailsScreenInteractions {
    public static void selectService(String serviceName) {
        MatrixServiceDetailsScreen matrixServiceDetailsScreen = new MatrixServiceDetailsScreen();
        matrixServiceDetailsScreen.getServiceListItem(serviceName).addService();
    }

    public static void switchToSelectedServices() {
        MatrixServiceDetailsScreen matrixServiceDetailsScreen = new MatrixServiceDetailsScreen();
        WaitUtils.click(matrixServiceDetailsScreen.getSelectedButton());
    }

    public static void setPrice(String priceValue) {
        MatrixServiceDetailsScreen matrixServiceDetailsScreen = new MatrixServiceDetailsScreen();
        matrixServiceDetailsScreen.getPriceField().clear();
        matrixServiceDetailsScreen.getPriceField().sendKeys(priceValue);
    }
}

package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.partservice.MatrixServiceData;
import com.cyberiansoft.test.vnext.screens.MatrixServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.springframework.util.Assert;

public class MatrixServiceDetailsValidations {
    public static void validateServiceSelected(String serviceName) {
        MatrixServiceDetailsScreen matrixServiceDetailsScreen = new MatrixServiceDetailsScreen();
        WaitUtils.collectionSizeIsGreaterThan(matrixServiceDetailsScreen.getServiceListItems(), 0);
        Assert.notNull(matrixServiceDetailsScreen.getServiceListItem(serviceName), "Service is not present in selectedSection");
    }

    public static void validateMatrixServiceDetails(MatrixServiceData matrixServiceData) {
        MatrixServiceDetailsScreen matrixServiceDetailsScreen = new MatrixServiceDetailsScreen();
        if (matrixServiceData.getSize() != null)
            WaitUtils.assertEquals(matrixServiceData.getSize(), matrixServiceDetailsScreen.getSizeField().getAttribute("value"));
        if (matrixServiceData.getSeverity() != null)
            WaitUtils.assertEquals(matrixServiceData.getSeverity(), matrixServiceDetailsScreen.getSeverityField().getAttribute("value"));
        if (matrixServiceData.getPrice() != null)
            WaitUtils.assertEquals(matrixServiceData.getPrice(), matrixServiceDetailsScreen.getPriceField().getAttribute("value"));
    }
}

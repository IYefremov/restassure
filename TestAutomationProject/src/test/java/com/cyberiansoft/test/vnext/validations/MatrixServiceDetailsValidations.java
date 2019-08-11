package com.cyberiansoft.test.vnext.validations;


import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
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
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        if (vehiclePartData.getVehiclePartSize() != null)
            WaitUtils.assertEquals(vehiclePartData.getVehiclePartSize(), matrixServiceDetailsScreen.getSizeField().getAttribute("value"));
        if (vehiclePartData.getVehiclePartSeverity() != null)
            WaitUtils.assertEquals(vehiclePartData.getVehiclePartSeverity(), matrixServiceDetailsScreen.getSeverityField().getAttribute("value"));
        if (vehiclePartData.getVehiclePartPrice() != null)
            WaitUtils.assertEquals(vehiclePartData.getVehiclePartPrice(), matrixServiceDetailsScreen.getPriceField().getAttribute("value"));
    }
}

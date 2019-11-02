package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import org.testng.Assert;

import java.util.List;

public class ServiceDetailsScreenValidations {

    public static void verifyServicePrice(String expectedPrice) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), expectedPrice);
    }

    public static void verifyServiceDetailsAdjustmentValue(String expectedValue) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentsValue(), expectedValue);
    }

    public static void verifyServiceDetailsScreenHasVehicleParts(List<VehiclePartData> vehiclePartsData) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        for (VehiclePartData vehiclePartData : vehiclePartsData)
            Assert.assertTrue(selectedServiceDetailsScreen.getVehiclePartValue().contains(vehiclePartData.getVehiclePartName()));
    }
}

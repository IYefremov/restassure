package com.cyberiansoft.test.ios10_client.hdvalidations;

import com.cyberiansoft.test.dataclasses.ServiceTechnician;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import org.testng.Assert;

import java.util.List;

public class ServiceDetailsScreenValidations {

    public static void verifyServicePrice(String expectedPrice) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        if (expectedPrice.contains("%"))
            verifyPercentageServicePriceValue(expectedPrice);
        else
            Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(expectedPrice));
    }

    public static void verifyPercentageServicePriceValue(String expectedPrice) {
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

    public static void verifyTechnicianCellHasValue(ServiceTechnician serviceTechnician, boolean isPresent) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        if (isPresent)
            Assert.assertTrue(selectedServiceDetailsScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
        else
            Assert.assertFalse(selectedServiceDetailsScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
    }

    public static void verifyServicePriceValue(String expectedPrice) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(expectedPrice));
    }

    public static void verifyServiceDetailsPriceValue(String expectedPrice) {
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServiceDetailsPriceValue(), PricesCalculations.getPriceRepresentation(expectedPrice));
    }

}

package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import org.testng.Assert;

import java.util.List;

public class RegularSelectedServicesScreenValidations {

    public static void verifyServiceIsSelected(String serviceName, boolean isSelected) {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        if (isSelected)
            Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceName));
        else
            Assert.assertFalse(selectedServicesScreen.checkServiceIsSelected(serviceName));
    }

    public static void verifyServicesAreSelected(List<ServiceData> servicesData) {
        for (ServiceData serviceData : servicesData)
            if (serviceData.isSelected())
                verifyServiceIsSelected(serviceData.getServiceName(), true);
            else
                verifyServiceIsSelected(serviceData.getServiceName(), false);
    }

    public static void verifyServiceIsSelectedWithServicePrice(ServiceData servicesData, String expectedPriceValue) {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        if (servicesData.getVehicleParts() != null) {
            for (VehiclePartData vehiclePartData : servicesData.getVehicleParts())
                Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(servicesData.getServiceName(),
                        vehiclePartData.getVehiclePartName()), expectedPriceValue);
        } else if (servicesData.getVehiclePart() != null) {
            Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(servicesData.getServiceName(),
                    servicesData.getVehiclePart().getVehiclePartName()), expectedPriceValue);
        } else
            Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(servicesData.getServiceName()), expectedPriceValue);
    }

    public static void verifyServiceDeclinedOrSkipped(String serviceName) {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(serviceName));
    }

    public static void verifyServiceApproved(String serviceName) {
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.isServiceApproved(serviceName));
    }
}

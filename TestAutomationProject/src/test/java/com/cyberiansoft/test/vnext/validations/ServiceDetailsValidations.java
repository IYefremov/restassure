package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.vnext.interactions.ServiceDetailsInteractions;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class ServiceDetailsValidations {
    public static void verifyServicePrice(String expectedPrice) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(ServiceDetailsInteractions.getPrice(), expectedPrice);
            return true;
        });
    }

    public static void servicePartShouldBe(VehiclePartData vehiclePart) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(ServiceDetailsInteractions.getVehiclePartValue(), vehiclePart.getVehiclePartName());
            return true;
        });
    }
}

package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.vnext.interactions.ServiceDetailsInteractions;

public class ServiceDetailsValidations {
    public static void verifyServicePrice(String expectedPrice) {

    }

    public static void servicePartShouldBe(VehiclePartData vehiclePart) {
        String test = ServiceDetailsInteractions.getVehiclePartValue();
    }
}

package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.vnext.interactions.services.ServiceDetailsInteractions;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class ServiceDetailsValidations {
    public static void verifyServicePrice(String expectedPrice) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(ServiceDetailsInteractions.getPrice(), expectedPrice);
            return true;
        });
    }

    public static void verifyServiceQuantity(String expectedQuantity) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(ServiceDetailsInteractions.getQuantity(), expectedQuantity);
            return true;
        });
    }

    public static void verifyServiceNotesValue(String expectedNotesText) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(ServiceDetailsInteractions.getNotes(), expectedNotesText);
            return true;
        });
    }

    public static void servicePartShouldBe(VehiclePartData vehiclePart) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(ServiceDetailsInteractions.getVehiclePartValue(), vehiclePart.getVehiclePartName());
            return true;
        });
    }

    public static void verifyPartsServicePresent(boolean shouldBePresent) {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        Assert.assertEquals(WaitUtils.isElementPresent(serviceDetailsScreen.getPartServiceInfoTitle()), shouldBePresent);
    }

    public static void verifyUserIsOnDetailsPage() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.elementShouldBeVisible(serviceDetailsScreen.getRootElement(), true);
    }
}

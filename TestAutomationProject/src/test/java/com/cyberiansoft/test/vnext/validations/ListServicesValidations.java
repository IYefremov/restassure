package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VnextBaseServicesScreen;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ServiceListItem;
import org.testng.Assert;

import java.util.List;

public class ListServicesValidations {

    public static void verifySelectedServices(List<ServiceData> expectedServiceList) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        selectedServicesScreen.switchToSelectedServicesView();
        expectedServiceList.forEach(serviceData -> Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName())));
    }

    public static void verifyServiceSelected(String serviceName) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        selectedServicesScreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
    }

    public static void verifyServiceNotSelected(String serviceName) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        selectedServicesScreen.switchToSelectedServicesView();
        Assert.assertFalse(selectedServicesScreen.isServiceSelected(serviceName));

    }

    public static void verifyNoServicesSelected() {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.getServicesListItems().isEmpty());
    }

    public static void verifyServiceWithDescriptionSelected(String expectedServiceName, String expectedDescription) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        selectedServicesScreen.switchToSelectedServicesView();
        Assert.assertTrue(
                selectedServicesScreen.getServiceList()
                        .stream()
                        .anyMatch(
                                service -> service.getServiceName().equals(expectedServiceName)
                                        && service.getServiceDescription().equals(expectedDescription)));
    }

    public static void validateMessagePresent(Boolean shouldBePresent, String messageText) {
        VnextBaseServicesScreen servicesScreen = new VnextBaseServicesScreen();
        WaitUtils.elementShouldBeVisible(servicesScreen.getNotificationPopup(), shouldBePresent);
        Assert.assertEquals(servicesScreen.getNotificationPopup().getText(), messageText);
    }

    public static void validateAvailableServiceCount(String serviceName, Integer expectedCount) {
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.switchToAvailableServices();
        SearchSteps.textSearch(serviceName);
        ServiceListItem serviceListItem = availableServicesScreen.getServiceListItem(serviceName);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(serviceListItem.getNumberOfAddedServices(), (int) expectedCount);
            return true;
        });
    }
}

package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.webelements.ServiceListItem;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectedServicesScreenValidations {

    public static void validateSelectedServicePrice(String serviceName, String expectedPrice) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        Assert.assertEquals(selectedServicesScreen.getServiceListItem(serviceName).getServicePrice(), expectedPrice);
    }

    public static void validateNumberOfSelectedServices(String serviceName, int expectedNumber) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        Assert.assertEquals(selectedServicesScreen.getNumberOfSelectedServices(serviceName), expectedNumber);
    }

    public static void validateMatrixServiceIsSelectedForService(String serviceName, String matrixServiceName) {
        VNextSelectedServicesScreen selectedServicesScreen = new VNextSelectedServicesScreen();
        List<ServiceListItem> serviceListItems = selectedServicesScreen.getServicesList().stream().filter(listElement -> listElement.getServiceName().equals(serviceName)).collect(Collectors.toCollection(ArrayList::new));
        Assert.assertTrue(serviceListItems.stream().anyMatch(service -> service.getMatrixServiceValue().equals(matrixServiceName)));
    }
}

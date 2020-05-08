package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.interactions.services.AvailableServiceScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextGroupServicesScreen;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.GroupServiceListItem;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class AvailableServicesScreenSteps {

    public static void selectServices(List<ServiceData> serviceDataList) {
        serviceDataList.stream().map(ServiceData::getServiceName).collect(Collectors.toList()).forEach(AvailableServicesScreenSteps::selectService);
    }

    public static void selectService(String serviceName) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        switchToAvailableServices();
        SearchSteps.textSearch(serviceName);
        servicesScreen.selectSingleService(serviceName);
        WaitUtils.waitUntilElementInvisible(By.xpath("//div[@class='notifier-contaier']"));
    }

    public static void clickAddServiceButton(String serviceName) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.selectSingleService(serviceName);
    }

    public static void selectServiceGroup(String groupName) {
        VNextGroupServicesScreen groupServicesScreen = new VNextGroupServicesScreen();
        groupServicesScreen.switchToAvailableGroupServicesView();
        WaitUtils.getGeneralFluentWait().until((webdriver) -> groupServicesScreen.getGroupServiceList().size() > 0);
        WaitUtils.waitUntilElementIsClickable(groupServicesScreen.getRootElement());
        GroupServiceListItem groupServiceElement = groupServicesScreen.getGroupServiceElement(groupName);
        groupServiceElement.selectGroupService();
    }

    public static void selectService(ServiceData serviceData) {
        selectService(serviceData.getServiceName());
    }

    public static void selectServiceAndSetData(ServiceData serviceData) {
        openServiceDetails(serviceData.getServiceName());
        if (serviceData.getServicePrice() != null)
            ServiceDetailsScreenSteps.changeServicePrice(serviceData.getServicePrice());
        if (serviceData.getServiceQuantity() != null) {
            ServiceDetailsScreenSteps.changeServiceQuantity(serviceData.getServiceQuantity());
        }
    }

    public static void openServiceDetails(String serviceName) {
        AvailableServiceScreenInteractions.switchToAvailableServicesView();
        AvailableServiceScreenInteractions.openServiceDetails(serviceName);
    }

    public static void selectMatrixService(MatrixServiceData matrixServiceData) {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.selectSingleService(matrixServiceData.getMatrixServiceName());
        if (matrixServiceData.getHailMatrixName() != null) {
            VNextPriceMatrixesScreen priceMatrixesScreen = new VNextPriceMatrixesScreen();
            priceMatrixesScreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        }
    }

    public static void openServiceDetails(ServiceData serviceData) {
        AvailableServicesScreenSteps.openServiceDetails(serviceData.getServiceName());
    }

    public static void switchToAvailableServices() {
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.switchToAvalableServicesView();
    }

    public static void openServicesList() {

        new VNextAvailableServicesScreen().getAllServicesList().click();
    }
}

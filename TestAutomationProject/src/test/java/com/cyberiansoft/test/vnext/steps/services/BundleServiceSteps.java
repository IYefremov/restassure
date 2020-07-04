package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.vnext.interactions.services.BundleServiceScreenInteractions;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.CategoryScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PartNameScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PartPositionScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.SubCategoryScreenSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

public class BundleServiceSteps {
    public static void openServiceDetails(String serviceName) {
        BundleServiceScreenInteractions.selectService(serviceName);
    }

    public static void switchToSelectedServices() {
        BundleServiceScreenInteractions.switchToSelectedServices();
    }

    public static void setBundlePrice(String bundlePrice) {
        BundleServiceScreenInteractions.setBundlePrice(bundlePrice);
    }

    public static void addServiceWithPlusButton(String serviceName) {

        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen();
        servicesScreen.selectSingleService(serviceName);
        BaseUtils.waitABit(500);
    }

    public static void addPartService(PartServiceData serviceData) {

        addServiceWithPlusButton(serviceData.getServiceName());
        CategoryScreenSteps.selectCategory(serviceData.getCategory());
        SubCategoryScreenSteps.selectSubCategory(serviceData.getSubCategory());
        PartNameScreenSteps.selectPartName(serviceData.getPartName().getPartNameList().get(0));
        TopScreenPanelSteps.saveChanges();
        PartPositionScreenSteps.selectPartPosition(serviceData.getPartPosition());
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.saveChanges();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        setBundlePrice(BundleServiceScreenInteractions.getBundleServiceSelectedAmount());
        TopScreenPanelSteps.saveChanges();
    }
}

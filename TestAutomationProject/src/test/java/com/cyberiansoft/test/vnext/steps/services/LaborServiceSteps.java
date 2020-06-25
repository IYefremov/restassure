package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.vnext.interactions.services.LaborServiceScreenInteractions;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.CategoryScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PartNameScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PartPositionScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.SubCategoryScreenSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

public class LaborServiceSteps {

    public static void selectService(String service) {
        LaborServiceScreenInteractions.switchToAvailableView();
        SearchSteps.textSearch(service);
        LaborServiceScreenInteractions.selectService(service);
        LaborServiceScreenInteractions.acceptDetailsScreen();
    }

    public static void addPartService() {
        LaborServiceScreenInteractions.clickAddPartService();
    }

    public static void addPartService(PartServiceData partServiceData) {
        LaborServiceScreenInteractions.clickAddPartService();
        AvailableServicesScreenSteps.clickAddServiceButton(partServiceData.getServiceName());
        CategoryScreenSteps.selectCategory(partServiceData.getCategory());
        SubCategoryScreenSteps.selectSubCategory(partServiceData.getSubCategory());
        PartNameScreenSteps.selectPartName(partServiceData.getPartName().getPartNameList().get(0));
        TopScreenPanelSteps.saveChanges();
        PartPositionScreenSteps.selectPartPosition(partServiceData.getPartPosition());
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.saveChanges();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
    }

    public static void confirmServiceDetails() {
        LaborServiceScreenInteractions.acceptDetailsScreen();
    }
}

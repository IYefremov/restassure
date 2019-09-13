package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.partservice.PartName;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.vnext.enums.partservice.PartInfoScreenField;
import com.cyberiansoft.test.vnext.enums.partservice.PartServiceWizardScreen;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.PartInfoScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.services.LaborServiceScreenInteractions;
import com.cyberiansoft.test.vnext.screens.PartInfoScreen;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class PartServiceSteps {
    public static void selectPartService(PartServiceData partServiceData) {
        AvailableServicesScreenSteps.openServiceDetails(partServiceData.getServiceName());
        PartServiceSteps.selectpartServiceDetails(partServiceData);
    }

    public static void changeCategory(PartServiceData partServiceData) {
        WaitUtils.click(PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.CATEGORY));
        PartServiceSteps.selectpartServiceDetails(partServiceData);
    }

    public static void selectpartServiceDetails(PartServiceData partServiceData) {
        if (partServiceData.getCategory() != null)
            PartServiceSteps.selectCategory(partServiceData.getCategory());
        if (partServiceData.getSubCategory() != null)
            PartServiceSteps.selectSubCategory(partServiceData.getSubCategory());
        if (partServiceData.getPartName() != null)
            PartServiceSteps.selectPartName(partServiceData.getPartName());
        if (partServiceData.getPartPosition() != null && partServiceData.getPartPosition() != "")
            PartServiceSteps.selectPartPosition(partServiceData.getPartPosition());
    }

    public static void selectCategory(String category) {
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.CATEGORY.getValue());
        ListSelectPageInteractions.selectItem(category);
    }

    public static void selectSubCategory(String subCategory) {
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SUB_CATEGORY.getValue());
        ListSelectPageInteractions.selectItem(subCategory);
    }

    public static void selectPartName(PartName partName) {
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_NAME.getValue());
        partName.getPartNameList().forEach(ListSelectPageInteractions::selectItem);
        if (partName.getIsMultiSelect())
            PartServiceSteps.acceptDetailsScreen();
    }

    public static void selectPartPosition(String partPosition) {
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_POSITION.getValue());
        ListSelectPageInteractions.selectItem(partPosition);
    }


    public static void acceptDetailsScreen() {
        ListSelectPageInteractions.saveListPage();
    }

    public static void confirmPartInfo() {
        PartInfoScreen partInfoScreen = new PartInfoScreen();
        WaitUtils.click(partInfoScreen.getSaveButton());
        WaitUtils.elementShouldBeVisible(partInfoScreen.getRootElement(), false);
    }

    public static void addLaborService() {
        LaborServiceScreenInteractions.clickAddlaborService();
    }

    public static void switchToSelectedView() {
        ListSelectPageInteractions.switchToSelectedView();
    }
}

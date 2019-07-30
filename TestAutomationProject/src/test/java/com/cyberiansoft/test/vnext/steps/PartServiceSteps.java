package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.vnext.enums.PartInfoScreenField;
import com.cyberiansoft.test.vnext.enums.PartServiceWizardScreen;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.PartInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.ListSelectPage;
import com.cyberiansoft.test.vnext.screens.PartInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class PartServiceSteps {
    public static void selectPartService(PartServiceData partServiceData) {
        PartServiceSteps.selectPartService(
                partServiceData.getServiceName(),
                partServiceData.getCategory(),
                partServiceData.getSubCategory(),
                partServiceData.getPartName(),
                partServiceData.getPartPosition());
    }

    public static void selectPartService(String serviceName,
                                         String category,
                                         String subCategory,
                                         String partName,
                                         String partPosition) {
        ListSelectPage listPage = new ListSelectPage();

        AvailableServicesScreenSteps.openServiceDetails(serviceName);
        if (category != null) {
            ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.CATEGORY.getValue());
            ListSelectPageInteractions.selectItem(category);
        }
        if (subCategory != null) {
            ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SUB_CATEGORY.getValue());
            ListSelectPageInteractions.selectItem(subCategory);
        }
        if (partName != null && partName != "") {
            ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_NAME.getValue());
            ListSelectPageInteractions.selectItem(partName);
            WaitUtils.waitUntilElementIsClickable(listPage.getSaveButton());
            WaitUtils.click(listPage.getSaveButton());
        }
        if (partPosition != null) {
            ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_POSITION.getValue());
            ListSelectPageInteractions.selectItem(partPosition);
        }
    }

    public static void confirmPartInfo() {
        PartInfoScreen partInfoScreen = new PartInfoScreen();
        WaitUtils.click(partInfoScreen.getSaveButton());
        WaitUtils.elementShouldBeVisible(partInfoScreen.getRootElement(), false);
    }

    public static void changeCategory(PartServiceData partServiceData) {
        PartServiceSteps.changeCategory(partServiceData.getCategory(), partServiceData.getSubCategory(), partServiceData.getPartName(), partServiceData.getPartPosition());
    }

    public static void changeCategory(String category, String subCategory, String partName, String partPosition) {
        WaitUtils.click(PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.CATEGORY));
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.CATEGORY.getValue());
        ListSelectPageInteractions.selectItem(category);
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SUB_CATEGORY.getValue());
        ListSelectPageInteractions.selectItem(subCategory);
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_NAME.getValue());
        ListSelectPageInteractions.selectItem(partName);
        if (partPosition != null && partPosition != "") {
            ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_POSITION.getValue());
            ListSelectPageInteractions.selectItem(partPosition);
        }
    }

    public static void changeSubcategory(String category, String subCategory, String partName, String partPosition) {
        WaitUtils.click(PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.SUB_CATEGORY));
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.SUB_CATEGORY.getValue());
        ListSelectPageInteractions.selectItem(subCategory);
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_NAME.getValue());
        ListSelectPageInteractions.selectItem(partName);
        if (partPosition != null && partPosition != "") {
            ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_POSITION.getValue());
            ListSelectPageInteractions.selectItem(partPosition);
        }
    }

    public static void changePartName(String partName, String partPosition) {
        WaitUtils.click(PartInfoScreenInteractions.getPartInfoScreenField(PartInfoScreenField.PART_NAME));
        ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_NAME.getValue());
        ListSelectPageInteractions.selectItem(partName);
        if (partPosition != null && partPosition != "") {
            ListSelectPageInteractions.waitListPageReady(PartServiceWizardScreen.PART_POSITION.getValue());
            ListSelectPageInteractions.selectItem(partPosition);
        }
    }
}

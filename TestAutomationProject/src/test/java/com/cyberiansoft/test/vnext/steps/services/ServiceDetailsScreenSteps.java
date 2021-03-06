package com.cyberiansoft.test.vnext.steps.services;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.services.ServiceDetailsInteractions;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import com.cyberiansoft.test.vnext.steps.NotesSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceDetailsScreenSteps {

    public static void changeServicePrice(String newServicePrice) {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.elementShouldBeVisible(serviceDetailsScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(serviceDetailsScreen.getRootElement());
        serviceDetailsScreen.setServiceAmountValue(newServicePrice);
    }

    public static void changeServiceQuantity(String newServiceQuantity) {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.elementShouldBeVisible(serviceDetailsScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(serviceDetailsScreen.getRootElement());
        serviceDetailsScreen.setServiceQuantityValue(newServiceQuantity);
    }

    public static void closeServiceDetailsScreen() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
    }

    public static void openTechniciansScreen() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.openTechnicianMenu();
    }

    public static void increaseServiceQuantity() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.clickIncreaseQuantity();

    }

    public static void decreaseServiceQuantity() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.clickDecreaseQuantity();
    }

    public static void openPartServiceDetails() {
        ServiceDetailsInteractions.waitPageReady();
        ServiceDetailsInteractions.openPartServiceDetails();
    }

    public static void selectVehiclePart(VehiclePartData vehiclePart) {
        ServiceDetailsInteractions.openVehiclePartSelection();
        ListSelectPageInteractions.selectItem(vehiclePart.getVehiclePartName());
    }

    public static void selectVehicleParts(List<VehiclePartData> vehiclePart) {
        ServiceDetailsInteractions.openVehiclePartSelection();
        vehiclePart.stream()
                .map(VehiclePartData::getVehiclePartName).collect(Collectors.toList())
                .forEach(ListSelectPageInteractions::selectItem);
        ListSelectPageInteractions.saveListPage();
    }

    public static void saveServiceDetails() {
        ListSelectPageInteractions.saveListPage();
    }

    public static void openQuestionForm(String questionFormFieldName) {
        ServiceDetailsInteractions.openQuestionForm(questionFormFieldName);
    }

    public static void setServiceTextNotes(String textNote) {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.waitUntilElementIsClickable(serviceDetailsScreen.getRootElement());
        openServiceNotes();
        NotesSteps.setNoteText(textNote);
        ScreenNavigationSteps.pressBackButton();
    }

    public static void startRepairOrderService() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        WaitUtils.waitUntilElementIsClickable(serviceDetailsScreen.getRootElement());
        serviceDetailsScreen.getStartServiceBtn().click();
        GeneralSteps.confirmDialog();
    }

    public static void completeRepairOrderService() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        serviceDetailsScreen.getCompleteServiceBtn().click();
        GeneralSteps.confirmDialog();
    }

    public static void openServiceNotes() {
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        serviceDetailsScreen.clickServiceNotesOption();
    }

    public static void setPartInfo() {

        WaitUtils.click(new VNextServiceDetailsScreen().getPartServiceInfoTitle());
        ScreenNavigationSteps.acceptScreen();
    }

    public static void waitUntilScreenIsLoaded() {

        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        ConditionWaiter.create(__ -> new VNextServiceDetailsScreen().getRootElement().isDisplayed()).execute();
        ConditionWaiter.create(__ -> new VNextServiceDetailsScreen().getServiceDetailsPrice().isDisplayed()).execute();
    }

    public static void saveDetailsChanges() {

        TopScreenPanelSteps.saveChanges();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
    }
}

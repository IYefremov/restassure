package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;

import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.screens.monitoring.InfoScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.PhaseServicesScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.PhasesScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class EditOrderSteps {
    public static void verifyPhaseSelected(OrderPhaseDto phaseDto) {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.getGeneralFluentWait().until((driver -> phasesScreen.getPhaseListElements().size() > 0));
        Assert.assertTrue(phasesScreen.getPhaseListElements().stream()
                .anyMatch((listElement) ->
                        (listElement.getName().equals(phaseDto.getPhaseName()) && listElement.getStatus().equals(phaseDto.getStatus()))));
    }

    public static void completePhase(OrderPhaseDto phaseDto) {
        PhasesScreen phasesScreen = new PhasesScreen();
        phasesScreen.getPhaseElement(phaseDto.getPhaseName()).openMenu();
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
    }

    public static void switchToInfo() {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.elementShouldBeVisible(phasesScreen.getInfoScreenButton(), true);
        phasesScreen.getInfoScreenButton().click();
    }

    public static void verifyOrderInfo(OrderInfoDto expectedOrderInfo) {
        InfoScreen infoScreen = new InfoScreen();
        OrderInfoDto actualOrderInfo = new OrderInfoDto();
        WaitUtils.elementShouldBeVisible(infoScreen.getVinField(), true);
        actualOrderInfo.setVin(infoScreen.getVinField().getAttribute("value"));
        WaitUtils.elementShouldBeVisible(infoScreen.getStartedDate(), true);
        actualOrderInfo.setStartDate(infoScreen.getStartedDate().getAttribute("value"));
        Assert.assertEquals(actualOrderInfo, expectedOrderInfo);
    }

    public static void expandPhase(OrderPhaseDto phaseDto) {
        PhasesScreen phasesScreen = new PhasesScreen();
        phasesScreen.getPhaseElement(phaseDto.getPhaseName()).expandElement();
    }

    public static void openServiceMenu(ServiceData serviceData) {
        PhaseServicesScreen phaseServicesScreen = new PhaseServicesScreen();
        WaitUtils.collectionSizeIsGreaterThan(phaseServicesScreen.getServicesList(), 0);
        phaseServicesScreen.getServiceElement(serviceData.getServiceName()).openMenu();
    }

    public static void verifyServiceStatus(ServiceData serviceDto) {
        PhaseServicesScreen phaseServicesScreen = new PhaseServicesScreen();
        WaitUtils.elementShouldBeVisible(phaseServicesScreen.getRootElement(), true);
        Assert.assertEquals(phaseServicesScreen.getServiceElement(serviceDto.getServiceName()).getStatus(), serviceDto.getStatus());
    }

    public static void verifyPhaseStatus(OrderPhaseDto phaseDto) {
        PhasesScreen phasesScreen = new PhasesScreen();
        Assert.assertEquals(phasesScreen.getPhaseElement(phaseDto.getPhaseName()).getStatus(), phaseDto.getStatus());
    }

    public static void openPhaseMenu(OrderPhaseDto phaseDto) {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.collectionSizeIsGreaterThan(phasesScreen.getPhaseListElements(), 0);
        phasesScreen.getPhaseElement(phaseDto.getPhaseName()).openMenu();
    }

    public static void setOrderPriority(OrderPriority orderPriority) {
        InfoScreen infoScreen = new InfoScreen();
        infoScreen.setOrderPriority(orderPriority);
    }
}

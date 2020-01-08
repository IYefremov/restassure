package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import com.cyberiansoft.test.vnext.screens.monitoring.InfoScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.PhasesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.order.edit.PhaseElement;
import org.testng.Assert;

public class EditOrderSteps {

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
        actualOrderInfo.setStartDate(infoScreen.getStartedDate().getAttribute("value"));
        WaitUtils.elementShouldBeVisible(infoScreen.getStartedDate(), true);
        Assert.assertEquals(actualOrderInfo.getVin(), expectedOrderInfo.getVin());
        Assert.assertNotNull(actualOrderInfo.getStartDate());
    }

    public static void openPhaseMenu(OrderPhaseDto phaseDto) {
        PhaseElement phaseElement = PhaseScreenInteractions.getPhaseElement(phaseDto.getPhaseName());
        PhaseScreenInteractions.openPhaseElementMenu(phaseElement);
    }

    public static void openServiceMenu(ServiceData serviceData) {
        PhaseScreenInteractions.openServiceElementMenu(
                PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()));
    }

    public static void setOrderPriority(OrderPriority orderPriority) {
        InfoScreen infoScreen = new InfoScreen();
        infoScreen.setOrderPriority(orderPriority);
    }

    public static void switchToParts() {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.elementShouldBeVisible(phasesScreen.getPartsScreenButton(), true);
        phasesScreen.getPartsScreenButton().click();
    }

    public static void waitPhasesScreenLoaded() {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.collectionSizeIsGreaterThan(phasesScreen.getServiceElementsList(), 0);
        WaitUtils.elementShouldBeVisible(phasesScreen.getRootElement(), true);
    }
}

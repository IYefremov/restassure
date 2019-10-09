package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import com.cyberiansoft.test.vnext.screens.monitoring.InfoScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.PhasesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
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

    public static void openElementMenu(OrderPhaseDto phaseDto) {
        PhasesScreen phasesScreen = new PhasesScreen();
        BaseUtils.waitABit(1000);
        WaitUtils.collectionSizeIsGreaterThan(phasesScreen.getPhaseListElements(), 0);
        PhaseScreenInteractions.getPhaseElement(phaseDto.getPhaseName()).openMenu();
    }

    public static void openElementMenu(String phaseName) {
        OrderPhaseDto orderPhaseDto = new OrderPhaseDto();
        orderPhaseDto.setPhaseName(phaseName);
        openElementMenu(orderPhaseDto);
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
}

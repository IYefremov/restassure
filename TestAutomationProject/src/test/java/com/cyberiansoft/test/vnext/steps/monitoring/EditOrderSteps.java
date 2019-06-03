package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.monitoring.InfoScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.PhasesScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import org.testng.Assert;

public class EditOrderSteps {
    public static void verifyPhaseSelected(OrderPhaseDto phaseDto) {
        PhasesScreen phasesScreen = new PhasesScreen();
        Assert.assertTrue(phasesScreen.getPhaseListElements().stream()
                .anyMatch((listElement) ->
                        (listElement.getPhaseName().equals(phaseDto.getPhaseName()) && listElement.getPhaseStatus().equals(phaseDto.getStatus()))));
    }

    public static void completePhase(OrderPhaseDto phaseDto) {
        PhasesScreen phasesScreen = new PhasesScreen();
        phasesScreen.getPhaseElement(phaseDto.getPhaseName()).selectPhase();
        MonitorMenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
    }

    public static void switchToInfo() {
        PhasesScreen phasesScreen = new PhasesScreen();
        phasesScreen.getInfoScreenButton().click();
    }

    public static void verifyOrderInfo(OrderInfoDto expectedOrderInfo) {
        InfoScreen infoScreen = new InfoScreen();
        OrderInfoDto actualOrderInfo = new OrderInfoDto();
        actualOrderInfo.setVin(infoScreen.getVinField().getAttribute("value"));
        actualOrderInfo.setStartDate(infoScreen.getStartedDate().getAttribute("value"));
        Assert.assertEquals(actualOrderInfo, expectedOrderInfo);
    }
}

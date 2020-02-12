package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.monitoring.PhasesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.order.edit.PhaseElement;
import com.cyberiansoft.test.vnext.webelements.order.edit.ServiceElement;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public class PhaseScreenInteractions {
    public static PhaseElement getPhaseElement(String phaseName) {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            phasesScreen.getPhaseListElements().forEach(elem -> elem.getRootElement().isDisplayed());
            return true;
        });
        return WaitUtils.getGeneralFluentWait().until(driver -> phasesScreen.getPhaseListElements().stream()
                .filter((phaseElement) ->
                        phaseElement.getName().equals(phaseName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Phase element not found " + phaseName)));
    }

    public static ServiceElement getServiceElements(String phaseName) {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.collectionSizeIsGreaterThan(phasesScreen.getServiceElementsList(), 0);
        WaitUtils.elementShouldBeVisible(phasesScreen.getRootElement(), true);
        BaseUtils.waitABit(2000);
        return WaitUtils.getGeneralFluentWait().until(driver -> phasesScreen.getServiceElementsList().stream()
                .filter((serviceElement) ->
                        serviceElement.getName().equals(phaseName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service element not found " + phaseName)));
    }

    public static void openServiceElementMenu(ServiceElement phaseElement) {
        WaitUtils.click(phaseElement.getRootElement());
    }

    public static void openPhaseElementMenu(PhaseElement phaseElement) {
        WaitUtils.click(phaseElement.getRootElement());
    }

    public static String getPhasesWorkOrderId() {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.collectionSizeIsGreaterThan(phasesScreen.getServiceElementsList(), 0);
        WaitUtils.elementShouldBeVisible(phasesScreen.getRootElement(), true);
        return phasesScreen.getWorkOrderNumber().getText().trim();
    }

    public static String getPhasesVINNumber() {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.collectionSizeIsGreaterThan(phasesScreen.getServiceElementsList(), 0);
        WaitUtils.elementShouldBeVisible(phasesScreen.getRootElement(), true);
        return phasesScreen.getPhasesVINNumber().getText().trim();
    }

    public static String getPhasesStockNumber() {
        PhasesScreen phasesScreen = new PhasesScreen();
        WaitUtils.elementShouldBeVisible(phasesScreen.getRootElement(), true);
        return phasesScreen.getPhasesStockNumber().getText().trim();
    }
}

package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextTechnicianScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

//TODO: DECORATE TECHNICIAN WEBELEMENT!!!
public class TechnicianScreenInteractions {
    public static WebElement getTechnicianElement(String technicianName) {
        VNextTechnicianScreen technicianScreen = new VNextTechnicianScreen();
        WaitUtils.elementShouldBeVisible(technicianScreen.getRootElement(), true);
        return technicianScreen.getTechList().stream()
                .filter(technicianElement ->
                        technicianElement.getText().replace('\n', ' ').contains(technicianName)
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Technician with name not found " + technicianName));
    }

    public static void selectTechnician(String technicianName) {
        WaitUtils.getGeneralWebdriverWait().until(driver -> {
            getTechnicianElement(technicianName).findElement(By.xpath(".//input[@action='check-item']")).click();
            return getTechnicianElement(technicianName).findElement(By.xpath(".//input[@action='check-item']")).getAttribute("checked") != null;
        });
    }

    public static void acceptScreen() {
        WaitUtils.click(new VNextTechnicianScreen().getAcceptButton());
    }
}

package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOPreferencesBlock;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class VNextBOPreferencesBlockInteractions {

    private VNextBOPreferencesBlock preferencesBlock;

    public VNextBOPreferencesBlockInteractions() {
        preferencesBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOPreferencesBlock.class);
    }

    public void clickUseSingleWoTypeCheckbox() {
        Utils.clickElement(preferencesBlock.getUseSingleWoTypeCheckbox());
    }

    public void setDefaultArea(String option) {
        Utils.clickElement(preferencesBlock.getDefaultAreaArrow());
        Utils.selectOptionInDropDown(preferencesBlock.getDefaultAreaDropDown(),
                preferencesBlock.getDefaultAreaListBoxOptions(), option);
    }

    public void verifyPreferencesBlockIsExpanded() {
        final List<WebElement> preferencesBlockElements = Arrays.asList(
                preferencesBlock.getUseSingleWoTypeCheckbox(),
                preferencesBlock.getDefaultAreaArrow());
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(200), Duration.ofSeconds(5))
                    .until(driver -> preferencesBlockElements
                            .stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickPreferencesTab();
        }
    }
}
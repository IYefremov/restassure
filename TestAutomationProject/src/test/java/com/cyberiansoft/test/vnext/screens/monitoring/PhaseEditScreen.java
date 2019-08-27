package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PhaseEditScreen extends MonitorScreen {

    @FindBy(xpath = "//*[@data-page='complete-phase']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[contains(@class,'icon-item-content') and @action='resolve-problem']")
    private List<WebElement> phaseListElements;

    @FindBy(xpath = "//*[@action='complete-phase']")
    private WebElement completeCurrentPhaseButton;

    public WebElement getPhaseElement(String phaseName) {
        BaseUtils.waitABit(2000);
        return phaseListElements.stream()
                .filter((phaseElement) -> phaseElement.getText().contains(phaseName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Phase element not found " + phaseName));
    }
}
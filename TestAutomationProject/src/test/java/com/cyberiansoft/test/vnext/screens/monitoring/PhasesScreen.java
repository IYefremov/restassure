package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.PhaseListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PhasesScreen extends MonitorScreen {
    @FindBy(xpath = "//div[@data-autotests-id=\"phases-list\"]/div")
    private List<PhaseListElement> phaseListElements;

    @FindBy(xpath = "//span[@action=\"info\"]")
    private WebElement infoScreenButton;

    public PhasesScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }

    public void switchToInfoScreen() {
        infoScreenButton.click();
    }

    public PhaseListElement getPhaseElement(String phaseName) {
        return phaseListElements.stream()
                .filter((phaseElement) -> phaseElement.getPhaseName().equals(phaseName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Phase elemnt not found " + phaseName));
    }
}

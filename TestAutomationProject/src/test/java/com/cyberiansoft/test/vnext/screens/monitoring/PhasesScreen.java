package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.webelements.EditListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PhasesScreen extends MonitorScreen {
    @FindBy(xpath = "//div[@data-autotests-id='phases-list']/div/div")
    private List<EditListElement> phaseListElements;

    @FindBy(xpath = "//span[@action='info']")
    private WebElement infoScreenButton;

    @FindBy(xpath = "//span[@action='parts']")
    private WebElement partsScreenButton;

    @FindBy(xpath = "//span[@action='quick-actions']")
    private WebElement phasesMenuButton;

    public PhasesScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }

    public EditListElement getPhaseElement(String phaseName) {
        BaseUtils.waitABit(2000);
        return phaseListElements.stream()
                .filter((phaseElement) -> phaseElement.getName().equals(phaseName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Phase element not found " + phaseName));
    }
}

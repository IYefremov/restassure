package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.EditListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PhaseServicesScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@data-page=\"services-list\"]")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-autotests-id=\"simple-services-list\"]/div")
    private List<EditListElement> servicesList;

    public PhaseServicesScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }

    public EditListElement getServiceElement(String elementName) {
        return servicesList.stream()
                .filter((phaseElement) -> phaseElement.getName().equals(elementName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service element not found " + elementName));
    }
}

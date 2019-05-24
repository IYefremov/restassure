package com.cyberiansoft.test.vnext.screens.monitoring;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class SelectLocationScreen extends MonitorScreen {
    @FindBy(xpath = "//div[@data-page=\"locations\"]")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-autotests-id=\"locations-list\"]/div")
    private List<WebElement> locationList;

    public void selectLocationByText(String locationPartialName) {
        locationList.stream()
                .filter(locationElement -> locationElement.getText().contains(locationPartialName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Monitoring location not found: " + locationPartialName))
                .click();
    }
}

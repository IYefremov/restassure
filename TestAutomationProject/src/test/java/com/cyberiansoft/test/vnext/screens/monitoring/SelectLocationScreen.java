package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class SelectLocationScreen extends MonitorScreen {
    @FindBy(xpath = "//div[@data-page=\"locations\"]")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@data-autotests-id=\"locations-list\"]/div")
    private List<WebElement> locationList;

    public void selectLocationByText(String locationPartialName) {
        WaitUtils.click(locationList.stream()
                .filter(locationElement -> locationElement.getText().contains(locationPartialName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Monitoring location not found: " + locationPartialName)));
    }
}

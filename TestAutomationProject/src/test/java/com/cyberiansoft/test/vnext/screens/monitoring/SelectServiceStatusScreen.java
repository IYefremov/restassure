package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SelectServiceStatusScreen extends MonitorScreen {
    @FindBy(xpath = "//div[@data-page=\"change-status\"]")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@data-autotests-id=\"change-status\"]/div")
    private List<WebElement> statusList;

    public void selectServiceStatusByText(String serviceStatus) {
        WaitUtils.click(statusList.stream()
                .filter(locationElement -> locationElement.getText().contains(serviceStatus))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service status not found: " + serviceStatus)));
    }
}

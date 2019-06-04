package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class RepairOrderSelectServiceScreen extends MonitorScreen {
    @FindBy(xpath = "//span[@action=\"save\"]")
    private WebElement completeScreenCheckbox;

    @FindBy(xpath = "//div[@data-autotests-id=\"simple-services-list\"]/div")
    private List<WebElement> serviceList;

    public void completeScreen() {
        WaitUtils.click(completeScreenCheckbox);
    }

    public void selectServices(List<String> serviceListToSelect) {
        serviceListToSelect.stream().forEach((serviceName) -> serviceList.stream()
                .filter((serviceElement) -> serviceElement.getText().contains(serviceName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to find service " + serviceName))
                .click());
    }
}

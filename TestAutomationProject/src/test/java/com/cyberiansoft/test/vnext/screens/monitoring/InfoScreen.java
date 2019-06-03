package com.cyberiansoft.test.vnext.screens.monitoring;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class InfoScreen extends MonitorScreen {

    @FindBy(xpath = "//input[@data-name=\"VIN\"]")
    private WebElement vinField;

    @FindBy(xpath = "//input[@data-name=\"startDate\"]")
    private WebElement startedDate;

    @FindBy(xpath = "//span[@action=\"info\"]")
    private WebElement serviceInfo;

    public void selectServiceInfo() {
        serviceInfo.click();
    }
}

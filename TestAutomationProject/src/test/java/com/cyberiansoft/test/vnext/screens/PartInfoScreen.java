package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.screens.monitoring.MonitorScreen;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class PartInfoScreen extends MonitorScreen {
    @FindBy(xpath = "//div[@data-page='part-info']")
    private WebElement rootElement;

    @FindBy(xpath = "//span[@action='save']")
    private WebElement saveButton;
}

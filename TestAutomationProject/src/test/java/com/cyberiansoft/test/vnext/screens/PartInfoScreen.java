package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.screens.monitoring.MonitorScreen;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class PartInfoScreen extends MonitorScreen {
    @FindBy(xpath = "//div[@data-page='part-info']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-field]")
    private List<WebElement> partInfoFields;

    @FindBy(xpath = "//*[@action='save']")
    private WebElement saveButton;

}

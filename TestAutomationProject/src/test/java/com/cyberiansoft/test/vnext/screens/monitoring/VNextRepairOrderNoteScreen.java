package com.cyberiansoft.test.vnext.screens.monitoring;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class VNextRepairOrderNoteScreen extends MonitorScreen {
    @FindBy(xpath = "//div[@data-page='comment']")
    private WebElement rootElement;

    @FindBy(xpath = "//textarea[@name='comment']")
    private WebElement commentTest;
}

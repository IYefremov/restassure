package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class VNextTechnicianScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@data-page='tech-split']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-autotests-id='tech-split-list']//div[@class='entity-item']")
    private List<WebElement> techList;

    @FindBy(xpath = "//span[@action='save']")
    private WebElement acceptButton;
}

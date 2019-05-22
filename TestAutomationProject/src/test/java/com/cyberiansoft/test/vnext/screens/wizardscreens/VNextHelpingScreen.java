package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextHelpingScreen {
    @FindBy(xpath = "//div[@class='help-button' and text()='OK, got it']")
    private WebElement okDismissButton;

    public VNextHelpingScreen() {
        PageFactory.initElements(DriverBuilder.getInstance().getAppiumDriver(), this);
    }
}

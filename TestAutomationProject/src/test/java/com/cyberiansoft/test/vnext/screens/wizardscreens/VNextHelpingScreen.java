package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextHelpingScreen {
    @FindBy(xpath = "//div[@class='help-button' and text()='OK, got it']")
    private WebElement okDismissButton;

    private final By okButtonLocator = By.xpath("//div[@class='help-button' and text()='OK, got it']");

    public VNextHelpingScreen() {
        PageFactory.initElements(DriverBuilder.getInstance().getAppiumDriver(), this);
    }
}

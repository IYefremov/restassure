package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class ErrorDialog {
    @FindBy(xpath = "//div[contains(@class,'modal-error')]")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@class='modal-text']")
    private WebElement errorText;

    @FindBy(xpath = "//span[@class='modal-button ']")
    private WebElement okButton;

    public ErrorDialog() {
        PageFactory.initElements(DriverBuilder.getInstance().getAppiumDriver(), this);
    }
}

package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class ConfirmationDialog {
    @FindBy(xpath = "//div[@class=\"modal-buttons\"]/span[2]")
    private WebElement confirmDialogButton;

    @FindBy(xpath = "//div[@class=\"modal-buttons\"]/span[1]")
    private WebElement cancelDialogButton;

    public ConfirmationDialog() {
        PageFactory.initElements(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), this);
    }
}

package com.cyberiansoft.test.vnext.screens.commonobjects.dialogs;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.Button;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextWarningDialog extends VNextBaseScreen {

    @FindBy(xpath = "//body/div[contains(@class, 'modal-in')]")
    private WebElement modaldlg;

    @FindBy(xpath = "//span[contains(text(),\"Don't save\")]")
    private Button dontSaveButton;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    private Button saveButton;

    @FindBy(xpath = "//span[contains(text(),'Yes')]")
    private Button yesButton;

    @FindBy(xpath = "//span[contains(text(),'Ok')]")
    private Button okButton;

    public VNextWarningDialog() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }
}

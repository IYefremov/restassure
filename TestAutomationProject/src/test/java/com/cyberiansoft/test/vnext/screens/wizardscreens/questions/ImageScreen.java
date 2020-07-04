package com.cyberiansoft.test.vnext.screens.wizardscreens.questions;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.DatePicker;
import com.cyberiansoft.test.vnext.webelements.GeneralQuestion;
import com.cyberiansoft.test.vnext.webelements.SignatureCanvas;
import com.cyberiansoft.test.vnext.webelements.TextQuestion;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class ImageScreen extends VNextBaseWizardScreen {

    @FindBy(xpath = "//div[contains(@class,'images-swiper')]")
    private WebElement rootElement;

    @FindBy(xpath = ".//span[@class='slide-action slide-remove']")
    private WebElement removeButton;

    public ImageScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }
}

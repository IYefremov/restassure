package com.cyberiansoft.test.vnext.screens.commonobjects;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextNothingFoundScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@class='searchlist-nothing-found']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@class='nothing-found-content-text']/b")
    private WebElement contentMessage;

    public VNextNothingFoundScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }
}
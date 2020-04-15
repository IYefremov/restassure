package com.cyberiansoft.test.vnext.screens.commonobjects;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextTopScreenPanel extends VNextBaseScreen {

    @FindBy(xpath = "//span[@action='back']")
    private WebElement backButton;

    @FindBy(xpath = "//*[@data-automation-id='search-icon']")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@data-autotests-id='search-input']")
    private WebElement searchField;

    @FindBy(xpath = "//*[@data-autotests-id='search-cancel']")
    private WebElement cancelSearchButton;

    @FindBy(xpath = "//*[@data-automation-id='search-clear']")
    private WebElement clearSearchIcon;

    @FindBy(xpath = "//span[@action='from-device-contact']")
    private WebElement fromDeviceContactsIcon;

    @FindBy(xpath = "//span[@action='save']")
    private WebElement saveIcon;

    public VNextTopScreenPanel() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }
}
package com.cyberiansoft.test.vnext.screens.commonobjects;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.Button;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextTopScreenPanel extends VNextBaseScreen {

    @FindBy(xpath = "//span[@action='back']")
    private Button backButton;

    @FindBy(xpath = "//*[@data-automation-id='search-icon']")
    private Button searchButton;

    @FindBy(xpath = "//*[@data-autotests-id='search-input']")
    private WebElement searchField;

    @FindBy(xpath = "//*[@data-autotests-id='search-cancel']")
    private Button cancelSearchButton;

    @FindBy(xpath = "//*[@data-automation-id='search-clear']")
    private Button clearSearchIcon;

    @FindBy(xpath = "//span[@action='from-device-contact']")
    private WebElement fromDeviceContactsIcon;

    @FindBy(xpath = "//span[@action='save']")
    private Button saveButton;

    public VNextTopScreenPanel() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }
}
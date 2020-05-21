package com.cyberiansoft.test.vnextbo.screens.commonobjects;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOReactSearchPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@class='SearchInput']")
    private WebElement searchPanel;

    @FindBy(xpath = "//input[@class='SearchInput-input']")
    private WebElement searchInputField;

    @FindBy(xpath = "//div[@class='SearchInput-loop']")
    private WebElement searchLoupeIcon;

    @FindBy(xpath = "//div[contains(@class, 'SearchInput-arrow')]")
    private WebElement advancedSearchCaret;

    @FindBy(xpath = "//div[contains(@class, 'SearchInput-reset')]")
    private WebElement searchXIcon;

    @FindBy(xpath = "//div[@class='SearchInput-text']")
    private WebElement filterInfoText;

    public VNextBOReactSearchPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
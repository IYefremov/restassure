package com.cyberiansoft.test.vnextbo.screens.commonObjects;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOSearchPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//section[@class='view']//div[contains(@id, 'search-form')]")
    private WebElement searchPanel;

    @FindBy(xpath = "//section[@class='view']//input[contains(@id, 'search-freeText')]")
    private WebElement searchInputField;

    @FindBy(xpath = "//section[@class='view']//i[contains(@id, 'search-search')]")
    private WebElement searchLoupeIcon;

    @FindBy(xpath = "//section[@class='view']//i[contains(@id, 'search-caret')]")
    private WebElement advancedSearchCaret;

    @FindBy(xpath = "//section[@class='view']//i[@class='icon-cancel-circle']")
    private WebElement searchXIcon;

    @FindBy(xpath = "//section[@class='view']//div[@data-bind='text: filterInfoString']")
    private WebElement filterInfoText;

    public VNextBOSearchPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
package com.cyberiansoft.test.vnextbo.screens.clients;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOClientsSearchPanel extends VNextBOClientsWebPage {

    @FindBy(id = "clients-search-form")
    private WebElement searchPanel;

    @FindBy(id = "clients-search-freeText")
    private WebElement searchInputField;

    @FindBy(id = "clients-search-search")
    private WebElement searchLoupeIcon;

    @FindBy(xpath = "//div[@id='clients-search']//i[@class='icon-cancel-circle']")
    private WebElement searchXIcon;

    public VNextBOClientsSearchPanel() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
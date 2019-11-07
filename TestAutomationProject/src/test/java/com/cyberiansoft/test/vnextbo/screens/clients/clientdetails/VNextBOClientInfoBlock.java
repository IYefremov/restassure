package com.cyberiansoft.test.vnextbo.screens.clients.clientDetails;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOClientInfoBlock extends VNextBOClientsDetailsViewAccordion {

    @FindBy(xpath = "//div[@id='collapseClientInfo']/div[@class='panel-body']/div")
    private WebElement clientInfoPanel;

    @FindBy(id = "clientEditForm-client-type-retail")
    private WebElement retailRadioButton;

    @FindBy(id = "clientEditForm-client-type-wholesale")
    private WebElement wholesaleRadioButton;

    @FindBy(id = "clientEditForm-client-companyName")
    private WebElement companyInputField;

    @FindBy(id = "clientEditForm-client-firstname")
    private WebElement firstNameInputField;

    @FindBy(id = "clientEditForm-client-lastname")
    private WebElement lastNameInputField;

    @FindBy(id = "clientEditForm-client-email")
    private WebElement emailInputField;

    @FindBy(id = "clientEditForm-client-phone")
    private WebElement phoneInputField;

    public VNextBOClientInfoBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
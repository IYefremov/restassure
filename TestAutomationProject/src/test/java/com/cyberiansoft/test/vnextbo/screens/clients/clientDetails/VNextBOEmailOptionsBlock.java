package com.cyberiansoft.test.vnextbo.screens.clients.clientDetails;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOEmailOptionsBlock extends VNextBOClientsDetailsViewAccordion {

    @FindBy(xpath = "//div[@id='collapseEmailOptions']/div[@class='panel-body']/div")
    private WebElement emailOptionsPanel;

    @FindBy(id = "clientEditForm-email-default-recepient")
    private WebElement defaultRecipientInputField;

    @FindBy(id = "clientEditForm-email-CC")
    private WebElement ccInputField;

    @FindBy(id = "clientEditForm-email-BCC")
    private WebElement bccInputField;

    @FindBy(id = "invoiceAutoEmailing")
    private WebElement invoicesCheckbox;

    @FindBy(id = "inspectionAutoEmailing")
    private WebElement inspectionsCheckbox;

    @FindBy(id = "includeInspectionAutoEmailing")
    private WebElement includeInspectionCheckbox;

    public VNextBOEmailOptionsBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
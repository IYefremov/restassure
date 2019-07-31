package com.cyberiansoft.test.vnextbo.screens.clients.clientDetails;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOMiscellaneousBlock extends VNextBOClientsDetailsViewAccordion {

    @FindBy(id = "clientEditForm-client-notes")
    private WebElement notesField;

    public VNextBOMiscellaneousBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
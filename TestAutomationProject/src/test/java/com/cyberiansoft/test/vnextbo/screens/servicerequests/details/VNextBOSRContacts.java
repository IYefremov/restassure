package com.cyberiansoft.test.vnextbo.screens.servicerequests.details;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOSRContacts extends VNextBOSRDetailsPage {

    @FindBy(xpath = "//span[contains(text(), 'Customer name:')]/parent::div")
    private WebElement customerName;

    @FindBy(xpath = "//span[contains(text(), 'Owner name:')]/parent::div/following-sibling::div[contains(@class, 'rowInfo')]")
    private WebElement ownerName;

    @FindBy(xpath = "//span[contains(text(), 'Advisor name:')]/parent::div/following-sibling::div[contains(@class, 'rowInfo')]")
    private WebElement advisorName;

    public VNextBOSRContacts() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

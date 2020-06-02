package com.cyberiansoft.test.vnextbo.screens.servicerequests.details;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOSRHeader extends VNextBOSRDetailsPage {

    @FindBy(xpath = "//div[text()='back']")
    private WebElement backButton;

    public VNextBOSRHeader() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

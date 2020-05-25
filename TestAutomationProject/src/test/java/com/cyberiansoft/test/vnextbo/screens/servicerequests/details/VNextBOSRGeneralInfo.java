package com.cyberiansoft.test.vnextbo.screens.servicerequests.details;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOSRGeneralInfo extends VNextBOSRDetailsPage {

    @FindBy(xpath = "//span[contains(text(), 'Type:')]/parent::div")
    private WebElement type;

    @FindBy(xpath = "//span[contains(text(), 'Team:')]/parent::div")
    private WebElement team;

    @FindBy(xpath = "//span[contains(text(), 'Assigned:')]/parent::div")
    private WebElement assigned;

    @FindBy(xpath = "//span[contains(text(), 'Created Time:')]/parent::div")
    private WebElement createdTime;

    @FindBy(xpath = "//span[text()='RO#: ']/parent::div")
    private WebElement roNum;

    @FindBy(xpath = "//span[text()='Stock#: ']/parent::div")
    private WebElement stockNum;

    @FindBy(xpath = "//span[contains(text(), 'Suggested Start:')]/parent::div")
    private WebElement suggestedStart;

    @FindBy(xpath = "//span[contains(text(), 'Suggested Finish:')]/parent::div")
    private WebElement suggestedFinish;

    public VNextBOSRGeneralInfo() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
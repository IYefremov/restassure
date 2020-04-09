package com.cyberiansoft.test.vnextbo.screens.reports;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOTimeReportDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@class, 'Modal--show')]/div[text()='TimeReport']")
    private WebElement dialogHeader;

    @FindBy(id = "TimeZoneId")
    private WebElement timeZone;

    @FindBy(id = "TeamId")
    private WebElement team;

    @FindBy(id = "EmployeeId")
    private WebElement employee;

    @FindBy(xpath = "//label[text()='From' and contains(@class, 'FormElement-label')]/..//input")
    private WebElement fromInputField;

    @FindBy(xpath = "//label[text()='To' and contains(@class, 'FormElement-label')]/..//input")
    private WebElement toInputField;

    @FindBy(id = "ToEmailRecipient")
    private WebElement emailInputField;

    @FindBy(id = "ToPhoneRecipient")
    private WebElement phoneInputField;

    @FindBy(id = "ToEmail")
    private WebElement emailCheckbox;

    @FindBy(id = "ToPhone")
    private WebElement phoneCheckbox;

    @FindBy(xpath = "//div[@class='FormContainer-footer']//button/div[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[@class='FormContainer-footer']//button/div[text()='Generate Report']")
    private WebElement generateReportButton;

    //todo change the locator after Anton adds the id
    @FindBy(xpath = "//input[@class='DatePicker-input'][1]/../../../..//span[@class='FormElement-validationError']")
    private WebElement fromDateValidationError;

    //todo change the locator after Anton adds the id
    @FindBy(xpath = "//input[@class='DatePicker-input'][2]/../../../..//span[@class='FormElement-validationError']")
    private WebElement toDateValidationError;

    public VNextBOTimeReportDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getCloseButton() {
        return dialogHeader.findElement(By.xpath("./button[contains(@class, 'Modal-close')]"));
    }

    public WebElement getEmailErrorMessage() {
        return emailInputField.findElement(By.xpath("./following-sibling::span"));
    }

    public WebElement getPhoneErrorMessage() {
        return phoneInputField.findElement(By.xpath("./following-sibling::span"));
    }
}

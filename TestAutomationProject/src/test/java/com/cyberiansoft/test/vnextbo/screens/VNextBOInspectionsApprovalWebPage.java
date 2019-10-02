package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class VNextBOInspectionsApprovalWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//p/button[@type='submit' and contains(@class, 'btn icon ok')]")
    private WebElement approveServiceButton;

    @FindBy(xpath = "//p/button[@type='submit' and contains(@class, 'btn icon cancel')]")
    private WebElement declineServiceButton;

    @FindBy(xpath = "//button[@type='submit' and contains(@class, 'btn icon skip middle')]")
    private WebElement skipServiceButton;

    @FindBy(xpath = "//button[contains(@onclick, 'approoveAndComplete')]")
    private WebElement approveAndCompleteServiceButton;

    @FindBy(xpath = "//button[@class='btn icon ok middle' and @value='SaveWholeEstimation']")
    private WebElement generalApproveButton;

    @FindBy(xpath = "//button[@class='btn icon save middle' and @value='SaveWholeEstimation']")
    private WebElement saveButton;

    @FindBy(xpath = "//input[@id='tbPO']")
    private WebElement poNumberField;

    @FindBy(xpath = "//textarea[@id='txtAreaNotes2']")
    private WebElement notesTextArea;

    @FindBy(xpath = "//input[@id='cbSelectAll']")
    private WebElement selectAllCheckbox;

    @FindBy(xpath = "//span[contains(text(), 'Inspection Status')]/b")
    private WebElement inspectionStatus;


    public VNextBOInspectionsApprovalWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(generalApproveButton));
    }

    public boolean isApproveServiceButtonDisplayed() {
        return Utils.isElementDisplayed(approveServiceButton);
    }

    public boolean isDeclineServiceButtonDisplayed() {
        return Utils.isElementDisplayed(declineServiceButton);
    }

    public boolean isSkipServiceButtonDisplayed() {
        return Utils.isElementDisplayed(skipServiceButton);
    }

    public boolean isApproveAndCompleteServiceButtonDisplayed() {
        return Utils.isElementDisplayed(approveAndCompleteServiceButton);
    }

    public boolean isSaveButtonDisplayed() {
        return Utils.isElementDisplayed(saveButton);
    }

    public boolean isGeneralApproveButtonDisplayed() {
        return Utils.isElementDisplayed(generalApproveButton);
    }

    public boolean isPoNumberFieldDisplayed() {
        return Utils.isElementDisplayed(poNumberField);
    }

    public boolean isSelectAllCheckBoxDisplayed() {
        return Utils.isElementDisplayed(selectAllCheckbox);
    }

    public boolean isNotesTextAreaDisplayed() {
        return Utils.isElementDisplayed(notesTextArea);
    }
}
package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOInspectionsApprovalWebPage extends VNextBOBaseWebPage {

    //1st variant of the inspection print page
    @FindBy(xpath = "//p/button[@type='submit' and contains(@class, 'btn icon ok')]")
    private WebElement approveButton;

    @FindBy(id = "txtAreaNotes")
    private WebElement notesTextArea;

    @FindBy(id = "btnApprove")
    private WebElement confirmApproveButton;

    @FindBy(xpath = "//span[contains(text(), 'Inspection Status')]/b")
    private WebElement inspectionStatus;


    //2nd variant of the inspection print page
    @FindBy(xpath = "//button[contains(@onclick, 'approoveAndComplete')]")
    private WebElement approveAndCompleteButton;

    public VNextBOInspectionsApprovalWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
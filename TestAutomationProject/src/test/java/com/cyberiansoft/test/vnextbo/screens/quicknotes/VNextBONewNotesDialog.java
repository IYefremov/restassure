package com.cyberiansoft.test.vnextbo.screens.quicknotes;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class VNextBONewNotesDialog extends VNextBOBaseWebPage {

    @FindBy(id = "quick-note-form")
    private WebElement dialogContent;

    @FindBy(id = "quick-notes-description")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//label[@for='quick-notes-description']")
    private WebElement descriptionLabel;

    @FindBy(xpath = "//button[@data-automation-id='quick-notes-add-btn' and contains(@data-bind, 'click: add')]")
    private WebElement addButton;

    @FindBy(xpath = "//button[@data-automation-id='quick-notes-add-btn' and contains(@data-bind, 'click: update')]")
    private WebElement updateButton;

    @FindBy(xpath = "//div[@id='quick-note-form']//button[@class='close']")
    private WebElement closeButton;

    @FindBy(xpath = "//span[text()='Text is required']")
    private WebElement errorMessage;

    public VNextBONewNotesDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        wait.until(ExpectedConditions.visibilityOf(dialogContent));
    }
}

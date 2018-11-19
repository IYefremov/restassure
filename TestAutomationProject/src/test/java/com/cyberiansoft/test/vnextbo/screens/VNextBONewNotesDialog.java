package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBONewNotesDialog extends VNextBOBaseWebPage {

    @FindBy(id = "quick-note-form")
    private WebElement notesDialog;

    @FindBy(id = "quick-notes-description")
    private WebElement quickNotesDescription;

    @FindBy(xpath = "//button[@data-automation-id='quick-notes-add-btn' and contains(@data-bind, 'click: add')]")
    private WebElement quickNotesDialogAddButton;

    @FindBy(xpath = "//button[@data-automation-id='quick-notes-add-btn' and contains(@data-bind, 'click: update')]")
    private WebElement quickNotesDialogUpdateButton;

    @FindBy(xpath = "//div[@id='quick-note-form']//button[@data-dismiss='modal']")
    private WebElement quickNotesDialogCloseButton;

    @FindBy(xpath = "//span[text()='Text is required']")
    private WebElement quickNotesErrorMessage;

    public VNextBONewNotesDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        wait.until(ExpectedConditions.visibilityOf(notesDialog));
    }

    public VNextBONewNotesDialog typeDescription(String description) {
//        waitShort.until(ExpectedConditions.elementToBeClickable(quickNotesDescription)).clear();
        quickNotesDescription.clear();
        quickNotesDescription.sendKeys(description);
        waitABit(1000);
        return this;
    }

    public VNextBOQuickNotesWebPage clickQuickNotesDialogAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(quickNotesDialogAddButton)).click();
        waitForLoading();
        waitABit(1500);
        return PageFactory.initElements(driver, VNextBOQuickNotesWebPage.class);
    }

    public VNextBOQuickNotesWebPage clickQuickNotesDialogUpdateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(quickNotesDialogUpdateButton)).click();
        waitForLoading();
        waitABit(1500);
        return PageFactory.initElements(driver, VNextBOQuickNotesWebPage.class);
    }

    public VNextBOQuickNotesWebPage clickQuickNotesDialogCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(quickNotesDialogCloseButton)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOQuickNotesWebPage.class);
    }

    public boolean isQuickNotesDescriptionErrorMessageDisplayed() {
        return wait.until(ExpectedConditions.elementToBeClickable(quickNotesErrorMessage)).isDisplayed();
    }
}

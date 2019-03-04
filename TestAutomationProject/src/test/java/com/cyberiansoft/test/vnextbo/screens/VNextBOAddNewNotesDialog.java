package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOAddNewNotesDialog extends VNextBOBaseWebPage {

    @FindBy(id = "quick-note-form")
    private WebElement addNewNotesDialog;

    @FindBy(id = "quick-notes-description")
    private WebElement quickNotesDescription;

    @FindBy(xpath = "//button[@data-bind='click: add, invisible: updateMode']")
    private WebElement quickNotesDialogAddButton;

    @FindBy(xpath = "//div[@id='quick-note-form']//button[@data-dismiss='modal']")
    private WebElement quickNotesDialogCloseButton;

    @FindBy(xpath = "//span[text()='Text is required']")
    private WebElement quickNotesErrorMessage;

    public VNextBOAddNewNotesDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        wait.until(ExpectedConditions.visibilityOf(addNewNotesDialog));
    }

    public VNextBOAddNewNotesDialog typeDescription(String description) {
        waitShort.until(ExpectedConditions.elementToBeClickable(quickNotesDescription)).clear();
        quickNotesDescription.sendKeys(description);
        return this;
    }

    public VNextBOQuickNotesWebPage clickQuickNotesDialogAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(quickNotesDialogAddButton)).click();
        waitForLoading();
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

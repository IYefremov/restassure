package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBOOrderServiceNotesDialog extends VNextBOBaseWebPage {

    @FindBy(id = "editOrderServiceNotesModal")
    private WebElement editNotesModalDialog;

    @FindBy(xpath = "//div[@id='editOrderServiceNotesModal']//textarea[contains(@data-bind, 'newNote')]")
    private WebElement notesMessageField;

    @FindBy(xpath = "//div[@id='editOrderServiceNotesModal']//button[text()='Add']")
    private WebElement notesAddButton;

    @FindBy(xpath = "//div[@id='editOrderServiceNotesModal']//button[@aria-label='Close']")
    private WebElement notesXbutton;

    @FindBy(xpath = "//div[@id='editOrderServiceNotesModal']//ul[@data-role='listview']/li")
    private List<WebElement> notesList;

    @FindBy(xpath = "//ul[@data-template='order-service-note-template']/li")
    private List<WebElement> notesTextList;

    public VNextBOOrderServiceNotesDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isNotesDialogDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(editNotesModalDialog));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public VNextBOOrderServiceNotesDialog typeNotesMessage(String message) {
        wait.until(ExpectedConditions.elementToBeClickable(notesMessageField)).click();
        notesMessageField.clear();
        notesMessageField.sendKeys(message);
        return this;
    }

    public VNextBORepairOrderDetailsPage clickNotesAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(notesAddButton)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBORepairOrderDetailsPage.class);
    }

    public VNextBORepairOrderDetailsPage clickNotesXbutton() {
        wait.until(ExpectedConditions.elementToBeClickable(notesXbutton)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBORepairOrderDetailsPage.class);
    }

    public int getNotesListNumber() {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElements(notesList)).size();
        } catch (Exception ignored) {
            return 0;
        }
    }

    public List<String> getNotesListValues() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(notesTextList)).size();
            return notesTextList.stream().map(WebElement::getText).collect(Collectors.toList());
        } catch (Exception ignored) {
            return null;
        }
    }
}

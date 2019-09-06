package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBOOrderServiceNotesDialog extends VNextBOBaseWebPage {

    @FindBy(id = "editOrderServiceNotesModal")
    private WebElement editNotesModalDialog;

    @FindBy(className = "repair-notes__list")
    private WebElement repairNotesBlock;

    @FindBy(xpath = "//div[@id='editOrderServiceNotesModal']//textarea[contains(@data-bind, 'newNote')]")
    private WebElement notesMessageField;

    @FindBy(xpath = "//div[@id='editOrderServiceNotesModal']//button[text()='Add']")
    private WebElement notesAddButton;

    @FindBy(xpath = "//button[@data-bind='click: addNote']")
    private WebElement addNewNoteButton;

    @FindBy(xpath = "//div[@id='editOrderServiceNotesModal']//button[@aria-label='Close']")
    private WebElement notesXbutton;

    @FindBy(xpath = "//button[@data-bind='click: clearNote']")
    private WebElement repairNotesXbutton;

    @FindBy(xpath = "//div[@id='editOrderServiceNotesModal']//ul[@data-role='listview']/li")
    private List<WebElement> notesList;

    @FindBy(xpath = "//div[contains(@class, 'content__notes__note__text')]")
    private List<WebElement> repairNotesList;

    @FindBy(className = "note-text")
    private WebElement repairNoteTextArea;

    @FindBy(xpath = "//div[@data-bind='text: title']")
    private WebElement repairNoteServiceTextTitle;

    @FindBy(xpath = "//div[@id='repairNotes']//button[@class='close']")
    private WebElement repairNoteDialogCloseButton;

    @FindBy(xpath = "//button[contains(@data-bind, 'saveNote')]")
    private WebElement repairNoteSaveButton;

    @FindBy(xpath = "//ul[@data-template='order-service-note-template']/li")
    private List<WebElement> notesTextList;

    @FindBy(xpath = "//div[@data-template='notesItem']//div[contains(@class, 'content__notes__note__text')]")
    private List<WebElement> repairNotesTextList;

    public VNextBOOrderServiceNotesDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isNotesDialogDisplayed() {
        return Utils.isElementDisplayed(editNotesModalDialog);
    }

    public boolean isRepairNotesBlockDisplayed() {
        return Utils.isElementDisplayed(repairNotesBlock);
    }

    public VNextBOOrderServiceNotesDialog typeRepairNotesMessage(String message) {
        Utils.clearAndType(repairNoteTextArea, message);
        clickRepairNoteServiceTitle();
        WaitUtilsWebDriver.waitABit(1000);
//        waitUntilRepairNoteTextContainsValue(message);
        return this;
    }

    public void clickAddNewNoteButton() {
        Utils.clickElement(addNewNoteButton);
    }

    public void clickRepairNoteSaveButton() {
        Utils.clickElement(repairNoteSaveButton);
        WaitUtilsWebDriver.waitForInvisibility(repairNoteSaveButton);
    }

    public VNextBOOrderServiceNotesDialog openRepairNoteTextArea() {
        if (!isRepairNoteTextAreaDisplayed()) {
            clickAddNewNoteButton();
        }
        return this;
    }

    public String getRepairNoteTextAreaValue() {
        try {
            return WaitUtilsWebDriver.waitForVisibility(repairNoteTextArea, 4).getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public void waitUntilRepairNoteTextContainsValue(String value) {
        try {
            WaitUtilsWebDriver.getShortWait().until(driver -> repairNoteTextArea.getText().equals(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRepairNoteTextAreaDisplayed() {
        return Utils.isElementDisplayed(repairNoteTextArea, 5);
    }

    public VNextBORepairOrderDetailsPage clickNotesAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(notesAddButton)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBORepairOrderDetailsPage.class);
    }

    public VNextBORepairOrderDetailsPage clickRepairNotesXbutton() {
        Utils.clickElement(repairNotesXbutton);
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

    public VNextBORepairOrderDetailsPage closeRepairNoteDialog() {
        Utils.clickElement(repairNoteDialogCloseButton);
        return PageFactory.initElements(driver, VNextBORepairOrderDetailsPage.class);
    }

    public int getRepairNotesListNumber() {
        try {
            return Objects
                    .requireNonNull(WaitUtilsWebDriver
                    .waitForVisibilityOfAllOptionsIgnoringException(repairNotesList, 10))
                    .size();
        } catch (Exception ignored) {
            return 0;
        }
    }

    public List<String> getRepairNotesListValues() {
        try {
            WaitUtilsWebDriver.waitForVisibilityOfAllOptions(repairNotesList).size();
            return repairNotesList.stream().map(WebElement::getText).collect(Collectors.toList());
        } catch (Exception ignored) {
            return null;
        }
    }

    public VNextBOOrderServiceNotesDialog clickRepairNoteServiceTitle() {
        Utils.clickElement(repairNoteServiceTextTitle);
        return this;
    }
}
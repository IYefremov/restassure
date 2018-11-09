package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOQuickNotesWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//button[@data-bind='click: onAddNewQuickNoteClick']")
    private WebElement addQuickNotesButton;

    @FindBy(xpath = "//div[@data-bind='text: noteText']")
    private List<WebElement> quickNotes;

    public VNextBOQuickNotesWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        wait.until(ExpectedConditions.visibilityOf(addQuickNotesButton));
    }

    public VNextBOAddNewNotesDialog clickAddNotesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addQuickNotesButton)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOAddNewNotesDialog.class);
    }

    public boolean isQuickNoteDisplayed(String quickNoteName) {
        wait.until(ExpectedConditions.visibilityOfAllElements(quickNotes));
        return quickNotes
                .stream()
                .anyMatch(e -> e.getText().equals(quickNoteName));
    }

    public int getNumberOfQuickNotesDisplayed(String quickNoteName) {
        wait
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(quickNotes));
        return quickNotes
                .stream()
                .filter(e -> e.getText().equals(quickNoteName))
                .collect(Collectors.toList())
                .size();
    }

    public int getNumberOfQuickNotesDisplayed() {
        return wait
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(quickNotes)).size();
    }
}

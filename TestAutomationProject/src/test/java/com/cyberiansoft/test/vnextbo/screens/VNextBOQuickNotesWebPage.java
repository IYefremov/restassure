package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

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

    public VNextBONewNotesDialog clickAddNotesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addQuickNotesButton)).click();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBONewNotesDialog.class);
    }

    public boolean isQuickNoteDisplayed(String quickNoteName) {
        return getQuickNotesList()
                .stream()
                .anyMatch(e -> e.getText().equals(quickNoteName));
    }

    public VNextBOQuickNotesWebPage clickDeleteQuickNote(String quickNoteName) {
        clickFilteredQuickNote(quickNoteName, ".//../..//span[@title='Delete']");
        return this;
    }

    public VNextBONewNotesDialog clickEditQuickNote(String quickNoteName) {
        clickFilteredQuickNote(quickNoteName, ".//../..//span[@title='Edit']");
        return PageFactory.initElements(driver, VNextBONewNotesDialog.class);
    }

    public void clickFilteredQuickNote(String quickNoteName, String locator) {
        getQuickNotesList()
                .stream()
                .filter(e -> e.getText().equals(quickNoteName))
                .findFirst()
                .ifPresent(el -> new Actions(driver)
                        .moveToElement(el.findElement(By.xpath(locator)))
                        .click()
                        .build()
                        .perform());
    }

    public VNextBOQuickNotesWebPage deleteQuickNote(String quickNoteName) {
        int numberOfQuickNotes = getNumberOfQuickNotesDisplayed(quickNoteName);
        clickDeleteQuickNote(quickNoteName);
        try {
            wait.until((num) -> numberOfQuickNotes > getNumberOfQuickNotesDisplayed(quickNoteName));
        } catch (Exception e) {
            Assert.fail("The Quick Note hasn't been deleted");
        }
        return this;
    }

    public VNextBOQuickNotesWebPage deleteQuickNotesIfPresent(String quickNoteName) {
        while (isQuickNoteDisplayed(quickNoteName)) {
            deleteQuickNote(quickNoteName);
        }
        return this;
    }

    public int getNumberOfQuickNotesDisplayed(String quickNoteName) {
        return getQuickNotesList()
                .stream()
                .filter(e -> e.getText().equals(quickNoteName))
                .collect(Collectors.toList())
                .size();
    }

    private List<WebElement> getQuickNotesList() {
        return wait
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(quickNotes));
    }

    public int getNumberOfQuickNotesDisplayed() {
        return getQuickNotesList().size();
    }
}

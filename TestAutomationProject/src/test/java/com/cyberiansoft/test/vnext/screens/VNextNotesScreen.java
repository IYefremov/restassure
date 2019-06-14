package com.cyberiansoft.test.vnext.screens;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class VNextNotesScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@data-page='notes']")
    private WebElement rootElement;

    @FindBy(xpath = "//textarea[@name='notes']")
    private WebElement noteEditField;

    @FindBy(xpath = "//span[@action='clear']")
    private WebElement clearNoteButton;

    @FindBy(xpath = "//div[@action='goto-quick-notes']")
    private WebElement switchToQuickNotes;

    @FindBy(xpath = "//div[@action='take-camera']")
    private WebElement takePictureButton;

    @FindBy(xpath = "//div[@action='select-quick-note']")
    private List<WebElement> quickNotesList;

    @FindBy(xpath = "//div[@class='image-item']")
    private List<WebElement> pictureElementList;

    public void selectQuickNote(String quickNoteText) {
        quickNotesList.stream()
                .filter(element -> element.getText().contains(quickNoteText))
                .findFirst().orElseThrow(() -> new RuntimeException("Quick note not found " + quickNoteText))
                .click();
    }

    public void setNoteText(String noteText) {
        clearNoteButton.click();
        noteEditField.sendKeys(noteText);
    }

    public void switchToQuickNotes() {
        switchToQuickNotes.click();
    }
}


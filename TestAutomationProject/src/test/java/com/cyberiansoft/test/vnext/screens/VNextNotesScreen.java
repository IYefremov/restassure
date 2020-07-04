package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
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

    @FindBy(xpath = "//*[@action='clear']")
    private WebElement clearNoteButton;

    @FindBy(xpath = "//*[@action='goto-quick-notes']")
    private WebElement switchToQuickNotes;

    @FindBy(xpath = "//*[@action='take-camera']")
    private WebElement takePictureButton;

    @FindBy(xpath = "//*[@action='select-quick-note']")
    private List<WebElement> quickNotesList;

    @FindBy(xpath = "//div[@class='image-item']")
    private WebElement pictureElement;

    @FindBy(xpath = "//div[@class='image-item']")
    private List<WebElement> pictureElementList;

    public void selectQuickNote(String quickNoteText) {
        quickNotesList.stream()
                .filter(element -> element.getText().equals(quickNoteText))
                .findFirst().orElseThrow(() -> new RuntimeException("Quick note not found " + quickNoteText))
                .click();
    }

    public void setNoteText(String noteText) {
        noteEditField.sendKeys(noteText);
    }

    public void switchToQuickNotes() {
        WaitUtils.click(switchToQuickNotes);
    }
}


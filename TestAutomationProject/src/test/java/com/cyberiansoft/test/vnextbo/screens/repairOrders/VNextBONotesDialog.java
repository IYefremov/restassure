package com.cyberiansoft.test.vnextbo.screens.repairOrders;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public abstract class VNextBONotesDialog extends VNextBOBaseWebPage {

    @FindBy(className = "repair-notes__list")
    private WebElement repairNotesBlock;

    @FindBy(xpath = "//button[@data-bind='click: addNote']")
    private WebElement addNewNoteButton;

    @FindBy(xpath = "//div[contains(@class, 'content__notes__note__text')]")
    private List<WebElement> roNotesList;

    @FindBy(className = "note-text")
    private WebElement roNoteTextArea;

    @FindBy(xpath = "//div[@data-bind='text: title']")
    private WebElement roNoteTextTitle;

    @FindBy(xpath = "//button[@data-bind='click: clearNote']")
    private WebElement roNotesXbutton;

    @FindBy(xpath = "//button[contains(@data-bind, 'saveNote')]")
    private WebElement roNoteSaveButton;

    public VNextBONotesDialog() {
        super(DriverBuilder.getInstance().getDriver());
    }
}
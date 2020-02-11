package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBONotesDialogNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='repairNotes']//div[@class='modal-content']")
    private WebElement notesDialog;

    @FindBy(xpath = "//div[@id='repairNotes']//button[@class='close']")
    private WebElement closeDialogXIcon;

    @FindBy(xpath = "//button[@data-bind='click: addNote']")
    private WebElement addNewNoteButton;

    @FindBy(xpath = "//div[contains(@class, 'content__notes__note__text')]")
    private List<WebElement> notesList;

    @FindBy(xpath = "//textarea[@class='note-text']")
    private WebElement noteTextArea;

    @FindBy(xpath = "//button[@data-bind='click: clearNote']")
    private WebElement noteXButton;

    @FindBy(xpath = "//button[contains(@data-bind, 'saveNote')]")
    private WebElement noteSaveButton;

    public VNextBONotesDialogNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
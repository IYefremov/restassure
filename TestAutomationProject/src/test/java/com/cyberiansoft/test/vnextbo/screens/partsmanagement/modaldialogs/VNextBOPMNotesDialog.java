package com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOPMNotesDialog extends VNextBOBaseWebPage {

    @FindBy(id = "repairNotes")
    private WebElement notesDialog;

    @FindBy(xpath = "//button[contains(@data-bind, 'addNote')]")
    private WebElement addNewNoteButton;

    @FindBy(xpath = "//textarea[@class='note-text']")
    private WebElement noteField;

    @FindBy(xpath = "//button[contains(@data-bind, 'saveNote')]")
    private WebElement saveNoteButton;

    @FindBy(xpath = "//button[contains(@data-bind, 'clearNote')]")
    private WebElement cancelNoteButton;

    @FindBy(xpath = "//div[contains(@data-bind, 'html:notes')]")
    private List<WebElement> notesList;

    public WebElement getCLoseButton() {
        return notesDialog.findElement(By.xpath(".//button[contains(@aria-label, 'Close')]"));
    }

    public VNextBOPMNotesDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

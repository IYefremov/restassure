package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOEditNotesDialog extends VNextBONotesDialog {

    @FindBy(id = "repairNotes")
    private WebElement roEditNotesModalDialog;

    @FindBy(xpath = "//div[@id='repairNotes']//button[@class='close']")
    private WebElement roNoteDialogCloseButton;

    public VNextBOEditNotesDialog() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
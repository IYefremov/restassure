package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBONotesDialog;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOOrderServiceNotesDialog extends VNextBONotesDialog {

    @FindBy(xpath = "//div[@class='modal-content']//div[@class='repair-notes__list__notes-category-item__content__notes']")
    private WebElement repairNotesList;

    public VNextBOOrderServiceNotesDialog() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
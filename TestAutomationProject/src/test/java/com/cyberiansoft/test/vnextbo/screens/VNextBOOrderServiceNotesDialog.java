package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBONotesDialog;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOOrderServiceNotesDialog extends VNextBONotesDialog {

    @FindBy(id = "editOrderServiceNotesModal")
    private WebElement editOrderServiceNotesModalDialog;

    public VNextBOOrderServiceNotesDialog() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
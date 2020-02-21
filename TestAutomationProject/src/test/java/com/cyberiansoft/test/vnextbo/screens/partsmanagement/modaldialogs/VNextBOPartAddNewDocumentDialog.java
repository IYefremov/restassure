package com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOPartAddNewDocumentDialog extends VNextBOBaseWebPage {

    @FindBy(id = "document-popup")
    private WebElement addNewDocumentDialog;

    @FindBy(id = "documentPopup-type")
    private WebElement numberField;

    @FindBy(id = "documentPopup-notes")
    private WebElement notesField;

    @FindBy(id = "documentPopup-amount")
    private WebElement amountField;

    @FindBy(xpath = "//button[@data-automation-id='documentPopup-submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//span[@aria-owns='document-popup-price-document-type_listbox']")
    private WebElement typeField;

    @FindBy(id = "document-popup-price-document-type-list")
    private WebElement typeDropDown;

    @FindBy(xpath = "//ul[@id='document-popup-price-document-type_listbox']/li")
    private List<WebElement> typeListBox;

    public VNextBOPartAddNewDocumentDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

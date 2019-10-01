package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class VNextBOInspectionNoteDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@data-bind, 'modalDialogHeight')]")
    private WebElement noteDialogContainer;

    @FindBy(xpath = "//pre[@class='notes__text']")
    private WebElement noteDialogText;

    @FindBy(xpath = "//div[contains(@data-bind, 'modalDialogHeight')]//button[@class='close']")
    private WebElement closeNoteDialogButtonButton;

    public VNextBOInspectionNoteDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(closeNoteDialogButtonButton));
    }

    public boolean isNoteDialogClosed() {return Utils.isElementNotDisplayed(noteDialogContainer); }

    public boolean isInspectionNoteTextDisplayed() {return Utils.isElementDisplayed(noteDialogText); }

    public void closeInspectionNote() {Utils.clickElement(closeNoteDialogButtonButton); }
}

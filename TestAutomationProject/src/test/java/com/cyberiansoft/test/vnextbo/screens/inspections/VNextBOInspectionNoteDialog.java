package com.cyberiansoft.test.vnextbo.screens.inspections;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
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
    public WebElement dialogContainer;

    @FindBy(xpath = "//pre[@class='notes__text']")
    public WebElement noteText;

    @FindBy(xpath = "//div[contains(@data-bind, 'modalDialogHeight')]//button[@class='close']")
    public WebElement closeDialogButton;

    public VNextBOInspectionNoteDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(closeDialogButton));
    }
}

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
public class VNextBOPartAddNewDocumentDialog extends VNextBOBaseWebPage {

    @FindBy(id = "document-popup")
    private WebElement addNewDocumentDialog;

    @FindBy(id = "documentPopup-type")
    private WebElement numberField;

    @FindBy(id = "documentPopup-notes")
    private WebElement notesField;

    @FindBy(xpath = "//span[@aria-controls='document-popup__date_dateview']")
    private WebElement dateButton;

    @FindBy(id = "document-popup__date")
    private WebElement dateField;

    @FindBy(id = "document-popup__date_dateview")
    private WebElement dateFieldCalendar;

    @FindBy(xpath = "//span[@aria-controls='document-popup__dueDate_dateview']")
    private WebElement dueDateButton;

    @FindBy(id = "document-popup__dueDate")
    private WebElement dueDateField;

    @FindBy(id = "document-popup__dueDate_dateview")
    private WebElement dueDateFieldCalendar;

    @FindBy(xpath = "//input[@id='documentPopup-amount']/preceding-sibling::input")
    private WebElement amountField;

    @FindBy(id = "document-popup__upload")
    private WebElement attachmentField;

    @FindBy(xpath = "//div[@data-name='number']/div[contains(@class, ' validation-message-wrapper')]")
    private WebElement warningMessage;


    @FindBy(xpath = "//span[@aria-owns='document-popup-price-document-type_listbox']")
    private WebElement typeField;

    @FindBy(id = "document-popup-price-document-type-list")
    private WebElement typeDropDown;

    @FindBy(xpath = "//ul[@id='document-popup-price-document-type_listbox']/li")
    private List<WebElement> typeListBox;

    @FindBy(xpath = "//button[@data-automation-id='documentPopup-submit']")
    private WebElement saveButton;

    public VNextBOPartAddNewDocumentDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getDatePicker() {
        return dateField.findElement(By.xpath(".//.."));
    }

    public WebElement getTodayDateButton() {
        return dateFieldCalendar.findElement(By.xpath(".//td[contains(@class, 'k-today')]"));
    }

    public WebElement getDueDatePicker() {
        return dueDateField.findElement(By.xpath(".//.."));
    }

    public WebElement getTodayDueDateButton() {
        return dueDateFieldCalendar.findElement(By.xpath(".//td[contains(@class, 'k-today')]"));
    }

    public WebElement getXIcon() {
        return addNewDocumentDialog.findElement(By.xpath(".//button[@aria-label='Close']"));
    }
}

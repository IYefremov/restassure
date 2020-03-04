package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

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
public class VNextBOTimeReportingDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='time-tracking-popup']//div[@class='modal-content']")
    private WebElement dialogContent;

    @FindBy(xpath = "//span[@aria-owns='time-tracking-popup__technician_listbox']//span[@class='k-input']")
    private WebElement selectTechnicianDropDownField;

    @FindBy(xpath = "//span[@aria-owns='time-tracking-popup__phase_listbox']//span[@class='k-input']")
    private WebElement phaseDropDownField;

    @FindBy(xpath = "//input[@id='time-tracking-popup__started-only-check']")
    private WebElement startedOnlyCheckBox;

    @FindBy(xpath = "//div[@data-bind='text: entityName']")
    private List<WebElement> servicesList;

    @FindBy(xpath = "//tr[contains(@data-bind,'grid__row--green')]")
    private List<WebElement> savedTimeRecordsList;

    @FindBy(xpath = "//tr[@class='grid__row--blue']")
    private List<WebElement> notSavedTimeRecordsList;

    @FindBy(xpath = "//i[@class='icon-trash-bin']")
    private List<WebElement> deleteIconsList;

    @FindBy(xpath = "//tr[contains(@data-bind,'grid__row--green')]//input[contains(@data-bind,'endDateOutput')]")
    private List<WebElement> savedRecordsStopDatesList;

    @FindBy(xpath = "//tr[contains(@data-bind,'grid__row--green')]//input[contains(@data-bind,'startDateOutput')]")
    private List<WebElement> savedRecordsStartDatesList;

    @FindBy(xpath = "//tr[contains(@data-bind,'grid__row--green')]//span[contains(@class,'time-tracking-popup__time-item-technician')]//span[@class='k-input']")
    private List<WebElement> savedRecordsTechniciansList;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalConfirmButton']")
    private WebElement confirmationButton;

    @FindBy(xpath = "//tr[contains(@data-bind,'grid__row--green')]//i[@class='icon-timer-running']")
    private List<WebElement> savedRecordsTimerIconsList;

    @FindBy(xpath = "//b[contains(@data-bind,'isStarted') and not(contains(@style,'none'))]")
    private List<WebElement> timeRecordsWithTime;

    @FindBy(xpath = "//b[contains(@data-bind,'isStarted') and not(contains(@style,'none'))]")
    private List<WebElement> serviceTotalTimeValuesList;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//input[contains(@data-bind,'startDateOutput')]")
    private WebElement notSavedRecordStartDateInputField;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//input[contains(@data-bind,'startDateOutput')]/ancestor::div[contains(@data-bind,'noStartDateSpecified')]")
    private WebElement notSavedRecordStartDateDatePicker;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//input[contains(@data-bind,'endDateOutput')]")
    private WebElement notSavedRecordStopDateInputField;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//span[contains(@class,'time-item-technician')]//span[@class='k-input']")
    private WebElement notSavedRecordTechnicianDropDown;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//span[contains(@class,'time-item-technician')]/ancestor::div[contains(@data-bind,'noTechnicianSpecified')]")
    private WebElement notSavedRecordTechnicianFieldWithBorder;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//i[@class='icon-save']")
    private WebElement notSavedRecordSaveIcon;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//i[@class='icon-arrow']")
    private WebElement notSavedRecordCancelIcon;

    @FindBy(xpath = "//div[@id='time-tracking-popup']//button[@class='close']")
    private WebElement closeXIconButton;

    public WebElement dropDownOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//li[contains(text(),'" + optionName + "')]"));
    }

    public WebElement addButtonByServiceName(String service) {

        return driver.findElement(By.xpath("//div[text()='" + service + "']/ancestor::tr//div[contains(@data-bind,'onAddTimeItemClick')]"));
    }

    public WebElement totalTimeByServiceName(String service) {

        return driver.findElement(By.xpath("//div[text()='" + service + "']/ancestor::tr//b"));
    }

    public VNextBOTimeReportingDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

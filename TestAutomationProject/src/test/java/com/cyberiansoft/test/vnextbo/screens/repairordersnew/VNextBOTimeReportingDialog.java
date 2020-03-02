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

    @FindBy(xpath = "//span[contains(@class,'time-tracking-popup__time-item-technician')]//span[@class='k-input']")
    private List<WebElement> techniciansList;

    @FindBy(xpath = "//input[contains(@data-bind,'endDateOutput')]")
    private List<WebElement> endDatesList;

    @FindBy(xpath = "//div[@data-bind='text: entityName']")
    private List<WebElement> servicesList;

    @FindBy(xpath = "//tr[contains(@data-bind,'grid__row--green')]")
    private List<WebElement> savedTimeRecords;

    @FindBy(xpath = "//tr[@class='grid__row--blue']")
    private List<WebElement> notSavedTimeRecords;

    @FindBy(xpath = "//i[@class='icon-trash-bin']")
    private List<WebElement> deleteIcons;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//input[contains(@data-bind,'startDateOutput')]")
    private WebElement notSavedRecordStartDate;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//input[contains(@data-bind,'endDateOutput')]")
    private WebElement notSavedRecordStopDate;

    @FindBy(xpath = "//tr[@class='grid__row--blue']//span[contains(@class,'time-item-technician')]//span[@class='k-input']")
    private WebElement notSavedRecordTechnicianDropDown;

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

    public VNextBOTimeReportingDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

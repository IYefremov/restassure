package com.cyberiansoft.test.vnextbo.screens.devicemanagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAddNewDeviceDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='active-license-popup']//div[@class='modal-content']")
    private WebElement addNewDeviceDialog;

    @FindBy(xpath = "//label[@for='activeLicensePopup-expires']")
    private WebElement expiresInLabel;

    @FindBy(xpath = "//input[@id='activeLicensePopup-expiresInHours']/preceding-sibling::input")
    private WebElement expiresInHoursField;

    @FindBy(id = "activeLicensePopup-expiresInHours")
    private WebElement hoursInputFieldToBeEdited;

    @FindBy(xpath = "//input[@id='activeLicensePopup-expiresInMinutes']/preceding-sibling::input")
    private WebElement expiresInMinutesField;

    @FindBy(id = "activeLicensePopup-expiresInMinutes")
    private WebElement minutesInputFieldToBeEdited;

    @FindBy(xpath = "//span[@aria-owns='activeLicensePopup-team_listbox']")
    private WebElement teamDropDownField;

    @FindBy(xpath = "//ul[@id='activeLicensePopup-team_listbox']")
    private WebElement teamDropDownOptionsList;

    @FindBy(xpath = "//input[@id='activeLicensePopup-nickname']")
    private WebElement nickNameField;

    @FindBy(xpath = "//span[@aria-owns='activeLicensePopup-licence_listbox']")
    private WebElement licenseDropDownField;

    @FindBy(xpath = "//ul[@id='activeLicensePopup-licence_listbox']")
    private WebElement licenseDropDownOptionsList;

    @FindBy(xpath = "//span[@aria-owns='activeLicensePopup-timeZone_listbox']")
    private WebElement timeZoneDropDownField;

    @FindBy(xpath = "//ul[@id='activeLicensePopup-timeZone_listbox']")
    private WebElement timeZoneDropDownOptionsList;

    @FindBy(xpath = "//span[@aria-owns='activeLicensePopup-technician_listbox']")
    private WebElement technicianDropDownField;

    @FindBy(xpath = "//ul[@id='activeLicensePopup-technician_listbox']")
    private WebElement technicianDropDownOptionsList;

    @FindBy(xpath = "//input[@id='activeLicensePopup-phoneNumber']")
    private WebElement phoneNumberField;

    //Buttons
    @FindBy(xpath = "//button[@data-automation-id='activeLicensePopup-submit']")
    private WebElement okButton;

    @FindBy(xpath = "//button[@data-automation-id='activeLicensePopup-cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[@id='active-license-popup']//button[@aria-label='Close']")
    private WebElement xIconButton;

    @FindBy(xpath = "(//ul[@aria-hidden='false']/li)[1]")
    private WebElement firstLicenseDropDownFieldOptions;

    public WebElement dropDownFieldOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']/li[text()='" + optionName + "']"));
    }

    public VNextBOAddNewDeviceDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
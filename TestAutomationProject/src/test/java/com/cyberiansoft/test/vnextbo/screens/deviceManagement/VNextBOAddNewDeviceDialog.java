package com.cyberiansoft.test.vnextbo.screens.deviceManagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOAddNewDeviceDialog extends VNextBODeviceDialog {

    @FindBy(id = "active-license-popup")
    private WebElement addNewDeviceDialog;

    @FindBy(xpath = "//label[@for='activeLicensePopup-expires']")
    private WebElement expiresInLabel;

    @FindBy(xpath = "//label[@for='activeLicensePopup-phoneNumber']")
    private WebElement phoneNumberLabel;

    //InputFields
    @FindBy(id = "activeLicensePopup-expiresInMinutes")
    private WebElement minutesInputFieldToBeEdited;

    @FindBy(xpath = "//input[@id='activeLicensePopup-expiresInMinutes']/preceding-sibling::input")
    private WebElement minutesInputField;

    @FindBy(id = "activeLicensePopup-expiresInHours")
    private WebElement hoursInputFieldToBeEdited;

    @FindBy(xpath = "//input[@id='activeLicensePopup-expiresInHours']/preceding-sibling::input")
    private WebElement hoursInputField;

    @FindBy(id = "activeLicensePopup-nickname")
    private WebElement nicknameInputField;

    @FindBy(id = "activeLicensePopup-phoneNumber")
    private WebElement phoneNumberInputField;

    //DropDowns
    @FindBy(xpath = "//div[@id='active-license-popup']//div[@data-name='team']//span[contains(@class, 'k-icon')]")
    private WebElement teamArrow;

    @FindBy(id = "activeLicensePopup-team_listbox")
    private WebElement teamDropDown;

    @FindBy(xpath = "//ul[@id='activeLicensePopup-team_listbox']/li")
    private List<WebElement> teamListBoxOptions;

    @FindBy(xpath = "//div[@id='active-license-popup']//div[@data-name='licence']//span[contains(@class, 'k-icon')]")
    private WebElement licenseArrow;

    @FindBy(id = "activeLicensePopup-licence_listbox")
    private WebElement licenseDropDown;

    @FindBy(xpath = "//ul[@id='activeLicensePopup-licence_listbox']/li")
    private List<WebElement> licenseListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='activeLicensePopup-timeZone_listbox']//span[contains(@class, 'k-icon')]")
    private WebElement timeZoneArrow;

    @FindBy(id = "activeLicensePopup-timeZone_listbox")
    private WebElement timeZoneDropDown;

    @FindBy(xpath = "//ul[@id='activeLicensePopup-timeZone_listbox']/li")
    private List<WebElement> timeZoneListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='activeLicensePopup-technician_listbox']//span[contains(@class, 'k-icon')]")
    private WebElement technicianArrow;

    @FindBy(id = "activeLicensePopup-technician_listbox")
    private WebElement technicianDropDown;

    @FindBy(xpath = "//ul[@id='activeLicensePopup-technician_listbox']/li")
    private List<WebElement> technicianListBoxOptions;

    //Buttons
    @FindBy(xpath = "//button[@data-automation-id='activeLicensePopup-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//button[@data-automation-id='activeLicensePopup-cancel']")
    private WebElement cancelButton;

    public VNextBOAddNewDeviceDialog() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
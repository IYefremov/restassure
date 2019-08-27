package com.cyberiansoft.test.vnextbo.screens.deviceManagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOEditDeviceDialog extends VNextBODeviceDialog {

    @FindBy(id = "edit-device-popup")
    private WebElement editDeviceDialog;

    @FindBy(id = "editDevicePopup-nickname")
    private WebElement nicknameInputField;

    //DropDowns
    @FindBy(xpath = "//div[@id='edit-device-popup']//div[@data-name='team']//span[contains(@class, 'k-icon')]")
    private WebElement teamArrow;

    @FindBy(id = "editDevicePopup-team_listbox")
    private WebElement teamDropDown;

    @FindBy(xpath = "//ul[@id='editDevicePopup-team_listbox']/li")
    private List<WebElement> teamListBoxOptions;

    @FindBy(xpath = "//div[@id='edit-device-popup']//div[@data-name='team']//span[contains(@class, 'k-input')]")
    private WebElement teamInput;

    @FindBy(xpath = "//span[@aria-owns='editDevicePopup-timeZone_listbox']//span[contains(@class, 'k-icon')]")
    private WebElement timeZoneArrow;

    @FindBy(id = "editDevicePopup-timeZone_listbox")
    private WebElement timeZoneDropDown;

    @FindBy(xpath = "//ul[@id='editDevicePopup-timeZone_listbox']/li")
    private List<WebElement> timeZoneListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='editDevicePopup-timeZone_listbox']//span[contains(@class, 'k-input')]")
    private WebElement timeZoneInput;

    //Buttons
    @FindBy(xpath = "//div[@id='edit-device-popup']//button[@data-automation-id='editDevicePopup-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='edit-device-popup']//button[@data-automation-id='editDevicePopup-cancel']")
    private WebElement cancelButton;

    public VNextBOEditDeviceDialog() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
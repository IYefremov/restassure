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
public class VNextBOEditDeviceDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='edit-device-popup']//div[@class='modal-content']")
    private WebElement editDeviceDialog;

    @FindBy(xpath = "//input[@id='editDevicePopup-nickname']")
    private WebElement nickNameField;

    @FindBy(xpath = "//span[@aria-owns='editDevicePopup-team_listbox']")
    private WebElement teamDropDownField;

    @FindBy(xpath = "//ul[@id='editDevicePopup-team_listbox']")
    private WebElement teamDropDownOptionsList;

    @FindBy(xpath = "//span[@aria-owns='editDevicePopup-timeZone_listbox']")
    private WebElement timeZoneDropDownField;

    @FindBy(xpath = "//ul[@id='editDevicePopup-timeZone_listbox']")
    private WebElement timeZoneDropDownOptionsList;

    //Buttons
    @FindBy(xpath = "//div[@id='edit-device-popup']//button[@data-automation-id='editDevicePopup-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='edit-device-popup']//button[@data-automation-id='editDevicePopup-cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[@id='edit-device-popup']//button[@aria-label='Close']")
    private WebElement xIconButton;

    public WebElement dropDownFieldOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']/li[text()='" + optionName + "']"));
    }

    public VNextBOEditDeviceDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
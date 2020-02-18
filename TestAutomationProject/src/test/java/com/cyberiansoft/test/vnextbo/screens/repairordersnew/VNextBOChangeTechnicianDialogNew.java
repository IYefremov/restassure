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
public class VNextBOChangeTechnicianDialogNew extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@id,'VendorTechnician_ModalTemplate') and contains(@style, 'display: block')]//button[@class='close']")
    private WebElement closeDialogXIcon;

    @FindBy(xpath = "//div[contains(@id,'VendorTechnician_ModalTemplate') and contains(@style, 'display: block')]//button[@data-automation-id='modalConfirmButton']")
    private WebElement okButton;

    @FindBy(xpath = "//div[contains(@id,'VendorTechnician_ModalTemplate') and contains(@style, 'display: block')]//button[@data-automation-id='modalCancelButton']")
    private WebElement cancelButton;

    @FindBy(xpath = "//input[contains(@data-bind,'onVendorChange')]/ancestor::span//span[@class='k-input']")
    private WebElement vendorDropDown;

    @FindBy(xpath = "//input[contains(@data-bind,'selectedTechnician')]/ancestor::span//span[@class='k-input']")
    private WebElement technicianDropDown;

    public WebElement dropDownOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//*[contains(.,'" + optionName + "')]"));
    }

    public VNextBOChangeTechnicianDialogNew() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
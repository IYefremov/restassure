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
public class VNextBOChangePartsDialog extends VNextBOBaseWebPage {

    @FindBy(id = "changeStatusPopup")
    private WebElement changeStatusDialog;

    @FindBy(xpath = "//ul[@id='partMultiAction_listbox']")
    private WebElement statusDropDown;

    @FindBy(xpath = "//ul[@id='partMultiAction_listbox']/li")
    private List<WebElement> statusDropDownOptions;

    @FindBy(xpath = "//button[@data-automation-id='change-status-popup-cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[@data-automation-id='change-status-popup-submit']")
    private WebElement submitButton;

    public VNextBOChangePartsDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getSelectStatusField() {
        return changeStatusDialog.findElement(By.xpath(".//span[@aria-owns='partMultiAction_listbox']"));
    }

    public WebElement getSelectStatusFieldOption() {
        return getSelectStatusField().findElement(By.xpath(".//span[@class='k-input']"));
    }

    public WebElement getSelectStatusError() {
        return changeStatusDialog.findElement(By.xpath(".//label[contains(@class, 'error')]"));
    }
}

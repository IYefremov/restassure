package com.cyberiansoft.test.vnextbo.screens.services;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOServicesAdvancedSearchDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//input[@id='advSearchServices-name']")
    private WebElement nameField;

    @FindBy(xpath = "//span[@aria-owns='advSearchServices-type_listbox']//span[@class='k-input']")
    private WebElement typeField;

    @FindBy(xpath = "//ul[@id='advSearchServices-type_listbox']")
    private WebElement typeFieldDropDown;

    @FindBy(xpath = "//input[@data-bind='checked: filter.isDeleted']")
    private WebElement archiveCheckBox;

    @FindBy(xpath = "//form[@id='advSearchServices-form']//button[@data-bind='click: search']")
    private WebElement searchButton;

    public WebElement dropDownFieldOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//*[text()='" + optionName + "']"));
    }

    public VNextBOServicesAdvancedSearchDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

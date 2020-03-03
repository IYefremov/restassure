package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOCompanyInfoPage extends VNextBOBaseWebPage {

    @FindBy(id = "companyInfo-companyName")
    private WebElement companyNameInputField;

    @FindBy(id = "companyInfo-address1")
    private WebElement address1InputField;

    @FindBy(id = "companyInfo-address2")
    private WebElement address2InputField;

    @FindBy(id = "companyInfo-city")
    private WebElement cityInputField;

    @FindBy(id = "companyInfo-country-list")
    private WebElement companyDropDown;

    @FindBy(id = "companyInfo-zip")
    private WebElement zipInputField;

    @FindBy(xpath = "//div[@class='amt-widget-phone-input']/input")
    private WebElement phoneInputField;

    @FindBy(id = "companyInfo-email")
    private WebElement emailInputField;

    @FindBy(xpath = "//input[@aria-owns='companyInfo-country_listbox']")
    private WebElement countryInputField;

    @FindBy(xpath = "//span[@aria-controls='companyInfo-country_listbox']")
    private WebElement countryArrow;

    @FindBy(id = "companyInfo-country-list")
    private WebElement countryDropDown;

    @FindBy(xpath = "//div[@id='companyInfo-phone-wrapper']//input[@class='k-input']")
    private WebElement phoneCodeInputField;

    @FindBy(xpath = "//div[@class='amt-widget-phone-codes']//span[@aria-label='select']")
    private WebElement phoneCodeArrow;

    @FindBy(id = "companyInfo-country-list")
    private WebElement phoneCodeDropDown;

    @FindBy(xpath = "//input[@aria-owns='companyInfo-state_listbox']")
    private WebElement stateProvinceInputField;

    @FindBy(xpath = "//span[@aria-controls='companyInfo-state_listbox']")
    private WebElement stateProvinceArrow;

    @FindBy(id = "companyInfo-state-list")
    private WebElement stateProvinceDropDown;

    @FindBy(xpath = "//ul[@id='companyInfo-country_listbox']/li")
    private List<WebElement> countryListBoxOptions;

    @FindBy(xpath = "//ul[@id='companyInfo-state_listbox']/li")
    private List<WebElement> stateProvinceListBoxOptions;

    @FindBy(xpath = "//ul[@data-role='staticlist']//div[@class='amt-widget-phone-codes-item-code']")
    private List<WebElement> phoneCodeListBoxOptions;

    @FindBy(xpath = "//section[@id='company-info-view']//button[text()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@class='notification__message']/b[contains(text(), 'Success')]")
    private WebElement successMessage;

    public VNextBOCompanyInfoPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class VNextBOCompanyInfoWebPage extends VNextBOBaseWebPage {

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

    public VNextBOCompanyInfoWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public VNextBOCompanyInfoWebPage setCompanyName(String companyName) {
        Utils.clearAndType(companyNameInputField, companyName);
        return this;
    }

    public VNextBOCompanyInfoWebPage setAddressLine1(String addressLine1) {
        Utils.clearAndType(address1InputField, addressLine1);
        return this;
    }

    public VNextBOCompanyInfoWebPage setAddressLine2(String addressLine2) {
        Utils.clearAndType(address2InputField, addressLine2);
        return this;
    }

    public VNextBOCompanyInfoWebPage setCity(String city) {
        Utils.clearAndType(cityInputField, city);
        return this;
    }

    public VNextBOCompanyInfoWebPage setZip(String zip) {
        Utils.clearAndType(zipInputField, zip);
        return this;
    }

    public VNextBOCompanyInfoWebPage setPhoneCode(String phoneCode) {
        Utils.clickElement(phoneCodeArrow);
        selectPhoneCode(phoneCode);
        return this;
    }

    public VNextBOCompanyInfoWebPage setPhone(String phone) {
        Utils.clearAndType(phoneInputField, phone);
        return this;
    }

    public VNextBOCompanyInfoWebPage setEmail(String email) {
        Utils.clearAndType(emailInputField, email);
        return this;
    }

    public VNextBOCompanyInfoWebPage setCountry(String country) {
        Utils.clickElement(countryArrow);
        selectCountry(country);
        return this;
    }

    public VNextBOCompanyInfoWebPage setStateProvince(String stateProvince) {
        Utils.clickElement(stateProvinceArrow);
        selectStateProvince(stateProvince);
        return this;
    }

    private VNextBOCompanyInfoWebPage selectCountry(String country) {
        Utils.selectOptionInDropDown(countryDropDown, countryListBoxOptions, country);
        return this;
    }

    private VNextBOCompanyInfoWebPage selectPhoneCode(String phoneCode) {
        Utils.selectOptionInDropDown(phoneCodeDropDown, phoneCodeListBoxOptions, phoneCode, true);
        return this;
    }

    private VNextBOCompanyInfoWebPage selectStateProvince(String state) {
        Utils.selectOptionInDropDown(stateProvinceDropDown, stateProvinceListBoxOptions, state);
        return this;
    }

    public VNextBOCompanyInfoWebPage clickSaveButton() {
        Utils.clickElement(saveButton);
        return this;
    }

    public boolean isSuccessNotificationDisplayed() {
        return Utils.isElementDisplayed(successMessage);
    }

    public String getCompanyValue() {
        return Utils.getInputFieldValue(companyNameInputField);
    }

    public String getAddressLine1Value() {
        return Utils.getInputFieldValue(address1InputField);
    }

    public String getAddressLine2Value() {
        return Utils.getInputFieldValue(address2InputField);
    }

    public String getCityValue() {
        return Utils.getInputFieldValue(cityInputField);
    }

    public String getCountryValue() {
        return Utils.getInputFieldValue(countryInputField);
    }

    public String getStateProvinceValue() {
        return Utils.getInputFieldValue(stateProvinceInputField);
    }

    public String getZipValue() {
        return Utils.getInputFieldValue(zipInputField);
    }

    public String getPhoneCodeValue() {
        return Utils.getInputFieldValue(phoneCodeInputField);
    }

    public String getPhoneValue() {
        return Utils.getInputFieldValue(phoneInputField);
    }

    public String getEmailValue() {
        return Utils.getInputFieldValue(emailInputField);
    }
}

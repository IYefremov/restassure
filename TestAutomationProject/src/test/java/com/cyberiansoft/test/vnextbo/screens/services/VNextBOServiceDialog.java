package com.cyberiansoft.test.vnextbo.screens.services;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOServiceDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='service-popup']//div[contains(@class, 'modal-content')]")
    private WebElement serviceDialog;

    @FindBy(xpath = "//input[@data-automation-id='servicePopup-serviceName']")
    private WebElement serviceNameField;

    @FindBy(xpath = "//span[@aria-owns='popup-services-type_listbox']//span[@class='k-input']")
    private WebElement serviceTypeDropDownField;

    @FindBy(xpath = "//ul[@id='advSearchServices-type_listbox']")
    private WebElement serviceTypeDropDown;

    @FindBy(xpath = "//textarea[@data-automation-id='servicePopup-description']")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//span[@aria-owns='price-type_listbox']//div")
    private WebElement priceTypeField;

    @FindBy(xpath = "//ul[@id='price-type_listbox']")
    private WebElement priceTypeDropDown;

    @FindBy(xpath = "//input[@data-automation-id='servicePopup-priceForMoneyType']/preceding-sibling::input")
    private WebElement moneyPriceField;

    @FindBy(xpath = "//input[@data-automation-id='servicePopup-priceForMoneyType']")
    private WebElement moneyPriceFieldToBeEdited;

    @FindBy(xpath = "//input[@data-automation-id='servicePopup-priceForPercentageType']/preceding-sibling::input")
    private WebElement percentagePriceField;

    @FindBy(xpath = "//input[@data-automation-id='servicePopup-priceForPercentageType']")
    private WebElement percentagePriceFieldToBeEdited;

    @FindBy(xpath = "//label[@data-bind='visible: priceVisible']")
    private WebElement priceFieldLabel;

    @FindBy(xpath = "//span[@aria-owns='servicePopup-clarification_listbox']//span[@class='k-input']")
    private WebElement clarificationField;

    @FindBy(xpath = "//ul[@id='servicePopup-clarification_listbox']")
    private WebElement clarificationFieldDropDown;

    @FindBy(xpath = "//input[@data-automation-id='servicePopup-clarificationPrefix']")
    private WebElement clarificationPrefixField;

    @FindBy(xpath = "//button[@data-automation-id='servicePopup-submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id='service-popup']//div[contains(@class, 'modal-content')]//button[@class='close']")
    private WebElement closeButton;

    @FindBy(xpath = "//p[@data-bind='visible: errorName.visible, text: errorName.text']")
    private WebElement serviceNameErrorMessage;

    //Labor services additional elements
    @FindBy(xpath = "//input[@id='priceForMoneyType']/preceding-sibling::input")
    private WebElement laborRateField;

    @FindBy(xpath = "//input[@id='priceForMoneyType']")
    private WebElement laborRateFieldToBeEdited;

    @FindBy(xpath = "//input[@id='laborTime']/preceding-sibling::input")
    private WebElement defaultLaborTimeField;

    @FindBy(xpath = "//input[@id='laborTime']")
    private WebElement defaultLaborTimeFieldToBeEdited;

    @FindBy(xpath = "//div[@data-automation-id='service-popup-labor-parts-list']/preceding-sibling::input")
    private WebElement addPartsSelectedSearchField;

    @FindBy(xpath = "//input[@data-automation-id='servicePopup-laborTime-useLaborTimes']")
    private WebElement useLaborTimesCheckbox;

    @FindBy(xpath = "//label[@data-bind='visible: laborTimes.isVisible']")
    private WebElement laborRateFieldLabel;

    //Part services additional elements
    @FindBy(xpath = "//span[@aria-owns='servicePopup-basicParts-category_listbox']//span[@class='k-input']")
    private WebElement categoryField;

    @FindBy(xpath = "//ul[@id='servicePopup-basicParts-category_listbox']")
    private WebElement categoryFieldDropDown;

    @FindBy(xpath = "//span[@aria-owns='servicePopup-basicParts-subCategory_listbox']//span[@class='k-input']")
    private WebElement subCategoryField;

    @FindBy(xpath = "//ul[@id='servicePopup-basicParts-subCategory_listbox']")
    private WebElement subCategoryFieldDropDown;

    @FindBy(xpath = "//span[@aria-owns='servicePopup-basicParts-partName_listbox']//span[@class='k-input']")
    private WebElement partNameField;

    @FindBy(xpath = "//ul[@id='servicePopup-basicParts-partName_listbox']")
    private WebElement partNameFieldDropDown;

    @FindBy(xpath = "//div[@id='service-popup']//button[@data-automation-id='servicePopup-add-part']")
    private WebElement addButton;

    public WebElement dropDownFieldOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//*[text()='" + optionName + "']"));
    }

    public VNextBOServiceDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

}

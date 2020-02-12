package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOAddNewServiceMonitorDialog extends VNextBOBaseWebPage {

    @FindBy(id = "service-instance-form")
    private WebElement newServicePopup;

    @FindBy(xpath = "//textarea[contains(@data-bind, 'serviceDescription')]")
    private WebElement serviceDescription;

    @FindBy(id = "service-instance-form-service-details")
    private WebElement serviceDetails;

    @FindBy(xpath = "//label[text()='Price']/..//input[@title]")
    private WebElement servicePrice;

    @FindBy(xpath = "//label[contains(text(), 'Rate')]/..//input[@title]")
    private WebElement serviceLaborRate;

    @FindBy(id = "service-instance-form-price")
    private WebElement servicePriceInputField;

    @FindBy(id = "service-instance-form-labor-rate")
    private WebElement serviceLaborRateInputField;

    @FindBy(xpath = "//label[text()='Quantity']/..//input[@title]")
    private WebElement serviceQuantity;

    @FindBy(id = "service-instance-form-quantity")
    private WebElement serviceQuantityInputField;

    @FindBy(xpath = "//label[contains(text(), 'Time')]/..//input[@title]")
    private WebElement serviceLaborTime;

    @FindBy(id = "service-instance-form-labor-time")
    private WebElement serviceLaborTimeInputField;

    @FindBy(xpath = "//div[@id='service-instance-form']//button[contains(text(), 'Submit')]")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='service-instance-form']//button[@aria-label='Close']")
    private WebElement xButton;

    @FindBy(xpath = "//div[@id='service-instance-form']//button[contains(text(), 'Cancel')]")
    private WebElement cancelButton;

    @FindBy(xpath = "//span[@aria-owns='service-type-instance-form-price-type_listbox']")
    private WebElement priceTypeListBox;

    @FindBy(id = "service-type-instance-form-price-type-list")
    private WebElement priceTypeDropDown;

    @FindBy(xpath = "//ul[contains(@id, 'price-type')]//div[contains(@class, 'item__text')]")
    private List<WebElement> priceTypeListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='service-instance-form-part-category_listbox']")
    private WebElement categoryListBox;

    @FindBy(id = "service-instance-form-part-category-list")
    private WebElement categoryDropDown;

    @FindBy(xpath = "//ul[contains(@id, 'part-category_listbox')]//li")
    private List<WebElement> categoryListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='service-instance-form-part-sub-category_listbox']")
    private WebElement subcategoryListBox;

    @FindBy(id = "service-instance-form-part-sub-category-list")
    private WebElement subcategoryDropDown;

    @FindBy(xpath = "//ul[contains(@id, 'part-sub-category_listbox')]//li")
    private List<WebElement> subcategoryListBoxOptions;

    @FindBy(xpath = "//input[@aria-owns='service-instance-form-service_listbox']")
    private WebElement serviceInputField;

    @FindBy(xpath = "//span[@aria-controls='service-instance-form-service_listbox']")
    private WebElement serviceArrow;

    @FindBy(id = "service-instance-form-service_listbox")
    private WebElement serviceDropDown;

    @FindBy(id = "service-instance-form-labor-parts-list")
    private WebElement addPartsDropDown;

    @FindBy(xpath = "//span[@data-bind='text: part.selectedPartsCounter']")
    private WebElement selectedAddPartsCounter;

    @FindBy(xpath = "//ul[@id='service-instance-form-service_listbox']/li")
    private List<WebElement> serviceListBoxOptions;

    @FindBy(xpath = "//tr[@data-order-service-id]//div[contains(@data-bind, 'visible: partDescription')]")
    private List<WebElement> partDescriptions;

    public VNextBOAddNewServiceMonitorDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public List<WebElement> getPartsOptions() {
        return addPartsDropDown.findElements(By.xpath("//div[contains(@data-bind, 'onLaborPartSelectionChange')]"));
    }
}

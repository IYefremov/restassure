package com.cyberiansoft.test.vnextbo.screens.repairordersnew;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOAddNewServiceDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='service-instance-form']//div[@class='modal-content']")
    private WebElement dialogContent;

    @FindBy(xpath = "//span[@aria-owns='service-type-instance-form-price-type_listbox']//div[@class='dropdown__template-item__text']")
    private WebElement priceTypeDropDown;

    @FindBy(xpath = "//input[@aria-owns='service-instance-form-service_listbox']")
    private WebElement serviceDropDownField;

    @FindBy(xpath = "//span[@aria-controls='service-instance-form-service_listbox']")
    private WebElement serviceDropDownArrow;

    @FindBy(xpath = "//textarea[@data-bind='value: services.selectedService.serviceDescription']")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//input[@data-automation-id='service-instance-form-price']/preceding-sibling::input")
    private WebElement priceVisibleInputField;

    @FindBy(xpath = "//input[@data-automation-id='service-instance-form-price']")
    private WebElement priceInvisibleInputField;

    @FindBy(xpath = "//input[@data-automation-id='service-instance-form-quantity']/preceding-sibling::input")
    private WebElement quantityVisibleInputField;

    @FindBy(xpath = "//input[@data-automation-id='service-instance-form-quantity']")
    private WebElement quantityInvisibleInputField;

    @FindBy(xpath = "//input[@data-automation-id='service-instance-form-labor-rate']/preceding-sibling::input")
    private WebElement laborRateVisibleInputField;

    @FindBy(xpath = "//input[@data-automation-id='service-instance-form-labor-rate']")
    private WebElement laborRateInvisibleInputField;

    @FindBy(xpath = "//input[@data-automation-id='service-instance-form-labor-time']/preceding-sibling::input")
    private WebElement laborTimeVisibleInputField;

    @FindBy(xpath = "//input[@data-automation-id='service-instance-form-labor-time']")
    private WebElement laborTimeInvisibleInputField;

    @FindBy(xpath = "//span[@aria-owns='service-instance-form-part-category_listbox']//span[@class='k-input']")
    private WebElement categoryDropDownField;

    @FindBy(xpath = "//span[@aria-owns='service-instance-form-part-sub-category_listbox']//span[@class='k-input']")
    private WebElement subCategoryDropDownField;

    @FindBy(xpath = "//button[@data-automation-id='service-instance-form-submit']")
    private WebElement submitButton;

    public WebElement priceTypeDropDownOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@id='service-type-instance-form-price-type_listbox']//div[text()='" + optionName + "']"));
    }

    public WebElement dropDownOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']//li[contains(text(),'" + optionName + "')]"));
    }

    public WebElement partListItem(String partName) {

        return driver.findElement(By.xpath("//div[contains(@data-bind,'onLaborPartSelectionChange')]//div[@class='listview-checklist__item__text' and contains(., '" + partName + "')]"));
    }

    public VNextBOAddNewServiceDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

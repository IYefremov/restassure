package com.cyberiansoft.test.vnextbo.screens.partsmanagement;

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
public class VNextBOAddNewPartDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='service-instance-form']//div[@class='modal-content']")
    private WebElement dialogContent;

    @FindBy(xpath = "//button[@data-automation-id='service-instance-form-cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[@id='service-instance-form']//button[@class='close']")
    private WebElement closeButton;

    @FindBy(xpath = "//button[@data-automation-id='service-instance-form-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//span[@aria-owns='service-type-instance-form-price-type_listbox']//span[@class='k-input']")
    private WebElement priceTypeField;

    @FindBy(xpath = "//input[@aria-owns='service-instance-form-service_listbox']")
    private WebElement serviceField;

    @FindBy(xpath = "//ul[@id='service-instance-form-service_listbox']")
    private WebElement serviceFieldDropDown;

    @FindBy(xpath = "//textarea[@data-bind='value: services.selectedService.serviceDescription']")
    private WebElement descriptionTextarea;

    @FindBy(xpath = "//span[@aria-owns='service-instance-form-part-category_listbox']//span[@class='k-input']")
    private WebElement categoryField;

    @FindBy(xpath = "//ul[@id='service-instance-form-part-category_listbox']")
    private WebElement categoryFieldDropDown;

    @FindBy(xpath = "//span[@aria-owns='service-instance-form-part-sub-category_listbox']//span[@class='k-input']")
    private WebElement subCategoryField;

    @FindBy(xpath = "//ul[@id='service-instance-form-part-sub-category_listbox']")
    private WebElement subCategoryFieldDropDown;

    @FindBy(xpath = "//input[contains(@data-bind,'value: part.partFilter')]")
    private WebElement partsListFilterField;

    @FindBy(xpath = "//button[@data-bind='click: part.selectAll, enabled: part.hasAvailableParts']")
    private WebElement selectAllPartsButton;

    @FindBy(xpath = "//button[@data-bind='click: part.unSelectAll, enabled: part.hasSelectedParts']")
    private WebElement unSelectAllPartsButton;

    @FindBy(xpath = "//div[@data-bind='visible: part.part.isEnabled']//span[@class='badge']")
    private WebElement selectedPartsCounter;

    @FindBy(xpath = "//div[contains(@data-bind, 'onLaborPartSelectionChange')]")
    private List<WebElement> partsList;

    public WebElement dropDownFieldOption(String optionName) {

        return driver.findElement(By.xpath("//ul[@aria-hidden='false']/li[contains(text(),'" + optionName + "')]"));
    }

    public WebElement partsListRecordByText(String text) {

        return driver.findElement(By.xpath("//div[@data-automation-id='service-instance-form-labor-parts-list']//div[contains(text(),'" + text +"')]"));
    }

    public WebElement selectedPartsListRecordByText(String text) {

        return driver.findElement(By.xpath("//div[@data-automation-id='service-instance-form-selected-labor-parts-list']//label[not(contains(@style, 'display: none'))]/div[contains(text(),'" + text + "')]"));
    }

    public VNextBOAddNewPartDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

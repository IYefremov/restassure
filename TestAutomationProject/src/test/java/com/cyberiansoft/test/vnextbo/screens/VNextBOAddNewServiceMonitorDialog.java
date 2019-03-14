package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOAddNewServiceMonitorDialog extends VNextBOBaseWebPage {

    @FindBy(id = "service-instance-form")
    private WebElement newServicePopup;

    @FindBy(xpath = "//textarea[contains(@data-bind, 'serviceDescription')]")
    private WebElement serviceDescription;

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

    @FindBy(xpath = "//ul[@id='service-instance-form-service_listbox']//div[contains(@class, 'item__text')]")
    private List<WebElement> serviceListBoxOptions;

    @FindBy(xpath = "//tr[@data-order-service-id]//div[contains(@data-bind, 'visible: partDescription')]")
    private List<WebElement> partDescriptions;

    public VNextBOAddNewServiceMonitorDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isNewServicePopupDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(newServicePopup)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public VNextBOAddNewServiceMonitorDialog setPriceType(String priceType) {
        clickPriceTypeBox();
        selectPriceType(priceType);
        return this;
    }

    private VNextBOAddNewServiceMonitorDialog clickPriceTypeBox() {
        wait.until(ExpectedConditions.elementToBeClickable(priceTypeListBox)).click();
        return this;
    }

    private VNextBOAddNewServiceMonitorDialog selectPriceType(String priceType) {
        selectOptionInDropDown(priceTypeDropDown, priceTypeListBoxOptions, priceType);
        return this;
    }

    public VNextBOAddNewServiceMonitorDialog setCategory(String category) {
        clickCategoryBox();
        selectCategory(category);
        return this;
    }

    private VNextBOAddNewServiceMonitorDialog clickCategoryBox() {
        waitForLoading();
        wait.until(ExpectedConditions.elementToBeClickable(categoryListBox)).click();
        return this;
    }

    private VNextBOAddNewServiceMonitorDialog selectCategory(String category) {
        selectOptionInDropDown(categoryDropDown, categoryListBoxOptions, category);
        return this;
    }

    public VNextBOAddNewServiceMonitorDialog setSubcategory(String subcategory) {
        clickSubcategoryBox();
        selectSubcategory(subcategory);
        return this;
    }

    public String setSubcategory() {
        clickSubcategoryBox();
        return selectSubcategory();
    }

    public boolean isPartDescriptionDisplayed(String description) {
        refreshPage();
        wait.until(ExpectedConditions.visibilityOfAllElements(partDescriptions));
        return partDescriptions
                .stream()
                .anyMatch(e -> {
                    final String option = e.getText().replaceFirst(".+(, )", "");
                    System.out.println(option);
                    return option.equals(description);
                });
    }

    public String selectRandomAddPartsOption() {
        wait.until(ExpectedConditions.visibilityOfAllElements(addPartsDropDown));
        final List<WebElement> labels = addPartsDropDown.findElements(By.tagName("label"));
        final int randomIndex = RandomUtils.nextInt(0, labels.size());
        final String selectedAddPartsNumber = getSelectedAddPartsNumber();
        wait.until(ExpectedConditions.elementToBeClickable(labels.get(randomIndex))).click();
        wait.until((ExpectedCondition<Boolean>) (num) -> !selectedAddPartsNumber.equals(getSelectedAddPartsNumber()));
        return labels.get(randomIndex).getText();
    }

    public String getSelectedAddPartsNumber() {
        return wait.until(ExpectedConditions.visibilityOf(selectedAddPartsCounter)).getText();
    }

    private VNextBOAddNewServiceMonitorDialog clickSubcategoryBox() {
        wait.until(ExpectedConditions.elementToBeClickable(subcategoryListBox)).click();
        return this;
    }

    private VNextBOAddNewServiceMonitorDialog selectSubcategory(String subcategory) {
        selectOptionInDropDown(subcategoryDropDown, subcategoryListBoxOptions, subcategory);
        return this;
    }

    private String selectSubcategory() {
        return selectOptionInDropDown(subcategoryDropDown, subcategoryListBoxOptions);
    }

    public VNextBOAddNewServiceMonitorDialog setService(String service) {
        clickServiceArrow();
        selectService(service);
        return this;
    }

    private VNextBOAddNewServiceMonitorDialog clickServiceArrow() {
        wait.until(ExpectedConditions.elementToBeClickable(serviceArrow)).click();
        return this;
    }

    private VNextBOAddNewServiceMonitorDialog selectService(String service) {
        selectOptionInDropDown(serviceDropDown, serviceListBoxOptions, service);
        return this;
    }

    public VNextBOAddNewServiceMonitorDialog setServiceDescription(String description) {
        clearAndType(serviceDescription, description);
        return this;
    }

    public VNextBOAddNewServiceMonitorDialog setServicePrice(String price) {
        selectServiceOption(servicePrice, servicePriceInputField, price);
        return this;
    }

    public VNextBOAddNewServiceMonitorDialog setServiceLaborRate(String laborRate) {
        selectServiceOption(serviceLaborRate, serviceLaborRateInputField, laborRate);
        return this;
    }

    public VNextBOAddNewServiceMonitorDialog setServiceQuantity(String quantity) {
        selectServiceOption(serviceQuantity, serviceQuantityInputField, quantity);
        return this;
    }

    public VNextBOAddNewServiceMonitorDialog setServiceLaborTime(String laborTime) {
        selectServiceOption(serviceLaborTime, serviceLaborTimeInputField, laborTime);
        return this;
    }

    private void selectServiceOption(WebElement serviceQuantity, WebElement serviceQuantityInputField, String quantity) {
        wait.until(ExpectedConditions.elementToBeClickable(serviceQuantity)).click();
        wait.until(ExpectedConditions.elementToBeClickable(serviceQuantityInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(serviceQuantity)).sendKeys(quantity);
    }

    public VNextBORepairOrderDetailsPage clickSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        waitForLoading();
        wait.until(ExpectedConditions.invisibilityOf(newServicePopup));
        return PageFactory.initElements(driver, VNextBORepairOrderDetailsPage.class);
    }

    public VNextBORepairOrderDetailsPage clickXButton() {
        wait.until(ExpectedConditions.elementToBeClickable(xButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(newServicePopup));
        return PageFactory.initElements(driver, VNextBORepairOrderDetailsPage.class);
    }

    public VNextBORepairOrderDetailsPage clickCancelButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(newServicePopup));
        return PageFactory.initElements(driver, VNextBORepairOrderDetailsPage.class);
    }
}
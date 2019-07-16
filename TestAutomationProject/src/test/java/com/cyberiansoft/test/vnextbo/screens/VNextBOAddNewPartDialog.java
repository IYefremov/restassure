package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBOAddNewPartDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//textarea[contains(@data-bind, 'serviceDescription')]")
    private WebElement description;

    @FindBy(xpath = "//div[@data-name='part']//span[contains(@data-bind, 'selectedPartsCounter')]")
    private WebElement partsCounter;

    @FindBy(xpath = "//button[@data-automation-id='service-instance-form-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//span[@aria-controls='service-instance-form-service_listbox']")
    private WebElement serviceArrow;

    @FindBy(xpath = "//input[@aria-owns='service-instance-form-service_listbox']")
    private WebElement serviceInputField;

    @FindBy(id = "service-instance-form-service-list")
    private WebElement serviceDropDown;

    @FindBy(xpath = "//ul[@id='service-instance-form-service_listbox']/li")
    private List<WebElement> serviceListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='service-instance-form-part-category_listbox']//span[@class='k-select']")
    private WebElement categoryArrow;

    @FindBy(id = "service-instance-form-part-category-list")
    private WebElement categoryDropDown;

    @FindBy(xpath = "//ul[@id='service-instance-form-part-category_listbox']/li")
    private List<WebElement> categoryListBoxOptions;

    @FindBy(xpath = "//span[@aria-owns='service-instance-form-part-sub-category_listbox']//span[@class='k-select']")
    private WebElement subcategoryArrow;

    @FindBy(id = "service-instance-form-part-sub-category-list")
    private WebElement subcategoryDropDown;

    @FindBy(xpath = "//ul[@id='service-instance-form-part-sub-category_listbox']/li")
    private List<WebElement> subcategoryListBoxOptions;

    @FindBy(xpath = "//div[@id='service-instance-form-labor-parts-list']//div[contains(@class, 'item__text')]")
    private List<WebElement> partsListOptions;

    public VNextBOAddNewPartDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public VNextBOAddNewPartDialog setService(String service) {
        clickElement(serviceArrow);
        selectService(service);
        waitForLoading();
        return this;
    }

    public VNextBOAddNewPartDialog setCategory(String category) {
        clickElement(categoryArrow);
        selectCategory(category);
        waitForLoading();
        return this;
    }

    public VNextBOAddNewPartDialog setSubcategory(String subcategory) {
        clickElement(subcategoryArrow);
        selectSubcategory(subcategory);
        return this;
    }

    public VNextBOAddNewPartDialog setServiceDescription(String description) {
        setData(this.description, description);
        return this;
    }

    public VNextBOAddNewPartDialog selectPartsFromPartsList(List<String> partsNames) {
        wait.until(ExpectedConditions.visibilityOfAllElements(partsListOptions));

        final List<String> partsOptionsNames = partsListOptions
                .stream()
                .map(element -> element
                        .getText()
                        .trim())
                .collect(Collectors.toList());

        for (String part : partsNames) {
            for (int i = 0; i < partsListOptions.size(); i++) {
                if (partsOptionsNames.get(i).contains(part)) {
                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(partsListOptions.get(i)));
                    } catch (Exception ignored) {}
                    actions.moveToElement(partsListOptions.get(i)).click().build().perform();
                }
            }
        }
        return this;
    }

    private VNextBOAddNewPartDialog selectService(String service) {
        selectOptionInDropDown(serviceDropDown, serviceListBoxOptions, service);
        return this;
    }

    private VNextBOAddNewPartDialog selectCategory(String category) {
        selectOptionInDropDown(categoryDropDown, categoryListBoxOptions, category);
        return this;
    }

    private VNextBOAddNewPartDialog selectSubcategory(String subcategory) {
        selectOptionInDropDown(subcategoryDropDown, subcategoryListBoxOptions, subcategory);
        return this;
    }

    public String getServiceFieldValue() {
        return getInputFieldValue(serviceInputField);
    }

    public String getPartsCounterValue() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(partsCounter)).getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void clickSubmitButton() {
        clickElement(submitButton);
        waitForLoading();
    }
}

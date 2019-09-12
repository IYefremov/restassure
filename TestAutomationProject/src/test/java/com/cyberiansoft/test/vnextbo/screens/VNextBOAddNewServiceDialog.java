package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBOAddNewServiceDialog extends VNextBOBaseWebPage {

    @FindBy(id = "service-popup")
    private WebElement newservicepopup;

    @FindBy(xpath = "//div[@class='modal fade in' and contains(@style, 'display: block;')]")
    private WebElement newServicePopupDisplayed;

    @FindBy(xpath = "//input[@data-automation-id='servicePopup-serviceName']")
    private TextField servicenamefld;

    @FindBy(xpath = "//input[@data-automation-id='servicePopup-serviceName']")
    private WebElement serviceNameField;

    @FindBy(xpath = "//span[@aria-owns='popup-services-type_listbox']/span/span/span")
    private WebElement servicetypecmb;

    @FindBy(xpath = "//textarea[@data-automation-id='servicePopup-description']")
    private WebElement servicedescfld;

    //	@FindBy(xpath = "//span[@aria-owns='price-type_listbox']/span/span")
    @FindBy(xpath = "//span[@aria-owns='price-type_listbox']/span/span[@class='k-input']")
    private WebElement servicepricetypecmb;

    @FindBy(xpath = "//span[@aria-owns='servicePopup-basicParts-category_listbox']/span/span[@class='k-input']")
    private WebElement servicePartCategoryCmb;

    @FindBy(xpath = "//input[@id='servicePopup-basicParts-category']/preceding-sibling::span[@class='k-dropdown-wrap k-state-default']")
    private WebElement servicePartCategoryClosed;

    @FindBy(xpath = "//span[@aria-owns='servicePopup-basicParts-subCategory_listbox']/span/span[@class='k-input']")
    private WebElement servicePartSubcategoryCmb;

    @FindBy(xpath = "//input[@id='servicePopup-basicParts-subCategory']/preceding-sibling::span[@class='k-dropdown-wrap k-state-default']")
    private WebElement servicePartSubcategoryClosed;

    @FindBy(xpath = "//span[@aria-owns='servicePopup-basicParts-partName_listbox']/span/span[@class='k-input']")
    private WebElement servicePartNameCmb;

    @FindBy(xpath = "//input[@id='servicePopup-basicParts-partName']/preceding-sibling::span[@class='k-dropdown-wrap k-state-default']")
    private WebElement servicePartNameClosed;

//    @FindBy(id = "priceForMoneyType")
//    private WebElement servicePriceTypingField;

    @FindBy(id = "priceForPercentageType")
    private TextField servicepercentagefld;

    @FindBy(xpath = "//div[@data-bind='visible: priceFieldVisible']//input[@id='priceForPercentageType']/preceding-sibling::input")
    protected WebElement servicePercentageField;

    @FindBy(xpath = "//div[@data-bind='visible: priceFieldVisible']//input[@id='priceForPercentageType']")
    private WebElement servicePercentageTypingField;

    @FindBy(xpath = "//div[@data-bind='visible: priceFieldVisible']//input[@id='priceForMoneyType']/preceding-sibling::input")
    private WebElement servicePriceField;

    @FindBy(xpath = "//div[@data-bind='visible: priceFieldVisible']//input[@id='priceForMoneyType']")
    protected WebElement servicePriceTypingField;

    @FindBy(xpath = "//div[@class='errorMessege']/p")
    private WebElement errormsg;

    @FindBy(xpath = "//div[@data-bind='visible: priceTypes.visible']//span[@class='k-dropdown-wrap k-state-default']")
    private WebElement priceTypeClosed;

    @FindBy(xpath = "//div[@data-bind='visible: priceTypes.visible']//span[contains(@class, 'k-widget k-dropdown k-header')]")
    private WebElement priceTypeDropDown;

    @FindBy(xpath = "//button[@data-automation-id='servicePopup-submit']")
    private WebElement saveNewServiceButton;

    @FindBy(xpath = "//div[@id='service-popup']//button[@class='close']")
    private WebElement rejectNewServiceButton;

    @FindBy(xpath = "//div[@data-bind='visible: priceFieldVisible']//input[@id='priceForMoneyType']")
    private WebElement laborRateTypingField;

    @FindBy(xpath = "//div[@data-bind='visible: priceFieldVisible']//input[@id='priceForMoneyType']/preceding-sibling::input")
    private WebElement laborRateField;

    @FindBy(xpath = "//div[@data-bind='visible: laborTimes.isVisible']//input[@id='laborTime']")
    private WebElement defaultLaborTimeTypingField;

    @FindBy(xpath = "//div[@data-bind='visible: laborTimes.isVisible']//input[@id='laborTime']/preceding-sibling::input")
    private WebElement defaultLaborTimeField;

    @FindBy(xpath = "//div[@data-bind='visible: laborTimes.isVisible']//input[@type='checkbox']")
    private WebElement useLaborTimesCheckbox;

    @FindBy(xpath = "//div[@data-bind='visible: laborTimes.useLaborTimes']/label[@id='servicePopup-laborTime-source_label']/..")
    private WebElement serviceSource;

    @FindBy(xpath = "//div[@data-bind='visible: laborTimes.useLaborTimes']/label[@id='servicePopup-laborTime-operation_label']/..")
    private WebElement serviceOperation;

    @FindBy(xpath = "//span[@aria-labelledby='servicePopup-laborTime-source_label']")
    private WebElement sourceLabel;

    @FindBy(xpath = "//span[@aria-labelledby='servicePopup-laborTime-operation_label']")
    private WebElement operationLabel;

    @FindBy(xpath = "//button[contains(@data-bind, 'presetParts.add')]")
    private WebElement servicePopupAddButton;

    @FindBy(xpath = "//div[@id='preset-parts-list']//span")
    private List<WebElement> servicePartsInfoList;

    final By serviceaddbtnby = By.xpath(".//button[@data-automation-id='servicePopup-submit']");
    final By closenewservicedialogbtnxpath = By.xpath(".//button[@class='close']");

    public VNextBOAddNewServiceDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isNewServicePopupDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(newServicePopupDisplayed)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public VNextBOServicesWebPage addNewService(String servicename, String servicetype, String servicedesc, String servicepricetype, String serviceprice) {
        setServiceName(servicename);
        selectServiceType(servicetype);
        setServiceDescription(servicedesc);
        selectServicePriceType(servicepricetype);
        setServicePriceValue(serviceprice);
        clickSaveNewServiceButton();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOServicesWebPage.class);
    }

    public VNextBOServicesWebPage addNewService(String servicename, String servicedesc, String servicepricetype, String serviceprice) {
        setServiceName(servicename);
        setServiceDescription(servicedesc);
        selectServicePriceType(servicepricetype);
        setServicePriceValue(serviceprice);
        clickSaveNewServiceButton();
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOServicesWebPage.class);
    }

    public VNextBOServicesWebPage addNewPercentageService(String servicename, String servicetype, String servicedesc, String servicepricetype, String serviceprice) {
        setServiceName(servicename);
        selectServiceType(servicetype);
        setServiceDescription(servicedesc);
        selectServicePriceType(servicepricetype);
        setServicePercentageValue(serviceprice);
        return saveNewService();
    }

    public VNextBOAddNewServiceDialog setServiceName(String servicename) {
        Utils.clearAndType(serviceNameField, servicename);
        return this;
    }

    public String getServiceName() {
        return servicenamefld.getValue();
    }

    public String getServiceType() {
        return servicetypecmb.getText();
    }

    public VNextBOAddNewServiceDialog selectServiceType(String servicetype) {
        wait.until(ExpectedConditions.elementToBeClickable(servicetypecmb)).click();
        waitABit(300);
        waitLong.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(By.xpath("//ul[@id='popup-services-type_listbox']/li/span[text()='" + servicetype + "']"))))
                .click();
        waitABit(500);
        return this;
    }

    public VNextBOAddNewServiceDialog setServiceDescription(String servicedesc) {
        try {
            waitABit(1000);
            wait.until(ExpectedConditions.elementToBeClickable(servicedescfld)).clear();
            servicedescfld.sendKeys(servicedesc);
            waitABit(1000);
        } catch (Exception e) {
            System.err.println(e);
        }
        return this;
    }

    //    public String getServiceDescription() {
//        return servicedescfld.getValue();
//    }
    public String getServiceDescription() {
        return servicedescfld.getText();
    }

    public VNextBOAddNewServiceDialog selectServicePriceType(String servicepricetype) {
        wait.until(ExpectedConditions.elementToBeClickable(servicepricetypecmb)).click();
        waitABit(500);
        waitLong
                .until(ExpectedConditions.elementToBeClickable(driver
                        .findElement(By.xpath("//ul[@id='price-type_listbox']/li/div/span[text()='" + servicepricetype + "']"))))
                .click();
        try {
            wait.until(ExpectedConditions.visibilityOf(priceTypeClosed));
        } catch (Exception ignored) {
            waitABit(500);
        }
        return this;
    }

    public VNextBOAddNewServiceDialog selectPartCategory(String servicePartCategory) {
        By xpath = By.xpath("//ul[@id='servicePopup-basicParts-category_listbox']/li[text() = '"
                + servicePartCategory + "']");
        return selectPartOption(servicePartCategoryCmb, servicePartCategoryClosed, xpath);
    }

    public VNextBOAddNewServiceDialog selectSubcategory(String servicePartSubcategory) {
        By xpath = By.xpath("//ul[@id='servicePopup-basicParts-subCategory_listbox']/li[text() = '"
                + servicePartSubcategory + "']");
        return selectPartOption(servicePartSubcategoryCmb, servicePartSubcategoryClosed, xpath);
    }

    public VNextBOAddNewServiceDialog selectPartName(String servicePartName) {
        By xpath = By.xpath("//ul[@id='servicePopup-basicParts-partName_listbox']/li[text() = '"
                + servicePartName + "']");
        return selectPartOption(servicePartNameCmb, servicePartNameClosed, xpath);
    }

    private VNextBOAddNewServiceDialog selectPartOption(WebElement combobox, WebElement comboboxClosed, By option) {
        wait.until(ExpectedConditions.elementToBeClickable(combobox)).click();
        waitABit(300);
        waitLong
                .until(ExpectedConditions.elementToBeClickable(driver
                        .findElement(option)))
                .click();
        try {
            wait.until(ExpectedConditions.visibilityOf(comboboxClosed));
        } catch (Exception ignored) {
            waitABit(500);
        }
        return this;
    }

    public boolean isServicePriceTypeVisible() {
        return driver.findElements(By.xpath("//span[@aria-owns='price-type_listbox']/span/span")).size() > 0;
    }

    /**
     * Keys.chord is used to solve the problem with clear() method while editing
     */
    public VNextBOAddNewServiceDialog setServicePriceValue(String servicepricevalue) {
        setAttributeWithJS(servicePriceTypingField, "style", "display: inline-block;");
        servicePriceTypingField.sendKeys(Keys.chord(Keys.CONTROL, Keys.HOME));
        servicePriceTypingField.sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.END));
        wait.until(ExpectedConditions.elementToBeClickable(servicePriceTypingField)).sendKeys(servicepricevalue);
        servicePriceTypingField.sendKeys(Keys.TAB);
        waitABit(1500);


//        wait.until(ExpectedConditions.elementToBeClickable(servicePriceField)).click();
//        wait.until(ExpectedConditions.elementToBeClickable(servicePriceTypingField)).clear();
//        clearAndType(servicePriceTypingField, servicepricevalue);

//        catch (Exception ignored) {
//            setAttributeWithJS(servicePriceTypingField, "aria-valuenow", servicepricevalue);
//            setAttributeWithJS(priceTypeDropDown, "aria-expanded", "false");
//            waitABit(500);
//        }
        return this;
    }

    public VNextBOAddNewServiceDialog setServiceLaborRate(String laborRateValue) {
        wait.until(ExpectedConditions.elementToBeClickable(laborRateField)).click();
        wait.until(ExpectedConditions.elementToBeClickable(laborRateTypingField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(laborRateTypingField)).sendKeys(laborRateValue);
        waitABit(500);
        return this;
    }

    public VNextBOAddNewServiceDialog setServiceDefaultLaborTime(String defaultLaborTime) {
        setAttributeWithJS(defaultLaborTimeField, "aria-valuenow", defaultLaborTime);
        wait.until(ExpectedConditions.elementToBeClickable(defaultLaborTimeField)).click();
        wait.until(ExpectedConditions.elementToBeClickable(defaultLaborTimeTypingField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(defaultLaborTimeTypingField)).sendKeys(defaultLaborTime);
        waitABit(500);
        return this;
    }

    public VNextBOAddNewServiceDialog checkUseLaborTimesCheckbox() {
//        wait.until(ExpectedConditions.visibilityOf(serviceSource));
        waitABit(1000);
        try {
            if (serviceSource.getAttribute("style").contains("display: none;")) {
                wait.until(ExpectedConditions.elementToBeClickable(useLaborTimesCheckbox)).click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public VNextBOAddNewServiceDialog setServicePercentageValue(String servicepercentagevalue) {
        wait.until(ExpectedConditions.elementToBeClickable(servicePercentageField));
        servicePercentageField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        new Actions(driver).sendKeys(servicepercentagevalue).build().perform();
        waitABit(1500);
        return this;
    }

    public WebElement getServicePricePercentageValueTxtField() {
        WebElement pricepercentagefld = null;
        List<WebElement> priceflds = driver.findElements(By.xpath("//span[@class='k-numeric-wrap k-state-default k-expand-padding']/input[@class='k-formatted-value k-input']"));
        for (WebElement elm : priceflds) {
            if (elm.isDisplayed()) {
                pricepercentagefld = elm;
            }
        }
        return pricepercentagefld;
    }

    public VNextBOServicesWebPage saveNewService() {
        clickServiceAddButton();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(serviceaddbtnby));
        waitForLoading();
        return PageFactory.initElements(
                driver, VNextBOServicesWebPage.class);
    }

    public void clickServiceAddButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(newservicepopup.findElement(serviceaddbtnby))).click();
        } catch (Exception e) {
            Assert.fail("Tha \"Add service\" button has not been clicked!", e);
        }
    }

    public VNextBOServicesWebPage clickSaveNewServiceButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(saveNewServiceButton));
        } catch (Exception ignored) {
        }
        try {
            clickWithJS(saveNewServiceButton);
            try {
                wait.until(ExpectedConditions.attributeToBe(saveNewServiceButton, "disabled", "disabled"));
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            Assert.fail("Tha \"Add service\" button has not been clicked!", e);
        }
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOServicesWebPage.class);
    }

    public VNextBOServicesWebPage clickRejectNewServiceButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(rejectNewServiceButton)).click();
        } catch (Exception ignored) {
        }
        try {
            wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.attributeContains(newservicepopup, "style", "display: none;"));
        } catch (Exception e) {
            Assert.fail("Tha \"Add new service\" modal dialog has not been clised!", e);
        }
        waitForLoading();
        return PageFactory.initElements(driver, VNextBOServicesWebPage.class);
    }

    public VNextBOAddNewServiceDialog clickNewServicePopupAddButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(servicePopupAddButton)).click();
        } catch (Exception ignored) {}
        return this;
    }

    public List<String> getServicePartsInfoList() {
        wait.until(ExpectedConditions.visibilityOfAllElements(servicePartsInfoList));
        return servicePartsInfoList
                .stream()
                .map(WebElement::getText)
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public VNextBOServicesWebPage closeNewServiceDialog() {
        newservicepopup.findElement(closenewservicedialogbtnxpath).click();
        waitABit(500);
        return PageFactory.initElements(
                driver, VNextBOServicesWebPage.class);
    }

    public String getErrorMessage() {
        return errormsg.getText();
    }

}

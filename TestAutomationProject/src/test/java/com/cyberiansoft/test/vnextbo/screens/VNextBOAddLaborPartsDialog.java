package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOAddLaborPartsDialog extends VNextBOBaseWebPage {

    @FindBy(id = "parts-labor-service-form")
    private WebElement addLaborDialog;

    @FindBy(xpath = "//span[contains(@aria-controls, 'parts-labor-service')]")
    private WebElement laborArrow;

    @FindBy(xpath = "//span[contains(@aria-controls, 'parts-labor-service')]/span")
    private WebElement laborArrowLoader;

    @FindBy(xpath = "//input[contains(@aria-owns, 'form-labors_listbox')]")
    private WebElement laborInputField;

    @FindBy(id = "parts-labor-service-form-labors-list")
    private WebElement laborDropDown;

    @FindBy(xpath = "//button[@data-automation-id='parts-labor-service-form-submit']")
    private WebElement addLaborButton;

    @FindBy(xpath = "//button[@data-automation-id='parts-labor-service-form-cancel']")
    private WebElement cancelAddingLaborButton;

    @FindBy(xpath = "//div[@id='parts-labor-service-form']//button[@aria-label='Close']")
    private WebElement xIconForAddingLabor;

    @FindBy(xpath = "//div[@id='parts-labor-service-form']//span[@title='clear']")
    private WebElement clearIcon;

    @FindBy(xpath = "//ul[contains(@id, 'parts-labor-service')]/li")
    private List<WebElement> laborListBoxOptions;

    public VNextBOAddLaborPartsDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isAddLaborDialogDisplayed() {
        return isElementDisplayed(addLaborDialog);
    }

    public boolean isLaborClearIconDisplayed() {
        return isElementDisplayed(clearIcon);
    }

    public VNextBOAddLaborPartsDialog clickClearLaborIcon() {
        final int size = laborListBoxOptions.size();
        Utils.clickElement(clearIcon);
        try {
            WaitUtilsWebDriver.getShortWait().until(e -> !laborArrowLoader.getAttribute("class").contains("loading"));
        } catch (Exception ignored) {}
        try {
            WaitUtilsWebDriver.getShortWait().until(e -> size != laborListBoxOptions.size());
        } catch (Exception ignored) {}
        return this;
    }

    public VNextBOAddLaborPartsDialog setLaborService(String laborService) {
        clickLaborServiceBox();
        selectLaborService(laborService);
        return this;
    }

    private VNextBOAddLaborPartsDialog clickLaborServiceBox() {
        wait.until(ExpectedConditions.elementToBeClickable(laborArrow)).click();
        return this;
    }

    private VNextBOAddLaborPartsDialog selectLaborService(String laborService) {
        selectOptionInDropDown(laborDropDown, laborListBoxOptions, laborService);
        return this;
    }

    public VNextBOPartsDetailsPanel clickAddLaborButtonForDialog() {
        wait.until(ExpectedConditions.elementToBeClickable(addLaborButton)).click();
        waitForLoading();
        refreshPage();
        return PageFactory.initElements(driver, VNextBOPartsDetailsPanel.class);
    }

    public VNextBOPartsDetailsPanel clickCancelAddingLaborButtonForDialog() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelAddingLaborButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(cancelAddingLaborButton));
        return PageFactory.initElements(driver, VNextBOPartsDetailsPanel.class);
    }

    public VNextBOPartsDetailsPanel clickXIconToCancelAddingLaborButtonForDialog() {
        wait.until(ExpectedConditions.elementToBeClickable(xIconForAddingLabor)).click();
        wait.until(ExpectedConditions.invisibilityOf(xIconForAddingLabor));
        return PageFactory.initElements(driver, VNextBOPartsDetailsPanel.class);
    }

    public VNextBOAddLaborPartsDialog typeLaborName(String labor) {
        setData(laborInputField, labor);
        WaitUtilsWebDriver.waitABit(2000);
        return this;
    }

    public VNextBOAddLaborPartsDialog selectLaborNameFromBoxList(String customer) {
        return (VNextBOAddLaborPartsDialog) selectDataFromBoxList(laborListBoxOptions, laborDropDown, customer);
    }

    public boolean isLaborDisplayed(String labor) {
        return isDataDisplayed(laborListBoxOptions, labor);
    }

    public int getLaborOptionsQuantity() {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElements(laborListBoxOptions)).size();
        } catch (Exception ignored) {
            return 0;
        }
    }
}
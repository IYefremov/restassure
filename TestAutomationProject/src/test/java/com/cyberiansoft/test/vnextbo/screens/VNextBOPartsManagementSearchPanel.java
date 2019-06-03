package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOPartsManagementSearchPanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//section[@id='parts-view']//div[@class='pull-right custom-search']")
    private WebElement searchPanel;

    @FindBy(id = "advSearchPartsOrders-freeText")
    private WebElement searchInputField;

    @FindBy(id = "advSearchPartsOrders-search")
    private WebElement searchLoupeIcon;

    @FindBy(xpath = "//div[@id='parts-orders-search']//i[@class='icon-cancel-circle']")
    private WebElement searchXIcon;

    @FindBy(xpath = "//div[@id='parts-orders-search']//div[@class='custom-search__info-text']")
    private WebElement searchOptionsInfo;

    @FindBy(id = "advSearchPartsOrders-caret")
    private WebElement partsOrdersSearchCaret;

    @FindBy(id = "advSearchPartsOrders-savedSearchList")
    private WebElement partsOrdersSearchOptionsDropDown;

    @FindBy(xpath = "//div[@id='advSearchPartsOrders-savedSearchList']/div[contains(text(), 'Advanced Search')]")
    private WebElement advancedSearchDropDownOption;

    @FindBy(xpath = "//div[@id='advSearchPartsOrders-savedSearchList']//i[@class='icon-gear']")
    private WebElement advancedSearchDropDownSettingsIcon;

    @FindBy(xpath = "//section[@id='parts-view']//div[text()='Search']")
    private WebElement searchSection;

    @FindBy(xpath = "//div[@id='advSearchPartsOrders-savedSearchList']//span[contains(@data-bind, 'applySavedSearch')]")
    private List<WebElement> savedSearchNames;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalConfirmButton']")
    private WebElement confirmDeletingSearchNameButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCancelButton']")
    private WebElement cancelDeletingSearchNameDeleteButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCancelButton']")
    private WebElement xIconForDeletingSearchName;

    public VNextBOPartsManagementSearchPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isPartsManagementSearchPanelDisplayed() {
        return isElementDisplayed(searchPanel);
    }

    public VNextBOPartsManagementSearchPanel setPartsSearchText(String value) {
        wait.until(ExpectedConditions.elementToBeClickable(searchInputField)).click();
        searchInputField.clear();
        searchInputField.sendKeys(value);
        waitABit(500);
        return this;
    }

    public VNextBOPartsManagementSearchPanel clickSearchLoupeIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(searchLoupeIcon)).click();
        waitForLoading();
        return this;
    }

    public boolean isSearchXIconDisplayed() {
        return isElementDisplayed(searchXIcon);
    }

    public boolean isSearchOptionsInfoTextEmpty() {
        try {
            waitShort.until((ExpectedCondition<Boolean>) driver -> searchOptionsInfo.getText().equals(""));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public VNextBOPartsManagementSearchPanel clickSearchXIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(searchXIcon)).click();
        waitForLoading();
        return this;
    }

    public String getSearchOptionsInfoText() {
        return wait.until(ExpectedConditions.visibilityOf(searchOptionsInfo)).getText();
    }

    public VNextBOPartsManagementSearchPanel clickSearchCaret() {
        waitABit(5000);
        wait.until(ExpectedConditions.elementToBeClickable(partsOrdersSearchCaret)).click();
        wait.until(ExpectedConditions.visibilityOf(partsOrdersSearchOptionsDropDown));
        return this;
    }

    public boolean isAdvancedSearchDisplayedInDropDown() {
        return isElementDisplayed(advancedSearchDropDownOption);
    }

    public boolean isAdvancedSearchSettingsIconDisplayed() {
        return isElementDisplayed(advancedSearchDropDownSettingsIcon);
    }

    public boolean areSavedSearchNamesDisplayed() {
        wait.until(ExpectedConditions.visibilityOfAllElements(savedSearchNames));
        return savedSearchNames
                .stream()
                .noneMatch(e -> e
                        .getText()
                        .equals(""));
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickAdvancedSearchOption() {
        wait.until(ExpectedConditions.elementToBeClickable(advancedSearchDropDownOption)).click();
        waitABit(1500);
        return PageFactory.initElements(driver, VNextBOPartsManagementAdvancedSearchDialog.class);
    }

    public VNextBOPartsManagementSearchPanel clickSearchSection() {
        wait.until(ExpectedConditions.elementToBeClickable(searchSection)).click();
        return this;
    }

    public VNextBOPartsManagementAdvancedSearchDialog clickEditForSearchOptionInDropDown(String option) {
        try {
            waitShort.until(ExpectedConditions
                    .elementToBeClickable(driver.findElement(By
                            .xpath("//div[@id='advSearchPartsOrders-savedSearchList']//span[text()='" + option + "']/../i"))))
                    .click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PageFactory.initElements(driver, VNextBOPartsManagementAdvancedSearchDialog.class);
    }

    public boolean isSearchOptionDisplayed(String option) {
        try {
            waitShort.until(ExpectedConditions.visibilityOfAllElements(savedSearchNames));
            return savedSearchNames
                    .stream()
                    .anyMatch(name -> name.getText().equals(option));
        } catch (Exception e) {
            return false;
        }
    }

    public void clickConfirmDeletingSearchNameButton() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeletingSearchNameButton)).click();
        waitForLoading();
    }

    public void clickCancelDeletingSearchNameButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelDeletingSearchNameDeleteButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(cancelDeletingSearchNameDeleteButton));
    }

    public void clickXIconForDeletingSearchName() {
        wait.until(ExpectedConditions.elementToBeClickable(xIconForDeletingSearchName)).click();
        wait.until(ExpectedConditions.invisibilityOf(xIconForDeletingSearchName));
    }

    public void verifySearchOptionIsNotDisplayedInDropDown(String option) {
        if (isSearchOptionDisplayed(option)) {
            clickEditForSearchOptionInDropDown(option).clickDeleteSavedSearchButton();
            clickConfirmDeletingSearchNameButton();
            clickSearchCaret();
            Assert.assertFalse(isSearchOptionDisplayed(option), "The option has been displayed after deleting");
        }
    }
}
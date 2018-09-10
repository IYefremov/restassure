package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditionFeatureDialog extends BasePage {

    @FindBy(xpath = "//input[@name='AddOnPricePerMonth']")
    private WebElement pricePerMonthTextField;

    @FindBy(xpath = "//input[@name='AddOnPricePerYear']")
    private WebElement pricePerYearTextField;

    @FindBy(xpath = "//button[@class='submit btn-save-edition-feature-addon']")
    private WebElement submitEditionFeatureButton;

    public EditionFeatureDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public EditionFeatureDialog typePricePerMonth(String price) {
        clearAndType(pricePerMonthTextField, price);
        return this;
    }

    @Step
    public EditionFeatureDialog typePricePerYear(String price) {
        clearAndType(pricePerYearTextField, price);
        return this;
    }

    @Step
    public PricingPage clickSubmitEditionFeatureButton() {
        clickButton(submitEditionFeatureButton);
        waitForLoading();
        return PageFactory.initElements(driver, PricingPage.class);
    }
}
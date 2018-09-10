package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddFeatureDialog extends FeatureDialog {

    @FindBy(xpath = "//div[@class='form-dialog add-feature-dialog active']")
    private WebElement addFeatureDialog;

    @FindBy(xpath = "//div[@class='form-dialog add-feature-dialog active']//button[@class='btn btn-outline btn-submit']")
    private WebElement addFeatureButton;

    @FindBy(xpath = "//div[@class='form-dialog add-feature-dialog active']//input[@id='Name']")
    private WebElement featureNameField;

    @FindBy(xpath = "//div[@class='form-dialog add-feature-dialog active']//select[@id='FeatureStateID']")
    private WebElement featureState;

    @FindBy(xpath = "//div[@class='form-dialog add-feature-dialog active']//textarea[@id='MarketingInfo']")
    private WebElement featureMarketingInfo;

    @FindBy(xpath = "//div[@class='form-dialog add-feature-dialog active']//textarea[@id='Description']")
    private WebElement featureDescription;

    public AddFeatureDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        try {
            wait.until(ExpectedConditions.visibilityOf(addFeatureDialog));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public FeatureDialog typeFeatureName(String featureName) {
        clearAndType(featureNameField, featureName);
        return this;
    }

    @Step
    public FeatureDialog selectFeatureState(String featureStateOption) {
        selectValue(featureState, featureStateOption);
        return this;
    }

    @Step
    public FeatureDialog typeMarketingInfo(String marketingInfoValue) {
        clearAndType(featureMarketingInfo, marketingInfoValue);
        return this;
    }

    @Step
    public FeatureDialog typeDescription(String descriptionValue) {
        clearAndType(featureDescription, descriptionValue);
        return this;
    }

    @Step
    public PricingPage clickAddFeatureButton() {
        clickButton(addFeatureButton);
        waitForLoading();
        return PageFactory.initElements(driver, PricingPage.class);
    }
}
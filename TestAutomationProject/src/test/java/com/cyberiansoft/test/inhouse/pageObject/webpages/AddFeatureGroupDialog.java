package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class AddFeatureGroupDialog extends BasePage {

    @FindBy(xpath = "//ul[contains(@class, 'feature-group')]//input[@name='Name']")
    private WebElement featureGroupNameField;

    @FindBy(id = "FeatureGroupStateID")
    private WebElement featureGroupState;

    @FindBy(xpath = "//ul[contains(@class, 'feature-group')]//textarea[@name='MarketingInfo']")
    private WebElement featureGroupMarketingInfoField;

    @FindBy(xpath = "//button[@class='submit btn-save-feature-group']")
    private WebElement featureGroupSubmitButton;

    @FindBy(xpath = "//div[@class='dropdown open']//ul[@class='dropdown-menu dropdown-menu-feature-group-button']")
    private WebElement featureGroupDropDown;

    public AddFeatureGroupDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        try {
            wait.until(ExpectedConditions.visibilityOf(featureGroupDropDown));
        } catch (Exception e) {
            Assert.fail("The feature group drop down has not been opened", e);
        }
    }

    @Step
    public AddFeatureGroupDialog typeFeatureGroupName(String featureGroupName) {
        clearAndType(featureGroupNameField, featureGroupName);
        return this;
    }

    @Step
    public AddFeatureGroupDialog typeMarketingInfo(String marketingInfoValue) {
        clearAndType(featureGroupMarketingInfoField, marketingInfoValue);
        return this;
    }

    @Step
    public AddFeatureGroupDialog selectFeatureGroupState(String featureGroupStateOption) {
        selectValue(featureGroupState, featureGroupStateOption);
        return this;
    }

    @Step
    public AddFeatureGroupDialog clickFeatureGroupSubmitButton() {
        clickButton(featureGroupSubmitButton);
        waitForLoading();
        return this;
    }
}